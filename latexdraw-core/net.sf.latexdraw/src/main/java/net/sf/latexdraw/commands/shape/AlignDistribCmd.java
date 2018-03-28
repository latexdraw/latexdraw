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

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.commands.ShapeCmdImpl;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.malai.undo.Undoable;

/**
 * Factorises code between align and distribute commands.
 * @author Arnaud Blouin
 */
abstract class AlignDistribCmd extends ShapeCmdImpl<IGroup> implements Undoable, Modifying {
	/** The former positions of the shapes to align. Used for undoing. */
	List<IPoint> oldPositions;

	@Override
	public void undo() {
		final IntegerProperty pos = new SimpleIntegerProperty(0);

		shape.ifPresent(gp -> {
			gp.getShapes().forEach(sh -> {
				// Reusing the old position.
				final IPoint pt = sh.getTopLeftPoint();
				final IPoint oldPt = oldPositions.get(pos.get());
				if(!pt.equals(oldPt)) {
					sh.translate(oldPt.getX() - pt.getX(), oldPt.getY() - pt.getY());
				}
				pos.set(pos.get() + 1);
			});
			gp.setModified(true);
		});
	}
}
