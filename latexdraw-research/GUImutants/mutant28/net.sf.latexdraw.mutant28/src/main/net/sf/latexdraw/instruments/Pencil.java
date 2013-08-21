package net.sf.latexdraw.instruments;

import java.awt.Point;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;

import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.actions.shape.InitTextSetter;
import net.sf.latexdraw.actions.shape.InsertPicture;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.PictureFilter;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.interaction.library.Press;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.swing.instrument.library.WidgetZoomer;
import org.malai.swing.interaction.library.AbortableDnD;
import org.malai.swing.interaction.library.MultiClick;

/**
 * This instrument allows to draw shapes.<br>
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
 * 05/13/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Pencil extends Instrument {
	/** The magnetic grid used to create shapes. */
	protected LMagneticGrid grid;

	/** The instrument used to add and modify texts. */
	protected TextSetter textSetter;

	/** The current editing choice (rectangle, ellipse, etc.) of the instrument. */
	protected EditionChoice currentChoice;

	/** The canvas that contains the shapes' views. */
	protected ICanvas canvas;

	/** The zoomer that is used to give the zoom level to compute coordinates of the created shapes. */
	protected WidgetZoomer zoomer;

	/** The file chooser used to select pictures. */
	protected JFileChooser pictureFileChooser;

	protected IGroup groupParams;


	/**
	 * Creates a pencil.
	 * @param canvas The canvas wherein the shapes are drawn.
	 * @param zoomer The instrument zoomer used to create shapes.
	 * @param grid The magnetic grid used to create shapes.
	 * @param textSetter The instrument used to add and modify texts.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public Pencil(final ICanvas canvas, final WidgetZoomer zoomer, final LMagneticGrid grid, final TextSetter textSetter) {
		super();

		final IShapeFactory factory = DrawingTK.getFactory();
		groupParams		= factory.createGroup(false);
		this.textSetter = Objects.requireNonNull(textSetter);
		this.grid		= Objects.requireNonNull(grid);
		this.canvas 	= Objects.requireNonNull(canvas);
		this.zoomer		= Objects.requireNonNull(zoomer);
		currentChoice 	= EditionChoice.RECT;
		groupParams.addShape(factory.createRectangle(false));
		groupParams.addShape(factory.createDot(factory.createPoint(), false));
		groupParams.addShape(factory.createGrid(false, factory.createPoint()));
		groupParams.addShape(factory.createAxes(false, factory.createPoint()));
		groupParams.addShape(factory.createText(false));
		groupParams.addShape(factory.createCircleArc(false));
		groupParams.addShape(factory.createPolyline(false));
		groupParams.addShape(factory.createBezierCurve(false));
		groupParams.addShape(factory.createFreeHand(factory.createPoint(), false));
	}


	@Override
	public void setActivated(final boolean activated) {
		if(this.activated!=activated)
			super.setActivated(activated);
	}


	@Override
	public void interimFeedback() {
		canvas.setTempView(null);
		canvas.refresh();
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Press2AddShape(this));
			addLink(new Press2AddText(this, false));
			addLink(new Press2InsertPicture(this, false));
			addLink(new DnD2AddShape(this, false));
			//Mutant28
			//addLink(new MultiClic2AddShape(this, false));
			addLink(new Press2InitTextSetter(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The shape that contains the parameters of the pencil. These parameters
	 * will be used for the creation of new shapes.
	 * @since 3.0
	 */
	public IGroup getGroupParams() {
		return groupParams;
	}


	/**
	 * @return An instance of a shape configured (thickness, colours, etc.) with the parameters of the pencil.
	 * @since 3.0
	 */
	public IShape createShapeInstance() {
		final IShape shape = getCurrentChoice().createShapeInstance();
		setShapeParameters(shape);
		return shape;
	}


	/**
	 * Configures the given shape with the parameters (e.g. thickness, colours, etc.) of the pencil.
	 * @param shape The shape to configure.
	 * @since 3.0
	 */
	public void setShapeParameters(final IShape shape) {
		if(shape==null)
			return ;

		if(shape instanceof IModifiablePointsShape) {
			IModifiablePointsShape mod = (IModifiablePointsShape)shape;
			mod.addPoint(DrawingTK.getFactory().createPoint());
			mod.addPoint(DrawingTK.getFactory().createPoint());
		}

//FIXME Should use copy operations to copy these parameters.
		shape.setLineColour(groupParams.getLineColour());

		if(shape.isArrowable()) {
			shape.setArrowStyle(groupParams.getArrowStyle(0), 0);
			shape.setArrowStyle(groupParams.getArrowStyle(1), 1);
		}
		if(shape.isThicknessable())
			shape.setThickness(groupParams.getThickness());
		if(shape.isBordersMovable())
			shape.setBordersPosition(groupParams.getBordersPosition());
		if(shape.isInteriorStylable())
			shape.copy(groupParams);
		if(shape.isDbleBorderable()) {
			shape.setHasDbleBord(groupParams.hasDbleBord());
			shape.setDbleBordCol(groupParams.getDbleBordCol());
			shape.setDbleBordSep(groupParams.getDbleBordSep());
		}
		if(shape.isShadowable()) {
			shape.setHasShadow(groupParams.hasShadow());
			shape.setShadowCol(groupParams.getShadowCol());
			shape.setShadowAngle(groupParams.getShadowAngle());
			shape.setShadowSize(groupParams.getShadowSize());
		}
		if(shape instanceof Dottable) {
			final Dottable dot = (Dottable)shape;
			dot.setDotStyle(groupParams.getDotStyle());
			dot.setRadius(groupParams.getRadius());
			dot.setDotFillingCol(groupParams.getDotFillingCol());
		}
		if(shape instanceof IArc) {
			final IArc arc = (IArc)shape;
			arc.setAngleStart(groupParams.getAngleStart());
			arc.setAngleEnd(groupParams.getAngleEnd());
			arc.setArcStyle(groupParams.getArcStyle());
		}
		if(shape.isLineStylable())
			shape.setLineStyle(groupParams.getLineStyle());
		// Setting the corner roundness.
		if(shape instanceof ILineArcShape)
			((ILineArcShape)shape).setLineArc(groupParams.getLineArc());
		// Setting the text parameters.
		if(shape instanceof IText) {
			final IText text = (IText)shape;
			text.setTextPosition(groupParams.getTextPosition());
		}
		if(shape instanceof IStandardGrid)
			shape.copy(groupParams);
		if(shape.isArrowable()) {
			shape.setArrowInset(groupParams.getArrowInset());
			shape.setArrowLength(groupParams.getArrowLength());
			shape.setArrowSizeDim(groupParams.getArrowSizeDim());
			shape.setArrowSizeNum(groupParams.getArrowSizeNum());
			shape.setDotSizeDim(groupParams.getDotSizeDim());
			shape.setDotSizeNum(groupParams.getDotSizeNum());
			shape.setBracketNum(groupParams.getBracketNum());
			shape.setRBracketNum(groupParams.getRBracketNum());
			shape.setTBarSizeDim(groupParams.getTBarSizeDim());
			shape.setTBarSizeNum(groupParams.getTBarSizeNum());
		}

		shape.setModified(true);
	}


	/**
	 * @return The file chooser used to select pictures.
	 * @since 3.0
	 */
	public JFileChooser getPictureFileChooser() {
		if(pictureFileChooser==null) {
			pictureFileChooser = new JFileChooser();

			pictureFileChooser.setMultiSelectionEnabled(false);
			pictureFileChooser.setAcceptAllFileFilterUsed(true);
			pictureFileChooser.setFileFilter(new PictureFilter());
		}

		return pictureFileChooser;
	}


	/**
	 * @return The current editing choice.
	 * @since 3.0
	 */
	public EditionChoice getCurrentChoice() {
		return currentChoice;
	}


	/**
	 * Sets the current editing choice.
	 * @param currentChoice The new editing choice to set.
	 * @since 3.0
	 */
	public void setCurrentChoice(final EditionChoice currentChoice) {
		if(currentChoice!=null)
			this.currentChoice = currentChoice;
	}


	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param point The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	public IPoint getAdaptedPoint(final Point point) {
		return grid.getTransformedPointToGrid(zoomer.getZoomable().getZoomedPoint(point));
	}
}




