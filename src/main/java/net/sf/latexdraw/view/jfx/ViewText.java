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

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.sf.latexdraw.commands.ExportFormat;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.OperatingSystem;
import net.sf.latexdraw.util.SystemService;
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
	static final Logger LOGGER = Logger.getAnonymousLogger();
	private static final ExecutorService COMPILATION_POOL = Executors.newFixedThreadPool(5);
	private static final double SCALE_COMPILE = 2d;

	private final Text text;
	private final ImageView compiledText;
	private final Tooltip compileTooltip;
	private final ChangeListener<String> textUpdate;
	private Future<?> currentCompilation;
	private final SystemService system;

	static {
		LOGGER.setLevel(Level.OFF);
	}

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewText(final IText sh, final SystemService system) {
		super(sh);
		text = new Text();
		compiledText = new ImageView();
		compileTooltip = new Tooltip(null);
		this.system = system;

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

	private final void setImageTextEnable(final boolean imageToEnable) {
		compiledText.setVisible(imageToEnable);
		compiledText.setDisable(!imageToEnable);
		text.setVisible(!imageToEnable);
		text.setDisable(imageToEnable);
	}

	private final void update() {
		text.setText(model.getText());
		currentCompilation = COMPILATION_POOL.submit(() -> {
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
			compileTooltip.setText(system.getLatexErrorMessageFromLog(values.b));
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

		if(!PSTricksConstants.DEFAULT_LINE_COLOR.equals(textColour)) {
			final String name = DviPsColors.INSTANCE.getColourName(textColour).orElseGet(() -> DviPsColors.INSTANCE.addUserColour(textColour).orElse(""));
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
	 * @return The LaTeX compiled picture of the text with its file path and its log.
	 */
	private Tuple<Image, String> createImage() {
		final Optional<File> optDir = system.createTempDir();

		if(!optDir.isPresent()) {
			return new Tuple<>(null, "A temporary file cannot be created."); //NON-NLS
		}

		Image img = null;
		String log = ""; //NON-NLS
		final File tmpDir = optDir.get();
		final String doc = getLaTeXDocument();
		final String basePathPic = tmpDir.getAbsolutePath() + SystemService.FILE_SEP + "latexdrawTmpPic" + System.currentTimeMillis(); //NON-NLS
		final String pathTex = basePathPic + ExportFormat.TEX.getFileExtension();
		final OperatingSystem os = system.getSystem().orElse(OperatingSystem.LINUX);

		LOGGER.log(Level.INFO, doc);

		// Saving the LaTeX document into a file to be compiled.
		if(!system.saveFile(pathTex, doc).isPresent()) {
			return new Triple<>(null, basePathPic, log);
		}

		// Compiling the LaTeX document.
		Tuple<Boolean, String> res = system.execute(new String[] {os.getLatexBinPath(), "--halt-on-error", "--interaction=nonstopmode", //NON-NLS
			"--output-directory=" + tmpDir.getAbsolutePath(), system.normalizeForLaTeX(pathTex)}, null); //NON-NLS
		boolean ok = res.a;
		log = res.b;

		// Compiling the DVI document.
		if(ok) {
			res = system.execute(new String[] {os.getDvipsBinPath(), basePathPic + ".dvi", "-o", //NON-NLS
				basePathPic + ExportFormat.EPS_LATEX.getFileExtension()}, null); //NON-NLS
			ok = res.a;
			log = log + res.b;
		}

		// Converting the PS document as a PDF one.
		if(ok) {
			res = system.execute(new String[] {os.getPs2pdfBinPath(), basePathPic + ExportFormat.EPS_LATEX.getFileExtension(),
				basePathPic + ExportFormat.PDF.getFileExtension()}, null); //NON-NLS
			ok = res.a;
			log = log + res.b;
		}

		// Getting the image of the first page of the PDF document.
		if(ok) {
			final String pdfpath = basePathPic + ExportFormat.PDF.getFileExtension();
			final String picPath = basePathPic + ".png"; //NON-NLS
			system.execute(new String[] {"convert", pdfpath, picPath}, null); //NON-NLS
			img = new Image(new File(picPath).toURI().toString());
		}

		// Deleting the temporary folder and its content.
		system.removeDirWithContent(tmpDir.getPath());

		LOGGER.log(Level.INFO, log);

		return new Tuple<>(img, log);
	}

	@Override
	public void flush() {
		model.textProperty().removeListener(textUpdate);
		super.flush();
	}
}
