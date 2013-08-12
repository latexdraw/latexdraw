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
object DistributionType extends Enumeration {
	type DistributionType = Value
	val vertBot, vertTop, vertMid, vertEq, horizLeft, horizRight, horizMid, horizEq = Value

	def isVertical(distrib:DistributionType) = distrib==vertBot || distrib==vertTop || distrib==vertMid || distrib==vertEq
}

import DistributionType._

/**
 * This action distributes the provided shapes.<br>
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
 * 2013-31-03<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class DistributeShapes extends Action with ShapeAction[IGroup] with Undoable with Modifying {
	/** The reference border that must bounds the shapes to align. */
	var _border : Rectangle2D = null

	/** The views corresponding to the shapes to align. */
	val _views = new ListBuffer[IViewShape]()

	/** The alignment to perform. */
	var _distribution : DistributionType = null

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


	override def canDo = _shape.isDefined && !_shape.get.isEmpty && _border!=null && _distribution!=null


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


	/**
	 * Distributes at equal distance between the shapes.
	 */
	protected def distributeEq() {
		var sortedSh= new ListBuffer[IViewShape]()
		var mins	= new ListBuffer[Double]()
		var maxs	= new ListBuffer[Double]()
		var i : Int = 0
		val shapes = shape.get.getShapes

		_views.foreach{view =>
			val coord = _distribution match {
				case DistributionType.horizEq => view.getBorder.getMinX
				case DistributionType.vertEq => view.getBorder.getMinY
			}

			i = mins.indexWhere{value=> coord<value}

			if(i == -1) {
				sortedSh+=view
				mins+=coord
				if(_distribution==DistributionType.horizEq)
					 maxs+=view.getBorder.getMaxX
				else maxs+=view.getBorder.getMaxY
			}else {
				sortedSh.insert(i, view)
				mins.insert(i, coord)
				if(_distribution==DistributionType.horizEq)
					 maxs.insert(i, view.getBorder.getMaxX)
				else maxs.insert(i, view.getBorder.getMaxY)
			}
		}

		var gap = mins.last - maxs.head

		for(i <- 1 until sortedSh.size-1)
			gap -= maxs(i) - mins(i)

		gap/=(sortedSh.size-1)

		if(DistributionType.isVertical(_distribution))
			for(i <- 1 until sortedSh.size-1) {
				sortedSh(i).getShape.translate(0, (sortedSh(i-1).getBorder.getMaxY + gap) - mins(i))
				sortedSh(i).updateBorder
			}
		else
			for(i <- 1 until sortedSh.size-1) {
				sortedSh(i).getShape.translate((sortedSh(i-1).getBorder.getMaxX + gap) - mins(i), 0)
				sortedSh(i).updateBorder
			}

		sortedSh.clear
		mins.clear
		maxs.clear
	}


	/**
	 * Distributes using bottom/top/left/right reference.
	 */
	protected def distributeNotEq() {
		var sortedSh= new ListBuffer[IViewShape]()
		var centres	= new ListBuffer[Double]()
		var i : Int = 0
		val shapes = shape.get.getShapes

		_views.foreach{view =>
			val x = _distribution match {
				case DistributionType.horizLeft => view.getBorder.getMinX
				case DistributionType.horizMid => view.getBorder.getCenterX
				case DistributionType.horizRight => view.getBorder.getMaxX
				case DistributionType.vertBot => view.getBorder.getMaxY
				case DistributionType.vertMid => view.getBorder.getCenterY
				case DistributionType.vertTop => view.getBorder.getMinY
			}

			i = centres.indexWhere{value=> x<value}

			if(i == -1) {
				sortedSh+=view
				centres+=x
			}else {
				sortedSh.insert(i, view)
				centres.insert(i, x)
			}
		}

		val gap = (centres.last - centres.head)/(_views.size-1)

		if(DistributionType.isVertical(_distribution))
			for(i <- 1 until sortedSh.size-1)
				sortedSh(i).getShape.translate(0, (centres.head+i*gap)-centres(i))
		else
			for(i <- 1 until sortedSh.size-1)
				sortedSh(i).getShape.translate((centres.head+i*gap)-centres(i), 0)

		sortedSh.clear
		centres.clear
	}


	override def redo() {
		if(_distribution==DistributionType.horizEq || _distribution==DistributionType.vertEq)
			distributeEq
		else
			distributeNotEq
		shape.get.setModified(true)
	}


	/**
	 * Sets the alignment to perform.
	 */
	def setDistribution(distribution:DistributionType) {
		_distribution = distribution
	}

	/**
	 * Sets the reference border that must bounds the shapes to align.
	 */
	def setBorder(rec:Rectangle2D) {
		_border = rec
	}

	override def getUndoName() = "distribute"

	override def isRegisterable()  = true

	override def flush() {
		super.flush
		_views.clear
		_oldPositions.clear
	}
}
