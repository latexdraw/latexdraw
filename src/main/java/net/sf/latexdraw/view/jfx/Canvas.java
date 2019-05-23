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

import io.reactivex.disposables.Disposable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
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
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Flushable;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.malai.command.CmdHandler;
import org.malai.command.Command;
import org.malai.command.CommandsRegistry;
import org.malai.properties.Modifiable;
import org.malai.properties.Preferenciable;
import org.malai.properties.Reinitialisable;
import org.malai.properties.Zoomable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The JFX canvas where shapes are painted.
 * @author Arnaud Blouin
 */
public class Canvas extends Pane implements Preferenciable, Modifiable, Reinitialisable, CmdHandler, Zoomable,
	ViewsSynchroniserHandler, Flushable {
	/** The margin used to surround the drawing. */
	static int margins = 1500;

	/** The origin of the drawing in the whole drawing area. */
	public static final @NotNull Point ORIGIN = ShapeFactory.INST.createPoint(margins, margins);

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
	private final @NotNull Drawing drawing;
	/** The zoom applied on the canvas. */
	private final @NotNull DoubleProperty zoom;
	/** The current page of the canvas. */
	private final @NotNull PageView page;
	/** The views of the shape. */
	private final @NotNull Group shapesPane;
	/** The pane that contains widgets to handle shapes, such as handlers, text fields. */
	private final @NotNull Group widgetsPane;
	private final @NotNull Rectangle selectionBorder;
	private final @NotNull Rectangle ongoingSelectionBorder;
	private final @NotNull Map<Shape, ViewShape<?>> shapesToViewMap;
	/** The magnetic grid of the canvas. */
	private final @NotNull MagneticGrid magneticGrid;
	/** Defined whether the canvas has been modified. */
	private boolean modified;
	/** The temporary view that the canvas may contain. */
	private @NotNull Optional<ViewShape<?>> tempView;
	private final @NotNull ViewFactory viewFactory;
	private final @NotNull List<Disposable> disposables;

	/**
	 * Creates the canvas.
	 */
	@Inject
	public Canvas(final PreferencesService prefs, final ViewFactory viewFactory) {
		super();
		this.viewFactory = Objects.requireNonNull(viewFactory);
		modified = false;
		drawing = ShapeFactory.INST.createDrawing();
		zoom = new SimpleDoubleProperty(1d);
		tempView = Optional.empty();
		page = new PageView(prefs, getOrigin());
		magneticGrid = new MagneticGrid(this, prefs);
		disposables = new ArrayList<>();

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

		selectionBorder.setFocusTraversable(false);
		selectionBorder.setVisible(false);
		selectionBorder.setFill(null);
		selectionBorder.setStroke(Color.GRAY);
		selectionBorder.setStrokeLineCap(StrokeLineCap.BUTT);
		selectionBorder.getStrokeDashArray().addAll(7d, 7d);
		disposables.add(JavaFxObservable.eventsOf(selectionBorder, MouseEvent.MOUSE_ENTERED)
			.subscribe(evt -> setCursor(Cursor.HAND), ex -> BadaboomCollector.INSTANCE.add(ex)));
		disposables.add(JavaFxObservable.eventsOf(selectionBorder, MouseEvent.MOUSE_EXITED)
			.subscribe(evt -> setCursor(Cursor.DEFAULT), ex -> BadaboomCollector.INSTANCE.add(ex)));

		// Instead of triggering the update on each change, wait for 20 ms
		disposables.add(JavaFxObservable.<ObservableList<Shape>>changesOf(drawing.getSelection().getShapes())
			.throttleLast(20, TimeUnit.MILLISECONDS)
			.subscribe(next -> updateSelectionBorders(), ex -> BadaboomCollector.INSTANCE.add(ex)));

		// Bloody key shortcuts. To work the canvas must grab the focus
		// Must be a MOUSE_CLICKED, not a MOUSE_PRESSED, do not know why...
		disposables.add(JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_CLICKED)
			.subscribe(evt -> requestFocus(), ex -> BadaboomCollector.INSTANCE.add(ex)));

		CommandsRegistry.INSTANCE.addHandler(this);

		shapesPane.setFocusTraversable(false);

		prefWidthProperty().bind(Bindings.createDoubleBinding(() -> margins * 2d + prefs.getPage().getWidth() * Shape.PPC, prefs.pageProperty()));
		prefHeightProperty().bind(Bindings.createDoubleBinding(() -> margins * 2d + prefs.getPage().getHeight() * Shape.PPC, prefs.pageProperty()));
	}


	@Override
	public void flush() {
		disposables.forEach(disposable -> disposable.dispose());
		disposables.clear();
	}

	public @NotNull MagneticGrid getMagneticGrid() {
		return magneticGrid;
	}

	private void updateSelectionBorders() {
		if(selectionBorder.isDisable()) {
			return;
		}

		final List<Shape> selection = List.copyOf(drawing.getSelection().getShapes());

		if(selection.isEmpty()) {
			selectionBorder.setVisible(false);
		}else {
			final Rectangle2D rec = selection.stream().map(sh -> shapesToViewMap.get(sh)).filter(vi -> vi != null).map(vi -> {
				final Bounds b = vi.getBoundsInParent();
				return (Rectangle2D) new Rectangle2D.Double(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
			}).reduce(Rectangle2D::createUnion).orElseGet(() -> new Rectangle2D.Double());

			setVisibleSelectionBorder(rec.getMinX(), rec.getMinY(), rec.getWidth(), rec.getHeight(), false);
		}
	}

	private void setVisibleSelectionBorder(final double x, final double y, final double w, final double h, final boolean ongoing) {
		final Rectangle rec = ongoing ? ongoingSelectionBorder : selectionBorder;
		rec.setLayoutX(x);
		rec.setLayoutY(y);
		rec.setWidth(w);
		rec.setHeight(h);
		rec.setVisible(true);
	}


	public void setOngoingSelectionBorder(final Bounds bounds) {
		if(bounds == null) {
			ongoingSelectionBorder.setVisible(false);
		}else {
			setVisibleSelectionBorder(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), true);
		}
	}


	/**
	 * @return The selected views.
	 */
	public @NotNull List<ViewShape<?>> getSelectedViews() {
		return drawing.getSelection().getShapes().stream().map(sh -> shapesToViewMap.get(sh)).collect(Collectors.toList());
	}


	private final void defineShapeListToViewBinding() {
		drawing.getShapes().addListener((Change<? extends Shape> evt) -> {
			while(evt.next()) {
				if(evt.wasAdded()) {
					evt.getAddedSubList().forEach(sh -> viewFactory.createView(sh).ifPresent(v -> {
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
	public @NotNull Point getOrigin() {
		return ORIGIN;
	}

	@Override
	public int getPPCDrawing() {
		return Shape.PPC;
	}

	/**
	 * @return The page of the drawing area. Cannot be null.
	 */
	public @NotNull PageView getPage() {
		return page;
	}

	public void update() {
		updateSelectionBorders();
	}

	public @NotNull Rectangle getSelectionBorder() {
		return selectionBorder;
	}

	@Override
	public double getZoom() {
		return zoom.getValue();
	}

	public @NotNull DoubleProperty zoomProperty() {
		return zoom;
	}

	@Override
	public void onCmdExecuted(final Command cmd) {
		if(cmd instanceof Modifying) {
			update();
		}
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
			if(node != null  && !generalPreferences && uri.equals(node.getNamespaceURI())) {
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
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
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
	public @NotNull Point getTopRightDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.INST.createPoint(border.getMaxX(), border.getMinY());
	}

	@Override
	public @NotNull Point getBottomLeftDrawingPoint() {
		final Bounds border = shapesPane.getBoundsInLocal();
		return ShapeFactory.INST.createPoint(border.getMinX(), border.getMaxY());
	}

	@Override
	public @NotNull Point getOriginDrawingPoint() {
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
	public @NotNull Point2D getZoomedPoint(final double x, final double y) {
		final double zoomValue = zoom.getValue();
		return new Point2D.Double(x / zoomValue, y / zoomValue);
	}

	@Override
	public @NotNull Point2D getZoomedPoint(final java.awt.Point pt) {
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
	public Point convertToOrigin(final Point pt) {
		final Point convertion;
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
	public @NotNull Drawing getDrawing() {
		return drawing;
	}

	/**
	 * Sets the temporary view.
	 * @param view The new temporary view.
	 */
	public void setTempView(final @Nullable ViewShape<?> view) {
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
	public @NotNull Group getViews() {
		return shapesPane;
	}

	/**
	 * @param sh The shape to look for.
	 * @return The view corresponding to the given shape or nothing.
	 */
	public @NotNull Optional<ViewShape<?>> getViewFromShape(final Shape sh) {
		if(sh == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(shapesToViewMap.get(sh));
	}
}
