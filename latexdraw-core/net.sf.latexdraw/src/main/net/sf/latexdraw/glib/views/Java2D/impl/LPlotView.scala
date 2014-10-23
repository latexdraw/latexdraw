package net.sf.latexdraw.glib.views.Java2D.impl

import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.Rectangle2D

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewPlot

/**
 * The graphical representation of a plotted function.
 * @since 3.2
 * @author Arnaud Blouin
 */
class LPlotView(model:IPlot) extends LShapeView[IPlot](model) with IViewPlot {
	private var lineView:LPolylineView = _
	private var curveView:LBezierCurveView = _

	update


	override def paint(g:Graphics2D, clip:Rectangle) {
		model.getPlotStyle match {
			case IPlotProp.PlotStyle.LINE => lineView.paint(g, clip)
			case IPlotProp.PlotStyle.CURVE => curveView.paint(g, clip)
			case IPlotProp.PlotStyle.ECURVE => curveView.paint(g, clip)
			case IPlotProp.PlotStyle.CCURVE => curveView.paint(g, clip)
			case _ =>
		}
	}

	override def updatePath() {
		val minX = model.getPlotMinX
		val maxX = model.getPlotMaxX
		val step = model.getPlottingStep
		val posX = model.getPosition.getX
		val posY = model.getPosition.getY

		model.getPlotStyle match {
			case IPlotProp.PlotStyle.LINE => updateLine(posX, posY, minX, maxX, step)
			case IPlotProp.PlotStyle.CURVE => updateCurve(posX, posY, minX, maxX, step)
			case IPlotProp.PlotStyle.ECURVE => updateCurve(posX, posY, minX+step, maxX-step, step)
			case IPlotProp.PlotStyle.CCURVE => updateCurve(posX, posY, minX, maxX, step)
			case _ =>
		}
	}


	private def fillPoints(sh:IModifiablePointsShape, posX:Double, posY:Double, minX:Double, maxX:Double, step:Double) {
		val xs = model.getXScale
		val ys = model.getYScale

		if(model.isPolar) {
  		for(x <- minX to maxX by step) {
  			val radius = model.getY(x)
  			val angle = Math.toRadians(x)
  			val x1 = Math.toDegrees(radius*Math.cos(angle))
  			val y1 = Math.toDegrees(-radius*Math.sin(angle))
  			sh.addPoint(ShapeFactory.createPoint(x1*xs+posX, y1*xs+posY))
  		}
		}else
  		for(x <- minX to maxX by step)
  				sh.addPoint(ShapeFactory.createPoint(x*IShape.PPC*xs+posX, -model.getY(x)*IShape.PPC*ys+posY))
	}


	private def updateLine(posX:Double, posY:Double, minX:Double, maxX:Double, step:Double) {
		val shape = ShapeFactory.createPolyline()
		shape.copy(model)
		if(lineView!=null) lineView.flush
		lineView = new LPolylineView(shape)
		fillPoints(shape, posX, posY, minX, maxX, step)
		lineView.update
	}


