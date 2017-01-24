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
package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.javafx.action.library.MoveCamera;

/**
 * The class that controls the main frame.
 * @author Arnaud Blouin
 */
public class FrameController implements Initializable {

	@FXML private ScrollPane scrollPane;
	@FXML private Canvas canvas;
	@Inject private TextSetter textSetter;
	@Inject private MetaShapeCustomiser meta;

	/**
	 * Creates the controller.
	 */
	public FrameController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		scrollPane.widthProperty().addListener(obs -> canvas.update());
		scrollPane.heightProperty().addListener(obs -> canvas.update());
		// Because several instruments are not bound to an FXML widget, have to call the initialization here.
		textSetter.initialize(null, null);
		meta.initialize(null, null);
	}

	public void centreViewport() {
		Platform.runLater(() -> {
			final MoveCamera action = new MoveCamera();
			final Page page = canvas.getPage().getPage();
			final IPoint origin = canvas.getOrigin();

			action.setScrollPane(scrollPane);
			action.setPx((origin.getX() + page.getWidth() * IShape.PPC / 2d) / canvas.getWidth());
			action.setPy((origin.getY() + page.getHeight() * IShape.PPC / 5d) / canvas.getHeight());

			if(action.canDo()) {
				action.doIt();
			}

			action.flush();
		});
	}
}
