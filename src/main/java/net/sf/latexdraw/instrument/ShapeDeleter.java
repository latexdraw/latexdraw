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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.command.shape.DeleteShapes;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument deletes the selected shapes.
 * @author Arnaud BLOUIN
 */
public class ShapeDeleter extends CanvasInstrument implements Initializable, CmdRegistrySearcher {
	/** The button used to remove the selected shapes. */
	@FXML private Button deleteB;
	private final @NotNull Hand hand;

	@Inject
	public ShapeDeleter(final Canvas canvas, final MagneticGrid grid, final Hand hand) {
		super(canvas, grid);
		this.hand = Objects.requireNonNull(hand);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(false);

		canvas.getDrawing().getSelection().getShapes().addListener(
			(ListChangeListener.Change<? extends Shape> evt) -> setActivated(hand.isActivated() && !evt.getList().isEmpty()));
	}

	@Override
	public void setActivated(final boolean activated, final boolean hideWidgets) {
		super.setActivated(activated, hideWidgets);
		updateWidgets(hideWidgets);
	}

	@Override
	public void setActivated(final boolean activ) {
		super.setActivated(activ);
		updateWidgets(false);
	}

	/**
	 * Updates the widgets of this instrument.
	 * @param hideWidgets True: the widgets are hidden on deactivation.
	 */
	protected void updateWidgets(final boolean hideWidgets) {
		deleteB.setVisible(activated || !hideWidgets);
		deleteB.setDisable(!activated);
	}

	@Override
	protected void configureBindings() {
		final Consumer<DeleteShapes> first = c -> getSelectCmd().ifPresent(sel -> sel.getShapes().forEach(sh -> c.addShape(sh)));

		buttonBinder(() -> new DeleteShapes(canvas.getDrawing())).on(deleteB).first(first).bind();
		keyNodeBinder(() -> new DeleteShapes(canvas.getDrawing())).on(canvas).with(KeyCode.DELETE).first(first).bind();
	}
}

