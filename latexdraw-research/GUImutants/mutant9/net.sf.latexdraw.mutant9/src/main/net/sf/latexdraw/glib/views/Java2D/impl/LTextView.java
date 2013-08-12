package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText;
import net.sf.latexdraw.util.LNumber;
import sun.font.FontDesignMetrics;

/**
 * Defines a view of the IText model.<br>
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
 * 05/23/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LTextView extends LShapeView<IText> implements IViewText {
	/** Used to detect if the last version of the text is different from the view. It helps to update the picture. */
	private String lastText;

	/** Used to detect if the last version of the text is different from the view. It helps to update the picture. */
	private Color lastColour;

	/** Used to detect if the last version of the text is different from the view. It helps to update the picture. */
	private TextPosition lastTextPos;

	public static final Font FONT = new Font("Times New Roman", Font.PLAIN, 18); //$NON-NLS-1$

	public static final FontMetrics FONT_METRICS = FontDesignMetrics.getMetrics(FONT);



	/**
	 * Creates and initialises a text view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is null.
	 * @since 3.0
	 */
	protected LTextView(final IText model) {
		super(model);

		lastText 	= ""; //$NON-NLS-1$
		lastColour 	= null;
		lastTextPos	= null;
		update();
	}


	@Override
	public void update() {
		final Image image = FlyweightThumbnail.getImage(this);
		final String log = FlyweightThumbnail.getLog(this);

		if(image==null && log.length()==0 || !lastText.equals(shape.getText()) ||
			lastColour==null || !lastColour.equals(shape.getLineColour()) ||
			lastTextPos==null || lastTextPos!=shape.getTextPosition()) {
			updateImage();
			lastText 	= shape.getText();
			lastColour 	= shape.getLineColour();
			lastTextPos	= shape.getTextPosition();
		}

		super.update();
	}


	@Override
	protected void finalize() {
		flush();
	}



	@Override
	public void flush() {
		FlyweightThumbnail.notifyImageFlushed(this, lastText);
		FlyweightThumbnail.notifyImageFlushed(this, shape.getText());
		super.flush();
	}


	@Override
	public void updateImage() {
		FlyweightThumbnail.notifyImageFlushed(this, lastText);
	}


	@Override
	public Image getImage() {
		return FlyweightThumbnail.getImage(this);
	}



	@Override
	public boolean intersects(final Rectangle2D rec) {
		if(rec==null)
			return false;

		final Shape sh = getRotatedShape2D(shape.getRotationAngle(), border,
						DrawingTK.getFactory().createPoint(border.getMinX(), border.getMinY()),
						DrawingTK.getFactory().createPoint(border.getMaxX(), border.getMaxY()));
		return sh.contains(rec) || sh.intersects(rec);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return border.contains(x, y);
	}


	private IPoint getTextPositionImage() {
		final Image image = FlyweightThumbnail.getImage(this);
		final double scale = FlyweightThumbnail.scaleImage();

		if(image!=null)
			switch(shape.getTextPosition()) {
				case BOT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/2./scale, shape.getY()-image.getHeight(null)/scale);
				case TOP : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/2./scale, shape.getY());
				case BOT_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY()-image.getHeight(null)/scale);
				case TOP_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY());
				case BOT_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/scale, shape.getY()-image.getHeight(null)/scale);
				case TOP_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/scale, shape.getY());
				case LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY()-image.getHeight(null)/scale/2.);
				case RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/scale, shape.getY()-image.getHeight(null)/scale/2.);
				case BASE: case BASE_LEFT: case BASE_RIGHT:
				case CENTER : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/2./scale, shape.getY()-image.getHeight(null)/scale/2.);
			}

		return null;
	}


	private IPoint getTextPositionText() {
		final TextLayout tl = new TextLayout(shape.getText(), FONT, FONT_METRICS.getFontRenderContext());
		final Rectangle2D bounds = tl.getBounds();

		switch(shape.getTextPosition()) {
			case BOT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth()/2., shape.getY());
			case TOP : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth()/2., shape.getY()+bounds.getHeight());
			case BOT_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY());
			case TOP_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY()+bounds.getHeight());
			case BOT_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth(), shape.getY());
			case TOP_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth(), shape.getY()+bounds.getHeight());
			case LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY()+bounds.getHeight()/2.);
			case RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth(), shape.getY()+bounds.getHeight()/2.);
			case BASE: case BASE_RIGHT: case BASE_LEFT:
			case CENTER : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth()/2., shape.getY()+bounds.getHeight()/2.);
		}

		return null;
	}



	@Override
	public void paint(final Graphics2D g) {
		if(g==null)
			return ;

		final Image image = FlyweightThumbnail.getImage(this);
		final IPoint p = beginRotation(g);
		final IPoint position = image==null ? getTextPositionText() : getTextPositionImage();

		if(image==null) {
			g.setColor(shape.getLineColour());
			g.setFont(FONT);
			g.drawString(shape.getText(), (int)position.getX(), (int)position.getY());
		}
		else {
			final double scale = FlyweightThumbnail.scaleImage();
			g.scale(1/scale, 1/scale);
			g.drawImage(image, (int)(position.getX()*scale), (int)(position.getY()*scale), null);
			g.scale(scale, scale);
		}

		if(p!=null)
			endRotation(g, p);
	}



	@Override
	public void updateBorder() {
		final Image image = FlyweightThumbnail.getImage(this);
		final IPoint position = image==null ? getTextPositionText() : getTextPositionImage();
		final double angle = shape.getRotationAngle();
		double tlx;
		double tly;
		double widthBorder;
		double heightBorder;

		if(image==null) {
			final TextLayout tl = new TextLayout(shape.getText(), FONT, FONT_METRICS.getFontRenderContext());
			final Rectangle2D bounds = tl.getBounds();
			tlx = position.getX();
			tly = position.getY()-bounds.getHeight()+tl.getDescent();
			widthBorder = tl.getAdvance();
			heightBorder = bounds.getHeight();
		}
		else {
			final double scale = FlyweightThumbnail.scaleImage();
			tlx = position.getX();
			tly = position.getY();
			widthBorder = image.getWidth(null)*(1/scale);
			heightBorder = image.getHeight(null)*(1/scale);
		}

		if(LNumber.INSTANCE.equals(angle, 0.))
			border.setFrame(tlx, tly, widthBorder, heightBorder);
		else {
			IPoint tl = DrawingTK.getFactory().createPoint();
			IPoint br = DrawingTK.getFactory().createPoint();
			getRotatedRectangle(tlx, tly, widthBorder, heightBorder, angle, shape.getGravityCentre(), tl, br);
			// The border of the rotated rectangle is now the border of the rectangular view.
			border.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
		}
	}


	@Override
	protected void updateDblePathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathOutside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathOutside() {
		// Nothing to do.
	}


	@Override
	public boolean isToolTipVisible(final double x, final double y) {
		return border.contains(x, y);
	}


	@Override
	public String getToolTip() {
		String msg;
		final String log = FlyweightThumbnail.getLog(this);

		if(log==null || log.length()==0)
			msg = "";
		else {
			msg = FlyweightThumbnail.getLatexErrorMessageFromLog(shape);

			if(msg.length()==0)
				msg = log;
		}

		return msg;
	}
}
