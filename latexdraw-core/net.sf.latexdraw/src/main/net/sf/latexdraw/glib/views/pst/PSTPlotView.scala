package net.sf.latexdraw.glib.views.pst

import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.util.LNumber

/**
 * The PST code generation of plot shapes.
 * @author Arnaud Blouin
 * @since 3.2
 */
class PSTPlotView(plot:IPlot) extends PSTClassicalView[IPlot](plot){

	update

	override def updateCache(position:IPoint, ppc:Float) {
		if(!GLibUtilities.isValidPoint(position) || ppc<1) return

		emptyCache

		val params  = getPropertiesCode(ppc)
		val tl  	= shape.getTopLeftPoint
		val br  	= shape.getBottomRightPoint
		val x1 		 = tl.getX - position.getX
		val x2 		 = br.getX - position.getX
		val y1 		 = position.getY - tl.getY
		val y2 		 = position.getY - br.getY
		val rotation = getRotationHeaderCode(ppc, position)

		if(rotation!=null)
			cache.append(rotation)

		cache.append("\\rput(")//$NON-NLS-1$
		cache.append(LNumber.getCutNumber((shape.getX+shape.getWidth/2.0-position.getX)/ppc).toFloat).append(',')
		cache.append(LNumber.getCutNumber((position.getY-shape.getY()-shape.getHeight/2.0)/ppc).toFloat).append(')').append('{')
		cache.append("\\psplot[")	//$NON-NLS-1$
		cache.append(params).append(", plotstyle=").append(shape.getPlotStyle.getPSTToken).append(", plotpoints=").append(shape.getNbPlottedPoints)
		cache.append("]{").append(shape.getMinX).append("}{").append(shape.getMaxX).append("}{").append(shape.getEquation).append('}')

		if(rotation!=null)
			cache.append('}')
		cache.append('}')
	}
}
