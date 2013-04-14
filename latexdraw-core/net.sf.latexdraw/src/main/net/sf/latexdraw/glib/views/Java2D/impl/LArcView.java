package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewArc;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IArc model.<br>
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
 * 03/20/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LArcView<M extends IArc> extends LEllipseView<IArc> implements IViewArc {
	/**
	 * Creates an initialises the Java view of a LArc.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LArcView(final IArc model) {
		super(model);
		update();
	}


	@Override
	protected void setRectangularShape(final Path2D path, final double tlx, final double tly, final double width, final double height) {
		setArcPath(path, tlx, tly, width, height, shape.getAngleStart(), shape.getAngleEnd(), getJava2DArcStyle());
	}


	@Override
	public void paintShowPointsLines(final Graphics2D g) {
		if(shape.getArcStyle()!=ArcStyle.WEDGE) {
			final IPoint gc = shape.getGravityCentre();
			final IPoint p1 = shape.getStartPoint();
			final IPoint p2 = shape.getEndPoint();

			g.setColor(shape.getLineColour());
			g.setStroke(new BasicStroke((float)(shape.getThickness()/2.), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f,
						new float[]{(float)shape.getDashSepBlack(), (float)shape.getDashSepWhite()}, 0));
			g.draw(new Line2D.Double(p1.getX(), p1.getY(), gc.getX(), gc.getY()));
			g.draw(new Line2D.Double(p2.getX(), p2.getY(), gc.getX(), gc.getY()));
		}
	}


	@Override
	protected void updateGeneralPathInside() {
		updateGeneralPath(0);
	}

	@Override
	protected void updateGeneralPathOutside() {
		updateGeneralPath(0);
	}


	@Override
	public void updateBorder() {
		final Shape sh = LNumber.INSTANCE.equals(shape.getRotationAngle(), 0.) ? path : getRotatedShape2D();
		border.setFrame(getStroke().createStrokedShape(sh).getBounds2D());
	}


	protected int getJava2DArcStyle() {
		switch(shape.getArcStyle()) {
			case ARC	: return Arc2D.OPEN;
			case CHORD	: return Arc2D.CHORD;
			case WEDGE	: return Arc2D.PIE;
		}
		return -1;
	}


	/**
	 * Appends an arc to the given path.
	 * @param path The path to fill.
	 * @param tlx The top-left point of the arc.
	 * @param tly The bottom right point of the arc.
	 * @param width The width of the arc.
	 * @param height The height of the arc.
	 * @param startAngle The start angle of the arc.
	 * @param endAngle The end angle of the arc.
	 * @param style The style of the arc. See Arc2D.OPEN etc.
	 * @since 3.0
	 */
	public void setArcPath(final Path2D path, final double tlx, final double tly, final double width, final double height, final double startAngle,
									final double endAngle, final int style) {
		if(path!=null) {
			final double w2 = Math.max(1., width);
			final double h2 = Math.max(1., height);
			double sAngle = startAngle;
			double eAngle = endAngle;

			if(shape.isArrowable()) {
				IArrow arr = shape.getArrowAt(-1);
				if(arr.getArrowStyle().isReducingShape())
					eAngle-=Math.atan(arr.getArrowShapeLength()/w2);
				arr = shape.getArrowAt(0);
				if(arr.getArrowStyle().isReducingShape())
					sAngle+=Math.atan(arr.getArrowShapeLength()/w2);
			}

			sAngle = Math.toDegrees(sAngle%(Math.PI*2.));
			eAngle = Math.toDegrees(eAngle%(Math.PI*2.));

			if(LNumber.INSTANCE.equals(sAngle, eAngle))
				eAngle += 0.1;

			final double end = sAngle>eAngle ? 360.-sAngle+eAngle : eAngle-sAngle;
			path.append(new Arc2D.Double(tlx, tly, w2, h2, sAngle, end, style), false);
		}
	}
}
