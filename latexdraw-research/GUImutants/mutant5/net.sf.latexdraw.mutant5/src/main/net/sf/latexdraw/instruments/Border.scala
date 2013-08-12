package net.sf.latexdraw.instruments

import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Cursor
import java.awt.Graphics2D
import scala.collection.mutable.ListBuffer
import org.malai.instrument.Link
import org.malai.instrument.Instrument
import org.malai.interaction.library.DnD
import org.malai.mapping.MappingRegistry
import org.malai.picking.Pickable
import org.malai.picking.Picker
import net.sf.latexdraw.actions.shape.MoveCtrlPoint
import net.sf.latexdraw.actions.shape.MovePointShape
import net.sf.latexdraw.actions.shape.ModifyShapeProperty
import net.sf.latexdraw.actions.shape.RotateShapes
import net.sf.latexdraw.actions.shape.ScaleShapes
import net.sf.latexdraw.actions.shape.ShapeProperties
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.handlers.ArcAngleHandler
import net.sf.latexdraw.glib.handlers.CtrlPointHandler
import net.sf.latexdraw.glib.handlers.IHandler
import net.sf.latexdraw.glib.handlers.MovePtHandler
import net.sf.latexdraw.glib.handlers.RotationHandler
import net.sf.latexdraw.glib.handlers.ScaleHandler
import net.sf.latexdraw.glib.models.interfaces.IShape.Position
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IArc
import net.sf.latexdraw.glib.models.interfaces.IControlPointShape
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewArc
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewBezierCurve
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewModifiablePtsShape
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape
import net.sf.latexdraw.mapping.Shape2BorderMapping
import net.sf.latexdraw.util.LNumber
import net.sf.latexdraw.glib.models.impl.LDrawing
import org.malai.action.Action
import net.sf.latexdraw.actions.shape.RotateShapes
import net.sf.latexdraw.actions.shape.TranslateShapes

/**
 * This instrument manages the selected views.<br>
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
class Border(val canvas : ICanvas) extends Instrument with Picker {
	/** The stroke uses by the border to display its bounding rectangle. */
	val stroke = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, Array(7, 7), 0)

	/** The selected views. */
	protected val _selection : ListBuffer[IViewShape] = new ListBuffer()

	/** The rectangle uses to show the selection. */
	protected val _border : Rectangle2D = new Rectangle2D.Double()

	/** The handlers that scale shapes. */
	protected val _scaleHandlers : ListBuffer[IHandler] = new ListBuffer()

	/** The handlers that move points. */
	protected lazy val _mvPtHandlers : ListBuffer[IHandler] = new ListBuffer()

	/** The handlers that move first control points. */
	protected lazy val _ctrlPt1Handlers : ListBuffer[IHandler] = new ListBuffer()

	/** The handlers that move second control points. */
	protected lazy val _ctrlPt2Handlers : ListBuffer[IHandler] = new ListBuffer()

