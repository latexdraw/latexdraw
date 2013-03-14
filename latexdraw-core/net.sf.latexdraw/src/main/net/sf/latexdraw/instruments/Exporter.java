package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import net.sf.latexdraw.actions.Export;
import net.sf.latexdraw.actions.Export.ExportFormat;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.dialog.ExportDialog;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;

import org.malai.action.Action;
import org.malai.instrument.Link;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.interaction.library.MenuItemPressed;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButton;
import org.malai.swing.widget.MMenu;
import org.malai.swing.widget.MMenuItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument exports a drawing in different formats.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/23/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class Exporter extends WidgetInstrument {
	/** The title of the dialog box used to export drawings. */
	public static final String TITLE_DIALOG_EXPORT = "Drawing export";

	/** The label of the exportAsBMPFile item */
	public final static String LABEL_EXPORT_BMP = LangTool.INSTANCE.getString16("LaTeXDrawFrame.6"); //$NON-NLS-1$

	/** The label of the exportAsPNGFile item */
	public final static String LABEL_EXPORT_PNG = LangTool.INSTANCE.getString16("LaTeXDrawFrame.7"); //$NON-NLS-1$

	/** The label of the exportCodeMenu item */
	public final static String LABEL_EXPORT_TRICKS = LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.19"); //$NON-NLS-1$

	/** The label of the exportDrawMenu item */
	public final static String LABEL_EXPORT_JPG = LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.20"); //$NON-NLS-1$

	/** The label of the menu export as */
	public final static String LABEL_EXPORT_AS = LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.16"); //$NON-NLS-1$

	/** The canvas that contains the shapes to export. The canvas is used instead of the drawing
	 * because to export as picture, we paint the views into a graphics. */
	protected ICanvas canvas;

	/** The drawing that contains the shapes to export. */
	protected IDrawing drawing;

	/** The PST generator. */
	protected PSTCodeGenerator pstGen;

	/** The button used to export the drawing as a pdf document. */
	protected MButton pdfButton;

	/** The export menu that contains all the export menu item. */
	protected MMenu exportMenu;

	/** The menu item that export as PST code. */
	protected MMenuItem menuItemPST;

	/** The menu item that export as JPG picture. */
	protected MMenuItem menuItemJPG;

	/** The menu item that export as PNG picture. */
	protected MMenuItem menuItemPNG;

	/** The menu item that export as BMP picture. */
	protected MMenuItem menuItemBMP;

	/** The menu item that export as PDF document. */
	protected MMenuItem menuItemPDF;

	/** The menu item that export as PS (using latex) document. */
	protected MMenuItem menuItemEPSLatex;

	/** The menu item that export as PDF (using pdfcrop) document. */
	protected MMenuItem menuItemPDFcrop;

	/** The dialog box that allows to define where the drawing must be exported. */
	protected ExportDialog fileChooserExport;

	/** The default location of the exports. */
	protected String pathExport;

	/**
	 * The latex packages that the interactive system saves by default.
	 * These packages should by set by the user and must be general, i.e.
	 * independent of any document.
	 */
	protected String defaultPackages;

	/** The field where messages are displayed. */
	protected JLabel statusBar;



	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param canvas The canvas that contains the views to export (for pictures).
	 * @param drawing The drawing that contains the shapes to export (for latex and code).
	 * @param statusBar The status bar where messages are displayed.
	 * @param pstGen The PST generator to use.
	 * @throws IllegalArgumentException If one of the given arguments is null.
	 * @since 3.0
	 */
	public Exporter(final UIComposer<?> composer, final ICanvas canvas, final IDrawing drawing, final JLabel statusBar,
			final PSTCodeGenerator pstGen) {
		super(composer);

		defaultPackages		= ""; //$NON-NLS-1$
		this.statusBar		= Objects.requireNonNull(statusBar);
		this.drawing		= Objects.requireNonNull(drawing);
		this.canvas 		= Objects.requireNonNull(canvas);
		this.pstGen 		= Objects.requireNonNull(pstGen);

		initialiseWidgets();
		reinit();
	}


	@Override
	protected void initialiseWidgets() {
		// Widgets initialisation
		pdfButton 		= new MButton(LResources.PDF_ICON);
		exportMenu		= new MMenu(LABEL_EXPORT_AS, true);
		menuItemBMP		= new MMenuItem(LABEL_EXPORT_BMP, KeyEvent.VK_B);
		menuItemEPSLatex= new MMenuItem("eps (latex) picture", KeyEvent.VK_S);
		menuItemJPG		= new MMenuItem(LABEL_EXPORT_JPG, KeyEvent.VK_J);
		menuItemPDF		= new MMenuItem("pdf (latex) picture", KeyEvent.VK_F);
		menuItemPDFcrop	= new MMenuItem("pdf (latex+pdfcrop) picture", KeyEvent.VK_C);
		menuItemPNG		= new MMenuItem(LABEL_EXPORT_PNG, KeyEvent.VK_P);
		menuItemPST		= new MMenuItem(LABEL_EXPORT_TRICKS, KeyEvent.VK_T);
		exportMenu.add(menuItemPST);
		exportMenu.add(menuItemJPG);
		exportMenu.add(menuItemPNG);
		exportMenu.add(menuItemBMP);
		exportMenu.add(menuItemPDF);
		exportMenu.add(menuItemEPSLatex);
		exportMenu.add(menuItemPDFcrop);
	}


	@Override
	public void reinit() {
		LaTeXGenerator.setPackages(defaultPackages);
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_LATEX_INCLUDES))
			LaTeXGenerator.setPackages(root.getTextContent());
	}


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null || root==null)
			return ;

		Element elt;

		if(generalPreferences) {
            elt = document.createElement(LNamespace.XML_PATH_EXPORT);
            elt.setTextContent(pathExport);
            root.appendChild(elt);

            elt = document.createElement(LNamespace.XML_LATEX_INCLUDES);
            elt.setTextContent(defaultPackages);
            root.appendChild(elt);
		}else {
			final String ns = LPath.INSTANCE.getNormaliseNamespaceURI(nsURI);

			elt = document.createElement(ns + LNamespace.XML_LATEX_INCLUDES);
			elt.appendChild(document.createCDATASection(LaTeXGenerator.getPackages()));
			root.appendChild(elt);
		}
	}



	@Override
	public void setActivated(final boolean activated, final boolean hide) {
		super.setActivated(activated);

		exportMenu.setVisible(activated || !hide);
		pdfButton.setVisible(activated || !hide);
		exportMenu.setEnabled(activated);
		pdfButton.setEnabled(activated);
	}


	@Override
	public void setActivated(final boolean activated) {
		setActivated(activated, false);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ButtonPressed2Export(this));
			addLink(new MenuPressed2Export(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The button used to export the drawing as a pdf document.
	 * @since 3.0
	 */
	public MButton getPdfButton() {
		return pdfButton;
	}


	/**
	 * @return The export menu that contains all the export menu item.
	 * @since 3.0
	 */
	public MMenu getExportMenu() {
		return exportMenu;
	}


	/**
	 * @param format The format of the document to export.
	 * @return The export dialog to select a path.
	 * @since 3.0
	 */
	protected ExportDialog getExportDialog(final ExportFormat format) {
		if(fileChooserExport==null)
			fileChooserExport = new ExportDialog(pathExport);// currentFile==null ? pathExport : currentFile.getPath());

		// Setting the dialog.
		fileChooserExport.removeChoosableFileFilter(fileChooserExport.getFileFilter());
		fileChooserExport.setFileFilter(fileChooserExport.getAcceptAllFileFilter());
		fileChooserExport.setFileFilter(format.getFilter());
		fileChooserExport.setDialogTitle(Exporter.TITLE_DIALOG_EXPORT);

		return fileChooserExport;
	}



	@Override
	public void onActionExecuted(final Action action) {
		statusBar.setText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.184")); //$NON-NLS-1$
	}



	/**
	 * @return The latex packages that the interactive system saves by default.
	 * @since 3.0
	 */
	public String getDefaultPackages() {
		return defaultPackages;
	}



	/**
	 * @param defaultPackages The latex packages that the interactive system saves by default.
	 * These packages should by set by the user and must be general, i.e. independent of any document.
	 * Packages for a given document should by set using {@link #setPackages(String)}.
	 * @since 3.0
	 */
	public void setDefaultPackages(final String defaultPackages) {
		if(defaultPackages!=null) {
			if(this.defaultPackages.length()==0)
				LaTeXGenerator.setPackages(defaultPackages +LResources.EOL+LaTeXGenerator.getPackages());
			this.defaultPackages = defaultPackages;
		}
	}


	/**
	 * @param packages The latex packages used when exporting using latex.
	 * These packages are defined for the current document but not for all documents.
	 * These general packages can be set using {@link #setDefaultPackages(String)}.
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
	 * @param pathExport The path where files are exported.
	 * @since 3.0
	 */
	public void setPathExport(final String pathExport) {
		if(pathExport!=null)
			this.pathExport = pathExport;
	}
}


/**
 * This link maps menus to an export action.
 */
class MenuPressed2Export extends Link<Export, MenuItemPressed, Exporter> {
	/**
	 * Initialises the link.
	 * @param ins The exporter.
	 */
	protected MenuPressed2Export(final Exporter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Export.class, MenuItemPressed.class);
	}


	@Override
	public void initAction() {
		final JMenuItem item = interaction.getMenuItem();
		ExportFormat format;

		if(item==instrument.menuItemPDF)
			format = ExportFormat.PDF;
		else if(item==instrument.menuItemPDFcrop)
			format = ExportFormat.PDF_CROP;
		else if(item==instrument.menuItemEPSLatex)
			format = ExportFormat.EPS_LATEX;
		else if(item==instrument.menuItemJPG)
			format = ExportFormat.JPG;
		else if(item==instrument.menuItemPST)
			format = ExportFormat.TEX;
		else if(item==instrument.menuItemPNG)
			format = ExportFormat.PNG;
		else if(item==instrument.menuItemBMP)
			format = ExportFormat.BMP;
		else format = null;

		action.setDialogueBox(instrument.getExportDialog(format));
		action.setCanvas(instrument.canvas);
		action.setFormat(format);
		action.setPstGen(instrument.pstGen);
	}


	@Override
	public boolean isConditionRespected() {
		final JMenuItem item = interaction.getMenuItem();

		return item==instrument.menuItemPDF || item==instrument.menuItemPDFcrop || item==instrument.menuItemEPSLatex ||
			item==instrument.menuItemJPG || item==instrument.menuItemPNG ||
			item==instrument.menuItemPST || item==instrument.menuItemBMP;
	}
}



/**
 * This link maps buttons to an export action.
 */
class ButtonPressed2Export extends Link<Export, ButtonPressed, Exporter> {
	/**
	 * Initialises the link.
	 * @param ins The exporter.
	 */
	protected ButtonPressed2Export(final Exporter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Export.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		action.setDialogueBox(instrument.getExportDialog(ExportFormat.PDF));
		action.setCanvas(instrument.canvas);
		action.setFormat(ExportFormat.PDF);
		action.setPstGen(instrument.pstGen);
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.pdfButton;
	}
}
