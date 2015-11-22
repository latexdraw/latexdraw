package net.sf.latexdraw.glib.models.impl

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.ArrowStyle
import net.sf.latexdraw.glib.models.interfaces.shape.IArrowableShape
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

/**
 * Implementation of IArrowableShape
 * @author Arnaud Blouin
 */
private[impl] trait LArrowableShape extends IArrowableShape {
	val arrows : Buffer[IArrow] = new ArrayBuffer[IArrow]()

	override def copy(sh:IShape) {
		if(getClass.isInstance(sh)) {
			val arr = sh.asInstanceOf[IArrowableShape]
			arrows.clear
			for(i <- 0 until arr.getNbArrows)
				arrows += ShapeFactory.createArrow(arr.getArrowAt(i), this)
		}else {
			sh match {
				case arr:IArrowableShape =>
					setArrowStyle(arr.getArrowStyle(0), 0)
					setArrowStyle(arr.getArrowStyle(-1), -1)
					setArrowInset(arr.getArrowInset)
					setArrowLength(arr.getArrowLength)
					setArrowSizeDim(arr.getArrowSizeDim)
					setArrowSizeNum(arr.getArrowSizeNum)
					setDotSizeDim(arr.getDotSizeDim)
					setDotSizeNum(arr.getDotSizeNum)
					setBracketNum(arr.getBracketNum)
					setRBracketNum(arr.getRBracketNum)
					setTBarSizeDim(arr.getTBarSizeDim)
					setTBarSizeNum(arr.getTBarSizeNum)
				case _ =>
			}
		}
	}

	override def getArrowAt(position:Int) : IArrow = {// TODO use Option
		if(arrows.isEmpty || position>=arrows.size || position < -1)
			return null
		if(position == -1)
			return arrows.last
		return arrows(position)
	}

	override def getArrowIndex(arrow:IArrow) = arrows.indexOf(arrow)

	override def getNbArrows = arrows.size

	override def setDotSizeDim(dotSizeDim:Double) {
		arrows.foreach{_.setDotSizeDim(dotSizeDim)}
	}

	override def setDotSizeNum(dotSizeNum:Double) {
		arrows.foreach{_.setDotSizeNum(dotSizeNum)}
	}

	override def setTBarSizeNum(tbarSizeNum:Double) {
		arrows.foreach{_.setTBarSizeNum(tbarSizeNum)}
	}

	override def setTBarSizeDim(tbarSizeDim:Double) {
		arrows.foreach{_.setTBarSizeDim(tbarSizeDim)}
	}

	override def getTBarSizeDim = if(arrows.isEmpty) Double.NaN else arrows(0).getTBarSizeDim

	override def getTBarSizeNum = if(arrows.isEmpty) Double.NaN else arrows(0).getTBarSizeNum

	override def setRBracketNum(rBracketNum:Double) {
		arrows.foreach{_.setRBracketNum(rBracketNum)}
	}

	override def setBracketNum(bracketNum:Double) {
		arrows.foreach{_.setBracketNum(bracketNum)}
	}

	override def setArrowLength(lgth:Double) {
		arrows.foreach{_.setArrowLength(lgth)}
	}

	override def setArrowSizeDim(arrowSizeDim:Double) {
		arrows.foreach{_.setArrowSizeDim(arrowSizeDim)}
	}

	override def setArrowSizeNum(arrowSizeNum:Double) {
		arrows.foreach{_.setArrowSizeNum(arrowSizeNum)}
	}

	override def setArrowInset(inset:Double) {
		arrows.foreach{_.setArrowInset(inset)}
	}

	override def getDotSizeDim = if(arrows.isEmpty) Double.NaN else arrows(0).getDotSizeDim

	override def getDotSizeNum = if(arrows.isEmpty) Double.NaN else arrows(0).getDotSizeNum

	override def getBracketNum = if(arrows.isEmpty) Double.NaN else arrows(0).getBracketNum

	override def getArrowSizeNum = if(arrows.isEmpty) Double.NaN else arrows(0).getArrowSizeNum

	override def getArrowSizeDim = if(arrows.isEmpty) Double.NaN else arrows(0).getArrowSizeDim

	override def getArrowInset = if(arrows.isEmpty) Double.NaN else arrows(0).getArrowInset

	override def getArrowLength = if(arrows.isEmpty) Double.NaN else arrows(0).getArrowLength

	override def getRBracketNum = if(arrows.isEmpty) Double.NaN else arrows(0).getRBracketNum

	override def setArrowStyle(style:ArrowStyle, position:Int) {
		if(style==null) return
		getArrowAt(position) match {
			case arrow:IArrow => arrow.setArrowStyle(style)
			case _ =>
		}
	}

	override def getArrowStyle(position:Int) : ArrowStyle = {
		getArrowAt(position) match {
			case arrow:IArrow => arrow.getArrowStyle
			case _ => ArrowStyle.NONE
		}
	}
}