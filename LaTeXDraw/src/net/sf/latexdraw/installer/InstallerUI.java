package net.sf.latexdraw.installer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.sf.latexdraw.util.LPath;

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
public final class InstallerUI extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	/** The name of the OS. */
	private String os;

	/** The java jre */
	private String javaVersion;

	/** The listener of the installer. */
	private transient InstallerListener listener;

	/** The current position of the slide of the installer. */
	private int slidePosition;

	/** The first slide. */
	private JPanel panel1;

	/** The second slide. */
	private JPanel panel2;

	/** The third slide. */
	private JPanel panel3;

	/** The button "previous". */
	private JButton previousB;

	/** The button "next". */
	private JButton nextB;

	/** The field that defines the path where we have to install LaTeXDraw. */
	private JTextField pathInstall;

	/** The progress bar of the installation. */
	private JProgressBar progressBar;

	/** Used to stop the thread of the installation. */
	private boolean continueInstall;

	/** The thread that installs LaTeXDraw. */
	private transient Thread installThread;

	/** The label that can appear during the installation. */
	private JLabel labelProgress;

	/** The path where the jar file is placed. */
	private String pathJar;

	/** The maximum number of slides. */
	public static final int MAX_PANEL = 3;

	public static final String ACTION_NEXT = "next";//$NON-NLS-1$

	public static final String ACTION_PREVIOUS = "previous";//$NON-NLS-1$

	public static final String ACTION_CANCEL = "cancel";//$NON-NLS-1$

	public static final String ACTION_TERMINATE = "terminate";//$NON-NLS-1$

	public static final String LABEL_NEXT = "Next";

	public static final String LABEL_TERMINATE = "Terminate";

	public static final String ACTION_CHOOSE_FOLDER = "choose";//$NON-NLS-1$

	/** The dimension of a slide. */
	public static final Dimension PANEL_SIZE = new Dimension(650, 350);

	public static final String NAME_TEMPLATES_DIR = "templates";//$NON-NLS-1$

	public static final String NAME_CACHE_TEMPLATES_DIR = ".cache";//$NON-NLS-1$

	public static final String DATA_DIR = "data";//$NON-NLS-1$


	/**
	 * The main function: it creates an installer and launch it.
	 * @param argc Useless.
	 * @since 1.9.2
	 */
	public static void main(final String argc[]) {
		final InstallerUI installer  = new InstallerUI();
		installer.launch();
	}


	/**
	 * The default constructor.
	 */
	public InstallerUI() {
		super("LaTeXDraw installer");

		pathJar 		= LPath.INSTANCE.getPathJar();
		os 				= System.getProperty("os.name");//$NON-NLS-1$
		javaVersion 	= System.getProperty("java.version");//$NON-NLS-1$
		listener 		= new InstallerListener(this);
		slidePosition 	= 1;

		if(os.equalsIgnoreCase("linux") && System.getProperty("java.version").toLowerCase().compareTo("1.6")>=0)//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); }//$NON-NLS-1$
			catch(final UnsupportedLookAndFeelException e) { /* Dommage ;) */ }
			catch(final ClassNotFoundException e) { /* Dommage ;) */ }
			catch(final IllegalAccessException e) { /* Dommage ;) */ }
			catch(final InstantiationException e) { /* Dommage ;) */ }

		if(os.toLowerCase().contains("win")) //$NON-NLS-1$
			try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); }//$NON-NLS-1$
			catch(final UnsupportedLookAndFeelException e) { /* Dommage ;) */ }
			catch(final ClassNotFoundException e) { /* Dommage ;) */ }
			catch(final IllegalAccessException e) { /* Dommage ;) */ }
			catch(final InstantiationException e) { /* Dommage ;) */ }

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("res/LaTeXDrawIcon.png")));//$NON-NLS-1$
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		final JPanel ctrlPanel = createControlButtonsPanel();
		createStartPanel();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createHeaderPanel(), BorderLayout.NORTH);
		getContentPane().add(panel1, BorderLayout.CENTER);
		getContentPane().add(ctrlPanel, BorderLayout.SOUTH);

		previousB.setEnabled(false);
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		pack();
		setLocation(dim.width/2-getWidth()/2, dim.height/2-getHeight()/2);
		setVisible(false);
	}


	/**
	 * Creates the first slide.
	 * @since 1.9.2
	 */
	protected void createStartPanel() {
		panel1 = new JPanel();
		setPanelDimension(panel1);

		String plus;

		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

		if(javaVersion.startsWith("1.5")) { //$NON-NLS-1$
			final JTextArea textArea 	= new JTextArea();
			final JLabel label 			= new JLabel();
			final JLabel label2 		= new JLabel();
			final JLabel label3 		= new JLabel();

			textArea.setEditable(false);

			label.setText("You must have Java 6 installed.");
			label2.setText("Your current version of Java is " + javaVersion + "."); //$NON-NLS-2$
			label3.setText("You can download the last version of java here:");

			textArea.setText("http://www.java.com/fr/download/index.jsp"); //$NON-NLS-1$
			textArea.setMaximumSize(new Dimension(300,20));
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label2.setAlignmentX(Component.CENTER_ALIGNMENT);
			label3.setAlignmentX(Component.CENTER_ALIGNMENT);
			textArea.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel1.add(Box.createVerticalStrut(60));
			panel1.add(label);
			panel1.add(label2);
			panel1.add(label3);
			panel1.add(Box.createVerticalStrut(10));
			panel1.add(textArea);
			nextB.setEnabled(false);
		} else {
			if(os.toLowerCase().contains("vista"))//$NON-NLS-1$
				plus = "</li><br><li>Be careful with Vista! Files\".<br>You must have launched this installer via the script install_vista.vbs.";
			else
				plus = "";//$NON-NLS-1$

			panel1.add(new JLabel("<html><br><br><br><ul><li>"+"This installer will, create the necessary directories, place the templates in your profile<br>and place the latexdraw files in the chosen directory."+//$NON-NLS-1$
					"</li><br><li>"+"You may need to be administrator/root to install LaTeXDraw."+ plus+"</li></ul></html>"));//$NON-NLS-1$//$NON-NLS-3$
		}
	}



	/**
	 * Creates the second slide.
	 * @since 1.9.2
	 */
	protected void createPanelChoosePath() {
		final JPanel p1 = new JPanel();
		final JPanel p2 = new JPanel();

		panel2 = new JPanel();

		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));

		pathInstall = new JTextField();
		pathInstall.setEditable(false);
		pathInstall.setPreferredSize(new Dimension(250, 35));
		pathInstall.setMinimumSize(new Dimension(250, 35));
		pathInstall.setMaximumSize(new Dimension(250, 35));
		final JButton chooseB = new JButton("Choose folder");
		chooseB.setName(ACTION_CHOOSE_FOLDER);
		chooseB.setActionCommand(ACTION_CHOOSE_FOLDER);
		chooseB.addActionListener(listener);

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
		panel2.add(p1);
		setPanelDimension(panel2);

		if(os.equals("Linux"))//$NON-NLS-1$
			pathInstall.setText("/opt");//$NON-NLS-1$
		else
			if(os.toLowerCase().contains("windows")) {//$NON-NLS-1$
				File dir = new File("C:\\Program Files\\");//$NON-NLS-1$
				int cpt = 0;
				final int max = 10;

				while(!dir.exists() && cpt<max)
					dir = new File((char)('C'+cpt++)+":\\Program Files\\");//$NON-NLS-1$

				pathInstall.setText(dir.exists() ? dir.getPath() : "C:\\");//$NON-NLS-1$
			}
	}


	/**
	 * Creates the third slide.
	 * @since 1.9.2
	 */
	protected void createPanelInstall() {
		final JPanel p1 = new JPanel();
		final JPanel p2 = new JPanel();
		panel3 = new JPanel();

		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));

		progressBar = new JProgressBar(0, 100);
		progressBar.setMinimumSize(new Dimension(300, 30));
		progressBar.setPreferredSize(new Dimension(300, 30));
		labelProgress = new JLabel();
		p2.add(progressBar);
		p2.add(labelProgress);


		p2.setMaximumSize(new Dimension(350, 150));
		p2.setMinimumSize(new Dimension(350, 150));
		p2.setPreferredSize(new Dimension(350, 150));
		p1.setMaximumSize(new Dimension(350, 300));
		p1.setMinimumSize(new Dimension(350, 300));
		p1.setPreferredSize(new Dimension(350, 300));
		p1.add(p2);
		panel3.add(p1);

		nextB.setEnabled(false);
		previousB.setEnabled(false);

		setPanelDimension(panel3);
	}



	/**
	 * Set the default dimension of a slide to the given panel.
	 * @param p The given panel.
	 * @throws IllegalArgumentException If the given panel is null.
	 * @since 1.9.2
	 */
	protected void setPanelDimension(final JPanel p) {
		if(p==null)
			throw new IllegalArgumentException();

		p.setPreferredSize(PANEL_SIZE);
		p.setMinimumSize(PANEL_SIZE);
		p.setMaximumSize(PANEL_SIZE);
	}



	/**
	 * @return The panel containing the control buttons (next, previous, and so on).
	 * @since 1.9.2
	 */
	protected JPanel createControlButtonsPanel() {
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

		nextB = new JButton(LABEL_NEXT);
		nextB.setActionCommand(ACTION_NEXT);
		nextB.setName(ACTION_NEXT);
		nextB.addActionListener(listener);

		ctrlPanel.add(nextB);
		ctrlPanel.setBorder(new CompoundBorder(new TitledBorder(null, null, TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(0,0,0,0)));

		return ctrlPanel;
	}



	/**
	 * @return The top panel (the banner in fact).
	 * @since 1.9.2
	 */
	protected JPanel createHeaderPanel() {
		final JPanel header = new JPanel();

		header.add(new JLabel("<html><div align=\"center\"><font size=\"+1\" color=\"white\">"+"Assistant of installation"+"</font></div></html>"));//$NON-NLS-1$//$NON-NLS-3$
		header.setBackground(new Color(90, 100, 130));
		header.setBorder(new CompoundBorder(new TitledBorder(null, null, TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(0,0,0,0)));

		return header;
	}



	/**
	 * Launch the installer.
	 * @since 1.9.2
	 */
	public void launch() {
		setVisible(true);
	}



	/**
	 * Allows to go to the next slide.
	 * @since 1.9.2
	 */
	public void incrementSlidePosition() {
		if(slidePosition<MAX_PANEL) {
			slidePosition++;

			switch(slidePosition) {
				case 2:
					if(panel2==null)
						createPanelChoosePath();

					final File f = new File(pathInstall.getText());
					getContentPane().remove(panel1);
					getContentPane().add(panel2, BorderLayout.CENTER);
					previousB.setEnabled(true);
					nextB.setEnabled(f.exists() && f.isDirectory() && f.canRead());
					repaint();
					pack();
					break;

				case 3:
					if(panel3==null)
						createPanelInstall();

					getContentPane().remove(panel2);
					getContentPane().add(panel3, BorderLayout.CENTER);
					labelProgress.setText("");//$NON-NLS-1$
					nextB.setText(LABEL_TERMINATE);
					nextB.setActionCommand(ACTION_TERMINATE);
					nextB.setName(ACTION_TERMINATE);
					repaint();
					pack();

					installThread = new Thread(this);
					installThread.start();

					break;
			}
		}
	}



	/**
	 * Allows to go to the previous slide.
	 * @since 1.9.2
	 */
	public void decrementSlidePosition() {
		if(slidePosition>1) {
			slidePosition--;

			switch(slidePosition) {
				case 1:
					getContentPane().remove(panel2);
					getContentPane().add(panel1, BorderLayout.CENTER);
					previousB.setEnabled(false);
					repaint();
					pack();
					break;

				case 2:
					final File f = new File(pathInstall.getText());
					getContentPane().remove(panel3);
					getContentPane().add(panel2, BorderLayout.CENTER);
					nextB.setText(LABEL_NEXT);
					nextB.setActionCommand(ACTION_NEXT);
					nextB.setName(ACTION_NEXT);
					nextB.setEnabled(f.exists() && f.isDirectory() && f.canRead());
					repaint();
					pack();
					break;
			}
		}
	}



	/**
	 * @param path the pathInstall to set.
	 * @since 0.1
	 */
	public void setPathInstall(final String path) {
		pathInstall.setText(path);
		final File f = new File(pathInstall.getText());
		nextB.setEnabled(f.exists() && f.isDirectory() && f.canRead());
	}



	@Override
	public void run() {
		setContinueInstall(true);
		progressBar.setValue(progressBar.getMinimum());

		LPath.INSTANCE.checkInstallDirectories();
		LPath.INSTANCE.checkDirectories();
		progressBar.setValue(progressBar.getValue()+20);
		boolean okInstall = false;
		boolean okCopying = true;

		if(!isContinueInstall())
			return ;

		try {
			okCopying = okCopying && copyFiles(pathJar+File.separator+DATA_DIR+File.separator+NAME_TEMPLATES_DIR, LPath.PATH_TEMPLATES_SHARED, false);
			okCopying = okCopying && copyFiles(pathJar+File.separator+DATA_DIR+File.separator+NAME_CACHE_TEMPLATES_DIR, LPath.PATH_CACHE_SHARE_DIR, false);

			progressBar.setValue(progressBar.getValue()+20);

			if(!isContinueInstall())
				return ;

			if(!isContinueInstall())
				return ;

			final String path  = pathInstall.getText() + File.separator + "latexdraw";//$NON-NLS-1$
			final File mainDir = new File(path);

			if(mainDir.exists())
				okCopying = delete(mainDir);

			okCopying = mainDir.mkdir();

			okCopying = okCopying && mainDir.exists();
			okCopying = okCopying && copyFile(pathJar+File.separator+DATA_DIR+File.separator+"LaTeXDraw.jar", path, true);//$NON-NLS-1$
			okCopying = okCopying && copyFile(pathJar+File.separator+"license.txt", path, true);//$NON-NLS-1$
			okCopying = okCopying && copyFile(pathJar+File.separator+"release_note.txt", path, true);//$NON-NLS-1$
			okCopying = okCopying && copyFiles(pathJar+File.separator+DATA_DIR+File.separator+"lib", path+File.separator+"lib", true);//$NON-NLS-1$//$NON-NLS-2$
			okCopying = okCopying && copyFiles(pathJar+File.separator+DATA_DIR+File.separator+"help", path+File.separator+"help", true);//$NON-NLS-1$//$NON-NLS-2$
			progressBar.setValue(progressBar.getValue()+30);

			if(!isContinueInstall())
				return ;

			createProgramLauncher();
			progressBar.setValue(progressBar.getValue()+20);

			if(!isContinueInstall())
				return ;

			okInstall = okCopying;

		}catch(final FileNotFoundException e) { okInstall = false; }
		catch(final SecurityException e) { okInstall = false; }

		if(okInstall) {
			progressBar.setValue(progressBar.getMaximum());
			nextB.setEnabled(true);
			previousB.setEnabled(true);
			labelProgress.setText("<html><font color=\"green\">" + "Installation succeeded!" + "<br></font></html>");//$NON-NLS-1$//$NON-NLS-3$
		}
		else {
			getLabelProgress().setText("<html><font color=\"red\">" + "Installation failed!" + "<br>" + //$NON-NLS-1$//$NON-NLS-3$
									"Launch the installer as administrator." + "</font></html>");//$NON-NLS-2$
			previousB.setEnabled(true);
		}

		setContinueInstall(false);
	}



	/**
	 * Creates the program launcher for the OS.
	 * @return true if the creation succeeded.
	 * @since 1.9.2
	 */
	private boolean createProgramLauncher() {
		boolean ok;

		try {
			if(os.equalsIgnoreCase("linux")) { //$NON-NLS-1$
				final File f = new File("/usr/bin/latexdraw");//$NON-NLS-1$

				if(!f.exists()) {
					final FileOutputStream fos 	= new FileOutputStream(f);
					final Writer osw 			= new OutputStreamWriter(fos);

					try {
						osw.write("#! /bin/sh\n");//$NON-NLS-1$
						osw.write("java -jar "+getPathInstall().getText()+File.separator+"latexdraw"+File.separator+"LaTeXDraw.jar $@\n");//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
						ok = true;
					}catch(final IOException ex) { ok = false; }
					try { osw.flush(); } catch(final IOException ex) { ok = false; }
					try { fos.flush(); } catch(final IOException ex) { ok = false; }
					try { osw.close(); } catch(final IOException ex) { ok = false; }
					try { fos.close(); } catch(final IOException ex) { ok = false; }

					f.setReadable(true);
					f.setExecutable(true);
				}
				else ok = true;
			}
			else ok = true;
		}
		catch(final FileNotFoundException e) 	{ ok = false; }
		catch(final SecurityException e) 		{ ok = false; }

		return ok;
	}



	/**
	 * Copy a file.
	 * @param from The current path of the file to move.
	 * @param here The directory where we have to move the file.
	 * @param replace If set to true, the file will replace any file with the same name in "here".
	 * @return True if the move succeeded
	 * @throws FileNotFoundException If "from" is invalid.
	 * @since 1.9.2
	 */
	private boolean copyFile(final String from, final String here, final boolean replace) throws FileNotFoundException {
		final File source = new File(from);

		if(!source.exists() || !source.canRead())
			throw new FileNotFoundException();

		final File dest = new File(here + File.separator + source.getName());

		if(dest.exists() && replace)
			delete(dest);

		if(!dest.exists())
			if(source.isDirectory())
				copyFiles(from, here, replace);
			else
				return copy(source, dest);

		return true;
	}



	/**
	 * Copy a file into another.
	 * @param source The source file.
	 * @param destination The output file.
	 * @return True if no problem occurred.
	 * @exception IllegalArgumentException If source or destination is null.
	 */
	private static boolean copy(final File source, final File destination) {
		if(destination==null || source==null)
			throw new IllegalArgumentException();

		if(!source.canRead())
			return false;

        final FileInputStream sourceFile;
        final FileOutputStream destinationFile;
        boolean ok;

	    try {
	    	ok 			= destination.createNewFile();
            sourceFile 	= new FileInputStream(source);

           try {
	            destinationFile 	= new FileOutputStream(destination);
	            final byte buffer[]	= new byte[512*1024];
	            int nbLecture;

	            try {
		            while( (nbLecture = sourceFile.read(buffer)) != -1 )
		                destinationFile.write(buffer, 0, nbLecture);
	            }catch(final IOException ex) { ok = false; }

	            try { destinationFile.flush(); 	} catch(final IOException ex) { ok = false; }
	            try { sourceFile.close(); 		} catch(final IOException ex) { ok = false; }
	            try { destinationFile.close(); 	} catch(final IOException ex) { ok = false; }
            }catch(final FileNotFoundException e) {
            	try { sourceFile.close(); } catch(final IOException ex) { ok = false; }
            }catch(final SecurityException e) {
            	try { sourceFile.close(); } catch(final IOException ex) { ok = false; }
            }

            return true;
        }
	    catch(final IOException e) 		 { ok = false; }
	    catch(final SecurityException e) { ok = false; }

	    return ok;
	}


	/**
	 * Move a couple of files but never replace an existing file.
	 * @param from The directory where we have to find the files to move.
	 * @param into The destination directory.
	 * @param replace True : the file/directory will be replaced.
	 * @throws FileNotFoundException If a problem occurred with a source file.
	 * @since 1.9.2
	 */
	private boolean copyFiles(final String from, final String into, final boolean replace) throws FileNotFoundException {
		final File src  = new File(from);
		final File here = new File(into);
		boolean ok = true;

		if(!src.exists() || !src.isDirectory() || !src.canRead())
			throw new FileNotFoundException();

		final File[] files = src.listFiles();
		File dest;
		here.mkdir();

		for(File file : files) {
			dest = new File(into + File.separator + file.getName());

			if(replace)
				delete(dest);

			if(!dest.exists())
				if(file.isFile())
					ok = copy(file, dest) && ok;
				else {
					String path = file.getPath();
					path = path.substring(path.lastIndexOf(File.separator));
					ok = copyFiles(file.getPath(), into+path, replace) && ok;
				}
		}

		return ok;
	}



	/**
	 * Add progression to the progress bar.
	 * @param add The value to add.
	 * @since 1.9.2
	 */
	public void addProgressBar(final int add) {
		progressBar.setValue(progressBar.getValue()+add);
	}


	/**
	 * @return the continueInstall.
	 * @since 0.1
	 */
	public boolean isContinueInstall() {
		return continueInstall;
	}



	/**
	 * @param continueInstall the continueInstall to set.
	 * @since 0.1
	 */
	public void setContinueInstall(final boolean continueInstall) {
		this.continueInstall = continueInstall;
	}



	/**
	 * remove a file or directory even if it is not empty.
	 * @param f The file to remove
	 * @return True if the file/directory was removed.
	 * @since 1.9.2
	 */
	private static boolean delete(final File f) {
	  boolean status = true;

	  try {
		  File fileCur;
		  final String[] lstFile;
		  int idxFile;

		  lstFile = f.list();

		  if(lstFile==null) {
			  f.delete();
			  return true;
		  }

		  for(idxFile = 0; idxFile < lstFile.length && status; idxFile++) {
			  fileCur = new File(f, lstFile[idxFile]);

			  if(fileCur.isDirectory())
		         status = delete(fileCur);
			  else
		         status = fileCur.delete();
		  }

		  f.delete();
	  }
	  catch(final SecurityException se)  { return false; }

	  return status;
	}


	/**
	 * @return the labelProgress.
	 * @since 2.0.0
	 */
	public JLabel getLabelProgress() {
		return labelProgress;
	}


	/**
	 * @return the pathInstall.
	 * @since 2.0.0
	 */
	public JTextField getPathInstall() {
		return pathInstall;
	}
}
