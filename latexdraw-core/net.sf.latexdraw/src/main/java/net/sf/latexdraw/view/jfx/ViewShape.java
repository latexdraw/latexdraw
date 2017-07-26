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
package net.sf.latexdraw.view.jfx;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The base class of a JFX shape view.
 * @param <S> The type of the model.
 * @author Arnaud Blouin
 */
public abstract class ViewShape<S extends IShape> extends Group {
	/** The model of the view. */
	protected final @NonNull S model;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewShape(final @NonNull S sh) {
		super();
		model = sh;

		setUserData(model);
		setFocusTraversable(false);
	}

	/**
	 * Flushes the view.
	 */
	public void flush() {
		setUserData(null);
		getChildren().clear();
		// Should be overridden to flush the bindings.
	}

	public @NonNull S getModel() {
		return model;
	}

	protected Optional<Canvas> getCanvasParent() {
		Parent parent = getParent();
		while(parent != null && !(parent instanceof Canvas)) {
			parent = parent.getParent();
		}

		if(parent != null) return Optional.of((Canvas) parent);
		return Optional.empty();
	}

	protected void checkToExecuteOnUIThread(final Runnable cmd) {
		if(Platform.isFxApplicationThread()) {
			cmd.run();
		}else {
			Platform.runLater(() -> cmd.run());
		}
	}
}
