package fr.eseo.malai.instrument;

/**
 * This exception must be launched when an action which is not undoable want to be undone or redone.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 *
 * @author Arnaud BLOUIN
 * @date 05/24/10
 * @since 0.1
 * @version 0.2
 */
public class MustBeUndoableActionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/** The class of the action that want to be undone/redone. */
	protected Class<?> clazz;


	/**
	 * The default constructor of the exception.
	 * @param clazz The class of the action that want to be undone/redone.
	 * @since 0.1
	 */
	public MustBeUndoableActionException(final Class<?> clazz) {
		super();

		this.clazz = clazz;
	}



	@Override
	public String toString() {
		return super.toString() + (clazz==null ?  "" : " " + clazz.getSimpleName()); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
