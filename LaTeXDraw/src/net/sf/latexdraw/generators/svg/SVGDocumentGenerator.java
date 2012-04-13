package net.sf.latexdraw.generators.svg;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.SVGFilter;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.ExceptionsManager;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.mapping.ShapeList2ExporterMapping;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGMetadataElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.ui.LFrame;
import net.sf.latexdraw.util.LNamespace;

import org.malai.instrument.Instrument;
import org.malai.instrument.WidgetInstrument;
import org.malai.mapping.IMapping;
import org.malai.mapping.MappingRegistry;
import org.malai.presentation.AbstractPresentation;
import org.malai.presentation.Presentation;
import org.malai.ui.ISOpenSaver;
import org.malai.ui.UI;
import org.malai.widget.MProgressBar;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines a generator that creates SVG documents from drawings.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
public class SVGDocumentGenerator implements ISOpenSaver {
	/** The singleton that allows the save/load latexdraw SVG documents. */
	public static final SVGDocumentGenerator INSTANCE = new SVGDocumentGenerator();


	private SVGDocumentGenerator() {
		super();
	}


	@Override
	public boolean save(final String path, final UI ui, final MProgressBar progressBar, final Object statusBar) {
		final SaveWorker lw = new SaveWorker(ui, path, statusBar);

		if(progressBar!=null)
			lw.addPropertyChangeListener(new ProgressListener(progressBar));

		lw.execute();
		return true;
	}



	@Override
	public boolean open(final String path, final UI ui, final MProgressBar progressBar, final Object statusBar) {
		final LoadWorker lw = new LoadWorker(ui, path, statusBar);

		if(progressBar!=null)
			lw.addPropertyChangeListener(new ProgressListener(progressBar));

		lw.execute();
		return true;
	}


	/**
	 * The listener that listens the progress performed by the workers to update the progress bar.
	 */
	class ProgressListener implements PropertyChangeListener {
		private MProgressBar progressBar;

