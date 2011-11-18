package net.sf.latexdraw.glib.views.Java2D;

import net.sf.latexdraw.glib.models.interfaces.IFreehand;

/**
 * Defines a view of the IFreeHand model.<br>
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




	@Override
	public void updateBorder() {
		// TODO Auto-generated method stub

	}



	@Override
	protected void updateDblePathInside() {
		// TODO Auto-generated method stub

	}



	@Override
	protected void updateDblePathMiddle() {
		// TODO Auto-generated method stub

	}



	@Override
	protected void updateDblePathOutside() {
		// TODO Auto-generated method stub

	}



	@Override
	protected void updateGeneralPathInside() {
		// TODO Auto-generated method stub

	}



	@Override
	protected void updateGeneralPathMiddle() {
		// TODO Auto-generated method stub

	}



	@Override
	protected void updateGeneralPathOutside() {
		// TODO Auto-generated method stub

	}
}
