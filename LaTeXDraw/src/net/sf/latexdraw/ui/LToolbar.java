package net.sf.latexdraw.ui;

import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.Box;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.widget.MSpinner;
import fr.eseo.malai.widget.MToolBar;

/**
 * Defines the top panel that contains a set of widgets to handle drawings.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
 * 05/20/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LToolbar extends MToolBar {
	private static final long serialVersionUID = 1L;

	/** The main frame of the interactive system. */
	protected LFrame frame;

	/** The toolbar that contains the widgets to create rectangle-like shapes. */
	protected ListToggleButton recListB;

	/** The toolbar that contains the widgets to create polygon-like shapes. */
	protected ListToggleButton polygonListB;

	/** The toolbar that contains the widgets to create arc-like shapes. */
	protected ListToggleButton arcListB;

	/** The toolbar that contains the widgets to create grid-like shapes. */
	protected ListToggleButton gridListB;

	/** The toolbar that contains the widgets to create ellipse-like shapes. */
	protected ListToggleButton ellipseListB;

	/** The toolbar that contains the widgets to create curve-like shapes. */
	protected ListToggleButton bezierListB;

	/** The toolbar that contains the widgets to customise the magnetic grid. */
	protected ListToggleButton magneticGridB;



	/**
	 * Creates the toolbar of the interactive system.
	 * @param frame The main frame of the interactive system.
	 * @throws NullPointerException If frame is null.
	 * @since 3.0
	 */
	public LToolbar(final LFrame frame) {
		super(true);

		this.frame = frame;
		initialiseToolbar();
	}



	/**
	 * Adds widgets to select the type of shape to create. Here rectangle/square shape.
	 * @since 3.0
	 */
	protected void initialiseRectangleLikeToolbar(final LCanvas canvas) {
		recListB = new ListToggleButton(frame, LResources.RECT_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
		recListB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.118")); //$NON-NLS-1$
 		add(recListB);

 		AbstractButton button = frame.editingSelector.getRecB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.119")); //$NON-NLS-1$
 		recListB.addComponent(button);

 		button = frame.editingSelector.getSquareB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.120")); //$NON-NLS-1$
 		recListB.addComponent(button);
 		recListB.addSeparator();
	}


	/**
	 * Adds a widgets to create ellipse/circle shapes.
	 * @since 3.0
	 */
	protected void initialiseEllipseLikeToolbar(final LCanvas canvas) {
 		ellipseListB = new ListToggleButton(frame, LResources.ELLIPSE_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		ellipseListB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.125")); //$NON-NLS-1$
 		add(ellipseListB);

 		AbstractButton button = frame.editingSelector.getEllipseB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.125")); //$NON-NLS-1$
 		ellipseListB.addComponent(button);

 		button = frame.editingSelector.getCircleB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.127")); //$NON-NLS-1$
 		ellipseListB.addComponent(button);
 		ellipseListB.addSeparator();
	}



	/**
	 * Adds a widgets to create polygon/rhombus/triangle shapes.
	 * @since 3.0
	 */
	protected void initialisePolygonLikeToolbar(final LCanvas canvas) {
 		polygonListB = new ListToggleButton(frame, LResources.POLYGON_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		polygonListB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.121")); //$NON-NLS-1$
 		add(polygonListB);

 		AbstractButton button = frame.editingSelector.getPolygonB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.121")); //$NON-NLS-1$
 		polygonListB.addComponent(button);

 		button = frame.editingSelector.getRhombusB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.123")); //$NON-NLS-1$
 		polygonListB.addComponent(button);

 		button = frame.editingSelector.getTriangleB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.124")); //$NON-NLS-1$
 		polygonListB.addComponent(button);
 		polygonListB.addSeparator();
	}


	/**
	 * Adds a widgets to create bezier curve shapes.
	 * @since 3.0
	 */
	protected void initialiseCurveLikeToolbar(final LCanvas canvas) {
 		bezierListB = new ListToggleButton(frame, LResources.CLOSED_BEZIER_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		bezierListB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.132")); //$NON-NLS-1$
 		add(bezierListB);

 		AbstractButton button = frame.editingSelector.getBezierClosedB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getString19("LaTeXDrawFrame.11")); //$NON-NLS-1$
 		bezierListB.addComponent(button);

 		button = frame.editingSelector.getBezierB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.132")); //$NON-NLS-1$
 		bezierListB.addComponent(button);
 		bezierListB.addSeparator();
	}


	/**
	 * Adds a widgets to create grid/axes shapes.
	 * @since 3.0
	 */
	protected void initialiseGridLikeToolbar(final LCanvas canvas) {
 		gridListB = new ListToggleButton(frame, LResources.GRID_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		gridListB.setToolTipText(LangTool.LANG.getString18("LaTeXDrawFrame.16")); //$NON-NLS-1$

 		AbstractButton button = frame.editingSelector.getGridB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.133")); //$NON-NLS-1$
 		gridListB.addComponent(button);

 		button = frame.editingSelector.getAxesB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getString18("LaTeXDrawFrame.17")); //$NON-NLS-1$
 		gridListB.addComponent(button);
 		gridListB.addSeparator();
 		add(gridListB);
	}


	/**
	 * Adds a widgets to create arc shapes.
	 * @since 3.0
	 */
	protected void initialiseArcLikeToolbar(final LCanvas canvas) {
 		arcListB = new ListToggleButton(frame, LResources.ARC_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
 		arcListB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.128")); //$NON-NLS-1$
 		add(arcListB);

 		AbstractButton button = frame.editingSelector.getArcB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.128")); //$NON-NLS-1$
 		arcListB.addComponent(button);

 		button = frame.editingSelector.getWedgeB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.130")); //$NON-NLS-1$
 		arcListB.addComponent(button);

 		button = frame.editingSelector.getChordB();
 		button.setMargin(LResources.INSET_BUTTON);
 		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.131")); //$NON-NLS-1$
 		arcListB.addComponent(button);
 		arcListB.addSeparator();
	}


	protected void initialiseMagnticGridToolbar(final LCanvas canvas) {
		magneticGridB = new ListToggleButton(frame, LResources.DISPLAY_GRID_ICON, ListToggleButton.LOCATION_SOUTH, canvas);
		magneticGridB.setToolTipText(LangTool.LANG.getString18("LaTeXDrawFrame.12")); //$NON-NLS-1$
		add(magneticGridB);

		magneticGridB.addComponent(frame.gridCustomiser.getStyleList());
		magneticGridB.addComponent(Box.createHorizontalStrut(LPropertiesToolbar.SEPARATION_WIDTH));
		magneticGridB.addComponent(frame.gridCustomiser.getMagneticCB());
		magneticGridB.addComponent(Box.createHorizontalStrut(LPropertiesToolbar.SEPARATION_WIDTH));
		MSpinner spinner = frame.gridCustomiser.getGridSpacing();
		spinner.setPreferredSize(new Dimension(65, LPropertiesToolbar.HEIGHT_TEXTFIELD));
		if(spinner.getLabel()!=null)
			magneticGridB.addComponent(spinner.getLabel());
		magneticGridB.addComponent(spinner);
		// The mini-toolbar is set to the instrument to be updated when some of its widgets are hidden.
		frame.gridCustomiser.setWidgetContainer(magneticGridB);

		magneticGridB.addSeparator();
	}


	/**
	 * Initialise the toolbar using the widgets of the instruments.
	 * @since 3.0
	 */
	protected void initialiseToolbar() {//TODO: remove "LaTeXDrawFrame.116"?
		final LCanvas canvas = frame.getCanvas();
		AbstractButton button;

		// Adding open/save buttons
		add(frame.fileLoader.getLoadButton());
		add(frame.fileLoader.getSaveButton());
		addSeparator();

		// Adding the pdf button
		add(frame.exporter.getPdfButton());
		addSeparator();

		// Adding the zoom buttons.
		add(frame.zoomer.getZoomSpinner());
		add(frame.zoomer.getZoomDefaultButton());
		addSeparator();

		initialiseMagnticGridToolbar(canvas);

		// Adding the undo/redo buttons.
		add(frame.undoManager.getUndoB());
		add(frame.undoManager.getRedoB());
		addSeparator();

 		//Adding a widgets to select shape.
		button = frame.editingSelector.getHandB();
		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.136") + //$NON-NLS-1$
				 				LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.137") + //$NON-NLS-1$
				 				LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.138")); //$NON-NLS-1$
 		add(button);
 		add(frame.deleter.getDeleteB());
 		addSeparator();

 		//Adding a widgets to create lines.
		button = frame.editingSelector.getLinesB();
		button.setToolTipText("Draw a single or several joined lines.");
		add(button);

		initialiseRectangleLikeToolbar(canvas);
		initialiseEllipseLikeToolbar(canvas);
		initialisePolygonLikeToolbar(canvas);
		initialiseCurveLikeToolbar(canvas);
		initialiseGridLikeToolbar(canvas);
		initialiseArcLikeToolbar(canvas);

 		//Adding a widgets to create text shapes.
		button = frame.editingSelector.getTextB();
		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.60")); //$NON-NLS-1$
		add(button);

 		//Adding a widgets to create free hand shapes.
		button = frame.editingSelector.getFreeHandB();
		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.7")); //$NON-NLS-1$
		add(button);

 		//Adding a widgets to create dot shapes.
		button = frame.editingSelector.getDotB();
		button.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.117")); //$NON-NLS-1$
		add(button);

		add(frame.exceptionsManager.getExceptionB());
	}
}

