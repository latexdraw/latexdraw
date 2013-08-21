package net.sf.latexdraw.mapping;

import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText;

import org.malai.mapping.IUnary;
import org.malai.mapping.Object2ObjectMapping;

/**
 * This mapping maps the LaTeX packages, used for LaTeX compilation, with the text views. This kind of mapping
 * is necessary to update the text views when the packages are modified.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 08/17/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Package2TextViewMapping extends Object2ObjectMapping<IUnary<String>, IViewText> {
	/**
	 * Creates the mapping.
	 * @param packages The LaTeX packages to listen.
	 * @param textView The text view to update.
	 * @throws IllegalArgumentException If one of the given arguments is null or if they are the same object.
	 * @since 3.0
	 */
	public Package2TextViewMapping(final IUnary<String> packages, final IViewText textView) {
		super(packages, textView);
	}

	@Override
	public void onObjectModified(final Object object) {
		updateTarget();
	}


	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		updateTarget();
	}


	/**
	 * Updates the text view.
	 */
	private void updateTarget() {
		targetObject.updateImage();
		targetObject.updateBorder();
	}
}
