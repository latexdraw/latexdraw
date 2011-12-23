package net.sf.latexdraw.actions;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.Arcable.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.instruments.Pencil;

/**
 * This action modifies a parameter of the pencil and updates its corresponding instrument.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
		return super.isPropertySupported() && property!=ShapeProperties.ROTATION_ANGLE &&
				property!=ShapeProperties.TEXT;
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
				pencil.setBorderPosition((BorderPos) value);
				break;
			case COLOUR_DBLE_BORD:
				pencil.setDoubleBorderColor((Color) value);
				break;
			case COLOUR_FILLING:
				pencil.setFillingColor((Color) value);
				break;
			case COLOUR_GRADIENT_END:
				pencil.setGradEndColor((Color) value);
				break;
			case COLOUR_GRADIENT_START:
				pencil.setGradStartColor((Color) value);
				break;
			case COLOUR_HATCHINGS:
				pencil.setHatchingsColor((Color) value);
				break;
			case COLOUR_LINE:
				pencil.setLineColor((Color) value);
				break;
			case COLOUR_SHADOW:
				pencil.setShadowColor((Color) value);
				break;
			case DBLE_BORDERS:
				pencil.setDoubleBorder((Boolean) value);
				break;
			case DOT_FILLING_COL:
				pencil.setDotFillingCol((Color)value);
				break;
			case DOT_SIZE:
				pencil.setDotSize((Double) value);
				break;
			case DOT_STYLE:
				pencil.setDotStyle((DotStyle) value);
				break;
			case FILLING_STYLE:
				pencil.setFillingStyle((FillingStyle) value);
				break;
			case LINE_STYLE:
				pencil.setLineStyle((LineStyle) value);
				break;
			case LINE_THICKNESS:
				pencil.setThickness((Double) value);
				break;
			case SHADOW:
				pencil.setShadow((Boolean) value);
				break;
			case ROUND_CORNER_VALUE:
				pencil.setRoundness((Double)value);
				break;
			case DBLE_BORDERS_SIZE:
				pencil.setDoubleBorderSize((Double)value);
				break;
			case SHADOW_ANGLE:
				pencil.setShadowAngle((Double)value);
				break;
			case SHADOW_SIZE:
				pencil.setShadowSize((Double)value);
				break;
			case GRAD_ANGLE:
				pencil.setGradAngle((Double)value);
				break;
			case GRAD_MID_POINT:
				pencil.setGradMidPt((Double)value);
				break;
			case HATCHINGS_ANGLE:
				pencil.setHatchAngle((Double)value);
				break;
			case HATCHINGS_SEP:
				pencil.setHatchSep((Double)value);
				break;
			case HATCHINGS_WIDTH:
				pencil.setHatchWidth((Double)value);
				break;
			case TEXT_POSITION:
				pencil.setTextPosition((TextPosition)value);
				break;
			case ROTATION_ANGLE:
				// The pencil does not perform rotation.
				break;
			case ARROW1_STYLE:
				pencil.setArrowLeftStyle((ArrowStyle)value);
				break;
			case ARROW2_STYLE:
				pencil.setArrowRightStyle((ArrowStyle)value);
				break;
			case TEXT:
				// The pencil does not set text values.
				break;
			case ARC_END_ANGLE	: pencil.setArcEndAngle((Double)value); 	break;
			case ARC_START_ANGLE: pencil.setArcStartAngle((Double)value);	break;
			case ARC_STYLE		: pencil.setArcStyle((ArcStyle)value);		break;
			case GRID_START: pencil.setGridStart((IPoint)value); break;
		}
	}
}
