package net.sf.latexdraw.instruments

import java.awt.{BasicStroke, Color, Cursor, Graphics2D}
import java.awt.geom.Rectangle2D

import net.sf.latexdraw.actions.shape._
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.handlers._
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.ShapeFactory.Point2IPoint
import net.sf.latexdraw.glib.models.interfaces.shape._
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.Position
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.glib.views.Java2D.interfaces.{IViewArc, IViewBezierCurve, IViewModifiablePtsShape, IViewShape}
import net.sf.latexdraw.mapping.Shape2BorderMapping
import net.sf.latexdraw.util.LNumber
import org.malai.action.Action
import org.malai.instrument.InteractorImpl
import org.malai.mapping.MappingRegistry
import org.malai.picking.{Pickable, Picker}
import org.malai.swing.interaction.library.DnD

import scala.collection.mutable.ListBuffer

/**
 * This instrument manages the selected views.<br>
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
class Border(canvas : ICanvas) extends CanvasInstrument(canvas) with Picker {
	/** The stroke uses by the border to display its bounding rectangle. */
	val stroke = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, Array(7, 7), 0)

	/** The selected views. */
	val _selection : ListBuffer[IViewShape] = new ListBuffer()

	/** The rectangle uses to show the selection. */
	val _border : Rectangle2D = new Rectangle2D.Double()

	/** The handlers that scale shapes. */
	val _scaleHandlers : ListBuffer[ScaleHandler] = new ListBuffer()

	/** The handlers that move points. */
	lazy val _mvPtHandlers : ListBuffer[MovePtHandler] = new ListBuffer()

	/** The handlers that move first control points. */
	lazy val _ctrlPt1Handlers : ListBuffer[CtrlPointHandler] = new ListBuffer()

	/** The handlers that move second control points. */
	lazy val _ctrlPt2Handlers : ListBuffer[CtrlPointHandler] = new ListBuffer()

