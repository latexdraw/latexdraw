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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LangService;

/**
 * The controller of the shortcuts dialogue box.
 * @author Arnaud BLOUIN
 */
public class ShortcutsController implements Initializable {
	@Inject private LangService lang;
	@FXML private TableView<ObservableList<String>> table;

	/**
	 * Creates the controller.
	 */
	public ShortcutsController() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final String ctrl = InputEvent.getModifiersExText(InputEvent.CTRL_DOWN_MASK);
		final String shift = InputEvent.getModifiersExText(InputEvent.SHIFT_DOWN_MASK);
		final String leftClick = lang.getBundle().getString("ShortcutsFrame.8");
		final String catEdit = lang.getBundle().getString("LaTeXDrawFrame.89");
		final String catNav = lang.getBundle().getString("ShortcutsFrame.4");
		final String catTran = lang.getBundle().getString("ShortcutsFrame.5");
		final String catDraw = lang.getBundle().getString("ShortcutsFrame.6");
		final String catFile = lang.getBundle().getString("LaTeXDrawFrame.88");

		for(int i = 0, size = table.getColumns().size(); i < size; i++) {
			final int colIndex = i;
			final TableColumn<ObservableList<String>, String> col = (TableColumn<ObservableList<String>, String>) table.getColumns().get(i);
			col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(colIndex)));
		}

		table.getColumns().forEach(col -> col.prefWidthProperty().bind(table.widthProperty().divide(3)));

		table.getItems().addAll(FXCollections.observableArrayList(ctrl + "+C", lang.getBundle().getString("LaTeXDrawFrame.40"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+V", lang.getBundle().getString("LaTeXDrawFrame.43"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+X", lang.getBundle().getString("LaTeXDrawFrame.44"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+Z", lang.getBundle().getString("LaTeXDrawFrame.23"), catEdit),  //NON-NLS
			FXCollections.observableArrayList(ctrl + "+Y", lang.getBundle().getString("LaTeXDrawFrame.22"), catEdit),  //NON-NLS
			FXCollections.observableArrayList(ctrl + "+N", lang.getBundle().getString("Res.2"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+O", lang.getBundle().getString("FileLoaderSaver.3"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+S", lang.getBundle().getString("FileLoaderSaver.1"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+W", lang.getBundle().getString("LaTeXDrawFrame.18"), catFile),  //NON-NLS
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_ADD), lang.getBundle().getString("LaTeXDrawFrame.57"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_SUBTRACT), lang.getBundle().getString("LaTeXDrawFrame.58"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DELETE), lang.getBundle().getString("LaTeXDrawFrame.17"), catDraw),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_RIGHT), lang.getBundle().getString("ShortcutsFrame.9"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_LEFT), lang.getBundle().getString("ShortcutsFrame.10"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_UP), lang.getBundle().getString("ShortcutsFrame.11"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DOWN), lang.getBundle().getString("ShortcutsFrame.12"), catNav),
			FXCollections.observableArrayList(ctrl + "+U", lang.getBundle().getString("ShortcutsFrame.23"), catTran), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+A", lang.getBundle().getString("ShortcutsFrame.25"), catDraw), //NON-NLS
			FXCollections.observableArrayList(ctrl + '+' + leftClick, lang.getBundle().getString("ShortcutsFrame.26"), catDraw),
			FXCollections.observableArrayList(shift + '+' + leftClick, lang.getBundle().getString("ShortcutsFrame.27"), catDraw),
			FXCollections.observableArrayList(ctrl + '+' + lang.getBundle().getString("ShortcutsFrame.29"),
				lang.getBundle().getString("ShortcutsFrame.30"), catDraw)
		);
	}
}
