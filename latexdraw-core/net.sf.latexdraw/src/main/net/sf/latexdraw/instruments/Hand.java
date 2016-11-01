/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
 */
package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import javafx.event.EventTarget;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.malai.javafx.action.library.MoveCamera;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.KeysPressure;
import org.malai.javafx.interaction.library.Press;

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.
 * @author Arnaud BLOUIN
 */
public class Hand extends CanvasInstrument {
	@Inject protected MetaShapeCustomiser metaCustomiser;
	@Inject protected TextSetter textSetter;

	public Hand() {
		super();
	}

	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new Press2Select(this));
		// addInteractor(new DnD2Select(this))
		// addInteractor(new DnD2Translate(this))
		addInteractor(new DnD2MoveViewport(this));
		// addInteractor(new DoubleClick2InitTextSetter(this))
		addInteractor(new CtrlA2SelectAllShapes(this));
		// addInteractor(new CtrlU2UpdateShapes(this))
	}

	@Override
	public void setActivated(final boolean activated) {
		if(this.activated != activated) super.setActivated(activated);
	}

	@Override
	public void interimFeedback() {
		// The rectangle used for the interim feedback of the selection is removed.
		// canvas.setTempUserSelectionBorder(null);
		canvas.setCursor(Cursor.DEFAULT);
	}

	// @Override
	// public void onActionDone(final Action action) {
	// if(action instanceof TranslateShapes) {
	// _metaCustomiser.dimPosCustomiser.update();
	// }
	// }

	private static class Press2Select extends JfxInteractor<SelectShapes, Press, Hand> {
		Press2Select(final Hand hand) throws InstantiationException, IllegalAccessException {
			super(hand, false, SelectShapes.class, Press.class, hand.canvas);
		}

		@Override
		public void initAction() {
			action.setDrawing(instrument.canvas.getDrawing());
		}

		@Override
		public void updateAction() {
			final IShape targetSh = ((ViewShape<?, ?>) ((Node) interaction.getTarget()).getParent()).getModel();

			if(interaction.isShiftPressed())
				instrument.canvas.getDrawing().getSelection().getShapes().stream().filter(sh -> sh != targetSh).forEach(sh -> action.addShape(sh));
			else if(interaction.isCtrlPressed()) {
				instrument.canvas.getDrawing().getSelection().getShapes().forEach(sh -> action.addShape(sh));
				action.addShape(targetSh);
			}else action.setShape(targetSh);
		}

		@Override
		public boolean isConditionRespected() {
			final EventTarget obj = interaction.getTarget();
			return obj instanceof Node && ((Node) obj).getParent() instanceof ViewShape<?, ?>;
		}
	}

	private static class CtrlA2SelectAllShapes extends JfxInteractor<SelectShapes, KeysPressure, Hand> {
		CtrlA2SelectAllShapes(final Hand hand) throws InstantiationException, IllegalAccessException {
			super(hand, false, SelectShapes.class, KeysPressure.class, hand.canvas);
		}

		@Override
		public void initAction() {
			instrument.canvas.getDrawing().getShapes().forEach(sh -> action.addShape(sh));
			action.setDrawing(instrument.canvas.getDrawing());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getKeyCodes().size() == 2 && interaction.getKeyCodes().contains(KeyCode.A) && interaction.getKeyCodes().contains(LSystem.INSTANCE.getControlKey());
		}
	}
}


/**
 * Moves the viewport using the hand.
 */
class DnD2MoveViewport extends JfxInteractor<MoveCamera, DnD, CanvasInstrument> {
	private IPoint pt = ShapeFactory.createPoint();

	DnD2MoveViewport(final CanvasInstrument ins) throws IllegalAccessException, InstantiationException {
		super(ins, true, MoveCamera.class, DnD.class, ins.canvas);
	}

	@Override
	public void initAction() {
		action.setScrollPane(instrument.canvas.getScrollPane());
		interaction.getStartPt().ifPresent(start -> pt.setPoint(start.getX(), start.getY()));
	}

	@Override
	public void updateAction() {
		interaction.getStartPt().ifPresent(start -> interaction.getEndPt().ifPresent(end -> {
			final ScrollPane pane = instrument.canvas.getScrollPane();
			action.setPx(pane.getHvalue() - (end.getX() - pt.getX()) / instrument.canvas.getWidth());
			action.setPy(pane.getVvalue() - (end.getY() - pt.getY()) / instrument.canvas.getHeight());
			pt = pt.centralSymmetry(ShapeFactory.createPoint(start));
		}));
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton() == MouseButton.MIDDLE;
	}

	@Override
	public void interimFeedback() {
		instrument.canvas.setCursor(Cursor.MOVE);
	}
}

