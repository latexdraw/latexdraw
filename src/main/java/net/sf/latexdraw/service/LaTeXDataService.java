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
package net.sf.latexdraw.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.jetbrains.annotations.NotNull;
import org.malai.properties.Modifiable;

public class LaTeXDataService implements Modifiable {
	/**
	 * The latex packages used when exporting using latex.
	 * These packages are defined for the current document but not for all documents.
	 */
	private final @NotNull ObjectProperty<String> packages;
	/** The comment of the drawing. */
	private @NotNull String comment;
	/** The label of the drawing. */
	private @NotNull String label;
	/** The caption of the drawing. */
	private @NotNull String caption;
	/** The token of the position of the drawing */
	private @NotNull VerticalPosition positionVertToken;
	/** The horizontal position of the drawing */
	private boolean positionHoriCentre;
	/** The scale of the drawing. */
	private double scale;
	private boolean modified;

	public LaTeXDataService() {
		super();
		modified = false;
		comment = ""; //NON-NLS
		label = ""; //NON-NLS
		caption = ""; //NON-NLS
		positionHoriCentre = false;
		positionVertToken = VerticalPosition.NONE;
		scale = 1d;
		packages = new SimpleObjectProperty<>("");
	}

	/**
	 * @param pkgs the packages to set.
	 */
	public void setPackages(final @NotNull String pkgs) {
		if(!pkgs.equals(getPackages())) {
			packages.setValue(pkgs);
		}
	}

	/**
	 * @return the packages.
	 */
	public @NotNull String getPackages() {
		return packages.getValue();
	}

	/**
	 * @return the scale of the drawing.
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * @param sc the scale to set.
	 */
	public void setScale(final double sc) {
		if(sc >= 0.1) {
			scale = sc;
		}
	}

	/**
	 * @return the comment.
	 */
	public @NotNull String getComment() {
		return comment;
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * @param newComments the comment to set. Nothing done if null.
	 */
	public void setComment(final @NotNull String newComments) {
		if(!comment.equals(newComments)) {
			comment = newComments;
			setModified(true);
		}
	}

	/**
	 * @return The comments with the '%' tag at the beginning of each line. Cannot be null.
	 */
	public @NotNull String getCommentWithTag() {
		return Stream.of(comment.split(SystemUtils.getInstance().eol)).map(commentLine -> "% " + commentLine).collect(Collectors.joining(SystemUtils.getInstance().eol));
	}

	/**
	 * @return The latex token corresponding to the specified vertical position.
	 */
	public @NotNull VerticalPosition getPositionVertToken() {
		return positionVertToken;
	}

	/**
	 * @param positionVert The new vertical position token. Must not be null.
	 */
	public void setPositionVertToken(final @NotNull VerticalPosition positionVert) {
		positionVertToken = positionVert;
		setModified(true);
	}

	/**
	 * @return True: the latex drawing will be horizontally centred.
	 */
	public boolean isPositionHoriCentre() {
		return positionHoriCentre;
	}

	/**
	 * @return the label of the latex drawing.
	 */
	public @NotNull String getLabel() {
		return label;
	}

	/**
	 * @param lab the new lab of the drawing. Must not be null.
	 */
	public void setLabel(final @NotNull String lab) {
		label = lab;
		setModified(true);
	}

	/**
	 * @return the caption of the drawing.
	 */
	public @NotNull String getCaption() {
		return caption;
	}

	/**
	 * @param theCaption the new caption of the drawing. Must not be null.
	 */
	public void setCaption(final @NotNull String theCaption) {
		caption = theCaption;
		setModified(true);
	}

	/**
	 * @param position True: the latex drawing will be horizontally centred.
	 */
	public void setPositionHoriCentre(final boolean position) {
		if(positionHoriCentre != position) {
			positionHoriCentre = position;
			setModified(true);
		}
	}
}
