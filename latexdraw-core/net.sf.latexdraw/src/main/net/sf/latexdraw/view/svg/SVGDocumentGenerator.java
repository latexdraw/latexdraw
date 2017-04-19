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
package net.sf.latexdraw.view.svg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressBar;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.instruments.ExceptionsManager;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGMetadataElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.ui.OpenSaver;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The SVG document generator of the app.
 * @author Arnaud BLOUIN
 */
public final class SVGDocumentGenerator implements OpenSaver<Label> {
	/** The singleton for saving and loading latexdraw SVG documents. */
	public static final SVGDocumentGenerator INSTANCE = new SVGDocumentGenerator();

	private SVGDocumentGenerator() {
		super();
	}


	@Override
	public Task<Boolean> save(final String path, final ProgressBar progressBar, final Label statusBar) {
		final SaveWorker sw = new SaveWorker(path, statusBar, true, false, progressBar);
		progressBar.progressProperty().bind(sw.progressProperty());
		new Thread(sw).start();
		return sw;
	}


	@Override
	public Task<Boolean> open(final String path, final ProgressBar progressBar, final Label statusBar) {
		final LoadWorker lw = new LoadWorker(path, statusBar, progressBar);
		progressBar.progressProperty().bind(lw.progressProperty());
		new Thread(lw).start();
		return lw;
	}


	/**
	 * Exports the selected shapes as a template.
	 * @param path The path where the template will be saved.
	 * @param progressBar The progress bar.
	 * @param statusBar The status bar.
	 * @param templateMenu The menu that contains the template menu items.
	 */
	public void saveTemplate(final String path, final ProgressBar progressBar, final Label statusBar, final Menu templateMenu) {
		final SaveTemplateWorker stw = new SaveTemplateWorker(path, statusBar, templateMenu, progressBar);
		progressBar.progressProperty().bind(stw.progressProperty());
		new Thread(stw).start();
	}


