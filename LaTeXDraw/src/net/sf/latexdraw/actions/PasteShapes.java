package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.undo.Undoable;

/**
 * This action pastes the copied or cut shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/03/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class PasteShapes extends DrawingAction implements Undoable {
	/** The cut or copy action. */
	protected CopyShapes copy;

	/** The default gap used to translate shapes while pasting. */
	public static final int GAP_PASTE = 10;


	@Override
	public boolean canDo() {
		return super.canDo() && copy!=null;
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		// While pasting cut shapes, the first paste must be at the same position that the original shapes.
		// But for pasting after just copying, a initial gap must be used.
		if(!(copy instanceof CutShapes))
			copy.nbTimeCopied++;

		IShape sh;
		int gap = copy.nbTimeCopied*GAP_PASTE;

		for(final IShape shape : copy.copiedShapes) {
			sh = DrawingTK.getFactory().duplicate(shape);
			sh.translate(gap, gap);
			drawing.addShape(sh);
		}

		if(copy instanceof CutShapes)
			copy.nbTimeCopied++;

		drawing.setModified(true);
	}


	@Override
	public void undo() {
		copy.nbTimeCopied--;
		for(int i=0, nbShapes = copy.copiedShapes.size(); i<nbShapes && !drawing.isEmpty(); i++)
			drawing.removeShape(drawing.size()-1);

		drawing.setModified(true);
	}


	@Override
	public void redo() {
		doActionBody();
	}


	@Override
	public String getUndoName() {
		return LResources.LABEL_PASTE;
	}


	@Override
	public void flush() {
		super.flush();
		copy = null;
	}


	/**
	 * @param copy the copy to set.
	 * @since 3.0
	 */
	public void setCopy(final CopyShapes copy) {
		this.copy = copy;
	}
}

