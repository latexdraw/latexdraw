package net.sf.latexdraw.instruments

import java.awt.Frame
import javax.swing.JLabel

import net.sf.latexdraw.actions.{CheckPSTCode, InsertPSTCode}
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.ui.dialog.InsertCodeDialog
import org.malai.action.library.InactivateInstrument
import org.malai.instrument.InteractorImpl
import org.malai.swing.instrument.SwingInstrument
import org.malai.swing.interaction.library.{ButtonPressed, WindowClosed}


/**
 * This instrument converts PST code into graphical shapes.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
class CodeInserter(parent: Frame, val canvas : ICanvas, val statusBar : JLabel) extends SwingInstrument {
	/** The dialogue box used to insert code. */
	lazy val _insertCodeDialog : InsertCodeDialog = new InsertCodeDialog(parent, this)


	/** Accessor for the code insertion dialogue box. */
	def insertCodeDialog() : InsertCodeDialog = _insertCodeDialog

	override def initialiseInteractors() {
		try{
			addInteractor(new KeyToCheckCode(this))
			addInteractor(new CloseDialogue2InactivateIns(this))
			addInteractor(new ButtonPressed2InsertCode(this))
			addInteractor(new ButtonPressed2InactivateIns(this))
		}catch{case ex: Throwable => BadaboomCollector.INSTANCE.add(ex)}
	}

	override def setActivated(activated : Boolean) {
		super.setActivated(activated)
		_insertCodeDialog.setVisible(activated)
	}
}

class KeyToCheckCode(ins : CodeInserter)
	extends InteractorImpl[CheckPSTCode, KeyTypedWithTimer, CodeInserter](ins, false, classOf[CheckPSTCode], classOf[KeyTypedWithTimer]){

	override def initAction() {
		action.setDialog(instrument._insertCodeDialog)
	}

	override def isConditionRespected: Boolean = true
}


/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class ButtonPressed2InsertCode(ins : CodeInserter)
	extends InteractorImpl[InsertPSTCode, ButtonPressed, CodeInserter](ins, false, classOf[InsertPSTCode], classOf[ButtonPressed]){
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
		action.setCode(instrument.insertCodeDialog.getText)
		action.setStatusBar(instrument.statusBar)
	}

	override def isConditionRespected = interaction.getButton==instrument.insertCodeDialog.getOkButton
}


/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class ButtonPressed2InactivateIns(ins : CodeInserter)
	extends InteractorImpl[InactivateInstrument, ButtonPressed, CodeInserter](ins, false, classOf[InactivateInstrument], classOf[ButtonPressed]){
	override def initAction() {
		action.setInstrument(getInstrument)
	}

	override def isConditionRespected = interaction.getButton==instrument.insertCodeDialog.getOkButton ||
										interaction.getButton==instrument.insertCodeDialog.getCancelButton
}



/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class CloseDialogue2InactivateIns(ins : CodeInserter)
	extends InteractorImpl[InactivateInstrument, WindowClosed, CodeInserter](ins, false, classOf[InactivateInstrument], classOf[WindowClosed]){
	override def initAction() {
		action.setInstrument(getInstrument)
	}

	override def isConditionRespected = true
}
