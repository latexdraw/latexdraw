package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape

/**
 * This trait encapsulates the code of the group related to the support of line arc shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected trait LGroupLineArc extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstLineArc = getShapes.find{shape => shape.isInstanceOf[ILineArcShape] }

	override def getLineArc() : Double = {
		firstLineArc match {
			case Some(la) => la.asInstanceOf[ILineArcShape].getLineArc
			case _ => Double.NaN
		}
	}


	override def setLineArc(lineArc : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[ILineArcShape])
				shape.asInstanceOf[ILineArcShape].setLineArc(lineArc)
		}
	}


	override def isRoundCorner() = getShapes.exists{sh => sh.isInstanceOf[ILineArcShape] && sh.asInstanceOf[ILineArcShape].isRoundCorner}
}
