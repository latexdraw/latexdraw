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
package net.sf.latexdraw.view.pst;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;

import static java.lang.Math.toDegrees;

/**
 * Defines a PSTricks view of the LShape model.
 * @author Arnaud Blouin
 */
public abstract class PSTShapeView<S extends IShape> {
	/** The shape model. */
	protected final S shape;

	/**
	 * The list of name of the colours added to the generated code. Useful when generating
	 * the code to define the colours in the latex document.
	 */
	protected Set<String> coloursName;


	/**
	 * Creates and initialises an abstract PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTShapeView(final S model) {
		super();
		shape = Objects.requireNonNull(model);
	}


	/**
	 * @return The mode of the view.
	 */
	public S getShape() {
		return shape;
	}


	/**
	 * Saves a colour coming from the generated code.
	 * @param name The name of the generated colour.
	 * @since 3.0
	 */
	protected void addColour(final String name) {
		if(name != null) {
			if(coloursName == null) {
				coloursName = new HashSet<>();
			}
			coloursName.add(name);
		}
	}


	public abstract String getCode(final IPoint origin, final float ppc);


	/**
	 * @return The PST code corresponding to the arrow parameters of the shape. Or null if no arrow.
	 * @since 3.0
	 */
	protected StringBuilder getArrowsParametersCode() {
		StringBuilder code = null;

		if(shape instanceof IArrowableSingleShape) {
			final IArrowableSingleShape arr = (IArrowableSingleShape) shape;
			final ArrowStyle style1 = arr.getArrowStyle(0);
			final ArrowStyle style2 = arr.getArrowStyle(-1);

			if(style1 == ArrowStyle.NONE) {
				if(style2 != ArrowStyle.NONE) {
					code = getArrowParametersCode(arr.getArrowAt(-1));
				}
			}else if(style2 == ArrowStyle.NONE) {
				code = getArrowParametersCode(arr.getArrowAt(0));
			}else if(style1.isSameKind(style2)) {
				code = getArrowParametersCode(arr.getArrowAt(0));
			}else {
				code = getArrowParametersCode(arr.getArrowAt(0));
				code.append(',').append(getArrowParametersCode(arr.getArrowAt(-1)));
			}
		}

		return code;
	}


	/**
	 * @return The PST code corresponding to the parameter of the style of the given arrow. The style of the
	 * given arrow must not be NONE.
	 */
	private StringBuilder getArrowParametersCode(final IArrow arrow) {
		final StringBuilder code = new StringBuilder();
		final ArrowStyle style = arrow.getArrowStyle();

		if(style.isBar() || style.isRoundBracket() || style.isSquareBracket()) {
			code.append("tbarsize=").append(MathUtils.INST.getCutNumberFloat(arrow.getTBarSizeDim() / IShape.PPC)).append(PSTricksConstants.TOKEN_CM).append(' '). //NON-NLS
				append(MathUtils.INST.getCutNumberFloat(arrow.getTBarSizeNum()));

			if(style.isSquareBracket()) {
				code.append(",bracketlength=").append(MathUtils.INST.getCutNumberFloat(arrow.getBracketNum())); //NON-NLS
			}else if(style.isRoundBracket()) {
				code.append(",rbracketlength=").append(MathUtils.INST.getCutNumberFloat(arrow.getRBracketNum())); //NON-NLS
			}
		}else if(style.isArrow()) {
			code.append("arrowsize=").append(MathUtils.INST.getCutNumberFloat(arrow.getArrowSizeDim() / IShape.PPC)).append(PSTricksConstants.TOKEN_CM).append(' '). //NON-NLS
				append(MathUtils.INST.getCutNumberFloat(arrow.getArrowSizeNum())).append(",arrowlength="). //NON-NLS
				append(MathUtils.INST.getCutNumberFloat(arrow.getArrowLength())).append(",arrowinset=").append(MathUtils.INST.getCutNumberFloat(arrow.getArrowInset())); //NON-NLS
		}else {
			code.append("dotsize=").append(MathUtils.INST.getCutNumberFloat(arrow.getDotSizeDim() / IShape.PPC)).append(PSTricksConstants.TOKEN_CM).append(' '). //NON-NLS
				append(MathUtils.INST.getCutNumberFloat(arrow.getDotSizeNum()));
		}

		return code;
	}


