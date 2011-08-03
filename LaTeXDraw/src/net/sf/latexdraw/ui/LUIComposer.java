package net.sf.latexdraw.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import fr.eseo.malai.ui.IProgressBar;
import fr.eseo.malai.ui.UIComposer;
import fr.eseo.malai.widget.MPanel;

/**
 * This composer composes the latexdraw user interface.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 20/31/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LUIComposer implements UIComposer {
	/** The frame of the interactive system that contains
	 *  the instruments and presentations to compose.
	 */
	protected LFrame frame;

	/** The progress bar used to show the progression of the construction of the frame. */
	protected IProgressBar progressBar;



	/**
	 * Creates the composer of the latexdraw user interface.
	 * @param frame The frame of the interactive system that contains
	 * the instruments and presentations to compose.
	 * @param progressBar The progress bar used to show the progression of the construction of the frame. Can be null.
	 * @since 3.0
	 */
	public LUIComposer(final LFrame frame, final IProgressBar progressBar) {
		super();

		if(frame==null)
			throw new IllegalArgumentException();

		this.frame = frame;
	}



	@Override
	public void compose() {
		/* Organisation of the general layout of the user interface. */
		final MPanel southPanel 	= new MPanel(false, false);
		final Container contentPane = frame.getContentPane();

		southPanel.setLayout(new BorderLayout());
     	southPanel.add(frame.propertiesToolbar, BorderLayout.CENTER);
     	southPanel.add(frame.statusBar, BorderLayout.SOUTH);

		contentPane.setLayout(new BorderLayout());
		contentPane.add(frame.toolbar, BorderLayout.NORTH);
		contentPane.add(frame.splitPane, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		frame.setJMenuBar(frame.menuBar);
		// The call the interim feedback of some instruments.
		frame.codePanelActivator.interimFeedback();
		// Updating the concrete presentations.
		frame.updatePresentations();

		if(progressBar!=null)
			progressBar.addToProgressBar(10);
	}
}
