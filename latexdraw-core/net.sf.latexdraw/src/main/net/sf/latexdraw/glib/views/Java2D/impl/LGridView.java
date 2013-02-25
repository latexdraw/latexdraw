package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IGrid model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
class LGridView extends LStandardGridView<IGrid> {
	/** The Java2D path used to draw the sub-grid. */
	protected Path2D pathSubGrid;


	/**
	 * Initialises the grid view.
	 * @param grid The model of the grid.
	 * @since 3.0
	 */
	protected LGridView(final IGrid grid) {
		super(grid);
		pathSubGrid = new Path2D.Double();
		update();
	}


	@Override
	public void flush() {
		super.flush();
		pathSubGrid = null;
	}


	@Override
	public void paint(final Graphics2D g) {
		if(g==null) return ;

		// Drawing the sub grid.
		g.setColor(shape.getSubGridColour());

		if(shape.getSubGridDots()>0)
			g.setStroke(new BasicStroke((float)shape.getSubGridWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		else
			g.setStroke(new BasicStroke((float)shape.getSubGridWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

		g.draw(pathSubGrid);

		// Drawing the main grid.
		g.setColor(shape.getLineColour());

		if(shape.getGridDots()>0)
			g.setStroke(new BasicStroke((float)shape.getGridWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		else
			g.setStroke(new BasicStroke((float)shape.getGridWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

		g.draw(path);

		// Drawing the labels.
		//FIXME: labels may be not visible.
		g.setColor(shape.getGridLabelsColour());
		g.fill(pathLabels);
	}


	@Override
	public void updateBorder() {
		final double angle = shape.getRotationAngle();

		if(LNumber.INSTANCE.equals(angle, 0.)) //FIXME: labels may be not visible.
			border.setFrame(path.getBounds2D().createUnion(pathLabels.getBounds2D()));
		else
			BadaboomCollector.INSTANCE.add(new IllegalAccessException());
			//TODO
	}


	private void updatePathMainGridDots(final double unit, final double minX, final double maxX, final double minY, final double maxY,
										final double posX, final double posY, final double xStep, final double yStep,
										final double tlx, final double tly, final double brx, final double bry, final double absStep) {
		final int gridDots   = shape.getGridDots();
		final double dotStep = unit*IShape.PPC/gridDots;
		double k, i, l, m, n, j;

		for(k=minX, i=posX; k<=maxX; i+=xStep, k++)
			for(m=tly, n=minY; n<maxY; n++, m+=absStep)
				for(l=0, j=m; l<gridDots; l++, j+=dotStep) {
					path.moveTo(i, j);
					path.lineTo(i, j);
				}

		for(k=minY, i=posY; k<=maxY; i-=yStep, k++)
			for(m=tlx, n=minX; n<maxX; n++, m+=absStep)
				for(l=0, j=m; l<gridDots; l++, j+=dotStep) {
					path.moveTo(j, i);
					path.lineTo(j, i);
				}

		path.moveTo(brx, bry);
		path.lineTo(brx, bry);
	}


	protected void updatePathMainGrid(final double unit, final double minX, final double maxX, final double minY, final double maxY,
										final double posX, final double posY, final double xStep, final double yStep,
										final double tlx, final double tly, final double brx, final double bry, final double absStep) {
		double k, i;

		if(shape.getGridDots()>0)
			updatePathMainGridDots(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, brx, bry, absStep);
		else {
			for(k=minX, i=posX; k<=maxX; i+=xStep, k++) {
				path.moveTo(i, bry);
				path.lineTo(i, tly);
			}

			for(k=minY, i=posY; k<=maxY; i-=yStep, k++) {
				path.moveTo(tlx, i);
				path.lineTo(brx, i);
			}
		}
	}



	protected void updatePathSubGrid(final double unit, final double minX, final double maxX, final double minY, final double maxY,
									final double posX, final double posY, final double xStep, final double yStep,
									final double tlx, final double tly, final double brx, final double bry) {
		final double subGridDiv	= shape.getSubGridDiv();
		final double subGridDots= shape.getSubGridDots();
		final double xSubStep  	= xStep/subGridDiv;
		final double ySubStep  	= yStep/subGridDiv;
		double i, j, n, m, k;

		// We draw the sub-grid
		if(subGridDots>0) {
			final double dotStep = unit*IShape.PPC/(subGridDots*subGridDiv);
			final double nbX = (maxX-minX)*subGridDiv;
			final double nbY = (maxY-minY)*subGridDiv;

			for(i=0, n=tlx; i<nbX; i++, n+=xSubStep)
				for(j=0, m=tly; j<=nbY; j++, m+=ySubStep)
					for(k=0; k<subGridDots; k++) {
						pathSubGrid.moveTo(n+k*dotStep, m);
						pathSubGrid.lineTo(n+k*dotStep, m);
					}

			for(j=0, n=tly; j<nbY; j++, n+=ySubStep)
				for(i=0, m=tlx; i<=nbX; i++, m+=xSubStep)
					for(k=0; k<subGridDots; k++) {
						pathSubGrid.moveTo(m, n+k*dotStep);
						pathSubGrid.lineTo(m, n+k*dotStep);
					}

			pathSubGrid.moveTo(brx, bry);
			pathSubGrid.lineTo(brx, bry);
		}
		else
			if(subGridDiv>1) {
				for(k=minX, i=posX; k<maxX; i+=xStep, k++)
					for(j=0; j<=subGridDiv; j++) {
						pathSubGrid.moveTo(i+xSubStep*j, bry);
						pathSubGrid.lineTo(i+xSubStep*j, tly);
					}

				for(k=minY, i=posY; k<maxY; i-=yStep, k++)
					for(j=0; j<=subGridDiv; j++) {
						pathSubGrid.moveTo(tlx, i-ySubStep*j);
						pathSubGrid.lineTo(brx, i-ySubStep*j);
					}
			}
	}



	protected void updatePathLabels(final double minX, final double maxX, final double minY, final double maxY,
									final double posX, final double posY, final double xStep, final double yStep,
									final double tlx, final double tly, final double absStep) {
		final int labelsSize 	= shape.getLabelsSize();
		if(labelsSize<0) return ;

		final float labelHeight = fontMetrics.getAscent();
		final float labelWidth	= fontMetrics.stringWidth(String.valueOf((int)maxX));
		final double origX 		= shape.getOriginX();
		final double origY 		= shape.getOriginY();
		final boolean isWest  	= shape.isYLabelWest();
		final boolean isSouth 	= shape.isXLabelSouth();
		final double xorig 		= posX+xStep*origX;
		final double yorig 		= isSouth  ? posY-yStep*origY+labelHeight : posY-yStep*origY-2;
		final double width 		= shape.getGridWidth()/2.;
		final double tmp 		= isSouth ? width : -width;
		final Font font 		= fontMetrics.getFont();
		final FontRenderContext frc = new FontRenderContext(null, true, true);
		double i, j;
		String label;
		float x;

		for(i=tlx + (isWest ? width+labelsSize/4. : -width-labelWidth-labelsSize/4.), j=minX; j<=maxX; i+=absStep, j++)
			updateText(String.valueOf((int)j), (float)i, (float)(yorig+tmp), font, frc);

		for(i=tly + (isSouth ? -width-labelsSize/4. : width+labelHeight), j=maxY ; j>=minY; i+=absStep, j--) {
			label = String.valueOf((int)j);
			x	  = isWest ? (float)(xorig-fontMetrics.stringWidth(label)-labelsSize/4.-width) : (float)(xorig+labelsSize/4.+width);
			updateText(label, x, (float)i, font, frc);
		}
	}


	@Override
	public void updatePath() {
		final double minY = shape.getGridMinY();
		final double maxY = shape.getGridMaxY();
		final double minX = shape.getGridMinX();
		final double maxX = shape.getGridMaxX();
		final double unit = shape.getUnit();
		double yStep 	  = IShape.PPC*unit;
		double xStep 	  = IShape.PPC*unit;
		xStep *= shape.getGridEndX()<shape.getGridStartX() ? -1 : 1 ;
		yStep *= shape.getGridEndY()<shape.getGridStartY() ? -1 : 1 ;
		final IPoint pos	 = shape.getPosition();
		final double posX 	 = pos.getX()+Math.min(shape.getGridStartX(), shape.getGridEndX())*IShape.PPC*unit;
		final double posY 	 = pos.getY()-Math.min(shape.getGridStartY(), shape.getGridEndY())*IShape.PPC*unit;
		final double absStep = Math.abs(xStep);
		final Rectangle2D bounds = getGridBounds(posX, posY);
		final double tlx = bounds.getMinX();
		final double tly = bounds.getMinY();

		path.reset();
		pathLabels.reset();
		pathSubGrid.reset();

		updatePathSubGrid(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, bounds.getMaxX(), bounds.getMaxY());
		updatePathMainGrid(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, bounds.getMaxX(), bounds.getMaxY(), absStep);
		updatePathLabels(minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, absStep);
	}



	protected Rectangle2D getGridBounds(final double posX, final double posY) {
		final Rectangle2D rec = new Rectangle2D.Double();
		final double gridStartx = shape.getGridStartX();
		final double gridStarty = shape.getGridStartY();
		final double gridEndx = shape.getGridEndX();
		final double gridEndy = shape.getGridEndY();
		final double step = IShape.PPC*shape.getUnit();

		if(gridStartx<gridEndx)
			if(gridStarty<gridEndy)
				rec.setFrameFromDiagonal(posX, posY-step*Math.abs(gridEndy-gridStarty), posX+step*Math.abs(gridEndx-gridStartx), posY);
			else
				rec.setFrameFromDiagonal(posX, posY, posX+step*Math.abs(gridEndx-gridStartx), posY+step*Math.abs(gridEndy-gridStarty));
		else
			if(gridStarty<gridEndy)
				rec.setFrameFromDiagonal(posX-step*Math.abs(gridEndx-gridStartx),  posY-step*Math.abs(gridEndy-gridStarty), posX, posY);
			else
				rec.setFrameFromDiagonal(posX-step*Math.abs(gridEndx-gridStartx), posY, posX, posY+step*Math.abs(gridEndy-gridStarty));

		return rec;
	}
}
