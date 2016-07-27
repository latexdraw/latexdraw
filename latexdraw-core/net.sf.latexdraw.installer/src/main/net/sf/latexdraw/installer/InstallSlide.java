package net.sf.latexdraw.installer;

import net.sf.latexdraw.util.InstallerLog;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LSystem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.logging.Level;

public class InstallSlide extends Slide implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final String NAME_TEMPLATES_DIR = "templates";//$NON-NLS-1$

	private static final String NAME_CACHE_TEMPLATES_DIR = ".cache";//$NON-NLS-1$

	private static final String DATA_DIR = "data";//$NON-NLS-1$
	
	public static final String EOL = System.getProperty("line.separator"); //$NON-NLS-1$
	
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
	private boolean createProgramLauncher(final String path) {
		boolean ok;

		try {
			if(LSystem.INSTANCE.isLinux()) {
				final File f = new File("/usr/bin/latexdraw");//$NON-NLS-1$

				InstallerLog.getLogger().log(Level.SEVERE, f + " exists? " + f.exists());
				if(!f.exists()) {
					final FileOutputStream fos 	= new FileOutputStream(f);
					final Writer osw 			= new OutputStreamWriter(fos);

					try {
						osw.write("#! /bin/sh\n");//$NON-NLS-1$
						osw.write("java -jar "+installer.chooseSlide.pathInstall.getText()+File.separator+"latexdraw"+File.separator+"LaTeXDraw.jar $@\n");//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
						ok = true;
					}catch(final IOException ex) {
						ok = false;
						InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
					}
					try { osw.flush(); } catch(final IOException ex) {
						ok = false;
						InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
					}
					try { fos.flush(); } catch(final IOException ex) {
						ok = false;
						InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
					}
					try { osw.close(); } catch(final IOException ex) {
						ok = false;
						InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
					}
					try { fos.close(); } catch(final IOException ex) {
						ok = false;
						InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
					}

					InstallerLog.getLogger().log(Level.INFO, "sh file created? " + ok);

					if(!f.setReadable(true))
						InstallerLog.getLogger().log(Level.SEVERE, "Failed to set sh file readable");
					if(!f.setExecutable(true, false))
						InstallerLog.getLogger().log(Level.SEVERE, "Failed to set sh file executable");
				}
				else ok = true;
			}
			else if(LSystem.INSTANCE.isWindows()) {
				final File f = new File(PATH_JAR+File.separator+"createShortcut.vbs");
				final FileOutputStream fos 	= new FileOutputStream(f);
				final Writer osw 			= new OutputStreamWriter(fos);

				InstallerLog.getLogger().log(Level.SEVERE, f + " exists? " + f.exists());

				try {
					osw.write("Set shell = WScript.CreateObject(\"WScript.Shell\")");
					osw.write(EOL);
					osw.write("strProg = shell.SpecialFolders(\"Programs\")");
					osw.write(EOL);
					osw.write("Set link= shell.CreateShortcut(strProg+\"\\LaTeXDraw.LNK\")");
					osw.write(EOL);
					osw.write("link.TargetPath = \"");
					osw.write(path);
					osw.write("\\LaTeXDraw.jar\"");
					osw.write(EOL);
					osw.write("link.Save");
					osw.write(EOL);
					ok = true;
				}catch(final IOException ex) {
					ok = false;
					InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
				}
				try { osw.flush(); } catch(final IOException ex) {
					ok = false;
					InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
				}
				try { fos.flush(); } catch(final IOException ex) {
					ok = false;
					InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
				}
				try { osw.close(); } catch(final IOException ex) {
					ok = false;
					InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
				}
				try { fos.close(); } catch(final IOException ex) {
					ok = false;
					InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
				}

				if(!f.setReadable(true))
					InstallerLog.getLogger().log(Level.SEVERE, "Failed to set vbs file readable");
				if(!f.setExecutable(true, false))
					InstallerLog.getLogger().log(Level.SEVERE, "Failed to set vbs file executable");
				ok = true;
				try {
					Runtime.getRuntime().exec(new String[]{"wscript", f.getPath()});
				}catch(IOException ex) {
					ex.printStackTrace();
					InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
				}
			}
			else ok = true;
		}
		catch(final FileNotFoundException ex) {
			ok = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
		}
		catch(final SecurityException ex) {
			ok = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
		}

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
	    }catch(final Exception ex) {
	    	ok = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
	    }

	    try { if(destinationFile!=null) destinationFile.close(); }
	    catch(final IOException ex) {
	    	ok = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
	    }
	    try { if(sourceFile!=null) sourceFile.close(); }
	    catch(final IOException ex) {
	    	ok = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
	    }
	    
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
			throw new FileNotFoundException(from + " exists? " + src.exists() + " isDir? " + src.isDirectory() + " canRead? " + src.canRead());

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
			okCopying = copyFiles(PATH_JAR+File.separator+DATA_DIR+File.separator+NAME_TEMPLATES_DIR, LPath.PATH_TEMPLATES_SHARED, false);
			InstallerLog.getLogger().log(Level.INFO, "Templates copied? " + okCopying);
			okCopying = okCopying && copyFiles(PATH_JAR+File.separator+DATA_DIR+File.separator+NAME_CACHE_TEMPLATES_DIR, LPath.PATH_CACHE_SHARE_DIR, false);
			InstallerLog.getLogger().log(Level.INFO, "Cache copied? " + okCopying);

			if(LSystem.INSTANCE.isLinux()){
				InstallerLog.getLogger().log(Level.INFO, "Installing on Linux");
				InstallerLog.getLogger().log(Level.INFO, LPath.PATH_SHARED+"/images/app/ created? " + new File(LPath.PATH_SHARED+"/images/app/").mkdirs());
				okCopying = okCopying && copyFile(PATH_JAR+File.separator+DATA_DIR+File.separator+"gnome"+File.separator+"latexdraw.png", 
						LPath.PATH_SHARED+"/images/app/", false);
				InstallerLog.getLogger().log(Level.INFO, "png file copied? " + okCopying);
				okCopying = okCopying && copyFile(PATH_JAR+File.separator+DATA_DIR+File.separator+"gnome"+File.separator+"latexdraw.desktop", 
						"/usr/share/applications/", false);
				InstallerLog.getLogger().log(Level.INFO, "desktop file copied? " + okCopying);
			}

			progressBar.setValue(progressBar.getValue()+20);

			if(!isContinueInstall())
				return ;

			final String path  = installer.chooseSlide.pathInstall.getText() + File.separator + "latexdraw";//$NON-NLS-1$
			final File mainDir = new File(path);
			InstallerLog.getLogger().log(Level.INFO, "Installation path: " + path + ". Exists? " + mainDir.exists());

			if(mainDir.exists()) {
				InstallerLog.getLogger().log(Level.INFO, mainDir + " already exists.");
				okCopying = delete(mainDir);
				InstallerLog.getLogger().log(Level.INFO, mainDir + " removed? " + okCopying);
			}

			okCopying = mainDir.mkdir();
			InstallerLog.getLogger().log(Level.INFO, mainDir + " created? " + okCopying);

			okCopying = okCopying && mainDir.exists();
			InstallerLog.getLogger().log(Level.INFO, mainDir + " exists? " + mainDir.exists());

			okCopying = okCopying && copyFile(PATH_JAR+File.separator+DATA_DIR+File.separator+"LaTeXDraw.jar", path, true);//$NON-NLS-1$
			InstallerLog.getLogger().log(Level.INFO, mainDir + " jar file copied? " + okCopying);

			okCopying = okCopying && copyFile(PATH_JAR+File.separator+"license.txt", path, true);//$NON-NLS-1$
			InstallerLog.getLogger().log(Level.INFO, mainDir + " license file copied? " + okCopying);

			okCopying = okCopying && copyFile(PATH_JAR+File.separator+"release_note.txt", path, true);//$NON-NLS-1$
			InstallerLog.getLogger().log(Level.INFO, mainDir + " release_note file copied? " + okCopying);

			okCopying = okCopying && copyFiles(PATH_JAR+File.separator+DATA_DIR+File.separator+"lib", path+File.separator+"lib", true);//$NON-NLS-1$//$NON-NLS-2$
			InstallerLog.getLogger().log(Level.INFO, PATH_JAR+File.separator+DATA_DIR+File.separator+"lib" + " libs copied? " +
				okCopying + " in " + path+File.separator+"lib (exists? " + (new File(path+File.separator+"lib")).exists() + ")");
			progressBar.setValue(progressBar.getValue()+30);

			if(!isContinueInstall())
				return ;

			createProgramLauncher(path);
			progressBar.setValue(progressBar.getValue()+20);

			if(!isContinueInstall())
				return ;

			okInstall = okCopying;

		}catch(final FileNotFoundException ex) {
			okInstall = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
		}
		catch(final SecurityException ex) {
			okInstall = false;
			InstallerLog.getLogger().log(Level.SEVERE, ex.toString(), ex);
		}

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

