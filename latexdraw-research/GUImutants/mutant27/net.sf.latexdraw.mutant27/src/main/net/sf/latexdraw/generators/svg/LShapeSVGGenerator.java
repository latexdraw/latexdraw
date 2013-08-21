package net.sf.latexdraw.generators.svg;

import static java.lang.Math.PI;
import static java.lang.Math.toDegrees;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.ParseException;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.*;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import net.sf.latexdraw.parsers.svg.parsers.URIReferenceParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LNumber;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class allows the generation or the importation of SVG parameters to a general LaTeXDraw shape.<br>
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
 * 05/30/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
abstract class LShapeSVGGenerator<S extends IShape> {
	/** The shape model use for the generation. */
	protected S shape;

	/** The beginning of the token used to declare a URL in an SVG document. */
	protected static final String SVG_URL_TOKEN_BEGIN = "url(#"; //$NON-NLS-1$


	/**
	 * Creates the SVG generator.
	 * @param sh The shape used for the generation.
	 * @throws IllegalArgumentException If the given shape is null.
	 * @since 2.0
	 */
	protected LShapeSVGGenerator(final S sh) {
		if(sh==null)
			throw new IllegalArgumentException();

		shape = sh;
	}



	/**
	 * @return The SVG ID of the shape (starting with the token "id" followed by the number of the shape).
	 * @since 2.0.0
	 */
	public String getSVGID() {
		return "id" + shape.getId(); //$NON-NLS-1$
	}



	/**
	 * Sets the ID of the figure according to the ID attribute of the SVGGElement, or an increment of meter if a problem occur.
	 * @param g The SVGGElement.
	 * @since 2.0.0
	 */
	public void setNumber(final SVGGElement g) {
		if(g==null)
			return;

		final String nb = g.getAttribute(g.getUsablePrefix()+SVGAttributes.SVG_ID);
		final String pref = "id"; //$NON-NLS-1$

		if(nb==null)
			shape.setNewId();
		else
			try{ shape.setId((int)Double.parseDouble(nb)); }
			catch(final NumberFormatException e) {
				if(nb.startsWith(pref))
					try { shape.setId((int)Double.parseDouble(nb.substring(pref.length()))); }
					catch(final NumberFormatException e2) { shape.setNewId(); }
				else
					shape.setNewId();
			}
	}




	/**
	 * Applies the set of transformations that concerned the given SVG element to the shape.
	 * @param elt The element that contains the SVG transformation list.
	 * @since 2.0.0
	 */
	public void applyTransformations(final SVGElement elt) {
		if(elt==null)
			return ;

		// The list of the transformations that are applied on the element.
		SVGTransformList tl = elt.getWholeTransform();

		for(int i = tl.size()-1; i>=0; i--)
			applyTransformation(tl.get(i));
	}




	/**
	 * Applies an SVG transformation on the shape.
	 * @param t The SVG transformation to apply.
	 * @since 2.0.0
	 */
	public void applyTransformation(final SVGTransform t) {
		if(t!=null)
			switch(t.getType()) {
				case SVGTransform.SVG_TRANSFORM_ROTATE:
					getShape().rotate(DrawingTK.getFactory().createPoint(t.getMatrix().getE(), t.getMatrix().getF()), Math.toRadians(t.getRotationAngle()));
					break;

				case SVGTransform.SVG_TRANSFORM_SCALE:
					break;

				case SVGTransform.SVG_TRANSFORM_TRANSLATE:
					getShape().translate(t.getTX(), t.getTY());
					break;

				default:
					BadaboomCollector.INSTANCE.add(new IllegalArgumentException("Bad transformation type: " + t.getType())); //$NON-NLS-1$
			}
	}



	/**
	 * When the arrows are read from an SVG document, we need to set the parameters of the first
	 * arrow to the second arrow and vise et versa; because the arrows of a shape share the same
	 * parameters. This method carries out this job.
	 * @param ah1 The first arrow.
	 * @param ah2 The second arrow.
	 * @since 2.0.0
	 */
	public void homogeniseArrows(final IArrow ah1, final IArrow ah2) {
		if(ah1==null || ah2==null)
			return ;

		homogeniseArrowFrom(ah1, ah2);
		homogeniseArrowFrom(ah2, ah1);
	}




	protected void setSVGArrow(final SVGElement parent, final int arrowPos, final boolean isShadow, final SVGDocument doc, final SVGDefsElement defs) {
		final IArrow arrow = shape.getArrowAt(arrowPos);

		if(arrow.getArrowStyle()!=ArrowStyle.NONE) {
			String arrowName 	= "arrow" + arrowPos + (isShadow ? "Shad-" : "-") + shape.getId();
			SVGElement arrowSVG = new LArrowSVGGenerator(arrow).toSVG(doc, isShadow);

			arrowSVG.setAttribute(SVGAttributes.SVG_ID, arrowName);
			defs.appendChild(arrowSVG);
			parent.setAttribute(arrowPos==0 ? SVGAttributes.SVG_MARKER_START : SVGAttributes.SVG_MARKER_END, SVG_URL_TOKEN_BEGIN + arrowName + ')');
		}
	}



