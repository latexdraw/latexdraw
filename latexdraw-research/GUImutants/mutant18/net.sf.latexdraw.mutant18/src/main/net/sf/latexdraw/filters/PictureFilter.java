package net.sf.latexdraw.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * This class defines a filter for pictures.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
 * 20/01/12<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PictureFilter extends FileFilter {
	@Override
	public boolean accept(final File file) {
		boolean accept;
		
		if(file==null)
			accept = false;
		else {
			final String name = file.getName();
			accept = file.isDirectory() || name.endsWith(PNGFilter.PNG_EXTENSION) || name.endsWith(BMPFilter.BMP_EXTENSION) || name.endsWith(GIFFilter.GIF_EXTENSION);
		}
		
		return accept;
	}


	@Override
	public String getDescription() {
		return "Picture";
	}
}
