package net.sf.latexdraw.util;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

/**
 * Defines some routines that provides information about the operating system currently used.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 05/14/10<br>
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 * @version 3.0
 */
public final class LSystem {
	/**
	 * The different operating systems managed.
	 */
	public static enum OperatingSystem {
		VISTA, XP, SEVEN,
		MAC_OS_X {
			@Override
			public String getLatexBinPath() {
				return "/usr/texbin/latex";
			}

			@Override
			public String getDvipsBinPath() {
				return "/usr/texbin/dvips";
			}

			@Override
			public String getPs2pdfBinPath() {
				return "/usr/local/bin/ps2pdf";
			}

			@Override
			public String getPdfcropBinPath() {
				return "pdfcrop";
			}
		},
		LINUX;

		/**
		 * @return The path where is the latex binary.
		 * @since 3.0
		 */
		public String getLatexBinPath() {
			return "latex";
		}

		/**
		 * @return The path where is the dvips binary.
		 * @since 3.0
		 */
		public String getDvipsBinPath() {
			return "dvips";
		}

		/**
		 * @return The path where is the ps2pdf binary.
		 * @since 3.0
		 */
		public String getPs2pdfBinPath() {
			return "ps2pdf";
		}

		/**
		 * @return The path where is the pdfcrop binary.
		 * @since 3.0
		 */
		public String getPdfcropBinPath() {
			return "pdfcrop";
		}
	}


	/** The singleton. */
	public static final LSystem INSTANCE = new LSystem();


	/**
	 * Creates the singleton.
	 */
	private LSystem() {
		super();
	}


	/**
	 * @return True: the operating system currently used is Windows.
	 * @since 3.0
	 */
	public boolean isWindows() {
		return isSeven() || isVista() || isXP();
	}


	/**
	 * @return True: the operating system currently used is Vista.
	 * @since 3.0
	 */
	public boolean isVista() {
		return getSystem()==OperatingSystem.VISTA;
	}


	/**
	 * @return True: the operating system currently used is XP.
	 * @since 3.0
	 */
	public boolean isXP() {
		return getSystem()==OperatingSystem.XP;
	}


	/**
	 * @return True: the operating system currently used is Seven.
	 * @since 3.0
	 */
	public boolean isSeven() {
		return getSystem()==OperatingSystem.SEVEN;
	}


	/**
	 * @return True: the operating system currently used is Linux.
	 * @since 3.0
	 */
	public boolean isLinux() {
		return getSystem()==OperatingSystem.LINUX;
	}


	/**
	 * @return True: the operating system currently used is Mac OS X.
	 * @since 3.0
	 */
	public boolean isMacOSX() {
		return getSystem()==OperatingSystem.MAC_OS_X;
	}


	/**
	 * @return The control modifier used by the currently used operating system.
	 * @since 3.0
	 */
	public int getControlKey() {
		if(LSystem.INSTANCE.isMacOSX())
			return KeyEvent.VK_META;
		return KeyEvent.VK_CONTROL;
	}


	/**
	 * @return The name of the operating system currently used.
	 * @since 3.0
	 */
	public OperatingSystem getSystem() {
		final String os = System.getProperty("os.name"); //$NON-NLS-1$

		if(os.equalsIgnoreCase("linux")) //$NON-NLS-1$
			return OperatingSystem.LINUX;

		if(os.equalsIgnoreCase("windows 7")) //$NON-NLS-1$
			return OperatingSystem.SEVEN;

		if(os.equalsIgnoreCase("windows vista")) //$NON-NLS-1$
			return OperatingSystem.VISTA;

		if(os.equalsIgnoreCase("windows xp") || os.contains("win")) //$NON-NLS-1$ //$NON-NLS-2$
			return OperatingSystem.XP;

		if(os.equalsIgnoreCase("mac os x")) //$NON-NLS-1$
			return OperatingSystem.MAC_OS_X;

		return null;
	}


	/**
	 * @return Returns the dimension of the first available screen. If in dual screen mode,
	 * the first screen is used. If no screen is available (Oo), the dimension (0,0) is returned.
	 * @since 3.0
	 */
	public Dimension getScreenDimension() {
     	final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	final GraphicsDevice[] gs = ge.getScreenDevices();

    	if(gs.length>0) {
    		final DisplayMode mode = gs[0].getDisplayMode();
    		return new Dimension(mode.getWidth(), mode.getHeight());
    	}
    	return new Dimension();
	}
}
