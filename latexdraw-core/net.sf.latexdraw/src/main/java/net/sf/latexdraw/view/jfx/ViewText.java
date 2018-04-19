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
package net.sf.latexdraw.view.jfx;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.sf.latexdraw.commands.ExportFormat;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.ImageCropper;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.OperatingSystem;
import net.sf.latexdraw.util.StreamExecReader;
import net.sf.latexdraw.util.Triple;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * The JFX shape view for text shapes.
 * @author Arnaud Blouin
 */
public class ViewText extends ViewPositionShape<IText> {
	private static final ExecutorService COMPILATION_POOL = Executors.newFixedThreadPool(5);
	private static final double SCALE_COMPILE = 2d;

	private final Text text;
	private final ImageView compiledText;
	private final Tooltip compileTooltip;
	private final ChangeListener<String> textUpdate;
	private Future<?> currentCompilation;


	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewText(final IText sh) {
		super(sh);
		text = new Text();
		compiledText = new ImageView();
		compileTooltip = new Tooltip(null);

		compiledText.setScaleX(1d / SCALE_COMPILE);
		compiledText.setScaleY(compiledText.getScaleX());
		compiledText.setVisible(false);
		compiledText.setSmooth(true);
		compiledText.setCache(true);
		compiledText.imageProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null) {
				compiledText.setX(-newValue.getWidth() / 4d);
				compiledText.setY(-newValue.getHeight() * 0.75);
			}
		});

		textUpdate = (observable, oldValue, newValue) -> update();
		model.textProperty().addListener(textUpdate);

		getChildren().add(text);
		getChildren().add(compiledText);
		setImageTextEnable(false);
		update();
	}

	private void setImageTextEnable(final boolean imageToEnable) {
		compiledText.setVisible(imageToEnable);
		compiledText.setDisable(!imageToEnable);
		text.setVisible(!imageToEnable);
		text.setDisable(imageToEnable);
	}

	private void update() {
		text.setText(model.getText());
		currentCompilation = COMPILATION_POOL.submit(() -> updateImageText(createImage()));
	}

	/**
	 * @return The current text compilation. May be null.
	 */
	public Future<?> getCurrentCompilation() {
		return currentCompilation;
	}

	private void updateImageText(final Tuple<Image, String> values) {
		if(currentCompilation != null && currentCompilation.isDone()) {
			currentCompilation = null;
		}

		compiledText.setUserData(values.b);

		// A text will be used to render the text shape.
		if(values.a == null) {
			compileTooltip.setText(LSystem.INSTANCE.getLatexErrorMessageFromLog(values.b));
			Tooltip.install(text, compileTooltip);
			setImageTextEnable(false);
			compiledText.setImage(null);
		}else {
			// An image will be used to render the text shape.
			compileTooltip.setText(null);
			Tooltip.uninstall(text, compileTooltip);
			compiledText.setImage(values.a);
			setImageTextEnable(true);

			getCanvasParent().ifPresent(canvas -> canvas.update());
		}
	}

	public Optional<String> getCompilationData() {
		if(compiledText.getUserData() instanceof String) {
			return Optional.of((String) compiledText.getUserData());
		}
		return Optional.empty();
	}

	private String getLaTeXDocument() {
		final String code = model.getText();
		final StringBuilder doc = new StringBuilder();
		final Color textColour = model.getLineColour();
		boolean coloured = false;

		// We must scale the text to fit its latex size: latexdrawDPI/latexDPI is the ratio to scale the created png picture.
		final double scale = IShape.PPC * PSTricksConstants.INCH_VAL_CM / PSTricksConstants.INCH_VAL_PT * SCALE_COMPILE;

		doc.append("\\documentclass{standalone}\n\\usepackage[usenames,dvipsnames]{pstricks}"); //NON-NLS
		doc.append(LaTeXGenerator.getPackages()).append('\n');
		doc.append("\\begin{document}\n\\psscalebox{"); //NON-NLS
		doc.append((float) MathUtils.INST.getCutNumber(scale)).append(' ');
		doc.append((float) MathUtils.INST.getCutNumber(scale)).append('}').append('{');

		if(!textColour.equals(PSTricksConstants.DEFAULT_LINE_COLOR)) {
			final String name = DviPsColors.INSTANCE.getColourName(textColour).orElse(DviPsColors.INSTANCE.addUserColour(textColour).orElse(""));
			coloured = true;
			doc.append(DviPsColors.INSTANCE.getUsercolourCode(name)).append("\\textcolor{").append(name).append('}').append('{'); //NON-NLS
		}

		doc.append(code);

		if(coloured) {
			doc.append('}');
		}

		doc.append("}\n\\end{document}"); //NON-NLS
		return doc.toString();
	}

	/**
	 * Executes a given command and returns the log.
	 * @param cmd The command to execute.
	 * @return True if the command exits normally plus the log.
	 */
	private Tuple<Boolean, String> execute(final String[] cmd) {
		String log = "";
		try {
			final Process process = Runtime.getRuntime().exec(cmd);
			final StreamExecReader errReader = new StreamExecReader(process.getErrorStream());
			final StreamExecReader outReader = new StreamExecReader(process.getInputStream());

			errReader.start();
			outReader.start();

			if(process.waitFor() == 0) {
				return new Tuple<>(true, log);
			}

			log = outReader.getLog() + LSystem.EOL + errReader.getLog();
		}catch(final IOException | InterruptedException | IllegalThreadStateException ex) {
			log += ex.getMessage();
		}
		return new Tuple<>(false, log);
	}

	/**
	 * Reads and returns the first page of the given pdf document.
	 * @param file The file of the pdf document.
	 * @return The image of the first page or null.
	 */
	private BufferedImage readPDFFirstPage(final File file) {
		BufferedImage bi = null;

		try(final FileChannel fc = new RandomAccessFile(file, "r").getChannel()) { //NON-NLS
			final MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			final PDFFile pdfFile = new PDFFile(mbb);
			mbb.clear();

			if(pdfFile.getNumPages() == 1) {
				final PDFPage page = pdfFile.getPage(0);
				final Rectangle2D bound = page.getBBox();
				final java.awt.Image img = page.getImage((int) bound.getWidth(), (int) bound.getHeight(), bound, null, false, true);

				if(img instanceof BufferedImage) {
					bi = ImageCropper.INSTANCE.cropImage((BufferedImage) img);
				}

				if(img != null) {
					img.flush();
				}
			}else {
				BadaboomCollector.INSTANCE.add(new IllegalArgumentException("Not a single page: " + pdfFile.getNumPages())); //NON-NLS
			}
		}catch(final IOException | IllegalArgumentException | SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		return bi;
	}

	/**
	 * @return The LaTeX compiled picture of the text with its file path and its log.
	 */
	private Tuple<Image, String> createImage() {
		final Optional<File> optDir = LFileUtils.INSTANCE.createTempDir();

		if(!optDir.isPresent()) {
			return new Tuple<>(null, "A temporary file cannot be created.");
		}

		BufferedImage bi = null;
		String log = ""; //NON-NLS
		final File tmpDir = optDir.get();
		final String doc = getLaTeXDocument();
		final String basePathPic = tmpDir.getAbsolutePath() + LSystem.FILE_SEP + "latexdrawTmpPic" + System.currentTimeMillis(); //NON-NLS
		final String pathTex = basePathPic + ExportFormat.TEX.getFileExtension();
		final OperatingSystem os = LSystem.INSTANCE.getSystem().orElse(OperatingSystem.LINUX);

		// Saving the LaTeX document into a file to be compiled.
		if(!LFileUtils.INSTANCE.saveFile(pathTex, doc).isPresent()) {
			return new Triple<>(null, basePathPic, log);
		}

		// Compiling the LaTeX document.
		Tuple<Boolean, String> res = execute(new String[]{os.getLatexBinPath(), "--halt-on-error", "--interaction=nonstopmode", //NON-NLS
			"--output-directory=" + tmpDir.getAbsolutePath(), LFileUtils.INSTANCE.normalizeForLaTeX(pathTex)}); //NON-NLS
		boolean ok = res.a;
		log = res.b;

		// Compiling the DVI document.
		if(ok) {
			res = execute(new String[]{os.getDvipsBinPath(), basePathPic + ".dvi", "-o", basePathPic + ExportFormat.EPS_LATEX.getFileExtension()}); //NON-NLS
			ok = res.a;
			log = log + res.b;
		}

		// Converting the PS document as a PDF one.
		if(ok) {
			res = execute(new String[]{os.getPs2pdfBinPath(), basePathPic + ExportFormat.EPS_LATEX.getFileExtension(), basePathPic + ExportFormat.PDF.getFileExtension()});
			ok = res.a;
			log = log + res.b;
		}

		// Getting the image of the first page of the PDF document.
		if(ok) {
			final String pdfpath = basePathPic + ExportFormat.PDF.getFileExtension();
			final File pdfFile = new File(pdfpath);
			bi = readPDFFirstPage(pdfFile);
		}

		// Converting the image as a JFX one.
		final Image fxImage;

		if(bi == null) {
			fxImage = null;
		}else {
			fxImage = SwingFXUtils.toFXImage(bi, null);
			bi.flush();
		}

		// Deleting the temporary folder and its content.
		LFileUtils.INSTANCE.removeDirWithContent(tmpDir.getPath());

		return new Tuple<>(fxImage, log);
	}

	@Override
	public void flush() {
		model.textProperty().removeListener(textUpdate);
		super.flush();
	}
}
