package net.sf.latexdraw.installer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.latexdraw.util.LSystem;

public class ChooseDirSlide extends Slide {
	private static final long serialVersionUID = 1L;
	
	protected static final String ACTION_CHOOSE_FOLDER = "choose";//$NON-NLS-1$
	
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
		chooseB.setName(ACTION_CHOOSE_FOLDER);
		chooseB.setActionCommand(ACTION_CHOOSE_FOLDER);
		chooseB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final Object obj = e.getSource();

				if(obj instanceof JButton && ACTION_CHOOSE_FOLDER.equals(((JButton)obj).getActionCommand())) {
					if(fileChooser.showDialog(installer, "Select") == JFileChooser.APPROVE_OPTION)
						pathInstall.setText(fileChooser.getSelectedFile().getPath());
					return ;
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

		if(LSystem.INSTANCE.isLinux())
			pathInstall.setText("/opt");//$NON-NLS-1$
		else
			if(LSystem.INSTANCE.isWindows()) {
				File dir = new File("C:\\Program Files\\");//$NON-NLS-1$
				int cpt = 0;
				final int max = 10;

				while(!dir.exists() && cpt<max)
					dir = new File((char)('C'+cpt++)+":\\Program Files\\");//$NON-NLS-1$

				pathInstall.setText(dir.exists() ? dir.getPath() : "C:\\");//$NON-NLS-1$
			}
	}
	
	
	
	/**
	 * @param path the pathInstall to set.
	 */
	protected void setPathInstall(final String path) {
		pathInstall.setText(path);
		final File f = new File(pathInstall.getText());
		installer.nextB.setEnabled(f.exists() && f.isDirectory() && f.canRead());
	}
}
