package net.sf.latexdraw.glib.views.pst;

import java.awt.Color;
import java.awt.geom.Point2D;

import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;

/**
 * This class contains several constants of pstricks<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.<br>
 *<br>
 * 06/06/06<br>
 * @author Arnaud BLOUIN
 * @version 2.0.0<br>
 */
public abstract class PSTricksConstants {
    /** The size in centimetres of an inch */
    public static final double INCH_VAL_CM = 2.54;

    /** A inch is 72 postscript points */
    public static final double INCH_VAL_PT = 72;

    /** A point is INCH_VAL_PT/INCH_VAL_CM postscript points */
    public static final double CM_VAL_PT = INCH_VAL_PT/INCH_VAL_CM;

    /** For psaxes, the Ox value by default. */
	public static final int DEFAULT_OX = 0;

	/** For psaxes, the Oy value by default. */
	public static final int DEFAULT_OY = 0;

	/** For psaxes, the dx value by default. */
	public static final int DEFAULT_DX = 1;

	/** For psaxes, the dy value by default. */
	public static final int DEFAULT_DY = 1;

	/** The distance between each X label in cm (Dx). */
	public static final double DEFAULT_DIST_X_LABEL = 0./CM_VAL_PT;

	/** The distance between each Y label in cm (Dy). */
	public static final double DEFAULT_DIST_Y_LABEL = 0./CM_VAL_PT;

	/** For psaxes, the showorigin value by default. */
	public static final boolean DEFAULT_SHOW_ORIGIN = true;

	/** The default size of the ticks in cm. */
	public static final double DEFAULT_TICKS_SIZE = 4./CM_VAL_PT;

	/** The text to display when all the labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_ALL 	= "all"; //$NON-NLS-1$

	/** The text to display when only the X labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_X 	= "x"; //$NON-NLS-1$

	/** The text to display when only the Y labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_Y 	= "y";//$NON-NLS-1$

	/** The text to display when no labels must be displayed. */
	public static final String TOKEN_LABELS_DISPLAYED_NONE 	= "none";//$NON-NLS-1$

	/** The token when the ticks must be fully shown. */
	public static final String TOKEN_TICKS_STYLE_FULL 	= "full";//$NON-NLS-1$

	/** The token when the ticks only the top of them must be shown. */
	public static final String TOKEN_TICKS_STYLE_TOP	= "top";//$NON-NLS-1$

	/** The token when the ticks only the bottom of them must be shown. */
	public static final String TOKEN_TICKS_STYLE_BOTTOM = "bottom";//$NON-NLS-1$

	/** The text to display when the axes have the axes shape. */
	public static final String TOKEN_AXES_STYLE_AXES 	= "axes";//$NON-NLS-1$

	/** The text to display when the axes have the frame shape. */
	public static final String TOKEN_AXES_STYLE_FRAME 	= "frame";//$NON-NLS-1$

	/** The text to display when the axes must not be shown. */
	public static final String TOKEN_AXES_STYLE_NONE 	= "none";//$NON-NLS-1$

	public static final AxesStyle DEFAULT_AXES_STYLE = AxesStyle.AXES;

	public static final TicksStyle DEFAULT_TICKS_STYLE = TicksStyle.FULL;

	public static final PlottingStyle DEFAULT_LABELS_DISPLAYED = PlottingStyle.ALL;

	public static final PlottingStyle DEFAULT_TICKS_DISPLAYED = PlottingStyle.ALL;

	/** The first colour of a gradient by default. */
	public static final Color DEFAULT_GRADIENT_START_COLOR = new Color(0, (int)(.1*255), (int)(.95*255));

	/** The second color of a gradient by default. */
	public static final Color DEFAULT_GRADIENT_END_COLOR   = new Color(0, 255, 255);

	/** The position of the midpoint, as a fraction of the distance from
		top to bottom. Should be between 0 and 1. */
	public static final double DEFAULT_GRADIENT_MID_POINT = .9;

	/** The number of lines. More lines means finer gradation. */
	public static final int DEFAULT_GRADIENT_LINES = 500;

	/** The angle of the gradient in degree. */
	public static final double DEFAULT_GRADIENT_ANGLE = 0;

	/** The value by default of a missing coordinate : (5,) */
	public static final double DEFAULT_VALUE_MISSING_COORDINATE = 1.;