//	/** The handler that sets the arc frame. */
//	protected lazy val frameArcHandler : FrameArcHandler = new FrameArcHandler()

	/** The handler that sets the start angle of an arc. */
	protected lazy val _arcHandlerStart : ArcAngleHandler = new ArcAngleHandler(true)

	/** The handler that sets the end angle of an arc. */
	protected lazy val _arcHandlerEnd : ArcAngleHandler = new ArcAngleHandler(false)

	/** The handler that rotates shapes. */
	protected val _rotHandler : IHandler = new RotationHandler()

	protected var _metaCustomiser : MetaShapeCustomiser = null

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


	//override def interimFeedback() {
		//canvas.setCursor(Cursor.getDefaultCursor)
	//}


	override def onActionDone(action:Action) {
		if(_metaCustomiser!=null) {
			action match {
				case _:RotateShapes => _metaCustomiser.rotationCustomiser.update()
				case _:ModifyShapeProperty => _metaCustomiser.arcCustomiser.update()
				case _:MoveCtrlPoint => _metaCustomiser.dimPosCustomiser.update()
				case _:MovePointShape => _metaCustomiser.dimPosCustomiser.update()
				case _:ScaleShapes => _metaCustomiser.dimPosCustomiser.update()
				case _ =>
			}
		}
	}


	/**
	 * Updates the bounding rectangle using the selected views.
	 * @since 3.0
	 */
	def update() {
		if(!isActivated()) return
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
		_scaleHandlers.foreach{handler => handler.updateFromShape(_border)}
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
			val sh = _selection.apply(0).getShape

			if(sh.isInstanceOf[IArc]) {
				val arc = sh.asInstanceOf[IArc]
				_arcHandlerStart.updateFromArc(arc, canvas.getZoom)
				_arcHandlerEnd.updateFromArc(arc, canvas.getZoom)
			}
		}
	}


	/**
	 * Updates the handlers that move control points.
	 * @since 3.0
	 */
	private def updateCtrlMvHandlers() {
		if(isCtrlPtMvHandlersShowable) {
			val sh = _selection.apply(0).getShape

			if(sh.isInstanceOf[IControlPointShape])
				// Lazy initialisation
				initialiseCtrlMvHandlers(sh.asInstanceOf[IControlPointShape])
		}
	}


	private def initialiseCtrlMvHandlers(cps : IControlPointShape) {
		val zoom  = canvas.getZoom
		val nbPts = cps.getNbPoints
		var pt : IPoint = null

		// Adding missing handlers.
		if(_ctrlPt1Handlers.size<nbPts)
			for(i <- _ctrlPt1Handlers.size to nbPts-1) {
				_ctrlPt1Handlers += new CtrlPointHandler(i)
				_ctrlPt2Handlers += new CtrlPointHandler(i)
			}
		// Removing extra handlers.
		else if(_ctrlPt1Handlers.size>nbPts)
			while(_ctrlPt1Handlers.size>nbPts) {
				_ctrlPt1Handlers.remove(0)
				_ctrlPt2Handlers.remove(0)
			}

		// Updating handlers.
		for(i <- 0 to _ctrlPt1Handlers.size-1) {
			pt = cps.getFirstCtrlPtAt(i)
			_ctrlPt1Handlers.apply(i).setPoint(pt.getX*zoom, pt.getY*zoom)
			pt = cps.getSecondCtrlPtAt(i)
			_ctrlPt2Handlers.apply(i).setPoint(pt.getX*zoom, pt.getY*zoom)
		}
	}



	/**
	 * Updates the handlers that move points.
	 * @since 3.0
	 */
	private def updateMvHandlers() {
		if(isPtMvHandlersShowable) {
			val sh = _selection.apply(0).getShape

			if(sh.isInstanceOf[IModifiablePointsShape]) {
				val pts   = sh.asInstanceOf[IModifiablePointsShape]
				val nbPts = pts.getNbPoints
				val zoom  = canvas.getZoom
				var pt : IPoint = null

				if(_mvPtHandlers.size<nbPts)
					for(i <- _mvPtHandlers.size to nbPts-1)
						_mvPtHandlers += new MovePtHandler(i)
				else if(_mvPtHandlers.size>nbPts)
						while(_mvPtHandlers.size>nbPts)
							_mvPtHandlers.remove(0)

				for(i <- 0 to _mvPtHandlers.size-1) {
					pt = pts.getPtAt(i)
					_mvPtHandlers.apply(i).setPoint(pt.getX*zoom, pt.getY*zoom)
				}
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
		_scaleHandlers.foreach{handler => handler.paint(g)}
		_rotHandler.paint(g)

//		if(isFrameArcHandlerShowable())
//			frameArcHandler.paint(g)

		if(isArcHandlerShowable) {
			_arcHandlerStart.paint(g)
			_arcHandlerEnd.paint(g)
		}

		if(isPtMvHandlersShowable) {
			_mvPtHandlers.foreach{mvHandler => mvHandler.paint(g)}

			if(isCtrlPtMvHandlersShowable) {
				_ctrlPt1Handlers.foreach{handler => handler.paint(g)}
				_ctrlPt2Handlers.foreach{handler => handler.paint(g)}
			}
		}
	}



	/**
	 * @return True if the control move point handlers can be painted.
	 */
	protected def isCtrlPtMvHandlersShowable() = _selection.size==1 && _selection.apply(0).isInstanceOf[IViewBezierCurve]


	/**
	 * @return True if the move point handlers can be painted.
	 */
	protected def isPtMvHandlersShowable() = _selection.size==1 && _selection.apply(0).isInstanceOf[IViewModifiablePtsShape]


	/**
	 * @return True if the arc handlers can be painted.
	 */
	protected def isArcHandlerShowable() = _selection.size==1 && _selection.apply(0).isInstanceOf[IViewArc]


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
			MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]), classOf[Shape2BorderMapping])
			update
		}
	}


	/**
	 * @return the selected views. Cannot be null.
	 * @since 3.0
	 */
	def selection = _selection


	override def initialiseLinks() {
		try{
			addLink(new DnD2Scale(this))
			addLink(new DnD2MovePoint(this))
			addLink(new DnD2MoveCtrlPoint(this))
			addLink(new DnD2Rotate(this))
			addLink(new DnD2ArcAngle(this))
		}catch{case ex => BadaboomCollector.INSTANCE.add(ex)}
	}


	/**
	 * Removes all the selected views.
	 * @since 3.0
	 */
	def clear() {
		if(!selection.isEmpty) {
			selection.foreach{view =>
				MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, classOf[IShape]), classOf[Shape2BorderMapping])}

			selection.clear
			setActivated(false)
		}
	}


	override def getPickableAt(x : Double, y : Double) : Pickable = {
		var pickable : Option[Pickable] = None

		if(activated) {
			val zoom = canvas.getZoom
			val x2 = x*zoom
			val y2 = y*zoom
			pickable = getHandlerAt(x2, y2, _scaleHandlers)

			if(pickable.isEmpty && _rotHandler.contains(x2, y2))
				pickable = Some(_rotHandler)

			if(pickable.isEmpty)
				pickable = getHandlerAt(x2, y2, _mvPtHandlers)

			if(pickable.isEmpty)
				pickable = getHandlerAt(x2, y2, _ctrlPt1Handlers)

			if(pickable.isEmpty)
				pickable = getHandlerAt(x2, y2, _ctrlPt2Handlers)

//			if(pickable.isEmpty && _frameArcHandler!=null && _frameArcHandler.contains(x2, y2))
//				pickable = Some(_frameArcHandler)

			if(pickable.isEmpty && _arcHandlerStart!=null && _arcHandlerStart.contains(x2, y2))
				pickable = Some(_arcHandlerStart)

			if(pickable.isEmpty && _arcHandlerEnd!=null && _arcHandlerEnd.contains(x2, y2))
				pickable = Some(_arcHandlerEnd)
		}

		if(pickable.isDefined)
			return pickable.get
		else return null
	}



	private def getHandlerAt(x : Double, y : Double, handlers : ListBuffer[IHandler]) : Option[IHandler] = {
		handlers match {
			case null => None
			case _ => handlers.find{handler => handler.contains(x, y)}
		}
	}


	override def getPickerAt(x : Double, y : Double) : Picker = null


	// Supposing that there is no handler outside the border.
	override def contains(obj : Object) = obj.isInstanceOf[IHandler]
}


