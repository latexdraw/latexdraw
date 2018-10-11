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
package net.sf.latexdraw.view.pst;

import java.awt.geom.Point2D;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.view.latex.DviPsColors;

/**
 * Contains several PSTricks constants.
 * @author Arnaud Blouin
 */
public final class PSTricksConstants {
	/** The size in centimetres of an inch */
	public static final double INCH_VAL_CM = 2.54;

	/** A inch is 72 postscript points */
	public static final double INCH_VAL_PT = 72;

	/** A point is INCH_VAL_PT/INCH_VAL_CM postscript points */
	public static final double CM_VAL_PT = INCH_VAL_PT / INCH_VAL_CM;

	/** For psaxes, the Ox value by default. */
	public static final int DEFAULT_OX = 0;

	/** For psaxes, the Oy value by default. */
	public static final int DEFAULT_OY = 0;

	/** For psaxes, the dx value by default. */
	public static final int DEFAULT_DX = 1;

	/** For psaxes, the dy value by default. */
	public static final int DEFAULT_DY = 1;

	/** The distance between each X label in cm (Dx). */
	public static final double DEFAULT_DIST_X_LABEL = 0. / CM_VAL_PT;

	/** The distance between each Y label in cm (Dy). */
	public static final double DEFAULT_DIST_Y_LABEL = 0. / CM_VAL_PT;

	/** For psaxes, the showorigin value by default. */
	public static final boolean DEFAULT_SHOW_ORIGIN = true;

	/** The default size of the ticks in cm. */
	public static final double DEFAULT_TICKS_SIZE = 4. / CM_VAL_PT;

	/** The text to display when all the labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_ALL = "all"; //NON-NLS

	/** The text to display when only the X labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_X = "x"; //NON-NLS

	/** The text to display when only the Y labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_Y = "y"; //NON-NLS

	/** The text to display when no labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_NONE = "none"; //NON-NLS

	/** The token when the ticks must be fully shown. */
	public static final String TOKEN_TICKS_STYLE_FULL = "full"; //NON-NLS

	/** The token when the ticks only the top of them must be shown. */
	public static final String TOKEN_TICKS_STYLE_TOP = "top"; //NON-NLS

	/** The token when the ticks only the bottom of them must be shown. */
	public static final String TOKEN_TICKS_STYLE_BOTTOM = "bottom"; //NON-NLS

	/** The text to display when the axes have the axes shape. */
	public static final String TOKEN_AXES_STYLE_AXES = "axes"; //NON-NLS

	/** The text to display when the axes have the frame shape. */
	public static final String TOKEN_AXES_STYLE_FRAME = "frame"; //NON-NLS

	/** The text to display when the axes must not be shown. */
	public static final String TOKEN_AXES_STYLE_NONE = "none"; //NON-NLS

	/** The first colour of a gradient by default. */
	public static final Color DEFAULT_GRADIENT_START_COLOR = ShapeFactory.INST.createColor(0.0, 0.1, 0.95, 1.0);

	/** The second color of a gradient by default. */
	public static final Color DEFAULT_GRADIENT_END_COLOR = ShapeFactory.INST.createColor(0.0, 1.0, 1.0, 1.0);

	/**
	 * The position of the midpoint, as a fraction of the distance from
	 * top to bottom. Should be between 0 and 1.
	 */
	public static final double DEFAULT_GRADIENT_MID_POINT = .9;

	/** The number of lines. More lines means finer gradation. */
	public static final int DEFAULT_GRADIENT_LINES = 500;

	/** The angle of the gradient in degree. */
	public static final double DEFAULT_GRADIENT_ANGLE = 0;

	/** The value by default of a missing coordinate : (5,) */
	public static final double DEFAULT_VALUE_MISSING_COORDINATE = 1.;

	/**
	 * gangle is the angle of rotation of a diamond and a triangle
	 * around their centre.
	 */
	public static final double DEFAULT_GANGLE = 0;

	/** The token of the parameters : fillstyle = none */
	public static final String TOKEN_FILL_NONE = "none"; //NON-NLS

	/** The token of the parameters : fillstyle = gradient */
	public static final String TOKEN_FILL_GRADIENT = "gradient"; //NON-NLS

	/** The token of the parameters : fillstyle = solid */
	public static final String TOKEN_FILL_SOLID = "solid"; //NON-NLS

	/** The token of the parameters : fillstyle = vlines */
	public static final String TOKEN_FILL_VLINES = "vlines"; //NON-NLS

	/** The token of the parameters : fillstyle = vlines* */
	public static final String TOKEN_FILL_VLINES_F = "vlines*"; //NON-NLS

	/** The token of the parameters : fillstyle = hlines */
	public static final String TOKEN_FILL_HLINES = "hlines"; //NON-NLS

