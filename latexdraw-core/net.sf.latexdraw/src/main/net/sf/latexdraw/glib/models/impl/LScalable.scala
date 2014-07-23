package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.interfaces.prop.IScalable
import net.sf.latexdraw.glib.models.GLibUtilities

/**
 * Implementation of the interface IScalable
 * @author Arnaud Blouin
 * @since 3.2
 */
trait LScalable extends IScalable {
	var xscale:Double = 1.0
	var yscale:Double = 1.0

	override def setXScale(xscale:Double) {
		if(xscale>0 && GLibUtilities.isValidCoordinate(xscale))
			this.xscale = xscale
	}

	override def setYScale(yscale:Double) {
		if(yscale>0 && GLibUtilities.isValidCoordinate(yscale))
			this.yscale = yscale
	}

	override def setScale(scale:Double) {
		setXScale(scale)
		setYScale(scale)
	}

	override def getXScale = xscale

	override def getYScale = yscale
}
