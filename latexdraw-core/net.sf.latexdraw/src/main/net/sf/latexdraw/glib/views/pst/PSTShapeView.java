package net.sf.latexdraw.glib.views.pst;

import static java.lang.Math.toDegrees;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.views.AbstractCodeView;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LShape model.<br>
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
 * 04/15/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
abstract class PSTShapeView<S extends IShape> extends AbstractCodeView<S> {
	/** The list of name of the colours added to the generated code. Useful when generating
	 * the code to define the colours in the latex document. */
	protected Set<String> coloursName;


	/**
	 * Creates and initialises an abstract PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTShapeView(final S model) {
		super(model);
	}


	/**
	 * Saves a colour coming from the generated code.
	 * @param name The name of the generated colour.
	 * @since 3.0
	 */
	protected void addColour(final String name) {
		if(name!=null) {
			if(coloursName==null)
				coloursName = new HashSet<>();
			if(!coloursName.contains(name))
				coloursName.add(name);
		}
	}


	@Override
	public void update() {
		//
	}


	/**
	 * Updates the cache.
	 * @param origin The origin point of the PST drawing.
	 * @param ppc The number of point per centimetre.
	 * @since 3.0
	 */
	public abstract void updateCache(final IPoint origin, final float ppc);




	/**
	 * @return The PST code corresponding to the arrow parameters of the shape. Or null if no arrow.
	 * @since 3.0
	 */
	protected StringBuilder getArrowsParametersCode() {
		StringBuilder code = null;

		if(shape.isArrowable()) {
			final ArrowStyle style1 = shape.getArrowStyle(0);
			final ArrowStyle style2 = shape.getArrowStyle(-1);

			if(style1==ArrowStyle.NONE) {
				if(style2!=ArrowStyle.NONE)
					code = getArrowParametersCode(shape.getArrowAt(-1));
			} else
				if(style2==ArrowStyle.NONE)
					code = getArrowParametersCode(shape.getArrowAt(0));
				else
					if(style1.isSameKind(style2))
						code = getArrowParametersCode(shape.getArrowAt(0));
					else {
						code = getArrowParametersCode(shape.getArrowAt(0));
						code.append(',').append(getArrowParametersCode(shape.getArrowAt(-1)));
					}
		}

		return code;
	}



	/**
	 * @return The PST code corresponding to the parameter of the style of the given arrow. The style of the
	 * given arrow must not be NONE.
	 */
	private StringBuilder getArrowParametersCode(final IArrow arrow) {
		StringBuilder code = new StringBuilder();
		final ArrowStyle style = arrow.getArrowStyle();

		if(style.isBar() || style.isRoundBracket() || style.isSquareBracket()) {
			code.append("tbarsize=").append(LNumber.INSTANCE.getCutNumber(arrow.getTBarSizeDim()/IShape.PPC)).append(PSTricksConstants.TOKEN_CM).append(' ').
			append(LNumber.INSTANCE.getCutNumber(arrow.getTBarSizeNum()));

			if(style.isSquareBracket())
				code.append(",bracketlength=").append(LNumber.INSTANCE.getCutNumber(arrow.getBracketNum()));
			else if(style.isRoundBracket())
				code.append(",rbracketlength=").append(LNumber.INSTANCE.getCutNumber(arrow.getRBracketNum()));
		}
		else if(style.isArrow())
			code.append("arrowsize=").append(LNumber.INSTANCE.getCutNumber(arrow.getArrowSizeDim()/IShape.PPC)).append(PSTricksConstants.TOKEN_CM).append(' ').
				 append(LNumber.INSTANCE.getCutNumber(arrow.getArrowSizeNum())).append(",arrowlength=").
				 append(LNumber.INSTANCE.getCutNumber(arrow.getArrowLength())).append(",arrowinset=").append(LNumber.INSTANCE.getCutNumber(arrow.getArrowInset()));
		else
			code.append("dotsize=").append(LNumber.INSTANCE.getCutNumber(arrow.getDotSizeDim()/IShape.PPC)).append(PSTricksConstants.TOKEN_CM).append(' ').
				 append(LNumber.INSTANCE.getCutNumber(arrow.getDotSizeNum()));

		return code;
	}