//	/** The handler that sets the arc frame. */
//	protected lazy val frameArcHandler : FrameArcHandler = new FrameArcHandler()

	/** The handler that sets the start angle of an arc. */
	lazy val _arcHandlerStart : ArcAngleHandler = new ArcAngleHandler(true)

	/** The handler that sets the end angle of an arc. */
	lazy val _arcHandlerEnd : ArcAngleHandler = new ArcAngleHandler(false)

	/** The handler that rotates shapes. */
	val _rotHandler : RotationHandler = new RotationHandler()

	var _metaCustomiser : MetaShapeCustomiser = _

	// Initialisation of the handlers that are always used.
	_scaleHandlers += new ScaleHandler(Position.NW)
	_scaleHandlers += new ScaleHandler(Position.NORTH)
	_scaleHandlers += new ScaleHandler(Position.NE)
	_scaleHandlers += new ScaleHandler(Position.WEST)
	_scaleHandlers += new ScaleHandler(Position.EAST)
	_scaleHandlers += new ScaleHandler(Position.SW)
	_scaleHandlers += new ScaleHandler(Position.SOUTH)
	_scaleHandlers += new ScaleHandler(Position.SE)


	def scaleHandlers = _scaleHandlers

	def mvPtHandlers = _mvPtHandlers

	def ctrlPt1Handlers = _ctrlPt1Handlers

	def ctrlPt2Handlers = _ctrlPt2Handlers

	def arcHandlerStart = _arcHandlerStart

	def arcHandlerEnd = _arcHandlerEnd

	def rotHandler = _rotHandler

	def border = _border

	def setMetaCustomiser(metaCustomiser:MetaShapeCustomiser) { _metaCustomiser = metaCustomiser }


	override def reinit() {
		_selection.clear
		_border.setFrame(0, 0, 1, 1)
	}


	override def interimFeedback() {
		canvas.setCursor(Cursor.getDefaultCursor)
	}


	override def onActionDone(action:Action) {
		action match {
			case _:RotateShapes => _metaCustomiser.rotationCustomiser.update()
			case _:ModifyShapeProperty => _metaCustomiser.arcCustomiser.update()
			case _:MoveCtrlPoint => _metaCustomiser.dimPosCustomiser.update()
			case _:MovePointShape => _metaCustomiser.dimPosCustomiser.update()
			case _:ScaleShapes => _metaCustomiser.dimPosCustomiser.update()
			case _ =>
		}
	}


	/**
	 * Updates the bounding rectangle using the selected views.
	 * @since 3.0
	 */
	def update() {
		if(!isActivated) return
		if(_selection.isEmpty)
			_border.setFrame(0, 0, 1, 1)
		else {
			val zoomLevel = canvas.getZoom
			var minX = Double.MaxValue
			var minY = Double.MaxValue
			var maxX = Double.MinValue
			var maxY = Double.MinValue

			_selection.foreach{view =>
				val bounds = view.getBorder

				if(bounds.getMinX<minX)
					minX = bounds.getMinX

				if(bounds.getMinY<minY)
					minY = bounds.getMinY

				if(bounds.getMaxX>maxX)
					maxX = bounds.getMaxX

				if(bounds.getMaxY>maxY)
					maxY = bounds.getMaxY
			}

			_border.setFrame(minX*zoomLevel, minY*zoomLevel, (maxX-minX)*zoomLevel, (maxY-minY)*zoomLevel)
			updateHandlersPosition
		}
	}


	/**
	 * Updates the position of the handlers.
	 * @since 3.0
	 */
	private def updateHandlersPosition() {
		_scaleHandlers.foreach(_.updateFromShape(_border))
		_rotHandler.setPoint(_border.getMaxX, _border.getMinY)

//		if(isFrameArcHandlerShowable())
//			frameArcHandler.updateFromLineArcShape((ILineArcShape)selection.get(0).getShape())

		updateArcHandlers
		updateMvHandlers
		updateCtrlMvHandlers
	}


	/**
	 * Updates the arc handlers.
	 * @since 3.0
	 */
	private def updateArcHandlers() {
		if(isArcHandlerShowable) {
			val sh = _selection(0).getShape

			sh match {
        case arc: IArc =>
          _arcHandlerStart.update(arc, canvas.getZoom)
          _arcHandlerEnd.update(arc, canvas.getZoom)
        case _ =>
      }
		}
	}


	/**
	 * Updates the handlers that move control points.
	 * @since 3.0
	 */
	private def updateCtrlMvHandlers() {
		if(isCtrlPtMvHandlersShowable) {
			val sh = _selection(0).getShape

			sh match {
        case cps: IControlPointShape => initialiseCtrlMvHandlers(cps)
        case _ =>
      }
		}
	}


	private def initialiseCtrlMvHandlers(cps : IControlPointShape) {
		val zoom  = canvas.getZoom
		val nbPts = cps.getNbPoints

		// Adding missing handlers.
		if(_ctrlPt1Handlers.size<nbPts)
			for(i <- _ctrlPt1Handlers.size until nbPts) {
				_ctrlPt1Handlers += new CtrlPointHandler(i)
				_ctrlPt2Handlers += new CtrlPointHandler(i)
			}
		// Removing extra handlers.
		else
			while(_ctrlPt1Handlers.size>nbPts) {
				_ctrlPt1Handlers.remove(_ctrlPt1Handlers.size-1)
				_ctrlPt2Handlers.remove(_ctrlPt2Handlers.size-1)
			}

		// Updating handlers.
		for(i <- 0 until _ctrlPt1Handlers.size) {
			val pt1 = cps.getFirstCtrlPtAt(i)
			_ctrlPt1Handlers(i).setPoint(pt1.getX*zoom, pt1.getY*zoom)
			val pt2 = cps.getSecondCtrlPtAt(i)
			_ctrlPt2Handlers(i).setPoint(pt2.getX*zoom, pt2.getY*zoom)
		}
	}



	/**
	 * Updates the handlers that move points.
	 * @since 3.0
	 */
	private def updateMvHandlers() {
		if(isPtMvHandlersShowable) {
			val sh = _selection(0).getShape

			sh match {
        case pts: IModifiablePointsShape =>
          val nbPts = pts.getNbPoints
          val zoom = canvas.getZoom

          if (_mvPtHandlers.size < nbPts)
            for (i <- _mvPtHandlers.size until nbPts)
              _mvPtHandlers += new MovePtHandler(i)
          else
            while (_mvPtHandlers.size > nbPts)
              _mvPtHandlers.remove(_mvPtHandlers.size - 1)

          for (i <- 0 until _mvPtHandlers.size) {
            val pt = pts.getPtAt(i)
            _mvPtHandlers(i).setPoint(pt.getX * zoom, pt.getY * zoom)
          }
        case _ =>
      }
		}
	}


	/**
	 * Paints the border if activated.
	 * @param g The graphics in which the border is painted.
	 * @since 3.0
	 */
	def paint(g : Graphics2D) {
		if(isActivated) {
			g.setColor(Color.GRAY)
			g.setStroke(stroke)
			g.draw(_border)
			paintHandlers(g)
		}
	}


	/**
	 * Paints the required handlers.
	 */
	private def paintHandlers(g : Graphics2D) {
		_scaleHandlers.foreach(_.paint(g))
		_rotHandler.paint(g)

//		if(isFrameArcHandlerShowable())
//			frameArcHandler.paint(g)

		if(isArcHandlerShowable) {
			_arcHandlerStart.paint(g)
			_arcHandlerEnd.paint(g)
		}

		if(isPtMvHandlersShowable) {
			_mvPtHandlers.foreach(_.paint(g))

			if(isCtrlPtMvHandlersShowable) {
				_ctrlPt1Handlers.foreach(_.paint(g))
				_ctrlPt2Handlers.foreach(_.paint(g))
			}
		}
	}

	/** @return True if the control move point handlers can be painted. */
	protected def isCtrlPtMvHandlersShowable = _selection.size==1 && _selection(0).isInstanceOf[IViewBezierCurve]

	/** @return True if the move point handlers can be painted. */
	protected def isPtMvHandlersShowable = _selection.size==1 && _selection(0).isInstanceOf[IViewModifiablePtsShape]

	/** @return True if the arc handlers can be painted. */
	protected def isArcHandlerShowable = _selection.size==1 && _selection(0).isInstanceOf[IViewArc]