	/**
	 * Inserts a set of shapes into the drawing.
	 * @param path The file of the SVG document to load.
	 */
	public IShape insert(final String path) {
		final InsertWorker worker = new InsertWorker(path);
		new Thread(worker).start();

		try {
			if(worker.get()) {
				return worker.getInsertedShapes();
			}
		}catch(final InterruptedException | ExecutionException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
		return null;
	}


	/**
	 * Updates the templates.
	 * @param templatesMenu The menu that contains the templates.
	 * @param updatesThumbnails True: the thumbnails of the template will be updated.
	 */
	public void updateTemplates(final Menu templatesMenu, final boolean updatesThumbnails) {
		//			new UpdateTemplatesWorker(templatesMenu, updatesThumbnails).execute();
	}


	/**
	 * The abstract worker that factorises the code of loading and saving workers.
	 */
	private abstract static class IOWorker extends Task<Boolean> {
		protected final Label statusBar;
		protected final String path;
		protected final ProgressBar progressBar;
		private final Map<JfxInstrument, Boolean> instrumentsState;
		private final List<JfxInstrument> instruments;
		/** set the ui as modified after the work? */
		protected boolean setModified;

		IOWorker(final String ioPath, final Label status, final ProgressBar bar) {
			super();
			path = ioPath;
			statusBar = status;
			setModified = false;
			progressBar = bar;
			instrumentsState = new HashMap<>();
			instruments = new ArrayList<>();
		}

		/**
		 * @return The name of the SVG document.
		 */
		protected String getDocumentName() {
			String name;

			if(path == null) {
				name = ""; //$NON-NLS-1$
			}else {
				name = new File(path).getName();
				final int indexSVG = name.lastIndexOf(".svg");

				if(indexSVG != -1) {
					name = name.substring(0, indexSVG);
				}
			}

			return name;
		}

		@Override
		protected Boolean call() throws Exception {
			progressBar.setVisible(true);

			Platform.runLater(() -> LaTeXDraw.getINSTANCE().getInstruments().stream().filter(ins -> !(ins instanceof ExceptionsManager))
				.forEach(ins -> {
				instrumentsState.put(ins, ins.isActivated());
				instruments.add(ins);
				ins.setActivated(false, true);
			}));

			return true;
		}

		@Override
		protected void done() {
			super.done();
			Platform.runLater(() -> instruments.forEach(ins -> ins.setActivated(instrumentsState.getOrDefault(ins, false))));
			if(progressBar != null) {
				progressBar.progressProperty().unbind();
				progressBar.setVisible(false);
			}
			LaTeXDraw.getINSTANCE().setModified(setModified);
		}
	}


	//	/** Abstract class dedicated to the support of templates. */
	//    abstract static class TemplatesWorker extends LoadShapesWorker {
	//		protected TemplatesWorker(final LFrame ui, final String path, final JLabel statusBar) {
	//			super(ui, path, statusBar);
	//		}
	//
	//		/**
	//	     * Creates a menu item using the name of the thumbnail.
	//	     * @param nameThumb The name of the thumbnail.
	//	     * @return The created menu item.
	//	     */
	//	    protected MMenuItem createTemplateMenuItem(final String svgPath, final String nameThumb, final String pathPic) {
	//	    	MMenuItem menu = null;
	//	    	ImageIcon icon;
	//	    	final String pngPath = pathPic+File.separator+nameThumb;
	//	    	final int id = nameThumb.lastIndexOf(SVGFilter.SVG_EXTENSION+PNGFilter.PNG_EXTENSION);
	//
	//	    	try {
	//	    		final Image image = ImageIO.read(new File(pngPath));
	//	    		icon = new ImageIcon(image);
	//	    		image.flush();
	//	    	}
	//    		catch(final Exception e) {
	//    			icon = LResources.EMPTY_ICON;
	//	    	}
	//
	//			if(id!=-1) {
	//				menu = new MMenuItem(nameThumb.substring(0, id), icon);
	//				menu.setName(svgPath);
	//			}
	//
	//	    	return menu;
	//	    }
	//	}


	/** This worker inserts the given set of shapes into the drawing. */
	private static class InsertWorker extends LoadShapesWorker {
		private IShape insertedShapes;

		InsertWorker(final String path) {
			super(path, null, null);
			setModified = true;
			insertedShapes = null;
		}

		@Override
		protected Boolean call() throws Exception {
			super.call();
			try {
				final SVGDocument svgDoc = new SVGDocument(new File(path).toURI());
				final IDrawing drawing = LaTeXDraw.getINSTANCE().getInjector().getInstance(IDrawing.class);
				final List<IShape> shapes = toLatexdraw(svgDoc, 0);

				if(shapes.size() == 1) {
					insertedShapes = shapes.get(0);
				}else {
					final IGroup gp = ShapeFactory.INST.createGroup();
					shapes.forEach(sh -> gp.addShape(sh));
					insertedShapes = gp;
				}

				Platform.runLater(() -> {
					drawing.addShape(insertedShapes);
					// Updating the possible widgets of the instruments.
					LaTeXDraw.getINSTANCE().getInstruments().forEach(ins -> ins.interimFeedback());
					//				ui.updatePresentations();
				});
				return true;
			}catch(final Exception e) {
				BadaboomCollector.INSTANCE.add(e);
				return false;
			}
		}

		protected IShape getInsertedShapes() {
			return insertedShapes;
		}
	}


	//	/** This worker updates the templates. */
	//	static class UpdateTemplatesWorker extends TemplatesWorker {
	//		protected MMenu templatesMenu;
	//
	//		protected boolean updateThumbnails;
	//
	//		protected UpdateTemplatesWorker(final MMenu templatesMenu, final boolean updateThumbnails) {
	//			super(null, null, null);
	//			this.templatesMenu = templatesMenu;
	//			this.updateThumbnails = updateThumbnails;
	//		}
	//
	//
	//		@Override
	//		protected Boolean doInBackground() throws Exception {
	//			super.doInBackground();
	//
	//			if(updateThumbnails) {
	//				updateTemplates(LPath.PATH_TEMPLATES_DIR_USER, LPath.PATH_CACHE_DIR);
	//				updateTemplates(LPath.PATH_TEMPLATES_SHARED, LPath.PATH_CACHE_SHARE_DIR);
	//			}
	//
	//			// Removing the former menu items but the last two of them (the update menu item and the separator).
	//			for(int i=0, size=templatesMenu.getMenuComponentCount()-2; i<size; i++)
	//				templatesMenu.remove(0);
	//
	//			createMenuItems(LPath.PATH_TEMPLATES_DIR_USER, LPath.PATH_CACHE_DIR, true);
	//			createMenuItems(LPath.PATH_TEMPLATES_SHARED, LPath.PATH_CACHE_SHARE_DIR, true);
	//
	//			return true;
	//		}
	//
	//
	//		/**
	//		 * fills the template menu with menu item gathered from the given directory of templates.
	//		 * @param pathTemplate The path of the templates.
	//		 * @param pathCache The path of the cache of the templates.
	//		 * @param sharedTemplates True if the templates are shared templates (in the shared directory).
	//		 */
	//		protected void createMenuItems(final String pathTemplate, final String pathCache, final boolean sharedTemplates) {
	//			final SVGFilter filter = new SVGFilter();
	//			final File[] files = new File(pathTemplate).listFiles();
	//
	//			if(files!=null)
	//				for(int i=0; i<files.length; i++)
	//					if(filter.accept(files[i]) && !files[i].isDirectory()) {
	//						final MMenuItem menu = createTemplateMenuItem(files[i].getPath(), files[i].getName()+PNGFilter.PNG_EXTENSION,
	// pathCache);
	//
	//						if(menu!=null)
	//							templatesMenu.add(menu, i);
	//					}
	//		}
	//
	//
	//		/**
	//		 * Updates the templates from the given path, in the given cache path.
	//		 * @param pathTemplate The path of the templates to update.
	//		 * @param pathCache The path where the cache of the thumbnails of the templates will be stored.
	//		 */
	//		protected void updateTemplates(final String pathTemplate, final String pathCache) {
	//			final File templateDir = new File(pathTemplate);
	//
	//			if(!templateDir.isDirectory())
	//				return; // There is no template
	//
	//			// We get the list of the templates
	//			final SVGFilter filter = new SVGFilter();
	//			final File[] files = templateDir.listFiles();
	//				if(files!=null)
	//	{
	//		Stream.of(files).filter(f -> filter.accept(f)).forEach(file -> {
	//			try {
	//				IGroup template = ShapeFactory.INST.createGroup();
	//				List<IShape> shapes = toLatexdraw(new SVGDocument(file.toURI()), 0);
	//				shapes.forEach(sh -> template.addShape(sh));
	//				File thumbnail = new File(pathCache + File.separator + file.getName() + PNGFilter.PNG_EXTENSION);
	//				createTemplateThumbnail(thumbnail, template);
	//			}catch(final Exception ex) {BadaboomCollector.INSTANCE.add(ex);}
	//		});
	//	}
	//		}
	//
	//
	//		/**
	//		 * Creates a thumbnail from the given selection in the given file.
	//		 * @param templateFile The file of the future thumbnail.
	//		 * @param selection The set of shapes composing the template.
	//		 */
	//		protected void createTemplateThumbnail(final File templateFile, final IShape selection) {
	//			final IPoint br = selection.getBottomRightPoint();
	//			final IPoint tl = selection.getTopLeftPoint();
	//			final double dec = 8.;
	//			final double width = Math.abs(br.getX()-tl.getX())+2*dec;
	//			final double height = Math.abs(br.getY()-tl.getY())+2*dec;
	//			final double maxSize = 20.;
	//			final BufferedImage bufferImage = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
	//			final Graphics2D graphic = bufferImage.createGraphics();
	//			final double scale = Math.min(maxSize/width, maxSize/height);
	//			final BufferedImage bufferImage2 = new BufferedImage((int)maxSize, (int)maxSize, BufferedImage.TYPE_INT_ARGB);
	//			final Graphics2D graphic2 = bufferImage2.createGraphics();
	////			final IViewShape view = View2DTK.getFactory().createView(selection);
	//			final AffineTransform aff = new AffineTransform();
	//			final int MAX_CPT = 100;
	//			int cpt = 0;
	//
	//			graphic.setColor(Color.WHITE);
	//			graphic.fillRect(0, 0, (int)width, (int)height);
	//			graphic.scale(scale, scale);
	//			graphic.translate(-tl.getX()+dec, -tl.getY()+dec);
	//
	//			aff.translate(0, 0);
	//
	////			view.paint(graphic, null);
	////
	////			// Waiting for the producing of the thumbnails before flushing them.
	////			while(FlyweightThumbnail.hasThumbnailsInProgress() && cpt<MAX_CPT) {
	////				try {
	////					Thread.sleep(100);
	////					cpt++;
	////				}catch(final InterruptedException ex) {
	////					BadaboomCollector.INSTANCE.add(ex);
	////				}
	////			}
	////
	////			view.flush();
	//
	//			graphic2.setColor(Color.WHITE);
	//			graphic2.fillRect(0, 0, (int)maxSize, (int)maxSize);
	//			graphic2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	//			graphic2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	//			graphic2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	//			graphic2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	//			graphic2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	//
	//			// Drawing the template
	//			graphic2.drawImage(bufferImage, aff, null);
	//
	//			// Creation of the png file with the second picture
	//			final ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	//			final ImageWriter iw = ImageIO.getImageWritersByFormatName("png").next();//$NON-NLS-1$
	//
	//			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	//			iwparam.setCompressionQuality(1);
	//
	//			try {
	//				try(final ImageOutputStream ios = ImageIO.createImageOutputStream(templateFile)){
	//					iw.setOutput(ios);
	//					iw.write(null, new IIOImage(bufferImage2, null, null), iwparam);
	//					iw.dispose();
	//				}
	//			}catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
	//
	//			graphic.dispose();
	//			graphic2.dispose();
	//			bufferImage.flush();
	//			bufferImage2.flush();
	//		}
	//	}


	static class SaveTemplateWorker extends SaveWorker {
		protected final Menu templateMenu;

		SaveTemplateWorker(final String path, final Label statusBar, final Menu templateContainer, final ProgressBar bar) {
			super(path, statusBar, false, true, bar);
			templateMenu = templateContainer;
		}

		@Override
		protected void done() {
			super.done();
			//				INSTANCE.updateTemplates(templateMenu, true);
		}
	}


	/** This worker saves the given document. */
	private static class SaveWorker extends IOWorker {
		/** Defines if the parameters of the drawing (instruments, presentations, etc.) must be saved. */
		private final boolean saveParameters;
		/** Specifies if only the selected shapes must be saved. */
		private final boolean onlySelection;

		SaveWorker(final String path, final Label statusBar, final boolean saveParams, final boolean onlySelected, final ProgressBar bar) {
			super(path, statusBar, bar);
			saveParameters = saveParams;
			onlySelection = onlySelected;
		}

		/**
		 * Creates an SVG document from a drawing.
		 * @param drawing The drawing to convert in SVG.
		 * @return The created SVG document or null.
		 */
		private SVGDocument toSVG(final IDrawing drawing, final double incr) {
			// Creation of the SVG document.
			final List<IShape> shapes = onlySelection ? drawing.getSelection().getShapes() : drawing.getShapes();
			final SVGDocument doc = new SVGDocument();
			final SVGSVGElement root = doc.getFirstChild();
			final SVGGElement g = new SVGGElement(doc);

			root.appendChild(g);
			root.setAttribute("xmlns:" + LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);//$NON-NLS-1$
			root.appendChild(new SVGDefsElement(doc));

			try {
				shapes.forEach(sh -> {
					// For each shape an SVG element is created.
					SVGElement elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);
					if(elt != null) {
						g.appendChild(elt);
					}
					Platform.runLater(() -> updateProgress(getProgress() + incr, 100d));
				});
			}catch(final Exception ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}

			// Setting SVG attributes to the created document.
			root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");//$NON-NLS-1$
			root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");//$NON-NLS-1$

			return doc;
		}


		@Override
		protected Boolean call() throws Exception {
			super.call();

			final IDrawing drawing = LaTeXDraw.getINSTANCE().getInjector().getInstance(IDrawing.class);
			final Canvas canvas = LaTeXDraw.getINSTANCE().getInjector().getInstance(Canvas.class);
			// Creation of the SVG document.
			final Set<JfxInstrument> instruments = LaTeXDraw.getINSTANCE().getInstruments();
			final double incr = 100d / (drawing.size() + instruments.size() + 1d);
			final SVGDocument doc = toSVG(drawing, incr);
			final SVGMetadataElement meta = new SVGMetadataElement(doc);
			final SVGSVGElement root = doc.getFirstChild();
			final SVGElement metaLTD = (SVGElement) doc.createElement(LNamespace.LATEXDRAW_NAMESPACE + ':' + SVGElements.SVG_METADATA);

			// Creation of the SVG meta data tag.
			meta.appendChild(metaLTD);
			root.appendChild(meta);

			if(saveParameters) {
				Platform.runLater(() -> {
					// The parameters of the instruments are now saved.
					instruments.forEach(ins -> {
						ins.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
						updateProgress(getProgress() + incr, 100d);
					});

					canvas.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
					updateProgress(getProgress() + incr, 100d);

					LaTeXDraw.getINSTANCE().save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
					LaTeXDraw.getINSTANCE().getMainStage().setTitle(getDocumentName());
				});
			}

			return doc.saveSVGDocument(path);
		}

		@Override
		protected void done() {
			super.done();
			// Showing a message in the status bar.
			if(statusBar != null) {
				Platform.runLater(() -> statusBar.setText(LangTool.INSTANCE.getBundle().getString("SVG.1"))); //$NON-NLS-1$
			}
		}
	}


