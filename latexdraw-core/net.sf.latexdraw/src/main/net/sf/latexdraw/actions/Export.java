package net.sf.latexdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.BMPFilter;
import net.sf.latexdraw.filters.JPGFilter;
import net.sf.latexdraw.filters.PDFFilter;
import net.sf.latexdraw.filters.PNGFilter;
import net.sf.latexdraw.filters.PSFilter;
import net.sf.latexdraw.filters.TeXFilter;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.instruments.Exporter;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.dialog.ExportDialog;

import org.malai.action.Action;

/**
 * This action allows to export a drawing in different formats.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * @author Arnaud Blouin
 * @since 3.0
 */
public class Export extends Action {
	/**
	 * The enumeration defines the different formats managed to export drawing.
	 * @author Arnaud Blouin
	 */
	public static enum ExportFormat {
		/**
		 * The latex format.
		 */
		TEX {
			@Override
			public FileFilter getFilter() {
				return new TeXFilter();
			}

			@Override
			public String getFileExtension() {
				return TeXFilter.TEX_EXTENSION;
			}
		},
		/**
		 * The PDF format.
		 */
		PDF {
			@Override
			public FileFilter getFilter() {
				return new PDFFilter();
			}

			@Override
			public String getFileExtension() {
				return PDFFilter.PDF_EXTENSION;
			}
		},
		/**
		 * The latex format (using latex).
		 */
		EPS_LATEX {
			@Override
			public FileFilter getFilter() {
				return new PSFilter();
			}

			@Override
			public String getFileExtension() {
				return PSFilter.PS_EXTENSION;
			}
		},
		/**
		 * The PDF format (using pdfcrop).
		 */
		PDF_CROP {
			@Override
			public FileFilter getFilter() {
				return new PDFFilter();
			}

			@Override
			public String getFileExtension() {
				return PDFFilter.PDF_EXTENSION;
			}
		},
		/**
		 * The BMP format.
		 */
		BMP {
			@Override
			public FileFilter getFilter() {
				return new BMPFilter();
			}

			@Override
			public String getFileExtension() {
				return BMPFilter.BMP_EXTENSION;
			}
		},
		/**
		 * The PNG format.
		 */
		PNG {
			@Override
			public FileFilter getFilter() {
				return new PNGFilter();
			}

			@Override
			public String getFileExtension() {
				return PNGFilter.PNG_EXTENSION;
			}
		},
		/**
		 * The JPG format.
		 */
		JPG {
			@Override
			public FileFilter getFilter() {
				return new JPGFilter();
			}

			@Override
			public String getFileExtension() {
				return JPGFilter.JPG_EXTENSION;
			}
		};

		/**
		 * @return The file filter corresponding to the format.
		 * @since 3.0
		 */
		public abstract FileFilter getFilter();

		/**
		 * @return The extension corresponding to the format.
		 * @since 3.0
		 */
		public abstract String getFileExtension();
	}


	/** The format with which the drawing must be exported. */
	protected ExportFormat format;

	/** The canvas that contains views. */
	protected ICanvas canvas;

	/** Defines if the shapes have been successfully exported. */
	protected boolean exported;

	/** The dialogue chooser used to select the targeted file. */
	protected ExportDialog dialogueBox;

	/** The PST generator to use. */
	protected PSTCodeGenerator pstGen;



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
		canvas 			= null;
		format 			= null;
		dialogueBox		= null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		// Showing the dialog.
		final int response 	= dialogueBox.showSaveDialog(null);
		File f 				= dialogueBox.getSelectedFile();

		exported = true;

		// Analysing the result of the dialog.
		if(response != JFileChooser.APPROVE_OPTION || f==null)
			exported = false;
		else {
			if(f.getName().toLowerCase().indexOf(format.getFileExtension().toLowerCase()) == -1)
				f = new File(f.getPath() + format.getFileExtension());

			if(f.exists()) {
				int replace = JOptionPane.showConfirmDialog(null,
							LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.173"), //$NON-NLS-1$
							Exporter.TITLE_DIALOG_EXPORT, JOptionPane.YES_NO_OPTION);

				if(replace == JOptionPane.NO_OPTION)
					exported = false; // The user doesn't want to replace the file
			}
		}

