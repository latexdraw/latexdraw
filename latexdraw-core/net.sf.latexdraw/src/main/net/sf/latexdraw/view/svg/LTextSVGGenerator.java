package net.sf.latexdraw.view.svg;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.models.interfaces.shape.TextSize;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGTextElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a text.<br>
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
class LTextSVGGenerator extends LShapeSVGGenerator<IText> {

	protected LTextSVGGenerator(final IText f) {
		super(f);
	}


	/**
	 * Creates a text from an SVG text element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LTextSVGGenerator(final SVGTextElement elt) {
		this(ShapeFactory.createText());

		if(elt==null)
			throw new IllegalArgumentException();

		final String txt = elt.getText();

		if(txt==null || txt.isEmpty())
			throw new IllegalArgumentException("This text is empty."); //$NON-NLS-1$

		shape.setText(txt);
		shape.setPosition(elt.getX(), elt.getY());
		applyTransformations(elt);
	}


	/**
	 * Creates a text from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LTextSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.createText());

		if(elt==null)
			throw new IllegalArgumentException();

		final NodeList nl = elt.getElementsByTagNameNS(SVGDocument.SVG_NAMESPACE, SVGElements.SVG_TEXT);

		if(nl.getLength()>0) {
			final SVGTextElement text = (SVGTextElement)nl.item(0);
			shape.setText(text.getText());
			shape.setPosition(text.getX(), text.getY());
		}
		else
			throw new IllegalArgumentException();

		TextSize textSize;
		try { textSize = TextSize.getTextSizeFromSize(Double.valueOf(elt.getSVGAttribute(SVGAttributes.SVG_FONT_SIZE, null)).intValue()); }
		catch(final Exception e) { textSize = null; }

		if(textSize!=null)
			shape.setText("\\" + textSize.getLatexToken() + '{' + shape.getText().replace("&", "\\&") + '}'); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if(SVGAttributes.SVG_FONT_WEIGHT_BOLD.equals(elt.getSVGAttribute(SVGAttributes.SVG_FONT_WEIGHT, null)))
			shape.setText("\\textbf{" + shape.getText() + '}'); //$NON-NLS-1$

		if(SVGAttributes.SVG_FONT_STYLE_ITALIC.equals(elt.getSVGAttribute(SVGAttributes.SVG_FONT_STYLE, null)))
			shape.setText("\\emph{" + shape.getText() + '}'); //$NON-NLS-1$

		shape.setLineColour(CSSColors.INSTANCE.getRGBColour(elt.getFill()));
		shape.setTextPosition(TextPosition.getTextPosition(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION)));

		if(withTransformation)
			applyTransformations(elt);
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc == null)
			return null;

		final SVGElement root = new SVGGElement(doc);
		final String ltdPref  = LNamespace.LATEXDRAW_NAMESPACE + ':';
		final Element txt 	  = new SVGTextElement(doc);

		root.setAttribute(ltdPref + LNamespace.XML_TYPE, LNamespace.XML_TYPE_TEXT);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		root.setAttribute(ltdPref + LNamespace.XML_POSITION, String.valueOf(shape.getTextPosition().getLatexToken()));

		txt.setAttribute(SVGAttributes.SVG_X, String.valueOf(shape.getX()));
		txt.setAttribute(SVGAttributes.SVG_Y, String.valueOf(shape.getY()));
		txt.appendChild(doc.createTextNode(shape.getText()));
		root.appendChild(txt);

		setSVGRotationAttribute(root);

		return root;
	}
}
