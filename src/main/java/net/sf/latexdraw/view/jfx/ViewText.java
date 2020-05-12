/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import net.sf.latexdraw.command.ExportFormat;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.OperatingSystem;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.Triple;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * The JFX shape view for text shapes.
 * @author Arnaud Blouin
 */
public class ViewText extends ViewPositionShape<Text> {
	static final Logger LOGGER = Logger.getAnonymousLogger();

	private final javafx.scene.text.Text text;
	private final ImageView compiledText;
	private final Tooltip compileTooltip;
	private final ChangeListener<String> textUpdate;
	private Future<?> currentCompilation;
	private final LaTeXDataService latexData;
	private final ChangeListener<Object> updateTrText = (observable, oldValue, newValue) -> updateTranslationCompiledText();

	static {
		LOGGER.setLevel(Level.OFF);
	}

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewText(final Text sh, final LaTeXDataService data) {
		super(sh);
		text = new javafx.scene.text.Text();
		compiledText = new ImageView();
		compileTooltip = new Tooltip(null);
		this.latexData = data;

		// Scaling at 0.5 as the png produced by latex is zoomed x 2 (for a better rendering)
		compiledText.setScaleX(0.5);
		compiledText.setScaleY(0.5);
		compiledText.setVisible(false);
		compiledText.setSmooth(true);
		compiledText.setCache(true);
		compiledText.imageProperty().addListener((observable, oldValue, theImg) -> {
			if(theImg != null) {
				// Have to move the picture as it is zoomed
				compiledText.setX(-theImg.getWidth() / 4d);
				compiledText.setY(-(3d * theImg.getHeight()) / 4d);
			}
		});

		textUpdate = (observable, oldValue, newValue) -> update();
		model.textProperty().addListener(textUpdate);

		getChildren().add(text);
		getChildren().add(compiledText);
		setImageTextEnable(false);
		update();
		bindTextPosition();
	}

	private final void bindTextPosition() {
		model.textPositionProperty().addListener(updateTrText);

		text.translateXProperty().bind(Bindings.createDoubleBinding(() -> switch(model.getTextPosition()) {
			case BOT_LEFT, TOP_LEFT, LEFT -> 0d;
			case BOT, TOP, CENTER -> -text.getBoundsInLocal().getWidth() / 2d;
			case BOT_RIGHT, TOP_RIGHT, RIGHT -> -text.getBoundsInLocal().getWidth();
		}, model.textPositionProperty()));

		text.translateYProperty().bind(Bindings.createDoubleBinding(() -> switch(model.getTextPosition()) {
			case BOT_LEFT, BOT, BOT_RIGHT -> 0d;
			case TOP_LEFT, TOP, TOP_RIGHT -> -text.getBoundsInLocal().getHeight();
			case LEFT, RIGHT, CENTER -> -text.getBoundsInLocal().getHeight() / 2d;
		}, model.textPositionProperty()));
	}

	private final void setImageTextEnable(final boolean imageToEnable) {
		compiledText.setVisible(imageToEnable);
		compiledText.setDisable(!imageToEnable);
		text.setVisible(!imageToEnable);
		text.setDisable(imageToEnable);
	}

