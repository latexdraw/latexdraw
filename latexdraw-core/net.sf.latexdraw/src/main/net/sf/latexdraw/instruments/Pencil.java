/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import net.sf.latexdraw.util.LNumber;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.library.AbortableDnD;

import java.awt.geom.Point2D;

/**
 * This instrument allows to draw shapes.
 */
public class Pencil extends CanvasInstrument {
	/** The current editing choice (rectangle, ellipse, etc.) of the instrument. */
	protected EditionChoice currentChoice;

	@Inject
	protected TextSetter textSetter;

	//	protected MLayeredPane layers;

	//	private JFileChooser pictureFileChooser;

	private IGroup groupParams;

	public Pencil() {
		super();
		currentChoice = EditionChoice.RECT;
	}

	//	JFileChooser getPictureFileChooser() {
	//		if(pictureFileChooser==null) {
	//			pictureFileChooser = new JFileChooser();
	//			pictureFileChooser.setMultiSelectionEnabled(false);
	//			pictureFileChooser.setAcceptAllFileFilterUsed(true);
	//			pictureFileChooser.setFileFilter(new PictureFilter());
	//		}
	//		return pictureFileChooser;
	//	}

	public IGroup getGroupParams() {
		if(groupParams == null) {
			groupParams = ShapeFactory.createGroup();
			groupParams.addShape(ShapeFactory.createRectangle());
			groupParams.addShape(ShapeFactory.createDot(ShapeFactory.createPoint()));
			groupParams.addShape(ShapeFactory.createGrid(ShapeFactory.createPoint()));
			groupParams.addShape(ShapeFactory.createAxes(ShapeFactory.createPoint()));
			groupParams.addShape(ShapeFactory.createText());
			groupParams.addShape(ShapeFactory.createCircleArc());
			groupParams.addShape(ShapeFactory.createPolyline());
			groupParams.addShape(ShapeFactory.createBezierCurve());
			groupParams.addShape(ShapeFactory.createFreeHand());
			groupParams.addShape(ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false));
		}
		return groupParams;
	}

	@Override
	public void setActivated(boolean act) {
		if(this.activated != act) super.setActivated(act);
	}

	@Override
	public void interimFeedback() {
		//		canvas.setTempView(null);
		// canvas.refresh();
		if(canvas.getScene() != null) canvas.getScene().setCursor(Cursor.DEFAULT);
	}

	@Override
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		// addInteractor(new DnD2MoveViewport(canvas, this))
		// addInteractor(new Press2AddShape(this))
		// addInteractor(new Press2AddText(this))
		// addInteractor(new Press2InsertPicture(this))
		addInteractor(new DnD2AddShape());
		// addInteractor(new MultiClic2AddShape(this))
		// addInteractor(new Press2InitTextSetter(this))
	}

	/**
	 * @return An instance of a shape configured (thickness, colours, etc.) with
	 * the parameters of the pencil.
	 * @since 3.0
	 */
	public IShape createShapeInstance() {
		return setShapeParameters(currentChoice.createShapeInstance());
	}

	/**
	 * Configures the given shape with the parameters (e.g. thickness, colours,
	 * etc.) of the pencil.
	 * @param shape The shape to configure.
	 * @return The modified shape given as argument.
	 * @since 3.0
	 */
	IShape setShapeParameters(final IShape shape) {
		if(shape instanceof IModifiablePointsShape && !(shape instanceof IFreehand)) {
			final IModifiablePointsShape mod = (IModifiablePointsShape) shape;
			mod.addPoint(ShapeFactory.createPoint());
			mod.addPoint(ShapeFactory.createPoint());
		}

		shape.copy(getGroupParams());
		shape.setModified(true);
		return shape;
	}

	//	/** @return The file chooser used to select pictures. */
	//	JFileChooser pictureFileChooser() {
	//		return pictureFileChooser;
	//	}

	/** @return The current editing choice. */
	public EditionChoice getCurrentChoice() {
		return currentChoice;
	}

	/**
	 * Sets the current editing choice.
	 * @param choice The new editing choice to set.
	 * @since 3.0
	 */
	public void setCurrentChoice(EditionChoice choice) {
		currentChoice = choice;
	}


	private abstract class PencilInteractor<I extends JfxInteraction> extends JfxInteractor<AddShape, I, Pencil> {
		PencilInteractor(final Class<I> clazzInteraction) throws InstantiationException, IllegalAccessException {
			super(Pencil.this, false, AddShape.class, clazzInteraction, Pencil.this.canvas);
		}

		ViewShape<?, ?> tmpShape;

		@Override
		public void initAction() {
			final IShape sh = instrument.createShapeInstance();
			ViewFactory.INSTANCE.createView(sh).ifPresent(v -> {
				tmpShape = v;
				action.setShape(sh);
				action.setDrawing(instrument.canvas.getDrawing());
				//				instrument.canvas.setTempView(tmpShape);
			});
		}

		@Override
		public void interimFeedback() {
			//			if(tmpShape != null) {
			//				tmpShape.update();
			//				instrument.canvas.refresh();
			//			}
		}
	}


	private class DnD2AddShape extends PencilInteractor<AbortableDnD> {
		DnD2AddShape() throws InstantiationException, IllegalAccessException {
			super(AbortableDnD.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.getShape().ifPresent(sh -> interaction.getStartPt().ifPresent(startPt -> {
				final IPoint pt = instrument.getAdaptedPoint(startPt);

				// For squares and circles, the centre of the shape is the reference point during the creation.
				if(sh instanceof ISquaredShape) {
					final ISquaredShape sq = (ISquaredShape) sh;
					sq.setPosition(pt.getX() - 1.0, pt.getY() - 1.0);
					sq.setWidth(2.0);
				}else if(sh instanceof IFreehand) {
					((IFreehand) sh).addPoint(ShapeFactory.createPoint(pt.getX(), pt.getY()));
				}else {
					sh.translate(pt.getX(), pt.getY());
				}
			}));
		}

		@Override
		public void updateAction() {
			action.getShape().ifPresent(sh -> {
				// Getting the points depending on the current zoom.
				final IPoint startPt = instrument.getAdaptedPoint(interaction.getStartPt().orElse(new Point2D.Double()));
				final IPoint endPt = instrument.getAdaptedPoint(interaction.getEndPt().orElse(new Point2D.Double()));

				if(sh instanceof ISquaredShape) {
					updateShapeFromCentre((ISquaredShape) sh, startPt, endPt.getX());
					sh.setModified(true);
					action.getDrawing().ifPresent(drawing -> drawing.setModified(true));
				}else if(sh instanceof IFreehand) {
					final IFreehand fh = (IFreehand) sh;
					final IPoint last = fh.getPtAt(-1);
					if(!LNumber.equalsDouble(last.getX(), endPt.getX(), 0.0001) && !LNumber.equalsDouble(last.getY(), endPt.getY(), 0.0001)) {
						fh.addPoint(endPt);
					}
				}else if(sh instanceof IRectangularShape) {
					updateShapeFromDiag((IRectangularShape) sh, startPt, endPt);
					sh.setModified(true);
					action.getDrawing().ifPresent(drawing -> drawing.setModified(true));
				}
			});
		}

		/**
		 * @param shape The shape to analyse.
		 * @return The gap that must respect the pencil to not allow shape to disappear when they are too small.
		 * @since 3.0
		 */
		private double getGap(final IShape shape) {
			// These lines are necessary to avoid shape to disappear. It appends when the borders position is INTO.
			// In this case,the minimum radius must be computed using the thickness and the double size.
			if(shape.isBordersMovable() && shape.getBordersPosition() == BorderPos.INTO)
				return shape.getThickness() + (shape.isDbleBorderable() && shape.hasDbleBord() ? shape.getDbleBordSep() : 0.0);
			return 1.0;
		}

		private void updateShapeFromCentre(final ISquaredShape shape, final IPoint startPt, final double endX) {
			final double sx = startPt.getX();
			final double radius = Math.max(sx < endX ? endX - sx : sx - endX, getGap(shape));
			shape.setPosition(sx - radius, startPt.getY() + radius);
			shape.setWidth(radius * 2.0);
		}

		private void updateShapeFromDiag(final IRectangularShape shape, final IPoint startPt, final IPoint endPt) {
			final double gap = getGap(shape);
			double v1 = startPt.getX();
			double v2 = endPt.getX();
			double tlx = Double.NaN;
			double tly = Double.NaN;
			double brx = Double.NaN;
			double bry = Double.NaN;
			boolean ok = true;

			if(Math.abs(v1 - v2) > gap) {
				if(v1 < v2) {
					brx = v2;
					tlx = v1;
				}else {
					brx = v1;
					tlx = v2;
				}
			}else ok = false;

			v1 = startPt.getY();
			v2 = endPt.getY();

			if(Math.abs(v1 - v2) > gap) {
				if(v1 < v2) {
					bry = v2;
					tly = v1;
				}else {
					bry = v1;
					tly = v2;
				}
			}else ok = false;

			if(ok) {
				shape.setPosition(tlx, bry);
				shape.setWidth(brx - tlx);
				shape.setHeight(bry - tly);
			}
		}

		@Override
		public boolean isConditionRespected() {
			final EditionChoice ec = instrument.currentChoice;
			return interaction.getButton() == MouseButton.PRIMARY && (ec == EditionChoice.RECT || ec == EditionChoice.ELLIPSE || ec == EditionChoice.SQUARE || ec == EditionChoice.CIRCLE || ec == EditionChoice.RHOMBUS || ec == EditionChoice.TRIANGLE || ec == EditionChoice.CIRCLE_ARC || ec == EditionChoice.FREE_HAND);
		}
	}


	//	class MultiClic2AddShape(pencil:Pencil)extends PencilInteractor[MultiClick](pencil,false,classOf[MultiClick])
	//
	//	{
	//		// To avoid the overlapping with the DnD2AddShape, the starting interaction
	//		must be
	//		// aborted when the condition is not respected, i.e. when the selected shape
	//		type is
	//		// not devoted to the multi-clic interaction.
	//		override def isInteractionMustBeAborted = !isConditionRespected
	//
	//		override def updateAction() {
	//		val pts = interaction.getPoints val currPoint = instrument.getAdaptedPoint(interaction.getCurrentPosition)
	//		val shape = action.shape.get.asInstanceOf[IModifiablePointsShape]
	//
	//		if(shape.getNbPoints == pts.size && !interaction.isLastPointFinalPoint)
	//			shape.addPoint(ShapeFactory.createPoint(currPoint.getX, currPoint.getY), pts.size - 1)
	//		else shape.setPoint(currPoint.getX, currPoint.getY, -1)
	//
	//		// Curves need to be balanced.
	//		shape match {
	//			case shape1:
	//				IControlPointShape =>shape1.balance case _ =>
	//		}
	//
	//		shape.setModified(true) action.drawing.get.setModified(true)
	//	}
	//
	//
	//		override def initAction() {
	//		super.initAction val shape = action.shape.get
	//
	//		shape match {
	//			case modShape:
	//				IModifiablePointsShape =>
	//				val pt = instrument.getAdaptedPoint(interaction.getPoints.get(0)) modShape.setPoint(pt, 0) modShape.setPoint(pt.getX + 1, pt.getY + 1, 1)
	//			case _ =>
	//		}
	//	}
	//
	//		override def
	//		isConditionRespected = instrument.currentChoice == EditionChoice.POLYGON || instrument.currentChoice == EditionChoice.LINES || instrument.currentChoice == EditionChoice.BEZIER_CURVE || instrument.currentChoice == EditionChoice.BEZIER_CURVE_CLOSED
	//
	//		override def interactionStarts(inter:Interaction){
	//		super.interactionStarts(inter) if(instrument.currentChoice == EditionChoice.POLYGON) interaction.setMinPoints(3)
	//		else interaction.setMinPoints(2)
	//	}
	//	}
}