	public static final String TOKEN_PUT_ANGLE_0   = "U";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_90  = "L";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_180 = "D";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_270 = "R";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_0_STAR	= "N";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_90_STAR	= "W";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_180_STAR	= "S";//$NON-NLS-1$
	public static final String TOKEN_PUT_ANGLE_270_STAR	= "E";//$NON-NLS-1$

	public static final char TOKEN_UPUT_DOWN 	= 'd';
	public static final char TOKEN_UPUT_UP	 	= 'u';
	public static final char TOKEN_UPUT_RIGHT 	= 'r';
	public static final char TOKEN_UPUT_LEFT 	= 'l';

	public static final double DEFAULT_UPUT_DOWN_ANGLE 	= 270;
	public static final double DEFAULT_UPUT_UP_ANGLE 	=  90;
	public static final double DEFAULT_UPUT_RIGHT_ANGLE =   0;
	public static final double DEFAULT_UPUT_LEFT_ANGLE 	= 180;
	public static final double DEFAULT_UPUT_DOWN_LEFT_ANGLE 	= 225;
	public static final double DEFAULT_UPUT_UP_LEFT_ANGLE 		= 135;
	public static final double DEFAULT_UPUT_DOWN_RIGHT_ANGLE 	= 315;
	public static final double DEFAULT_UPUT_UP_RIGHT_ANGLE		=  45;

	/** gangle is the angle of rotation of a diamond and a triangle
	 * around their centre. */
	public static final double DEFAULT_GANGLE = 0;

	/** The token of the parameters : fillstyle = none */
	public static final String TOKEN_FILL_NONE = "none";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = gradient */
	public static final String TOKEN_FILL_GRADIENT = "gradient";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = solid */
	public static final String TOKEN_FILL_SOLID = "solid";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = vlines */
	public static final String TOKEN_FILL_VLINES = "vlines";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = vlines* */
	public static final String TOKEN_FILL_VLINES_F = "vlines*";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = hlines */
	public static final String TOKEN_FILL_HLINES = "hlines";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = hlines* */
	public static final String TOKEN_FILL_HLINES_F = "hlines*";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = crosshatch */
	public static final String TOKEN_FILL_CROSSHATCH = "crosshatch";//$NON-NLS-1$

	/** The token of the parameters : fillstyle = crosshatch* */
	public static final String TOKEN_FILL_CROSSHATCH_F = "crosshatch*";//$NON-NLS-1$

	/** The token of the parameters : cornersize = relative */
	public static final String TOKEN_RELATIVE = "relative";//$NON-NLS-1$

	/** The token of the parameters : cornersize = absolute */
	public static final String TOKEN_ABSOLUTE = "absolute";//$NON-NLS-1$

	/** The token of the postscript point unit of length */
	public static final String TOKEN_PS_PT = "pt";//$NON-NLS-1$

	/** The token of the unit of length : centimeter */
	public static final String TOKEN_CM = "cm";//$NON-NLS-1$

	/** The token of the unit of length : millimeter */
	public static final String TOKEN_MM = "mm";//$NON-NLS-1$

	/** The token of the unit of length : inch */
	public static final String TOKEN_INCH = "in";//$NON-NLS-1$

	/** The style which draw a o and aplus */
	public static final String OPLUS_STYLE      = "oplus"; //$NON-NLS-1$

	/** The style which draw a o and a cross */
	public static final String OTIMES_STYLE     = "otimes"; //$NON-NLS-1$

	/** The style which draw a plus */
	public static final String PLUS_STYLE       = "+"; //$NON-NLS-1$

	/** The style which draw an asterisk */
	public static final String ASTERISK_STYLE   = "asterisk"; //$NON-NLS-1$

	/** The style which draw a triangle */
	public static final String TRIANGLE_STYLE   = "triangle"; //$NON-NLS-1$

	/** The style which draw a square */
	public static final String SQUARE_STYLE     = "square"; //$NON-NLS-1$

	/** The style which draw a diamond */
	public static final String DIAMOND_STYLE    = "diamond"; //$NON-NLS-1$

	/** The style which draw a pentagon */
	public static final String PENTAGON_STYLE   = "pentagon"; //$NON-NLS-1$

	/** The style which draw a o */
	public static final String O_STYLE          = "o"; //$NON-NLS-1$

