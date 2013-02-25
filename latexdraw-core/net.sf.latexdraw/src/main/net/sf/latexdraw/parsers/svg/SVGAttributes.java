package net.sf.latexdraw.parsers.svg;

/**
 * Contains SVG attributes according to the SVG specification version 1.1.<br>
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
 * 06/04/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1<br>
 */
public final class SVGAttributes {
	private SVGAttributes() {
		super();
	}

	public static final String SVG_X 						= "x";								//$NON-NLS-1$
	public static final String SVG_Y 						= "y";								//$NON-NLS-1$
	public static final String SVG_X1 						= "x1";								//$NON-NLS-1$
	public static final String SVG_Y1 						= "y1";								//$NON-NLS-1$
	public static final String SVG_X2 						= "x2";								//$NON-NLS-1$
	public static final String SVG_Y2 						= "y2";								//$NON-NLS-1$
	public static final String SVG_RX 						= "rx";								//$NON-NLS-1$
	public static final String SVG_RY 						= "ry";								//$NON-NLS-1$
	public static final String SVG_ID 						= "id";								//$NON-NLS-1$
	public static final String SVG_HEIGHT					= "height";							//$NON-NLS-1$
	public static final String SVG_WIDTH					= "width";							//$NON-NLS-1$
	public static final String SVG_FILL						= "fill";							//$NON-NLS-1$
	public static final String SVG_STROKE					= "stroke";							//$NON-NLS-1$
	public static final String SVG_STROKE_WIDTH				= "stroke-width";					//$NON-NLS-1$
	public static final String SVG_TRANSFORM				= "transform";						//$NON-NLS-1$
	public static final String SVG_OFFSET					= "offset";							//$NON-NLS-1$
	public static final String SVG_STOP_COLOR				= "stop-color";						//$NON-NLS-1$
	public static final String SVG_GRADIENT_UNITS			= "gradientUnits";					//$NON-NLS-1$
	public static final String SVG_PATTERN_UNITS			= "patternUnits";					//$NON-NLS-1$
	public static final String SVG_D						= "d";								//$NON-NLS-1$
	public static final String SVG_STROKE_DASHARRAY			= "stroke-dasharray";				//$NON-NLS-1$
	public static final String SVG_STROKE_LINECAP			= "stroke-linecap";					//$NON-NLS-1$
	public static final String SVG_STROKE_LINEJOIN			= "stroke-linejoin";				//$NON-NLS-1$
	public static final String SVG_STROKE_MITERLIMIT		= "stroke-miterlimit";				//$NON-NLS-1$
	public static final String SVG_STROKE_DASHOFFSET		= "stroke-dashoffset";				//$NON-NLS-1$
	public static final String SVG_POINTS					= "points";							//$NON-NLS-1$
	public static final String SVG_MARKER_END				= "marker-end";						//$NON-NLS-1$
	public static final String SVG_MARKER_START				= "marker-start";					//$NON-NLS-1$
	public static final String SVG_MARKER_MID				= "marker-mid";						//$NON-NLS-1$
	public static final String SVG_ORIENT					= "orient";							//$NON-NLS-1$
	public static final String SVG_R						= "r";								//$NON-NLS-1$
	public static final String SVG_REF_X					= "refX";							//$NON-NLS-1$
	public static final String SVG_REF_Y					= "refY";							//$NON-NLS-1$
	public static final String SVG_CX						= "cx";								//$NON-NLS-1$
	public static final String SVG_CY						= "cy";								//$NON-NLS-1$
	public static final String SVG_DX						= "dx";								//$NON-NLS-1$
	public static final String SVG_DY						= "dy";								//$NON-NLS-1$
	public static final String SVG_FONT_SIZE				= "font-size";						//$NON-NLS-1$
	public static final String SVG_FONT_FAMILY				= "font-family";					//$NON-NLS-1$
	public static final String SVG_VERSION					= "version";						//$NON-NLS-1$
	public static final String SVG_PATTERN_CONTENTS_UNITS	= "patternContentUnits";			//$NON-NLS-1$
	public static final String SVG_SPREAD_METHOD			= "spreadMethod";					//$NON-NLS-1$
	public static final String SVG_STYLE					= "style";							//$NON-NLS-1$
	public static final String SVG_MARKER_UNITS				= "markerUnits";					//$NON-NLS-1$
	public static final String SVG_MARKER_HEIGHT			= "markerHeight";					//$NON-NLS-1$
	public static final String SVG_MARKER_WIDTH				= "markerWidth";					//$NON-NLS-1$
	public static final String SVG_OVERFLOW					= "overflow";						//$NON-NLS-1$
	public static final String SVG_BASE_PROFILE				= "baseProfile";					//$NON-NLS-1$
	public static final String SVG_FONT_STYLE				= "font-style";						//$NON-NLS-1$
	public static final String SVG_FONT_WEIGHT				= "font-weight";					//$NON-NLS-1$

