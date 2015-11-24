package net.sf.latexdraw.generators.svg;

/**
 * Defines a generator that creates SVG documents from drawings.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGDocumentGenerator { //implements ISOpenSaver<LFrame, JLabel> {
	/** The singleton that allows the save/load latexdraw SVG documents. */
	public static final SVGDocumentGenerator INSTANCE = new SVGDocumentGenerator();


	private SVGDocumentGenerator() {
		super();
	}


//	@Override
//	public boolean save(final String path, final LFrame ui, final MProgressBar progressBar, final JLabel statusBar) {
//		final SaveWorker lw = new SaveWorker(ui, path, statusBar, true, false);
//
//		if(progressBar!=null)
//			lw.addPropertyChangeListener(new ProgressListener(progressBar));
//
//		SwingUtilities.invokeLater(lw);
//		return true;
//	}
//
//
//
//	@Override
//	public boolean open(final String path, final LFrame ui, final MProgressBar progressBar, final JLabel statusBar) {
//		final LoadWorker lw = new LoadWorker(ui, path, statusBar);
//
//		if(progressBar!=null)
//			lw.addPropertyChangeListener(new ProgressListener(progressBar));
//
//		SwingUtilities.invokeLater(lw);
//		return true;
//	}
//
//
//	/**
//	 * Exports the selected shapes as a template.
//	 * @param path The path where the template will be saved.
//	 * @param ui The main frame of the app.
//	 * @param progressBar The progress bar.
//	 * @param statusBar The status bar.
//	 * @param templateMenu The menu that contains the template menu items.
//	 */
//	public void saveTemplate(final String path, final LFrame ui, final MProgressBar progressBar, final JLabel statusBar, final MMenu templateMenu) {
//		final SaveTemplateWorker lw = new SaveTemplateWorker(ui, path, statusBar, templateMenu);
//
//		if(progressBar!=null)
//			lw.addPropertyChangeListener(new ProgressListener(progressBar));
//
//		SwingUtilities.invokeLater(lw);
//	}
//
//
//	/**
//	 * Inserts a set of shapes into the drawing.
//	 * @param path The file of the SVG document to load.
//	 * @param ui The UI that contains the drawing.
//	 * @since 3.0
//	 */
//	public void insert(final String path, final LFrame ui) {
//		new InsertWorker(ui, path).execute();
//	}
//
//
//	/**
//	 * Updates the templates.
//	 * @param templatesMenu The menu that contains the templates.
//	 * @param updatesThumbnails True: the thumbnails of the template will be updated.
//	 * @since 3.0
//	 */
//	public void updateTemplates(final MMenu templatesMenu, final boolean updatesThumbnails) {
//		new UpdateTemplatesWorker(templatesMenu, updatesThumbnails).execute();
//	}
//
//
//
//	/**
//	 * The listener that listens the progress performed by the workers to update the progress bar.
//	 */
//	static class ProgressListener implements PropertyChangeListener {
//		private final MProgressBar progressBar;
//
//		protected ProgressListener(final MProgressBar progressBar) {
//			super();
//			this.progressBar = progressBar;
//		}
//
//		@Override
//		public void propertyChange(final PropertyChangeEvent evt) {
//            if("progress".equals(evt.getPropertyName())) //$NON-NLS-1$
//            	progressBar.setValue((Integer)evt.getNewValue());
//            else if("state".equals(evt.getPropertyName())) //$NON-NLS-1$
//            	switch((SwingWorker.StateValue)evt.getNewValue()){
//					case STARTED:
//						progressBar.setVisible(true);
//						break;
//					case DONE:
//						progressBar.setVisible(false);
//						break;
//					default: break;
//				}
//		}
//	}
//
//
//	/**
//	 * The abstract worker that factorises the code of loading and saving workers.
//	 */
//    abstract static class IOWorker extends SwingWorker<Boolean, Void> {
//		protected LFrame ui;
//
//		protected String path;
//
//		protected JLabel statusBar;
//
//		private List<Boolean> instrumentsState;
//
//		private List<Instrument> instruments;
//		
//		/** set the ui as modified after the work? */ 
//		protected boolean setModified;
//
//
//		protected IOWorker(final LFrame ui, final String path, final JLabel statusBar) {
//			super();
//			this.ui = ui;
//			this.path = path;
//			this.statusBar = statusBar;
//			setModified = false;
//		}
//
//
//		/**
//		 * @return The name of the SVG document.
//		 * @since 3.0
//		 */
//		protected String getDocumentName() {
//			String name;
//
//			if(path==null)
//				name = ""; //$NON-NLS-1$
//			else {
//				name = new File(path).getName();
//				final int indexSVG = name.lastIndexOf(SVGFilter.SVG_EXTENSION);
//
//				if(indexSVG!=-1)
//					name = name.substring(0, indexSVG);
//			}
//
//			return name;
//		}
//
//
//		@Override
//		protected Boolean doInBackground() throws Exception {
//			if(ui!=null) {
//				final Instrument[] ins = ui.getInstruments();
//
//				instrumentsState = new ArrayList<>();
//				instruments 	 = new ArrayList<>();
//
//				MappingRegistry.REGISTRY.removeMappingsUsingTarget(ui.getExporter(), ShapeList2ExporterMapping.class);
//
//				for(final Instrument instrument : ins) {
//					if(!(instrument instanceof ExceptionsManager)) {
//						instrumentsState.add(instrument.isActivated());
//						instruments.add(instrument);
//					}
//
//					if(instrument instanceof WidgetInstrument)
//						((WidgetInstrument)instrument).setActivated(false, true);
//					else
//						instrument.setActivated(false);
//				}
//			}
//
//			return true;
//		}
//
//
//		@Override
//		protected void done() {
//			super.done();
//
//			if(ui!=null) {
//				for(int i=0, size=instrumentsState.size(); i<size; i++)
//					instruments.get(i).setActivated(instrumentsState.get(i));
//
//				final IMapping mapping = new ShapeList2ExporterMapping(ui.getDrawing().getShapes(), ui.getExporter());
//				MappingRegistry.REGISTRY.addMapping(mapping);
//				mapping.init();
//				
//				ui.setModified(setModified);
//			}
//		}
//	}
//
//
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
//
//
//	/** This worker inserts the given set of shapes into the drawing. */
//	static class InsertWorker extends LoadShapesWorker {
//		protected InsertWorker(final LFrame ui, final String path) {
//			super(ui, path, null);
//			setModified = true;
//		}
//
//
//		@Override
//		protected Boolean doInBackground() throws Exception {
//			super.doInBackground();
//			try{
//				final SVGDocument svgDoc = new SVGDocument(new File(path).toURI());
//				final IDrawing pres = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();
//				// Adding loaded shapes.
//				pres.addShape(toLatexdraw(svgDoc, 0));
//				// Updating the possible widgets of the instruments.
//				for(final Instrument instrument : ui.getInstruments())
//					instrument.interimFeedback();
//				ui.updatePresentations();
//				return true;
//			}catch(final Exception e){
//				BadaboomCollector.INSTANCE.add(e);
//				return false;
//			}
//		}
//	}
//
//
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
//						final MMenuItem menu = createTemplateMenuItem(files[i].getPath(), files[i].getName()+PNGFilter.PNG_EXTENSION, pathCache);
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
//			IShape template;
//			File thumbnail;
//
//			// We export the updated template
//			if(files!=null)
//                for(final File file : files)
//                    if(filter.accept(file))
//                        try {
//                            template = toLatexdraw(new SVGDocument(file.toURI()), 0);
//                            thumbnail = new File(pathCache + File.separator + file.getName() + PNGFilter.PNG_EXTENSION);
//                            createTemplateThumbnail(thumbnail, template);
//                        }catch(final Exception ex) {BadaboomCollector.INSTANCE.add(ex);}
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
//
//
//	static class SaveTemplateWorker extends SaveWorker {
//		protected MMenu templateMenu;
//
//		protected SaveTemplateWorker(final LFrame ui, final String path, final JLabel statusBar, final MMenu templateMenu) {
//			super(ui, path, statusBar, false, true);
//			this.templateMenu = templateMenu;
//		}
//
//
//		@Override
//		protected void done() {
//			super.done();
//			INSTANCE.updateTemplates(templateMenu, true);
//		}
//	}
//
//
//	/** This worker saves the given document. */
//	static class SaveWorker extends IOWorker {
//		/** Defines if the parameters of the drawing (instruments, presentations, etc.) must be saved. */
//		protected boolean saveParameters;
//
//		/** Specifies if only the selected shapes must be saved. */
//		protected boolean onlySelection;
//
//
//		protected SaveWorker(final LFrame ui, final String path, final JLabel statusBar, final boolean saveParameters, final boolean onlySelection) {
//			super(ui, path, statusBar);
//			this.saveParameters = saveParameters;
//			this.onlySelection = onlySelection;
//		}
//
//
//		/**
//		 * Creates an SVG document from a drawing.
//		 * @param drawing The drawing to convert in SVG.
//		 * @return The created SVG document or null.
//		 * @since 2.0
//		 */
//		private SVGDocument toSVG(final IDrawing drawing, final double incr) {
//			// Creation of the SVG document.
//			final List<IShape> shapes;
//			final SVGDocument doc 		= new SVGDocument();
//			final SVGSVGElement root 	= doc.getFirstChild();
//			final SVGGElement g 		= new SVGGElement(doc);
//			SVGElement elt;
//
//			if(onlySelection)
//				shapes  = drawing.getSelection().getShapes();
//			else shapes = drawing.getShapes();
//
//			root.appendChild(g);
//			root.setAttribute("xmlns:"+LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);//$NON-NLS-1$
//			root.appendChild(new SVGDefsElement(doc));
//
//			try {
//		        for(final IShape sh : shapes) {
//	        		// For each shape an SVG element is created.
//	        		elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);
//		        	if(elt!=null)
//		        		g.appendChild(elt);
//		        	setProgress((int)Math.min(100., getProgress()+incr));
//		        }
//			}catch(final Exception ex) { BadaboomCollector.INSTANCE.add(ex); }
//			// Setting SVG attributes to the created document.
//			root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");//$NON-NLS-1$
//			root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");//$NON-NLS-1$
//
//			return doc;
//		}
//
//
//		@Override
//		protected Boolean doInBackground() throws Exception {
//			super.doInBackground();
//
//			setProgress(0);
//			final IDrawing drawing = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();
//			// Creation of the SVG document.
//			final Instrument[] instruments 	= ui.getInstruments();
//			final double incr				= 100./(drawing.size() + instruments.length + ui.getPresentations().size());
//			final SVGDocument doc 			= toSVG(drawing, incr);
//			final SVGMetadataElement meta	= new SVGMetadataElement(doc);
//			final SVGSVGElement root 		= doc.getFirstChild();
//			final SVGElement metaLTD		= (SVGElement)doc.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+SVGElements.SVG_METADATA);
//
//			// Creation of the SVG meta data tag.
//			meta.appendChild(metaLTD);
//			root.appendChild(meta);
//
//			if(saveParameters) {
//				// The parameters of the instruments are now saved.
//				for(final Instrument instrument : instruments) {
//					instrument.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
//					setProgress((int)Math.min(100., getProgress()+incr));
//				}
//
//				for(final Presentation<?,?> presentation : ui.getPresentations()) {
//					presentation.getConcretePresentation().save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
//					setProgress((int)Math.min(100., getProgress()+incr));
//				}
//
//				ui.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
//				ui.setTitle(getDocumentName());
//			}
//
//			return doc.saveSVGDocument(path);
//		}
//
//
//		@Override
//		protected void done() {
//			super.done();
//			// Showing a message in the status bar.
//			if(statusBar!=null)
//				statusBar.setText(LangTool.INSTANCE.getStringDialogFrame("SVG.1")); //$NON-NLS-1$
//		}
//	}
//
//
//
//	abstract static class LoadShapesWorker extends IOWorker {
//		protected LoadShapesWorker(final LFrame ui, final String path, final JLabel statusBar) {
//			super(ui, path, statusBar);
//		}
//
//
//		/**
//		 * Converts an SVG document into a set of shapes.
//		 * @param doc The SVG document.
//		 * @param incrProgressBar The increment that will be used by the progress bar.
//		 * @return The created shapes or null.
//		 * @since 3.0
//		 */
//		protected IShape toLatexdraw(final SVGDocument doc, final double incrProgressBar) {
//			final IGroup shapes = ShapeFactory.createGroup();
//			final NodeList elts = doc.getDocumentElement().getChildNodes();
//			Node node;
//
//			for(int i=0, size=elts.getLength(); i<size; i++) {
//				node = elts.item(i);
//
//				if(node instanceof SVGElement)
//					shapes.addShape(IShapeSVGFactory.INSTANCE.createShape((SVGElement)node));
//				setProgress((int)Math.min(100., getProgress()+incrProgressBar));
//			}
//
//			return shapes.size() == 1 ? shapes.getShapeAt(0) : shapes.isEmpty() ? null : shapes;
//		}
//	}
//
//
//
//	/**
//	 * The worker that loads SVG documents.
//	 */
//	static class LoadWorker extends LoadShapesWorker {
//		protected LoadWorker(final LFrame ui, final String path, final JLabel statusBar) {
//			super(ui, path, statusBar);
//		}
//
//
//		/**
//		 * Loads the instruments of the systems.
//		 * @param meta The meta-data that contains the data of the instruments.
//		 * @param instruments The instruments to set.
//		 * @since 3.0
//		 */
//		private void loadInstruments(final Element meta, final Instrument[] instruments) {
//			final NodeList nl = meta.getChildNodes();
//			Node n;
//			Element elt;
//
//			for(int i=0, size = nl.getLength(); i<size; i++) {
//				n = nl.item(i);
//
//				if(n instanceof Element && LNamespace.LATEXDRAW_NAMESPACE_URI.equals(n.getNamespaceURI()))
//					try {
//						elt = (Element)n;
//						for(final Instrument instrument : instruments)
//							instrument.load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, elt);
//					}
//					catch(final Exception e) { BadaboomCollector.INSTANCE.add(e); }
//			}
//		}
//
//
//
//		@Override
//		protected Boolean doInBackground() throws Exception {
//			super.doInBackground();
//
//			try{
//				final SVGDocument svgDoc 		= new SVGDocument(new File(path).toURI());
//				final Element meta 			 	= svgDoc.getDocumentElement().getMeta();
//				final Instrument[] instruments 	= ui.getInstruments();
//				final IDrawing drawing = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();
//				final Element ldMeta;
//				final double incrProgressBar;
//
//				if(meta==null)
//					ldMeta = null;
//				else {
//					final NodeList nl	= meta.getElementsByTagNameNS(LNamespace.LATEXDRAW_NAMESPACE_URI, SVGElements.SVG_METADATA);
//					final Node node		= nl.getLength()==0 ? null : nl.item(0);
//					ldMeta 				= node instanceof Element ? (Element)node : null;
//				}
//
//	            setProgress(0);
//
//				// Adding loaded shapes.
//				incrProgressBar		   = Math.max(50./(svgDoc.getDocumentElement().getChildNodes().getLength()+ui.getPresentations().size()), 1.);
//				final IShape shape     = toLatexdraw(svgDoc, incrProgressBar);
//
//				if(shape instanceof IGroup) { // If several shapes have been loaded
//					final IGroup group = (IGroup)shape;
//					final double incr  = Math.max(50./group.size(), 1.);
//
//					for(final IShape sh : group.getShapes()) {
//						drawing.addShape(sh);
//						setProgress((int)Math.min(100., getProgress()+incr));
//					}
//				} else { // If only a single shape has been loaded.
//					drawing.addShape(shape);
//					setProgress(Math.min(100, getProgress()+50));
//				}
//
//				// Loads the presentation's data.
//				for(final Presentation<?,?> presentation : ui.getPresentations()) {
//					presentation.getConcretePresentation().load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
//					setProgress((int)Math.min(100., getProgress()+incrProgressBar));
//				}
//
//				// The parameters of the instruments are loaded.
//				if(ldMeta!=null)
//					loadInstruments(ldMeta, instruments);
//
//				// Updating the possible widgets of the instruments.
//				for(final Instrument instrument : instruments)
//					instrument.interimFeedback();
//
//				if(ldMeta!=null)
//					ui.load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
//				ui.updatePresentations();
//				ui.setTitle(getDocumentName());
//
//				return true;
//			}catch(final Exception e){
//				BadaboomCollector.INSTANCE.add(e);
//				return false;
//			}
//		}
//	}
}
