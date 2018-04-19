/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import net.sf.latexdraw.commands.DrawingCmd;
import net.sf.latexdraw.commands.ShapeCmd;
import net.sf.latexdraw.commands.ShapesCmd;
import net.sf.latexdraw.commands.shape.MovePoint;
import net.sf.latexdraw.commands.shape.ShapePropertyCmd;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import org.malai.command.CmdHandler;
import org.malai.command.Command;
import org.malai.command.CommandsRegistry;
import org.malai.command.library.Redo;
import org.malai.command.library.Undo;
import org.malai.javafx.command.IOCommand;
import org.malai.properties.Modifiable;
import org.malai.properties.Preferenciable;
import org.malai.properties.Reinitialisable;
import org.malai.properties.Zoomable;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The JFX canvas where shapes are painted.
 * @author Arnaud Blouin
 */
public class Canvas extends Pane implements Preferenciable, Modifiable, Reinitialisable, CmdHandler, Zoomable, ViewsSynchroniserHandler {
	/** The margin used to surround the drawing. */
	protected static int margins = 1500;

	protected static Page defaultPage = Page.USLETTER;

	/** The origin of the drawing in the whole drawing area. */
	public static final IPoint ORIGIN = ShapeFactory.INST.createPoint(margins, margins);

	public static void setDefaultPage(final Page newPage) {
		if(newPage != null) {
			defaultPage = newPage;
		}
	}

	public static int getMargins() {
		return margins;
	}

	public static void setMargins(final int newMargins) {
		if(newMargins >= 0) {
			margins = newMargins;
			ORIGIN.setPoint(margins, margins);
		}
	}

	/** The model of the view. */
	private final IDrawing drawing;

	/** The zoom applied on the canvas. */
	private final DoubleProperty zoom;

	/** The current page of the canvas. */
	private final PageView page;

	/** The views of the shape. */
	private final Group shapesPane;

	/** The pane that contains widgets to handle shapes, such as handlers, text fields. */
	private final Group widgetsPane;

	private final Rectangle selectionBorder;

	private final Rectangle ongoingSelectionBorder;

	private final Map<IShape, ViewShape<?>> shapesToViewMap;

	/** The magnetic grid of the canvas. */
	private final MagneticGridImpl magneticGrid;

	/** Defined whether the canvas has been modified. */
	private boolean modified;

	/** The temporary view that the canvas may contain. */
	private Optional<ViewShape<?>> tempView;

	/**
	 * Creates the canvas.
	 */
	public Canvas() {
		super();

		modified = false;
		drawing = ShapeFactory.INST.createDrawing();
		zoom = new SimpleDoubleProperty(1d);
		tempView = Optional.empty();
		page = new PageView(defaultPage, getOrigin());
		setPrefWidth(margins * 2d + page.getPage().getWidth() * IShape.PPC);
		setPrefHeight(margins * 2d + page.getPage().getHeight() * IShape.PPC);

		magneticGrid = new MagneticGridImpl(this);

		widgetsPane = new Group();
		shapesPane = new Group();
		shapesToViewMap = new HashMap<>();
		selectionBorder = new Rectangle();
		ongoingSelectionBorder = new Rectangle();

		widgetsPane.setFocusTraversable(false);
		ongoingSelectionBorder.setFocusTraversable(false);
		ongoingSelectionBorder.setMouseTransparent(true);
		ongoingSelectionBorder.setFill(null);
		ongoingSelectionBorder.setStroke(Color.GRAY);
		ongoingSelectionBorder.setStrokeLineCap(StrokeLineCap.BUTT);
		ongoingSelectionBorder.getStrokeDashArray().addAll(7d, 7d);

		getChildren().add(page);
		getChildren().add(magneticGrid);
		getChildren().add(shapesPane);
		getChildren().add(widgetsPane);
		widgetsPane.getChildren().add(selectionBorder);
		widgetsPane.getChildren().add(ongoingSelectionBorder);
		widgetsPane.relocate(ORIGIN.getX(), ORIGIN.getY());
		shapesPane.relocate(ORIGIN.getX(), ORIGIN.getY());

		defineShapeListToViewBinding();
		configureSelection();

		CommandsRegistry.INSTANCE.addHandler(this);

		shapesPane.setFocusTraversable(false);
	}


