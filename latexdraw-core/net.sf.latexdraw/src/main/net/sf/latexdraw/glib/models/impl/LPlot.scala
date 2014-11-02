package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.parsers.ps.PSFunctionParser
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import java.awt.Color

/**
 * Implementation of the plotted function.
 * @since 3.2
 * @author Arnaud Blouin
 */
private[impl] class LPlot(pt:IPoint, var minX:Double, var maxX:Double, var equation:String, var polar:Boolean) extends LPositionShape(pt) with IPlot with LScalable {
	private var nbPoints:Int = 50
	private var style:IPlotProp.PlotStyle = IPlotProp.PlotStyle.CURVE
	private var parser:PSFunctionParser = new PSFunctionParser(equation)
	
	private var dotStyle = IDotProp.DotStyle.DOT
	private var dotDiametre = PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM*IShape.PPC+PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM

	require(GLibUtilities.isValidPoint(pt) && minX<maxX && GLibUtilities.isValidPoint(minX, maxX), "Parameter not valid: " + minX + " " + maxX + " " +GLibUtilities.isValidPoint(pt))


	override def setPlotStyle(style:IPlotProp.PlotStyle) {
		if(style!=null) this.style = style
	}

	override def getPlotStyle = style

	override def setNbPlottedPoints(nbPts:Int) {
		if(nbPts>1 && GLibUtilities.isValidCoordinate(nbPts))
			nbPoints = nbPts
	}

	override def isShowPtsable = true
	override def isThicknessable = true
	override def isShadowable = true
	override def isLineStylable = true
	override def isInteriorStylable = true
	override def isFillable = true
	override def isDbleBorderable = true

	override def getPlottingStep = (maxX-minX)/(nbPoints-1)

	override def getTopLeftPoint = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+minX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.min*IShape.PPC*yscale)
	}

	override def getBottomRightPoint = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+maxX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.max*IShape.PPC*yscale)
	}

	override def getTopRightPoint = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+maxX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.min*IShape.PPC*yscale)
	}

	override def getBottomLeftPoint = {
		val step = getPlottingStep
		val pos = getPosition
		ShapeFactory.createPoint(pos.getX+minX*IShape.PPC*xscale, pos.getY+(minX to maxX by step).map{x=>getY(x)}.max*IShape.PPC*yscale)
	}

	override def getPosition = getPtAt(0)

	override def getNbPlottedPoints = nbPoints

	override def getY(x:Double) = parser.getY(x)

	override def getPlotEquation = equation

	override def setPlotEquation(eq:String) {
		if(eq!=null) {
			equation = eq
      parser = new PSFunctionParser(equation)
		}
	}

	override def getPlotMinX = minX

	override def getPlotMaxX = maxX

	override def setPolar(pol:Boolean) {
		polar = pol
	}

	override def isPolar = polar

	override def setPlotMaxX(x:Double) {
		if(GLibUtilities.isValidCoordinate(x) && x>minX)
			maxX = x
	}

	override def setPlotMinX(x:Double) {
		if(GLibUtilities.isValidCoordinate(x) && x<maxX)
			minX = x
	}
	
	
	def getDiametre(): Double = dotDiametre

  def getDotFillingCol(): Color = if(isFillable) super.getFillingCol else Color.BLACK

  def getDotStyle(): IDotProp.DotStyle = dotStyle

  def setDiametre(diam: Double) {
  	if(diam>0.0) dotDiametre = diam
  }

  def setDotFillingCol(col: Color) {
  	setFillingCol(col)
  }

  def setDotStyle(dotst: IDotProp.DotStyle) {
  	if(dotStyle!=null) dotStyle = dotst
  }
}