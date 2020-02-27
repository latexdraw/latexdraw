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
package net.sf.latexdraw.command;


import io.github.interacto.undo.Undoable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.pst.PSTContext;
import net.sf.latexdraw.parser.pst.PSTLatexdrawListener;
import net.sf.latexdraw.parser.pst.PSTLexer;
import net.sf.latexdraw.parser.pst.PSTParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jetbrains.annotations.NotNull;

/**
 * This command converts PST code into shapes and add them to the drawing.
 * @author Arnaud Blouin
 */
public class InsertPSTCode extends DrawingCmdImpl implements Undoable, Modifying {
	/** The code to parse. */
	private final @NotNull String code;
	/** The status bar. */
	private final @NotNull Label statusBar;
	/** The added shapes. */
	private @NotNull Optional<Shape> shapes;
	private final @NotNull ResourceBundle lang;
	private boolean mementoModified;


	public InsertPSTCode(final @NotNull String codeToInsert, final @NotNull Label status, final @NotNull Drawing drawingToFill,
			final @NotNull ResourceBundle lang) {
		super(drawingToFill);
		code = codeToInsert;
		statusBar = status;
		shapes = Optional.empty();
		this.lang = lang;
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
	}

	@Override
	protected void doCmdBody() {
		final PSTLatexdrawListener listener = new PSTLatexdrawListener();
		final PSTLexer lexer = new PSTLexer(CharStreams.fromString(code));
		final PSTParser parser = new PSTParser(new CommonTokenStream(lexer));
		parser.addParseListener(listener);
		parser.pstCode(new PSTContext());

		final Group group = ShapeFactory.INST.createGroup();
		group.getShapes().addAll(listener.flatShapes());

		if(!group.isEmpty()) {
			final Shape sh = group.size() > 1 ? group : group.getShapeAt(0).orElseThrow();
			final Point tl = sh.getTopLeftPoint();
			final double tx = tl.getX() < 0d ? -tl.getX() + 50d : 0d;
			final double ty = tl.getY() < 0d ? -tl.getY() + 50d : 0d;

			shapes = Optional.of(sh);
			sh.translate(tx, ty);
			redo();

			statusBar.setText(lang.getString("LaTeXDrawFrame.36"));
		}
		parser.getInterpreter().clearDFA();
		lexer.getInterpreter().clearDFA();
	}

	@Override
	public void undo() {
		shapes.ifPresent(sh -> {
			drawing.removeShape(sh);
			drawing.setModified(mementoModified);
		});
	}

	@Override
	public void redo() {
		shapes.ifPresent(sh -> {
			drawing.addShape(sh);
			drawing.setModified(true);
		});
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.4");
	}

	@Override
	public boolean hadEffect() {
		return isDone() && shapes.isPresent();
	}
}
