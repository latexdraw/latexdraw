/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import io.github.interacto.jfx.command.IOCommand;
import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * This command loads a given template.
 * @author Arnaud Blouin
 */
public class LoadTemplate extends IOCommand<Label> implements Undoable, Modifying {
	private Shape insertedShapes;
	private final @NotNull Drawing drawing;
	private Point position;
	private final @NotNull SVGDocumentGenerator svgGen;

	public LoadTemplate(final @NotNull SVGDocumentGenerator svgGen, final @NotNull Drawing drawing) {
		super();
		this.svgGen = svgGen;
		this.drawing = drawing;
	}

	@Override
	protected void doCmdBody() {
		insertedShapes = svgGen.insert(file.getPath(), position);
		System.out.println(">>" + insertedShapes);
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
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("template.added");
	}

	@Override
	public boolean canDo() {
		return ui != null;
	}

	@Override
	public void flush() {
		super.flush();
		insertedShapes = null;
	}

	public void setPosition(final Point templatePosition) {
		position = templatePosition;
	}
}
