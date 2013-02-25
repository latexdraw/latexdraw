package net.sf.latexdraw.generators.svg;

import java.awt.geom.Point2D;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGNodeList;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for an shape.<br>
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
class LAxeSVGGenerator extends LShapeSVGGenerator<IAxes> {

	protected LAxeSVGGenerator(final IAxes shape) {
		super(shape);
	}


	protected LAxeSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates axes from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @param withTransformation If true, the SVG transformations will be applied.
	 * @since 2.0.0
	 */
	protected LAxeSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint()));

		if(elt==null)
			throw new IllegalArgumentException();

		setNumber(elt);
		setSVGParameters(elt);

		List<Point2D> values;
		String pref = LNamespace.LATEXDRAW_NAMESPACE+':';
		String str;

		shape.setYLabelWest(Boolean.parseBoolean(elt.getAttribute(pref+LNamespace.XML_AXE_IS_WEST)));
		shape.setXLabelSouth(Boolean.parseBoolean(elt.getAttribute(pref+LNamespace.XML_AXE_IS_SOUTH)));
		shape.setShowOrigin(Boolean.parseBoolean(elt.getAttribute(pref+LNamespace.XML_AXE_SHOW_ORIGIN)));
		shape.setAxesStyle(AxesStyle.getStyle(elt.getAttribute(pref+LNamespace.XML_STYLE)));
		shape.setTicksDisplayed(PlottingStyle.getStyle(elt.getAttribute(pref+LNamespace.XML_AXE_SHOW_TICKS)));
		shape.setTicksStyle(TicksStyle.getStyle(elt.getAttribute(pref+LNamespace.XML_AXE_TICKS_STYLE)));
		shape.setLabelsDisplayed(PlottingStyle.getStyle(elt.getAttribute(pref+LNamespace.XML_AXE_LABELS_STYLE)));
		str = elt.getAttribute(pref+LNamespace.XML_AXE_TICKS_SIZE);

		if(str!=null)
			try{ shape.setTicksSize(Double.parseDouble(str)); }
			catch(NumberFormatException e) { /* */ }

		values = SVGPointsParser.getPoints(elt.getAttribute(pref+LNamespace.XML_GRID_END));

		if(values!=null && values.size()>0) {
			shape.setGridEndX((int)values.get(0).getX());
			shape.setGridEndY((int)values.get(0).getY());
		}

		values = SVGPointsParser.getPoints(elt.getAttribute(pref+LNamespace.XML_GRID_START));

		if(values!=null && values.size()>0) {
			shape.setGridStartX((int)values.get(0).getX());
			shape.setGridStartY((int)values.get(0).getY());
		}

		values = SVGPointsParser.getPoints(elt.getAttribute(pref+LNamespace.XML_GRID_ORIGIN));

		if(values!=null && values.size()>0) {
			shape.setOriginX((int)values.get(0).getX());
			shape.setOriginY((int)values.get(0).getY());
		}

		values = SVGPointsParser.getPoints(elt.getAttribute(pref+LNamespace.XML_AXE_INCREMENT));

		if(values!=null && values.size()>0) {
			shape.setIncrementX(values.get(0).getX());
			shape.setIncrementY(values.get(0).getY());
		}

		values = SVGPointsParser.getPoints(elt.getAttribute(pref+LNamespace.XML_AXE_DIST_LABELS));

		if(values!=null && values.size()>0) {
			shape.setDistLabelsX(values.get(0).getX());
			shape.setDistLabelsY(values.get(0).getY());
		}

		/* Looking for the two axe in order to get the position of the axes. */
		SVGNodeList nl = elt.getChildren(SVGElements.SVG_G);
		int i=0, size = nl.getLength();
		SVGGElement l1=null, l2=null;
		SVGElement element;

		while((l1==null || l2==null) && i<size) {
			element = nl.item(i);

			if(element instanceof SVGGElement)
				if(l1==null)
					l1 = (SVGGElement)element;
				else
					l2 = (SVGGElement)element;

			i++;
		}

		if(l1!=null && l2!=null)
			try {
				IPolyline la = new LPolylinesSVGGenerator(l1, false).shape;
				IPolyline lb = new LPolylinesSVGGenerator(l2, false).shape;

				shape.setPosition(DrawingTK.getFactory().createPoint(lb.getPtAt(0).getX(), la.getPtAt(0).getY()));
				shape.setLineStyle(la.getLineStyle());

				if(shape.isXLabelSouth()) {
					shape.getArrowAt(0).setArrowStyle(la.getArrowAt(0).getArrowStyle());
					shape.getArrowAt(1).setArrowStyle(la.getArrowAt(1).getArrowStyle());
				}
				else {
					shape.getArrowAt(0).setArrowStyle(la.getArrowAt(1).getArrowStyle());
					shape.getArrowAt(1).setArrowStyle(la.getArrowAt(0).getArrowStyle());
				}
			}
			catch(IllegalArgumentException e) { BadaboomCollector.INSTANCE.add(e); }

		homogeniseArrows(shape.getArrowAt(0), shape.getArrowAt(1));
		applyTransformations(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

//		final Graphics2D2SVG graphics = new Graphics2D2SVG(doc);
//		IViewShape view = View2DTK.getFactory().createView(shape);

//        view.paint(graphics);
        //  graphics.getElement();
        final SVGElement root = new SVGGElement(doc);
		String pref = LNamespace.LATEXDRAW_NAMESPACE+':';
		setThickness(root, shape.getThickness(), false, 0.);
		root.setStroke(shape.getLineColour());

		root.setAttribute(pref+LNamespace.XML_AXE_IS_WEST, String.valueOf(shape.isYLabelWest()));
		root.setAttribute(pref+LNamespace.XML_AXE_IS_SOUTH, String.valueOf(shape.isXLabelSouth()));
		root.setAttribute(pref+LNamespace.XML_STYLE, shape.getAxesStyle().toString());
		root.setAttribute(pref+LNamespace.XML_GRID_START, shape.getGridStartX() + " " + shape.getGridStartY()); //$NON-NLS-1$
		root.setAttribute(pref+LNamespace.XML_GRID_END, shape.getGridEndX() + " " + shape.getGridEndY());//$NON-NLS-1$
		root.setAttribute(pref+LNamespace.XML_GRID_ORIGIN, shape.getOriginX() + " " + shape.getOriginY());//$NON-NLS-1$
		root.setAttribute(pref+LNamespace.XML_AXE_INCREMENT, shape.getIncrementX() + " " + shape.getIncrementY());//$NON-NLS-1$
		root.setAttribute(pref+LNamespace.XML_AXE_DIST_LABELS, shape.getDistLabelsX() + " " + shape.getDistLabelsY());//$NON-NLS-1$
		root.setAttribute(pref+LNamespace.XML_AXE_TICKS_SIZE, String.valueOf(shape.getTicksSize()));
		root.setAttribute(pref+LNamespace.XML_AXE_SHOW_ORIGIN, String.valueOf(shape.isShowOrigin()));
		root.setAttribute(pref+LNamespace.XML_AXE_SHOW_TICKS, shape.getTicksDisplayed().toString());
		root.setAttribute(pref+LNamespace.XML_AXE_LABELS_STYLE, shape.getLabelsDisplayed().toString());
		root.setAttribute(pref+LNamespace.XML_AXE_TICKS_STYLE, shape.getTicksStyle().toString());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_AXE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		createSVGAxe(root, doc);

//		view.flush();
//		graphics.dispose();

		return root;
	}


	private void createArrows(final SVGElement elt, final SVGDocument document) {
		if(shape.getAxesStyle().supportsArrows() && shape.getArrows().size()==4) {
			final IShapeFactory fac = DrawingTK.getFactory();
			final double posX = shape.getPosition().getX();
			final double posY = shape.getPosition().getY();
			final IArrow arr0 = shape.getArrowAt(0);
			final IArrow arr1 = shape.getArrowAt(1);
			final double arr0Reduction = arr0.getArrowStyle().needsLineReduction() ? arr0.getArrowShapedWidth() : 0.;
			final double arr1Reduction = arr1.getArrowStyle().needsLineReduction() ? arr1.getArrowShapedWidth() : 0.;
			final IPolyline xLine = fac.createPolyline(false);
			final IPolyline yLine = fac.createPolyline(false);

			xLine.addPoint(fac.createPoint(posX+shape.getGridStartX()*IShape.PPC + arr0Reduction, posY));
			xLine.addPoint(fac.createPoint(posX+shape.getGridEndX()*IShape.PPC - arr1Reduction, posY));
			yLine.addPoint(fac.createPoint(posX, posY-shape.getGridStartY()*IShape.PPC - arr0Reduction));
			yLine.addPoint(fac.createPoint(posX, posY-shape.getGridEndY()*IShape.PPC + arr1Reduction));

			xLine.getArrowAt(0).copy(arr0);
			xLine.getArrowAt(1).copy(arr1);
			yLine.getArrowAt(0).copy(shape.getArrowAt(2));
			yLine.getArrowAt(1).copy(shape.getArrowAt(3));
			final SVGElement eltX = new LPolylinesSVGGenerator(xLine).toSVG(document);
			final SVGElement eltY = new LPolylinesSVGGenerator(yLine).toSVG(document);

			elt.appendChild(eltX);
			elt.appendChild(eltY);
		}
	}


	private void createFrame(final SVGElement elt, final SVGDocument document) {
		final double gridEndx = shape.getGridEndX();
		final double gridEndy = shape.getGridEndY();

		if(gridEndx>0 || gridEndy>0) {
			final double positionx = shape.getPosition().getX();
			final double positiony = shape.getPosition().getY();
			final double xMax = positionx+gridEndx*IShape.PPC;
			final double yMax = positiony-gridEndy*IShape.PPC;
			final IPoint pos  = DrawingTK.getFactory().createPoint(positionx, gridEndy>0 ? yMax : positiony);
			final IRectangle r= DrawingTK.getFactory().createRectangle(pos, Math.abs(pos.getX()-(gridEndx>0 ? xMax : positionx)),
																		Math.abs(pos.getY()-positiony), false);

			r.setBordersPosition(BorderPos.MID);
			r.setLineColour(shape.getLineColour());
			r.setLineStyle(shape.getLineStyle());
			r.setThickness(shape.getThickness());
			elt.appendChild(new LRectangleSVGGenerator(r).toSVG(document));
		}
	}



	private void createSVGAxe(final SVGElement elt, final SVGDocument document) {
		switch(shape.getAxesStyle()) {
			case AXES:
				createArrows(elt, document);
				break;
			case FRAME:
				createFrame(elt, document);
				break;
			case NONE:
				break;
		}

//		double minX, maxX, minY, maxY, maxX3, minX3, maxY3, minY3;
//		IPoint increment = shape.getIncrement();
//		IPoint gridEnd   = shape.getGridEnd();
//		IPoint gridStart = shape.getGridStart();
//		IPoint position  = shape.getPosition();
//		IPoint origin    = shape.getOrigin();
//		double gridEndx 	= gridEnd.getX();
//		double gridEndy 	= gridEnd.getY();
//		double positionx  	= position.getX();
//		double positiony  	= position.getY();
//		double incrementx 	= increment.getX();
//		double incrementy 	= increment.getY();
//		double originx 		= origin.getX();
//		double originy 		= origin.getY();
//		double gridStartx 	= gridStart.getX();
//		double gridStarty 	= gridStart.getY();
//		double distLabelsX 		 = shape.getDistLabelsX();
//		double distLabelsY 		 = shape.getDistLabelsY();
//		double gapX = distLabelsX==0. ? IShape.PPC : (distLabelsX/incrementx)*IShape.PPC, i;
//		double gapY = distLabelsY==0. ? IShape.PPC : (distLabelsY/incrementy)*IShape.PPC;
//		double rotationAngle 	= shape.getRotationAngle();
//		double lgth;
//		TicksStyle ticksStyle 	= shape.getTicksStyle();
//		AxesStyle axesStyle  	= shape.getAxesStyle();
//		PlottingStyle ticksDisplayed  = shape.getTicksDisplayed();
//		PlottingStyle labelsDisplayed = shape.getLabelsDisplayed();
//		double ticksSize  		= shape.getTicksSize();
//		boolean isYLabelWest  	= shape.isYLabelWest();
//		boolean isXLabelSouth 	= shape.isXLabelSouth();
//		boolean showOrigin    	= shape.isShowOrigin();
//		FontMetrics fontMetrics = shape.getFontMetrics();
//		Font font 				= shape.getFont();
//		IArrow arrowHead1 		= shape.getArrowAt(0);
//		IArrow arrowHead2 		= shape.getArrowAt(1);
//		boolean ticksBot = ticksStyle.equals(TicksStyle.BOTTOM) || ticksStyle.equals(TicksStyle.FULL);
//		double thickness = shape.getThickness();
//		int j;
//
//        elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_AXE);
//        elt.setAttribute(SVGAttributes.SVG_ID, getSVGID());
//
//		if(gridEndx<gridStartx) {
//			minX = gridEndx;
//			maxX = gridStartx;
//		}
//		else {
//			minX = gridStartx;
//			maxX = gridEndx;
//		}
//
//		if(gridEnd.getY()<gridStart.getY()) {
//			minY = gridEnd.getY();
//			maxY = gridStart.getY();
//		}
//		else {
//			minY = gridStart.getY();
//			maxY = gridEnd.getY();
//		}
//
//		if(distLabelsX!=0.) {
//			if(maxX!=0.) {
//				maxX = maxX/distLabelsX;
//				if(maxX==0.)
//					maxX=0.1;
//			}
//
//			if(minX!=0) {
//				minX = minX/distLabelsX;
//				if(minX==0.)
//					minX=0.1;
//			}
//		}
//
//		if(distLabelsY!=0.) {
//			if(maxY!=0.) {
//				maxY = maxY/distLabelsY;
//				if(maxY==0.)
//					maxY=0.1;
//			}
//
//			if(minY!=0.) {
//				minY = minY/distLabelsY;
//				if(minY==0.)
//					minY=0.1;
//			}
//		}
//
//		boolean arrow1Ok = !arrowHead1.getArrowStyle().equals(PSTricksConstants.NONEARROW_STYLE);
//		boolean arrow2Ok = !arrowHead2.getArrowStyle().equals(PSTricksConstants.NONEARROW_STYLE);
//
//		minX3 = axesStyle.equals(AxesStyle.AXES) && ((arrow1Ok && isYLabelWest) ||
//				(arrow2Ok && !isYLabelWest)) && gridStartx!=0 && (distLabelsX==0. || distLabelsX==1.)? minX+1 : minX;
//
//		minY3 = axesStyle.equals(AxesStyle.AXES) && ((arrow1Ok && isXLabelSouth) ||
//				(arrow2Ok && !isXLabelSouth)) && gridStart.getY()!=0 && (distLabelsY==0. || distLabelsY==1.)? minY+1 : minY;
//
//		maxX3 = axesStyle.equals(AxesStyle.AXES) &&
//		 		((arrow2Ok && isYLabelWest) || (arrow1Ok && !isYLabelWest)) && (distLabelsX==0. || distLabelsX==1.)? maxX-1 : maxX;
//
//		maxY3 = axesStyle.equals(AxesStyle.AXES) &&
//		 		((arrow2Ok && isXLabelSouth) || (arrow1Ok && !isXLabelSouth)) && (distLabelsY==0. || distLabelsY==1.)? maxY-1 : maxY;
//
//		minX3 = minX>0 ? 0 : minX3;
//		maxX3 = maxX<0 ? 0 : maxX3;
//		minY3 = minY>0 ? 0 : minY3;
//		maxY3 = maxY<0 ? 0 : maxY3;
//
//		switch(axesStyle)
//		{
//			case AXES:
//
//				boolean arrowOk = (float)minX!=(float)maxX || (float)maxX!=(float)minY || (float)minY!=(float)maxY;
//				LLines xLine = new LLines(new LPoint(positionx+gridStartx*IShape.PPC, position.getY()),
//										new LPoint(positionx+gridEndx*IShape.PPC, position.getY()), true);
//				LLines yLine = new LLines(new LPoint(positionx, position.getY()-gridEnd.getY()*IShape.PPC),
//										new LPoint(positionx, position.getY()-gridStart.getY()*IShape.PPC), true);
//
//				xLine.setLineColour(shape.getLineColour());
//				xLine.setLineStyle(shape.getLineStyle());
//				xLine.setThickness(thickness);
//				yLine.setLineColour(shape.getLineColour());
//				yLine.setLineStyle(shape.getLineStyle());
//				yLine.setThickness(thickness);
//
//				if(!arrowHead1.getArrowStyle().getPSTToken().equals(PSTricksConstants.NONEARROW_STYLE) && arrowOk) {
//					String arrowStyle 	= arrowHead1.getArrowStyle().getPSTToken();
//					lgth 				= arrowHead1.getArrowHeadLength();
//					boolean isArrow 	= arrowHead1.needReduceLine();
//
//					if((minX!=0 && isYLabelWest) || (maxX!=0 && !isYLabelWest) || maxY==minY)
//					{
//						if(isArrow && !isYLabelWest) {
//							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
//								arrowStyle = PSTricksConstants.LARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
//								arrowStyle = PSTricksConstants.RARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
//								arrowStyle = PSTricksConstants.DRARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
//								arrowStyle = PSTricksConstants.DLARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
//						}
//
//						if(isArrow)
//							if(isYLabelWest)
//								 xLine.getPt1().x-=lgth;
//							else xLine.getPt2().x+=lgth;
//
//						if(isYLabelWest)
//							xLine.setArrow1Style(arrowStyle);
//						else
//							xLine.setArrow2Style(arrowStyle);
//					}
//
//					if((minY!=0 && isXLabelSouth) || (maxY!=0 && !isXLabelSouth) || maxX==minX)
//					{
//						arrowStyle = arrowHead1.getArrowStyle().getPSTToken();
//
//						if(isArrow && isXLabelSouth) {
//							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
//								arrowStyle = PSTricksConstants.LARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
//								arrowStyle = PSTricksConstants.RARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
//								arrowStyle = PSTricksConstants.DRARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
//								arrowStyle = PSTricksConstants.DLARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
//						}
//
//						if(isArrow)
//							if(isXLabelSouth)
//								 yLine.getPt2().y+=lgth;
//							else yLine.getPt1().y-=lgth;
//
//						if(isXLabelSouth)
//							yLine.setArrow2Style(arrowStyle);
//						else
//							yLine.setArrow1Style(arrowStyle);
//					}
//				}
//
//				if(!arrowHead2.getArrowStyle().getPSTToken().equals(PSTricksConstants.NONEARROW_STYLE) && arrowOk) {
//					String arrowStyle = arrowHead2.getArrowStyle().getPSTToken();
//					lgth = arrowHead2.getArrowHeadLength();
//					boolean isArrow = arrowHead2.needReduceLine();
//
//					if((maxY!=0 && isXLabelSouth) || (minY!=0 && !isXLabelSouth) || maxY==minY)
//					{
//						if(isArrow && !isXLabelSouth) {
//							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
//								arrowStyle = PSTricksConstants.LARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
//								arrowStyle = PSTricksConstants.RARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
//								arrowStyle = PSTricksConstants.DRARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
//								arrowStyle = PSTricksConstants.DLARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
//						}
//
//						if(maxY==minY && minY==0) {
//							if(isArrow)
//								if(maxX>0)
//									 xLine.getPt1().y-=lgth;
//								else xLine.getPt2().y+=lgth;
//
//							if(maxX>0)
//								xLine.setArrow1Style(arrowStyle);
//							else
//								xLine.setArrow2Style(arrowStyle);
//						}
//						else {
//							if(isArrow)
//								if(isXLabelSouth)
//									 yLine.getPt1().y-=lgth;
//								else yLine.getPt2().y+=lgth;
//
//							if(isXLabelSouth)
//								yLine.setArrow1Style(arrowStyle);
//							else
//								yLine.setArrow2Style(arrowStyle);
//						}
//					}
//
//					if((maxX!=0 && isYLabelWest) || (minX!=0 && !isYLabelWest) || maxX==minX)
//					{
//						arrowStyle = arrowHead2.getArrowStyle().getPSTToken();
//
//						if(isArrow && isYLabelWest) {
//							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
//								arrowStyle = PSTricksConstants.LARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
//								arrowStyle = PSTricksConstants.RARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
//								arrowStyle = PSTricksConstants.DRARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
//								arrowStyle = PSTricksConstants.DLARROW_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
//							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
//								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
//						}
//
//						if(maxX==minX && minX==0) {
//							if(isArrow)
//								if(maxY>0)
//									 yLine.getPt2().x+=lgth;
//								else xLine.getPt1().x-=lgth;
//
//							if(maxY>0)
//								yLine.setArrow2Style(arrowStyle);
//							else
//								yLine.setArrow1Style(arrowStyle);
//						}
//						else {
//							if(isArrow)
//								if(isYLabelWest)
//									 xLine.getPt2().x+=lgth;
//								else xLine.getPt1().x-=lgth;
//
//							if(isYLabelWest)
//								xLine.setArrow2Style(arrowStyle);
//							else
//								xLine.setArrow1Style(arrowStyle);
//						}
//					}
//				}
//
//				elt.appendChild(new LLineSVGGenerator(xLine).toSVG(document));
//				elt.appendChild(new LLineSVGGenerator(yLine).toSVG(document));
//
//				break;
//
//			case FRAME:
//				if(gridEndx>0 || gridEndy>0) {
//					double xMax = positionx+gridEndx*IShape.PPC;
//					double yMax = positiony-gridEndy*IShape.PPC;
//					IPoint pos  = new LPoint(positionx, gridEndy>0?yMax:positiony);
//
//					IRectangle r = new LRectangle(pos, Math.abs(pos.getX()-(gridEndx>0?xMax:positionx)),
//										Math.abs(pos.getY()-positiony), false);
//
//					r.setBordersPosition(BorderPos.MID);
//					r.setLineColour(shape.getLineColour());
//					r.setLineStyle(shape.getLineStyle());
//					r.setThickness((float)thickness);
//					elt.appendChild(new LRectangleSVGGenerator(r).toSVG(document));
//				}
//				break;
//		}
//
//		double endX = distLabelsX!=0. ? maxX3 : maxX3/incrementx;
//		double endY = distLabelsY!=0. ? maxY3 : maxY3/increment.getY();
//		double startX = distLabelsX!=0. ? minX3 : minX3/incrementx;
//		double startY = distLabelsY!=0. ? minY3 : minY3/increment.getY();
//
//		if(!ticksDisplayed.equals(PlottingStyle.NONE))// We draw the ticks
//		{
//			if(ticksStyle.equals(TicksStyle.FULL) || (ticksStyle.equals(TicksStyle.TOP) && maxY>0) ||
//			  (ticksStyle.equals(TicksStyle.BOTTOM) && maxY<=0))
//				if(ticksDisplayed.equals(PlottingStyle.X) || ticksDisplayed.equals(PlottingStyle.ALL)) {
//					SVGElement ticksX 	= new SVGGElement(document);
//					Line l 				= new LLine(false);
//					l.setThickness((float)thickness);
//					l.setLinesColor(shape.getLineColour());
//
//					for(j=1; j<=endX; j++)
//						try{ ticksX.appendChild(new SVGLineElement(positionx+j*gapX*incrementx, positiony,
//								            positionx+j*gapX*incrementx, positiony-ticksSize-thickness/2., document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					for(j=-1; j>=startX; j--)
//						try{ ticksX.appendChild(new SVGLineElement(positionx+j*gapX*incrementx, positiony,
//											positionx+j*gapX*incrementx, positiony-ticksSize-thickness/2., document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					elt.appendChild(ticksX);
//				}
//
//			if(ticksStyle.equals(TicksStyle.FULL) || (ticksStyle.equals(TicksStyle.TOP) && maxX>0) ||
//			  (ticksStyle.equals(TicksStyle.BOTTOM) && maxX<=0))
//				if(ticksDisplayed.equals(PlottingStyle.Y) || ticksDisplayed.equals(PlottingStyle.ALL)) {
//					SVGElement ticksY = new SVGGElement(document);
//
//					for(j=1; j<=endY; j++)
//						try{ ticksY.appendChild(new SVGLineElement(positionx, positiony-j*gapY*increment.getY(),
//								  			positionx+ticksSize+thickness/2., positiony-j*gapY*increment.getY(), document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					for(j=-1; j>=startY; j--)
//						try{ ticksY.appendChild(new SVGLineElement(positionx, positiony-j*gapY*increment.getY(),
//											positionx+ticksSize+thickness/2., positiony-j*gapY*increment.getY(), document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					elt.appendChild(ticksY);
//				}
//
//			if(ticksStyle.equals(TicksStyle.FULL) || (ticksStyle.equals(TicksStyle.BOTTOM) && maxY>0) ||
//			  (ticksStyle.equals(TicksStyle.TOP) && maxY<=0))
//				if(ticksDisplayed.equals(PlottingStyle.X) || ticksDisplayed.equals(PlottingStyle.ALL)) {
//					SVGElement ticksX = new SVGGElement(document);
//
//					for(j=1; j<=endX; j++)
//						try{ ticksX.appendChild(new SVGLineElement(positionx+j*gapX*incrementx, positiony,
//											positionx+j*gapX*incrementx, positiony+ticksSize+thickness/2., document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					for(j=-1; j>=startX; j--)
//						try{ ticksX.appendChild(new SVGLineElement(positionx+j*gapX*incrementx, positiony,
//											positionx+j*gapX*incrementx, positiony+ticksSize+thickness/2., document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					elt.appendChild(ticksX);
//				}
//
//			if(ticksStyle.equals(TicksStyle.FULL) || (ticksStyle.equals(TicksStyle.BOTTOM) && maxX>0) ||
//				(ticksStyle.equals(TicksStyle.TOP) && maxX<=0))
//				if(ticksDisplayed.equals(PlottingStyle.Y) || ticksDisplayed.equals(PlottingStyle.ALL)) {
//					SVGElement ticksY = new SVGGElement(document);
//
//					for(j=1; j<=endY; j++)
//						try{ ticksY.appendChild(new SVGLineElement(positionx, positiony-j*gapY*increment.getY(),
//											positionx-ticksSize-thickness/2., positiony-j*gapY*increment.getY(), document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					for(j=-1; j>=startY; j--)
//						try{ ticksY.appendChild(new SVGLineElement(positionx, positiony-j*gapY*increment.getY(),
//											positionx-ticksSize-thickness/2., positiony-j*gapY*increment.getY(), document)); }
//						catch(MalformedSVGDocument e) { /* */ }
//
//					elt.appendChild(ticksY);
//				}
//		}
//
//		if(labelsDisplayed.equals(PlottingStyle.ALL) || labelsDisplayed.equals(PlottingStyle.X))
//		{// We show the labels on the X-shape.
//			float height 	= fontMetrics.getAscent();
//			double gap 		= ((ticksDisplayed.equals(PlottingStyle.ALL) ||
//							  ticksDisplayed.equals(PlottingStyle.X))&&
//							((isXLabelSouth && (ticksStyle.equals(TicksStyle.BOTTOM) || ticksStyle.equals(TicksStyle.FULL))) ||
//							 (!isXLabelSouth && (ticksStyle.equals(TicksStyle.TOP) || ticksStyle.equals(TicksStyle.FULL))))?
//							  ticksSize:0)+thickness/2.+IAxes.GAP_LABEL;
//			double sep		= maxY<=0 || !isXLabelSouth? -gap-IAxes.GAP_LABEL : gap+height;
//			String str;
//
//			if(((isXLabelSouth && gridStarty>=0) || (!isXLabelSouth &&
//				gridEndy<=0)) && (gridStartx!=gridEndx || gridStartx!=0) && gridStarty<=0 && showOrigin) {
//				SVGElement text = new SVGTextElement(document);
//				text.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(font.getSize()));
//				text.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1");//$NON-NLS-1$
//				text.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_BLACK_NAME);
//				text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx-fontMetrics.stringWidth(String.valueOf((int)originx))/2.)));
//				text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+sep)));
//				text.setTextContent(String.valueOf((int)originx));
//				elt.appendChild(text);
//			}
//
//			SVGElement texts = new SVGGElement(document);
//			texts.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_BLACK_NAME);
//			texts.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1");//$NON-NLS-1$
//			texts.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(font.getSize()));
//			SVGElement text;
//
//			for(i=incrementx, j=1; j<=endX; i+=incrementx, j++) {
//				text = new SVGTextElement(document);
//				str = (((int)incrementx)==incrementx?String.valueOf((int)(i+originx)) : String.valueOf(i+originx));
//				text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx+j*gapX*incrementx-fontMetrics.stringWidth(str)/2.)));
//				text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+sep)));
//				text.setTextContent(str);
//				texts.appendChild(text);
//			}
//
//			for(i=-incrementx, j=-1; j>=startX; i-=incrementx, j--) {
//				text = new SVGTextElement(document);
//				str = (((int)incrementx)==incrementx?String.valueOf((int)(i+originx)) : String.valueOf(i+originx));
//				text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx+j*gapX*incrementx-fontMetrics.stringWidth(str)/2.)));
//				text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+sep)));
//				text.setTextContent(str);
//				texts.appendChild(text);
//			}
//
//			elt.appendChild(texts);
//		}
//
//		if(labelsDisplayed.equals(PlottingStyle.ALL) || labelsDisplayed.equals(PlottingStyle.Y))
//		{// We show the labels on the Y-shape.
//			float height = fontMetrics.getAscent();
//			String str;
//
//			if(maxX>0 && isYLabelWest)
//			{
//				double gap	= (ticksBot && (ticksDisplayed.equals(PlottingStyle.ALL) ||
//								ticksDisplayed.equals(PlottingStyle.Y))?ticksSize:0)+thickness/2.;
//
//				if(gridStartx==0 && (gridStarty!=gridEndy || gridStarty!=0) && showOrigin) {
//					SVGElement text = new SVGTextElement(document);
//					text.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(font.getSize()));
//					text.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1");//$NON-NLS-1$
//					text.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_BLACK_NAME);
//					text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx-gap-fontMetrics.stringWidth(
//										String.valueOf((int)originy))-IAxes.GAP_LABEL)));
//					text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+height/2.)));
//					text.setTextContent(String.valueOf((int)originy));
//					elt.appendChild(text);
//				}
//
//				SVGElement texts = new SVGGElement(document);
//				texts.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_BLACK_NAME);
//				texts.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1");//$NON-NLS-1$
//				texts.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(font.getSize()));
//				SVGElement text;
//
//				for(i=increment.getY(), j=1; j<=endY; i+=increment.getY(), j++) {
//					text = new SVGTextElement(document);
//					str = ((int)increment.getY())==increment.getY()?String.valueOf((int)(i+originy)):String.valueOf(i+originy);
//					text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx-gap-fontMetrics.stringWidth(str)-IAxes.GAP_LABEL)));
//					text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+height/2.-j*gapY*increment.getY())));
//					text.setTextContent(str);
//					texts.appendChild(text);
//				}
//
//				for(i=-increment.getY(), j=-1; j>=startY; i-=increment.getY(), j--) {
//					text = new SVGTextElement(document);
//					str = ((int)increment.getY())==increment.getY()?String.valueOf((int)(i+originy)):String.valueOf(i+originy);
//					text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx-gap-fontMetrics.stringWidth(str)-IAxes.GAP_LABEL)));
//					text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+height/2.-j*gapY*increment.getY())));
//					text.setTextContent(str);
//					texts.appendChild(text);
//				}
//
//				elt.appendChild(texts);
//			}
//			else
//			{
//				double gap	= ((!ticksBot || ticksStyle.equals(TicksStyle.FULL)) && (ticksDisplayed.equals(PlottingStyle.ALL) ||
//								ticksDisplayed.equals(PlottingStyle.Y))?ticksSize:0)+thickness/2.;
//
//				if((!isYLabelWest && gridEndx<=0) || (isYLabelWest && gridStartx>=0) && (gridStarty!=gridEndy  || gridStarty!=0) && showOrigin)
//				{
//					SVGElement text = new SVGTextElement(document);
//					text.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(font.getSize()));
//					text.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1");//$NON-NLS-1$
//					text.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_BLACK_NAME);
//					text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx+gap+IAxes.GAP_LABEL)));
//					text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+height/2.)));
//					text.setTextContent(String.valueOf((int)originy));
//					elt.appendChild(text);
//				}
//
//				SVGElement texts = new SVGGElement(document);
//				texts.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_BLACK_NAME);
//				texts.setAttribute(SVGAttributes.SVG_FONT_SIZE, String.valueOf(font.getSize()));
//				texts.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1");//$NON-NLS-1$
//				SVGElement text;
//
//				for(i=increment.getY(), j=1; j<=endY; i+=increment.getY(), j++) {
//					text = new SVGTextElement(document);
//					text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx+gap+IAxes.GAP_LABEL)));
//					text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+height/2.-j*gapY*increment.getY())));
//					text.setTextContent(((int)increment.getY())==increment.getY()?String.valueOf((int)(i+originy)):String.valueOf(i+originy));
//					texts.appendChild(text);
//				}
//
//				for(i=-increment.getY(), j=-1; j>=startY; i-=increment.getY(), j--) {
//					text = new SVGTextElement(document);
//					text.setAttribute(SVGAttributes.SVG_X, String.valueOf((int)(positionx+gap+IAxes.GAP_LABEL)));
//					text.setAttribute(SVGAttributes.SVG_Y, String.valueOf((int)(positiony+height/2.-j*gapY*increment.getY())));
//					text.setTextContent(((int)increment.getY())==increment.getY()?String.valueOf((int)(i+originy)):String.valueOf(i+originy));
//					texts.appendChild(text);
//				}
//
//				elt.appendChild(texts);
//			}
//		}
//
//		if(rotationAngle%(Math.PI*2)!=0)
//			setSVGRotationAttribute(elt);
	}
}
