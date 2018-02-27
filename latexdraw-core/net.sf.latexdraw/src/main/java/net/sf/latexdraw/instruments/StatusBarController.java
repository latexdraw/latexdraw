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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * The controller for the status bar.
 * @author Arnaud Blouin
 */
public class StatusBarController implements Initializable {
	@FXML private Label label;
	@FXML private ProgressBar progressBar;
	@FXML private Hyperlink link;

	/**
	 * Creates the controller.
	 */
	public StatusBarController() {
		super();
	}

	/**
	 * @return The label of status bar.
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * @return The progress bar of the status bar.
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @return The hyperlink of the status bar.
	 */
	public Hyperlink getLink() {
		return link;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		progressBar.managedProperty().bind(progressBar.visibleProperty());
		link.setVisible(false);
		link.setOnAction(evt -> {
			if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				new Thread(() -> {
					try {
						Desktop.getDesktop().browse(new URI(link.getText()));
						link.setVisible(false);
						label.setVisible(false);
					}catch(IOException | URISyntaxException ex) {
						BadaboomCollector.INSTANCE.add(ex);
					}
				}).start();
			}
		});

	}
}
