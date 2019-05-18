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
package net.sf.latexdraw.instrument;

import io.reactivex.disposables.Disposable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.ListChange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import net.sf.latexdraw.view.jfx.ViewPlot;
import net.sf.latexdraw.view.jfx.ViewShape;
import net.sf.latexdraw.view.jfx.ViewText;
import org.jetbrains.annotations.NotNull;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.DoubleClick;
import org.malai.javafx.interaction.library.Press;
import org.malai.javafx.interaction.library.SrcTgtPointsData;

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.
 * @author Arnaud BLOUIN
 */
public class Hand extends CanvasInstrument implements Flushable {
	private final @NotNull TextSetter textSetter;
	private final @NotNull PreferencesService prefs;
	private final @NotNull List<Disposable> disposables;
	private final @NotNull Map<Node, Tuple<Disposable, Disposable>> cursorsEvents;

	@Inject
	public Hand(final Canvas canvas, final MagneticGrid grid, final TextSetter textSetter, final PreferencesService prefs) {
		super(canvas, grid);
		this.textSetter = Objects.requireNonNull(textSetter);
		this.prefs = Objects.requireNonNull(prefs);
		disposables = new ArrayList<>();
		cursorsEvents = new HashMap<>();
	}

	/**
	 * Adds RX features to change the cursor when mouse hovering a shape
	 * @param evt The added/removed shape to process
	 */
	private void setUpCursorOnShapeView(final ListChange<Node> evt) {
		switch(evt.getFlag()) {
			case ADDED:
				final Disposable disposable1 = JavaFxObservable.eventsOf(evt.getValue(), MouseEvent.MOUSE_ENTERED)
					.filter(mouseEnter -> isActivated())
					.subscribe(mouseEnter -> canvas.setCursor(Cursor.HAND));
				final Disposable disposable2 = JavaFxObservable.eventsOf(evt.getValue(), MouseEvent.MOUSE_EXITED)
					.filter(mouseEnter -> isActivated())
					.subscribe(mouseEnter -> canvas.setCursor(Cursor.DEFAULT));
				cursorsEvents.put(evt.getValue(), new Tuple<>(disposable1, disposable2));
				break;
			case REMOVED:
				Optional.ofNullable(cursorsEvents.get(evt.getValue()))
					.ifPresent(tuple -> {
						tuple.a.dispose();
						tuple.b.dispose();
					});
				break;

		}
	}

	@Override
	protected void configureBindings() {
		disposables.add(JavaFxObservable.changesOf(canvas.getViews().getChildren())
			.subscribe(next -> setUpCursorOnShapeView(next), ex -> BadaboomCollector.INSTANCE.add(ex)));

		addBinding(new DnD2Select(this));

		bindPressureToSelectShape();
		bindDnDTranslate();

		dbleClickToInitTextSetter();

		keyNodeBinder(() -> new SelectShapes(canvas.getDrawing()))
			.on(canvas)
			.with(KeyCode.A, SystemUtils.getInstance().getControlKey())
			.first(c -> c.getShapes().addAll(canvas.getDrawing().getShapes()))
			.bind();

		keyNodeBinder(() -> new UpdateToGrid(canvas.getMagneticGrid(), canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(canvas).
			with(KeyCode.U, SystemUtils.getInstance().getControlKey())
			.when(i -> prefs.isMagneticGrid())
			.bind();
	}

	/**
	 * Double click to initialise the text setter to edit plot and text shapes.
	 */
	private void dbleClickToInitTextSetter() {
		// For text shapes.
		nodeBinder(new DoubleClick(), i -> {
			final Text text = ((ViewText) i.getSrcObject().orElseThrow().getParent()).getModel();
			return new InitTextSetter(textSetter, textSetter, null, ShapeFactory.INST.createPoint(text.getPosition().getX() * canvas.getZoom(),
				text.getPosition().getY() * canvas.getZoom()), text, null);
		}).on(canvas.getViews().getChildren()).
			when(i -> i.getSrcObject().isPresent() && i.getSrcObject().get().getParent() instanceof ViewText).
			strictStart().
			bind();

		// For plot shapes.
		nodeBinder(new DoubleClick(), i -> {
			final Plot plot = getViewShape(i.getSrcObject()).map(view -> ((ViewPlot) view).getModel()).orElseThrow();
			return new InitTextSetter(textSetter, textSetter, null, ShapeFactory.INST.createPoint(plot.getPosition().getX() * canvas.getZoom(),
				plot.getPosition().getY() * canvas.getZoom()), null, plot);
		}).on(canvas.getViews().getChildren()).
			when(i -> i.getSrcObject().isPresent() && i.getSrcObject().get().getParent() != null && getViewShape(i.getSrcObject()).orElse(null) instanceof ViewPlot).
			strictStart().
			bind();
	}

	/**
	 * Pressure to select shapes
	 */
	private void bindPressureToSelectShape() {
		nodeBinder(new Press(), () -> new SelectShapes(canvas.getDrawing())).on(canvas.getViews().getChildren()).first((i, c) ->
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
			})).when(i -> !canvas.getSelectedViews().contains(getViewShape(i.getSrcObject()).orElse(null))).bind();

		// A simple pressure on the canvas deselects the shapes
		nodeBinder(new Press(), () -> new SelectShapes(canvas.getDrawing())).on(canvas).
			when(i -> i.getSrcObject().orElse(null) instanceof Canvas).
			bind();
	}

