package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.instruments.TextSetter;

import org.malai.action.library.ActivateInstrument;

/**
 * This action activates and moves the text setter to a given position.<br>
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
 * 20/12/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class InitTextSetter extends ActivateInstrument {
	/** The text setter to move. */
	protected TextSetter setter;

	/** The text to set to the setter. */
	protected String text;

	/** The position that takes account of the zoom. */
	protected IPoint relativePoint;

	/** The position that does not taks account of the zoom (for the text field). */
	protected IPoint absolutePoint;

	/** The text (shape) to modify throw the setter. Can be null. */
	protected IText textShape;


	@Override
	public void flush() {
		super.flush();
		text		  = null;
		textShape	  = null;
		setter 		  = null;
		relativePoint = null;
		absolutePoint = null;
	}


	/**
	 * Sets the text shape to modify.
	 * @param textShape The text (shape) to modify throw the setter. Can be null.
	 * @since 3.0
	 */
	public void setTextShape(final IText textShape) {
		this.textShape = textShape;
	}


	/**
	 * Sets the text to display into the text setter.
	 * @param text The text to set.
	 * @since 3.0
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * @param textSetter the textSetter to set.
	 * @since 3.0
	 */
	public void setTextSetter(final TextSetter textSetter) {
		setter = textSetter;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * @param pt The position that takes account of the zoom.
	 * @since 3.0
	 */
	public void setRelativePoint(final IPoint pt) {
		relativePoint = pt;
	}

	/**
	 * @param pt The position that does not taks account of the zoom (for the text field).
	 * @since 3.0
	 */
	public void setAbsolutePoint(final IPoint pt) {
		absolutePoint = pt;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && GLibUtilities.INSTANCE.isValidPoint(absolutePoint) &&
				GLibUtilities.INSTANCE.isValidPoint(relativePoint) && setter!=null && (text!=null || textShape!=null);
	}


	@Override
	protected void doActionBody() {
		super.doActionBody();
		setter.getTextField().setLocation((int)absolutePoint.getX(), (int)absolutePoint.getY()-setter.getTextField().getHeight());
		setter.setRelativePoint(relativePoint);
		setter.getTextField().setText(text);
		setter.setText(textShape);
	}
}
