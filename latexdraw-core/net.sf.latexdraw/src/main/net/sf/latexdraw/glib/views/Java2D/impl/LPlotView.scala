package net.sf.latexdraw.glib.views.Java2D.impl

import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import java.awt.Rectangle
import java.awt.Graphics2D
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK
import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape
import scala.collection.JavaConversions.asScalaBuffer

/**
 * The graphical representation of a plotted function.
 * @since 3.2
 * @author Arnaud Blouin
 */
class LPlotView(model:IPlot) extends LShapeView[IPlot](model) {
	private var lineView:LPolylineView = _
	private var curveView:LBezierCurveView = _

	model.setNbPlottedPoints(5)
	model.setPlotStyle(IPlotProp.PlotStyle.CURVE)
	update


	override def paint(g:Graphics2D, clip:Rectangle) {
		model.getPlotStyle match {
			case IPlotProp.PlotStyle.LINE => lineView.paint(g, clip)
			case IPlotProp.PlotStyle.CURVE => curveView.paint(g, clip)
			case _ =>
		}
	}

	override def updatePath() {
		val minX = model.getMinX
		val maxX = model.getMaxX
		val step = (maxX-minX)/model.getNbPlottedPoints
		val posX = model.getPosition.getX
		val posY = model.getPosition.getY

		model.getPlotStyle match {
			case IPlotProp.PlotStyle.LINE => updateLine(posX, posY, minX, maxX, step)
			case IPlotProp.PlotStyle.CURVE => updateCurve(posX, posY, minX, maxX, step)
			case _ =>
		}
	}


	private def fillPoints(sh:IModifiablePointsShape, posX:Double, posY:Double, minX:Double, maxX:Double, step:Double) {
		sh.addPoint(ShapeFactory.createPoint(minX*IShape.PPC+posX, -model.getY(minX)*IShape.PPC+posY))
		for(x <- minX+step to maxX by step)
			sh.addPoint(ShapeFactory.createPoint(x*IShape.PPC+posX, -model.getY(x)*IShape.PPC+posY))
	}


	private def updateLine(posX:Double, posY:Double, minX:Double, maxX:Double, step:Double) {
		val shape = ShapeFactory.createPolyline(false)
		if(lineView!=null) lineView.flush
		lineView = new LPolylineView(shape)
		fillPoints(shape, posX, posY, minX, maxX, step)
		lineView.update
	}


	private def updateCurve(posX:Double, posY:Double, minX:Double, maxX:Double, step:Double) {
		// The algorithm follows this definition:
		//https://stackoverflow.com/questions/15864441/how-to-make-a-line-curve-through-points
		val scale = 0.33
		val pFirst = ShapeFactory.createPoint(minX, model.getY(minX))
		val pNext = ShapeFactory.createPoint(minX+step, model.getY(minX+step))
		val shape = ShapeFactory.createBezierCurve(false)
		if(curveView!=null) curveView.flush
		curveView = new LBezierCurveView(shape)
		fillPoints(shape, posX, posY, minX, maxX, step)
		shape.setIsClosed(false)
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


	override def updateDblePathInside(){}
	override def updateDblePathMiddle(){}
	override def updateDblePathOutside(){}
	override def updateGeneralPathInside(){}
	override def updateGeneralPathMiddle(){}
	override def updateGeneralPathOutside(){}
}