// private sealed class CtrlU2UpdateShapes(ins:Hand) extends
// InteractorImpl[UpdateToGrid, KeysPressure, Hand](ins, false,
// classOf[UpdateToGrid], classOf[KeysPressure]) {
// override def initAction() {
// action.setShape(instrument.canvas.getDrawing.getSelection.duplicateDeep(false))
// // action.setGrid(instrument.canvas.getMagneticGrid)
// }
//
// override def isConditionRespected =
// //instrument.canvas.getMagneticGrid.isMagnetic &&
// interaction.getKeys.size==2 && interaction.getKeys.contains(KeyEvent.VK_U) &&
// interaction.getKeys.contains(KeyEvent.VK_CONTROL)
// }
//
//
//
// private sealed class DoubleClick2InitTextSetter(ins : Hand) extends
// InteractorImpl[InitTextSetter, DoubleClick, Hand](ins, false,
// classOf[InitTextSetter], classOf[DoubleClick]) {
// override def initAction() {
// var pos:Option[IPoint] = None
//
// interaction.getTarget match {
// case text1: IViewText =>
// val text = text1.getShape.asInstanceOf[IText]
// action.setTextShape(text)
// pos = Some(text.getPosition)
// case plot1:IViewPlot =>
// val plot = plot1.getShape.asInstanceOf[IPlot]
// action.setPlotShape(plot)
// pos = Some(plot.getPosition)
// case _ =>
// }
//
// pos match {
// case Some(position) =>
// val screen = instrument.canvas.asInstanceOf[LCanvas].getVisibleRect
// val zoom = instrument.canvas.getZoom
// val x = instrument.canvas.getOrigin.getX - screen.getX + position.getX * zoom
// val y = instrument.canvas.getOrigin.getY - screen.getY + position.getY * zoom
// action.setInstrument(instrument.textSetter)
// action.setTextSetter(instrument.textSetter)
// action.setAbsolutePoint(ShapeFactory.createPoint(x, y))
// action.setRelativePoint(ShapeFactory.createPoint(position))
// case None =>
// }
// }
//
// override def isConditionRespected =
// interaction.getTarget.isInstanceOf[IViewText] ||
// interaction.getTarget.isInstanceOf[IViewPlot]
// }
//
//
//
// /**
// * This link allows to translate the selected shapes.
// */
// private sealed class DnD2Translate(hand : Hand) extends
// InteractorImpl[TranslateShapes, DnD, Hand](hand, true,
// classOf[TranslateShapes], classOf[DnD]) {
// override def initAction() {
// action.setDrawing(instrument.canvas.getDrawing)
// action.setShape(instrument.canvas.getDrawing.getSelection.duplicateDeep(false))
// }
//
//
// override def updateAction() {
// val startPt = instrument.getAdaptedGridPoint(interaction.getStartPt)
// val endPt = instrument.getAdaptedGridPoint(interaction.getEndPt)
//
// action.setTx(endPt.getX - startPt.getX)
// action.setTy(endPt.getY - startPt.getY)
// }
//
// override def isConditionRespected: Boolean = {
// val startObject = interaction.getStartObject
// val button = interaction.getButton
// return !instrument.canvas.getDrawing.getSelection.isEmpty &&
// (startObject==instrument.canvas && button==MouseEvent.BUTTON3 ||
// startObject.isInstanceOf[IViewShape] && (button==MouseEvent.BUTTON1 ||
// button==MouseEvent.BUTTON3))
// }
//
//
// override def interimFeedback() {
// super.interimFeedback()
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR))
// }
// }
//
// private sealed class DnD2Select(hand : Hand) extends
// InteractorImpl[SelectShapes, DnDWithKeys, Hand](hand, true,
// classOf[SelectShapes], classOf[DnDWithKeys]) {
// /** The is rectangle is used as interim feedback to show the rectangle made
// by the user to select some shapes. */
// val selectionBorder : Rectangle2D = new Rectangle2D.Double()
// var selectedShapes : Buffer[IShape] = _
// var selectedViews : Buffer[IViewShape] = _
//
// override def initAction() {
// action.setDrawing(instrument.canvas.getDrawing)
// selectedShapes = new
// ArrayList(instrument.canvas.getDrawing.getSelection.getShapes)
// selectedViews = instrument.canvas.getBorderInstrument.selection.clone
// }
//
//
// override def updateAction() {
// val start = instrument.getAdaptedOriginPoint(interaction.getPoint)
// val end = instrument.getAdaptedOriginPoint(interaction.getEndPt)
// val minX = math.min(start.getX, end.getX)
// val maxX = math.max(start.getX, end.getX)
// val minY = math.min(start.getY, end.getY)
// val maxY = math.max(start.getY, end.getY)
// val zoom = instrument.canvas.getZoom
// val keys = interaction.getKeys
//
// // Updating the rectangle used for the interim feedback and for the selection
// of shapes.
// selectionBorder.setFrame(minX, minY, Math.max(maxX-minX, 1),
// Math.max(maxY-minY, 1))
// // Cleaning the selected shapes in the action.
// action.setShape(null)
//
// if(keys.contains(KeyEvent.VK_SHIFT))
// selectedViews.filter{view => !view.intersects(selectionBorder)}.foreach{
// view => action.addShape(MappingRegistry.REGISTRY.getSourceFromTarget(view,
// classOf[IShape]))}
// else {
// if(keys.contains(KeyEvent.VK_CONTROL))
// selectedShapes.foreach{sh => action.addShape(sh)}
// if(!selectionBorder.isEmpty)
// instrument.canvas.getViews.foreach{view =>
// if(view.intersects(selectionBorder))
// // Taking the shape in function of the view.
// action.addShape(MappingRegistry.REGISTRY.getSourceFromTarget(view,
// classOf[IShape]))
// }
// }
// }
//
// override def isConditionRespected =
// interaction.getStartObject==instrument.canvas &&
// interaction.getButton==MouseEvent.BUTTON1
//
// override def interimFeedback() {
// instrument.canvas.setTempUserSelectionBorder(selectionBorder)
// instrument.canvas.refresh
// }
// }
//
//
