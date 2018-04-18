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
package net.sf.latexdraw.instruments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;
import net.sf.latexdraw.commands.shape.InitTextSetter;
import net.sf.latexdraw.commands.shape.SelectShapes;
import net.sf.latexdraw.commands.shape.TranslateShapes;
import net.sf.latexdraw.commands.shape.UpdateToGrid;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewPlot;
import net.sf.latexdraw.view.jfx.ViewShape;
import net.sf.latexdraw.view.jfx.ViewText;
import org.malai.command.Command;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.DoubleClick;
import org.malai.javafx.interaction.library.Press;
import org.malai.javafx.interaction.library.SrcTgtPointsData;

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.
 * @author Arnaud BLOUIN
 */
public class Hand extends CanvasInstrument {
	@Inject private MetaShapeCustomiser metaCustomiser;
	@Inject private TextSetter textSetter;

	public Hand() {
		super();
	}

	private final ListChangeListener<Node> viewHandler = evt -> {
		while(evt.next()) {
			if(evt.wasAdded()) {
				evt.getAddedSubList().forEach(v -> {
					v.setOnMouseEntered(mouseEvt -> {
						if(isActivated()) {
							canvas.setCursor(Cursor.HAND);
						}
					});
					v.setOnMouseExited(mouseEvt -> {
						if(isActivated()) {
							canvas.setCursor(Cursor.DEFAULT);
						}
					});
				});
			}
		}
	};

	@Override
	public void uninstallBindings() {
		canvas.getViews().getChildren().removeListener(viewHandler);
		super.uninstallBindings();

	}

	@Override
	protected void configureBindings() {
		canvas.getViews().getChildren().addListener(viewHandler);

		addBinding(new DnD2Select(this));

		bindPressureToSelectShape();
		bindDnDTranslate();

		dbleClickToInitTextSetter();

		keyNodeBinder(SelectShapes.class).on(canvas).with(KeyCode.A, LSystem.INSTANCE.getControlKey()).first(c -> {
			c.getShapes().addAll(canvas.getDrawing().getShapes());
			c.setDrawing(canvas.getDrawing());
		}).bind();

		keyNodeBinder(UpdateToGrid.class).on(canvas).with(KeyCode.U, LSystem.INSTANCE.getControlKey()).first(c -> {
			c.setShape(canvas.getDrawing().getSelection().duplicateDeep(false));
			c.setGrid(canvas.getMagneticGrid());
		}).when(i -> canvas.getMagneticGrid().isMagnetic()).bind();
	}

	/**
	 * Double click to initialise the text setter to edit plot and text shapes.
	 */
	private void dbleClickToInitTextSetter() {
		// For text shapes.
		nodeBinder(new DoubleClick(), InitTextSetter.class).
			on(canvas.getViews().getChildren()).
			map(i -> {
				final IText text = ((ViewText) i.getSrcObject().get().getParent()).getModel();
				return new InitTextSetter(textSetter, textSetter, null, ShapeFactory.INST.createPoint(text.getPosition().getX() * canvas.getZoom(),
					text.getPosition().getY() * canvas.getZoom()), text, null);
			}).
			when(i -> i.getSrcObject().isPresent() && i.getSrcObject().get().getParent() instanceof ViewText).
			strictStart().
			bind();

		// For plot shapes.
		nodeBinder(new DoubleClick(), InitTextSetter.class).
			on(canvas.getViews().getChildren()).
			map(i -> {
				final IPlot plot = getViewShape(i.getSrcObject()).map(view -> ((ViewPlot) view).getModel()).get();
				return new InitTextSetter(textSetter, textSetter, null, ShapeFactory.INST.createPoint(plot.getPosition().getX() * canvas.getZoom(),
					plot.getPosition().getY() * canvas.getZoom()), null, plot);
			}).
			when(i -> i.getSrcObject().isPresent() && i.getSrcObject().get().getParent() != null &&
				getViewShape(i.getSrcObject()).orElse(null) instanceof ViewPlot).
			strictStart().
			bind();
	}

