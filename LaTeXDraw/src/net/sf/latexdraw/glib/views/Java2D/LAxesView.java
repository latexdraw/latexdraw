package net.sf.latexdraw.glib.views.Java2D;

import net.sf.latexdraw.glib.models.interfaces.IAxes;

/**
 * Defines a view of the IAxes model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 04/12/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LAxesView extends LStandardGridView<IAxes> {
	/** The interval between the labels and the axes. */
	public static final double GAP_LABEL = 5.;


	protected LAxesView(final IAxes model) {
		super(model);
		update();
	}



//	@Override
//	public void paint(Graphics2D g)
//	{
//		if(g==null)
//			return ;
//
//		IPoint p 	 = beginRotation(g);
//		IAxes axe 	 = (IAxes)shape;
//		IPoint end   = shape.getGridEnd();
//		IPoint start = shape.getGridStart();
//		IPoint orig  = shape.getOrigin();
//		IPoint incr  = shape.getIncrement();
//		IPoint pos   = shape.getPosition();
//		double endx  = end.getX();
//		double endy  = end.getY();
//		double posx  = pos.getX();
//		double posy  = pos.getY();
//		double incrx = incr.getX();
//		double incry = incr.getY();
//		double origx = orig.getX();
//		double origy = orig.getY();
//		double startx = start.getX();
//		double starty = start.getY();
//		double distX  = shape.getDistLabelsX();
//		double distY  = shape.getDistLabelsY();
//		boolean isSouth = shape.isXLabelSouth();
//		boolean isWest  = shape.isYLabelWest();
//		boolean isOrig  = shape.isShowOrigin();
//		PlottingStyle labelsDisplay = shape.getLabelsDisplayed();
//		PlottingStyle ticksDisplay  = shape.getTicksDisplayed();
//		TicksStyle ticksStyle		= shape.getTicksStyle();
//		AxesStyle axesStyle			= shape.getAxesStyle();
//		LineStyle lineStyle			= shape.getLineStyle();
//		double thickness			= shape.getThickness();
//		Color lineColour			= shape.getLineColour();
//		double ticksSize			= shape.getTicksSize();
//
//		double minX, maxX, minY, maxY, maxX3, minX3, maxY3, minY3;
//		double gapX = distX==0. ? IShape.PPC : (distX/incr.getX())*IShape.PPC, i;
//		double gapY = distY==0. ? IShape.PPC : (distY/incr.getY())*IShape.PPC;
//		boolean ticksBot = ticksStyle==TicksStyle.BOTTOM || ticksStyle==TicksStyle.FULL;
//		int j;
//
//		switch(lineStyle) {
//			case SOLID:
//				g.setStroke(new BasicStroke((float)thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
//				break;
//
//			case DOTTED:
//				g.setStroke(new BasicStroke((float)thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER,
//						1.f, new float[]{0,(float)(thickness+shape.getDotSep())}, 0));
//				break;
//
//			case DASHED:
//				g.setStroke(new BasicStroke((float)thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//						1.f, new float[]{(float)shape.getDashSepBlack(), (float)shape.getDashSepWhite()}, 0));
//				break;
//
//			default: break;
//		}
//
//
//		g.setColor(lineColour);
//		g.setFont(font);
//
//		if(end.getX()<start.getX()) {
//			minX = end.getX();
//			maxX = start.getX();
//		}
//		else {
//			minX = start.getX();
//			maxX = end.getX();
//		}
//
//		if(end.getY()<start.getY()) {
//			minY = end.getY();
//			maxY = start.getY();
//		}
//		else {
//			minY = start.getY();
//			maxY = end.getY();
//		}
//
//		if(distX!=0.) {
//			if(maxX!=0.) {
//				maxX = maxX/distX;
//				if(maxX==0.)
//					maxX=0.1;
//			}
//
//			if(minX!=0) {
//				minX = minX/distX;
//				if(minX==0.)
//					minX=0.1;
//			}
//		}
//
//		if(distY!=0.) {
//			if(maxY!=0.) {
//				maxY = maxY/distY;
//				if(maxY==0.)
//					maxY=0.1;
//			}
//
//			if(minY!=0.) {
//				minY = minY/distY;
//				if(minY==0.)
//					minY=0.1;
//			}
//		}
//
////		boolean arrow1Ok = !arrowHead1.getArrowStyle().equals(PSTricksConstants.NONEARROW_STYLE);
////		boolean arrow2Ok = !arrowHead2.getArrowStyle().equals(PSTricksConstants.NONEARROW_STYLE);
//		boolean arrow1Ok = false;
//		boolean arrow2Ok = false;
//
//		minX3 = axesStyle==AxesStyle.AXES && ((arrow1Ok && isWest) ||
//				(arrow2Ok && !isWest)) && start.getX()!=0 && (distX==0. || distX==1.)? minX+1 : minX;
//		minY3 = axesStyle==AxesStyle.AXES && ((arrow1Ok && isSouth) ||
//				(arrow2Ok && !isSouth)) && start.getY()!=0 && (distY==0. || distY==1.)? minY+1 : minY;
//		maxX3 = axesStyle==AxesStyle.AXES && ((arrow2Ok && isWest) || (arrow1Ok && !isWest)) &&
//		 		(distX==0. || distX==1.)? maxX-1 : maxX;
//		maxY3 = axesStyle==AxesStyle.AXES && ((arrow2Ok && isSouth) || (arrow1Ok && !isSouth)) &&
//		 		(distY==0. || distY==1.)? maxY-1 : maxY;
//
//		minX3 = minX>0 ? 0 : minX3;
//		maxX3 = maxX<0 ? 0 : maxX3;
//		minY3 = minY>0 ? 0 : minY3;
//		maxY3 = maxY<0 ? 0 : maxY3;
//
//		switch(axesStyle)
//		{
//			case AXES:
////				boolean arrowOk = (float)minX!=(float)maxX || (float)maxX!=(float)minY || (float)minY!=(float)maxY;
//				LLine xLine = new LLine(new LPoint(posx+start.getX()*IShape.PPC, posy),
//										new LPoint(posx+end.getX()*IShape.PPC, posy));
//				LLine yLine = new LLine(new LPoint(posx, posy-end.getY()*IShape.PPC),
//										new LPoint(posx, posy-start.getY()*IShape.PPC));
//
////				if(!arrowHead1.getArrowStyle().equals(PSTricksConstants.NONEARROW_STYLE) && arrowOk)
////				{
////					String arrowStyle = arrowHead1.getArrowStyle();
////					lgth = arrowHead1.getArrowHeadLength();
////					boolean isArrow = arrowHead1.needReduceLine();
////
////					if((minX!=0 && isWest) || (maxX!=0 && !isWest) || maxY==minY)
////					{
////						if(isArrow && !isWest)
////						{
////							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
////								arrowStyle = PSTricksConstants.LARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
////								arrowStyle = PSTricksConstants.RARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
////								arrowStyle = PSTricksConstants.DRARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
////								arrowStyle = PSTricksConstants.DLARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
////						}
////
////						if(isArrow)
////							if(isWest)
////								 xLine.getPt1().x-=lgth;
////							else xLine.getPt2().x+=lgth;
////
////						if(isWest)
////							xLine.setArrow1Style(arrowStyle);
////						else
////							xLine.setArrow2Style(arrowStyle);
////					}
////
////					if((minY!=0 && isSouth) || (maxY!=0 && !isSouth) || maxX==minX)
////					{
////						arrowStyle = arrowHead1.getArrowStyle();
////
////						if(isArrow && isSouth)
////						{
////							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
////								arrowStyle = PSTricksConstants.LARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
////								arrowStyle = PSTricksConstants.RARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
////								arrowStyle = PSTricksConstants.DRARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
////								arrowStyle = PSTricksConstants.DLARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
////						}
////
////						if(isArrow)
////							if(isSouth)
////								 yLine.getPt2().y+=lgth;
////							else yLine.getPt1().y-=lgth;
////
////						if(isSouth)
////							yLine.setArrow2Style(arrowStyle);
////						else
////							yLine.setArrow1Style(arrowStyle);
////					}
////				}
////
////				if(!arrowHead2.getArrowStyle().equals(PSTricksConstants.NONEARROW_STYLE) && arrowOk)
////				{
////					String arrowStyle = arrowHead2.getArrowStyle();
////					lgth = arrowHead2.getArrowHeadLength();
////					boolean isArrow = arrowHead2.needReduceLine();
////
////					if((maxY!=0 && isSouth) || (minY!=0 && !isSouth) || maxY==minY)
////					{
////						if(isArrow && !isSouth)
////						{
////							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
////								arrowStyle = PSTricksConstants.LARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
////								arrowStyle = PSTricksConstants.RARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
////								arrowStyle = PSTricksConstants.DRARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
////								arrowStyle = PSTricksConstants.DLARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
////						}
////
////						if(maxY==minY && minY==0)
////						{
////							if(isArrow)
////								if(maxX>0)
////									 xLine.getPt1().y-=lgth;
////								else xLine.getPt2().y+=lgth;
////
////							if(maxX>0)
////								xLine.setArrow1Style(arrowStyle);
////							else
////								xLine.setArrow2Style(arrowStyle);
////						}
////						else
////						{
////							if(isArrow)
////								if(isSouth)
////									 yLine.getPt1().y-=lgth;
////								else yLine.getPt2().y+=lgth;
////
////							if(isSouth)
////								yLine.setArrow1Style(arrowStyle);
////							else
////								yLine.setArrow2Style(arrowStyle);
////						}
////					}
////
////					if((maxX!=0 && isWest) || (minX!=0 && !isWest) || maxX==minX)
////					{
////						arrowStyle = arrowHead2.getArrowStyle();
////
////						if(isArrow && isWest)
////						{
////							if(arrowStyle.equals(PSTricksConstants.RARROW_STYLE))
////								arrowStyle = PSTricksConstants.LARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LARROW_STYLE))
////								arrowStyle = PSTricksConstants.RARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DLARROW_STYLE))
////								arrowStyle = PSTricksConstants.DRARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.DRARROW_STYLE))
////								arrowStyle = PSTricksConstants.DLARROW_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RRBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.RSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.LSBRACKET_STYLE;
////							else if(arrowStyle.equals(PSTricksConstants.LSBRACKET_STYLE))
////								arrowStyle = PSTricksConstants.RSBRACKET_STYLE;
////						}
////
////						if(maxX==minX && minX==0)
////						{
////							if(isArrow)
////								if(maxY>0)
////									 yLine.getPt2().x+=lgth;
////								else xLine.getPt1().x-=lgth;
////
////							if(maxY>0)
////								yLine.setArrow2Style(arrowStyle);
////							else
////								yLine.setArrow1Style(arrowStyle);
////						}
////						else
////						{
////							if(isArrow)
////								if(isWest)
////									 xLine.getPt2().x+=lgth;
////								else xLine.getPt1().x-=lgth;
////
////							if(isWest)
////								xLine.setArrow2Style(arrowStyle);
////							else
////								xLine.setArrow1Style(arrowStyle);
////						}
////					}
////				}
//
//				g.draw(xLine);
//				g.draw(yLine);
//
//				break;
//
//			case FRAME:
//				if(endx>0 || endy>0) {
//					double xMax = posx+endx*IShape.PPC;
//					double yMax = posy-endy*IShape.PPC;
//
//					g.draw(new Rectangle2D.Double(posx, endy>0?yMax:posy, endx>0?xMax:posx, posy));
//				}
//				break;
//
//			case NONE:
//				/* Nothing to do. */
//				break;
//		}
//
//		double endX = distX!=0. ? maxX3 : maxX3/incrx;
//		double endY = distY!=0. ? maxY3 : maxY3/incry;
//		double startX = distX!=0. ? minX3 : minX3/incrx;
//		double startY = distY!=0. ? minY3 : minY3/incry;
//
//		if(ticksDisplay!=PlottingStyle.NONE)// We draw the ticks
//		{
//			if(ticksStyle==TicksStyle.FULL || (ticksStyle==TicksStyle.TOP && maxY>0) ||
//			  (ticksStyle==TicksStyle.BOTTOM && maxY<=0))
//				if(ticksDisplay==PlottingStyle.X || ticksDisplay==PlottingStyle.ALL)
//				{
//					for(j=1; j<=endX; j++)
//						g.draw(new Line2D.Double(posx+j*gapX*incrx, posy,
//								 posx+j*gapX*incrx, posy-ticksSize-thickness/2.));
//
//					for(j=-1; j>=startX; j--)
//						g.draw(new Line2D.Double(posx+j*gapX*incrx, posy,
//								posx+j*gapX*incrx, posy-ticksSize-thickness/2.));
//				}
//
//			if(ticksStyle==TicksStyle.FULL || (ticksStyle==TicksStyle.TOP && maxX>0) ||
//			  (ticksStyle==TicksStyle.BOTTOM && maxX<=0))
//				if(ticksDisplay==PlottingStyle.Y || ticksDisplay==PlottingStyle.ALL)
//				{
//					for(j=1; j<=endY; j++)
//						g.draw(new Line2D.Double(posx, posy-j*gapY*incry,
//								 posx+ticksSize+thickness/2., posy-j*gapY*incry));
//
//					for(j=-1; j>=startY; j--)
//						g.draw(new Line2D.Double(posx, posy-j*gapY*incry,
//								posx+ticksSize+thickness/2., posy-j*gapY*incry));
//				}
//
//			if(ticksStyle==TicksStyle.FULL || (ticksStyle==TicksStyle.BOTTOM && maxY>0) ||
//			  (ticksStyle==TicksStyle.TOP && maxY<=0))
//				if(ticksDisplay==PlottingStyle.X || ticksDisplay==PlottingStyle.ALL)
//				{
//					for(j=1; j<=endX; j++)
//						g.draw(new Line2D.Double(posx+j*gapX*incrx, posy,
//								 posx+j*gapX*incrx, posy+ticksSize+thickness/2.));
//
//					for(j=-1; j>=startX; j--)
//						g.draw(new Line2D.Double(posx+j*gapX*incrx, posy,
//								posx+j*gapX*incrx, posy+ticksSize+thickness/2.));
//				}
//
//			if(ticksStyle==TicksStyle.FULL || (ticksStyle==TicksStyle.BOTTOM && maxX>0) ||
//				(ticksStyle==TicksStyle.TOP && maxX<=0))
//				if(ticksDisplay==PlottingStyle.Y || ticksDisplay==PlottingStyle.ALL)
//				{
//					for(j=1; j<=endY; j++)
//						g.draw(new Line2D.Double(posx, posy-j*gapY*incry,
//								 posx-ticksSize-thickness/2., posy-j*gapY*incry));
//
//					for(j=-1; j>=startY; j--)
//						g.draw(new Line2D.Double(posx, posy-j*gapY*incry,
//								posx-ticksSize-thickness/2., posy-j*gapY*incry));
//				}
//		}
//
//		g.setColor(Color.BLACK);
//
//		if(labelsDisplay==PlottingStyle.ALL || labelsDisplay==PlottingStyle.X)
//		{// We show the labels on the X-shape.
//			float height 	= fontMetrics.getAscent();
//			double gap 		= ((ticksDisplay==PlottingStyle.ALL || ticksDisplay==PlottingStyle.X)&&
//							((isSouth && (ticksStyle==TicksStyle.BOTTOM || ticksStyle==TicksStyle.FULL)) ||
//							 (!isSouth && (ticksStyle==TicksStyle.TOP || ticksStyle==TicksStyle.FULL)))?
//							  ticksSize:0)+thickness/2.+GAP_LABEL;
//			double sep		= maxY<=0 || !isSouth? -gap-GAP_LABEL : gap+height;
//			String str;
//
//			if(((isSouth && starty>=0) || (!isSouth &&
//				endy<=0)) && (startx!=endx || startx!=0) && starty<=0 && isOrig)
//					g.drawString(String.valueOf((int)origx),
//							(int)(posx-fontMetrics.stringWidth(String.valueOf((int)origx))/2.), (int)(posy+sep));
//
//			for(i=incrx, j=1; j<=endX; i+=incrx, j++) {
//				str = (((int)incrx)==incrx?String.valueOf((int)(i+origx)) : String.valueOf(i+origx));
//				g.drawString(str, (int)(posx+j*gapX*incrx-fontMetrics.stringWidth(str)/2.), (int)(posy+sep));
//			}
//
//			for(i=-incrx, j=-1; j>=startX; i-=incrx, j--) {
//				str = (((int)incrx)==incrx?String.valueOf((int)(i+origx)): String.valueOf(i+origx));
//				g.drawString(str, (int)(posx+j*gapX*incrx-fontMetrics.stringWidth(str)/2.), (int)(posy+sep));
//			}
//		}
//
//		if(labelsDisplay==PlottingStyle.ALL || labelsDisplay==PlottingStyle.Y)
//		{// We show the labels on the Y-shape.
//			float height = fontMetrics.getAscent();
//			String str;
//
//			if(maxX>0 && isWest)
//			{
//				double gap	= (ticksBot && (ticksDisplay==PlottingStyle.ALL ||
//								ticksDisplay==PlottingStyle.Y)?ticksSize:0)+thickness/2.;
//
//				if(startx==0 && (starty!=endy || starty!=0) && isOrig)
//					g.drawString(String.valueOf((int)origy),
//							(int)(posx-gap-g.getFontMetrics().stringWidth(String.valueOf((int)origy))-GAP_LABEL),
//							(int)(posy+height/2.));
//
//				for(i=incry, j=1; j<=endY; i+=incry, j++) {
//					str = ((int)incry)==incry?String.valueOf((int)(i+origy)):String.valueOf(i+origy);
//					g.drawString(str, (int)(posx-gap-fontMetrics.stringWidth(str)-GAP_LABEL),
//								(int)(posy+height/2.-j*gapY*incry));
//				}
//
//				for(i=-incry, j=-1; j>=startY; i-=incry, j--) {
//					str = ((int)incry)==incry?String.valueOf((int)(i+origy)):String.valueOf(i+origy);
//					g.drawString(str, (int)(posx-gap-fontMetrics.stringWidth(str)-GAP_LABEL),
//								(int)(posy+height/2.-j*gapY*incry));
//				}
//			}
//			else
//			{
//				double gap	= ((!ticksBot || ticksStyle==TicksStyle.FULL) && (ticksDisplay==PlottingStyle.ALL||
//								ticksDisplay==PlottingStyle.Y)?ticksSize:0)+thickness/2.;
//
//				if((!isWest && endx<=0) || (isWest && startx>=0) && (starty!=endy  || starty!=0) && isOrig)
//					g.drawString(String.valueOf((int)origy), (int)(posx+gap+GAP_LABEL), (int)(posy+height/2.));
//
//				for(i=incry, j=1; j<=endY; i+=incry, j++)
//					g.drawString(((int)incry)==incry?String.valueOf((int)(i+origy)):String.valueOf(i+origy),
//								(int)(posx+gap+GAP_LABEL), (int)(posy+height/2.-j*gapY*incry));
//
//				for(i=-incry, j=-1; j>=startY; i-=incry, j--)
//					g.drawString(((int)incry)==incry?String.valueOf((int)(i+origy)):String.valueOf(i+origy),
//								(int)(posx+gap+GAP_LABEL), (int)(posy+height/2.-j*gapY*incry));
//			}
//		}
//
//		if(isSelected)
//			border.paint(g);
//
//		if(p!=null)
//			endRotation(g, p);
//	}



