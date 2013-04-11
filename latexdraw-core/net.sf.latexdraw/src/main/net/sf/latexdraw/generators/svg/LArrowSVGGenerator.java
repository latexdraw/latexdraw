package net.sf.latexdraw.generators.svg;

import java.util.Objects;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGMarkerElement;
import net.sf.latexdraw.parsers.svg.SVGNodeList;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLinetoVertical;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines an SVG generator for arrows.<br>
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
class LArrowSVGGenerator {
	/** The arrowhead generated or used to generate the SVG-arrow. */
	protected IArrow arrow;


	/**
	 * Creates an SVG arrow generator.
	 * @param arr The arrow. Must not be null.
	 */
	protected LArrowSVGGenerator(final IArrow arr) {
		super();
		arrow = Objects.requireNonNull(arr);
	}



	/**
	 * Initialises the arrow using an SVGMarkerElement.
	 * @param elt The SVGMarkerElement uses to initialise the arrow.
	 * @param owner The figure the has the arrow.
	 * @since 2.0.0
	 */
	protected void setArrow(final SVGMarkerElement elt, final IShape owner, final String svgMarker) {
		SVGNodeList nl = elt.getChildren(SVGElements.SVG_PATH);

		if(nl.getLength()==0) {
			nl = elt.getChildren(SVGElements.SVG_CIRCLE);

			if(nl.getLength()>0)
				setArrow((SVGCircleElement)nl.item(0), elt, owner);
		}
		else
			setArrow((SVGPathElement)nl.item(0), elt, owner, svgMarker);
	}



	/**
	 * Initialises the arrowhead using a circle arrow.
	 * @param circle The circle element.
	 * @param elt The arrowhead element.
	 * @param owner The shape that has the arrow.
	 * @since 2.0.0
	 */
	protected void setArrow(final SVGCircleElement circle, final SVGMarkerElement elt, final IShape owner) {
		double radius = circle.getR();
		String dotSizeNumStr = circle.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_DOT_SIZE_NUM);
		double dotSizeDim;
		double dotSizeNum;
		double lineWidth = owner.hasDbleBord() ? owner.getDbleBordSep() + 2.*owner.getThickness() : owner.getThickness();

		if(dotSizeNumStr==null)
			dotSizeNum = 1.;
		else
			try { dotSizeNum = Double.parseDouble(dotSizeNumStr); }
			catch(NumberFormatException e) {
				BadaboomCollector.INSTANCE.add(e);
				dotSizeNum = 1.;
			}

		if(circle.getStroke()==null) {
			arrow.setArrowStyle(LNumber.INSTANCE.equals(elt.getRefX(), 0.) ? ArrowStyle.DISK_END : ArrowStyle.DISK_IN);
			dotSizeDim = radius*lineWidth*2.-dotSizeNum*lineWidth;
		}
		else {
			arrow.setArrowStyle(LNumber.INSTANCE.equals(elt.getRefX(), 0.) ? ArrowStyle.CIRCLE_END : ArrowStyle.CIRCLE_IN);
			dotSizeDim = (radius*lineWidth+lineWidth/2.)*2.-dotSizeNum*lineWidth;
		}

