package net.sf.latexdraw.actions;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.Arcable.ArcStyle;
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
	/** Modification of the start angle of arcs. */
	ARC_START_ANGLE {
		@Override
		public String getMessage() {
			return "Arc start angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the end angle of arcs. */
	ARC_END_ANGLE {
		@Override
		public String getMessage() {
			return "Arc end angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the style of arcs. */
	ARC_STYLE {
		@Override
		public String getMessage() {
			return "Arc style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArcStyle;
		}
	},
	/** Defines if the shape has a second arrow. */
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
	/** Defines if the shape has a first arrow. */
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
	/** Modification of the rotation angle a shape. */
	ROTATION_ANGLE {
		@Override
		public String getMessage() {
			return "rotation";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the position of texts. */
	TEXT_POSITION {
		@Override
		public String getMessage() {
			return "text position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IText.TextPosition;
		}
	},
	/** Modification of the text. */
	TEXT {
		@Override
		public String getMessage() {
			return "text";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof String;
		}
	},
	/** Modification of the hatchings angle of shapes. */
	HATCHINGS_ANGLE {
		@Override
		public String getMessage() {
			return "hatchings angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the hatchings width a shape. */
	HATCHINGS_WIDTH {
		@Override
		public String getMessage() {
			return "hatchings width";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the hatching spacing a shape. */
	HATCHINGS_SEP {
		@Override
		public String getMessage() {
			return "hatchings spacing";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the gradient angle a shape. */
	GRAD_ANGLE {
		@Override
		public String getMessage() {
			return "gradient angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the middle point of the gradient a shape. */
	GRAD_MID_POINT {
			@Override
			public String getMessage() {
				return "gradient middle point";
			}

			@Override
			public boolean isValueValid(final Object obj) {
				return obj instanceof Double;
			}
	},
	/** Modification of the round corner value of a shape. */
	ROUND_CORNER_VALUE {
		@Override
		public String getMessage() {
			return "corner roundness";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the colour of the filling of a shape. */
	COLOUR_FILLING {
		@Override
		public String getMessage() {
			return "interior colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Modification of the colour of the borders of a shape. */
	COLOUR_LINE {
		@Override
		public String getMessage() {
			return "lines colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Modification of the colour of the hatchings of a shape. */
	COLOUR_HATCHINGS {
		@Override
		public String getMessage() {
			return "hatchings colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Defines if a shape must have double borders. */
	DBLE_BORDERS {
		@Override
		public String getMessage() {
			return "double border";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}
	},
	/** Modification of the size of the double borders of a shape. */
	DBLE_BORDERS_SIZE {
		@Override
		public String getMessage() {
			return "double border size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the colour of the double borders of a shape. */
	COLOUR_DBLE_BORD {
		@Override
		public String getMessage() {
			return "double border colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Defines if a shape must have a shadow. */
	SHADOW {
		@Override
		public String getMessage() {
			return "shadow";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}
	},
	/** Modification of the size of the shadow of a shape. */
	SHADOW_SIZE {
		@Override
		public String getMessage() {
			return "shadow size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the angle of the shadow of a shape. */
	SHADOW_ANGLE {
		@Override
		public String getMessage() {
			return "shadow angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of colour of the shadow of a shape. */
	COLOUR_SHADOW {
		@Override
		public String getMessage() {
			return "shadow colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Modification of the colour of the start gradient of a shape. */
	COLOUR_GRADIENT_START {
		@Override
		public String getMessage() {
			return "gradient start colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Modification of the colour of the end gradient of a shape. */
	COLOUR_GRADIENT_END {
		@Override
		public String getMessage() {
			return "gradient end colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}
	},
	/** Modification of the thickness of the borders of a shape. */
	LINE_THICKNESS {
		@Override
		public String getMessage() {
			return "thickness";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer || obj instanceof Double || obj instanceof Float;
		}
	},
	/** Modification of the filling style of a shape. */
	FILLING_STYLE {
		@Override
		public String getMessage() {
			return "filling style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof FillingStyle;
		}
	},
	/** Modification of the border position of a shape. */
	BORDER_POS {
		@Override
		public String getMessage() {
			return "border position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof BorderPos;
		}
	},
	/** Modification of the line style of a shape. */
	LINE_STYLE {
		@Override
		public String getMessage() {
			return "line style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof LineStyle;
		}
	},
	/** Modification of the style of a dot. */
	DOT_STYLE {
		@Override
		public String getMessage() {
			return "dot style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof DotStyle;
		}
	},
	/** Modification of the size of dots. */
	DOT_SIZE {
		@Override
		public String getMessage() {
			return "dot size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer || obj instanceof Double || obj instanceof Float;
		}
	},
	/** Modification of the style of the first arrow of a shape. */
	ARROW_START_STYLE {
		@Override
		public String getMessage() {
			return "starting arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}
	},
	/** Modification of the style of the second arrow of a shape. */
	ARROW_END_STYLE {
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