//	/**
//	 * @return True if the frame arc handler can be painted.
//	 */
//	protected boolean isFrameArcHandlerShowable() {
//		return selection.size()==1 && selection.get(0).getShape() instanceof ILineArcShape
//	}

	/**
	 * Adds the given shape to the selection. If the instrument is
	 * activated and the addition is performed, the instrument is updated.
	 * @param view The view to add. If null, nothing is done.
	 * @since 3.0
	 */
	def add(view : IViewShape) {
		if(view!=null) {
			_selection += view
			if(isActivated) {
				// The border is updated only if the view has been added and
				// the border is activated.
				update
				MappingRegistry.REGISTRY.addMapping(new Shape2BorderMapping(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]), this))
			}
		}
	}


	/**
	 * Removes the given view from the selection. If the instrument is
	 * activated and the removal is performed, the instrument is updated.
	 * @param view The view to remove. If null or it is not
	 * already in the selection, nothing is performed.
	 * @since 3.0
	 */
	def remove(view : IViewShape) {
		if(view!=null) {
			_selection -= view
			MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]),
											classOf[Shape2BorderMapping])
			update
		}
	}


	/**
	 * @return the selected views. Cannot be null.
	 * @since 3.0
	 */
	def selection = _selection


	override def initialiseInteractors() {
		try{
			addInteractor(new DnD2Scale(this))
			addInteractor(new DnD2MovePoint(this))
			addInteractor(new DnD2MoveCtrlPoint(this))
			addInteractor(new DnD2Rotate(this))
			addInteractor(new DnD2ArcAngle(this))
		}catch{case ex: Throwable => BadaboomCollector.INSTANCE.add(ex)}
	}


	/**
	 * Removes all the selected views.
	 * @since 3.0
	 */
	def clear() {
		if(selection.nonEmpty) {
			selection.foreach{view =>
				MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]),
											classOf[Shape2BorderMapping])}
			selection.clear
			setActivated(false)
		}
	}


	override def getPickableAt(x : Double, y : Double) : Pickable = {
		if(activated) {
			var pickable : Option[Pickable] = getHandlerAt(x, y, _scaleHandlers)

			if(pickable.isEmpty && _rotHandler.contains(x, y))
				pickable = Some(_rotHandler)

			if(pickable.isEmpty)
				pickable = getHandlerAt(x, y, _mvPtHandlers)

			if(pickable.isEmpty)
				pickable = getHandlerAt(x, y, _ctrlPt1Handlers)

			if(pickable.isEmpty)
				pickable = getHandlerAt(x, y, _ctrlPt2Handlers)

//			if(pickable.isEmpty && _frameArcHandler!=null && _frameArcHandler.contains(x2, y2))
//				pickable = Some(_frameArcHandler)

			if(pickable.isEmpty && _arcHandlerStart.contains(x, y))
				pickable = Some(_arcHandlerStart)

			if(pickable.isEmpty && _arcHandlerEnd.contains(x, y))
				pickable = Some(_arcHandlerEnd)

			return pickable.orNull
		}
		return null
	}


	private def getHandlerAt[T <: IHandler[_]](x : Double, y : Double, handlers : ListBuffer[T]) : Option[T] =
		handlers.find{handler => handler.contains(x, y)}


	override def getPickerAt(x : Double, y : Double) : Picker = null


	// Supposing that there is no handler outside the border.
	override def contains(obj : Object) = obj.isInstanceOf[IHandler[_]]
}


