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

import io.github.interacto.undo.Undoable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.api.shape.Group;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This command modifies a shape property of the given shape.
 * @author Arnaud Blouin
 */
public class ModifyShapeProperty<T> extends ShapePropertyCmd<T> implements Undoable, Modifying {
	/** The shape to modify. */
	private final @NotNull Group shapes;

	/** The old value of the property. */
	private @NotNull List<Optional<T>> oldValue;

	public ModifyShapeProperty(final @NotNull ShapeProperties<T> property, final @NotNull Group shapes, final @Nullable T value) {
		super(property, value);
		this.shapes = shapes;
		oldValue = Collections.emptyList();
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
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return property.getMessage(bundle);
	}


	@Override
	protected void applyValue(final @NotNull T obj) {
		property.setPropertyValue(shapes, obj);
		shapes.setModified(true);
	}


	@Override
	protected void doCmdBody() {
		oldValue = property.getPropertyValues(shapes);
		applyValue(value);
	}

	public @NotNull Group getShapes() {
		return shapes;
	}

	@Override
	protected boolean isPropertySupported() {
		return super.isPropertySupported() && property.accept(shapes);
	}
}
