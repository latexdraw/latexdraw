package net.sf.latexdraw.instruments

import java.awt.event.MouseEvent
import java.awt.geom.Rectangle2D
import java.awt.Cursor
import scala.collection.JavaConversions.asScalaBuffer
import org.malai.instrument.Link
import org.malai.instrument.Instrument
import org.malai.mapping.MappingRegistry
import net.sf.latexdraw.actions.shape.SelectShapes
import net.sf.latexdraw.actions.shape.TranslateShapes
import net.sf.latexdraw.actions.shape.InitTextSetter
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IText
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.glib.ui.LMagneticGrid
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText
import org.malai.swing.interaction.library.DoubleClick
import org.malai.interaction.library.DnD
import org.malai.swing.instrument.library.WidgetZoomer
import org.malai.interaction.library.Press
import net.sf.latexdraw.actions.shape.MovePointShape
import net.sf.latexdraw.actions.shape.ModifyShapeProperty
import net.sf.latexdraw.actions.shape.ScaleShapes
import net.sf.latexdraw.actions.shape.MoveCtrlPoint
import net.sf.latexdraw.actions.shape.RotateShapes
import org.malai.action.Action
import net.sf.latexdraw.actions.shape.TranslateShapes
import org.malai.interaction.library.KeysPressure
import java.awt.event.KeyEvent

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.<br>
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
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class Hand(val canvas : ICanvas, val grid : LMagneticGrid, val zoomer : WidgetZoomer, val textSetter : TextSetter) extends Instrument {
	protected var _metaCustomiser : MetaShapeCustomiser = null

	override protected def initialiseLinks() {
		try{
			addLink(new Press2Select(this))
			addLink(new DnD2Select(this))
			addLink(new DnD2Translate(this))
			addLink(new DoubleClick2InitTextSetter(this))
			addLink(new CtrlA2SelectAllShapes(this))
		}catch{case ex => BadaboomCollector.INSTANCE.add(ex)}
	}


	override def setActivated(activated : Boolean) {
		if(this.activated!=activated)
			super.setActivated(activated)
	}

	//Mutant7
	//override def interimFeedback() {
		// The rectangle used for the interim feedback of the selection is removed.
		//canvas.setTempUserSelectionBorder(null)
		//canvas.setCursor(Cursor.getDefaultCursor)
	//}

	def setMetaCustomiser(metaCustomiser:MetaShapeCustomiser) { _metaCustomiser = metaCustomiser }


	override def onActionDone(action:Action) {
		if(_metaCustomiser!=null) {
			action match {
				case _:TranslateShapes => _metaCustomiser.dimPosCustomiser.update()
				case _ =>
			}
		}
	}
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
			val zoom = instrument.zoomer.getZoomable.getZoom

			action.setTextShape(text)
			action.setInstrument(instrument.textSetter)
			action.setTextSetter(instrument.textSetter)
			action.setAbsolutePoint(DrawingTK.getFactory.createPoint(position.getX*zoom, position.getY*zoom))
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
		val startPt	= instrument.grid.getTransformedPointToGrid(instrument.zoomer.getZoomable.getZoomedPoint(interaction.getStartPt))
		val endPt	= instrument.grid.getTransformedPointToGrid(instrument.zoomer.getZoomable.getZoomedPoint(interaction.getEndPt))

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



private sealed class Press2Select(ins : Hand) extends Link[SelectShapes, Press, Hand](ins, false, classOf[SelectShapes], classOf[Press]) {
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
	}

	override def updateAction() {
		val target = interaction.getTarget

		if(target.isInstanceOf[IViewShape])
			action.setShape(MappingRegistry.REGISTRY.getSourceFromTarget(target, classOf[IShape]))
	}

	override def isConditionRespected() : Boolean = {
		val target = interaction.getTarget
		return target.isInstanceOf[IViewShape] &&
			   !instrument.canvas.getDrawing.getSelection.contains(MappingRegistry.REGISTRY.getSourceFromTarget(target, classOf[IShape]))
	}
}



/**
 * This link allows to select several shapes.
 */
private sealed class DnD2Select(hand : Hand) extends Link[SelectShapes, DnD, Hand](hand, true, classOf[SelectShapes], classOf[DnD]) {
	/** The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
	private val selectionBorder : Rectangle2D = new Rectangle2D.Double()

	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
	}


	override def updateAction() {
		val start = interaction.getStartPt
		val end	  = interaction.getEndPt
		val minX  = math.min(start.x, end.x)
		val maxX  = math.max(start.x, end.x)
		val minY  = math.min(start.y, end.y)
		val maxY  = math.max(start.y, end.y)
		val zoom  = instrument.canvas.getZoom

		// Updating the rectangle used for the interim feedback and for the selection of shapes.
		selectionBorder.setFrame(minX/zoom, minY/zoom, (maxX-minX)/zoom, (maxY-minY)/zoom)
		// Cleaning the selected shapes in the action.
		action.setShape(null)

		if(!selectionBorder.isEmpty)
			instrument.canvas.getViews.foreach{view =>
				if(view.intersects(selectionBorder))
					// Taking the shape in function of the view.
					action.addShape(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]))
			}
	}

	override def isConditionRespected() = interaction.getStartObject==instrument.canvas && interaction.getButton==MouseEvent.BUTTON1

	override def interimFeedback() {
		instrument.canvas.setTempUserSelectionBorder(selectionBorder)
		instrument.canvas.refresh
	}
}
