package net.sf.latexdraw.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.malai.ui.IProgressBar;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * This class defines a splash screen displayed during the start of the program with a progress
 * bar showing us the progression of the loading of the LaTeXDraw interface.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.<br>
 *<br>
 * 05/23/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 1.9
 */
public class SplashScreen extends JWindow implements IProgressBar {
	private static final long serialVersionUID = 1L;

	/** The progress bar showing us the progression of the loading of the LaTeXDraw interface.*/
	protected JProgressBar progressBar;

	/** The canvas that contains the image to display. */
	protected DisplayCanvas canvas;


	/**
	 * The constructor by default.
	 * @param lookAndFeel the class of the look and feel; default LaF if null.
	 */
	public SplashScreen(final String lookAndFeel) {
		super();

		try {
			if(lookAndFeel==null)
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			else
				UIManager.setLookAndFeel(lookAndFeel);
		}catch(final Exception ex) { BadaboomCollector.INSTANCE.add(ex); }

		Dimension dim 	= Toolkit.getDefaultToolkit().getScreenSize();
		progressBar 	= new JProgressBar(0, 100);
		Image img 		= Toolkit.getDefaultToolkit().getImage(
						  getClass().getClassLoader().getResource("res/LaTeXDrawSmall.png"));//$NON-NLS-1$
		MediaTracker tracker=new MediaTracker(this);
		tracker.addImage(img,0);

		try { tracker.waitForID(0); }
		catch(InterruptedException e) { BadaboomCollector.INSTANCE.add(e); }

		canvas = new DisplayCanvas(img);

		setLayout(new BorderLayout());
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		setLocation((dim.width-img.getWidth(null))/2, (dim.height-img.getHeight(null))/2);
		setSize(img.getWidth(null), img.getHeight(null)+15);
	}


	/**
	 * Flushes the resources taken by the spash screen.
	 * @since 3.0
	 */
	public void flush() {
		canvas.flush();
	}


	@Override
	public void addToProgressBar(final int increment) {
		final int cpt = progressBar.getValue()+increment;

		if(cpt>=progressBar.getMinimum() && cpt<=progressBar.getMaximum())
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {updateBar(cpt);} });
			}catch(Exception e) { BadaboomCollector.INSTANCE.add(e); }
	}



	/**
	 * Allows to update the progress bar.
	 * @param newValue its new value.
	 */
	protected void updateBar(final int newValue) {
		synchronized(progressBar){
			progressBar.setValue(newValue);
		}
	}
}
