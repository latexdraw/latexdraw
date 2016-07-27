package net.sf.latexdraw.installer;

import net.sf.latexdraw.util.InstallerLog;
import net.sf.latexdraw.util.LSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;

public class ChooseDirSlide extends Slide {
	private static final long serialVersionUID = 1L;
	
	/** The field that defines the path where we have to install LaTeXDraw. */
	protected JTextField pathInstall;
	
	/** The file chooser used to locate files. */
	protected JFileChooser fileChooser;

	
	protected ChooseDirSlide(final Slide prev, final Slide next, final Installer installer) {
		super(prev, next, installer);
	}


	@Override
	protected void init() {
		super.init();
		
		final JPanel p1 = new JPanel();
		final JPanel p2 = new JPanel();
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));

		pathInstall = new JTextField();
		pathInstall.setEditable(false);
		pathInstall.setPreferredSize(new Dimension(250, 35));
		pathInstall.setMinimumSize(new Dimension(250, 35));
		pathInstall.setMaximumSize(new Dimension(250, 35));
		final JButton chooseB = new JButton("Choose folder");
		chooseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if(fileChooser.showDialog(installer, "Select") == JFileChooser.APPROVE_OPTION) {
					pathInstall.setText(fileChooser.getSelectedFile().getPath());
					InstallerLog.getLogger().log(Level.INFO, "Install path selected: " + fileChooser.getSelectedFile().getPath());
				}
			}
		});

		p2.add(new JLabel("Choose the path where the directory LaTeXDraw will be installed:"));
		p2.add(pathInstall);
		p2.add(chooseB);

		p2.setMaximumSize(new Dimension(450, 150));
		p2.setMinimumSize(new Dimension(450, 150));
		p2.setPreferredSize(new Dimension(450, 150));
		p1.setMaximumSize(new Dimension(450, 300));
		p1.setMinimumSize(new Dimension(450, 300));
		p1.setPreferredSize(new Dimension(450, 300));
		p1.add(p2);
		add(p1);
		setPanelDimension();

		if(LSystem.INSTANCE.isLinux() || LSystem.INSTANCE.IsMac())
			pathInstall.setText("/opt");//$NON-NLS-1$
		else {
			if(LSystem.INSTANCE.isWindows()) {
				File dir = new File("C:\\Program Files\\");//$NON-NLS-1$
				int cpt = 0;
				final int max = 10;

				while(!dir.exists() && cpt < max) dir = new File((char) ('C' + cpt++) + ":\\Program Files\\");//$NON-NLS-1$

				pathInstall.setText(dir.exists() ? dir.getPath() : "C:\\");//$NON-NLS-1$
			}
		}

		InstallerLog.getLogger().log(Level.INFO, "Predefined install path: " + pathInstall.getText() +
										" exists? " + (new File(pathInstall.getText())).exists());
	}
}
