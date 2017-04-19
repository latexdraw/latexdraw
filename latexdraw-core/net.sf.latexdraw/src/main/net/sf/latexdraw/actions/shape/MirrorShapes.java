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

import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This action mirrors a shape.
 * @author Arnaud Blouin
 */
public class MirrorShapes extends ShapeActionImpl<IShape> implements Undoable, Modifying {
	/** If true, the mirroring is horizontal. */
	boolean horizontally;


	public MirrorShapes() {
		super();
		horizontally = true;
	}

	@Override
	protected void doActionBody() {
		shape.ifPresent(sh -> {
			if(horizontally) {
				sh.mirrorHorizontal(sh.getGravityCentre());
			}else {
				sh.mirrorVertical(sh.getGravityCentre());
			}
			sh.setModified(true);
		});
	}


	@Override
	public boolean canDo() {
		return shape.isPresent();
	}

	@Override
	public void undo() {
		doActionBody();
	}

	@Override
	public void redo() {
		doActionBody();
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.7");
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	/**
	 * If true, the mirroring is horizontal. Otherwise, vertical.
	 */
	public void setHorizontally(final boolean horiz) {
		horizontally = horiz;
	}
}