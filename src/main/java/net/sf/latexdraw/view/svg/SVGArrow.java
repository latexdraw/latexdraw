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

import java.util.Objects;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.svg.CSSColors;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGCircleElement;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGElements;
import net.sf.latexdraw.parser.svg.SVGMarkerElement;
import net.sf.latexdraw.parser.svg.SVGNodeList;
import net.sf.latexdraw.parser.svg.SVGPathElement;
import net.sf.latexdraw.parser.svg.SVGTransform;
import net.sf.latexdraw.parser.svg.SVGTransformList;
import net.sf.latexdraw.parser.svg.path.SVGPathSeg;
import net.sf.latexdraw.parser.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parser.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLinetoVertical;
import net.sf.latexdraw.parser.svg.path.SVGPathSegList;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.GenericViewArrow;
import org.jetbrains.annotations.NotNull;

/**
 * An SVG generator for arrows.
 * @author Arnaud BLOUIN
 */
class SVGArrow implements GenericViewArrow {
	/** The arrowhead generated or used to generate the SVG-arrowed */
	Arrow arrow;

	SVGMarkerElement currentMarker;
	SVGDocument currentDoc;
	SVGCircleElement currentCircle;
	SVGPathElement currentPathElt;
	SVGPathSegList currentPath;

	/**
	 * Creates an SVG arrow generator.
	 * @param arr The arrow. Must not be null.
	 */
	SVGArrow(final Arrow arr) {
		super();
		arrow = Objects.requireNonNull(arr);
	}


	/**
	 * Initialises the arrow using an SVGMarkerElement.
	 * @param elt The SVGMarkerElement uses to initialise the arrow.
	 * @param owner The figure the has the arrow.
	 */
	void setArrow(final SVGMarkerElement elt, final Shape owner, final String svgMarker) {
		SVGNodeList nl = elt.getChildren(SVGElements.SVG_PATH);

		if(nl.getLength() == 0) {
			nl = elt.getChildren(SVGElements.SVG_CIRCLE);

			if(nl.getLength() > 0) {
				setArrow((SVGCircleElement) nl.item(0), owner);
			}
		}else {
			setArrow((SVGPathElement) nl.item(0), owner, svgMarker);
		}
	}

	/**
	 * Initialises the arrowhead using a circle arrow.
	 * @param circle The circle element.
	 * @param owner The shape that has the arrow.
	 */
	void setArrow(final SVGCircleElement circle, final Shape owner) {
		final double radius = circle.getR();
		final double dotSizeNum = MathUtils.INST.parserDouble(
			circle.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ARROW_DOT_SIZE_NUM)).orElse(1d);
		final double lineWidth = owner.hasDbleBord() ? owner.getDbleBordSep() + 2d * owner.getThickness() : owner.getThickness();
		final String stroke = circle.getAttribute(SVGAttributes.SVG_STROKE);

		if(SVGAttributes.SVG_VALUE_NONE.equals(stroke) && MathUtils.INST.equalsDouble(0d, circle.getCy()) &&
			MathUtils.INST.equalsDouble(arrow.getShape().getFullThickness() / 2d, circle.getR())) {
			arrow.setArrowStyle(MathUtils.INST.equalsDouble(0d, circle.getCx()) ? ArrowStyle.ROUND_END : ArrowStyle.ROUND_IN);
			return;
		}

		if(stroke.equals(circle.getFill())) {
			arrow.setArrowStyle(MathUtils.INST.equalsDouble(0d, circle.getCx()) ? ArrowStyle.DISK_END : ArrowStyle.DISK_IN);
		}else {
			arrow.setArrowStyle(MathUtils.INST.equalsDouble(0d, circle.getCx()) ? ArrowStyle.CIRCLE_END : ArrowStyle.CIRCLE_IN);
		}

