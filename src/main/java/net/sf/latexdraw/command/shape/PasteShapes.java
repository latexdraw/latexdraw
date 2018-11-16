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
import java.util.Optional;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.MagneticGrid;
import org.jetbrains.annotations.NotNull;
import org.malai.command.Command;
import org.malai.undo.Undoable;

/**
 * This command pastes the copied or cut shapes.
 * @author Arnaud Blouin
 */
public class PasteShapes extends DrawingCmdImpl implements Undoable, Modifying {
	/** The cut or copy command. */
	private final @NotNull Optional<CopyShapes> copy;
	/** The magnetic grid to use. */
	private final @NotNull MagneticGrid grid;
	private final @NotNull List<Shape> pastedShapes;

	public PasteShapes(final @NotNull Optional<CopyShapes> copyCmd, final @NotNull MagneticGrid magnetGrid, final @NotNull Drawing drawing) {
		super(drawing);
		copy = copyCmd;
		grid = magnetGrid;
		pastedShapes = new ArrayList<>();
	}

	@Override
	public boolean canDo() {
		return copy.isPresent();
	}

	@Override
	public void doCmdBody() {
		copy.ifPresent(cop -> {
			// While pasting cut shapes, the first paste must be at the same position that the original shapes.
			// But for pasting after just copying, a initial gap must be used.
			if(!(cop instanceof CutShapes)) {
				cop.nbTimeCopied++;
			}

			final int gapPaste = grid.isMagnetic() ? grid.getGridSpacing() : 10;
			final int gap = cop.nbTimeCopied * gapPaste;

			cop.copiedShapes.forEach(shape -> ShapeFactory.INST.duplicate(shape).ifPresent(sh -> {
				pastedShapes.add(sh);
				sh.translate(gap, gap);
				drawing.addShape(sh);
			}));

			if(cop instanceof CutShapes) {
				cop.nbTimeCopied++;
			}

			drawing.setModified(true);
		});
	}

	@Override
	public void undo() {
		copy.ifPresent(cop -> {
			int i = 0;
			final int nbShapes = cop.copiedShapes.size();

			while(i < nbShapes && !drawing.isEmpty()) {
				drawing.removeShape(drawing.size() - 1);
				i++;
			}

			cop.nbTimeCopied--;
			drawing.setModified(true);
		});
	}

	@Override
	public void redo() {
		copy.ifPresent(cop -> {
			if(!(cop instanceof CutShapes)) {
				cop.nbTimeCopied++;
			}

			pastedShapes.forEach(drawing::addShape);

			if(cop instanceof CutShapes) {
				cop.nbTimeCopied++;
			}

			drawing.setModified(true);
		});
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("LaTeXDrawFrame.43");
	}

	@Override
	public @NotNull List<Command> followingCmds() {
		final List<Command> list = new ArrayList<>();
		final SelectShapes selectCmd = new SelectShapes(drawing);
		pastedShapes.forEach(selectCmd::addShape);
		list.add(selectCmd);
		return list;
	}

	public @NotNull Optional<CopyShapes> getCopy() {
		return copy;
	}
}
