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
package net.sf.latexdraw.commands.shape;

import java.util.List;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.prop.IClosableProp;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.prop.IScalable;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.malai.undo.Undoable;

/**
 * This command modifies a shape property of the given shape.
 * @author Arnaud Blouin
 */
public class ModifyShapeProperty extends ShapePropertyCmd implements Undoable, Modifying {
	/** The shape to modify. */
	private IGroup shapes;

	/** The old value of the property. */
	private List<?> oldValue;

	public ModifyShapeProperty(final ShapeProperties property, final IGroup shapes, final Object value) {
		super(property, value);
		this.shapes = shapes;
	}

	@Override
	public void flush() {
		super.flush();

		if(shapes != null) {
			shapes.clear();
			shapes = null;
		}

		if(oldValue != null) {
			oldValue.clear();
			oldValue = null;
		}
	}


	@Override
	public boolean canDo() {
		return super.canDo() && shapes != null;
	}


	@Override
	public void undo() {
		property.setPropertyValueList(shapes, oldValue);
		shapes.setModified(true);
	}


	@Override
	public void redo() {
		applyValue(value);
	}


	@Override
	public String getUndoName() {
		return property == null ? "" : property.getMessage(); //NON-NLS
	}


	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}


	@Override
	protected void applyValue(final Object obj) {
		property.setPropertyValue(shapes, obj);
		shapes.setModified(true);
	}


	@Override
	protected void doCmdBody() {
		if(oldValue == null) {
			oldValue = property.getPropertyValues(shapes);
		}
		applyValue(value);
	}

	public IGroup getShapes() {
		return shapes;
	}

	@Override
	protected boolean isPropertySupported() {
		if(shapes != null && super.isPropertySupported()) {
			switch(property) {
				case SHOW_POINTS:
					return shapes.isShowPtsable();
				case ARROW_BRACKET_NUM:
				case ARROW_DOT_SIZE_DIM:
				case ARROW_DOT_SIZE_NUM:
				case ARROW_R_BRACKET_NUM:
				case ARROW_SIZE_DIM:
				case ARROW_SIZE_NUM:
				case ARROW_T_BAR_SIZE_DIM:
				case ARROW_T_BAR_SIZE_NUM:
				case ARROW_INSET:
				case ARROW_LENGTH:
				case ARROW1_STYLE:
				case ARROW2_STYLE:
					return shapes.isTypeOf(IArrowableSingleShape.class);
				case BORDER_POS:
					return shapes.isBordersMovable();
				case COLOUR_FILLING:
					return shapes.isFillable();
				case COLOUR_GRADIENT_END:
				case COLOUR_GRADIENT_START:
				case LINE_STYLE:
					return shapes.isLineStylable();
				case LINE_THICKNESS:
					return shapes.isThicknessable();
				case DBLE_BORDERS_SIZE:
				case DBLE_BORDERS:
				case COLOUR_DBLE_BORD:
					return shapes.isDbleBorderable();
				case SHADOW_ANGLE:
				case SHADOW_SIZE:
				case SHADOW_COLOUR:
				case SHADOW:
					return shapes.isShadowable();
				case GRAD_ANGLE:
				case GRAD_MID_POINT:
				case HATCHINGS_ANGLE:
				case HATCHINGS_SEP:
				case HATCHINGS_WIDTH:
				case COLOUR_HATCHINGS:
				case FILLING_STYLE:
					return shapes.isInteriorStylable();
				case GRID_START:
				case GRID_END:
				case GRID_LABEL_POSITION_Y:
				case GRID_LABEL_POSITION_X:
				case GRID_SIZE_LABEL:
				case GRID_ORIGIN:
					return shapes.isTypeOf(IStdGridProp.class);
				case ARC_END_ANGLE:
				case ARC_START_ANGLE:
				case ARC_STYLE:
					return shapes.isTypeOf(IArcProp.class);
				case ROUND_CORNER_VALUE:
					return shapes.isTypeOf(ILineArcProp.class);
				case DOT_SIZE:
				case DOT_FILLING_COL:
				case DOT_STYLE:
					return shapes.isTypeOf(IDotProp.class);
				case TEXT:
				case TEXT_POSITION:
					return shapes.isTypeOf(ITextProp.class);
				case COLOUR_LINE:
					return true;
				case AXES_TICKS_STYLE:
				case AXES_TICKS_SHOW:
				case AXES_LABELS_INCR:
				case AXES_LABELS_SHOW:
				case AXES_SHOW_ORIGIN:
				case AXES_LABELS_DIST:
				case AXES_STYLE:
					return shapes.isTypeOf(IAxesProp.class);
				case GRID_SUBGRID_COLOUR:
				case GRID_SUBGRID_WIDTH:
				case GRID_WIDTH:
				case GRID_DOTS:
				case GRID_SUBGRID_DOTS:
				case GRID_SUBGRID_DIV:
				case GRID_LABELS_COLOUR:
					return shapes.isTypeOf(IGridProp.class);
				case CLOSABLE_CLOSE:
					return shapes.isTypeOf(IClosableProp.class);
				case FREEHAND_INTERVAL:
				case FREEHAND_STYLE:
					return shapes.isTypeOf(IFreeHandProp.class);
				case PLOT_EQ:
				case PLOT_STYLE:
				case PLOT_POLAR:
				case PLOT_NB_PTS:
				case PLOT_MAX_X:
				case PLOT_MIN_X:
					return shapes.isTypeOf(IPlotProp.class);
				case X_SCALE:
				case Y_SCALE:
					return shapes.isTypeOf(IScalable.class);
			}
		}

		return false;
	}
}
