package net.sf.latexdraw.ui;

import javax.swing.JMenuBar;

import net.sf.latexdraw.lang.LangTool;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MCheckBoxMenuItem;
import org.malai.swing.widget.MMenu;
import org.malai.swing.widget.MProgressBar;

/**
 * The composer that creates the menu bar of the application.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 12/08/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class MenubarBuilder extends UIComposer<JMenuBar> {
	/** The main frame of the application. */
	protected LFrame frame;

	/** This menu contains the menu items related to the visibility of components. */
	protected MMenu displayMenu;

	/** This menu contains menu items related with drawings. */
	protected MMenu drawingMenu;

	/** This menu contains menu items related with the editing of shapes. */
	protected MMenu editMenu;

	/** This menu contains menu items to change the unit of the drawing. */
	protected MMenu unitMenu;

	/** This menu contains menu items related with the help. */
	protected MMenu helpMenu;


	/**
	 * Creates the menu bar.
	 * @param frame The user interface that contains all the instruments.
	 * @throws NullPointerException If one of the given arguments is null.
	 * @since 3.0
	 */
	public MenubarBuilder(final LFrame frame) {
		super();
		this.frame = frame;
	}


	@Override
	public void compose(final MProgressBar progressBar) {
		widget = new JMenuBar();
		composeDrawingMenu();
		composeEditMenu();
		if(progressBar!=null) progressBar.addToProgressBar(5);
		composeDisplayMenu();
		composeHelpMenu();
		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	protected void composeDrawingMenu() {
		drawingMenu = new MMenu(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.91"), true); //$NON-NLS-1$
		unitMenu	= new MMenu("Unit", true);

		widget.add(drawingMenu);
		drawingMenu.add(frame.fileLoader.getNewMenu());
		drawingMenu.add(frame.fileLoader.getLoadMenu());
		drawingMenu.add(frame.fileLoader.getSaveMenu());
		drawingMenu.add(frame.fileLoader.getSaveAsMenu());
		drawingMenu.add(frame.fileLoader.getRecentFilesMenu());
		drawingMenu.addSeparator();
		drawingMenu.add(frame.exporter.getExportMenu());
		drawingMenu.addSeparator();
		drawingMenu.add(frame.templateManager.templateMenu());
		drawingMenu.add(unitMenu);
		unitMenu.add(frame.scaleRulersCustomiser.getUnitCmItem());
		unitMenu.add(frame.scaleRulersCustomiser.getUnitInchItem());

		frame.exporter.getExportMenu().add(frame.templateManager.exportTemplateMenu());
	}


	protected void composeEditMenu() {
		editMenu = new MMenu(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.89"), true); //$NON-NLS-1$
		editMenu.add(frame.paster.getCutMenu());
		editMenu.add(frame.paster.getCopyMenu());
		editMenu.add(frame.paster.getPasteMenu());
		editMenu.addSeparator();
		editMenu.add(frame.prefActivator.getShowPreferencesMenu());
		widget.add(editMenu);
	}


	protected void composeHelpMenu() {
		helpMenu = new MMenu(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.93"), true); //$NON-NLS-1$
		helpMenu.add(frame.helper.getShortcutItem());
		helpMenu.add(frame.helper.getReportBugItem());
		helpMenu.add(frame.helper.getForumItem());
		helpMenu.add(frame.helper.getDonateItem());
		helpMenu.addSeparator();
		helpMenu.add(frame.helper.getAboutItem());
		widget.add(helpMenu);
	}


	/**
	 * Initialises the menu "Display"
	 * @since 3.0
	 */
	protected void composeDisplayMenu() {
		displayMenu = new MMenu(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.90"), true); //$NON-NLS-1$

		widget.add(displayMenu);

		MCheckBoxMenuItem menuCBItem = frame.scaleRulersCustomiser.getxRulerItem();
		menuCBItem.setSelected(true);
        displayMenu.add(menuCBItem);
        menuCBItem = frame.scaleRulersCustomiser.getyRulerItem();
        menuCBItem.setSelected(true);
        displayMenu.add(menuCBItem);
        displayMenu.addSeparator();
        displayMenu.add(menuCBItem);
	}
}