	/** The style which draw a dot */
	public static final String DOT_STYLE        = "*"; //$NON-NLS-1$

	/** The style which draw a cross */
	public static final String X_STYLE          = "x"; //$NON-NLS-1$

	/** The style which draw a vertical bar */
	public static final String BAR_STYLE        = "|"; //$NON-NLS-1$

	/** The style which draw a full triangle */
	public static final String FTRIANGLE_STYLE  = "triangle*"; //$NON-NLS-1$

	/** The style which draw a full square */
	public static final String FSQUARE_STYLE    = "square*"; //$NON-NLS-1$

	/** The style which draw a full diamond */
	public static final String FDIAMOND_STYLE   = "diamond*"; //$NON-NLS-1$

	/** The style which draw a full pentagon */
	public static final String FPENTAGON_STYLE  = "pentagon*"; //$NON-NLS-1$

	/** Is the special coor activated by default */
	public static final boolean DEFAULT_SPECIAL_COOR = false;

	/** Is the unit of length is rad by default */
	public static final boolean DEFAULT_ON_RADIANS = false;

	/** The angle in degrees by default */
	public static final double DEFAULT_DEGREES = 360;

	/** The width of the line by default (in cm) */
	public static final double DEFAULT_LINE_WIDTH = 0.8/CM_VAL_PT;

	/** Is points are displayed by default */
	public static final boolean DEFAULT_SHOW_POINTS = false;

	/** The radius of arcs drawn at the corner of lines by default (in pt) **/
	public static final double DEFAULT_LINE_ARC = 0;

	/** The radius of rounded corners by default (between 0 and 1)<br>
	 *  The radius is compute with :<br>
	 *  radius = min(width, height)*1.5*frameArc **/
	public static final double DEFAULT_FRAME_ARC = 0;

	/** Is the corner size relative by default (if not, it's absolute)<br>
	 * If cornersize is relative, then the framearc parameter determines
	 * the radius of the rounded corners for \psframe. If
 	 * cornersize is absolute, then the linearc parameter determines the
	 * radius of the rounded corners for \psframe.*/
	public static final boolean DEFAULT_CORNER_SIZE_RELATIVE = true;

	/** The value of arcsepA by default (in pt)*/
	public static final double DEFAULT_ARC_SEP_A = 0;

	/** The value of arcsepB by default (in pt)*/
	public static final double DEFAULT_ARC_SEP_B = 0;

	public static final double DEFAULT_ARC_SEP = 0;

	/** The curvature parameters control the curvature of a parabola */
	public static final double DEFAULT_CURVATURE_NUM1 = 1;

	/** The curvature parameters control the curvature of a parabola */
	public static final double DEFAULT_CRUVATURE_NUM2 = 0.1;

	/** The curvature parameters control the curvature of a parabola */
	public static final double DEFAULT_CRUVATURE_NUM3 = 0;

	/** The type of the dot by default */
	public static final DotStyle DEFAULT_DOT_STYLE = DotStyle.DOT;

	/** The scale of the dot by default */
	public static final double DEFAULT_DOT_SCALE1 = 1;

	/** The scale of the dot by default */
	public static final double DEFAULT_DOT_SCALE2 = 1;

	/** The angle of the dot by default */
	public static final double DEFAULT_DOT_ANGLE = 0;

	/** The width of the main lines of the grid by default in cm */
	public static final double DEFAULT_GRID_WIDTH = 0.8/CM_VAL_PT;

	/** The size of the labels of a grid by default */
	public static final double DEFAULT_GRID_LABEL = 10;

	/** The size of the sub-grid lines of a grid by default in cm */
	public static final double DEFAULT_SUB_GRID_WIDTH = 0.4/CM_VAL_PT;

	/** The colour of the sub grid by default */
	public static final Color DEFAULT_SUB_GRID_COLOR = Color.GRAY;

	/** The dimensions of the figure are with reference to the inner boundary. */
	public static final String BORDERS_INSIDE = "outer"; //$NON-NLS-1$

	/** The dimensions of the figure are with reference to the outter boundary. */
	public static final String BORDERS_OUTSIDE = "inner"; //$NON-NLS-1$

	/** The dimensions of the figure are with reference to the middle boundary. */
	public static final String BORDERS_MIDDLE = "middle"; //$NON-NLS-1$