/**
 * This class defines a generic link for the pencil.
 * @author Arnaud Blouin
 * @since 3.0
 * @version 3.0
 */
abstract class PencilLink<I extends Interaction> extends Link<AddShape, I, Pencil> {
	protected IViewShape tmpShape;

	/**
	 * The constructor by default.
	 * @param ins The pencil.
	 * @param exec True: the action will be executed each time the interaction is updated.
	 * @since 3.0
	 */
	protected PencilLink(final Pencil ins, final boolean exec, final Class<I> clazzInteraction) throws InstantiationException, IllegalAccessException {
		super(ins, exec, AddShape.class, clazzInteraction);
	}


	@Override
	public void initAction() {
		final IShape sh = instrument.createShapeInstance();
		tmpShape = View2DTK.getFactory().createView(sh);
		action.setShape(sh);
		action.setDrawing(instrument.canvas.getDrawing());
		instrument.canvas.setTempView(tmpShape);
	}


	@Override
	public void interimFeedback() {
		tmpShape.update();
		instrument.canvas.refresh();
	}
}



/**
 * This link allows to create shapes using a multi-clic interaction.
 */
class MultiClic2AddShape extends PencilLink<MultiClick> {
	/**
	 * The constructor by default.
	 * @param ins The pencil.
	 * @param exec True: the action will be executed each time the interaction is updated.
	 * @since 3.0
	 */
	protected MultiClic2AddShape(final Pencil ins, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(ins, exec, MultiClick.class);
	}