	private abstract static class LoadShapesWorker extends IOWorker {
		LoadShapesWorker(final String path, final Label statusBar, final ProgressBar bar) {
			super(path, statusBar, bar);
		}

		/**
		 * Converts an SVG document into a set of shapes.
		 * @param doc The SVG document.
		 * @param incrProgressBar The increment that will be used by the progress bar.
		 * @return The created shapes or null.
		 */
		protected List<IShape> toLatexdraw(final SVGDocument doc, final double incrProgressBar) {
			final NodeList elts = doc.getDocumentElement().getChildNodes();
			final List<IShape> shapes = IntStream.range(0, elts.getLength()).mapToObj(i -> {
				updateProgress(getProgress() + incrProgressBar, 100d);
				return elts.item(i);
			}).filter(node -> node instanceof SVGElement).map(node -> IShapeSVGFactory.INSTANCE.createShape((SVGElement) node)).
				filter(sh -> sh != null).collect(Collectors.toList());

			if(shapes.size() == 1 && shapes.get(0) instanceof IGroup) {
				return ((IGroup) shapes.get(0)).getShapes();
			}

			return shapes;
		}
	}


	/**
	 * The worker that loads SVG documents.
	 */
	private static class LoadWorker extends LoadShapesWorker {
		LoadWorker(final String path, final Label statusBar, final ProgressBar bar) {
			super(path, statusBar, bar);
		}

