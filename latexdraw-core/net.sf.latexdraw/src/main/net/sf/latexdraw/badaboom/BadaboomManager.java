package net.sf.latexdraw.badaboom;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.sf.latexdraw.util.VersionChecker;

/**
 * Defines a frame that shows exceptions.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
	private final JEditorPane description;

	/** The table that sums up the gathered exceptions. */
	private final JTable table;

	private final JButton email;

	private final JButton clear;


	/**
	 * Creates an initialises an exception manager.
	 */
	public BadaboomManager() {
		super();

		description 					= new JEditorPane();
		final JScrollPane scrollerDesc	= new JScrollPane();
		final JScrollPane scrollerTable	= new JScrollPane();
	 	final JPanel panel 				= new JPanel();
	 	final JPanel actionPanel		= new JPanel();
	 	final BadaboomTableModel model	= new BadaboomTableModel();
		table							= new JTable(model);
		final Dimension dim 			= Toolkit.getDefaultToolkit().getScreenSize();

		table.getSelectionModel().addListSelectionListener(evt -> {
			final int index = evt!=null && evt.getSource() instanceof DefaultListSelectionModel ?
				((DefaultListSelectionModel)evt.getSource()).getMinSelectionIndex() : -1;
			final JEditorPane desc = description;

			if(index<0 || index>=BadaboomCollector.INSTANCE.size())
				desc.setText("");//$NON-NLS-1$
			else {
				desc.setText(errorToString(BadaboomCollector.INSTANCE.get(index), false));
				desc.setCaretPosition(0);
			}
		});

		clear = new JButton("Clear");
		email = new JButton("Send by email");

		actionPanel.add(clear);
		actionPanel.add(email);

		clear.addActionListener(evt -> {
			BadaboomCollector.INSTANCE.clear();
			description.setText("");
			table.revalidate();
			email.setEnabled(false);
		});

		email.addActionListener(evt -> {
			if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
				String text = VersionChecker.VERSION + "\n" +
					BadaboomCollector.INSTANCE.stream().map(ex -> errorToString(ex, true)).collect(Collectors.joining("\n\n"));

				try {
					URI mailto = new URI("mailto:arno_b@users.sf.net?subject=LaTeXDraw%20Logs&body=" +
						URLEncoder.encode(text, "UTF-8").replace("+", "%20"));
					Desktop.getDesktop().mail(mailto);
				}catch(final IOException | URISyntaxException ex) {
					BadaboomCollector.INSTANCE.add(ex);
				}
			}
		});

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		description.setEditable(false);
		scrollerDesc.getViewport().add(description);
		scrollerTable.getViewport().add(table);
		panel.add(scrollerDesc);
		panel.add(scrollerTable);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(actionPanel, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
		setResizable(true);

		scrollerDesc.setPreferredSize(new Dimension(800, 350));
		scrollerDesc.setMinimumSize(new Dimension(800, 350));
		scrollerTable.setPreferredSize(new Dimension(800, 200));
		pack();
		setLocation(dim.width/2-getWidth()/2, dim.height/2-getHeight()/2);
	}


	private String errorToString(final Throwable ex, final boolean withLimit) {
		final StackTraceElement[] stackTrace = ex.getStackTrace();
		return ex.toString() + "\n" +
			IntStream.range(0, withLimit ? Math.min(7, stackTrace.length) : stackTrace.length).
				mapToObj(i -> stackTrace[i].toString()).collect(Collectors.joining("\n"));
	}

	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);

		if(visible) {
			table.revalidate();
			clear.setEnabled(!BadaboomCollector.INSTANCE.isEmpty());
			email.setEnabled(clear.isEnabled() && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL));
		}
	}
}
