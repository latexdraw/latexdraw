package net.sf.latexdraw.glib.models.impl

import org.malai.mapping.MappingRegistry
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.ShapeFactory

/**
 * Implements the concept of drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
private[impl] class LDrawing extends IDrawing with LSetShapes {
	/** The selected shapes of the drawing. */
	val selection = ShapeFactory.createGroup()

	/** Defined if the shape has been modified. */
	var modified = false


	override def getSelection = selection

	override def setSelection(newSelection : java.util.List[IShape]) {
		selection.clear
		newSelection.forEach(sh => selection.addShape(sh))
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
			shapes.forEach{_.setModified(false)}

		this.modified = modified
	}

	override def isModified = modified || shapes.stream.filter{_.isModified}.findAny.isPresent

	override def reinit() = clear
}
