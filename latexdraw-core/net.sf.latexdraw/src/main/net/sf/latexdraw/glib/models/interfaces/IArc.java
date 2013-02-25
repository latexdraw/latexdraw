package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.lang.LangTool;

/**
 * Defines an interface that classes defining a elliptic arc should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IArc extends IEllipse {
	/** The different styles of arc. */
	public static enum ArcStyle {
		WEDGE {
			@Override
			public boolean supportArrow() { return false; }

			@Override
			public String getLabel() { return LangTool.INSTANCE.getStringOthers("Arc.arc"); }//$NON-NLS-1$
		}, ARC {
			@Override
			public boolean supportArrow() { return true; }

			@Override
			public String getLabel() { return LangTool.INSTANCE.getStringOthers("Arc.wedge"); }//$NON-NLS-1$
		}, CHORD {
			@Override
			public boolean supportArrow() { return false; }

			@Override
			public String getLabel() { return LangTool.INSTANCE.getStringOthers("Arc.chord"); }//$NON-NLS-1$
		};


		/**
		 * @return True if the arc type can have arrows.
		 * @since 3.0.0
		 */
		public abstract boolean supportArrow();


		/**
		 * @return The translated label of the arc type.
		 * @since 3.0
		 */
		public abstract String getLabel();
	}

	/**
	 * @return the style of the arc.
	 * @since 3.0.0
	 */
	ArcStyle getArcStyle();

	/**
	 * @param style the arc style to set.
	 * @since 3.0.0
	 */
	void setArcStyle(final ArcStyle style);

	/**
	 * @return the angleStart.
	 * @since 3.0.0
	 */
	double getAngleStart();

	/**
	 * @param angleStart the angleStart to set.
	 * @since 3.0.0
	 */
	void setAngleStart(final double angleStart);

	/**
	 * @return the angleEnd.
	 * @since 3.0.0
	 */
	double getAngleEnd();

	/**
	 * @param angleEnd the angleEnd to set.
	 * @since 3.0.0
	 */
	void setAngleEnd(final double angleEnd);

	/**
	 * @return The coordinate of the start point of the arc.
	 * @since 1.9
	 */
	IPoint getStartPoint();

	/**
	 * @return The coordinate of the end point of the arc.
	 * @since 1.9
	 */
	IPoint getEndPoint();
}
