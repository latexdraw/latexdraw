/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.malai.undo.Undoable;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


/**
 * This action aligns the provided shapes.
 */
public class AlignShapes extends ShapeActionImpl<IGroup> implements Undoable, Modifying {
	/**
	 * This enumeration describes the different possible alignment types.
	 */
	public enum AlignmentType {
		left, right, top, bottom, midHoriz, midVert;
	}

	/** The reference border that must bounds the shapes to align. */
	Rectangle2D border;

	/** The views corresponding to the shapes to align. */
	final List<ViewShape<?, ?>> views;

	/** The alignment to perform. */
	AlignmentType alignment;

	/** The former positions of the shapes to align. Used for undoing. */
	final List<IPoint> oldPositions;


	public AlignShapes() {
		super();
		views = new ArrayList<>();
		oldPositions = new ArrayList<>();
	}

	@Override
	protected void doActionBody() {
		//		var v : IViewShape = null
		//		shape.get.getShapes.foreach{sh=>
		//			// Because the views have already computed the border of their shape, we get the corresponding views.
		//			_views += MappingRegistry.REGISTRY.getTargetFromSource(sh, classOf[IViewShape])
		//			// Saving the old position of the shape for undoing.
		//			_oldPositions += sh.getTopLeftPoint
		//		}
		//		redo
	}

	/**
	 * Middle-horizontal aligning the provided shapes.
	 */
	protected void alignMidHoriz() {
		//		var theMaxY = Double.MinValue
		//		var theMinY = Double.MaxValue
		//		var middles = new ListBuffer[Double]()
		//		var i = 0
		//
		//		_views.foreach{view=>
		//			val maxY = view.getBorder.getMaxY
		//			val minY = view.getBorder.getMinY
		//			if(maxY>theMaxY) theMaxY = maxY
		//			if(minY<theMinY) theMinY = minY
		//			middles+=(minY+maxY)/2
		//		}
		//
		//		val middle = (theMaxY+theMinY)/2
		//
		//		shape.get.getShapes.foreach{sh=>
		//			val middle2 = middles(i)
		//			if(!LNumber.equalsDouble(middle2, middle))
		//				sh.translate(0, middle-middle2)
		//			i+=1
		//		}
	}


	/**
	 * Middle-vertical aligning the provided shapes.
	 */
	protected void alignMidVert() {
		//		var theMaxX = Double.MinValue
		//		var theMinX = Double.MaxValue
		//		var middles = new ListBuffer[Double]()
		//		var i = 0
		//
		//		_views.foreach{view=>
		//			val maxX = view.getBorder.getMaxX
		//			val minX = view.getBorder.getMinX
		//			if(maxX>theMaxX) theMaxX = maxX
		//			if(minX<theMinX) theMinX = minX
		//			middles+=(minX+maxX)/2
		//		}
		//
		//		val middle = (theMaxX+theMinX)/2
		//
		//		shape.get.getShapes.foreach{sh=>
		//			val middle2 = middles(i)
		//			if(!LNumber.equalsDouble(middle2, middle))
		//				sh.translate(middle-middle2, 0)
		//			i+=1
		//		}
	}


	/**
	 * Bottom aligning the provided shapes.
	 */
	protected void alignBottom() {
		//		var theMaxY = Double.MinValue
		//		var ys = new ListBuffer[Double]()
		//		var i = 0
		//
		//		_views.foreach{view=>
		//			val maxY = view.getBorder.getMaxY
		//			if(maxY>theMaxY)
		//				theMaxY = maxY
		//				ys+=maxY
		//		}
		//
		//		shape.get.getShapes.foreach{sh=>
		//		val y = ys(i)
		//		if(!LNumber.equalsDouble(y, theMaxY))
		//			sh.translate(0, theMaxY-y)
		//			i+=1
		//		}
	}


	/**
	 * Top aligning the provided shapes.
	 */
	protected void alignTop() {
		//		var theMinY = Double.MaxValue
		//		var ys = new ListBuffer[Double]()
		//		var i = 0
		//
		//		_views.foreach{view=>
		//			val minY = view.getBorder.getMinY
		//			if(minY<theMinY)
		//				theMinY = minY
		//			ys+=minY
		//		}
		//
		//		shape.get.getShapes.foreach{sh=>
		//			val y = ys(i)
		//			if(!LNumber.equalsDouble(y, theMinY))
		//				sh.translate(0, theMinY-y)
		//			i+=1
		//		}
	}


	/**
	 * Right aligning the provided shapes.
	 */
	protected void alignRight() {
		//		var theMaxX = Double.MinValue
		//		var xs = new ListBuffer[Double]()
		//		var i = 0
		//
		//		_views.foreach{view=>
		//			val maxX = view.getBorder.getMaxX
		//			if(maxX>theMaxX)
		//				theMaxX = maxX
		//			xs+=maxX
		//		}
		//
		//		shape.get.getShapes.foreach{sh=>
		//			val x = xs(i)
		//			if(!LNumber.equalsDouble(x, theMaxX))
		//				sh.translate(theMaxX-x, 0)
		//			i+=1
		//		}
	}


	/**
	 * Left aligning the provided shapes.
	 */
	protected void alignLeft() {
		//		var theMinX = Double.MaxValue
		//		var xs = new ListBuffer[Double]()
		//		var i = 0
		//
		//		_views.foreach{view=>
		//			val minX = view.getBorder.getMinX
		//			if(minX<theMinX)
		//				theMinX = minX
		//			xs+=minX
		//		}
		//
		//		shape.get.getShapes.foreach{sh=>
		//			val x = xs(i)
		//			if(!LNumber.equalsDouble(x, theMinX))
		//				sh.translate(theMinX-x, 0)
		//			i+=1
		//		}
	}


	@Override
	public boolean canDo() {
		return shape.isPresent() && !shape.get().isEmpty() && border != null && alignment != null;
	}


	@Override
	public void undo() {
		final IntegerProperty pos = new SimpleIntegerProperty(0);

		shape.ifPresent(gp -> {
			gp.getShapes().forEach(sh -> {
				// Reusing the old position.
				IPoint pt = sh.getTopLeftPoint();
				IPoint oldPt = oldPositions.get(pos.get());
				if(!pt.equals(oldPt)) sh.translate(oldPt.getX() - pt.getX(), oldPt.getY() - pt.getY());
				pos.set(pos.get() + 1);
			});
			gp.setModified(true);
		});
	}


	@Override
	public void redo() {
		switch(alignment) {
			case left:
				alignLeft();
				break;
			case right:
				alignRight();
				break;
			case top:
				alignTop();
				break;
			case bottom:
				alignBottom();
				break;
			case midHoriz:
				alignMidHoriz();
				break;
			case midVert:
				alignMidVert();
				break;
		}

		shape.ifPresent(sh -> sh.setModified(true));
	}

	/**
	 * Sets the alignment to perform.
	 */
	public void setAlignment(final AlignmentType align) {
		alignment = align;
	}

	/**
	 * Sets the reference border that must bounds the shapes to align.
	 */
	public void setBorder(final Rectangle2D rec) {
		border = rec;
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.30");
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	public void flush() {
		super.flush();
		views.clear();
		oldPositions.clear();
	}
}
