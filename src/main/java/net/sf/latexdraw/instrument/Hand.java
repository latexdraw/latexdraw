/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.binding.Bindings;
import io.github.interacto.jfx.binding.JfxWidgetBinding;
import io.github.interacto.jfx.command.ChangeCursor;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.interaction.library.DoubleClick;
import io.github.interacto.jfx.interaction.library.MouseEntered;
import io.github.interacto.jfx.interaction.library.MouseExited;
import io.github.interacto.jfx.interaction.library.Press;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.rxjavafx.sources.ListChange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;
import net.sf.latexdraw.command.shape.InitTextSetter;
import net.sf.latexdraw.command.shape.SelectShapes;
import net.sf.latexdraw.command.shape.TranslateShapes;
import net.sf.latexdraw.command.shape.UpdateToGrid;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Flushable;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import net.sf.latexdraw.view.jfx.ViewGroup;
import net.sf.latexdraw.view.jfx.ViewPlot;
import net.sf.latexdraw.view.jfx.ViewShape;
import net.sf.latexdraw.view.jfx.ViewText;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.
 * @author Arnaud BLOUIN
 */
public class Hand extends CanvasInstrument implements Flushable {
	private final @NotNull TextSetter textSetter;
	private final @NotNull PreferencesService prefs;
	private final @NotNull Map<Node, Tuple<JfxWidgetBinding<?, ?, ?>, JfxWidgetBinding<?, ?, ?>>> cursorsEvents;

	@Inject
	public Hand(final Canvas canvas, final MagneticGrid grid, final TextSetter textSetter, final PreferencesService prefs) {
		super(canvas, grid);
		this.textSetter = Objects.requireNonNull(textSetter);
		this.prefs = Objects.requireNonNull(prefs);
		cursorsEvents = new HashMap<>();
	}

	/**
	 * Adds RX features to change the cursor when mouse hovering a shape
	 * @param evt The added/removed shape to process
	 */
	private void setUpCursorOnShapeView(final ListChange<Node> evt) {
		switch(evt.getFlag()) {
			case ADDED:
				final var binding1 = Bindings.nodeBinder(this)
					.toProduce(() -> new ChangeCursor(Cursor.HAND, canvas))
					.usingInteraction(MouseEntered::new)
					.on(evt.getValue())
					.bind();
				final var binding2 = Bindings.nodeBinder(this)
					.toProduce(() -> new ChangeCursor(Cursor.DEFAULT, canvas))
					.usingInteraction(MouseExited::new)
					.on(evt.getValue())
					.bind();

				cursorsEvents.put(evt.getValue(), new Tuple<>(binding1, binding2));
				break;
			case REMOVED:
				Optional.ofNullable(cursorsEvents.get(evt.getValue()))
					.ifPresent(tuple -> {
						tuple.a.uninstallBinding();
						tuple.b.uninstallBinding();
					});
				break;
			case UPDATED:
				break;
		}
	}