		if(exported)
			exported = export(f);
	}


	protected boolean export(final File file) {
		switch(format) {
			case BMP: 		return exportAsBMP(file);
			case EPS_LATEX: return exportAsPS(file);
			case JPG: 		return exportAsJPG(file);
			case PDF: 		return exportAsPDF(file);
			case PDF_CROP: 	return exportAsPDF(file);
			case PNG: 		return exportAsPNG(file);
			case TEX: 		return exportAsPST(file);
		}
		return false;
	}


	@Override
	public boolean canDo() {
		return canvas!=null && format!=null && dialogueBox!=null &&
				(format==ExportFormat.BMP || format==ExportFormat.JPG || format==ExportFormat.PNG || pstGen!=null);
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
	protected boolean exportAsPNG(final File file) {
		final RenderedImage rendImage = createRenderedImage();

		try {
			ImageIO.write(rendImage, "png", file);  //$NON-NLS-1$
			return true;
		}
		catch(IOException e) { BadaboomCollector.INSTANCE.add(e); }
		return false;
	}



	/**
	 * Exports the drawing as a JPG picture.
	 * @param file The targeted location.
	 * @return true if the picture was well created.
	 */
	protected boolean exportAsJPG(final File file) {
		final RenderedImage rendImage = createRenderedImage();

		try {
			final ImageWriteParam iwparam 	= new JPEGImageWriteParam(Locale.getDefault());
			final ImageWriter iw 			= ImageIO.getImageWritersByFormatName("jpg").next();//$NON-NLS-1$
			try(final ImageOutputStream ios = ImageIO.createImageOutputStream(file);){
				iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwparam.setCompressionQuality(dialogueBox.getCompressionRate()/100f);
				iw.setOutput(ios);
				iw.write(null, new IIOImage(rendImage, null, null), iwparam);
				iw.dispose();
				return true;
			}
	    }
		catch(IOException e) { BadaboomCollector.INSTANCE.add(e); }
		return false;
	}



	/**
	 * Creates a ps document of the given views (compiled using latex).
	 * @param file The targeted location.
	 * @return True: the file has been created.
	 * @since 3.0
	 */
	protected boolean exportAsPS(final File file) {
		File psFile;

		try{
			psFile = LaTeXGenerator.createPSFile(canvas.getDrawing(), file.getAbsolutePath(), canvas, pstGen);
		}
		catch(final Exception e) {
			BadaboomCollector.INSTANCE.add(e);
			psFile = null;
		}

		return psFile!=null && psFile.exists();
	}



	/**
	 * Creates a pdf document of the given views (compiled using latex).
	 * @param file The targeted location.
	 * @return True: the file has been created.
	 * @since 3.0
	 */
	protected boolean exportAsPDF(final File file) {
		File pdfFile;

		try{
			pdfFile = LaTeXGenerator.createPDFFile(canvas.getDrawing(), file.getAbsolutePath(), canvas, format==ExportFormat.PDF_CROP, pstGen);
		} catch(final Exception e) {
			BadaboomCollector.INSTANCE.add(e);
			pdfFile = null;
		}

		return pdfFile!=null && pdfFile.exists();
	}


	/**
	 * Exports the drawing as a PST document.
	 * @param file The targeted location.
	 * @return true if the PST document was been successfully created.
	 */
	protected boolean exportAsPST(final File file) {
		boolean ok;

		try {
			try(final FileWriter fw 	= new FileWriter(file);
				final BufferedWriter bw = new BufferedWriter(fw);
				final PrintWriter out 	= new PrintWriter(bw);) {
				out.println(LaTeXGenerator.getLatexDocument(canvas.getDrawing(), canvas, pstGen));
				ok = true;
			}
		}
		catch(final IOException e) {
			BadaboomCollector.INSTANCE.add(e);
			ok = false;
		}
		return ok;
	}



	/**
	 * Exports the drawing as a BMP picture.
	 * @param file The targeted location.
	 * @return true if the picture was successfully created.
	 */
	protected boolean exportAsBMP(final File file){
		final RenderedImage rendImage = createRenderedImage();

		try {
			final ImageWriteParam iwparam	= new BMPImageWriteParam();
			final ImageWriter iw			= ImageIO.getImageWritersByFormatName("bmp").next();//$NON-NLS-1$
			try(final ImageOutputStream ios	= ImageIO.createImageOutputStream(file);) {
				iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iw.setOutput(ios);
				iw.write(null, new IIOImage(rendImage, null, null), iwparam);
				iw.dispose();
				return true;
			}
	    }
		catch(final IOException e) { BadaboomCollector.INSTANCE.add(e); }
		return false;
	}



	/**
	 * @return A buffered image that contains given views (not null).
	 * @since 3.0
	 */
	protected BufferedImage createRenderedImage() {
		final IPoint tr 	= canvas.getTopRightDrawingPoint();
		final IPoint bl 	= canvas.getBottomLeftDrawingPoint();
		final double dec    = 5.;
		BufferedImage bi 	= new BufferedImage((int)(tr.getX()+dec), (int)(bl.getY()+dec), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic 	= bi.createGraphics();
		final double height	= bl.getY()-tr.getY();
		final double width	= tr.getX()-bl.getX();

		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, (int)(tr.getX()+dec), (int)(bl.getY()+dec));

		List<IViewShape> views = canvas.getViews();
		synchronized(views){
			for(IViewShape view : canvas.getViews())
				view.paint(graphic);
		}

		// To delete the empty whitespace, we do a translation to
		// the North-West point of the drawing (dec: to avoid to cut
		// the start of some figures, we let a few white space around the drawing.
		AffineTransform aff = new AffineTransform();
		aff.translate(-bl.getX()+dec, -tr.getY()+dec);

		BufferedImage bufferImage2 = new BufferedImage((int)(width+dec), (int)(height+dec), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic2 = bufferImage2.createGraphics();
		graphic2.setColor(Color.WHITE);
		graphic2.fillRect(0, 0, (int)(width+dec), (int)(height+dec));

		graphic2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphic2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphic2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		graphic2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphic2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// We draw the new picture
		graphic2.drawImage(bi, aff, null);

		graphic.dispose();
		graphic2.dispose();
		bi.flush();

		return bufferImage2;
	}


	/**
	 * @param dialogueBox The file chooser to set.
	 * @since 3.0
	 */
	public void setDialogueBox(final ExportDialog dialogueBox) {
		this.dialogueBox = dialogueBox;
	}


	/**
	 * @param format The format to set.
	 * @since 3.0
	 */
	public void setFormat(final ExportFormat format) {
		this.format = format;
	}


	/**
	 * @param canvas The canvas to set.
	 * @since 3.0
	 */
	public void setCanvas(final ICanvas canvas) {
		this.canvas = canvas;
	}


	/**
	 * @param pstGen The PST generator to use for latex, ps, or pdf exports.
	 * @since 3.0
	 */
	public void setPstGen(final PSTCodeGenerator pstGen) {
		this.pstGen = pstGen;
	}
}
