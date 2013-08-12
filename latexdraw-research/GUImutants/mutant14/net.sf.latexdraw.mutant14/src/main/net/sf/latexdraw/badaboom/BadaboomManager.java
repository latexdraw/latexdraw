package net.sf.latexdraw.badaboom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Defines a frame that shows exceptions.<br>
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
public class BadaboomManager extends JFrame {
	private static final long serialVersionUID = 1L;

	/** Contains the description of the selected exception. */
	protected JEditorPane description;

	/** The table that sums up the gathered exceptions. */
	protected JTable table;


	/**
	 * Creates an initialises an exception manager.
	 */
	public BadaboomManager() {
		super();

		description 					= new JEditorPane();
		final JScrollPane scrollerDesc	= new JScrollPane();
		final JScrollPane scrollerTable	= new JScrollPane();
	 	final JPanel panel 				= new JPanel();
	 	final BadaboomTableModel model	= new BadaboomTableModel(BadaboomCollector.INSTANCE);
		table							= new JTable(model);
		final Dimension dim 			= Toolkit.getDefaultToolkit().getScreenSize();

		table.getSelectionModel().addListSelectionListener(new BadaboomListener(this));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		description.setEditable(false);
		scrollerDesc.getViewport().add(description);
		scrollerTable.getViewport().add(table);
		panel.add(scrollerDesc);
		panel.add(scrollerTable);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		setResizable(true);

		scrollerDesc.setPreferredSize(new Dimension(800, 350));
		scrollerDesc.setMinimumSize(new Dimension(800, 350));
		scrollerTable.setPreferredSize(new Dimension(800, 200));
		pack();
		setLocation(dim.width/2-getWidth()/2, dim.height/2-getHeight()/2);
	}


	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);

		if(visible)
			table.revalidate();
	}
}
