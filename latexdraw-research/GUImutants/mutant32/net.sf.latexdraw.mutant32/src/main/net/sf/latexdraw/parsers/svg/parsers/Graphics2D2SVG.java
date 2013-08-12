package net.sf.latexdraw.parsers.svg.parsers;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.SVGTransform;
import net.sf.latexdraw.parsers.svg.SVGTransformList;

/**
 * This Graphics can be used to convert Java drawing (using Graphics2D) into SVG.
 * Instead of the classical Java Graphics2D, this graphics can be given as argument to
 * paint methods in order to create an SVG element.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 08/03/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Graphics2D2SVG extends Graphics2D {
	/** The generated SVG G element. */
	protected SVGGElement element;

	/** The SVG document used to created SVG elements. */
	protected SVGDocument document;

	/** The current colour. Is null as the initialisation. */
	protected Color currentColour;

	/** The current stroke. Is null as the initialisation. */
	protected Stroke currentStroke;

	/** Corresponds to the last SVG path element created. Is used for optimisation. */
	private SVGPathElement lastPathPainted;

	/** True if the last path was filled. Painted otherwise. Is used for optimisation. */
	private boolean lastPathPaintedFilled;

	/** The list of the current transformations. Can be null. */
	private SVGTransformList currentTransforms;


	/**
	 * Creates the converter.
	 * @param document The SVG document used to created SVG elements.
	 * @since 3.0
	 */
	public Graphics2D2SVG(final SVGDocument document) {
		super();
		this.document = Objects.requireNonNull(document);
		element 	  = new SVGGElement(document);
	}


	protected void clearLastPath() {
		lastPathPainted = null;
	}


	/**
	 * Sets the current stroke, colour, etc. to the given SVG element.
	 * @param elt The SVG element to set.
	 * @since 3.0
	 */
	protected void setShapeAttributes(final SVGElement elt, final boolean fill, final boolean draw) {
		if(draw)
			setShapeStroke(elt);
		if(fill)
			elt.setFill(currentColour);
			// TODO gradient, etc.
		if(currentTransforms!=null && !currentTransforms.isEmpty())
			elt.setAttribute(SVGAttributes.SVG_TRANSFORM, currentTransforms.toString());
	}


	/**
	 * Sets the current stroke to the given SVG element.
	 * @param elt The SVG element to set.
	 * @since 3.0
	 */
	protected void setShapeStroke(final SVGElement elt) {
		if(currentStroke instanceof BasicStroke) {
			final BasicStroke bs = (BasicStroke)currentStroke;

			setShapeLineCap(elt, bs.getEndCap());
			setShapeLineJoin(elt, bs.getLineJoin());
			elt.setStrokeWidth(bs.getLineWidth());
			elt.setStrokeMiterLevel(bs.getMiterLimit());
			elt.setStrokeDashOffset(bs.getDashPhase());
			setShapeDash(elt, bs.getDashArray());
			elt.setStroke(currentColour);
		}
	}


	/**
	 * Sets the dashes of the given SVG element.
	 * @param elt The element to set.
	 * @param dashes The dashes to set.
	 * @since 3.0
	 */
	protected void setShapeDash(final SVGElement elt, final float[] dashes) {
		if(dashes!=null && dashes.length>0) {
			final StringBuilder sb = new StringBuilder();
			sb.append(dashes[0]);

			for(int i=1; i<dashes.length; i++)
				sb.append(',').append(dashes[i]);

			elt.setStrokeDashArray(sb.toString());
		}
	}



	/**
	 * Sets the line join of the given SVG element.
	 * @param elt The element to set.
	 * @param lineJoin The Java line join.
	 * @since 3.0
	 */
	protected void setShapeLineJoin(final SVGElement elt, final int lineJoin) {
		switch(lineJoin) {
			case BasicStroke.JOIN_BEVEL: elt.setStrokeLineJoin(SVGAttributes.SVG_LINEJOIN_VALUE_BEVEL); break;
			case BasicStroke.JOIN_MITER: elt.setStrokeLineJoin(SVGAttributes.SVG_LINEJOIN_VALUE_MITER); break;
			case BasicStroke.JOIN_ROUND: elt.setStrokeLineJoin(SVGAttributes.SVG_LINEJOIN_VALUE_ROUND); break;
			default:
		}
	}


	/**
	 * Sets the line cap of the given SVG element.
	 * @param elt The element to set.
	 * @param lineCap The Java line cap (line end in Java).
	 * @since 3.0
	 */
	protected void setShapeLineCap(final SVGElement elt, final int lineCap) {
		switch(lineCap) {
			case BasicStroke.CAP_BUTT:   elt.setStrokeLineCap(SVGAttributes.SVG_LINECAP_VALUE_BUTT);   break;
			case BasicStroke.CAP_ROUND:  elt.setStrokeLineCap(SVGAttributes.SVG_LINECAP_VALUE_ROUND);  break;
			case BasicStroke.CAP_SQUARE: elt.setStrokeLineCap(SVGAttributes.SVG_LINECAP_VALUE_SQUARE); break;
			default:
		}
	}



	@Override
	public void draw(final Shape sh) {
		fillPaintShape(sh, false, true);
	}



	@Override
	public boolean drawImage(final Image img, final AffineTransform xform, final ImageObserver obs) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawImage(final BufferedImage img, final BufferedImageOp op, final int x, final int y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawRenderedImage(final RenderedImage img, final AffineTransform xform) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawRenderableImage(final RenderableImage img, final AffineTransform xform) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawString(final String str, final int x, final int y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawString(final String str, final float x, final float y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawString(final AttributedCharacterIterator iterator, final int x, final int y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawString(final AttributedCharacterIterator iterator, final float x, final float y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawGlyphVector(final GlyphVector g, final float x, final float y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	/**
	 * Draws or/and fills the given shape in the SVG element.
	 * @param sh The shape to draw or/and fill.
	 * @param fill True: the shape will be filled.
	 * @param draw True: the shape will be drawn.
	 * @since 3.0
	 */
	protected void fillPaintShape(final Shape sh, final boolean fill, final boolean draw) {
		if(sh==null) return ;
		if(sh instanceof Path2D) {
			Path2D2SVGPath parser = new Path2D2SVGPath((Path2D)sh, document);
			try{
				parser.parse();
				SVGPathElement shapeElt = parser.getSVGElement();

				if(lastPathPainted!=null && fill!=lastPathPaintedFilled && shapeElt.getPathData().equals(lastPathPainted.getPathData())) {
					setShapeAttributes(lastPathPainted, fill, draw);
					// The shape was both filled and painted so the optimisation ends.
					lastPathPainted = null;
				}else {
					setShapeAttributes(shapeElt, fill, draw);
					element.appendChild(shapeElt);
					// Maybe the next instruction will be the painting of the filling of the shape just filled or painted.
					lastPathPainted = shapeElt;
					lastPathPaintedFilled = fill;
				}
			}catch(ParseException exception){ BadaboomCollector.INSTANCE.add(exception); }
		}
		else throw new IllegalArgumentException();
	}



	@Override
	public void fill(final Shape sh) {
		fillPaintShape(sh, true, false);
	}



	@Override
	public boolean hit(final Rectangle rect, final Shape s, final boolean onStroke) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public GraphicsConfiguration getDeviceConfiguration() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setComposite(final Composite comp) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setPaint(final Paint paint) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}


	@Override
	public void setStroke(final Stroke stroke) {
		if(stroke!=null)
			currentStroke = stroke;
	}


	@Override
	public void setRenderingHint(final Key hintKey, final Object hintValue) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Object getRenderingHint(final Key hintKey) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setRenderingHints(final Map<?, ?> hints) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void addRenderingHints(final Map<?, ?> hints) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public RenderingHints getRenderingHints() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void translate(final int x, final int y) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void translate(final double tx, final double ty) {
		clearLastPath();
		addTransformation(SVGTransform.createTranslation(tx, ty));
	}



	@Override
	public void rotate(final double theta) {
		clearLastPath();
		rotate(theta, 0., 0.);
	}


	private void addTransformation(final SVGTransform transformation) {
		if(currentTransforms==null)
			currentTransforms = new SVGTransformList();

		if(currentTransforms.isEmpty())
			currentTransforms.add(transformation);
		else {
			SVGTransform transformPrev = currentTransforms.get(currentTransforms.size()-1);
			if(transformPrev.cancels(transformation))
				currentTransforms.remove(currentTransforms.size()-1);
			else
				currentTransforms.add(transformation);
		}
	}


	@Override
	public void rotate(final double theta, final double x, final double y) {
		clearLastPath();
		addTransformation(SVGTransform.createRotation(Math.toDegrees(theta), x, y));
	}



	@Override
	public void scale(final double sx, final double sy) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void shear(final double shx, final double shy) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void transform(final AffineTransform tx) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setTransform(final AffineTransform tx) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public AffineTransform getTransform() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Paint getPaint() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Composite getComposite() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setBackground(final Color color) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Color getBackground() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}


	@Override
	public Stroke getStroke() {
		return currentStroke;
	}


	@Override
	public void clip(final Shape s) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public FontRenderContext getFontRenderContext() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Graphics create() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}


	@Override
	public Color getColor() {
		return currentColour;
	}


	@Override
	public void setColor(final Color col) {
		if(col!=null)
			currentColour = col;
	}


	@Override
	public void setPaintMode() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setXORMode(final Color c1) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setFont(final Font font) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public FontMetrics getFontMetrics(final Font f) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void clipRect(final int x, final int y, final int width, final int height) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setClip(final int x, final int y, final int width, final int height) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void setClip(final Shape clip) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void copyArea(final int x, final int y, final int width, final int height, final int dx, final int dy) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void fillRect(final int x, final int y, final int width, final int height) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void clearRect(final int x, final int y, final int width, final int height) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawRoundRect(final int x, final int y, final int width, final int height, final int arcWidth, final int arcHeight) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void fillRoundRect(final int x, final int y, final int width, final int height, final int arcWidth, final int arcHeight) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawOval(final int x, final int y, final int width, final int height) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void fillOval(final int x, final int y, final int width, final int height) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void fillArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawPolyline(final int[] xPoints, final int[] yPoints, final int nPoints) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void drawPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public void fillPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public boolean drawImage(final Image img, final int x, final int y, final ImageObserver observer) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public boolean drawImage(final Image img, final int x, final int y, final int width, final int height, final ImageObserver observer) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public boolean drawImage(final Image img, final int x, final int y, final Color bgcolor, final ImageObserver observer) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public boolean drawImage(final Image img, final int x, final int y, final int width, final int height, final Color bgcolor, final ImageObserver observer) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public boolean drawImage(final Image img, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1, final int sx2, final int sy2,
			final ImageObserver observer) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}



	@Override
	public boolean drawImage(final Image img, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1, final int sx2, final int sy2, final Color bgcolor,
			final ImageObserver observer) {
		clearLastPath();
		// TODO Auto-generated method stub
		throw new IllegalArgumentException();
	}


	@Override
	public void dispose() {
		clearLastPath();

		if(currentTransforms!=null) {
			currentTransforms.clear();
			currentTransforms = null;
		}

		currentStroke	= null;
		currentColour	= null;
		element 		= null;
		document		= null;
	}


	/**
	 * @return the created SVG element after the painting.
	 * @since 3.0
	 */
	public SVGGElement getElement() {
		return element;
	}
}
