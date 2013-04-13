package net.sf.latexdraw.glib.models.impl

import org.malai.mapping.ActiveArrayList

import net.sf.latexdraw.glib.models.interfaces.ISetShapes
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * This trait implements the ISetShapes interface.<br>
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
protected trait LSetShapes extends ISetShapes {
	/** The set of shapes. */
	var shapes : java.util.List[IShape] = new ActiveArrayList[IShape]()


	override def contains(sh : IShape) = if(sh==null) false else shapes.contains(sh)


	override def addShape(sh : IShape) {
		if(sh!=null && (!sh.isInstanceOf[ISetShapes] || !sh.asInstanceOf[ISetShapes].isEmpty))
			shapes.add(sh)
	}


	override def addShape(sh : IShape, index : Int) =
		if(sh!=null && index<=shapes.size && (index== -1 || index>=0) && (!sh.isInstanceOf[ISetShapes] || !sh.asInstanceOf[ISetShapes].isEmpty))
			if(index== -1 || index==shapes.size)
				shapes.add(sh)
			else
				shapes.add(index, sh)


	override def clear() {
		if(!shapes.isEmpty)
			shapes.clear
	}

	override def getShapeAt(i : Int) : IShape =
		if(i< -1 || i>=shapes.size)
			null
		else
			i match {
				case -1 => shapes.get(shapes.size-1)
				case _ => shapes.get(i)
			}


	override def getShapes() = shapes


	override def isEmpty() = shapes.isEmpty


	override def removeShape(sh : IShape) : Boolean =
		sh match {
			case null => false
			case _ => shapes.remove(sh)
		}


	override def removeShape(i : Int) : IShape =
		shapes.isEmpty || i< -1 || i>=shapes.size match {
			case true => null
			case false => i== -1 match {
				case true => shapes.remove(shapes.size-1)
				case false => shapes.remove(i)
			}
		}


	override def size() = shapes.size
}