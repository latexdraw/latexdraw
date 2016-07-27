package net.sf.latexdraw.installer;

import net.sf.latexdraw.util.InstallerLog;
import net.sf.latexdraw.util.LSystem;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;

class StartSlide extends Slide {
	private static final long serialVersionUID = 1L;


	protected StartSlide(final Slide next, final Installer installer) {
		super(null, next, installer);
	}


	@Override
	protected void init() {
		super.init();
		
		String plus;
		final String javaVersion = System.getProperty("java.version");

		setPanelDimension();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		InstallerLog.getLogger().log(Level.INFO, "Java lesser than 1.7? " + (javaVersion.compareToIgnoreCase("1.7")<0));

		if(javaVersion.compareToIgnoreCase("1.7")<0) { //$NON-NLS-1$
			final JTextArea textArea 	= new JTextArea();
			final JLabel label 			= new JLabel();
			final JLabel label2 		= new JLabel();
			final JLabel label3 		= new JLabel();

			textArea.setEditable(false);

			label.setText("You must have Java 7 installed.");
			label2.setText("Your current version of Java is " + javaVersion + "."); //$NON-NLS-2$
			label3.setText("You can download the latest version of java here:");

			textArea.setText("http://www.java.com"); //$NON-NLS-1$
			textArea.setMaximumSize(new Dimension(300,20));
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label2.setAlignmentX(Component.CENTER_ALIGNMENT);
			label3.setAlignmentX(Component.CENTER_ALIGNMENT);
			textArea.setAlignmentX(Component.CENTER_ALIGNMENT);

			add(Box.createVerticalStrut(60));
			add(label);
			add(label2);
			add(label3);
			add(Box.createVerticalStrut(10));
			add(textArea);
			installer.nextB.setEnabled(false);
		} else {
			if(LSystem.INSTANCE.isLinux()) {
				InstallerLog.getLogger().log(Level.INFO, "is /opt a dir? " + new File("/opt").isDirectory());
			}

			if(LSystem.INSTANCE.isLinux() && !new File("/opt").isDirectory()) {
				final JLabel label = new JLabel();
				final JLabel label2 = new JLabel();
				final JTextField label3 = new JTextField();
				label3.setEditable(false);
				label3.setMaximumSize(new Dimension(550, 40));
				label.setText("Cannot proceed without the /opt folder.");
				label2.setText("You may create it using a command like:");
				label3.setText("sudo mkdir --mode=755 /opt");
				add(label);
				add(label2);
				add(label3);
				installer.nextB.setEnabled(false);
			}else {
				InstallerLog.getLogger().log(Level.INFO, "is Vista? " + LSystem.INSTANCE.isVista());

				if(LSystem.INSTANCE.isVista())
					plus="</li><br><li>Be careful with Vista!<br>You must have launched this installer via the script install_vista.vbs.";
				else plus="";//$NON-NLS-1$

				add(new JLabel("<html><br><br><br><ul><li>" + "This installer will: create the required directories; create some provided templates;<br>place the latexdraw files in the chosen directory." +//$NON-NLS-1$
						"</li><br><li>" + "You may need to be administrator/root to install LaTeXDraw." + plus + "</li></ul></html>"));//$NON-NLS-1$//$NON-NLS-3$
			}
		}
	}
}