/** Maps a DnD interaction to an action that changes the arc angles. */
private sealed class DnD2ArcAngle(ins : Border) extends InteractorImpl[ModifyShapeProperty, DnD, Border](ins, true, classOf[ModifyShapeProperty], classOf[DnD]) {
	/** The gravity centre used for the rotation. */
	var gc : IPoint = _

	/** Defines whether the current handled shape is rotated. */
	var isRotated = false

	/** The current handled shape. */
	var shape : IShape = _

	var gap : IPoint = ShapeFactory.createPoint


	def initAction() {
		val drawing = instrument.canvas.getDrawing

		if(drawing.getSelection.size==1) {
			shape = drawing.getSelection.getShapeAt(0)
			val rotAngle = shape.getRotationAngle
			var pCentre = interaction.getStartObject.asInstanceOf[IHandler[_]].getCentre
			var pt : IPoint = interaction.getStartPt
			gc = instrument.getAdaptedOriginPoint(shape.getGravityCentre)

			if(LNumber.equalsDouble(rotAngle, 0.0))
				isRotated = false
			else {
				pt = pt.rotatePoint(gc, -rotAngle)
				pCentre = pCentre.rotatePoint(gc, -rotAngle)
				isRotated = true
			}

			gap.setPoint(pt.getX-pCentre.getX, pt.getY-pCentre.getY)

			if(interaction.getStartObject==instrument.arcHandlerStart)
				action.setProperty(ShapeProperties.ARC_START_ANGLE)
			else
				action.setProperty(ShapeProperties.ARC_END_ANGLE)

			action.setGroup(drawing.getSelection.duplicateDeep(false))
		}
	}


	override def updateAction() {
		var pt : IPoint = instrument.getAdaptedOriginPoint(interaction.getEndPt)

		if(isRotated)
			pt = pt.rotatePoint(gc, -shape.getRotationAngle)

		action.setValue(computeAngle(ShapeFactory.createPoint(pt.getX-gap.getX, pt.getY-gap.getY)))
	}


