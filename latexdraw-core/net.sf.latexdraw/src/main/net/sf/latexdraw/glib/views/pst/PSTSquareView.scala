package net.sf.latexdraw.glib.views.pst

import net.sf.latexdraw.glib.models.interfaces.shape.ISquare
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.util.LNumber

/**
 * Defines a PSTricks view of the LSquare model.
 * @author Arnaud Blouin
 */
class PSTSquareView(sq:ISquare) extends PSTClassicalView[ISquare](sq){

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

		if(shape.isRoundCorner)
			params.append(", framearc=").append(LNumber.getCutNumberFloat(shape.getLineArc)) //$NON-NLS-1$

		val rotation = getRotationHeaderCode(ppc, position)

		if(rotation!=null)
			cache.append(rotation)

		cache.append("\\psframe[")	//$NON-NLS-1$
		cache.append(params)
		cache.append(']').append('(')
		cache.append(LNumber.getCutNumberFloat(x2 / ppc)).append(',')
		cache.append(LNumber.getCutNumberFloat(y1 / ppc)).append(')').append('(')
		cache.append(LNumber.getCutNumberFloat(x1 / ppc)).append(',')
		cache.append(LNumber.getCutNumberFloat(y2 / ppc)).append(')')

		if(rotation!=null)
			cache.append('}')
	}
}