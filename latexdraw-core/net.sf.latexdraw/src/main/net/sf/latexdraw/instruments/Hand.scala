package net.sf.latexdraw.instruments

import java.awt.Cursor
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.geom.Rectangle2D
import scala.collection.JavaConversions.asScalaBuffer
import org.malai.action.Action
import org.malai.instrument.Instrument
import org.malai.instrument.Link
import org.malai.interaction.library.DnD
import org.malai.interaction.library.KeysPressure
import org.malai.mapping.MappingRegistry
import org.malai.swing.instrument.library.WidgetZoomer
import org.malai.swing.interaction.library.DoubleClick
import net.sf.latexdraw.actions.shape.InitTextSetter
import net.sf.latexdraw.actions.shape.RotateShapes
import net.sf.latexdraw.actions.shape.ScaleShapes
import net.sf.latexdraw.actions.shape.SelectShapes
import net.sf.latexdraw.actions.shape.TranslateShapes
import net.sf.latexdraw.actions.shape.TranslateShapes
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IText
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.glib.ui.LMagneticGrid
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText
import org.malai.interaction.library.PressWithKeys
import org.malai.interaction.library.DnDWithKeys
import scala.collection.mutable.Buffer
import net.sf.latexdraw.actions.shape.UpdateToGrid
import org.malai.swing.action.library.MoveCamera
import net.sf.latexdraw.glib.models.interfaces.IPoint
import java.awt.Point
import net.sf.latexdraw.glib.models.impl.LShapeFactory._


/**
 * This instrument allows to manipulate (e.g. move or select) shapes.<br>
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
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class Hand(canvas : ICanvas, val textSetter : TextSetter) extends CanvasInstrument(canvas) {
	var _metaCustomiser : MetaShapeCustomiser = _

	override protected def initialiseLinks() {
		try{
			addLink(new Press2Select(this))
			addLink(new DnD2Select(this))
			addLink(new DnD2Translate(this))
			addLink(new DnD2MoveViewport(this))
			addLink(new DoubleClick2InitTextSetter(this))
			addLink(new CtrlA2SelectAllShapes(this))
			addLink(new CtrlU2UpdateShapes(this))
		}catch{case ex: Throwable => BadaboomCollector.INSTANCE.add(ex)}
	}


	override def setActivated(activated : Boolean) {
		if(this.activated!=activated)
			super.setActivated(activated)
	}


	override def interimFeedback() {
		// The rectangle used for the interim feedback of the selection is removed.
		canvas.setTempUserSelectionBorder(null)
		canvas.setCursor(Cursor.getDefaultCursor)
	}

	def setMetaCustomiser(metaCustomiser:MetaShapeCustomiser) { _metaCustomiser = metaCustomiser }


	override def onActionDone(action:Action) {
		action match {
			case _:TranslateShapes => _metaCustomiser.dimPosCustomiser.update()
			case _ =>
		}
	}
}


private sealed class CtrlU2UpdateShapes(ins:Hand) extends Link[UpdateToGrid, KeysPressure, Hand](ins, false, classOf[UpdateToGrid], classOf[KeysPressure]) {
	override def initAction() {
		action.setShape(instrument.canvas.getDrawing.getSelection.duplicate.asInstanceOf[IGroup])
		action.setGrid(instrument.canvas.getMagneticGrid)
	}

	override def isConditionRespected() = instrument.canvas.getMagneticGrid.isMagnetic() &&
		interaction.getKeys().size()==2 && interaction.getKeys().contains(KeyEvent.VK_U) && interaction.getKeys().contains(KeyEvent.VK_CONTROL)
}


private sealed class CtrlA2SelectAllShapes(ins:Hand) extends Link[SelectShapes, KeysPressure, Hand](ins, false, classOf[SelectShapes], classOf[KeysPressure]) {
	override def initAction() {
		instrument.canvas.getDrawing.getShapes.foreach{sh => action.addShape(sh)}
		action.setDrawing(instrument.canvas.getDrawing)
	}

	override def isConditionRespected() =
		interaction.getKeys().size()==2 && interaction.getKeys().contains(KeyEvent.VK_A) && interaction.getKeys().contains(KeyEvent.VK_CONTROL)
}


private sealed class DoubleClick2InitTextSetter(ins : Hand) extends Link[InitTextSetter, DoubleClick, Hand](ins, false, classOf[InitTextSetter], classOf[DoubleClick]) {
	override def initAction() {
		val o = interaction.getTarget

		if(o.isInstanceOf[IViewText]) {
			val text = o.asInstanceOf[IViewText].getShape.asInstanceOf[IText]
			val position = text.getPosition

			action.setTextShape(text)
			action.setInstrument(instrument.textSetter)
			action.setTextSetter(instrument.textSetter)
			action.setAbsolutePoint(instrument.getAdaptedOriginPoint(position))
			action.setRelativePoint(DrawingTK.getFactory.createPoint(position))
		}
	}

	override def isConditionRespected() = interaction.getTarget.isInstanceOf[IViewText]
}



/**
 * This link allows to translate the selected shapes.
 */