	@Override
	public boolean isInteractionMustBeAborted() {
		// To avoid the overlapping with the DnD2AddShape, the starting interaction must be
		// aborted when the condition is not respected, i.e. when the selected shape type is
		// not devoted to the multi-clic interaction.
		return !isConditionRespected();
	}


	@Override
	public void updateAction() {
		final List<Point> pts	= interaction.getPoints();
		final IPoint currPoint	= instrument.getAdaptedPoint(interaction.getCurrentPosition());
		final IModifiablePointsShape shape = (IModifiablePointsShape)action.shape().get();

		if(shape.getNbPoints()==pts.size() && !interaction.isLastPointFinalPoint())
			shape.addPoint(DrawingTK.getFactory().createPoint(currPoint.getX(), currPoint.getY()), pts.size()-1);
		else
			shape.setPoint(currPoint.getX(), currPoint.getY(), -1);

		// Curves need to be balanced.
		if(shape instanceof IControlPointShape)
			((IControlPointShape)shape).balance();

		shape.setModified(true);
		action.drawing().get().setModified(true);
	}


	@Override
	public void initAction() {
		super.initAction();

		final IShape shape = action.shape().get();

		if(shape instanceof IModifiablePointsShape) {
			final IModifiablePointsShape modShape = (IModifiablePointsShape)shape;
			final IPoint pt = instrument.getAdaptedPoint(interaction.getPoints().get(0));
			modShape.setPoint(pt, 0);
			modShape.setPoint(pt.getX()+1, pt.getY()+1, 1);
		}
	}


	@Override
	public boolean isConditionRespected() {
		final EditionChoice ec = instrument.getCurrentChoice();
		return ec==EditionChoice.POLYGON || ec==EditionChoice.LINES || ec==EditionChoice.BEZIER_CURVE ||
			   ec==EditionChoice.BEZIER_CURVE_CLOSED;
	}


	@Override
	public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
		super.interactionStarts(inter);

