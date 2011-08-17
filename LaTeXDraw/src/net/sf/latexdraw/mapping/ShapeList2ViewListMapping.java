package net.sf.latexdraw.mapping;

import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.views.Java2D.IShapeView;
import net.sf.latexdraw.glib.views.Java2D.LTextView;
import net.sf.latexdraw.glib.views.Java2D.LViewsFactory;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;

import org.malai.mapping.MappingRegistry;
import org.malai.mapping.SymmetricList2ListMapping;

/**
 * Defines a mapping that link a list of IShape to a list of IShapeView.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class ShapeList2ViewListMapping extends SymmetricList2ListMapping<IShape, IShapeView<?>> {
	/**
	 * {@link SymmetricList2ListMapping#SymmetricList2ListMapping(List, List)}
	 */
	public ShapeList2ViewListMapping(final List<IShape> source, final List<IShapeView<?>> target) {
		super(source, target);
	}



	@Override
	protected IShapeView<?> createTargetObject(final Object sourceObject) {
		return sourceObject instanceof IShape ? LViewsFactory.INSTANCE.generateView((IShape)sourceObject) : null;
	}



	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		super.onObjectAdded(list, object, index);

		if(object instanceof IShape) {
			MappingRegistry.REGISTRY.addMapping(new Shape2ViewMapping((IShape)object, index==-1 ? target.get(target.size()-1) : target.get(index)));

			// If the shape is a text, a special mapping must be added.
			if(object instanceof IText)
				MappingRegistry.REGISTRY.addMapping(new Package2TextViewMapping(
						LaTeXGenerator.getPackagesSingleton(), (LTextView)(index==-1 ? target.get(target.size()-1) : target.get(index))));
		}
	}



	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		IShapeView<?> view = index==-1 ? target.get(target.size()-1) : target.get(index);

		view.flush();
		super.onObjectRemoved(list, object, index);
		MappingRegistry.REGISTRY.removeMappingsUsingSource(object, Shape2ViewMapping.class);

		// If the shape is a text, the special mapping previously added must be removed.
		if(object instanceof IText)
			MappingRegistry.REGISTRY.removeMappingsUsingTarget(view, Package2TextViewMapping.class);
	}


	@Override
	public void onListCleaned(final Object list) {
		super.onListCleaned(list);

		for(IShape shape : source)
			MappingRegistry.REGISTRY.removeMappingsUsingSource(shape, Shape2ViewMapping.class);
	}
}