private sealed class DnD2Translate(hand : Hand) extends Link[TranslateShapes, DnD, Hand](hand, true, classOf[TranslateShapes], classOf[DnD]) {
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
		action.setShape(instrument.canvas.getDrawing.getSelection.duplicate.asInstanceOf[IGroup])
	}


	override def updateAction() {
		val startPt	= instrument.getAdaptedGridPoint(interaction.getStartPt)
		val endPt	= instrument.getAdaptedGridPoint(interaction.getEndPt)

		action.setTx(endPt.getX - startPt.getX)
		action.setTy(endPt.getY - startPt.getY)
	}

	override def isConditionRespected() : Boolean = {
		val startObject = interaction.getStartObject
		val button 		= interaction.getButton
		return  !instrument.canvas.getDrawing.getSelection.isEmpty &&
				(startObject==instrument.canvas && button==MouseEvent.BUTTON3 ||
				 startObject.isInstanceOf[IViewShape] && (button==MouseEvent.BUTTON1 || button==MouseEvent.BUTTON3))
	}


	override def interimFeedback() {
		super.interimFeedback()
		instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR))
	}
}



private sealed class Press2Select(ins : Hand) extends Link[SelectShapes, PressWithKeys, Hand](ins, false, classOf[SelectShapes], classOf[PressWithKeys]) {
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
	}

	override def updateAction() {
		val target = interaction.getTarget

		if(target.isInstanceOf[IViewShape]) {
			val keys = interaction.getKeys
			val selection = instrument.canvas.getDrawing.getSelection.getShapes
			val targetSh = MappingRegistry.REGISTRY.getSourceFromTarget(target, classOf[IShape])

			if(keys.contains(KeyEvent.VK_SHIFT))
				selection.filter{sh => sh!=targetSh}.foreach{sh => action.addShape(sh)}
			else if(keys.contains(KeyEvent.VK_CONTROL)) {
				selection.foreach{sh => action.addShape(sh)}
				action.addShape(targetSh)
			}
			else
				action.setShape(targetSh)
		}
	}

	override def isConditionRespected() : Boolean = interaction.getTarget.isInstanceOf[IViewShape]
}



/**
 * This link allows to select several shapes.
 */
private sealed class DnD2Select(hand : Hand) extends Link[SelectShapes, DnDWithKeys, Hand](hand, true, classOf[SelectShapes], classOf[DnDWithKeys]) {
	/** The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
	val selectionBorder : Rectangle2D = new Rectangle2D.Double()
	var selectedShapes : Buffer[IShape] = _
	var selectedViews  : Buffer[IViewShape] = _

	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
		selectedShapes = instrument.canvas.getDrawing.getSelection.getShapes.clone
		selectedViews  = instrument.canvas.getBorderInstrument.selection.clone
	}


	override def updateAction() {
		val start = instrument.getAdaptedOriginPoint(interaction.getPoint)
		val end	  = instrument.getAdaptedOriginPoint(interaction.getEndPt)
		val minX  = math.min(start.getX, end.getX)
		val maxX  = math.max(start.getX, end.getX)
		val minY  = math.min(start.getY, end.getY)
		val maxY  = math.max(start.getY, end.getY)
		val zoom  = instrument.canvas.getZoom
		val keys = interaction.getKeys

		// Updating the rectangle used for the interim feedback and for the selection of shapes.
		selectionBorder.setFrame(minX/zoom, minY/zoom, Math.max((maxX-minX)/zoom, 1), Math.max((maxY-minY)/zoom, 1))
		// Cleaning the selected shapes in the action.
		action.setShape(null)

		if(keys.contains(KeyEvent.VK_SHIFT))
			selectedViews.filter{view => !view.intersects(selectionBorder)}.foreach{
					view => action.addShape(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]))}
		else {
			if(keys.contains(KeyEvent.VK_CONTROL))
				selectedShapes.foreach{sh => action.addShape(sh)}
			if(!selectionBorder.isEmpty)
				instrument.canvas.getViews.foreach{view =>
					if(view.intersects(selectionBorder))
						// Taking the shape in function of the view.
						action.addShape(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]))
				}
		}
	}

	override def isConditionRespected() = interaction.getStartObject==instrument.canvas && interaction.getButton==MouseEvent.BUTTON1

	override def interimFeedback() {
		instrument.canvas.setTempUserSelectionBorder(selectionBorder)
		instrument.canvas.refresh
	}
}


/**
 * Moves the viewport using the hand.
 */
private sealed class DnD2MoveViewport(hand : Hand) extends Link[MoveCamera, DnD, Hand](hand, true, classOf[MoveCamera], classOf[DnD]) {
	override def initAction() {
		action.setScrollPane(hand.canvas.getScrollpane)
	}

	override def updateAction() {
		val startPt	= interaction.getStartPt
		val endPt	= interaction.getEndPt
		val pane	= hand.canvas.getScrollpane
		action.setPx(pane.getHorizontalScrollBar.getValue+pane.getHorizontalScrollBar.getWidth/2+(startPt.getX - endPt.getX).toInt)
		action.setPy(pane.getVerticalScrollBar.getValue+pane.getVerticalScrollBar.getHeight/2+(startPt.getY - endPt.getY).toInt)
	}

	override def isConditionRespected() = interaction.getButton==MouseEvent.BUTTON2

	override def interimFeedback() {
		super.interimFeedback
		instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR))
	}
}