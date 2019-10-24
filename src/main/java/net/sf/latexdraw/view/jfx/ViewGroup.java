/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.stream.Collectors;
import net.sf.latexdraw.model.api.shape.Group;

/**
 * The JFX view of a group of shapes.
 * @author Arnaud Blouin
 */
public class ViewGroup extends ViewShape<Group> {
	private final javafx.scene.Group group;

	/**
	 * Creates the view.
	 * @param gp The model.
	 */
	ViewGroup(final Group gp, final JfxViewProducer viewProducer) {
		super(gp);
		group = new javafx.scene.Group();
		group.getChildren().addAll(model.getShapes().stream().map(sh -> viewProducer.createView(sh).get()).collect(Collectors.toList()));
		getChildren().add(group);
	}

	@Override
	public void flush() {
		super.flush();
		group.getChildren().forEach(ch -> ((ViewShape<?>) ch).flush());
		group.getChildren().clear();
	}
}
