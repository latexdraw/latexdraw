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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.sf.latexdraw.util.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * The controller of the shortcuts dialogue box.
 * @author Arnaud BLOUIN
 */
public class ShortcutsController implements Initializable {
	private final @NotNull ResourceBundle lang;
	@FXML private TableView<ObservableList<String>> table;

	@Inject
	public ShortcutsController(final ResourceBundle lang) {
		super();
		this.lang = Objects.requireNonNull(lang);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final String ctrl = InputEvent.getModifiersExText(InputEvent.CTRL_DOWN_MASK);
		final String shift = InputEvent.getModifiersExText(InputEvent.SHIFT_DOWN_MASK);
		final String leftClick = lang.getString("leftClick");
		final String catEdit = lang.getString("edit");
		final String catNav = lang.getString("navigation");
		final String catTran = lang.getString("transformation");
		final String catDraw = lang.getString("drawing");
		final String catFile = lang.getString("file");

		for(int i = 0, size = table.getColumns().size(); i < size; i++) {
			final int colIndex = i;
			final TableColumn<ObservableList<String>, String> col = (TableColumn<ObservableList<String>, String>) table.getColumns().get(i);
			col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(colIndex)));
		}

		table.getColumns().forEach(col -> col.prefWidthProperty().bind(table.widthProperty().divide(3)));

		table.getItems().addAll(FXCollections.observableArrayList(ctrl + "+C", lang.getString("copy"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+V", lang.getString("paste"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+X", lang.getString("cut"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+Z", lang.getString("close"), catEdit),  //NON-NLS
			FXCollections.observableArrayList(ctrl + "+Y", lang.getString("redo"), catEdit),  //NON-NLS
			FXCollections.observableArrayList(ctrl + "+N", lang.getString("newDrawing"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+O", lang.getString("openDrawing"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+S", lang.getString("saveDrawing"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+W", lang.getString("quit"), catFile),  //NON-NLS
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_ADD), lang.getString("zoomIn"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_SUBTRACT), lang.getString("zoomOut"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DELETE), lang.getString("drawAxes"), catDraw),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_RIGHT), lang.getString("moveScrollbarRight"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_LEFT), lang.getString("moveScrollbarLeft"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_UP), lang.getString("moveScrollbarTop"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DOWN), lang.getString("moveScrollbarBottom"), catNav),
			FXCollections.observableArrayList(ctrl + "+U", lang.getString("updateShapes"), catTran), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+A", lang.getString("selectAll"), catDraw), //NON-NLS
			FXCollections.observableArrayList(ctrl + '+' + leftClick, lang.getString("addClickedShape"), catDraw),
			FXCollections.observableArrayList(shift + '+' + leftClick, lang.getString("removeClickedShape"), catDraw),
			FXCollections.observableArrayList(ctrl + '+' + lang.getString("mouseWheel"),
				lang.getString("zoom"), catDraw)
		);
	}
}
