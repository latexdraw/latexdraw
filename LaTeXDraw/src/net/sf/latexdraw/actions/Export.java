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
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.filechooser.FileFilter;

import org.malai.action.Action;
import org.malai.mapping.MappingRegistry;

import net.sf.latexdraw.badaboom.BordelCollector;
import net.sf.latexdraw.filters.BMPFilter;
import net.sf.latexdraw.filters.JPGFilter;
import net.sf.latexdraw.filters.PDFFilter;
import net.sf.latexdraw.filters.PNGFilter;
import net.sf.latexdraw.filters.PSFilter;
import net.sf.latexdraw.filters.TeXFilter;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.views.Java2D.IShapeView;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;

/**
 * This action allows to export a drawing in different formats.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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


	/** The file where the drawing must be exported. */
	protected File file;

	/** The format with which the drawing must be exported. */
	protected ExportFormat format;

	/** The compression rate of JPG pictures. */
	protected float compressionRate;

	/** The canvas that contains views. */
	protected ICanvas canvas;

	/** The latex packages used to export as PDF and EPS using latex. */
	protected String packages;

	/** The path where the latex binaries are located. */
	protected String latexDistribPath;

	/** Defines if the shapes have been successfully exported. */
	protected boolean exported;



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
		packages 		= null;
		latexDistribPath= null;
		format 			= null;
		file 			= null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		switch(format) {
			case BMP:
				exported = exportAsBMP();
				break;
			case EPS_LATEX:
				exported = exportAsPS();
				break;
			case JPG:
				exported = exportAsJPG();
				break;
			case PDF:
				exported = exportAsPDF();
				break;
			case PDF_CROP:
				exported = exportAsPDF();
				break;
			case PNG:
				exported = exportAsPNG();
				break;
			case TEX:
				exported = exportAsPST();
				break;
		}
	}



	@Override
	public boolean canDo() {
		boolean ok = canvas!=null && format!=null && file!=null;

		// Testing the export as jpg.
		ok = ok && (format!=ExportFormat.JPG || (compressionRate>0f && compressionRate<=1f));
		// Testing the export as PS/PDF using latex.
		ok = ok && ((format!=ExportFormat.EPS_LATEX && format!=ExportFormat.PDF && format!=ExportFormat.PDF_CROP) ||
				(latexDistribPath!=null && packages!=null));

		return ok;
	}



	@Override
	public boolean hadEffect() {
		return super.hadEffect() && exported;
	}



	/**
	 * Exports the drawing as a PNG picture.
	 * @return true if the picture was well created.
	 */
	public boolean exportAsPNG() {
		final RenderedImage rendImage = createRenderedImage();

		try {
			ImageIO.write(rendImage, "png", file);  //$NON-NLS-1$
			return true;
		}
		catch(IOException e) { BordelCollector.INSTANCE.add(e); }
		return false;
	}



	/**
	 * Exports the drawing as a JPG picture.
	 * @return true if the picture was well created.
	 */
	public boolean exportAsJPG() {
		final RenderedImage rendImage = createRenderedImage();

		try {
			final ImageWriteParam iwparam 	= new JPEGImageWriteParam(Locale.getDefault());
			final ImageWriter iw 			= ImageIO.getImageWritersByFormatName("jpg").next();//$NON-NLS-1$
			final ImageOutputStream ios 	= ImageIO.createImageOutputStream(file);

			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwparam.setCompressionQuality(compressionRate);
			iw.setOutput(ios);
			iw.write(null, new IIOImage(rendImage, null, null), iwparam);
			iw.dispose();
			ios.close();

			return true;
	    }
		catch(IOException e) { BordelCollector.INSTANCE.add(e); }
		return false;
	}



	/**
	 * Creates a ps document of the given views (compiled using latex).
	 * @return True: the file has been created.
	 * @since 3.0
	 */
	public boolean exportAsPS() {
		File psFile;

		try{
			psFile = LaTeXGenerator.createPSFile(MappingRegistry.REGISTRY.getSourceFromTarget(canvas, IDrawing.class),
													latexDistribPath, file.getAbsolutePath(), canvas, packages);
		}
		catch(final Exception e) {
			BordelCollector.INSTANCE.add(e);
			psFile = null;
		}

		return psFile!=null && psFile.exists();
	}



	/**
	 * Creates a pdf document of the given views (compiled using latex).
	 * @return True: the file has been created.
	 * @since 3.0
	 */
	public boolean exportAsPDF() {
		File pdfFile;

		try{
			pdfFile = LaTeXGenerator.createPDFFile(MappingRegistry.REGISTRY.getSourceFromTarget(canvas, IDrawing.class),
												latexDistribPath, file.getAbsolutePath(), canvas, packages, format==ExportFormat.PDF);
		} catch(final Exception e) {
			BordelCollector.INSTANCE.add(e);
			pdfFile = null;
		}

		return pdfFile!=null && pdfFile.exists();
	}


	/**
	 * Allows to export the drawing as a PST document.
	 * @return true if the PST document was been successfully created.
	 */
	public boolean exportAsPST() {
		PrintWriter out = null;
		boolean ok;

		try {
			final FileWriter fw 	= new FileWriter(file);
			final BufferedWriter bw = new BufferedWriter(fw);
			out  					= new PrintWriter(bw);

			out.println(LaTeXGenerator.getLatexDocument(MappingRegistry.REGISTRY.getSourceFromTarget(canvas, IDrawing.class), canvas, packages));
			out.close();
			bw.close();
			fw.close();
			ok = true;
		}
		catch(final IOException e) {
			BordelCollector.INSTANCE.add(e);
			ok = false;
		}

		if(out!=null)
			out.close();

		return ok;
	}



	/**
	 * Exports the drawing as a BMP picture.
	 * @return true if the picture was successfully created.
	 */
	public boolean exportAsBMP(){
		final RenderedImage rendImage = createRenderedImage();

		try {
			final ImageWriteParam iwparam	= new BMPImageWriteParam();
			final ImageWriter iw			= ImageIO.getImageWritersByFormatName("bmp").next();//$NON-NLS-1$
			final ImageOutputStream ios		= ImageIO.createImageOutputStream(file);

			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iw.setOutput(ios);
			iw.write(null, new IIOImage(rendImage, null, null), iwparam);
			iw.dispose();
			ios.close();

			return true;
	    }
		catch(final IOException e) { BordelCollector.INSTANCE.add(e); }
		return false;
	}



	/**
	 * @return A buffered image that contains given views (not null).
	 * @since 3.0
	 */
	public BufferedImage createRenderedImage() {
		final IPoint tr 	= canvas.getTopRightDrawingPoint();
		final IPoint bl 	= canvas.getBottomLeftDrawingPoint();
		final double dec    = 5.;
		BufferedImage bi 	= new BufferedImage((int)(tr.getX()+dec), (int)(bl.getY()+dec), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic 	= bi.createGraphics();
		final double height	= bl.getY()-tr.getY();
		final double width	= tr.getX()-bl.getX();

		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, (int)(tr.getX()+dec), (int)(bl.getY()+dec));

		for(IShapeView<?> view : canvas.getViews())
			view.paint(graphic);

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
	 * @param compressionRate The new JPG compression rate
	 * @since 3.0
	 */
	public void setCompressionRate(final float compressionRate) {
		this.compressionRate = compressionRate;
	}



	/**
	 * @param packages The latex packages used during latex export.
	 * @since 3.0
	 */
	public void setPackages(final String packages) {
		this.packages = packages;
	}



	/**
	 * @param latexDistribPath The location of the latex binaries.
	 * @since 3.0
	 */
	public void setLatexDistribPath(final String latexDistribPath) {
		this.latexDistribPath = latexDistribPath;
	}



	/**
	 * @param file the file to set.
	 * @since 3.0
	 */
	public void setFile(final File file) {
		this.file = file;
	}



	/**
	 * @param format the format to set.
	 * @since 3.0
	 */
	public void setFormat(final ExportFormat format) {
		this.format = format;
	}



	/**
	 * @param canvas the canvas to set.
	 * @since 3.0
	 */
	public void setCanvas(final ICanvas canvas) {
		this.canvas = canvas;
	}
}
