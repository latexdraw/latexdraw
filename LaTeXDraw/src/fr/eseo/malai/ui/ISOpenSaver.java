package fr.eseo.malai.ui;


/**
 * Defines an interface for objects that must open and save abstract presentations and
 * instruments data into a file.<br>
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
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public interface ISOpenSaver {
	/**
	 * Saves the abstract presentations and the instruments parameters of the given UI
	 * to the given file.
	 * @param path The destination path.
	 * @param ui The user interface that contains abstract presentations and instruments.
	 * @return True: the operation is successful.
	 * @since 0.2
	 */
	boolean save(final String path, final UI ui);


	/**
	 * Opens the given file and sets the abstract presentations and the instruments parameters of the given UI
	 * to the given file.
	 * @param path The source path that contains information for presentations and instruments.
	 * @param ui The user interface that contains abstract presentations and instruments.
	 * @return True: the operation is successful.
	 * @since 0.2
	 */
	boolean open(final String path, final UI ui);
}
