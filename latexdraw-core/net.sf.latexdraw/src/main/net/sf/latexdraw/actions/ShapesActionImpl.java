/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.action.ActionImpl;

/**
 * This trait encapsulates a set of shapes attribute.
 */
public abstract class ShapesActionImpl extends ActionImpl implements ShapesAction {
	/** The shapes to handle. */
	final List<IShape> shapes;

	protected ShapesActionImpl() {
		super();
		shapes = new ArrayList<>();
	}

	@Override
	public List<IShape> getShapes() {
		return shapes;
	}
}
