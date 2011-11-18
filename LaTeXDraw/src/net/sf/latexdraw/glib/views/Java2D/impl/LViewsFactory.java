package net.sf.latexdraw.glib.views.Java2D.impl;

import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewsFactory;

/**
 * The factory that creates views from given models.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
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
 * 03/10/08<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 */
public class LViewsFactory implements IViewsFactory {
	/** The chain of responsibility used to reduce the complexity of the factory. */
	private CreateViewCmd createCmd;
	
	/**
	 * Creates the factory.
	 */
	public LViewsFactory() {
		super();
		initCommands();
	}
	
	
	@Override
	public IViewShape<?> generateView(final IShape shape) {
		return shape==null ? null : createCmd.execute(shape);
	}
	
	
	/**
	 * Initialises the chain of responsibility.
	 */
	private void initCommands() {
		CreateViewCmd cmd = new CreateViewCmd(null, IPicture.class) { @Override public IViewShape<?> create(final IShape shape) { return new LPictureView((IPicture)shape); } };
		cmd = new CreateViewCmd(cmd, IText.class) 		{ @Override public IViewShape<?> create(final IShape shape) { return new LTextView((IText)shape); } };
		cmd = new CreateViewCmd(cmd, IFreehand.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LFreeHandView((IFreehand)shape); } };
		cmd = new CreateViewCmd(cmd, IDot.class) 		{ @Override public IViewShape<?> create(final IShape shape) { return new LDotView((IDot)shape); } };
		cmd = new CreateViewCmd(cmd, IGrid.class)		{ @Override public IViewShape<?> create(final IShape shape) { return new LGridView((IGrid)shape); } };
		cmd = new CreateViewCmd(cmd, IBezierCurve.class){ @Override public IViewShape<?> create(final IShape shape) { return new LBezierCurveView((IBezierCurve)shape); } };
		cmd = new CreateViewCmd(cmd, IPolygon.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LPolygonView((IPolygon)shape); } };
		// All the commands of the chain of responsibility are chained together.
		cmd = new CreateViewCmd(cmd, IPolyline.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LPolylineView((IPolyline)shape); } };
		cmd = new CreateViewCmd(cmd, IRhombus.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LRhombusView((IRhombus)shape); } };
		cmd = new CreateViewCmd(cmd, ITriangle.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LTriangleView((ITriangle)shape); } };
		cmd = new CreateViewCmd(cmd, IGroup.class) 		{ @Override public IViewShape<?> create(final IShape shape) { return new LGroupView((IGroup)shape); } };
		cmd = new CreateViewCmd(cmd, IEllipse.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LEllipseView<IEllipse>((IEllipse)shape); } };
		cmd = new CreateViewCmd(cmd, IArc.class) 		{ @Override public IViewShape<?> create(final IShape shape) { return new LArcView((IArc)shape); } };
		cmd = new CreateViewCmd(cmd, ICircleArc.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LCircleArcView((ICircleArc)shape); } };
		cmd = new CreateViewCmd(cmd, ICircle.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LCircleView((ICircle)shape); } };
		cmd = new CreateViewCmd(cmd, IRectangle.class) 	{ @Override public IViewShape<?> create(final IShape shape) { return new LRectangleView((IRectangle)shape); } };
		// The last created command is the first element of the chain.
		createCmd = new CreateViewCmd(cmd, ISquare.class) { @Override public IViewShape<?> create(final IShape shape) { return new LSquareView((ISquare)shape); } };
	}
	
	/**
	 * This class is a mix of the design patterns Command and Chain of responsibility.
	 * The goal is to find the command which can create the view of the given shape.
	 */
	private abstract class CreateViewCmd {
		/** The next command. */
		private CreateViewCmd next;
		
		/** The type of the shape supported by the command. */
		private Class<? extends IShape> classShape;
		
		/**
		 * Creates the command.
		 * @param next The next command in the chain of responsibility. Can be null.
		 * @param classShape The type of the shape supported by the command.
		 * @since 3.0
		 */
		public CreateViewCmd(final CreateViewCmd next, final Class<? extends IShape> classShape) {
			super();
			this.next = next;
			this.classShape = classShape;
		}
		
		/**
		 * Launches the creation process.
		 * @param shape The shape used to create the view.
		 * @return The created view or null.
		 * @since 3.0
		 */
		public IViewShape<? extends IShape> execute(final IShape shape) {
			IViewShape<? extends IShape> view;
			
			if(classShape.isInstance(shape))
				view = create(shape);
			else
				view = next==null ? null : next.execute(shape);
			
			return view;
		}
		
		/**
		 * Creates an instance of the view corresponding to the given shape.
		 * @param shape The shape used to create the view.
		 * @return The created view.
		 * @since 3.0
		 */
		protected abstract IViewShape<? extends IShape> create(final IShape shape);
	}
}
