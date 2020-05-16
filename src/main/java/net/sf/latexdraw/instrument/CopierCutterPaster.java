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

import io.github.interacto.command.CommandsRegistry;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.BooleanSupplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.command.shape.CopyShapes;
import net.sf.latexdraw.command.shape.CutShapes;
import net.sf.latexdraw.command.shape.PasteShapes;
import net.sf.latexdraw.command.shape.SelectShapes;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.OperatingSystem;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.jetbrains.annotations.NotNull;

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
	private final @NotNull PreferencesService prefs;

	private final BooleanSupplier isShapeSelected = () -> {
		final SelectShapes cmd = (SelectShapes) CommandsRegistry.getInstance().getCommands().parallelStream().
			filter(command -> command instanceof SelectShapes).findFirst().orElse(null);
		return cmd != null && !cmd.getShapes().isEmpty();
	};


	@Inject
	public CopierCutterPaster(final Canvas canvas, final MagneticGrid grid, final PreferencesService prefs) {
		super(canvas, grid);
		this.prefs = Objects.requireNonNull(prefs);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
		addDisposable(CommandsRegistry.getInstance().commands().subscribe(c -> updateWidgets()));
	}

	@Override
	public void setActivated(final boolean activ) {
		super.setActivated(activ);
		updateWidgets();
	}

	/**
	 * Updates the widgets of the instrument.
	 */
	private void updateWidgets() {
		copyMenu.setDisable(!activated || !isShapeSelected.getAsBoolean());
		cutMenu.setDisable(!activated || copyMenu.isDisable());
		pasteMenu.setDisable(!activated || getCopyCutCmd().isEmpty());
	}

	@Override
	protected void configureBindings() {
		final var baseMenuBinder = menuItemBinder()
			.end(() -> updateWidgets());
		final var baseShortcutBinder = shortcutBinder()
			.end(() -> updateWidgets());

		// menu to paste shapes.
		baseMenuBinder
			.toProduce(() -> new PasteShapes(getCopyCutCmd().orElseThrow(), prefs, canvas.getDrawing()))
			.on(pasteMenu)
			.when(() -> getCopyCutCmd().isPresent())
			.bind();

		// Key shortcut ctrl+V to paste shapes.
		baseShortcutBinder
			.toProduce(() -> new PasteShapes(getCopyCutCmd().orElseThrow(), prefs, canvas.getDrawing()))
			.on(canvas)
			.with(KeyCode.V, OperatingSystem.getControlKey())
			.when(() -> getCopyCutCmd().isPresent())
			.bind();

		// menu to copy shapes.
		baseMenuBinder
			.toProduce(() -> new CopyShapes(getSelectCmd().orElseThrow()))
			.on(copyMenu)
			.when(isShapeSelected)
			.bind();

		// Key shortcut ctrl+C to copy shapes.
		baseShortcutBinder
			.toProduce(i -> new CopyShapes(getSelectCmd().orElseThrow()))
			.on(canvas)
			.with(KeyCode.C, OperatingSystem.getControlKey())
			.when(isShapeSelected)
			.bind();

		// menu to cut shapes.
		baseMenuBinder
			.toProduce(() -> new CutShapes(getSelectCmd().orElseThrow()))
			.on(cutMenu)
			.when(isShapeSelected)
			.bind();

		// Key shortcut ctrl+X to cut shapes.
		baseShortcutBinder
			.toProduce(() -> new CutShapes(getSelectCmd().orElseThrow()))
			.on(canvas)
			.with(KeyCode.X, OperatingSystem.getControlKey())
			.when(isShapeSelected)
			.bind();
	}
}

