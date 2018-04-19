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
package net.sf.latexdraw.models.interfaces.shape;

import javafx.scene.shape.ArcType;
import net.sf.latexdraw.util.LangTool;

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
		public String getLabel() {
			return LangTool.INSTANCE.getBundle().getString("Arc.arc"); //NON-NLS
		}

		@Override
		public ArcType getJFXStyle() {
			return ArcType.ROUND;
		}
	}, ARC {
		@Override
		public boolean supportArrow() {
			return true;
		}

		@Override
		public String getLabel() {
			return LangTool.INSTANCE.getBundle().getString("Arc.wedge"); //NON-NLS
		}

		@Override
		public ArcType getJFXStyle() {
			return ArcType.OPEN;
		}
	}, CHORD {
		@Override
		public boolean supportArrow() {
			return false;
		}

		@Override
		public String getLabel() {
			return LangTool.INSTANCE.getBundle().getString("Arc.chord"); //NON-NLS
		}

		@Override
		public ArcType getJFXStyle() {
			return ArcType.CHORD;
		}
	};

	/**
	 * @return True if the arc type can have arrows.
	 * @since 3.0.0
	 */
	public abstract boolean supportArrow();

	/**
	 * @return The internationalised label of the arc type.
	 * @since 3.0
	 */
	public abstract String getLabel();

	/**
	 * @return The JFX arc style corresponding to the current arc style.
	 */
	public abstract ArcType getJFXStyle();
}