/** Maps a DnD interaction to an action that changes the arc angles. */
private sealed class DnD2ArcAngle(ins : Border) extends Link[ModifyShapeProperty, DnD, Border](ins, true, classOf[ModifyShapeProperty], classOf[DnD]) {
	/** The point corresponding to the 'press' position. */
	private var p1 : IPoint = null

	/** The gravity centre used for the rotation. */
	private var gc : IPoint = null

	/** Defines whether the current handled shape is rotated. */
	private var isRotated = false

	/** The current handled shape. */
	private var shape : IShape = null

	private var gap : IPoint = DrawingTK.getFactory.createPoint


	def initAction() {
		val drawing = instrument.canvas.getDrawing

		if(drawing.getSelection.size()==1) {
			shape = drawing.getSelection().getShapeAt(0)
			val rotAngle = shape.getRotationAngle
			var pCentre = interaction.getStartObject.asInstanceOf[IHandler].getCentre
			var pt = DrawingTK.getFactory.createPoint(interaction.getStartPt)
			gc = shape.getGravityCentre
			gc = DrawingTK.getFactory.createPoint(gc.getX*instrument.canvas.getZoom, gc.getY*instrument.canvas.getZoom)

			if(LNumber.INSTANCE.equals(rotAngle, 0))
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

			action.setGroup(drawing.getSelection.duplicate.asInstanceOf[IGroup])
		}
	}


