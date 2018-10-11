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
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * The JFX shape view for dot shapes.
 * @author Arnaud Blouin
 */
public class ViewDot extends ViewShape<IDot> {
	final Path path;
	final Ellipse dot;
	final ChangeListener<Object> updateDot = (observable, oldValue, newValue) -> updateDot();
	final ChangeListener<Object> updateStrokeFill = (observable, oldValue, newValue) -> {
		setFill();
		setStroke();
	};


	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewDot(final IDot sh) {
		super(sh);
		path = new Path();
		dot = new Ellipse();
		getChildren().addAll(dot, path);

		model.styleProperty().addListener(updateDot);
		model.getPosition().xProperty().addListener(updateDot); // FIXME to optimise by adding addListener in IPoint
		model.getPosition().yProperty().addListener(updateDot);
		model.diametreProperty().addListener(updateDot);
		model.fillingColProperty().addListener(updateDot);
		model.lineColourProperty().addListener(updateStrokeFill);
		rotateProperty().bind(Bindings.createDoubleBinding(() -> Math.toDegrees(model.getRotationAngle()), model.rotationAngleProperty()));
		updateDot();
	}

	private void updateDot() {
		final DotStyle dotStyle = model.getDotStyle();

		path.setVisible(dotStyle != DotStyle.DOT && dotStyle != DotStyle.O);
		dot.setVisible(dotStyle == DotStyle.DOT || dotStyle == DotStyle.O || dotStyle == DotStyle.OPLUS || dotStyle == DotStyle.OTIMES);

		path.getElements().clear();
		setStroke();
		setFill();

		switch(dotStyle) {
			case ASTERISK:
				setPathAsterisk();
				break;
			case BAR:
				setPathBar();
				break;
			case DIAMOND:
				setPathDiamond();
				break;
			case O:
			case DOT:
				setPathO();
				break;
			case FDIAMOND:
				setPathDiamond();
				break;
			case PENTAGON:
			case FPENTAGON:
				setPathPentagon();
				break;
			case SQUARE:
			case FSQUARE:
				setPathSquare();
				break;
			case TRIANGLE:
			case FTRIANGLE:
				setPathTriangle();
				break;
			case OPLUS:
				setPathOPlus();
				break;
			case OTIMES:
				setPathOTime();
				break;
			case PLUS:
				setPathPlus();
				break;
			case X:
				setPathX();
				break;
			default:
				// Nothing to do for the other ones.
		}
	}