		if(instrument.getCurrentChoice()==EditionChoice.POLYGON)
			interaction.setMinPoints(3);
		else
			interaction.setMinPoints(2);
	}
}



/**
 * This link allows to create shapes using a drag-and-drop interaction.
 */
class DnD2AddShape extends PencilLink<AbortableDnD> {
	/**
	 * The constructor by default.
	 * @param ins The pencil.
	 * @param exec True: the action will be executed each time the interaction is updated.
	 * @since 3.0
	 */
	protected DnD2AddShape(final Pencil ins, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(ins, exec, AbortableDnD.class);
	}


	@Override
	public void initAction() {
		super.initAction();

		final IShape shape = action.shape().get();

		if(shape!=null) {
			final EditionChoice ec 	= instrument.getCurrentChoice();
			final IPoint pt = instrument.getAdaptedPoint(interaction.getStartPt());

			// For squares and circles, the centre of the shape is the reference point during the creation.
			if((ec==EditionChoice.SQUARE || ec==EditionChoice.CIRCLE || ec==EditionChoice.CIRCLE_ARC) && shape instanceof IRectangularShape) {
				final IRectangularShape recShape = (IRectangularShape)shape;

				recShape.setPosition(pt.getX()-1, pt.getY()-1);
				recShape.setWidth(2.);
				recShape.setHeight(2.);
			}
			else
				if(ec==EditionChoice.FREE_HAND && shape instanceof IFreehand)
					((IFreehand)shape).getPtAt(0).setPoint(pt.getX(), pt.getY());
				else
					shape.translate(pt.getX(), pt.getY());
		}
	}


	@Override
	public boolean isConditionRespected() {
		final EditionChoice ec = instrument.getCurrentChoice();
		return ec==EditionChoice.RECT || ec==EditionChoice.ELLIPSE || ec==EditionChoice.SQUARE ||
			   ec==EditionChoice.CIRCLE || ec==EditionChoice.RHOMBUS || ec==EditionChoice.TRIANGLE  ||
			   ec==EditionChoice.CIRCLE_ARC || ec==EditionChoice.FREE_HAND;
	}


	@Override
	public void updateAction() {
		final IShape shape 	= action.shape().get();
		final EditionChoice ec = instrument.getCurrentChoice();
		// Getting the points depending on the current zoom.
		final IPoint startPt= instrument.getAdaptedPoint(interaction.getStartPt());
		final IPoint endPt	= instrument.getAdaptedPoint(interaction.getEndPt());

		if((ec==EditionChoice.SQUARE || ec==EditionChoice.CIRCLE  || ec==EditionChoice.CIRCLE_ARC) && shape instanceof IRectangularShape)
			updateShapeFromCentre((IRectangularShape)shape, startPt, endPt.getX());
		else
			if(ec==EditionChoice.FREE_HAND && shape instanceof IFreehand)
				((IFreehand)shape).addPoint(endPt);
			else
				if(shape instanceof IRectangularShape)
					updateShapeFromDiag((IRectangularShape)shape, startPt, endPt);

		shape.setModified(true);
		action.drawing().get().setModified(true);
	}


	/**
	 * @param shape The shape to analyse.
	 * @return The gap that must respect the pencil to not allow shape to disappear when they are too small.
	 * @since 3.0
	 */
	private double getGap(final IShape shape) {
		double gap;

		// These lines are necessary to avoid shape to disappear. It appends when the borders position is INTO. In this case, the
		// minimum radius must be computed using the thickness and the double size.
		if(shape.isBordersMovable() && shape.getBordersPosition()==BorderPos.INTO)
			gap = shape.getThickness() + (shape.isDbleBorderable() && shape.hasDbleBord() ? shape.getDbleBordSep() : 0.);
		else
			gap = 1.;

		return gap;
	}


