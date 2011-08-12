package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.latexdraw.actions.Export;
import net.sf.latexdraw.actions.Export.ExportFormat;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.dialog.ExportDialog;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;

import org.malai.action.Action;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.ButtonPressed;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.widget.MButton;
import org.malai.widget.MMenu;
import org.malai.widget.MMenuItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * This instrument exports a drawing in different formats.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class Exporter extends Instrument {
	/** The title of the dialog box used to export drawings. */
	public static final String TITLE_DIALOG_EXPORT = "Drawing export";

	/** The label of the exportAsBMPFile item */
	public final static String LABEL_EXPORT_BMP = LangTool.LANG.getString16("LaTeXDrawFrame.6"); //$NON-NLS-1$

	/** The label of the exportAsPNGFile item */
	public final static String LABEL_EXPORT_PNG = LangTool.LANG.getString16("LaTeXDrawFrame.7"); //$NON-NLS-1$

	/** The label of the exportCodeMenu item */
	public final static String LABEL_EXPORT_TRICKS = LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.19"); //$NON-NLS-1$

	/** The label of the exportDrawMenu item */
	public final static String LABEL_EXPORT_JPG = LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.20"); //$NON-NLS-1$

	/** The label of the menu export as */
	public final static String LABEL_EXPORT_AS = LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.16"); //$NON-NLS-1$

	/** The canvas that contains the shapes to export. The canvas is used instead of the drawing
	 * because to export as picture, we paint the views into a graphics. */
	protected ICanvas canvas;

	/** The drawing that contains the shapes to export. */
	protected IDrawing drawing;

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

	/** The current location where exports occur. */
	protected File currentFile;

	/** The default location of the exports. */
	protected String pathExport;

	/** The compression rate of JPG pictures. */
	protected float compressionRate;

	/** The path where latex binaries are. */
	protected String latexPathDistrib;

	/**
	 * The latex packages that the interactive system saves by default.
	 * These packages should by set by the user and must be general, i.e.
	 * independent of any document.
	 * Packages for a given document should by set to the attribute {@link #packages}
	 */
	protected String defaultPackages;

	/** The field where messages are displayed. */
	protected JTextField statusBar;



	/**
	 * Creates the instrument.
	 * @param canvas The canvas that contains the views to export (for pictures).
	 * @param drawing The drawing that contains the shapes to export (for latex and code).
	 * @param statusBar The status bar where messages are displayed.
	 * @throws IllegalArgumentException If one of the given arguments is null.
	 * @since 3.0
	 */
	public Exporter(final ICanvas canvas, final IDrawing drawing, final JTextField statusBar) {
		super();

		if(canvas==null || drawing==null || statusBar==null)
			throw new IllegalArgumentException();

		defaultPackages		= ""; //$NON-NLS-1$
		latexPathDistrib	= ""; //$NON-NLS-1$
		compressionRate		= 0.9f;
		this.statusBar		= statusBar;
		this.drawing		= drawing;
		this.canvas 		= canvas;

		// Widgets initialisation
		pdfButton 			= new MButton(LResources.PDF_ICON);
		exportMenu			= new MMenu(LABEL_EXPORT_AS, true);
		menuItemBMP			= new MMenuItem(LABEL_EXPORT_BMP, KeyEvent.VK_B);
		menuItemEPSLatex	= new MMenuItem("eps (latex) picture", KeyEvent.VK_S);
		menuItemJPG			= new MMenuItem(LABEL_EXPORT_JPG, KeyEvent.VK_J);
		menuItemPDF			= new MMenuItem("pdf (latex) picture", KeyEvent.VK_F);
		menuItemPDFcrop		= new MMenuItem("pdf (latex+pdfcrop) picture", KeyEvent.VK_C);
		menuItemPNG			= new MMenuItem(LABEL_EXPORT_PNG, KeyEvent.VK_P);
		menuItemPST			= new MMenuItem(LABEL_EXPORT_TRICKS, KeyEvent.VK_T);
		exportMenu.add(menuItemPST);
		exportMenu.add(menuItemJPG);
		exportMenu.add(menuItemPNG);
		exportMenu.add(menuItemBMP);
		exportMenu.add(menuItemPDF);
		exportMenu.add(menuItemEPSLatex);
		exportMenu.add(menuItemPDFcrop);

		reinit();
		initialiseLinks();
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

            elt = document.createElement(LNamespace.XML_PATH_LATEX_DISTRIB);
            elt.setTextContent(latexPathDistrib);
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
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		exportMenu.setEnabled(activated);
		pdfButton.setEnabled(activated);
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new ButtonPressed2Export(this));
			links.add(new MenuPressed2Export(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
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
	 * Show the export dialog to select a path.
	 * @param format The format of the document to export.
	 * @return True if the user has successfully selected a file.
	 * @since 3.0
	 */
	protected boolean showExportDialog(final ExportFormat format) {
		if(format==null)
			return false;

		if(fileChooserExport==null)
			fileChooserExport = new ExportDialog(currentFile==null ? pathExport : currentFile.getPath());

		// Setting the dialog.
		fileChooserExport.removeChoosableFileFilter(fileChooserExport.getFileFilter());
		fileChooserExport.setFileFilter(fileChooserExport.getAcceptAllFileFilter());
		fileChooserExport.setFileFilter(format.getFilter());
		fileChooserExport.setDialogTitle(Exporter.TITLE_DIALOG_EXPORT);

		if(currentFile==null)
			fileChooserExport.setSelectedFile(null);
		else {
			String fPath = currentFile.getPath();

			// Removing the extension added during the previous export.
			final int indexDot = fPath.lastIndexOf('.');

			if(indexDot!=-1)
				fPath = fPath.substring(0, indexDot);

			fileChooserExport.setSelectedFile(new File(fPath));
		}

		// Showing the dialog.
		final int response 	= fileChooserExport.showSaveDialog(null);
		File file 			= fileChooserExport.getSelectedFile();

		// Analysing the result of the dialog.
		if(response != JFileChooser.APPROVE_OPTION || file==null)
			return false;

		if(file.getName().toLowerCase().indexOf(format.getFileExtension().toLowerCase()) == -1)
			file = new File(file.getPath() + format.getFileExtension());

		if(file.exists()) {
			int replace = JOptionPane.showConfirmDialog(null,
						LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.173"), //$NON-NLS-1$
						Exporter.TITLE_DIALOG_EXPORT, JOptionPane.YES_NO_OPTION);

			if(replace == JOptionPane.NO_OPTION)
				return false; // The user doesn't want to replace the file
		}

		if(format==ExportFormat.JPG)
			compressionRate = fileChooserExport.getCompressionRate()/100f;

		currentFile = file;

		return true;
	}



	@Override
	public void onActionDone(final Link<?,?,?> link, final Action action) {
		super.onActionDone(link, action);

		statusBar.setText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.184")); //$NON-NLS-1$
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
			if(this.defaultPackages=="")
				LaTeXGenerator.setPackages(defaultPackages + System.getProperty("line.separator")+LaTeXGenerator.getPackages());
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
	 * @return The path of the latex distribution.
	 * @since 3.0
	 */
	public String getLatexPathDistrib() {
		return latexPathDistrib;
	}


	/**
	 * @param latexPathDistrib The path of the latex distribution.
	 * @since 3.0
	 */
	public void setLatexPathDistrib(final String latexPathDistrib) {
		if(latexPathDistrib!=null)
			this.latexPathDistrib = latexPathDistrib;
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
 * @author Arnaud Blouin
 */
class MenuPressed2Export extends Link<Export, MenuItemPressed, Exporter> {
	/**
	 * Initialises the link.
	 * @param ins The exporter.
	 */
	public MenuPressed2Export(final Exporter ins) throws InstantiationException, IllegalAccessException {
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

		if(format!=null && instrument.showExportDialog(format)) {
			action.setCanvas(instrument.canvas);
			action.setFormat(format);
			action.setFile(instrument.currentFile);
			action.setLatexDistribPath(instrument.latexPathDistrib);
			action.setCompressionRate(instrument.compressionRate);
		}
		else
			action = null;
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
	public ButtonPressed2Export(final Exporter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Export.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		if(instrument.showExportDialog(ExportFormat.PDF)) {
			action.setCanvas(instrument.canvas);
			action.setFormat(ExportFormat.PDF);
			action.setFile(instrument.currentFile);
			action.setLatexDistribPath(instrument.latexPathDistrib);
		}
		else action = null;
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.pdfButton;
	}
}
