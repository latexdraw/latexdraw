package net.sf.latexdraw.ui;

import com.google.inject.Inject;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Objects;

/**
 * This class defines an abstract scale ruler.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
 * 11/12/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public abstract class ScaleRuler extends JComponent implements Pickable, Accessible, AdjustmentListener {
	/** The current unit of the rulers. */
    protected static final IUnary<Unit> UNIT = new ActiveUnary<>(Unit.CM);

	/** The stroke of the ruler. */
	protected static final BasicStroke STROKE = new BasicStroke(0, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

	/** This value defines the threshold under which sub-lines of the rule will be not drawn. */
	protected static final double MIN_PCC_SUBLINES = 20.;

	/** The canvas that the ruler manages. */
	protected final Canvas canvas;

//	/** The event manager that listens events produced by the panel. */
//	protected final SwingEventManager eventManager;

	/** The size of the lines in axes */
	public static final int SIZE = 10;


	/**
	 * Creates the ruler.
	 * @param canvas The canvas that the ruler manages.
	 * @throws IllegalArgumentException If the given canvas is null.
	 */
	@Inject
    protected ScaleRuler(final Canvas canvas) {
		super();
		this.canvas  = Objects.requireNonNull(canvas);
//		eventManager = new SwingEventManager();
//		eventManager.attachTo(this);
		setDoubleBuffered(true);

//		canvas.getScrollpane().getVerticalScrollBar().addAdjustmentListener(this);
//		canvas.getScrollpane().getHorizontalScrollBar().addAdjustmentListener(this);
	}


	@Override
	public void adjustmentValueChanged(final AdjustmentEvent e) {
		repaint();
	}


	/**
	 * @return The starting position where the ruler must be drawn.
	 * @since 3.0
	 */
	protected abstract double getStart();


	/**
	 * @return The length of the ruler.
	 * @since 3.0
	 */
	protected abstract double getLength();


	/**
	 * Draws a line of the ruler.
	 * @param g2 The graphics where the line will be drawn. Must not be null.
	 * @param positionA The static position of the vertical or horizontal line.
	 * @param positionB1 The starting point of the line.
	 * @param positionB2 The ending point of the line.
	 * @throws NullPointerException if the given graphics is null.
	 * @since 3.0
	 */
	protected abstract void drawLine(final Graphics2D g2, final double positionA, final double positionB1, final double positionB2);

	/**
	 * Adapts the given graphics to the current position of the grid.
	 * @param g The graphics of the ruler.
	 * @since 3.1
	 */
	protected abstract void adaptGraphicsToViewpoint(final Graphics2D g);

	/**
	 * @return The gap between the origin (0) and the current position of the painted view.
	 * @since 3.1
	 */
	protected abstract double getClippingGap();

	@Override
    public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if(!(g instanceof Graphics2D)) return;

    	final double zoom 		= canvas.getZoom();
    	final double lgth 		= getLength()/zoom;
    	final double start 		= getStart()/zoom;
    	double ppc 				= canvas.getPPCDrawing();
    	final Graphics2D g2 	= (Graphics2D)g;
    	final double sizeZoomed = SIZE/zoom;
    	double i;
        double j;
        double cpt;

        // adjusting the ppc value according to the current unit.
		if(getUnit()==Unit.INCH)
			ppc*=PSTricksConstants.INCH_VAL_CM;

    	// Optimisation for limitating the painting to the visible part only.
    	final double clipStart = (int)(getClippingGap()/zoom/ppc)*ppc;

    	// Settings the parameters of the graphics.
    	adaptGraphicsToViewpoint(g2);
    	g2.scale(zoom, zoom);
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setStroke(STROKE);
    	g2.setColor(java.awt.Color.BLACK);

		// If the ppc is not to small sub-lines are drawn.
    	if(ppc>MIN_PCC_SUBLINES/zoom) {
    		final double ppc10 			= ppc/10.;
    		final double halfSizeZoomed = sizeZoomed/2.;

    		for(i=start+ppc10+clipStart; i<lgth; i+=ppc)
    			for(j=i, cpt=1; cpt<10; j+=ppc10, cpt++)
    				drawLine(g2, j, halfSizeZoomed, sizeZoomed);
    	}

    	// Major lines of the ruler are drawn.
    	for(i=start+clipStart; i<lgth;i+=ppc)
    		drawLine(g2, i, 0., sizeZoomed);
    }


	@Override
	public boolean contains(final double x, final double y) {
		return contains((int)x, (int)y);
	}


	@Override
	public Picker getPicker() {
		return null;// SwingWidgetUtilities.INSTANCE.getPicker(this);
	}


	/**
	 * @return the current unit used by the rulers.
	 * @since 3.0
	 */
	public static Unit getUnit() {
		return UNIT.getValue();
	}


	/**
	 * @param unit The unit that the rulers must use. Must not be null.
	 * @since 3.0
	 */
	public static void setUnit(final Unit unit) {
		if(unit!=null)
			ScaleRuler.UNIT.setValue(unit);
	}


	/**
	 * @return The singleton that contains the unit value.
	 * @since 3.0
	 */
	public static IUnary<Unit> getUnitSingleton() {
		return ScaleRuler.UNIT;
	}


    @Override
	public AccessibleContext getAccessibleContext() {
        if(accessibleContext==null)
            accessibleContext = new AccessibleScaleRuler();

        return accessibleContext;
    }


	/**
	 * This class implements accessibility support for the
     * <code>ScaleRuler</code> class. It provides an implementation of the
     * Java Accessibility API appropriate to panel user-interface elements.
	 */
    protected class AccessibleScaleRuler extends AccessibleJComponent {
		private static final long serialVersionUID = 1L;

        @Override
		public AccessibleRole getAccessibleRole() {
            return AccessibleRole.RULER;
        }
    }
}
