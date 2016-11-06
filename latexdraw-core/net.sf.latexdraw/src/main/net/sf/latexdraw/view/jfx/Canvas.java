/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.view.jfx;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LNumber;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.presentation.ConcretePresentation;
import org.malai.properties.Zoomable;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Defines a canvas that draw the drawing and manages the selected shapes.
 */
public class Canvas extends Pane implements ConcretePresentation, ActionHandler, Zoomable, ViewsSynchroniserHandler {
	/** The margin used to surround the drawing. */
	public static final int MARGINS = 2500;

	/** The origin of the drawing in the whole drawing area. */
	public static final @NonNull IPoint ORIGIN = ShapeFactory.createPoint(MARGINS, MARGINS);

	/** The model of the view. */
	protected final @NonNull IDrawing drawing;

	/** The zoom applied on the canvas. */
	protected final IUnary<Double> zoom;

	/** The current page of the canvas. */
	private final @NonNull PageView page;

	/** The views of the shape. */
	private final @NonNull Group shapesPane;

	/** The pane that contains widgets to handle shapes, such as handlers, text fields. */
	private final @NonNull Group widgetsPane;

	private final @NonNull Rectangle selectionBorder;

	private final @NonNull Map<IShape, ViewShape<?, ?>> shapesToViewMap;

	/** The magnetic grid of the canvas. */
	protected MagneticGridImpl magneticGrid;

	/** Defined whether the canvas has been modified. */
	protected boolean modified;

	/** The temporary view that the canvas may contain. */
	protected Optional<ViewShape<?, ?>> tempView;

	/**
	 * Creates the canvas.
	 */
	public Canvas() {
		super();

		modified = false;
		drawing = ShapeFactory.createDrawing();
		zoom = new ActiveUnary<>(1.0);
		tempView = Optional.empty();
		page = new PageView(Page.USLETTER, getOrigin());
		magneticGrid = new MagneticGridImpl(this);
		widgetsPane = new Group();
		shapesPane = new Group();
		shapesToViewMap = new HashMap<>();
		selectionBorder = new Rectangle();

		getChildren().add(page);
		getChildren().add(shapesPane);
		getChildren().add(widgetsPane);
		widgetsPane.getChildren().add(selectionBorder);
		widgetsPane.relocate(ORIGIN.getX(), ORIGIN.getY());
		shapesPane.relocate(ORIGIN.getX(), ORIGIN.getY());

		setPrefWidth(MARGINS * 2 + page.getPage().getWidth() * IShape.PPC);
		setPrefHeight(MARGINS * 2 + page.getPage().getHeight() * IShape.PPC);

		defineShapeListToViewBinding();
		configureSelection();

		//		IRectangle rec = ShapeFactory.createRectangle(ShapeFactory.createPoint(150, 120), 200, 60);
		//		rec.setThickness(10.0);
		//		rec.setGradColStart(DviPsColors.APRICOT);
		//		rec.setGradColEnd(DviPsColors.BLUEVIOLET);
		//		rec.setFillingStyle(FillingStyle.GRAD);
		//		rec.setFillingCol(DviPsColors.RED);
		//		getDrawing().addShape(rec);

		// FlyweightThumbnail.setCanvas(this);
		ActionsRegistry.INSTANCE.addHandler(this);

		shapesPane.setFocusTraversable(true);
		shapesPane.addEventHandler(MouseEvent.ANY, evt -> shapesPane.requestFocus());
	}


	public MagneticGrid getMagneticGrid() {
		return magneticGrid;
	}

