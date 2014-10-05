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
		val rotation = getRotationHeaderCode(ppc, position)

		if(rotation!=null)
			cache.append(rotation)

		cache.append("\\rput(")//$NON-NLS-1$
		cache.append(LNumber.getCutNumber((shape.getX-position.getX)/ppc).toFloat).append(',')
		cache.append(LNumber.getCutNumber((position.getY-shape.getY())/ppc).toFloat).append(')').append('{')
		cache.append("\\psplot[")	//$NON-NLS-1$
		cache.append(params).append(", plotstyle=").append(shape.getPlotStyle.getPSTToken).append(", plotpoints=").
		append(shape.getNbPlottedPoints).append(", xunit=").append(shape.getXScale).append(", yunit=").append(shape.getYScale)
		//TODO xscale and yscale should be put in a trait
		cache.append("]{").append(shape.getPlotMinX).append("}{").append(shape.getPlotMaxX).append("}{").append(shape.getPlotEquation).append('}')

		if(rotation!=null)
			cache.append('}')
		cache.append('}')
	}
}
