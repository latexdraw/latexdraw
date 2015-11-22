package net.sf.latexdraw.instruments;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import net.sf.latexdraw.actions.ExportFormat;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.jfx.Canvas;

import org.malai.javafx.instrument.JfxInstrument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.Inject;

/**
 * This instrument exports a drawing in different formats.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/23/2010<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class Exporter extends JfxInstrument {
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

	/** The PST generator. */
	protected PSTCodeGenerator pstGen;

	/** The dialog box that allows to define where the drawing must be exported. */
	private FileChooser fileChooserExport;

	/** The default location of the exports. */
	protected String pathExport;

	/**
	 * The latex packages that the interactive system saves by default. These
	 * packages should by set by the user and must be general, i.e. independent
	 * of any document.
	 */
	protected String defaultPackages;

	// /** The field where messages are displayed. */
	// protected JLabel statusBar;

	@Inject protected FileLoaderSaver loader;

	/**
	 * The canvas that contains the shapes to export. The canvas is used instead
	 * of the drawing because to export as picture, we paint the views into a
	 * graphics.
	 */
	@Inject protected Canvas canvas;

	/**
	 * Creates the instrument.
	 */
	public Exporter() {
		super();
		defaultPackages = ""; //$NON-NLS-1$
		reinit();
	}

	@Override
	public void reinit() {
		LaTeXGenerator.setPackages(defaultPackages);
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_LATEX_INCLUDES)) {
			final String[] lines = root.getTextContent().split(LResources.EOL);
			final String pkgs = LaTeXGenerator.getPackages();
			final StringBuilder build = new StringBuilder(LaTeXGenerator.getPackages());
			build.append(Arrays.stream(lines).filter(line -> !pkgs.contains(line)).collect(Collectors.joining(LResources.EOL, LResources.EOL, "")));
			LaTeXGenerator.setPackages(build.toString());
		}
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null||root==null)
			return;

		if(generalPreferences) {
			Element elt = document.createElement(LNamespace.XML_PATH_EXPORT);
			elt.setTextContent(pathExport);
			root.appendChild(elt);

			elt = document.createElement(LNamespace.XML_LATEX_INCLUDES);
			elt.setTextContent(defaultPackages);
			root.appendChild(elt);
		}else {
			final String ns = LPath.INSTANCE.getNormaliseNamespaceURI(nsURI);
			Element elt = document.createElement(ns+LNamespace.XML_LATEX_INCLUDES);
			elt.appendChild(document.createTextNode(LaTeXGenerator.getPackages()));
			root.appendChild(elt);
		}
	}

	// @Override
	// public void setActivated(final boolean isActivated, final boolean hide) {
	// super.setActivated(isActivated);

	// exportMenu.setVisible(isActivated || !hide);
	// exportMenu.setDisable(!isActivated);
	// }

	// @Override
	// public void setActivated(final boolean activated) {
	// setActivated(activated, false);
	// }

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new MenuPressed2Export(this));
	}

	/**
	 * @param format
	 *            The format of the document to export.
	 * @return The export dialog to select a path.
	 * @since 3.0
	 */
	protected FileChooser getExportDialog(final ExportFormat format) {
		if(fileChooserExport==null) {
			fileChooserExport = new FileChooser();
			// fileChooserExport.removeChoosableFileFilter(fileChooserExport.getFileFilter());
			// fileChooserExport.setFileFilter(fileChooserExport.getAcceptAllFileFilter());
			// fileChooserExport.setFileFilter(format.getFilter());
			fileChooserExport.setTitle(LangTool.INSTANCE.getBundle().getString("Exporter.1"));

		}

		fileChooserExport.setInitialDirectory(new File(pathExport));
		//
		// if(loader.currentFile!=null &&
		// fileChooserExport.getSelectedFile()==null) {
		// String path = loader.currentFile.getPath();
		//			if(path.contains(".")) path = path.substring(0, path.lastIndexOf('.')); //$NON-NLS-1$
		// path += format.getFileExtension();
		// fileChooserExport.setSelectedFile(new File(path));
		// }

		return fileChooserExport;
	}

	// @Override
	// public void onActionExecuted(final Action action) {
	//		statusBar.setText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.184")); //$NON-NLS-1$
	// }

	/**
	 * @return The latex packages that the interactive system saves by default.
	 * @since 3.0
	 */
	public String getDefaultPackages() {
		return defaultPackages;
	}

	/**
	 * @param defaultPkgs
	 *            The latex packages that the interactive system saves by
	 *            default. These packages should by set by the user and must be
	 *            general, i.e. independent of any document. Packages for a
	 *            given document should by set using
	 *            {@link #setPackages(String)}.
	 * @since 3.0
	 */
	public void setDefaultPackages(final String defaultPkgs) {
		if(defaultPkgs!=null) {
			if(this.defaultPackages.isEmpty())
				LaTeXGenerator.setPackages(defaultPkgs+LResources.EOL+LaTeXGenerator.getPackages());
			defaultPackages = defaultPkgs;
		}
	}

	/**
	 * @param packages
	 *            The latex packages used when exporting using latex. These
	 *            packages are defined for the current document but not for all
	 *            documents. These general packages can be set using
	 *            {@link #setDefaultPackages(String)}.
	 * @since 3.0
	 */
	public void setPackages(final String packages) {
		if(packages!=null) {
			LaTeXGenerator.setPackages(packages);
			setModified(true);
		}
	}

	/**
	 * @return The path where files are exported.
	 * @since 3.0
	 */
	public String getPathExport() {
		return pathExport;
	}

	/**
	 * @param path
	 *            The path where files are exported.
	 * @since 3.0
	 */
	public void setPathExport(final String path) {
		if(path!=null)
			pathExport = path;
	}
}

/**
 * // * This link maps menus to an export action. //
 */
// class MenuPressed2Export extends InteractorImpl<Export, MenuItemPressed,
// Exporter> {
// /**
// * Initialises the link.
// * @param ins The exporter.
// */
// protected MenuPressed2Export(final Exporter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, Export.class, MenuItemPressed.class);
// }
//
//
// @Override
// public void initAction() {
// final JMenuItem item = interaction.getMenuItem();
// final ExportFormat format;
//
// if(item==instrument.menuItemPDF)
// format = ExportFormat.PDF;
// else if(item==instrument.menuItemPDFcrop)
// format = ExportFormat.PDF_CROP;
// else if(item==instrument.menuItemEPSLatex)
// format = ExportFormat.EPS_LATEX;
// else if(item==instrument.menuItemJPG)
// format = ExportFormat.JPG;
// else if(item==instrument.menuItemPST)
// format = ExportFormat.TEX;
// else if(item==instrument.menuItemPNG)
// format = ExportFormat.PNG;
// else if(item==instrument.menuItemBMP)
// format = ExportFormat.BMP;
// else format = null;
//
// if(format!=null){
// action.setDialogueBox(instrument.getExportDialog(format));
// action.setCanvas(instrument.canvas);
// action.setFormat(format);
// action.setPstGen(instrument.pstGen);
// }
// }
//
//
// @Override
// public boolean isConditionRespected() {
// final JMenuItem item = interaction.getMenuItem();
//
// return item==instrument.menuItemPDF || item==instrument.menuItemPDFcrop ||
// item==instrument.menuItemEPSLatex ||
// item==instrument.menuItemJPG || item==instrument.menuItemPNG ||
// item==instrument.menuItemPST || item==instrument.menuItemBMP;
// }
// }
