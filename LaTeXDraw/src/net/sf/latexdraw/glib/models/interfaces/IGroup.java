package net.sf.latexdraw.glib.models.interfaces;


/**
 * Defines an interface that classes defining a group of shapes should implement.<br>
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
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IGroup extends ISetShapes, ILineArcShape, IText {
	/**
	 * @return True if one of the shape of the group supports rounded corners.
	 * @since 3.0
	 */
	boolean containsRoundables();

	/**
	 * @return True if one of the shape of the group is a text.
	 * @since 3.0
	 */
	boolean containsTexts();

	/**
	 * Duplicates the group of shapes.
	 * @param duplicateShapes True: the shapes will be duplicated as well.
	 * @return The duplicated group of shapes.
	 * @since 3.0
	 */
	IGroup duplicate(final boolean duplicateShapes);
}
