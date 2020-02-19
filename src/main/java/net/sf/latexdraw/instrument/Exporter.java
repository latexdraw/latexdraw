/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.ui.JfxUI;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import net.sf.latexdraw.command.Export;
import net.sf.latexdraw.command.ExportFormat;
import net.sf.latexdraw.command.ExportTemplate;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Element;

/**
 * This instrument exports a drawing to different formats.
 * @author Arnaud BLOUIN
 */
public class Exporter extends JfxInstrument implements Initializable {
	/** The export menu that contains all the export menu item. */
	@FXML protected MenuButton exportMenu;
	/** The menu item that export as PST code. */
	@FXML protected MenuItem menuItemPST;
	/** The menu item that export as JPG picture. */
	@FXML protected MenuItem menuItemJPG;
	/** The menu item that export as PNG picture. */
	@FXML protected MenuItem menuItemPNG;
	/** The menu item that export as BMP picture. */
	@FXML protected MenuItem menuItemBMP;
	/** The menu item that export as PDF document. */
	@FXML protected MenuItem menuItemPDF;
	/** The menu item that export as PS (using latex) document. */
	@FXML protected MenuItem menuItemEPSLatex;
	/** The menu item that export as PDF (using pdfcrop) document. */
	@FXML protected MenuItem menuItemPDFcrop;
	@FXML protected MenuItem exportTemplateMenu;
	/** The dialog box that allows to define where the drawing must be exported. */
	private final @NotNull FileChooser fileChooserExport;
	private final @NotNull PreferencesService prefs;
	/** The PST generator. */
	private final @NotNull PSTCodeGenerator pstGen;
	private final @NotNull LaTeXDataService latexData;
	private final @NotNull StatusBarController statusBar;
	private final @NotNull TemplateManager templateManager;
	private final @NotNull JfxUI app;
	/**
	 * The canvas that contains the shapes to export. The canvas is used instead
	 * of the drawing because to export as picture, we paint the views into a graphics.
	 */
	private final @NotNull Canvas canvas;
	private final @NotNull SVGDocumentGenerator svgGen;

	private @Nullable TextInputDialog templateNameInput;
	private @Nullable Alert alertReplace;


	@Inject
	public Exporter(final PreferencesService prefs, final PSTCodeGenerator pstGen, final StatusBarController statusBar, final TemplateManager templateManager,
					final JfxUI app, final Canvas canvas, final SVGDocumentGenerator svgGen, final LaTeXDataService latexData) {
		super();
		this.prefs = Objects.requireNonNull(prefs);
		this.pstGen = Objects.requireNonNull(pstGen);
		this.latexData = Objects.requireNonNull(latexData);
		this.statusBar = Objects.requireNonNull(statusBar);
		this.templateManager = Objects.requireNonNull(templateManager);
		this.app = Objects.requireNonNull(app);
		this.canvas = Objects.requireNonNull(canvas);
		this.svgGen = Objects.requireNonNull(svgGen);
		fileChooserExport = new FileChooser();
		fileChooserExport.setTitle(prefs.getBundle().getString("Exporter.1"));
	}

	private @NotNull TextInputDialog getTemplateNameInput() {
		if(templateNameInput == null) {
			templateNameInput = new TextInputDialog("templateFileName"); //NON-NLS
			templateNameInput.setHeaderText(prefs.getBundle().getString("DrawContainer.nameTemplate"));
		}
		return templateNameInput;
	}

	private @NotNull Alert getTemplateNameAlert() {
		if(alertReplace == null) {
			alertReplace = new Alert(Alert.AlertType.CONFIRMATION);
			alertReplace.setHeaderText(prefs.getBundle().getString("DrawContainer.overwriteTemplate"));
			alertReplace.setTitle(prefs.getBundle().getString("LaTeXDrawFrame.42"));
		}
		return alertReplace;
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_LATEX_INCLUDES)) {
			final String[] lines = root.getTextContent().split(SystemUtils.getInstance().eol);
			final String pkgs = latexData.getPackages();
			latexData.setPackages(latexData.getPackages() +
				Arrays.stream(lines).filter(line -> !pkgs.contains(line)).collect(Collectors.joining(SystemUtils.getInstance().eol, SystemUtils.getInstance().eol, "")));
		}
	}

	@Override
	public void setActivated(final boolean isActivated, final boolean hide) {
		super.setActivated(isActivated);
		exportMenu.setVisible(isActivated || !hide);
		exportMenu.setDisable(!isActivated);
	}

	@Override
	public void setActivated(final boolean activated) {
		setActivated(activated, false);
	}

	@Override
	protected void configureBindings() {
		final var bindingFragment = menuItemBinder()
			.end(() -> statusBar.getLabel().setText(prefs.getBundle().getString("LaTeXDrawFrame.184")));

		bindingFragment
			.toProduce(i -> new Export(canvas, pstGen, (ExportFormat) i.getWidget().getUserData(), getExportDialog((ExportFormat) i.getWidget().getUserData())))
			.on(menuItemBMP, menuItemEPSLatex, menuItemJPG, menuItemPDF, menuItemPDFcrop, menuItemPNG, menuItemPST)
			.when(i -> i.getWidget().getUserData() instanceof ExportFormat)
			.bind();

		bindingFragment
			.toProduce(() -> new ExportTemplate(templateManager.templatePane, svgGen, app, statusBar.getProgressBar(), statusBar.getLabel(),
				getTemplateNameAlert(), getTemplateNameInput()))
			.on(exportTemplateMenu)
			.bind();

		exportTemplateMenu.disableProperty().bind(canvas.getDrawing().getSelection().getShapes().emptyProperty());
	}

	/**
	 * @param format The format of the document to export.
	 * @return The export dialog to select a path.
	 */
	protected @NotNull FileChooser getExportDialog(final ExportFormat format) {
		fileChooserExport.getExtensionFilters().clear();
		fileChooserExport.getExtensionFilters().addAll(format.getFilter());

		if(!new File(prefs.getPathExport()).isDirectory()) {
			prefs.getCurrentFile().filter(f -> f.isDirectory()).ifPresent(f -> fileChooserExport.setInitialDirectory(new File(f.getPath()).getParentFile()));
		}else {
			fileChooserExport.setInitialDirectory(new File(prefs.getPathExport()));
		}

		return fileChooserExport;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(false);
		canvas.getDrawing().getShapes().addListener((ListChangeListener<Shape>) c -> setActivated(!canvas.getDrawing().isEmpty()));

		menuItemPDF.setUserData(ExportFormat.PDF);
		menuItemPDFcrop.setUserData(ExportFormat.PDF_CROP);
		menuItemEPSLatex.setUserData(ExportFormat.EPS_LATEX);
		menuItemJPG.setUserData(ExportFormat.JPG);
		menuItemPST.setUserData(ExportFormat.TEX);
		menuItemPNG.setUserData(ExportFormat.PNG);
		menuItemBMP.setUserData(ExportFormat.BMP);
	}
}
