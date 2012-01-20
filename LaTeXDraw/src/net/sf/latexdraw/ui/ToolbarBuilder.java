package net.sf.latexdraw.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Box;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.ui.IProgressBar;
import org.malai.ui.UIComposer;
import org.malai.widget.MSpinner;
import org.malai.widget.MToolBar;

/**
 * The composer that creates the tool bar of the application.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
public class ToolbarBuilder extends UIComposer<MToolBar> {
	/** The main frame of the interactive system. */
	protected LFrame frame;

	/** The toolbar that contains the widgets to create rectangle-like shapes. */
	protected ListToggleButton recListB;

	/** The toolbar that contains the widgets to create polygon-like shapes. */
	protected ListToggleButton polygonListB;

	/** The toolbar that contains the widgets to create grid-like shapes. */
	protected ListToggleButton gridListB;

	/** The toolbar that contains the widgets to create ellipse-like shapes. */
	protected ListToggleButton ellipseListB;

	/** The toolbar that contains the widgets to create curve-like shapes. */
	protected ListToggleButton bezierListB;

	/** The toolbar that contains the widgets to customise the magnetic grid. */
	protected ListToggleButton magneticGridB;

	/** The hash map used to map a widget to its container. */
	protected Map<Component, ListToggleButton> mapContainers;


	/**
	 * Creates the toolbar of the interactive system.
	 * @param frame The main frame of the interactive system.
	 * @throws NullPointerException If frame is null.
	 * @since 3.0
	 */
	public ToolbarBuilder(final LFrame frame) {
		super();
		this.frame 		= frame;
		mapContainers	= new IdentityHashMap<Component, ListToggleButton>();
	}


	@Override
	public void compose(final IProgressBar progressBar) {//TODO: remove "LaTeXDrawFrame.116"?
		widget = new MToolBar(true);

		final LCanvas canvas = frame.getCanvas();
		AbstractButton button;

		// Adding new/open/save buttons
		widget.add(frame.fileLoader.getNewButton());
		widget.add(frame.fileLoader.getLoadButton());
		widget.add(frame.fileLoader.getSaveButton());
		widget.addSeparator();

		// Adding the pdf button
		widget.add(frame.exporter.getPdfButton());
		widget.addSeparator();

		// Adding the zoom buttons.
		widget.add(frame.zoomer.getZoomSpinner());
		widget.add(frame.zoomer.getZoomDefaultButton());

		if(progressBar!=null) progressBar.addToProgressBar(5);

		composeMagneticGridToolbar(canvas);

		// Adding the undo/redo buttons.
		widget.add(frame.undoManager.getUndoB());
		widget.add(frame.undoManager.getRedoB());

 		//Adding a widget to select shape.
		button = frame.editingSelector.getHandB();
		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.136") + //$NON-NLS-1$
				 				LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.137") + //$NON-NLS-1$
				 				LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.138")); //$NON-NLS-1$
		widget.add(button);
		widget.add(frame.deleter.getDeleteB());

		if(progressBar!=null) progressBar.addToProgressBar(5);

 		//Adding a widget to create lines.
		button = frame.editingSelector.getLinesB();
		button.setToolTipText("Draw a single or several joined lines.");
		widget.add(button);

		composeRectangleLikeToolbar(canvas);
		composeEllipseLikeToolbar(canvas);
		composePolygonLikeToolbar(canvas);

		if(progressBar!=null) progressBar.addToProgressBar(5);

		composeCurveLikeToolbar(canvas);
		composeGridLikeToolbar(canvas);

		// Adding a widget to create arcs.
 		button = frame.editingSelector.getArcB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.128")); //$NON-NLS-1$
 		widget.add(button);

 		//Adding a widget to create text shapes.
		button = frame.editingSelector.getTextB();
		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.60")); //$NON-NLS-1$
		widget.add(button);

 		//Adding a widget to create free hand shapes.
		button = frame.editingSelector.getFreeHandB();
		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.7")); //$NON-NLS-1$
		widget.add(button);

 		//Adding a widget to create dot shapes.
		button = frame.editingSelector.getDotB();
		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.117")); //$NON-NLS-1$
		widget.add(button);

		//Adding a widget to add pictures.
		button = frame.editingSelector.getPicB();
		button.setToolTipText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.1")); //$NON-NLS-1$
		widget.add(button);

		widget.add(frame.exceptionsManager.getExceptionB());

		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	/**
	 * Adds widgets to select the type of shape to create. Here rectangle/square shape.
	 * @since 3.0
	 */
	protected void composeRectangleLikeToolbar(final LCanvas canvas) {
		recListB = new ListToggleButton(frame, LResources.RECT_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
		recListB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.118")); //$NON-NLS-1$
		widget.add(recListB);

 		AbstractButton button = frame.editingSelector.getRecB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.119")); //$NON-NLS-1$
 		recListB.addComponent(button);
 		mapContainers.put(button, recListB);

 		button = frame.editingSelector.getSquareB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.120")); //$NON-NLS-1$
 		recListB.addComponent(button);
 		mapContainers.put(button, recListB);
 		recListB.addSeparator();
	}


	/**
	 * Adds a widgets to create ellipse/circle shapes.
	 * @since 3.0
	 */
	protected void composeEllipseLikeToolbar(final LCanvas canvas) {
 		ellipseListB = new ListToggleButton(frame, LResources.ELLIPSE_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		ellipseListB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.125")); //$NON-NLS-1$
 		widget.add(ellipseListB);

 		AbstractButton button = frame.editingSelector.getEllipseB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.125")); //$NON-NLS-1$
 		ellipseListB.addComponent(button);
 		mapContainers.put(button, ellipseListB);

 		button = frame.editingSelector.getCircleB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.127")); //$NON-NLS-1$
 		ellipseListB.addComponent(button);
 		mapContainers.put(button, ellipseListB);
 		ellipseListB.addSeparator();
	}



	/**
	 * Adds a widgets to create polygon/rhombus/triangle shapes.
	 * @since 3.0
	 */
	protected void composePolygonLikeToolbar(final LCanvas canvas) {
 		polygonListB = new ListToggleButton(frame, LResources.POLYGON_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		polygonListB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.121")); //$NON-NLS-1$
 		widget.add(polygonListB);

 		AbstractButton button = frame.editingSelector.getPolygonB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.121")); //$NON-NLS-1$
 		polygonListB.addComponent(button);
 		mapContainers.put(button, polygonListB);

 		button = frame.editingSelector.getRhombusB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.123")); //$NON-NLS-1$
 		polygonListB.addComponent(button);
 		mapContainers.put(button, polygonListB);

 		button = frame.editingSelector.getTriangleB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.124")); //$NON-NLS-1$
 		polygonListB.addComponent(button);
 		mapContainers.put(button, polygonListB);
 		polygonListB.addSeparator();
	}


	/**
	 * Adds a widgets to create bezier curve shapes.
	 * @since 3.0
	 */
	protected void composeCurveLikeToolbar(final LCanvas canvas) {
 		bezierListB = new ListToggleButton(frame, LResources.CLOSED_BEZIER_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		bezierListB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.132")); //$NON-NLS-1$
 		widget.add(bezierListB);

 		AbstractButton button = frame.editingSelector.getBezierClosedB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getString19("LaTeXDrawFrame.11")); //$NON-NLS-1$
 		bezierListB.addComponent(button);
 		mapContainers.put(button, bezierListB);

 		button = frame.editingSelector.getBezierB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.132")); //$NON-NLS-1$
 		bezierListB.addComponent(button);
 		mapContainers.put(button, bezierListB);
 		bezierListB.addSeparator();
	}


	/**
	 * Adds a widgets to create grid/axes shapes.
	 * @since 3.0
	 */
	protected void composeGridLikeToolbar(final LCanvas canvas) {
 		gridListB = new ListToggleButton(frame, LResources.GRID_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		gridListB.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.16")); //$NON-NLS-1$

 		AbstractButton button = frame.editingSelector.getGridB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.133")); //$NON-NLS-1$
 		gridListB.addComponent(button);
 		mapContainers.put(button, gridListB);

 		button = frame.editingSelector.getAxesB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.17")); //$NON-NLS-1$
 		gridListB.addComponent(button);
 		mapContainers.put(button, gridListB);
 		gridListB.addSeparator();
 		widget.add(gridListB);
	}


	protected void composeMagneticGridToolbar(final LCanvas canvas) {
		magneticGridB = new ListToggleButton(frame, LResources.DISPLAY_GRID_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
		magneticGridB.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.12")); //$NON-NLS-1$
		widget.add(magneticGridB);

		magneticGridB.addComponent(frame.gridCustomiser.getStyleList());
		mapContainers.put(frame.gridCustomiser.getStyleList(), magneticGridB);
		magneticGridB.addComponent(Box.createHorizontalStrut(PropertiesToolbarBuilder.SEPARATION_WIDTH));
		magneticGridB.addComponent(frame.gridCustomiser.getMagneticCB());
		mapContainers.put(frame.gridCustomiser.getMagneticCB(), magneticGridB);
		magneticGridB.addComponent(Box.createHorizontalStrut(PropertiesToolbarBuilder.SEPARATION_WIDTH));
		MSpinner spinner = frame.gridCustomiser.getGridSpacing();
		spinner.setPreferredSize(new Dimension(65, PropertiesToolbarBuilder.HEIGHT_TEXTFIELD));
		if(spinner.getLabel()!=null)
			magneticGridB.addComponent(spinner.getLabel());
		magneticGridB.addComponent(spinner);
		mapContainers.put(spinner, magneticGridB);
		magneticGridB.addSeparator();
	}
}

