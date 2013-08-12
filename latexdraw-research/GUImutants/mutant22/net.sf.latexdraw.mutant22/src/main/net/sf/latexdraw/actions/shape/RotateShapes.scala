package net.sf.latexdraw.actions.shape

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * This action increments to rotation angle of shapes.<br>
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
class RotateShapes extends Action with ShapeAction[IShape] with Undoable with Modifying {
	/** The rotation angle to apply. */
	protected var _rotationAngle : Double = Double.NaN

	/** The gravity centre used for the rotation. */
	protected var _gc : Option[IPoint] = None

	/** The last increment performed on shapes. Used to execute several times the action. */
	private var lastRotationAngle : Double = 0.0


	override def canDo() = _shape.isDefined && _gc.isDefined && GLibUtilities.INSTANCE.isValidCoordinate(_rotationAngle) &&
							GLibUtilities.INSTANCE.isValidPoint(_gc.get)


	override def isRegisterable() = true


	override def doActionBody() {
		rotateShapes(_rotationAngle-lastRotationAngle)
		lastRotationAngle = _rotationAngle
	}


	/**
	 * Rotates the shape.
	 * @param angleIncrement The increment to add to the rotation angle of the shape.
	 */
	private def rotateShapes(angleIncrement : Double) {
		_shape.get.addToRotationAngle(_gc.get, angleIncrement)
		_shape.get.setModified(true)
	}


	override def undo() {
		//Mutant22
		//rotateShapes(-_rotationAngle)
	}


	override def redo() {
		rotateShapes(_rotationAngle)
	}


	override def getUndoName() = "rotation"


	/**
	 * @param rotationAngle The rotation angle to apply.
	 */
	def setRotationAngle(rotationAngle : Double) {
		_rotationAngle = rotationAngle
	}

	def rotationAngle = _rotationAngle

	/**
	 * @param gc The gravity centre used for the rotation.
	 */
	def setGravityCentre(gc : IPoint) {
		if(gc!=null)
			_gc = Some(gc)
		else _gc = None
	}

	def gc = _gc
}
