/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import io.github.interacto.command.CommandImpl;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * This command allows to export a drawing in different formats.
 * @author Arnaud Blouin
 */
public class Export extends CommandImpl {
	/** The format with which the drawing must be exported. */
	private final @NotNull ExportFormat format;

	/** The canvas that contains views. */
	private final @NotNull Canvas canvas;

	/** Defines if the shapes have been successfully exported. */
	private boolean exported;

	/** The dialogue chooser used to select the targeted file. */
	private final @NotNull FileChooser dialogueBox;

	/** The PST generator to use. */
	private final @NotNull PSTCodeGenerator pstGen;

	/**
	 * Creates the command.
	 */
	public Export(final @NotNull Canvas canvas, final @NotNull PSTCodeGenerator pstGen, final @NotNull ExportFormat format,
				final @NotNull FileChooser dialogueBox) {
		super();
		this.canvas = canvas;
		this.pstGen = pstGen;
		this.format = format;
		this.dialogueBox = dialogueBox;
		exported = false;
	}

	@Override
	protected void doCmdBody() {
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
		}
	}

	private boolean export(final @NotNull File file) {
		switch(format) {
			case BMP:
				return exportAsPicture(file, "bmp", false); //NON-NLS
			case EPS_LATEX:
				return exportAsEPS(file);
			case JPG:
				return exportAsPicture(file, "jpg", false); //NON-NLS
			case PDF:
				return exportAsPDF(file);
			case PDF_CROP:
				return exportAsPDF(file);
			case PNG:
				return exportAsPicture(file, "png", true); //NON-NLS
			case TEX:
				return exportAsPST(file);
		}
		return false;
	}


	@Override
	public boolean hadEffect() {
		return exported;
	}


	/**
	 * Exports the drawing as a PNG picture.
	 * @param file The targeted location.
	 * @return true if the picture was well created.
	 */
	private boolean exportAsPicture(final @NotNull File file, final @NotNull String format, final boolean alpha) {
		BufferedImage rendImage = createRenderedImage();
		boolean success = false;

		if(!alpha) {
			final BufferedImage copy = new BufferedImage(rendImage.getWidth(), rendImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			final Graphics2D g2d = copy.createGraphics();
			g2d.drawImage(rendImage, 0, 0, null);
			g2d.dispose();
			rendImage.flush();
			rendImage = copy;
		}

		try {
			ImageIO.write(rendImage, format, file);
			success = true;
		}catch(final IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
		rendImage.flush();
		return success;
	}


	/**
	 * Creates a ps document of the given views (compiled using latex).
	 * @param file The targeted location.
	 * @return True: the file has been created.
	 */
	private boolean exportAsEPS(final @NotNull File file) {
		File psFile;

		try {
			psFile = pstGen.createEPSFile(file.getAbsolutePath()).orElse(null);
		}catch(final @NotNull SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			psFile = null;
		}

		return psFile != null && psFile.exists();
	}


	/**
	 * Creates a pdf document of the given views (compiled using latex).
	 * @param file The targeted location.
	 * @return True: the file has been created.
	 */
	private boolean exportAsPDF(final @NotNull File file) {
		File pdfFile;

		try {
			pdfFile = pstGen.createPDFFile(file.getAbsolutePath(), format == ExportFormat.PDF_CROP).orElse(null);
		}catch(final @NotNull SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			pdfFile = null;
		}

		return pdfFile != null && pdfFile.exists();
	}


	/**
	 * Exports the drawing as a PST document.
	 * @param file The targeted location.
	 * @return true if the PST document was been successfully created.
	 */
	private boolean exportAsPST(final @NotNull File file) {
		boolean ok;

		try {
			try(final FileWriter fw = new FileWriter(file);
				final BufferedWriter bw = new BufferedWriter(fw);
				final PrintWriter out = new PrintWriter(bw)) {
				out.println(pstGen.getDrawingCode());
				ok = true;
			}
		}catch(final @NotNull IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			ok = false;
		}
		return ok;
	}


	/**
	 * @return A writable image that contains given views (not null).
	 */
	private @NotNull BufferedImage createRenderedImage() {
		final Group views = canvas.getViews();
		final Bounds bounds = views.getBoundsInParent();
		final double scale = 3d;
		final WritableImage img = new WritableImage((int) (bounds.getWidth() * scale), (int) (bounds.getHeight() * scale));
		final SnapshotParameters snapshotParameters = new SnapshotParameters();
		final CountDownLatch latch = new CountDownLatch(1);

		snapshotParameters.setFill(Color.WHITE);
		snapshotParameters.setTransform(new Scale(scale, scale));

		Platform.runLater(() -> {
			views.snapshot(snapshotParameters, img);
			latch.countDown();
		});

		try {
			exported = latch.await(10, TimeUnit.SECONDS);
		}catch(final InterruptedException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		return SwingFXUtils.fromFXImage(img, null);
	}
}
