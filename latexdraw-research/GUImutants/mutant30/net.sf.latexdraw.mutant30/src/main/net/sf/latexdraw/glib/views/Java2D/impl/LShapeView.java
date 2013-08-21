package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.views.AbstractView;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewArrow;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.util.LNumber;

import org.malai.picking.Picker;

/**
 * Defines a view of the LShape model.<br>
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
 * 02/16/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
abstract class LShapeView<S extends IShape> extends AbstractView<S> implements IViewShape {
	/** The Java2D path used to draw the shape. */
	protected Path2D path;

	/** The view of the arrows of the shape. */
	protected List<IViewArrow> arrows;

	/**
	 * The border of the shape. This attribute must be used to compute
	 * 'intersects', 'contains' operations, etc. operations.
	 */
	protected Rectangle2D border;



	/**
	 * Creates a view of the given model and initialises the Java2D view.
	 * @param model The shape model.
	 * @throws IllegalArgumentException If <code>model</code> is null.
	 */
	protected LShapeView(final S model) {
		super(model);

		path  	= new Path2D.Double();
		border 	= new Rectangle2D.Double();

		// Creation of the views of the arrows of the shape.
		if(model.isArrowable()) {
			arrows = new ArrayList<>();

			for(final IArrow arrow : shape.getArrows())
				arrows.add(new LArrowView(arrow));
		}
		else arrows = null;
	}



	protected static Shape getRotatedShape2D(final double angle, final Shape shape, final IPoint tlPoint, final IPoint brPoint) {
		Shape sh;

		if(LNumber.INSTANCE.equals(angle, 0.))
			sh = shape;
		else {
			final double cx 		 = (tlPoint.getX()+brPoint.getX())/2.;
			final double cy 		 = (tlPoint.getY()+brPoint.getY())/2.;
			final double c2x 		 = Math.cos(angle)*cx - Math.sin(angle)*cy;
			final double c2y 		 = Math.sin(angle)*cx + Math.cos(angle)*cy;
			final AffineTransform at = AffineTransform.getTranslateInstance(cx - c2x, cy - c2y);
			at.rotate(angle);
			sh	 					 = at.createTransformedShape(shape);
		}

		return sh;
	}


	@Override
	public Shape getRotatedShape2D() {
		return getRotatedShape2D(shape.getRotationAngle(), path, shape.getTopLeftPoint(), shape.getBottomRightPoint());
	}



	@Override
	public Path2D getPath() {
		return path;
	}


	@Override
	public void updateBorder() {
		Shape sh;

		if(LNumber.INSTANCE.equals(shape.getRotationAngle(), 0.))
			sh = path;
		else
			sh = getRotatedShape2D();

		border.setFrame(getStroke().createStrokedShape(sh).getBounds2D());
	}



	/**
	 * Updates the borders of the shape as inside borders.
	 * @since 3.0
	 */
	protected abstract void updateGeneralPathInside();



	/**
	 * Updates the borders of the shape as middle borders.
	 * @since 3.0
	 */
	protected abstract void updateGeneralPathMiddle();



	/**
	 * Updates the borders of the shape as outside borders.
	 * @since 3.0
	 */
	protected abstract void updateGeneralPathOutside();



	@Override
	public void updatePath() {
		if(shape.isBordersMovable())
			if(shape.isDbleBorderable() && shape.hasDbleBord())
				switch(shape.getBordersPosition()) {
					case INTO	: updateDblePathInside(); break;
					case MID	: updateDblePathMiddle(); break;
					case OUT	: updateDblePathOutside(); break;
				}
			else
				switch(shape.getBordersPosition()) {
					case INTO	: updateGeneralPathInside(); break;
					case MID	: updateGeneralPathMiddle(); break;
					case OUT	: updateGeneralPathOutside(); break;
				}
		else
			if(shape.isDbleBorderable() && shape.hasDbleBord())
				updateDblePathMiddle();
			else
				updateGeneralPathMiddle();

		updatePathArrows();
	}


	/**
	 * Updates the path of the view of the arrows.
	 */
	protected void updatePathArrows() {
		if(shape.isArrowable())
			for(final IViewArrow arrView : arrows)
				arrView.updatePath();
	}



	/**
	 * Updates the borders of the double boundary when position is outside.
	 * @since 3.0
	 */
	protected abstract void updateDblePathOutside();



	/**
	 * Updates the borders of the double boundary when position is inside.
	 * @since 3.0
	 */
	protected abstract void updateDblePathInside();



	/**
	 * Updates the borders of the double boundary when position is middle.
	 * @since 3.0
	 */
	protected abstract void updateDblePathMiddle();



	@Override
	public void update() {
		updatePath();
		updateBorder();
	}


	@Override
	public void paintFilling(final Graphics2D g) {
		final FillingStyle fStyle = shape.getFillingStyle();

		switch(fStyle) {
			case NONE:
				if(shape.hasShadow() && shape.shadowFillsShape()) {
					g.setColor(shape.getFillingCol());
					g.fill(path);
				}

				break;

			case PLAIN:
				g.setColor(shape.getFillingCol());
				g.fill(path);
				break;

			case GRAD:
				final IShapeFactory factory = DrawingTK.getFactory();
				final GeneralPath p = new GeneralPath(path);//TODO checks if useful the create an other path
				final IPoint tl  = shape.getTopLeftPoint();
				final IPoint br  = shape.getBottomRightPoint();
				IPoint pt1 		 = factory.createPoint((tl.getX()+br.getX())/2., tl.getY());
				IPoint pt2 		 = factory.createPoint((tl.getX()+br.getX())/2., br.getY());
				double angle 	 = shape.getGradAngle()%(2*Math.PI);
				double gradMidPt = shape.getGradMidPt();

				p.setWindingRule(Path2D.WIND_NON_ZERO);

				if(angle<0.)
					angle = 2.*Math.PI + angle;

				if(angle>=Math.PI) {
					gradMidPt = 1. - gradMidPt;
					angle = angle-Math.PI;
				}

				if(!LNumber.INSTANCE.equals(angle, 0.)) {
					if(LNumber.INSTANCE.equals(angle%(Math.PI/2.), 0.)) {
						pt1 = factory.createPoint(tl.getX(), (tl.getY()+br.getY())/2.);
						pt2 = factory.createPoint(br.getX(), (tl.getY()+br.getY())/2.);

						if(gradMidPt<0.5)
							pt1.setX(pt2.getX() - Point2D.distance(pt2.getX(), pt2.getY(), br.getX(),(tl.getY()+br.getY())/2.));

						pt2.setX(tl.getX()+(br.getX()-tl.getX())*gradMidPt);
					}
					else {
						final IPoint cg = shape.getGravityCentre();
						ILine l2, l;

						pt1 = pt1.rotatePoint(cg, -angle);
						pt2 = pt2.rotatePoint(cg, -angle);
						l = factory.createLine(pt1, pt2);

						if(angle>=0. && angle<(Math.PI/2.))
							l2 = l.getPerpendicularLine(tl);
						else
							l2 = l.getPerpendicularLine(factory.createPoint(tl.getX(),br.getY()));

						pt1 = l.getIntersection(l2);
						final double distance = Point2D.distance(cg.getX(), cg.getY(), pt1.getX(), pt1.getY());
						l.setX1(pt1.getX());
						l.setY1(pt1.getY());
						final IPoint[] pts = l.findPoints(pt1, 2*distance*gradMidPt);
						pt2 = pts[0];

						if(gradMidPt<0.5)
							pt1 = pt1.rotatePoint(shape.getGravityCentre(), Math.PI);
					}
				}//if(angle!=0)
				else {
					if(gradMidPt<0.5)
						pt1.setY(pt2.getY() - Point2D.distance(pt2.getX(), pt2.getY(), (tl.getX()+br.getX())/2.,br.getY()));

					pt2.setY(tl.getY()+(br.getY()-tl.getY())*gradMidPt);
				}

				g.setPaint(new GradientPaint(
							(float)pt1.getX(), (float)pt1.getY(), shape.getGradColStart(),
							(float)pt2.getX(), (float)pt2.getY(), shape.getGradColEnd(),true));
				g.fill(p);
				break;

			case CLINES_PLAIN:
			case HLINES_PLAIN:
			case VLINES_PLAIN:
			case CLINES:
			case VLINES:
			case HLINES:
				final Shape oldClip = g.getClip();
				final Rectangle2D bounds  = path.getBounds2D();
				g.setClip(path);

				if(shape.isFilled() || (shape.hasShadow() && shape.shadowFillsShape())) {
					g.setColor(shape.getFillingCol());
					g.fill(bounds);
				}

				final Stroke oldStroke = g.getStroke();
				final double hAngle	 = shape.getHatchingsAngle();

				if(fStyle==FillingStyle.VLINES || fStyle==FillingStyle.VLINES_PLAIN)
					paintHatchings2(g, hAngle, bounds);
				else
					if(fStyle==FillingStyle.HLINES || fStyle==FillingStyle.HLINES_PLAIN)
					paintHatchings2(g, hAngle>0?hAngle-Math.PI/2.:hAngle+Math.PI/2., bounds);
				else
					if(fStyle==FillingStyle.CLINES || fStyle==FillingStyle.CLINES_PLAIN) {
						paintHatchings2(g, hAngle, bounds);
						paintHatchings2(g, hAngle>0?hAngle-Math.PI/2.:hAngle+Math.PI/2., bounds);
					}

				g.setStroke(oldStroke);
				g.setClip(oldClip);
				break;
		}
	}



	@Override
	public boolean contains(final double x, final double y) {
		// We test the borders first to limit the computations.
		if(!border.contains(x, y))
			return false;

		final Shape sh = LNumber.INSTANCE.equals(shape.getRotationAngle(), 0.) ? path : getRotatedShape2D();

		if(shape.isFilled() && sh.contains(x, y))
			return true;

		final BasicStroke bc = getStroke();

		// We test if the point is on the shape.
		return bc==null ? false : bc.createStrokedShape(sh).contains(x, y);
	}



	@Override
	public boolean contains(final IPoint pt) {
		return pt==null ? false : contains(pt.getX(), pt.getY());
	}



	@Override
	public boolean intersects(final Rectangle2D rec) {
		if(rec==null || (LNumber.INSTANCE.equals(shape.getRotationAngle(), 0.) && !rec.contains(border) && !border.contains(rec) && !rec.intersects(border)))
			return false;

		final BasicStroke stroke = getStroke();
		final Shape sh	 		 = LNumber.INSTANCE.equals(shape.getRotationAngle(), 0.) ? path : getRotatedShape2D();

		if(stroke==null)
			return sh.intersects(rec) || sh.contains(rec);

		return (shape.isFilled() && sh.contains(rec)) || stroke.createStrokedShape(sh).intersects(rec);
	}



	@Override
	public void paint(final Graphics2D g) {
		// We begin the rotation, if needed.
		IPoint vectorTrans = beginRotation(g);
		boolean isShowPts  = shape.isShowPtsable() && shape.isShowPts();

		// Lines of the show points option must be drawn before all.
		if(isShowPts)
			paintShowPointsLines(g);

		if(shape.isShadowable())
			paintShadow(g);

		if(shape.isFillable())
			paintFilling(g);

		paintBorders(g);

		// Painting the arrows of the shape.
		if(shape.isArrowable())
			paintArrows(g, false);

		// Dots of the show points option must be drawn after all.
		if(isShowPts)
			paintShowPointsDots(g);

		// We close the rotation.
		if(vectorTrans!=null)
			endRotation(g, vectorTrans);
	}


	/**
	 * Paints the arrows of the shape.
	 * @since 3.0
	 */
	protected void paintArrows(final Graphics2D g, final boolean asShadow) {
		final Color colour = asShadow ? shape.getShadowCol() : shape.getFillingCol();

		for(final IViewArrow arrow : arrows)
			arrow.paint(g, colour, asShadow);
	}



	@Override
	public void paintBorders(final Graphics2D g) {
		if(shape.getLineStyle()==LineStyle.NONE)
			return;

		if(shape.hasDbleBord())
			paintBordersDouble(g);
		else
			paintBordersSimple(g);
	}



	/**
	 * Draws the double borders of the shape.
	 * @param g The graphics to print into.
	 * @since 3.0
	 */
	private void paintBordersDouble(final Graphics2D g) {
		g.setStroke(getStroke());
		g.setColor(shape.getLineColour());
		g.draw(path);
		g.setColor(shape.getDbleBordCol());
		g.setStroke(new BasicStroke((float) shape.getDbleBordSep()));
		g.draw(path);
	}



	/**
	 * Draws the simple border (not with double borders).
	 * @param g The graphics to print into.
	 * @since 3.0
	 */
	private void paintBordersSimple(final Graphics2D g) {
		g.setColor(shape.getLineColour());
		g.setStroke(getStroke());
		g.draw(path);
	}



	@Override
	public void paintShadow(final Graphics2D g) {
		if(shape.hasShadow()) {
			final double dx;
			final double dy;
			final IPoint gc 		= shape.getGravityCentre();
			final IPoint shadowgc 	= DrawingTK.getFactory().createPoint(gc.getX()+shape.getShadowSize(), gc.getY());

			shadowgc.setPoint(shadowgc.rotatePoint(gc, shape.getShadowAngle()));
			dx = shadowgc.getX() - gc.getX();
			dy = gc.getY() - shadowgc.getY();

			g.setStroke(new BasicStroke((float) getStrokeThickness(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
			g.translate(dx, dy);
			g.setColor(shape.getShadowCol());
			g.draw(path);

			if(shape.shadowFillsShape())
				g.fill(path);

			// Painting the arrows of the shadow.
			if(shape.isArrowable())
				paintArrows(g, true);

			g.translate(-dx, -dy);
		}
	}



	@Override
	public void paintShowPointsLines(final Graphics2D g) {
		// TODO Auto-generated method stub
	}



	@Override
	public void paintShowPointsDots(final Graphics2D g) {
		// TODO Auto-generated method stub
	}



	/**
	 * Paints the hatchings.
	 * @param g The graphics to paint.
	 * @param angle The angle of the hatchings (in radian).
	 * @param clip The clip box.
	 */
	private void paintHatchings2(final Graphics2D g, final double angle, final Rectangle2D clip) {
		if(g==null || clip==null)
			return ;

		double angle2 = angle%(Math.PI*2.);
		float halphPI = (float)(Math.PI/2.);

		if(angle2>0) {
			if((float)angle2>3f*halphPI)
				angle2 = angle2-Math.PI*2.;
			else
				if((float)angle2>halphPI)
					angle2 = angle2-Math.PI;
		}
		else
			if((float)angle2<-3f*halphPI)
				angle2 = angle2+Math.PI*2.;
			else
				if((float)angle2<-halphPI)
					angle2 = angle2+Math.PI;

		Line2D.Double line  = new Line2D.Double();
		double val			= shape.getHatchingsWidth()+shape.getHatchingsSep();
		float fAngle		= (float)angle2;

		g.setStroke(new BasicStroke((float)shape.getHatchingsWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		g.setPaint(shape.getHatchingsCol());

		if(fAngle==0f) {
			line.y1 	= clip.getMinY();
			line.y2 	= clip.getMaxY();
			double maxX = clip.getMaxX();

			for(double x = clip.getMinX(); x<maxX; x+=val) {
				line.x1 = x;
				line.x2 = x;
				g.draw(line);
			}
		}
		else
			if(fAngle==halphPI || fAngle==-halphPI) {
				line.x1 	= clip.getMinX();
				line.x2 	= clip.getMaxX();
				double maxY = clip.getMaxY();

				for(double y = clip.getMinY(); y<maxY; y+=val) {
					line.y1 = y;
					line.y2 = y;
					g.draw(line);
				}
			}
			else {
				double incX = val/Math.cos(angle2);
				double incY = val/Math.sin(angle2);
				double maxX;

				if(fAngle>0f) {
					line.y1 = clip.getMinY();
					maxX 	= clip.getMaxX() + (clip.getMaxY()-(clip.getMinY()<0?clip.getMinY():0)) * Math.tan(angle2);
				}
				else {
					line.y1 = clip.getMaxY();
					maxX 	= clip.getMaxX() - clip.getMaxY() * Math.tan(angle2);
				}

				line.x1 = clip.getMinX();
				line.x2 = line.x1;
				line.y2 = line.y1;

				if(((float)incX)<=0f)
					return ;

				while(line.x2 < maxX) {
					line.x2 += incX;
					line.y1 += incY;
					g.draw(line);
				}
			}
	}



	@Override
	public BasicStroke getStroke() {
		final float strokeTh  = (float) getStrokeThickness();
		BasicStroke stroke;

		switch(shape.getLineStyle()) {
			case SOLID:
				stroke = new BasicStroke(strokeTh, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				break;

			case DASHED:
				stroke = new BasicStroke(strokeTh, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f,
						 new float[] { (float)shape.getDashSepBlack(), (float)shape.getDashSepWhite() }, 0f);
				break;

			case DOTTED:
				final float thickness = (float)shape.getThickness();
				// The size of the dots of the stroke depends on the thickness and eventually on the double borders size.
				final float dot = (float)shape.getDotSep() +
								(shape.hasDbleBord() ? thickness*2f + (float)shape.getDbleBordSep() : thickness);

				stroke = new BasicStroke(strokeTh, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, new float[] { 0f, dot }, 0f);
				break;

			case NONE:
			default:
				stroke = null;
				break;
		}

		return stroke;
	}



	/**
	 * @return The thickness of the stroke; The difference with shape.getThickness is that
	 * the double borders and their size are took in account.
	 * @since 3.0
	 */
	public double getStrokeThickness() {
		final double thickness = shape.getThickness();
		return  shape.hasDbleBord() ? thickness*2. + shape.getDbleBordSep() : thickness;
	}



	/**
	 * Begins the rotation by modifying the Graphics2D using the rotation angle.
	 * @param g The graphics to modify to draw rotated views.
	 * @return The transition vector used to place the rotated view to its original location.
	 * The rotation was made as the origin point, so as translation is needed to
	 * place the view at the centre of the view. This point will be used to end the rotation.
	 * Returns null if g is null or if the rotation angle is equal to 0.
	 * @since 3.0
	 */
	protected IPoint beginRotation(final Graphics2D g) {
		final double rotationAngle = shape.getRotationAngle();
		IPoint p = null;

		if(!LNumber.INSTANCE.equals(rotationAngle%(Math.PI*2.), 0.) && g!=null) {
			final IPoint tl = shape.getTopLeftPoint();//FIXME: should be border?
			final IPoint br = shape.getBottomRightPoint();
			final double cx = (tl.getX() + br.getX()) / 2., cy = (tl.getY() + br.getY()) / 2.;
			final double c2x = Math.cos(rotationAngle) * cx - Math.sin(rotationAngle)* cy;
			final double c2y = Math.sin(rotationAngle) * cx + Math.cos(rotationAngle)* cy;
			final double c3x = Math.cos(-rotationAngle) * (cx - c2x)- Math.sin(-rotationAngle) * (cy - c2y);
			final double c3y = Math.sin(-rotationAngle) * (cx - c2x)+ Math.cos(-rotationAngle) * (cy - c2y);

			g.rotate(rotationAngle);
			g.translate(c3x, c3y);
			p = DrawingTK.getFactory().createPoint(c3x, c3y);
		}

		return p;
	}



	/**
	 * Ends the rotation of the view by modifying the Graphics2D using the rotation angle
	 * of the model of the view, and the given translation vector.
	 * @param g The graphics to un-rotate.
	 * @param translation This translation vector is given by function beginRotation. It is
	 * used to translate the graphics at the initial position.
	 * @since 3.0
	 */
	protected void endRotation(final Graphics2D g, final IPoint translation) {
		if(GLibUtilities.INSTANCE.isValidPoint(translation) && g!=null) {
			g.translate(-translation.getX(), -translation.getY());
			g.rotate(-shape.getRotationAngle());
		}
	}


	/**
	 * Gives the top-left and the bottom-right points of the rotated rectangle.
	 * @param tlx The top-left x-coordinate of the rectangle to rotate.
	 * @param tly The top-left y-coordinate of the rectangle to rotate.
	 * @param width The width of the rectangle to rotate.
	 * @param height The height of the rectangle to rotate.
	 * @param angle The rotation angle.
	 * @param gravityCentre The gravity centre used for the rotation.
	 * @param tl The resulting top-left point. Must not be null.
	 * @param br The resulting bottom-right point. Must not be null.
	 * @since 3.0
	 */
	protected static void getRotatedRectangle(final double tlx, final double tly, final double width,
											 final double height, final double angle, final IPoint gravityCentre,
											 final IPoint tl, final IPoint br) {
		final IShapeFactory factory = DrawingTK.getFactory();
		IPoint pts[] = new IPoint[4];
		// Rotation of the four points of the rectangle.
		pts[0] = factory.createPoint(tlx, tly).rotatePoint(gravityCentre, angle);
		pts[1] = factory.createPoint(tlx+width, tly).rotatePoint(gravityCentre, angle);
		pts[2] = factory.createPoint(tlx+width, tly+height).rotatePoint(gravityCentre, angle);
		pts[3] = factory.createPoint(tlx, tly+height).rotatePoint(gravityCentre, angle);
		tl.setPoint(Double.MAX_VALUE, Double.MAX_VALUE);
		br.setPoint(Double.MIN_VALUE, Double.MIN_VALUE);

		// Defining the border of the rotated rectangle.
		for(int i=0; i<pts.length; i++) {
			if(pts[i].getX()<tl.getX())
				tl.setX(pts[i].getX());
			if(pts[i].getX()>br.getX())
				br.setX(pts[i].getX());
			if(pts[i].getY()<tl.getY())
				tl.setY(pts[i].getY());
			if(pts[i].getY()>br.getY())
				br.setY(pts[i].getY());
		}
	}



	@Override
	public Rectangle2D getBorder() {
		return border;
	}


	@Override
	public Picker getPicker() {
		return null;
	}


	@Override
	public void flush() {
		shape = null;
		path = null;
		border = null;

		if(arrows!=null) {
			arrows.clear();
			arrows = null;
		}
	}
}
