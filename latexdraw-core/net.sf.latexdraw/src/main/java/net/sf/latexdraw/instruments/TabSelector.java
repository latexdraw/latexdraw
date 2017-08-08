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
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.javafx.action.ActivateInactivateInstruments;
import org.malai.javafx.action.MoveCamera;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument (de-)activates instruments while changing of tab (drawing tab, PST tab, etc.) and
 * initialises several parameters of the tab contents.
 * @author Arnaud BLOUIN
 */
public class TabSelector extends JfxInstrument implements Initializable {
	@Inject private EditingSelector selector;
	@Inject private CopierCutterPaster paster;
	@Inject private UndoRedoManager undo;
	@Inject private Zoomer zoomer;
	@Inject private ShapeDeleter deleter;
	@FXML private TabPane tabPane;
	@FXML private ScrollPane scrollPane;
	@FXML private StackPane canvasPane;
	@Inject private TextSetter textSetter;
	@Inject private MetaShapeCustomiser meta;
	@Inject private Canvas canvas;
	@Inject private PreferencesSetter prefSetter;

	/**
	 * Creates the instrument.
	 */
	TabSelector() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		canvasPane.setPrefWidth(canvas.getPrefWidth());
		canvasPane.setPrefHeight(canvas.getPrefHeight());

		canvas.scaleXProperty().addListener(obs -> {
			canvasPane.setPrefWidth(canvas.getPrefWidth() * canvas.getScaleX());
			canvasPane.setTranslateX((canvasPane.getPrefWidth() * canvas.getScaleX() - canvasPane.getPrefWidth()) / 2d);
		});

		canvas.scaleYProperty().addListener(obs -> {
			canvasPane.setPrefHeight(canvas.getPrefHeight() * canvas.getScaleY());
			canvasPane.setTranslateY((canvasPane.getPrefHeight() * canvas.getScaleY() - canvasPane.getPrefHeight()) / 2d);
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
			final double y = Math.max(0d, contentHeight - vpHeight) * (scrollPane.getVvalue() - vmin) / (scrollPane.getVmax() - vmin);
			return new Rectangle(x, y, vpWidth, vpHeight);
		}, scrollPane.vvalueProperty(), scrollPane.hvalueProperty(), scrollPane.viewportBoundsProperty()));

		setActivated(true);
	}

	@Override
	public void reinit() {
		// Needs to do a pause before doing the centering. The canvas is also reinitialised so that we have to wait for all the refresh of the canvas
		// before the reinit of the scroll pane. Otherwise the centering will fail.
		final PauseTransition pause = new PauseTransition(Duration.millis(200));
		pause.setOnFinished(evt -> centreViewport());
		pause.play();
	}

	public void centreViewport() {
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
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		bindTab(ActivateInactivateInstruments.class, action -> {
			if(tabPane.getSelectionModel().getSelectedIndex() == 0) {
				action.addInstrumentToActivate(selector);
				action.addInstrumentToActivate(paster);
				action.addInstrumentToActivate(undo);
				action.addInstrumentToActivate(zoomer);
				action.addInstrumentToInactivate(prefSetter);
				if(canvas.getDrawing().getSelection().getShapes().isEmpty()) {
					action.addInstrumentToInactivate(deleter);
				}else {
					action.addInstrumentToActivate(deleter);
				}
			}else {
				action.setHideWidgets(true);
				action.addInstrumentToInactivate(selector);
				action.addInstrumentToInactivate(paster);
				action.addInstrumentToInactivate(undo);
				action.addInstrumentToInactivate(zoomer);
				// The deleter must be added to use the hideWidgets parameter of the
				action.addInstrumentToInactivate(deleter);
				if(tabPane.getSelectionModel().getSelectedIndex() == 1) {
					action.addInstrumentToInactivate(prefSetter);
				}else {
					action.addInstrumentToActivate(prefSetter);
				}
			}
		}, tabPane);
	}
}
