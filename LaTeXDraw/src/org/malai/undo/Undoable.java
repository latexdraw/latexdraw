package org.malai.undo;

/**
 * Defines an interface for undoable objects.
 * This file is part of libMalai.
 * Copyright (c) 2009-2012 Arnaud BLOUIN
 *
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * @author Arnaud BLOUIN
 * @date 05/23/2010
 * @since 0.1
 */
public interface Undoable {
	/**
	 * Cancels the action.
	 * @since 0.1
	 */
	void undo();


	/**
	 * Redoes the cancelled action.
	 * @since 0.1
	 */
	void redo();


	/**
	 * @return The name of the undo action.
	 * @since 0.1
	 */
	String getUndoName();
}
