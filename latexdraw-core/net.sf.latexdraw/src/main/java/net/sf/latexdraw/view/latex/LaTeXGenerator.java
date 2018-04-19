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
package net.sf.latexdraw.view.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.commands.ExportFormat;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.OperatingSystem;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import org.malai.properties.Modifiable;

/**
 * Defines an abstract LaTeX generator.
 * @author Arnaud Blouin
 */
public abstract class LaTeXGenerator implements Modifiable {
	/**
	 * Defines the number of characters added at the beginning
	 * of each lines of the comment (these characters are "% ").
	 */
	public static final int LGTH_START_LINE_COMMENT = 2;

	/**
	 * The latex packages used when exporting using latex.
	 * These packages are defined for the current document but not for all documents.
	 */
	public static final ObjectProperty<String> PACKAGES = new SimpleObjectProperty<>(""); //NON-NLS


	/**
	 * @param packages the packages to set.
	 * @since 3.0
	 */
	public static void setPackages(final String packages) {
		if(packages != null && !packages.equals(getPackages())) {
			PACKAGES.setValue(packages);
		}
	}


	/**
	 * @return the packages.
	 * @since 3.0
	 */
	public static String getPackages() {
		return PACKAGES.getValue();
	}


	/** The comment of the drawing. */
	protected String comment;

	/** The label of the drawing. */
	protected String label;

	/** The caption of the drawing. */
	protected String caption;

	/** The token of the position of the drawing */
	protected VerticalPosition positionVertToken;

	/** The horizontal position of the drawing */
	protected boolean positionHoriCentre;

	/** Defined if the instrument has been modified. */
	protected boolean modified;

	/** The scale of the drawing. */
	protected double scale;

	@Inject protected IDrawing drawing;

	@Inject protected ViewsSynchroniserHandler handler;

	/** Defines if the latex parameters (position, caption, etc.) must be generated. */
	protected boolean withLatexParams;

	/** Defines if the comments must be generated. */
	protected boolean withComments;


	/**
	 * Initialises the abstract generator.
	 */
	protected LaTeXGenerator() {
		super();

		modified = false;
		comment = ""; //NON-NLS
		label = ""; //NON-NLS
		caption = ""; //NON-NLS
		positionHoriCentre = false;
		positionVertToken = VerticalPosition.NONE;
		scale = 1.0;
		withComments = true;
		withLatexParams = true;
	}


	/**
	 * @return the scale of the drawing.
	 * @since 3.0
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * @param sc the scale to set.
	 * @since 3.0
	 */
	public void setScale(final double sc) {
		if(sc >= 0.1) {
			scale = sc;
		}
	}

