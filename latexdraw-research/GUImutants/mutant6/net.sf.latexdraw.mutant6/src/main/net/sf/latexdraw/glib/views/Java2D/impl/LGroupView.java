package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

/**
 * Defines a view of the IGroup model.<br>
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
class LGroupView extends LShapeView<IGroup> {
	/** The view that contains the drawing. */
	protected List<IViewShape> views;


	/**
	 * Creates an initialises a drawing view.
	 * @since 3.0
	 */
	protected LGroupView() {
		this(DrawingTK.getFactory().createGroup(false));
	}


	/**
	 * Creates an initialises a drawing view.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LGroupView(final IGroup model) {
		super(model);

		views = new ArrayList<>();

		for(IShape s : model.getShapes())
			views.add(View2DTK.getFactory().createView(s));

		updateBorder();
	}


	@Override
	public void flush() {
		super.flush();

		for(IViewShape v : views)
			v.flush();
		views.clear();
	}


	@Override
	public void update() {
		for(IViewShape view : views)
			view.update();

		super.update();
	}


	@Override
	public boolean contains(final double x, final double y) {
		// We test the borders first to limit the computations.
		if(!border.contains(x, y))
			return false;

		for(IViewShape sh : views)
			if(sh.contains(x, y))
				return true;

		return false;
	}


	/**
	 * @return The number of views that has the drawing view.
	 * @since 3.0
	 */
	public int size() {
		return views.size();
	}


	@Override
	public boolean intersects(final Rectangle2D r) {
		final int size = views.size();
		int i=0;
		boolean again = true;

		while(i<size && again)
			if(views.get(i).intersects(r))
				again = false;
			else
				i++;

		return !again;
	}


	@Override
	public boolean contains(final IPoint p) {
		if(p==null)
			return false;

		if(!border.contains(p.getX(), p.getY()))
			return false;

		final int size=size();
		int i=0;
		boolean again = true;

		while(i<size && again)
			if(views.get(i).contains(p))
				again = false;
			else
				i++;

		return !again;
	}


	@Override
	public void paint(final Graphics2D g) {
		for(IViewShape view : views)
			view.paint(g);
	}


	@Override
	public void updateBorder() {
		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		Rectangle2D rec;

		for(final IViewShape v : views) {
			rec = v.getBorder();

			if(rec.getMinX()<xMin) xMin = rec.getMinX();
			if(rec.getMinY()<yMin) yMin = rec.getMinY();
			if(rec.getMaxX()>xMax) xMax = rec.getMaxX();
			if(rec.getMaxY()>yMax) yMax = rec.getMaxY();
		}

		border.setFrameFromDiagonal(xMin, yMin, xMax, yMax);
	}


	/**
	 * @return the views.
	 * @since 3.0
	 */
	public List<IViewShape> getViews() {
		return views;
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
}