	/**
	 * @return The PST code corresponding to the style of the arrows (e.g. {|->}).
	 * @since 3.0
	 */
	protected StringBuilder getArrowsStyleCode() {
		StringBuilder code;

		if(shape.isArrowable()) {
			final ArrowStyle style1 = shape.getArrowStyle(0);
			final ArrowStyle style2 = shape.getArrowStyle(-1);

			if(style1==ArrowStyle.NONE && style2==ArrowStyle.NONE)
				code = null;
			else
				code = new StringBuilder().append('{').append(style1.getPSTToken()).append('-').append(style2.getPSTToken()).append('}');
		}
		else code = null;

		return code;
	}






	/**
	 * @return The PSTricks code of the show-points option or null.
	 * @since 3.0
	 */
	protected StringBuilder getShowPointsCode() {
		StringBuilder code;

		if(shape.isShowPts()) {
			code = new StringBuilder();
			code.append("showpoints=true"); //$NON-NLS-1$ //TODO add arrow params
		}
		else
			code = null;

		return code;
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @param position The reference point of the PSTricks drawing.
	 * @return The header of the PSTricks rotation code.
	 * @since 3.0
	 */
	protected StringBuilder getRotationHeaderCode(final float ppc, final IPoint position) {
		if(ppc<1 || !GLibUtilities.INSTANCE.isValidPoint(position))
			return null;

		StringBuilder code;
		final double angle = shape.getRotationAngle();

		if(!LNumber.INSTANCE.equals(angle, 0.)) {
			IPoint gravityCenter = shape.getGravityCentre();//FIXME doit merder pour les formes qui n'ont pas de gc (comme le texte)
			// car les vues java utilise le gc des borders.
			final double cx 	= (gravityCenter.getX() - position.getX()) / ppc;
			final double cy 	= (position.getY() - gravityCenter.getY()) / ppc;
			double x 			= LNumber.INSTANCE.getCutNumber(-Math.cos(-angle)*cx + Math.sin(-angle)*cy+cx);
			double y 			= LNumber.INSTANCE.getCutNumber(-Math.sin(-angle)*cx - Math.cos(-angle)*cy+cy);

			code = new StringBuilder();
			code.append("\\rput{").append((float)LNumber.INSTANCE.getCutNumber(-Math.toDegrees(shape.getRotationAngle())%360)).append('}').append('('); //$NON-NLS-1$
			code.append((float)x).append(',').append((float)y).append(')').append('{');
		}
		else
			code = null;

		return code;
	}


	/**
	 * @param colour The colour which name is looking for. If the colour does
	 * not exist yet, it is created.
	 * @return The name of a predefined or a newly generated colour.
	 * @since 3.0
	 */
	protected String getColourName(final Color colour) {
		String name = DviPsColors.INSTANCE.getColourName(colour);

		if(name==null)
			name = DviPsColors.INSTANCE.addUserColour(colour);

		addColour(name);

		return name;
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code of the double border of the shape.
	 * @since 3.0
	 */
	protected StringBuilder getDoubleBorderCode(final float ppc) {
		StringBuilder code;

		if(shape.hasDbleBord()) {
			final Color doubleColor = shape.getDbleBordCol();

			code = new StringBuilder();
			code.append("doubleline=true, doublesep=");//$NON-NLS-1$
			code.append((float)LNumber.INSTANCE.getCutNumber(shape.getDbleBordSep()/ppc));

			if(!doubleColor.equals(PSTricksConstants.DEFAULT_DOUBLE_COLOR))
				code.append(", doublecolor=").append(getColourName(doubleColor)); //$NON-NLS-1$
		}
		else
			code = null;

		return code;
	}



	/**
	 * @return The PSTricks code of the border position.
	 * @since 3.0
	 */
	protected StringBuilder getBorderPositionCode() {
		StringBuilder code;

		if(shape.isBordersMovable())
			switch(shape.getBordersPosition()) {
				case INTO:
					code = new StringBuilder().append("dimen=").append(PSTricksConstants.BORDERS_INSIDE); //$NON-NLS-1$
					break;

				case MID:
					code = new StringBuilder().append("dimen=").append(PSTricksConstants.BORDERS_MIDDLE); //$NON-NLS-1$
					break;

				case OUT:
					code = new StringBuilder().append("dimen=").append(PSTricksConstants.BORDERS_OUTSIDE); //$NON-NLS-1$
					break;

				default:
					code = null;
					break;
			}
		else
			code = null;

		return code;
	}



	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code of the line style.
	 * @since 1.7
	 */
	protected StringBuilder getLineCode(final float ppc) {
		final StringBuilder code = new StringBuilder();
		final Color linesColor   = shape.getLineColour();

		code.append("linecolor=").append(getColourName(linesColor)); //$NON-NLS-1$

		if(shape.isThicknessable()) {
			code.append(", linewidth=");//$NON-NLS-1$
			code.append((float)LNumber.INSTANCE.getCutNumber(shape.getThickness()/ppc));
		}

		switch(shape.getLineStyle()) {
			case DOTTED:
				code.append(", linestyle=");//$NON-NLS-1$
				code.append(PSTricksConstants.LINE_DOTTED_STYLE);
				code.append(", dotsep=");//$NON-NLS-1$
				code.append((float)LNumber.INSTANCE.getCutNumber(shape.getDotSep()/ppc));
				code.append(PSTricksConstants.TOKEN_CM);
				break;
			case DASHED:
				code.append(", linestyle=");//$NON-NLS-1$
				code.append(PSTricksConstants.LINE_DASHED_STYLE);
				code.append(", dash=");//$NON-NLS-1$
				code.append((float)LNumber.INSTANCE.getCutNumber(shape.getDashSepBlack()/ppc));
				code.append(PSTricksConstants.TOKEN_CM).append(' ');
				code.append((float)LNumber.INSTANCE.getCutNumber(shape.getDashSepWhite()/ppc));
				code.append(PSTricksConstants.TOKEN_CM);
				break;
			case SOLID:
			case NONE:
			default:
				break;
		}
		return code;
	}



	/**
	 * @return The PST code of the filling with parameter "plain".
	 * @since 3.0
	 */
	private StringBuilder getFillingPlain() {
		final Color interiorColor	= shape.getFillingCol();
		final StringBuilder code 	= new StringBuilder("fillstyle=solid"); //$NON-NLS-1$

		if(!interiorColor.equals(PSTricksConstants.DEFAULT_INTERIOR_COLOR))
			code.append(",fillcolor=").append(getColourName(interiorColor)); //$NON-NLS-1$

		return code;
	}


	/**
	 * @return The PST code of the filling with parameter "gradient".
	 * @since 3.0
	 */
	private StringBuilder getFillingGrad() {
		final Color gradStartCol = shape.getGradColStart();
		final Color gradEndCol   = shape.getGradColEnd();
		final float gradMidPt	 = (float)LNumber.INSTANCE.getCutNumber(shape.getGradMidPt());
		final float gradAngle	 = (float)LNumber.INSTANCE.getCutNumber(shape.getGradAngle());
		final StringBuilder code = new StringBuilder("fillstyle=gradient, gradlines=2000");//$NON-NLS-1$

		if(!gradStartCol.equals(PSTricksConstants.DEFAULT_GRADIENT_START_COLOR))
			code.append(", gradbegin=").append(getColourName(gradStartCol)); //$NON-NLS-1$

		if(!gradEndCol.equals(PSTricksConstants.DEFAULT_GRADIENT_END_COLOR))
			code.append(", gradend=").append(getColourName(gradEndCol)); //$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(gradMidPt, PSTricksConstants.DEFAULT_GRADIENT_MID_POINT))
			code.append(", gradmidpoint=").append(gradMidPt);//$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(toDegrees(gradAngle), PSTricksConstants.DEFAULT_GRADIENT_ANGLE))
			code.append(", gradangle=").append((float)LNumber.INSTANCE.getCutNumber(toDegrees(gradAngle)));//$NON-NLS-1$

		return code;
	}


	/**
	 * @return The PST code of the filling with parameter "hlines" or "vlines" etc.
	 * @since 3.0
	 */
	private StringBuilder getFillingHatchings(final float ppc) {
		final Color hatchingsCol = shape.getHatchingsCol();
		final StringBuilder code = new StringBuilder();
		final FillingStyle fillingStyle = shape.getFillingStyle();

		if(fillingStyle==FillingStyle.CLINES || fillingStyle==FillingStyle.CLINES_PLAIN)
			code.append("fillstyle=crosshatch"); //$NON-NLS-1$
		else
			if(fillingStyle==FillingStyle.HLINES || fillingStyle==FillingStyle.HLINES_PLAIN)
				code.append("fillstyle=hlines"); //$NON-NLS-1$
			else
				code.append("fillstyle=vlines"); //$NON-NLS-1$

		if(shape.isFilled())
			code.append('*');

		code.append(", hatchwidth="); //$NON-NLS-1$
		code.append((float)LNumber.INSTANCE.getCutNumber(shape.getHatchingsWidth()/ppc));
		code.append(", hatchangle=").append(LNumber.INSTANCE.getCutNumber(Math.toDegrees(shape.getHatchingsAngle()))); //$NON-NLS-1$
		code.append(", hatchsep="); //$NON-NLS-1$
		code.append((float)LNumber.INSTANCE.getCutNumber(shape.getHatchingsSep()/ppc));

		if(!hatchingsCol.equals(PSTricksConstants.DEFAULT_HATCHING_COLOR))
			code.append(", hatchcolor=").append(getColourName(hatchingsCol)); //$NON-NLS-1$

		return code;
	}



	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code for the filling of the shape. Null if there is no filling.
	 * @since 1.7
	 */
	protected StringBuilder getFillingCode(final float ppc) {
		StringBuilder code;
		final Color interiorColor = shape.getFillingCol();

		switch(shape.getFillingStyle()) {
			case NONE:
				code = null;
				break;
			case PLAIN:
				code = getFillingPlain();
				break;
			case GRAD:
				code = getFillingGrad();
				break;
			case CLINES:
			case CLINES_PLAIN:
			case HLINES:
			case HLINES_PLAIN:
			case VLINES:
			case VLINES_PLAIN:
				code = getFillingHatchings(ppc);
				break;

			default:
				code = null;
				break;
		}

		if(!shape.isFilled() && shape.hasShadow() && shape.shadowFillsShape() &&
			!interiorColor.equals(PSTricksConstants.DEFAULT_INTERIOR_COLOR)) {
			if(code==null)
				code = new StringBuilder();
			else
				code.append(',').append(' ');

			code.append("fillcolor=").append(getColourName(interiorColor)); //$NON-NLS-1$
		}

		return code;
	}



	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The code of the shape shadow or null if there is no shadow.
	 * @since 3.0
	 */
	protected StringBuilder getShadowCode(final float ppc) {
		StringBuilder code;

		if(shape.hasShadow()) {
			final Color shadowColor = shape.getShadowCol();

			code = new StringBuilder();
			code.append("shadow=true");//$NON-NLS-1$

			if(!LNumber.INSTANCE.equals(Math.toDegrees(shape.getShadowAngle()), PSTricksConstants.DEFAULT_SHADOW_ANGLE))
				code.append(",shadowangle="+(float)LNumber.INSTANCE.getCutNumber(Math.toDegrees(shape.getShadowAngle())));//$NON-NLS-1$

			code.append(",shadowsize="+(float)LNumber.INSTANCE.getCutNumber(shape.getShadowSize()/ppc));//$NON-NLS-1$

			if(!shadowColor.equals(PSTricksConstants.DEFAULT_SHADOW_COLOR))
				code.append(",shadowcolor=").append(getColourName(shadowColor)); //$NON-NLS-1$
		}
		else
			code = null;

		return code;
	}
}