	/** The position of the borders by default */
	public static final BorderPos DEFAULT_BORDERS_POS = BorderPos.INTO;

	/** The colour of the double boundary of the figure by default */
	public static final Color DEFAULT_DOUBLE_COLOR = Color.WHITE;

	/** The colour of the hatching of the figure by default */
	public static final Color DEFAULT_HATCHING_COLOR = Color.BLACK;

	/** The colour of the interior of the figure by default */
	public static final Color DEFAULT_INTERIOR_COLOR = Color.WHITE;

	/** The colour of the borders of the figure by default */
	public static final Color DEFAULT_LINE_COLOR = Color.BLACK;

	/** The colour of the labels by default */
	public static final Color DEFAULT_LABELGRIDCOLOR = Color.BLACK;

	/** The colour of the grid by default */
	public static final Color DEFAULT_GRIDCOLOR = Color.BLACK;

	/** The number of division in a sub-grid by default */
	public static final int DEFAULT_SUBGRIDDIV = 5;

	/** The number of dots in a line of the grid by default */
	public static final int DEFAULT_GRIDDOTS = 0;

	/** The number of dots in a line of the sub-grid by default */
	public static final int DEFAULT_SUBGRIDDOTS = 0;

	/** The unit by default in cm */
	public static final double DEFAULT_UNIT = 1;

	public static final int DEFAULT_PLOT_POINTS = 50;

	/** The origin of the coordinate system by default */
	public static final Point2D.Double DEFAULT_ORIGIN = new Point2D.Double(0,0);

	/** Is the axes swept by default */
	public static final boolean DEFAULT_SWAP_AXES = false;

	/** The line(s) of the figure are dotted */
	public static final String LINE_DOTTED_STYLE = "dotted"; //$NON-NLS-1$

	/** The line(s) of the figure are dashed */
	public static final String LINE_DASHED_STYLE = "dashed"; //$NON-NLS-1$

	/** The line(s) of the figure have no style */
	public static final String LINE_NONE_STYLE = "none"; //$NON-NLS-1$

	/** The line(s) of the figure are solid */
	public static final String LINE_SOLID_STYLE = "solid"; //$NON-NLS-1$

	/** The style of the lines by default */
	public static final LineStyle DEFAULT_LINE_STYLE = LineStyle.SOLID;

	/** The black-white dash pattern for the dashed line style (in cm) */
	public static final double DEFAULT_DASH_BLACK = 5./CM_VAL_PT;

	/** The black-white dash pattern for the dashed line style (in cm) */
	public static final double DEFAULT_DASH_WHITE = 3./CM_VAL_PT;

	/** The distance between dots in the dotted line style (in cm) */
	public static final double DEFAULT_DOT_STEP = 3./CM_VAL_PT;

	/** A positive value draws a border of width DEFAULT_BORDER. */
	public static final double DEFAULT_BORDER = 0.;

	/** The colour by default of the border */
	public static final Color DEFAULT_BORDER_COLOR = Color.WHITE;

	/** Is a double line by default */
	public static final boolean DEFAULT_DOUBLE_LINE = false;

	/** The width of the separation between the double lines */
	public static final double DEFAULT_DOUBLE_SEP = 1.25/(DEFAULT_LINE_WIDTH*CM_VAL_PT)/CM_VAL_PT;

	/** Is there shadow by default */
	public static final boolean DEFAULT_SHADOW = false;

	/** The size of the shadow by default in cm */
	public static final double DEFAULT_SHADOW_SIZE = 3/CM_VAL_PT;

	/** The angle of the shadow by default in degrees */
	public static final double DEFAULT_SHADOW_ANGLE = -45;

	/** The colour of the shadow by default */
	public static final Color DEFAULT_SHADOW_COLOR = Color.DARK_GRAY;

	/** The type of the fill by default */
	public static final FillingStyle DEFAULT_FILL_STYLE = FillingStyle.NONE;

	/** The colour of the filling */
	public static final Color DEFAULT_FILL_COLOR = Color.WHITE;

	/** The width of the hatching in cm */
	public static final double DEFAULT_HATCH_WIDTH = 0.8/CM_VAL_PT;

	/** The separation between the lines of the hatching in cm */
	public static final double DEFAULT_HATCH_SEP = 4/CM_VAL_PT;

