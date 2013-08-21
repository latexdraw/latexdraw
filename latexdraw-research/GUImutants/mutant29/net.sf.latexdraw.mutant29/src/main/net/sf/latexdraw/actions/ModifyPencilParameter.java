package net.sf.latexdraw.actions;

import java.awt.Color;

import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IFreehand.FreeHandType;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.instruments.Pencil;

/**
 * This action modifies a parameter of the pencil and updates its corresponding instrument.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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


	/**
	 * Creates and initialises the action.
	 * @since 3.0
	 */
	public ModifyPencilParameter() {
		super();
	}


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
				pencil.getGroupParams().setBordersPosition((BorderPos) value);
				break;
			case COLOUR_DBLE_BORD:
				pencil.getGroupParams().setDbleBordCol((Color) value);
				break;
			case COLOUR_FILLING:
				pencil.getGroupParams().setFillingCol((Color) value);
				break;
			case COLOUR_GRADIENT_END:
				pencil.getGroupParams().setGradColEnd((Color) value);
				break;
			case COLOUR_GRADIENT_START:
				pencil.getGroupParams().setGradColStart((Color) value);
				break;
			case COLOUR_HATCHINGS:
				pencil.getGroupParams().setHatchingsCol((Color) value);
				break;
			case COLOUR_LINE:
				pencil.getGroupParams().setLineColour((Color) value);
				break;
			case COLOUR_SHADOW:
				pencil.getGroupParams().setShadowCol((Color) value);
				break;
			case DBLE_BORDERS:
				pencil.getGroupParams().setHasDbleBord((Boolean) value);
				break;
			case DOT_FILLING_COL:
				pencil.getGroupParams().setDotFillingCol((Color)value);
				break;
			case DOT_SIZE:
				pencil.getGroupParams().setRadius((Double) value);
				break;
			case DOT_STYLE:
				pencil.getGroupParams().setDotStyle((DotStyle) value);
				break;
			case FILLING_STYLE:
				pencil.getGroupParams().setFillingStyle((FillingStyle) value);
				break;
			case LINE_STYLE:
				pencil.getGroupParams().setLineStyle((LineStyle) value);
				break;
			case LINE_THICKNESS:
				pencil.getGroupParams().setThickness((Double) value);
				break;
			case SHADOW:
				pencil.getGroupParams().setHasShadow((Boolean) value);
				break;
			case ROUND_CORNER_VALUE:
				pencil.getGroupParams().setLineArc((Double)value);
				break;
			case DBLE_BORDERS_SIZE:
				pencil.getGroupParams().setDbleBordSep((Double)value);
				break;
			case SHADOW_ANGLE:
				pencil.getGroupParams().setShadowAngle((Double)value);
				break;
			case SHADOW_SIZE:
				pencil.getGroupParams().setShadowSize((Double)value);
				break;
			case GRAD_ANGLE:
				pencil.getGroupParams().setGradAngle((Double)value);
				break;
			case GRAD_MID_POINT:
				pencil.getGroupParams().setGradMidPt((Double)value);
				break;
			case HATCHINGS_ANGLE:
				pencil.getGroupParams().setHatchingsAngle((Double)value);
				break;
			case HATCHINGS_SEP:
				pencil.getGroupParams().setHatchingsSep((Double)value);
				break;
			case HATCHINGS_WIDTH:
				pencil.getGroupParams().setHatchingsWidth((Double)value);
				break;
			case TEXT_POSITION:
				pencil.getGroupParams().setTextPosition((TextPosition)value);
				break;
			case ARROW1_STYLE:
				pencil.getGroupParams().setArrowStyle((ArrowStyle)value, 0);
				break;
			case ARROW2_STYLE:
				pencil.getGroupParams().setArrowStyle((ArrowStyle)value, 1);
				break;
			case TEXT:
				// The pencil does not set text values.
				break;
			case ARC_END_ANGLE	: pencil.getGroupParams().setAngleEnd((Double)value); 	break;
			case ARC_START_ANGLE: pencil.getGroupParams().setAngleStart((Double)value);	break;
			case ARC_STYLE		: pencil.getGroupParams().setArcStyle((ArcStyle)value);		break;
			case GRID_START: pencil.getGroupParams().setGridStart(((IPoint)value).getX(), ((IPoint)value).getY()); break;
			case GRID_END: pencil.getGroupParams().setGridEnd(((IPoint)value).getX(), ((IPoint)value).getY()); break;
			case GRID_LABEL_POSITION_Y: pencil.getGroupParams().setXLabelSouth((Boolean)value); break;
			case GRID_LABEL_POSITION_X: pencil.getGroupParams().setYLabelWest((Boolean)value); break;
			case ARROW_INSET: pencil.getGroupParams().setArrowInset((Double)value); break;
			case ARROW_LENGTH: pencil.getGroupParams().setArrowLength((Double)value); break;
			case ARROW_BRACKET_NUM: pencil.getGroupParams().setBracketNum((Double)value); break;
			case ARROW_DOT_SIZE_DIM: pencil.getGroupParams().setDotSizeDim((Double)value); break;
			case ARROW_DOT_SIZE_NUM: pencil.getGroupParams().setDotSizeNum((Double)value); break;
			case ARROW_R_BRACKET_NUM: pencil.getGroupParams().setRBracketNum((Double)value); break;
			case ARROW_SIZE_DIM: pencil.getGroupParams().setArrowSizeDim((Double)value); break;
			case ARROW_SIZE_NUM: pencil.getGroupParams().setArrowSizeNum((Double)value); break;
			case ARROW_T_BAR_SIZE_DIM: pencil.getGroupParams().setTBarSizeDim((Double)value); break;
			case ARROW_T_BAR_SIZE_NUM: pencil.getGroupParams().setTBarSizeNum((Double)value); break;
			case GRID_SIZE_LABEL: pencil.getGroupParams().setLabelsSize((Integer)value); break;
			case GRID_ORIGIN: pencil.getGroupParams().setOrigin(((IPoint)value).getX(), ((IPoint)value).getY()); break;
			case AXES_STYLE: pencil.getGroupParams().setAxesStyle((AxesStyle)value); break;
			case AXES_TICKS_STYLE: pencil.getGroupParams().setTicksStyle((TicksStyle)value); break;
