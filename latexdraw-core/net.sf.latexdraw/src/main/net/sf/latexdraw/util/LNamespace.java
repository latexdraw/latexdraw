package net.sf.latexdraw.util;

/**
 * Defines the elements of the latexdraw namespace.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/06/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class LNamespace {
	public static final String LATEXDRAW_NAMESPACE_URI 	= "http://latexdraw.sourceforge.net/namespaces/latexdraw";	//$NON-NLS-1$
	public static final String LATEXDRAW_NAMESPACE 		= "latexdraw";				//$NON-NLS-1$

	public static final String XML_TYPE					= "type";				//$NON-NLS-1$

	public static final String XML_TYPE_RECT 			= "rectangle";			//$NON-NLS-1$
	public static final String XML_TYPE_FREEHAND		= "akinPoints";			//$NON-NLS-1$
	public static final String XML_TYPE_ARC 			= "arc";				//$NON-NLS-1$
	public static final String XML_TYPE_ARROWHEAD 		= "arrowhead";			//$NON-NLS-1$
	public static final String XML_TYPE_AXE 			= "axe";				//$NON-NLS-1$
	public static final String XML_TYPE_BEZIER_CURVE	= "bezierCurve";		//$NON-NLS-1$
	public static final String XML_TYPE_CIRCLE			= "circle";				//$NON-NLS-1$
	public static final String XML_TYPE_DOT				= "dot";				//$NON-NLS-1$
	public static final String XML_TYPE_GROUP			= "drawing";			//$NON-NLS-1$
	public static final String XML_TYPE_ELLIPSE			= "ellipse";			//$NON-NLS-1$
	public static final String XML_TYPE_FRAMEDBOX		= "framedbox";			//$NON-NLS-1$
	public static final String XML_TYPE_GRID			= "grid";				//$NON-NLS-1$
	public static final String XML_TYPE_JOINED_LINES	= "joinedLines";		//$NON-NLS-1$
	public static final String XML_TYPE_POLYGON			= "polygon";			//$NON-NLS-1$
	public static final String XML_TYPE_PICTURE			= "picture";			//$NON-NLS-1$
	public static final String XML_TYPE_RHOMBUS			= "rhombus";			//$NON-NLS-1$
	public static final String XML_TYPE_SQUARE			= "square";				//$NON-NLS-1$
	public static final String XML_TYPE_TEXT			= "text";				//$NON-NLS-1$
	public static final String XML_TYPE_TRIANGLE		= "triangle";			//$NON-NLS-1$
	public static final String XML_TYPE_LINE			= "line";				//$NON-NLS-1$
	public static final String XML_TYPE_DBLE_BORDERS	= "dbleborders";		//$NON-NLS-1$
	public static final String XML_TYPE_SHOW_PTS		= "showPoints";			//$NON-NLS-1$
	public static final String XML_BORDERS_POS			= "borderPos";			//$NON-NLS-1$
	public static final String XML_TYPE_SHADOW			= "shadow";				//$NON-NLS-1$
	public static final String XML_ROTATION 			= "rotationAngle";		//$NON-NLS-1$
	public static final String XML_DOT_SHAPE			= "dotShape";			//$NON-NLS-1$
	public static final String XML_TYPE_GRID_SUB		= "subGrid";			//$NON-NLS-1$
	public static final String XML_TYPE_BG				= "background";			//$NON-NLS-1$

	public static final String XML_BORDER				= "border";				//$NON-NLS-1$
	public static final String XML_CAPTION				= "caption";			//$NON-NLS-1$
	public static final String XML_LABEL				= "label";				//$NON-NLS-1$
	public static final String XML_POSITION_VERT		= "positionVert";		//$NON-NLS-1$
	public static final String XML_POSITION_HORIZ		= "centreHoriz";		//$NON-NLS-1$
	public static final String XML_COMMENTS				= "comments";			//$NON-NLS-1$
	public static final String XML_DRAW_BORDERS			= "displayBorders";		//$NON-NLS-1$
	public static final String XML_AUTO_ADJUST			= "autoAdjust";			//$NON-NLS-1$
	public static final String XML_ZOOM					= "zoom";				//$NON-NLS-1$
	public static final String XML_UNIT					= "unit";				//$NON-NLS-1$
	public static final String XML_CLASSIC_GRID			= "classicGrid";		//$NON-NLS-1$
	public static final String XML_MAGNETIC_GRID_STYLE	= "magneticGridStyle";	//$NON-NLS-1$
	public static final String XML_GRID_GAP				= "gridGap";			//$NON-NLS-1$
	public static final String XML_CODE					= "code";				//$NON-NLS-1$
	public static final String XML_MAGNETIC_GRID		= "magneticGrid";		//$NON-NLS-1$
	public static final String XML_VERSION				= "version";			//$NON-NLS-1$
	public static final String XML_POSITION 			= "position";			//$NON-NLS-1$
	public static final String XML_POSITION_X 			= "x";					//$NON-NLS-1$
	public static final String XML_POSITION_Y 			= "y";					//$NON-NLS-1$
	public static final String XML_SIZE 				= "size";				//$NON-NLS-1$
	public static final String XML_HEIGHT 				= "height";				//$NON-NLS-1$
	public static final String XML_WIDTH 				= "width";				//$NON-NLS-1$
	public static final String XML_MAXIMISED 			= "maximised";			//$NON-NLS-1$
	public static final String XML_DISPLAY_GRID 		= "displayGrid";		//$NON-NLS-1$
	public static final String XML_ROOT_PREFERENCES 	= "preferences";		//$NON-NLS-1$
	public static final String XML_RENDERING 			= "rendering";			//$NON-NLS-1$
	public static final String XML_COLOR_RENDERING 		= "colorRendering";		//$NON-NLS-1$
	public static final String XML_ALPHA_INTER 			= "alphaInter";			//$NON-NLS-1$
	public static final String XML_ANTI_ALIAS 			= "antiAlias";			//$NON-NLS-1$
	public static final String XML_PATH_EXPORT 			= "pathExport";			//$NON-NLS-1$
	public static final String XML_PATH_OPEN 			= "pathOpen";			//$NON-NLS-1$
	public static final String XML_DISPLAY_X 			= "displayXScale";		//$NON-NLS-1$
	public static final String XML_DISPLAY_Y 			= "displayYScale";		//$NON-NLS-1$
	public static final String XML_CHECK_VERSION 		= "checkVersion";		//$NON-NLS-1$
	public static final String XML_LANG 				= "lang";				//$NON-NLS-1$
	public static final String XML_PATH_TEX_EDITOR 		= "pathTexEditor";		//$NON-NLS-1$
	public static final String XML_LATEX_INCLUDES		= "latexIncludes";		//$NON-NLS-1$
	public static final String XML_RECENT_FILES 		= "recentFiles";		//$NON-NLS-1$
	public static final String XML_NB_RECENT_FILES 		= "nb";					//$NON-NLS-1$
	public static final String XML_RECENT_FILE 			= "recentFile";			//$NON-NLS-1$
	public static final String XML_LAF 					= "laf";				//$NON-NLS-1$
	public static final String XML_PATH_TYPE			= "pathType";			//$NON-NLS-1$
	public static final String XML_INTERVAL				= "interval";			//$NON-NLS-1$
	public static final String XML_STYLE				= "style";				//$NON-NLS-1$
	public static final String XML_POINTS				= "points";				//$NON-NLS-1$

	public static final String XML_GRID_X_SOUTH			= "XLabelsSouth";		//$NON-NLS-1$
	public static final String XML_GRID_Y_WEST			= "YLabelsWest";		//$NON-NLS-1$
	public static final String XML_GRID_DOTS			= "gridDots";			//$NON-NLS-1$
	public static final String XML_GRID_UNIT			= "gridUnit";			//$NON-NLS-1$
	public static final String XML_GRID_END				= "gridEnd";			//$NON-NLS-1$
	public static final String XML_GRID_START			= "gridStart";			//$NON-NLS-1$
	public static final String XML_GRID_ORIGIN			= "origin";				//$NON-NLS-1$
	public static final String XML_GRID_SUB_DIV			= "subDiv";				//$NON-NLS-1$
	public static final String XML_GRID_WIDTH			= "Gridwidth";			//$NON-NLS-1$

	public static final String XML_ARROW_DOT_SIZE_NUM	= "dotSizeNum";			//$NON-NLS-1$
	public static final String XML_ARROW_TBAR_SIZE_NUM	= "tbarSizeNum";		//$NON-NLS-1$
	public static final String XML_ARROW_SIZE_NUM		= "arrSizeNum";			//$NON-NLS-1$

	public static final String XML_AXE_IS_WEST			= "isWest";				//$NON-NLS-1$
	public static final String XML_AXE_IS_SOUTH			= "isSouth";			//$NON-NLS-1$
	public static final String XML_AXE_INCREMENT		= "increment";			//$NON-NLS-1$
	public static final String XML_AXE_TICKS_SIZE		= "ticksSize";			//$NON-NLS-1$
	public static final String XML_AXE_SHOW_ORIGIN		= "showOrigin";			//$NON-NLS-1$
	public static final String XML_AXE_SHOW_TICKS		= "showTicks";			//$NON-NLS-1$
	public static final String XML_AXE_LABELS_STYLE		= "showLabels";			//$NON-NLS-1$
	public static final String XML_AXE_DIST_LABELS		= "distLabels";			//$NON-NLS-1$
	public static final String XML_AXE_TICKS_STYLE		= "ticksStyle";			//$NON-NLS-1$
	
	private LNamespace() {
		super();
	}
}