	/** The angle of the hatching by default */
	public static final double DEFAULT_HATCH_ANGLE = 45;

	public static final String LARROW_STYLE    = "<"; //$NON-NLS-1$
	public static final String RARROW_STYLE    = ">"; //$NON-NLS-1$
	public static final String DLARROW_STYLE   = "<<";//$NON-NLS-1$
	public static final String DRARROW_STYLE   = ">>";//$NON-NLS-1$
	public static final String BAREND_STYLE   = "|*";//$NON-NLS-1$
	public static final String BARIN_STYLE     = "|";//$NON-NLS-1$
	public static final String LSBRACKET_STYLE = "[";//$NON-NLS-1$
	public static final String RSBRACKET_STYLE = "]";//$NON-NLS-1$
	public static final String LRBRACKET_STYLE = "(";//$NON-NLS-1$
	public static final String RRBRACKET_STYLE = ")";//$NON-NLS-1$
	public static final String CIRCLEEND_STYLE = "o";//$NON-NLS-1$
	public static final String CIRCLEIN_STYLE  = "oo";//$NON-NLS-1$
	public static final String DISKEND_STYLE   = "*";//$NON-NLS-1$
	public static final String DISKIN_STYLE    = "**";//$NON-NLS-1$
	public static final String ROUNDEND_STYLE  = "c";//$NON-NLS-1$
	public static final String ROUNDIN_STYLE   = "cc";//$NON-NLS-1$
	public static final String SQUAREEND_STYLE = "C";//$NON-NLS-1$

	/** Width of arrowheads, in cm */
	public static final double DEFAULT_ARROW_SIZE_DIM = 1.5/CM_VAL_PT;

	/** Width of arrowheads */
	public static final double DEFAULT_ARROW_SIZE_NUM = 2.;

	/** The length of the arrowhead by default*/
	public static final double DEFAULT_ARROW_LENGTH = 1.4;

	/** The inset of the arrowhead by default */
	public static final double DEFAULT_ARROW_INSET = 0.4;

	/** The width of a t-bar,in cm */
	public static final double DEFAULT_ARROW_TBARSIZE_DIM = 2/CM_VAL_PT;

	/** The width of a t-bar */
	public static final double DEFAULT_ARROW_TBARSIZE_NUM = 5;

	/** The height of a square bracket */
	public static final double DEFAULT_ARROW_BRACKET_LGTH = 0.15;

	/** The height of a round bracket */
	public static final double DEFAULT_ARROW_RBRACKET_LGTH = 0.15;

	/** The diameter of a circle in cm */
	public static final double DEFAULT_ARROW_DOTSIZE_DIM = 2/CM_VAL_PT;

	/** The diameter of a circle */
	public static final double DEFAULT_ARROW_DOTSIZE_NUM = 2;

	/** The scale of the arrowhead */
	public static final double DEFAULT_ARROW_SCALE1 = 1;

	/** The scale of the arrowhead */
	public static final double DEFAULT_ARROW_SCALE2 = 1;

	/** The value by default of labelsep of the command uput (in cm)*/
	public static final double DEFAULT_LABEL_SEP = 5/CM_VAL_PT;

	/** Distance between each side of a frame and the enclosed box (in cm). */
	public static final double DEFAULT_FRAME_SEP = 3/CM_VAL_PT;

	/** When true, the box that is produced is the size of the frame or
		whatever that is drawn around the object. */
	public static final boolean DEFAULT_BOX_SEP = true;


	/**
	 * Allows to know if the kind of filling is valid or not.
	 * @param style The style to check.
	 * @return True is the style is valid.
	 */
	public static boolean isValidFillStyle(final String style) {
		if(style==null)
			return false;

		return style.equals(TOKEN_FILL_NONE) || style.equals(TOKEN_FILL_HLINES)
				|| style.equals(TOKEN_FILL_HLINES_F) || style.equals(TOKEN_FILL_CROSSHATCH)
				|| style.equals(TOKEN_FILL_CROSSHATCH_F) || style.equals(TOKEN_FILL_VLINES)
				|| style.equals(TOKEN_FILL_VLINES_F) || style.equals(TOKEN_FILL_GRADIENT) || style.equals(TOKEN_FILL_SOLID);
	}


	private PSTricksConstants() {
		super();
	}
}
