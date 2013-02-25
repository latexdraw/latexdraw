package net.sf.latexdraw.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * This class define a filter for TeX files (*.tex)<br>
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
 * 01/20/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class TeXFilter extends FileFilter {
    /** The name of the extension of TeX file*/
    public final static String TEX_EXTENSION = ".tex"; //$NON-NLS-1$


	@Override
	public boolean accept(final File file) {
		return file!=null && (file.getName().endsWith(TEX_EXTENSION)|| file.isDirectory());
	}


	@Override
	public String getDescription() {
		return "*" + TEX_EXTENSION; //$NON-NLS-1$
	}
}
