package net.sf.latexdraw.badaboom;

import java.util.Objects;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JEditorPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Defines a listener for the 'Bordel !' manager.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/18/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public class BadaboomListener implements ListSelectionListener {
	/** The manager to listen. */
	protected BadaboomManager manager;


	/**
	 * Creates a Border manager listener.
	 * @param bordelManager The manager to listen.
	 * @throws NullPointerException If the given manager is null.
	 */
	public BadaboomListener(final BadaboomManager bordelManager) {
		super();
		manager = Objects.requireNonNull(bordelManager);
	}


	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final int index = e!=null && e.getSource() instanceof DefaultListSelectionModel ?
						((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex() : -1;
		final JEditorPane desc = manager.description;

		if(index<0 || index>=BadaboomCollector.INSTANCE.size())
			desc.setText("");//$NON-NLS-1$
		else {
			final Throwable ex				= BadaboomCollector.INSTANCE.get(index);
			final StackTraceElement[] stack = ex.getStackTrace();
			int i;
			final int size 					= stack.length;
			final StringBuilder buf			= new StringBuilder(ex.toString());

		 	for(i=0; i<size; i++)
		 		buf.append("\n\tat ").append(stack[i]);//$NON-NLS-1$

		 	desc.setText(buf.toString());
		 	desc.setCaretPosition(0);
		}
	}



	/**
	 * @return the manager.
	 */
	public BadaboomManager getManager() {
		return manager;
	}
}
