package net.sf.latexdraw.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.UIManager;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;

import org.malai.swing.widget.MProgressBar;

/**
 * This class defines a splash screen displayed during the start of the program with a progress
 * bar showing us the progression of the loading of the LaTeXDraw interface.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
public class SplashScreen extends JFrame {
	private static final long serialVersionUID = 1L;

	/** The progress bar showing us the progression of the loading of the LaTeXDraw interface.*/
	protected MProgressBar progressBar;

	/** The canvas that contains the image to display. */
	protected DisplayCanvas canvas;


	/**
	 * The constructor by default.
	 * @param lookAndFeel the class of the look and feel; default LaF if null.
	 */
	public SplashScreen(final String lookAndFeel) {
		super();

		try {
			if(lookAndFeel==null || lookAndFeel.length()==0)
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			else
				UIManager.setLookAndFeel(lookAndFeel);
		}catch(final Exception ex) { BadaboomCollector.INSTANCE.add(ex); }

		try{setIconImage(LResources.LATEXDRAW_ICON.getImage());}catch(Exception ex){BadaboomCollector.INSTANCE.add(ex);}
		setUndecorated(true);
		setType(Window.Type.UTILITY);
		Dimension dim 	= LSystem.INSTANCE.getScreenDimension();
		final Rectangle frameBound = getGraphicsConfiguration().getBounds();
		progressBar 	= new MProgressBar(0, 100);
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

		// In case of dual screen, frameBound provides the position of the current screen.
		setLocation((int)(frameBound.getX()+(dim.width-img.getWidth(null))/2.), (int)(frameBound.getY()+(dim.height-img.getHeight(null))/2.));
		setSize(img.getWidth(null), img.getHeight(null)+15);
	}


	/**
	 * Flushes the resources taken by the spash screen.
	 * @since 3.0
	 */
	public void flush() {
		canvas.flush();
	}


	/**
	 * @return The progress bar used to show the progress of the initialisation.
	 * @since 3.0
	 */
	public MProgressBar getProgressBar() {
		return progressBar;
	}
}