		if(dotSizeDim==0)
			arrow.setArrowStyle(ArrowStyle.ROUND_IN);
		else {
			arrow.setDotSizeDim(dotSizeDim);
			arrow.setDotSizeNum(dotSizeNum);
		}
	}



	private void setArrowBarBracket(final SVGPathElement path, final SVGPathSegMoveto m, final double lineWidth, final SVGPathSeg seg,
									final SVGMarkerElement elt, final SVGPathSegList list, final String svgMarker) {
		String tbarNumStr = path.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM);
		double tbarNum;
		double y = Math.abs(m.getY());
		final boolean isStartArrow = SVGAttributes.SVG_MARKER_START.equals(svgMarker);

		if(tbarNumStr==null)
			tbarNum = 1.;
		else
			try { tbarNum = Double.parseDouble(tbarNumStr); }
			catch(NumberFormatException e) {
				BadaboomCollector.INSTANCE.add(e);
				tbarNum = 1.;
			}

		arrow.setTBarSizeNum(tbarNum);
		arrow.setTBarSizeDim(y*lineWidth*2. - tbarNum*lineWidth);

		if((seg instanceof SVGPathSegLineto && LNumber.INSTANCE.equals(((SVGPathSegLineto)seg).getX(), m.getX())) || (seg instanceof SVGPathSegLinetoVertical))
			arrow.setArrowStyle(LNumber.INSTANCE.equals(m.getX(),0.) ? ArrowStyle.BAR_IN : ArrowStyle.BAR_END);
		else if(seg instanceof SVGPathSegCurvetoCubic) {
			double width  = (arrow.getTBarSizeDim() + arrow.getTBarSizeNum()*lineWidth)/lineWidth;
			double rBrack = (Math.abs(m.getX())-0.5)/width;

			arrow.setArrowStyle(LNumber.INSTANCE.equals(Math.abs(m.getX()), 0.5) ? ArrowStyle.RIGHT_ROUND_BRACKET : ArrowStyle.LEFT_ROUND_BRACKET);
			if(!isStartArrow)
				arrow.setArrowStyle(arrow.getArrowStyle().getOppositeArrowStyle());
			arrow.setRBracketNum(rBrack);
		}
		else // It may be a bracket.
		if(list.size()==4 && seg instanceof SVGPathSegLineto && list.get(2) instanceof SVGPathSegLineto && list.get(3) instanceof SVGPathSegLineto) {
			double lgth = Math.abs(m.getX()-((SVGPathSegLineto)seg).getX());

			y = y + (m.getY()>0. ? -0.5 : 0.5);
			arrow.setTBarSizeDim(y*lineWidth*2. - tbarNum*lineWidth);
			arrow.setBracketNum(((lgth-0.5)*lineWidth)/(arrow.getTBarSizeDim()/IShape.PPC + arrow.getTBarSizeNum()*lineWidth));
			arrow.setArrowStyle(elt.getRefX()>0.? ArrowStyle.RIGHT_SQUARE_BRACKET : ArrowStyle.LEFT_SQUARE_BRACKET);
		}
	}


	private void setArrowArrow(final SVGPathElement path, final SVGPathSegMoveto m, final double lineWidth, final SVGPathSeg seg,
							   final SVGPathSegList list, final String svgMarker) {
		if(!(seg instanceof SVGPathSegLineto && list.get(2) instanceof SVGPathSegLineto &&
				list.get(3) instanceof SVGPathSegLineto && list.get(4) instanceof SVGPathSegClosePath))
				return ;

			final String arrNumStr = path.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_SIZE_NUM);
			double arrNum, arrDim;
			final double lgth = Math.abs(((SVGPathSegLineto)seg).getX() - m.getX());
			final boolean moveIs0 = LNumber.INSTANCE.equals(m.getX(),0.) && LNumber.INSTANCE.equals(m.getY(),0.);
			final boolean isStartArrow = SVGAttributes.SVG_MARKER_START.equals(svgMarker);

			if(arrNumStr==null)
				arrNum = 1.;
			else
				try { arrNum = Double.parseDouble(arrNumStr); }
				catch(NumberFormatException e) {
					BadaboomCollector.INSTANCE.add(e);
					arrNum = 1.;
				}

			if(list.size()==10)
				arrow.setArrowStyle(moveIs0 ? ArrowStyle.LEFT_DBLE_ARROW : ArrowStyle.RIGHT_DBLE_ARROW);
			else
				arrow.setArrowStyle(moveIs0 ? ArrowStyle.LEFT_ARROW : ArrowStyle.RIGHT_ARROW);

			if(!isStartArrow)
				arrow.setArrowStyle(arrow.getArrowStyle().getOppositeArrowStyle());

			arrDim = lineWidth*(((SVGPathSegLineto)seg).getY()*2. - arrNum);
			arrow.setArrowLength(lgth/((arrNum*lineWidth + arrDim)/lineWidth));
			arrow.setArrowSizeDim(arrDim);
			arrow.setArrowSizeNum(arrNum);
			arrow.setArrowInset((Math.abs(((SVGPathSegLineto)seg).getX()-((SVGPathSegLineto)list.get(2)).getX()))/lgth);
	}


	/**
	 * Initialises the arrowhead using a path arrow.
	 * @param path The path element.
	 * @param elt The arrowhead element.
	 * @param owner The shape that has the arrow.
	 * @since 2.0.0
	 */
	protected void setArrow(final SVGPathElement path, final SVGMarkerElement elt, final IShape owner, final String svgMarker) {
		final SVGPathSegList list	= path.getSegList();
		final SVGPathSegMoveto m 	= (SVGPathSegMoveto)list.get(0);
		final double lineWidth  	= owner.hasDbleBord() ? owner.getDbleBordSep() + 2.*owner.getThickness() : owner.getThickness();

		if(list.size()==2 || list.size()==4) // It may be a bar or a bracket
			setArrowBarBracket(path, m, lineWidth, list.get(1), elt, list, svgMarker);
		else if(list.size()==5 || list.size()==10)// It may be an arrow or a double arrow
			setArrowArrow(path, m, lineWidth, list.get(1), list, svgMarker);
	}



	private double toSVGCircle(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape 		= arrow.getShape();
		final SVGElement circle = new SVGCircleElement(doc);
		final double r 			= (arrow.getDotSizeDim()+arrow.getDotSizeNum()*lineWidth)/2.-lineWidth/2.;

		circle.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_DOT_SIZE_NUM, String.valueOf(arrow.getDotSizeNum()));
		circle.setAttribute(SVGAttributes.SVG_R, String.valueOf(r/lineWidth));
		circle.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(shape.getFillingCol(), true));
		circle.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		circle.setStrokeWidth(1);
		marker.appendChild(circle);

		return arrow.getArrowStyle()==ArrowStyle.CIRCLE_IN ? lineWidth*(arrow.isLeftArrow() ? -1. : 1.) : 0.;
	}



	private double toSVGDisk(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape 		= arrow.getShape();
		final SVGElement circle = new SVGCircleElement(doc);
		final double r 			= (arrow.getDotSizeDim()+arrow.getDotSizeNum()*lineWidth)/2.;

		circle.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_DOT_SIZE_NUM, String.valueOf(arrow.getDotSizeNum()));
		circle.setAttribute(SVGAttributes.SVG_R, String.valueOf(r/lineWidth));
		circle.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		marker.appendChild(circle);

		return arrow.getArrowStyle()==ArrowStyle.DISK_IN ? lineWidth *  (arrow.isLeftArrow() ? -1. : 1.) : 0.;
	}



	private void toSVGBar(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape			= arrow.getShape();
		final SVGPathElement bar	= new SVGPathElement(doc);
		final double width 			= arrow.getTBarSizeDim() + arrow.getTBarSizeNum()*lineWidth;
		final SVGPathSegList path 	= new SVGPathSegList();
		final double x 				= arrow.getArrowStyle()==ArrowStyle.BAR_IN ? arrow.isLeftArrow() ? 0.5 : -0.5 : 0.;

		bar.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM, String.valueOf(arrow.getTBarSizeNum()));
		path.add(new SVGPathSegMoveto(x, -width/(lineWidth*2.), false));
		path.add(new SVGPathSegLineto(x, width/(lineWidth*2.), false));
		bar.setPathData(path);
		bar.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		bar.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
		bar.setPathData(path);
		bar.setStrokeWidth(1.);
		marker.appendChild(bar);
	}



	private double toSVGSquareBracket(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape			= arrow.getShape();
		final SVGPathElement bar 	= new SVGPathElement(doc);
		final double width 			= arrow.getTBarSizeDim() + arrow.getTBarSizeNum()*lineWidth;
		final SVGPathSegList path 	= new SVGPathSegList();
		final double lgth 			= (arrow.getBracketNum()*(arrow.getTBarSizeDim()/IShape.PPC + arrow.getTBarSizeNum()*lineWidth))/lineWidth;
		final boolean isInverted  	= arrow.isInverted();//FIXME shape.PPC
		double gapPostion;

		if(arrow.getArrowStyle()==ArrowStyle.LEFT_SQUARE_BRACKET) {
			double lgth2 = isInverted ? -lgth : 0.;
			path.add(new SVGPathSegMoveto(lgth+lgth2+0.5, -width/(lineWidth*2)+0.5, false));
			path.add(new SVGPathSegLineto(lgth2, -width/(lineWidth*2)+0.5, false));
			path.add(new SVGPathSegLineto(lgth2, width/(lineWidth*2)-0.5, false));
			path.add(new SVGPathSegLineto(lgth+lgth2+0.5, width/(lineWidth*2)-0.5, false));
			gapPostion = isInverted ? -lineWidth/4. : -lineWidth/2.;
		}
		else {
			double lgth2 = isInverted ? lgth : 0.;
			path.add(new SVGPathSegMoveto(-lgth+lgth2-0.5, -width/(lineWidth*2)+0.5, false));
			path.add(new SVGPathSegLineto(lgth2, -width/(lineWidth*2)+0.5, false));
			path.add(new SVGPathSegLineto(lgth2, width/(lineWidth*2)-0.5, false));
			path.add(new SVGPathSegLineto(-lgth+lgth2-0.5, width/(lineWidth*2)-0.5, false));
			gapPostion = isInverted ? lineWidth/4. : lineWidth/2.;
		}

		marker.appendChild(bar);
		bar.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM, String.valueOf(arrow.getTBarSizeNum()));
		bar.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		bar.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
		bar.setPathData(path);
		bar.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, "1"); //$NON-NLS-1$

		return gapPostion;
	}



	private void toSVGArrow(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape			= arrow.getShape();
		final SVGElement arrowSVG 	= new SVGPathElement(doc);
		final double width  		= (arrow.getArrowSizeNum()*lineWidth + arrow.getArrowSizeDim())/lineWidth;
		double length 				= arrow.getArrowLength()*width, inset = arrow.getArrowInset()*length;
		final SVGPathSegList path 	= new SVGPathSegList();

		if(arrow.getArrowStyle()==ArrowStyle.LEFT_ARROW) {
			length *= -1.;
			inset *= -1.;
		}

		double lgth2 = arrow.isInverted() ? length : 0.;
		path.add(new SVGPathSegMoveto(lgth2, 0., false));
		path.add(new SVGPathSegLineto(-length+lgth2, width/2., false));
		path.add(new SVGPathSegLineto(-length+inset+lgth2, 0.,  false));
		path.add(new SVGPathSegLineto(-length+lgth2, -width/2., false));
		path.add(new SVGPathSegClosePath());

		marker.appendChild(arrowSVG);
		arrowSVG.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_SIZE_NUM, String.valueOf(arrow.getArrowSizeNum()));
		arrowSVG.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		arrowSVG.setAttribute(SVGAttributes.SVG_D, path.toString());
	}



	private void toSVGRoundBracket(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape				= arrow.getShape();
		final SVGPathElement rbracket 	= new SVGPathElement(doc);
		final double width 				= (arrow.getTBarSizeDim() + arrow.getTBarSizeNum()*lineWidth)/lineWidth;
		double lgth  					= arrow.getRBracketNum()*width;
		final SVGPathSegList path 		= new SVGPathSegList();
		double gap						= 0.5;

		if(arrow.getArrowStyle()==ArrowStyle.LEFT_ROUND_BRACKET) {
			lgth *= -1.;
			gap *= -1.;
		}

		double lgth2 = arrow.isInverted() ? lgth : 0.;
		path.add(new SVGPathSegMoveto(-lgth+lgth2-gap, width/2., false));
		path.add(new SVGPathSegCurvetoCubic(-lgth+lgth2-gap, -width/2., 0., width/2., 0., -width/2., false));

		marker.appendChild(rbracket);
		rbracket.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		rbracket.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
		rbracket.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_TBAR_SIZE_NUM, String.valueOf(arrow.getTBarSizeNum()));
		rbracket.setPathData(path);
		rbracket.setStrokeWidth(1);
	}


	private void toSVGDoubleArrow(final SVGDocument doc, final double lineWidth, final boolean isShadow, final SVGElement marker) {
		final IShape shape			= arrow.getShape();
		final SVGElement arrowSVG 	= new SVGPathElement(doc);
		final double width  		= (arrow.getArrowSizeNum()*lineWidth + arrow.getArrowSizeDim())/lineWidth;
		double length 				= arrow.getArrowLength()*width, inset = arrow.getArrowInset()*length;
		final SVGPathSegList path 	= new SVGPathSegList();

		if(arrow.getArrowStyle()==ArrowStyle.LEFT_DBLE_ARROW) {
			inset *= -1.;
			length *= -1.;
		}

		double lgth2 = arrow.isInverted() ? length*2 : 0.;
		path.add(new SVGPathSegMoveto(lgth2, 0., false));
		path.add(new SVGPathSegLineto(-length+lgth2, width/2., false));
		path.add(new SVGPathSegLineto(-length+inset+lgth2, 0., false));
		path.add(new SVGPathSegLineto(-length+lgth2, -width/2., false));
		path.add(new SVGPathSegClosePath());
		path.add(new SVGPathSegMoveto(-length+lgth2, 0., false));
		path.add(new SVGPathSegLineto(-length*2+lgth2, width/2., false));
		path.add(new SVGPathSegLineto(-length*2+inset+lgth2, 0., false));
		path.add(new SVGPathSegLineto(-length*2+lgth2, -width/2., false));
		path.add(new SVGPathSegClosePath());

		marker.appendChild(arrowSVG);
		arrowSVG.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ARROW_SIZE_NUM, String.valueOf(arrow.getArrowSizeNum()));
		arrowSVG.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		arrowSVG.setAttribute(SVGAttributes.SVG_D, path.toString());
	}


	private void toSVGRoundIn(final SVGDocument doc, final boolean isShadow, final SVGElement marker) {
		final IShape shape 		= arrow.getShape();
		final SVGElement circle = new SVGCircleElement(doc);
		circle.setAttribute(SVGAttributes.SVG_R, "0.5");
		circle.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(isShadow ? shape.getShadowCol() : shape.getLineColour(), true));
		marker.appendChild(circle);
	}



	/**
	 * Return the SVG tree of the arrowhead or null if this arrowhead has no style.
	 * @param doc The document used to create elements.
	 * @param isShadow True: this operation is call to create the SVG shadow of the shape.
	 * @return The SVG tree of the arrowhead or null if doc is null.
	 * @since 2.0.0
	 */
	public SVGElement toSVG(final SVGDocument doc, final boolean isShadow) {
		if(doc==null || !arrow.hasStyle())
			return null;

		final IShape shape 			= arrow.getShape();
		final ArrowStyle arrowStyle = arrow.getArrowStyle();
		final SVGElement marker 	= new SVGMarkerElement(doc);
		final double lineWidth  	= shape.hasDbleBord() ? shape.getDbleBordSep() + 2.*shape.getThickness() : shape.getThickness();
		double gapPostion 			= 0.;

		if(arrowStyle==ArrowStyle.CIRCLE_END || arrowStyle==ArrowStyle.CIRCLE_IN)
			gapPostion = toSVGCircle(doc, lineWidth, isShadow, marker);
		else
		if(arrowStyle==ArrowStyle.DISK_END || arrowStyle==ArrowStyle.DISK_IN)
			gapPostion = toSVGDisk(doc, lineWidth, isShadow, marker);
		else
		if(arrowStyle.isBar())
			toSVGBar(doc, lineWidth, isShadow, marker);
		else if(arrowStyle.isSquareBracket())
			toSVGSquareBracket(doc, lineWidth, isShadow, marker);
		else
		if(arrowStyle==ArrowStyle.RIGHT_ARROW || arrowStyle==ArrowStyle.LEFT_ARROW)
			toSVGArrow(doc, lineWidth, isShadow, marker);
		else
		if(arrowStyle.isRoundBracket())
			toSVGRoundBracket(doc, lineWidth, isShadow, marker);
		else
		if(arrowStyle==ArrowStyle.LEFT_DBLE_ARROW || arrowStyle==ArrowStyle.RIGHT_DBLE_ARROW)
			toSVGDoubleArrow(doc, lineWidth, isShadow, marker);
		else
		if(arrowStyle==ArrowStyle.ROUND_IN)
			toSVGRoundIn(doc, isShadow, marker);

		if(!LNumber.INSTANCE.equals(gapPostion,0.))
			marker.setAttribute(SVGAttributes.SVG_REF_X, String.valueOf(gapPostion/lineWidth));

		marker.setAttribute(SVGAttributes.SVG_OVERFLOW, SVGAttributes.SVG_VALUE_VISIBLE);
		marker.setAttribute(SVGAttributes.SVG_ORIENT, SVGAttributes.SVG_VALUE_AUTO);

		return marker;
	}
}
