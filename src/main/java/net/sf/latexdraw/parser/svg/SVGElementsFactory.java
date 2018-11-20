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
package net.sf.latexdraw.parser.svg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Tuple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * This class must be used to create SVG elements.
 * @author Arnaud BLOUIN
 */
public final class SVGElementsFactory {
	/** The singleton. */
	public static final SVGElementsFactory INSTANCE = new SVGElementsFactory();

	private final @NotNull List<Tuple<String, BiFunction<Node, SVGElement, SVGElement>>> producers;

	private SVGElementsFactory() {
		super();
		producers = new ArrayList<>();
		fillProducers();
	}

	private final void fillProducers() {
		producers.add(new Tuple<>(SVGElements.SVG_SVG, (n, p) -> new SVGSVGElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_G, (n, p) -> new SVGGElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_RECT, (n, p) -> new SVGRectElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_ELLIPSE, (n, p) -> new SVGEllipseElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_CIRCLE, (n, p) -> new SVGCircleElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_POLY_LINE, (n, p) -> new SVGPolyLineElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_LINE, (n, p) -> new SVGLineElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_POLYGON, (n, p) -> new SVGPolygonElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_METADATA, (n, p) -> new SVGMetadataElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_DEFS, (n, p) -> new SVGDefsElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_PATTERN, (n, p) -> new SVGPatternElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_LINEAR_GRADIENT, (n, p) -> new SVGLinearGradientElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_STOP, (n, p) -> new SVGStopElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_PATH, (n, p) -> new SVGPathElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_MARKER, (n, p) -> new SVGMarkerElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_TEXT, (n, p) -> new SVGTextElement(n, p)));
		producers.add(new Tuple<>(SVGElements.SVG_IMAGE, (n, p) -> new SVGImageElement(n, p)));
	}


	/**
	 * Companion method of createSVGElement
	 */
	private String getElementNS(final @NotNull Node src, final @NotNull SVGElement parent) {
		final String pref = src.getNodeName().contains(":") ? src.getNodeName().substring(0, src.getNodeName().indexOf(':')) : null;
		final NamedNodeMap nnm = src.getAttributes();

		if(nnm == null) {
			return parent.lookupNamespaceURI(pref);
		}
		final Node xmlnsNode = nnm.getNamedItem(pref == null ? "xmlns" : "xmlns:" + pref); //NON-NLS

		return xmlnsNode == null ? parent.lookupNamespaceURI(pref) : xmlnsNode.getNodeValue();
	}


	/**
	 * This factory can be used to create an SVG element according to the given SVG node (tag).
	 * @param src The node that will be used to create the SVG element.
	 * @param parent The parent of the element to create.
	 */
	public void createSVGElement(final @Nullable Node src, final @NotNull SVGElement parent) {
		if(src == null) {
			return;
		}

		try {
			final String name = src.getNodeName();
			final String ns = getElementNS(src, parent);

			if(ns != null && !name.endsWith("#text") && !name.endsWith("#comment")) { //NON-NLS
				if(SVGDocument.SVG_NAMESPACE.equals(ns)) {
					createElement(name, src, parent);
				}else {
					new OtherNSElement(src, parent);
				}
			}
		}catch(final IllegalArgumentException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}


	private SVGElement createElement(final String name, final Node src, final SVGElement parent) {
		// Makes use of a list of tuples to reduce the CC.
		// Looking for the tuple which type matches the type of the given shape.
		// Then calling the associated function that produces the PST view.
		return producers.stream()
			.filter(t -> name.endsWith(t.a))
			.findFirst()
			.map(t -> t.b.apply(src, parent))
			.orElse(null);
	}
}
