package org.malai.action.library;


/**
 * This action allows to load abstract presentations and instruments' parameters to a file.
 * <br>
 * This file is part of Malai<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 *  Malai is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  Malai is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * @author Arnaud Blouin
 * @since 0.2
 */
public class Load extends IOAction {
	/**
	 * Creates a save action.
	 * @since 0.2
	 */
	public Load() {
		super();
	}


	@Override
	protected void doActionBody() {
		ui.reinit();
		ok = openSaveManager.open(file.getPath(), ui);
		ui.setModified(false);
	}
}
