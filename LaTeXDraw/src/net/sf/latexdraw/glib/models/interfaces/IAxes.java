package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.lang.LangTool;

/**
 * Defines an interface that classes defining latex axes should implement.<br>
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
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IAxes extends IStandardGrid {
	/** The interval between the labels and the axes. */
	double GAP_LABEL = 5.;


	/** Defines the different kinds of axes. */
	public static enum AxesStyle {
		AXES {
			@Override
			public String getLabel() { return LangTool.LANG.getString18("Axe.1"); } //$NON-NLS-1$
		}, FRAME {
			@Override
			public String getLabel() { return LangTool.LANG.getString18("Axe.2"); } //$NON-NLS-1$
		}, NONE {
			@Override
			public String getLabel() { return LangTool.LANG.getString18("Axe.0"); } //$NON-NLS-1$
		};

		/** The translated label of the style. */
		public abstract String getLabel();


		/**
		 * @param style The PST token or the name of the style (e.g. AXES.toString()) corresponding to the style to get.
		 * @return The corresponding style or null.
		 * @since 3.0
		 */
		public static AxesStyle getStyle(final String style) {
			return style==null ? null :
					style.equals(PSTricksConstants.TOKEN_AXES_STYLE_AXES) || style.equals(AXES.toString()) ? AXES :
					style.equals(PSTricksConstants.TOKEN_AXES_STYLE_FRAME) || style.equals(FRAME.toString()) ? FRAME :
					style.equals(PSTricksConstants.TOKEN_AXES_STYLE_NONE) || style.equals(NONE.toString()) ? NONE : null;
		}
	}


	/** Defines the different kinds of ticks. */
	public static enum TicksStyle {
		FULL {
			@Override
			public String getLabel() { return LangTool.LANG.getString18("Axe.3"); }//$NON-NLS-1$
		}, TOP {
			@Override
			public String getLabel() { return LangTool.LANG.getString18("Axe.4"); }//$NON-NLS-1$
		}, BOTTOM {
			@Override
			public String getLabel() { return LangTool.LANG.getString18("Axe.5"); }//$NON-NLS-1$
		};

		/** The translated label of the style. */
		public abstract String getLabel();


		/**
		 * @param style The style to check. Can be the PST token or the name of the style (e.g. FULL.toString()).
		 * @return The corresponding style or null.
		 * @since 3.0
		 */
		public static TicksStyle getStyle(final String style) {
			if(style==null) return null;
			if(PSTricksConstants.TOKEN_TICKS_STYLE_FULL.equals(style) || FULL.toString().equals(style)) return FULL;
			if(PSTricksConstants.TOKEN_TICKS_STYLE_TOP.equals(style) || TOP.toString().equals(style)) return TOP;
			if(PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM.equals(style) || BOTTOM.toString().equals(style)) return BOTTOM;
			return null;
		}
	}


	/** Defines the different style of labels. */
	public static enum PlottingStyle {
		ALL,
		X,
		Y,
		NONE;

		/**
		 * The style that corresponds to the given string.
		 * @param style The style to check.
		 * @return The corresponding style or null.
		 * @since 3.0
		 */
		public static PlottingStyle getStyle(final String style) {
			if(style==null) return null;
			if(style.equals(ALL.toString())) return ALL;
			if(style.equals(Y.toString())) return Y;
			if(style.equals(X.toString())) return X;
			if(style.equals(NONE.toString())) return NONE;
			return null;
		}
	}

	
	@Override
	IAxes duplicate();

	/**
	 * @return The X increment of the axes.
	 * @since 3.0
	 */
	double getIncrementX();


	/**
	 * @return The Y increment of the axes.
	 * @since 3.0
	 */
	double getIncrementY();


	/**
	 * @param increment the X increment to set.
	 */
	void setIncrementX(final double increment);

	/**
	 * @param increment the Y increment to set.
	 */
	void setIncrementY(final double increment);

	/**
	 * @return the distLabels.x.
	 */
	double getDistLabelsX();

	/**
	 * @return the distLabels.y.
	 */
	double getDistLabelsY();

	/**
	 * @param distLabelsX the distLabels.x to set.
	 */
	void setDistLabelsX(final double distLabelsX);

	/**
	 * @param distLabelsY the distLabels.y to set.
	 */
	void setDistLabelsY(final double distLabelsY);

	/**
	 * @return the labelsDisplayed.
	 */
	PlottingStyle getLabelsDisplayed();

	/**
	 * @param labelsDisplayed the labelsDisplayed to set.
	 */
	void setLabelsDisplayed(final PlottingStyle labelsDisplayed);

	/**
	 * @return the showOrigin.
	 */
	boolean isShowOrigin();

	/**
	 * @param showOrigin the showOrigin to set.
	 */
	void setShowOrigin(final boolean showOrigin);

	/**
	 * @return the ticksDisplayed.
	 */
	PlottingStyle getTicksDisplayed();

	/**
	 * @param ticksDisplayed the ticksDisplayed to set.
	 */
	void setTicksDisplayed(final PlottingStyle ticksDisplayed);

	/**
	 * @return the ticksStyle.
	 */
	TicksStyle getTicksStyle();

	/**
	 * @param ticksStyle the ticksStyle to set.
	 */
	void setTicksStyle(final TicksStyle ticksStyle);

	/**
	 * @return the ticksSize.
	 */
	double getTicksSize();

	/**
	 * @param ticksSize the ticksSize to set.
	 */
	void setTicksSize(final double ticksSize);

	/**
	 * @return the axesStyle.
	 */
	AxesStyle getAxesStyle();

	/**
	 * @param axesStyle the axesStyle to set.
	 */
	void setAxesStyle(final AxesStyle axesStyle);
}
