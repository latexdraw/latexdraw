package net.sf.latexdraw.models.impl

import net.sf.latexdraw.models.interfaces.prop.ILineArcProp
import net.sf.latexdraw.models.GLibUtilities

/**
 * Line arc properties.
 * @author Arnaud Blouin
 */
private[impl] trait LineArcProp extends ILineArcProp {
		/** The radius of arcs drawn at the corners of lines. */
	var frameArc:Double = 0.0

	override def getLineArc = frameArc

	override def isRoundCorner = frameArc>0

	override def setLineArc(arc:Double) {
		if(GLibUtilities.isValidCoordinate(arc) && arc>=0 && arc<=1)
			frameArc = arc
	}
}