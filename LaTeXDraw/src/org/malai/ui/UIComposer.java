package org.malai.ui;

import java.awt.Component;

/**
 * A UI composer is a object that composes a user interface using instruments and presentations.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 10/31/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 * @param <T> The type of widget produced by the composer.
 */
public abstract class UIComposer<T extends Component> {
	/** The widget composed by the composer. */
	protected T widget;



	/**
	 * Creates the composer.
	 * @since 0.2
	 */
	public UIComposer() {
		super();
	}


	/**
	 * This method composes the user interface using instruments, presentations and widgets of the interactive system.
	 * @param progressBar The progress bar that can be used to show the progress of the UI composition. Can be null.
	 */
	public abstract void compose(final IProgressBar progressBar);


	/**
	 * Changes the visibility of the given widget and may launch a process of recomposition/adaptation of the UI if needed.
	 * @param widget The widget to change its visibility.
	 * @param visible True: the widget will be visible.
	 * @since 0.2
	 */
	public void setWidgetVisible(final Component widget, final boolean visible) {
		if(widget!=null)
			widget.setVisible(visible);
	}


	/**
	 * @return The widget composed by the composer.
	 * @since 0.2
	 */
	public T getWidget() {
		return widget;
	}
}
