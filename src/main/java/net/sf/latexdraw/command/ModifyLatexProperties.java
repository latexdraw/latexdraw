/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import io.github.interacto.command.CommandImpl;
import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.jetbrains.annotations.NotNull;

/**
 * This command modifies the latex properties of the current drawing.
 * @author Arnaud Blouin
 */
public class ModifyLatexProperties extends CommandImpl implements Undoable, Modifying {
	/** The new value to set. */
	private Object value;
	/** The saved value used for undo/redo. */
	private Object oldValue;
	/** The property to modify. */
	private final @NotNull LatexProperties property;
	/** The LaTeX data to modify. */
	private final @NotNull LaTeXDataService data;


	public ModifyLatexProperties(final @NotNull LaTeXDataService data, final @NotNull LatexProperties property, final Object value) {
		super();
		this.data = data;
		this.property = property;
		this.value = value;
	}

	@Override
	protected void doCmdBody() {
		oldValue = switch(property) {
			case SCALE -> data.getScale();
			case CAPTION -> data.getCaption();
			case COMMENT -> data.getComment();
			case LABEL -> data.getLabel();
			case PACKAGES -> data.getPackages();
			case POSITION_HORIZONTAL -> data.isPositionHoriCentre();
			case POSITION_VERTICAL -> data.getPositionVertToken();
		};

		applyValue(value);
	}

	private void applyValue(final Object object) {
		switch(property) {
			case SCALE -> data.setScale((Double) object);
			case CAPTION -> data.setCaption((String) object);
			case COMMENT -> data.setComment((String) object);
			case LABEL -> data.setLabel((String) object);
			case PACKAGES -> data.setPackages((String) object);
			case POSITION_HORIZONTAL -> data.setPositionHoriCentre((Boolean) object);
			case POSITION_VERTICAL -> data.setPositionVertToken((VerticalPosition) object);
		}
	}

	@Override
	public boolean canDo() {
		return property.isValueSupported(value);
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
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("drawingProps");
	}

	/**
	 * @param val The new val to set.
	 */
	public void setValue(final Object val) {
		value = val;
	}
}
