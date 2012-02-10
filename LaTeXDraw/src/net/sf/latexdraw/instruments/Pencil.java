package net.sf.latexdraw.instruments;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import javax.swing.JFileChooser;

import net.sf.latexdraw.actions.AddShape;
import net.sf.latexdraw.actions.InitTextSetter;
import net.sf.latexdraw.actions.InsertPicture;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.PictureFilter;
import net.sf.latexdraw.glib.models.interfaces.Arcable;
import net.sf.latexdraw.glib.models.interfaces.Arcable.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.Dottable;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IControlPointShape;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.glib.ui.LMagneticGrid;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.interaction.library.AbortableDnD;
import org.malai.interaction.library.MultiClick;
import org.malai.interaction.library.Press;
import org.malai.stateMachine.MustAbortStateMachineException;

/**
 * This instrument allows to draw shapes.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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

	/** The drawing that contains the shapes. */
	protected IDrawing drawing;

	/** A shape that supports shadows. Used to get and set shadow parameters. */
	protected IShape shadowable;

	/** A shape that supports double borders. Used to get and set double line parameters. */
	protected IShape dbleBorderable;

	/** A shape that supports line customisation. Used to get and set line parameters. */
	protected IShape lineStylable;

	/** A shape that supports round corners. Used to get and set round corner parameters. */
	protected ILineArcShape roundable;

	/** A shape that supports text features. */
	protected IText textable;

	/** A shape that supports move of its border. Used to get and set border positions. */
	protected IShape borderMoveable;

	/** The style of the created dots. */
	protected Dottable dottable;

	/** The style of the created arcs. */
	protected Arcable arcable;

	/** Contains the properties of the created grid. */
	protected IStandardGrid gridShape;

	/** A shape that supports filling customisation. Used to get and set filling properties. */
	protected IShape fillingable;

	/** The zoomer that is used to give the zoom level to compute coordinates of the created shapes. */
	protected Zoomer zoomer;

	/** The style of the left arrows. */
	protected ArrowStyle arrowLeftStyle;

	/** The style of the right arrows. */
	protected ArrowStyle arrowRightStyle;

	protected IArrow arrow;

	/** The file chooser used to select pictures. */
	protected JFileChooser pictureFileChooser;


	/**
	 * Creates a pencil.
	 * @param drawing The drawing where the shapes are drawn.
	 * @param zoomer The instrument zoomer used to create shapes.
	 * @param grid The magnetic grid used to create shapes.
	 * @param textSetter The instrument used to add and modify texts.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public Pencil(final IDrawing drawing, final Zoomer zoomer, final LMagneticGrid grid,
				final TextSetter textSetter) {
		super();

		if(drawing==null || zoomer==null || grid==null || textSetter==null)
			throw new IllegalArgumentException();

		IShapeFactory factory = DrawingTK.getFactory();
		this.textSetter = textSetter;
		this.grid		= grid;
		this.drawing 	= drawing;
		this.zoomer		= zoomer;
		currentChoice 	= EditionChoice.RECT;
		lineStylable	= factory.createRectangle(false);
		shadowable		= lineStylable;
		dbleBorderable	= lineStylable;
		roundable		= (ILineArcShape)lineStylable;
		dottable		= factory.createDot(factory.createPoint(), false);
		gridShape		= factory.createGrid(false, factory.createPoint());
		borderMoveable	= lineStylable;
		fillingable		= lineStylable;
		textable		= factory.createText(false);
		arcable			= factory.createCircleArc(false);
		arrowLeftStyle	= ArrowStyle.NONE;
		arrowRightStyle	= ArrowStyle.NONE;
		arrow			= factory.createArrow(lineStylable);
	}


	@Override
	public void setActivated(final boolean activated) {
		if(this.activated!=activated)
			super.setActivated(activated);
	}


	@Override
	public void interimFeedback() {
		drawing.setTempShape(null);
		drawing.setModified(true);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Press2AddShape(this));
			addLink(new Press2AddText(this, false));
			addLink(new Press2InsertPicture(this, false));
			addLink(new DnD2AddShape(this, false));
			addLink(new MultiClic2AddShape(this, false));
			addLink(new Press2InitTextSetter(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
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
//FIXME Should use copy operations to copy these parameters.
		shape.setLineColour(lineStylable.getLineColour());

		if(shape.isArrowable()) {
			shape.setArrowStyle(arrowLeftStyle, 0);
			shape.setArrowStyle(arrowRightStyle, 1);
		}
		if(shape.isThicknessable())
			shape.setThickness(lineStylable.getThickness());
		if(shape.isBordersMovable())
			shape.setBordersPosition(borderMoveable.getBordersPosition());
		if(shape.isInteriorStylable())
			shape.copy(fillingable);
		if(shape.isDbleBorderable()) {
			shape.setHasDbleBord(dbleBorderable.hasDbleBord());
			shape.setDbleBordCol(dbleBorderable.getDbleBordCol());
			shape.setDbleBordSep(dbleBorderable.getDbleBordSep());
		}
		if(shape.isShadowable()) {
			shape.setHasShadow(shadowable.hasShadow());
			shape.setShadowCol(shadowable.getShadowCol());
			shape.setShadowAngle(shadowable.getShadowAngle());
			shape.setShadowSize(shadowable.getShadowSize());
		}
		if(shape instanceof Dottable) {
			final Dottable dot = (Dottable)shape;
			dot.setDotStyle(dottable.getDotStyle());
			dot.setRadius(dottable.getRadius());
			dot.setDotFillingCol(dottable.getDotFillingCol());
		}
		if(shape instanceof Arcable) {
			final Arcable arc = (Arcable)shape;
			arc.setAngleStart(arcable.getAngleStart());
			arc.setAngleEnd(arcable.getAngleEnd());
			arc.setArcStyle(arcable.getArcStyle());
		}
		if(shape.isLineStylable())
			shape.setLineStyle(lineStylable.getLineStyle());
		// Setting the corner roundness.
		if(shape instanceof ILineArcShape)
			((ILineArcShape)shape).setLineArc(roundable.getLineArc());
		// Setting the text parameters.
		if(shape instanceof IText) {
			final IText text = (IText)shape;
			text.setTextPosition(textable.getTextPosition());
		}
		if(shape instanceof IStandardGrid)
			((IStandardGrid)shape).copy(gridShape);
		if(shape.isArrowable()) {
			shape.setArrowInset(arrow.getArrowInset());
			shape.setArrowLength(arrow.getArrowLength());
			shape.setArrowSizeDim(arrow.getArrowSizeDim());
			shape.setArrowSizeNum(arrow.getArrowSizeNum());
			shape.setDotSizeDim(arrow.getDotSizeDim());
			shape.setDotSizeNum(arrow.getDotSizeNum());
			shape.setBracketNum(arrow.getBracketNum());
			shape.setRBracketNum(arrow.getRBracketNum());
			shape.setTBarSizeDim(arrow.getTBarSizeDim());
			shape.setTBarSizeNum(arrow.getTBarSizeNum());
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
	 * Sets the arrow inset of the pencil.
	 * @param inset The new inset.
	 * @since 3.0
	 */
	public void setArrowInset(final double inset) {
		arrow.setArrowInset(inset);
	}


	/**
	 * Sets the arrow inset of the pencil.
	 * @param lgth The new inset.
	 * @since 3.0
	 */
	public void setArrowLength(final double lgth) {
		arrow.setArrowLength(lgth);
	}

	/**
	 * Sets the arrow dot size dim of the pencil.
	 * @param dotSizeDim The new dot size dim.
	 * @since 3.0
	 */
	public void setArrowDotSizeDim(final double dotSizeDim) {
		arrow.setDotSizeDim(dotSizeDim);
	}

	/**
	 * Sets the arrow dot size num of the pencil.
	 * @param dotSizeNum The new dot size num.
	 * @since 3.0
	 */
	public void setArrowDotSizeNum(final double dotSizeNum) {
		arrow.setDotSizeNum(dotSizeNum);
	}

	/**
	 * Sets the arrow size dim of the pencil.
	 * @param sizeDim The new size dim.
	 * @since 3.0
	 */
	public void setArrowSizeDim(final double sizeDim) {
		arrow.setArrowSizeDim(sizeDim);
	}

	/**
	 * Sets the arrow size num of the pencil.
	 * @param sizeNum The new size num.
	 * @since 3.0
	 */
	public void setArrowSizeNum(final double sizeNum) {
		arrow.setArrowSizeNum(sizeNum);
	}

	/**
	 * Sets the arrow t bar dim of the pencil.
	 * @param tbarDim The new t bar dim.
	 * @since 3.0
	 */
	public void setArrowTBarDim(final double tbarDim) {
		arrow.setTBarSizeDim(tbarDim);
	}

	/**
	 * Sets the arrow t bar num of the pencil.
	 * @param tbarNum The new t bar num.
	 * @since 3.0
	 */
	public void setArrowTBarNum(final double tbarNum) {
		arrow.setTBarSizeNum(tbarNum);
	}

	/**
	 * Sets the round bracket num of the pencil.
	 * @param rBracketNum The new round bracket.
	 * @since 3.0
	 */
	public void setArrowRBracketNum(final double rBracketNum) {
		arrow.setRBracketNum(rBracketNum);
	}

	/**
	 * Sets the bracket num of the pencil.
	 * @param bracketNum The new bracket.
	 * @since 3.0
	 */
	public void setArrowBracketNum(final double bracketNum) {
		arrow.setRBracketNum(bracketNum);
	}


	/**
	 * Sets the thickness of the pencil.
	 * @param thickness The new thickness.
	 * @since 3.0
	 */
	public void setThickness(final double thickness) {
		lineStylable.setThickness(thickness);
	}


	/**
	 * Sets the size of the shadow.
	 * @param shadowSize The new shadow size.
	 * @since 3.0
	 */
	public void setShadowSize(final double shadowSize) {
		shadowable.setShadowSize(shadowSize);
	}


	/**
	 * Sets the angle of the shadow.
	 * @param shadowAngle The new shadow angle.
	 * @since 3.0
	 */
	public void setShadowAngle(final double shadowAngle) {
		shadowable.setShadowAngle(shadowAngle);
	}


	/**
	 * Defines the size of the created dots.
	 * @param dotSize The size of the created dots.
	 * @since 3.0
	 */
	public void setDotSize(final double dotSize) {
		dottable.setRadius(dotSize);
	}


	/**
	 * Defines the filling colour of the created dots.
	 * @param fillingDotCol The filling colour of the created dots.
	 * @since 3.0
	 */
	public void setDotFillingCol(final Color fillingDotCol) {
		dottable.setDotFillingCol(fillingDotCol);
	}


	/**
	 * Defines if the created shapes must have a shadow.
	 * @param shadow True: the created shapes will have a shadow.
	 * @since 3.0
	 */
	public void setShadow(final boolean shadow) {
		shadowable.setHasShadow(shadow);
	}


	/**
	 * Sets if the created shapes must have double borders.
	 * @param doubleBorder True: created shapes must have double borders.
	 * @since 3.0
	 */
	public void setDoubleBorder(final boolean doubleBorder) {
		dbleBorderable.setHasDbleBord(doubleBorder);
	}


	/**
	 * Sets the line colour of the created shapes.
	 * @param lineColor The line colour of the created shapes.
	 * @since 3.0
	 */
	public void setLineColor(final Color lineColor) {
		lineStylable.setLineColour(lineColor);
	}


	/**
	 * Sets the filling colour of the created shapes.
	 * @param fillingColor The filling colour of the created shapes.
	 * @since 3.0
	 */
	public void setFillingColor(final Color fillingColor) {
		fillingable.setFillingCol(fillingColor);
	}


	/**
	 * Sets the shadow colour of the created shapes.
	 * @param shadowColor The shadow colour of the created shapes.
	 * @since 3.0
	 */
	public void setShadowColor(final Color shadowColor) {
		shadowable.setShadowCol(shadowColor);
	}


	/**
	 * Sets the hatchings colour of the created shapes.
	 * @param hatchingsColor The hatchings colour of the created shapes.
	 * @since 3.0
	 */
	public void setHatchingsColor(final Color hatchingsColor) {
		fillingable.setHatchingsCol(hatchingsColor);
	}


	/**
	 * Sets the starting gradient colour of the created shapes.
	 * @param gradStartColor The starting gradient colour of the created shapes.
	 * @since 3.0
	 */
	public void setGradStartColor(final Color gradStartColor) {
		fillingable.setGradColStart(gradStartColor);
	}


	/**
	 * Sets the ending gradient colour of the created shapes.
	 * @param gradEndColor The ending gradient colour of the created shapes.
	 * @since 3.0
	 */
	public void setGradEndColor(final Color gradEndColor) {
		fillingable.setGradColEnd(gradEndColor);
	}


	/**
	 * Sets the double colour of the created shapes.
	 * @param doubleBorderColor The double colour of the created shapes.
	 * @since 3.0
	 */
	public void setDoubleBorderColor(final Color doubleBorderColor) {
		dbleBorderable.setDbleBordCol(doubleBorderColor);
	}


	/**
	 * Sets the style of the created dots.
	 * @param dotStyle The style of the created dots.
	 * @since 3.0
	 */
	public void setDotStyle(final DotStyle dotStyle) {
		dottable.setDotStyle(dotStyle);
	}

	/**
	 * Sets the style of the created arcs.
	 * @param arcStyle The style of the created arcs.
	 * @since 3.0
	 */
	public void setArcStyle(final ArcStyle arcStyle) {
		arcable.setArcStyle(arcStyle);
	}

	/**
	 * Sets the start angle of the created arcs.
	 * @param startAngle The start angle of the created arcs.
	 * @since 3.0
	 */
	public void setArcStartAngle(final double startAngle) {
		arcable.setAngleStart(startAngle);
	}

	/**
	 * Sets the end angle of the created arcs.
	 * @param endAngle The end angle of the created arcs.
	 * @since 3.0
	 */
	public void setArcEndAngle(final double endAngle) {
		arcable.setAngleEnd(endAngle);
	}


	/**
	 * Sets the style of the left arrows.
	 * @param arrowLeftStyle The style of the left arrows.
	 * @since 3.0
	 */
	public void setArrowLeftStyle(final ArrowStyle arrowLeftStyle) {
		if(arrowLeftStyle!=null)
			this.arrowLeftStyle = arrowLeftStyle;
	}


	/**
	 * Sets the style of the right arrows.
	 * @param arrowRightStyle The style of the right arrows.
	 * @since 3.0
	 */
	public void setArrowRightStyle(final ArrowStyle arrowRightStyle) {
		if(arrowRightStyle!=null)
			this.arrowRightStyle = arrowRightStyle;
	}


	/**
	 * Sets the position of the borders of the created shapes.
	 * @param borderPosition The position of the borders of the created shapes.
	 * @since 3.0
	 */
	public void setBorderPosition(final BorderPos borderPosition) {
		borderMoveable.setBordersPosition(borderPosition);
	}


	/**
	 * Sets the line style of the created shapes.
	 * @param lineStyle The line style of the created shapes.
	 * @since 3.0
	 */
	public void setLineStyle(final LineStyle lineStyle) {
		lineStylable.setLineStyle(lineStyle);
	}


	/**
	 * Sets the filling style of the created shapes.
	 * @param fillingStyle The filling style of the created shapes.
	 * @since 3.0
	 */
	public void setFillingStyle(final FillingStyle fillingStyle) {
		fillingable.setFillingStyle(fillingStyle);
	}


	/**
	 * @param roundness Defines the roundness of the border corners.
	 * @since 3.0
	 */
	public void setRoundness(final double roundness) {
		roundable.setLineArc(roundness);
	}


	/**
	 * @param dbleSize The space between double border.
	 * @since 3.0
	 */
	public void setDoubleBorderSize(final double dbleSize) {
		dbleBorderable.setDbleBordSep(dbleSize);
	}

	/**
	 * @param angle The angle of the gradient.
	 * @since 3.0
	 */
	public void setGradAngle(final double angle) {
		fillingable.setGradAngle(angle);
	}

	/**
	 * @param midPt The middle point of the gradient.
	 * @since 3.0
	 */
	public void setGradMidPt(final double midPt) {
		fillingable.setGradMidPt(midPt);
	}

	/**
	 * @param angle The angle of the hatchings.
	 * @since 3.0
	 */
	public void setHatchAngle(final double angle) {
		fillingable.setHatchingsAngle(angle);
	}

	/**
	 * @param sep The spacing between the hatchings.
	 * @since 3.0
	 */
	public void setHatchSep(final double sep) {
		fillingable.setHatchingsSep(sep);
	}

	/**
	 * @param width the width of the hatchings.
	 * @since 3.0
	 */
	public void setHatchWidth(final double width) {
		fillingable.setHatchingsWidth(width);
	}


	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param point The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	public IPoint getAdaptedPoint(final Point point) {
		return grid.getTransformedPointToGrid(zoomer.zoomable.getZoomedPoint(point));
	}


	/**
	 * @param value The text position of texts added by the pencil.
	 * @since 3.0
	 */
	public void setTextPosition(final TextPosition value) {
		textable.setTextPosition(value);
	}

	/**
	 * @param value The starting point of the grid that must be set by the pencil.
	 * @since 3.0
	 */
	public void setGridStart(final IPoint value) {
		if(value!=null)
			gridShape.setGridStart(value.getX(), value.getY());
	}
}