	private void setPathOTime() {
		final IPoint centre = model.getPosition();
		final IPoint br = model.getLazyBottomRightPoint();
		final IPoint tl = model.getLazyTopLeftPoint();
		final double dec = model.getGeneralGap();
		final double tlx = tl.getX();
		final double tly = tl.getY();
		final double brx = br.getX();
		final double bry = br.getY();

		setPathOLikeDot(model.getOGap());

		IPoint p1 = ShapeFactory.INST.createPoint((tlx + brx) / 2d, tly + dec * 2d);
		IPoint p2 = ShapeFactory.INST.createPoint((tlx + brx) / 2d, bry - dec * 2d);
		p1 = p1.rotatePoint(centre, Math.PI / 4d);
		p2 = p2.rotatePoint(centre, Math.PI / 4d);

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(p1.getX(), p1.getY()));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(p2.getX(), p2.getY()));

		p1.setPoint(tlx + dec * 2d, (tly + bry) / 2d);
		p2.setPoint(brx - dec * 2d, (tly + bry) / 2d);
		p1 = p1.rotatePoint(centre, Math.PI / 4d);
		p2 = p2.rotatePoint(centre, Math.PI / 4d);

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(p1.getX(), p1.getY()));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(p2.getX(), p2.getY()));
	}


	private void setPathOPlus() {
		final double dec = model.getGeneralGap();
		final IPoint br = model.getLazyBottomRightPoint();
		final IPoint tl = model.getLazyTopLeftPoint();

		setPathOLikeDot(model.getOGap());
		path.getElements().add(ViewFactory.INSTANCE.createMoveTo((tl.getX() + br.getX()) / 2d, tl.getY() + dec * 2d));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo((tl.getX() + br.getX()) / 2d, br.getY() - dec * 2d));
		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(tl.getX() + dec * 2d, (tl.getY() + br.getY()) / 2d));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(br.getX() - dec * 2d, (tl.getY() + br.getY()) / 2d));
	}


	private void setPathO() {
		setPathOLikeDot(model.getOGap());
	}


	private void setPathOLikeDot(final double dec) {
		final IPoint pos = model.getPosition();
		final double radius = (model.getDiametre() - dec) / 2d;
		dot.setCenterX(pos.getX());
		dot.setCenterY(pos.getY());
		dot.setRadiusX(radius);
		dot.setRadiusY(radius);
	}


	private void setPathBar() {
		final IPoint br = model.getLazyBottomRightPoint();
		final IPoint tl = model.getLazyTopLeftPoint();

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo((tl.getX() + br.getX()) / 2d, tl.getY() + model.getBarThickness() / 2d));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo((tl.getX() + br.getX()) / 2d, br.getY() + model.getBarGap()));
	}


	private void setPathPlus() {
		final double plusGap = model.getPlusGap();
		final IPoint br = model.getLazyBottomRightPoint();
		final IPoint tl = model.getLazyTopLeftPoint();

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo((tl.getX() + br.getX()) / 2d, tl.getY() - plusGap));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo((tl.getX() + br.getX()) / 2d, br.getY() + plusGap));
		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(tl.getX() - plusGap, (tl.getY() + br.getY()) / 2d));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(br.getX() + plusGap, (tl.getY() + br.getY()) / 2d));
	}


	private void setPathSquare() {
		final double dec = model.getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR;
		final IPoint tl = model.getLazyTopLeftPoint();
		final double width = model.getDiametre() - dec * 3d;
		final double x = tl.getX() + dec + dec / 2d;
		final double y = tl.getY() + dec + dec / 2d;

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(x, y));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(x + width, y));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(x + width, y + width));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(x, y + width));
		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}


	private void setPathX() {
		final IPoint br = model.getLazyBottomRightPoint();
		final IPoint tl = model.getLazyTopLeftPoint();
		final double crossGap = model.getCrossGap();

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(tl.getX() + crossGap, tl.getY() + crossGap));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(br.getX() - crossGap, br.getY() - crossGap));
		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(br.getX() - crossGap, tl.getY() + crossGap));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(tl.getX() + crossGap, br.getY() - crossGap));
	}


	private void setPathAsterisk() {
		final IPoint br = model.getLazyBottomRightPoint();
		final IPoint tl = model.getLazyTopLeftPoint();
		final double width = model.getDiametre();
		final double dec = width / IDot.THICKNESS_O_STYLE_FACTOR;
		final double xCenter = (tl.getX() + br.getX()) / 2d;
		final double yCenter = (tl.getY() + br.getY()) / 2d;
		final double radius = Math.abs(tl.getY() + width / 10d - (br.getY() - width / 10d)) / 2d + dec;

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(xCenter, tl.getY() + width / 10d - dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(xCenter, br.getY() - width / 10d + dec));
		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(Math.cos(Math.PI / 6d) * radius + xCenter, radius / 2d + yCenter));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(Math.cos(7d * Math.PI / 6d) * radius + xCenter, Math.sin(7d * Math.PI / 6d) * radius + yCenter));
		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(Math.cos(5d * Math.PI / 6d) * radius + xCenter, Math.sin(5d * Math.PI / 6d) * radius + yCenter));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(Math.cos(11d * Math.PI / 6d) * radius + xCenter, Math.sin(11d * Math.PI / 6d) * radius + yCenter));
	}


	/**
	 * Creates a diamond (one of the possibles shapes of a dot).
	 */
	private void setPathDiamond() {
		final IPoint tl = model.getLazyTopLeftPoint();
		final IPoint br = model.getLazyBottomRightPoint();
		final double dec = model.getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR;
		// This diamond is a golden diamond
		// cf. http://mathworld.wolfram.com/GoldenRhombus.html
		final double midY = (tl.getY() + br.getY()) / 2d;
		final double a = Math.abs(tl.getX() - br.getX()) / (2d * Math.sin(IShape.GOLDEN_ANGLE));
		final double p = 2d * a * Math.cos(IShape.GOLDEN_ANGLE);
		final double x1 = br.getX() - dec - 0.5 * dec;
		final double x3 = tl.getX() + dec + 0.5 * dec;

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo((x1 + x3) / 2d, midY + p / 2d - dec - 0.5 * dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(x1, midY));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo((x1 + x3) / 2d, midY - p / 2d + dec + 0.5 * dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(x3, midY));
		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}


	/**
	 * Creates a pentagon (one of the possibles shapes of a dot)
	 */
	private void setPathPentagon() {
		final double dec = model.getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR;
		final IPoint tl = model.getLazyTopLeftPoint();
		final IPoint br = model.getLazyBottomRightPoint();
		final double yCenter = (tl.getY() + br.getY()) / 2d - dec;
		final double xCenter = (tl.getX() + br.getX()) / 2d;
		final double dist = Math.abs(tl.getY() - br.getY()) / 2d + dec;
		final double c1 = 0.25 * (Math.sqrt(5d) - 1d) * dist;
		final double s1 = Math.sin(2 * Math.PI / 5d) * dist;
		final double c2 = 0.25 * (Math.sqrt(5d) + 1d) * dist;
		final double s2 = Math.sin(4 * Math.PI / 5d) * dist;

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo(xCenter, tl.getY() - dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(s1 + xCenter, -c1 + yCenter + dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(s2 + xCenter, c2 + yCenter + dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(-s2 + xCenter, c2 + yCenter + dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(-s1 + xCenter, -c1 + yCenter + dec));
		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}


	/**
	 * Creates a triangle (one of the possibles shapes of a dot).
	 */
	private void setPathTriangle() {
		final IPoint tl = model.getLazyTopLeftPoint();
		final double dec = model.getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR;
		final IPoint br = model.getLazyBottomRightPoint();

		path.getElements().add(ViewFactory.INSTANCE.createMoveTo((br.getX() + tl.getX()) / 2d, tl.getY() - 1.5 * dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(tl.getX() - 0.3 * dec, br.getY() - 3d * dec));
		path.getElements().add(ViewFactory.INSTANCE.createLineTo(br.getX() + 0.3 * dec, br.getY() - 3d * dec));
		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}


	public void setFill() {
		switch(model.getDotStyle()) {
			case O:
				dot.setFill(model.getFillingCol().toJFX());
				break;
			case OPLUS:
			case OTIMES:
				dot.setFill(null);
				break;
			case DIAMOND:
			case PENTAGON:
			case SQUARE:
			case TRIANGLE:
				path.setFill(model.getFillingCol().toJFX());
				break;
			case DOT:
				dot.setFill(model.getLineColour().toJFX());
				break;
			case FDIAMOND:
			case FPENTAGON:
			case FSQUARE:
			case FTRIANGLE:
				path.setFill(model.getLineColour().toJFX());
				break;
			default:
				// Nothing to do for the other ones.
		}
	}


	private void setStroke() {
		path.setStroke(model.getLineColour().toJFX());
		dot.setStroke(model.getLineColour().toJFX());

		switch(model.getDotStyle()) {
			case O:
				dot.setStrokeLineCap(StrokeLineCap.SQUARE);
				dot.setStrokeWidth(model.getGeneralGap());
				break;
			case DOT:
				dot.setStrokeLineCap(StrokeLineCap.SQUARE);
				dot.setStrokeWidth(model.getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR);
				break;
			case OPLUS:
			case OTIMES:
				dot.setStrokeLineCap(StrokeLineCap.SQUARE);
				dot.setStrokeWidth(model.getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR);
				path.setStrokeLineCap(StrokeLineCap.BUTT);
				path.setStrokeWidth(model.getGeneralGap());
				break;
			case FTRIANGLE:
			case TRIANGLE:
			case FPENTAGON:
			case PENTAGON:
			case FDIAMOND:
			case DIAMOND:
			case ASTERISK:
				path.setStrokeLineCap(StrokeLineCap.SQUARE);
				path.setStrokeWidth(model.getGeneralGap());
				break;
			case BAR:
				path.setStrokeLineCap(StrokeLineCap.SQUARE);
				path.setStrokeWidth(model.getBarThickness());
				break;
			case FSQUARE:
			case SQUARE:
				path.setStrokeLineCap(StrokeLineCap.BUTT);
				path.setStrokeWidth(model.getGeneralGap());
				break;
			case PLUS:
				path.setStrokeLineCap(StrokeLineCap.SQUARE);
				path.setStrokeWidth(model.getDiametre() / IDot.PLUS_COEFF_WIDTH);
				break;
			case X:
				path.setStrokeLineCap(StrokeLineCap.SQUARE);
				path.setStrokeWidth(model.getCrossGap());
				break;
		}
	}

	@Override
	public void flush() {
		model.lineColourProperty().removeListener(updateStrokeFill);
		model.styleProperty().removeListener(updateDot);
		model.getPosition().xProperty().removeListener(updateDot);
		model.getPosition().yProperty().removeListener(updateDot);
		model.diametreProperty().removeListener(updateDot);
		model.fillingColProperty().removeListener(updateDot);
		super.flush();
	}
}
