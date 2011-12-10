package net.sf.latexdraw.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import net.sf.latexdraw.glib.ui.LCanvas;

import org.malai.instrument.Instrument;
import org.malai.ui.IProgressBar;
import org.malai.ui.UIComposer;
import org.malai.widget.MPanel;
import org.malai.widget.MToolBar;

/**
 * This composer composes the latexdraw user interface.<br>
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
 * 20/31/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class UIBuilder extends UIComposer<LFrame> {
	/** The menu bar composer. */
	protected MenubarBuilder menubarBuilder;

	/** The tool bar composer. */
	protected ToolbarBuilder toolbarBuilder;

	/** The properties tool bar composer. */
	protected PropertiesToolbarBuilder propToolbarBuilder;


	/**
	 * Creates the composer of the latexdraw user interface.
	 * @param frame The frame of the interactive system that contains the instruments and presentations to compose.
	 * @since 3.0
	 */
	public UIBuilder(final LFrame frame) {
		super();
		widget = frame;
		menubarBuilder 		= new MenubarBuilder(widget);
		toolbarBuilder 		= new ToolbarBuilder(widget);
		propToolbarBuilder 	= new PropertiesToolbarBuilder(widget);
	}


	@Override
	public void setWidgetVisible(final Component widget, final boolean visible) {
		super.setWidgetVisible(widget, visible);

		ListToggleButton list = toolbarBuilder.mapContainers.get(widget);

		if(list!=null)
			list.setVisible(visible || list.isContentVisible());
	}


	@Override
	public void compose(final IProgressBar progressBar) {
		menubarBuilder.compose(progressBar);
		toolbarBuilder.compose(progressBar);
		propToolbarBuilder.compose(progressBar);

		/* Organisation of the general layout of the user interface. */
		final Container contentPane = widget.getContentPane();

		/* Creation of the drawing area composed of the canvas, the scales, etc. */
		final MPanel drawingArea = new MPanel(false, false);
		drawingArea.setLayout(new BorderLayout());
		drawingArea.add(widget.xScaleRuler, BorderLayout.NORTH);
		drawingArea.add(widget.yScaleRuler, BorderLayout.WEST);
		drawingArea.add(widget.layeredPanel, BorderLayout.CENTER);
		drawingArea.add(propToolbarBuilder.getWidget(), BorderLayout.SOUTH);
		if(progressBar!=null) progressBar.addToProgressBar(5);

		/* Creation of the tabbed pane. */
		widget.tabbedPanel.addTab("Drawing", drawingArea);
		widget.tabbedPanel.addTab("PST", widget.getCodePanel());

		contentPane.setLayout(new BorderLayout());
		contentPane.add(toolbarBuilder.getWidget(), BorderLayout.NORTH);
		contentPane.add(widget.tabbedPanel, BorderLayout.CENTER);
		contentPane.add(widget.statusBar, BorderLayout.SOUTH);
		if(progressBar!=null) progressBar.addToProgressBar(5);

		widget.setJMenuBar(menubarBuilder.getWidget());
		// Updating the concrete presentations.
		widget.updatePresentations();

		setEventableToInstruments();
		initialiseInstrumentsActivation();

		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	/**
	 * Sets the eventable objects to the instruments.
	 * @since 3.0
	 */
	protected void setEventableToInstruments() {
		final LCanvas canvas 	= widget.getCanvas();
		final MToolBar toolbar 	= toolbarBuilder.getWidget();

		widget.prefActivator.addEventable(menubarBuilder.editMenu);
		widget.exceptionsManager.addEventable(toolbar);
		widget.scroller.addEventable(canvas);
		widget.editingSelector.addEventable(toolbar);
		widget.editingSelector.addEventable(toolbarBuilder.recListB.getToolbar());
		widget.editingSelector.addEventable(toolbarBuilder.polygonListB.getToolbar());
		widget.editingSelector.addEventable(toolbarBuilder.ellipseListB.getToolbar());
		widget.editingSelector.addEventable(toolbarBuilder.gridListB.getToolbar());
		widget.editingSelector.addEventable(toolbarBuilder.bezierListB.getToolbar());
		widget.hand.addEventable(canvas);
		widget.pencil.addEventable(canvas);
		widget.undoManager.addEventable(toolbar);
		widget.zoomer.addEventable(toolbar);
		widget.zoomer.addEventable(canvas);
		widget.exporter.addEventable(toolbar);
		widget.exporter.addEventable(widget.exporter.getExportMenu());
		widget.scaleRulersCustomiser.addEventable(menubarBuilder.displayMenu);
		widget.scaleRulersCustomiser.addEventable(menubarBuilder.unitMenu);
		widget.gridCustomiser.addEventable(toolbarBuilder.magneticGridB.getToolbar());
		widget.helper.addEventable(menubarBuilder.helpMenu);
		setGlobalShortcutEventable(widget.deleter, canvas);
		setGlobalShortcutEventable(widget.paster, canvas);
		setGlobalShortcutEventable(widget.fileLoader, canvas);
		widget.fileLoader.addEventable(widget);
		widget.tabSelector.addEventable(widget.tabbedPanel);
	}


	protected void setGlobalShortcutEventable(final Instrument instrument, final LCanvas canvas) {
		if(instrument!=null) {
			instrument.addEventable(toolbarBuilder.getWidget());
			instrument.addEventable(propToolbarBuilder.getWidget());
			instrument.addEventable(canvas);
			instrument.addEventable(widget.getTabbedPanel());
			instrument.addEventable(widget.getCodePanel());
			instrument.addEventable(menubarBuilder.displayMenu);
			instrument.addEventable(menubarBuilder.drawingMenu);
			instrument.addEventable(menubarBuilder.editMenu);
			instrument.addEventable(menubarBuilder.helpMenu);
			instrument.addEventable(menubarBuilder.unitMenu);
		}
	}


	protected void initialiseInstrumentsActivation() {
		widget.prefActivator.setActivated(true);
		widget.helper.setActivated(true);
		widget.gridCustomiser.setActivated(true);
		widget.scroller.setActivated(true);
		widget.exporter.setActivated(false);
		widget.editingSelector.setActivated(true);
		widget.undoManager.setActivated(true);
		widget.zoomer.setActivated(true);
		widget.fileLoader.setActivated(true);
		widget.scaleRulersCustomiser.setActivated(true);
		widget.paster.setActivated(true);
		widget.tabSelector.setActivated(true);
	}
}
