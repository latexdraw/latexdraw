package net.sf.latexdraw.glib.views.Java2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.PDFFilter;
import net.sf.latexdraw.filters.PNGFilter;
import net.sf.latexdraw.filters.PSFilter;
import net.sf.latexdraw.filters.TeXFilter;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LNumber;
import net.sf.latexdraw.util.LResources;
import sun.font.FontDesignMetrics;

/**
 * Defines a view of the IText model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class LTextView extends LShapeView<IText> {
	/** The picture. */
	protected Image image;

	/** The log of the compilation. */
	protected String log;

	/** The path of the files: for instance on Unix is can be /tmp/latexdraw180980 (without any extension). */
	private String pathPic;

	/** Used to detect if the last version of the text is different from the view. It helps to update the picture. */
	private String lastText;

	/** Used to detect if the last version of the text is different from the view. It helps to update the picture. */
	private Color lastColour;

	/** Used to detect if the last version of the text is different from the view. It helps to update the picture. */
	private TextPosition lastTextPos;

	public static final Font FONT = new Font("Times New Roman", Font.PLAIN, 18); //$NON-NLS-1$

	public static final FontMetrics FONT_METRICS = FontDesignMetrics.getMetrics(FONT);


	/**
	 * Creates and initialises a text view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is null.
	 * @since 3.0
	 */
	public LTextView(final IText model) {
		super(model);

		log			= ""; //$NON-NLS-1$
		lastText 	= ""; //$NON-NLS-1$
		lastColour 	= null;
		lastTextPos	= null;
		update();
	}


	@Override
	public void update() {
		if((image==null && log.length()==0) || !lastText.equals(shape.getText()) ||
			lastColour==null || !lastColour.equals(shape.getLineColour()) ||
			lastTextPos==null || lastTextPos!=shape.getTextPosition()) {
			updateImage();
			lastText 	= shape.getText();
			lastColour 	= shape.getLineColour();
			lastTextPos	= shape.getTextPosition();
		}

		super.update();
	}


	@Override
	protected void finalize() throws Throwable {
		flush();
		super.finalize();
	}



	@Override
	public void flush() {
		super.flush();

		if(image!=null)
			image.flush();

		if(pathPic!=null) {
			File file = new File(pathPic);
			if(file.exists() && file.canWrite())
				file.delete();
		}

		pathPic  = null;
		image 	 = null;
		lastText = ""; //$NON-NLS-1$
	}



	/**
	 * Updates the image.
	 * @since 3.0
	 */
	public void updateImage() {
		flush();
		image = createImage();
	}



	/**
	 * @return the image.
	 * @since 3.0
	 */
	public Image getImage() {
		return image;
	}



	/**
	 * @return The LaTeX compiled picture of the text or null.
	 * @since 3.0
	 */
	protected Image createImage() {
		BufferedImage bi = null;
		log = ""; //$NON-NLS-1$

		try {
			final String code = shape.getText();

			if(code!=null && !code.isEmpty()) {
				File tmpDir 			= LFileUtils.INSTANCE.createTempDir();
				final String doc      	= getLaTeXDocument();
				pathPic					= tmpDir.getAbsolutePath() + LResources.FILE_SEP + "latexdrawTmpPic" + System.currentTimeMillis(); //$NON-NLS-1$
				final String pathTex  	= pathPic + TeXFilter.TEX_EXTENSION;
				final FileOutputStream fos = new FileOutputStream(pathTex);
				final OutputStreamWriter osw = new OutputStreamWriter(fos);

				try {
					osw.append(doc);
					osw.flush();
					osw.close();
					fos.flush();
					fos.close();

					log  = execute("latex --halt-on-error --interaction=nonstopmode --output-directory=" + tmpDir.getAbsolutePath() + " " + pathTex); //$NON-NLS-1$ //$NON-NLS-2$
					new File(pathTex).delete();
					new File(pathPic + ".aux").delete(); //$NON-NLS-1$
					new File(pathPic + ".log").delete(); //$NON-NLS-1$

					if(log.length()==0) {
						log += execute("dvips " + pathPic + ".dvi -o " + pathPic + PSFilter.PS_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
						new File(pathPic + ".dvi").delete(); //$NON-NLS-1$
					}
					if(log.length()==0)
						log += execute("ps2pdf " + pathPic + PSFilter.PS_EXTENSION + " " + pathPic + PDFFilter.PDF_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
					if(log.length()==0)
						log += execute("pdfcrop " + pathPic + PDFFilter.PDF_EXTENSION + " " + pathPic + PDFFilter.PDF_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
					if(log.length()==0) {
						log += execute("pdftops " + pathPic + PDFFilter.PDF_EXTENSION + " " + pathPic + PSFilter.PS_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
						new File(pathPic + PDFFilter.PDF_EXTENSION).delete();
					}
					if(log.length()==0) {
						log += execute("convert -channel RGBA " + pathPic + PSFilter.PS_EXTENSION + " " + pathPic + PNGFilter.PNG_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
						new File(pathPic + PSFilter.PS_EXTENSION).delete();
					}

					if(log.length()==0) {
						File picFile = new File(pathPic + PNGFilter.PNG_EXTENSION);
						picFile.deleteOnExit();
						bi = ImageIO.read(picFile);
					}
				}catch(final IOException ex) {
					try { fos.flush(); } catch(final IOException ex2) { BadaboomCollector.INSTANCE.add(ex2); }
					try { osw.flush(); } catch(final IOException ex2) { BadaboomCollector.INSTANCE.add(ex2); }
					LFileUtils.INSTANCE.closeStream(fos);
					LFileUtils.INSTANCE.closeStream(osw);
				}
			}
		}
		catch(Exception e) {
			new File(pathPic + TeXFilter.TEX_EXTENSION).delete();
			new File(pathPic + PDFFilter.PDF_EXTENSION).delete();
			new File(pathPic + PSFilter.PS_EXTENSION).delete();
			new File(pathPic + ".dvi").delete(); //$NON-NLS-1$
			new File(pathPic + ".aux").delete(); //$NON-NLS-1$
			new File(pathPic + ".log").delete(); //$NON-NLS-1$
			BadaboomCollector.INSTANCE.add(new FileNotFoundException(log+e.getMessage()));
		}

		return bi;
	}


	/**
	 * @return The precise latex error messages that the latex compilation produced.
	 * @since 3.0
	 */
	public String getLatexErrorMessageFromLog() {
		final Matcher matcher 		= Pattern.compile(".*\r?\n").matcher(log); //$NON-NLS-1$
		final StringBuilder errors 	= new StringBuilder();
		String line;

		while(matcher.find()) {
			line = matcher.group();

			if(line.startsWith("!")) { //$NON-NLS-1$
				errors.append(line.substring(2, line.length()));
				boolean ok = true;
				while(ok && matcher.find()) {
					line = matcher.group();

					if(line.startsWith("l.")) //$NON-NLS-1$
						ok = false;
					else
						errors.append(LResources.EOL).append(line).append(LResources.EOL);
				}
			}
		}

		return errors.toString();
	}


	/**
	 * Executes a given command and returns the log.
	 * @param cmd The command to execute.
	 * @return The log resulting of the command. If not empty the command failed.
	 * @since 3.0
	 */
	private static String execute(final String cmd) {
		StringBuilder log 		= new StringBuilder();
		InputStreamReader isr 	= null;
		BufferedReader br 		= null;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process process;
			process = runtime.exec(cmd);

			boolean ok 		= true;
			int cpt    		= 1;
			int exit   		= 0;

			synchronized(runtime) {
				while(ok && cpt<10)
					try {
						exit = process.exitValue();
						ok = false;
					}
					catch(IllegalThreadStateException e) {
						runtime.wait(10);
						cpt++;
					}
			}

			isr = new InputStreamReader(process.getInputStream());
			br     = new BufferedReader(isr);

			String line = br.readLine();

			while(line!=null) {
				log.append(line).append(LResources.EOL);
				line = br.readLine();
			}

			if(exit==0)
				log.delete(0, log.length());

		}catch(final IOException ex) {
			log.append(ex.getMessage());
		}catch(final InterruptedException ex) {
			log.append(ex.getMessage());
		}

		try{ if(br!=null) br.close(); }   catch(final IOException ex){ log.append(ex.getMessage()); }
		try{ if(isr!=null) isr.close(); } catch(final IOException ex){ log.append(ex.getMessage()); }

		return log.toString();
	}



	/**
	 * @return The LaTeX document that will be compiled in order to get the picture of the text.
	 * @since 3.0
	 */
	public String getLaTeXDocument() {
		final String code		= shape.getText();
		final StringBuffer doc 	= new StringBuffer();
		final Color textColour	= shape.getLineColour();
		final boolean coloured;

		// We must scale the text to fit its latex size: latexdrawDPI/latexDPI is the ratio to scale the
		// created png picture.
		final double scale = IShape.PPC*PSTricksConstants.INCH_VAL_CM/PSTricksConstants.INCH_VAL_PT;

		doc.append("\\documentclass[10pt]{article}\\usepackage[usenames,dvipsnames]{pstricks}"); //$NON-NLS-1$
		doc.append(LaTeXGenerator.getPackages());
		doc.append("\\usepackage[left=0cm,top=0.1cm,right=0cm,nohead,nofoot,paperwidth=100cm,paperheight=100cm]{geometry}");
		doc.append("\\pagestyle{empty}\\begin{document}\\psscalebox{"); //$NON-NLS-1$
		doc.append((float)LNumber.INSTANCE.getCutNumber(scale)).append(' ');
		doc.append((float)LNumber.INSTANCE.getCutNumber(scale)).append('}').append('{');

		if(!textColour.equals(PSTricksConstants.DEFAULT_LINE_COLOR)) {
			String name = DviPsColors.INSTANCE.getColourName(textColour);
			coloured = true;

			if(name==null)
				name = DviPsColors.INSTANCE.addUserColour(textColour);

			doc.append(DviPsColors.INSTANCE.getUsercolourCode(name)).append("\\textcolor{").append(name).append('}').append('{'); //$NON-NLS-1$
		}
		else coloured = false;

		doc.append(code);

		if(coloured)
			doc.append('}');

		doc.append("}\\end{document}"); //$NON-NLS-1$

		return doc.toString();
	}


	@Override
	public boolean intersects(final Rectangle2D rec) {
		if(rec==null)
			return false;

		final Shape sh = getRotatedShape2D(shape.getRotationAngle(), border,
						DrawingTK.getFactory().createPoint(border.getMinX(), border.getMinY()),
						DrawingTK.getFactory().createPoint(border.getMaxX(), border.getMaxY()));
		return sh.contains(rec) || sh.intersects(rec);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return border.contains(x, y);
	}


	private IPoint getTextPositionImage() {
		switch(shape.getTextPosition()) {
			case BOT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/2., shape.getY()-image.getHeight(null));
			case TOP : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null)/2., shape.getY());
			case BOT_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY()-image.getHeight(null));
			case TOP_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY());
			case BOT_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null), shape.getY()-image.getHeight(null));
			case TOP_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-image.getWidth(null), shape.getY());
		}

		return null;
	}


	private IPoint getTextPositionText() {
		TextLayout tl = new TextLayout(shape.getText(), FONT, FONT_METRICS.getFontRenderContext());
		Rectangle2D bounds = tl.getBounds();

		switch(shape.getTextPosition()) {
			case BOT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth()/2., shape.getY());
			case TOP : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth()/2., shape.getY()+bounds.getHeight());
			case BOT_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY());
			case TOP_LEFT : return DrawingTK.getFactory().createPoint(shape.getX(), shape.getY()+bounds.getHeight());
			case BOT_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth(), shape.getY());
			case TOP_RIGHT : return DrawingTK.getFactory().createPoint(shape.getX()-bounds.getWidth(), shape.getY()+bounds.getHeight());
		}

		return null;
	}



	@Override
	public void paint(final Graphics2D g) {
		if(g==null)
			return ;

		final IPoint p = beginRotation(g);
		final IPoint position = image==null ? getTextPositionText() : getTextPositionImage();

		if(image==null) {
			g.setColor(shape.getLineColour());
			g.setFont(FONT);
			g.drawString(shape.getText(), (int)position.getX(), (int)position.getY());
		}
		else
			g.drawImage(image, (int)position.getX(), (int)position.getY(), null);

		if(p!=null)
			endRotation(g, p);
	}



	@Override
	public void updateBorder() {
		final IPoint position = image==null ? getTextPositionText() : getTextPositionImage();

		if(image==null) {
			TextLayout tl = new TextLayout(shape.getText(), FONT, FONT_METRICS.getFontRenderContext());
			Rectangle2D bounds = tl.getBounds();
			border.setFrame(position.getX(), position.getY()-bounds.getHeight(), bounds.getWidth(), bounds.getHeight());
		}
		else {
			final double height = image.getHeight(null);
			border.setFrame(position.getX(), position.getY(), image.getWidth(null), height);
		}
	}


	@Override
	protected void updateDblePathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathOutside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathOutside() {
		// Nothing to do.
	}
}
