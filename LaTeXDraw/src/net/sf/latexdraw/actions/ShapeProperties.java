package net.sf.latexdraw.actions;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;

/**
 * Defines shape properties.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public enum ShapeProperties {
	ARROW2_STYLE {
		@Override
		public String getMessage() {
			return "second arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}
	},
	ARROW1_STYLE {
		@Override
		public String getMessage() {
			return "first arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}
	},
	ROTATION_ANGLE {
		@Override
		public String getMessage() {
			return "rotation";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, TEXT_POSITION {
		@Override
		public String getMessage() {
			return "text position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IText.TextPosition;
		}
	}, HATCHINGS_ANGLE {
		@Override
		public String getMessage() {
			return "hatchings angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, HATCHINGS_WIDTH {
		@Override
		public String getMessage() {
			return "hatchings width";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, HATCHINGS_SEP {
		@Override
		public String getMessage() {
			return "hatchings spacing";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, GRAD_ANGLE {
		@Override
		public String getMessage() {
			return "gradient angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, GRAD_MID_POINT {
			@Override
			public String getMessage() {
				return "gradient middle point";
			}

			@Override
			public boolean isValueValid(final Object obj) {
				return obj instanceof Double;
			}
	}, ROUND_CORNER_VALUE {
		@Override
		public String getMessage() {
			return "corner roundness";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, ROUND_CORNER {
		@Override
		public String getMessage() {
			return "round corner";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}
	}, COLOUR_FILLING {
		@Override
		public String getMessage() {
			return "interior colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, COLOUR_LINE {
		@Override
		public String getMessage() {
			return "lines colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, COLOUR_HATCHINGS {
		@Override
		public String getMessage() {
			return "hatchings colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, DBLE_BORDERS {
		@Override
		public String getMessage() {
			return "double border";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}
	}, DBLE_BORDERS_SIZE {
		@Override
		public String getMessage() {
			return "double border size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, COLOUR_DBLE_BORD {
		@Override
		public String getMessage() {
			return "double border colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, SHADOW {
		@Override
		public String getMessage() {
			return "shadow";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}
	}, SHADOW_SIZE {
		@Override
		public String getMessage() {
			return "shadow size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, SHADOW_ANGLE {
		@Override
		public String getMessage() {
			return "shadow angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	}, COLOUR_SHADOW {
		@Override
		public String getMessage() {
			return "shadow colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, COLOUR_GRADIENT_START {
		@Override
		public String getMessage() {
			return "gradient start colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, COLOUR_GRADIENT_END {
		@Override
		public String getMessage() {
			return "gradient end colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	}, LINE_THICKNESS {
		@Override
		public String getMessage() {
			return "thickness";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer || obj instanceof Double || obj instanceof Float;
		}
	}, FILLING_STYLE {
		@Override
		public String getMessage() {
			return "filling style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof FillingStyle;
		}
	}, BORDER_POS {
		@Override
		public String getMessage() {
			return "border position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof BorderPos;
		}
	}, LINE_STYLE {
		@Override
		public String getMessage() {
			return "line style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof LineStyle;
		}
	}, DOT_STYLE {
		@Override
		public String getMessage() {
			return "dot style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof DotStyle;
		}
	}, DOT_SIZE {
		@Override
		public String getMessage() {
			return "dot size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer || obj instanceof Double || obj instanceof Float;
		}
	}, ARROW_START_STYLE {
		@Override
		public String getMessage() {
			return "starting arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}
	}, ARROW_END_STYLE {
		@Override
		public String getMessage() {
			return "ending arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}
	};


	/**
	 * @return The title of the properties.
	 * @since 3.0
	 */
	public abstract String getMessage();


	/**
	 * @param obj The new value to test.
	 * @return True if the given value can be set to the shape property.
	 * @since 3.0
	 */
	public abstract boolean isValueValid(final Object obj);
}