	/** The token of the parameters : fillstyle = hlines* */
	public static final String TOKEN_FILL_HLINES_F = "hlines*"; //NON-NLS

	/** The token of the parameters : fillstyle = crosshatch */
	public static final String TOKEN_FILL_CROSSHATCH = "crosshatch"; //NON-NLS

	/** The token of the parameters : fillstyle = crosshatch* */
	public static final String TOKEN_FILL_CROSSHATCH_F = "crosshatch*"; //NON-NLS

	/** The token of the parameters : cornersize = relative */
	public static final String TOKEN_RELATIVE = "relative"; //NON-NLS

	/** The token of the parameters : cornersize = absolute */
	public static final String TOKEN_ABSOLUTE = "absolute"; //NON-NLS

	/** The token of the postscript point unit of length */
	public static final String TOKEN_PS_PT = "pt"; //NON-NLS

	/** The token of the unit of length : centimeter */
	public static final String TOKEN_CM = "cm"; //NON-NLS

	/** The token of the unit of length : millimeter */
	public static final String TOKEN_MM = "mm"; //NON-NLS

	/** The token of the unit of length : inch */
	public static final String TOKEN_INCH = "in"; //NON-NLS

	/** The style which draw a o and aplus */
	public static final String OPLUS_STYLE = "oplus"; //NON-NLS

	/** The style which draw a o and a cross */
	public static final String OTIMES_STYLE = "otimes"; //NON-NLS

	/** The style which draw a plus */
	public static final String PLUS_STYLE = "+"; //NON-NLS

	/** The style which draw an asterisk */
	public static final String ASTERISK_STYLE = "asterisk"; //NON-NLS

	/** The style which draw a triangle */
	public static final String TRIANGLE_STYLE = "triangle"; //NON-NLS

	/** The style which draw a square */
	public static final String SQUARE_STYLE = "square"; //NON-NLS

	/** The style which draw a diamond */
	public static final String DIAMOND_STYLE = "diamond"; //NON-NLS

	/** The style which draw a pentagon */
	public static final String PENTAGON_STYLE = "pentagon"; //NON-NLS

	/** The style which draw a o */
	public static final String O_STYLE = "o"; //NON-NLS

	/** The style which draw a dot */
	public static final String DOT_STYLE = "*"; //NON-NLS

	/** The style which draw a cross */
	public static final String X_STYLE = "x"; //NON-NLS

	/** The style which draw a vertical bar */
	public static final String BAR_STYLE = "|"; //NON-NLS

	/** The style which draw a full triangle */
	public static final String FTRIANGLE_STYLE = "triangle*"; //NON-NLS

	/** The style which draw a full square */
	public static final String FSQUARE_STYLE = "square*"; //NON-NLS

	/** The style which draw a full diamond */
	public static final String FDIAMOND_STYLE = "diamond*"; //NON-NLS

	/** The style which draw a full pentagon */
	public static final String FPENTAGON_STYLE = "pentagon*"; //NON-NLS

	/** Is the special coor activated by default */
	public static final boolean DEFAULT_SPECIAL_COOR = false;

	/** Is the unit of length is rad by default */
	public static final boolean DEFAULT_ON_RADIANS = false;

	/** The angle in degrees by default */
	public static final double DEFAULT_DEGREES = 360;

	/** The width of the line by default (in cm) */
	public static final double DEFAULT_LINE_WIDTH = 0.8 / CM_VAL_PT;

	/** Is points are displayed by default */
	public static final boolean DEFAULT_SHOW_POINTS = false;

	/** The radius of arcs drawn at the corner of lines by default (in pt) **/
	public static final double DEFAULT_LINE_ARC = 0;

	/**
	 * The radius of rounded corners by default (between 0 and 1)
	 * The radius is compute with :
	 * radius = min(width, height)*1.5*frameArc
	 **/
	public static final double DEFAULT_FRAME_ARC = 0;

	/**
	 * Is the corner size relative by default (if not, it's absolute)
	 * If cornersize is relative, then the framearc parameter determines
	 * the radius of the rounded corners for \psframe. If
	 * cornersize is absolute, then the linearc parameter determines the
	 * radius of the rounded corners for \psframe.
	 */
	public static final boolean DEFAULT_CORNER_SIZE_RELATIVE = true;

	/** The value of arcsepA by default (in pt) */
	public static final double DEFAULT_ARC_SEP_A = 0;

	/** The value of arcsepB by default (in pt) */
	public static final double DEFAULT_ARC_SEP_B = 0;

	public static final double DEFAULT_ARC_SEP = 0;

	/** The curvature parameters control the curvature of a parabola */
	public static final double DEFAULT_CURVATURE_NUM1 = 1;

