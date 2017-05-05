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

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.actions.shape.CopyShapes;
import net.sf.latexdraw.actions.shape.CutShapes;
import net.sf.latexdraw.actions.shape.PasteShapes;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.util.LSystem;
import org.malai.action.Action;
import org.malai.action.ActionsRegistry;
import org.malai.javafx.binding.JfxMenuItemBinding;
import org.malai.javafx.binding.KeysShortcutBinding;
import org.malai.javafx.interaction.library.MenuItemPressed;

/**
 * This instrument permits to copy, cut and paste the selected shapes.
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 */
public class CopierCutterPaster extends CanvasInstrument implements Initializable, ActionRegistrySearcher {
	/** The menu item to copy the shapes. */
	@FXML private MenuItem copyMenu;
	/** The menu item to paste the shapes. */
	@FXML private MenuItem pasteMenu;
	/** The menu item to cut the shapes. */
	@FXML private MenuItem cutMenu;

	private final Supplier<Boolean> isShapeSelected = () -> {
		final SelectShapes act = (SelectShapes) ActionsRegistry.INSTANCE.getActions().parallelStream().
			filter(action -> action instanceof SelectShapes).findFirst().orElse(null);
		return act != null && !act.getShapes().isEmpty();
	};


	/**
	 * Creates the instrument.
	 */
	CopierCutterPaster() {
		super();
		ActionsRegistry.INSTANCE.addHandler(this);
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
	 * @param executedAction The action currently executed. Can be null.
	 * @since 3.0
	 */
	protected void updateWidgets(final Action executedAction) {
		copyMenu.setDisable(!activated || !isShapeSelected.get());
		cutMenu.setDisable(!activated || copyMenu.isDisable());
		pasteMenu.setDisable(!activated || !(executedAction instanceof CopyShapes || getCopyCutAction().isPresent()));
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		addBinding(new MenuItem2PasteShapes());

		addBinding(new KeysShortcutBinding<>(this, PasteShapes.class, action -> {
			action.setCopy(getCopyCutAction().orElse(null));
			action.setDrawing(canvas.getDrawing());
			action.setGrid(grid);
		}, interaction -> getCopyCutAction().isPresent(), Arrays.asList(KeyCode.V, LSystem.INSTANCE.getControlKey()), canvas));

		addBinding(new MenuItem2CopyShapes<>(CopyShapes.class, copyMenu));

		addBinding(new KeysShortcutBinding<>(this, CopyShapes.class,
			action -> action.setSelection(getSelectAction().orElse(null)),
			interaction -> isShapeSelected.get(), Arrays.asList(KeyCode.C, LSystem.INSTANCE.getControlKey()), canvas));

		addBinding(new MenuItem2CopyShapes<>(CutShapes.class, cutMenu));

		addBinding(new KeysShortcutBinding<>(this, CutShapes.class,
			action -> action.setSelection(getSelectAction().orElse(null)),
			interaction -> isShapeSelected.get(), Arrays.asList(KeyCode.X, LSystem.INSTANCE.getControlKey()), canvas));
	}

	@Override
	public void onActionAdded(final Action action) {
		updateWidgets(action);
	}


	private class MenuItem2CopyShapes<T extends CopyShapes> extends JfxMenuItemBinding<T, MenuItemPressed, CopierCutterPaster> {
		MenuItem2CopyShapes(final Class<T> clazz, final MenuItem item) throws InstantiationException, IllegalAccessException {
			super(CopierCutterPaster.this, false, clazz, MenuItemPressed.class, item);
		}

		@Override
		public void initAction() {
			action.setSelection(getSelectAction().orElse(null));
		}

		@Override
		public boolean isConditionRespected() {
			return isShapeSelected.get();
		}
	}

	private class MenuItem2PasteShapes extends JfxMenuItemBinding<PasteShapes, MenuItemPressed, CopierCutterPaster> {
		MenuItem2PasteShapes() throws InstantiationException, IllegalAccessException {
			super(CopierCutterPaster.this, false, PasteShapes.class, MenuItemPressed.class, pasteMenu);
		}

		@Override
		public void initAction() {
			action.setCopy(getCopyCutAction().orElse(null));
			action.setDrawing(canvas.getDrawing());
			action.setGrid(grid);
		}

		@Override
		public boolean isConditionRespected() {
			return getCopyCutAction().isPresent();
		}
	}
}

