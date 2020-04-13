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
package net.sf.latexdraw.view.svg;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.svg.CSSColors;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGCircleElement;
import net.sf.latexdraw.parser.svg.SVGDefsElement;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGElements;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.parser.svg.SVGLineElement;
import net.sf.latexdraw.parser.svg.SVGLinearGradientElement;
import net.sf.latexdraw.parser.svg.SVGMarkerElement;
import net.sf.latexdraw.parser.svg.SVGParserUtils;
import net.sf.latexdraw.parser.svg.SVGPathElement;
import net.sf.latexdraw.parser.svg.SVGPatternElement;
import net.sf.latexdraw.parser.svg.SVGRectElement;
import net.sf.latexdraw.parser.svg.SVGStopElement;
import net.sf.latexdraw.parser.svg.SVGTransform;
import net.sf.latexdraw.parser.svg.SVGTransformList;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parser.svg.path.SVGPathSegList;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static java.lang.Math.toDegrees;

/**
 * The generation or the importation of SVG parameters to a general LaTeXDraw shape.
 * @author Arnaud BLOUIN
 */
abstract class SVGShape<S extends Shape> {
	/** The beginning of the token used to declare a URL in an SVG document. */
	static final String SVG_URL_TOKEN_BEGIN = "url(#"; //NON-NLS

	static void parameteriseSVGArrow(final ArrowableSingleShape shape, final SVGElement parent, final int arrowPos, final boolean isShadow,
									final SVGDocument doc, final SVGDefsElement defs) {
		final Arrow arrow = shape.getArrowAt(arrowPos);

		if(arrow.getArrowStyle() != ArrowStyle.NONE) {
			final String arrowName = "arrow" + arrowPos + (isShadow ? "Shad-" : "-") + shape.hashCode(); //NON-NLS
			final SVGElement arrowSVG = new SVGArrow(arrow).toSVG(doc, isShadow);

			arrowSVG.setAttribute(SVGAttributes.SVG_ID, arrowName);
			defs.appendChild(arrowSVG);
			parent.setAttribute(arrowPos == 0 ? SVGAttributes.SVG_MARKER_START : SVGAttributes.SVG_MARKER_END, SVG_URL_TOKEN_BEGIN + arrowName + ')');
		}
	}

	/**
	 * @param elt The source <code>g</code> element.
	 * @param type The type of the latexdraw element (double borders, shadow, main), if null, the main element is returned.
	 * @return The Researched element.
	 */
	static SVGElement getLaTeXDrawElement(final SVGGElement elt, final String type) {
		if(elt == null) {
			return null;
		}

		final NodeList nl = elt.getChildNodes();
		int i = 0;
		final int size = nl.getLength();
		Node ltdElt = null;
		final String bis = elt.lookupPrefixUsable(LNamespace.LATEXDRAW_NAMESPACE_URI);
		final Predicate<Node> test = type == null ?
			node -> ((SVGElement) node).getAttribute(bis + LNamespace.XML_TYPE).isEmpty() :
			node -> type.equals(((SVGElement) node).getAttribute(bis + LNamespace.XML_TYPE));

		while(i < size && ltdElt == null) {
			final Node n = nl.item(i);

			if(test.test(n)) {
				ltdElt = n;
			}else {
				i++;
			}
		}

		return (SVGElement) ltdElt;
	}

	/**
	 * Sets the thickness of the given shape with the given SVG stroke-width.
	 * @param shape The shape to set.
	 * @param strokeWidth The SVG stroke-width to convert.
	 * @param stroke The stroke.
	 */
	static void setThickness(final Shape shape, final String strokeWidth, final String stroke) {
		if(shape != null && strokeWidth != null && !SVGAttributes.SVG_VALUE_NONE.equals(stroke)) {
			SVGParserUtils.INSTANCE.parseLength(strokeWidth).ifPresent(lgth -> shape.setThickness(lgth.getValue()));
		}
	}

	/**
	 * Sets the colour of the line of the shape with the given SVG stroke.
	 * @param shape The shape to set.
	 * @param stoke The stroke of the shape.
	 * @param opacity The possible stroke-opacity of the colour. May be null.
	 */
	static void setLineColour(final Shape shape, final String stoke, final String opacity) {
		if(shape != null && stoke != null) {
			final Color col = CSSColors.INSTANCE.getRGBColour(stoke);
			shape.setLineColour(col);

			if(opacity != null) {
				try {
					shape.setLineColour(ShapeFactory.INST.createColor(col.getR(), col.getG(), col.getB(), Double.parseDouble(opacity)));
				}catch(final NumberFormatException ex) {
					BadaboomCollector.INSTANCE.add(ex);
				}
			}
		}
	}

