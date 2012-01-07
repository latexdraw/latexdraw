package net.sf.latexdraw.mapping;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.malai.mapping.IUnary;
import org.malai.mapping.MappingRegistry;
import org.malai.mapping.Unary2UnaryMapping;

/**
 * Defines a mapping that link the temporary shape of the drawing to the
 * temporary view of the canvas.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
 * 05/15/10<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 */
public class TempShape2TempViewMapping extends Unary2UnaryMapping<IShape, IViewShape> {
	/**
	 * {@link Unary2UnaryMapping#Unary2UnaryMapping(IUnary, IUnary)}
	 */
	public TempShape2TempViewMapping(final IUnary<IShape> source, final IUnary<IViewShape> target) {
		super(source, target);
	}



	@Override
	public void onObjectModified(final Object object) {
		// Nothing to do.
	}



	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		if(replacedObject!=null) {
			MappingRegistry.REGISTRY.removeMappingsUsingSource(replacedObject);
			targetObject.getValue().flush();
			targetObject.setValue(null);
		}

		if(sourceObject.getValue()!=null) {
			targetObject.setValue(View2DTK.getFactory().createView(sourceObject.getValue()));
			MappingRegistry.REGISTRY.addMapping(new Shape2ViewMapping(sourceObject.getValue(), targetObject.getValue()));
		}
	}
}
