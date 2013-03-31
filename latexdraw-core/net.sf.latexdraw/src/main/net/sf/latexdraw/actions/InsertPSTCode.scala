package net.sf.latexdraw.actions

import java.text.ParseException
import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.parsers.pst.parser.PSTParser
import javax.swing.JLabel
import net.sf.latexdraw.lang.LangTool
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.badaboom.BadaboomManager

/**
 * This action converts PST code into shapes and add them to the drawing.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 2013-02-19<br>
 * @author Arnaud Blouin
 * @since 3.0
 */
class InsertPSTCode extends Action with DrawingAction with Undoable {
	/** The code to parse. */
	protected var _code : Option[String] = None

	/** The status bar. */
	protected var _statusBar : Option[JLabel] = None

	/** The added shapes. */
	private var _shapes : IShape = null



	protected def doActionBody() {
		PSTParser.cleanErrors
		try {
			new PSTParser().parsePSTCode(_code.get) match {
				case Some(group) if(!group.isEmpty) =>
					if(group.size()>1)
						_shapes = group
					else _shapes = group.getShapeAt(0)

					val br = _shapes.getBottomRightPoint
					val tl = _shapes.getTopLeftPoint
					val tx = if(tl.getX<0) -tl.getX+50 else 0
					val ty = if(tl.getY<0) -tl.getY+50 else 0

					_shapes.translate(tx, ty)
					redo
					if(_statusBar.isDefined) _statusBar.get.setText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.36"))
				case _ => if(_statusBar.isDefined) _statusBar.get.setText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.33"))
			}
		}catch{
			case ex =>
				BadaboomCollector.INSTANCE.add(ex)
				if(_statusBar.isDefined) _statusBar.get.setText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.34"))
			}

		PSTParser.errorLogs.foreach{str => BadaboomCollector.INSTANCE.add(new ParseException(str, -1))}
		done
	}

	override def undo() {
		if(_shapes!=null) {
			_drawing.get.removeShape(_shapes)
			_drawing.get.setModified(true)
		}
	}

	override def redo() {
		if(_shapes!=null) {
			_drawing.get.addShape(_shapes)
			_drawing.get.setModified(true)
		}
	}

	def setStatusBar(value:JLabel) { if(value!=null) _statusBar = Some(value) else _statusBar = None }

	def setCode(value:String) { if(value!=null) _code = Some(value) else _code = None }

	override def getUndoName() = "Inserting of PST code"

	override def canDo() = _code.isDefined && _drawing.isDefined

	override def hadEffect() = isDone && _shapes!=null

	override def isRegisterable() = hadEffect
}