	private void updateShapeFromCentre(final IRectangularShape shape, final IPoint startPt, final double endX) {
		final double sx = startPt.getX();
		final double sy = startPt.getY();
		final double radius = Math.max(sx<endX ? endX-sx : sx - endX, getGap(shape));

		shape.setPosition(sx-radius, sy+radius);
		shape.setWidth(radius*2.);
		shape.setHeight(radius*2.);
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

		if(Math.abs(v1-v2)>gap)
			if(v1<v2) {
				brx = v2;
				tlx = v1;
			}
			else {
				brx = v1;
				tlx = v2;
			}
		else ok = false;

		v1 = startPt.getY();
		v2 = endPt.getY();

		if(Math.abs(v1-v2)>gap)
			if(v1<v2) {
				bry = v2;
				tly = v1;
			}
			else {
				bry = v1;
				tly = v2;
			}
		else ok = false;

		if(ok) {
			shape.setPosition(tlx, bry);
			shape.setWidth(brx - tlx);
			shape.setHeight(bry - tly);
		}
	}
}



/**
 * Maps a mouse press interaction to an action that asks and adds a picture into the drawing.
 */
class Press2InsertPicture extends Link<InsertPicture, Press, Pencil> {
	protected Press2InsertPicture(final Pencil ins, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(ins, exec, InsertPicture.class, Press.class);
	}

	@Override
	public void initAction() {
		action.setDrawing(instrument.canvas.getDrawing());
		action.setShape(DrawingTK.getFactory().createPicture(true, instrument.getAdaptedPoint(interaction.getPoint())));
		action.setFileChooser(instrument.getPictureFileChooser());
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.getCurrentChoice()==EditionChoice.PICTURE;
	}
}



class Press2AddShape extends PencilLink<Press> {
	protected Press2AddShape(final Pencil ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Press.class);
	}


	@Override
	public void initAction() {
		super.initAction();

		final IShape shape = action.shape().get();

		if(shape instanceof IPositionShape) {
			((IPositionShape)shape).setPosition(instrument.getAdaptedPoint(interaction.getPoint()));
			shape.setModified(true);
		}
	}


	@Override
	public boolean isConditionRespected() {
		final EditionChoice ec = instrument.getCurrentChoice();
		return ec==EditionChoice.GRID || ec==EditionChoice.DOT || ec==EditionChoice.AXES;
	}
}


/**
 * When a user starts to type a text using the text setter and then he clicks somewhere else
 * in the canvas, the text typed must be added (if possible to the canvas) before starting typing
 * a new text.
 */
class Press2AddText extends Link<AddShape, Press, Pencil> {
	/**
	 * Creates the link.
	 */
	protected Press2AddText(final Pencil ins, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(ins, exec, AddShape.class, Press.class);
	}

	@Override
	public void initAction() {
		action.setDrawing(instrument.canvas.getDrawing());
		action.setShape(DrawingTK.getFactory().createText(true, DrawingTK.getFactory().createPoint(instrument.textSetter.relativePoint),
						instrument.textSetter.getTextField().getText()));
	}

	@Override
	public boolean isConditionRespected() {
		// The action is created only when the user uses the text setter and the text field of the text setter is not empty.
		return instrument.textSetter.isActivated() && instrument.textSetter.getTextField().getText().length()>0;
	}
}


/**
 * This link maps a press interaction to activate the instrument
 * that allows to add and modify some texts.
 */
class Press2InitTextSetter extends Link<InitTextSetter, Press, Pencil> {
	protected Press2InitTextSetter(final Pencil ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, InitTextSetter.class, Press.class);
	}

	@Override
	public void initAction() {
		final IPoint adaptedPt = instrument.getAdaptedPoint(interaction.getPoint());
		final double zoom = instrument.zoomer.getZoomable().getZoom();

		action.setText("");
		action.setTextShape(null);
		action.setInstrument(instrument.textSetter);
		action.setTextSetter(instrument.textSetter);
		action.setAbsolutePoint(DrawingTK.getFactory().createPoint(adaptedPt.getX()*zoom, adaptedPt.getY()*zoom));
		action.setRelativePoint(adaptedPt);
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.currentChoice==EditionChoice.TEXT;
	}
}