	private def computeAngle(position : IPoint) : Double = {
		val angle = math.acos((position.getX-gc.getX)/position.distance(gc))

		if(position.getY>gc.getY)
			 2*math.Pi - angle
		else angle
	}


	override def isConditionRespected = interaction.getStartObject==instrument.arcHandlerEnd || interaction.getStartObject==instrument.arcHandlerStart
}



/**
 * This link maps a DnD interaction on a rotation handler to an action that rotates the selected shapes.
 */
private sealed class DnD2Rotate(ins : Border) extends InteractorImpl[RotateShapes, DnD, Border](ins, true, classOf[RotateShapes], classOf[DnD]) {
	/** The point corresponding to the 'press' position. */
	var p1 : IPoint = _

	/** The gravity centre used for the rotation. */
	var gc : IPoint = _


	def initAction() {
		val drawing = instrument.canvas.getDrawing
		p1 = instrument.getAdaptedOriginPoint(interaction.getStartPt)
		gc = drawing.getSelection.getGravityCentre
		action.setGravityCentre(gc)
		action.setShape(drawing.getSelection.duplicateDeep(false))
	}


	override def updateAction() {
		action.setRotationAngle(gc.computeRotationAngle(p1, instrument.getAdaptedOriginPoint(interaction.getEndPt)))
	}

	override def isConditionRespected = interaction.getStartObject==instrument.rotHandler
}



/**
 * This link maps a DnD interaction on a move control point handler to an action that moves the selected control point.
 */
private sealed class DnD2MoveCtrlPoint(ins : Border) extends InteractorImpl[MoveCtrlPoint, DnD, Border](ins, true, classOf[MoveCtrlPoint], classOf[DnD]) {
	/** The original coordinates of the moved point. */
	var sourcePt : IPoint = _


	override def initAction() {
		val group = instrument.canvas.getDrawing.getSelection

		if(group.size==1 && group.getShapeAt(0).isInstanceOf[IControlPointShape]) {
			val handler = ctrlPtHandler.get
			sourcePt = ShapeFactory.createPoint(handler.getCentre)
			action.setIndexPt(handler.getIndexPt)
			action.setShape(group.getShapeAt(0).asInstanceOf[IControlPointShape])
			action.setIsFirstCtrlPt(instrument.ctrlPt1Handlers.contains(interaction.getStartObject))
		}
	}


	override def updateAction() {
		super.updateAction
		val startPt = interaction.getStartPt
		val endPt 	= interaction.getEndPt
		val x 		= sourcePt.getX + endPt.getX-startPt.getX
		val y 		= sourcePt.getY + endPt.getY-startPt.getY
		action.setNewCoord(instrument.getAdaptedGridPoint(ShapeFactory.createPoint(x, y)))
	}


	override def isConditionRespected = ctrlPtHandler.isDefined


	/**
	 * @return The selected move control point handler or null.
	 * @since 3.0
	 */
	private def ctrlPtHandler : Option[CtrlPointHandler] = {
		val obj = interaction.getStartObject

		obj.isInstanceOf[CtrlPointHandler] &&
		(instrument.ctrlPt1Handlers.contains(obj) || instrument.ctrlPt2Handlers.contains(obj)) match {
			case true => Some(obj.asInstanceOf[CtrlPointHandler])
			case false => None
		}
	}
}



/**
 * This link maps a DnD interaction on a move point handler to an action that moves the selected point.
 */
private sealed class DnD2MovePoint(ins : Border) extends InteractorImpl[MovePointShape, DnD, Border](ins, true, classOf[MovePointShape], classOf[DnD]) {
	/** The original coordinates of the moved point. */
	var sourcePt : IPoint = _


	override def initAction() {
		val group = instrument.canvas.getDrawing.getSelection

		if(group.size==1 && group.getShapeAt(0).isInstanceOf[IModifiablePointsShape]) {
			val handler = movePtHandler.get
			sourcePt = ShapeFactory.createPoint(handler.getCentre)
			action.setIndexPt(handler.getIndexPt)
			action.setShape(group.getShapeAt(0).asInstanceOf[IModifiablePointsShape])
		}
	}


