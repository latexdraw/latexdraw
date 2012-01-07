package net.sf.latexdraw.generators.svg;

import java.awt.geom.Point2D;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGTextElement;

/**
 * Defines a SVG generator for a text.<br>
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
		this(DrawingTK.getFactory().createText(true));

		if(elt==null)
			throw new IllegalArgumentException();

		String txt = elt.getText();

		if(txt==null || txt.length()==0)
			throw new IllegalArgumentException("This text is empty."); //$NON-NLS-1$

		shape.setText(txt);
		shape.setPosition(DrawingTK.getFactory().createPoint(elt.getX(), elt.getY()));
		setTextAttributes(elt);
		applyTransformations(elt);
	}



	protected LTextSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates a text from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LTextSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createText(true));

		if(elt==null)
			throw new IllegalArgumentException();

//		double x, y = 0;
//		String v;
//		NodeList nl;
//		String fontFam = elt.getAttribute(SVGAttributes.SVG_FONT_FAMILY);
//		int fontSize;
//		IText t;
//
//		try { fontSize = Double.valueOf(elt.getAttribute(SVGAttributes.SVG_FONT_SIZE)).intValue(); }
//		catch(NumberFormatException e) { fontSize = Text.DEFAULT_SIZE.getSize(); }
//
//		setNumber(elt);
//		setTextAttributes(elt);
//		v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE_URI+':'+SVGAttributes.SVG_X);
//		x = v==null ? Double.MAX_VALUE : Double.valueOf(v).doubleValue();
//
//		nl = elt.getElementsByTagNameNS(SVGDocument.SVG_NAMESPACE, SVGElements.SVG_TEXT);
//
//		if(nl.getLength()>0)
//		{
//			SVGTextElement text = (SVGTextElement)nl.item(0);
//			LaTeXDrawPSTricksParserActions action = new LaTeXDrawPSTricksParserActions();
//			PSTricksParser parser = new PSTricksParser(action);
//			String code = "\\begin{pspicture}(10,10)\n\\rput(0,0){" + text.getText() + "}\n\\end{pspicture}";//$NON-NLS-1$ //$NON-NLS-2$
//
//
//			parser.parse(code, null, null);
//
//			if(action.getFigures().isEmpty() || !(action.getFigures().firstElement() instanceof Text))
//				throw new IllegalArgumentException();
//
//			t = (IText)action.getFigures().firstElement();
//			y = text.getY();
//			x = text.getX()<x ? text.getX() : x;
//			t.setPosition(new LPoint(x, y));
//
//			shape = t;
//		}
//		else
//			throw new IllegalArgumentException();
//
//		if(t.hasSimpleFramedBox()) {
//			FramedBox fb  = t.getSimpleBox();
//			Figure figure = fb.getBox();
//
//			if(((float)fb.getFrameSep())==0f && figure.getLinesColor().equals(figure.getInteriorColor()) &&
//					figure.isFilled() && fb.getBoxType()==FramedBox.BOX_RECTANGLE) {
//				t.setOpacityColor(figure.getLinesColor());
//				t.removeAllBoxes();
//				t.setOpaque(true);
//
//			}
//		}
//
//		t = (IText)shape;
//		t.setSize(fontSize);
//		t.setTextFont(fontFam);
//		t.setIsBold(SVGAttributes.SVG_FONT_WEIGHT_BOLD.equals(elt.getAttribute(SVGAttributes.SVG_FONT_WEIGHT)));
//		t.setIsItalic(SVGAttributes.SVG_FONT_STYLE_ITALIC.equals(elt.getAttribute(SVGAttributes.SVG_FONT_STYLE)));

		if(withTransformation)
			applyTransformations(elt);
	}




	/**
	 * Sets the text attribute (is italic, font family,...) from the given SVG element.
	 * @param e The source SVG element.
	 * @since 2.0.0
	 */
	public void setTextAttributes(final SVGElement e)
	{
		if(e==null)
			return ;
//
//		Text t = (Text)getShape();
//		float fontSize = e.getFontSize();
//		String fam	   = e.getFontFamily();
//		String style   = e.getFontStyle();
//		String weight  = e.getFontWeight();
//
//		if(fontSize>0)
//			t.setSize((int)fontSize);
//
//		if(fam.length()>0)
//			t.setTextFontByFamily(fam);
//
//		t.setLinesColor(CSSColors.getRGBColour(e.getFill()));
//		t.setIsItalic(style.equals(SVGAttributes.SVG_FONT_STYLE_ITALIC) ||
//					  style.equals(SVGAttributes.SVG_FONT_STYLE_OBLIQUE));
//		t.setIsBold(weight.equals(SVGAttributes.SVG_FONT_WEIGHT_BOLD));
	}




	/**
	 * Creates an SVGTextElement from the given part of text.
	 * @param txt The text to convert in SVG.
	 * @param doc The document to create the text element.
	 * @param position The position of the text.
	 * @return The SVG text element or null if a parameter is null.
	 * @since 2.0.0
	 */
	public SVGTextElement getSVGTextElement(final String txt, final SVGDocument doc, final Point2D position) {
		if(txt==null || position==null || doc==null)
			return null;

		SVGTextElement textElt = new SVGTextElement(doc);

		textElt.setAttribute(SVGAttributes.SVG_X, String.valueOf(position.getX()));
		textElt.setAttribute(SVGAttributes.SVG_Y, String.valueOf(position.getY()));
		textElt.appendChild(doc.createCDATASection(txt));

		return textElt;
	}




	@Override
	public SVGElement toSVG(final SVGDocument doc)
	{
		if(doc == null)
			return null;

		SVGElement root = new SVGGElement(doc);
//		IText t 		= (IText)getShape();
//		String ltdPref  = LNamespace.LATEXDRAW_NAMESPACE + ':';
//
//		root.setAttribute(ltdPref + LNamespace.XML_TYPE, LNamespace.XML_TYPE_TEXT);
//		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
//		root.setAttribute(SVGAttributes.SVG_FONT_FAMILY, String.valueOf(t.getCurrentFont().getFamily()));
//		root.setAttribute(SVGAttributes.SVG_FILL, CSSColors.getColorName(t.getLineColour(), true));
//		root.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(t.getCurrentFont().getSize()));
//		root.setAttribute(ltdPref + SVGAttributes.SVG_X, String.valueOf(t.getPosition().x));
//
//		if(t.isItalic())
//			root.setAttribute(SVGAttributes.SVG_FONT_STYLE, SVGAttributes.SVG_FONT_STYLE_ITALIC);
//
//		if(t.isBold())
//			root.setAttribute(SVGAttributes.SVG_FONT_WEIGHT, SVGAttributes.SVG_FONT_WEIGHT_BOLD);
//
//		Element txt = new SVGTextElement(doc);
//		String str  = t.getCodePSTricksBody(new DrawBorders(false), IShape.PPC);
//		String cols = DviPsColors.getUserColoursCode(str);
//
//		if(cols!=null)
//			cols = cols.replace(System.getProperty("line.separator"), "");
//
//		txt.setAttribute(SVGAttributes.SVG_X, String.valueOf(t.getPosition().x));
//		txt.setAttribute(SVGAttributes.SVG_Y, String.valueOf(t.getPosition().y));
//		txt.appendChild(doc.createCDATASection(cols+str));
//		root.appendChild(txt);
//
//		setSVGRotationAttribute(root);
//
		return root;
	}
}
