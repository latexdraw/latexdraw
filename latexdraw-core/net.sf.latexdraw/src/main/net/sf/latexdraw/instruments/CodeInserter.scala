package net.sf.latexdraw.instruments

import org.malai.instrument.Instrument
import net.sf.latexdraw.ui.dialog.InsertCodeDialog
import org.malai.action.library.InactivateInstrument
import org.malai.instrument.Link
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.actions.InsertPSTCode
import net.sf.latexdraw.glib.ui.ICanvas
import javax.swing.JLabel
import org.malai.swing.interaction.library.ButtonPressed
import org.malai.swing.interaction.library.WindowClosed


/**
 * This instrument converts PST code into graphical shapes.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 2013-02-07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class CodeInserter(val canvas : ICanvas, val statusBar : JLabel) extends Instrument {
	/** The dialogue box used to insert code. */
	protected lazy val _insertCodeDialog : InsertCodeDialog = new InsertCodeDialog(this)


	/** Accessor for the code insertion dialogue box. */
	def insertCodeDialog : InsertCodeDialog = _insertCodeDialog

	override def initialiseLinks() {
		try{
			addLink(new CloseDialogue2InactivateIns(this))
			addLink(new ButtonPressed2InsertCode(this))
			addLink(new ButtonPressed2InactivateIns(this))
		}catch{case ex => BadaboomCollector.INSTANCE.add(ex)}
	}

	override def setActivated(activated : Boolean) {
		super.setActivated(activated)
		_insertCodeDialog.setVisible(activated)
	}
}


/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class ButtonPressed2InsertCode(ins : CodeInserter)
	extends Link[InsertPSTCode, ButtonPressed, CodeInserter](ins, false, classOf[InsertPSTCode], classOf[ButtonPressed]){
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
		action.setCode(instrument.insertCodeDialog.getText)
		action.setStatusBar(instrument.statusBar)
	}

	override def isConditionRespected() = interaction.getButton==instrument.insertCodeDialog.getOkButton
}


/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class ButtonPressed2InactivateIns(ins : CodeInserter)
	extends Link[InactivateInstrument, ButtonPressed, CodeInserter](ins, false, classOf[InactivateInstrument], classOf[ButtonPressed]){
	override def initAction() {
		action.setInstrument(getInstrument)
	}

	override def isConditionRespected() = interaction.getButton==instrument.insertCodeDialog.getOkButton ||
										interaction.getButton==instrument.insertCodeDialog.getCancelButton
}



/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class CloseDialogue2InactivateIns(ins : CodeInserter)
	extends Link[InactivateInstrument, WindowClosed, CodeInserter](ins, false, classOf[InactivateInstrument], classOf[WindowClosed]){
	override def initAction() {
		action.setInstrument(getInstrument)
	}

	override def isConditionRespected() = true
}