	/** The curvature parameters control the curvature of a parabola */
	public static final double DEFAULT_CRUVATURE_NUM2 = 0.1;

	/** The curvature parameters control the curvature of a parabola */
	public static final double DEFAULT_CRUVATURE_NUM3 = 0;

	/** The scale of the dot by default */
	public static final double DEFAULT_DOT_SCALE1 = 1;

	/** The scale of the dot by default */
	public static final double DEFAULT_DOT_SCALE2 = 1;

	/** The angle of the dot by default */
	public static final double DEFAULT_DOT_ANGLE = 0;

	/** The width of the main lines of the grid by default in cm */
	public static final double DEFAULT_GRID_WIDTH = 0.8 / CM_VAL_PT;

	/** The size of the labels of a grid by default */
	public static final double DEFAULT_GRID_LABEL = 10;

	/** The size of the sub-grid lines of a grid by default in cm */
	public static final double DEFAULT_SUB_GRID_WIDTH = 0.4 / CM_VAL_PT;

	/** The colour of the sub grid by default */
	public static final Color DEFAULT_SUB_GRID_COLOR = DviPsColors.GRAY;

	/** The dimensions of the figure are with reference to the inner boundary. */
	public static final String BORDERS_INSIDE = "outer"; //NON-NLS

	/** The dimensions of the figure are with reference to the outter boundary. */
	public static final String BORDERS_OUTSIDE = "inner"; //NON-NLS

	/** The dimensions of the figure are with reference to the middle boundary. */
	public static final String BORDERS_MIDDLE = "middle"; //NON-NLS

	/** The colour of the double boundary of the figure by default */
	public static final Color DEFAULT_DOUBLE_COLOR = DviPsColors.WHITE;

	/** The colour of the hatching of the figure by default */
	public static final Color DEFAULT_HATCHING_COLOR = DviPsColors.BLACK;

	/** The colour of the interior of the figure by default */
	public static final Color DEFAULT_INTERIOR_COLOR = DviPsColors.WHITE;

	/** The colour of the borders of the figure by default */
	public static final Color DEFAULT_LINE_COLOR = DviPsColors.BLACK;

	/** The colour of the labels by default */
	public static final Color DEFAULT_LABELGRIDCOLOR = DviPsColors.BLACK;

	/** The colour of the grid by default */
	public static final Color DEFAULT_GRIDCOLOR = DviPsColors.BLACK;

	/** The number of division in a sub-grid by default */
	public static final int DEFAULT_SUBGRIDDIV = 5;

	/** The number of dots in a line of the grid by default */
	public static final int DEFAULT_GRIDDOTS = 0;

	/** The number of dots in a line of the sub-grid by default */
	public static final int DEFAULT_SUBGRIDDOTS = 0;

	/** The unit by default in cm */
	public static final double DEFAULT_UNIT = 1;

	/** The origin of the coordinate system by default */
	public static final Point2D.Double DEFAULT_ORIGIN = new Point2D.Double(0, 0);

	/** Is the axes swept by default */
	public static final boolean DEFAULT_SWAP_AXES = false;

	/** The line(s) of the figure are dotted */
	public static final String LINE_DOTTED_STYLE = "dotted"; //NON-NLS

	/** The line(s) of the figure are dashed */
	public static final String LINE_DASHED_STYLE = "dashed"; //NON-NLS

	/** The line(s) of the figure have no style */
	public static final String LINE_NONE_STYLE = "none"; //NON-NLS

	/** The line(s) of the figure are solid */
	public static final String LINE_SOLID_STYLE = "solid"; //NON-NLS

	/** The black-white dash pattern for the dashed line style (in cm) */
	public static final double DEFAULT_DASH_BLACK = 5. / CM_VAL_PT;

	/** The black-white dash pattern for the dashed line style (in cm) */
	public static final double DEFAULT_DASH_WHITE = 3. / CM_VAL_PT;

	/** The distance between dots in the dotted line style (in cm) */
	public static final double DEFAULT_DOT_STEP = 3. / CM_VAL_PT;

	/** A positive value draws a border of width DEFAULT_BORDER. */
	public static final double DEFAULT_BORDER = 0.;

	/** The colour by default of the border */
	public static final Color DEFAULT_BORDER_COLOR = DviPsColors.WHITE;

	/** Is a double line by default */
	public static final boolean DEFAULT_DOUBLE_LINE = false;

	/** The width of the separation between the double lines */
	public static final double DEFAULT_DOUBLE_SEP = 1.25 / (DEFAULT_LINE_WIDTH * CM_VAL_PT) / CM_VAL_PT;

	/** Is there shadow by default */
	public static final boolean DEFAULT_SHADOW = false;