	override def updateAction() {
		var pt = DrawingTK.getFactory.createPoint(interaction.getEndPt)

		if(isRotated)
			pt = pt.rotatePoint(gc, -shape.getRotationAngle)

		action.setValue(computeAngle(DrawingTK.getFactory.createPoint(pt.getX-gap.getX, pt.getY-gap.getY)))
	}


	private def computeAngle(position : IPoint) : Double = {
		val angle = math.acos((position.getX-gc.getX)/position.distance(gc))

		if(position.getY>gc.getY)
			 2*math.Pi - angle
		else angle
	}


	override def isConditionRespected() = interaction.getStartObject==instrument.arcHandlerEnd || interaction.getStartObject==instrument.arcHandlerStart
}



/**
 * This link maps a DnD interaction on a rotation handler to an action that rotates the selected shapes.
 */
private sealed class DnD2Rotate(ins : Border) extends Link[RotateShapes, DnD, Border](ins, true, classOf[RotateShapes], classOf[DnD]) {
	/** The point corresponding to the 'press' position. */
	private var p1 : IPoint = null

	/** The gravity centre used for the rotation. */
	private var gc : IPoint = null


	def initAction() {
		val drawing = instrument.canvas.getDrawing
		p1 = DrawingTK.getFactory.createPoint(instrument.canvas.getZoomedPoint(interaction.getStartPt))
		gc = drawing.getSelection.getGravityCentre
		action.setGravityCentre(gc)
		action.setShape(drawing.getSelection.duplicate)
	}


	override def updateAction() {
		val p2 = DrawingTK.getFactory.createPoint(instrument.canvas.getZoomedPoint(interaction.getEndPt))
		action.setRotationAngle(gc.computeRotationAngle(p1, p2))
	}

	override def isConditionRespected() = interaction.getStartObject==instrument.rotHandler
}



/**
 * This link maps a DnD interaction on a move control point handler to an action that moves the selected control point.
 */
private sealed class DnD2MoveCtrlPoint(ins : Border) extends Link[MoveCtrlPoint, DnD, Border](ins, true, classOf[MoveCtrlPoint], classOf[DnD]) {
	/** The original coordinates of the moved point. */
	private var sourcePt : IPoint = null


	override def initAction() {
		val group = instrument.canvas.getDrawing.getSelection

		if(group.size==1 && group.getShapeAt(0).isInstanceOf[IControlPointShape]) {
			val handler = ctrlPtHandler.get
			sourcePt = DrawingTK.getFactory.createPoint(handler.getCentre)
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

		action.setNewCoord(instrument.canvas.getMagneticGrid.getTransformedPointToGrid(instrument.canvas.getZoomedPoint(x, y)))
	}


	override def isConditionRespected() = ctrlPtHandler.isDefined


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
private sealed class DnD2MovePoint(ins : Border) extends Link[MovePointShape, DnD, Border](ins, true, classOf[MovePointShape], classOf[DnD]) {
	/** The original coordinates of the moved point. */
	private var sourcePt : IPoint = null


	override def initAction() {
		val group = instrument.canvas.getDrawing.getSelection

		if(group.size==1 && group.getShapeAt(0).isInstanceOf[IModifiablePointsShape]) {
			val handler = movePtHandler.get
			sourcePt = DrawingTK.getFactory.createPoint(handler.getCentre)
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

		action.setNewCoord(instrument.canvas.getMagneticGrid.getTransformedPointToGrid(instrument.canvas.getZoomedPoint(x, y)))
	}


	override def isConditionRespected() = movePtHandler.isDefined


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
private sealed class DnD2Scale(ins : Border) extends Link[ScaleShapes, DnD, Border](ins, true, classOf[ScaleShapes], classOf[DnD]) {
	/** The point corresponding to the 'press' position. */
	private var p1 : IPoint = null

	/** The x gap (gap between the pressed position and the targeted position) of the X-scaling. */
	private var xGap : Double = 0.0

	/** The y gap (gap between the pressed position and the targeted position) of the Y-scaling. */
	private var yGap : Double = 0.0


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
		action.setShape(drawing.getSelection.duplicate.asInstanceOf[IGroup])
		action.refPosition = refPosition
	}


	override def updateAction() {
		super.updateAction

		val pt = instrument.canvas.getMagneticGrid.getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getEndPt))
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


	override def isConditionRespected() = scaleHandler.isDefined


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
