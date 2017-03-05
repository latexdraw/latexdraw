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
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
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
	@FXML private StackPane canvasPane;
	@Inject private TextSetter textSetter;
	@Inject private MetaShapeCustomiser meta;
	@Inject private Canvas canvas;

	/**
	 * Creates the controller.
	 */
	public FrameController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		canvasPane.setPrefWidth(canvas.getPrefWidth());
		canvasPane.setPrefHeight(canvas.getPrefHeight());

		canvas.scaleXProperty().addListener(obs -> {
			canvasPane.setPrefWidth(canvas.getPrefWidth()*canvas.getScaleX());
			canvasPane.setTranslateX((canvasPane.getPrefWidth()*canvas.getScaleX() - canvasPane.getPrefWidth())/2d);
		});

		canvas.scaleYProperty().addListener(obs -> {
			canvasPane.setPrefHeight(canvas.getPrefHeight()*canvas.getScaleY());
			canvasPane.setTranslateY((canvasPane.getPrefHeight()*canvas.getScaleY() - canvasPane.getPrefHeight())/2d);
		});

		// Because several instruments are not bound to an FXML widget, have to call the initialization here.
		textSetter.initialize(null, null);
		meta.initialize(null, null);

		canvas.clipProperty().bind(Bindings.createObjectBinding(() -> {
			final double zoom = canvas.getScaleX();
			final double hmin = scrollPane.getHmin();
			final double vmin = scrollPane.getVmin();
			final double contentWidth = canvasPane.getPrefWidth() / zoom;
			final double contentHeight = canvasPane.getPrefHeight() / zoom;
			final double vpWidth = scrollPane.getViewportBounds().getWidth() / zoom;
			final double vpHeight = scrollPane.getViewportBounds().getHeight() / zoom;
			final double x = Math.max(0d, contentWidth - vpWidth) * (scrollPane.getHvalue() - hmin) / (scrollPane.getHmax() - hmin);
			final double y = Math.max(0d,  contentHeight - vpHeight) * (scrollPane.getVvalue() - vmin) / (scrollPane.getVmax() - vmin);
			return new Rectangle(x, y, vpWidth, vpHeight);
		}, scrollPane.vvalueProperty(), scrollPane.hvalueProperty(), scrollPane.viewportBoundsProperty()));
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
