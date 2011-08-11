package org.malai.action.library;

import org.malai.action.Action;
import org.malai.undo.UndoCollector;

/**
 * Defines an action that redoes an action.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/10/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class Redo extends Action {
	/**
	 * Initialises a Redo action.
	 * @since 0.2
	 */
	public Redo() {
		super();
	}


	@Override
	public boolean canDo() {
		return UndoCollector.INSTANCE.getLastRedo()!=null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}




	@Override
	protected void doActionBody() {
		UndoCollector.INSTANCE.redo();
		done();
	}
}
