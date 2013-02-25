package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniser;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;

/**
 * Defines a synchroniser between a sets of Java2D views and their
 * PSTricks views.<br>
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
public class PSTViewsSynchroniser extends ViewsSynchroniser {
	/**
	 * Creates and launches the synchronisation of the synchroniser.
	 * @param drawing The shapes to synchronise.
	 * @param h The object called for several operations needed by the synchroniser.
	 * @since 3.0
	 */
	public PSTViewsSynchroniser(final ViewsSynchroniserHandler h, final IDrawing drawing) {
		super(h, drawing);

		synchronise();
	}



	@Override
	public void synchronise() {
		if(drawing.isEmpty()) {
			viewsCode.clear();
			return ;
		}

		PSTShapeView<?> pstView;

		for(IShape shape : drawing.getShapes())
			if(getView(shape)==null) {
				pstView = PSTViewsFactory.INSTANCE.createView(shape);

				if(pstView!=null) {
					pstView.updateCache(handler.getOriginDrawingPoint(), handler.getPPCDrawing());
					viewsCode.put(shape, pstView);
				}
			}

		if(viewsCode.size()<drawing.size())
			BadaboomCollector.INSTANCE.add(new ArrayIndexOutOfBoundsException("Error during the synchronisation.")); //$NON-NLS-1$
	}



	@Override
	public PSTShapeView<?> getView(final IShape shape) {
		return (PSTShapeView<?>) super.getView(shape);
	}



	@Override
	public PSTShapeView<?> getViewAt(final int position) {
		return (PSTShapeView<?>) super.getViewAt(position);
	}



	@Override
	public boolean updateCode(final IShape view) {
		if(view!=null && drawing.contains(view)) {
			PSTShapeView<?> pst = getView(view);

			if(pst==null)
				synchronise();
			else
				pst.updateCache(handler.getOriginDrawingPoint(), handler.getPPCDrawing());

			return true;
		}

		return false;
	}



	/**
	 * Updates the cache of every shapes.
	 * @since 3.0
	 */
	public void updateFull() {
		synchronise();

		for(IShape shape : drawing.getShapes())
			updateCode(shape);
	}
}