	/**
	 * @return the comment.
	 * @since 3.0
	 */
	public String getComment() {
		return comment;
	}


	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
	}

	/**
	 * @param newComments the comment to set. Nothing done if null.
	 */
	public void setComment(final String newComments) {
		if(newComments != null && !comment.equals(newComments)) {
			comment = newComments;
			setModified(true);
		}
	}

	/**
	 * @return The comments with the '%' tag at the beginning of each line. Cannot be null.
	 */
	public String getCommentWithTag() {
		return Stream.of(comment.split(LSystem.EOL)).map(commentLine -> "% " + commentLine).collect(Collectors.joining(LSystem.EOL));
	}


	/**
	 * @return The latex token corresponding to the specified vertical position.
	 */
	public VerticalPosition getPositionVertToken() {
		return positionVertToken;
	}


	/**
	 * @param positionVert The new vertical position token. Must not be null.
	 */
	public void setPositionVertToken(final VerticalPosition positionVert) {
		if(positionVert != null) {
			positionVertToken = positionVert;
			setModified(true);
		}
	}


	/**
	 * @return True: the latex drawing will be horizontally centred.
	 */
	public boolean isPositionHoriCentre() {
		return positionHoriCentre;
	}


	/**
	 * @return the label of the latex drawing.
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @param lab the new lab of the drawing. Must not be null.
	 */
	public void setLabel(final String lab) {
		if(lab != null) {
			label = lab;
			setModified(true);
		}
	}


	/**
	 * @return the caption of the drawing.
	 */
	public String getCaption() {
		return caption;
	}


	/**
	 * @param theCaption the new caption of the drawing. Must not be null.
	 */
	public void setCaption(final String theCaption) {
		if(theCaption != null) {
			caption = theCaption;
			setModified(true);
		}
	}


	/**
	 * @param position True: the latex drawing will be horizontally centred.
	 */
	public void setPositionHoriCentre(final boolean position) {
		if(positionHoriCentre != position) {
			positionHoriCentre = position;
			setModified(true);
		}
	}


	/**
	 * Produces and returns the code.
	 * @return The generate code.
	 */
	public abstract String getDrawingCode();


	/**
	 * Generates a latex document that contains the pstricks code of the given canvas.
	 * @return The latex document or an empty string.
	 */
	public abstract String getDocumentCode();

	/**
	 * Create a .ps file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param pathExportPs The path of the .ps file to create (MUST ends with .ps).
	 * @return The create file or nothing.
	 */
	public Optional<File> createPSFile(final String pathExportPs) {
		return createPSFile(pathExportPs, null);
	}


	/**
	 * Create an .eps file that corresponds to the compiled latex document containing the pstricks drawing.
	 * @param pathExportEPS The path of the .eps file to create (MUST ends with .eps).
	 * @return The create file or nothing.
	 * @throws SecurityException In case of problem while accessing files.
	 */
	public Optional<File> createEPSFile(final String pathExportEPS) {
		final Optional<File> optDir = LFileUtils.INSTANCE.createTempDir();

		if(!optDir.isPresent()) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a tmp dir"));
			return Optional.empty();
		}

		final File tmpDir = optDir.get();
		final Optional<File> optFile = createPSFile(tmpDir.getAbsolutePath() + LSystem.FILE_SEP + "tmpPSFile.ps", tmpDir); //NON-NLS

		if(!optFile.isPresent()) {
			return Optional.empty();
		}

		final File psFile = optFile.get();
		final OperatingSystem os = LSystem.INSTANCE.getSystem().orElse(OperatingSystem.LINUX);
		final File finalFile = new File(pathExportEPS);
		final File fileEPS = new File(psFile.getAbsolutePath().replace(".ps", ExportFormat.EPS_LATEX.getFileExtension())); //NON-NLS
		final String[] paramsLatex = {os.getPS2EPSBinPath(), psFile.getAbsolutePath(), fileEPS.getAbsolutePath()};
		final String log = LSystem.INSTANCE.execute(paramsLatex, tmpDir);

		if(!fileEPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + LSystem.EOL + log));
			return Optional.empty();
		}

		try {
			Files.copy(fileEPS.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}catch(final IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}

		LFileUtils.INSTANCE.removeDirWithContent(tmpDir.getPath());

		return Optional.of(finalFile);
	}


	/**
	 * Create a .ps file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param pathExportPs The path of the .ps file to create (MUST ends with .ps).
	 * @param tmpDir The temporary directory used for the compilation.
	 * @return The create file or nothing.
	 */
	private Optional<File> createPSFile(final String pathExportPs, final File tmpDir) {
		if(pathExportPs == null) {
			return Optional.empty();
		}

		final int lastSep = pathExportPs.lastIndexOf(LSystem.FILE_SEP) + 1;
		final String name = pathExportPs.substring(lastSep == -1 ? 0 : lastSep, pathExportPs.lastIndexOf(".ps")); //NON-NLS
		final File tmpDir2 = tmpDir == null ? LFileUtils.INSTANCE.createTempDir().orElse(null) : tmpDir;

		if(tmpDir2 == null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //NON-NLS
			return Optional.empty();
		}

		final String path = tmpDir2.getAbsolutePath() + LSystem.FILE_SEP;
		final Optional<File> optFile = LFileUtils.INSTANCE.saveFile(path + name + ExportFormat.TEX.getFileExtension(), getDocumentCode());

		if(!optFile.isPresent()) {
			return Optional.empty();
		}

		final File texFile = optFile.get();
		String log;
		File finalPS;
		final IPoint tr = handler.getTopRightDrawingPoint();
		final IPoint bl = handler.getBottomLeftDrawingPoint();
		final int ppc = handler.getPPCDrawing();
		final float dec = 0.2f;
		final OperatingSystem os = LSystem.INSTANCE.getSystem().orElse(OperatingSystem.LINUX);

		if(!texFile.exists()) {
			return Optional.empty();
		}

		final String[] paramsLatex = {os.getLatexBinPath(), "--interaction=nonstopmode", "--output-directory=" + tmpDir2.getAbsolutePath(), //NON-NLS
			LFileUtils.INSTANCE.normalizeForLaTeX(texFile.getAbsolutePath())}; //NON-NLS
		log = LSystem.INSTANCE.execute(paramsLatex, tmpDir2);

		final String[] paramsDvi = {os.getDvipsBinPath(), "-Pdownload35", "-T", //NON-NLS
			(tr.getX() - bl.getX()) / ppc * scale + dec + "cm," + ((bl.getY() - tr.getY()) / ppc * scale + dec) + "cm", //NON-NLS
			name, "-o", pathExportPs}; //NON-NLS
		log += LSystem.INSTANCE.execute(paramsDvi, tmpDir2);

		finalPS = new File(pathExportPs);

		if(!finalPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + LSystem.EOL + log));
			finalPS = null;
		}

		if(tmpDir == null) {
			LFileUtils.INSTANCE.removeDirWithContent(tmpDir2.getPath());
		}

		return Optional.ofNullable(finalPS);
	}


	/**
	 * Create a .pdf file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param pathExportPdf The path of the .pdf file to create (MUST ends with .pdf).
	 * @param crop if true, the output document will be cropped.
	 * @return The create file or null.
	 * @throws SecurityException In case of problem while accessing files.
	 */
	public Optional<File> createPDFFile(final String pathExportPdf, final boolean crop) {
		if(pathExportPdf == null) {
			return Optional.empty();
		}

		final Optional<File> optDir = LFileUtils.INSTANCE.createTempDir();

		if(!optDir.isPresent()) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //NON-NLS
			return Optional.empty();
		}

		final File tmpDir = optDir.get();
		final String name = pathExportPdf.substring(pathExportPdf.lastIndexOf(LSystem.FILE_SEP) + 1, pathExportPdf.lastIndexOf(ExportFormat.PDF.getFileExtension()));
		final File psFile;
		final Optional<File> optFile = createPSFile(tmpDir.getAbsolutePath() + LSystem.FILE_SEP + name + ".ps"); //NON-NLS

		if(optFile.isPresent()) {
			psFile = optFile.get();
		}else {
			return Optional.empty();
		}

		String log;
		File pdfFile;
		final OperatingSystem os = LSystem.INSTANCE.getSystem().orElse(OperatingSystem.LINUX);

		// On windows, an option must be defined using this format:
		// -optionName#valueOption Thus, the classical = character must be replaced by a # when latexdraw runs on Windows.
		final String optionEmbed = "-dEmbedAllFonts" + (LSystem.INSTANCE.isWindows() ? "#" : "=") + "true"; //NON-NLS

		log = LSystem.INSTANCE.execute(new String[]{os.getPs2pdfBinPath(), optionEmbed, psFile.getAbsolutePath(), crop ? name + ExportFormat.PDF.getFileExtension() : pathExportPdf}, tmpDir);

		if(crop) {
			pdfFile = new File(tmpDir.getAbsolutePath() + LSystem.FILE_SEP + name + ExportFormat.PDF.getFileExtension());
			log = LSystem.INSTANCE.execute(new String[]{os.getPdfcropBinPath(), pdfFile.getAbsolutePath(), pdfFile.getAbsolutePath()}, tmpDir);
			try {
				Files.move(pdfFile.toPath(), Paths.get(pathExportPdf), StandardCopyOption.REPLACE_EXISTING);
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
				log += " The final pdf document cannot be moved to its final destination. If you use Windows, you must have a Perl interpretor installed, such as strawberryPerl (http://strawberryperl.com/)"; //NON-NLS
			}
		}

		pdfFile = new File(pathExportPdf);

		if(!pdfFile.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + LSystem.EOL + log));
			pdfFile = null;
		}

		LFileUtils.INSTANCE.removeDirWithContent(tmpDir.getPath());

		return Optional.ofNullable(pdfFile);
	}


	/**
	 * @return True: The latex parameters must be used by the generated code.
	 */
	public boolean isWithLatexParams() {
		return withLatexParams;
	}


	/**
	 * Defines if the latex parameters must be used by the generated code.
	 * @param latexParams True: The latex parameters must be used by the generated code.
	 */
	public void setWithLatexParams(final boolean latexParams) {
		this.withLatexParams = latexParams;
	}


	/**
	 * @return True: comments will be included.
	 */
	public boolean isWithComments() {
		return withComments;
	}


	/**
	 * Defines if the code must contains comments.
	 * @param comments True: comments will be included.
	 */
	public void setWithComments(final boolean comments) {
		this.withComments = comments;
	}
}
