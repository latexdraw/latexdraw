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
package net.sf.latexdraw.command.shape;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.api.shape.Group;
import org.malai.undo.Undoable;

/**
 * This command modifies a shape property of the given shape.
 * @author Arnaud Blouin
 */
public class ModifyShapeProperty<T> extends ShapePropertyCmd<T> implements Undoable, Modifying {
	/** The shape to modify. */
	private final Group shapes;

	/** The old value of the property. */
	private List<Optional<T>> oldValue;

	public ModifyShapeProperty(final ShapeProperties<T> property, final Group shapes, final T value) {
		super(property, value);
		this.shapes = shapes;
	}

	@Override
	public void flush() {
		super.flush();

		if(shapes != null) {
			shapes.clear();
		}

		if(oldValue != null) {
			oldValue.clear();
		}
	}


	@Override
	public boolean canDo() {
		return shapes != null && super.canDo();
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
	public String getUndoName(final ResourceBundle bundle) {
		return property == null ? "" : property.getMessage(bundle); //NON-NLS
	}


	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}


	@Override
	protected void applyValue(final T obj) {
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

	public Group getShapes() {
		return shapes;
	}

	@Override
	protected boolean isPropertySupported() {
		return super.isPropertySupported() && property.accept(shapes);
	}
}
