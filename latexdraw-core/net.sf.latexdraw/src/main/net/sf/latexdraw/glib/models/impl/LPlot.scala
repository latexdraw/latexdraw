package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.parsers.ps.PSFunctionParser
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

/**
 * Implementation of the plotted function.
 * @since 3.2
 * @author Arnaud Blouin
 */
private[impl] class LPlot(uniqueID:Boolean, pt:IPoint, var minX:Double, var maxX:Double, var equation:String) extends LPositionShape(uniqueID, pt) with IPlot with LScalable {
	private var nbPoints:Int = 50
	private var style:IPlotProp.PlotStyle = IPlotProp.PlotStyle.LINE
	private var parser:PSFunctionParser = new PSFunctionParser(equation)

	require(GLibUtilities.isValidPoint(pt) && minX<maxX && GLibUtilities.isValidPoint(minX, maxX))


	override def setPlotStyle(style:IPlotProp.PlotStyle) {
		if(style!=null) this.style = style
	}

	override def getPlotStyle() = style

	override def setNbPlottedPoints(nbPts:Int) {
		if(nbPts>1 && GLibUtilities.isValidCoordinate(nbPts))
			nbPoints = nbPts
	}

	override def isShowPtsable() = true

	override def getPlottingStep() = (maxX-minX)/(nbPoints-1)

	override def getTopLeftPoint() = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+minX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.min*IShape.PPC*yscale)
	}

	override def getBottomRightPoint() = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+maxX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.max*IShape.PPC*yscale)
	}

	override def getTopRightPoint() = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+maxX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.min*IShape.PPC*yscale)
	}

	override def getBottomLeftPoint() = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+minX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.max*IShape.PPC*yscale)
	}

	override def getPosition() = getPtAt(0)

	override def getNbPlottedPoints = nbPoints

	override def getY(x:Double) = parser.getY(x)

	override def getEquation() = equation

	override def setEquation(eq:String) {
		if(eq!=null) {
			equation = eq;
			parser = new PSFunctionParser(equation)
		}
	}

	override def getMinX() = minX

	override def getMaxX() = maxX

	override def setMaxX(x:Double) {
		if(GLibUtilities.isValidCoordinate(x) && x>minX)
			maxX = x
	}

	override def setMinX(x:Double) {
		if(GLibUtilities.isValidCoordinate(x) && x<maxX)
			minX = x
	}
}