	private static void setHatchingsFromSVG(final Shape shape, final SVGPatternElement pat) {
		final @NotNull Optional<Color> c = pat.getBackgroundColor();
		final String str = pat.getAttribute(pat.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_TYPE);
		double angle;
		double sep;
		final double width;
		final String attr;

		try {
			angle = Double.parseDouble(pat.getAttribute(pat.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_ROTATION));
		}catch(final NumberFormatException ex) {
			angle = 0d;
		}

		attr = pat.getAttribute(pat.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_SIZE);

		if(attr.isEmpty()) {
			sep = pat.getHatchingSep();
		}else {
			try {
				sep = Double.parseDouble(attr);
			}catch(final NumberFormatException ex) {
				sep = 0d;
			}
		}

		if(PSTricksConstants.isValidFillStyle(str)) {
			shape.setFillingStyle(FillingStyle.getStyleFromLatex(str));
		}

		if(!Double.isNaN(angle)) {
			shape.setHatchingsAngle(angle);
		}

		shape.setFilled(c.isPresent());
		c.ifPresent(col -> shape.setFillingCol(col));

		pat.getHatchingColor().ifPresent(col -> shape.setHatchingsCol(col));

		if(!Double.isNaN(sep)) {
			shape.setHatchingsSep(sep);
		}

		width = pat.getHatchingStrokeWidth();

		if(!Double.isNaN(width)) {
			shape.setHatchingsWidth(width);
		}
	}

	private static void setPlainFillFromSVG(final Shape shape, final String fill, final String opacity) {
		// Just getting the filling colour.
		final Color fillCol = CSSColors.INSTANCE.getRGBColour(fill);

		if(fillCol != null) {
			shape.setFillingCol(fillCol);
			shape.setFilled(true);
			if(opacity != null) {
				try {
					shape.setFillingCol(fillCol.newColorWithOpacity(Double.parseDouble(opacity)));
				}catch(final NumberFormatException ignored) {
				}
			}
		}
	}

	private static void setGradientFromSVG(final Shape shape, final SVGLinearGradientElement grad) {
		shape.setGradColStart(grad.getStartColor());
		shape.setGradColEnd(grad.getEndColor());
		shape.setFillingStyle(FillingStyle.GRAD);
		shape.setGradMidPt(grad.getMiddlePoint());
		shape.setGradAngle(grad.getAngle() - Math.PI / 2d);
	}

	/**
	 * Sets the fill properties to the given figure.
	 * @param shape The figure to set.
	 * @param fill The fill properties
	 * @param defs The definition that may be useful to the the fill properties (url), may be null.
	 * @param opacity The possible fill-opacity of the colour. May be null.
	 */
	static void setFillFromSVG(final Shape shape, final String fill, final String opacity, final SVGDefsElement defs) {
		if(shape == null || SVGAttributes.SVG_VALUE_NONE.equals(fill)) {
			return;
		}

		// Getting the url to the SVG symbol of the filling.
		if(defs != null && fill.startsWith(SVG_URL_TOKEN_BEGIN) && fill.endsWith(")")) {
			final String uri = fill.substring(5, fill.length() - 1);
			final SVGElement def = defs.getDef(uri);

			// A pattern means hatchings.
			if(def instanceof SVGPatternElement) {
				setHatchingsFromSVG(shape, (SVGPatternElement) def);
			}else {
				// A linear gradient means a gradient.
				if(def instanceof SVGLinearGradientElement) {
					setGradientFromSVG(shape, (SVGLinearGradientElement) def);
				}
			}
		}else {
			setPlainFillFromSVG(shape, fill, opacity);
		}
	}

	/**
	 * Sets the figure properties concerning the line properties.
	 * @param shape The figure to set.
	 * @param dashArray The dash array SVG property.
	 * @param linecap The line cap SVG property.
	 */
	static void setDashedDotted(final Shape shape, final String dashArray, final String linecap) {
		if(shape != null && dashArray != null && !SVGAttributes.SVG_VALUE_NONE.equals(dashArray)) {
			if(SVGAttributes.SVG_LINECAP_VALUE_ROUND.equals(linecap)) {
				shape.setLineStyle(LineStyle.DOTTED);
			}else {
				shape.setLineStyle(LineStyle.DASHED);
			}
		}
	}

	/**
	 * Sets the given thickness to the given SVG element.
	 * @param elt The element to set.
	 * @param thickness The thickness to set.
	 * @param hasDoubleBorders True: the shape has double borders and it must be considered during the computation of the thickness.
	 * @param doubleSep The size of the double borders.
	 */
	static void setThickness(final SVGElement elt, final double thickness, final boolean hasDoubleBorders, final double doubleSep) {
		if(elt != null) {
			elt.setStrokeWidth(hasDoubleBorders ? thickness * 2d + doubleSep : thickness);
		}
	}

