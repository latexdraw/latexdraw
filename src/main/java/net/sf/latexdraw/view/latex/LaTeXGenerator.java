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
import java.util.Objects;
import java.util.Optional;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.command.ExportFormat;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.OperatingSystem;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Defines an abstract LaTeX generator.
 * @author Arnaud Blouin
 */
public abstract class LaTeXGenerator {
	protected final @NotNull Drawing drawing;
	protected final @NotNull ViewsSynchroniserHandler handler;
	protected final @NotNull LaTeXDataService latexdata;
	/** Defines whether the latex parameters (position, caption, etc.) must be generated. */
	protected boolean withLatexParams;
	/** Defines whether the comments must be generated. */
	protected boolean withComments;


	/**
	 * Initialises the abstract generator.
	 */
	protected LaTeXGenerator(final Drawing drawing, final ViewsSynchroniserHandler handler, final LaTeXDataService latexdata) {
		super();
		this.drawing = Objects.requireNonNull(drawing);
		this.handler = Objects.requireNonNull(handler);
		this.latexdata = Objects.requireNonNull(latexdata);
		withComments = true;
		withLatexParams = true;
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
		final Optional<File> optDir = SystemUtils.getInstance().createTempDir();

		if(!optDir.isPresent()) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a tmp dir"));
			return Optional.empty();
		}

		final File tmpDir = optDir.get();
		final Optional<File> optFile = createPSFile(tmpDir.getAbsolutePath() + SystemUtils.getInstance().FILE_SEP + "tmpPSFile.ps", tmpDir); //NON-NLS

		if(!optFile.isPresent()) {
			return Optional.empty();
		}

		final File psFile = optFile.get();
		final OperatingSystem os = SystemUtils.getInstance().getSystem().orElse(OperatingSystem.LINUX);
		final File finalFile = new File(pathExportEPS);
		final File fileEPS = new File(psFile.getAbsolutePath().replace(".ps", ExportFormat.EPS_LATEX.getFileExtension())); //NON-NLS
		final String[] paramsLatex = {os.getPS2EPSBinPath(), psFile.getAbsolutePath(), fileEPS.getAbsolutePath()};
		final String log = SystemUtils.getInstance().execute(paramsLatex, tmpDir).b;

		if(!fileEPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + SystemUtils.getInstance().EOL + log));
			return Optional.empty();
		}

		try {
			Files.copy(fileEPS.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}catch(final IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}

		SystemUtils.getInstance().removeDirWithContent(tmpDir.getPath());

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

		final int lastSep = pathExportPs.lastIndexOf(SystemUtils.getInstance().FILE_SEP) + 1;
		final String name = pathExportPs.substring(lastSep, pathExportPs.lastIndexOf(".ps")); //NON-NLS
		final File tmpDir2 = tmpDir == null ? SystemUtils.getInstance().createTempDir().orElse(null) : tmpDir;

		if(tmpDir2 == null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //NON-NLS
			return Optional.empty();
		}

		final String path = tmpDir2.getAbsolutePath() + SystemUtils.getInstance().FILE_SEP;
		final Optional<File> optFile = SystemUtils.getInstance().saveFile(path + name + ExportFormat.TEX.getFileExtension(), getDocumentCode());

		if(!optFile.isPresent()) {
			return Optional.empty();
		}

		final File texFile = optFile.get();
		String log;
		File finalPS;
		final Point tr = handler.getTopRightDrawingPoint();
		final Point bl = handler.getBottomLeftDrawingPoint();
		final int ppc = handler.getPPCDrawing();
		final float dec = 0.2f;
		final OperatingSystem os = SystemUtils.getInstance().getSystem().orElse(OperatingSystem.LINUX);

		if(!texFile.exists()) {
			return Optional.empty();
		}

		final String[] paramsLatex = {os.getLatexBinPath(), "--interaction=nonstopmode", "--output-directory=" + tmpDir2.getAbsolutePath(), //NON-NLS
			SystemUtils.getInstance().normalizeForLaTeX(texFile.getAbsolutePath())}; //NON-NLS
		log = SystemUtils.getInstance().execute(paramsLatex, tmpDir2).b;

		final String[] paramsDvi = {os.getDvipsBinPath(), "-Pdownload35", "-T", //NON-NLS
			(tr.getX() - bl.getX()) / ppc * latexdata.getScale() + dec + "cm," + ((bl.getY() - tr.getY()) / ppc * latexdata.getScale() + dec) + "cm", //NON-NLS
			name, "-o", pathExportPs}; //NON-NLS
		log += SystemUtils.getInstance().execute(paramsDvi, tmpDir2);

		finalPS = new File(pathExportPs);

		if(!finalPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + SystemUtils.getInstance().EOL + log));
			finalPS = null;
		}

		if(tmpDir == null) {
			SystemUtils.getInstance().removeDirWithContent(tmpDir2.getPath());
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

		final Optional<File> optDir = SystemUtils.getInstance().createTempDir();

		if(!optDir.isPresent()) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //NON-NLS
			return Optional.empty();
		}

		final File tmpDir = optDir.get();
		final String name = pathExportPdf.substring(pathExportPdf.lastIndexOf(SystemUtils.getInstance().FILE_SEP) + 1, pathExportPdf.lastIndexOf(ExportFormat.PDF.getFileExtension()));
		final File psFile;
		final Optional<File> optFile = createPSFile(tmpDir.getAbsolutePath() + SystemUtils.getInstance().FILE_SEP + name + ".ps"); //NON-NLS

		if(optFile.isPresent()) {
			psFile = optFile.get();
		}else {
			return Optional.empty();
		}

		String log;
		File pdfFile;
		final OperatingSystem os = SystemUtils.getInstance().getSystem().orElse(OperatingSystem.LINUX);

		// On windows, an option must be defined using this format:
		// -optionName#valueOption Thus, the classical = character must be replaced by a # when latexdraw runs on Windows.
		final String optionEmbed = "-dEmbedAllFonts" + (SystemUtils.getInstance().isWindows() ? "#" : "=") + "true"; //NON-NLS

		log = SystemUtils.getInstance().execute(new String[] {os.getPs2pdfBinPath(), optionEmbed, psFile.getAbsolutePath(),
			crop ? name + ExportFormat.PDF.getFileExtension() : pathExportPdf}, tmpDir).b;

		if(crop) {
			pdfFile = new File(tmpDir.getAbsolutePath() + SystemUtils.getInstance().FILE_SEP + name + ExportFormat.PDF.getFileExtension());
			log = SystemUtils.getInstance().execute(new String[] {os.getPdfcropBinPath(), pdfFile.getAbsolutePath(), pdfFile.getAbsolutePath()}, tmpDir).b;
			try {
				Files.move(pdfFile.toPath(), Paths.get(pathExportPdf), StandardCopyOption.REPLACE_EXISTING);
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
				log += " The final pdf document cannot be moved to its final destination. If you use Windows, you must have a Perl interpretor installed, such as strawberryPerl (http://strawberryperl.com/)"; //NON-NLS
			}
		}

		pdfFile = new File(pathExportPdf);

		if(!pdfFile.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + SystemUtils.getInstance().EOL + log));
			pdfFile = null;
		}

		SystemUtils.getInstance().removeDirWithContent(tmpDir.getPath());

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
