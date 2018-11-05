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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.undo.Undoable;

/**
 * This command updates the given shapes to magnetic grid if activated.
 * @author Arnaud Blouin
 */
public class UpdateToGrid extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** The magnetic grid to use. */
	private final MagneticGrid grid;
	private final List<List<Point>> listPts;


	public UpdateToGrid(final MagneticGrid grid, final Group gp) {
		super(gp);
		listPts = new ArrayList<>();
		this.grid = grid;
	}

	@Override
	protected void doCmdBody() {
		shape.ifPresent(gp -> {
			gp.getShapes().forEach(sh -> {
				final List<Point> list = new ArrayList<>();
				listPts.add(list);
				sh.getPoints().forEach(pt -> list.add(ShapeFactory.INST.createPoint(pt)));
			});
			redo();
		});
	}

	@Override
	public boolean canDo() {
		return grid != null && super.canDo();
	}

	@Override
	public void undo() {
		shape.ifPresent(gp -> {
			final IntegerProperty i = new SimpleIntegerProperty();
			final IntegerProperty j = new SimpleIntegerProperty();

			gp.getShapes().forEach(sh -> {
				j.set(0);
				sh.getPoints().forEach(pt -> {
					pt.setPoint(listPts.get(i.get()).get(j.get()).getX(), listPts.get(i.get()).get(j.get()).getY());
					j.setValue(j.getValue() + 1);
				});
				i.set(i.get() + 1);
				sh.setModified(true);
			});
		});
	}


	@Override
	public void redo() {
		shape.ifPresent(gp -> {
			final IntegerProperty i = new SimpleIntegerProperty();
			final IntegerProperty j = new SimpleIntegerProperty();

			gp.getShapes().forEach(sh -> {
				j.set(0);
				sh.getPoints().forEach(pt -> {
					pt.setPoint(grid.getTransformedPointToGrid(pt.toPoint3D()));
					j.set(j.get() + 1);
				});
				i.set(i.get() + 1);
				sh.setModified(true);
			});
		});
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.33");
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}
}
