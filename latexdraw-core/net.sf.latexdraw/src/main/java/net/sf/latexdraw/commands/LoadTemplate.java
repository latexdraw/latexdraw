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
package net.sf.latexdraw.commands;

import java.util.Optional;
import javafx.scene.control.Label;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.javafx.command.IOCommand;
import org.malai.undo.Undoable;

/**
 * This command loads a given template.
 * @author Arnaud Blouin
 */
public class LoadTemplate extends IOCommand<Label> implements DrawingCmd, Undoable, Modifying {
	private IShape insertedShapes;
	private IDrawing drawing;
	private IPoint position;

	public LoadTemplate() {
		super();
	}

	@Override
	protected void doCmdBody() {
		insertedShapes = SVGDocumentGenerator.INSTANCE.insert(file.getPath(), position);
		ok = insertedShapes != null;
	}

	@Override
	public void redo() {
		drawing.addShape(insertedShapes);
	}

	@Override
	public void undo() {
		drawing.removeShape(insertedShapes);
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("template.added");
	}

	@Override
	public boolean canDo() {
		return drawing != null;
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}

	@Override
	public void flush() {
		super.flush();
		insertedShapes = null;
	}

	@Override
	public void setDrawing(final IDrawing dr) {
		drawing = dr;
	}

	@Override
	public Optional<IDrawing> getDrawing() {
		return Optional.ofNullable(drawing);
	}

	public void setPosition(final IPoint templatePosition) {
		position = templatePosition;
	}
}
