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
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.command.shape.JoinShapes;
import net.sf.latexdraw.command.shape.SeparateShapes;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * This instrument groups and separates shapes.
 * @author Arnaud Blouin
 */
public class ShapeGrouper extends ShapePropertyCustomiser implements Initializable, CmdRegistrySearcher {
	/** The widget to group shapes. */
	@FXML private Button groupB;
	/** The widget to separate shapes. */
	@FXML private Button sepB;
	@FXML private AnchorPane mainPane;

	@Inject
	public ShapeGrouper(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	protected void update(final Group shape) {
		if(hand.isActivated()) {
			groupB.setDisable(shape.size() < 2 || !hand.isActivated());
			sepB.setDisable(shape.size() != 1 || !shape.getShapeAt(0).filter(s -> s instanceof Group).isPresent());
			setActivated(!groupB.isDisable() || !sepB.isDisable());
		}else {
			setActivated(false);
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void configureBindings() {
		buttonBinder()
			.toProduce(() -> new SeparateShapes(canvas.getDrawing(), getSelectCmd().map(sel -> sel.getShapes()).
				filter(sel -> sel.size() == 1 && sel.get(0) instanceof Group).
				map(sel -> (Group) sel.get(0)).orElseThrow()))
			.when(() -> getSelectCmd().map(sel -> sel.getShapes()).filter(sel -> sel.size() == 1 && sel.get(0) instanceof Group).isPresent())
			.on(sepB)
			.bind();

		buttonBinder()
			.toProduce(() -> new JoinShapes(canvas.getDrawing()))
			.on(groupB)
			.first(c -> getSelectCmd().ifPresent(sel -> sel.getShapes().forEach(sh -> c.addShape(sh))))
			.bind();
	}
}
