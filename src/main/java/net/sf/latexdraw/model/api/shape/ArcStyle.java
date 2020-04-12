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
package net.sf.latexdraw.model.api.shape;

import java.util.ResourceBundle;
import javafx.scene.shape.ArcType;
import org.jetbrains.annotations.NotNull;

/**
 * The different styles of arc.
 * @author Arnaud Blouin
 */
public enum ArcStyle {
	WEDGE {
		@Override
		public boolean supportArrow() {
			return false;
		}

		@Override
		public @NotNull String getLabel(final @NotNull ResourceBundle bundle) {
			return bundle.getString("arc"); //NON-NLS
		}

		@Override
		public @NotNull ArcType getJFXStyle() {
			return ArcType.ROUND;
		}
	}, ARC {
		@Override
		public boolean supportArrow() {
			return true;
		}

		@Override
		public @NotNull String getLabel(final @NotNull ResourceBundle bundle) {
			return bundle.getString("wedge"); //NON-NLS
		}

		@Override
		public @NotNull ArcType getJFXStyle() {
			return ArcType.OPEN;
		}
	}, CHORD {
		@Override
		public boolean supportArrow() {
			return false;
		}

		@Override
		public @NotNull String getLabel(final @NotNull ResourceBundle bundle) {
			return bundle.getString("chord"); //NON-NLS
		}

		@Override
		public @NotNull ArcType getJFXStyle() {
			return ArcType.CHORD;
		}
	};

	/**
	 * @return True if the arc type can have arrows.
	 */
	public abstract boolean supportArrow();

	/**
	 * @return The internationalised label of the arc type.
	 */
	public abstract @NotNull String getLabel(final @NotNull ResourceBundle bundle);

	/**
	 * @return The JFX arc style corresponding to the current arc style.
	 */
	public abstract @NotNull ArcType getJFXStyle();
}
