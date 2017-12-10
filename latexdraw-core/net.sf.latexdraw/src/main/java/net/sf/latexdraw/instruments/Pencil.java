/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.actions.shape.InitTextSetter;
import net.sf.latexdraw.actions.shape.InsertPicture;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.jfx.ViewFactory;
import org.malai.javafx.interaction.library.AbortableDnD;
import org.malai.javafx.interaction.library.MultiClick;
import org.malai.javafx.interaction.library.Press;

/**
 * This instrument allows to draw shapes.
 * @author Arnaud Blouin
 */
public class Pencil extends CanvasInstrument {
	/** The current editing choice (rectangle, ellipse, etc.) of the instrument. */
	private final ObjectProperty<EditionChoice> currentChoice;

	/** The text setter used to create text shapes. */
	@Inject protected TextSetter textSetter;

	/** The file chooser used to select the picture to insert. Use its getter instead as it is lazy instantiated. */
	private FileChooser pictureFileChooser;

	/** This shape gathers all the current shape parameters. Used as a model when creating shapes. Use its getter instead as it is lazy instantiated. */
	private IGroup groupParams;


	/**
	 * Creates the pencil. Automatically invoked by the dependency injector. Do not call.
	 */
	public Pencil() {
		super();
		currentChoice = new SimpleObjectProperty<>(EditionChoice.RECT);
	}

	/**
	 * Entry point for a testing purpose only.
	 */
	protected void setPictureFileChooser(final FileChooser chooser) {
		pictureFileChooser = chooser;
	}

