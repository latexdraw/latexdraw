package net.sf.latexdraw.glib.views.Java2D.impl;

import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a view of the IFreeHand model.<br>
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
 * 04/13/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LFreeHandView extends LShapeView<IFreehand> {
	/**
	 * Creates and initialises a view of a free hand model.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LFreeHandView(final IFreehand model) {
		super(model);
		update();
	}


	/**
	 * Updates the Swing path of the freehand shape.
	 * @since 3.0
	 */
	protected void setPath() {
		path.reset();

		if(shape.getNbPoints()>1) {
			switch(shape.getType()) {
				case CURVES: setPathCurves(); break;
				case LINES: setPathLines(); break;
			}

			if(!shape.isOpen())
				path.closePath();
		}
	}


	/**
	 * Fills the path of curves.
	 */
	protected void setPathCurves() {
		final int interval = shape.getInterval();
		final List<IPoint> pts = shape.getPoints();
		final int size = pts.size();
		double prevx = pts.get(size-1).getX();
		double prevy = pts.get(size-1).getY();
		double curx = pts.get(0).getX();
		double cury = pts.get(0).getY();
        double midx = (curx + prevx)/2.;
        double midy = (cury + prevy)/2.;
		int i;
		double x1, x2, y1, y2;

    	path.moveTo(curx, cury);

    	// Starting the drawing of the shape with a line.
        if(size>interval) {
            prevx = curx;
            prevy = cury;
            curx = pts.get(interval).getX();
            cury = pts.get(interval).getY();
            midx = (curx + prevx) / 2.;
            midy = (cury + prevy) / 2.;

            path.lineTo(midx, midy);
        }

        // Adding curves
        for(i=interval*2; i<size; i+=interval) {
        	 x1 = (midx + curx)/2.;
             y1 = (midy + cury)/2.;
             prevx = curx;
             prevy = cury;
             curx = pts.get(i).getX();
             cury = pts.get(i).getY();
             midx = (curx + prevx)/2.;
             midy = (cury + prevy)/2.;
             x2 = (prevx + midx)/2.;
             y2 = (prevy + midy)/2.;

        	 path.curveTo(x1, y1, x2, y2, midx, midy);
        }

        // If it remains not used points.
        if(i-interval+1<size) {
        	x1 = (midx + curx)/2.;
        	y1 = (midy + cury)/2.;
            prevx = curx;
            prevy = cury;
            curx = pts.get(size-1).getX();
            cury = pts.get(size-1).getY();
            midx = (curx + prevx)/2.;
            midy = (cury + prevy)/2.;
            x2 = (prevx + midx)/2.;
            y2 = (prevy + midy)/2.;

        	path.curveTo(x1, y1, x2, y2, pts.get(size-1).getX(), pts.get(size-1).getY());
        }
	}


	/**
	 * Fills the path of lines.
	 */
	protected void setPathLines() {
		final int interval = shape.getInterval();
		final List<IPoint> pts = shape.getPoints();
		final int size = pts.size();
		IPoint pt = pts.get(0);
		int i;

		path.moveTo(pt.getX(), pt.getY());

		for(i=interval; i<size; i+=interval) {
			pt = pts.get(i);
			path.lineTo(pt.getX(), pt.getY());
		}

		if(i-interval<size)
			path.lineTo(pts.get(size-1).getX(), pts.get(size-1).getY());
	}


	@Override
	protected void updateDblePathInside() {
		updateDblePathMiddle();
	}

	@Override
	protected void updateDblePathMiddle() {
		updateGeneralPathMiddle();
	}

	@Override
	protected void updateDblePathOutside() {
		updateDblePathMiddle();
	}

	@Override
	protected void updateGeneralPathInside() {
		updateGeneralPathMiddle();
	}

	@Override
	protected void updateGeneralPathMiddle() {
		setPath();
	}

	@Override
	protected void updateGeneralPathOutside() {
		updateGeneralPathMiddle();
	}
}
