package net.sf.latexdraw.glib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.ui.ScaleRuler.Unit;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;

import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class defines a magnetic grid.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 01/21/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LMagneticGrid implements Preferenciable, Modifiable {
	/**
	 * This enumeration contains the different style
	 * of a magnetic grid.
	 */
	public static enum GridStyle {
		CUSTOMISED {
			@Override
			public String getLabel() {
				return LResources.LABEL_DISPLAY_PERSO_GRID;
			}
		},
		STANDARD {
			@Override
			public String getLabel() {
				return LResources.LABEL_DISPLAY_GRID;
			}
		},
		NONE {
			@Override
			public String getLabel() {
				return "None"; //$NON-NLS-1$
			}
		};

		/**
		 * @return The label of the style.
		 * @since 3.0
		 */
		public abstract String getLabel();


		/**
		 * Searches the style which label matches the given name.
		 * @param name The name of the style to find.
		 * @return The found style or null.
		 * @since 3.0
		 */
		public static GridStyle getStylefromName(final String name) {
			GridStyle style = null;

			if(CUSTOMISED.toString().equals(name))
				style = CUSTOMISED;
			else if(STANDARD.toString().equals(name))
				style = STANDARD;
			else if(NONE.toString().equals(name))
				style = NONE;

			return style;
		}

		/**
		 * Searches the style which label matches the given label. Warning,
		 * labels change depending on the language.
		 * @param label The label of the style to find.
		 * @return The found style or null.
		 * @since 3.0
		 */
		public static GridStyle getStyleFromLabel(final String label) {
			GridStyle style = null;

			if(CUSTOMISED.getLabel().equals(label))
				style = CUSTOMISED;
			else if(STANDARD.getLabel().equals(label))
				style = STANDARD;
			else if(NONE.getLabel().equals(label))
				style = NONE;

			return style;
		}
	}

	/** The stroke of the grid. */
	public static final BasicStroke STROKE = new BasicStroke(0, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

	/** Allows to know if the grid is magnetic or not. */
	protected boolean isMagnetic;

	/** defines the spacing between the lines of the grid. */
	protected int gridSpacing;

	/** The width of the grid. */
	protected int width;

	/** The height of the grid. */
	protected int height;

	/** The style of the grid. */
	protected GridStyle style;

	/** The canvas that paints the grid. */
	protected ICanvas canvas;

	/** Defined if the canvas has been modified. */
	protected boolean modified;


	/**
	 * Creates the magnetic grid.
	 * @param width The width of the grid.
	 * @param height The height of the grid.
	 * @param canvas The canvas in which the grid will work.
	 * @throws IllegalArgumentException if the given parameters are not valid.
	 * @since 2.0
	 */
	public LMagneticGrid(final int width, final int height, final ICanvas canvas) {
		super();

		if(width<0 || height<0 || canvas==null)
			throw new IllegalArgumentException();

		modified	= false;
		this.canvas	= canvas;
		this.width  = width;
		this.height = height;
		reinitGrid();
	}


	/**
	 * Paints the magnetic grid is activated.
	 * @param gaph The graphics in which the grid will be drawn.
	 * @since 3.0
	 */
	public void paint(final Graphics2D gaph) {
		if(isGridDisplayed() && width>0 && height>0) {
			double ppc = canvas.getPPCDrawing();

			if(ScaleRuler.getUnit()==Unit.INCH)
				ppc*=PSTricksConstants.INCH_VAL_CM;

			gaph.setColor(Color.WHITE);
			gaph.fillRect(0, 0, width, height);
			gaph.setColor(Color.BLACK);
			gaph.setStroke(STROKE);
			gaph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			switch(style) {
				case STANDARD:
					if(ScaleRuler.getUnit()==Unit.INCH)
						ppc*=PSTricksConstants.INCH_VAL_CM;

					paintSubLines(gaph);
					paintMainLines(gaph, ppc);
					break;
				case CUSTOMISED:
					paintMainLines(gaph, gridSpacing);
					break;
				case NONE: break;
			}
		}
	}



	protected void paintSubLines(final Graphics2D graph) {
		double ppc    = canvas.getPPCDrawing();
		double zoom	  = canvas.getZoom();

		if(ScaleRuler.getUnit()==Unit.INCH)
			ppc*=PSTricksConstants.INCH_VAL_CM;

		if(ppc>20) {
    		double i, j;
    		double pixPerCm10 = ppc/10.;
    		int cpt;
    		ppc*=zoom;
    		pixPerCm10*=zoom;

    		for(i=pixPerCm10-1; i<width; i+=ppc)
    			for(j=i, cpt=0; cpt<10; j+=pixPerCm10, cpt++)
    				graph.draw(new Line2D.Double(j, 0, j, height));

    		for(i=pixPerCm10-1; i<height; i+=ppc)
    			for(j=i, cpt=0; cpt<10; j+=pixPerCm10, cpt++)
    				graph.draw(new Line2D.Double(0, j, width, j));
    	}
	}



	protected void paintMainLines(final Graphics2D graph, final double gap) {
		double zoom	  = canvas.getZoom();
		double i, j;
		double gap2 = gap;

		gap2*=zoom;

		for(i=gap2-1; i<width; i+=gap2)
			graph.draw(new Line2D.Double(i, 0, i, height));

		for(j=gap2-1; j<height; j+=gap2)
			graph.draw(new Line2D.Double(0, j, width, j));
	}


	/**
	 * Transform a point to another "stick" to the magnetic grid.
	 * @since 1.8
	 * @param pt The point to transform.
	 * @return The transformed point or if there is no magnetic grid, a clone of the given point.
	 */
	public IPoint getTransformedPointToGrid(final Point2D pt) {
		final IShapeFactory factory = DrawingTK.getFactory();

	   	if(isMagnetic() && isGridDisplayed()) {
	   		IPoint point 	= factory.createPoint(pt.getX(), pt.getY());
    		double modulo 	= getMagneticGridGap();
    		double x 		= point.getX();
    		double y 		= point.getY();
    		int base 		= (int)(((int)(x/modulo))*modulo);

    		if(x>modulo) x = x%((int)modulo);

    		double res = modulo-x;
    		x = base;

    		if(res<modulo/2.) x+=modulo;

    		point.setX((int)x);
    		base = (int)(((int)(point.getY()/modulo))*modulo);

    		if(y>modulo) y = y%((int)modulo);

    		res = modulo-y;
    		y = base;

    		if(res<modulo/2.) y+=modulo;

    		point.setY((int)y);

    		return point;
    	}

	   	return factory.createPoint(pt.getX(), pt.getY());
	}



	/**
	 * @return The gap between the lines of the magnetic grid.
	 * @since 1.9
	 */
	public double getMagneticGridGap() {
		double gap;

		if(isPersonalGrid())
			gap = getGridSpacing();
		else {
			final double ppc = canvas.getPPCDrawing();
			gap = ScaleRuler.getUnit()==Unit.CM ? ppc/10. : (ppc*PSTricksConstants.INCH_VAL_CM)/10.;
			gap = (gap-(int)gap)>0.5? ((int)gap)+1 : (int)gap;
		}

		return gap;
	}


	/**
	 * Reinitialises the magnetic grid.
	 * @since 3.0
	 */
	public void reinitGrid() {
		setStyle(GridStyle.CUSTOMISED);
		setGridSpacing(20);
		setMagnetic(true);
	}


	/**
	 * @return True: the grid is magnetic.
	 * @since 2.0.0
	 */
	public boolean isMagnetic() {
		return isMagnetic;
	}


	/**
	 * @param isMagnetic True: the grid will be magnetic.
	 * @since 2.0.0
	 */
	public void setMagnetic(final boolean isMagnetic) {
		if(this.isMagnetic!=isMagnetic) {
			this.isMagnetic = isMagnetic;
			setModified(true);
		}
	}


	/**
	 * @return The new spacing between lines of the personal grid.
	 * @since 3.0
	 */
	public int getGridSpacing() {
		return gridSpacing;
	}


	/**
	 * @param gridSpacing The new spacing between lines of the personal grid.
	 * @since 3.0
	 */
	public void setGridSpacing(final int gridSpacing) {
		if(gridSpacing>1 && this.gridSpacing!=gridSpacing) {
			this.gridSpacing = gridSpacing;
			setModified(true);
		}
	}


	/**
	 * @return True: the grid is magnetic.
	 * @since 2.0.0
	 */
	public boolean isPersonalGrid() {
		return style==GridStyle.CUSTOMISED;
	}


	/**
	 * @return The width of the magnetic grid.
	 * @since 2.0.0
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * @return The height of the magnetic grid.
	 * @since 2.0.0
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * Sets the size of the magnetic grid.
	 * @param width The new width.
	 * @param height The new height.
	 * @since 3.0
	 */
	public void setSize(final int width, final int height) {
		if(height>=0)
			this.height = height;
		if(width>=0)
			this.width  = width;
	}


	/**
	 * @return The style of the magnetic grid.
	 * @since 2.0.0
	 */
	public GridStyle getStyle() {
		return style;
	}


	/**
	 * @param style The new style of the grid. If null, nothing is performed.
	 * @since 3.0
	 */
	public void setStyle(final GridStyle style) {
		if(style!=null && style!=this.style) {
			this.style = style;
			setModified(true);
		}
	}


	/**
	 * @return True: The magnetic grid must be displayed.
	 * @since 2.0.0
	 */
	public boolean isGridDisplayed() {
		return style!=GridStyle.NONE;
	}


	/**
	 * @return the canvas which displays the grid.
	 * @since 3.0
	 */
	public ICanvas getCanvas() {
		return canvas;
	}


	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;
	}


	@Override
	public boolean isModified() {
		return modified;
	}


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document==null || root==null)
			return ;

		Element elt;
		final String ns = generalPreferences ? "" : LPath.INSTANCE.getNormaliseNamespaceURI(nsURI); //$NON-NLS-1$
		elt = document.createElement(ns + LNamespace.XML_MAGNETIC_GRID_STYLE);
        elt.setTextContent(getStyle().toString());
        root.appendChild(elt);
        elt = document.createElement(ns + LNamespace.XML_GRID_GAP);
        elt.setTextContent(String.valueOf(getGridSpacing()));
        root.appendChild(elt);
        elt = document.createElement(ns + LNamespace.XML_MAGNETIC_GRID);
        elt.setTextContent(String.valueOf(isMagnetic()));
        root.appendChild(elt);
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		final NodeList nl = meta.getChildNodes();
		final String uri = nsURI==null ? "" : nsURI; //$NON-NLS-1$
		Node node;
		String name;

		for(int i=0, size=nl.getLength(); i<size; i++) {
			node = nl.item(i);

			// Must be a latexdraw tag.
			if(node!=null && uri.equals(node.getNamespaceURI())) {
				name = node.getNodeName();

				if(name.endsWith(LNamespace.XML_GRID_GAP))
					setGridSpacing(Integer.parseInt(node.getTextContent()));
				else if(name.endsWith(LNamespace.XML_MAGNETIC_GRID))
					setMagnetic(Boolean.parseBoolean(node.getTextContent()));
				else if(name.endsWith(LNamespace.XML_MAGNETIC_GRID_STYLE))
					setStyle(GridStyle.getStylefromName(node.getTextContent()));
				// To keep compatibility with latexdraw 2.0
				//TODO to remove with these tokens in a future release (in a couple of years).
				else if(name.endsWith(LNamespace.XML_DISPLAY_GRID)) {
					if(!Boolean.parseBoolean(node.getTextContent()))
						setStyle(GridStyle.NONE);
				} else if(name.endsWith(LNamespace.XML_CLASSIC_GRID))
					setStyle(Boolean.parseBoolean(node.getTextContent()) ? GridStyle.STANDARD : GridStyle.CUSTOMISED);
			}
		}
	}
}
