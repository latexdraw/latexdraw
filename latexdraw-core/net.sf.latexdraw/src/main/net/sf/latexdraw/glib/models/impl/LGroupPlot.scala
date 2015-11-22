package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.PlotStyle

private[impl] trait LPlotGroup extends IGroup {
  private def firstPlot = plotShapes.find{_.isTypeOf(classOf[IPlotProp])}

  private def plotShapes = getShapes.flatMap{case x:IPlotProp => x::Nil; case _ => Nil}

	override def getYScale: Double = {
		firstPlot match {
			case Some(la) => la.getYScale
			case _ => Double.NaN
		}
	}

	override def setScale(s:Double) {
	  plotShapes.foreach(_.setScale(s))
	}

	override def setYScale(sy:Double) {
	  plotShapes.foreach(_.setYScale(sy))
	}

	override def getXScale: Double = {
		firstPlot match {
			case Some(la) => la.getXScale
			case _ => Double.NaN
		}
	}

	override def setXScale(sx:Double) {
	  plotShapes.foreach(_.setXScale(sx))
	}

	override def isPolar:Boolean= {
		firstPlot match {
			case Some(sh) => sh.isPolar
			case _ => false
		}
	}

	override def setPolar(polar:Boolean) {
	  plotShapes.foreach(_.setPolar(polar))
	}

	override def getPlotStyle: PlotStyle = {
		firstPlot match {
			case Some(la) => la.getPlotStyle
			case _ => PlotStyle.CURVE
		}
	}

	override def setPlotStyle(style:PlotStyle) {
	  plotShapes.foreach(_.setPlotStyle(style))
	}

	override def getPlotEquation: String = {
		firstPlot match {
			case Some(la) => la.getPlotEquation
			case _ => ""
		}
	}

	override def setPlotEquation(eq:String) {
	  plotShapes.foreach(_.setPlotEquation(eq))
	}

	override def getNbPlottedPoints: Int = {
		firstPlot match {
			case Some(la) => la.getNbPlottedPoints
			case _ => 0
		}
	}

	override def setNbPlottedPoints(nb:Int) {
	  plotShapes.foreach(_.setNbPlottedPoints(nb))
	}

	override def getPlotMinX: Double = {
		firstPlot match {
			case Some(la) => la.getPlotMinX()
			case _ => java.lang.Double.NaN
		}
	}

	override def setPlotMinX(minX:Double) {
	  plotShapes.foreach(_.setPlotMinX(minX))
	}

	override def getPlotMaxX: Double = {
		firstPlot match {
			case Some(la) => la.getPlotMaxX()
			case _ => java.lang.Double.NaN
		}
	}

	override def setPlotMaxX(maxX:Double) {
	  plotShapes.foreach(_.setPlotMaxX(maxX))
	}

	override def getPlottingStep: Double = {
		firstPlot match {
			case Some(la) => la.getPlottingStep()
			case _ => 0
		}
	}
}
