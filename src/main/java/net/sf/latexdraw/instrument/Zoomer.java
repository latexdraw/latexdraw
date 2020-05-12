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

import io.github.interacto.command.library.Zoom;
import io.github.interacto.jfx.instrument.BasicZoomer;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * The instrument for zooming on the canvas.
 * @author Arnaud Blouin
 */
public class Zoomer extends BasicZoomer<Canvas> implements Initializable {
	@FXML private Spinner<Double> zoom;
	private final @NotNull Canvas canvas;
	private final ChangeListener<Number> zoomChange = (observable, oldValue, newValue) -> zoom.getValueFactory().setValue(newValue.doubleValue() * 100d);

	@Inject
	public Zoomer(final Canvas canvas) {
		super();
		this.canvas = Objects.requireNonNull(canvas);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setZoomable(canvas);
		setWithKeys(true);
		setActivated(true);

		// Cannot use a data binding here since interacting with the widget will produce
		// an 'already bound' exception
		zoomable.zoomProperty().addListener(zoomChange);

		// Conflict between the standard scroll interaction of the scrollpane
		// and the scroll-zoom interaction. Must consume the event
		canvas.setOnScroll(evt -> {
			if(evt.isControlDown()) {
				evt.consume();
			}
		});
	}

	@Override
	public void setActivated(final boolean activ) {
		super.setActivated(activ);
		zoom.setDisable(!activ);
	}

	@Override
	protected void configureBindings() {
		super.configureBindings();

		spinnerBinder()
			.toProduce(() -> new Zoom(zoomable))
			.on(zoom)
			.continuousExecution()
			.then((i, c) -> c.setZoomLevel(Double.parseDouble(i.getWidget().getValue().toString()) / 100d))
			.bind();
	}

	@Override
	public void uninstallBindings() {
		super.uninstallBindings();
		zoomable.zoomProperty().removeListener(zoomChange);
	}
}
