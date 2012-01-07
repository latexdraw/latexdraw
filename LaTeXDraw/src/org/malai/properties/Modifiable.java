package org.malai.properties;

/**
 * Defines an interface for object that can be modified and set as modified. This interface can also be used
 * to notify objects that the Modifiable object as been modified.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/31/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface Modifiable {
	/**
	 * Sets the Modifiable object as modified.
	 * @param modified True: the element is will tagged as modified.
	 * @since 0.2
	 */
	void setModified(final boolean modified);

	/**
	 * @return True: the object has been modified. False otherwise.
	 * @since 3.0
	 */
	boolean isModified();
}
