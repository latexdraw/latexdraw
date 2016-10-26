package net.sf.latexdraw.models.impl

import net.sf.latexdraw.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.PlotStyle
import java.util.stream.Collectors
import net.sf.latexdraw.models.interfaces.shape.IShape

private[impl] trait LPlotGroup extends IGroup {
  private def firstPlot = plotShapes.stream.filter{_.isTypeOf(classOf[IPlotProp])}.findFirst

  private def plotShapes =
  	  getShapes.stream.filter{_.isInstanceOf[IPlotProp]}.map[IShape with IPlotProp]{_.asInstanceOf[IShape with IPlotProp]}.collect(Collectors.toList())

	override def getYScale = if(firstPlot.isPresent) firstPlot.get.getYScale else Double.NaN

	override def setScale(s:Double) {
	  plotShapes.forEach(_.setScale(s))
	}

	override def setYScale(sy:Double) {
	  plotShapes.forEach(_.setYScale(sy))
	}

	override def getXScale = if(firstPlot.isPresent) firstPlot.get.getXScale else Double.NaN

	override def setXScale(sx:Double) {
	  plotShapes.forEach(_.setXScale(sx))
	}

	override def isPolar = if(firstPlot.isPresent) firstPlot.get.isPolar else false

	override def setPolar(polar:Boolean) {
	  plotShapes.forEach(_.setPolar(polar))
	}

	override def getPlotStyle = if(firstPlot.isPresent) firstPlot.get.getPlotStyle() else PlotStyle.CURVE

	override def setPlotStyle(style:PlotStyle) {
	  plotShapes.forEach(_.setPlotStyle(style))
	}

	override def getPlotEquation = if(firstPlot.isPresent) firstPlot.get.getPlotEquation else ""

	override def setPlotEquation(eq:String) {
	  plotShapes.forEach(_.setPlotEquation(eq))
	}

	override def getNbPlottedPoints = if(firstPlot.isPresent) firstPlot.get.getNbPlottedPoints else 0

	override def setNbPlottedPoints(nb:Int) {
	  plotShapes.forEach(_.setNbPlottedPoints(nb))
	}

	override def getPlotMinX = if(firstPlot.isPresent) firstPlot.get.getPlotMinX else Double.NaN

	override def setPlotMinX(minX:Double) {
	  plotShapes.forEach(_.setPlotMinX(minX))
	}

	override def getPlotMaxX = if(firstPlot.isPresent) firstPlot.get.getPlotMaxX else Double.NaN

	override def setPlotMaxX(maxX:Double) {
	  plotShapes.forEach(_.setPlotMaxX(maxX))
	}

	override def getPlottingStep = if(firstPlot.isPresent) firstPlot.get.getPlottingStep else 0
}