	private final void update() {
		text.setText(model.getText());
		currentCompilation = latexData.getCompilationPool().submit(() -> {
			final Tuple<Image, String> image = createImage();
			Platform.runLater(() -> updateImageText(image));
		});
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
			compileTooltip.setText(SystemUtils.getInstance().getLatexErrorMessageFromLog(values.b));
			Tooltip.install(text, compileTooltip);
			setImageTextEnable(false);
			compiledText.setImage(null);
		}else {
			// An image will be used to render the text shape.
			compileTooltip.setText(null);
			Tooltip.uninstall(text, compileTooltip);
			compiledText.setImage(values.a);
			setImageTextEnable(true);
			updateTranslationCompiledText();

			getCanvasParent().ifPresent(canvas -> canvas.update());
		}
	}

	private void updateTranslationCompiledText() {
		if(compiledText.getImage() == null) {
			compiledText.setTranslateX(0d);
			compiledText.setTranslateY(0d);
		}else {
			compiledText.setTranslateX(switch(model.getTextPosition()) {
				case BOT_LEFT, TOP_LEFT, LEFT -> 0d;
				case BOT, TOP, CENTER -> -compiledText.getImage().getWidth() / 4d;
				case BOT_RIGHT, TOP_RIGHT, RIGHT -> -compiledText.getImage().getWidth() / 2d;
			});
			compiledText.setTranslateY(switch(model.getTextPosition()) {
				case BOT_LEFT, BOT, BOT_RIGHT -> 0d;
				case TOP_LEFT, TOP, TOP_RIGHT -> -compiledText.getImage().getHeight() / 2d;
				case LEFT, RIGHT, CENTER -> -compiledText.getImage().getHeight() / 4d;
			});
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
		final String eol = SystemUtils.getInstance().eol;

		// Do not want to use the convert option of standalone to get a png picture as
		// it requires gs to be installed in Windows (not provided by Miktex)
		doc.append("\\documentclass[border=0.5pt]{standalone}") //NON-NLS
			.append(eol)
			.append("\\usepackage[usenames,dvipsnames]{pstricks}") //NON-NLS
			.append(latexData.getPackages())
			.append(eol)
			.append("\\begin{document}") //NON-NLS
			.append(eol);

		if(!PSTricksConstants.DEFAULT_LINE_COLOR.equals(textColour)) {
			final String name = DviPsColors.INSTANCE.getColourName(textColour)
				.orElseGet(() -> DviPsColors.INSTANCE.addUserColour(textColour).orElse(""));
			coloured = true;
			doc.append(DviPsColors.INSTANCE.getUsercolourCode(name))
				.append(eol)
				.append("\\textcolor{") //NON-NLS
				.append(name)
				.append("}{"); //NON-NLS
		}

		doc.append(code);

		if(coloured) {
			doc.append('}');
		}

		doc.append("\\end{document}"); //NON-NLS
		return doc.toString();
	}


	/**
	 * @return The LaTeX compiled picture of the text with its file path and its log.
	 */
	private Tuple<Image, String> createImage() {
		final Optional<File> optDir = SystemUtils.getInstance().createTempDir();

		if(optDir.isEmpty()) {
			return new Tuple<>(null, "A temporary file cannot be created."); //NON-NLS
		}

		Image img = null;
		String log = ""; //NON-NLS
		final File tmpDir = optDir.get();
		final String doc = getLaTeXDocument();
		final String basePathPic = tmpDir.getAbsolutePath() + SystemUtils.getInstance().fileSep + "latexdrawTmpPic" + System.currentTimeMillis(); //NON-NLS
		final String pathTex = basePathPic + ExportFormat.TEX.getFileExtension();
		final OperatingSystem os = SystemUtils.getInstance().getSystem().orElse(OperatingSystem.LINUX);

		LOGGER.log(Level.INFO, doc);

		// Saving the LaTeX document into a file to be compiled.
		if(SystemUtils.getInstance().saveFile(pathTex, doc).isEmpty()) {
			return new Triple<>(null, basePathPic, log);
		}

		// Cannot use the pst-pdf package as it requires the shell-escape options that
		// cannot be used with our system execution process.

		// Compiling the LaTeX document.
		Tuple<Boolean, String> res = SystemUtils.getInstance().execute(new String[] {os.getLatexBinPath(), "--halt-on-error", "--interaction=nonstopmode", //NON-NLS
			"--output-directory=" + tmpDir.getAbsolutePath(), SystemUtils.getInstance().normalizeForLaTeX(pathTex)}, null); //NON-NLS
		boolean ok = res.a;
		log = res.b;

		// Compiling the DVI document.
		if(ok) {
			res = SystemUtils.getInstance().execute(new String[] {os.getDvipsBinPath(), basePathPic + ".dvi", "-o", //NON-NLS
				basePathPic + ExportFormat.EPS_LATEX.getFileExtension()}, null); //NON-NLS
			ok = res.a;
			log = log + res.b;
		}

		// Converting the PS document as a PDF one.
		if(ok) {
			res = SystemUtils.getInstance().execute(new String[] {os.getPs2pdfBinPath(), basePathPic + ExportFormat.EPS_LATEX.getFileExtension(),
				basePathPic + ExportFormat.PDF.getFileExtension()}, null); //NON-NLS
			ok = res.a;
			log += res.b;
		}

		// Getting the image of the first page of the PDF document.
		if(ok) {
			final String pdfpath = basePathPic + ExportFormat.PDF.getFileExtension();

			// Trying ghostscript
			// gs -dNOPAUSE -dBATCH -sDEVICE=pngalpha -r255 -o pic-1.png doc.pdf
			log += SystemUtils.getInstance().execute(new String[] {os.getGSbinPath(), "-dNOPAUSE", "-dBATCH", "-sDEVICE=pngalpha", //NON-NLS
				"-r255", "-o", basePathPic + ".png", pdfpath}, null).b + SystemUtils.getInstance().eol; //NON-NLS

			final File gsFile = new File(basePathPic + ".png");
			if(gsFile.exists()) {
				img = new Image(gsFile.toURI().toString());
			}

			if(img == null) {
				// trying pdftoppm
				// We defined -r empirically: 127 for a ratio 1:1 with the exported PDF
				// 255 as we zoom x2 for a better resolution
				log += SystemUtils.getInstance().execute(new String[] {os.getPDFtoPPMbinPath(), "-png", "-r", "255", pdfpath, basePathPic}, null).b; //NON-NLS
				final File ppmFile = new File(basePathPic + "-1.png"); //NON-NLS
				if(ppmFile.exists()) {
					img = toTransparentPNG(new Image(ppmFile.toURI().toString()));
				}
			}
		}

		// Deleting the temporary folder and its content.
		SystemUtils.getInstance().removeDirWithContent(tmpDir.getPath());

		LOGGER.log(Level.INFO, log);

		return new Tuple<>(img, log);
	}

	/**
	 * Adds transparency to the given image.
	 * @param img The image to transform.
	 * @return The same image with white replaced by transparent.
	 */
	private WritableImage toTransparentPNG(final Image img) {
		final PixelReader pixelReader = img.getPixelReader();
		final WritableImage wImage = new WritableImage((int) img.getWidth(), (int) img.getHeight());
		final PixelWriter pixelWriter = wImage.getPixelWriter();

		for(int readY = 0; readY < img.getHeight(); readY++) {
			for(int readX = 0; readX < img.getWidth(); readX++) {
				final javafx.scene.paint.Color color = pixelReader.getColor(readX, readY);
				if (color.equals(javafx.scene.paint.Color.WHITE)) {
					pixelWriter.setColor(readX, readY, new javafx.scene.paint.Color(color.getRed(), color.getGreen(), color.getBlue(), 0)); // new javafx.scene.paint.Color(1, 1, 1, 0));
				} else {
					pixelWriter.setColor(readX, readY, color);
				}
			}
		}

		return wImage;
	}

	@Override
	public void flush() {
		model.textProperty().removeListener(textUpdate);
		compiledText.translateXProperty().unbind();
		compiledText.translateYProperty().unbind();
		text.translateXProperty().unbind();
		text.translateYProperty().unbind();
		model.textPositionProperty().removeListener(updateTrText);
		super.flush();
	}
}
