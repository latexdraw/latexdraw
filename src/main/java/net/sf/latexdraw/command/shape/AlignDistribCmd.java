/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command.shape;

import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.jetbrains.annotations.NotNull;
import org.malai.undo.Undoable;

/**
 * Factorises code between align and distribute commands.
 * @author Arnaud Blouin
 */
abstract class AlignDistribCmd extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** The former positions of the shapes to align. Used for undoing. */
	protected List<Point> oldPositions;
	/** The views corresponding to the shapes to align. */
	protected List<ViewShape<?>> views;
	protected final @NotNull Canvas canvas;

	AlignDistribCmd(final @NotNull Canvas canvas, final @NotNull Group gp) {
		super(gp);
		this.canvas = canvas;
	}

	@Override
	public void undo() {
		final IntegerProperty pos = new SimpleIntegerProperty(0);

		shape.getShapes().forEach(sh -> {
			// Reusing the old position.
			final Point pt = sh.getTopLeftPoint();
			final Point oldPt = oldPositions.get(pos.get());
			if(!pt.equals(oldPt)) {
				sh.translate(oldPt.getX() - pt.getX(), oldPt.getY() - pt.getY());
			}
			pos.set(pos.get() + 1);
		});
		shape.setModified(true);
	}

	@Override
	protected void doCmdBody() {
		views = shape.getShapes().stream().map(sh -> canvas.getViewFromShape(sh)).filter(opt -> opt.isPresent()).map(opt -> opt.get()).collect(Collectors.<ViewShape<?>>toList());
		oldPositions = shape.getShapes().stream().map(sh -> sh.getTopLeftPoint()).collect(Collectors.toList());
		redo();
	}

	@Override
	public @NotNull RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}
}