//			case AXES_TICKS_SIZE: pencil.getGroupParams().setTicksSize((Double)value); break;
			case AXES_TICKS_SHOW: pencil.getGroupParams().setTicksDisplayed((PlottingStyle)value); break;
			case AXES_LABELS_INCR: pencil.getGroupParams().setIncrement((IPoint)value); break;
			case AXES_LABELS_SHOW: pencil.getGroupParams().setLabelsDisplayed((PlottingStyle)value); break;
			case AXES_SHOW_ORIGIN: pencil.getGroupParams().setShowOrigin((Boolean)value); break;
			case AXES_LABELS_DIST: pencil.getGroupParams().setDistLabels((IPoint)value); break;
			case GRID_LABELS_COLOUR: pencil.getGroupParams().setGridLabelsColour((Color)value); break;
			case GRID_SUBGRID_COLOUR: pencil.getGroupParams().setSubGridColour((Color)value); break;
			case GRID_WIDTH: pencil.getGroupParams().setGridWidth((Double)value); break;
			case GRID_SUBGRID_WIDTH: pencil.getGroupParams().setSubGridWidth((Double)value); break;
			case GRID_DOTS: pencil.getGroupParams().setGridDots((Integer)value); break;
			case GRID_SUBGRID_DOTS: pencil.getGroupParams().setSubGridDots((Integer)value); break;
			case GRID_SUBGRID_DIV: pencil.getGroupParams().setSubGridDiv((Integer)value); break;
			case FREEHAND_STYLE: pencil.getGroupParams().setType((FreeHandType)value); break;
			case FREEHAND_INTERVAL: pencil.getGroupParams().setInterval((Integer)value); break;
			case FREEHAND_OPEN: pencil.getGroupParams().setOpen((Boolean)value); break;
			case SHOW_POINTS: pencil.getGroupParams().setShowPts((Boolean)value); break;
		}
	}
}
