package net.sf.latexdraw.ui.dialog;

import java.awt.Component;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import net.sf.latexdraw.filters.JPGFilter;
import net.sf.latexdraw.lang.LangTool;

/**
 * Define a JFileChooser that exports the drawing in a graphical format.<br>
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
 * 04/08/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ExportDialog extends JFileChooser {
	private static final long serialVersionUID = 1L;

	/** The slide that allows to change the compression rate. */
	protected JSlider compressionSlide;

	/** The panel that contains the compression slider and its label. */
	protected JPanel pCompression;


	/**
	 * Creates the dialogue box used to export drawings.
	 * @param path The path of a file or directory to focus on.
	 * @since 3.0
	 */
	public ExportDialog(final String path) {
		super(path);

		setApproveButtonText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.171")); //$NON-NLS-1$
		setAcceptAllFileFilterUsed(true);
		setMultiSelectionEnabled(false);
 		setFileSelectionMode(JFileChooser.FILES_ONLY);
		setDragEnabled(true);

 		pCompression = new JPanel();
 		pCompression.setLayout(new BoxLayout(pCompression, BoxLayout.X_AXIS));
 		pCompression.add(new JLabel(LangTool.INSTANCE.getStringDialogFrame("ExportDialog.0"))); //$NON-NLS-1$
 		compressionSlide = new JSlider(SwingConstants.VERTICAL, 0, 100, 20);
 		compressionSlide.setMajorTickSpacing(10);
 		compressionSlide.setPaintTicks(true);
 		compressionSlide.setPaintLabels(true);

 		pCompression.add(compressionSlide);

 		setAccessory(pCompression);
	}



	@Override
	public int showDialog(final Component parent, final String approveButtonText) {
		pCompression.setVisible(accept(new File(JPGFilter.JPG_EXTENSION)));

		return super.showDialog(parent, approveButtonText);
	}



	/**
	 * @return The compression rate if the filter accepts jpg files, else return -1.
	 * @since 1.9.2
	 */
	public int getCompressionRate() {
		return accept(new File(JPGFilter.JPG_EXTENSION)) ? compressionSlide.getValue() : -1;
	}
}
