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
package net.sf.latexdraw.view.svg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javax.imageio.ImageIO;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.ExportFormat;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGMetadataElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewFactory;
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
	 * @param templatePane The menu that contains the template menu items.
	 */
	public void saveTemplate(final String path, final ProgressBar progressBar, final Label statusBar, final Pane templatePane) {
		final SaveTemplateWorker stw = new SaveTemplateWorker(path, statusBar, templatePane, progressBar);
		progressBar.progressProperty().bind(stw.progressProperty());
		new Thread(stw).start();
	}


	/**
	 * Inserts a set of shapes into the drawing.
	 * @param path The file of the SVG document to load.
	 * @param position The position where the shapes will be inserted. Can be null.
	 */
	public IShape insert(final String path, final IPoint position) {
		final InsertWorker worker = new InsertWorker(path, position);
		new Thread(worker).start();

		try {
			if(worker.get()) {
				return worker.getInsertedShapes();
			}
		}catch(final InterruptedException | ExecutionException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
		return null;
	}


	/**
	 * Updates the templates.
	 * @param templatesPane The pane that contains the templates.
	 * @param updatesThumbnails True: the thumbnails of the template will be updated.
	 */
	public void updateTemplates(final Pane templatesPane, final boolean updatesThumbnails) {
		new Thread(new UpdateTemplatesWorker(templatesPane, updatesThumbnails)).start();
	}


	/**
	 * The abstract worker that factorises the code of loading and saving workers.
	 */
	private abstract static class IOWorker extends Task<Boolean> {
		protected final Label statusBar;
		protected final String path;
		protected final ProgressBar progressBar;
		/** set the ui as modified after the work? */
		protected boolean setModified;

		IOWorker(final String ioPath, final Label status, final ProgressBar bar) {
			super();
			path = ioPath;
			statusBar = status;
			setModified = false;
			progressBar = bar;
		}

		/**
		 * @return The name of the SVG document.
		 */
		protected String getDocumentName() {
			String name;

			if(path == null) {
				name = ""; //NON-NLS
			}else {
				name = new File(path).getName();
				final int indexSVG = name.lastIndexOf(".svg"); //NON-NLS

				if(indexSVG != -1) {
					name = name.substring(0, indexSVG);
				}
			}

			return name;
		}

		@Override
		protected Boolean call() throws Exception {
			if(progressBar!=null) {
				progressBar.setVisible(true);
			}
			return true;
		}

		@Override
		protected void done() {
			super.done();
			if(progressBar != null) {
				progressBar.progressProperty().unbind();
				progressBar.setVisible(false);
			}
			if(setModified) {
				LaTeXDraw.getInstance().setModified(true);
			}
		}
	}


	/** This worker inserts the given set of shapes into the drawing. */
	private static class InsertWorker extends LoadShapesWorker {
		private IShape insertedShapes;
		private final IPoint position;

		InsertWorker(final String path, final IPoint positionTemplate) {
			super(path, null, null);
			setModified = true;
			insertedShapes = null;
			position = positionTemplate;
		}

		@Override
		protected Boolean call() {
			try {
				final SVGDocument svgDoc = new SVGDocument(new File(path).toURI());
				final IDrawing drawing = LaTeXDraw.getInstance().getInjector().getInstance(IDrawing.class);

				Platform.runLater(() -> {
					final List<IShape> shapes = toLatexdraw(svgDoc, 0);

					if(shapes.size() == 1) {
						insertedShapes = shapes.get(0);
					}else {
						final IGroup gp = ShapeFactory.INST.createGroup();
						shapes.forEach(sh -> gp.addShape(sh));
						insertedShapes = gp;
					}

					if(position != null) {
						final IPoint tp = insertedShapes.getTopLeftPoint();
						insertedShapes.translate(position.getX() - tp.getX(), position.getY() - tp.getY());
					}

					drawing.addShape(insertedShapes);
					// Updating the possible widgets of the instruments.
					LaTeXDraw.getInstance().getInstruments().forEach(ins -> ins.interimFeedback());
				});
				return true;
			}catch(final IOException | MalformedSVGDocument ex) {
				BadaboomCollector.INSTANCE.add(ex);
				return false;
			}
		}

		protected IShape getInsertedShapes() {
			return insertedShapes;
		}
	}


	/** This worker updates the templates. */
	private static class UpdateTemplatesWorker extends LoadShapesWorker {
		private final Pane templatesPane;
		private final boolean updateThumbnails;

		UpdateTemplatesWorker(final Pane pane, final boolean updateThumbs) {
			super(null, null, null);
			templatesPane = pane;
			updateThumbnails = updateThumbs;
		}

		@Override
		protected Boolean call() {
			if(updateThumbnails) {
				updateTemplates(LPath.PATH_TEMPLATES_DIR_USER, LPath.PATH_CACHE_DIR);
				updateTemplates(LPath.PATH_TEMPLATES_SHARED, LPath.PATH_CACHE_SHARE_DIR);
			}

			Platform.runLater(() -> {
				templatesPane.getChildren().clear();
				fillTemplatePane(LPath.PATH_TEMPLATES_DIR_USER, LPath.PATH_CACHE_DIR, true);
				fillTemplatePane(LPath.PATH_TEMPLATES_SHARED, LPath.PATH_CACHE_SHARE_DIR, true);
			});

			return true;
		}

		/**
		 * Creates an image view from the template
		 * @param nameThumb The name of the thumbnail.
		 * @param  pathPic The path of the thumbnail of the template.
		 * @return The created image view or nothing.
		 */
		private Optional<ImageView> createTemplateItem(final String svgPath, final String nameThumb, final String pathPic) {
			try {
				final ImageView view = new ImageView(new Image("file:"+pathPic + File.separator + nameThumb)); //NON-NLS
				view.setUserData(svgPath);

				final int id = nameThumb.lastIndexOf(".svg" + ExportFormat.PNG.getFileExtension()); //NON-NLS
				if(id != -1) {
					Tooltip.install(view, new Tooltip(nameThumb.substring(0, id)));
				}
				return Optional.of(view);
			}catch(final Exception ex) {
				return Optional.empty();
			}
		}

		/**
		 * fills the template pane with image views gathered from the given directory of templates.
		 * @param pathTemplate The path of the folder that contains the templates.
		 * @param pathCache The path of the folder that contains the cache of the templates.
		 * @param sharedTemplates True: the templates are shared templates (in the shared directory).
		 */
		private void fillTemplatePane(final String pathTemplate, final String pathCache, final boolean sharedTemplates) {
			try(final DirectoryStream<Path> paths =
					Files.newDirectoryStream(Paths.get(pathTemplate), elt -> elt.toFile().isFile() && elt.toString().endsWith(".svg"))) { //NON-NLS
				paths.forEach(entry -> createTemplateItem(entry.toFile().getPath(), entry.getFileName() + ExportFormat.PNG.getFileExtension(), pathCache).
					ifPresent(item -> templatesPane.getChildren().add(item)));
			}catch(final IOException ex) {
				// No matter.
			}
		}

		/**
		 * Updates the templates from the given path, in the given cache path.
		 * @param pathTemplate The path of the templates to update.
		 * @param pathCache The path where the cache of the thumbnails of the templates will be stored.
		 */
		private void updateTemplates(final String pathTemplate, final String pathCache) {
			final File templateDir = new File(pathTemplate);

			if(!templateDir.isDirectory()) {
				return;
			}

			try(final DirectoryStream<Path> paths =
					Files.newDirectoryStream(Paths.get(pathTemplate), elt -> elt.toFile().isFile() && elt.toString().endsWith(".svg"))) { //NON-NLS
				paths.forEach(file -> updateTemplate(file, pathCache));
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}

		/**
		 * Update the thumbnail of a template contained in the given file.
		 * @param file The template to update the thumbnail.
		 * @param pathCache The path where the thumbnails are stored.
		 */
		private void updateTemplate(final Path file, final String pathCache) {
			Platform.runLater(() -> {
				try {
					final Group template = new Group();
					final List<IShape> shapes = toLatexdraw(new SVGDocument(file.toUri()), 0);
					template.getChildren().setAll(shapes.stream().map(sh -> ViewFactory.INSTANCE.createView(sh)).
						filter(opt -> opt.isPresent()).map(opt -> opt.get()).collect(Collectors.toList()));
					final File thumb = new File(pathCache + File.separator + file.getFileName() + ExportFormat.PNG.getFileExtension());
					createTemplateThumbnail(thumb, template);
				}catch(final Exception ex) {
					BadaboomCollector.INSTANCE.add(ex);
				}
			});
		}

		/**
		 * Creates a thumbnail from the given selection in the given file.
		 * @param templateFile The file of the future thumbnail.
		 * @param selection The set of shapes composing the template.
		 */
		private void createTemplateThumbnail(final File templateFile, final Group selection) {
			final Bounds bounds = selection.getBoundsInParent();
			final double scale = 70d / Math.max(bounds.getWidth(), bounds.getHeight());
			final WritableImage img = new WritableImage((int) (bounds.getWidth() * scale), (int) (bounds.getHeight() * scale));
			final SnapshotParameters snapshotParameters = new SnapshotParameters();

			snapshotParameters.setFill(Color.WHITE);
			snapshotParameters.setTransform(new Scale(scale, scale));
			selection.snapshot(snapshotParameters, img);

			while(img.isBackgroundLoading()) {
				LSystem.INSTANCE.sleep(100L);
			}

			final BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);

			try {
				ImageIO.write(bufferedImage, "png", templateFile);  //NON-NLS
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
			bufferedImage.flush();
		}
	}


	private static class SaveTemplateWorker extends SaveWorker {
		private final Pane templatePane;

		SaveTemplateWorker(final String path, final Label statusBar, final Pane templates, final ProgressBar bar) {
			super(path, statusBar, false, true, bar);
			templatePane = templates;
		}

		@Override
		protected void done() {
			super.done();
			INSTANCE.updateTemplates(templatePane, true);
			if(statusBar != null) {
				Platform.runLater(() -> statusBar.setText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.169"))); //NON-NLS
			}
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
			final int padding = 20;
			final Optional<IPoint> opttl = drawing.getShapes().parallelStream().map(sh -> sh.getTopLeftPoint()).
				reduce((p1, p2) -> ShapeFactory.INST.createPoint(p1.getX() < p2.getX() ? p1.getX() : p2.getX(), p1.getY() < p2.getY() ? p1.getY() : p2.getY()));
			final Optional<IPoint> optbr = drawing.getShapes().parallelStream().map(sh -> sh.getBottomRightPoint()).
				reduce((p1, p2) -> ShapeFactory.INST.createPoint(p1.getX() > p2.getX() ? p1.getX() : p2.getX(), p1.getY() > p2.getY() ? p1.getY() : p2.getY()));

			opttl.ifPresent(tl -> optbr.ifPresent(br ->
				root.setAttribute("viewBox", MathUtils.INST.format.format(tl.getX() - padding) + " " + //NON-NLS
					MathUtils.INST.format.format(tl.getY() - padding) + " " +
					MathUtils.INST.format.format(br.getX() - tl.getX() + padding * 2) + " " +
					MathUtils.INST.format.format(br.getY() - tl.getY() + padding * 2))));

			root.appendChild(g);
			root.setAttribute("xmlns:" + LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI); //NON-NLS
			root.appendChild(new SVGDefsElement(doc));

			try {
				shapes.forEach(sh -> {
					// For each shape an SVG element is created.
					final SVGElement elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);
					if(elt != null) {
						g.appendChild(elt);
					}
					Platform.runLater(() -> updateProgress(getProgress() + incr, 100d));
				});
			}catch(final Exception ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}

			// Setting SVG attributes to the created document.
			root.setAttribute(SVGAttributes.SVG_VERSION, "1.1"); //NON-NLS
			root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full"); //NON-NLS

			return doc;
		}


		@Override
		protected Boolean call() throws Exception {
			super.call();
			final IDrawing drawing = LaTeXDraw.getInstance().getInjector().getInstance(IDrawing.class);
			final Canvas canvas = LaTeXDraw.getInstance().getInjector().getInstance(Canvas.class);
			// Creation of the SVG document.
			final Set<JfxInstrument> instruments = LaTeXDraw.getInstance().getInstruments();
			final double incr = 100d / (drawing.size() + instruments.size() + 1d);
			final SVGDocument doc = toSVG(drawing, incr);
			final SVGMetadataElement meta = new SVGMetadataElement(doc);
			final SVGSVGElement root = doc.getFirstChild();
			final SVGElement metaLTD = (SVGElement) doc.createElement(LNamespace.LATEXDRAW_NAMESPACE + ':' + SVGElements.SVG_METADATA);

			// Creation of the SVG meta data tag.
			meta.appendChild(metaLTD);
			root.appendChild(meta);

			if(saveParameters) {
					// The parameters of the instruments are now saved.
				instruments.forEach(ins -> {
					ins.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
					Platform.runLater(() -> updateProgress(getProgress() + incr, 100d));
				});

				canvas.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
				Platform.runLater(() -> updateProgress(getProgress() + incr, 100d));

				LaTeXDraw.getInstance().save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
				Platform.runLater(() -> LaTeXDraw.getInstance().getMainStage().setTitle(getDocumentName()));
			}
			return doc.saveSVGDocument(path);
		}

		@Override
		protected void done() {
			super.done();
			// Showing a message in the status bar.
			if(statusBar != null) {
				Platform.runLater(() -> statusBar.setText(LangTool.INSTANCE.getBundle().getString("SVG.1"))); //NON-NLS
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
			}).filter(node -> node instanceof SVGElement).map(node -> SVGShapesFactory.INSTANCE.createShape((SVGElement) node)).
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
				final Set<JfxInstrument> instruments = LaTeXDraw.getInstance().getInstruments();
				final IDrawing drawing = LaTeXDraw.getInstance().getInjector().getInstance(IDrawing.class);
				final Canvas canvas = LaTeXDraw.getInstance().getInjector().getInstance(Canvas.class);
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
							LaTeXDraw.getInstance().load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
						}

						LaTeXDraw.getInstance().getMainStage().setTitle(getDocumentName());
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