	/**
	 * Copies the parameters of the first arrow to the second arrow (only
	 * the parameters of the current style are copied).
	 * @see #homogeniseArrows(IArrow, IArrow)
	 * @param source The arrow that will be copied.
	 * @param target The arrow that will be set.
	 * @since 2.0.0
	 */
	protected void homogeniseArrowFrom(final IArrow source, final IArrow target) {
		if(source==null || target==null)
			return ;

		final ArrowStyle style = source.getArrowStyle();

		if(style!=null && style!=ArrowStyle.NONE)
			if(style.isBar()) {
				target.setTBarSizeDim(source.getTBarSizeDim());
				target.setTBarSizeNum(source.getTBarSizeNum());
			} else if(style.isArrow()) {
				target.setArrowInset(source.getArrowInset());
				target.setArrowLength(source.getArrowLength());
				target.setArrowSizeDim(source.getArrowSizeDim());
				target.setArrowSizeNum(target.getArrowSizeNum());
			} else if(style.isRoundBracket()) {
				target.setRBracketNum(source.getRBracketNum());
				target.setTBarSizeDim(source.getTBarSizeDim());
				target.setTBarSizeNum(source.getTBarSizeNum());
			} else if(style.isSquareBracket()) {
				target.setBracketNum(source.getBracketNum());
				target.setTBarSizeDim(source.getTBarSizeDim());
				target.setTBarSizeNum(source.getTBarSizeNum());
			} else {
				target.setDotSizeDim(source.getDotSizeDim());
				target.setDotSizeNum(source.getDotSizeNum());
			}
	}



	/**
	 * Sets the shadow parameters of the figure by using an SVG element having "type:shadow".
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected void setSVGShadowParameters(final SVGElement elt) {
		if(elt==null || !shape.isShadowable())
			return ;

		final String fill = elt.getFill();

		if(fill!=null && !fill.equals(SVGAttributes.SVG_VALUE_NONE) && !fill.startsWith(SVG_URL_TOKEN_BEGIN))
			shape.setShadowCol(CSSColors.INSTANCE.getRGBColour(fill));

		final SVGTransformList tl = elt.getTransform();
		SVGTransform t;
		double tx, ty;
		boolean sSize = false, sAngle = false;

		for(int i=0, size=tl.size(); i<size && (!sSize || !sAngle); i++ ) {
			t = tl.get(i);

			if(t.isTranslation()) {
				tx = t.getTX();
				ty = t.getTY();

				if(LNumber.INSTANCE.equals(ty, 0.) && !sSize) { // It is shadowSize.
					shape.setShadowSize(tx);
					sSize = true;
				}
				else {
					final IPoint gravityCenter 	= shape.getGravityCentre();
					double angle 				= Double.NaN;
					double shSize 				= shape.getShadowSize();

					if(LNumber.INSTANCE.equals(ty, 0.))
						angle = tx<0. ? Math.PI : 0.;
					else
						if(LNumber.INSTANCE.equals(shSize, Math.abs(tx)))
							angle = ty>0. ? -Math.PI/2. : Math.PI/2.;
						else {
							angle = Math.acos(gravityCenter.distance(gravityCenter.getX()+tx+shSize, gravityCenter.getY())/
												gravityCenter.distance(gravityCenter.getX()+tx+shSize, gravityCenter.getY()+ty));

							if(tx+shSize<0) {
								if(ty<0.)
									angle = Math.PI - angle;
								else
									angle += Math.PI;
							}
							else
								if(ty>0.)
									angle *= -1;
						}

					shape.setShadowAngle(angle);
					sAngle = true;
				}
			}
		}

		shape.setHasShadow(true);
	}



	/**
	 * Sets the double borders parameters of the figure by using an SVG element.
	 * @param elt The SVG element.
	 * @since 2.0.0
	 */
	protected void setSVGDbleBordersParameters(final SVGElement elt) {
		if(elt==null)
			return ;

		shape.setDbleBordSep(elt.getStrokeWidth());
		shape.setDbleBordCol(elt.getStroke());
		shape.setThickness((shape.getThickness()-shape.getDbleBordSep())/2.);
		shape.setHasDbleBord(true);
	}




