package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import org.malai.mapping.MappingRegistry

import net.sf.latexdraw.glib.models.interfaces.IDrawing
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * Implements the concept of drawing.<br>
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
 * 2012-04-17<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected class LDrawing extends IDrawing with LSetShapes {
	/** The selected shapes of the drawing. */
	var selection : IGroup = new LGroup(false)

	/** Defined if the shape has been modified. */
	var modified : Boolean = false


	override def addToSelection(sh : IShape) { selection.addShape(sh) }


	override def addToSelection(newSelection : java.util.List[IShape]) {
		if(newSelection!=null)
			newSelection.foreach(sh => selection.addShape(sh))
	}


	override def getSelection() = selection


	override def removeFromSelection(sh : IShape) = selection.removeShape(sh)


	override def removeSelection() = selection.clear


	override def setSelection(sh : IShape) {
		selection.clear
		selection.addShape(sh)
	}


	override def setSelection(newSelection : java.util.List[IShape]) {
		selection.clear
		addToSelection(newSelection)
	}


	override def clear() {
		super.clear
		selection.clear
	}


	override def removeShape(sh : IShape) : Boolean = {
		selection.removeShape(sh)
		super.removeShape(sh)
	}


	override def removeShape(i : Int) : IShape = {
		// Must be removed from the selection before removing from the main list (otherwise mapping selection2border will fail.
		if(!shapes.isEmpty && i>= -1 && i<shapes.size)
			i match {
				case -1 => selection.removeShape(shapes.get(shapes.size()-1))
				case _ => selection.removeShape(shapes.get(i))
			}

		super.removeShape(i)
	}


	override def setModified(modified : Boolean) {
		if(modified)
			MappingRegistry.REGISTRY.onObjectModified(this)
		else
			shapes.foreach{sh => sh.setModified(false)}

		this.modified = modified
	}


	override def isModified() = modified || shapes.exists{sh => sh.isModified}


	override def reinit() = clear
}