	public static final String SVG_VALUE_NONE				= "none";							//$NON-NLS-1$
	public static final String SVG_UNITS_VALUE_USR 			= "userSpaceOnUse";					//$NON-NLS-1$
	public static final String SVG_UNITS_VALUE_OBJ 			= "objectBoundingBox";				//$NON-NLS-1$
	public static final String SVG_LINECAP_VALUE_BUTT		= "butt";							//$NON-NLS-1$
	public static final String SVG_LINECAP_VALUE_ROUND		= "round";							//$NON-NLS-1$
	public static final String SVG_LINECAP_VALUE_SQUARE		= "square";							//$NON-NLS-1$
	public static final String SVG_LINEJOIN_VALUE_MITER		= "butt";							//$NON-NLS-1$
	public static final String SVG_LINEJOIN_VALUE_ROUND		= "round";							//$NON-NLS-1$
	public static final String SVG_LINEJOIN_VALUE_BEVEL		= "bevel";							//$NON-NLS-1$
	public static final String SVG_VALUE_AUTO				= "auto";							//$NON-NLS-1$
	public static final String SVG_INHERIT					= "inherit";						//$NON-NLS-1$
	public static final String SVG_PAD						= "pad";							//$NON-NLS-1$
	public static final String SVG_REFLECT					= "reflect";						//$NON-NLS-1$
	public static final String SVG_REPEAT					= "repeat";							//$NON-NLS-1$
	public static final String SVG_UNITS_VALUE_STROKE		= "strokeWidth";					//$NON-NLS-1$
	public static final String SVG_VALUE_VISIBLE			= "visible";						//$NON-NLS-1$

	public static final String SVG_TRANSFORM_ROTATE			= "rotate";							//$NON-NLS-1$
	public static final String SVG_TRANSFORM_MATRIX			= "matrix";							//$NON-NLS-1$
	public static final String SVG_TRANSFORM_TRANSLATE		= "translate";						//$NON-NLS-1$
	public static final String SVG_TRANSFORM_SCALE			= "scale";							//$NON-NLS-1$
	public static final String SVG_TRANSFORM_SKEW_X			= "skewX";							//$NON-NLS-1$
	public static final String SVG_TRANSFORM_SKEW_Y			= "skewY";							//$NON-NLS-1$

