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
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.commands.shape.CopyShapes;
import net.sf.latexdraw.commands.shape.CutShapes;
import net.sf.latexdraw.commands.shape.PasteShapes;
import net.sf.latexdraw.commands.shape.SelectShapes;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.SystemService;
import org.malai.command.Command;
import org.malai.command.CommandsRegistry;

/**
 * This instrument permits to copy, cut and paste the selected shapes.
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 */
public class CopierCutterPaster extends CanvasInstrument implements Initializable, CmdRegistrySearcher {
	/** The menu item to copy the shapes. */
	@FXML protected MenuItem copyMenu;
	/** The menu item to paste the shapes. */
	@FXML protected MenuItem pasteMenu;
	/** The menu item to cut the shapes. */
	@FXML protected MenuItem cutMenu;
	@Inject private SystemService system;

	private final Supplier<Boolean> isShapeSelected = () -> {
		final SelectShapes cmd = (SelectShapes) CommandsRegistry.INSTANCE.getCommands().parallelStream().
			filter(command -> command instanceof SelectShapes).findFirst().orElse(null);
		return cmd != null && !cmd.getShapes().isEmpty();
	};


	/**
	 * Creates the instrument.
	 */
	public CopierCutterPaster() {
		super();
		CommandsRegistry.INSTANCE.addHandler(this);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}

	@Override
	public void setActivated(final boolean activ) {
		super.setActivated(activ);
		updateWidgets(null);
	}

	/**
	 * Updates the widgets of the instrument.
	 * @param executedCmd The command currently executed. Can be null.
	 * @since 3.0
	 */
	protected void updateWidgets(final Command executedCmd) {
		copyMenu.setDisable(!activated || !isShapeSelected.get());
		cutMenu.setDisable(!activated || copyMenu.isDisable());
		pasteMenu.setDisable(!activated || !(executedCmd instanceof CopyShapes || getCopyCutCmd().isPresent()));
	}

	@Override
	protected void configureBindings() {
		// menu to paste shapes.
		menuItemBinder(i -> new PasteShapes(getCopyCutCmd().orElse(null), grid, canvas.getDrawing())).on(pasteMenu).
			when(i -> getCopyCutCmd().isPresent()).bind();

		// Key shortcut ctrl+V to paste shapes.
		keyNodeBinder(i -> new PasteShapes(getCopyCutCmd().orElse(null), grid, canvas.getDrawing())).
			on(canvas).with(KeyCode.V, system.getControlKey()).when(i -> getCopyCutCmd().isPresent()).bind();

		// menu to copy shapes.
		menuItemBinder(i -> new CopyShapes(getSelectCmd().orElse(null))).on(copyMenu).when(i -> isShapeSelected.get()).bind();

		// Key shortcut ctrl+C to copy shapes.
		keyNodeBinder(i -> new CopyShapes(getSelectCmd().orElse(null))).on(canvas).with(KeyCode.C, system.getControlKey()).
			when(i -> isShapeSelected.get()).bind();

		// menu to cut shapes.
		menuItemBinder(i -> new CutShapes(getSelectCmd().orElse(null))).on(cutMenu).when(i -> isShapeSelected.get()).bind();

		// Key shortcut ctrl+X to cut shapes.
		keyNodeBinder(i -> new CutShapes(getSelectCmd().orElse(null))).on(canvas).with(KeyCode.X, system.getControlKey()).
			when(i -> isShapeSelected.get()).bind();
	}

	@Override
	public void onCmdAdded(final Command cmd) {
		updateWidgets(cmd);
	}
}

