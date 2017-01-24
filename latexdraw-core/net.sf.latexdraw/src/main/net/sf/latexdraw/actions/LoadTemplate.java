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
package net.sf.latexdraw.actions;

import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.undo.Undoable;

/**
 * This action loads a given template.
 */
public class LoadTemplate extends DrawingActionImpl implements Undoable, Modifying { //IOAction[LFrame, JLabel] {
	IShape insertedShapes;

	public LoadTemplate() {
		super();
	}

	@Override
	protected void doActionBody() {
		//    insertedShapes = SVGDocumentGenerator.INSTANCE.insert(file.getPath, ui)
		//    println(insertedShapes)
		//    ok = insertedShapes!=null
	}

	@Override
	public void redo() {
		drawing.ifPresent(dr -> dr.addShape(insertedShapes));
	}

	@Override
	public void undo() {
		drawing.ifPresent(dr -> dr.removeShape(insertedShapes));
	}

	@Override
	public String getUndoName() {
		return "template added";
	}

	@Override
	public boolean canDo() {
		return drawing.isPresent();
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	public void flush() {
		super.flush();
		insertedShapes = null;
	}
}