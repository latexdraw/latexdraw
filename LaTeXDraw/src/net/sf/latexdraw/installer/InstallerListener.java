package net.sf.latexdraw.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import net.sf.latexdraw.installer.InstallerUI;

/**
 * This class defines the listener of the installer.
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
 * <br>
 * 01/21/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 1.9<br>
 */
public class InstallerListener implements ActionListener {
	/** The listened installer. */
	protected InstallerUI installer;

	/** The file chooser used to locate files. */
	protected JFileChooser fileChooser;


	/**
	 * Creates a installer listener.
	 * @param installerUI The listened installer.
	 */
	public InstallerListener(final InstallerUI installerUI) {
		if(installerUI==null)
			throw new IllegalArgumentException();

		installer = installerUI;

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
	}



	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object o = e.getSource();

		if(o instanceof JButton) {
			final String actionCmd = ((JButton)o).getActionCommand();

			if(actionCmd==null)
				return ;

			if(actionCmd.equals(InstallerUI.ACTION_CANCEL)) {
				if(installer.isContinueInstall())
					installer.setContinueInstall(false);
				else
					System.exit(0);

				return ;
			}

			if(actionCmd.equals(InstallerUI.ACTION_TERMINATE)) {
				System.exit(0);
				return ;
			}

			if(actionCmd.equals(InstallerUI.ACTION_NEXT)) {
				installer.incrementSlidePosition();
				return ;
			}

			if(actionCmd.equals(InstallerUI.ACTION_PREVIOUS)) {
				installer.decrementSlidePosition();
				return ;
			}

			if(actionCmd.equals(InstallerUI.ACTION_CHOOSE_FOLDER)) {
				if(fileChooser.showDialog(installer, "Select") == JFileChooser.APPROVE_OPTION)
					installer.setPathInstall(fileChooser.getSelectedFile().getPath());

				return ;
			}

			return ;
		}
	}
}
