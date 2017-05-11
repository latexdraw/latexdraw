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


import java.text.ParseException;
import java.util.Optional;
import javafx.scene.control.Label;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;
import scala.Option;

/**
 * This action converts PST code into shapes and add them to the drawing.
 * @author Arnaud Blouin
 */
public class InsertPSTCode extends DrawingActionImpl implements Undoable {
	/** The code to parse. */
	Optional<String> code;

	/** The status bar. */
	Optional<Label> statusBar;

	/** The added shapes. */
	Optional<IShape> shapes;

	public InsertPSTCode() {
		super();
		code = Optional.empty();
		statusBar = Optional.empty();
		shapes = Optional.empty();
	}

	@Override
	protected void doActionBody() {
		PSTParser.cleanErrors();

		code.ifPresent(co -> {
			try {
				Option<IGroup> parserRes = new PSTParser().parsePSTCode(co);

				if(parserRes.isDefined()) {
					IGroup group = parserRes.get();
					if(!group.isEmpty()) {
						final IShape sh = group.size() > 1 ? group : group.getShapeAt(0);
						final IPoint tl = sh.getTopLeftPoint();
						final double tx = tl.getX() < 0.0 ? -tl.getX() + 50.0 : 0.0;
						final double ty = tl.getY() < 0.0 ? -tl.getY() + 50.0 : 0.0;

						shapes = Optional.of(sh);
						sh.translate(tx, ty);
						redo();
						statusBar.ifPresent(bar -> bar.setText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.36")));
					}
				}else {
					statusBar.ifPresent(bar -> bar.setText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.33")));
				}
			}catch(final Throwable ex) {
				BadaboomCollector.INSTANCE.add(ex);
				statusBar.ifPresent(bar -> bar.setText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.34")));
			}

			PSTParser.errorLogs().foreach(str -> BadaboomCollector.INSTANCE.add(new ParseException(str, -1)));
		});

		done();
	}

	@Override
	public void undo() {
		shapes.ifPresent(sh -> drawing.ifPresent(dr -> {
			dr.removeShape(sh);
			dr.setModified(true);
		}));
	}

	@Override
	public void redo() {
		shapes.ifPresent(sh -> drawing.ifPresent(dr -> {
			dr.addShape(sh);
			dr.setModified(true);
		}));
	}

	public void setStatusBar(final Label value) {
		statusBar = Optional.ofNullable(value);
	}

	public void setCode(final String value) {
		code = Optional.ofNullable(value);
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.4");
	}

	@Override
	public boolean canDo() {
		return super.canDo() && code.isPresent();
	}

	@Override
	public boolean hadEffect() {
		return isDone() && shapes.isPresent();
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.LIMITED;
	}
}
