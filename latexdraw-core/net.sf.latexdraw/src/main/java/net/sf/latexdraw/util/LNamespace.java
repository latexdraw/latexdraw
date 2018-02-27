/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

/**
 * The elements of the latexdraw namespace.
 * @author Arnaud BLOUIN
 */
public final class LNamespace {
	public static final String LATEXDRAW_NAMESPACE_URI 	= "http://latexdraw.sourceforge.net/namespaces/latexdraw"; //NON-NLS
	public static final String LATEXDRAW_NAMESPACE 		= "latexdraw"; //NON-NLS

	public static final String XML_TYPE					= "type"; //NON-NLS

	public static final String XML_TYPE_RECT 			= "rectangle"; //NON-NLS
	public static final String XML_TYPE_FREEHAND		= "akinPoints"; //NON-NLS
	public static final String XML_TYPE_ARC 			= "arc"; //NON-NLS
	public static final String XML_TYPE_AXE 			= "axe"; //NON-NLS
	public static final String XML_TYPE_BEZIER_CURVE	= "bezierCurve"; //NON-NLS
	public static final String XML_TYPE_CIRCLE			= "circle"; //NON-NLS
	public static final String XML_TYPE_DOT				= "dot"; //NON-NLS
	public static final String XML_TYPE_GROUP			= "drawing"; //NON-NLS
	public static final String XML_TYPE_ELLIPSE			= "ellipse"; //NON-NLS
	public static final String XML_TYPE_GRID			= "grid"; //NON-NLS
	public static final String XML_TYPE_JOINED_LINES	= "joinedLines"; //NON-NLS
	public static final String XML_TYPE_POLYGON			= "polygon"; //NON-NLS
	public static final String XML_TYPE_PICTURE			= "picture"; //NON-NLS
	public static final String XML_TYPE_RHOMBUS			= "rhombus"; //NON-NLS
	public static final String XML_TYPE_SQUARE			= "square"; //NON-NLS
	public static final String XML_TYPE_TEXT			= "text"; //NON-NLS
	public static final String XML_TYPE_TRIANGLE		= "triangle"; //NON-NLS
	public static final String XML_TYPE_LINE			= "line"; //NON-NLS
	public static final String XML_TYPE_DBLE_BORDERS	= "dbleborders"; //NON-NLS
	public static final String XML_TYPE_SHOW_PTS		= "showPoints"; //NON-NLS
	public static final String XML_BORDERS_POS			= "borderPos"; //NON-NLS
	public static final String XML_TYPE_SHADOW			= "shadow"; //NON-NLS
	public static final String XML_ROTATION 			= "rotationAngle"; //NON-NLS
	public static final String XML_DOT_SHAPE			= "dotShape"; //NON-NLS
	public static final String XML_TYPE_GRID_SUB		= "subGrid"; //NON-NLS
	public static final String XML_TYPE_BG				= "background"; //NON-NLS

	public static final String XML_CAPTION				= "caption"; //NON-NLS
	public static final String XML_LABEL				= "label"; //NON-NLS
	public static final String XML_POSITION_VERT		= "positionVert"; //NON-NLS
	public static final String XML_POSITION_HORIZ		= "centreHoriz"; //NON-NLS
	public static final String XML_COMMENTS				= "comments"; //NON-NLS
	public static final String XML_ZOOM					= "zoom"; //NON-NLS
	public static final String XML_UNIT					= "unit"; //NON-NLS
	public static final String XML_CLASSIC_GRID			= "classicGrid"; //NON-NLS
	public static final String XML_MAGNETIC_GRID_STYLE	= "magneticGridStyle"; //NON-NLS
	public static final String XML_GRID_GAP				= "gridGap"; //NON-NLS
	public static final String XML_MAGNETIC_GRID		= "magneticGrid"; //NON-NLS
	public static final String XML_VERSION				= "version"; //NON-NLS
	public static final String XML_POSITION 			= "position"; //NON-NLS
	public static final String XML_POSITION_X 			= "x"; //NON-NLS
	public static final String XML_POSITION_Y 			= "y"; //NON-NLS
	public static final String XML_SIZE 				= "size"; //NON-NLS
	public static final String XML_HEIGHT 				= "height"; //NON-NLS
	public static final String XML_WIDTH 				= "width"; //NON-NLS
	public static final String XML_MAXIMISED 			= "maximised"; //NON-NLS
	public static final String XML_DISPLAY_GRID 		= "displayGrid"; //NON-NLS
	public static final String XML_ROOT_PREFERENCES 	= "preferences"; //NON-NLS
	public static final String XML_OPENGL	 			= "openGL"; //NON-NLS
	public static final String XML_PATH_EXPORT 			= "pathExport"; //NON-NLS
	public static final String XML_PATH_OPEN 			= "pathOpen"; //NON-NLS
	public static final String XML_CHECK_VERSION 		= "checkVersion"; //NON-NLS
	public static final String XML_LANG 				= "lang"; //NON-NLS
	public static final String XML_LATEX_INCLUDES		= "latexIncludes"; //NON-NLS
	public static final String XML_RECENT_FILES 		= "recentFiles"; //NON-NLS
	public static final String XML_NB_RECENT_FILES 		= "nb"; //NON-NLS
	public static final String XML_RECENT_FILE 			= "recentFile"; //NON-NLS
	public static final String XML_PATH_TYPE			= "pathType"; //NON-NLS
	public static final String XML_INTERVAL				= "interval"; //NON-NLS
	public static final String XML_STYLE				= "style"; //NON-NLS
	public static final String XML_POINTS				= "points"; //NON-NLS

	public static final String XML_GRID_X_SOUTH			= "XLabelsSouth"; //NON-NLS
	public static final String XML_GRID_Y_WEST			= "YLabelsWest"; //NON-NLS
	public static final String XML_GRID_DOTS			= "gridDots"; //NON-NLS
	public static final String XML_GRID_UNIT			= "gridUnit"; //NON-NLS
	public static final String XML_GRID_END				= "gridEnd"; //NON-NLS
	public static final String XML_GRID_START			= "gridStart"; //NON-NLS
	public static final String XML_GRID_ORIGIN			= "origin"; //NON-NLS
	public static final String XML_GRID_SUB_DIV			= "subDiv"; //NON-NLS
	public static final String XML_GRID_WIDTH			= "Gridwidth"; //NON-NLS

	public static final String XML_ARROW_DOT_SIZE_NUM	= "dotSizeNum"; //NON-NLS
	public static final String XML_ARROW_TBAR_SIZE_NUM	= "tbarSizeNum"; //NON-NLS
	public static final String XML_ARROW_SIZE_NUM		= "arrSizeNum"; //NON-NLS

	public static final String XML_AXE_INCREMENT		= "increment"; //NON-NLS
	public static final String XML_AXE_TICKS_SIZE		= "ticksSize"; //NON-NLS
	public static final String XML_AXE_SHOW_ORIGIN		= "showOrigin"; //NON-NLS
	public static final String XML_AXE_SHOW_TICKS		= "showTicks"; //NON-NLS
	public static final String XML_AXE_LABELS_STYLE		= "showLabels"; //NON-NLS
	public static final String XML_AXE_DIST_LABELS		= "distLabels"; //NON-NLS
	public static final String XML_AXE_TICKS_STYLE		= "ticksStyle"; //NON-NLS

	private LNamespace() {
		super();
	}
}
