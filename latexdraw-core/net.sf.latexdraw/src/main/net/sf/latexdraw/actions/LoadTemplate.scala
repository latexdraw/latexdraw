package net.sf.latexdraw.actions

import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import org.malai.action.Action
import org.malai.undo.Undoable

/**
 * This action loads a given template.<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LoadTemplate extends Action with Undoable with DrawingAction with Modifying { //IOAction[LFrame, JLabel] {
private var insertedShapes:IShape = _

  protected def doActionBody() {
//    insertedShapes = SVGDocumentGenerator.INSTANCE.insert(file.getPath, ui)
//    println(insertedShapes)
//    ok = insertedShapes!=null
  }

  override def redo() {
    _drawing.get.addShape(insertedShapes)
  }

  override def undo() {
    _drawing.get.removeShape(insertedShapes)
  }

  override def getUndoName = "template added"

  override def canDo = drawing.isDefined

  override def isRegisterable = true

  override def flush() {
    super.flush()
    insertedShapes = null
  }
}