	/**
	 * @return The file chooser used to select pictures to insert. Cannot be null.
	 */
	protected FileChooser getPictureFileChooser() {
		if(pictureFileChooser == null) {
			pictureFileChooser = new FileChooser();
			pictureFileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
				LangTool.INSTANCE.getBundle().getString("Filter.1"), Arrays.asList("*.png", "*.bmp", "*.gif", "*.jpeg", "*.jpg")));
		}
		return pictureFileChooser;
	}

	/**
	 * @return the shape that gathers all the current shape parameters. Use as a model to copy while creating a new shape. Cannot be null.
	 */
	public IGroup getGroupParams() {
		if(groupParams == null) {
			groupParams = ShapeFactory.INST.createGroup();
			groupParams.addShape(ShapeFactory.INST.createRectangle());
			groupParams.addShape(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
			groupParams.addShape(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
			groupParams.addShape(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
			groupParams.addShape(ShapeFactory.INST.createText());
			groupParams.addShape(ShapeFactory.INST.createCircleArc());
			groupParams.addShape(ShapeFactory.INST.createPolyline(Collections.emptyList()));
			groupParams.addShape(ShapeFactory.INST.createBezierCurve(Collections.emptyList()));
			groupParams.addShape(ShapeFactory.INST.createFreeHand(Collections.emptyList()));
			groupParams.addShape(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 10, "x", false));
		}
		return groupParams;
	}

	@Override
	public void setActivated(final boolean active) {
		if(activated != active) {
			super.setActivated(active);
		}
	}

	@Override
	public void interimFeedback() {
		canvas.setTempView(null);
		canvas.setCursor(Cursor.DEFAULT);
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		addBinding(new Hand.DnD2MoveViewport(this));
		bindPressToAddShape();

		// Binds a pressure to insert a picture
		nodeBinder(InsertPicture.class, new Press()).on(canvas).
			map(i -> new InsertPicture(ShapeFactory.INST.createPicture(getAdaptedPoint(i.getSrcPoint())), canvas.getDrawing(), getPictureFileChooser())).
			when(i -> currentChoice.get() == EditionChoice.PICTURE && i.getButton() == MouseButton.PRIMARY).bind();

		bindDnDToDrawRectangularShape();
		bindDnDToDrawSquaredShape();
		bindDnDToDrawFreeHandShape();
		bindMultiClic2AddShape();

		// Binds a pressure to show the text setter
		nodeBinder(InitTextSetter.class, new Press()).on(canvas).
			map(i -> new InitTextSetter(textSetter, textSetter, "", getAdaptedPoint(i.getSrcPoint()), null, null)).
			when(i -> (currentChoice.get() == EditionChoice.TEXT || currentChoice.get() == EditionChoice.PLOT) && i.getButton() == MouseButton.PRIMARY).bind();
	}

	/**
	 * Binds a DnD interaction to create shape.
	 */
	private void bindDnDToDrawFreeHandShape() throws InstantiationException, IllegalAccessException {
		nodeBinder(AddShape.class, new AbortableDnD()).on(canvas).
			map(i -> {
				final IShape sh = createShapeInstance();
				final IPoint pt = getAdaptedPoint(i.getSrcPoint());
				sh.getPoints().get(0).setPoint(pt.getX(), pt.getY());
				return new AddShape(sh, canvas.getDrawing());
			}).
			then((a, i) -> {
				final IPoint last = a.getShape().get().getPtAt(-1);
				final IPoint endPt = getAdaptedPoint(i.getEndPt());
				if(!MathUtils.INST.equalsDouble(last.getX(), endPt.getX(), 0.0001) &&
					!MathUtils.INST.equalsDouble(last.getY(), endPt.getY(), 0.0001)) {
					a.setShape(ShapeFactory.INST.createFreeHandFrom((IFreehand) a.getShape().get(), endPt));
				}
				canvas.setTempView(ViewFactory.INSTANCE.createView(a.getShape().orElse(null)).orElse(null));
			}).
			end((a, i) -> canvas.setTempView(null)).
			when(i -> i.getButton() == MouseButton.PRIMARY && currentChoice.get() == EditionChoice.FREE_HAND).
			bind();
	}

	/**
	 * Binds a DnD interaction to draw squared shapes.
	 */
	private void bindDnDToDrawSquaredShape() throws InstantiationException, IllegalAccessException {
		nodeBinder(AddShape.class, new AbortableDnD()).on(canvas).
			map(i -> {
				final ISquaredShape sq = (ISquaredShape) createShapeInstance();
				final IPoint pt = getAdaptedPoint(i.getSrcPoint());
				sq.setPosition(pt.getX() - 1d, pt.getY() - 1d);
				sq.setWidth(2d);
				return new AddShape(sq, canvas.getDrawing());
			}).
			first(a -> canvas.setTempView(ViewFactory.INSTANCE.createView(a.getShape().orElse(null)).orElse(null))).
			then((a, i) -> updateShapeFromCentre((ISquaredShape) a.getShape().get(), getAdaptedPoint(i.getSrcPoint()), getAdaptedPoint(i.getEndPt()).getX())).
			end((a, i) -> canvas.setTempView(null)).
			when(i -> i.getButton() == MouseButton.PRIMARY).
			bind().activationProperty().bind(activatedProp.and(currentChoice.isEqualTo(EditionChoice.SQUARE).or(currentChoice.isEqualTo(EditionChoice.CIRCLE).
				or(currentChoice.isEqualTo(EditionChoice.CIRCLE_ARC)))));
	}

	/**
	 * Binds a DnD interaction to draw rectangular shapes.
	 */
	private void bindDnDToDrawRectangularShape() throws InstantiationException, IllegalAccessException {
		nodeBinder(AddShape.class, new AbortableDnD()).on(canvas).
			map(i -> new AddShape(createShapeInstance(), canvas.getDrawing())).
			first(a -> canvas.setTempView(ViewFactory.INSTANCE.createView(a.getShape().orElse(null)).orElse(null))).
			then((a, i) -> updateShapeFromDiag((IRectangularShape) a.getShape().get(), getAdaptedPoint(i.getSrcPoint()), getAdaptedPoint(i.getEndPt()))).
			end((a, i) -> canvas.setTempView(null)).
			when(i -> i.getButton() == MouseButton.PRIMARY).
			bind().activationProperty().bind(activatedProp.and(currentChoice.isEqualTo(EditionChoice.RECT).or(currentChoice.isEqualTo(EditionChoice.ELLIPSE).
				or(currentChoice.isEqualTo(EditionChoice.RHOMBUS).or(currentChoice.isEqualTo(EditionChoice.TRIANGLE))))));
	}

	/**
	 * Binds a multi-click interaction to creates multi-point shapes.
	 */
	private void bindMultiClic2AddShape() throws InstantiationException, IllegalAccessException {
		final Function<MultiClick, AddShape> creation = i -> new AddShape(setInitialPtsShape(createShapeInstance(), i.getPoints().get(0)), canvas.getDrawing());

		// Binding for polygons
		nodeBinder(AddShape.class, new MultiClick(3)).on(canvas).map(creation).
			then((a, i) -> {
				final IPoint currPoint = getAdaptedPoint(i.getCurrentPosition());
				if(a.getShape().get().getNbPoints() == i.getPoints().size() && !i.isLastPointFinalPoint()) {
					a.setShape(ShapeFactory.INST.createPolygonFrom((IPolygon) a.getShape().get(), ShapeFactory.INST.createPoint(currPoint.getX(), currPoint.getY())));
				}else {
					((IModifiablePointsShape) a.getShape().get()).setPoint(currPoint.getX(), currPoint.getY(), -1);
				}
				canvas.setTempView(ViewFactory.INSTANCE.createView(a.getShape().orElse(null)).orElse(null));
			}).
			end((a, i) -> canvas.setTempView(null)).
			bind().activationProperty().bind(currentChoice.isEqualTo(EditionChoice.POLYGON).and(activatedProp));

		// Binding for polyline
		nodeBinder(AddShape.class, new MultiClick()).on(canvas).map(creation).
			then((a, i) -> {
				final IPoint currPoint = getAdaptedPoint(i.getCurrentPosition());
				if(a.getShape().get().getNbPoints() == i.getPoints().size() && !i.isLastPointFinalPoint()) {
					a.setShape(ShapeFactory.INST.createPolylineFrom((IPolyline) a.getShape().get(), ShapeFactory.INST.createPoint(currPoint.getX(), currPoint.getY())));
				}else {
					((IModifiablePointsShape) a.getShape().get()).setPoint(currPoint.getX(), currPoint.getY(), -1);
				}
				canvas.setTempView(ViewFactory.INSTANCE.createView(a.getShape().orElse(null)).orElse(null));
			}).
			end((a, i) -> canvas.setTempView(null)).
			bind().activationProperty().bind(currentChoice.isEqualTo(EditionChoice.LINES).and(activatedProp));

		// Binding for bézier curves
		nodeBinder(AddShape.class, new MultiClick()).on(canvas).map(creation).
			then((a, i) -> {
				final IPoint currPoint = getAdaptedPoint(i.getCurrentPosition());
				if(a.getShape().get().getNbPoints() == i.getPoints().size() && !i.isLastPointFinalPoint()) {
					a.setShape(ShapeFactory.INST.createBezierCurveFrom((IBezierCurve) a.getShape().get(), ShapeFactory.INST.createPoint(currPoint.getX(), currPoint.getY())));
				}else {
					((IModifiablePointsShape) a.getShape().get()).setPoint(currPoint.getX(), currPoint.getY(), -1);
				}
				((IControlPointShape) a.getShape().get()).balance();
				canvas.setTempView(ViewFactory.INSTANCE.createView(a.getShape().orElse(null)).orElse(null));
			}).
			end((a, i) -> canvas.setTempView(null)).
			bind().activationProperty().bind(currentChoice.isEqualTo(EditionChoice.BEZIER_CURVE).and(activatedProp));
	}

	/**
	 * Binds a press interaction to add a shape.
	 */
	private void bindPressToAddShape() throws InstantiationException, IllegalAccessException {
		// Add axes, grids, or dots
		nodeBinder(AddShape.class, new Press()).on(canvas).
			map(i -> {
				final IPositionShape sh = (IPositionShape) createShapeInstance();
				sh.setPosition(getAdaptedPoint(i.getSrcPoint()));
				return new AddShape(sh, canvas.getDrawing());
			}).
			when(i -> i.getButton() == MouseButton.PRIMARY).
			bind().activationProperty().bind(activatedProp.and(currentChoice.isEqualTo(EditionChoice.GRID).or(currentChoice.isEqualTo(EditionChoice.DOT)).
				or(currentChoice.isEqualTo(EditionChoice.AXES))));

		// When a user starts to type a text using the text setter and then he clicks somewhere else in the canvas,
		// the text typed must be added (if possible to the canvas) before starting typing a new text.
		nodeBinder(AddShape.class, new Press()).on(canvas).
			map(i -> new AddShape(ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(textSetter.getPosition()), textSetter.getTextField().getText()),
				canvas.getDrawing())).
			when(i -> textSetter.isActivated() && !textSetter.getTextField().getText().isEmpty()).
			bind().activationProperty().bind(currentChoice.isEqualTo(EditionChoice.TEXT).and(activatedProp));
	}

	/**
	 * Companion function to the bindMultiClic2AddShape binding.
	 * It initialises the first two points of the given shape.
	 */
	private IShape setInitialPtsShape(final IShape sh, final Point3D firstPt) {
		if(sh instanceof IModifiablePointsShape) {
			final IModifiablePointsShape modShape = (IModifiablePointsShape) sh;
			final IPoint pt = getAdaptedPoint(firstPt);
			modShape.setPoint(pt.getX(), pt.getY(), 0);
			modShape.setPoint(pt.getX() + 1d, pt.getY() + 1d, 1);
		}
		return sh;
	}

	/**
	 * @param shape The shape to analyse.
	 * @return The gap that must respect the pencil to not allow shape to disappear when they are too small.
	 */
	private double getGap(final IShape shape) {
		// These lines are necessary to avoid shape to disappear. It appends when the borders position is INTO.
		// In this case,the minimum radius must be computed using the thickness and the double size.
		if(shape.isBordersMovable() && shape.getBordersPosition() == BorderPos.INTO) {
			return shape.getThickness() + (shape.isDbleBorderable() && shape.hasDbleBord() ? shape.getDbleBordSep() : 0d);
		}
		return 1d;
	}

	/**
	 * Companion method to a bind. It updates the given from its centre using the new given data points.
	 */
	private void updateShapeFromCentre(final ISquaredShape shape, final IPoint startPt, final double endX) {
		final double sx = startPt.getX();
		final double radius = Math.max(sx < endX ? endX - sx : sx - endX, getGap(shape));
		shape.setPosition(sx - radius, startPt.getY() + radius);
		shape.setWidth(radius * 2d);
	}

	/**
	 * Companion method to a bind. It updates the given from its diagonal using the new given data points.
	 */
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
		}else {
			ok = false;
		}

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
		}else {
			ok = false;
		}

		if(ok) {
			shape.setPosition(tlx, bry);
			shape.setWidth(brx - tlx);
			shape.setHeight(bry - tly);
		}
	}

	/**
	 * @return An instance of a shape configured (thickness, colours, etc.) with the parameters of the pencil.
	 */
	public IShape createShapeInstance() {
		return setShapeParameters(currentChoice.get().createShapeInstance());
	}

	/**
	 * Configures the given shape with the parameters (e.g. thickness, colours, etc.) of the pencil.
	 * @param shape The shape to configure.
	 * @return The modified shape given as argument.
	 */
	private IShape setShapeParameters(final IShape shape) {
		shape.copy(getGroupParams());
		shape.setModified(true);
		return shape;
	}


	/**
	 * @return The current editing choice.
	 */
	public EditionChoice getCurrentChoice() {
		return currentChoice.get();
	}

	/**
	 * Sets the current editing choice.
	 * @param choice The new editing choice to set.
	 */
	public void setCurrentChoice(final EditionChoice choice) {
		if(choice != null) {
			currentChoice.set(choice);
		}
	}
}