	private void configureSelection() {
		selectionBorder.setMouseTransparent(true);
		selectionBorder.setVisible(false);
		selectionBorder.setFill(null);
		selectionBorder.setStroke(Color.GRAY);
		selectionBorder.setStrokeLineCap(StrokeLineCap.BUTT);
		selectionBorder.getStrokeDashArray().addAll(7d, 7d);

		final ObservableList<IShape> selection = (ObservableList<IShape>) drawing.getSelection().getShapes();

		selection.addListener((Change<? extends IShape> evt) -> {
			if(selection.isEmpty()) selectionBorder.setVisible(false);
			else {
				final double zoomLevel = getZoom();
				final Rectangle2D rec = selection.stream().map(sh -> {
					Bounds b = shapesToViewMap.get(sh).getBoundsInLocal();
					return (Rectangle2D) new Rectangle2D.Double(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
				}).reduce(Rectangle2D::createUnion).get();

				selectionBorder.setLayoutX(rec.getMinX() * zoomLevel);
				selectionBorder.setLayoutY(rec.getMinY() * zoomLevel);
				selectionBorder.setWidth(rec.getWidth() * zoomLevel);
				selectionBorder.setHeight(rec.getHeight() * zoomLevel);
				selectionBorder.setVisible(true);
			}
		});
	}

	private void defineShapeListToViewBinding() {
		if(drawing.getShapes() instanceof ObservableList) {
			((ObservableList<IShape>) drawing.getShapes()).addListener((Change<? extends IShape> evt) -> {
				while(evt.next()) {
					if(evt.wasAdded()) {
						evt.getAddedSubList().forEach(sh -> ViewFactory.INSTANCE.createView(sh).ifPresent(v -> {
							shapesToViewMap.put(sh, v);
							shapesPane.getChildren().add(v);
						}));
					}else if(evt.wasRemoved()) {
						evt.getRemoved().forEach(sh -> shapesPane.getChildren().remove(shapesToViewMap.remove(sh)));
					}
				}
			});
		}
	}

	// /**
	// * Repaints the canvas.
	// * @param withZoom True: zoom will be activated.
	// * @param withGrid True: the grid will be painted.
	// */
	// public void repaint(final boolean withZoom, final boolean withGrid) {
	// final GraphicsContext gc = getGraphicsContext2D();
	// final double zoomValue = getZoom();
	// final boolean mustZoom = withZoom && !LNumber.equalsDouble(zoomValue, 1.);
	//
	// gc.setFill(Color.WHITE);
	// gc.fillRect(0, 0, getWidth(), getHeight());
	//
	// // Displaying the magnetic grid.
	// if(withGrid && magneticGrid.isGridDisplayed())
	// magneticGrid.paint(gc);
	//
	// gc.translate(ORIGIN.getX(), ORIGIN.getY());
	//
	// if(mustZoom)
	// gc.scale(zoomValue, zoomValue);
	//
	// page.paint(gc, ShapeFactory.createPoint());
	//
	// // Getting the clip must be done here to consider the scaling and translation.
	//// final Rectangle clip = gc.getClipBounds();
	//
	//// synchronized(views){
	//// for(final IViewShape view : views)
	//// view.paint(g, clip);
	//// }
	//
	//// if(temp!=null)
	//// temp.paint(g, clip);
	//
	// if(mustZoom)
	// gc.scale(1./zoomValue, 1./zoomValue);
	//
	//// borderIns.paint(g);
	//
	// gc.translate(-ORIGIN.getX(), -ORIGIN.getY());
	// }

	/**
	 * @return The point where the page is located.
	 */
	public @NonNull IPoint getOrigin() {
		return ORIGIN;
	}

	@Override
	public int getPPCDrawing() {
		return IShape.PPC;
	}

	/**
	 * @return The page of the drawing area. Cannot be null.
	 */
	public @NonNull PageView getPage() {
		return page;
	}

	@Override
	public void update() {
		// Nothing to do.
	}

	@Override
	public double getZoom() {
		return zoom.getValue();
	}

	@Override
	public void onActionExecuted(final Action a) {
		update();
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
	public void onActionAborted(final Action a) {
		/* Nothing to do. */
	}

	@Override
	public void onActionAdded(final Action a) {
		/* Nothing to do. */
	}

	@Override
	public void onActionCancelled(final Action a) {
		/* Nothing to do. */
	}

	@Override
	public void onActionDone(final Action a) {
		/* Nothing to do. */
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document == null || root == null) return;

		Element elt;

		if(!generalPreferences) {
			final String ns = nsURI == null || nsURI.isEmpty() ? "" : nsURI + ':'; //$NON-NLS-1$
			elt = document.createElement(ns + LNamespace.XML_ZOOM);
			elt.appendChild(document.createTextNode(String.valueOf(getZoom())));
			root.appendChild(elt);
		}

		magneticGrid.save(generalPreferences, nsURI, document, root);
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		if(meta == null) return;
		// Getting the list of meta information tags.
		final NodeList nl = meta.getChildNodes();
		Node node;
		int i;
		final int size = nl.getLength();
		final String uri = nsURI == null ? "" : nsURI; //$NON-NLS-1$
		String name;

		// For each meta information tag.
		for(i = 0; i < size; i++) {
			node = nl.item(i);

			// Must be a latexdraw tag.
			if(node != null && uri.equals(node.getNamespaceURI())) {
				name = node.getNodeName();

				if(!generalPreferences && name.endsWith(LNamespace.XML_ZOOM))
					setZoom(Double.NaN, Double.NaN, Double.parseDouble(node.getTextContent()));
			} // if
		}// for

		magneticGrid.load(generalPreferences, nsURI, meta);
	}

	@Override
	public boolean isModified() {
		return modified || magneticGrid.isModified();
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
		if(!modif) magneticGrid.setModified(false);
	}

	@Override
	public void reinit() {
		synchronized(shapesPane) {
			shapesPane.getChildren().clear();
		}
		zoom.setValue(1.);
		update();
	}

	@Override
	public IPoint getTopRightDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.createPoint(border.getMaxX(), border.getMinY());
	}

