package net.sf.latexdraw.filters;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * This class defines a filter for bmp files (*.bmp)
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 * 05/24/10
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class BMPFilter extends FileFilter {
    /** The name of the extension of bmp file*/
    public static final String BMP_EXTENSION = ".bmp";//$NON-NLS-1$


	@Override
	public boolean accept(final File file) {
		return file!=null && (file.getName().endsWith(BMP_EXTENSION)|| file.isDirectory());
	}


	@Override
	public String getDescription() {
		return "*" + BMP_EXTENSION; //$NON-NLS-1$
	}
}
