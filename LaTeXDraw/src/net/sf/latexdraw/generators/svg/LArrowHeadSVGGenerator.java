package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGMarkerElement;
import net.sf.latexdraw.parsers.svg.SVGNodeList;
import net.sf.latexdraw.parsers.svg.SVGPathElement;


/**
 * Defines a SVG generator for an arrow head.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class LArrowHeadSVGGenerator
{
	/** The arrowhead generated or used to generate the SVG-arrow. */
	protected IArrow arrowHead;


	/**
	 * Creates an SVG-arrowhead generator.
	 * @param ah The arrowhead.
	 */
	public LArrowHeadSVGGenerator(final IArrow ah) {
		if(ah==null)
			throw new IllegalArgumentException();

		arrowHead = ah;
	}



	/**
	 * Creates an SVG-arrowhead generator using an SVGMarkerElement.
	 * @param elt The SVGMarkerElement uses to creates the arrowhead.
	 * @param owner The shape that has the arrowhead.
	 */
	public LArrowHeadSVGGenerator(final SVGMarkerElement elt, final IShape owner) {
		if(elt==null)
			throw new IllegalArgumentException();

		arrowHead = DrawingTK.getFactory().createArrow(owner);
		setArrow(elt, owner);
	}



	/**
	 * Initialises the arrowhead using an SVGMarkerElement.
	 * @param elt The SVGMarkerElement uses to initialise the arrowhead.
	 * @param owner The figure the has the arrowhead.
	 * @since 2.0.0
	 */
	public void setArrow(final SVGMarkerElement elt, final IShape owner) {
		if(elt==null)
			return ;

		SVGNodeList nl = elt.getChildren(SVGElements.SVG_PATH);

		if(nl.getLength()==0) {
			nl = elt.getChildren(SVGElements.SVG_CIRCLE);

			if(nl.getLength()>0)
			setArrow((SVGCircleElement)nl.item(0), elt, owner);
		}
		else
			setArrow((SVGPathElement)nl.item(0), elt, owner);
	}



	/**
	 * Initialises the arrowhead using a circle arrowhead.
	 * @param circle The circle element.
	 * @param elt The arrowhead element.
	 * @param owner The shape that has the arrowhead.
	 * @since 2.0.0
	 */
	public void setArrow(final SVGCircleElement circle, final SVGMarkerElement elt, final IShape owner)
	{
		if(circle==null || elt==null || owner==null)
			return ;

//		double radius = circle.getR();
//		String dotSizeNumStr = circle.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_DOT_SIZE_NUM);
//		double dotSizeDim;
//		double dotSizeNum;
//		double lineWidth = owner.hasDbleBord() ? owner.getDbleBordSep() + 2*owner.getThickness() : owner.getThickness();
//
//		if(dotSizeNumStr==null)
//			dotSizeNum = 1;
//		else
//			try { dotSizeNum = Double.valueOf(dotSizeNumStr); }
//			catch(NumberFormatException e) {
//				e.printStackTrace();
//				dotSizeNum = 1;
//			}
//
//		if(circle.getStroke()==null) {
//			arrowHead.setArrowStyle(elt.getRefX()==0 ? PSTricksConstants.DISKEND_STYLE : PSTricksConstants.DISKIN_STYLE);
//			dotSizeDim = radius*lineWidth*2.-dotSizeNum*lineWidth;
//		}
//		else {
//			arrowHead.setArrowStyle(elt.getRefX()==0 ? PSTricksConstants.CIRCLEEND_STYLE : PSTricksConstants.CIRCLEIN_STYLE);
//			dotSizeDim = (radius*lineWidth+lineWidth/2.)*2.-dotSizeNum*lineWidth;
//		}

//		if(dotSizeDim==0)
//			arrowHead.setArrowStyle(PSTricksConstants.ROUNDIN_STYLE);
//		else {
//			Color filling = CSSColors.getRGBColour(circle.getFill());
//
//			if(filling!=null)
//				arrowHead.getLine().setInteriorColor(filling);
//
//			arrowHead.setDotSizeDim(dotSizeDim);
//			arrowHead.setDotSizeNum(dotSizeNum);
//		}
	}



	/**
	 * Initialises the arrowhead using a path arrowhead.
	 * @param path The path element.
	 * @param elt The arrowhead element.
	 * @param owner The shape that has the arrowhead.
	 * @since 2.0.0
	 */
	public void setArrow(final SVGPathElement path, final SVGMarkerElement elt, final IShape owner)
	{
		if(path==null || path.getSegList().size()<2 || elt==null || owner==null)
			return;

//		try
//		{
//			SVGPathSegList list = path.getSegList();
//			SVGPathSegMoveto m = (SVGPathSegMoveto)list.get(0);
//			SVGPathSeg seg = list.get(1);
//			ILine line = arrowHead.getLine();
//			IPoint p1  = line.getPoint1();
//			IPoint pos = arrowHead.getPosition();
//			boolean isInverted   = pos==p1 ? false : true;
//			double lineWidth  = owner.hasDbleBord() ? owner.getDbleBordSep() + 2*owner.getThickness() : owner.getThickness();
//
//			if(list.size()==2 || list.size()==4)// It may be a bar or a bracket
//			{
//				String tbarNumStr = path.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM);
//				double tbarNum;
//				double y = Math.abs(m.getY());
//
//				if(tbarNumStr==null)
//					tbarNum = 1;
//				else
//					try { tbarNum = Double.valueOf(tbarNumStr); }
//					catch(NumberFormatException e) {
//						e.printStackTrace();
//						tbarNum = 1;
//					}
//
//					arrowHead.setTBarSizeNum(tbarNum);
//					arrowHead.setTBarSizeDim(y*lineWidth*2 - tbarNum*lineWidth);
//
//				if((seg instanceof SVGPathSegLineto && ((float)((SVGPathSegLineto)seg).getX())==((float)m.getX())) || (seg instanceof SVGPathSegLinetoVertical))
//					arrowHead.setArrowStyle(((float)m.getX())!=0f ? PSTricksConstants.BARIN_STYLE : PSTricksConstants.BAREND_STYLE);
//				else
//				if(seg instanceof SVGPathSegCurvetoCubic) {
//					double width  = (arrowHead.getTBarSizeDim() + arrowHead.getTBarSizeNum()*lineWidth)/lineWidth;
//					double rBrack = (Math.abs(m.getX())-0.5)/width;
//
//					arrowHead.setArrowStyle(Math.abs(m.getX())==0.5 ? isInverted ? PSTricksConstants.LRBRACKET_STYLE : PSTricksConstants.RRBRACKET_STYLE :
//											isInverted ? PSTricksConstants.RRBRACKET_STYLE : PSTricksConstants.LRBRACKET_STYLE);
//					arrowHead.setRBracketNum(rBrack);
//				}
//				else
//				if(list.size()==4 && seg instanceof SVGPathSegLineto && list.get(2) instanceof SVGPathSegLineto &&
//					list.get(3) instanceof SVGPathSegLineto)// It may be a bracket.
//					{
//						double lgth = Math.abs(m.getX()-((SVGPathSegLineto)seg).getX());
//					String style = elt.getRefX()>0?
//									PSTricksConstants.RSBRACKET_STYLE : PSTricksConstants.LSBRACKET_STYLE;
//
//					y = y + (m.getY()>0 ? -0.5 : 0.5);
//					arrowHead.setTBarSizeDim(y*lineWidth*2 - tbarNum*lineWidth);
//					arrowHead.setBracketNum(((lgth-0.5)*lineWidth)/(arrowHead.getTBarSizeDim()/IShape.PPC +
//											arrowHead.getTBarSizeNum()*lineWidth));
//
//					arrowHead.setArrowStyle(style);
//					}
//				}
//			else if(list.size()==5 || list.size()==10)// It may be an arrow or a double arrow
//			{
//				if(!(seg instanceof SVGPathSegLineto && list.get(2) instanceof SVGPathSegLineto &&
//					list.get(3) instanceof SVGPathSegLineto && list.get(4) instanceof SVGPathSegClosePath))
//					return ;
//
//				String arrNumStr = path.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_SIZE_NUM);
//				double arrNum, arrDim;
//				double lgth = Math.abs(((SVGPathSegLineto)seg).getX() - m.getX());
//
//				if(arrNumStr==null)
//					arrNum = 1;
//				else
//					try { arrNum = Double.valueOf(arrNumStr); }
//					catch(NumberFormatException e) {
//						e.printStackTrace();
//						arrNum = 1;
//					}
//
//				if(isInverted)
//					if(list.size()==10)
//						arrowHead.setArrowStyle(m.getX()!=0. || m.getY()!=0. ? PSTricksConstants.DLARROW_STYLE : PSTricksConstants.DRARROW_STYLE);
//					else
//						arrowHead.setArrowStyle(m.getX()!=0. || m.getY()!=0. ? PSTricksConstants.LARROW_STYLE : PSTricksConstants.RARROW_STYLE);
//				else
//					if(list.size()==10)
//						arrowHead.setArrowStyle(m.getX()!=0. || m.getY()!=0. ? PSTricksConstants.DRARROW_STYLE : PSTricksConstants.DLARROW_STYLE);
//					else
//						arrowHead.setArrowStyle(m.getX()!=0. || m.getY()!=0. ? PSTricksConstants.RARROW_STYLE : PSTricksConstants.LARROW_STYLE);
//
//				arrDim = lineWidth*(((SVGPathSegLineto)seg).getY()*2 - arrNum);
//				arrowHead.setArrowLength(lgth/((arrNum*lineWidth + arrDim)/lineWidth));
//				arrowHead.setArrowSizeDim(arrDim);
//				arrowHead.setArrowSizeNum(arrNum);
//				arrowHead.setArrowInset((Math.abs(((SVGPathSegLineto)seg).getX()-((SVGPathSegLineto)list.get(2)).getX()))/lgth);
//			}
//		}
//		catch(ClassCastException e) { e.printStackTrace(); }
	}




	/**
	 * Return the SVG tree of the arrowhead or null if this arrowhead has no style.
	 * @param doc The document used to create elements.
	 * @return The SVG tree of the arrowhead or null if doc is null.
	 * @since 2.0.0
	 */
	public SVGElement toSVG(final SVGDocument doc, final boolean isShadow)
	{
		if(doc==null)
			return null;

		if(arrowHead.isWithoutStyle())
			return null;

//		IShape figure 	  = arrowHead.getShape();
//		String arrowStyle = arrowHead.getArrowStyle().getPSTToken();
		SVGElement marker = new SVGMarkerElement(doc);
//		double lineWidth  = figure.hasDbleBord() ? figure.getDbleBordSep() + 2*figure.getThickness() : figure.getThickness();
//		double gapPostion = 0.;
//
//		if(arrowStyle.equals(PSTricksConstants.CIRCLEEND_STYLE) || arrowStyle.equals(PSTricksConstants.CIRCLEIN_STYLE)) {
//			SVGElement circle = new SVGCircleElement(doc);
//			double r = (arrowHead.getDotSizeDim()+arrowHead.getDotSizeNum()*lineWidth)/2.-lineWidth/2.;
//
//			circle.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_DOT_SIZE_NUM, String.valueOf(arrowHead.getDotSizeNum()));
//			circle.setAttribute(SVGAttributes.SVG_R, String.valueOf(r/lineWidth));
//			circle.setAttribute(SVGAttributes.SVG_FILL,
//					CSSColors.getColorName(figure.getFillingCol(), true));
//			circle.setAttribute(SVGAttributes.SVG_STROKE,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			circle.setStrokeWidth(1);
//			marker.appendChild(circle);
//
//			if(arrowStyle.equals(PSTricksConstants.CIRCLEIN_STYLE))
//				gapPostion = lineWidth *  (arrowHead.isLeftArrow() ? -1 : 1);
//		}
//		else
//		if(arrowStyle.equals(PSTricksConstants.DISKEND_STYLE) || arrowStyle.equals(PSTricksConstants.DISKIN_STYLE)) {
//			SVGElement circle = new SVGCircleElement(doc);
//			double r = (arrowHead.getDotSizeDim()+arrowHead.getDotSizeNum()*lineWidth)/2.;
//
//			circle.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_DOT_SIZE_NUM, String.valueOf(arrowHead.getDotSizeNum()));
//			circle.setAttribute(SVGAttributes.SVG_R, String.valueOf(r/lineWidth));
//			circle.setAttribute(SVGAttributes.SVG_FILL,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			marker.appendChild(circle);
//
//			if(arrowStyle.equals(PSTricksConstants.DISKIN_STYLE))
//				gapPostion = lineWidth *  (arrowHead.isLeftArrow() ? -1 : 1);
//		}
//		else
//		if(arrowStyle.equals(PSTricksConstants.BARIN_STYLE) || arrowStyle.equals(PSTricksConstants.BAREND_STYLE)) {
//			SVGPathElement bar = new SVGPathElement(doc);
//			double width = arrowHead.getTBarSizeDim() + arrowHead.getTBarSizeNum()*lineWidth;
//			SVGPathSegList path = new SVGPathSegList();
//			double x = arrowStyle.equals(PSTricksConstants.BARIN_STYLE) ? arrowHead.isLeftArrow() ? 0.5 : -0.5 : 0;
//
//			bar.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM, String.valueOf(arrowHead.getTBarSizeNum()));
//			path.add(new SVGPathSegMoveto(x, -width/(lineWidth*2), false));
//			path.add(new SVGPathSegLineto(x, width/(lineWidth*2), false));
//			bar.setPathData(path);
//			bar.setAttribute(SVGAttributes.SVG_STROKE,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			bar.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
//			bar.setPathData(path);
//			bar.setStrokeWidth(1);
//			marker.appendChild(bar);
//		}
//		else
//		if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE) || arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE)) {
//			SVGPathElement bar = new SVGPathElement(doc);
//			double width = arrowHead.getTBarSizeDim() + arrowHead.getTBarSizeNum()*lineWidth;
//			SVGPathSegList path = new SVGPathSegList();
//			double lgth = (arrowHead.getBracketNum()*(arrowHead.getTBarSizeDim()/IShape.PPC + arrowHead.getTBarSizeNum()*lineWidth))/lineWidth;
//			boolean isInverted  = arrowHead.isInverted();//FIXME Figure.PPC
//			double lgth2;
//
//			bar.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM, String.valueOf(arrowHead.getTBarSizeNum()));
//
//			if((arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))) {
//				lgth2 = isInverted ? -lgth : 0;
//
//				path.add(new SVGPathSegMoveto(lgth+lgth2+0.5, -width/(lineWidth*2)+0.5, false));
//				path.add(new SVGPathSegLineto(lgth2, -width/(lineWidth*2)+0.5, false));
//				path.add(new SVGPathSegLineto(lgth2, width/(lineWidth*2)-0.5, false));
//				path.add(new SVGPathSegLineto(lgth+lgth2+0.5, width/(lineWidth*2)-0.5, false));
//				gapPostion = isInverted ? -lineWidth/4. : -lineWidth/2.;
//			}
//			else {
//				lgth2 = isInverted ? lgth : 0;
//
//				path.add(new SVGPathSegMoveto(-lgth+lgth2-0.5, -width/(lineWidth*2)+0.5, false));
//				path.add(new SVGPathSegLineto(lgth2, -width/(lineWidth*2)+0.5, false));
//				path.add(new SVGPathSegLineto(lgth2, width/(lineWidth*2)-0.5, false));
//				path.add(new SVGPathSegLineto(-lgth+lgth2-0.5, width/(lineWidth*2)-0.5, false));
//				gapPostion = isInverted ? lineWidth/4. : lineWidth/2.;
//			}
//
//			marker.appendChild(bar);
//			bar.setAttribute(SVGAttributes.SVG_STROKE,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			bar.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
//			bar.setPathData(path);
//			bar.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1"); //$NON-NLS-1$
//		}
//		else
//		if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE) || arrowStyle.equals(PSTricksConstants.LARROW_STYLE)) {
//			SVGElement arrow = new SVGPathElement(doc);
//			double width  = (arrowHead.getArrowSizeNum()*lineWidth + arrowHead.getArrowSizeDim())/lineWidth;
//			double length = arrowHead.getArrowLength()*width, inset = arrowHead.getArrowInset()*length;
//			SVGPathSegList path = new SVGPathSegList();
//			boolean isInverted  = arrowHead.isInverted();
//			double lgth2;
//
//			if((arrowStyle.equals(PSTricksConstants.LARROW_STYLE))) {
//				lgth2 = isInverted ? -length : 0;
//
//				path.add(new SVGPathSegMoveto(lgth2, 0., false));
//				path.add(new SVGPathSegLineto(length+lgth2, width/2., false));
//				path.add(new SVGPathSegLineto(length-inset+lgth2, 0.,  false));
//				path.add(new SVGPathSegLineto(length+lgth2, -width/2., false));
//				path.add(new SVGPathSegClosePath());
//			}
//			else {
//				lgth2 = isInverted ? length : 0;
//
//				path.add(new SVGPathSegMoveto(lgth2, 0., false));
//				path.add(new SVGPathSegLineto(-length+lgth2, width/2., false));
//				path.add(new SVGPathSegLineto(-length+inset+lgth2, 0.,  false));
//				path.add(new SVGPathSegLineto(-length+lgth2, -width/2., false));
//				path.add(new SVGPathSegClosePath());
//			}
//
//			arrow.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_SIZE_NUM, String.valueOf(arrowHead.getArrowSizeNum()));
//			marker.appendChild(arrow);
//			arrow.setAttribute(SVGAttributes.SVG_FILL,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			arrow.setAttribute(SVGAttributes.SVG_D, path.toString());
//		}
//		else
//		if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE) || arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE)) {
//			SVGPathElement rbracket = new SVGPathElement(doc);
//			double width = (arrowHead.getTBarSizeDim() + arrowHead.getTBarSizeNum()*lineWidth)/lineWidth;
//			double lgth  = arrowHead.getRBracketNum()*width;
//			double lgth2;
//			SVGPathSegList path = new SVGPathSegList();
//			boolean isInverted  = arrowHead.isInverted();
//
//			if((arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))) {
//				lgth2 = isInverted ? -lgth : 0;
//
//				path.add(new SVGPathSegMoveto(lgth+lgth2+0.5, width/2., false));
//				path.add(new SVGPathSegCurvetoCubic(lgth+lgth2+0.5, -width/2., 0, width/2., 0, -width/2., false));
//			}
//			else {
//				lgth2 = isInverted ? lgth : 0;
//
//				path.add(new SVGPathSegMoveto(-lgth+lgth2-0.5, width/2., false));
//				path.add(new SVGPathSegCurvetoCubic(-lgth+lgth2-0.5, -width/2., 0, width/2., 0, -width/2., false));
//			}
//
//			marker.appendChild(rbracket);
//			rbracket.setAttribute(SVGAttributes.SVG_STROKE,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			rbracket.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
//			rbracket.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM,
//									String.valueOf(arrowHead.getTBarSizeNum()));
//			rbracket.setPathData(path);
//			rbracket.setStrokeWidth(1);
//		}
//		else
//		if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE) || arrowStyle.equals(PSTricksConstants.DRARROW_STYLE)) {
//			SVGElement arrow = new SVGPathElement(doc);
//			double width  = (arrowHead.getArrowSizeNum()*lineWidth + arrowHead.getArrowSizeDim())/lineWidth;
//			double length = arrowHead.getArrowLength()*width, inset = arrowHead.getArrowInset()*length;
//			SVGPathSegList path = new SVGPathSegList();
//			double lgth2;
//			boolean isInverted  = arrowHead.isInverted();
//
//			if((arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))) {
//				lgth2 = isInverted ? -length*2 : 0;
//
//				path.add(new SVGPathSegMoveto(lgth2, 0, false));
//				path.add(new SVGPathSegLineto(length+lgth2, width/2., false));
//				path.add(new SVGPathSegLineto(length-inset+lgth2, 0, false));
//				path.add(new SVGPathSegLineto(length+lgth2, -width/2., false));
//				path.add(new SVGPathSegClosePath());
//				path.add(new SVGPathSegMoveto(length+lgth2, 0, false));
//				path.add(new SVGPathSegLineto(length*2+lgth2, width/2., false));
//				path.add(new SVGPathSegLineto(length*2-inset+lgth2, 0, false));
//				path.add(new SVGPathSegLineto(length*2+lgth2, -width/2., false));
//				path.add(new SVGPathSegClosePath());
//			}
//			else {
//				lgth2 = isInverted ? length*2 : 0;
//
//				path.add(new SVGPathSegMoveto(lgth2, 0, false));
//				path.add(new SVGPathSegLineto(-length+lgth2, width/2., false));
//				path.add(new SVGPathSegLineto(-length+inset+lgth2, 0, false));
//				path.add(new SVGPathSegLineto(-length+lgth2, -width/2., false));
//				path.add(new SVGPathSegClosePath());
//				path.add(new SVGPathSegMoveto(-length+lgth2, 0, false));
//				path.add(new SVGPathSegLineto(-length*2+lgth2, width/2., false));
//				path.add(new SVGPathSegLineto(-length*2+inset+lgth2, 0, false));
//				path.add(new SVGPathSegLineto(-length*2+lgth2, -width/2., false));
//				path.add(new SVGPathSegClosePath());
//			}
//
//			marker.appendChild(arrow);
//			arrow.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_SIZE_NUM,
//					String.valueOf(arrowHead.getArrowSizeNum()));
//			arrow.setAttribute(SVGAttributes.SVG_FILL,
//					CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//			arrow.setAttribute(SVGAttributes.SVG_D, path.toString());
//		}
//		else
//			if(arrowStyle.equals(PSTricksConstants.ROUNDIN_STYLE)) {
//				SVGElement circle = new SVGCircleElement(doc);
//
//				circle.setAttribute(SVGAttributes.SVG_R, "0.5");
//				circle.setAttribute(SVGAttributes.SVG_FILL,
//						CSSColors.getColorName(isShadow ? figure.getShadowCol() : figure.getLineColour(), true));
//				marker.appendChild(circle);
//			}
//
//		if(gapPostion!=0f)
//			marker.setAttribute(SVGAttributes.SVG_REF_X, String.valueOf(gapPostion/lineWidth));
//
//		marker.setAttribute(SVGAttributes.SVG_OVERFLOW, SVGAttributes.SVG_VALUE_VISIBLE);
//		marker.setAttribute(SVGAttributes.SVG_ORIENT, SVGAttributes.SVG_VALUE_AUTO);

		return marker;
	}
}
