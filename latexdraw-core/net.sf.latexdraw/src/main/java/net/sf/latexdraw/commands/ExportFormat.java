/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.commands;

import javafx.stage.FileChooser;

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
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("TeX", "*." + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".tex"; //NON-NLS
		}
	}, PDF {
		@Override
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("PDF", "*" + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".pdf"; //NON-NLS
		}
	}, EPS_LATEX {
		@Override
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("EPS", "*" + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".eps"; //NON-NLS
		}
	}, PDF_CROP {
		@Override
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("PDF", "*" + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".pdf"; //NON-NLS
		}
	}, BMP {
		@Override
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("BMP", "*" + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".bmp"; //NON-NLS
		}
	}, PNG {
		@Override
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("PNG", "*" + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".png"; //NON-NLS
		}
	}, JPG {
		@Override
		public FileChooser.ExtensionFilter getFilter() {
			return new FileChooser.ExtensionFilter("JPG", "*" + getFileExtension()); //NON-NLS
		}

		@Override
		public String getFileExtension() {
			return ".jpg"; //NON-NLS
		}
	};

	/**
	 * @return The file filter corresponding to the format.
	 * @since 3.0
	 */
	public abstract FileChooser.ExtensionFilter getFilter();

	/**
	 * @return The extension corresponding to the format.
	 * @since 3.0
	 */
	public abstract String getFileExtension();
}
