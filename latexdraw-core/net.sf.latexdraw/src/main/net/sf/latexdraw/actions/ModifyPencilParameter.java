package net.sf.latexdraw.actions;

import java.awt.Color;

import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp.FreeHandType;
import net.sf.latexdraw.glib.models.interfaces.prop.ITextProp.TextPosition;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.instruments.Pencil;

/**
 * This action modifies a parameter of the pencil and updates its corresponding instrument.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ModifyPencilParameter extends ShapePropertyAction {
	/** The pencil to modify. */
	protected Pencil pencil;


    @Override
	public void flush() {
		super.flush();
		pencil = null;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && pencil!=null;
	}


	@Override
	protected boolean isPropertySupported() {
		return super.isPropertySupported() && property!=ShapeProperties.TEXT;
	}


	@Override
	protected void doActionBody() {
		// Modification of the pencil.
		applyValue(value);
	}



	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * Defines the pencil to modify.
	 * @param pencil The pencil to modify.
	 * @since 3.0
	 */
	public void setPencil(final Pencil pencil) {
		this.pencil = pencil;
	}


	@Override
	protected void applyValue(final Object obj) {
		switch(property) {
			case BORDER_POS:
				pencil.groupParams().setBordersPosition((BorderPos) value);
				break;
			case COLOUR_DBLE_BORD:
				pencil.groupParams().setDbleBordCol((Color) value);
				break;
			case COLOUR_FILLING:
				pencil.groupParams().setFillingCol((Color) value);
				break;
			case COLOUR_GRADIENT_END:
				pencil.groupParams().setGradColEnd((Color) value);
				break;
			case COLOUR_GRADIENT_START:
				pencil.groupParams().setGradColStart((Color) value);
				break;
			case COLOUR_HATCHINGS:
				pencil.groupParams().setHatchingsCol((Color) value);
				break;
			case COLOUR_LINE:
				pencil.groupParams().setLineColour((Color) value);
				break;
			case COLOUR_SHADOW:
				pencil.groupParams().setShadowCol((Color) value);
				break;
			case DBLE_BORDERS:
				pencil.groupParams().setHasDbleBord((Boolean) value);
				break;
			case DOT_FILLING_COL:
				pencil.groupParams().setDotFillingCol((Color)value);
				break;
			case DOT_SIZE:
				pencil.groupParams().setDiametre((Double) value);
				break;
			case DOT_STYLE:
				pencil.groupParams().setDotStyle((DotStyle) value);
				break;
			case FILLING_STYLE:
				pencil.groupParams().setFillingStyle((FillingStyle) value);
				break;
			case LINE_STYLE:
				pencil.groupParams().setLineStyle((LineStyle) value);
				break;
			case LINE_THICKNESS:
				pencil.groupParams().setThickness((Double) value);
				break;
			case SHADOW:
				pencil.groupParams().setHasShadow((Boolean) value);
				break;
			case ROUND_CORNER_VALUE:
				pencil.groupParams().setLineArc((Double)value);
				break;
			case DBLE_BORDERS_SIZE:
				pencil.groupParams().setDbleBordSep((Double)value);
				break;
			case SHADOW_ANGLE:
				pencil.groupParams().setShadowAngle((Double)value);
				break;
			case SHADOW_SIZE:
				pencil.groupParams().setShadowSize((Double)value);
				break;
			case GRAD_ANGLE:
				pencil.groupParams().setGradAngle((Double)value);
				break;
			case GRAD_MID_POINT:
				pencil.groupParams().setGradMidPt((Double)value);
				break;
			case HATCHINGS_ANGLE:
				pencil.groupParams().setHatchingsAngle((Double)value);
				break;
			case HATCHINGS_SEP:
				pencil.groupParams().setHatchingsSep((Double)value);
				break;
			case HATCHINGS_WIDTH:
				pencil.groupParams().setHatchingsWidth((Double)value);
				break;
			case TEXT_POSITION:
				pencil.groupParams().setTextPosition((TextPosition)value);
				break;
			case ARROW1_STYLE:
				pencil.groupParams().setArrowStyle((ArrowStyle)value, 0);
				break;
			case ARROW2_STYLE: pencil.groupParams().setArrowStyle((ArrowStyle)value, -1); break;
			case TEXT:
				// The pencil does not set text values.
				break;
			case ARC_END_ANGLE	: pencil.groupParams().setAngleEnd((Double)value); 	break;
			case ARC_START_ANGLE: pencil.groupParams().setAngleStart((Double)value);	break;
			case ARC_STYLE		: pencil.groupParams().setArcStyle((ArcStyle)value);		break;
			case GRID_START: pencil.groupParams().setGridStart(((IPoint)value).getX(), ((IPoint)value).getY()); break;
			case GRID_END: pencil.groupParams().setGridEnd(((IPoint)value).getX(), ((IPoint)value).getY()); break;
			case GRID_LABEL_POSITION_Y: pencil.groupParams().setXLabelSouth((Boolean)value); break;
			case GRID_LABEL_POSITION_X: pencil.groupParams().setYLabelWest((Boolean)value); break;
			case ARROW_INSET: pencil.groupParams().setArrowInset((Double)value); break;
			case ARROW_LENGTH: pencil.groupParams().setArrowLength((Double)value); break;
			case ARROW_BRACKET_NUM: pencil.groupParams().setBracketNum((Double)value); break;
			case ARROW_DOT_SIZE_DIM: pencil.groupParams().setDotSizeDim((Double)value); break;
			case ARROW_DOT_SIZE_NUM: pencil.groupParams().setDotSizeNum((Double)value); break;
			case ARROW_R_BRACKET_NUM: pencil.groupParams().setRBracketNum((Double)value); break;
			case ARROW_SIZE_DIM: pencil.groupParams().setArrowSizeDim((Double)value); break;
			case ARROW_SIZE_NUM: pencil.groupParams().setArrowSizeNum((Double)value); break;
			case ARROW_T_BAR_SIZE_DIM: pencil.groupParams().setTBarSizeDim((Double)value); break;
			case ARROW_T_BAR_SIZE_NUM: pencil.groupParams().setTBarSizeNum((Double)value); break;
			case GRID_SIZE_LABEL: pencil.groupParams().setLabelsSize((Integer)value); break;
			case GRID_ORIGIN: pencil.groupParams().setOrigin(((IPoint)value).getX(), ((IPoint)value).getY()); break;
			case AXES_STYLE: pencil.groupParams().setAxesStyle((AxesStyle)value); break;
			case AXES_TICKS_STYLE: pencil.groupParams().setTicksStyle((TicksStyle)value); break;
//			case AXES_TICKS_SIZE: pencil.groupParams().setTicksSize((Double)value); break;
			case AXES_TICKS_SHOW: pencil.groupParams().setTicksDisplayed((PlottingStyle)value); break;
			case AXES_LABELS_INCR: pencil.groupParams().setIncrement((IPoint)value); break;
			case AXES_LABELS_SHOW: pencil.groupParams().setLabelsDisplayed((PlottingStyle)value); break;
			case AXES_SHOW_ORIGIN: pencil.groupParams().setShowOrigin((Boolean)value); break;
			case AXES_LABELS_DIST: pencil.groupParams().setDistLabels((IPoint)value); break;
			case GRID_LABELS_COLOUR: pencil.groupParams().setGridLabelsColour((Color)value); break;
			case GRID_SUBGRID_COLOUR: pencil.groupParams().setSubGridColour((Color)value); break;
			case GRID_WIDTH: pencil.groupParams().setGridWidth((Double)value); break;
			case GRID_SUBGRID_WIDTH: pencil.groupParams().setSubGridWidth((Double)value); break;
			case GRID_DOTS: pencil.groupParams().setGridDots((Integer)value); break;
			case GRID_SUBGRID_DOTS: pencil.groupParams().setSubGridDots((Integer)value); break;
			case GRID_SUBGRID_DIV: pencil.groupParams().setSubGridDiv((Integer)value); break;
			case FREEHAND_STYLE: pencil.groupParams().setType((FreeHandType)value); break;
			case FREEHAND_INTERVAL: pencil.groupParams().setInterval((Integer)value); break;
			case FREEHAND_OPEN: pencil.groupParams().setOpen((Boolean)value); break;
			case SHOW_POINTS: pencil.groupParams().setShowPts((Boolean)value); break;
			case PLOT_NB_PTS: pencil.groupParams().setNbPlottedPoints((Integer)value); break;
			case PLOT_MAX_X: pencil.groupParams().setPlotMaxX((Double)value); break;
			case PLOT_MIN_X: pencil.groupParams().setPlotMinX((Double)value); break;
			case X_SCALE: pencil.groupParams().setXScale((Double)value); break;
			case Y_SCALE: pencil.groupParams().setYScale((Double)value); break;
			case PLOT_EQ: pencil.groupParams().setPlotEquation((String)value); break;
			case PLOT_POLAR: pencil.groupParams().setPolar((Boolean)value); break;
		}
	}
}
