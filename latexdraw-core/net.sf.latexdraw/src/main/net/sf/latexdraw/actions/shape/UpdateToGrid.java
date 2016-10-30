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
package net.sf.latexdraw.actions.shape;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.undo.Undoable;

import java.util.ArrayList;
import java.util.List;

/**
 * This action updates the given shapes to magnetic grid if activated.
 */
public class UpdateToGrid extends ShapeActionImpl<IGroup> implements Undoable, Modifying {
	/** The magnetic grid to use. */
	MagneticGrid grid;

	final List<List<IPoint>> listPts;


	public UpdateToGrid() {
		super();
		listPts = new ArrayList<>();
	}

	@Override
	protected void doActionBody() {
		shape.ifPresent(gp -> {
			gp.getShapes().forEach(sh -> {
				List<IPoint> list = new ArrayList<>();
				listPts.add(list);
				sh.getPoints().forEach(pt -> list.add(ShapeFactory.createPoint(pt)));
			});
			redo();
		});
	}

	@Override
	public boolean canDo() {
		return super.canDo() && grid != null;
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
					pt.setPoint(grid.getTransformedPointToGrid(pt.toPoint2D()));
					j.set(j.get() + 1);
				});
				i.set(i.get() + 1);
				sh.setModified(true);
			});
		});
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getStringActions("Actions.33");
	}

	@Override
	public boolean isRegisterable() {
		return hadEffect();
	}

	/** Sets the magnetic grid to use. */
	public void setGrid(final MagneticGrid gr) {
		grid = gr;
	}
}
