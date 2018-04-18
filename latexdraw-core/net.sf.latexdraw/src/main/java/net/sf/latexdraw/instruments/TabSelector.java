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
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.javafx.command.ActivateInactivateInstruments;
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
	@FXML private Pane canvasPane;
	@FXML private ScaleRuler xruler;
	@FXML private ScaleRuler yruler;
	@FXML private Pane rulersScrollerPane;
	@Inject private TextSetter textSetter;
	@Inject private MetaShapeCustomiser meta;
	@Inject private Canvas canvas;
	@Inject private PreferencesSetter prefSetter;

	/**
	 * Creates the instrument.
	 */
	public TabSelector() {
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

		initScaleRulers();

		setActivated(true);
	}

	private void initScaleRulers() {
		xruler.setCanvas(canvas);
		yruler.setCanvas(canvas);

		canvas.zoomProperty().addListener((observable, oldValue, newValue) -> xruler.update(rulersScrollerPane.getWidth(), rulersScrollerPane.getHeight()));

		rulersScrollerPane.widthProperty().addListener((observable, oldValue, newValue) -> xruler.update(rulersScrollerPane.getWidth(), rulersScrollerPane.getHeight()));
		rulersScrollerPane.heightProperty().addListener((observable, oldValue, newValue) -> yruler.update(rulersScrollerPane.getWidth(), rulersScrollerPane.getHeight()));

		xruler.getGroup().translateXProperty().bind(Bindings.createDoubleBinding(() -> {
			final int gap = 100; // 100mm = 1cm
			int val = (int) (canvas.getWidth() * scrollPane.getHvalue() - Canvas.getMargins());
			while(val < 0) {
				val += gap;
			}
			val %= gap;
			return (double) val;
		}, scrollPane.hvalueProperty()));

		yruler.getGroup().translateYProperty().bind(Bindings.createDoubleBinding(() -> {
			final int gap = 100; // 100mm = 1cm
			int val = (int) (canvas.getHeight() * scrollPane.getVvalue() - Canvas.getMargins());
			while(val < 0) {
				val += gap;
			}
			val %= gap;
			return (double) val;
		}, scrollPane.vvalueProperty()));
	}

	@Override
	public void reinit() {
		centreViewport();
	}

	public void centreViewport() {
		scrollPane.setHvalue(0.5);
		scrollPane.setVvalue(0.4);
	}

	@Override
	protected void configureBindings() {
		tabBinder(ActivateInactivateInstruments.class).on(tabPane).first((i, c) -> {
			if(i.getWidget().getSelectionModel().getSelectedIndex() == 0) {
				c.addInstrumentToActivate(selector);
				c.addInstrumentToActivate(paster);
				c.addInstrumentToActivate(undo);
				c.addInstrumentToActivate(zoomer);
				c.addInstrumentToInactivate(prefSetter);
				if(canvas.getDrawing().getSelection().getShapes().isEmpty()) {
					c.addInstrumentToInactivate(deleter);
				}else {
					c.addInstrumentToActivate(deleter);
				}
			}else {
				c.setHideWidgets(false);
				c.addInstrumentToInactivate(selector);
				c.addInstrumentToInactivate(paster);
				c.addInstrumentToInactivate(undo);
				c.addInstrumentToInactivate(zoomer);
				// The deleter must be added to use the hideWidgets parameter of the
				c.addInstrumentToInactivate(deleter);
				if(i.getWidget().getSelectionModel().getSelectedIndex() == 1) {
					c.addInstrumentToInactivate(prefSetter);
				}else {
					c.addInstrumentToActivate(prefSetter);
				}
			}
		}).bind();
	}
}
