package net.sf.latexdraw.installer;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LSystem;

public class InstallSlide extends Slide implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final String NAME_TEMPLATES_DIR = "templates";//$NON-NLS-1$

	private static final String NAME_CACHE_TEMPLATES_DIR = ".cache";//$NON-NLS-1$

	private static final String DATA_DIR = "data";//$NON-NLS-1$
	
	/** The path where the jar file is placed. */
	private static String PATH_JAR = LPath.INSTANCE.getPathJar();
	
	/** The progress bar of the installation. */
	private JProgressBar progressBar;
	
	/** The label that can appear during the installation. */
	private JLabel labelProgress;
	
	/** The thread that installs LaTeXDraw. */
	private Thread installThread;
	
	/** Used to stop the thread of the installation. */
	private boolean continueInstall;
	

	protected InstallSlide(final Slide prev, final Slide next, final Installer installer) {
		super(prev, next, installer);
	}
	
	
	
	@Override
	protected void install() {
		super.install();
		
		installThread = new Thread(this);
		installThread.start();
	}

	
	@Override
	protected void init() {
		super.init();
		
		final JPanel p1 = new JPanel();
		final JPanel p2 = new JPanel();

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
		add(p1);

		installer.nextB.setEnabled(false);
		installer.previousB.setEnabled(false);

		setPanelDimension();
	}
	

	/**
	 * Creates the program launcher for the OS.
	 * @return true if the creation succeeded.
	 * @since 1.9.2
	 */
	private boolean createProgramLauncher() {
		boolean ok;

		try {
			if(LSystem.INSTANCE.isLinux()) {
				final File f = new File("/usr/bin/latexdraw");//$NON-NLS-1$

				if(!f.exists()) {
					final FileOutputStream fos 	= new FileOutputStream(f);
					final Writer osw 			= new OutputStreamWriter(fos);

					try {
						osw.write("#! /bin/sh\n");//$NON-NLS-1$
						osw.write("java -jar "+installer.chooseSlide.pathInstall.getText()+File.separator+"latexdraw"+File.separator+"LaTeXDraw.jar $@\n");//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
						ok = true;
					}catch(final IOException ex) { ok = false; }
					try { osw.flush(); } catch(final IOException ex) { ok = false; }
					try { fos.flush(); } catch(final IOException ex) { ok = false; }
					try { osw.close(); } catch(final IOException ex) { ok = false; }
					try { fos.close(); } catch(final IOException ex) { ok = false; }

					f.setReadable(true);
					f.setExecutable(true, false);
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

        FileInputStream sourceFile = null;
        FileOutputStream destinationFile = null;
        boolean ok;

	    try {
	    	ok 			= destination.createNewFile();
            sourceFile 	= new FileInputStream(source);
            destinationFile 	= new FileOutputStream(destination);
            final byte buffer[]	= new byte[512*1024];
            int nbLecture = sourceFile.read(buffer);
            
            while(nbLecture!=-1) {
            	destinationFile.write(buffer, 0, nbLecture);
            	nbLecture = sourceFile.read(buffer);
            }
	    }catch(final Exception ex) { ok = false; }

	    try { if(destinationFile!=null) destinationFile.close(); } catch(final IOException ex) { ok = false; }
	    try { if(sourceFile!=null) sourceFile.close(); } catch(final IOException ex) { ok = false; }
	    
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
	
	
	protected boolean isContinueInstall() {
		return continueInstall;
	}


	protected void setContinueInstall(final boolean continueInstall) {
		this.continueInstall = continueInstall;
	}

	
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
			okCopying = okCopying && copyFiles(PATH_JAR+File.separator+DATA_DIR+File.separator+NAME_TEMPLATES_DIR, LPath.PATH_TEMPLATES_SHARED, false);
			okCopying = okCopying && copyFiles(PATH_JAR+File.separator+DATA_DIR+File.separator+NAME_CACHE_TEMPLATES_DIR, LPath.PATH_CACHE_SHARE_DIR, false);
			if(LSystem.INSTANCE.isLinux()){
				new File(LPath.PATH_SHARED+"/images/app/").mkdirs();
				okCopying = okCopying && copyFile(PATH_JAR+File.separator+DATA_DIR+File.separator+"gnome"+File.separator+"latexdraw.png", 
						LPath.PATH_SHARED+"/images/app/", false);
				okCopying = okCopying && copyFile(PATH_JAR+File.separator+DATA_DIR+File.separator+"gnome"+File.separator+"latexdraw.desktop", 
						"/usr/share/applications/", false);
			}

			progressBar.setValue(progressBar.getValue()+20);

			if(!isContinueInstall())
				return ;

			if(!isContinueInstall())
				return ;

			final String path  = installer.chooseSlide.pathInstall.getText() + File.separator + "latexdraw";//$NON-NLS-1$
			final File mainDir = new File(path);

			if(mainDir.exists())
				okCopying = delete(mainDir);

			okCopying = mainDir.mkdir();

			okCopying = okCopying && mainDir.exists();
			okCopying = okCopying && copyFile(PATH_JAR+File.separator+DATA_DIR+File.separator+"LaTeXDraw.jar", path, true);//$NON-NLS-1$
			okCopying = okCopying && copyFile(PATH_JAR+File.separator+"license.txt", path, true);//$NON-NLS-1$
			okCopying = okCopying && copyFile(PATH_JAR+File.separator+"release_note.txt", path, true);//$NON-NLS-1$
			okCopying = okCopying && copyFiles(PATH_JAR+File.separator+DATA_DIR+File.separator+"lib", path+File.separator+"lib", true);//$NON-NLS-1$//$NON-NLS-2$
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
			installer.nextB.setEnabled(true);
			installer.previousB.setEnabled(true);
			labelProgress.setText("<html><font color=\"green\">" + "Installation succeeded!" + "<br></font></html>");//$NON-NLS-1$//$NON-NLS-3$
		}
		else {
			labelProgress.setText("<html><font color=\"red\">" + "Installation failed!" + "<br>" + //$NON-NLS-1$//$NON-NLS-3$
									"Launch the installer as administrator." + "</font></html>");//$NON-NLS-2$
			installer.previousB.setEnabled(true);
		}

		setContinueInstall(false);
	}
}