	private def updateCurve(posX:Double, posY:Double, minX:Double, maxX:Double, step:Double) {
		// The algorithm follows this definition:
		//https://stackoverflow.com/questions/15864441/how-to-make-a-line-curve-through-points
		val scale = 0.33
		val shape = ShapeFactory.createBezierCurve()
		if(curveView!=null) curveView.flush
		curveView = new LBezierCurveView(shape)
		fillPoints(shape, posX, posY, minX, maxX, step)
		if(model.getPlotStyle==IPlotProp.PlotStyle.CCURVE)
			shape.setIsClosed(true)
		else shape.setIsClosed(false)
		shape.copy(model)
		var i=0
		val Last = shape.getPoints.size-1

		shape.getPoints.foreach{pt =>
			val p1 = pt
			i match {
				case 0 =>
					val p2 = shape.getPtAt(i+1)
					val tangent = p2.substract(p1)
					val q1 =  p1.add(tangent.zoom(scale))
					shape.setXFirstCtrlPt(q1.getX, i)
					shape.setYFirstCtrlPt(q1.getY, i)
				case Last =>
					val p0 = shape.getPtAt(i-1)
					val tangent = p1.substract(p0)
					val q0 =  p1.substract(tangent.zoom(scale))
					shape.setXFirstCtrlPt(q0.getX, i)
					shape.setYFirstCtrlPt(q0.getY, i)
				case _ =>
					val p0 = shape.getPtAt(i-1)
					val p2 = shape.getPtAt(i+1)
					val tangent = p2.substract(p0).normalise
					val q0 = p1.substract(tangent.zoom(scale*p1.substract(p0).magnitude))
//					val q1 = p1.substract(tangent.zoom(scale*p2.substract(p1).magnitude))
					shape.setXFirstCtrlPt(q0.getX, i)
					shape.setYFirstCtrlPt(q0.getY, i)
//					shape.setXSecondCtrlPt(q1.getX, i)
//					shape.setYSecondCtrlPt(q1.getY, i)
			}
			i+=1
		}
		shape.updateSecondControlPoints
		curveView.update
	}


	override def getBorder:Rectangle2D = {
		model.getPlotStyle match {
			case IPlotProp.PlotStyle.CCURVE => curveView.getBorder
			case IPlotProp.PlotStyle.CURVE => curveView.getBorder
			case IPlotProp.PlotStyle.DOTS => new Rectangle2D.Double
			case IPlotProp.PlotStyle.ECURVE => curveView.getBorder
			case IPlotProp.PlotStyle.LINE => lineView.getBorder
			case IPlotProp.PlotStyle.POLYGON => new Rectangle2D.Double
			case _ => new Rectangle2D.Double
		}
	}

	override def intersects(rec:Rectangle2D):Boolean = {
		model.getPlotStyle match {
			case IPlotProp.PlotStyle.CCURVE => curveView.intersects(rec)
			case IPlotProp.PlotStyle.CURVE => curveView.intersects(rec)
			case IPlotProp.PlotStyle.DOTS => false
			case IPlotProp.PlotStyle.ECURVE => curveView.intersects(rec)
			case IPlotProp.PlotStyle.LINE => lineView.intersects(rec)
			case IPlotProp.PlotStyle.POLYGON => false
			case _ => false
		}
	}


	override def contains(px:Double, py:Double):Boolean = {
		model.getPlotStyle match {
			case IPlotProp.PlotStyle.CCURVE => curveView.contains(px, py)
			case IPlotProp.PlotStyle.CURVE => curveView.contains(px, py)
			case IPlotProp.PlotStyle.DOTS => false
			case IPlotProp.PlotStyle.ECURVE => curveView.contains(px, py)
			case IPlotProp.PlotStyle.LINE => lineView.contains(px, py)
			case IPlotProp.PlotStyle.POLYGON => false
			case _ => false
		}
	}


	override def updateBorder() {
		model.getPlotStyle match {
			case IPlotProp.PlotStyle.CCURVE => curveView.updateBorder
			case IPlotProp.PlotStyle.CURVE => curveView.updateBorder
			case IPlotProp.PlotStyle.DOTS =>
			case IPlotProp.PlotStyle.ECURVE => curveView.updateBorder
			case IPlotProp.PlotStyle.LINE => lineView.updateBorder
			case IPlotProp.PlotStyle.POLYGON =>
			case _ =>
		}
	}


	override def flush() {
		super.flush
		if(lineView!=null) lineView.flush
		if(curveView!=null) curveView.flush
	}

	override def updateDblePathInside(){}
	override def updateDblePathMiddle(){}
	override def updateDblePathOutside(){}
	override def updateGeneralPathInside(){}
	override def updateGeneralPathMiddle(){}
	override def updateGeneralPathOutside(){}
}