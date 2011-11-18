package net.sf.latexdraw.glib.views.Java2D;

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
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class LGroupView extends LShapeView<IGroup> {
	/** The view that contains the drawing. */
	protected List<IViewShape<?>> views;


	/**
	 * Creates an initialises a drawing view.
	 * @since 3.0
	 */
	public LGroupView() {
		this(DrawingTK.getFactory().createGroup(false));
	}



	/**
	 * Creates an initialises a drawing view.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LGroupView(final IGroup model) {
		super(model);

		views = new ArrayList<IViewShape<?>>();

		for(IShape s : model.getShapes())
			views.add(View2DTK.getFactory().generateView(s));

		updateBorder();
	}


	@Override
	public void update() {
		for(IViewShape<?> view : views)
			view.update();

		super.update();
	}



	/**
	 * @param i The position of the view to get.
	 * @return The view at the given position.
	 * @since 3.0
	 */
	public IViewShape<?> getViewAt(final int i) {
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

//		if(isSelected() && getHandler(p) != null)
//			return true;

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
		for(IViewShape<?> view : views)
			view.paint(g);

//		if(isSelected && views.size()>1)
//			border.paint(g);
	}



	@Override
	public void updateBorder() {
//		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
//		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
//		Rectangle2D rec;
//
//		for(IShapeView v : views) {
//			rec = v.getBorder();
//
//			if(rec.getMinX()<xMin) xMin = rec.getMinX();
//			if(rec.getMinY()<yMin) yMin = rec.getMinY();
//			if(rec.getMaxX()>xMax) xMax = rec.getMaxX();
//			if(rec.getMaxY()>yMax) yMax = rec.getMaxY();
//		}
//
//		border.border.setFrameFromDiagonal(xMin, yMin, xMax, yMax);
//		border.update();
	}



	/**
	 * @return the views.
	 * @since 3.0
	 */
	public List<IViewShape<?>> getViews() {
		return views;
	}



//	/**
//	 * Align the selected shapes to the shape on the left.
//	 * @since 2.0.0
//	 */
//	public void alignLeft()
//	{
//		if(size()<2)
//			return ;
//
//		Vector<IShape> shapes   = ((IGroup)shape).getShapes();
//		LinkedList<IPoint> tls 	= new LinkedList<IPoint>();
//		double minX 			= Double.MAX_VALUE;
//		IPoint pt;
//
//		for(IShape s : shapes) {
//			pt = s.getFullTopLeftPoint();
//			tls.add(pt);
//
//			if(pt.getX()<minX)
//				minX = pt.getX();
//		}
//
//		for(IShape s : shapes) {
//			pt = tls.poll();
//
//			if(((float)minX)!=((float)pt.getX()))
//				s.translate(minX-pt.getX(), 0);
//		}
//	}



//
//	/**
//	 * Align the selected shapes to the shape on the right.
//	 * @since 2.0.0
//	 */
//	public void alignRight()
//	{
//		if(size()<2)
//			return ;
//
//		Vector<IShape> shapes   = ((IGroup)shape).getShapes();
//		LinkedList<IPoint> brs 	= new LinkedList<IPoint>();
//		double maxX 			= Double.MIN_VALUE;
//		IPoint pt;
//
//		for(IShape s : shapes) {
//			pt = s.getFullBottomRightPoint();
//			brs.add(pt);
//
//			if(pt.getX()>maxX)
//				maxX = pt.getX();
//		}
//
//		for(IShape s : shapes) {
//			pt = brs.poll();
//
//			if(((float)maxX)!=((float)pt.getX()))
//				s.translate(maxX-pt.getX(), 0);
//		}
//	}



//	/**
//	 * Align the selected shapes to the shape on the top.
//	 * @since 2.0.0
//	 */
//	public void alignTop()
//	{
//		if(size()<2)
//			return ;
//
//		LinkedList<IPoint> tls 	= new LinkedList<IPoint>();
//		double minY 			= Double.MAX_VALUE;
//		IPoint pt;
//		Vector<IShape> shapes   = ((IGroup)shape).getShapes();
//
//		for(IShape s : shapes) {
//			pt = s.getFullTopLeftPoint();
//			tls.add(pt);
//
//			if(pt.getY()<minY)
//				minY = pt.getY();
//		}
//
//		for(IShape s : shapes) {
//			pt = tls.poll();
//
//			if(((float)minY)!=((float)pt.getY()))
//				s.translate(0, minY-pt.getY());
//		}
//	}


//
//
//	/**
//	 * Align the selected figures to the figure on the bottom.
//	 * @since 2.0.0
//	 */
//	public void alignBottom()
//	{
//		if(size()<2)
//			return ;
//
//		LinkedList<IPoint> ses 	= new LinkedList<IPoint>();
//		double maxY 			= Double.MIN_VALUE;
//		IPoint pt;
//		Vector<IShape> shapes  	= ((IGroup)shape).getShapes();
//
//		for(IShape f : shapes) {
//			pt = f.getFullBottomRightPoint();
//			ses.add(pt);
//
//			if(pt.getY()>maxY)
//				maxY = pt.getY();
//		}
//
//		for(IShape f : shapes) {
//			pt = ses.poll();
//
//			if(((float)maxY)!=((float)pt.getY()))
//				f.translate(0, maxY-pt.getY());
//		}
//	}



//	/**
//	 * Align vertically the selected figures to the figure in the middle.
//	 * @since 2.0.0
//	 */
//	public void alignMiddleVertically()
//	{
//		if(size()<2)
//			return ;
//
//		LinkedList<Double> middles = new LinkedList<Double>();
//		double maxX = Double.MIN_VALUE;
//		double minX = Double.MAX_VALUE, middle, middle2;
//		IPoint pt, pt2;
//		Vector<IShape> shapes      = ((IGroup)shape).getShapes();
//
//		for(IShape f : shapes) {
//			pt  = f.getFullTopLeftPoint();
//			pt2 = f.getFullBottomRightPoint();
//
//			if(pt.getX()<minX)
//				minX = pt.getX();
//
//			if(pt2.getX()>maxX)
//				maxX = pt2.getX();
//
//			middles.add((pt.getX()+pt2.getX())/2.);
//		}
//
//		middle = (minX+maxX)/2.;
//
//		for(IShape f : shapes) {
//			middle2 = middles.poll();
//
//			if(((float)middle2)!=((float)middle))
//				f.translate(middle-middle2, 0);
//		}
//	}
//
//
//
//	/**
//	 * Align horizontally the selected figures to the figure in the middle.
//	 * @since 2.0.0
//	 */
//	public void alignMiddleHorizontally()
//	{
//		if(size()<2)
//			return ;
//
//		LinkedList<Double> middles = new LinkedList<Double>();
//		Vector<IShape> shapes      = ((IGroup)shape).getShapes();
//		double maxY = Double.MIN_VALUE;
//		double minY = Double.MAX_VALUE, middle, middle2;
//		IPoint pt, pt2;
//
//		for(IShape sh : shapes) {
//			pt  = sh.getFullTopLeftPoint();
//			pt2 = sh.getFullBottomRightPoint();
//
//			if(pt.getY()<minY)
//				minY = pt.getY();
//
//			if(pt2.getY()>maxY)
//				maxY = pt2.getY();
//
//			middles.add((pt.getY()+pt2.getY())/2.);
//		}
//
//		middle = (minY+maxY)/2.;
//
//		for(IShape f : shapes) {
//			middle2 = middles.poll();
//
//			if(((float)middle2)!=((float)middle))
//				f.translate(0, middle-middle2);
//		}
//	}
//
//
//
//
//
//	/**
//	 * Distributes horizontally at equal distance between the selected shapes.
//	 * @since 2.0.0
//	 */
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
