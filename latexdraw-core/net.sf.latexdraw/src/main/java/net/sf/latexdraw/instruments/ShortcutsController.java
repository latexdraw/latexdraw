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
import net.sf.latexdraw.util.LangTool;

/**
 * The controller of the shortcuts dialogue box.
 * @author Arnaud BLOUIN
 */
public class ShortcutsController implements Initializable {
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
		final String ctrl = KeyEvent.getKeyModifiersText(InputEvent.CTRL_MASK);
		final String shift = KeyEvent.getKeyModifiersText(InputEvent.SHIFT_MASK);
		final String leftClick = LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.8");
		final String catEdit = LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.89");
		final String catNav = LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.4");
		final String catTran = LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.5");
		final String catDraw = LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.6");
		final String catFile = LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.88");

		for(int i = 0, size = table.getColumns().size(); i < size; i++) {
			final int colIndex = i;
			final TableColumn<ObservableList<String>, String> col = (TableColumn<ObservableList<String>, String>) table.getColumns().get(i);
			col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(colIndex)));
		}

		table.getColumns().forEach(col -> col.prefWidthProperty().bind(table.widthProperty().divide(3)));

		table.getItems().addAll(FXCollections.observableArrayList(ctrl + "+C", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.40"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+V", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.43"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+X", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.44"), catEdit), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+Z", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.23"), catEdit),  //NON-NLS
			FXCollections.observableArrayList(ctrl + "+Y", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.22"), catEdit),  //NON-NLS
			FXCollections.observableArrayList(ctrl + "+N", LangTool.INSTANCE.getBundle().getString("Res.2"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+O", LangTool.INSTANCE.getBundle().getString("FileLoaderSaver.3"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+S", LangTool.INSTANCE.getBundle().getString("FileLoaderSaver.1"), catFile), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+W", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.18"), catFile),  //NON-NLS
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_ADD), LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.57"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_SUBTRACT), LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.58"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DELETE), LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.17"), catDraw),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_RIGHT), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.9"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_LEFT), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.10"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_UP), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.11"), catNav),
			FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DOWN), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.12"), catNav),
			FXCollections.observableArrayList(ctrl + "+U", LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.23"), catTran), //NON-NLS
			FXCollections.observableArrayList(ctrl + "+A", LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.25"), catDraw), //NON-NLS
			FXCollections.observableArrayList(ctrl + '+' + leftClick, LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.26"), catDraw),
			FXCollections.observableArrayList(shift + '+' + leftClick, LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.27"), catDraw),
			FXCollections.observableArrayList(ctrl + '+' + LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.29"),
				LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.30"), catDraw)
		);
	}
}
