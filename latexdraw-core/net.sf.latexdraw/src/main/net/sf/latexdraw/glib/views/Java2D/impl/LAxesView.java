package net.sf.latexdraw.glib.views.Java2D.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IAxes model.<br>
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
 * 04/12/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LAxesView extends LStandardGridView<IAxes> {
	/** The interval between the labels and the axes. */
	public static final double GAP_LABEL = 5.;

	/** The path containing the data to paint concerning the ticks of the axes. */
	protected Path2D pathTicks;


	protected LAxesView(final IAxes model) {
		super(model);
		pathTicks = new Path2D.Double();
		update();
	}


	/**
	 * Updates the ticks path by drawing the ticks of the X-axis.
	 */
	private void updatePathTicksX(final double gapx, final TicksStyle ticksStyle, final double tickLgth) {
		final int origx 	= (int)shape.getOriginX();
		final double posx 	= shape.getPosition().getX();
		final double posy 	= shape.getPosition().getY();
		final boolean noArrowLeftX = shape.getArrowStyle(1)==ArrowStyle.NONE;
		final boolean noArrowRightX = shape.getArrowStyle(3)==ArrowStyle.NONE;
		final double distX = shape.getDistLabelsX();
		double x;
		final double y;
		int val;
		int inti;

		switch(ticksStyle) {
			case FULL: 	y = posy + tickLgth/2.; break;
			case TOP:	y = posy; break;
			default: 	y = posy + tickLgth;
		}

		for(double incrx = shape.getIncrementX(), maxx = shape.getGridMaxX()/distX, minx = shape.getGridMinX()/distX, i=origx; i<=maxx*incrx; i+=incrx) {
			inti = (int)i;
			val = inti+origx;
			if(isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				x = posx+val*gapx;
				pathTicks.moveTo(x, y);
				pathTicks.lineTo(x, y-tickLgth);
			}
		}
		for(double incrx = shape.getIncrementX(), maxx = shape.getGridMaxX()/distX, minx = shape.getGridMinX()/distX, i=origx-incrx; i>=minx*incrx; i-=incrx) {
			inti = (int)i;
			val = inti+origx;
			if(isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				x = posx+val*gapx;
				pathTicks.moveTo(x, y);
				pathTicks.lineTo(x, y-tickLgth);
			}
		}
	}


	/**
	 * Updates the ticks path by drawing the ticks of the Y-axis.
	 */
	private void updatePathTicksY(final double gapy, final TicksStyle ticksStyle, final double tickLgth) {
		final int origy 	= (int)shape.getOriginY();
		final double posx 	= shape.getPosition().getX();
		final double posy 	= shape.getPosition().getY();
		final boolean noArrowTopY = shape.getArrowStyle(2)==ArrowStyle.NONE;
		final boolean noArrowBotY  = shape.getArrowStyle(0)==ArrowStyle.NONE;
		final double distY = shape.getDistLabelsY();
		final double x;
		double y;
		int val;
		int inti;

		switch(ticksStyle) {
			case FULL: 	x = posx - tickLgth/2.; break;
			case TOP:	x = posx; break;
			default: 	x = posx - tickLgth;
		}

		for(double incry = shape.getIncrementY(), maxy = shape.getGridMaxY()/distY, miny = shape.getGridMinY()/distY, i=origy; i<=maxy*incry; i+=incry) {
			inti = (int)i;
			val = inti+origy;
			if(isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				y = posy-val*gapy;
				pathTicks.moveTo(x, y);
				pathTicks.lineTo(x+tickLgth, y);
			}
		}
		for(double incry = shape.getIncrementY(), maxy = shape.getGridMaxY()/distY, miny = shape.getGridMinY()/distY, i=origy-incry; i>=miny*incry; i-=incry) {
			inti = (int)i;
			val = inti+origy;
			if(isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				y = posy-val*gapy;
				pathTicks.moveTo(x, y);
				pathTicks.lineTo(x+tickLgth, y);
			}
		}
	}


	/**
	 * Updates the ticks path by drawing the ticks of the X/Y-axis.
	 */
	private void updatePathTicks(final double gapx, final double gapy) {
		final PlottingStyle ticksDisplay = shape.getTicksDisplayed();
		final TicksStyle ticksStyle = shape.getTicksStyle();
		final double tickLgth = ticksStyle==TicksStyle.FULL ? shape.getTicksSize()*2. : shape.getTicksSize();

		if(ticksDisplay.isX())
			updatePathTicksX(gapx, ticksStyle, tickLgth);

		if(ticksDisplay.isY())
			updatePathTicksY(gapy, ticksStyle, tickLgth);
	}


	/**
	 * Updates the labels path by drawing the labels of the Y-axis.
	 */
	private void updatePathLabelsY(final PlottingStyle ticksDisplay, final TicksStyle ticksStyle, final double gapy, final FontRenderContext frc) {
		final double posx 		= shape.getPosition().getX();
		final double posy 		= shape.getPosition().getY();
		final int origy 		= (int)shape.getOriginY();
		final double incry 		= shape.getIncrementY();
		final double gap;
		final Font font 		= fontMetrics.getFont();
		final int height 		= fontMetrics.getAscent();
		final boolean noArrowTopY = shape.getArrowStyle(2)==ArrowStyle.NONE;
		final boolean noArrowBotY  = shape.getArrowStyle(0)==ArrowStyle.NONE;
		final boolean showOrig = shape.isShowOrigin();
		final double distY = shape.getDistLabelsY();
		final boolean xGE0 = shape.getGridMinX()>=shape.getOriginX();
		String str;
		int val;
		int inti;

		if(ticksStyle.isBottom() && ticksDisplay.isY())
			gap = -(shape.getTicksSize() + shape.getThickness()/2. + GAP_LABEL);
		else
			gap = -(shape.getThickness()/2. + GAP_LABEL);

		for(double maxy = shape.getGridMaxY()/distY, miny = shape.getGridMinY()/distY, i=origy; i<=maxy*incry; i+=incry) {
			inti = (int)i;
			val = inti+origy;
			if((!showOrig || val!=origy || xGE0) && isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				str	 = String.valueOf(val+origy);
				updateText(str, (float)(posx+gap-fontMetrics.stringWidth(str)), (float)(posy+height/2.-val*gapy), font, frc);
			}
		}
		for(double maxy = shape.getGridMaxY()/distY, miny = shape.getGridMinY()/distY, i=origy-incry; i>=miny*incry; i-=incry) {
			inti = (int)i;
			val = inti+origy;
			if(isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				str	 = String.valueOf(val+origy);
				updateText(str, (float)(posx+gap-fontMetrics.stringWidth(str)), (float)(posy+height/2.-val*gapy), font, frc);
			}
		}
	}


	/**
	 * Updates the labels path by drawing the labels of the X-axis.
	 */
	private void updatePathLabelsX(final PlottingStyle ticksDisplay, final TicksStyle ticksStyle, final double gapx, final FontRenderContext frc) {
		// Painting the labels on the X-axis.
		final double posx 		= shape.getPosition().getX();
		final double posy 		= shape.getPosition().getY();
		final int origx 		= (int)shape.getOriginX();
		final double incrx 		= shape.getIncrementX();
		final double gap 		= (ticksDisplay.isX() && ticksStyle.isBottom() ? shape.getTicksSize() : 0) + shape.getThickness()/2. + GAP_LABEL;
		final double sep 		= shape.getGridMaxY()<=-shape.getOriginY() ? -gap-GAP_LABEL : gap + fontMetrics.getAscent();
		final Font font 		= fontMetrics.getFont();
		final boolean noArrowLeftX = shape.getArrowStyle(1)==ArrowStyle.NONE;
		final boolean noArrowRightX = shape.getArrowStyle(3)==ArrowStyle.NONE;
		final boolean showOrig = shape.isShowOrigin();
		final double distX = shape.getDistLabelsX();
		final boolean yGE0 = shape.getGridMinY()>=shape.getOriginY();
		String str;
		int val;
		int inti;

		for(double maxx = shape.getGridMaxX()/distX, minx = shape.getGridMinX()/distX, i=origx; i<=maxx*incrx; i+=incrx) {
			inti = (int)i;
			val = inti+origx;
			if((!showOrig || val!=origx || yGE0) && isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				str	 = String.valueOf(val+origx);
				updateText(str, (float)(posx+val*gapx-fontMetrics.stringWidth(str)/2.), (float)(posy+sep), font, frc);
			}
		}
		for(double maxx = shape.getGridMaxX()/distX, minx = shape.getGridMinX()/distX, i=origx-incrx; i>=minx*incrx; i-=incrx) {
			inti = (int)i;
			val = inti+origx;
			if(isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				str	 = String.valueOf(val+origx);
				updateText(str, (float)(posx+val*gapx-fontMetrics.stringWidth(str)/2.), (float)(posy+sep), font, frc);
			}
		}
	}


	/**
	 * @return True if a ticks or a label corresponding to the given parameter can be painted.
	 */
	private boolean isElementPaintable(final boolean noArrow1, final boolean noArrow2, final double min, final double max, final int i) {
		return (noArrow2 || !LNumber.INSTANCE.equals(max, i)) && (noArrow1 || !LNumber.INSTANCE.equals(min, i));
	}


	/**
	 * Updates the labels path by drawing the labels of the X/Y-axis.
	 */
	protected void updatePathLabels(final double gapx, final double gapy) {
		final FontRenderContext frc = new FontRenderContext(null, true, true);
		final PlottingStyle labelsDisplay = shape.getLabelsDisplayed();
		final PlottingStyle ticksDisplay = shape.getTicksDisplayed();
		final TicksStyle ticksStyle = shape.getTicksStyle();

		if(labelsDisplay.isX())
			updatePathLabelsX(ticksDisplay, ticksStyle, gapx, frc);

		if(labelsDisplay.isY())
			updatePathLabelsY(ticksDisplay, ticksStyle, gapy, frc);
	}


	/**
	 * Updates the general path of the view by drawing the frame of the shape.
	 */
	protected void updatePathFrame() {
		final double endx = shape.getGridEndX();
		final double endy = shape.getGridEndY();

		if(endx>0 || endy>0) {
			final double posx = shape.getPosition().getX();
			final double posy = shape.getPosition().getY();
			final double y1 = endy>0. ? posy-endy*IShape.PPC : posy;
			final double x2 = endx>0. ? posx+endx*IShape.PPC : posx;

			path.moveTo(posx, y1);
			path.lineTo(x2, y1);
			path.lineTo(x2, posy);
			path.lineTo(posx, posy);
			path.closePath();
		}
	}


	/**
	 * Updates the general path of the view by drawing the axis of the shape.
	 */
	protected void updatePathAxes() {
		final double posX = shape.getPosition().getX();
		final double posY = shape.getPosition().getY();
		final IArrow arr0 = shape.getArrowAt(1);
		final IArrow arr1 = shape.getArrowAt(3);
		final double arr0Reduction = arr0.getArrowStyle().needsLineReduction() ? arr0.getArrowShapedWidth() : 0.;
		final double arr1Reduction = arr1.getArrowStyle().needsLineReduction() ? arr1.getArrowShapedWidth() : 0.;

		path.moveTo(posX+shape.getGridStartX()*IShape.PPC + arr0Reduction, posY);
		path.lineTo(posX+shape.getGridEndX()*IShape.PPC - arr1Reduction, posY);
		path.moveTo(posX, posY-shape.getGridStartY()*IShape.PPC - arr0Reduction);
		path.lineTo(posX, posY-shape.getGridEndY()*IShape.PPC + arr1Reduction);
	}


	@Override
	public void updatePath() {
		final double incrx = shape.getIncrementX();
		final double incry = shape.getIncrementY();
		final double distX  = shape.getDistLabelsX();
		final double distY  = shape.getDistLabelsY();
		final AxesStyle axesStyle = shape.getAxesStyle();
		final double gapX = LNumber.INSTANCE.equals(distX, 0.) ? IShape.PPC : distX/incrx*IShape.PPC;
		final double gapY = LNumber.INSTANCE.equals(distY, 0.) ? IShape.PPC : distY/incry*IShape.PPC;

		path.reset();
		pathLabels.reset();
		pathTicks.reset();

		updatePathArrows();

		switch(axesStyle) {
			case AXES: updatePathAxes(); break;
			case FRAME:updatePathFrame(); break;
			case NONE: break;
		}

		updatePathTicks(gapX, gapY);

		if(shape.getLabelsDisplayed()!=PlottingStyle.NONE)
			updatePathLabels(gapX, gapY);
	}



	@Override
	protected void updatePathArrows() {
		if(shape.getAxesStyle().supportsArrows())
			super.updatePathArrows();
	}


	@Override
	public void paint(final Graphics2D g) {
		final IPoint vectorTrans = beginRotation(g);

		g.setStroke(getStroke());
		g.setColor(shape.getLineColour());
		g.draw(path);
		g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		g.draw(pathTicks);

		if(shape.getAxesStyle().supportsArrows())
			paintArrows(g, false);

		if(shape.getLabelsDisplayed()!=PlottingStyle.NONE) {
			g.setColor(Color.BLACK);
			g.fill(pathLabels);
		}

		if(vectorTrans!=null)
			endRotation(g, vectorTrans);
	}


	@Override
	protected void paintArrows(final Graphics2D g, final boolean asShadow) {
		if(arrows.size()==4) {
			final Color colour = asShadow ? shape.getShadowCol() : shape.getFillingCol();

			if(!LNumber.INSTANCE.equals(shape.getGridMinX(), 0))
				arrows.get(1).paint(g, colour, asShadow);

			if(!LNumber.INSTANCE.equals(shape.getGridMaxX(), 0))
				arrows.get(3).paint(g, colour, asShadow);

			if(!LNumber.INSTANCE.equals(shape.getGridMaxY(), 0))
				arrows.get(2).paint(g, colour, asShadow);

			if(!LNumber.INSTANCE.equals(shape.getGridMinY(), 0))
				arrows.get(0).paint(g, colour, asShadow);
		}
	}


	@Override
	public boolean intersects(final Rectangle2D r) {
		if(r==null)
			return false;

		final double rotationAngle = shape.getRotationAngle();
		boolean intersects;

		Path2D pa;

		if(shape.getAxesStyle()==AxesStyle.NONE)
			pa = new Path2D.Double(new Rectangle2D.Double(shape.getPosition().getX(), shape.getPosition().getY(), 2, 2));
		else pa = new Path2D.Double(path);

		if(shape.getTicksDisplayed()!=PlottingStyle.NONE)
			pa.append(pathTicks, false);

		if(shape.getLabelsDisplayed()!=PlottingStyle.NONE)
			pa.append(pathLabels, false);

		if(LNumber.INSTANCE.equals(rotationAngle, 0.)) {
			intersects = pa.intersects(r);
		}
		else {
			final IPoint tl 	= shape.getTopLeftPoint();
			final IPoint br 	= shape.getBottomRightPoint();
			final double cx 	= (tl.getX()+br.getX())/2.;
			final double cy		= (tl.getY()+br.getY())/2.;
			final double c2x 	= cos(rotationAngle)*cx - sin(rotationAngle)*cy;
			final double c2y 	= sin(rotationAngle)*cx + cos(rotationAngle)*cy;
			final AffineTransform at = AffineTransform.getTranslateInstance(cx - c2x, cy - c2y);

			at.rotate(rotationAngle);
			intersects = at.createTransformedShape(pa).intersects(r);
		}

		return intersects;
	}


	@Override
	public void updateBorder() {
		final double angle = shape.getRotationAngle();
		Rectangle2D bound;

		if(shape.getAxesStyle()==AxesStyle.NONE)
			bound = new Rectangle2D.Double(shape.getPosition().getX(), shape.getPosition().getY(), 2, 2);
		else bound = path.getBounds2D();

		if(shape.getTicksDisplayed()!=PlottingStyle.NONE)
			bound = bound.createUnion(pathTicks.getBounds2D()).getBounds2D();

		if(shape.getLabelsDisplayed()!=PlottingStyle.NONE)
			bound = bound.createUnion(pathLabels.getBounds2D()).getBounds2D();

		if(LNumber.INSTANCE.equals(angle, 0.))
			border.setFrame(bound);
		else {
			IPoint tl = DrawingTK.getFactory().createPoint();
			IPoint br = DrawingTK.getFactory().createPoint();
			getRotatedRectangle(bound.getMinX(), bound.getMinY(), bound.getWidth(), bound.getHeight(), angle, shape.getGravityCentre(), tl, br);
			border.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
		}
	}
}
