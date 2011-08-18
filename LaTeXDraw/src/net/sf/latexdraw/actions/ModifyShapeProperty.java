package net.sf.latexdraw.actions;

import java.awt.Color;

import org.malai.undo.Undoable;

import net.sf.latexdraw.glib.models.interfaces.Dottable;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;

/**
 * This action modifies a shape property of the given shape.<br>
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
 * 11/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ModifyShapeProperty extends ShapePropertyAction implements Undoable {
	/** The shape to modify. */
	protected IShape shape;

	/** The old value of the property. */
	protected Object oldValue;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public ModifyShapeProperty() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		shape 		= null;
		oldValue 	= null;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && shape!=null;
	}


	@Override
	public void undo() {
		applyValue(oldValue);
	}


	@Override
	public void redo() {
		applyValue(value);
	}


	@Override
	public String getUndoName() {
		return property==null ? "" : property.getMessage(); //$NON-NLS-1$
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void applyValue(final Object obj) {
		switch(property) {
			case ARROW_END_STYLE:
				break;
			case ARROW_START_STYLE:
				break;
			case BORDER_POS:
				oldValue = shape.getBordersPosition();
				shape.setBordersPosition((BorderPos)obj);
				break;
			case COLOUR_DBLE_BORD:
				oldValue = shape.getDbleBordCol();
				shape.setDbleBordCol((Color)obj);
				break;
			case COLOUR_FILLING:
				oldValue = shape.isFilled();
				shape.setFillingCol((Color)value);
				break;
			case COLOUR_GRADIENT_END:
				oldValue = shape.getGradColEnd();
				shape.setGradColEnd((Color)obj);
				break;
			case COLOUR_GRADIENT_START:
				oldValue = shape.getGradColStart();
				shape.setGradColStart((Color)obj);
				break;
			case COLOUR_HATCHINGS:
				oldValue = shape.getHatchingsCol();
				shape.setHatchingsCol((Color)obj);
				break;
			case COLOUR_LINE:
				oldValue = shape.getLineColour();
				shape.setLineColour((Color)obj);
				break;
			case COLOUR_SHADOW:
				oldValue = shape.getShadowCol();
				shape.setShadowCol((Color)obj);
				break;
			case DBLE_BORDERS:
				oldValue = shape.hasDbleBord();
				shape.setHasDbleBord((Boolean)obj);
				break;
			case DOT_SIZE:
				oldValue = ((Dottable)shape).getRadius();
				((Dottable)shape).setRadius((Double)obj);
				break;
			case DOT_STYLE:
				oldValue = ((Dottable)shape).getDotStyle();
				((Dottable)shape).setDotStyle((DotStyle)obj);
				break;
			case FILLING_STYLE:
				oldValue = shape.getFillingStyle();
				shape.setFillingStyle((FillingStyle)obj);
				break;
			case LINE_STYLE:
				oldValue = shape.getLineStyle();
				shape.setLineStyle((LineStyle)obj);
				break;
			case LINE_THICKNESS:
				oldValue = shape.getThickness();
				shape.setThickness((Double)obj);
				break;
			case ROUND_CORNER:
				oldValue = ((ILineArcShape)shape).isRoundCorner();
				((ILineArcShape)shape).setRoundCorner((Boolean)obj);
				break;
			case ROUND_CORNER_VALUE:
				oldValue = ((ILineArcShape)shape).getLineArc();
				((ILineArcShape)shape).setLineArc((Double)obj);
				break;
			case SHADOW:
				oldValue = shape.hasShadow();
				shape.setHasShadow((Boolean)obj);
				break;
			case DBLE_BORDERS_SIZE:
				oldValue = shape.getDbleBordSep();
				shape.setDbleBordSep((Double)obj);
				break;
			case SHADOW_ANGLE:
				oldValue = shape.getShadowAngle();
				shape.setShadowAngle((Double)obj);
				break;
			case SHADOW_SIZE:
				oldValue = shape.getShadowSize();
				shape.setShadowSize((Double)obj);
				break;
			case GRAD_ANGLE:
				oldValue = shape.getGradAngle();
				shape.setGradAngle((Double)obj);
				break;
			case GRAD_MID_POINT:
				oldValue = shape.getGradMidPt();
				shape.setGradMidPt((Double)obj);
				break;
			case HATCHINGS_ANGLE:
				oldValue = shape.getHatchingsAngle();
				shape.setHatchingsAngle((Double)obj);
				break;
			case HATCHINGS_SEP:
				oldValue = shape.getHatchingsSep();
				shape.setHatchingsSep((Double)obj);
				break;
			case HATCHINGS_WIDTH:
				oldValue = shape.getHatchingsWidth();
				shape.setHatchingsWidth((Double)obj);
				break;
			case TEXT_POSITION:
				final IText text = (IText)shape;
				oldValue = text.getTextPosition();
				text.setTextPosition((TextPosition)obj);
				break;
			case ROTATION_ANGLE:
				oldValue = shape.getRotationAngle();
				shape.setRotationAngle((Double)obj);
				break;
			case ARROW1_STYLE:
				oldValue = shape.getArrowStyle(0);
				shape.setArrowStyle((ArrowStyle)obj, 0);
				break;
			case ARROW2_STYLE:
				oldValue = shape.getArrowStyle(1);
				shape.setArrowStyle((ArrowStyle)obj, 1);
				break;
		}

		shape.setModified(true);
	}



	@Override
	protected void doActionBody() {
		applyValue(value);
	}


	/**
	 * Sets the shape to modify.
	 * @param sh The shape to modify.
	 * @since 3.0
	 */
	public void setShape(final IShape sh) {
		this.shape = sh;
	}



	@Override
	protected boolean isPropertySupported() {
		if(super.isPropertySupported())
			switch(property) {
				case ARROW_END_STYLE:   return shape.isArrowable();
				case ARROW_START_STYLE: return shape.isArrowable();
				case BORDER_POS:		return shape.isBordersMovable();
				case COLOUR_DBLE_BORD:	return shape.isDbleBorderable();
				case COLOUR_FILLING:	return shape.isFillable();
				case COLOUR_GRADIENT_END:	return shape.isInteriorStylable();
				case COLOUR_GRADIENT_START:	return shape.isInteriorStylable();
				case COLOUR_HATCHINGS:		return shape.isInteriorStylable();
				case COLOUR_LINE:		return true;
				case COLOUR_SHADOW:		return shape.isShadowable();
				case DBLE_BORDERS:		return shape.isDbleBorderable();
				case DOT_SIZE:			return shape instanceof Dottable;
				case DOT_STYLE:			return shape instanceof Dottable;
				case FILLING_STYLE:		return shape.isInteriorStylable();
				case LINE_STYLE:		return shape.isLineStylable();
				case LINE_THICKNESS:	return shape.isThicknessable();
				case ROUND_CORNER:		return shape instanceof ILineArcShape;
				case ROUND_CORNER_VALUE:	return shape instanceof ILineArcShape;
				case SHADOW:			return shape.isShadowable();
				case DBLE_BORDERS_SIZE:	return shape.isDbleBorderable();
				case SHADOW_ANGLE:		return shape.isShadowable();
				case SHADOW_SIZE:		return shape.isShadowable();
				case GRAD_ANGLE:		return shape.isInteriorStylable();
				case GRAD_MID_POINT:	return shape.isInteriorStylable();
				case HATCHINGS_ANGLE:	return shape.isInteriorStylable();
				case HATCHINGS_SEP:		return shape.isInteriorStylable();
				case HATCHINGS_WIDTH:	return shape.isInteriorStylable();
				case TEXT_POSITION:		return shape instanceof IText;
				case ROTATION_ANGLE:	return true;
				case ARROW1_STYLE:		return shape.isArrowable();
				case ARROW2_STYLE:		return shape.isArrowable();
			}

		return false;
	}
}
