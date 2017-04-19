/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.malai.action.ActionImpl;

/**
 * This action allows to export a drawing in different formats.
 * @author Arnaud Blouin
 */
public class Export extends ActionImpl {
	/** The format with which the drawing must be exported. */
	private ExportFormat format;

	/** The canvas that contains views. */
	private Canvas canvas;

	/** Defines if the shapes have been successfully exported. */
	private boolean exported;

	/** The dialogue chooser used to select the targeted file. */
	private FileChooser dialogueBox;

	/** The PST generator to use. */
	private PSTCodeGenerator pstGen;

	private File outputFile;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public Export() {
		super();
		exported = false;
	}

	@Override
	public void flush() {
		super.flush();
		canvas = null;
		format = null;
		dialogueBox = null;
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		// Showing the dialog.
		File file = dialogueBox.showSaveDialog(canvas.getScene().getWindow());
		exported = true;

		// Analysing the result of the dialog.
		if(file == null) {
			exported = false;
		}else {
			if(!file.getName().toLowerCase().endsWith(format.getFileExtension().toLowerCase())) {
				file = new File(file.getPath() + format.getFileExtension());
			}
		}

		if(exported) {
			exported = export(file);
			if(exported) {
				outputFile = file;
			}
		}
	}

	private boolean export(final File file) {
		switch(format) {
			case BMP:
				return exportAsPicture(file, "bmp", false);
			case EPS_LATEX:
				return exportAsEPS(file);
			case JPG:
				return exportAsPicture(file, "jpg", false);
			case PDF:
				return exportAsPDF(file);
			case PDF_CROP:
				return exportAsPDF(file);
			case PNG:
				return exportAsPicture(file, "png", true);
			case TEX:
				return exportAsPST(file);
		}
		return false;
	}


	@Override
	public boolean canDo() {
		return canvas != null && format != null && dialogueBox != null && (format == ExportFormat.BMP || format == ExportFormat.JPG ||
			format == ExportFormat.PNG || pstGen != null);
	}


	@Override
	public boolean hadEffect() {
		return super.hadEffect() && exported;
	}


	/**
	 * Exports the drawing as a PNG picture.
	 * @param file The targeted location.
	 * @return true if the picture was well created.
	 */
	private boolean exportAsPicture(final File file, final String format, final boolean alpha) {
		BufferedImage rendImage = createRenderedImage();
		boolean success = false;

		if(!alpha) {
			final BufferedImage copy = new BufferedImage(rendImage.getWidth(), rendImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = copy.createGraphics();
			g2d.drawImage(rendImage, 0, 0, null);
			g2d.dispose();
			rendImage.flush();
			rendImage = copy;
		}

		try {
			ImageIO.write(rendImage, format, file);  //$NON-NLS-1$
			success = true;
		}catch(final IOException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
		rendImage.flush();
		return success;
	}


	/**
	 * Creates a ps document of the given views (compiled using latex).
	 * @param file The targeted location.
	 * @return True: the file has been created.
	 * @since 3.0
	 */
	private boolean exportAsEPS(final File file) {
		File psFile;

		try {
			psFile = pstGen.createEPSFile(file.getAbsolutePath()).orElse(null);
		}catch(final Exception e) {
			BadaboomCollector.INSTANCE.add(e);
			psFile = null;
		}

		return psFile != null && psFile.exists();
	}


	/**
	 * Creates a pdf document of the given views (compiled using latex).
	 * @param file The targeted location.
	 * @return True: the file has been created.
	 * @since 3.0
	 */
	private boolean exportAsPDF(final File file) {
		File pdfFile;

		try {
			pdfFile = pstGen.createPDFFile(file.getAbsolutePath(), format == ExportFormat.PDF_CROP).orElse(null);
		}catch(final Exception e) {
			BadaboomCollector.INSTANCE.add(e);
			pdfFile = null;
		}

		return pdfFile != null && pdfFile.exists();
	}


	/**
	 * Exports the drawing as a PST document.
	 * @param file The targeted location.
	 * @return true if the PST document was been successfully created.
	 */
	private boolean exportAsPST(final File file) {
		boolean ok;

		try {
			try(final FileWriter fw = new FileWriter(file);
				final BufferedWriter bw = new BufferedWriter(fw);
				final PrintWriter out = new PrintWriter(bw)) {
				out.println(pstGen.getDrawingCode());
				ok = true;
			}
		}catch(final IOException e) {
			BadaboomCollector.INSTANCE.add(e);
			ok = false;
		}
		return ok;
	}


	/**
	 * @return A writable image that contains given views (not null).
	 */
	private BufferedImage createRenderedImage() {
		final Group views = canvas.getViews();
		final Bounds bounds = views.getBoundsInParent();
		final double scale = 3d;
		final WritableImage img = new WritableImage((int) (bounds.getWidth() * scale), (int) (bounds.getHeight() * scale));
		final SnapshotParameters snapshotParameters = new SnapshotParameters();

		snapshotParameters.setFill(Color.WHITE);
		snapshotParameters.setTransform(new Scale(scale, scale));
		views.snapshot(snapshotParameters, img);

		while(img.isBackgroundLoading()) {
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				BadaboomCollector.INSTANCE.add(e);
			}
		}

		return SwingFXUtils.fromFXImage(img, null);
	}

	/**
	 * @param dialogBox The file chooser to set.
	 * @since 3.0
	 */
	public void setDialogueBox(final FileChooser dialogBox) {
		dialogueBox = dialogBox;
	}

	/**
	 * @param expFormat The expFormat to set.
	 * @since 3.0
	 */
	public void setFormat(final ExportFormat expFormat) {
		format = expFormat;
	}

	/**
	 * @param theCanvas The theCanvas to set.
	 * @since 3.0
	 */
	public void setCanvas(final Canvas theCanvas) {
		canvas = theCanvas;
	}

	/**
	 * @param gen The PST generator to use for latex, ps, or pdf exports.
	 * @since 3.0
	 */
	public void setPstGen(final PSTCodeGenerator gen) {
		pstGen = gen;
	}

	/**
	 * @return The output file produced during the export.
	 */
	public File getOutputFile() {
		return outputFile;
	}
}
