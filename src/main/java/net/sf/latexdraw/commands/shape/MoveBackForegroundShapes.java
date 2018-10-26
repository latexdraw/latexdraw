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
package net.sf.latexdraw.commands.shape;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.commands.ShapeCmdImpl;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.undo.Undoable;

/**
 * This command puts in background / foreground shapes.
 * @author Arnaud Blouin
 */
public class MoveBackForegroundShapes extends ShapeCmdImpl<IGroup> implements Undoable, Modifying {
	/** Defines whether the shapes must be placed in the foreground. */
	private final boolean foreground;
	/** The drawing that will be handled by the command. */
	private final IDrawing drawing;
	/** The former position of the shapes. */
	private int[] formerId;
	/** The shapes sorted by their position. */
	private List<IShape> sortedSh;

	public MoveBackForegroundShapes(final IGroup gp, final boolean foreground, final IDrawing drawing) {
		super(gp);
		this.drawing = drawing;
		this.foreground = foreground;
	}

	@Override
	protected void doCmdBody() {
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
			final List<IShape> drshapes = drawing.getShapes();
			sortedSh = gp.getShapes().stream().sorted((a, b) -> drshapes.indexOf(a) < drshapes.indexOf(b) ? -1 : 1).collect(Collectors.toList());

			for(int i = 0; i < size; i++) {
				final IShape sh = sortedSh.get(i);
				formerId[i] = drshapes.indexOf(sh);
				drawing.removeShape(sh);
				drawing.addShape(sh);
			}
			drawing.setModified(true);
		});
	}


	/** Puts the shapes in the background. */
	private void moveBackground() {
		shape.ifPresent(gp -> {
			final int size = gp.size();
			formerId = new int[size];
			final List<IShape> drshapes = drawing.getShapes();
			sortedSh = gp.getShapes().stream().sorted((a, b) -> drshapes.indexOf(a) < drshapes.indexOf(b) ? -1 : 1).collect(Collectors.toList());

			for(int i = size - 1; i >= 0; i--) {
				final IShape sh = sortedSh.get(i);
				formerId[i] = drshapes.indexOf(sh);
				drawing.removeShape(sh);
				drawing.addShape(sh, 0);
			}
			drawing.setModified(true);
		});
	}

	@Override
	public boolean canDo() {
		return drawing != null && shape.isPresent() && !shape.get().isEmpty() && super.canDo();
	}

	@Override
	public void undo() {
		if(foreground) {
			final IntegerProperty i = new SimpleIntegerProperty(formerId.length - 1);
			sortedSh.stream().sorted(Collections.reverseOrder()).forEach(sh -> {
				drawing.removeShape(sh);
				drawing.addShape(sh, formerId[i.get()]);
				i.set(i.get() - 1);
			});
		}else {
			final IntegerProperty i = new SimpleIntegerProperty(0);
			sortedSh.forEach(sh -> {
				drawing.removeShape(sh);
				drawing.addShape(sh, formerId[i.get()]);
				i.set(i.get() + 1);
			});
		}

		drawing.setModified(true);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.8");
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}
}