	public MagneticGrid getMagneticGrid() {
		return magneticGrid;
	}

	private void configureSelection() {
		selectionBorder.setFocusTraversable(false);
		selectionBorder.setVisible(false);
		selectionBorder.setFill(null);
		selectionBorder.setStroke(Color.GRAY);
		selectionBorder.setStrokeLineCap(StrokeLineCap.BUTT);
		selectionBorder.getStrokeDashArray().addAll(7d, 7d);
		selectionBorder.addEventHandler(MouseEvent.MOUSE_ENTERED, evt -> setCursor(Cursor.HAND));
		selectionBorder.addEventHandler(MouseEvent.MOUSE_EXITED, evt -> setCursor(Cursor.DEFAULT));

		drawing.getSelection().getShapes().addListener((Change<? extends IShape> evt) -> updateSelectionBorders());
	}


	private void updateSelectionBorders() {
		if(selectionBorder.isDisable()) {
			return;
		}

		Platform.runLater(() -> {
			final ObservableList<IShape> selection = drawing.getSelection().getShapes();
			if(selection.isEmpty()) {
				selectionBorder.setVisible(false);
			}else {
				final Rectangle2D rec = selection.stream().map(sh -> shapesToViewMap.get(sh)).filter(vi -> vi != null).map(vi -> {
					final Bounds b = vi.getBoundsInParent();
					return (Rectangle2D) new Rectangle2D.Double(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
				}).reduce(Rectangle2D::createUnion).orElse(new Rectangle2D.Double());

				selectionBorder.setLayoutX(rec.getMinX());
				selectionBorder.setLayoutY(rec.getMinY());
				selectionBorder.setWidth(rec.getWidth());
				selectionBorder.setHeight(rec.getHeight());
				selectionBorder.setVisible(true);
			}
		});
	}


	public void setOngoingSelectionBorder(final Bounds bounds) {
		if(bounds == null) {
			ongoingSelectionBorder.setVisible(false);
		}else {
			ongoingSelectionBorder.setLayoutX(bounds.getMinX());
			ongoingSelectionBorder.setLayoutY(bounds.getMinY());
			ongoingSelectionBorder.setWidth(bounds.getWidth());
			ongoingSelectionBorder.setHeight(bounds.getHeight());
			ongoingSelectionBorder.setVisible(true);
		}
	}


	/**
	 * @return The selected views.
	 */
	public List<ViewShape<?>> getSelectedViews() {
		return drawing.getSelection().getShapes().stream().map(sh -> shapesToViewMap.get(sh)).collect(Collectors.toList());
	}


	private void defineShapeListToViewBinding() {
		drawing.getShapes().addListener((Change<? extends IShape> evt) -> {
			while(evt.next()) {
				if(evt.wasAdded()) {
					evt.getAddedSubList().forEach(sh -> ViewFactory.INSTANCE.createView(sh).ifPresent(v -> {
						final int index = drawing.getShapes().indexOf(sh);
						if(index != -1) {
							shapesToViewMap.put(sh, v);
							if(index == drawing.size()) {
								shapesPane.getChildren().add(v);
							}else {
								shapesPane.getChildren().add(index, v);
							}
						}
					}));
				}else {
					if(evt.wasRemoved()) {
						evt.getRemoved().forEach(sh -> {
							final ViewShape<?> toRemove = shapesToViewMap.remove(sh);
							shapesPane.getChildren().remove(toRemove);
							toRemove.flush();
						});
					}
				}
			}
		});
	}


	/**
	 * @return The point where the page is located.
	 */
	public IPoint getOrigin() {
		return ORIGIN;
	}

	@Override
	public int getPPCDrawing() {
		return IShape.PPC;
	}

	/**
	 * @return The page of the drawing area. Cannot be null.
	 */
	public PageView getPage() {
		return page;
	}

	public void update() {
		updateSelectionBorders();
	}

	public Rectangle getSelectionBorder() {
		return selectionBorder;
	}

	@Override
	public double getZoom() {
		return zoom.getValue();
	}

	public DoubleProperty zoomProperty() {
		return zoom;
	}

	@Override
	public void onCmdExecuted(final Command cmd) {
		if(cmd instanceof ShapesCmd || cmd instanceof DrawingCmd || cmd instanceof IOCommand || cmd instanceof ShapePropertyCmd ||
			cmd instanceof ShapeCmd || cmd instanceof Undo || cmd instanceof Redo || cmd instanceof MovePoint) {
			update();
		}
	}

	@Override
	public void onUndoableAdded(final Undoable u) {
		/* Nothing to do. */
	}

	@Override
	public void onUndoableCleared() {
		/* Nothing to do. */
	}

	@Override
	public void onUndoableRedo(final Undoable u) {
		/* Nothing to do. */
	}

	@Override
	public void onUndoableUndo(final Undoable u) {
		/* Nothing to do. */
	}

	@Override
	public void onCmdCancelled(final Command a) {
		/* Nothing to do. */
	}

	@Override
	public void onCmdAdded(final Command a) {
		/* Nothing to do. */
	}

	@Override
	public void onCmdDone(final Command a) {
		/* Nothing to do. */
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document == null || root == null) {
			return;
		}

		if(!generalPreferences) {
			final String ns = nsURI == null || nsURI.isEmpty() ? "" : nsURI + ':';
			Element elt = document.createElement(ns + LNamespace.XML_ZOOM);
			elt.appendChild(document.createTextNode(String.valueOf(getZoom())));
			root.appendChild(elt);
			elt = document.createElement(ns + LNamespace.XML_VIEWPORT_X);
			elt.appendChild(document.createTextNode(MathUtils.INST.format.format(getScrollPane().getHvalue())));
			root.appendChild(elt);
			elt = document.createElement(ns + LNamespace.XML_VIEWPORT_Y);
			elt.appendChild(document.createTextNode(MathUtils.INST.format.format(getScrollPane().getVvalue())));
			root.appendChild(elt);
		}

		magneticGrid.save(generalPreferences, nsURI, document, root);
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		if(meta == null) {
			return;
		}
		// Getting the list of meta information tags.
		final NodeList nl = meta.getChildNodes();
		final String uri = nsURI == null ? "" : nsURI;

		// For each meta information tag.
		for(int i = 0, size = nl.getLength(); i < size; i++) {
			final Node node = nl.item(i);

			// Must be a latexdraw tag.
			if(node != null && uri.equals(node.getNamespaceURI()) && !generalPreferences) {
				if(node.getNodeName().endsWith(LNamespace.XML_ZOOM)) {
					setZoom(Double.NaN, Double.NaN, MathUtils.INST.parserDouble(node.getTextContent()).orElse(1d));
				}
				if(node.getNodeName().endsWith(LNamespace.XML_VIEWPORT_X)) {
					getScrollPane().setHvalue(MathUtils.INST.parserDouble(node.getTextContent()).orElse(0.5));
				}
				if(node.getNodeName().endsWith(LNamespace.XML_VIEWPORT_Y)) {
					getScrollPane().setVvalue(MathUtils.INST.parserDouble(node.getTextContent()).orElse(0.45));
				}
			}
		}
		magneticGrid.load(generalPreferences, nsURI, meta);
	}