	@Override
	public IPoint getBottomLeftDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.createPoint(border.getMinX(), border.getMaxY());
	}

	@Override
	public IPoint getOriginDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.createPoint(border.getMinX(), (border.getMaxY() - border.getMinY()) / 2.0);
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
		if(z <= getMaxZoom() && z >= getMinZoom() && !LNumber.equalsDouble(z, zoom.getValue())) {
			// final double oldZoom = zoom.getValue();
			zoom.setValue(z);

			// if(GLibUtilities.isValidCoordinate(x) && GLibUtilities.isValidCoordinate(y)) {
			// final double dx = (z-oldZoom)*(x-ORIGIN.getX())/oldZoom;
			// final double dy = (z-oldZoom)*(y-ORIGIN.getY())/oldZoom;
			// final Point pt = scrollpane.getViewport().getViewPosition();
			// pt.x += dx;
			// pt.y += dy;
			// getScrollpane().getViewport().setViewPosition(pt);
			// }
			//
			// borderIns.update();
			update();
			setModified(true);
		}
	}

	/**
	 * Converts the given point in the coordinate system based on the canvas' origin. The given
	 * point must be in the coordinate system of a container widget (the top-left point is the
	 * origin).
	 * @param pt The point to convert.
	 * @return The converted point or null if the given point is null.
	 */
	public IPoint convertToOrigin(final IPoint pt) {
		final IPoint convertion;
		if(pt == null) convertion = null;
		else {
			convertion = ShapeFactory.createPoint(pt);
			convertion.translate(-ORIGIN.getX(), -ORIGIN.getY());
		}
		return convertion;
	}

	/**
	 * @return The model of the canvas.
	 */
	public @NonNull IDrawing getDrawing() {
		return drawing;
	}

	/**
	 * Sets the temporary view.
	 * @param view The new temporary view.
	 */
	public void setTempView(final @Nullable ViewShape<?, ?> view) {
		tempView.ifPresent(v -> shapesPane.getChildren().remove(v));
		tempView = Optional.ofNullable(view);
		tempView.ifPresent(v -> shapesPane.getChildren().add(v));
	}

	public ScrollPane getScrollPane() {
		Parent parent = getParent();
		while(parent!=null && !(parent instanceof ScrollPane)) {
			parent = parent.getParent();
		}
		return (ScrollPane) parent;
	}

	public void addToWidgetLayer(final javafx.scene.Node node) {
		if(node!=null) {
			widgetsPane.getChildren().add(node);
		}
	}

	public boolean removeFromWidgetLayer(final javafx.scene.Node node) {
		return node != null && widgetsPane.getChildren().remove(node);
	}
}
