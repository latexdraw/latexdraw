package net.sf.latexdraw.parsers.svg;

/**
 * Contains SVG elements according to the SVG specification version 1.1.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.<br>
 *<br>
 * 06/03/07<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 */
public final class SVGElements {
	private SVGElements() {
		super();
	}
	
	public static final String SVG_CIRCLE				= "circle";					//$NON-NLS-1$
	public static final String SVG_DEFS					= "defs";					//$NON-NLS-1$
//	public static final String SVG_DESC		   			= "desc";					//$NON-NLS-1$
//	public static final String SVG_CLIP_PATH			= "clipPath";				//$NON-NLS-1$
	public static final String SVG_ELLIPSE	   			= "ellipse";				//$NON-NLS-1$
//	public static final String SVG_FONT					= "font";					//$NON-NLS-1$
//	public static final String SVG_FONT_FACE			= "font-face";				//$NON-NLS-1$
//	public static final String SVG_FONT_FACE_FORMAT		= "font-face-format";		//$NON-NLS-1$
//	public static final String SVG_FONT_FACE_NAME		= "font-face-name";			//$NON-NLS-1$
	public static final String SVG_G					= "g";						//$NON-NLS-1$
	public static final String SVG_METADATA				= "metadata";				//$NON-NLS-1$
	public static final String SVG_POLYGON				= "polygon";				//$NON-NLS-1$
	public static final String SVG_POLY_LINE			= "polyline";				//$NON-NLS-1$
	public static final String SVG_LINEAR_GRADIENT		= "linearGradient";			//$NON-NLS-1$
	public static final String SVG_RECT					= "rect";					//$NON-NLS-1$
	public static final String SVG_TEXT					= "text";					//$NON-NLS-1$
//	public static final String SVG_TEXT_PATH			= "textPath";				//$NON-NLS-1$
	public static final String SVG_SVG					= "svg";					//$NON-NLS-1$
	public static final String SVG_STOP					= "stop";					//$NON-NLS-1$
	public static final String SVG_PATTERN				= "pattern";				//$NON-NLS-1$
	public static final String SVG_PATH					= "path";					//$NON-NLS-1$
	public static final String SVG_USE					= "use";					//$NON-NLS-1$
	public static final String SVG_MARKER				= "marker";					//$NON-NLS-1$
	public static final String SVG_LINE					= "line";					//$NON-NLS-1$
	public static final String SVG_IMAGE				= "image";					//$NON-NLS-1$

//	public static final String SVG_RADIAL_GRADIENT		= "radialGradient";			//$NON-NLS-1$
//	public static final String SVG_A					= "a";						//$NON-NLS-1$
//	public static final String SVG_ALT_GLYPH			= "altGlyph";				//$NON-NLS-1$
//	public static final String SVG_ALT_GLYPH_DEF		= "altGlyphDef";			//$NON-NLS-1$
//	public static final String SVG_ALT_GLYPH_ITEM		= "altGlyphItem";			//$NON-NLS-1$
//	public static final String SVG_ANIMATE				= "animate";				//$NON-NLS-1$
//	public static final String SVG_ANIMATE_COLOR		= "animateColor";			//$NON-NLS-1$
//	public static final String SVG_ANIMATE_MOTION		= "animateMotion";			//$NON-NLS-1$
//	public static final String SVG_ANIMATE_TRANSFORM	= "animateTransform";		//$NON-NLS-1$
//	public static final String SVG_COLOR_PROFILE		= "color-profile";			//$NON-NLS-1$
//	public static final String SVG_CURSOR				= "cursor";					//$NON-NLS-1$
//	public static final String SVG_DEFINITION_SRC		= "definition-src";			//$NON-NLS-1$
//	public static final String SVG_FE_BLEND	   			= "feBlend";				//$NON-NLS-1$
//	public static final String SVG_FE_COLOR_MATRIX		= "feColorMatrix";			//$NON-NLS-1$
//	public static final String SVG_FE_COMPONENT_TRANSFER= "feComponentTransfer";	//$NON-NLS-1$
//	public static final String SVG_FE_COMPOSITE			= "feComposite";			//$NON-NLS-1$
//	public static final String SVG_FE_CONVOLVE_MATRIX	= "feConvolveMatrix";		//$NON-NLS-1$
//	public static final String SVG_FE_DIFFUSE_LIGHTING	= "feDiffuseLighting";		//$NON-NLS-1$
//	public static final String SVG_FE_DISPLACEMENT_MAP	= "feDisplacementMap";		//$NON-NLS-1$
//	public static final String SVG_FE_DISTANT_LIGHT		= "feDistantLight";			//$NON-NLS-1$
//	public static final String SVG_FE_FLOOD				= "feFlood";				//$NON-NLS-1$
//	public static final String SVG_FE_FUNC_A			= "feFuncA";				//$NON-NLS-1$
//	public static final String SVG_FE_FUNC_B			= "feFuncB";				//$NON-NLS-1$
//	public static final String SVG_FE_FUNC_R			= "feFuncR";				//$NON-NLS-1$
//	public static final String SVG_FE_FUNC_G			= "feFuncG";				//$NON-NLS-1$
//	public static final String SVG_FE_GAUSSIAN_BLUR		= "feGaussianBlur";			//$NON-NLS-1$
//	public static final String SVG_FE_IMAGE				= "feImage";				//$NON-NLS-1$
//	public static final String SVG_FE_MERGE				= "feMerge";				//$NON-NLS-1$
//	public static final String SVG_FE_MERGE_NODE		= "feMergeNode";			//$NON-NLS-1$
//	public static final String SVG_FE_MORPHOLOGY		= "feMorphology";			//$NON-NLS-1$
//	public static final String SVG_FE_OFFSET			= "feOffset";				//$NON-NLS-1$
//	public static final String SVG_FE_POINT_LIGHT		= "fePointLight";			//$NON-NLS-1$
//	public static final String SVG_FE_SPECULAR_LIGHTING	= "feSpecularLighting";		//$NON-NLS-1$
//	public static final String SVG_FE_SPOT_LIGHT		= "feSpotLight";			//$NON-NLS-1$
//	public static final String SVG_FE_TILE				= "feTile";					//$NON-NLS-1$
//	public static final String SVG_FE_TURBULENCE		= "feTurbulence";			//$NON-NLS-1$
//	public static final String SVG_FILTER				= "filter";					//$NON-NLS-1$
//	public static final String SVG_FONT_FACE_SRC		= "font-face-src";			//$NON-NLS-1$
//	public static final String SVG_FONT_FACE_URI		= "font-face-uri";			//$NON-NLS-1$
//	public static final String SVG_FOREIGN_OBJECT		= "foreignObject";			//$NON-NLS-1$
//	public static final String SVG_GLYPH				= "glyph";					//$NON-NLS-1$
//	public static final String SVG_GLYPH_REF			= "glyphRef";				//$NON-NLS-1$
//	public static final String SVG_HKERN				= "hkern";					//$NON-NLS-1$
//	public static final String SVG_MASK					= "mask";					//$NON-NLS-1$
//	public static final String SVG_MISSING_GLYPH		= "missing-glyph";			//$NON-NLS-1$
//	public static final String SVG_MPATH				= "mpath";					//$NON-NLS-1$
//	public static final String SVG_SCRIPT				= "script";					//$NON-NLS-1$
//	public static final String SVG_SET					= "set";					//$NON-NLS-1$
//	public static final String SVG_STYLE				= "style";					//$NON-NLS-1$
//	public static final String SVG_SWITCH				= "switch";					//$NON-NLS-1$
//	public static final String SVG_SYMBOL				= "symbol";					//$NON-NLS-1$
//	public static final String SVG_TITLE				= "title";					//$NON-NLS-1$
//	public static final String SVG_TREF					= "tref";					//$NON-NLS-1$
//	public static final String SVG_TSPAN				= "tspan";					//$NON-NLS-1$
//	public static final String SVG_VIEW					= "view";					//$NON-NLS-1$
//	public static final String SVG_VKERN				= "vkern";					//$NON-NLS-1$
}
