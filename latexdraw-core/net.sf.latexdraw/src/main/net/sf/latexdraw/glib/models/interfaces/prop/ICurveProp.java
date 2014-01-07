package net.sf.latexdraw.glib.models.interfaces.prop;

/**
 * Curves' properties.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 */
public interface ICurveProp {
	/** The different styles of a curve. */
	public static enum CurveStyle {CURVE, ECURVE, CCURVE}

	/**
	 * @return the style of the curve.
	 */
	CurveStyle getStyle();

	/**
	 * @param style the style to set.
	 */
	void setStyle(final CurveStyle style);
}
