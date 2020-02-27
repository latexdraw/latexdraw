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
package net.sf.latexdraw.view.jfx;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Page;
import org.jetbrains.annotations.NotNull;

/**
 * The different page sizes that can be used.
 * @author Arnaud Blouin
 */
public class PageView extends Group {
	/** The gap between the page and its shadow. */
	private static final int GAP_SHADOW = 3;

	/** The size of the shadow of the page. */
	private static final int SIZE_SHADOW = 4;

	/** The main rectangle of the page. */
	private final Rectangle recPage;

	/** The shadow rectangle of the page. */
	private final Rectangle recShadowBottom;
	/** The shadow rectangle of the page. */

	private final Rectangle recShadowRight;

	/**
	 * Creates a view of a page.
	 * @param origin The origin point where the page has to be placed. Cannot be null.
	 */
	public PageView(final @NotNull PreferencesService prefs, final Point origin) {
		super();

		recPage = new Rectangle();
		recShadowBottom = new Rectangle();
		recShadowRight = new Rectangle();

		recPage.setStrokeWidth(1d);
		recPage.setStroke(Color.BLACK);
		recPage.setFill(null);
		recPage.setX(origin.getX());
		recPage.setY(origin.getY());

		recShadowRight.setStroke(null);
		recShadowBottom.setStroke(null);
		recShadowRight.setFill(Color.GRAY);
		recShadowBottom.setFill(Color.GRAY);

		getChildren().add(recShadowBottom);
		getChildren().add(recShadowRight);
		getChildren().add(recPage);

		recPage.toFront();
		setMouseTransparent(true);
		setFocusTraversable(false);

		update(prefs.getPage());
		prefs.pageProperty().addListener((observable, oldValue, newValue) -> update(newValue));
	}

	private final void update(final Page newPage) {
		recPage.setWidth(newPage.getWidth() * Shape.PPC);
		recPage.setHeight(newPage.getHeight() * Shape.PPC);

		recShadowRight.setX(recPage.getX() + recPage.getWidth());
		recShadowRight.setY(recPage.getY() + GAP_SHADOW);
		recShadowRight.setWidth(SIZE_SHADOW);
		recShadowRight.setHeight(recPage.getHeight());

		recShadowBottom.setX(recPage.getX() + GAP_SHADOW);
		recShadowBottom.setY(recPage.getY() + recPage.getHeight());
		recShadowBottom.setWidth(recPage.getWidth());
		recShadowBottom.setHeight(SIZE_SHADOW);
	}
}
