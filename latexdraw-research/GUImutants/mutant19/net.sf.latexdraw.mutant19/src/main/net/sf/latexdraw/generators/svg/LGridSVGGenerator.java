package net.sf.latexdraw.generators.svg;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Point2D;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGLineElement;
import net.sf.latexdraw.parsers.svg.SVGTextElement;
import net.sf.latexdraw.parsers.svg.SVGTransform;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LNumber;
import sun.font.FontDesignMetrics;

/**
 * Defines a SVG generator for a grid.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
class LGridSVGGenerator extends LShapeSVGGenerator<IGrid> {
	/**
	 * Creates a generator of SVG grids.
	 * @param grid The grid used for the generation.
	 * @throws IllegalArgumentException If grid is null.
	 */
	protected LGridSVGGenerator(final IGrid grid) {
		super(grid);
	}


	/**
	 * Creates a grid from a G SVG element.
	 * @param elt The G SVG element used for the creation of a grid.
	 * @throws IllegalArgumentException If the given element is null.
	 */
	protected LGridSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates a grid from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LGridSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createGrid(true, DrawingTK.getFactory().createPoint()));

		if(elt==null)
			throw new IllegalArgumentException();

		final String prefix = LNamespace.LATEXDRAW_NAMESPACE+':';

		setDimensionGridElement(elt, prefix);

		SVGElement gridElt = getLaTeXDrawElement(elt, LNamespace.XML_TYPE_GRID_SUB);

		if(gridElt!=null)
			setSubGridElement(gridElt, prefix);

		gridElt = getLaTeXDrawElement(elt, LNamespace.XML_TYPE_GRID);

		if(gridElt!=null)
			setMainGridElement(gridElt, prefix);

		setLabelGridElement(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_TEXT));
		setNumber(elt);

		if(withTransformation)
			applyTransformations(elt);
	}



	/**
	 * Sets the dimensions of a grid from an SVGGElement.
	 */
	private void setDimensionGridElement(final SVGGElement elt, final String prefix) {
		List<Point2D> values;

		shape.setLineColour(elt.getStroke());
		values = SVGPointsParser.getPoints(elt.getAttribute(prefix+LNamespace.XML_GRID_END));

		if(values!=null && values.size()>0) {
			shape.setGridEndX(values.get(0).getX());
			shape.setGridEndY(values.get(0).getY());
		}

		values = SVGPointsParser.getPoints(elt.getAttribute(prefix+LNamespace.XML_GRID_START));

		if(values!=null && values.size()>0) {
			shape.setGridStartX(values.get(0).getX());
			shape.setGridStartY(values.get(0).getY());
		}

		values = SVGPointsParser.getPoints(elt.getAttribute(prefix+LNamespace.XML_GRID_ORIGIN));

		if(values!=null && values.size()>0) {
			shape.setOriginX(values.get(0).getX());
			shape.setOriginY(values.get(0).getY());
		}

		String v = elt.getAttribute(prefix+LNamespace.XML_GRID_X_SOUTH);

		if(v!=null)
			shape.setXLabelSouth(Boolean.parseBoolean(v));

		v = elt.getAttribute(prefix+LNamespace.XML_GRID_Y_WEST);

		if(v!=null)
			shape.setYLabelWest(Boolean.parseBoolean(v));
	}



	/**
	 * Sets the label properties of a grid from an SVGElement.
	 */
	private void setLabelGridElement(final SVGElement labelElt) {
		if(labelElt==null)
			shape.setLabelsSize(0);
		else {
			String val = labelElt.getAttribute(labelElt.getUsablePrefix()+SVGAttributes.SVG_FONT_SIZE);

			if(val!=null)
				try { shape.setLabelsSize((int)Double.parseDouble(val)); }
				catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }

			shape.setGridLabelsColour(labelElt.getStroke());
		}
	}



	/**
	 * Sets the main grid properties of a grid from an SVGElement.
	 */
	private void setMainGridElement(final SVGElement mainGridElt, final String prefix) {
		boolean isGridDotted = false;
		String val 			 = mainGridElt.getAttribute(prefix+LNamespace.XML_GRID_DOTS);

		if(val!=null)
			try {
				shape.setGridDots((int)Double.parseDouble(val));
				isGridDotted = shape.getGridDots()>0;
			}
			catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }

		val = mainGridElt.getAttribute(prefix+LNamespace.XML_GRID_WIDTH);

		if(isGridDotted)
			shape.setLineColour(CSSColors.INSTANCE.getRGBColour(mainGridElt.getFill()));
		else
			shape.setLineColour(mainGridElt.getStroke());

		if(val==null) {
			double st = mainGridElt.getStrokeWidth();

			if(!Double.isNaN(st))
				shape.setGridWidth(st);
		}
		else
			try{ shape.setGridWidth(Double.parseDouble(val));  }
			catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }
	}



	/**
	 * Sets the sub-grid properties of a grid from an SVGElement.
	 */
	private void setSubGridElement(final SVGElement subGridElt, final String prefix) {
		boolean isGridDotted = false;
		String val 			 = subGridElt.getAttribute(prefix+LNamespace.XML_GRID_DOTS);

		if(val!=null)
			try {
				shape.setSubGridDots((int)Double.parseDouble(val));
				isGridDotted = shape.getSubGridDots()>0;
			}
			catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }

		val = subGridElt.getAttribute(prefix+LNamespace.XML_GRID_SUB_DIV);

		if(val!=null)
			try{ shape.setSubGridDiv((int)Double.parseDouble(val));  }
			catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }

		val = subGridElt.getAttribute(prefix+LNamespace.XML_GRID_WIDTH);

		if(isGridDotted)
			shape.setSubGridColour(CSSColors.INSTANCE.getRGBColour(subGridElt.getFill()));
		else
			shape.setSubGridColour(subGridElt.getStroke());

		if(val==null) {
			double st = subGridElt.getStrokeWidth();

			if(!Double.isNaN(st))
				shape.setSubGridWidth(st);
		}
		else
			try{ shape.setSubGridWidth(Double.parseDouble(val));  }
			catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }
	}



	/**
	 * Creates the SVG element corresponding to the sub dotted part of the grid.
	 */
	private void createSVGSubGridDots(final SVGDocument document, final SVGElement elt, final String prefix, final double subGridDiv, final double unit,
									  final double xSubStep, final double ySubStep, final double minX, final double maxX,
									  final double minY, final double maxY, final int subGridDots, final double subGridWidth,
									  final double tlx, final double tly, final double brx, final double bry, final Color subGridColour) {
		double i, j, n, m, k;
		final double dotStep = (unit*IShape.PPC)/(subGridDots*subGridDiv);
		final double nbX = (maxX-minX)*subGridDiv;
		final double nbY = (maxY-minY)*subGridDiv;
		final SVGElement subgridDots = new SVGGElement(document);
		SVGElement dot;

		subgridDots.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(subGridColour, true));
		subgridDots.setAttribute(prefix+LNamespace.XML_TYPE, LNamespace.XML_TYPE_GRID_SUB);
		subgridDots.setAttribute(prefix+LNamespace.XML_GRID_DOTS, String.valueOf(subGridDots));
		subgridDots.setAttribute(prefix+LNamespace.XML_GRID_SUB_DIV, String.valueOf(subGridDots));
		subgridDots.setAttribute(prefix+LNamespace.XML_GRID_WIDTH, String.valueOf(subGridWidth));

		for(i=0, n=tlx; i<nbX; i++, n+=xSubStep)
			for(j=0, m=tly; j<=nbY; j++, m+=ySubStep)
				for(k=0; k<subGridDots; k++) {
					dot = new SVGCircleElement(document);
					dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(n+k*dotStep));
					dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(m));
					dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(subGridWidth/2.));
					subgridDots.appendChild(dot);
				}

		for(j=0, n=tly; j<nbY; j++, n+=ySubStep)
			for(i=0, m=tlx; i<=nbX; i++, m+=xSubStep)
				for(k=0; k<subGridDots; k++) {
					dot = new SVGCircleElement(document);
					dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(m));
					dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(n+k*dotStep));
					dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(subGridWidth/2.));
					subgridDots.appendChild(dot);
				}

		dot = new SVGCircleElement(document);
		dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(brx));
		dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(bry));
		dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(subGridWidth/2.));

		elt.appendChild(subgridDots);
	}



	/**
	 * Creates the SVG element corresponding to the sub not-dotted part of the grid.
	 */
	private void createSVGSubGridDiv(final SVGDocument document, final SVGElement elt, final String prefix, final double subGridDiv,
									 final double xSubStep, final double ySubStep, final double minX, final double maxX,
									  final double minY, final double maxY, final int subGridDots, final double subGridWidth,
									  final double tlx, final double tly, final double brx, final double bry, final Color subGridColour,
									  final double posX, final double posY, final double xStep, final double yStep) {
		double i, j, k;
		final SVGElement subgrids = new SVGGElement(document);
		SVGElement line;

		subgrids.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(subGridWidth));
		subgrids.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(subGridColour, true));
		subgrids.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_ROUND);
		subgrids.setAttribute(prefix+LNamespace.XML_TYPE, LNamespace.XML_TYPE_GRID_SUB);
		subgrids.setAttribute(prefix+LNamespace.XML_GRID_DOTS, String.valueOf(subGridDots));
		subgrids.setAttribute(prefix+LNamespace.XML_GRID_SUB_DIV, String.valueOf(subGridDiv));

		for(k=minX, i=posX; k<maxX; i+=xStep, k++)
			for(j=0; j<=subGridDiv; j++) {
				line = new SVGLineElement(document);
				line.setAttribute(SVGAttributes.SVG_X1, String.valueOf(i+xSubStep*j));
				line.setAttribute(SVGAttributes.SVG_X2, String.valueOf(i+xSubStep*j));
				line.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(bry));
				line.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(tly));
				subgrids.appendChild(line);
			}

		for(k=minY, i=posY; k<maxY; i-=yStep, k++)
			for(j=0; j<=subGridDiv; j++) {
				line = new SVGLineElement(document);
				line.setAttribute(SVGAttributes.SVG_X1, String.valueOf(tlx));
				line.setAttribute(SVGAttributes.SVG_X2, String.valueOf(brx));
				line.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(i-ySubStep*j));
				line.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(i-ySubStep*j));
				subgrids.appendChild(line);
			}

		elt.appendChild(subgrids);
	}


	/**
	 * Creates the SVG element corresponding to the main dotted part of the grid.
	 */
	private void createSVGGridDots(final SVGDocument document, final SVGElement elt, final String prefix, final double absStep,
								   final double minX, final double maxX, final double minY, final double maxY, final double tlx,
								   final double tly, final double brx, final double bry, final double unit, final double posX,
								   final double posY, final double xStep, final double yStep, final double gridWidth, final Color linesColour) {
		double k, i, m, n, l, j;
		final int gridDots = shape.getGridDots();
		final double dotStep = (unit*IShape.PPC)/gridDots;
		final SVGElement gridDotsElt = new SVGGElement(document);
		SVGElement dot;

		gridDotsElt.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(linesColour, true));
		gridDotsElt.setAttribute(prefix+LNamespace.XML_TYPE, LNamespace.XML_TYPE_GRID);
		gridDotsElt.setAttribute(prefix+LNamespace.XML_GRID_DOTS, String.valueOf(gridDots));
		gridDotsElt.setAttribute(prefix+LNamespace.XML_GRID_WIDTH, String.valueOf(gridWidth));

		for(k=minX, i=posX; k<=maxX; i+=xStep, k++)
			for(m=tly, n=minY; n<maxY; n++, m+=absStep)
				for(l=0, j=m; l<gridDots; l++, j+=dotStep) {
					dot = new SVGCircleElement(document);
					dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(i));
					dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(j));
					dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(gridWidth/2.));
					gridDotsElt.appendChild(dot);
				}

		for(k=minY, i=posY; k<=maxY; i-=yStep, k++)
			for(m=tlx, n=minX; n<maxX; n++, m+=absStep)
				for(l=0, j=m; l<gridDots; l++, j+=dotStep) {
					dot = new SVGCircleElement(document);
					dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(j));
					dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(i));
					dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(gridWidth/2.));
					gridDotsElt.appendChild(dot);
				}

		dot = new SVGCircleElement(document);
		dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(brx));
		dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(bry));
		dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(gridWidth/2.));
		gridDotsElt.appendChild(dot);

		elt.appendChild(gridDotsElt);
	}


	/**
	 * Creates the SVG element corresponding to the main not-dotted part of the grid.
	 */
	private void createSVGGridDiv(final SVGDocument document, final SVGElement elt, final String prefix, final double minX, final double maxX,
								  final double minY, final double maxY, final double tlx, final double tly, final double brx, final double bry,
								  final double posX, final double posY, final double xStep, final double yStep, final double gridWidth, final Color linesColour) {
		double k, i;
		final SVGElement grids = new SVGGElement(document);
		SVGElement line;

		grids.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(gridWidth));
		grids.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(linesColour, true));
		grids.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_SQUARE);
		grids.setAttribute(prefix+LNamespace.XML_TYPE, LNamespace.XML_TYPE_GRID);

		for(k=minX, i=posX; k<=maxX; i+=xStep, k++) {
			line = new SVGLineElement(document);
			line.setAttribute(SVGAttributes.SVG_X1, String.valueOf(i));
			line.setAttribute(SVGAttributes.SVG_X2, String.valueOf(i));
			line.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(bry));
			line.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(tly));
			grids.appendChild(line);
		}

		for(k=minY, i=posY; k<=maxY; i-=yStep, k++) {
			line = new SVGLineElement(document);
			line.setAttribute(SVGAttributes.SVG_X1, String.valueOf(tlx));
			line.setAttribute(SVGAttributes.SVG_X2, String.valueOf(brx));
			line.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(i));
			line.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(i));
			grids.appendChild(line);
		}

		elt.appendChild(grids);
	}


	/**
	 * Creates the SVG element corresponding to the labels of the grid.
	 */
	private void createSVGGridLabels(final SVGDocument document, final SVGElement elt, final String prefix, final double minX,
									 final double maxX, final double minY, final double maxY, final double tlx, final double tly,
									 final double xStep, final double yStep, final double gridWidth, final double absStep) {
		final int gridLabelsSize 	= shape.getLabelsSize();
		final boolean isXLabelSouth = shape.isXLabelSouth();
		final boolean isYLabelWest 	= shape.isYLabelWest();
		final double originX 		= shape.getOriginX();
		final double originY 		= shape.getOriginY();
		final Color gridLabelsColor = shape.getGridLabelsColour();
		final FontMetrics fontMetrics = FontDesignMetrics.getMetrics(new Font(null, Font.PLAIN, shape.getLabelsSize()));
		final float labelHeight 	= fontMetrics.getAscent();
		final float labelWidth 		= fontMetrics.stringWidth(String.valueOf((int)maxX));
		final double xorigin = xStep*originX;
		final double yorigin = isXLabelSouth  ? yStep*originY+labelHeight : yStep*originY-2;
		final double width=gridWidth/2., tmp = isXLabelSouth ? width : -width;
		final SVGElement texts = new SVGGElement(document);
		SVGElement text;
		String label;
		double i, j;

		texts.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(shape.getLabelsSize()));
		texts.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(gridLabelsColor, true));
		texts.setAttribute(prefix+LNamespace.XML_TYPE, LNamespace.XML_TYPE_TEXT);

		for(i=tlx + (isYLabelWest ? width+gridLabelsSize/4. : -width-labelWidth-gridLabelsSize/4.), j=minX; j<=maxX; i+=absStep, j++) {
			text = new SVGTextElement(document);
			text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)i));
			text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(yorigin+tmp)));
			text.setTextContent(String.valueOf((int)j));
			texts.appendChild(text);
		}

		if(isYLabelWest)
			for(i=tly + (isXLabelSouth ? -width-gridLabelsSize/4. : width+labelHeight), j=maxY ; j>=minY; i+=absStep, j--) {
				label = String.valueOf((int)j);
				text = new SVGTextElement(document);
				text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(xorigin-fontMetrics.stringWidth(label)-gridLabelsSize/4.-width)));
				text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)i));
				text.setTextContent(label);
				texts.appendChild(text);
			}
		else
			for(i=tly + (isXLabelSouth ? -width-gridLabelsSize/4. : width+labelHeight), j=maxY; j>=minY; i+=absStep, j--) {
				label = String.valueOf((int)j);
				text = new SVGTextElement(document);
				text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(xorigin+gridLabelsSize/4.+width)));
				text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)i));
				text.setTextContent(label);
				texts.appendChild(text);
			}

		elt.appendChild(texts);
	}


	/**
	 * Creates the SVG element corresponding to the grid.
	 */
	protected void createSVGGrid(final SVGElement elt, final SVGDocument document) {
		if(elt==null || document==null)
			return ;
		// Initialisation of the parameters.
		final String prefix	= LNamespace.LATEXDRAW_NAMESPACE+':';
		final double unit 	= shape.getUnit();
		final int subGridDiv= shape.getSubGridDiv();
		double xStep 		= IShape.PPC*unit;
		double xSubStep;
		double yStep 		= IShape.PPC*unit;
		double ySubStep;
		xStep *= shape.getGridEndX()<shape.getGridStartX() ? -1 : 1 ;
		yStep *= shape.getGridEndY()<shape.getGridStartY() ? -1 : 1 ;
		xSubStep = xStep/subGridDiv;
		ySubStep = yStep/subGridDiv;
		final int subGridDots = shape.getSubGridDots();
		final IPoint tl = shape.getTopLeftPoint();
		final IPoint br = shape.getBottomRightPoint();
		double tlx  = tl.getX();
		double tly  = tl.getY();
		double brx  = br.getX();
		double bry  = br.getY();
		final double minX = shape.getGridMinX();
		final double maxX = shape.getGridMaxX();
		final double minY = shape.getGridMinY();
		final double maxY = shape.getGridMaxY();
		final double absStep 	= Math.abs(xStep);
		final Color subGridColor= shape.getSubGridColour();
		final Color linesColor 	= shape.getLineColour();
		final double gridWidth 	= shape.getGridWidth();
		final double posX 		= Math.min(shape.getGridStartX(), shape.getGridEndX())*IShape.PPC*unit;
		final double posY 		= -Math.min(shape.getGridStartY(), shape.getGridEndY())*IShape.PPC*unit;
		final IPoint position 	= shape.getPosition();

		tlx -= position.getX();
		brx -= position.getX();
		tly -= position.getY();
		bry -= position.getY();
		elt.setAttribute(SVGAttributes.SVG_TRANSFORM, SVGTransform.createTranslation(position.getX(), position.getY()).toString());

		// Creation of the sub-grid
		if(subGridDots>0)
			createSVGSubGridDots(document, elt, prefix, subGridDiv, unit, xSubStep, ySubStep, minX, maxX, minY, maxY, subGridDots, shape.getSubGridWidth(),
								tlx, tly, brx, bry, subGridColor);
		else
			if(subGridDiv>1)
				createSVGSubGridDiv(document, elt, prefix, subGridDiv, xSubStep, ySubStep, minX, maxX, minY, maxY, subGridDots, shape.getSubGridWidth(),
									tlx, tly, brx, bry, subGridColor, posX, posY, xStep, yStep);

		if(shape.getGridDots()>0)
			createSVGGridDots(document, elt, prefix, absStep, minX, maxX, minY, maxY, tlx, tly, brx, bry, unit, posX, posY, xStep, yStep,
								gridWidth, linesColor);
		else
			createSVGGridDiv(document, elt, prefix, minX, maxX, minY, maxY, tlx, tly, brx, bry, posX, posY, xStep, yStep, gridWidth, linesColor);

		if(shape.getLabelsSize()>0)
			createSVGGridLabels(document, elt, prefix, minX, maxX, minY, maxY, tlx, tly, xStep, yStep, gridWidth, absStep);

		if(LNumber.INSTANCE.equals(shape.getRotationAngle()%(Math.PI*2), 0.))
			setSVGRotationAttribute(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

		final String prefix   = LNamespace.LATEXDRAW_NAMESPACE+':';
		final SVGElement root = new SVGGElement(doc);

		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(prefix+LNamespace.XML_TYPE, LNamespace.XML_TYPE_GRID);
		root.setAttribute(prefix+LNamespace.XML_GRID_X_SOUTH, String.valueOf(shape.isXLabelSouth()));
		root.setAttribute(prefix+LNamespace.XML_GRID_Y_WEST, String.valueOf(shape.isYLabelWest()));
		root.setAttribute(prefix+LNamespace.XML_GRID_UNIT, String.valueOf(shape.getUnit()));
		root.setAttribute(prefix+LNamespace.XML_GRID_END, shape.getGridEndX() + " " + shape.getGridEndY());//$NON-NLS-1$
		root.setAttribute(prefix+LNamespace.XML_GRID_START, shape.getGridStartX() + " " + shape.getGridStartY());//$NON-NLS-1$
		root.setAttribute(prefix+LNamespace.XML_GRID_ORIGIN, shape.getOriginX() + " " + shape.getOriginY());//$NON-NLS-1$
		createSVGGrid(root, doc);

		return root;
	}
}