	/**
	 * Sets the line style to the given SVG element.
	 * @param elt The element to set.
	 * @param blackDash The black dash of the line for dashed line style.
	 * @param whiteDash The white dash of the line for dashed line style.
	 * @param dotSep The separation between dots for dotted line style.
	 * @param lineStyle The line style to set to the SVG element.
	 * @param hasDoubleBorders True: the shape has double borders.
	 * @param thickness The thickness to set to the element.
	 * @param doubleSep The size of the double borders.
	 */
	static void setDashedDotted(final Element elt, final double blackDash, final double whiteDash, final double dotSep, final String lineStyle, final
								boolean hasDoubleBorders, final double thickness, final double doubleSep) {
		if(elt == null) {
			return;
		}
		if(PSTricksConstants.LINE_DASHED_STYLE.equals(lineStyle)) {
			elt.setAttribute(SVGAttributes.SVG_STROKE_DASHARRAY, blackDash + ", " + whiteDash); //NON-NLS
		}else {
			if(PSTricksConstants.LINE_DOTTED_STYLE.equals(lineStyle)) {
				elt.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_ROUND);
				elt.setAttribute(SVGAttributes.SVG_STROKE_DASHARRAY, 1 + ", " + (dotSep + (hasDoubleBorders ? thickness * 2f + doubleSep : thickness))); //NON-NLS
			}
		}
	}

	/**
	 * Creates a line with the style of the 'show points' option.
	 * @param doc The document owner.
	 * @param thickness The thickness of the line to create.
	 * @param col The colour of the line.
	 * @param p1 The first point of the line.
	 * @param p2 The second point of the line.
	 * @param blackDash The black dash interval.
	 * @param whiteDash The white dash interval.
	 * @param hasDble Defines if the shape had double borders.
	 * @param dotSep The dot interval.
	 * @return The created SVG line or null.
	 */
	static SVGLineElement getShowPointsLine(final SVGDocument doc, final double thickness, final Color col, final Point p1, final Point p2, final
						double blackDash, final double whiteDash, final boolean hasDble, final double dotSep, final double doubleSep) {
		if(doc == null) {
			return null;
		}

		final SVGLineElement line = new SVGLineElement(doc);

		if(p1 != null) {
			line.setX1(p1.getX());
			line.setY1(p1.getY());
		}

		if(p2 != null) {
			line.setX2(p2.getX());
			line.setY2(p2.getY());
		}

		line.setStrokeWidth(thickness);
		line.setStroke(col);
		SVGShape.setDashedDotted(line, blackDash, whiteDash, dotSep, PSTricksConstants.LINE_DASHED_STYLE, hasDble, thickness, doubleSep);

		return line;
	}

	/**
	 * Creates an SVG circle that represents a dot for the option 'show points'.
	 * @param doc The document owner.
	 * @param rad The radius of the circle.
	 * @param pt The position of the point.
	 * @param col The colour of the dot.
	 * @return The created dot or null.
	 */
	static SVGCircleElement getShowPointsDot(final SVGDocument doc, final double rad, final Point pt, final Color col) {
		if(doc == null) {
			return null;
		}

		final SVGCircleElement circle = new SVGCircleElement(doc);

		circle.setR(rad);

		if(pt != null) {
			circle.setCx(pt.getX());
			circle.setCy(pt.getY());
		}

		circle.setFill(col);

		return circle;
	}

	/** The shape model use for the generation. */
	final S shape;


	/**
	 * Creates the SVG generator.
	 * @param sh The shape used for the generation.
	 * @throws IllegalArgumentException If the given shape is null.
	 */
	SVGShape(final S sh) {
		super();
		if(sh == null) {
			throw new IllegalArgumentException();
		}
		shape = sh;
	}

	/**
	 * @return The SVG ID of the shape (starting with the token "id" followed by the number of the shape).
	 */
	final String getSVGID() {
		return "id" + shape.hashCode(); //NON-NLS
	}

	/**
	 * Applies the set of transformations that concerned the given SVG element to the shape.
	 * @param elt The element that contains the SVG transformation list.
	 */
	final void applyTransformations(final SVGElement elt) {
		if(elt == null) {
			return;
		}

		// The list of the transformations that are applied on the element.
		final SVGTransformList tl = elt.getWholeTransform();

		for(int i = tl.size() - 1; i >= 0; i--) {
			applyTransformation(tl.get(i));
		}
	}

	/**
	 * Applies an SVG transformation on the shape.
	 * @param transfo The SVG transformation to apply.
	 */
	final void applyTransformation(final SVGTransform transfo) {
		if(transfo instanceof SVGTransform.SVGTranslateTransformation) {
			final SVGTransform.SVGTranslateTransformation translate = (SVGTransform.SVGTranslateTransformation) transfo;
			getShape().translate(translate.getTx(), translate.getTy());
		}

		if(transfo instanceof SVGTransform.SVGRotateTransformation) {
			final SVGTransform.SVGRotateTransformation rotation = (SVGTransform.SVGRotateTransformation) transfo;
			getShape().rotate(ShapeFactory.INST.createPoint(rotation.getCx(), rotation.getCy()), Math.toRadians(rotation.getRotationAngle()));
		}
	}

	/**
	 * When the arrows are read from an SVG document, we need to set the parameters of the first
	 * arrow to the second arrow and vise et versa; because the arrows of a shape share the same
	 * parameters. This method carries out this job.
	 * @param ah1 The first arrow.
	 * @param ah2 The second arrow.
	 */
	final void homogeniseArrows(final Arrow ah1, final Arrow ah2) {
		if(ah1 == null || ah2 == null) {
			return;
		}

		homogeniseArrowFrom(ah1, ah2);
		homogeniseArrowFrom(ah2, ah1);
	}

	/**
	 * Copies the parameters of the first arrow to the second arrow (only
	 * the parameters of the current style are copied).
	 * @param source The arrow that will be copied.
	 * @param target The arrow that will be set.
	 */
	final void homogeniseArrowFrom(final Arrow source, final Arrow target) {
		if(source == null || target == null) {
			return;
		}

		final ArrowStyle style = source.getArrowStyle();

		if(style != ArrowStyle.NONE) {
			if(style.isBar()) {
				target.setTBarSizeDim(source.getTBarSizeDim());
				target.setTBarSizeNum(source.getTBarSizeNum());
				return;
			}
			if(style.isArrow()) {
				target.setArrowInset(source.getArrowInset());
				target.setArrowLength(source.getArrowLength());
				target.setArrowSizeDim(source.getArrowSizeDim());
				target.setArrowSizeNum(source.getArrowSizeNum());
				return;
			}
			if(style.isRoundBracket()) {
				target.setRBracketNum(source.getRBracketNum());
				target.setTBarSizeDim(source.getTBarSizeDim());
				target.setTBarSizeNum(source.getTBarSizeNum());
				return;
			}
			if(style.isSquareBracket()) {
				target.setBracketNum(source.getBracketNum());
				target.setTBarSizeDim(source.getTBarSizeDim());
				target.setTBarSizeNum(source.getTBarSizeNum());
				return;
			}
			target.setDotSizeDim(source.getDotSizeDim());
			target.setDotSizeNum(source.getDotSizeNum());
		}
	}

	private void applyShadowTransformations(final SVGElement elt) {
		final SVGTransformList tl = elt.getTransform();
		boolean sSize = false;
		boolean sAngle = false;

		for(int i = 0, size = tl.size(); i < size && (!sSize || !sAngle); i++) {
			final SVGTransform transformation = tl.get(i);

			if(transformation instanceof SVGTransform.SVGTranslateTransformation) {
				final SVGTransform.SVGTranslateTransformation translation = (SVGTransform.SVGTranslateTransformation) transformation;
				// It is shadowSize.
				if(!sSize && MathUtils.INST.equalsDouble(translation.getTy(), 0d)) {
					shape.setShadowSize(translation.getTx());
					sSize = true;
				}else {
					shape.setShadowAngle(applyShadowTransformationsComputeAngle(translation.getTx(), translation.getTy()));
					sAngle = true;
				}
			}
		}
	}

	private double applyShadowTransformationsComputeAngle(final double tx, final double ty) {
		final Point gravityCenter = shape.getGravityCentre();
		final double shSize = shape.getShadowSize();

		if(MathUtils.INST.equalsDouble(ty, 0d)) {
			return tx < 0d ? Math.PI : 0d;
		}

		if(MathUtils.INST.equalsDouble(shSize, Math.abs(tx))) {
			return ty > 0d ? -Math.PI / 2d : Math.PI / 2d;
		}
		final double angle = Math.acos(gravityCenter.distance(gravityCenter.getX() + tx + shSize,
			gravityCenter.getY()) / gravityCenter.distance(gravityCenter.getX() + tx + shSize, gravityCenter.getY() + ty));

		if(tx + shSize < 0d) {
			if(ty < 0d) {
				return Math.PI - angle;
			}
			return angle + Math.PI;
		}
		if(ty > 0d) {
			return -angle;
		}
		return angle;
	}



	/**
	 * Sets the shadow parameters of the figure by using an SVG element having "type:shadow".
	 * @param elt The source element.
	 */
	final void setSVGShadowParameters(final SVGElement elt) {
		if(elt == null || !shape.isShadowable()) {
			return;
		}

		if(shape.isFillable()) {
			final String fill = elt.getFill();

			if(fill != null && !SVGAttributes.SVG_VALUE_NONE.equals(fill) && !fill.startsWith(SVG_URL_TOKEN_BEGIN)) {
				shape.setShadowCol(CSSColors.INSTANCE.getRGBColour(fill));
			}
		}

		elt.getStroke().ifPresent(col -> shape.setShadowCol(col));
		applyShadowTransformations(elt);
		shape.setHasShadow(true);
	}

	/**
	 * Sets the double borders parameters of the figure by using an SVG element.
	 * @param elt The SVG element.
	 */
	final void setSVGDbleBordersParameters(final SVGElement elt) {
		if(elt == null) {
			return;
		}

		shape.setDbleBordSep(elt.getStrokeWidth());
		elt.getStroke().ifPresent(col -> shape.setDbleBordCol(col));
		shape.setThickness((shape.getThickness() - shape.getDbleBordSep()) / 2d);
		shape.setHasDbleBord(true);
	}

	final void setSVGLatexdrawParameters(final SVGElement elt) {
		if(elt == null) {
			return;
		}

		final String pos = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_BORDERS_POS);
		if(shape.isBordersMovable() && !pos.isEmpty()) {
			shape.setBordersPosition(BorderPos.getStyle(pos));
		}
	}

	/**
	 * Sets the global parameters of the figure by using an SVG element.
	 * @param elt The SVG element.
	 */
	final void setSVGParameters(final SVGElement elt) {
		if(elt == null) {
			return;
		}

		if(shape.isThicknessable()) {
			shape.setThickness(elt.getStrokeWidth());
		}

		setSVGLatexdrawParameters(elt);

		elt.getStroke().ifPresent(col -> shape.setLineColour(col));

		final Color lineCol = shape.getLineColour();
		try {
			shape.setLineColour(ShapeFactory.INST.createColor(lineCol.getR(), lineCol.getG(), lineCol.getB(),
				Double.parseDouble(elt.getSVGAttribute(SVGAttributes.SVG_STROKE_OPACITY, null))));
		}catch(final NumberFormatException ignore) {
		}

		if(shape.isLineStylable()) {
			setDashedDotted(shape, elt.getStrokeDasharray(), elt.getStrokeLinecap());
		}

		if(shape.isFillable()) {
			setFillFromSVG(shape, elt.getFill(), elt.getSVGAttribute(SVGAttributes.SVG_FILL_OPACITY, null), elt.getSVGRoot().getDefs());
		}

		final Map<String, String> stylesCSS = elt.getStylesCSS();
		if(stylesCSS != null) {
			SVGShape.setThickness(shape, stylesCSS.get(SVGAttributes.SVG_STROKE_WIDTH), stylesCSS.get(SVGAttributes.SVG_STROKE));
			SVGShape.setLineColour(shape, stylesCSS.get(SVGAttributes.SVG_STROKE), stylesCSS.get(SVGAttributes.SVG_STROKE_OPACITY));
			SVGShape.setDashedDotted(shape, stylesCSS.get(SVGAttributes.SVG_STROKE_DASHARRAY), stylesCSS.get(SVGAttributes.SVG_STROKE_LINECAP));
			SVGShape.setFillFromSVG(shape, stylesCSS.get(SVGAttributes.SVG_FILL), stylesCSS.get(SVGAttributes.SVG_FILL_OPACITY), elt.getSVGRoot().getDefs());
		}
	}

	/**
	 * Sets the given arrow head using the SVG arrow with the ID arrowID
	 * @param ah The arrow head to set.
	 * @param arrowID The SVG ID of the SVG arrow head.
	 * @param elt An element of the SVG document (useful to get the defs of the document).
	 */
	final void setSVGArrow(final Arrow ah, final @NotNull String arrowID, final SVGElement elt, final @NotNull String svgMarker) {
		if(ah != null && elt != null && !arrowID.isEmpty()) {
			final SVGArrow arrGen = new SVGArrow(ah);
			arrGen.setArrow((SVGMarkerElement) elt.getDef(SVGParserUtils.INSTANCE.parseURIRerefence(arrowID)), getShape(), svgMarker);
		}
	}

	/**
	 * Creates an SVG element from the current latexdraw shape.
	 * @param doc The SVG document.
	 * @return The created SVGElement or null.
	 */
	abstract SVGElement toSVG(final @NotNull SVGDocument doc);

	/**
	 * @param elt Rotates the SVG element.
	 * @throws IllegalArgumentException If elt is null.
	 */
	final void setSVGRotationAttribute(final Element elt) {
		if(elt == null) {
			throw new IllegalArgumentException();
		}

		final double rotationAngle = shape.getRotationAngle();
		final Point gravityCenter = shape.getGravityCentre();

		if(!MathUtils.INST.equalsDouble(MathUtils.INST.mod2pi(rotationAngle), 0d)) {
			final double gcx = gravityCenter.getX();
			final double gcy = gravityCenter.getY();
			final double x = -Math.cos(-rotationAngle) * gcx + Math.sin(-rotationAngle) * gcy + gcx;
			final double y = -Math.sin(-rotationAngle) * gcx - Math.cos(-rotationAngle) * gcy + gcy;
			String transfo = elt.getAttribute(SVGAttributes.SVG_TRANSFORM);
			final String rotation = new SVGTransform.SVGRotateTransformation(toDegrees(rotationAngle), 0d, 0d) + " " +
				new SVGTransform.SVGTranslateTransformation(-x, -y);

			if(transfo.isEmpty()) {
				transfo = rotation;
			}else {
				transfo = rotation + " " + transfo;
			}

			elt.setAttribute(SVGAttributes.SVG_TRANSFORM, transfo);
		}
	}

	/**
	 * Sets the double borders parameters to the given SVG element if the current figure has double borders.
	 * @param elt The element to set.
	 * @throws IllegalArgumentException If elt is null.
	 */
	final void setSVGDoubleBordersAttributes(final Element elt) {
		if(elt == null) {
			throw new IllegalArgumentException();
		}

		if(shape.hasDbleBord()) {
			elt.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getDbleBordCol(), true));
			elt.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, MathUtils.INST.format.format(shape.getDbleBordSep()));
			elt.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
			elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_DBLE_BORDERS);

			if(shape.getDbleBordCol().getO() < 1d) {
				elt.setAttribute(SVGAttributes.SVG_STROKE_OPACITY, MathUtils.INST.format.format(shape.getDbleBordCol().getO()));
			}
		}
	}

	/**
	 * @param elt The element to set if the current figure has a shadow.
	 * @param shadowFills True if a shadow must fill the figure.
	 * @throws IllegalArgumentException If elt is null.
	 */
	final void setSVGShadowAttributes(final Element elt, final boolean shadowFills) {
		if(elt == null) {
			throw new IllegalArgumentException();
		}

		if(shape.hasShadow()) {
			final Point gravityCenter = shape.getGravityCentre();
			final double gcx = gravityCenter.getX();
			final double gcy = gravityCenter.getY();
			final Point pt = ShapeFactory.INST.createPoint(gcx + shape.getShadowSize(), gcy).rotatePoint(shape.getGravityCentre(), -shape.getShadowAngle());
			final boolean filledShadow = shadowFills || shape.isFilled();

			elt.setAttribute(SVGAttributes.SVG_TRANSFORM, new SVGTransform.SVGTranslateTransformation(shape.getShadowSize(), 0.) + " " +
				new SVGTransform.SVGTranslateTransformation(pt.getX() - gcx - shape.getShadowSize(), pt.getY() - gcy));
			elt.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, MathUtils.INST.format.format(shape.hasDbleBord() ?
				shape.getThickness() * 2d + shape.getDbleBordSep() : shape.getThickness()));
			elt.setAttribute(SVGAttributes.SVG_FILL, filledShadow ? CSSColors.INSTANCE.getColorName(shape.getShadowCol(), true) : SVGAttributes.SVG_VALUE_NONE);
			elt.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getShadowCol(), true));
			elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_SHADOW);

			if(shape.getShadowCol().getO() < 1d) {
				elt.setAttribute(SVGAttributes.SVG_STROKE_OPACITY, MathUtils.INST.format.format(shape.getShadowCol().getO()));
				if(filledShadow) {
					elt.setAttribute(SVGAttributes.SVG_FILL_OPACITY, MathUtils.INST.format.format(shape.getShadowCol().getO()));
				}
			}
		}
	}


	private void setSVGFill(final SVGElement root) {
		root.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(shape.getFillingCol(), true));
		if(shape.getFillingCol().getO() < 1d) {
			root.setAttribute(SVGAttributes.SVG_FILL_OPACITY, MathUtils.INST.format.format(shape.getFillingCol().getO()));
		}
	}


	private void setSVGThickness(final SVGElement root) {
		// Setting the thickness of the borders.
		if(shape.isThicknessable()) {
			SVGShape.setThickness(root, shape.getThickness(), shape.hasDbleBord(), shape.getDbleBordSep());
			if(shape.getLineColour().getO() < 1d) {
				root.setAttribute(SVGAttributes.SVG_STROKE_OPACITY, MathUtils.INST.format.format(shape.getLineColour().getO()));
			}
		}
	}

	private void setSVGLinearGradient(final SVGDocument doc, final SVGElement root) {
		final SVGDefsElement defs = doc.getFirstChild().getDefs();
		final SVGElement grad = new SVGLinearGradientElement(doc);
		final double gradMidPt = shape.getGradMidPt();
		final double gradAngle = shape.getGradAngle();
		final String id = SVGElements.SVG_LINEAR_GRADIENT + shape.hashCode();
		grad.setAttribute(SVGAttributes.SVG_ID, id);

		if(!MathUtils.INST.equalsDouble(MathUtils.INST.mod2pi(gradAngle + Math.PI / 2d), 0d)) {
			grad.setAttribute(SVGAttributes.SVG_GRADIENT_TRANSFORM, new SVGTransform.SVGRotateTransformation(toDegrees(gradAngle) + 90d, 0.5, 0.5).toString());
		}

		final SVGStopElement stop1 = new SVGStopElement(doc);
		stop1.setAttribute(SVGAttributes.SVG_OFFSET, "0");
		stop1.setAttribute(SVGAttributes.SVG_STOP_COLOR, CSSColors.INSTANCE.getColorName(shape.getGradColStart(), true));

		if(shape.getGradColStart().getO() < 1d) {
			stop1.setAttribute(SVGAttributes.SVG_STOP_OPACITY, MathUtils.INST.format.format(shape.getGradColStart().getO()));
		}

		grad.appendChild(stop1);

		final SVGStopElement stop2 = new SVGStopElement(doc);
		stop2.setAttribute(SVGAttributes.SVG_OFFSET, MathUtils.INST.format.format(gradMidPt));
		stop2.setAttribute(SVGAttributes.SVG_STOP_COLOR, CSSColors.INSTANCE.getColorName(shape.getGradColEnd(), true));

		if(shape.getGradColEnd().getO() < 1d) {
			stop2.setAttribute(SVGAttributes.SVG_STOP_OPACITY, MathUtils.INST.format.format(shape.getGradColEnd().getO()));
		}

		grad.appendChild(stop2);
		defs.appendChild(grad);
		root.setAttribute(SVGAttributes.SVG_FILL, SVG_URL_TOKEN_BEGIN + id + ')');
	}


	private void setSVGHatchings(final SVGDocument doc, final SVGElement root, final boolean shadowFills) {
		final SVGDefsElement defs = doc.getFirstChild().getDefs();
		final String id = SVGElements.SVG_PATTERN + shape.hashCode();
		final SVGPatternElement hatch = new SVGPatternElement(doc);
		final SVGGElement gPath = new SVGGElement(doc);
		final Point max = shape.getFullBottomRightPoint();
		final SVGPathElement path = new SVGPathElement(doc);

		root.setAttribute(SVGAttributes.SVG_FILL, SVG_URL_TOKEN_BEGIN + id + ')');
		hatch.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, shape.getFillingStyle().getLatexToken());
		hatch.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getHatchingsAngle()));
		hatch.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_SIZE, String.valueOf(shape.getHatchingsSep()));
		hatch.setAttribute(SVGAttributes.SVG_PATTERN_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		hatch.setAttribute(SVGAttributes.SVG_ID, id);
		hatch.setAttribute(SVGAttributes.SVG_X, "0");
		hatch.setAttribute(SVGAttributes.SVG_Y, "0");
		hatch.setAttribute(SVGAttributes.SVG_WIDTH, String.valueOf((int) max.getX()));
		hatch.setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf((int) max.getY()));
		gPath.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getHatchingsCol(), true));
		gPath.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getHatchingsWidth()));
		gPath.setAttribute(SVGAttributes.SVG_STROKE_DASHARRAY, SVGAttributes.SVG_VALUE_NONE);

		if(shape.getHatchingsCol().getO() < 1d) {
			gPath.setAttribute(SVGAttributes.SVG_STROKE_OPACITY, MathUtils.INST.format.format(shape.getHatchingsCol().getO()));
		}

		path.setAttribute(SVGAttributes.SVG_D, getSVGHatchingsPath().toString());
		gPath.appendChild(path);

		// Several shapes having hatching must have their shadow filled.
		if(shape.isFilled() || (shape.hasShadow() && shadowFills)) {
			final SVGRectElement fill = new SVGRectElement(doc);
			fill.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(shape.getFillingCol(), true));

			if(shape.getFillingCol().getO() < 1d) {
				fill.setAttribute(SVGAttributes.SVG_FILL_OPACITY, MathUtils.INST.format.format(shape.getFillingCol().getO()));
			}

			fill.setAttribute(SVGAttributes.SVG_STROKE, SVGAttributes.SVG_VALUE_NONE);
			fill.setAttribute(SVGAttributes.SVG_WIDTH, String.valueOf((int) max.getX()));
			fill.setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf((int) max.getY()));

			hatch.appendChild(fill);
		}

		defs.appendChild(hatch);
		hatch.appendChild(gPath);
	}


	private void setSVGFillStyle(final SVGDocument doc, final SVGElement root, final boolean shadowFills) {
		final FillingStyle fillStyle = shape.getFillingStyle();

		// Setting the filling properties.
		if(!shape.isFillable()) {
			return;
		}

		if((shape.isFilled() || (shape.hasShadow() && shadowFills)) && !shape.hasHatchings() && !shape.hasGradient()) {
			setSVGFill(root);
			return;
		}

		// Setting the filling colour.
		if(fillStyle == FillingStyle.NONE) {
			root.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
			return;
		}

		// Setting the gradient properties.
		if(fillStyle == FillingStyle.GRAD) {
			setSVGLinearGradient(doc, root);
			return;
		}

		// Setting the hatchings.
		if(shape.hasHatchings()) {
			setSVGHatchings(doc, root, shadowFills);
		}
	}

	/**
	 * Sets the SVG attribute of the current figure.
	 * @param doc The original document with which, all the elements will be created.
	 * @param root The root element of the document.
	 * @param shadowFills True if a shadow must fill the figure.
	 * @throws IllegalArgumentException If the root or the "defs" part of the document is null.
	 */
	final void setSVGAttributes(final SVGDocument doc, final SVGElement root, final boolean shadowFills) {
		if(root == null || doc.getFirstChild().getDefs() == null) {
			throw new IllegalArgumentException();
		}

		// Setting the position of the borders.
		if(shape.isBordersMovable()) {
			root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_BORDERS_POS, shape.getBordersPosition().getLatexToken());
		}

		root.setStroke(shape.getLineColour());

		setSVGThickness(root);
		setSVGFillStyle(doc, root, shadowFills);

		if(shape.isLineStylable()) {
			SVGShape.setDashedDotted(root, shape.getDashSepBlack(), shape.getDashSepWhite(), shape.getDotSep(), shape.getLineStyle().getLatexToken(), shape
				.hasDbleBord(), shape.getThickness(), shape.getDbleBordSep());
		}
	}

	/**
	 * @return The path of the hatchings of the shape.
	 */
	final SVGPathSegList getSVGHatchingsPath() {
		final SVGPathSegList path = new SVGPathSegList();

		if(!shape.hasHatchings()) {
			return path;
		}

		final String hatchingStyle = shape.getFillingStyle().getLatexToken();
		final double hatchingAngle = shape.getHatchingsAngle();
		final Rectangle bound = ShapeFactory.INST.createRectangle(shape.getFullTopLeftPoint(), shape.getFullBottomRightPoint());
		final double hAngle = hatchingAngle > 0 ? hatchingAngle - Math.PI / 2. : hatchingAngle + Math.PI / 2.;

		switch(hatchingStyle) {
			case PSTricksConstants.TOKEN_FILL_VLINES, PSTricksConstants.TOKEN_FILL_VLINES_F -> getSVGHatchingsPath2(path, hatchingAngle, bound);
			case PSTricksConstants.TOKEN_FILL_HLINES, PSTricksConstants.TOKEN_FILL_HLINES_F -> getSVGHatchingsPath2(path, hAngle, bound);
			case PSTricksConstants.TOKEN_FILL_CROSSHATCH, PSTricksConstants.TOKEN_FILL_CROSSHATCH_F -> {
				getSVGHatchingsPath2(path, hatchingAngle, bound);
				getSVGHatchingsPath2(path, hAngle, bound);
			}
		}

		return path;
	}

	/**
	 * Sub-routine of getSVGHatchingsPath2
	 */
	private double getAngleForSVGHacthings2(final double baseAngle) {
		final double halphPI = Math.PI / 2d;
		// Computing the angle.
		if(baseAngle > 3d * halphPI) {
			return baseAngle - Math.PI * 2d;
		}
		if(baseAngle > halphPI) {
			return baseAngle - Math.PI;
		}
		if(baseAngle < -3d * halphPI) {
			return baseAngle + Math.PI * 2d;
		}
		if(baseAngle < -halphPI) {
			return baseAngle + Math.PI;
		}
		return baseAngle;
	}

	/**
	 * Sub-routine of getSVGHatchingsPath2
	 */
	private void drawHatchingsNoRotation(final @NotNull SVGPathSegList path, final double val, final @NotNull Rectangle bound) {
		final Point nw = bound.getTopLeftPoint();
		final Point se = bound.getBottomRightPoint();
		for(double x = nw.getX(); x < se.getX(); x += val) {
			path.add(new SVGPathSegMoveto(x, nw.getY(), false));
			path.add(new SVGPathSegLineto(x, se.getY(), false));
		}
	}

	/**
	 * Sub-routine of getSVGHatchingsPath2
	 */
	private void drawHatchingsRotation(final @NotNull SVGPathSegList path, final double val, final @NotNull Rectangle bound, final double angle) {
		final Point nw = bound.getTopLeftPoint();
		final Point se = bound.getBottomRightPoint();
		final double incX = val / Math.cos(angle);
		final double incY = val / Math.sin(angle);
		final double maxX;
		double y1;
		double x2;

		if(angle > 0d) {
			y1 = nw.getY();
			maxX = se.getX() + (se.getY() - Math.min(nw.getY(), 0d)) * Math.tan(angle);
		}else {
			y1 = se.getY();
			maxX = se.getX() - se.getY() * Math.tan(angle);
		}

		if(incX < 0d || MathUtils.INST.equalsDouble(incX, 0d)) {
			return;
		}

		final double x1 = nw.getX();
		final double y2 = y1;
		x2 = x1;

		while(x2 < maxX) {
			x2 += incX;
			y1 += incY;
			path.add(new SVGPathSegMoveto(x1, y1, false));
			path.add(new SVGPathSegLineto(x2, y2, false));
		}
	}

	private void getSVGHatchingsPath2(final @NotNull SVGPathSegList path, final double hAngle, final @NotNull Rectangle bound) {
		final double angle = getAngleForSVGHacthings2(hAngle % (Math.PI * 2d));
		final double val = shape.getHatchingsWidth() + shape.getHatchingsSep();

		if(MathUtils.INST.equalsDouble(angle, 0d)) { // Drawing the hatchings vertically.
			drawHatchingsNoRotation(path, val, bound);
		}else { // Drawing the hatchings horizontally.
			if(MathUtils.INST.equalsDouble(angle, Math.PI / 2d) || MathUtils.INST.equalsDouble(angle, -Math.PI / 2d)) {
				final Point nw = bound.getTopLeftPoint();
				final Point se = bound.getBottomRightPoint();
				for(double y = nw.getY(); y < se.getY(); y += val) {
					path.add(new SVGPathSegMoveto(nw.getX(), y, false));
					path.add(new SVGPathSegLineto(se.getX(), y, false));
				}
			}else {
				// Drawing the hatchings by rotation.
				drawHatchingsRotation(path, val, bound, angle);
			}
		}
	}


	/**
	 * If a figure can move its border, we have to compute the difference between the PSTricks shape and the SVG shape.
	 * @return The gap computed with the border position, the thickness and the double boundary. Or NaN if the shape cannot move
	 * its border.
	 */
	final double getPositionGap() {
		final double gap;

		if(!shape.isBordersMovable() || PSTricksConstants.BORDERS_MIDDLE.equals(shape.getBordersPosition().getLatexToken())) {
			gap = 0.;
		}else {
			if(PSTricksConstants.BORDERS_INSIDE.equals(shape.getBordersPosition().getLatexToken())) {
				gap = -shape.getThickness() - (shape.hasDbleBord() ? shape.getDbleBordSep() + shape.getThickness() : 0.);
			}else {
				gap = shape.getThickness() + (shape.hasDbleBord() ? shape.getDbleBordSep() + shape.getThickness() : 0.);
			}
		}

		return gap;
	}

	/**
	 * When a shape has a shadow and is filled, the background of its borders must be filled with the
	 * colour of the interior of the shape. This method does not test if it must be done, it sets a
	 * SVG element which carries out that.
	 * @param elt The element that will be set to define the background of the borders.
	 * @param root The root element to which 'elt' will be appended.
	 */
	final void setSVGBorderBackground(final SVGElement elt, final SVGElement root) {
		if(elt == null || root == null) {
			return;
		}

		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_BG);
		elt.setFill(shape.getFillingCol());
		elt.setStroke(shape.getFillingCol());
		setThickness(elt, shape.getThickness(), shape.isDbleBorderable(), shape.getDbleBordSep());
		root.appendChild(elt);
	}

	/**
	 * @return the shape.
	 */
	final S getShape() {
		return shape;
	}
}
