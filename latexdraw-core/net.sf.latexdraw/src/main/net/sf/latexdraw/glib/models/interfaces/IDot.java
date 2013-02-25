package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

/**
 * Defines an interface that classes defining a dot should implement.<br>
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
public interface IDot extends IPositionShape, Dottable {
	/** Useful to calculate the thickness of dot with the o style. */
	double THICKNESS_O_STYLE_FACTOR = 16.;

	/** The thickness of the plus shape is computed with that coefficient. */
	double PLUS_COEFF_WIDTH = 6.5;


	/** The different styles of dot. */
	public static enum DotStyle {
		ASTERISK {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.ASTERISK_STYLE;
			}
		}, BAR {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.BAR_STYLE;
			}
		}, OPLUS {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.OPLUS_STYLE;
			}
		}, OTIMES {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.OTIMES_STYLE;
			}
		}, PLUS {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.PLUS_STYLE;
			}
		}, DOT {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.DOT_STYLE;
			}
		}, X {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.X_STYLE;
			}
		}, FDIAMOND {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.FDIAMOND_STYLE;
			}
		}, FTRIANGLE {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.FTRIANGLE_STYLE;
			}
		}, FPENTAGON {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.FPENTAGON_STYLE;
			}
		},
		DIAMOND {
			@Override
			public boolean isFillable() { return true; }

			@Override
			public String getPSTToken() {
				return PSTricksConstants.DIAMOND_STYLE;
			}
		}, O {
			@Override
			public boolean isFillable() { return true; }

			@Override
			public String getPSTToken() {
				return PSTricksConstants.O_STYLE;
			}
		}, TRIANGLE {
			@Override
			public boolean isFillable() { return true; }

			@Override
			public String getPSTToken() {
				return PSTricksConstants.TRIANGLE_STYLE;
			}
		}, PENTAGON {
			@Override
			public boolean isFillable() { return true; }

			@Override
			public String getPSTToken() {
				return PSTricksConstants.PENTAGON_STYLE;
			}
		}, SQUARE {
			@Override
			public boolean isFillable() { return true; }

			@Override
			public String getPSTToken() {
				return PSTricksConstants.SQUARE_STYLE;
			}
		}, FSQUARE {
			@Override
			public String getPSTToken() {
				return PSTricksConstants.FSQUARE_STYLE;
			}
		};

		/** 
		 * Allows to know if the dot shape can be filled.
		 * @return True if the dot can be filled.
		 */
		public boolean isFillable() { return false; }

		/**
		 * @return The PSTricks token corresponding to the dot style.
		 * @since 3.0
		 */
		public abstract String getPSTToken();

		/**
		 * @param styleName The style to get (in PST or the name of the style, e.g. FSQUARE.toString())
		 * @return The style which name is the given name style or null.
		 * @since 3.0
		 */
		public static DotStyle getStyle(final String styleName) {
			if(styleName==null)
				return null;

			if(FSQUARE.name().equals(styleName) || PSTricksConstants.FSQUARE_STYLE.equals(styleName)) 		return FSQUARE;
			if(SQUARE.name().equals(styleName) || PSTricksConstants.SQUARE_STYLE.equals(styleName))  		return SQUARE;
			if(PENTAGON.name().equals(styleName) || PSTricksConstants.PENTAGON_STYLE.equals(styleName)) 	return PENTAGON;
			if(TRIANGLE.name().equals(styleName) || PSTricksConstants.TRIANGLE_STYLE.equals(styleName)) 	return TRIANGLE;
			if(O.name().equals(styleName) || PSTricksConstants.O_STYLE.equals(styleName)) 					return O;
			if(DIAMOND.name().equals(styleName) || PSTricksConstants.DIAMOND_STYLE.equals(styleName)) 		return DIAMOND;
			if(FPENTAGON.name().equals(styleName) || PSTricksConstants.FPENTAGON_STYLE.equals(styleName)) 	return FPENTAGON;
			if(FTRIANGLE.name().equals(styleName) || PSTricksConstants.FTRIANGLE_STYLE.equals(styleName)) 	return FTRIANGLE;
			if(FDIAMOND.name().equals(styleName) || PSTricksConstants.FDIAMOND_STYLE.equals(styleName)) 	return FDIAMOND;
			if(X.name().equals(styleName) || PSTricksConstants.X_STYLE.equals(styleName)) 					return X;
			if(DOT.name().equals(styleName) || PSTricksConstants.DOT_STYLE.equals(styleName)) 				return DOT;
			if(PLUS.name().equals(styleName) || PSTricksConstants.PLUS_STYLE.equals(styleName)) 			return PLUS;
			if(OTIMES.name().equals(styleName) || PSTricksConstants.OTIMES_STYLE.equals(styleName)) 		return OTIMES;
			if(OPLUS.name().equals(styleName) || PSTricksConstants.OPLUS_STYLE.equals(styleName)) 			return OPLUS;
			if(BAR.name().equals(styleName) || PSTricksConstants.BAR_STYLE.equals(styleName)) 				return BAR;
			if(ASTERISK.name().equals(styleName) || PSTricksConstants.ASTERISK_STYLE.equals(styleName)) 	return ASTERISK;

			return null;
		}
	}
	
	
	@Override
	IDot duplicate();
	

	/**
	 * While getTopLeftPoint takes care about the current shape of the dot to compute the top left point,
	 * this function computes the top left point only using the centre and the width of the dot which are
	 * the same for all the dot styles
	 * @return The top left point of the dot.
	 * @since 3.0
	 */
	IPoint getLazyTopLeftPoint();

	/**
	 * While getBottomRightPoint takes care about the current shape of the dot to compute the bottom right point,
	 * this function computes the bottom right point only using the centre and the width of the dot which are
	 * the same for all the dot styles
	 * @return The top bottom right of the dot.
	 * @since 3.0
	 */
	IPoint getLazyBottomRightPoint();

	/**
	 * @return The gap used to create plus-shaped dots.
	 * @since 3.0
	 */
	double getPlusGap();

	/**
	 * @return The gap used to create cross-shaped dots.
	 * @since 3.0
	 */
	double getCrossGap();

	/**
	 * @return The gap used to create bar-shaped dots.
	 * @since 3.0
	 */
	double getBarGap();

	/**
	 * @return The thickness used to create bar-shaped dots.
	 * @since 3.0
	 */
	double getBarThickness();

	/**
	 * @return The gap used to compute the shape of several kinds of dot.
	 * @since 3.0
	 */
	double getGeneralGap();

	/**
	 * @return The gap used to create O-shaped dots.
	 * @since 3.0
	 */
	double getOGap();
}