/**
 * This class defines a generic link for the pencil.
 * @author Arnaud Blouin
 * @since 3.0
 * @version 3.0
 */
abstract class PencilLink<I extends Interaction> extends Link<AddShape, I, Pencil> {
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
		action.setShape(instrument.createShapeInstance());
		action.setDrawing(instrument.drawing);
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
		final IModifiablePointsShape shape = (IModifiablePointsShape)action.getShape();

		if(shape.getNbPoints()==pts.size() && !interaction.isLastPointFinalPoint()) {
			final IPoint pt = instrument.getAdaptedPoint(pts.get(pts.size()-1));
			shape.setPoint(pt.getX(), pt.getY(), -1);
			shape.addPoint(DrawingTK.getFactory().createPoint(currPoint.getX(), currPoint.getY()));
		}
		else
			shape.setPoint(currPoint.getX(), currPoint.getY(), -1);

		// Curves need to be balanced.
		if(shape instanceof IControlPointShape)
			((IControlPointShape)shape).balance();

		shape.setModified(true);
		action.getDrawing().setModified(true);
	}


	@Override
	public void initAction() {
		super.initAction();

		final IShape shape = action.getShape();

		if(shape instanceof IModifiablePointsShape) {
			final IModifiablePointsShape modShape = (IModifiablePointsShape)shape;
			final IPoint pt = instrument.getAdaptedPoint(interaction.getPoints().get(0));

			modShape.addPoint(DrawingTK.getFactory().createPoint(pt.getX(), pt.getY()));
			modShape.addPoint(DrawingTK.getFactory().createPoint(pt.getX()+1, pt.getY()+1));
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


	@Override
	public void interimFeedback() {
		// The temp shape must be be the same shape than the shape what will be added to the drawing. So we
		// need to duplicate it at each feedback. The issue can be cpu consuming.
		instrument.drawing.setTempShape(action.getShape().duplicate());
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

		final IShape shape = action.getShape();

		if(shape!=null) {
			final EditionChoice ec 	= instrument.getCurrentChoice();
			IPoint pt = instrument.getAdaptedPoint(interaction.getStartPt());

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
		final IShape shape 	= action.getShape();
		final EditionChoice ec = instrument.getCurrentChoice();
		// Getting the points depending on the current zoom.
		final IPoint startPt= instrument.getAdaptedPoint(interaction.getStartPt());
		final IPoint endPt	= instrument.getAdaptedPoint(interaction.getEndPt());

		if(ec==EditionChoice.SQUARE || ec==EditionChoice.CIRCLE  || ec==EditionChoice.CIRCLE_ARC)
			updateShapeFromCentre(shape, startPt, endPt.getX());
		else
			if(ec==EditionChoice.FREE_HAND && shape instanceof IFreehand)
				((IFreehand)shape).addPoint(endPt);
			else
				updateShapeFromDiag(shape, startPt, endPt);

		shape.setModified(true);
		action.getDrawing().setModified(true);
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


	private void updateShapeFromCentre(final IShape shape, final IPoint startPt, final double endX) {
		final double sx = startPt.getX();
		final double sy = startPt.getY();
		final double radius = Math.max(sx<endX ? endX-sx : sx - endX, getGap(shape));

		shape.setTop(sy-radius);
		shape.setBottom(sy+radius);
		shape.setLeft(sx-radius);
		shape.setRight(sx+radius);
	}


	private void updateShapeFromDiag(final IShape shape, final IPoint startPt, final IPoint endPt) {
		final double gap = getGap(shape);
		double v1 = startPt.getX();
		double v2 = endPt.getX();

		if(Math.abs(v1-v2)>gap)
			if(v1<v2) {
				shape.setRight(v2);
				shape.setLeft(v1);
			}
			else {
				shape.setLeft(v2);
				shape.setRight(v1);
			}

		v1 = startPt.getY();
		v2 = endPt.getY();

		if(Math.abs(v1-v2)>gap)
			if(v1<v2) {
				shape.setBottom(v2);
				shape.setTop(v1);
			}
			else {
				shape.setTop(v2);
				shape.setBottom(v1);
			}
	}


	@Override
	public void interimFeedback() {
		// The temp shape must be be the same shape than the shape what will be added to the drawing. So we
		// need to duplicate it at each feedback. The issue can be cpu consuming.
		instrument.drawing.setTempShape(action.getShape().duplicate());
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
		action.setDrawing(instrument.drawing);
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

		final IShape shape = action.getShape();

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
		action.setDrawing(instrument.drawing);
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
		final double zoom = instrument.zoomer.zoomable.getZoom();

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
