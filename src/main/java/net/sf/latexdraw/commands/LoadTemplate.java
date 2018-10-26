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

import java.util.ResourceBundle;
import javafx.scene.control.Label;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.javafx.command.IOCommand;
import org.malai.undo.Undoable;

/**
 * This command loads a given template.
 * @author Arnaud Blouin
 */
public class LoadTemplate extends IOCommand<Label> implements Undoable, Modifying {
	private IShape insertedShapes;
	private final IDrawing drawing;
	private IPoint position;
	private final SVGDocumentGenerator svgGen;

	public LoadTemplate(final SVGDocumentGenerator svgGen, final IDrawing drawing) {
		super();
		this.svgGen = svgGen;
		this.drawing = drawing;
	}

	@Override
	protected void doCmdBody() {
		insertedShapes = svgGen.insert(file.getPath(), position);
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
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("template.added");
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

	public void setPosition(final IPoint templatePosition) {
		position = templatePosition;
	}
}
