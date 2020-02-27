/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
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
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.PreferencesService;
import org.jetbrains.annotations.NotNull;

/**
 * This command pastes the copied or cut shapes.
 * @author Arnaud Blouin
 */
public class PasteShapes extends DrawingCmdImpl implements Undoable, Modifying {
	/** The cut or copy command. */
	private final @NotNull CopyShapes copy;
	/** The magnetic grid to use. */
	private final @NotNull PreferencesService prefs;
	private boolean mementoModified;

	public PasteShapes(final @NotNull CopyShapes copyCmd, final @NotNull PreferencesService prefs, final @NotNull Drawing drawing) {
		super(drawing);
		copy = copyCmd;
		this.prefs = prefs;
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
	}

	@Override
	public void doCmdBody() {
		// While pasting cut shapes, the first paste must be at the same position that the original shapes.
		// But for pasting after just copying, a initial gap must be used.
		if(!(copy instanceof CutShapes)) {
			copy.nbTimeCopied++;
		}

		final int gapPaste = prefs.isMagneticGrid() ? prefs.gridGapProperty().get() : 10;
		final int gap = copy.nbTimeCopied * gapPaste;
		final List<Shape> pastedShapes = new ArrayList<>();

		copy.copiedShapes.forEach(shape -> ShapeFactory.INST.duplicate(shape).ifPresent(sh -> {
			pastedShapes.add(sh);
			sh.translate(gap, gap);
			drawing.addShape(sh);
		}));

		if(copy instanceof CutShapes) {
			copy.nbTimeCopied++;
		}

		drawing.setSelection(pastedShapes);
		drawing.setModified(true);
	}

	@Override
	public void undo() {
		int i = 0;
		final int nbShapes = copy.copiedShapes.size();

		while(i < nbShapes) {
			drawing.removeShape(drawing.size() - 1);
			i++;
		}

		copy.nbTimeCopied--;
		drawing.setModified(mementoModified);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("LaTeXDrawFrame.43");
	}
}