	/**
	 * A DnD on a shape view allows to translate the underlying shape.
	 */
	private void bindDnDTranslate() {
		nodeBinder(new DnD(true, true), i -> new TranslateShapes(canvas.getDrawing(), canvas.getDrawing().getSelection().duplicateDeep(false))).
			on(canvas.getViews().getChildren()).on(canvas.getSelectionBorder()).
			then((i, c) -> {
				final Point startPt = grid.getTransformedPointToGrid(i.getSrcScenePoint());
				final Point endPt = grid.getTransformedPointToGrid(i.getTgtScenePoint());
				c.setT(endPt.getX() - startPt.getX(), endPt.getY() - startPt.getY());
			}).
			when(i -> i.getButton() == MouseButton.PRIMARY && !canvas.getDrawing().getSelection().isEmpty()).
			exec().
			first((i, c) -> {
				i.getSrcObject().ifPresent(node -> Platform.runLater(() -> node.requestFocus()));
				canvas.setCursor(Cursor.MOVE);
			}).
			cancel((i, c) -> canvas.update()).
			strictStart().
			bind();
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

	@Override
	public void interimFeedback() {
		// The rectangle used for the interim feedback of the selection is removed.
		canvas.setOngoingSelectionBorder(null);
		canvas.setCursor(Cursor.DEFAULT);
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

		ViewShape<?> parent = view;
		while(parent.getUserData() instanceof ViewShape) {
			parent = (ViewShape<?>) parent.getUserData();
		}

		return parent;
	}

	private static Optional<ViewShape<?>> getViewShape(final Optional<Node> node) {
		if(node.isPresent()) {
			final Node value = node.get();
			Node parent = value.getParent();

			while(parent != null && !(parent instanceof ViewShape<?>)) {
				parent = parent.getParent();
			}

			return Optional.ofNullable(getRealViewShape((ViewShape<?>) parent));
		}
		return Optional.empty();
	}

	@Override
	public void flush() {
		disposables.forEach(d -> d.dispose());
		cursorsEvents.values().forEach(tuple -> {
			tuple.a.dispose();
			tuple.b.dispose();
		});
	}


	private static class DnD2Select extends JfXWidgetBinding<SelectShapes, DnD, Hand, SrcTgtPointsData> {
		/** The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
		private Bounds selectionBorder;
		private List<Shape> selectedShapes;
		private List<ViewShape<?>> selectedViews;

		DnD2Select(final Hand hand) {
			super(hand, true, new DnD(), i -> new SelectShapes(hand.canvas.getDrawing()), Collections.singletonList(hand.canvas), false, null);
		}

		@Override
		public void first() {
			selectedShapes = new ArrayList<>(instrument.canvas.getDrawing().getSelection().getShapes());
			selectedViews = instrument.canvas.getSelectedViews();
			Platform.runLater(() -> instrument.canvas.requestFocus());
		}

		@Override
		public void then() {
			final Point start = instrument.getAdaptedOriginPoint(interaction.getSrcLocalPoint());
			final Point end = instrument.getAdaptedOriginPoint(interaction.getTgtLocalPoint());
			final double minX = Math.min(start.getX(), end.getX());
			final double maxX = Math.max(start.getX(), end.getX());
			final double minY = Math.min(start.getY(), end.getY());
			final double maxY = Math.max(start.getY(), end.getY());

			// Updating the rectangle used for the interim feedback and for the selection of shapes.
			selectionBorder = new BoundingBox(minX, minY, Math.max(maxX - minX, 1d), Math.max(maxY - minY, 1d));
			// Cleaning the selected shapes in the command.
			cmd.setShape(null);

			if(interaction.isShiftPressed()) {
				selectedViews.stream().filter(view -> !view.intersects(selectionBorder)).forEach(view -> cmd.addShape(view.getModel()));
				return;
			}
			if(interaction.isCtrlPressed()) {
				selectedShapes.forEach(sh -> cmd.addShape(sh));
			}
			if(!selectionBorder.isEmpty()) {
				updateSelection();
			}
		}

		private void updateSelection() {
			final Rectangle selectionRec = new Rectangle(selectionBorder.getMinX() + Canvas.ORIGIN.getX(),
				selectionBorder.getMinY() + Canvas.ORIGIN.getY(), selectionBorder.getWidth(), selectionBorder.getHeight());
			// Transforming the selection rectangle to match the transformation of the canvas.
			selectionRec.getTransforms().setAll(getInstrument().canvas.getLocalToSceneTransform());

			instrument.canvas.getViews().getChildren().stream().filter(view -> {
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
			}).forEach(view -> cmd.addShape((Shape) view.getUserData()));
		}

		@Override
		public boolean when() {
			return interaction.getButton() == MouseButton.PRIMARY && interaction.getSrcObject().orElse(null) == instrument.canvas;
		}

		@Override
		public void feedback() {
			instrument.canvas.setOngoingSelectionBorder(selectionBorder);
			selectionBorder = null;
		}
	}
}
