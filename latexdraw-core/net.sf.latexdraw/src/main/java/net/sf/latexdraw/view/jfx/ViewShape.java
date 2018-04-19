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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * The base class of a JFX shape view.
 * @param <S> The type of the model.
 * @author Arnaud Blouin
 */
public abstract class ViewShape<S extends IShape> extends Group {
	/** The model of the view. */
	protected final S model;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewShape(final S sh) {
		super();
		model = sh;

		setUserData(model);
		setFocusTraversable(false);
	}

	public Collection<Shape> getActivatedShapes() {
		return getActivatedGroupNodes(this);
	}

	private static Collection<Shape> getActivatedGroupNodes(final Group gp) {
		// Adding all the shape children
		final Collection<Shape> shapes = gp.getChildren().stream().filter(node -> node instanceof Shape && node.isVisible() && !node.isDisable()).
			map(node -> (Shape) node).collect(Collectors.toList());

		// Adding all the view shape children
		shapes.addAll(gp.getChildren().stream().filter(node -> node instanceof ViewShape<?> && node.isVisible() && !node.isDisable()).
			map(vs -> ((ViewShape<?>) vs).getActivatedShapes()).flatMap(st -> st.stream()).collect(Collectors.toList()));

		// Adding the shapes contained in groups that are not view shapes
		shapes.addAll(gp.getChildren().stream().filter(node -> node instanceof Group && !(node instanceof ViewShape<?>)).
			map(node -> getActivatedGroupNodes((Group) node)).flatMap(st -> st.stream()).collect(Collectors.toList()));

		// Adding the images contained in the group
		shapes.addAll(gp.getChildren().stream().filter(node -> node instanceof ImageView && node.isVisible() && !node.isDisable()).
			map(node -> {
				final Bounds bounds = node.getBoundsInParent();
				final Rectangle rec = new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
				rec.setFill(Color.WHITE);
				rec.getTransforms().setAll(gp.getLocalToSceneTransform());
				return rec;
			}).collect(Collectors.toList()));

		return shapes;
	}

	/**
	 * Flushes the view.
	 */
	public void flush() {
		setUserData(null);
		getChildren().clear();
		// Should be overridden to flush the bindings.
	}

	public S getModel() {
		return model;
	}

	protected Optional<Canvas> getCanvasParent() {
		Parent parent = getParent();
		while(parent != null && !(parent instanceof Canvas)) {
			parent = parent.getParent();
		}

		if(parent != null) {
			return Optional.of((Canvas) parent);
		}
		return Optional.empty();
	}

	protected void checkToExecuteOnUIThread(final Runnable cmd) {
		if(Platform.isFxApplicationThread()) {
			cmd.run();
		}else {
			Platform.runLater(cmd);
		}
	}
}
