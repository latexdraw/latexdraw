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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import org.malai.javafx.command.MoveCamera;
import org.malai.javafx.interaction.library.DnD;

/**
 * Control of standard canvas user interactions.
 * @author Arnaud Blouin
 */
public class CanvasController extends CanvasInstrument implements Initializable {
	public CanvasController() {
		super();
	}

	@Override
	protected void configureBindings() {
		nodeBinder(new DnD(), MoveCamera.class).on(canvas).
			first((i, c) -> c.setScrollPane(canvas.getScrollPane())).
			then((i, c) -> {
				final ScrollPane pane = canvas.getScrollPane();
				c.setPx(pane.getHvalue() - (i.getTgtLocalPoint().getX() - i.getSrcLocalPoint().getX()) / canvas.getWidth());
				c.setPy(pane.getVvalue() - (i.getTgtLocalPoint().getY() - i.getSrcLocalPoint().getY()) / canvas.getHeight());
			}).
			when(i -> i.getButton() == MouseButton.MIDDLE).
			feedback(() -> canvas.setCursor(Cursor.MOVE)).
			exec().
			bind();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}
}