		/**
		 * Loads the instruments of the systems.
		 * @param meta The meta-data that contains the data of the instruments.
		 * @param instruments The instruments to set.
		 */
		private void loadInstruments(final Element meta, final Set<JfxInstrument> instruments) {
			final NodeList nl = meta.getChildNodes();

			Platform.runLater(() -> {
				for(int i = 0, size = nl.getLength(); i < size; i++) {
					final Node n = nl.item(i);

					if(n instanceof Element && LNamespace.LATEXDRAW_NAMESPACE_URI.equals(n.getNamespaceURI())) {
						try {
							final Element elt = (Element) n;
							instruments.forEach(ins -> ins.load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, elt));
						}catch(final Exception e) {
							BadaboomCollector.INSTANCE.add(e);
						}
					}
				}
			});
		}

		@Override
		protected Boolean call() throws Exception {
			super.call();

			try {
				final SVGDocument svgDoc = new SVGDocument(new File(path).toURI());
				final Element meta = svgDoc.getDocumentElement().getMeta();
				final Set<JfxInstrument> instruments = LaTeXDraw.getINSTANCE().getInstruments();
				final IDrawing drawing = LaTeXDraw.getINSTANCE().getInjector().getInstance(IDrawing.class);
				final Canvas canvas = LaTeXDraw.getINSTANCE().getInjector().getInstance(Canvas.class);
				final Element ldMeta;

				if(meta == null) {
					ldMeta = null;
				}else {
					final NodeList nl = meta.getElementsByTagNameNS(LNamespace.LATEXDRAW_NAMESPACE_URI, SVGElements.SVG_METADATA);
					final Node node = nl.getLength() == 0 ? null : nl.item(0);
					ldMeta = node instanceof Element ? (Element) node : null;
				}

				// Adding loaded shapes.
				final double incrProgressBar = Math.max(50d / (svgDoc.getDocumentElement().getChildNodes().getLength() + 1d), 1d);

				Platform.runLater(() -> {
					toLatexdraw(svgDoc, incrProgressBar).forEach(s -> drawing.addShape(s));
					updateProgress(getProgress() + 50d, 100d);

					// Loads the canvas' data.
					canvas.load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
					updateProgress(getProgress() + incrProgressBar, 100d);

					// The parameters of the instruments are loaded.
					if(ldMeta != null) {
						loadInstruments(ldMeta, instruments);
					}

					// Updating the possible widgets of the instruments.
					instruments.forEach(ins -> {
						ins.interimFeedback();

						if(ldMeta != null) {
							LaTeXDraw.getINSTANCE().load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
						}

						LaTeXDraw.getINSTANCE().getMainStage().setTitle(getDocumentName());
					});
				});

				return true;
			}catch(final Exception e) {
				BadaboomCollector.INSTANCE.add(e);
				return false;
			}
		}
	}
}
