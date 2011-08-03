package net.sf.latexdraw.instruments;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.IShapeView;
import net.sf.latexdraw.mapping.Shape2BorderMapping;
import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.mapping.MappingRegistry;

/**
 * This instrument manages the selected views.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 10/27/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Border extends Instrument {
	/** The stroke uses by the border to display its bounding rectangle. */
	public static final BasicStroke STROKE = new BasicStroke(2f, BasicStroke.CAP_BUTT,
												BasicStroke.JOIN_MITER, 1f, new float[] { 7f, 7f}, 0);

	/** The selected views. */
	protected List<IShapeView<?>> selection;

	/** The rectangle uses to show the selection. */
	protected Rectangle2D border;



	/**
	 * Creates and initialises the border.
	 * @since 3.0
	 */
	public Border() {
		super();

		selection = new ArrayList<IShapeView<?>>();
		border	  = new Rectangle2D.Double();
	}


	@Override
	public boolean isActivated() {
		return super.isActivated() && selection.size()>0;
	}


	/**
	 * Updates the bounding rectangle using the selected views.
	 * @since 3.0
	 */
	public void update() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;

		Rectangle2D bounds;

		for(final IShapeView<?> view : selection) {
			bounds = view.getBorder();

			if(bounds.getMinX()<minX)
				minX = bounds.getMinX();

			if(bounds.getMinY()<minY)
				minY = bounds.getMinY();

			if(bounds.getMaxX()>maxX)
				maxX = bounds.getMaxX();

			if(bounds.getMaxY()>maxY)
				maxY = bounds.getMaxY();
		}

		border.setFrame(minX, minY, maxX-minX, maxY-minY);
	}



	/**
	 * Paints the border if activated.
	 * @param g The graphics in which the border is painted.
	 * @since 3.0
	 */
	public void paint(final Graphics2D g) {
		if(isActivated()) {
			g.setColor(Color.GRAY);
			g.setStroke(STROKE);
			g.draw(border);
		}
	}



	/**
	 * Adds the given shape to the selection. If the instrument is
	 * activated and the addition is performed, the instrument is updated.
	 * @param view The view to add. If null, nothing is done.
	 * @since 3.0
	 */
	public void add(final IShapeView<?> view) {
		if(view!=null && selection.add(view) && isActivated()) {
			// The border is updated only if the view has been added and
			// the border is activated.
			update();
			MappingRegistry.REGISTRY.addMapping(new Shape2BorderMapping(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), this));
		}
	}



	/**
	 * Removes the given view from the selection. If the instrument is
	 * activated and the removal is performed, the instrument is updated.
	 * @param view The view to remove. If null or it is not
	 * already in the selection, nothing is performed.
	 * @since 3.0
	 */
	public void remove(final IShapeView<?> view) {
		if(view!=null && isActivated() && selection.remove(view)) {
			MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), Shape2BorderMapping.class);
			update();
		}
	}


	/**
	 * @return the selected views. Cannot be null.
	 * @since 3.0
	 */
	public List<IShapeView<?>> getSelection() {
		return selection;
	}


	@Override
	protected void initialiseLinks() {
		// Nothing to do for the moment.
	}


	/**
	 * Removes all the selected views.
	 * @since 3.0
	 */
	public void clear() {
		if(!selection.isEmpty()) {
			for(IShapeView<?> view : selection)
				MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), Shape2BorderMapping.class);

			selection.clear();

			if(isActivated())
				update();
		}
	}
}
