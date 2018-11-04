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
package net.sf.latexdraw.commands;

import java.util.ResourceBundle;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.malai.command.CommandImpl;
import org.malai.undo.Undoable;

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
	private final LatexProperties property;
	/** The LaTeX generator to modify. */
	private final LaTeXGenerator generator;


	public ModifyLatexProperties(final LaTeXGenerator generator, final LatexProperties property, final Object value) {
		super();
		this.generator = generator;
		this.property = property;
		this.value = value;
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}

	@Override
	protected void doCmdBody() {
		switch(property) {
			case SCALE:
				oldValue = generator.getScale();
				break;
			case CAPTION:
				oldValue = generator.getCaption();
				break;
			case COMMENT:
				oldValue = generator.getComment();
				break;
			case LABEL:
				oldValue = generator.getLabel();
				break;
			case PACKAGES:
				oldValue = generator.getPackages();
				break;
			case POSITION_HORIZONTAL:
				oldValue = generator.isPositionHoriCentre();
				break;
			case POSITION_VERTICAL:
				oldValue = generator.getPositionVertToken();
				break;
		}

		applyValue(value);
	}

	private void applyValue(final Object object) {
		switch(property) {
			case SCALE:
				generator.setScale((Double) object);
				break;
			case CAPTION:
				generator.setCaption((String) object);
				break;
			case COMMENT:
				generator.setComment((String) object);
				break;
			case LABEL:
				generator.setLabel((String) object);
				break;
			case PACKAGES:
				generator.setPackages((String) object);
				break;
			case POSITION_HORIZONTAL:
				generator.setPositionHoriCentre((Boolean) object);
				break;
			case POSITION_VERTICAL:
				generator.setPositionVertToken((VerticalPosition) object);
				break;
		}
	}

	@Override
	public boolean canDo() {
		// packages does not require the generator since it is a static attribute.
		return property != null && property.isValueSupported(value) && (generator != null || property == LatexProperties.PACKAGES);
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
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.0");
	}

	/**
	 * @param val The new val to set.
	 */
	public void setValue(final Object val) {
		value = val;
	}
}
