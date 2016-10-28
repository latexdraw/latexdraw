package net.sf.latexdraw.actions

import java.text.ParseException
import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.parsers.pst.parser.PSTParser
import javax.swing.JLabel
import net.sf.latexdraw.lang.LangTool
import net.sf.latexdraw.models.interfaces.shape.IShape

/**
 * This action converts PST code into shapes and add them to the drawing.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
	var _code : Option[String] = None

	/** The status bar. */
	var _statusBar : Option[JLabel] = None

	/** The added shapes. */
	var _shapes : Option[IShape] = None



	protected def doActionBody() {
		PSTParser.cleanErrors
		try {
			new PSTParser().parsePSTCode(_code.get) match {
				case Some(group) if !group.isEmpty =>
					val sh = if(group.size()>1) group else group.getShapeAt(0)
					val br = sh.getBottomRightPoint
					val tl = sh.getTopLeftPoint
					val tx = if(tl.getX<0) -tl.getX+50 else 0
					val ty = if(tl.getY<0) -tl.getY+50 else 0

					_shapes = Some(sh)
					sh.translate(tx, ty)
					redo
					if(_statusBar.isDefined) _statusBar.get.setText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.36"))
				case _ => if(_statusBar.isDefined) _statusBar.get.setText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.33"))
			}
		}catch{
			case ex : Throwable =>
				BadaboomCollector.INSTANCE.add(ex)
				if(_statusBar.isDefined) _statusBar.get.setText(LangTool.INSTANCE.getString16("LaTeXDrawFrame.34"))
			}

		PSTParser.errorLogs.foreach{str => BadaboomCollector.INSTANCE.add(new ParseException(str, -1))}
		done
	}

	override def undo() {
		if(_shapes.isDefined) {
			_drawing.get.removeShape(_shapes.get)
			_drawing.get.setModified(true)
		}
	}

	override def redo() {
		if(_shapes.isDefined) {
			_drawing.get.addShape(_shapes.get)
			_drawing.get.setModified(true)
		}
	}

	def setStatusBar(value:JLabel) { _statusBar = Option(value) }

	def setCode(value:String) { _code = Option(value) }

	override def getUndoName = LangTool.INSTANCE.getStringActions("Actions.4")

	override def canDo = _code.isDefined && _drawing.isPresent

	override def hadEffect() = isDone && _shapes.isDefined

	override def isRegisterable = hadEffect()
}