	/** The size of the shadow by default in cm */
	public static final double DEFAULT_SHADOW_SIZE = 3 / CM_VAL_PT;

	/** The angle of the shadow by default in degrees */
	public static final double DEFAULT_SHADOW_ANGLE = -45;

	/** The colour of the shadow by default */
	public static final Color DEFAULT_SHADOW_COLOR = DviPsColors.DARKGRAY;

	/** The colour of the filling */
	public static final Color DEFAULT_FILL_COLOR = DviPsColors.WHITE;

	/** The width of the hatching in cm */
	public static final double DEFAULT_HATCH_WIDTH = 0.8 / CM_VAL_PT;

	/** The separation between the lines of the hatching in cm */
	public static final double DEFAULT_HATCH_SEP = 4 / CM_VAL_PT;

	/** The angle of the hatching by default */
	public static final double DEFAULT_HATCH_ANGLE = 45;

	public static final String LARROW_STYLE = "<"; //NON-NLS
	public static final String RARROW_STYLE = ">"; //NON-NLS
	public static final String DLARROW_STYLE = "<<"; //NON-NLS
	public static final String DRARROW_STYLE = ">>"; //NON-NLS
	public static final String BAREND_STYLE = "|*"; //NON-NLS
	public static final String BARIN_STYLE = "|"; //NON-NLS
	public static final String LSBRACKET_STYLE = "["; //NON-NLS
	public static final String RSBRACKET_STYLE = "]"; //NON-NLS
	public static final String LRBRACKET_STYLE = "("; //NON-NLS
	public static final String RRBRACKET_STYLE = ")"; //NON-NLS
	public static final String CIRCLEEND_STYLE = "o"; //NON-NLS
	public static final String CIRCLEIN_STYLE = "oo"; //NON-NLS
	public static final String DISKEND_STYLE = "*"; //NON-NLS
	public static final String DISKIN_STYLE = "**"; //NON-NLS
	public static final String ROUNDEND_STYLE = "c"; //NON-NLS
	public static final String ROUNDIN_STYLE = "cc"; //NON-NLS
	public static final String SQUAREEND_STYLE = "C"; //NON-NLS

	/** Width of arrowheads, in cm */
	public static final double DEFAULT_ARROW_SIZE_DIM = 1.5 / CM_VAL_PT;

	/** Width of arrowheads */
	public static final double DEFAULT_ARROW_SIZE_NUM = 2.;

	/** The length of the arrowhead by default */
	public static final double DEFAULT_ARROW_LENGTH = 1.4;

	/** The inset of the arrowhead by default */
	public static final double DEFAULT_ARROW_INSET = 0.4;

	/** The width of a t-bar,in cm */
	public static final double DEFAULT_ARROW_TBARSIZE_DIM = 2 / CM_VAL_PT;

	/** The width of a t-bar */
	public static final double DEFAULT_ARROW_TBARSIZE_NUM = 5;

	/** The height of a square bracket */
	public static final double DEFAULT_ARROW_BRACKET_LGTH = 0.15;

	/** The height of a round bracket */
	public static final double DEFAULT_ARROW_RBRACKET_LGTH = 0.15;

	/** The diameter of a circle in cm */
	public static final double DEFAULT_ARROW_DOTSIZE_DIM = 2 / CM_VAL_PT;

	/** The diameter of a circle */
	public static final double DEFAULT_ARROW_DOTSIZE_NUM = 2;

	/** The scale of the arrowhead */
	public static final double DEFAULT_ARROW_SCALE1 = 1;

	/** The scale of the arrowhead */
	public static final double DEFAULT_ARROW_SCALE2 = 1;

	/** Distance between each side of a frame and the enclosed box (in cm). */
	public static final double DEFAULT_FRAME_SEP = 3 / CM_VAL_PT;

	/**
	 * When true, the box that is produced is the size of the frame or
	 * whatever that is drawn around the object.
	 */
	public static final boolean DEFAULT_BOX_SEP = true;


	/**
	 * Allows to know if the kind of filling is valid or not.
	 * @param style The style to check.
	 * @return True is the style is valid.
	 */
	public static boolean isValidFillStyle(final String style) {
		return style != null && (style.equals(TOKEN_FILL_NONE) || style.equals(TOKEN_FILL_HLINES) || style.equals(TOKEN_FILL_HLINES_F) ||
			style.equals(TOKEN_FILL_CROSSHATCH) || style.equals(TOKEN_FILL_CROSSHATCH_F) || style.equals(TOKEN_FILL_VLINES) ||
			style.equals(TOKEN_FILL_VLINES_F) || style.equals(TOKEN_FILL_GRADIENT) || style.equals(TOKEN_FILL_SOLID));
	}


	private PSTricksConstants() {
		super();
	}
}
