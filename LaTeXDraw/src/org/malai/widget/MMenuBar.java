package org.malai.widget;

import java.awt.geom.Point2D;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JMenuBar. It allows to be used in the Malai framework for picking.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MMenuBar extends JMenuBar implements Picker{
	private static final long serialVersionUID = 1L;


	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o) {
		if(o==null || !contains((int)x, (int)y))
			return null;

		Point2D ptRel 	= new Point2D.Double(Double.NaN, Double.NaN);
		int i			= 0;
		boolean ok 		= false;
		final int size	= getMenuCount();

		while(i<size && !ok)
			if(getMenu(i)==o)
				ok = true;
			else
				i++;

		if(ok)
			ptRel.setLocation(x, y);
		else {
			JMenu menu;
			i = 0;

			while(Double.isNaN(ptRel.getX()) && i<size) {
				menu = getMenu(i);

				if(menu instanceof Picker)
					ptRel = ((Picker)menu).getRelativePoint(x-menu.getX(), y-menu.getY(), o);

				i++;
			}
		}

		return ptRel;
	}


	@Override
	public Pickable getPickableAt(final double x, final double y) {
		if(!contains((int)x, (int)y))
			return null;

		Pickable o		= null;
		int i			= 0;
		final int size 	= getMenuCount();
		JMenu menu;

		while(i<size && o==null) {
			menu = getMenu(i);

			if(menu instanceof Pickable && menu.getBounds().contains(x, y))
				o = (Pickable)menu;
			else if(getMenu(i) instanceof Picker)
				o = ((Picker)menu).getPickableAt(x-menu.getX(), y-menu.getY());
			i++;
		}

		return o;
	}


	@Override
	public Picker getPickerAt(final double x, final double y) {
		if(!contains((int)x, (int)y))
			return null;

		Picker o		= null;
		int i			= 0;
		final int size 	= getMenuCount();
		JMenu menu;

		while(i<size && o==null) {
			menu = getMenu(i);

			if(menu instanceof Picker && menu.getBounds().contains(x, y))
				o = (Picker)menu;
			else
				i++;
		}

		return o;
	}


	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(getComponents(), obj);
	}
}
