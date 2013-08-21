package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.views.CreateViewCmd;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;

/**
 * Creates SVG elements based on latexdraw.<br>
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
 * 09/21/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class SVGShapesFactory {
	/** The singleton. */
	public static final SVGShapesFactory INSTANCE = new SVGShapesFactory();

	/** The chain of responsibility used to reduce the complexity of the factory. */
	private CreateViewSVGCmd createCmd;

	/**
	 * Creates the factory.
	 */
	private SVGShapesFactory() {
		super();
		initCommands();
	}


	/**
	 * Creates an SVG Element corresponding to the given shape.
	 * @param shape The shape used to determine which SVG element to create.
	 * @param doc The SVG document used to instantiate to SVG element.
	 * @return The created SVG element.
	 */
	public SVGElement createSVGElement(final IShape shape, final SVGDocument doc) {
		return shape==null || doc==null ? null : createCmd.execute(shape, doc);
	}


	/**
	 * Initialises the chain of responsibility.
	 */
	private void initCommands() {
		CreateViewSVGCmd cmd = new CreateViewSVGCmd(null, IPicture.class) { @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LPictureSVGGenerator((IPicture)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IText.class) 		{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LTextSVGGenerator((IText)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IFreehand.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LFreeHandSVGGenerator((IFreehand)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IDot.class) 		{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LDotSVGGenerator((IDot)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IGrid.class)		{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LGridSVGGenerator((IGrid)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IAxes.class)		{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LAxeSVGGenerator((IAxes)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IBezierCurve.class){ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LBezierCurveSVGGenerator((IBezierCurve)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IPolygon.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LPolygonSVGGenerator((IPolygon)shape).toSVG(doc); } };
		// All the commands of the chain of responsibility are chained together.
		cmd = new CreateViewSVGCmd(cmd, IPolyline.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LPolylinesSVGGenerator((IPolyline)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IRhombus.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LRhombusSVGGenerator((IRhombus)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, ITriangle.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LTriangleSVGGenerator((ITriangle)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IGroup.class) 		{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LGroupSVGGenerator((IGroup)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IEllipse.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LEllipseSVGGenerator<>((IEllipse)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, ICircleArc.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LCircleArcSVGGenerator((ICircleArc)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, ICircle.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LCircleSVGGenerator((ICircle)shape).toSVG(doc); } };
		cmd = new CreateViewSVGCmd(cmd, IRectangle.class) 	{ @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LRectangleSVGGenerator((IRectangle)shape).toSVG(doc); } };
		// The last created command is the first element of the chain.
		createCmd = new CreateViewSVGCmd(cmd, ISquare.class) { @Override public SVGElement create(final IShape shape, final SVGDocument doc) { return new LSquareSVGGenerator((ISquare)shape).toSVG(doc); } };
	}


	/**
	 * This class is a mix of the design patterns Command and Chain of responsibility.
	 * The goal is to find the command which can create the view of the given shape.
	 */
	private abstract class CreateViewSVGCmd extends CreateViewCmd<IShape, SVGElement, CreateViewSVGCmd> {
		/**
		 * Creates the command.
		 * @param next The next command in the chain of responsibility. Can be null.
		 * @param classShape The type of the shape supported by the command.
		 * @since 3.0
		 */
		public CreateViewSVGCmd(final CreateViewSVGCmd next, final Class<? extends IShape> classShape) {
			super(next, classShape);
		}

		@Override
		public SVGElement create(final IShape shape) {
			return create(shape, null);
		}


		/**
		 * Creates an instance of the view corresponding to the given shape.
		 * @param shape The model used to create the view.
		 * @param doc The SVG document used to create elements.
		 * @return The created view.
		 * @since 3.0
		 */
		public abstract SVGElement create(final IShape shape, final SVGDocument doc);


		/**
		 * Launches the creation process.
		 * @param model The model used to create the view.
		 * @param doc The SVG document used to create elements.
		 * @return The created view or null.
		 * @since 3.0
		 */
		public SVGElement execute(final IShape model, final SVGDocument doc) {
			SVGElement view;

			if(clazz.isInstance(model))
				view = create(model, doc);
			else
				view = next==null ? null : next.execute(model, doc);

			return view;
		}
	}
}
