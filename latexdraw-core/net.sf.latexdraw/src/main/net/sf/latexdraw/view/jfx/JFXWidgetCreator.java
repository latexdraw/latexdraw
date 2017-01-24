package net.sf.latexdraw.view.jfx;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.eclipse.jdt.annotation.NonNull;

/**
 * A trait for creating items.
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 2014-10-20
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public interface JFXWidgetCreator {
	default <T> void initComboBox(final ComboBox<T> box, final @NonNull Map<T, Image> map, T[] values) {
		ComboBoxFactoryList<T> factory = new ComboBoxFactoryList<>(map);
		box.getItems().addAll(values);
		box.setButtonCell(factory.call(null));
		box.setCellFactory(factory);
	}


	class ComboBoxFactoryList<T> implements Callback<ListView<T>, ListCell<T>> {
		final Map<T, Image> cache;

		public ComboBoxFactoryList(final @NonNull Map<T, Image> itemMap) {
			super();
			cache = new HashMap<>();
			cache.putAll(itemMap);
		}

		@Override
		public ListCell<T> call(ListView<T> p) {
			return new ListCell<T>() {
				@Override
				protected void updateItem(T item, boolean empty) {
					super.updateItem(item, empty);
					if(item != null && !empty) {
						if(cache.get(item) == null) System.out.println(item);
						setGraphic(new ImageView(cache.get(item)));
					}
				}
			};
		}
	}
}
