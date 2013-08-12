package net.sf.latexdraw.mapping;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.ui.ICanvas;

import org.malai.mapping.Object2ObjectMapping;

/**
 * Defines a mapping that link an IDrawing to a ICanvas.<br>Qvas> {
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 05/16/10<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 */
public class Drawing2CanvasMapping extends Object2ObjectMapping<IDrawing, ICanvas> {
	/**
	 * {@link Object2ObjectMapping#Object2ObjectMapping(Object, Object)}
	 */
	public Drawing2CanvasMapping(final IDrawing source, final ICanvas target) {
		super(source, target);
	}



	@Override
	public void onObjectModified(final Object object) {
		targetObject.update();
	}
}
