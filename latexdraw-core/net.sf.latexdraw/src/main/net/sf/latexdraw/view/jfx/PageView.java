package net.sf.latexdraw.view.jfx;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Page;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The different page sizes that can be used. <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 * @since 4.0 <br>
 * @author Arnaud Blouin
 * @date 2014-10-15
 */
public class PageView extends Group {
	/** The gap between the page and its shadow. */
	private static final int GAP_SHADOW = 3;

	/** The size of the shadow of the page. */
	private static final int SIZE_SHADOW = 4;

	/** The main rectangle of the page. */
	private final @NonNull Rectangle recPage;

	/** The shadow rectangle of the page. */
	private final @NonNull Rectangle recShadowBottom;
	/** The shadow rectangle of the page. */

	private final @NonNull Rectangle recShadowRight;

	/** The origin point where the page has to ben placed. */
	private final @NonNull IPoint origin;

	/** The current page format. */
	private @NonNull Page format;

	/**
	 * Creates a view of a page.
	 * @param page The page format. Cannot be null.
	 * @param orig The origin point where the page has to be placed. Cannot be null.
	 */
	public PageView(@NonNull final Page page, @NonNull final IPoint orig) {
		super();

		format = page;
		origin = orig;
		recPage = new Rectangle();
		recShadowBottom = new Rectangle();
		recShadowRight = new Rectangle();

		recPage.setStrokeWidth(1.0);
		recPage.setStroke(Color.BLACK);
		recPage.setFill(null);
		recShadowRight.setStroke(null);
		recShadowBottom.setStroke(null);
		recShadowRight.setFill(Color.GRAY);
		recShadowBottom.setFill(Color.GRAY);

		getChildren().add(recShadowBottom);
		getChildren().add(recShadowRight);
		getChildren().add(recPage);

		recPage.toFront();
		setMouseTransparent(true);
		setPage(page);
	}

	/**
	 * @return The current page format.
	 */
	public @NonNull Page getPage() {
		return format;
	}

	/**
	 * Sets the current page format.
	 * @param page The new page format to use. Cannot be null.
	 */
	public void setPage(@NonNull final Page page) {
		recPage.setX(origin.getX());
		recPage.setY(origin.getY());
		recPage.setWidth(page.getWidth() * IShape.PPC);
		recPage.setHeight(page.getHeight() * IShape.PPC);

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