	/**
	 * Pressure to select shapes
	 */
	private void bindPressureToSelectShape() {
		nodeBinder(new Press(), SelectShapes.class).on(canvas.getViews().getChildren()).first((i, c) -> {
			c.setDrawing(canvas.getDrawing());
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
			});
		}).bind();

		// A simple pressure on the canvas deselects the shapes
		nodeBinder(new Press(), SelectShapes.class).on(canvas).
			first((i, c) -> c.setDrawing(canvas.getDrawing())).
			when(i -> i.getSrcObject().orElse(null) instanceof Canvas).
			bind();
	}

	/**
	 * A DnD on a shape view allows to translate the underlying shape.
	 */
	private void bindDnDTranslate() {
		nodeBinder(new DnD(true, true), TranslateShapes.class).
			on(canvas.getViews().getChildren()).on(canvas.getSelectionBorder()).
			map(i -> new TranslateShapes(canvas.getDrawing(), canvas.getDrawing().getSelection().duplicateDeep(false))).
			then((i, c) -> {
				final IPoint startPt = grid.getTransformedPointToGrid(i.getSrcScenePoint());
				final IPoint endPt = grid.getTransformedPointToGrid(i.getTgtScenePoint());
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

	@Override
	public void onCmdDone(final Command cmd) {
		if(cmd instanceof TranslateShapes) {
			metaCustomiser.dimPosCustomiser.update();
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
		if(view != null && view.getUserData() instanceof ViewPlot) {
			return (ViewShape<?>) view.getUserData();
		}
		return view;
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


	private static class DnD2Select extends JfXWidgetBinding<SelectShapes, DnD, Hand, SrcTgtPointsData> {
		/** The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
		private Bounds selectionBorder;
		private List<IShape> selectedShapes;
		private List<ViewShape<?>> selectedViews;

		DnD2Select(final Hand hand) {
			super(hand, true, new DnD(), SelectShapes.class, Collections.singletonList(hand.canvas), false, null);
		}

		@Override
		public void first() {
			cmd.setDrawing(instrument.canvas.getDrawing());
			selectedShapes = new ArrayList<>(instrument.canvas.getDrawing().getSelection().getShapes());
			selectedViews = instrument.canvas.getSelectedViews();
			Platform.runLater(() -> instrument.canvas.requestFocus());
		}

		@Override
		public void then() {
			final IPoint start = instrument.getAdaptedOriginPoint(interaction.getSrcLocalPoint());
			final IPoint end = instrument.getAdaptedOriginPoint(interaction.getTgtLocalPoint());
			final double minX = Math.min(start.getX(), end.getX());
			final double maxX = Math.max(start.getX(), end.getX());
			final double minY = Math.min(start.getY(), end.getY());
			final double maxY = Math.max(start.getY(), end.getY());

			// Updating the rectangle used for the interim feedback and for the selection of shapes.
			selectionBorder = new BoundingBox(minX, minY, Math.max(maxX - minX, 1d), Math.max(maxY - minY, 1d));
			final Rectangle selectionRec = new Rectangle(selectionBorder.getMinX() + Canvas.ORIGIN.getX(),
				selectionBorder.getMinY() + Canvas.ORIGIN.getY(), selectionBorder.getWidth(), selectionBorder.getHeight());
			// Transforming the selection rectangle to match the transformation of the canvas.
			selectionRec.getTransforms().setAll(getInstrument().canvas.getLocalToSceneTransform());
			// Cleaning the selected shapes in the command.
			cmd.setShape(null);

			if(interaction.isShiftPressed()) {
				selectedViews.stream().filter(view -> !view.intersects(selectionBorder)).forEach(view -> cmd.addShape(view.getModel()));
			}else {
				if(interaction.isCtrlPressed()) {
					selectedShapes.forEach(sh -> cmd.addShape(sh));
				}
				if(!selectionBorder.isEmpty()) {
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
								//TODO log
							}
						}
						return view.intersects(bounds) &&
							((ViewShape<?>) view).getActivatedShapes().stream().anyMatch(sh -> !Shape.intersect(sh, selectionRec).getLayoutBounds().isEmpty());
					}).forEach(view -> cmd.addShape((IShape) view.getUserData()));
				}
			}
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