		final double dotSizeDim = 2d * radius + lineWidth - dotSizeNum * lineWidth;
		if(MathUtils.INST.equalsDouble(dotSizeDim, 0d)) {
			arrow.setArrowStyle(ArrowStyle.ROUND_IN);
		}else {
			arrow.setDotSizeDim(dotSizeDim);
			arrow.setDotSizeNum(dotSizeNum);
		}
	}

	private boolean is180Rotation(final SVGElement elt) {
		final SVGTransformList transform = elt.getTransform();
		return transform != null && transform.size() == 1 && transform.get(0) instanceof SVGTransform.SVGRotateTransformation &&
			MathUtils.INST.equalsDouble(((SVGTransform.SVGRotateTransformation) transform.get(0)).getRotationAngle(), 180d);
	}

	private void setArrowBarBracketLegacy(final SVGPathSegMoveto m, final double lineWidth, final String svgMarker) {
		final boolean isStartArrow = SVGAttributes.SVG_MARKER_START.equals(svgMarker);
		final double width = (arrow.getTBarSizeDim() + arrow.getTBarSizeNum() * lineWidth) / lineWidth;
		final double rBrack = (Math.abs(m.getX()) - 0.5) / width;

		arrow.setArrowStyle(MathUtils.INST.equalsDouble(Math.abs(m.getX()), 0.5) ? ArrowStyle.RIGHT_ROUND_BRACKET : ArrowStyle.LEFT_ROUND_BRACKET);
		if(!isStartArrow) {
			arrow.setArrowStyle(arrow.getArrowStyle().getOppositeArrowStyle());
		}
		arrow.setRBracketNum(rBrack);
	}

	private void setArrowBarBracketRoundBracket(final SVGPathElement path, final SVGPathSeg seg, final double lineWidth, final double tbarNum) {
		arrow.setTBarSizeDim(((SVGPathSegArc) seg).getRY() * 2d - tbarNum * lineWidth);
		arrow.setRBracketNum(((SVGPathSegArc) seg).getRX() / arrow.getBarShapedArrowWidth());

		if(is180Rotation(path)) {
			arrow.setArrowStyle(arrow.isLeftArrow() ? ArrowStyle.RIGHT_ROUND_BRACKET : ArrowStyle.LEFT_ROUND_BRACKET);
		}else {
			arrow.setArrowStyle(arrow.isLeftArrow() ? ArrowStyle.LEFT_ROUND_BRACKET : ArrowStyle.RIGHT_ROUND_BRACKET);
		}
	}

	private void setArrowBarBracketSquareBracket(final SVGPathElement path, final SVGPathSegMoveto m, final double lineWidth, final double tbarNum) {
		arrow.setTBarSizeDim(lineWidth - tbarNum * lineWidth - 2d * m.getY());
		arrow.setBracketNum((-m.getX() - lineWidth / 2d) / (arrow.getTBarSizeDim() + arrow.getTBarSizeNum() * lineWidth));

		if(is180Rotation(path)) {
			arrow.setArrowStyle(arrow.isLeftArrow() ? ArrowStyle.RIGHT_SQUARE_BRACKET : ArrowStyle.LEFT_SQUARE_BRACKET);
		}else {
			arrow.setArrowStyle(arrow.isLeftArrow() ? ArrowStyle.LEFT_SQUARE_BRACKET : ArrowStyle.RIGHT_SQUARE_BRACKET);
		}
	}

	private void setArrowBarBracket(final SVGPathElement path, final SVGPathSegMoveto m, final double lineWidth, final SVGPathSeg seg,
									final SVGPathSegList list, final String svgMarker) {
		final double tbarNum = MathUtils.INST.parserDouble(
			path.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ARROW_TBAR_SIZE_NUM)).orElse(1d);
		arrow.setTBarSizeNum(tbarNum);
		arrow.setTBarSizeDim(-m.getY() * 2d - tbarNum * lineWidth);

		// legacy code
		if(seg instanceof SVGPathSegCurvetoCubic) {
			setArrowBarBracketLegacy(m, lineWidth, svgMarker);
			return;
		}

		if(seg instanceof SVGPathSegArc) {
			setArrowBarBracketRoundBracket(path, seg, lineWidth, tbarNum);
			return;
		}

		// It may be a bracket.
		if(isBarBracketBracket(list, seg)) {
			setArrowBarBracketSquareBracket(path, m, lineWidth, tbarNum);
			return;
		}

		// square end
		if(isBarBracketSquareEnd(list, m, seg)) {
			arrow.setArrowStyle(ArrowStyle.SQUARE_END);
			return;
		}

		if(isBarBracketBar(list, m, seg)) {
			arrow.setArrowStyle(MathUtils.INST.equalsDouble(m.getX(), 0d) ? ArrowStyle.BAR_END : ArrowStyle.BAR_IN);
		}
	}

	/**
	 * Companion method of setArrowBarBracket
	 */
	private boolean isBarBracketBracket(final SVGPathSegList list, final SVGPathSeg seg) {
		return list.size() == 4 && seg instanceof SVGPathSegLineto && list.get(2) instanceof SVGPathSegLineto && list.get(3) instanceof SVGPathSegLineto;
	}

	/**
	 * Companion method of setArrowBarBracket
	 */
	private boolean isBarBracketSquareEnd(final SVGPathSegList list, final SVGPathSegMoveto m, final SVGPathSeg seg) {
		return list.size() == 2 && MathUtils.INST.equalsDouble(0d, m.getX()) && MathUtils.INST.equalsDouble(0d, m.getY()) &&
			seg instanceof SVGPathSegLineto && MathUtils.INST.equalsDouble(0d, ((SVGPathSegLineto) seg).getY());
	}

	/**
	 * Companion method of setArrowBarBracket
	 */
	private boolean isBarBracketBar(final SVGPathSegList list, final SVGPathSegMoveto m, final SVGPathSeg seg) {
		return list.size() == 2 && ((seg instanceof SVGPathSegLineto && MathUtils.INST.equalsDouble(((SVGPathSegLineto) seg).getX(), m.getX())) ||
			seg instanceof SVGPathSegLinetoVertical);
	}

	private boolean checkArrowArrow(final SVGPathSegList list, final SVGPathSeg seg) {
		return !(seg instanceof SVGPathSegLineto && list.get(2) instanceof SVGPathSegLineto && list.get(3) instanceof SVGPathSegLineto &&
			list.get(4) instanceof SVGPathSegClosePath);
	}

	private void setArrowArrow(final SVGPathElement path, final SVGPathSegMoveto m, final double lineWidth, final SVGPathSeg seg, final SVGPathSegList list,
							final String svgMarker) {
		if(checkArrowArrow(list, seg)) {
			return;
		}

		final boolean moveIs0 = MathUtils.INST.equalsDouble(m.getX(), 0d) && MathUtils.INST.equalsDouble(m.getY(), 0d);
		final boolean isStartArrow = SVGAttributes.SVG_MARKER_START.equals(svgMarker);
		final boolean doubleArrow;

		if(list.size() != 5) {
			arrow.setArrowStyle(moveIs0 ? ArrowStyle.LEFT_DBLE_ARROW : ArrowStyle.RIGHT_DBLE_ARROW);
			doubleArrow = true;
		}else {
			arrow.setArrowStyle(moveIs0 ? ArrowStyle.LEFT_ARROW : ArrowStyle.RIGHT_ARROW);
			doubleArrow = false;
		}

		if(!isStartArrow) {
			arrow.setArrowStyle(arrow.getArrowStyle().getOppositeArrowStyle());
		}

		arrow.setArrowSizeNum(MathUtils.INST.parserDouble(
			path.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ARROW_SIZE_NUM)).orElse(1d));
		arrow.setArrowSizeDim(((SVGPathSegLineto) list.get(1)).getY() * -2d - arrow.getArrowSizeNum() * lineWidth);

		if(arrow.isInverted()) {
			final double gap = doubleArrow ? arrow.getArrowShapedWidth() * 2d : arrow.getArrowShapedWidth();
			arrow.setArrowLength(m.getX() / gap);
		}else {
			arrow.setArrowLength(-((SVGPathSegLineto) seg).getX() / arrow.getArrowShapedWidth());
		}

		arrow.setArrowInset(Math.abs(
			((SVGPathSegLineto) seg).getX() - ((SVGPathSegLineto) list.get(2)).getX()) / (arrow.getArrowLength() * arrow.getArrowShapedWidth()));
	}


	/**
	 * Initialises the arrowhead using a path arrow.
	 * @param path The path element.
	 * @param owner The shape that has the arrow.
	 */
	final void setArrow(final SVGPathElement path, final Shape owner, final String svgMarker) {
		final SVGPathSegList list = path.getSegList();
		final SVGPathSegMoveto m = (SVGPathSegMoveto) list.get(0);
		final double lineWidth = owner.hasDbleBord() ? owner.getDbleBordSep() + 2d * owner.getThickness() : owner.getThickness();

		// It may be a bar or a bracket
		// == 4 is legacy code for 3.x
		if(list.size() == 2 || list.size() == 4 || list.size() == 6) {
			setArrowBarBracket(path, m, lineWidth, list.get(1), list, svgMarker);
		}else {
			// It may be an arrow or a double arrow
			// == 10 is legacy code for the 3.x branch. Now the double arrow has 12 elements.
			if(list.size() == 5 || list.size() == 10 || list.size() == 12) {
				setArrowArrow(path, m, lineWidth, list.get(1), list, svgMarker);
			}
		}
	}


	/**
	 * Return the SVG tree of the arrowhead or null if this arrowhead has no style.
	 * @param document The document used to create elements.
	 * @param isShadow True: this operation is call to create the SVG shadow of the shape.
	 * @return The SVG tree of the arrowhead or null
	 */
	SVGElement toSVG(final @NotNull SVGDocument document, final boolean isShadow) {
		if(!arrow.hasStyle()) {
			return null;
		}

		final Line line = arrow.getArrowLine();
		final double lineAngle = (-line.getLineAngle() + Math.PI * 2d) % (Math.PI * 2d);

		currentDoc = document;
		currentMarker = new SVGMarkerElement(document);
		currentMarker.setAttribute(SVGAttributes.SVG_OVERFLOW, SVGAttributes.SVG_VALUE_VISIBLE);
		currentMarker.setAttribute(SVGAttributes.SVG_MARKER_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);

		if(arrow.getArrowStyle() != ArrowStyle.NONE && !MathUtils.INST.equalsDouble(lineAngle, 0d)) {
			currentMarker.setAttribute(SVGAttributes.SVG_ORIENT, MathUtils.INST.format.format(Math.toDegrees(lineAngle)));
		}else {
			currentMarker.setAttribute(SVGAttributes.SVG_ORIENT, SVGAttributes.SVG_VALUE_AUTO);
		}

		updatePath(isShadow);

		if(currentPath != null) {
			createPathElement();
			currentPathElt.setAttribute(SVGAttributes.SVG_D, currentPath.toString());
			currentPathElt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ARROW_SIZE_NUM,
				MathUtils.INST.format.format(arrow.getArrowSizeNum()));
			currentPathElt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ARROW_TBAR_SIZE_NUM,
				MathUtils.INST.format.format(arrow.getTBarSizeNum()));

		}

		return currentMarker;
	}

	private void createPathElement() {
		if(currentMarker != null && currentDoc != null && currentPathElt == null) {
			currentPathElt = new SVGPathElement(currentDoc);

			currentMarker.appendChild(currentPathElt);
		}
	}

	private void createPath() {
		if(currentPath == null) {
			currentPath = new SVGPathSegList();
		}
	}

	@Override
	public Arrow getArrow() {
		return arrow;
	}

	@Override
	public void setTranslation(final double tx, final double ty) {
		// Not used as markers are relative to their object.
	}

	@Override
	public void createMoveTo(final double x, final double y) {
		createPath();
		currentPath.add(new SVGPathSegMoveto(x, y, false));
	}

	@Override
	public void createLineTo(final double x, final double y) {
		createPath();
		currentPath.add(new SVGPathSegLineto(x, y, false));
	}

	@Override
	public void createClosePath() {
		createPath();
		currentPath.add(new SVGPathSegClosePath());
	}

	@Override
	public void createArc(final double cx, final double cy, final double rx, final double ry, final double angle, final double length, final
							ObservableValue<Color> strokeProp, final ObservableDoubleValue strokeWidthProp) {
		final boolean sweepFlag = angle < 0d ^ arrow.isInverted() ^ !arrow.isLeftArrow();
		createPath();
		currentPath.add(new SVGPathSegMoveto(cx + rx * Math.cos(Math.toRadians(angle)), cy - ry * Math.sin(Math.toRadians(angle)), false));
		currentPath.add(new SVGPathSegArc(cx + rx * Math.cos(Math.toRadians(angle + length)),
			cy - ry * Math.sin(Math.toRadians(angle + length)), rx, ry, 0, false, sweepFlag, false));
		setPathStroke(strokeProp);
		setPathStrokeWidth(strokeWidthProp);
		setPathFill(null);
	}

	@Override
	public void createCircle(final double cx, final double cy, final double r) {
		if(currentDoc != null && currentMarker != null) {
			currentCircle = new SVGCircleElement(currentDoc);
			currentCircle.setAttribute(SVGAttributes.SVG_R, MathUtils.INST.format.format(r));
			currentCircle.setAttribute(SVGAttributes.SVG_CX, MathUtils.INST.format.format(cx));
			currentCircle.setAttribute(SVGAttributes.SVG_CY, MathUtils.INST.format.format(cy));
			currentCircle.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ARROW_DOT_SIZE_NUM,
				MathUtils.INST.format.format(arrow.getDotSizeNum()));
			currentMarker.appendChild(currentCircle);
		}
	}

	@Override
	public void setPathStrokeWidth(final ObservableDoubleValue widthProp) {
		createPathElement();
		if(currentPathElt != null) {
			currentPathElt.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, MathUtils.INST.format.format(widthProp.get()));
		}
	}

	@Override
	public void setPathFill(final ObservableValue<Color> fill) {
		createPathElement();
		if(currentPathElt != null) {
			final String col = fill == null ? SVGAttributes.SVG_VALUE_NONE :
				CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorFX(fill.getValue()), true);
			currentPathElt.setAttribute(SVGAttributes.SVG_FILL, col);
		}
	}

	@Override
	public void setCircleStrokeBinding(final ObservableValue<Color> stroke) {
		setCircleStroke(stroke.getValue());
	}

	@Override
	public void setCircleFillBinding(final ObservableValue<Color> fill) {
		setCircleFill(fill.getValue());
	}

	@Override
	public void setCircleFill(final Color fill) {
		if(currentCircle != null) {
			currentCircle.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorFX(fill), true));
		}
	}

	@Override
	public void setCircleStroke(final Color stroke) {
		if(currentCircle != null) {
			final String col = stroke == null ? SVGAttributes.SVG_VALUE_NONE : CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorFX(stroke), true);
			currentCircle.setAttribute(SVGAttributes.SVG_STROKE, col);
		}
	}

	@Override
	public void setCircleStrokeWidth(final double width) {
		if(currentCircle != null) {
			currentCircle.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, MathUtils.INST.format.format(width));
		}
	}

	@Override
	public void setPathStroke(final ObservableValue<Color> stroke) {
		createPathElement();
		if(currentPathElt != null) {
			currentPathElt.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorFX(stroke.getValue()), true));
		}
	}

	@Override
	public void setRotation180() {
		final String rotate = "rotate(180 0 0)"; //NON-NLS
		if(currentPathElt != null) {
			currentPathElt.setAttribute(SVGAttributes.SVG_TRANSFORM, rotate);
		}
		if(currentCircle != null) {
			currentCircle.setAttribute(SVGAttributes.SVG_TRANSFORM, rotate);
		}
	}
}
