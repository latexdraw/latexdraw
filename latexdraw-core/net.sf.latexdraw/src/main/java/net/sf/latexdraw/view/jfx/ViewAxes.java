/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;

/**
 * The JFX view of a axes.
 * @author Arnaud Blouin
 */
public class ViewAxes extends ViewStdGrid<IAxes> {
	/** The interval between the labels and the axes. */
	private static final double GAP_LABEL = 5d;

	private final Path mainAxes;
	private final Path pathTicks;
//	private final ViewArrowableTrait viewArrows;
	private final ChangeListener<Object> labelUpdate;
	private final ChangeListener<Object> labelTicksUpdate;
	private final ChangeListener<Object> fullAxesUpdate;
	private final ChangeListener<Object> ticksUpdate;
	private final ChangeListener<Object> axesUpdate;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewAxes(final IAxes sh) {
		super(sh);
		labelUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, false, true, false));
		labelTicksUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, true, true, false));
		fullAxesUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(true, true, true, false));
		ticksUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, true, false, false));
		axesUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(true, false, false, false));

		mainAxes = new Path();
		pathTicks = new Path();
//		viewArrows = new ViewArrowableTrait(this);

		getChildren().add(mainAxes);
		getChildren().add(pathTicks);
//		getChildren().add(viewArrows);

		model.labelsSizeProperty().addListener(labelUpdate);
		model.gridEndXProperty().addListener(fullAxesUpdate);
		model.gridEndYProperty().addListener(fullAxesUpdate);
		model.gridStartXProperty().addListener(fullAxesUpdate);
		model.gridStartYProperty().addListener(fullAxesUpdate);
		model.originXProperty().addListener(labelUpdate);
		model.originYProperty().addListener(labelUpdate);
		model.incrementXProperty().addListener(labelUpdate);
		model.incrementYProperty().addListener(labelUpdate);
		model.distLabelsXProperty().addListener(labelTicksUpdate);
		model.distLabelsYProperty().addListener(labelTicksUpdate);
		model.labelsDisplayedProperty().addListener(labelUpdate);
		model.showOriginProperty().addListener(labelUpdate);
		model.ticksDisplayedProperty().addListener(ticksUpdate);
		model.ticksStyleProperty().addListener(ticksUpdate);
		model.ticksSizeProperty().addListener(ticksUpdate);
		model.axesStyleProperty().addListener(axesUpdate);

		updatePath(true, true, true, true);
	}


	private void updatePathTicksX(final double gapx, final TicksStyle ticksStyle, final double tickLgth) {
		final boolean noArrowLeftX = model.getArrowStyle(1) == ArrowStyle.NONE || model.getGridMinX() == model.getOriginX();
		final boolean noArrowRightX = model.getArrowStyle(3) == ArrowStyle.NONE || model.getGridMaxX() == model.getOriginX();
		final double distX = model.getDistLabelsX();
		double x;
		final double y;
		int inti;

		switch(ticksStyle) {
			case FULL:
				y = tickLgth / 2d;
				break;
			case TOP:
				y = 0d;
				break;
			default:
				y = tickLgth;
		}

		for(double incrx = model.getIncrementX(), maxx = model.getGridMaxX() / distX, minx = model.getGridMinX() / distX, i = minx * incrx;
			i <= maxx * incrx; i += incrx * distX) {
			inti = (int) i;
			if(isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				x = inti * gapx;
				pathTicks.getElements().add(ViewFactory.INSTANCE.createMoveTo(x, y));
				pathTicks.getElements().add(ViewFactory.INSTANCE.createLineTo(x, y - tickLgth));
			}
		}
	}


	/**
	 * @return True if a ticks or a label corresponding to the given parameter can be painted.
	 */
	private boolean isElementPaintable(final boolean noArrow1, final boolean noArrow2, final double min, final double max, final double i) {
		return (noArrow2 || !MathUtils.INST.equalsDouble(max, i)) && (noArrow1 || !MathUtils.INST.equalsDouble(min, i));
	}


	private void updatePathTicksY(final double gapy, final TicksStyle ticksStyle, final double tickLgth) {
		final boolean noArrowTopY = model.getArrowStyle(2) == ArrowStyle.NONE || model.getGridMaxY() == model.getOriginY();
		final boolean noArrowBotY = model.getArrowStyle(0) == ArrowStyle.NONE || model.getGridMinY() == model.getOriginY();
		final double distY = model.getDistLabelsY();
		final double x;
		double y;
		int inti;

		switch(ticksStyle) {
			case FULL:
				x = -tickLgth / 2d;
				break;
			case TOP:
				x = 0d;
				break;
			default:
				x = -tickLgth;
		}

		for(double incry = model.getIncrementY(), maxy = model.getGridMaxY() / distY, miny = model.getGridMinY() / distY, i = miny * incry;
			i <= maxy * incry; i += incry * distY) {
			inti = (int) i;
			if(isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				y = -inti * gapy;
				pathTicks.getElements().add(ViewFactory.INSTANCE.createMoveTo(x, y));
				pathTicks.getElements().add(ViewFactory.INSTANCE.createLineTo(x + tickLgth, y));
			}
		}
	}


	private void updatePathTicks(final double gapx, final double gapy) {
		final PlottingStyle ticksDisplay = model.getTicksDisplayed();
		final TicksStyle ticksStyle = model.getTicksStyle();
		final double tickLgth = ticksStyle == TicksStyle.FULL ? model.getTicksSize() * 2d : model.getTicksSize();

		if(ticksDisplay.isX()) {
			updatePathTicksX(gapx, ticksStyle, tickLgth);
		}

		if(ticksDisplay.isY()) {
			updatePathTicksY(gapy, ticksStyle, tickLgth);
		}

		pathTicks.setDisable(!ticksDisplay.isX() && !ticksDisplay.isY());
		pathTicks.setFill(Color.BLACK);
	}


	private void updatePathLabelsY(final PlottingStyle ticksDisplay, final TicksStyle ticksStyle, final double gapy, final Text fontText) {
		final int origy = (int) model.getOriginY();
		final double gap;
		final double height = fontText.getBaselineOffset();
		final boolean noArrowTopY = model.getArrowStyle(2) == ArrowStyle.NONE || model.getGridMaxY() == model.getOriginY();
		final boolean noArrowBotY = model.getArrowStyle(0) == ArrowStyle.NONE || model.getGridMinY() == model.getOriginY();
		final boolean showOrig = model.isShowOrigin();
		final double distY = model.getDistLabelsY();
		final boolean xGE0 = model.getGridMinX() >= 0;
		String str;

		if(ticksStyle.isBottom() && ticksDisplay.isY()) {
			gap = -(model.getTicksSize() + model.getThickness() / 2d + GAP_LABEL);
		}else {
			gap = -(model.getThickness() / 2d + GAP_LABEL);
		}

		for(double incry = model.getIncrementY(), maxy = model.getGridMaxY() / distY, miny = model.getGridMinY() / distY, i = miny * incry; i <= maxy * incry;
			i += incry * distY) {
			int inti = (int) i;
			if((inti != 0 || showOrig && xGE0) && isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				str = String.valueOf(inti + origy);
				fontText.setText(str);
				addTextLabel(str, gap - fontText.getBoundsInLocal().getWidth(), height / 2d - inti * gapy, fontText.getFont());
			}
		}
	}


	/**
	 * Updates the labels path by drawing the labels of the X-axis.
	 */
	private void updatePathLabelsX(final PlottingStyle ticksDisplay, final TicksStyle ticksStyle, final double gapx, final Text fontText) {
		// Painting the labels on the X-axis.
		final int origx = (int) model.getOriginX();
		final double gap = (ticksDisplay.isX() && ticksStyle.isBottom() ? model.getTicksSize() : 0d) + model.getThickness() / 2d + GAP_LABEL;
		final double sep = model.getGridMaxY() <= -model.getOriginY() ? -gap - GAP_LABEL : gap + fontText.getBaselineOffset();
		final boolean noArrowLeftX = model.getArrowStyle(1) == ArrowStyle.NONE || model.getGridMinX() == model.getOriginX();
		final boolean noArrowRightX = model.getArrowStyle(3) == ArrowStyle.NONE || model.getGridMaxX() == model.getOriginX();
		final boolean showOrig = model.isShowOrigin();
		final double distX = model.getDistLabelsX();
		final boolean yGE0 = model.getGridMinY() >= 0;
		String str;

		for(double incrx = model.getIncrementX(), maxx = model.getGridMaxX() / distX, minx = model.getGridMinX() / distX, i = minx * incrx; i <= maxx * incrx; i += incrx * distX) {
			int inti = (int) i;
			if((inti != 0 || showOrig && yGE0) && isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				str = String.valueOf(inti + origx);
				fontText.setText(str);
				addTextLabel(str, inti * gapx - fontText.getBoundsInLocal().getWidth() / 2d, sep, fontText.getFont());
			}
		}
	}


	private void updatePathLabels(final double gapx, final double gapy) {
		final Font font = new Font("cmr10", model.getLabelsSize());
		final PlottingStyle labelsDisplay = model.getLabelsDisplayed();
		final PlottingStyle ticksDisplay = model.getTicksDisplayed();
		final TicksStyle ticksStyle = model.getTicksStyle();
		// This fake text is used to compute widths and heights and other font metrics of a current text.
		final Text fooText = new Text("foo");
		fooText.setFont(font);

		if(labelsDisplay.isX()) {
			updatePathLabelsX(ticksDisplay, ticksStyle, gapx, fooText);
		}

		if(labelsDisplay.isY()) {
			updatePathLabelsY(ticksDisplay, ticksStyle, gapy, fooText);
		}
	}


	private void updatePathFrame() {
		final double endx = model.getGridEndX();
		final double endy = model.getGridEndY();

		if(endx > 0d || endy > 0d) {
			final double y1 = endy > 0d ? - endy * IShape.PPC : 0d;
			final double x2 = endx > 0d ? + endx * IShape.PPC : 0d;

			mainAxes.getElements().add(ViewFactory.INSTANCE.createMoveTo(0d, y1));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(x2, y1));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(x2, 0d));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(0d, 0d));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createClosePath());
		}
	}


	private void updatePathAxes() {
		final IArrow arr0 = model.getArrowAt(1);
		final IArrow arr1 = model.getArrowAt(3);
		final double arr0Reduction = arr0.getArrowStyle().needsLineReduction() ? arr0.getArrowShapedWidth() : 0d;
		final double arr1Reduction = arr1.getArrowStyle().needsLineReduction() ? arr1.getArrowShapedWidth() : 0d;

		mainAxes.getElements().add(ViewFactory.INSTANCE.createMoveTo(model.getGridStartX() * IShape.PPC + arr0Reduction, 0d));
		mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(model.getGridEndX() * IShape.PPC - arr1Reduction, 0d));
		mainAxes.getElements().add(ViewFactory.INSTANCE.createMoveTo(0d, -model.getGridStartY() * IShape.PPC - arr0Reduction));
		mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(0d, -model.getGridEndY() * IShape.PPC + arr1Reduction));
	}


	public void updatePath(final boolean axes, final boolean ticks, final boolean texts, final boolean arrows) {
		final double incrx = model.getIncrementX();
		final double incry = model.getIncrementY();
		final double distX = model.getDistLabelsX();
		final double distY = model.getDistLabelsY();
		final AxesStyle axesStyle = model.getAxesStyle();
		final double gapX = MathUtils.INST.equalsDouble(distX, 0d) ? IShape.PPC : distX / incrx * IShape.PPC;
		final double gapY = MathUtils.INST.equalsDouble(distY, 0d) ? IShape.PPC : distY / incry * IShape.PPC;

		if(arrows || axes) {
//			viewArrows.update(model.getAxesStyle().supportsArrows());
		}

		if(axes) {
			mainAxes.getElements().clear();
			switch(axesStyle) {
				case AXES:
					updatePathAxes();
					break;
				case FRAME:
					updatePathFrame();
					break;
				case NONE:
					break;
			}
		}

		if(ticks) {
			pathTicks.getElements().clear();
			updatePathTicks(gapX, gapY);
		}

		if(texts) {
			cleanLabels();
			if(model.getLabelsDisplayed() != PlottingStyle.NONE) {
				labels.setDisable(false);
				updatePathLabels(gapX, gapY);
			}else {
				labels.setDisable(true);
			}
		}
	}


	//	@Override
	//	public void paint(final Graphics2D g, final Rectangle clip) {
	//		final IPoint vectorTrans = beginRotation(g);
	//
	//		g.setStroke(getStroke());
	//		g.setColor(model.getLineColour());
	//		g.draw(path);
	//		g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
	//		g.draw(pathTicks);
	//
	//		if(model.getAxesStyle().supportsArrows())
	//			paintArrows(g, false);
	//
	//		if(model.getLabelsDisplayed()!=PlottingStyle.NONE) {
	//			g.setColor(Color.BLACK);
	//			g.fill(pathLabels);
	//		}
	//
	//		if(vectorTrans!=null)
	//			endRotation(g, vectorTrans);
	//	}
	//
	//
	//	@Override
	//	protected void paintArrows(final Graphics2D g, final boolean asShadow) {
	//		if(arrows.size()==4) {
	//			final Color colour = asShadow ? model.getShadowCol() : model.getFillingCol();
	//			arrows.forEach(arr -> arr.paint(g, colour, asShadow));
	//		}
	//	}


	@Override
	public void flush() {
		mainAxes.getElements().clear();
		pathTicks.getElements().clear();
		model.labelsSizeProperty().removeListener(labelUpdate);
		model.gridEndXProperty().removeListener(fullAxesUpdate);
		model.gridEndYProperty().removeListener(fullAxesUpdate);
		model.gridStartXProperty().removeListener(fullAxesUpdate);
		model.gridStartYProperty().removeListener(fullAxesUpdate);
		model.originXProperty().removeListener(labelUpdate);
		model.originYProperty().removeListener(labelUpdate);
		model.incrementXProperty().removeListener(labelUpdate);
		model.incrementYProperty().removeListener(labelUpdate);
		model.distLabelsXProperty().removeListener(labelTicksUpdate);
		model.distLabelsYProperty().removeListener(labelTicksUpdate);
		model.labelsDisplayedProperty().removeListener(labelUpdate);
		model.showOriginProperty().removeListener(labelUpdate);
		model.ticksDisplayedProperty().removeListener(ticksUpdate);
		model.ticksStyleProperty().removeListener(ticksUpdate);
		model.ticksSizeProperty().removeListener(ticksUpdate);
		model.axesStyleProperty().removeListener(axesUpdate);
		super.flush();
	}

	/**
	 * @return The main axes path.
	 */
	public Path getMainAxes() {
		return mainAxes;
	}

	/**
	 * @return The ticks path.
	 */
	public Path getPathTicks() {
		return pathTicks;
	}
}
