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
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.PSTContext;
import net.sf.latexdraw.parsers.pst.PSTLatexdrawListener;
import net.sf.latexdraw.parsers.pst.PSTLexer;
import net.sf.latexdraw.parsers.pst.PSTParser;
import net.sf.latexdraw.util.LangService;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.malai.undo.Undoable;

/**
 * This command converts PST code into shapes and add them to the drawing.
 * @author Arnaud Blouin
 */
public class InsertPSTCode extends DrawingCmdImpl implements Undoable, Modifying {
	/** The code to parse. */
	private final String code;
	/** The status bar. */
	private final Label statusBar;
	/** The added shapes. */
	private Optional<IShape> shapes;
	private final LangService lang;


	public InsertPSTCode(final String codeToInsert, final Label status, final IDrawing drawingToFill, final LangService lang) {
		super(drawingToFill);
		code = codeToInsert;
		statusBar = status;
		shapes = Optional.empty();
		this.lang = lang;

	}

	@Override
	protected void doCmdBody() {
		try {
			final PSTLatexdrawListener listener = new PSTLatexdrawListener();
			final PSTLexer lexer = new PSTLexer(CharStreams.fromString(code));
			final PSTParser parser = new PSTParser(new CommonTokenStream(lexer));
			parser.addParseListener(listener);
			parser.pstCode(new PSTContext());

			final IGroup group = ShapeFactory.INST.createGroup();
			group.getShapes().addAll(listener.flatShapes());

			if(!group.isEmpty()) {
				final IShape sh = group.size() > 1 ? group : group.getShapeAt(0);
				final IPoint tl = sh.getTopLeftPoint();
				final double tx = tl.getX() < 0d ? -tl.getX() + 50d : 0d;
				final double ty = tl.getY() < 0d ? -tl.getY() + 50d : 0d;

				shapes = Optional.of(sh);
				sh.translate(tx, ty);
				redo();

				if(statusBar != null) {
					statusBar.setText(lang.getBundle().getString("LaTeXDrawFrame.36"));
				}
			}
			parser.getInterpreter().clearDFA();
			lexer.getInterpreter().clearDFA();
		}catch(final RecognitionException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			if(statusBar != null) {
				statusBar.setText(lang.getBundle().getString("LaTeXDrawFrame.34"));
			}
		}

		done();
	}

	@Override
	public void undo() {
		shapes.ifPresent(sh -> {
			drawing.removeShape(sh);
			drawing.setModified(true);
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
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.4");
	}

	@Override
	public boolean canDo() {
		return code != null && super.canDo();
	}

	@Override
	public boolean hadEffect() {
		return isDone() && shapes.isPresent();
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}
}
