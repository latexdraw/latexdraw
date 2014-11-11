package net.sf.latexdraw.ui;

import java.awt.Component;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.JLabel;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.DrawingPropertiesCustomiser;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.SwingUIComposer;
import org.malai.swing.widget.MProgressBar;
import org.malai.swing.widget.MToolBar;

/**
 * The composer that creates the tool bar of the application.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 12/08/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ToolbarBuilder extends SwingUIComposer<MToolBar> {
	/** The main frame of the interactive system. */
	protected LFrame frame;

	/** The toolbar that contains the widgets to customise the magnetic grid. */
	protected WidgetMiniToolbar magneticGridB;

	/** The toolbar that contains the widgets to customise the drawing's properties. */
	protected WidgetMiniToolbar drawingB;

	/** The hash map used to map a widget to its container. */
	protected Map<Component, WidgetMiniToolbar> mapContainers;


	/**
	 * Creates the toolbar of the interactive system.
	 * @param lframe The main frame of the interactive system.
	 * @throws NullPointerException If frame is null.
	 * @since 3.0
	 */
	public ToolbarBuilder(final LFrame lframe) {
		super();
		frame 			= lframe;
		mapContainers	= new IdentityHashMap<>();
	}


	@Override
	public void compose(final MProgressBar progressBar) {
		widget = new MToolBar(true);

		final LCanvas canvas = frame.getCanvas();

		// Adding the zoom buttons.
		widget.add(frame.zoomer.getZoomSpinner());
		widget.add(frame.zoomer.getZoomDefaultButton());

		if(progressBar!=null) progressBar.addToProgressBar(5);

		composeDrawingPropertiesToolbar(canvas);

		// Adding the undo/redo buttons.
		widget.add(frame.undoManager.getUndoB());
		widget.add(frame.undoManager.getRedoB());

 		//Adding a widget to select shape.
		widget.add(frame.editingSelector.getHandB());
		widget.add(frame.deleter.getDeleteB());

		if(progressBar!=null) progressBar.addToProgressBar(5);

 		//Adding a widget to create lines.
		widget.add(frame.editingSelector.getLinesB());
		widget.add(frame.editingSelector.getPlotB());
 		widget.add(frame.editingSelector.getArcB());
		widget.add(frame.editingSelector.getTextB());
		widget.add(frame.editingSelector.getFreeHandB());
		widget.add(frame.editingSelector.getDotB());
		widget.add(frame.editingSelector.getPicB());
		widget.add(frame.editingSelector.getCodeB());
		widget.add(frame.exceptionsManager.getExceptionB());

		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	protected void composeDrawingPropertiesToolbar(final LCanvas canvas) {
		final DrawingPropertiesCustomiser cust = frame.getDrawingPropCustomiser();
		drawingB = new WidgetMiniToolbar(LResources.DRAWING_PROP_ICON, WidgetMiniToolbar.LOCATION_SOUTH, canvas);
		drawingB.setToolTipText(LangTool.INSTANCE.getStringActions("ToolbarBuilder.1")); //$NON-NLS-1$
		widget.add(drawingB);

		cust.getTitleField().setColumns(15);
		cust.getLabelField().setColumns(10);

		UIBuilder.addSpinner(drawingB, cust.getScaleSpinner(), 65);
		drawingB.addComponent(new JLabel(LangTool.INSTANCE.getStringActions("ToolbarBuilder.2"))); //$NON-NLS-1$
		drawingB.addComponent(cust.getTitleField());
		drawingB.addComponent(new JLabel(LangTool.INSTANCE.getStringActions("ToolbarBuilder.3"))); //$NON-NLS-1$
		drawingB.addComponent(cust.getLabelField());
		drawingB.addComponent(cust.getMiddleHorizPosCB());
		drawingB.addComponent(cust.getPositionCB().getLabel());
		drawingB.addComponent(cust.getPositionCB());
		drawingB.addSeparator();
	}
}

