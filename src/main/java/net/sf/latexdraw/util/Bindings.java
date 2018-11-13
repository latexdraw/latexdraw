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
package net.sf.latexdraw.util;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.WeakListener;
import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

public final class Bindings {
	public static final Bindings INSTANCE = new Bindings();

	private Bindings() {
		super();
	}

	/**
	 * Binds the content of listSrc to the list listSrc using the 'map' transformation function.
	 * @param listTarget The targeted list.
	 * @param listSrc The source list.
	 * @param map The transformation function.
	 * @param <S> The source type.
	 * @param <T> The target type.
	 */
	public <S, T> void bindListContent(final @NotNull ObservableList<T> listTarget, final @NotNull ListProperty<S> listSrc,
		final @NotNull Function<S, T> map) {
		listTarget.setAll(listSrc.stream().map(map).collect(Collectors.toList()));
		listSrc.addListener(new ListContentBinding<>(listTarget, map));
	}


	private static class ListContentBinding<T, S> implements ListChangeListener<S>, WeakListener {
		private final @NotNull WeakReference<List<T>> listRef;
		private final @NotNull Function<S, T> map;

		ListContentBinding(final @NotNull List<T> list, final @NotNull Function<S, T> map) {
			super();
			listRef = new WeakReference<>(list);
			this.map = map;
		}

		@Override
		public void onChanged(final ListChangeListener.Change<? extends S> change) {
			if(listRef.get() == null) {
				change.getList().removeListener(this);
			}else {
				processChange(change);
			}
		}

		private void processChange(final ListChangeListener.Change<? extends S> change) {
			final List<T> list = listRef.get();

			while(change.next()) {
				if(change.wasPermutated()) {
					// I do not think permutation is used by collections.
					throw new UnsupportedOperationException("Not implemented yet.");
				}
				if(change.wasRemoved()) {
					list.subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
				}
				if(change.wasAdded()) {
					list.addAll(change.getFrom(), change.getAddedSubList().stream().map(elt -> map.apply(elt)).collect(Collectors.toList()));
				}
			}
		}

		@Override
		public boolean wasGarbageCollected() {
			return listRef.get() == null;
		}
	}
}
