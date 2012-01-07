package net.sf.latexdraw.generators.svg;

import java.io.File;
import java.net.URI;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.SVGFilter;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGMetadataElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;

import org.malai.instrument.Instrument;
import org.malai.presentation.AbstractPresentation;
import org.malai.presentation.Presentation;
import org.malai.ui.ISOpenSaver;
import org.malai.ui.UI;
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
	public static final SVGDocumentGenerator SVG_GENERATOR = new SVGDocumentGenerator();



	/**
	 * Creates an SVG document from a set of shapes.
	 * @param shapes The shapes to convert in SVG.
	 * @return The created SVG document or null.
	 * @since 2.0
	 */
	public SVGDocument toSVG(final List<IShape> shapes) {
		if(shapes==null)
			return null;

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
	        }

        return doc;
	}



	/**
	 * Creates an SVG document from a drawing.
	 * @param drawing The drawing to convert in SVG.
	 * @return The created SVG document or null.
	 * @see SVGDocumentGenerator#toSVG(List)
	 * @since 2.0
	 */
	public SVGDocument toSVG(final IDrawing drawing) {
		if(drawing==null)
			return null;

		// Creation of the SVG document.
		final SVGDocument doc = toSVG(drawing.getShapes());

		// Setting SVG attributes to the created document.
		if(doc!=null) {
			final SVGSVGElement root = doc.getFirstChild();
			root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");//$NON-NLS-1$
			root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");//$NON-NLS-1$
		}

		return doc;
	}




	@Override
	public boolean save(final String path, final UI ui) {
		if(ui==null || path==null)
			return false;

		final AbstractPresentation absPres = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();

		if(!(absPres instanceof IDrawing))
			return false;

		// Creation of the SVG document.
		final SVGDocument doc = toSVG((IDrawing) absPres);

		if(doc==null)
			return false;

		final SVGMetadataElement meta	= new SVGMetadataElement(doc);
		final SVGElement metaLTD		= (SVGElement)doc.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+SVGElements.SVG_METADATA);
		final SVGSVGElement root 		= doc.getFirstChild();
		final Instrument[] instruments 	= ui.getInstruments();

		// Creation of the SVG meta data tag.
		meta.appendChild(metaLTD);
		root.appendChild(meta);

		// The parameters of the instruments are now saved.
		for(Instrument instrument : instruments)
			instrument.save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);

		for(Presentation<?,?> presentation : ui.getPresentations())
			presentation.getConcretePresentation().save(false, LNamespace.LATEXDRAW_NAMESPACE, doc, metaLTD);

		ui.setTitle(getDocumentName(path));

		return doc.saveSVGDocument(path);
	}


	/**
	 * @param path The path of the SVG file.
	 * @return The name of the SVG document.
	 * @since 3.0
	 */
	protected String getDocumentName(final String path) {
		String name;

		if(path==null)
			name = ""; //$NON-NLS-1$
		else {
			name = new File(path).getName();
			int indexSVG = name.lastIndexOf(SVGFilter.SVG_EXTENSION);

			if(indexSVG!=-1)
				name = name.substring(0, indexSVG);
		}

		return name;
	}


	/**
	 * Converts an SVG document into a set of shapes.
	 * @param doc The SVG document.
	 * @return The created shapes or null.
	 * @since 3.0
	 */
	public IShape toLatexdraw(final SVGDocument doc) {
		if(doc==null)
			return null;

		final IGroup shapes = DrawingTK.getFactory().createGroup(false);
		final NodeList elts = doc.getDocumentElement().getChildNodes();
		IShape shape 		= null;
		Node node;

		for(int i=0, size=elts.getLength(); i<size; i++) {
			node = elts.item(i);

			if(node instanceof SVGElement)
				shape = IShapeSVGFactory.INSTANCE.createShape((SVGElement)node);

			if(shape!=null)
				shapes.addShape(shape);
		}

		return shapes.size() == 1 ? shapes.getShapeAt(0) : shapes.size()==0 ? null : shapes;
	}



	@Override
	public boolean open(final String path, final UI ui) {
		try{
			final SVGDocument svgDoc 		= new SVGDocument(new URI(path));
			Element meta 			 		= svgDoc.getDocumentElement().getMeta();
			final Instrument[] instruments 	= ui.getInstruments();
			final AbstractPresentation pres = ui.getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();
			Element ldMeta;

			if(meta==null)
				ldMeta = null;
			else {
				final NodeList nl	= meta.getElementsByTagNameNS(LNamespace.LATEXDRAW_NAMESPACE_URI, SVGElements.SVG_METADATA);
				final Node node		= nl.getLength()==0 ? null : nl.item(0);
				ldMeta 				= node instanceof Element ? (Element)node : null;
			}

			// Adding loaded shapes.
			if(pres instanceof IDrawing) {
				final IShape shape     = toLatexdraw(svgDoc);
				final IDrawing drawing = (IDrawing)pres;

				if(shape instanceof IGroup) { // If several shapes have been loaded
					final IGroup group = (IGroup)shape;

					for(IShape sh : group.getShapes())
						drawing.addShape(sh);
				} else // If only a single shape has been loaded.
					drawing.addShape(shape);
			}

			// Loads the presentation's data.
			for(Presentation<?,?> presentation : ui.getPresentations())
				presentation.getConcretePresentation().load(false, LNamespace.LATEXDRAW_NAMESPACE_URI, meta);
			//TODO drawborders, autoadjust, border, delimitorOpacity
			// zoom : Zoomer or Canvas?
			// unit : scaleRulerCustomiser
			// code, caption, label, POSITION_HORIZ, POSITION_VERT, COMMENTS, AUTO_UPDATE: codePanelActivator or CodePanel, ...?
			// size (width, height), position (x, y)

			// The parameters of the instruments are loaded.
			if(meta!=null)
				loadInstruments(ldMeta, instruments);

			// Updating the possible widgets of the instruments.
			for(Instrument instrument : instruments)
				instrument.interimFeedback();

			ui.updatePresentations();
			ui.setTitle(getDocumentName(path));

			return true;

		}catch(final Exception e){
			BadaboomCollector.INSTANCE.add(e);
			return false;
		}
	}


	/**
	 * Loads the instruments of the systems.
	 * @param meta The meta-data that contains the data of the instruments.
	 * @param instruments The instruments to set.
	 * @since 3.0
	 */
	protected void loadInstruments(final Element meta, final Instrument[] instruments) {
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
}