	@Override
	protected void configureBindings() {
		disposables.add(JavaFxObservable.changesOf(canvas.getViews().getChildren())
			.observeOn(JavaFxScheduler.platform())
			.subscribe(next -> setUpCursorOnShapeView(next), ex -> BadaboomCollector.INSTANCE.add(ex)));

		bindDnD2Select();
		bindPressureToSelectShape();
		bindDnDTranslate();
		dbleClickToInitTextSetter();

		shortcutBinder()
			.toProduce(() -> new SelectShapes(canvas.getDrawing()))
			.on(canvas)
			.with(KeyCode.A, SystemUtils.getInstance().getControlKey())
			.first(c -> c.getShapes().addAll(canvas.getDrawing().getShapes()))
			.bind();

		shortcutBinder()
			.toProduce(() -> new UpdateToGrid(canvas.getMagneticGrid(), canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(canvas)
			.with(KeyCode.U, SystemUtils.getInstance().getControlKey())
			.when(i -> prefs.isMagneticGrid())
			.bind();
	}

	/**
	 * Double click to initialise the text setter to edit plot and text shapes.
	 */
	private void dbleClickToInitTextSetter() {
		// For text shapes.
		nodeBinder()
			.usingInteraction(DoubleClick::new)
			.toProduce(i -> {
				final Text text = ((ViewText) i.getSrcObject().orElseThrow().getParent()).getModel();
				return new InitTextSetter(textSetter, textSetter, null, ShapeFactory.INST.createPoint(text.getPosition().getX() * canvas.getZoom(),
					text.getPosition().getY() * canvas.getZoom()), text, null);
			})
			.on(canvas.getViews().getChildren())
			.when(i -> i.getSrcObject().isPresent() && i.getSrcObject().get().getParent() instanceof ViewText)
			.strictStart()
			.bind();

		// For plot shapes.
		nodeBinder()
			.usingInteraction(DoubleClick::new)
			.toProduce(i -> {
				final Plot plot = getViewShape(i.getSrcObject()).map(view -> ((ViewPlot) view).getModel()).orElseThrow();
				return new InitTextSetter(textSetter, textSetter, null, ShapeFactory.INST.createPoint(plot.getPosition().getX() * canvas.getZoom(),
					plot.getPosition().getY() * canvas.getZoom()), null, plot);
			})
			.on(canvas.getViews().getChildren())
			.when(i -> i.getSrcObject().isPresent() && i.getSrcObject().get().getParent() != null && getViewShape(i.getSrcObject()).orElse(null) instanceof ViewPlot)
			.strictStart()
			.bind();
	}

	/**
	 * Pressure to select shapes
	 */
	private void bindPressureToSelectShape() {
		nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(() -> new SelectShapes(canvas.getDrawing()))
			.on(canvas.getViews().getChildren())
			.first((i, c) ->
				getViewShape(i.getSrcObject()).map(src -> src.getModel()).ifPresent(targetSh -> {
					if(i.isShiftPressed()) {
						canvas.getDrawing().getSelection().getShapes().stream().filter(sh -> sh != targetSh).forEach(sh -> c.addShape(sh));
						return;
					}
					if(i.isCtrlPressed()) {
						canvas.getDrawing().getSelection().getShapes().forEach(sh -> c.addShape(sh));
						c.addShape(targetSh);
						return;
					}
					c.setShape(targetSh);
				}))
			.when(i -> !canvas.getSelectedViews().contains(getViewShape(i.getSrcObject()).orElse(null)))
			.bind();

		// A simple pressure on the canvas deselects the shapes
		nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(() -> new SelectShapes(canvas.getDrawing()))
			.on(canvas)
			.when(i -> i.getSrcObject().orElse(null) instanceof Canvas)
			.bind();
	}

	/**
	 * A DnD on a shape view allows to translate the underlying shape.
	 */
	private void bindDnDTranslate() {
		nodeBinder()
			.usingInteraction(() -> new DnD(true, true))
			.toProduce(i -> new TranslateShapes(canvas.getDrawing(), canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(canvas.getViews().getChildren()).on(canvas.getSelectionBorder())
			.then((i, c) -> {
				final Point startPt = grid.getTransformedPointToGrid(i.getSrcScenePoint());
				final Point endPt = grid.getTransformedPointToGrid(i.getTgtScenePoint());
				c.setT(endPt.getX() - startPt.getX(), endPt.getY() - startPt.getY());
				canvas.update();
			})
			.when(i -> i.getButton() == MouseButton.PRIMARY && !canvas.getDrawing().getSelection().isEmpty())
			.continuousExecution()
			.first((i, c) -> {
				i.getSrcObject().ifPresent(node -> node.requestFocus());
				canvas.setCursor(Cursor.MOVE);
			})
			.endOrCancel(i -> canvas.setCursor(Cursor.DEFAULT))
			.cancel(i -> canvas.update())
			.strictStart()
			.bind();
	}

	private void bindDnD2Select() {
		final List<Shape> selectedShapes = new ArrayList<>();
		final List<ViewShape<?>> selectedViews = new ArrayList<>();

		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(() -> new SelectShapes(canvas.getDrawing()))
			.on(canvas)
			.continuousExecution()
			.first(c -> {
				selectedShapes.addAll(canvas.getDrawing().getSelection().getShapes());
				selectedViews.addAll(canvas.getSelectedViews());
				canvas.requestFocus();
			})
			.then((i, c) -> {
				/* The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
				final Bounds selectionBorder;
				final Point start = getAdaptedOriginPoint(i.getSrcLocalPoint());
				final Point end = getAdaptedOriginPoint(i.getTgtLocalPoint());
				final double minX = Math.min(start.getX(), end.getX());
				final double maxX = Math.max(start.getX(), end.getX());
				final double minY = Math.min(start.getY(), end.getY());
				final double maxY = Math.max(start.getY(), end.getY());

				// Updating the rectangle used for the interim feedback and for the selection of shapes.
				selectionBorder = new BoundingBox(minX, minY, Math.max(maxX - minX, 1d), Math.max(maxY - minY, 1d));
				// Cleaning the selected shapes in the command.
				c.setShape(null);

				if(i.isShiftPressed()) {
					selectedViews.stream().filter(view -> !view.intersects(selectionBorder)).forEach(view -> c.addShape(view.getModel()));
					return;
				}
				if(i.isCtrlPressed()) {
					selectedShapes.forEach(sh -> c.addShape(sh));
				}
				if(!selectionBorder.isEmpty()) {
					updateSelection(selectionBorder).forEach(s -> c.addShape(s));
				}
				canvas.setOngoingSelectionBorder(selectionBorder);
			})
			.endOrCancel(i -> {
				selectedShapes.clear();
				selectedViews.clear();
				canvas.setOngoingSelectionBorder(null);
				canvas.setCursor(Cursor.DEFAULT);
			})
			.when(i -> i.getButton() == MouseButton.PRIMARY && i.getSrcObject().orElse(null) == canvas)
			.bind();
	}

	private List<Shape> updateSelection(final Bounds selectionBorder) {
		final Rectangle selectionRec = new Rectangle(selectionBorder.getMinX() + Canvas.ORIGIN.getX(),
			selectionBorder.getMinY() + Canvas.ORIGIN.getY(), selectionBorder.getWidth(), selectionBorder.getHeight());
		// Transforming the selection rectangle to match the transformation of the canvas.
		selectionRec.getTransforms().setAll(canvas.getLocalToSceneTransform());

		return canvas.getViews().getChildren().stream().filter(view -> {
			Bounds bounds;
			final Transform transform = view.getLocalToParentTransform();
			if(transform.isIdentity()) {
				bounds = selectionBorder;
			}else {
				try {
					bounds = transform.createInverse().transform(selectionBorder);
				}catch(final NonInvertibleTransformException ex) {
					bounds = selectionBorder;
				}
			}
			return view.intersects(bounds) &&
				((ViewShape<?>) view).getActivatedShapes().stream().anyMatch(sh -> !javafx.scene.shape.Shape.intersect(sh, selectionRec).getLayoutBounds().isEmpty());
		})
			.map(view -> (Shape) view.getUserData())
			.collect(Collectors.toList());
	}

	@Override
	public void setActivated(final boolean activ) {
		if(activated != activ) {
			super.setActivated(activ);
			canvas.getSelectionBorder().setVisible(activated);
			canvas.getSelectionBorder().setDisable(!activated);
			if(activated) {
				canvas.update();
			}
		}
	}


	/**
	 * A tricky workaround to get the real plot view hidden behind its content views (Bezier curve, dots, etc.).
	 * If the view has a ViewPlot as its user data, this view plot is returned. The source view is returned otherwise.
	 * setMouseTransparency cannot be used since the mouse over would not work anymore.
	 * @param view The view to check. Cannot be null.
	 * @return The given view or the plot view.
	 */
	private static ViewShape<?> getRealViewShape(final ViewShape<?> view) {
		if(view == null) {
			return null;
		}

		// Checking whether the shape is not part of another shape
		ViewShape<?> currView = view;

		while(currView.getUserData() instanceof ViewShape) {
			currView = (ViewShape<?>) currView.getUserData();
		}

		// Checking whether the shape is not part of a group
		Node parent = currView;
		while(parent.getParent() != null && !(parent.getParent() instanceof ViewGroup)) {
			parent = parent.getParent();
		}

		if(parent.getParent() instanceof ViewGroup) {
			return (ViewShape<?>) parent.getParent();
		}

		return currView;
	}

	private static Optional<ViewShape<?>> getViewShape(final Optional<Node> node) {
		return node.map(n -> {
			Node parent = n.getParent();
			while(parent != null && !(parent instanceof ViewShape<?>)) {
				parent = parent.getParent();
			}
			return getRealViewShape((ViewShape<?>) parent);
		});
	}

	@Override
	public void flush() {
		cursorsEvents.values().forEach(tuple -> {
			tuple.a.uninstallBinding();
			tuple.b.uninstallBinding();
		});
	}
}
