package net.sf.latexdraw.glib.views.jfx.ui;

import java.util.Optional;

import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A JFX helper.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-10-20<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public final class JFXUtil {
	/** The singleton. */
	public static final JFXUtil INSTANCE = new JFXUtil();
	
	private JFXUtil() {
		super();
	}

	
	/**
	 * Creates an image view from the given picture. The given object is associated to the image view as the user data.
	 * @param userData The object to map with the picture.
	 * @param pic The path of the picture.
	 * @return The created image view.
	 * @throws NullPointerException If the given path is null.
	 * @throws IllegalArgumentException If the given path is not valid.
	 */
	public ImageView createItem(final Object userData, final String pic) {
		final ImageView iv = new ImageView(new Image(pic));
		iv.setUserData(userData);
		return iv;
	}
	
	
	/**
	 * Search for an image view having the given object as user data.
	 * @param cb The combo box to analyse.
	 * @param userData The object to look for. 
	 * @return The image view or empty.
	 */
	public Optional<ImageView> getItem(final ComboBox<ImageView> cb, final Object userData) {
		return cb.getItems().stream().filter(it -> it.getUserData()==userData).findFirst();
	}
}