		protected ProgressListener(final MProgressBar progressBar) {
			super();
			this.progressBar = progressBar;
		}

		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
            if("progress".equals(evt.getPropertyName()))
            	progressBar.setValue((Integer)evt.getNewValue());
            else if("state".equals(evt.getPropertyName()))
            	switch((SwingWorker.StateValue)evt.getNewValue()){
					case STARTED:
						progressBar.setVisible(true);
						break;
					case DONE:
						progressBar.setVisible(false);
						break;
					default: break;
				}
		}
	}


	/**
	 * The abstract worker that factorises the code of loading and saving workers.
	 */
	abstract class IOWorker extends SwingWorker<Boolean, Void> {
		protected UI ui;

		protected String path;

		protected Object statusBar;

		private List<Boolean> instrumentsState;

		private List<Instrument> instruments;


		protected IOWorker(final UI ui, final String path, final Object statusBar) {
			super();
			this.ui = ui;
			this.path = path;
			this.statusBar = statusBar;
		}


		/**
		 * @return The name of the SVG document.
		 * @since 3.0
		 */
		protected String getDocumentName() {
			String name;

			if(path==null)
				name = ""; //$NON-NLS-1$
			else {
				name = new File(path).getName();
				final int indexSVG = name.lastIndexOf(SVGFilter.SVG_EXTENSION);

				if(indexSVG!=-1)
					name = name.substring(0, indexSVG);
			}

			return name;
		}


		@Override
		protected Boolean doInBackground() throws Exception {
			final Instrument[] ins = ui.getInstruments();

			instrumentsState = new ArrayList<Boolean>();
			instruments 	 = new ArrayList<Instrument>();

			if(ui instanceof LFrame)
				MappingRegistry.REGISTRY.removeMappingsUsingTarget(((LFrame)ui).getExporter(), ShapeList2ExporterMapping.class);

			for(final Instrument instrument : ins) {
				if(!(instrument instanceof ExceptionsManager)) {
					instrumentsState.add(instrument.isActivated());
					instruments.add(instrument);
				}

				if(instrument instanceof WidgetInstrument)
					((WidgetInstrument)instrument).setActivated(false, true);
				else
					instrument.setActivated(false);
			}

			return true;
		}


		@Override
		protected void done() {
			super.done();

			for(int i=0, size=instrumentsState.size(); i<size; i++)
				instruments.get(i).setActivated(instrumentsState.get(i));

			if(ui instanceof LFrame) {
				final LFrame frame = (LFrame)ui;
				final IMapping mapping = new ShapeList2ExporterMapping(frame.getDrawing().getShapes(), frame.getExporter());
				MappingRegistry.REGISTRY.addMapping(mapping);
				mapping.init();
			}

			ui.setModified(false);
		}
	}



	class SaveWorker extends IOWorker {
		protected SaveWorker(final UI ui, final String path, final Object statusBar) {
			super(ui, path, statusBar);
		}


		/**
		 * Creates an SVG document from a drawing.
		 * @param drawing The drawing to convert in SVG.
		 * @return The created SVG document or null.
		 * @since 2.0
		 */
		private SVGDocument toSVG(final IDrawing drawing, final double incr) {
			// Creation of the SVG document.
			final List<IShape> shapes	= drawing.getShapes();
			final SVGDocument doc 		= new SVGDocument();
			final SVGSVGElement root 	= doc.getFirstChild();
			final SVGGElement g 		= new SVGGElement(doc);
			final SVGDefsElement defs	= new SVGDefsElement(doc);
			SVGElement elt;

			root.appendChild(defs);
			root.appendChild(g);
			root.setAttribute("xmlns:"+LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);//$NON-NLS-1$

	        for(final IShape sh : shapes)
	        	if(sh!=null) {
	        		// For each shape an SVG element is created.
	        		elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);

		        	if(elt!=null)
		        		g.appendChild(elt);

		        	setProgress((int)Math.min(100., getProgress()+incr));
		        }

			// Setting SVG attributes to the created document.
			root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");//$NON-NLS-1$
			root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");//$NON-NLS-1$

			return doc;
		}


		@Override
		protected Boolean doInBackground() throws Exception {
			super.doInBackground();

			setProgress(0);
			final AbstractPresentation absPres = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();

			if(!(absPres instanceof IDrawing))
				return false;

			// Creation of the SVG document.
			final Instrument[] instruments 	= ui.getInstruments();
			final IDrawing drawing			= (IDrawing)absPres;
			final double incr				= 100./(drawing.size() + instruments.length + ui.getPresentations().size());
			final SVGDocument doc 			= toSVG(drawing, incr);
			final SVGMetadataElement meta	= new SVGMetadataElement(doc);
			final SVGElement metaLTD		= (SVGElement)doc.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+SVGElements.SVG_METADATA);
			final SVGSVGElement root 		= doc.getFirstChild();

			// Creation of the SVG meta data tag.
			meta.appendChild(metaLTD);
			root.appendChild(meta);

			// The parameters of the instruments are now saved.
			for(final Instrument instrument : instruments) {
				instrument.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
				setProgress((int)Math.min(100., getProgress()+incr));
			}

			for(final Presentation<?,?> presentation : ui.getPresentations()) {
				presentation.getConcretePresentation().save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
				setProgress((int)Math.min(100., getProgress()+incr));
			}

			ui.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);
			ui.setTitle(getDocumentName());

			return doc.saveSVGDocument(path);
		}


		@Override
		protected void done() {
			super.done();

			// Showing a message in the status bar.
			try {
				if(get() && statusBar instanceof JLabel)
					((JLabel)statusBar).setText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.191")); //$NON-NLS-1$
			}catch(final Exception ex){ BadaboomCollector.INSTANCE.add(ex); }
		}
	}




	/**
	 * The worker that loads SVG documents.
	 */
	class LoadWorker extends IOWorker {
		protected LoadWorker(final UI ui, final String path, final Object statusBar) {
			super(ui, path, statusBar);
		}


		/**
		 * Converts an SVG document into a set of shapes.
		 * @param doc The SVG document.
		 * @param incrProgressBar The increment that will be used by the progress bar.
		 * @return The created shapes or null.
		 * @since 3.0
		 */
		private IShape toLatexdraw(final SVGDocument doc, final double incrProgressBar) {
			final IGroup shapes = DrawingTK.getFactory().createGroup(false);
			final NodeList elts = doc.getDocumentElement().getChildNodes();
			Node node;

			for(int i=0, size=elts.getLength(); i<size; i++) {
				node = elts.item(i);

				if(node instanceof SVGElement)
					shapes.addShape(IShapeSVGFactory.INSTANCE.createShape((SVGElement)node));
				setProgress((int)Math.min(100., getProgress()+incrProgressBar));
			}

			return shapes.size() == 1 ? shapes.getShapeAt(0) : shapes.size()==0 ? null : shapes;
		}



		/**
		 * Loads the instruments of the systems.
		 * @param meta The meta-data that contains the data of the instruments.
		 * @param instruments The instruments to set.
		 * @since 3.0
		 */
		private void loadInstruments(final Element meta, final Instrument[] instruments) {
			final NodeList nl = meta.getChildNodes();
			Node n;
			Element elt;

			for(int i=0, size = nl.getLength(); i<size; i++) {
				n = nl.item(i);

				if(n instanceof Element && LNamespace.LATEXDRAW_NAMESPACE_URI.equals(n.getNamespaceURI()))
					try {
						elt = (Element)n;
						for(final Instrument instrument : instruments)
							instrument.load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, elt);
					}
					catch(final Exception e) { BadaboomCollector.INSTANCE.add(e); }
			}
		}



		@Override
		protected Boolean doInBackground() throws Exception {
			super.doInBackground();

			try{
				final SVGDocument svgDoc 		= new SVGDocument(new URI(path));
				Element meta 			 		= svgDoc.getDocumentElement().getMeta();
				final Instrument[] instruments 	= ui.getInstruments();
				final AbstractPresentation pres = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();
				Element ldMeta;
				double incrProgressBar;

				if(meta==null)
					ldMeta = null;
				else {
					final NodeList nl	= meta.getElementsByTagNameNS(LNamespace.LATEXDRAW_NAMESPACE_URI, SVGElements.SVG_METADATA);
					final Node node		= nl.getLength()==0 ? null : nl.item(0);
					ldMeta 				= node instanceof Element ? (Element)node : null;
				}

	            setProgress(0);

				// Adding loaded shapes.
				if(pres instanceof IDrawing) {
					incrProgressBar		   = Math.max(50./(svgDoc.getDocumentElement().getChildNodes().getLength()+ui.getPresentations().size()), 1.);
					final IShape shape     = toLatexdraw(svgDoc, incrProgressBar);
					final IDrawing drawing = (IDrawing)pres;

					if(shape instanceof IGroup) { // If several shapes have been loaded
						final IGroup group = (IGroup)shape;
						final double incr  = Math.max(50./group.size(), 1.);

						for(final IShape sh : group.getShapes()) {
							drawing.addShape(sh);
							setProgress((int)Math.min(100., getProgress()+incr));
						}
					} else { // If only a single shape has been loaded.
						drawing.addShape(shape);
						setProgress(Math.min(100, getProgress()+50));
					}
				}
				else incrProgressBar = Math.max(100./ui.getPresentations().size(), 1.);

				// Loads the presentation's data.
				for(final Presentation<?,?> presentation : ui.getPresentations()) {
					presentation.getConcretePresentation().load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
					setProgress((int)Math.min(100., getProgress()+incrProgressBar));
				}

				//TODO drawborders, autoadjust, border
				// code, caption, label, POSITION_HORIZ, POSITION_VERT, COMMENTS

				// The parameters of the instruments are loaded.
				if(meta!=null)
					loadInstruments(ldMeta, instruments);

				// Updating the possible widgets of the instruments.
				for(final Instrument instrument : instruments)
					instrument.interimFeedback();

				ui.load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, ldMeta);
				ui.updatePresentations();
				ui.setTitle(getDocumentName());

				return true;
			}catch(final Exception e){
				BadaboomCollector.INSTANCE.add(e);
				return false;
			}
		}
	}
}
