package net.sf.latexdraw.actions.shape

import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.IShape
import java.awt.geom.Rectangle2D
import org.malai.mapping.MappingRegistry
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape
import net.sf.latexdraw.glib.models.interfaces.IGroup
import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.util.LNumber
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * This enumeration describes the different possible alignment types.
 */
object AlignmentType extends Enumeration {
	type AlignmentType = Value
	val left, right, top, bottom, midHoriz, midVert = Value
}

import AlignmentType._

/**
 * This action aligns the provided shapes.<br>
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
 * 2013-30-03<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class AlignShapes extends Action with ShapeAction[IGroup] with Undoable with Modifying {
	/** The reference border that must bounds the shapes to align. */
	var _border : Rectangle2D = null

	/** The views corresponding to the shapes to align. */
	val _views = new ListBuffer[IViewShape]()

	/** The alignment to perform. */
	var _alignment : AlignmentType = null

	/** The former positions of the shapes to align. Used for undoing. */
	val _oldPositions = new ListBuffer[IPoint]()


	override protected def doActionBody() {
		var v : IViewShape = null
		shape.get.getShapes.foreach{sh=>
			// Because the views have already computed the border of their shape, we get the corresponding views.
			_views += MappingRegistry.REGISTRY.getTargetFromSource(sh, classOf[IViewShape])
			// Saving the old position of the shape for undoing.
			_oldPositions += sh.getTopLeftPoint
		}
		redo
	}

	/**
	 * Middle-horizontal aligning the provided shapes.
	 */
	protected def alignMidHoriz() {
		var theMaxY = Double.MinValue
		var theMinY = Double.MaxValue
		var middles = new ListBuffer[Double]()
		var i = 0

		_views.foreach{view=>
			val maxY = view.getBorder.getMaxY
			val minY = view.getBorder.getMinY
			if(maxY>theMaxY) theMaxY = maxY
			if(minY<theMinY) theMinY = minY
			middles+=(minY+maxY)/2
		}

		val middle = (theMaxY+theMinY)/2

		shape.get.getShapes.foreach{sh=>
			val middle2 = middles(i)
			if(!LNumber.INSTANCE.equals(middle2, middle))
				sh.translate(0, middle-middle2)
			i+=1
		}
	}


	/**
	 * Middle-vertical aligning the provided shapes.
	 */
	protected def alignMidVert() {
		var theMaxX = Double.MinValue
		var theMinX = Double.MaxValue
		var middles = new ListBuffer[Double]()
		var i = 0

		_views.foreach{view=>
			val maxX = view.getBorder.getMaxX
			val minX = view.getBorder.getMinX
			if(maxX>theMaxX) theMaxX = maxX
			if(minX<theMinX) theMinX = minX
			middles+=(minX+maxX)/2
		}

		val middle = (theMaxX+theMinX)/2

		shape.get.getShapes.foreach{sh=>
			val middle2 = middles(i)
			if(!LNumber.INSTANCE.equals(middle2, middle))
				sh.translate(middle-middle2, 0)
			i+=1
		}
	}



	/**
	 * Bottom aligning the provided shapes.
	 */
	protected def alignBottom() {
		var theMaxY = Double.MinValue
		var ys = new ListBuffer[Double]()
		var i = 0

		_views.foreach{view=>
			val maxY = view.getBorder.getMaxY
			if(maxY>theMaxY)
				theMaxY = maxY
				ys+=maxY
		}

		shape.get.getShapes.foreach{sh=>
		val y = ys(i)
		if(!LNumber.INSTANCE.equals(y, theMaxY))
			sh.translate(0, theMaxY-y)
			i+=1
		}
	}


	/**
	 * Top aligning the provided shapes.
	 */
	protected def alignTop() {
		var theMinY = Double.MaxValue
		var ys = new ListBuffer[Double]()
		var i = 0

		_views.foreach{view=>
			val minY = view.getBorder.getMinY
			if(minY<theMinY)
				theMinY = minY
			ys+=minY
		}

		shape.get.getShapes.foreach{sh=>
			val y = ys(i)
			if(!LNumber.INSTANCE.equals(y, theMinY))
				sh.translate(0, theMinY-y)
			i+=1
		}
	}


	/**
	 * Right aligning the provided shapes.
	 */
	protected def alignRight() {
		var theMaxX = Double.MinValue
		var xs = new ListBuffer[Double]()
		var i = 0

		_views.foreach{view=>
			val maxX = view.getBorder.getMaxX
			if(maxX>theMaxX)
				theMaxX = maxX
			xs+=maxX
		}

		shape.get.getShapes.foreach{sh=>
			val x = xs(i)
			if(!LNumber.INSTANCE.equals(x, theMaxX))
				sh.translate(theMaxX-x, 0)
			i+=1
		}
	}


	/**
	 * Left aligning the provided shapes.
	 */
	protected def alignLeft() {
		var theMinX = Double.MaxValue
		var xs = new ListBuffer[Double]()
		var i = 0

		_views.foreach{view=>
			val minX = view.getBorder.getMinX
			if(minX<theMinX)
				theMinX = minX
			xs+=minX
		}

		shape.get.getShapes.foreach{sh=>
			val x = xs(i)
			if(!LNumber.INSTANCE.equals(x, theMinX))
				sh.translate(theMinX-x, 0)
			i+=1
		}
	}


	override def canDo = _shape.isDefined && !_shape.get.isEmpty && _border!=null && _alignment!=null


	override def undo() {
		var pt : IPoint = null
		var pos : Int = 0
		var oldPt : IPoint = null

		shape.get.getShapes.foreach{sh=>
			// Reusing the old position.
			pt = sh.getTopLeftPoint
			oldPt = _oldPositions(pos)
			if(!pt.equals(oldPt))
				sh.translate(oldPt.getX-pt.getX, oldPt.getY-pt.getY)
			pos += 1
		}
		shape.get.setModified(true)
	}


	override def redo() {
		_alignment match {
			case AlignmentType.left => alignLeft
			case AlignmentType.right => alignRight
			case AlignmentType.top => alignTop
			case AlignmentType.bottom => alignBottom
			case AlignmentType.midHoriz => alignMidHoriz
			case AlignmentType.midVert => alignMidVert
		}

		shape.get.setModified(true)
	}

	/**
	 * Sets the alignment to perform.
	 */
	def setAlignment(align:AlignmentType) {
		_alignment = align
	}

	/**
	 * Sets the reference border that must bounds the shapes to align.
	 */
	def setBorder(rec:Rectangle2D) {
		_border = rec
	}

	override def getUndoName() = "align"

	override def isRegisterable()  = true

	override def flush() {
		super.flush
		_views.clear
		_oldPositions.clear
	}
}
