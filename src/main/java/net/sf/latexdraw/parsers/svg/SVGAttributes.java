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
package net.sf.latexdraw.parsers.svg;

/**
 * Contains SVG attributes according to the SVG specification version 1.1.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public final class SVGAttributes {
	private SVGAttributes() {
		super();
	}

	public static final String SVG_GRADIENT_TRANSFORM = "gradientTransform"; //NON-NLS
	public static final String SVG_X 						= "x";								//NON-NLS
	public static final String SVG_Y 						= "y";								//NON-NLS
	public static final String SVG_X1 						= "x1";								//NON-NLS
	public static final String SVG_Y1 						= "y1";								//NON-NLS
	public static final String SVG_X2 						= "x2";								//NON-NLS
	public static final String SVG_Y2 						= "y2";								//NON-NLS
	public static final String SVG_RX 						= "rx";								//NON-NLS
	public static final String SVG_RY 						= "ry";								//NON-NLS
	public static final String SVG_ID 						= "id";								//NON-NLS
	public static final String SVG_HEIGHT					= "height";							//NON-NLS
	public static final String SVG_WIDTH					= "width";							//NON-NLS
	public static final String SVG_FILL						= "fill";							//NON-NLS
	public static final String SVG_STROKE					= "stroke";							//NON-NLS
	public static final String SVG_STROKE_WIDTH				= "stroke-width";					//NON-NLS
	public static final String SVG_TRANSFORM				= "transform";						//NON-NLS
	public static final String SVG_OFFSET					= "offset";							//NON-NLS
	public static final String SVG_STOP_COLOR				= "stop-color";						//NON-NLS
	public static final String SVG_PATTERN_UNITS			= "patternUnits";					//NON-NLS
	public static final String SVG_D						= "d";								//NON-NLS
	public static final String SVG_STROKE_DASHARRAY			= "stroke-dasharray";				//NON-NLS
	public static final String SVG_STROKE_LINECAP			= "stroke-linecap";					//NON-NLS
	public static final String SVG_STROKE_LINEJOIN			= "stroke-linejoin";				//NON-NLS
	public static final String SVG_STROKE_MITERLIMIT		= "stroke-miterlimit";				//NON-NLS
	public static final String SVG_STROKE_DASHOFFSET		= "stroke-dashoffset";				//NON-NLS
	public static final String SVG_POINTS					= "points";							//NON-NLS
	public static final String SVG_MARKER_END				= "marker-end";						//NON-NLS
	public static final String SVG_MARKER_START				= "marker-start";					//NON-NLS
	public static final String SVG_ORIENT					= "orient";							//NON-NLS
	public static final String SVG_R						= "r";								//NON-NLS
	public static final String SVG_REF_X					= "refX";							//NON-NLS
	public static final String SVG_REF_Y					= "refY";							//NON-NLS
	public static final String SVG_CX						= "cx";								//NON-NLS
	public static final String SVG_CY						= "cy";								//NON-NLS
	public static final String SVG_DX						= "dx";								//NON-NLS
	public static final String SVG_DY						= "dy";								//NON-NLS
	public static final String SVG_FONT_SIZE				= "font-size";						//NON-NLS
	public static final String SVG_FONT_FAMILY				= "font-family";					//NON-NLS
	public static final String SVG_VERSION					= "version";						//NON-NLS
	public static final String SVG_PATTERN_CONTENTS_UNITS	= "patternContentUnits";			//NON-NLS
	public static final String SVG_STYLE					= "style";							//NON-NLS
	public static final String SVG_MARKER_UNITS				= "markerUnits";					//NON-NLS
	public static final String SVG_MARKER_HEIGHT			= "markerHeight";					//NON-NLS
	public static final String SVG_MARKER_WIDTH				= "markerWidth";					//NON-NLS
	public static final String SVG_OVERFLOW					= "overflow";						//NON-NLS
	public static final String SVG_FONT_STYLE				= "font-style";						//NON-NLS
	public static final String SVG_FONT_WEIGHT				= "font-weight";					//NON-NLS
	public static final String SVG_VALUE_NONE				= "none";							//NON-NLS
	public static final String SVG_UNITS_VALUE_USR 			= "userSpaceOnUse";					//NON-NLS
	public static final String SVG_UNITS_VALUE_OBJ 			= "objectBoundingBox";				//NON-NLS
	public static final String SVG_LINECAP_VALUE_BUTT		= "butt";							//NON-NLS
	public static final String SVG_LINECAP_VALUE_ROUND		= "round";							//NON-NLS
	public static final String SVG_LINECAP_VALUE_SQUARE		= "square";							//NON-NLS
	public static final String SVG_LINEJOIN_VALUE_MITER		= "butt";							//NON-NLS
	public static final String SVG_LINEJOIN_VALUE_ROUND		= "round";							//NON-NLS
	public static final String SVG_LINEJOIN_VALUE_BEVEL		= "bevel";							//NON-NLS
	public static final String SVG_VALUE_AUTO				= "auto";							//NON-NLS
	public static final String SVG_UNITS_VALUE_STROKE		= "strokeWidth";					//NON-NLS
	public static final String SVG_VALUE_VISIBLE			= "visible";						//NON-NLS
	public static final String SVG_TRANSFORM_ROTATE			= "rotate";							//NON-NLS
	public static final String SVG_TRANSFORM_MATRIX			= "matrix";							//NON-NLS
	public static final String SVG_TRANSFORM_TRANSLATE		= "translate";						//NON-NLS
	public static final String SVG_TRANSFORM_SCALE			= "scale";							//NON-NLS
	public static final String SVG_TRANSFORM_SKEW_X			= "skewX";							//NON-NLS
	public static final String SVG_TRANSFORM_SKEW_Y			= "skewY";							//NON-NLS
	public static final String SVG_FONT_STYLE_NORMAL		= "normal";							//NON-NLS
	public static final String SVG_FONT_STYLE_ITALIC		= "italic";							//NON-NLS
	public static final String SVG_FONT_WEIGHT_BOLD			= "bold";							//NON-NLS
	public static final String SVG_FONT_WEIGHT_NORMAL		= "normal";							//NON-NLS
	public static final String SVG_FILL_OPACITY				= "fill-opacity";					//NON-NLS
	public static final String SVG_STROKE_OPACITY			= "stroke-opacity";					//NON-NLS
	public static final String SVG_STOP_OPACITY = "stop-opacity"; //NON-NLS
	public static final String SVG_OPACITY = "opacity"; //NON-NLS
	public static final String SVG_BASE_PROFILE				= "baseProfile";					//NON-NLS
}
