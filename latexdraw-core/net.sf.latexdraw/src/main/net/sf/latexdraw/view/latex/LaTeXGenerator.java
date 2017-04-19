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
package net.sf.latexdraw.view.latex;

import com.google.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.actions.ExportFormat;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LResources;
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
	public static final ObjectProperty<String> PACKAGES = new SimpleObjectProperty<>(""); //$NON-NLS-1$


	/**
	 * @param packages the packages to set.
	 * @since 3.0
	 */
	public static void setPackages(final String packages) {
		if(packages != null && !packages.equals(getPackages())) PACKAGES.setValue(packages);
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
		comment = ""; //$NON-NLS-1$
		label = ""; //$NON-NLS-1$
		caption = ""; //$NON-NLS-1$
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
		if(sc >= 0.1) scale = sc;
	}

	/**
	 * @return the comment.
	 * @since 3.0
	 */
	public String getComment() {
		return comment;
	}


	/**
	 * @return The comments without any characters like "%"
	 * at the start of each lines. (these characters are used like comment symbol by LaTeX).
	 */
	public String getCommentsWithoutTag() {
		int i = 0;
		int j = 0;
		final int lgth = comment.length();
		final char[] buffer = new char[lgth];
		boolean eol = true;

		while(i < lgth) {
			if(eol && comment.charAt(i) == '%') {
				i += LGTH_START_LINE_COMMENT;
				eol = false;
			}else {
				if(comment.charAt(i) == '\n') eol = true;

				buffer[j++] = comment.charAt(i);
				i++;
			}
		}

		final String str = String.valueOf(buffer, 0, j);

		return str.length() > 1 ? str.substring(0, str.length() - LResources.EOL.length()) : str;
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
	 * @param newComments the comment to set.
	 * @since 3.0
	 */
	public void setComment(final String newComments) {
		if(newComments != null && !newComments.isEmpty()) {
			int i;
			int j = 0;
			final int lgth = newComments.length();
			final char[] buffer = new char[lgth * 3];
			boolean eol = true;

			for(i = 0; i < newComments.length(); i++) {
				if(eol) {
					buffer[j++] = '%';
					buffer[j++] = ' ';
					eol = false;
				}

				if(newComments.charAt(i) == '\n') eol = true;

				buffer[j++] = newComments.charAt(i);
			}

			comment = String.valueOf(buffer, 0, j);
			setModified(true);
		}
	}


	/**
	 * @return The latex token corresponding to the specified vertical position.
	 * @since 3.0
	 */
	public VerticalPosition getPositionVertToken() {
		return positionVertToken;
	}


	/**
	 * @param positionVert The new vertical position token. Must not be null.
	 * @since 3.0
	 */
	public void setPositionVertToken(final VerticalPosition positionVert) {
		if(positionVert != null) {
			positionVertToken = positionVert;
			setModified(true);
		}
	}


	/**
	 * @return True: the latex drawing will be horizontally centred.
	 * @since 3.0
	 */
	public boolean isPositionHoriCentre() {
		return positionHoriCentre;
	}


	/**
	 * @return the label of the latex drawing.
	 * @since 3.0
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @param lab the new lab of the drawing. Must not be null.
	 * @since 3.0
	 */
	public void setLabel(final String lab) {
		if(lab != null) {
			label = lab;
			setModified(true);
		}
	}


	/**
	 * @return the caption of the drawing.
	 * @since 3.0
	 */
	public String getCaption() {
		return caption;
	}


	/**
	 * @param theCaption the new caption of the drawing. Must not be null.
	 * @since 3.0
	 */
	public void setCaption(final String theCaption) {
		if(theCaption != null) {
			caption = theCaption;
			setModified(true);
		}
	}


	/**
	 * @param position True: the latex drawing will be horizontally centred.
	 * @since 3.0
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
	 * @since 3.0
	 */
	public abstract String getDocumentCode();


	/**
	 * Creates a latex file that contains the pstricks code of the given canvas.
	 * @param pathExportTex The location where the file must be created.
	 * @return The latex file or nothing.
	 * @since 3.0
	 */
	public Optional<File> createLatexFile(final String pathExportTex) {
		boolean ok = true;

		try(FileOutputStream fos = new FileOutputStream(pathExportTex); OutputStreamWriter osw = new OutputStreamWriter(fos)) {
			osw.append(getDocumentCode());
		}catch(final IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			ok = false;
		}

		return ok ? Optional.of(new File(pathExportTex)) : Optional.empty();
	}


	/**
	 * Create a .ps file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param pathExportPs The path of the .ps file to create (MUST ends with .ps).
	 * @return The create file or nothing.
	 * @since 3.0
	 */
	public Optional<File> createPSFile(final String pathExportPs) {
		return createPSFile(pathExportPs, null);
	}


	/**
	 * Create an .eps file that corresponds to the compiled latex document containing the pstricks drawing.
	 * @param pathExportEPS The path of the .eps file to create (MUST ends with .eps).
	 * @return The create file or nothing.
	 * @since 3.0
	 */
	public Optional<File> createEPSFile(final String pathExportEPS) {
		final File tmpDir = LFileUtils.INSTANCE.createTempDir();

		if(tmpDir == null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a tmp dir"));
			return Optional.empty();
		}

		Optional<File> optFile = createPSFile(tmpDir.getAbsolutePath() + LResources.FILE_SEP + "tmpPSFile.ps", tmpDir);//$NON-NLS-1$
		File psFile;

		if(optFile.isPresent()) {
			psFile = optFile.get();
		}else {
			return Optional.empty();
		}
		final OperatingSystem os = LSystem.INSTANCE.getSystem();

		if(os == null) {
			return Optional.empty();
		}

		final File finalFile = new File(pathExportEPS);
		final File fileEPS = new File(psFile.getAbsolutePath().replace(".ps", ExportFormat.EPS_LATEX.getFileExtension())); //$NON-NLS-1$
		final String[] paramsLatex = {os.getPS2EPSBinPath(), psFile.getAbsolutePath(), fileEPS.getAbsolutePath()};
		final String log = LSystem.INSTANCE.execute(paramsLatex, tmpDir);

		if(!fileEPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + LResources.EOL + log));
			return Optional.empty();
		}

		LFileUtils.INSTANCE.copy(fileEPS, finalFile);
		psFile.delete();
		fileEPS.delete();

		if(!finalFile.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException("Cannot create the EPS file at this location: " + finalFile.getAbsolutePath())); //$NON-NLS-1$
			return Optional.empty();
		}
		return Optional.of(finalFile);
	}


	/**
	 * Create a .ps file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param pathExportPs The path of the .ps file to create (MUST ends with .ps).
	 * @param tmpDir The temporary directory used for the compilation.
	 * @return The create file or nothing.
	 * @since 3.0
	 */
	public Optional<File> createPSFile(final String pathExportPs, final File tmpDir) {
		if(pathExportPs == null) return Optional.empty();

		final int lastSep = pathExportPs.lastIndexOf(LResources.FILE_SEP) + 1;
		final String name = pathExportPs.substring(lastSep == -1 ? 0 : lastSep, pathExportPs.lastIndexOf(".ps")); //$NON-NLS-1$
		final File tmpDir2 = tmpDir == null ? LFileUtils.INSTANCE.createTempDir() : tmpDir;

		if(tmpDir2 == null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //$NON-NLS-1$
			return Optional.empty();
		}

		final String path = tmpDir2.getAbsolutePath() + LResources.FILE_SEP;
		Optional<File> optFile = createLatexFile(path + name + ExportFormat.TEX.getFileExtension());
		File texFile;

		if(optFile.isPresent()) {
			texFile = optFile.get();
		}else {
			return Optional.empty();
		}

		String log;
		File finalPS;
		final IPoint tr = handler.getTopRightDrawingPoint();
		final IPoint bl = handler.getBottomLeftDrawingPoint();
		final int ppc = handler.getPPCDrawing();
		final float dec = 0.2f;
		final OperatingSystem os = LSystem.INSTANCE.getSystem();

		if(texFile == null || !texFile.exists() || os == null) return Optional.empty();

		final String[] paramsLatex = {os.getLatexBinPath(), "--interaction=nonstopmode", "--output-directory=" + tmpDir2.getAbsolutePath(),//$NON-NLS-1$//$NON-NLS-2$
			LFileUtils.INSTANCE.normalizeForLaTeX(texFile.getAbsolutePath())};//$NON-NLS-1$
		log = LSystem.INSTANCE.execute(paramsLatex, tmpDir2);
		final File dviFile = new File(tmpDir2.getAbsolutePath() + LResources.FILE_SEP + name + ".dvi"); //$NON-NLS-1$
		final boolean dviRenamed = dviFile.renameTo(new File(tmpDir2.getAbsolutePath() + LResources.FILE_SEP + name));

		final String[] paramsDvi = {os.getDvipsBinPath(), "-Pdownload35", "-T", //$NON-NLS-1$ //$NON-NLS-2$
			(tr.getX() - bl.getX()) / ppc * scale + dec + "cm," + ((bl.getY() - tr.getY()) / ppc * scale + dec) + "cm", //$NON-NLS-1$ //$NON-NLS-2$
			name, "-o", pathExportPs}; //$NON-NLS-1$
		log += LSystem.INSTANCE.execute(paramsDvi, tmpDir2);

		texFile.delete();
		new File(path + name + (dviRenamed ? "" : ".div")).delete();    //$NON-NLS-1$ //$NON-NLS-2$
		new File(path + name + ".log").delete();                        //$NON-NLS-1$
		new File(path + name + ".aux").delete();                        //$NON-NLS-1$

		finalPS = new File(pathExportPs);

		if(!finalPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + LResources.EOL + log));
			finalPS = null;
		}

		if(tmpDir == null) tmpDir2.delete();

		return Optional.ofNullable(finalPS);
	}


	/**
	 * Create a .pdf file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param pathExportPdf The path of the .pdf file to create (MUST ends with .pdf).
	 * @param crop if true, the output document will be cropped.
	 * @return The create file or null.
	 * @since 3.0
	 */
	public Optional<File> createPDFFile(final String pathExportPdf, final boolean crop) {
		if(pathExportPdf == null) return Optional.empty();

		final File tmpDir = LFileUtils.INSTANCE.createTempDir();

		if(tmpDir == null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //$NON-NLS-1$
			return Optional.empty();
		}

		final String name = pathExportPdf.substring(pathExportPdf.lastIndexOf(LResources.FILE_SEP) + 1, pathExportPdf.lastIndexOf(ExportFormat.PDF.getFileExtension()));
		final File psFile;
		Optional<File> optFile = createPSFile(tmpDir.getAbsolutePath() + LResources.FILE_SEP + name + ".ps");//$NON-NLS-1$

		if(optFile.isPresent()) {
			psFile = optFile.get();
		}else {
			return Optional.empty();
		}

		String log;
		File pdfFile;
		final OperatingSystem os = LSystem.INSTANCE.getSystem();

		if(psFile == null || os == null) return Optional.empty();

		// On windows, an option must be defined using this format:
		// -optionName#valueOption Thus, the classical = character must be replaced by a # when latexdraw runs on Windows.
		final String optionEmbed = "-dEmbedAllFonts" + (LSystem.INSTANCE.isWindows() ? "#" : "=") + "true"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		log = LSystem.INSTANCE.execute(new String[]{os.getPs2pdfBinPath(), optionEmbed, psFile.getAbsolutePath(), crop ? name + ExportFormat.PDF.getFileExtension() : pathExportPdf}, tmpDir);

		if(crop) {
			pdfFile = new File(tmpDir.getAbsolutePath() + LResources.FILE_SEP + name + ExportFormat.PDF.getFileExtension());
			log = LSystem.INSTANCE.execute(new String[]{os.getPdfcropBinPath(), pdfFile.getAbsolutePath(), pdfFile.getAbsolutePath()}, tmpDir);
			// JAVA7: test pdfFile.toPath().move(pathExportPdf)
			// the renameto method is weak and fails sometimes.
			if(!pdfFile.renameTo(new File(pathExportPdf)) && !LFileUtils.INSTANCE.copy(pdfFile, new File(pathExportPdf)))
				log += " The final pdf document cannot be moved to its final destination. If you use Windows, you must have a Perl interpretor installed, such as strawberryPerl (http://strawberryperl.com/)"; //$NON-NLS-1$
			pdfFile.delete();
		}

		pdfFile = new File(pathExportPdf);
		psFile.delete();

		if(!pdfFile.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getDocumentCode() + LResources.EOL + log));
			pdfFile = null;
		}

		tmpDir.delete();

		return Optional.ofNullable(pdfFile);
	}


	/**
	 * @return True: The latex parameters must be used by the generated code.
	 * @since 3.0
	 */
	public boolean isWithLatexParams() {
		return withLatexParams;
	}


	/**
	 * Defines if the latex parameters must be used by the generated code.
	 * @param latexParams True: The latex parameters must be used by the generated code.
	 * @since 3.0
	 */
	public void setWithLatexParams(final boolean latexParams) {
		this.withLatexParams = latexParams;
	}


	/**
	 * @return True: comments will be included.
	 * @since 3.0
	 */
	public boolean isWithComments() {
		return withComments;
	}


	/**
	 * Defines if the code must contains comments.
	 * @param comments True: comments will be included.
	 * @since 3.0
	 */
	public void setWithComments(final boolean comments) {
		this.withComments = comments;
	}
}
