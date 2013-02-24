package net.sf.latexdraw.installer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.sf.latexdraw.util.LSystem;

/**
 * This class defines the LaTeXDraw installer.<br>
 * <br>
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
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 05/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class Installer extends JFrame {
	private static final long serialVersionUID = 1L;

	/** The listener of the installer. */
	private InstallerListener listener;

	protected Slide startSlide;
	
	protected ChooseDirSlide chooseSlide;
	
	protected InstallSlide installSlide;
	
	protected Slide currentSlide;

	/** The button "previous". */
	protected JButton previousB;

	/** The button "next". */
	protected JButton nextB;
	
	protected JButton endB;

	protected static final String ACTION_NEXT = "next";//$NON-NLS-1$

	protected static final String ACTION_PREVIOUS = "previous";//$NON-NLS-1$

	protected static final String ACTION_CANCEL = "cancel";//$NON-NLS-1$
	
	protected static final String ACTION_END = "end";//$NON-NLS-1$



	/**
	 * The main function: it creates an installer and launch it.
	 * @param argc Useless.
	 * @since 1.9.2
	 */
	public static void main(final String argc[]) {
		new Installer().setVisible(true);
	}


	/**
	 * The default constructor.
	 */
	private Installer() {
		super("LaTeXDraw installer");

		listener = new InstallerListener();

		if(LSystem.INSTANCE.isLinux() && System.getProperty("java.version").toLowerCase().compareTo("1.6")>=0)//$NON-NLS-1$//$NON-NLS-2$
			try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); }//$NON-NLS-1$
			catch(final Exception ex) { ex.printStackTrace(); }

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("res/LaTeXDrawIcon.png")));//$NON-NLS-1$
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		final JPanel ctrlPanel = createControlButtonsPanel();
		
		startSlide 		= new StartSlide(null, this);
		chooseSlide 	= new ChooseDirSlide(startSlide, null, this);
		installSlide 	= new InstallSlide(chooseSlide, null, this);
		startSlide.next = chooseSlide;
		chooseSlide.next= installSlide;
		
		currentSlide = startSlide;
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createHeaderPanel(), BorderLayout.NORTH);
		getContentPane().add(ctrlPanel, BorderLayout.SOUTH);
		currentSlide.install();

		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		pack();
		setLocation(dim.width/2-getWidth()/2, dim.height/2-getHeight()/2);
		setVisible(false);
	}



	/**
	 * @return The panel containing the control buttons (next, previous, and so on).
	 * @since 1.9.2
	 */
	private JPanel createControlButtonsPanel() {
		final JPanel ctrlPanel = new JPanel();
		final JButton b = new JButton("Cancel");
		b.setActionCommand(ACTION_CANCEL);
		b.setName(ACTION_CANCEL);
		b.addActionListener(listener);

		ctrlPanel.add(b);

		previousB = new JButton("Previous");
		previousB.setActionCommand(ACTION_PREVIOUS);
		previousB.setName(ACTION_PREVIOUS);
		previousB.addActionListener(listener);

		ctrlPanel.add(previousB);

		nextB = new JButton("Next");
		nextB.setActionCommand(ACTION_NEXT);
		nextB.setName(ACTION_NEXT);
		nextB.addActionListener(listener);
		
		ctrlPanel.add(nextB);
		
		endB = new JButton("End");
		endB.setActionCommand(ACTION_END);
		endB.setName(ACTION_END);
		endB.addActionListener(listener);

		ctrlPanel.add(endB);
		
		ctrlPanel.setBorder(new CompoundBorder(new TitledBorder(null, null, TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(0,0,0,0)));

		return ctrlPanel;
	}



	/**
	 * @return The top panel (the banner in fact).
	 * @since 1.9.2
	 */
	private JPanel createHeaderPanel() {
		final JPanel header = new JPanel();

		header.add(new JLabel("<html><div align=\"center\"><font size=\"+1\" color=\"white\">"+"Assistant of installation"+"</font></div></html>"));//$NON-NLS-1$//$NON-NLS-3$
		header.setBackground(new Color(90, 100, 130));
		header.setBorder(new CompoundBorder(new TitledBorder(null, null, TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(0,0,0,0)));

		return header;
	}



	class InstallerListener implements ActionListener {
		InstallerListener() {
			super();
		}
		
		public void actionPerformed(final ActionEvent e) {
			final Object o = e.getSource();

			if(o instanceof JButton) {
				final String actionCmd = ((JButton)o).getActionCommand();

				if(Installer.ACTION_CANCEL.equals(actionCmd)) {
					if(Installer.this.installSlide.isContinueInstall())
						Installer.this.installSlide.setContinueInstall(false);
					else
						System.exit(0);

					return ;
				}

				if(Installer.ACTION_NEXT.equals(actionCmd)) {
					Installer.this.currentSlide = Installer.this.currentSlide.next();
					return ;
				}

				if(Installer.ACTION_PREVIOUS.equals(actionCmd)) {
					Installer.this.currentSlide = Installer.this.currentSlide.prev();
					return ;
				}
				
				if(Installer.ACTION_END.equals(actionCmd))
					System.exit(0);

				return ;
			}
		}
	}
}