	@Override
	public boolean isModified() {
		return modified || magneticGrid.isModified();
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
		if(!modif) {
			magneticGrid.setModified(false);
		}
	}

	@Override
	public void reinit() {
		// No need to clean the views and the associated map since the binding between the model and the view will do the job.
		setScaleX(1d);
		setScaleY(1d);
		zoom.setValue(1d);
		update();
	}

	@Override
	public IPoint getTopRightDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.INST.createPoint(border.getMaxX(), border.getMinY());
	}

	@Override
	public IPoint getBottomLeftDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.INST.createPoint(border.getMinX(), border.getMaxY());
	}

	@Override
	public IPoint getOriginDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.INST.createPoint(border.getMinX(), (border.getMaxY() - border.getMinY()) / 2.0);
	}

	@Override
	public double getZoomIncrement() {
		return 0.05;
	}

	@Override
	public double getMaxZoom() {
		return 4.5;
	}

	@Override
	public double getMinZoom() {
		return 0.1;
	}

	@Override
	public Point2D getZoomedPoint(final double x, final double y) {
		final double zoomValue = zoom.getValue();
		return new Point2D.Double(x / zoomValue, y / zoomValue);
	}

	@Override
	public Point2D getZoomedPoint(final Point pt) {
		return pt == null ? new Point2D.Double() : getZoomedPoint(pt.x, pt.y);
	}

	@Override
	public void setZoom(final double x, final double y, final double z) {
		if(z <= getMaxZoom() && z >= getMinZoom() && !MathUtils.INST.equalsDouble(z, zoom.getValue())) {
			zoom.setValue(z);
			final Duration duration = Duration.millis(250);
			final ParallelTransition parallelTransition = new ParallelTransition();

			parallelTransition.getChildren().addAll(
				new Timeline(new KeyFrame(duration, new KeyValue(scaleYProperty(), z))),
				new Timeline(new KeyFrame(duration, new KeyValue(scaleXProperty(), z)))
			);

			parallelTransition.play();
			setModified(true);
		}
	}

	/**
	 * Converts the given point in the coordinate system based on the canvas' origin. The given
	 * point must be in the coordinate system of a container widget (the top-left point is the origin).
	 * @param pt The point to convert.
	 * @return The converted point or null if the given point is null.
	 */
	public IPoint convertToOrigin(final IPoint pt) {
		final IPoint convertion;
		if(pt == null) {
			convertion = null;
		}else {
			convertion = ShapeFactory.INST.createPoint(pt);
			convertion.translate(-ORIGIN.getX(), -ORIGIN.getY());
		}
		return convertion;
	}

	/**
	 * @return The model of the canvas.
	 */
	public IDrawing getDrawing() {
		return drawing;
	}

	/**
	 * Sets the temporary view.
	 * @param view The new temporary view.
	 */
	public void setTempView(final ViewShape<?> view) {
		tempView.ifPresent(v -> {
			shapesPane.getChildren().remove(v);
			v.flush();
		});
		tempView = Optional.ofNullable(view);
		tempView.ifPresent(v -> {
			view.setMouseTransparent(true);
			shapesPane.getChildren().add(v);
		});
	}

	public ScrollPane getScrollPane() {
		Parent parent = getParent();
		while(parent != null && !(parent instanceof ScrollPane)) {
			parent = parent.getParent();
		}
		return (ScrollPane) parent;
	}

	public void addToWidgetLayer(final javafx.scene.Node node) {
		if(node != null) {
			widgetsPane.getChildren().add(node);
		}
	}

	public void removeFromWidgetLayer(final javafx.scene.Node node) {
		if(node != null) {
			widgetsPane.getChildren().remove(node);
		}
	}

	/**
	 * @return The views that the canvas contains.
	 */
	public Group getViews() {
		return shapesPane;
	}

	/**
	 * @param sh The shape to look for.
	 * @return The view corresponding to the given shape or nothing.
	 */
	public Optional<ViewShape<?>> getViewFromShape(final IShape sh) {
		if(sh == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(shapesToViewMap.get(sh));
	}
}
