package fr.eseo.malai.properties;

/**
 * Defines an interface for object that can be reinitialised.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/09/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface Reinitialisable {
	/**
	 * Reinitialises the object.
	 * @since 0.2
	 */
	void reinit();
}