	/**
	 * @return The PST code corresponding to the style of the arrows (e.g. {|->}).
	 * @since 3.0
	 */
	protected StringBuilder getArrowsStyleCode() {
		final StringBuilder code;

		if(shape instanceof IArrowableSingleShape) {
			final IArrowableSingleShape arr = (IArrowableSingleShape) shape;
			final ArrowStyle style1 = arr.getArrowStyle(0);
			final ArrowStyle style2 = arr.getArrowStyle(-1);

			if(style1 == ArrowStyle.NONE && style2 == ArrowStyle.NONE) {
				code = null;
			}else {
				code = new StringBuilder().append('{').append(style1.getPSTToken()).append('-').append(style2.getPSTToken()).append('}');
			}
		}else {
			code = null;
		}

		return code;
	}


	/**
	 * @return The PSTricks code of the show-points option or null.
	 * @since 3.0
	 */
	protected StringBuilder getShowPointsCode() {
		final StringBuilder code;

		if(shape.isShowPts()) {
			code = new StringBuilder();
			code.append("showpoints=true"); //NON-NLS //TODO add arrow params
		}else {
			code = null;
		}

		return code;
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @param position The reference point of the PSTricks drawing.
	 * @return The header of the PSTricks rotation code.
	 * @since 3.0
	 */
	protected StringBuilder getRotationHeaderCode(final float ppc, final IPoint position) {
		if(ppc < 1 || !MathUtils.INST.isValidPt(position)) {
			return null;
		}

		final StringBuilder code;
		final double angle = shape.getRotationAngle();

		if(MathUtils.INST.equalsDouble(angle, 0.0)) {
			code = null;
		}else {
			final IPoint gravityCenter = shape.getGravityCentre();
			final double cx = (gravityCenter.getX() - position.getX()) / ppc;
			final double cy = (position.getY() - gravityCenter.getY()) / ppc;
			final double x = MathUtils.INST.getCutNumberFloat(-Math.cos(-angle) * cx + Math.sin(-angle) * cy + cx);
			final double y = MathUtils.INST.getCutNumberFloat(-Math.sin(-angle) * cx - Math.cos(-angle) * cy + cy);

			code = new StringBuilder();
			code.append("\\rput{").append(MathUtils.INST.getCutNumberFloat(-Math.toDegrees(shape.getRotationAngle()) % 360)).append('}').append('('); //NON-NLS
			code.append((float) x).append(',').append((float) y).append(')').append('{');
		}

		return code;
	}


	/**
	 * @param colour The colour which name is looking for. If the colour does
	 * not exist yet, it is created.
	 * @return The name of a predefined or a newly generated colour.
	 * @since 3.0
	 */
	protected String getColourName(final Color colour) {
		final String name = DviPsColors.INSTANCE.getColourName(colour).orElseGet(() -> DviPsColors.INSTANCE.addUserColour(colour).orElse(""));
		addColour(name);
		return name;
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code of the double border of the shape.
	 * @since 3.0
	 */
	protected StringBuilder getDoubleBorderCode(final float ppc) {
		final StringBuilder code;

		if(shape.hasDbleBord()) {
			final Color doubleColor = shape.getDbleBordCol();

			code = new StringBuilder();
			code.append("doubleline=true, doublesep="); //NON-NLS
			code.append(MathUtils.INST.getCutNumberFloat(shape.getDbleBordSep() / ppc));

			if(!doubleColor.equals(PSTricksConstants.DEFAULT_DOUBLE_COLOR)) {
				code.append(", doublecolor=").append(getColourName(doubleColor)); //NON-NLS
			}
		}else {
			code = null;
		}

		return code;
	}


	/**
	 * @return The PSTricks code of the border position.
	 * @since 3.0
	 */
	protected StringBuilder getBorderPositionCode() {
		final StringBuilder code;

		if(shape.isBordersMovable()) {
			switch(shape.getBordersPosition()) {
				case INTO:
					code = new StringBuilder().append("dimen=").append(PSTricksConstants.BORDERS_INSIDE); //NON-NLS
					break;
				case MID:
					code = new StringBuilder().append("dimen=").append(PSTricksConstants.BORDERS_MIDDLE); //NON-NLS
					break;
				case OUT:
					code = new StringBuilder().append("dimen=").append(PSTricksConstants.BORDERS_OUTSIDE); //NON-NLS
					break;
				default:
					code = null;
					break;
			}
		}else {
			code = null;
		}

		return code;
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code of the line style.
	 * @since 1.7
	 */
	protected StringBuilder getLineCode(final float ppc) {
		final StringBuilder code = new StringBuilder();
		final Color linesColor = shape.getLineColour();

		code.append("linecolor=").append(getColourName(linesColor)); //NON-NLS

		if(shape.isThicknessable()) {
			code.append(", linewidth=").append(MathUtils.INST.getCutNumberFloat(shape.getThickness() / ppc)); //NON-NLS
		}

		if(linesColor.getO() < 1.0) {
			code.append(", strokeopacity=").append(MathUtils.INST.getCutNumberFloat(linesColor.getO())); //NON-NLS
		}

		switch(shape.getLineStyle()) {
			case DOTTED:
				code.append(", linestyle="); //NON-NLS
				code.append(PSTricksConstants.LINE_DOTTED_STYLE);
				code.append(", dotsep="); //NON-NLS
				code.append(MathUtils.INST.getCutNumberFloat(shape.getDotSep() / ppc));
				code.append(PSTricksConstants.TOKEN_CM);
				break;
			case DASHED:
				code.append(", linestyle="); //NON-NLS
				code.append(PSTricksConstants.LINE_DASHED_STYLE);
				code.append(", dash="); //NON-NLS
				code.append(MathUtils.INST.getCutNumberFloat(shape.getDashSepBlack() / ppc));
				code.append(PSTricksConstants.TOKEN_CM).append(' ');
				code.append(MathUtils.INST.getCutNumberFloat(shape.getDashSepWhite() / ppc));
				code.append(PSTricksConstants.TOKEN_CM);
				break;
			case SOLID:
		}
		return code;
	}


	/**
	 * @return The PST code of the filling with parameter "plain".
	 * @since 3.0
	 */
	private StringBuilder getFillingPlain() {
		final Color interiorColor = shape.getFillingCol();
		final StringBuilder code = new StringBuilder("fillstyle=solid"); //NON-NLS

		if(!interiorColor.equals(PSTricksConstants.DEFAULT_INTERIOR_COLOR)) {
			code.append(",fillcolor=").append(getColourName(interiorColor)); //NON-NLS
		}

		if(interiorColor.getO() < 1.0) {
			code.append(", opacity=").append(MathUtils.INST.getCutNumberFloat(interiorColor.getO())); //NON-NLS
		}

		return code;
	}


	/**
	 * @return The PST code of the filling with parameter "gradient".
	 * @since 3.0
	 */
	private StringBuilder getFillingGrad() {
		final Color gradStartCol = shape.getGradColStart();
		final Color gradEndCol = shape.getGradColEnd();
		final float gradMidPt = MathUtils.INST.getCutNumberFloat(shape.getGradMidPt());
		final float gradAngle = MathUtils.INST.getCutNumberFloat(shape.getGradAngle());
		final StringBuilder code = new StringBuilder("fillstyle=gradient, gradlines=2000"); //NON-NLS

		if(!gradStartCol.equals(PSTricksConstants.DEFAULT_GRADIENT_START_COLOR)) {
			code.append(", gradbegin=").append(getColourName(gradStartCol)); //NON-NLS
		}

		if(!gradEndCol.equals(PSTricksConstants.DEFAULT_GRADIENT_END_COLOR)) {
			code.append(", gradend=").append(getColourName(gradEndCol)); //NON-NLS
		}

		if(!MathUtils.INST.equalsDouble(gradMidPt, PSTricksConstants.DEFAULT_GRADIENT_MID_POINT)) {
			code.append(", gradmidpoint=").append(gradMidPt); //NON-NLS
		}

		if(!MathUtils.INST.equalsDouble(toDegrees(gradAngle), PSTricksConstants.DEFAULT_GRADIENT_ANGLE)) {
			code.append(", gradangle=").append(MathUtils.INST.getCutNumberFloat(toDegrees(gradAngle))); //NON-NLS
		}

		return code;
	}


	/**
	 * @return The PST code of the filling with parameter "hlines" or "vlines" etc.
	 * @since 3.0
	 */
	private StringBuilder getFillingHatchings(final float ppc) {
		final Color hatchingsCol = shape.getHatchingsCol();
		final StringBuilder code = new StringBuilder();

		switch(shape.getFillingStyle()) {
			case CLINES:
			case CLINES_PLAIN:
				code.append("fillstyle=crosshatch"); //NON-NLS
				break;
			case HLINES:
			case HLINES_PLAIN:
				code.append("fillstyle=hlines"); //NON-NLS
				break;
			default:
				code.append("fillstyle=vlines"); //NON-NLS
				break;
		}

		if(shape.isFilled()) {
			code.append('*');
		}

		code.append(", hatchwidth="); //NON-NLS
		code.append(MathUtils.INST.getCutNumberFloat(shape.getHatchingsWidth() / ppc));
		code.append(", hatchangle=").append(MathUtils.INST.getCutNumberFloat(Math.toDegrees(shape.getHatchingsAngle()))); //NON-NLS
		code.append(", hatchsep="); //NON-NLS
		code.append(MathUtils.INST.getCutNumberFloat(shape.getHatchingsSep() / ppc));

		if(!hatchingsCol.equals(PSTricksConstants.DEFAULT_HATCHING_COLOR)) {
			code.append(", hatchcolor=").append(getColourName(hatchingsCol)); //NON-NLS
		}

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

		if(!shape.isFilled() && shape.hasShadow() && shape.shadowFillsShape() && !interiorColor.equals(PSTricksConstants.DEFAULT_INTERIOR_COLOR)) {
			if(code == null) {
				code = new StringBuilder();
			}else {
				code.append(',').append(' ');
			}

			code.append("fillcolor=").append(getColourName(interiorColor)); //NON-NLS
		}

		return code;
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The code of the shape shadow or null if there is no shadow.
	 * @since 3.0
	 */
	protected StringBuilder getShadowCode(final float ppc) {
		final StringBuilder code;

		if(shape.hasShadow()) {
			final Color shadowColor = shape.getShadowCol();

			code = new StringBuilder();
			code.append("shadow=true"); //NON-NLS

			if(!MathUtils.INST.equalsDouble(Math.toDegrees(shape.getShadowAngle()), PSTricksConstants.DEFAULT_SHADOW_ANGLE)) {
				code.append(",shadowangle=").append(MathUtils.INST.getCutNumberFloat(Math.toDegrees(shape.getShadowAngle()))); //NON-NLS
			}

			code.append(",shadowsize=").append(MathUtils.INST.getCutNumberFloat(shape.getShadowSize() / ppc)); //NON-NLS

			if(!shadowColor.equals(PSTricksConstants.DEFAULT_SHADOW_COLOR)) {
				code.append(",shadowcolor=").append(getColourName(shadowColor)); //NON-NLS
			}
		}else {
			code = null;
		}

		return code;
	}
}
