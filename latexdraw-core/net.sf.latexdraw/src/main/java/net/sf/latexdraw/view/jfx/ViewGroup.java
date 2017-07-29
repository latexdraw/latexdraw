/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.stream.Collectors;
import javafx.scene.Group;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

/**
 * The JFX view of a group of shapes.
 * @author Arnaud Blouin
 */
public class ViewGroup extends ViewShape<IGroup> {
	private final Group group;

	/**
	 * Creates the view.
	 * @param gp The model.
	 */
	ViewGroup(final  IGroup gp) {
		super(gp);
		group = new Group();
		group.getChildren().addAll(model.getShapes().stream().map(sh -> ViewFactory.INSTANCE.createView(sh).get()).collect(Collectors.toList()));
		getChildren().add(group);
	}

	@Override
	public void flush() {
		super.flush();
		group.getChildren().forEach(ch -> ((ViewShape<?>)ch).flush());
		group.getChildren().clear();
	}
}