	public static final String SVG_FONT_STYLE_NORMAL		= "normal";							//$NON-NLS-1$
	public static final String SVG_FONT_STYLE_OBLIQUE		= "oblique";						//$NON-NLS-1$
	public static final String SVG_FONT_STYLE_ITALIC		= "italic";							//$NON-NLS-1$
	public static final String SVG_FONT_WEIGHT_BOLD			= "bold";							//$NON-NLS-1$
	public static final String SVG_FONT_WEIGHT_NORMAL		= "normal";							//$NON-NLS-1$

//	public static final String SVG_TEXT_ANCHOR				= "text-anchor";					//$NON-NLS-1$
//	public static final String SVG_TEXT_ANCHOR_VALUE_END	= "end";							//$NON-NLS-1$
//	public static final String SVG_TEXT_ANCHOR_VALUE_START	= "start";							//$NON-NLS-1$
//	public static final String SVG_TEXT_ANCHOR_VALUE_MIDDLE	= "middle";							//$NON-NLS-1$
//	public static final String SVG_STOP_OPACITY				= "stop-opacity";					//$NON-NLS-1$
//	public static final String SVG_ACCENT_HEIGHT 			= "accent-height";					//$NON-NLS-1$
//	public static final String SVG_ACCUMULATE				= "accumulate";						//$NON-NLS-1$
//	public static final String SVG_ALIGNMENT_BASELINE		= "alignment-baseline";				//$NON-NLS-1$
//	public static final String SVG_ALPHABETIC				= "alphabetic";						//$NON-NLS-1$
//	public static final String SVG_AMPLITUDE				= "amplitude";						//$NON-NLS-1$
//	public static final String SVG_ANIMATE					= "animate";						//$NON-NLS-1$
//	public static final String SVG_ARABIC_FORM				= "arabic-form";					//$NON-NLS-1$
//	public static final String SVG_ASCENT					= "ascent";							//$NON-NLS-1$
//	public static final String SVG_ATTRIBUTE_TYPE			= "attributeType";					//$NON-NLS-1$
//	public static final String SVG_AZIMUTH					= "azimuth";						//$NON-NLS-1$
//	public static final String SVG_BASE_FREQUENCY			= "baseFrequency";					//$NON-NLS-1$
//	public static final String SVG_BASELINE_SHIFT			= "baseline-shift";					//$NON-NLS-1$
//	public static final String SVG_BBOX						= "bbox";							//$NON-NLS-1$
//	public static final String SVG_BIAS						= "bias";							//$NON-NLS-1$
//	public static final String SVG_BY						= "by";								//$NON-NLS-1$
//	public static final String SVG_CALC_MODE				= "calcMode";						//$NON-NLS-1$
//	public static final String SVG_CAP_HEIGHT				= "cap-height";						//$NON-NLS-1$
//	public static final String SVG_CAP_CLASS				= "class";							//$NON-NLS-1$
//	public static final String SVG_CLIP						= "clip";							//$NON-NLS-1$
//	public static final String SVG_CLIP_PATH				= "clip-path";						//$NON-NLS-1$
//	public static final String SVG_CLIP_RULE				= "clip-rule";						//$NON-NLS-1$
//	public static final String SVG_CLIP_PATH_UNITS			= "clipPathUnits";					//$NON-NLS-1$
//	public static final String SVG_COLOR					= "color";							//$NON-NLS-1$
//	public static final String SVG_COLOR_INTERPOLATION		= "color-interpolation";			//$NON-NLS-1$
//	public static final String SVG_COLOR_INTERPOLATION_FILTERS	= "color-interpolation-filters";//$NON-NLS-1$
//	public static final String SVG_COLOR_PROFILE			= "color-profile";					//$NON-NLS-1$
//	public static final String SVG_COLOR_RENDERING			= "color-rendering";				//$NON-NLS-1$
//	public static final String SVG_CONTENT_SCRIPT_TYPE		= "contentScriptType";				//$NON-NLS-1$
//	public static final String SVG_CONTENT_STYLE_TYPE		= "contentStyleType";				//$NON-NLS-1$
//	public static final String SVG_CURSOR					= "cursor";							//$NON-NLS-1$
//	public static final String SVG_DESCENT					= "descent";						//$NON-NLS-1$
//	public static final String SVG_DIFFUSE_CONSTANT			= "diffuseConstant";				//$NON-NLS-1$
//	public static final String SVG_DIRECTION				= "direction";						//$NON-NLS-1$
//	public static final String SVG_DISPLAY					= "display";						//$NON-NLS-1$
//	public static final String SVG_DIVISOR					= "divisor";						//$NON-NLS-1$
//	public static final String SVG_DOMINANT_BASELINE		= "dominant-baseline";				//$NON-NLS-1$
//	public static final String SVG_EDGE_MODE				= "edgeMode";						//$NON-NLS-1$
//	public static final String SVG_ELEVATION				= "elevation";						//$NON-NLS-1$
//	public static final String SVG_ENABLE_BACKGROUND		= "enable-background";				//$NON-NLS-1$
//	public static final String SVG_END						= "end";							//$NON-NLS-1$
//	public static final String SVG_EXPONENT					= "exponent";						//$NON-NLS-1$
//	public static final String SVG_EXTERNAL_RESOURCES_REQUIRED	= "externalResourcesRequired";	//$NON-NLS-1$
//	public static final String SVG_FE_COLOR_MATRIX			= "feColorMatrix";					//$NON-NLS-1$
//	public static final String SVG_FE_COMPOSITE				= "feComposite";					//$NON-NLS-1$
//	public static final String SVG_FE_GAUSSIAN_BLUR			= "feGaussianBlur";					//$NON-NLS-1$
//	public static final String SVG_FE_MORPHOLOGY			= "feMorphology";					//$NON-NLS-1$
//	public static final String SVG_FE_TILE					= "feTile";							//$NON-NLS-1$
//	public static final String SVG_FILL_OPACITY				= "fill-opacity";					//$NON-NLS-1$
//	public static final String SVG_FILL_RULE				= "fill-rule";						//$NON-NLS-1$
//	public static final String SVG_FILTER					= "filter";							//$NON-NLS-1$
//	public static final String SVG_FILTER_RES				= "filterRes";						//$NON-NLS-1$
//	public static final String SVG_FILTER_UNITS				= "filterUnits";					//$NON-NLS-1$
//	public static final String SVG_FLOOD_COLOR				= "flood-color";					//$NON-NLS-1$
//	public static final String SVG_FLOOD_OPACITY			= "flood-opacity";					//$NON-NLS-1$
//	public static final String SVG_FONT_SIZE_ADJUST			= "font-size-adjust";				//$NON-NLS-1$
//	public static final String SVG_FONT_STRETCH				= "font-stretch";					//$NON-NLS-1$
//	public static final String SVG_FONT_VARIANT				= "font-variant";					//$NON-NLS-1$
//	public static final String SVG_FORMAT					= "format";							//$NON-NLS-1$
//	public static final String SVG_FROM						= "from";							//$NON-NLS-1$
//	public static final String SVG_FX						= "fx";								//$NON-NLS-1$
//	public static final String SVG_FY						= "fy";								//$NON-NLS-1$
//	public static final String SVG_G1						= "g1";								//$NON-NLS-1$
//	public static final String SVG_G2						= "g2";								//$NON-NLS-1$
//	public static final String SVG_GLYPH_NAME				= "glyph-name";						//$NON-NLS-1$
//	public static final String SVG_GLYPH_ORIENTATION_HORIZONTAL	= "glyph-orientation-horizontal";	//$NON-NLS-1$
//	public static final String SVG_GLYPH_ORIENTATION_VERTICAL	= "glyph-orientation-vertical";		//$NON-NLS-1$
//	public static final String SVG_GLYPH_REF				= "glyphRef";						//$NON-NLS-1$
//	public static final String SVG_GRADIENT_TRANSFORM		= "gradientTransform";				//$NON-NLS-1$
//	public static final String SVG_HANGING					= "hanging";						//$NON-NLS-1$
//	public static final String SVG_HORIZ_ADV_X				= "horiz-adv-x";					//$NON-NLS-1$
//	public static final String SVG_HORIZ_ORIGIN_X			= "horiz-origin-x";					//$NON-NLS-1$
//	public static final String SVG_HORIZ_ORIGIN_Y			= "horiz-origin-y";					//$NON-NLS-1$
//	public static final String SVG_IDEOGRAPHIC				= "ideographic";					//$NON-NLS-1$
//	public static final String SVG_IMAGE_RENDERING			= "image-rendering";				//$NON-NLS-1$
//	public static final String SVG_IN						= "in";								//$NON-NLS-1$
//	public static final String SVG_IN2						= "in2";							//$NON-NLS-1$
//	public static final String SVG_INTERCEPT				= "intercept";						//$NON-NLS-1$
//	public static final String SVG_K						= "k";								//$NON-NLS-1$
//	public static final String SVG_K1						= "k1";								//$NON-NLS-1$
//	public static final String SVG_K2						= "k2";								//$NON-NLS-1$
//	public static final String SVG_K3						= "k3";								//$NON-NLS-1$
//	public static final String SVG_K4						= "k4";								//$NON-NLS-1$
//	public static final String SVG_KERNEL_MATRIX			= "kernelMatrix";					//$NON-NLS-1$
//	public static final String SVG_KERNEL_UNIT_LENGTH		= "kernelUnitLength";				//$NON-NLS-1$
//	public static final String SVG_KERNING					= "kerning";						//$NON-NLS-1$
//	public static final String SVG_KEY_POINTS				= "keyPoints";						//$NON-NLS-1$
//	public static final String SVG_KEY_SPLINES				= "keySplines";						//$NON-NLS-1$
//	public static final String SVG_KEY_TIMES				= "keyTimes";						//$NON-NLS-1$
//	public static final String SVG_LANG						= "lang";							//$NON-NLS-1$
//	public static final String SVG_LENGTH_ADJUST			= "lengthAdjust";					//$NON-NLS-1$
//	public static final String SVG_LETTER_SPACING			= "letter-spacing";					//$NON-NLS-1$
//	public static final String SVG_LIGHTING_COLOR			= "lighting-color";					//$NON-NLS-1$
//	public static final String SVG_LIMITING_CONE_ANGLE		= "limitingConeAngle";				//$NON-NLS-1$
//	public static final String SVG_LOCAL					= "local";							//$NON-NLS-1$
//	public static final String SVG_MASK						= "mask";							//$NON-NLS-1$
//	public static final String SVG_MASK_CONTENT_UNITS		= "maskContentUnits";				//$NON-NLS-1$
//	public static final String SVG_MASK_UNITS				= "maskUnits";						//$NON-NLS-1$
//	public static final String SVG_MASK_MATHEMATICAL		= "mathematical";					//$NON-NLS-1$
//	public static final String SVG_MAX						= "max";							//$NON-NLS-1$
//	public static final String SVG_MEDIA					= "media";							//$NON-NLS-1$
//	public static final String SVG_METHOD					= "method";							//$NON-NLS-1$
//	public static final String SVG_MIN						= "min";							//$NON-NLS-1$
//	public static final String SVG_MODE						= "mode";							//$NON-NLS-1$
//	public static final String SVG_NAME						= "name";							//$NON-NLS-1$
//	public static final String SVG_NUM_OCTAVES				= "numOctaves";						//$NON-NLS-1$
//	public static final String SVG_ONABORT					= "onabort";						//$NON-NLS-1$
//	public static final String SVG_ONACTIVE					= "onactivate";						//$NON-NLS-1$
//	public static final String SVG_ONBEGIN					= "onbegin";						//$NON-NLS-1$
//	public static final String SVG_ONCLICK					= "onclick";						//$NON-NLS-1$
//	public static final String SVG_ONEND					= "onend";							//$NON-NLS-1$
//	public static final String SVG_ONERROR					= "onerror";						//$NON-NLS-1$
//	public static final String SVG_ONFOCUSIN				= "onfocusin";						//$NON-NLS-1$
//	public static final String SVG_ONFOCUSOUT				= "onfocusout";						//$NON-NLS-1$
//	public static final String SVG_ONLOAD					= "onload";							//$NON-NLS-1$
//	public static final String SVG_ONMOUSEDOWN				= "onmousedown";					//$NON-NLS-1$
//	public static final String SVG_ONMOUSEMOVE				= "onmousemove";					//$NON-NLS-1$
//	public static final String SVG_ONMOUSEOUT				= "onmouseout";						//$NON-NLS-1$
//	public static final String SVG_ONMOUSEOVER				= "onmouseover";					//$NON-NLS-1$
//	public static final String SVG_ONMOUSEUP				= "onmouseup";						//$NON-NLS-1$
//	public static final String SVG_ONREPEAT					= "onrepeat";						//$NON-NLS-1$
//	public static final String SVG_ONRESIZE					= "onresize";						//$NON-NLS-1$
//	public static final String SVG_ONSCROLL					= "onscroll";						//$NON-NLS-1$
//	public static final String SVG_ONUNLOAD					= "onunload";						//$NON-NLS-1$
//	public static final String SVG_ONZOOM					= "onzoom";							//$NON-NLS-1$
//	public static final String SVG_OPACITY					= "opacity";						//$NON-NLS-1$
//	public static final String SVG_OPERATOR					= "operator";						//$NON-NLS-1$
//	public static final String SVG_ORDER					= "order";							//$NON-NLS-1$
//	public static final String SVG_ORIENTATION				= "orientation";					//$NON-NLS-1$
//	public static final String SVG_ORIGIN					= "origin";							//$NON-NLS-1$
//	public static final String SVG_OVERLINE_POSITION		= "overline-position";				//$NON-NLS-1$
//	public static final String SVG_OVERLINE_THICKNESS		= "overline-thickness";				//$NON-NLS-1$
//	public static final String SVG_PANOSE_1					= "panose-1";						//$NON-NLS-1$
//	public static final String SVG_PATH						= "path";							//$NON-NLS-1$
//	public static final String SVG_PATH_LENGTH				= "pathLength";						//$NON-NLS-1$
//	public static final String SVG_PATTERN_TRANSFORMATION	= "patternTransform";				//$NON-NLS-1$
//	public static final String SVG_POINTER_EVENTS			= "pointer-events";					//$NON-NLS-1$
//	public static final String SVG_POINTS_AT_X				= "pointsAtX";						//$NON-NLS-1$
//	public static final String SVG_POINTS_AT_Y				= "pointsAtY";						//$NON-NLS-1$
//	public static final String SVG_POINTS_AT_Z				= "pointsAtZ";						//$NON-NLS-1$
//	public static final String SVG_PRESERVE_ALPHA			= "preserveAlpha";					//$NON-NLS-1$
//	public static final String SVG_PRESERVE_ASPECT_RATIO	= "preserveAspectRatio";			//$NON-NLS-1$
//	public static final String SVG_PRIMITIVE_UNITS			= "primitiveUnits";					//$NON-NLS-1$
//	public static final String SVG_RADIUS					= "radius";							//$NON-NLS-1$
//	public static final String SVG_RENDERING_INTENT			= "rendering-intent";				//$NON-NLS-1$
//	public static final String SVG_REPEAT_COUNT				= "repeatCount";					//$NON-NLS-1$
//	public static final String SVG_REPEAT_DUR				= "repeatDur";						//$NON-NLS-1$
//	public static final String SVG_REQUIRED_EXTENSIONS		= "requiredExtensions";				//$NON-NLS-1$
//	public static final String SVG_RESTART					= "restart";						//$NON-NLS-1$
//	public static final String SVG_RESULT					= "result";							//$NON-NLS-1$
//	public static final String SVG_ROTATE					= "rotate";							//$NON-NLS-1$
//	public static final String SVG_SCALE					= "scale";							//$NON-NLS-1$
//	public static final String SVG_SEED						= "seed";							//$NON-NLS-1$
//	public static final String SVG_SHAPE_RENDERING			= "shape-rendering";				//$NON-NLS-1$
//	public static final String SVG_SLOPE					= "slope";							//$NON-NLS-1$
//	public static final String SVG_SPACING					= "spacing";						//$NON-NLS-1$
//	public static final String SVG_SPECULAR_CONSTANT		= "specularConstant";				//$NON-NLS-1$
//	public static final String SVG_SPECULAR_EXPONENT		= "specularExponent";				//$NON-NLS-1$
//	public static final String SVG_START_OFFSET				= "startOffset";					//$NON-NLS-1$
//	public static final String SVG_STD_DEVIATION			= "stdDeviation";					//$NON-NLS-1$
//	public static final String SVG_STEMH					= "stemh";							//$NON-NLS-1$
//	public static final String SVG_STEMV					= "stemv";							//$NON-NLS-1$
//	public static final String SVG_STITCH_TILES				= "stitchTiles";					//$NON-NLS-1$
//	public static final String SVG_STRIKETHROUGH_POSITION	= "strikethrough-position";			//$NON-NLS-1$
//	public static final String SVG_STRIKETHROUGH_THICKNESS	= "strikethrough-thickness";		//$NON-NLS-1$
//	public static final String SVG_STROKE_OPACITY			= "stroke-opacity";					//$NON-NLS-1$
//	public static final String SVG_SURFACE_SCALE			= "surfaceScale";					//$NON-NLS-1$
//	public static final String SVG_SYSTEM_LANGUAGE			= "systemLanguage";					//$NON-NLS-1$
//	public static final String SVG_TABLE_VALUES				= "tableValues";					//$NON-NLS-1$
//	public static final String SVG_TARGET					= "target";							//$NON-NLS-1$
//	public static final String SVG_TARGETX					= "targetX";						//$NON-NLS-1$
//	public static final String SVG_TARGETY					= "targetY";						//$NON-NLS-1$
//	public static final String SVG_TEXT_DECORATION			= "text-decoration";				//$NON-NLS-1$
//	public static final String SVG_TEXT_RENDERING			= "text-rendering";					//$NON-NLS-1$
//	public static final String SVG_TEXT_LENGTH				= "textLength";						//$NON-NLS-1$
}
