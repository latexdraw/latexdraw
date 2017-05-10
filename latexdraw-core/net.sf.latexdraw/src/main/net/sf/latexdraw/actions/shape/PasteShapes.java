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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.latexdraw.actions.DrawingActionImpl;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.action.Action;
import org.malai.undo.Undoable;

/**
 * This action pastes the copied or cut shapes.
 * @author Arnaud Blouin
 */
public class PasteShapes extends DrawingActionImpl implements Undoable, Modifying {
	/** The cut or copy action. */
	private CopyShapes copy;

	/** The magnetic grid to use. */
	private MagneticGrid grid;

	private final List<IShape> pastedShapes;


	public PasteShapes() {
		super();
		pastedShapes = new ArrayList<>();
	}

	@Override
	public boolean canDo() {
		return super.canDo() && copy != null && grid != null;
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	public void doActionBody() {
		drawing.ifPresent(dr -> {
			// While pasting cut shapes, the first paste must be at the same position that the original shapes.
			// But for pasting after just copying, a initial gap must be used.
			if(!(copy instanceof CutShapes)) {
				copy.nbTimeCopied++;
			}

			final int gapPaste = grid.isMagnetic() ? grid.getGridSpacing() : 10;
			final int gap = copy.nbTimeCopied * gapPaste;

			copy.copiedShapes.forEach(shape -> {
				final IShape sh = ShapeFactory.INST.duplicate(shape);
				pastedShapes.add(sh);
				sh.translate(gap, gap);
				dr.addShape(sh);
			});

			if(copy instanceof CutShapes) {
				copy.nbTimeCopied++;
			}

			dr.setModified(true);
		});
	}

	@Override
	public void undo() {
		drawing.ifPresent(dr -> {
			int i = 0;
			final int nbShapes = copy.copiedShapes.size();

			while(i < nbShapes && !dr.isEmpty()) {
				dr.removeShape(dr.size() - 1);
				i++;
			}

			copy.nbTimeCopied--;
			dr.setModified(true);
		});
	}

	@Override
	public void redo() {
		drawing.ifPresent(dr -> {
			if(!(copy instanceof CutShapes)) {
				copy.nbTimeCopied++;
			}

			pastedShapes.forEach(dr::addShape);

			if(copy instanceof CutShapes) {
				copy.nbTimeCopied++;
			}

			dr.setModified(true);
		});
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.43");
	}

	@Override
	public List<Action> followingActions() {
		if(drawing.isPresent()) return Collections.emptyList();

		final List<Action> list = new ArrayList<>();
		final SelectShapes selectAction = new SelectShapes();
		selectAction.setDrawing(drawing.get());
		pastedShapes.forEach(selectAction::addShape);
		list.add(selectAction);
		return list;
	}

	public void setCopy(final CopyShapes copysh) {
		copy = copysh;
	}

	public CopyShapes getCopy() {
		return copy;
	}

	/** Sets the magnetic grid to use. */
	public void setGrid(final MagneticGrid gr) {
		grid = gr;
	}
}