	override def updateAction() {
		super.updateAction
		val startPt = interaction.getStartPt
		val endPt 	= interaction.getEndPt
		val x 		= sourcePt.getX + endPt.getX-startPt.getX
		val y 		= sourcePt.getY + endPt.getY-startPt.getY
		action.setNewCoord(instrument.getAdaptedGridPoint(ShapeFactory.createPoint(x, y)))
	}


	override def isConditionRespected = movePtHandler.isDefined


	/**
	 * @return The selected move point handler or null.
	 * @since 3.0
	 */
	private def movePtHandler : Option[MovePtHandler] = {
		val obj = interaction.getStartObject
		obj.isInstanceOf[MovePtHandler] && instrument.mvPtHandlers.contains(obj) match {
			case true => Some(obj.asInstanceOf[MovePtHandler])
			case false => None
		}
	}
}



/**
 * This link maps a DnD interaction on a scale handler to an action that scales the selection.
 */
private sealed class DnD2Scale(ins : Border) extends InteractorImpl[ScaleShapes, DnD, Border](ins, true, classOf[ScaleShapes], classOf[DnD]) {
	/** The point corresponding to the 'press' position. */
	var p1 : IPoint = _

	/** The x gap (gap between the pressed position and the targeted position) of the X-scaling. */
	var xGap : Double = _

	/** The y gap (gap between the pressed position and the targeted position) of the Y-scaling. */
	var yGap : Double = _


	private def setXGap(refPosition : Position, tl : IPoint, br : IPoint) {
		refPosition match {
			case Position.NW | Position.SW | Position.WEST => xGap = p1.getX - br.getX
			case Position.NE | Position.SE | Position.EAST => xGap = tl.getX - p1.getX
			case _ => xGap = 0.0
		}
	}

	private def setYGap(refPosition : Position, tl : IPoint, br : IPoint) {
		refPosition match {
			case Position.NW | Position.NE | Position.NORTH => yGap = p1.getY - br.getY
			case Position.SW | Position.SE | Position.SOUTH => yGap = tl.getY - p1.getY
			case _ => yGap = 0.0
		}
	}


	override def initAction() {
		val drawing = instrument.canvas.getDrawing
		val refPosition = scaleHandler.get.getPosition.getOpposite
		val br = drawing.getSelection.getBottomRightPoint
		val tl = drawing.getSelection.getTopLeftPoint

		p1 = instrument.canvas.getMagneticGrid.getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getStartPt))

		setXGap(refPosition, tl, br)
		setYGap(refPosition, tl, br)
		action.setDrawing(drawing)
		action.setShape(drawing.getSelection.duplicateDeep(false))
		action.refPosition = refPosition
	}


	override def updateAction() {
		super.updateAction

		val pt = instrument.getAdaptedGridPoint(interaction.getEndPt)
		val refPosition = action.refPosition.get

		if(refPosition.isSouth)
			action.newY = pt.getY + yGap
		else if(refPosition.isNorth)
			action.newY = pt.getY - yGap

		if(refPosition.isWest)
			action.newX = pt.getX - xGap
		else if(refPosition.isEast)
			action.newX = pt.getX + xGap
	}


	override def isConditionRespected = scaleHandler.isDefined


	override def interimFeedback() {
		super.interimFeedback
		action.refPosition.get match {
			case Position.EAST => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR))
			case Position.NE => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR))
			case Position.NORTH => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR))
			case Position.NW => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR))
			case Position.SE => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR))
			case Position.SOUTH => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR))
			case Position.SW => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR))
			case Position.WEST => instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR))
		}
	}


	private def scaleHandler : Option[ScaleHandler] = {
		val obj = interaction.getStartObject

		obj.isInstanceOf[ScaleHandler] && instrument.scaleHandlers.contains(obj) match {
			case true => Some(obj.asInstanceOf[ScaleHandler])
			case false => None
		}
	}
}
