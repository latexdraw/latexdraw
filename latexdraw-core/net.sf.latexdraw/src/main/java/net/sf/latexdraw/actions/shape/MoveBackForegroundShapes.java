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
package net.sf.latexdraw.actions.shape;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.actions.DrawingAction;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This action puts in background / foreground shapes.
 * @author Arnaud Blouin
 */
public class MoveBackForegroundShapes extends ShapeActionImpl<IGroup> implements DrawingAction, Undoable, Modifying {
	/** Defines whether the shapes must be placed in the foreground. */
	private boolean foreground;
	/** The former position of the shapes. */
	private int[] formerId;
	/** The shapes sorted by their position. */
	private List<IShape> sortedSh;
	/** The drawing that will be handled by the action. */
	private Optional<IDrawing> drawing;

	public MoveBackForegroundShapes() {
		super();
		drawing = Optional.empty();
		foreground = false;
	}

	@Override
	protected void doActionBody() {
		if(foreground) {
			moveForeground();
		}else {
			moveBackground();
		}
	}

	/** Puts the shapes in the foreground. */
	private void moveForeground() {
		shape.ifPresent(gp -> {
			final int size = gp.size();
			formerId = new int[size];
			drawing.ifPresent(dr -> {
				final List<IShape> drshapes = dr.getShapes();
				sortedSh = gp.getShapes().stream().sorted((a, b) -> drshapes.indexOf(a) < drshapes.indexOf(b) ? -1 : 1).collect(Collectors.toList());

				for(int i = 0; i < size; i++) {
					IShape sh = sortedSh.get(i);
					formerId[i] = drshapes.indexOf(sh);
					dr.removeShape(sh);
					dr.addShape(sh);
				}
				dr.setModified(true);
			});
		});
	}


	/** Puts the shapes in the background. */
	private void moveBackground() {
		shape.ifPresent(gp -> {
			final int size = gp.size();
			formerId = new int[size];
			drawing.ifPresent(dr -> {
				final List<IShape> drshapes = dr.getShapes();
				sortedSh = gp.getShapes().stream().sorted((a, b) -> drshapes.indexOf(a) < drshapes.indexOf(b) ? -1 : 1).collect(Collectors.toList());

				for(int i = size - 1; i >= 0; i--) {
					IShape sh = sortedSh.get(i);
					formerId[i] = drshapes.indexOf(sh);
					dr.removeShape(sh);
					dr.addShape(sh, 0);
				}
				dr.setModified(true);
			});
		});
	}

	@Override
	public boolean canDo() {
		return super.canDo() && shape.isPresent() && !shape.get().isEmpty() && drawing.isPresent();
	}

	@Override
	public void undo() {
		drawing.ifPresent(dr -> {
			if(foreground) {
				final IntegerProperty i = new SimpleIntegerProperty(formerId.length - 1);
				sortedSh.stream().sorted(Collections.reverseOrder()).forEach(sh -> {
					dr.removeShape(sh);
					dr.addShape(sh, formerId[i.get()]);
					i.set(i.get() - 1);
				});
			}else {
				final IntegerProperty i = new SimpleIntegerProperty(0);
				sortedSh.forEach(sh -> {
					dr.removeShape(sh);
					dr.addShape(sh, formerId[i.get()]);
					i.set(i.get() + 1);
				});
			}

			dr.setModified(true);
		});
	}

	@Override
	public void redo() {
		doActionBody();
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.8");
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}

	/** Defines whether the shapes must be placed in the foreground. */
	public void setIsForeground(final boolean fore) {
		foreground = fore;
	}

	@Override
	public void setDrawing(final IDrawing dr) {
		drawing = Optional.ofNullable(dr);
	}

	@Override
	public Optional<IDrawing> getDrawing() {
		return drawing;
	}
}
