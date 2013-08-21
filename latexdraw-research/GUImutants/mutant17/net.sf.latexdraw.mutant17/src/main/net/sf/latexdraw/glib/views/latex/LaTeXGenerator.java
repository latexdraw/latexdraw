package net.sf.latexdraw.glib.views.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.PDFFilter;
import net.sf.latexdraw.filters.PSFilter;
import net.sf.latexdraw.filters.TeXFilter;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LSystem.OperatingSystem;
import net.sf.latexdraw.util.StreamExecReader;

import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.properties.Modifiable;

/**
 * Defines an abstract LaTeX generator.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/23/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class LaTeXGenerator implements Modifiable {

	/** Defines the number of characters added at the beginning
	 * of each lines of the comment (these characters are "% "). */
	public static final int LGTH_START_LINE_COMMENT = 2;

	/**
	 * The latex packages used when exporting using latex.
	 * These packages are defined for the current document but not for all documents.
	 */
	protected static final IUnary<String> PACKAGES = new ActiveUnary<>("");



	/**
	 * @param packages the packages to set.
	 * @since 3.0
	 */
	public static void setPackages(final String packages) {
		if(packages!=null && !packages.equals(getPackages()))
			LaTeXGenerator.PACKAGES.setValue(packages);
	}


	/**
	 * @return the packages.
	 * @since 3.0
	 */
	public static String getPackages() {
		return PACKAGES.getValue();
	}


	/**
	 * @return The unary relation that contains the packages value.
	 * @since 3.0
	 */
	public static IUnary<String> getPackagesUnary() {
		return PACKAGES;
	}


	/**
	 * The different vertical positions.
	 */
	public static enum VerticalPosition {
		TOP {
			@Override
			public String getToken() { return "t"; }//$NON-NLS-1$
		}, BOTTOM {
			@Override
			public String getToken() { return "b"; }//$NON-NLS-1$
		}, FLOATS_PAGE {
			@Override
			public String getToken() { return "p"; }//$NON-NLS-1$
		}, HERE {
			@Override
			public String getToken() { return "h"; }//$NON-NLS-1$
		}, HERE_HERE {
			@Override
			public String getToken() { return "H"; }//$NON-NLS-1$
		}, NONE {
			@Override
			public String getToken() { return ""; }//$NON-NLS-1$
		};


		@Override
		public String toString() {
			return getToken();
		}

		/**
		 * @return The token corresponding to the placement.
		 * @since 3.0
		 */
		public abstract String getToken();


		/**
		 * @param pos The position token to check.
		 * @return The corresponding vertical position.
		 * @since 3.0
		 */
		public static VerticalPosition getPosition(final String pos) {
			if(pos==null)
				return null;

			if(pos.equals(TOP.getToken()))
				return TOP;

			if(pos.equals(BOTTOM.getToken()))
				return BOTTOM;

			if(pos.equals(FLOATS_PAGE.getToken()))
				return FLOATS_PAGE;

			if(pos.equals(HERE.getToken()))
				return HERE;

			if(pos.equals(HERE_HERE.getToken()))
				return HERE_HERE;

			if(pos.equals(NONE.getToken()))
				return NONE;

			return null;
		}
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


	/**
	 * Initialises the abstract generator.
	 * @since 3.0
	 */
	public LaTeXGenerator() {
		super();

		modified= false;
		comment = ""; //$NON-NLS-1$
		label   = ""; //$NON-NLS-1$
		caption = ""; //$NON-NLS-1$
		positionHoriCentre = false;
		positionVertToken  = VerticalPosition.NONE;
		scale = 1.;
	}


	/**
	 * @return the scale of the drawing.
	 * @since 3.0
	 */
	public double getScale() {
		return scale;
	}



	/**
	 * @param scale the scale to set.
	 * @since 3.0
	 */
	public void setScale(final double scale) {
		if(scale>=0.1)
			this.scale = scale;
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
		int i=0, j=0, lgth = comment.length();
		char buffer[] = new char[lgth];
		boolean eol   = true;

		while(i<lgth) {
			if(eol && comment.charAt(i)=='%') {
				i+=LGTH_START_LINE_COMMENT;
				eol = false;
			}
			else {
				if(comment.charAt(i)=='\n')
					eol = true;

				buffer[j++] = comment.charAt(i);
				i++;
			}
		}

		String str = String.valueOf(buffer, 0, j);

		return str.length()>1 ? str.substring(0, str.length()-LResources.EOL.length()) : str;
	}


	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;
	}


	/**
	 * @param newComments the comment to set.
	 * @since 3.0
	 */
	public void setComment(final String newComments) {
		if(newComments!=null && newComments.length()>0) {
			int i, j=0, lgth = newComments.length();
			char buffer[] = new char[lgth*3];
			boolean eol = true;

			for(i=0; i<newComments.length(); i++) {
				if(eol) {
					buffer[j++] = '%';
					buffer[j++] = ' ';
					eol = false;
				}

				if(newComments.charAt(i)=='\n')
					eol = true;

				buffer[j++] = newComments.charAt(i);
			}

			comment = String.valueOf(buffer, 0, j);
			comment+=LResources.EOL;
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
	 * @param positionVertToken The new vertical position token. Must not be null.
	 * @since 3.0
	 */
	public void setPositionVertToken(final VerticalPosition positionVertToken) {
		if(positionVertToken!=null) {
			this.positionVertToken = positionVertToken;
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
	 * @param label the new label of the drawing. Must not be null.
	 * @since 3.0
	 */
	public void setLabel(final String label) {
		if(label!=null) {
			this.label = label;
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
	 * @param caption the new caption of the drawing. Must not be null.
	 * @since 3.0
	 */
	public void setCaption(final String caption) {
		if(caption!=null) {
			this.caption = caption;
			setModified(true);
		}
	}



	/**
	 * @param positionHoriCentre True: the latex drawing will be horizontally centred.
	 * @since 3.0
	 */
	public void setPositionHoriCentre(final boolean positionHoriCentre) {
		if(this.positionHoriCentre!=positionHoriCentre) {
			this.positionHoriCentre = positionHoriCentre;
			setModified(true);
		}
	}



	/**
	 * Updates the code cache.
	 * @since 3.0
	 */
	public abstract void update();




	/**
	 * Generates a latex document that contains the pstricks code of the given canvas.
	 * @param drawing The shapes to export.
	 * @param synchronizer The object that synchronises the view and the model.
	 * @param pstGen The PST generator to use.
	 * @return The latex document or an empty string.
	 * @since 3.0
	 */
	public static String getLatexDocument(final IDrawing drawing, final ViewsSynchroniserHandler synchronizer, final PSTCodeGenerator pstGen) {
		if(drawing==null || synchronizer==null)
			return ""; //$NON-NLS-1$

		final StringBuilder doc = new StringBuilder();
		final IPoint bl			= synchronizer.getBottomLeftDrawingPoint();
		final IPoint tr			= synchronizer.getTopRightDrawingPoint();
		final float ppc			= synchronizer.getPPCDrawing();
		final float scale		= (float)pstGen.getScale();

		pstGen.updateFull();
		doc.append("\\documentclass{article}").append(LResources.EOL).append("\\pagestyle{empty}").append(LResources.EOL).append(getPackages()).append(LResources.EOL).append( //$NON-NLS-1$ //$NON-NLS-2$
		"\\usepackage[left=0cm,top=0.1cm,right=0cm,bottom=0cm,nohead,nofoot,paperwidth=").append( //$NON-NLS-1$
		(tr.getX()/ppc)*scale).append("cm,paperheight=").append( //$NON-NLS-1$
		(bl.getY()/ppc)*scale+0.2).append("cm]{geometry}").append( //$NON-NLS-1$
		LResources.EOL).append("\\usepackage[usenames,dvipsnames]{pstricks}").append(//$NON-NLS-1$
		LResources.EOL).append("\\usepackage{epsfig}").append(//$NON-NLS-1$//$NON-NLS-2$
		LResources.EOL).append("\\usepackage{pst-grad}").append(LResources.EOL).append("\\usepackage{pst-plot}").append(LResources.EOL).append(//$NON-NLS-1$//$NON-NLS-2$
		"\\begin{document}").append(LResources.EOL).append( //$NON-NLS-1$
		"\\addtolength{\\oddsidemargin}{-0.2in}").append(LResources.EOL).append("\\addtolength{\\evensidemargin}{-0.2in}").append( //$NON-NLS-1$ //$NON-NLS-2$
		LResources.EOL).append(pstGen.getCache()).append(LResources.EOL).append("\\end{document}");//$NON-NLS-1$

		return doc.toString();
	}



	/**
	 * Creates a latex file that contains the pstricks code of the given canvas.
	 * @param drawing The shapes to export.
	 * @param pathExportTex The location where the file must be created.
	 * @param synchronizer The object that synchronises the view and the model.
	 * @param pstGen The PST generator to use.
	 * @return The latex file or null.
	 * @since 3.0
	 */
	public static File createLatexFile(final IDrawing drawing, final String pathExportTex, final ViewsSynchroniserHandler synchronizer, final PSTCodeGenerator pstGen) {
		if(drawing==null || pathExportTex==null)
			return null;

		boolean ok = true;

		try {
			try(FileOutputStream fos = new FileOutputStream(pathExportTex);
				OutputStreamWriter osw = new OutputStreamWriter(fos)){
				osw.append(getLatexDocument(drawing, synchronizer, pstGen));
			}
		} catch(final IOException ex) { ok = false; }

		return ok ? new File(pathExportTex) : null;
	}



	/**
	 * Create a .ps file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param drawing The shapes to export.
	 * @param pathExportPs The path of the .ps file to create (MUST ends with .ps).
	 * @param synchronizer The object that synchronises the view and the model.
	 * @param pstGen The PST generator to use.
	 * @return The create file or null.
	 * @since 3.0
	 */
	public static File createPSFile(final IDrawing drawing, final String pathExportPs, final ViewsSynchroniserHandler synchronizer, final PSTCodeGenerator pstGen){
		return createPSFile(drawing, pathExportPs, synchronizer, null, pstGen);
	}



	/**
	 * Create a .ps file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param drawing The shapes to export.
	 * @param pathExportPs The path of the .ps file to create (MUST ends with .ps).
	 * @param synchronizer The object that synchronises the view and the model.
	 * @param tmpDir The temporary directory used for the compilation.
	 * @param pstGen The PST generator to use.
	 * @return The create file or null.
	 * @since 3.0
	 */
	public static File createPSFile(final IDrawing drawing, final String pathExportPs, final ViewsSynchroniserHandler synchronizer, final File tmpDir,
			final PSTCodeGenerator pstGen) {
		if(pathExportPs==null)
			return null;

		int lastSep			= pathExportPs.lastIndexOf(LResources.FILE_SEP)+1;
		String name			= pathExportPs.substring(lastSep==-1 ? 0 : lastSep, pathExportPs.lastIndexOf(PSFilter.PS_EXTENSION));
		File tmpDir2		= tmpDir==null ? LFileUtils.INSTANCE.createTempDir() : tmpDir;
		final float scale	= (float)pstGen.getScale();

		if(tmpDir2==null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //$NON-NLS-1$
			return null;
		}

		String path		= tmpDir2.getAbsolutePath() + LResources.FILE_SEP;
		File texFile    = createLatexFile(drawing, path + name + TeXFilter.TEX_EXTENSION, synchronizer, pstGen);
		String log;
		File finalPS;
		IPoint tr		= synchronizer.getTopRightDrawingPoint();
		IPoint bl		= synchronizer.getBottomLeftDrawingPoint();
		int ppc			= synchronizer.getPPCDrawing();
		float dec		= 0.2f;
		final OperatingSystem os = LSystem.INSTANCE.getSystem();

		if(texFile==null || !texFile.exists())
			return null;

		String[] paramsLatex = new String[] {os.getLatexBinPath(), "--interaction=nonstopmode", "--output-directory=" + tmpDir2.getAbsolutePath(),//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
				texFile.getAbsolutePath()};
		log    = execute(paramsLatex, tmpDir2);
		File dviFile = new File(tmpDir2.getAbsolutePath() + LResources.FILE_SEP + name + ".dvi"); //$NON-NLS-1$
		boolean dviRenamed = dviFile.renameTo(new File(tmpDir2.getAbsolutePath() + LResources.FILE_SEP + name));

		String[] paramsDvi = new String[] {os.getDvipsBinPath(), "-Pdownload35", "-T", //$NON-NLS-1$ //$NON-NLS-2$
				(((tr.getX()-bl.getX())/ppc)*scale+dec)+"cm,"+(((bl.getY()-tr.getY())/ppc)*scale+dec)+"cm", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						name, "-o", pathExportPs}; //$NON-NLS-1$
		log   += execute(paramsDvi, tmpDir2);

		texFile.delete();
		new File(path + name + (dviRenamed ? "" : ".div")).delete();	//$NON-NLS-1$ //$NON-NLS-2$
		new File(path + name + ".log").delete();						//$NON-NLS-1$
		new File(path + name + ".aux").delete();						//$NON-NLS-1$

		finalPS = new File(pathExportPs);

		if(!finalPS.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getLatexDocument(drawing, synchronizer, pstGen) + LResources.EOL + log));
			finalPS = null;
		}

		if(tmpDir==null)
			tmpDir2.delete();

		return finalPS;
	}



	/**
	 * Create a .pdf file that corresponds to the compiled latex document containing
	 * the pstricks drawing.
	 * @param drawing The shapes to export.
	 * @param pathExportPdf The path of the .pdf file to create (MUST ends with .pdf).
	 * @param synchronizer The object that synchronises the view and the model.
	 * @param pstGen The PST generator to use.
	 * @return The create file or null.
	 * @param crop if true, the output document will be cropped.
	 * @since 3.0
	 */
	public static File createPDFFile(final IDrawing drawing, final String pathExportPdf, final ViewsSynchroniserHandler synchronizer, final boolean crop,
			final PSTCodeGenerator pstGen) {
		if(pathExportPdf==null)
			return null;

		File tmpDir = LFileUtils.INSTANCE.createTempDir();

		if(tmpDir==null) {
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Cannot create a temporary folder.")); //$NON-NLS-1$
			return null;
		}

		String name = pathExportPdf.substring(pathExportPdf.lastIndexOf(LResources.FILE_SEP)+1, pathExportPdf.lastIndexOf(PDFFilter.PDF_EXTENSION));
		File psFile = createPSFile(drawing, tmpDir.getAbsolutePath() + LResources.FILE_SEP + name + PSFilter.PS_EXTENSION, synchronizer, tmpDir, pstGen);
		String log;
		File pdfFile;
		final OperatingSystem os = LSystem.INSTANCE.getSystem();

		if(psFile==null)
			return null;

		// On windows, an option must be defined using this format:
		// -optionName#valueOption Thus, the classical = character must be replaced by a # when latexdraw runs on Windows.
		String optionEmbed = "-dEmbedAllFonts" + (LSystem.INSTANCE.isWindows() ? "#" : "=") + "true"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		log = execute(new String[] {os.getPs2pdfBinPath(), optionEmbed, psFile.getAbsolutePath(), //$NON-NLS-1$
							crop ? name + PDFFilter.PDF_EXTENSION : pathExportPdf}, tmpDir);

		if(crop) {
			pdfFile = new File(tmpDir.getAbsolutePath() + LResources.FILE_SEP + name + PDFFilter.PDF_EXTENSION);
			log 	= execute(new String[] {os.getPdfcropBinPath(), pdfFile.getAbsolutePath(), pdfFile.getAbsolutePath()}, tmpDir); //$NON-NLS-1$
			// JAVA7: test pdfFile.toPath().move(pathExportPdf)
			// the renameto method is weak and fails sometimes.
			if(!pdfFile.renameTo(new File(pathExportPdf)) && !LFileUtils.INSTANCE.copy(pdfFile, new File(pathExportPdf)))
				log += " The final pdf document cannot be moved to its final destination. If you use Windows, you must have a Perl interpretor installed, such as strawberryPerl (http://strawberryperl.com/)"; //$NON-NLS-1$
			pdfFile.delete();
		}

		pdfFile = new File(pathExportPdf);
		psFile.delete();

		if(!pdfFile.exists()) {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException(getLatexDocument(drawing, synchronizer, pstGen) + LResources.EOL + log));
			pdfFile = null;
		}

		tmpDir.delete();

		return pdfFile;
	}


	private static String execute(final String[] cmd, final File tmpdir) {
		if(cmd==null || cmd.length==0)
			return null;

		try {
			Process process 	 = Runtime.getRuntime().exec(cmd, null, tmpdir);  // Command launched
			StreamExecReader err = new StreamExecReader(process.getErrorStream());// Catch the error log
			StreamExecReader inp = new StreamExecReader(process.getInputStream());// Catch the log

			err.start();
			inp.start();

			process.waitFor();// Waiting for the end of the process.

			return err.getLog() + LResources.EOL + inp.getLog();
		} catch(Exception e) {
			BadaboomCollector.INSTANCE.add(e);
			return ""; //$NON-NLS-1$
		}
	}
}