	protected void setSVGLatexdrawParameters(final SVGElement elt) {
		if(elt==null)
			return ;

		if(shape.isBordersMovable()) {
			final String bp = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_BORDERS_POS);

			if(bp!=null)
				shape.setBordersPosition(BorderPos.getStyle(bp));
		}
	}



	/**
	 * Sets the global parameters of the figure by using an SVG element.
	 * @param elt The SVG element.
	 * @since 2.0.0
	 */
	protected void setSVGParameters(final SVGElement elt) {
		if(elt==null)
			return ;

		if(shape.isThicknessable())
			shape.setThickness(elt.getStrokeWidth());

		if(shape.isBordersMovable()) {
			final String bp = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_BORDERS_POS);

			if(bp!=null)
				shape.setBordersPosition(BorderPos.getStyle(bp));
		}

		shape.setLineColour(elt.getStroke());

		if(shape.isLineStylable())
			LShapeSVGGenerator.setDashedDotted(shape, elt.getStrokeDasharray(), elt.getStrokeLinecap());

		if(shape.isFillable())
			LShapeSVGGenerator.setFill(shape, elt.getFill(), elt.getSVGRoot().getDefs());

		CSSStylesGenerator.INSTANCE.setCSSStyles(shape, elt.getStylesCSS(), elt.getSVGRoot().getDefs());
	}



	/**
	 * Sets the given arrow head using the SVG arrow with the ID arrowID
	 * @param ah The arrow head to set.
	 * @param arrowID The SVG ID of the SVG arrow head.
	 * @param elt An element of the SVG document (useful to get the defs of the document).
	 * @since 2.0.0
	 */
	protected void setSVGArrow(final IArrow ah, final String arrowID, final SVGElement elt, final String svgMarker) {
		if(ah==null || arrowID==null || elt==null)
			return ;

		final LArrowSVGGenerator arrGen = new LArrowSVGGenerator(ah);
		arrGen.setArrow((SVGMarkerElement)elt.getDef(new URIReferenceParser(arrowID).getURI()), getShape(), svgMarker);
	}




	/**
	 * @param elt The source <code>g</code> element.
	 * @param type The type of the latexdraw element (double borders, shadow, main), if null, the main element is returned.
	 * @return The Researched element.
	 * @since 2.0.0
	 */
	protected SVGElement getLaTeXDrawElement(final SVGGElement elt, final String type) {
		if(elt==null)
			return null;

		final NodeList nl 	= elt.getChildNodes();
		int i				= 0;
		final int size 		= nl.getLength();
		Node ltdElt			= null;
		Node n;
		final String bis 	= elt.lookupPrefixUsable(LNamespace.LATEXDRAW_NAMESPACE_URI);

		if(type==null)
			while(i<size && ltdElt==null) {
				n = nl.item(i);

				if(((SVGElement)n).getAttribute(bis+LNamespace.XML_TYPE)==null)
					ltdElt = n;
				else
					i++;
			}
		else
			while(i<size && ltdElt==null) {
				n = nl.item(i);

				if(type.equals(((SVGElement)n).getAttribute(bis+LNamespace.XML_TYPE)))
					ltdElt = n;
				else
					i++;
			}

		return (SVGElement)ltdElt;
	}



	/**
	 * Sets the thickness of the given figure with the given SVG stroke-width.
	 * @param shape The figure to set.
	 * @param strokeWidth The SVG stroke-width to convert.
	 * @param stroke The stroke.
	 * @since 2.0.0
	 */
	public static void setThickness(final IShape shape, final String strokeWidth, final String stroke) {
		if(shape==null || strokeWidth==null)
			return;

		if(stroke==null || !stroke.equals(SVGAttributes.SVG_VALUE_NONE))
			try{ shape.setThickness(new SVGLengthParser(strokeWidth).parseLength().getValue()); }
			catch(final ParseException e){ BadaboomCollector.INSTANCE.add(e); }
	}



	/**
	 * Creates an SVG element from the current latexdraw shape.
	 * @param doc The SVG document.
	 * @return The created SVGElement or null.
	 * @since 2.0.0
	 */
	public abstract SVGElement toSVG(final SVGDocument doc);





	/**
	 * @param elt Rotates the SVG element.
	 * @exception IllegalArgumentException If elt is null.
	 * @since 2.0.0
	 */
	protected void setSVGRotationAttribute(final Element elt) {
		if(elt==null)
			throw new IllegalArgumentException();

		final double rotationAngle = shape.getRotationAngle();
		final IPoint gravityCenter = shape.getGravityCentre();

		if(!LNumber.INSTANCE.equals(rotationAngle%(2*PI), 0.)) {
			final double gcx = gravityCenter.getX();
			final double gcy = gravityCenter.getY();
			final double x   = -Math.cos(-rotationAngle) * gcx + Math.sin(-rotationAngle) * gcy + gcx;
			final double y   = -Math.sin(-rotationAngle) * gcx - Math.cos(-rotationAngle) * gcy + gcy;
			String transfo   = elt.getAttribute(SVGAttributes.SVG_TRANSFORM);
			final String rotation = SVGTransform.createRotation(toDegrees(rotationAngle), 0., 0.) + " " +//$NON-NLS-1$
							  SVGTransform.createTranslation(-x, -y);

			if(transfo!=null && transfo.length()>0)
				transfo = rotation + " " + transfo; //$NON-NLS-1$
			else
				transfo = rotation;

			elt.setAttribute(SVGAttributes.SVG_TRANSFORM, transfo);
		}
	}




	/**
	 * Sets the double borders parameters to the given SVG element if the current figure has double borders.
	 * @param elt The element to set.
	 * @exception IllegalArgumentException If elt is null.
	 * @since 2.0.0
	 */
	protected void setSVGDoubleBordersAttributes(final Element elt) {
		if(elt==null)
			throw new IllegalArgumentException();

		if(shape.hasDbleBord()) {
			elt.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getDbleBordCol(), true));
			elt.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getDbleBordSep()));
			elt.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
			elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_DBLE_BORDERS);
		}
	}



	/**
	 *
	 * @param elt The element to set if the current figure has a shadow.
	 * @param shadowFills True if a shadow must fill the figure.
	 * @exception IllegalArgumentException If elt is null.
	 * @since 2.0.0
	 */
	protected void setSVGShadowAttributes(final Element elt, final boolean shadowFills) {
		if(elt==null)
			throw new IllegalArgumentException();

		if(shape.hasShadow()) {
			final IPoint gravityCenter 	= shape.getGravityCentre();
			final double gcx 			= gravityCenter.getX();
			final double gcy 			= gravityCenter.getY();
			IPoint pt 			 		= DrawingTK.getFactory().createPoint(
										gcx+shape.getShadowSize(), gcy).rotatePoint(shape.getGravityCentre(), -shape.getShadowAngle());

			elt.setAttribute(SVGAttributes.SVG_TRANSFORM,
				SVGTransform.createTranslation(shape.getShadowSize(), 0.) + " " + //$NON-NLS-1$
				SVGTransform.createTranslation(pt.getX()-gcx-shape.getShadowSize(), pt.getY()-gcy));
			elt.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.hasDbleBord() ?
								shape.getThickness()*2+shape.getDbleBordSep() : shape.getThickness()));
			elt.setAttribute(SVGAttributes.SVG_FILL, shadowFills || shape.isFilled() ? CSSColors.INSTANCE.getColorName(shape.getShadowCol(), true) : SVGAttributes.SVG_VALUE_NONE);
			elt.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getShadowCol(), true));
			elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_SHADOW);
		}
	}



	/**
	 * Sets the SVG attribute of the current figure.
	 * @param doc The original document with which, all the elements will be created.
	 * @param root The root element of the document.
	 * @param shadowFills True if a shadow must fill the figure.
	 * @throws IllegalArgumentException If the root or the "defs" part of the document is null.
	 * @since 2.0.0
	 */
	protected void setSVGAttributes(final SVGDocument doc, final SVGElement root, final boolean shadowFills) {
		if(root==null || doc.getFirstChild().getDefs()==null)
			throw new IllegalArgumentException();

		final SVGDefsElement defs 	= doc.getFirstChild().getDefs();
		final FillingStyle fillStyle= shape.getFillingStyle();

		// Setting the position of the borders.
		if(shape.isBordersMovable())
	        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE +':'+ LNamespace.XML_BORDERS_POS, shape.getBordersPosition().getLatexToken());

		// Setting the thickness of the borders.
		if(shape.isThicknessable()) {
			LShapeSVGGenerator.setThickness(root, shape.getThickness(), shape.hasDbleBord(), shape.getDbleBordSep());
			root.setStroke(shape.getLineColour());
		}

		// Setting the filling properties.
		if(shape.isFillable())
			if((shape.isFilled() || (shape.hasShadow() && shadowFills)) && !shape.hasHatchings() && !shape.hasGradient())
				root.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(shape.getFillingCol(), true));
			else
				// Setting the filling colour.
				if(fillStyle==FillingStyle.NONE)
		        	root.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
		        else
		        	// Setting the gradient properties.
		        	if(fillStyle==FillingStyle.GRAD) {
		        		final SVGElement grad	= new SVGLinearGradientElement(doc);
		        		SVGStopElement stop;
		        		final String id 		= SVGElements.SVG_LINEAR_GRADIENT + shape.getId();
		        		final double gradMidPt 	= shape.getGradAngle()>PI || (shape.getGradMidPt()<0 && shape.getGradMidPt()>-PI)?
		        								1-shape.getGradMidPt() : shape.getGradMidPt();

		        		grad.setAttribute(SVGAttributes.SVG_ID, id);

		        		if(!LNumber.INSTANCE.equals(shape.getGradAngle()%(2*PI), PI/2.)) {
		        			final Point2D.Float p1 = new Point2D.Float(), p2 = new Point2D.Float();

		        			getGradientPoints(p1, p2, true);

		            		grad.setAttribute(SVGAttributes.SVG_X1, String.valueOf(p1.x));
		            		grad.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(p1.y));
		            		grad.setAttribute(SVGAttributes.SVG_X2, String.valueOf(p2.x));
		            		grad.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(p2.y));
		            		grad.setAttribute(SVGAttributes.SVG_GRADIENT_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		        		}

		        		// Setting the middle point of the gradient and its colours.
		        		if(!LNumber.INSTANCE.equals(gradMidPt, 0.)) {
			        		stop = new SVGStopElement(doc);
			        		stop.setAttribute(SVGAttributes.SVG_OFFSET, "0");//$NON-NLS-1$
			        		stop.setAttribute(SVGAttributes.SVG_STOP_COLOR, CSSColors.INSTANCE.getColorName(shape.getGradColStart(), true));
			        		grad.appendChild(stop);
		        		}

		        		stop = new SVGStopElement(doc);
		        		stop.setAttribute(SVGAttributes.SVG_OFFSET, String.valueOf(gradMidPt));
		        		stop.setAttribute(SVGAttributes.SVG_STOP_COLOR, CSSColors.INSTANCE.getColorName(shape.getGradColEnd(), true));
		        		grad.appendChild(stop);

		        		if(!LNumber.INSTANCE.equals(gradMidPt, 1.)) {
		            		stop = new SVGStopElement(doc);
		            		stop.setAttribute(SVGAttributes.SVG_OFFSET, "1");//$NON-NLS-1$
		            		stop.setAttribute(SVGAttributes.SVG_STOP_COLOR, CSSColors.INSTANCE.getColorName(shape.getGradColStart(), true));
		            		grad.appendChild(stop);
		        		}

		        		defs.appendChild(grad);
		        		root.setAttribute(SVGAttributes.SVG_FILL, SVG_URL_TOKEN_BEGIN + id + ')');
		        	}
		        	else {
		        		// Setting the hatchings.
		        		if(shape.hasHatchings()) {
		        			final String id 		= SVGElements.SVG_PATTERN + shape.getId();
		        			final SVGPatternElement hatch = new SVGPatternElement(doc);
		        			final SVGGElement gPath = new SVGGElement(doc);
		        			final IPoint max 		= shape.getFullBottomRightPoint();
		        			final SVGPathElement path = new SVGPathElement(doc);

		        			root.setAttribute(SVGAttributes.SVG_FILL, SVG_URL_TOKEN_BEGIN + id + ')');
		        			hatch.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, shape.getFillingStyle().getLatexToken());
		        			hatch.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ROTATION, String.valueOf(shape.getHatchingsAngle()));
		        			hatch.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_SIZE, String.valueOf(shape.getHatchingsSep()));
		        			hatch.setAttribute(SVGAttributes.SVG_PATTERN_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		        			hatch.setAttribute(SVGAttributes.SVG_ID, id);
		        			hatch.setAttribute(SVGAttributes.SVG_X, "0"); //$NON-NLS-1$
		        			hatch.setAttribute(SVGAttributes.SVG_Y, "0"); //$NON-NLS-1$
		        			hatch.setAttribute(SVGAttributes.SVG_WIDTH,  String.valueOf((int)max.getX()));
		        			hatch.setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf((int)max.getY()));
		        			gPath.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getHatchingsCol(), true));
		        			gPath.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getHatchingsWidth()));
		        			gPath.setAttribute(SVGAttributes.SVG_STROKE_DASHARRAY, SVGAttributes.SVG_VALUE_NONE);

		        			path.setAttribute(SVGAttributes.SVG_D, getSVGHatchingsPath().toString());
	        				gPath.appendChild(path);

	        				// Several shapes having hatching must have their shadow filled.
		        			if(shape.isFilled() || (shape.hasShadow() && shadowFills)) {
		        				SVGRectElement fill = new SVGRectElement(doc);
		        				fill.setAttribute(SVGAttributes.SVG_FILL,   CSSColors.INSTANCE.getColorName(shape.getFillingCol(), true));
		        				fill.setAttribute(SVGAttributes.SVG_STROKE, SVGAttributes.SVG_VALUE_NONE);
		        				fill.setAttribute(SVGAttributes.SVG_WIDTH,  String.valueOf((int)max.getX()));
		        				fill.setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf((int)max.getY()));

		        				hatch.appendChild(fill);
		        			}

		        			defs.appendChild(hatch);
		        			hatch.appendChild(gPath);
		        		}
		        	}//else

		if(shape.isLineStylable())
	        LShapeSVGGenerator.setDashedDotted(root, shape.getDashSepBlack(), shape.getDashSepWhite(), shape.getDotSep(),
	        		shape.getLineStyle().getLatexToken(), shape.hasDbleBord(), shape.getThickness(), shape.getDbleBordSep());
	}



	/**
	 * @return The path of the hatchings of the shape.
	 * @since 2.0.0
	 */
	public SVGPathSegList getSVGHatchingsPath() {
		final SVGPathSegList path = new SVGPathSegList();

		if(!shape.hasHatchings())
			return path;

		final String hatchingStyle 	= shape.getFillingStyle().getLatexToken();
		final double hatchingAngle 	= shape.getHatchingsAngle();
		final IRectangle bound 		= DrawingTK.getFactory().createRectangle(shape.getFullTopLeftPoint(), shape.getFullBottomRightPoint(), false);

		if(hatchingStyle.equals(PSTricksConstants.TOKEN_FILL_VLINES) ||
			hatchingStyle.equals(PSTricksConstants.TOKEN_FILL_VLINES_F))
			getSVGHatchingsPath2(path, hatchingAngle, bound);
			else
				if(hatchingStyle.equals(PSTricksConstants.TOKEN_FILL_HLINES) ||
				   hatchingStyle.equals(PSTricksConstants.TOKEN_FILL_HLINES_F))
					getSVGHatchingsPath2(path, hatchingAngle>0?hatchingAngle-Math.PI/2.:hatchingAngle+Math.PI/2., bound);
			else
				if(hatchingStyle.equals(PSTricksConstants.TOKEN_FILL_CROSSHATCH) ||
				   hatchingStyle.equals(PSTricksConstants.TOKEN_FILL_CROSSHATCH_F)) {
					getSVGHatchingsPath2(path, hatchingAngle, bound);
					getSVGHatchingsPath2(path, hatchingAngle>0?hatchingAngle-Math.PI/2.:hatchingAngle+Math.PI/2., bound);
				}

		return path;
	}


	/**
	 * @see LShapeSVGGenerator#getSVGHatchingsPath()
	 */
	private void getSVGHatchingsPath2(final SVGPathSegList path, final double hAngle, final IRectangle bound) {
		if(path==null || bound==null)
			return;

		double angle2  				= hAngle%(Math.PI*2.);
		final double halphPI  		= Math.PI/2.;
		final double hatchingWidth 	= shape.getHatchingsWidth();
		final double hatchingSep   	= shape.getHatchingsSep();
		final IPoint nw 			= bound.getTopLeftPoint();
		final IPoint se 			= bound.getBottomRightPoint();
		final double nwx 			= nw.getX();
		final double nwy 			= nw.getY();
		final double sex 			= se.getX();
		final double sey 			= se.getY();

		// Computing the angle.
		if(angle2>0.) {
			if(angle2>3.*halphPI)
				angle2 = angle2-Math.PI*2.;
			else
				if(angle2>halphPI)
					angle2 = angle2-Math.PI;
		}
		else
			if(angle2<-3.*halphPI)
				angle2 = angle2+Math.PI*2.;
			else
				if(angle2<-halphPI)
					angle2 = angle2+Math.PI;

		final double val = hatchingWidth+hatchingSep;

		if(LNumber.INSTANCE.equals(angle2, 0.))
			// Drawing the hatchings vertically.
			for(double x = nwx; x<sex; x+=val) {
				path.add(new SVGPathSegMoveto(x, nwy, false));
				path.add(new SVGPathSegLineto(x, sey, false));
			}
		else
			// Drawing the hatchings horizontally.
			if(LNumber.INSTANCE.equals(angle2, halphPI) || LNumber.INSTANCE.equals(angle2, -halphPI))
				for(double y = nwy; y<sey; y+=val) {
					path.add(new SVGPathSegMoveto(nwx, y, false));
					path.add(new SVGPathSegLineto(sex, y, false));
				}
			else {
				// Drawing the hatchings by rotation.
				final double incX = val/Math.cos(angle2);
				final double incY = val/Math.sin(angle2);
				double maxX;
				double y1, x2, y2, x1;

				if(angle2>0.) {
					y1 	 = nwy;
					maxX = sex + (sey-(nwy<0?nwy:0)) * Math.tan(angle2);
				}
				else  {
					y1 	 = sey;
					maxX = sex - sey * Math.tan(angle2);
				}

				x1 = nwx;
				x2 = x1;
				y2 = y1;

				if(incX<0. || LNumber.INSTANCE.equals(incX, 0.))
					return ;

				while(x2 < maxX) {
					x2 += incX;
					y1 += incY;
					path.add(new SVGPathSegMoveto(x1, y1, false));
					path.add(new SVGPathSegLineto(x2, y2, false));
				}
			}
	}



	/**
	 * Gets the points needed to the gradient definition. The given points must not be null, there value will be set in the method.
	 * @param p1 The first point to set.
	 * @param p2 The second point to set.
	 * @param ignoreMidPt True, gradientMidPt will be ignored.
	 * @throws IllegalArgumentException If p1 or p2 is null.
	 * @since 2.0.0
	 */
	protected void getGradientPoints(final Point2D.Float p1, final Point2D.Float p2, final boolean ignoreMidPt) {
		if(p1==null || p2==null)
			throw new IllegalArgumentException();

		try {
			final IShapeFactory	factory = DrawingTK.getFactory();
			final IPoint nw 	= shape.getTopLeftPoint();
			final IPoint se 	= shape.getBottomRightPoint();
			final double nwx 	= nw.getX();
			final double nwy 	= nw.getY();
			final double sex 	= se.getX();
			final double sey 	= se.getY();
			IPoint pt1 			= factory.createPoint((nwx+sex)/2., nwy);
			IPoint pt2 			= factory.createPoint((nwx+sex)/2., sey);
			double angle 		= shape.getGradAngle()%(2*PI);
			double gradMidPt 	= shape.getGradMidPt();

			// Transforming the negative angle in a positive angle.
			if(angle<0.)
				angle = 2*PI + angle;

			if(angle>PI || LNumber.INSTANCE.equals(angle, PI)) {
				gradMidPt 	= 1 - shape.getGradMidPt();
				angle 		= angle-PI;
			}

			if(!LNumber.INSTANCE.equals(angle, 0.)) {
				if(LNumber.INSTANCE.equals(angle%(PI/2.), 0.)) {
					// The gradient is horizontal.
					pt1 = factory.createPoint(nwx, (nwy+sey)/2.);
					pt2 = factory.createPoint(sex, (nwy+sey)/2.);

					if(!ignoreMidPt && gradMidPt<0.5)
						pt1.setX(pt2.getX() - Point2D.distance(pt2.getX(), pt2.getY(), sex,(nwy+sey)/2.));

					pt2.setX(nwx+(sex-nwx)*(ignoreMidPt ? 1 : gradMidPt));
				}
				else {
					IPoint cg = shape.getGravityCentre();
					ILine l2, l;

					pt1 = pt1.rotatePoint(cg, -angle);
					pt2 = pt2.rotatePoint(cg, -angle);
					l 	= factory.createLine(pt1, pt2);

					if(LNumber.INSTANCE.equals(angle, 0.) && angle>0. && angle<(PI/2.))
						 l2 = l.getPerpendicularLine(nw);
					else l2 = l.getPerpendicularLine(factory.createPoint(nwx,sey));

					pt1 			= l.getIntersection(l2);
					double distance = Point2D.distance(cg.getX(), cg.getY(), pt1.getX(), pt1.getY());
					l.setP1(pt1);
					IPoint[] pts 	= l.findPoints(pt1, 2*distance*(ignoreMidPt ? 1 : gradMidPt));
					pt2 = pts[0];

					if(!ignoreMidPt && gradMidPt<0.5)
						pt1 = pt1.rotatePoint(cg, PI);
				}
			}//if(angle!=0)
			else {
				// The gradient is vertical.
				if(!ignoreMidPt && gradMidPt<0.5)
					pt1.setY(pt2.getY() - Point2D.distance(pt2.getX(), pt2.getY(), (nwx+sex)/2.,se.getY()));

				pt2.setY(nwy+(sey-nwy)*(ignoreMidPt ? 1 : gradMidPt));
			}

			p1.setLocation(pt1.getX(), pt1.getY());
			p2.setLocation(pt2.getX(), pt2.getY());

		}catch(final Exception e) {
			p1.setLocation(0f, 0f);
			p2.setLocation(0f, 0f);
		}
	}



	/**
	 * Sets the colour of the line of the shape with the given SVG stroke.
	 * @param shape The shape to set.
	 * @param stoke The stroke of the shape.
	 * @since 2.0.0
	 */
	public static void setLineColour(final IShape shape, final String stoke) {
		if(shape!=null && stoke!=null)
			shape.setLineColour(CSSColors.INSTANCE.getRGBColour(stoke));
	}



	/**
	 * Sets the fill properties to the given figure.
	 * @param shape The figure to set.
	 * @param fill The fill properties
	 * @param defs The definition that may be useful to the the fill properties (url), may be null.
	 * @since 2.0.0
	 */
	public static void setFill(final IShape shape, final String fill, final SVGDefsElement defs) {
		if(fill==null || shape==null || fill.equals(SVGAttributes.SVG_VALUE_NONE))
			return ;

		// Getting the url to the SVG symbol of the filling.
		if(fill.startsWith(SVG_URL_TOKEN_BEGIN) && fill.endsWith(")") && defs!=null) { //$NON-NLS-1$
			final String uri 		= fill.substring(5, fill.length()-1);
			final SVGElement def 	= defs.getDef(uri);

			// A pattern means hatchings.
			if(def instanceof SVGPatternElement) {
				final SVGPatternElement pat = (SVGPatternElement)def;
				final Color c 				= pat.getBackgroundColor();
				final String str 			= pat.getAttribute(pat.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_TYPE);
				double angle;
				double sep;
				double width;
				String attr;

				try { angle = Double.parseDouble(pat.getAttribute(pat.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+ LNamespace.XML_ROTATION));
				}catch(final Exception e) { angle = 0.; }

				attr = pat.getAttribute(pat.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_SIZE);

				if(attr==null)
					sep = pat.getHatchingSep();
				else
					try { sep = Double.parseDouble(attr); }
					catch(final Exception e) { sep = 0.; }

				if(PSTricksConstants.isValidFillStyle(str))
					shape.setFillingStyle(FillingStyle.getStyleFromLatex(str));

				if(!Double.isNaN(angle))
					shape.setHatchingsAngle(angle);

				shape.setFilled(c!=null);
				shape.setFillingCol(c);
				shape.setHatchingsCol(pat.getHatchingColor());

				if(!Double.isNaN(sep))
					shape.setHatchingsSep(sep);

				width = pat.getHatchingStrokeWidth();

				if(!Double.isNaN(width))
					shape.setHatchingsWidth(width);
			}
			else
				// A linear gradient means a gradient.
				if(def instanceof SVGLinearGradientElement) {
					final SVGLinearGradientElement grad = (SVGLinearGradientElement)def;

					shape.setGradColStart(grad.getStartColor());
					shape.setGradColEnd(grad.getEndColor());
					shape.setFillingStyle(FillingStyle.getStyle(PSTricksConstants.TOKEN_FILL_GRADIENT));
					shape.setGradMidPt(grad.getMiddlePoint());
					shape.setGradAngle(grad.getAngle());
				}
		}
		else {
			// Just getting the filling colour.
			final Color c = CSSColors.INSTANCE.getRGBColour(fill);

			if(c!=null) {
				shape.setFillingCol(c);
				shape.setFilled(true);
			}
		}
	}



	/**
	 * Sets the figure properties concerning the line properties.
	 * @param shape The figure to set.
	 * @param dashArray The dash array SVG property.
	 * @param linecap The line cap SVG property.
	 * @since 2.0.0
	 */
	public static void setDashedDotted(final IShape shape, final String dashArray, final String linecap) {
		if(shape==null || dashArray==null)
			return ;

		if(!dashArray.equals(SVGAttributes.SVG_VALUE_NONE))
			if(linecap!=null && SVGAttributes.SVG_LINECAP_VALUE_ROUND.equals(linecap))
				shape.setLineStyle(LineStyle.DOTTED);
			else
				shape.setLineStyle(LineStyle.DASHED);
	}



	/**
	 * Sets the given thickness to the given SVG element.
	 * @param elt The element to set.
	 * @param thickness The thickness to set.
	 * @param hasDoubleBorders True: the shape has double borders and it must be considered during the computation of the thickness.
	 * @param doubleSep The size of the double borders.
	 * @since 3.0
	 */
	public static void setThickness(final SVGElement elt, final double thickness, final boolean hasDoubleBorders, final double doubleSep) {
		if(elt==null)
			return ;

		elt.setStrokeWidth(hasDoubleBorders ? thickness*2. + doubleSep : thickness);
	}



	/**
	 * Sets the line style to the given SVG element.
	 * @param elt The element to set.
	 * @param blackDash The black dash of the line for dashed line style.
	 * @param whiteDash The white dash of the line for dashed line style.
	 * @param dotSep The separation between dots for dotted line style.
	 * @param lineStyle The line style to set to the SVG element.
	 * @param hasDoubleBorders True: the shape has double borders.
	 * @param thickness The thickness to set to the element.
	 * @param doubleSep The size of the double borders.
	 * @since 3.0
	 */
	public static void setDashedDotted(final Element elt, final double blackDash, final double whiteDash, final double dotSep, final String lineStyle,
										final boolean hasDoubleBorders, final double thickness, final double doubleSep) {
		if(elt==null)
			return ;

        if(lineStyle.equals(PSTricksConstants.LINE_DASHED_STYLE))
        	elt.setAttribute(SVGAttributes.SVG_STROKE_DASHARRAY, blackDash + ", " + whiteDash);//$NON-NLS-1$
        else
        	if(lineStyle.equals(PSTricksConstants.LINE_DOTTED_STYLE)) {
        		elt.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_ROUND);
        		elt.setAttribute(SVGAttributes.SVG_STROKE_DASHARRAY, 1 + ", " + //$NON-NLS-1$
        						(dotSep + (hasDoubleBorders ? thickness*2f + doubleSep : thickness)));
	        }
	}




	/**
	 * If a figure can move its border, we have to compute the difference between the PSTricks shape and the SVG shape.
	 * @return The gap computed with the border position, the thickness and the double boundary. Or NaN if the shape cannot move
	 * its border.
	 * @since 2.0.0
	 */
	protected double getPositionGap() {
		double gap;

        if(!shape.isBordersMovable() || shape.getBordersPosition().getLatexToken().equals(PSTricksConstants.BORDERS_MIDDLE))
        	gap = 0.;
        else
        	if(shape.getBordersPosition().getLatexToken().equals(PSTricksConstants.BORDERS_INSIDE))
        		gap = -shape.getThickness() - (shape.hasDbleBord() ? shape.getDbleBordSep() + shape.getThickness() : 0.) ;
        	else
        		gap = shape.getThickness() + (shape.hasDbleBord() ? shape.getDbleBordSep() + shape.getThickness() : 0.);

        return gap;
	}



	/**
	 * Creates a line with the style of the 'show points' option.
	 * @param doc The document owner.
	 * @param thickness The thickness of the line to create.
	 * @param col The colour of the line.
	 * @param p1 The first point of the line.
	 * @param p2 The second point of the line.
	 * @param blackDash The black dash interval.
	 * @param whiteDash The white dash interval.
	 * @param hasDble Defines if the shape had double borders.
	 * @param dotSep The dot interval.
	 * @return The created SVG line or null.
	 * @since 2.0.0
	 */
	protected static SVGLineElement getShowPointsLine(final SVGDocument doc, final double thickness, final Color col,
									final IPoint p1, final IPoint p2, final double blackDash, final double whiteDash,
									final boolean hasDble, final double dotSep, final double doubleSep) {
		if(doc==null)
			return null;

		final SVGLineElement line = new SVGLineElement(doc);

		if(p1!=null) {
			line.setX1(p1.getX());
			line.setY1(p1.getY());
		}

		if(p2!=null) {
			line.setX2(p2.getX());
			line.setY2(p2.getY());
		}

		line.setStrokeWidth(thickness);
		line.setStroke(col);
		LShapeSVGGenerator.setDashedDotted(line, blackDash, whiteDash, dotSep,
											PSTricksConstants.LINE_DASHED_STYLE, hasDble, thickness, doubleSep);

		return line;
	}


	/**
	 * When a shape has a shadow and is filled, the background of its borders must be filled with the
	 * colour of the interior of the shape. This method does not test if it must be done, it sets a
	 * SVG element which carries out that.
	 * @param elt The element that will be set to define the background of the borders.
	 * @param root The root element to which 'elt' will be appended.
	 * @since 2.0.0
	 */
	protected void setSVGBorderBackground(final SVGElement elt, final SVGElement root) {
		if(elt==null || root==null)
			return ;

		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_BG);
        elt.setFill(shape.getFillingCol());
        elt.setStroke(shape.getFillingCol());
        setThickness(elt, shape.getThickness(), shape.isDbleBorderable(), shape.getDbleBordSep());
        root.appendChild(elt);
	}



	/**
	 * Creates an SVG circle that represents a dot for the option 'show points'.
	 * @param doc The document owner.
	 * @param rad The radius of the circle.
	 * @param pt The position of the point.
	 * @param col The colour of the dot.
	 * @return The created dot or null.
	 * @since 2.0.0
	 */
	protected static SVGCircleElement getShowPointsDot(final SVGDocument doc, final double rad, final IPoint pt, final Color col) {
		if(doc==null)
			return null;

		final SVGCircleElement circle = new SVGCircleElement(doc);

		circle.setR(rad);

		if(pt!=null) {
			circle.setCx(pt.getX());
			circle.setCy(pt.getY());
		}

		circle.setFill(col);

		return circle;
	}


	/**
	 * @return the shape.
	 * @since 2.0.0
	 */
	public S getShape() {
		return shape;
	}
}
