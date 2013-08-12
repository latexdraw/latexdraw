package net.sf.latexdraw.actions.shape

import javax.swing.JFileChooser
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.models.interfaces.IPicture

/**
 * This action asks the user to select a picture and, if valid, adds it to a drawing.<br>
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
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class InsertPicture extends AddShape {
	/** The file chooser used to select the picture to add. */
	protected var _fileChooser : Option[JFileChooser] = None

	/** Defines if the picture has been successfully loaded. */
	private var loaded : Boolean = false


	override protected def doActionBody() {
		// Asks the user for the picture to load.
		if(_fileChooser.get.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
			try{
				_shape.get.asInstanceOf[IPicture].setPathSource(_fileChooser.get.getSelectedFile.getAbsolutePath)
				loaded = true
			}catch { case ex => BadaboomCollector.INSTANCE.add(ex) }

		if(loaded)
			redo
	}


	override def redo() {
		_drawing.get.addShape(_shape.get)
		_drawing.get.setModified(true)
	}


	override def hadEffect() = super.hadEffect && loaded


	override def canDo() = super.canDo && _fileChooser.isDefined && _shape.get.isInstanceOf[IPicture]


	/**
	 * @param fileChooser The file chooser used to select the picture to load.
	 * @since 3.0
	 */
	def setFileChooser(fileChooser : JFileChooser) {
		_fileChooser = Some(fileChooser)
	}
}