//	@Override
//	public void updateBorder() {
//		final IPoint pos   = shape.getPosition();
//		final double endx  = shape.getGridEndX();
//		final double endy  = shape.getGridEndY();
//		final double posx  = pos.getX();
//		final double posy  = pos.getY();
//		final double startx = shape.getGridStartX();
//		final double starty = shape.getGridStartY();
//		final TicksStyle ticksStyle = shape.getTicksStyle();
//		final double ticksSize	  	= shape.getTicksSize();
//
//		double minX, maxX, minY, maxY, add1X=0., add2X=0., add1Y=0., add2Y=0.;
//		final boolean ticksTop = ticksStyle==TicksStyle.FULL || ticksStyle==TicksStyle.TOP;
//		final boolean ticksBot = ticksStyle==TicksStyle.FULL || ticksStyle==TicksStyle.BOTTOM;
//
//		if(endx<startx) {
//			minX = endx;
//			maxX = startx;
//		}
//		else {
//			minX = startx;
//			maxX = endx;
//		}
//
//		if(endy<starty) {
//			minY = endy;
//			maxY = starty;
//		}
//		else {
//			minY = starty;
//			maxY = endy;
//		}
//
//		if(minX>=0. && shape.isYLabelWest())
//			add1X = Math.max(fontMetrics.stringWidth(String.valueOf(minY)),
//							 fontMetrics.stringWidth(String.valueOf(maxY)))+(ticksBot ? ticksSize : 0.);
//
//		if(maxX<=0.)
//			add2X = Math.max(fontMetrics.stringWidth(String.valueOf(minY)),
//							 fontMetrics.stringWidth(String.valueOf(maxY)))+(ticksBot ? ticksSize : 0.);
//
//		if(minY>=0. && shape.isXLabelSouth())
//			add2Y = fontMetrics.getHeight()+GAP_LABEL+(ticksBot ? ticksSize : 0.);
//
//		if(maxY<=0.)
//			add1Y = fontMetrics.getHeight()+GAP_LABEL+(ticksTop ? ticksSize : 0.);
//
//		border.setFrameFromDiagonal(posx+Math.min(0, minX)*IShape.PPC-add1X,
//									posy-Math.max(0, maxY)*IShape.PPC-add1Y,
//									posx+Math.max(0, maxX)*IShape.PPC+add2X,
//									posy-Math.min(0, minY)*IShape.PPC+add2Y);
//	}
}
