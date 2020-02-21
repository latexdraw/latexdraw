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
package net.sf.latexdraw.command.shape;

import io.github.interacto.undo.Undoable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.jetbrains.annotations.NotNull;

/**
 * This command updates the given shapes to magnetic grid if activated.
 * @author Arnaud Blouin
 */
public class UpdateToGrid extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** The magnetic grid to use. */
	private final @NotNull MagneticGrid grid;
	private List<List<Point>> listPts;
	private boolean mementoModified;

	public UpdateToGrid(final @NotNull MagneticGrid grid, final @NotNull Group gp) {
		super(gp);
		listPts = new ArrayList<>();
		this.grid = grid;
	}

	@Override
	protected void createMemento() {
		mementoModified = shape.isModified();
		listPts = shape
			.getShapes()
			.stream()
			.map(sh -> sh
				.getPoints()
				.stream()
				.map(pt -> ShapeFactory.INST.createPoint(pt))
				.collect(Collectors.toList()))
			.collect(Collectors.toList());
	}

	@Override
	protected void doCmdBody() {
		shape.getShapes().forEach(sh -> {
			sh.getPoints().forEach(pt -> {
				System.out.println(pt.toPoint3D());
				pt.setPoint(grid.getTransformedPointToGrid(pt.toPoint3D()));
			});
		});
		shape.setModified(true);
	}

	@Override
	public void undo() {
		final AtomicInteger i = new AtomicInteger();
		final AtomicInteger j = new AtomicInteger();

		shape.getShapes().forEach(sh -> {
			j.set(0);
			sh.getPoints().forEach(pt -> {
				pt.setPoint(listPts.get(i.get()).get(j.get()).getX(), listPts.get(i.get()).get(j.get()).getY());
				j.incrementAndGet();
			});
			i.incrementAndGet();
		});
		shape.setModified(mementoModified);
	}


	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.33");
	}
}
