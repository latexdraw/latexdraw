package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.view.PlotViewHelper;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a plotted functions.<br>
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
 * 2014-11-20<br>
 * @author Arnaud BLOUIN
 */
class LPlotSVGGenerator extends LShapeSVGGenerator<IPlot> {
	static final String XML_TYPE_PLOT 	= "plot"; //$NON-NLS-1$
	static final String XML_NB_POINTS 	= "nbpts"; //$NON-NLS-1$
	static final String XML_EQ 			= "eq"; //$NON-NLS-1$
	static final String XML_MIN 		= "min"; //$NON-NLS-1$
	static final String XML_MAX 		= "max"; //$NON-NLS-1$
	static final String XML_XSCALE 		= "xscale"; //$NON-NLS-1$
	static final String XML_YSCALE 		= "yscale"; //$NON-NLS-1$
	static final String XML_POLAR 		= "polar"; //$NON-NLS-1$
	static final String XML_STYLE 		= "plotstyle"; //$NON-NLS-1$

	
	protected LPlotSVGGenerator(final IPlot plot){
		super(plot);
	}


	/**
	 * Creates a Bézier curve from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	protected LPlotSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 5, "x", false));

		setSVGParameters(elt);
		
		shape.setPlotEquation(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_EQ));
		shape.setPlotStyle(PlotStyle.getPlotStyle(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_STYLE)));
		
		try { shape.setPlotMinX(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MIN)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setPlotMaxX(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MAX)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		// Repetition just to assure the setting of these values because of the minX<MaxX constraint.
		try { shape.setPlotMinX(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MIN)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setPlotMaxX(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MAX)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }		
		
		try { shape.setNbPlottedPoints(Integer.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_NB_POINTS)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setPolar(Boolean.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_POLAR)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setX(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION_X)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setY(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION_Y)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setXScale(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_XSCALE)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		try { shape.setYScale(Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_YSCALE)));}
		catch(NumberFormatException | NullPointerException ex) { BadaboomCollector.INSTANCE.add(ex); }
		
		if(elt.getChildNodes().getLength()>0 && elt.getChildNodes().item(0) instanceof SVGElement) {
			IShape sh = IShapeSVGFactory.INSTANCE.createShape((SVGElement)elt.getChildNodes().item(0));
			if(sh instanceof IDot) {
				IDot dot = (IDot)sh;
				shape.setDiametre(dot.getDiametre());
				shape.setDotStyle(dot.getDotStyle());
				shape.setLineColour(dot.getLineColour());
				if(dot.isFillable())
					shape.setDotFillingCol(dot.getDotFillingCol());
			}
		}
		
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation)
			applyTransformations(elt);
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null || doc.getFirstChild().getDefs()==null)
			return null;

		final SVGElement root = new SVGGElement(doc);
		final double minX = shape.getPlotMinX();
		final double maxX = shape.getPlotMaxX();
		final double step = shape.getPlottingStep();
		final double posX = shape.getPosition().getX();
		final double posY = shape.getPosition().getY();

        if(shape.hasShadow()) {
        	final SVGGElement shad = new SVGGElement(doc);
    		switch(shape.getPlotStyle()) {
    			case LINE: toSVGLine(shad, doc, posX, posY, minX, maxX, step); break;
    			case CURVE: toSVGCurve(shad, doc, posX, posY, minX, maxX, step); break;
    			case ECURVE: toSVGCurve(shad, doc, posX, posY, minX+step, maxX-step, step); break;
    			case CCURVE: toSVGCurve(shad, doc, posX, posY, minX, maxX, step); break;
    			case DOTS: toSVGDots(shad, doc, posX, posY, minX, maxX, step); break;
    			case POLYGON: toSVGPolygon(shad, doc, posX, posY, minX, maxX, step); break;
    		}
        	setSVGShadowAttributes(shad, false);
        	root.appendChild(shad);
        }
        
        if(shape.hasDbleBord()) {
        	final SVGGElement dble = new SVGGElement(doc);
    		switch(shape.getPlotStyle()) {
    			case LINE: toSVGLine(dble, doc, posX, posY, minX, maxX, step); break;
    			case CURVE: toSVGCurve(dble, doc, posX, posY, minX, maxX, step); break;
    			case ECURVE: toSVGCurve(dble, doc, posX, posY, minX+step, maxX-step, step); break;
    			case CCURVE: toSVGCurve(dble, doc, posX, posY, minX, maxX, step); break;
    			case DOTS: toSVGDots(dble, doc, posX, posY, minX, maxX, step); break;
    			case POLYGON: toSVGPolygon(dble, doc, posX, posY, minX, maxX, step); break;
    		}
    		setSVGDoubleBordersAttributes(dble);
        	root.appendChild(dble);
        }
		
		switch(shape.getPlotStyle()) {
			case LINE: toSVGLine(root, doc, posX, posY, minX, maxX, step); break;
			case CURVE: toSVGCurve(root, doc, posX, posY, minX, maxX, step); break;
			case ECURVE: toSVGCurve(root, doc, posX, posY, minX+step, maxX-step, step); break;
			case CCURVE: toSVGCurve(root, doc, posX, posY, minX, maxX, step); break;
			case DOTS: toSVGDots(root, doc, posX, posY, minX, maxX, step); break;
			case POLYGON: toSVGPolygon(root, doc, posX, posY, minX, maxX, step); break;
		}

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, XML_TYPE_PLOT);
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_POLAR, Boolean.toString(shape.isPolar()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_EQ, shape.getPlotEquation());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_STYLE, shape.getPlotStyle().getPSTToken());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_MIN, Double.toString(shape.getPlotMinX()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_MAX, Double.toString(shape.getPlotMaxX()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_NB_POINTS, Integer.toString(shape.getNbPlottedPoints()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_XSCALE, Double.toString(shape.getXScale()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+XML_YSCALE, Double.toString(shape.getYScale()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION_X, Double.toString(shape.getX()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION_Y, Double.toString(shape.getY()));
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		setSVGAttributes(doc, root, true);
		
		return root;
	}
	
	
	private void toSVGDots(final SVGElement elt, final SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		for(final IDot dot : PlotViewHelper.INSTANCE.updatePoints(shape, posX, posY, minX, maxX, step))
			elt.appendChild(SVGShapesFactory.INSTANCE.createSVGElement(dot, doc));
	}
	
	
	private void toSVGPolygon(final SVGElement elt, final SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		elt.appendChild(SVGShapesFactory.INSTANCE.createSVGElement(PlotViewHelper.INSTANCE.updatePolygon(shape, posX, posY, minX, maxX, step), doc));
	}
	
	
	private void toSVGLine(final SVGElement elt, final SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		elt.appendChild(SVGShapesFactory.INSTANCE.createSVGElement(PlotViewHelper.INSTANCE.updateLine(shape, posX, posY, minX, maxX, step), doc));
	}
	
	
	private void toSVGCurve(final SVGElement elt, final SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		elt.appendChild(SVGShapesFactory.INSTANCE.createSVGElement(PlotViewHelper.INSTANCE.updateCurve(shape, posX, posY, minX, maxX, step), doc));
	}
}
