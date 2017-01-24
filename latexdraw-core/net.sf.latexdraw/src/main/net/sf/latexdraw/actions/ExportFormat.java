/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.actions;

import javax.swing.filechooser.FileFilter;
import net.sf.latexdraw.filters.BMPFilter;
import net.sf.latexdraw.filters.EPSFilter;
import net.sf.latexdraw.filters.JPGFilter;
import net.sf.latexdraw.filters.PDFFilter;
import net.sf.latexdraw.filters.PNGFilter;
import net.sf.latexdraw.filters.TeXFilter;

/**
 * The enumeration defines the different formats managed to export drawing.
 * @author Arnaud Blouin
 */
public enum ExportFormat {
	/**
	 * The latex format.
	 */
	TEX {
		@Override
		public FileFilter getFilter() {
			return new TeXFilter();
		}

		@Override
		public String getFileExtension() {
			return TeXFilter.TEX_EXTENSION;
		}
	},
	/**
	 * The PDF format.
	 */
	PDF {
		@Override
		public FileFilter getFilter() {
			return new PDFFilter();
		}

		@Override
		public String getFileExtension() {
			return PDFFilter.PDF_EXTENSION;
		}
	},
	/**
	 * The latex format (using latex).
	 */
	EPS_LATEX {
		@Override
		public FileFilter getFilter() {
			return new EPSFilter();
		}

		@Override
		public String getFileExtension() {
			return EPSFilter.EPS_EXTENSION;
		}
	},
	/**
	 * The PDF format (using pdfcrop).
	 */
	PDF_CROP {
		@Override
		public FileFilter getFilter() {
			return new PDFFilter();
		}

		@Override
		public String getFileExtension() {
			return PDFFilter.PDF_EXTENSION;
		}
	},
	/**
	 * The BMP format.
	 */
	BMP {
		@Override
		public FileFilter getFilter() {
			return new BMPFilter();
		}

		@Override
		public String getFileExtension() {
			return BMPFilter.BMP_EXTENSION;
		}
	},
	/**
	 * The PNG format.
	 */
	PNG {
		@Override
		public FileFilter getFilter() {
			return new PNGFilter();
		}

		@Override
		public String getFileExtension() {
			return PNGFilter.PNG_EXTENSION;
		}
	},
	/**
	 * The JPG format.
	 */
	JPG {
		@Override
		public FileFilter getFilter() {
			return new JPGFilter();
		}

		@Override
		public String getFileExtension() {
			return JPGFilter.JPG_EXTENSION;
		}
	};

	/**
	 * @return The file filter corresponding to the format.
	 * @since 3.0
	 */
	public abstract FileFilter getFilter();

	/**
	 * @return The extension corresponding to the format.
	 * @since 3.0
	 */
	public abstract String getFileExtension();
}
