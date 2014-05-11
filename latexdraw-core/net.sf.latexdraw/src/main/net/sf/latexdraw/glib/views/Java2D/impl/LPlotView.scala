package net.sf.latexdraw.glib.views.Java2D.impl

import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import java.awt.Rectangle
import java.awt.Graphics2D
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

/**
 * The graphical representation of a plotted function.
 * @since 3.2
 * @author Arnaud Blouin
 */
class LPlotView(model:IPlot) extends LShapeView[IPlot](model) {

	update


	override def updatePath() {
		val minX = model.getMinX
		val maxX = model.getMaxX
		val step = (maxX-minX)/model.getNbPlottedPoints
		val posX = model.getPosition.getX
		val posY = model.getPosition.getY

		path.reset
		path.moveTo(minX*IShape.PPC+posX, -model.getY(minX)*IShape.PPC+posY)

		for(x <- minX to maxX by step) {
			path.lineTo(x*IShape.PPC+posX, -model.getY(x)*IShape.PPC+posY)
		}
	}


	override def updateDblePathInside(){}
	override def updateDblePathMiddle(){}
	override def updateDblePathOutside(){}
	override def updateGeneralPathInside(){}
	override def updateGeneralPathMiddle(){}
	override def updateGeneralPathOutside(){}
}