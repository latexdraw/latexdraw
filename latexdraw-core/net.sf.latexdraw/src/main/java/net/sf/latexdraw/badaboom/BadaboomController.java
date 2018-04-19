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
package net.sf.latexdraw.badaboom;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * The controller of the exceptions frame.
 * @author Arnaud Blouin
 */
public class BadaboomController implements Initializable {
	@FXML private TableView<Throwable> table;
	@FXML private TextArea stack;

	/**
	 * Creates the controller.
	 */
	public BadaboomController() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final ObservableList<TableColumn<Throwable, ?>> cols = table.getColumns();
		((TableColumn<Throwable, String>) cols.get(0)).setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().toString()));
		((TableColumn<Throwable, String>) cols.get(1)).setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getMessage()));
		((TableColumn<Throwable, String>) cols.get(2)).setCellValueFactory(cell -> {
			final StackTraceElement[] stackTrace = cell.getValue().getStackTrace();
			final String msg = stackTrace != null && stackTrace.length > 0 ? stackTrace[0].toString() : "";
			return new ReadOnlyStringWrapper(msg);
		});
		cols.forEach(col -> col.prefWidthProperty().bind(table.widthProperty().divide(3)));
		table.setItems(FXCollections.observableArrayList(BadaboomCollector.INSTANCE));
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		final Callable<String> converter = () -> {
			final Throwable ex = table.getSelectionModel().getSelectedItem();
			if(ex == null) {
				return "";
			}
			return Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat ", ex.toString() + "\n\tat ", "")); //NON-NLS
		};
		stack.textProperty().bind(Bindings.createStringBinding(converter, table.getSelectionModel().selectedItemProperty()));
	}
}