// private sealed class Press2InsertPicture(pencil:Pencil) extends
// InteractorImpl[InsertPicture, Press, Pencil](pencil, false,
// classOf[InsertPicture], classOf[Press]) {
// override def initAction() {
// action.setDrawing(instrument.canvas.getDrawing)
// action.setShape(ShapeFactory.createPicture(instrument.getAdaptedPoint(interaction.getPoint)))
// action.setFileChooser(instrument.pictureFileChooser)
// }
//
// override def isConditionRespected =
// instrument.currentChoice==EditionChoice.PICTURE &&
// interaction.getButton==MouseEvent.BUTTON1
// }
//
//
//
// private sealed class Press2AddShape(pencil:Pencil) extends
// PencilInteractor[Press](pencil, false, classOf[Press]) {
// override def initAction() {
// super.initAction
// val shape = action.shape.get
// shape match {
// case shape1: IPositionShape =>
// shape1.setPosition(instrument.getAdaptedPoint(interaction.getPoint))
// shape.setModified(true)
// case _ =>
// }
// }
//
// override def isConditionRespected =
// (instrument.currentChoice==EditionChoice.GRID ||
// instrument.currentChoice==EditionChoice.DOT ||
// instrument.currentChoice==EditionChoice.AXES) &&
// interaction.getButton==MouseEvent.BUTTON1
// }
//
//
//
// /**
// * When a user starts to type a text using the text setter and then he clicks
// somewhere else
// * in the canvas, the text typed must be added (if possible to the canvas)
// before starting typing
// * a new text.
// */
// private sealed class Press2AddText(pencil:Pencil) extends
// PencilInteractor[Press](pencil, false, classOf[Press]) {
// override def initAction() {
// action.setDrawing(instrument.canvas.getDrawing)
// action.setShape(ShapeFactory.createText(ShapeFactory.createPoint(instrument.textSetter.relativePoint),
// instrument.textSetter.getTextField.getText))
// }
//
// // The action is created only when the user uses the text setter and the text
// field of the text setter is not empty.
// override def isConditionRespected =
// instrument.currentChoice==EditionChoice.TEXT &&
// instrument.textSetter.isActivated &&
// instrument.textSetter.getTextField.getText.length>0
// }
//
//
// /**
// * This link maps a press interaction to activate the instrument
// * that allows to add and modify some texts.
// */
// private sealed class Press2InitTextSetter(pencil:Pencil) extends
// InteractorImpl[InitTextSetter, Press, Pencil](pencil, false,
// classOf[InitTextSetter], classOf[Press]) {
// override def initAction() {
// action.setText("")
// action.setTextShape(null)
// action.setInstrument(instrument.textSetter)
// action.setTextSetter(instrument.textSetter)
// action.setAbsolutePoint(ShapeFactory.createPoint(
// SwingUtilities.convertPoint(instrument.canvas.asInstanceOf[LCanvas],interaction.getPoint,
// instrument.layers)))
// action.setRelativePoint(instrument.getAdaptedPoint(interaction.getPoint))
// }
//
// override def isConditionRespected =
// (instrument.currentChoice==EditionChoice.TEXT||
// instrument.currentChoice==EditionChoice.PLOT) &&
// interaction.getButton==MouseEvent.BUTTON1
// }
