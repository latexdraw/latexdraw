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
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.command.MoveCamera;
import io.github.interacto.jfx.interaction.library.DnD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;

/**
 * Control of standard canvas user interactions.
 * @author Arnaud Blouin
 */
public class CanvasController extends CanvasInstrument implements Initializable {
	@Inject
	public CanvasController(final Canvas canvas, final MagneticGrid grid) {
		super(canvas, grid);
	}

	@Override
	protected void configureBindings() {
		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(MoveCamera::new)
			.on(canvas)
			.first(c -> {
				c.setScrollPane(canvas.getScrollPane());
				canvas.setCursor(Cursor.MOVE);
			})
			.then((i, c) -> {
				final ScrollPane pane = canvas.getScrollPane();
				c.setPx(pane.getHvalue() - (i.getTgtLocalPoint().getX() - i.getSrcLocalPoint().getX()) / canvas.getWidth());
				c.setPy(pane.getVvalue() - (i.getTgtLocalPoint().getY() - i.getSrcLocalPoint().getY()) / canvas.getHeight());
			})
			.when(i -> i.getButton() == MouseButton.MIDDLE)
			.endOrCancel(i -> canvas.setCursor(Cursor.DEFAULT))
			.continuousExecution()
			.bind();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}
}
