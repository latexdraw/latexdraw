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
	 * @param i The position of the view to get.
	 * @return The view at the given position.
	 * @since 3.0
	 */
	public IViewShape getViewAt(final int i) {
		if(i>=0 && i<views.size())
			return views.get(i);

		return null;
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


//	public void distributeHorizBetween()
//	{
//		LinkedList<LShapeView> sortedF 	= new LinkedList<LShapeView>();
//		LinkedList<Double> rights 		= new LinkedList<Double>();
//		LinkedList<Double> lefts 		= new LinkedList<Double>();
//		int i, size;
//		boolean ok;
//		double gap, x;
//		Rectangle2D bounds;
//
//		for(LShapeView vi : views)
//		{
//			bounds = vi.getBounds2D();
//			ok     = true;
//			x      = bounds.getMinX();
//
//			for(i=0, size=lefts.size(); i<size && ok; i++)
//				if(x<lefts.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(vi);
//				lefts.addLast(x);
//				rights.addLast(bounds.getMaxX());
//			}
//			else {
//				sortedF.add(i-1, vi);
//				lefts.add(i-1, x);
//				rights.add(i-1, bounds.getMaxX());
//			}
//		}
//
//		gap = lefts.getLast() - rights.getFirst();
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			gap -= rights.get(i) - lefts.get(i);
//
//		gap/=size;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate((sortedF.get(i-1).getBounds2D().getMaxX() + gap) - lefts.get(i), 0);
//
//		sortedF.clear();
//		rights.clear();
//		lefts.clear();
//		rights  = null;
//		lefts   = null;
//		sortedF = null;
//	}
//
//
//
//	/**
//	 * Distributes horizontally at equal distance between the middle of the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeHorizMiddle()
//	{
//		Vector<IShape> shapes  	   = ((IGroup)shape).getShapes();
//		LinkedList<IShape> sortedF = new LinkedList<IShape>();
//		LinkedList<Double> gcs     = new LinkedList<Double>();
//		IPoint pt;
//		int i, size;
//		boolean ok;
//		double gap;
//
//		for(IShape sh : shapes)
//		{
//			pt = sh.getGravityCentre();
//			ok = true;
//
//			for(i=0, size=sortedF.size(); i<size && ok; i++)
//				if(pt.getX()<gcs.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(sh);
//				gcs.addLast(pt.getX());
//			}
//			else {
//				sortedF.add(i-1, sh);
//				gcs.add(i-1, pt.getX());
//			}
//		}
//
//		gap = (gcs.getLast()-gcs.getFirst())/(size()-1);
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate((gcs.getFirst()+i*gap)-gcs.get(i), 0);
//
//		sortedF.clear();
//		gcs.clear();
//		gcs     = null;
//		sortedF = null;
//	}
//
//
//
//	/**
//	 * Distributes horizontally at equal distance between the left sides of the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeHorizLeft()
//	{
//		LinkedList<LShapeView> sortedF = new LinkedList<LShapeView>();
//		LinkedList<Double> nws         = new LinkedList<Double>();
//		int i, size;
//		boolean ok;
//		double gap, x;
//
//		for(LShapeView vi : views)
//		{
//			x  = vi.getBounds2D().getMinX();
//			ok = true;
//
//			for(i=0, size=sortedF.size(); i<size && ok; i++)
//				if(x<nws.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(vi);
//				nws.addLast(x);
//			}
//			else {
//				sortedF.add(i-1, vi);
//				nws.add(i-1, x);
//			}
//		}
//
//		gap = (nws.getLast()-nws.getFirst())/(size()-1);
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate((nws.getFirst()+i*gap)-nws.get(i), 0);
//
//		sortedF.clear();
//		nws.clear();
//		nws     = null;
//		sortedF = null;
//	}
//
//
//
//	/**
//	 * Distributes horizontally at equal distance between the right sides of the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeHorizRight()
//	{
//		LinkedList<LShapeView> sortedF = new LinkedList<LShapeView>();
//		LinkedList<Double> ses         = new LinkedList<Double>();
//		int i, size;
//		boolean ok;
//		double gap, x;
//
//		for(LShapeView vi : views)
//		{
//			x = vi.getBounds2D().getMaxX();
//
//			ok = true;
//			for(i=0, size=sortedF.size(); i<size && ok; i++)
//				if(x<ses.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(vi);
//				ses.addLast(x);
//			}
//			else {
//				sortedF.add(i-1, vi);
//				ses.add(i-1, x);
//			}
//		}
//
//		gap = (ses.getLast()-ses.getFirst())/(size()-1);
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate((ses.getFirst()+i*gap)-ses.get(i), 0);
//
//		sortedF.clear();
//		ses.clear();
//		ses     = null;
//		sortedF = null;
//	}
//
//
//
//	/**
//	 * Distributes horizontally at equal distance between the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeVertBetween()
//	{
//		LinkedList<LShapeView> sortedF = new LinkedList<LShapeView>();
//		LinkedList<Double> tops        = new LinkedList<Double>();
//		LinkedList<Double> bots        = new LinkedList<Double>();
//		int i, size;
//		boolean ok;
//		double gap;
//		Rectangle2D bounds;
//
//		for(LShapeView vi : views)
//		{
//			bounds = vi.getBounds2D();
//
//			ok = true;
//			for(i=0, size=tops.size(); i<size && ok; i++)
//				if(bounds.getMinY()<tops.get(i))
//					ok = false;
//
//			if(ok)
//			{
//				sortedF.addLast(vi);
//				tops.addLast(bounds.getMinY());
//				bots.addLast(bounds.getMaxY());
//			}
//			else
//			{
//				sortedF.add(i-1, vi);
//				tops.add(i-1, bounds.getMinY());
//				bots.add(i-1, bounds.getMaxY());
//			}
//		}
//
//		gap = tops.getLast() - bots.getFirst();
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			gap -= bots.get(i) - tops.get(i);
//
//		gap/=size;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate(0, (sortedF.get(i-1).getBounds2D().getMaxY() + gap) - tops.get(i));
//
//		sortedF.clear();
//		tops.clear();
//		bots.clear();
//		tops    = null;
//		sortedF = null;
//		bots    = null;
//	}
//
//
//
//	/**
//	 * Distributes vertically at equal distance between the bottom sides of the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeVertBottom()
//	{
//		LinkedList<LShapeView> sortedF = new LinkedList<LShapeView>();
//		LinkedList<Double> ses         = new LinkedList<Double>();
//		boolean ok;
//		double gap, y;
//		int i, size;
//
//		for(LShapeView vi : views)
//		{
//			y  = vi.getBounds2D().getMaxY();
//			ok = true;
//
//			for(i=0, size=sortedF.size(); i<size && ok; i++)
//				if(y<ses.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(vi);
//				ses.addLast(y);
//			}
//			else {
//				sortedF.add(i-1, vi);
//				ses.add(i-1, y);
//			}
//		}
//
//		gap = (ses.getLast()-ses.getFirst())/(size()-1);
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate(0, (ses.getFirst()+i*gap)-ses.get(i));
//
//		sortedF.clear();
//		ses.clear();
//		ses     = null;
//		sortedF = null;
//	}
//
//
//
//	/**
//	 * Distributes vertically at equal distance between the middle of the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeVertMiddle()
//	{
//		Vector<IShape> shapes      = ((IGroup)shape).getShapes();
//		LinkedList<IShape> sortedF = new LinkedList<IShape>();
//		LinkedList<Double> gcs     = new LinkedList<Double>();
//		IPoint pt;
//		int i, size;
//		boolean ok;
//		double gap;
//
//		for(IShape sh : shapes)
//		{
//			pt = sh.getGravityCentre();
//			ok = true;
//
//			for(i=0, size=sortedF.size(); i<size && ok; i++)
//				if(pt.getY()<gcs.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(sh);
//				gcs.addLast(pt.getY());
//			}
//			else {
//				sortedF.add(i-1, sh);
//				gcs.add(i-1, pt.getY());
//			}
//		}
//
//		gap = (gcs.getLast()-gcs.getFirst())/(size()-1);
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate(0, (gcs.getFirst()+i*gap)-gcs.get(i));
//
//		sortedF.clear();
//		gcs.clear();
//		gcs     = null;
//		sortedF = null;
//	}
//
//
//
//	/**
//	 * Distributes vertically at equal distance between the top sides of the selected shapes.
//	 * @since 2.0.0
//	 */
//	public void distributeVertTop()
//	{
//		LinkedList<LShapeView> sortedF = new LinkedList<LShapeView>();
//		LinkedList<Double> nws         = new LinkedList<Double>();
//		int i, size;
//		boolean ok;
//		double gap, y;
//
//		for(LShapeView vi : views)
//		{
//			y  = vi.getBounds2D().getMinY();
//			ok = true;
//
//			for(i=0, size=sortedF.size(); i<size && ok; i++)
//				if(y<nws.get(i))
//					ok = false;
//
//			if(ok) {
//				sortedF.addLast(vi);
//				nws.addLast(y);
//			}
//			else {
//				sortedF.add(i-1, vi);
//				nws.add(i-1, y);
//			}
//		}
//
//		gap = (nws.getLast()-nws.getFirst())/(size()-1);
//		size = sortedF.size()-1;
//
//		for(i=1; i<size; i++)
//			sortedF.get(i).translate(0, (nws.getFirst()+i*gap)-nws.get(i));
//
//		sortedF.clear();
//		nws.clear();
//		nws     = null;
//		sortedF = null;
//	}


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
