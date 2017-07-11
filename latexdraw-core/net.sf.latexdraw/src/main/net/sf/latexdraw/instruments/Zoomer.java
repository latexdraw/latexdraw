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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.action.library.Zoom;
import org.malai.javafx.instrument.BasicZoomer;

/**
 * The instrument for zooming on the canvas.
 * @author Arnaud Blouin
 */
public class Zoomer extends BasicZoomer<Canvas> implements Initializable {
	@FXML private Spinner<Double> zoom;
	@Inject private Canvas canvas;

	Zoomer() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setZoomable(canvas);
		setWithKeys(true);
		setActivated(true);

		// Conflict between the standard scroll interaction of the scrollpane
		// and the scroll-zoom interaction. Must consume the event
		canvas.setOnScroll(evt -> {
			if(evt.isControlDown()) {
				evt.consume();
			}
		});
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		super.configureBindings();
		bindSpinner(Zoom.class, action -> action.setZoomable(zoomable), action -> action.setZoomLevel(zoom.getValue() / 100d), true, zoom);
	}

	@Override
	public void reinit() {
		interimFeedback();
	}

	@Override
	public void interimFeedback() {
		zoom.getValueFactory().setValue(zoomable.getZoom() * 100d);
	}

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		zoom.setVisible(activated);
	}
}
