package net.sf.latexdraw.actions;

import org.malai.action.Action;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.instruments.TextSetter;

/**
 * This action moves the text setter to a given position.<br>
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
 * 20/12/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class SetTextSetterPosition extends Action {
	/** The text setter to move. */
	protected TextSetter setter;

	/** The position that takes account of the zoom. */
	protected IPoint relativePoint;

	/** The position that does not taks account of the zoom (for the text field). */
	protected IPoint absolutePoint;


	/**
	 * Creates the action.
	 */
	public SetTextSetterPosition() {
		super();

		relativePoint = null;
		absolutePoint = null;
	}


	@Override
	public void flush() {
		super.flush();
		setter 		  = null;
		relativePoint = null;
		absolutePoint = null;
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
		return GLibUtilities.INSTANCE.isValidPoint(absolutePoint) && GLibUtilities.INSTANCE.isValidPoint(relativePoint) && setter!=null;
	}


	@Override
	protected void doActionBody() {
		setter.getTextField().setLocation((int)absolutePoint.getX(), (int)absolutePoint.getY()-setter.getTextField().getHeight());
		setter.setRelativePoint(relativePoint);
	}
}
