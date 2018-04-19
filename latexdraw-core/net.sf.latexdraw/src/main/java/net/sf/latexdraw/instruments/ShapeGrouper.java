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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.commands.shape.JoinShapes;
import net.sf.latexdraw.commands.shape.SeparateShapes;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

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

	/**
	 * Creates the instrument.
	 */
	public ShapeGrouper() {
		super();
	}

	@Override
	protected void update(final IGroup shape) {
		if(hand.isActivated()) {
			groupB.setDisable(shape.size() < 2 || !hand.isActivated());
			sepB.setDisable(shape.size() != 1 || !(shape.getShapeAt(0) instanceof IGroup));
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
		buttonBinder(SeparateShapes.class).on(sepB).first(c -> getSelectCmd().map(sel -> sel.getShapes()).ifPresent(shapes -> {
			if(shapes.size() == 1 && shapes.get(0) instanceof IGroup) {
				c.setShape((IGroup) shapes.get(0));
			}
			c.setDrawing(pencil.canvas.getDrawing());
		})).bind();

		buttonBinder(JoinShapes.class).on(groupB).first(c -> getSelectCmd().ifPresent(sel -> {
			sel.getShapes().forEach(sh -> c.addShape(sh));
			c.setDrawing(pencil.canvas.getDrawing());
		})).bind();
	}
}
