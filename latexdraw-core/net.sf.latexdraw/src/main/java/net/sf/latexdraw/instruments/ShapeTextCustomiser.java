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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.commands.LatexProperties;
import net.sf.latexdraw.commands.ModifyLatexProperties;
import net.sf.latexdraw.commands.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.view.jfx.ViewText;
import net.sf.latexdraw.view.latex.LaTeXGenerator;

/**
 * This instrument modifies texts.
 * @author Arnaud BLOUIN
 */
public class ShapeTextCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The button that selects the bottom-left text position. */
	@FXML private ToggleButton blButton;
	/** The button that selects the bottom text position. */
	@FXML private ToggleButton bButton;
	/** The button that selects the bottom-right text position. */
	@FXML private ToggleButton brButton;
	/** The button that selects the top-left text position. */
	@FXML private ToggleButton tlButton;
	/** The button that selects the top text position. */
	@FXML private ToggleButton tButton;
	/** The button that selects the top-right text position. */
	@FXML private ToggleButton trButton;
	/** The button that selects the left text position. */
	@FXML private ToggleButton lButton;
	/** The button that selects the right text position. */
	@FXML private ToggleButton rButton;
	/** The button that selects the centre text position. */
	@FXML private ToggleButton centreButton;
	/** This text field permits to add latex packages that will be used during compilation. */
	@FXML private TextArea packagesField;
	/** The error log field. */
	@FXML private TextArea logField;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeTextCustomiser() {
		super();
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	public void initialize(final URL url, final ResourceBundle bundle) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(ITextProp.class)) {
			setActivated(true);
			final TextPosition tp = shape.getTextPosition();

			bButton.setSelected(tp == TextPosition.BOT);
			brButton.setSelected(tp == TextPosition.BOT_RIGHT);
			blButton.setSelected(tp == TextPosition.BOT_LEFT);
			tButton.setSelected(tp == TextPosition.TOP);
			trButton.setSelected(tp == TextPosition.TOP_RIGHT);
			tlButton.setSelected(tp == TextPosition.TOP_LEFT);
			centreButton.setSelected(tp == TextPosition.CENTER);
			lButton.setSelected(tp == TextPosition.LEFT);
			rButton.setSelected(tp == TextPosition.RIGHT);

			// Otherwise it means that this field is currently being edited and must not be updated.
			if(!packagesField.isFocused()) {
				packagesField.setText(LaTeXGenerator.getPackages());
			}

			// Updating the log field.
			Platform.runLater(() -> shape.getShapes().stream().filter(sh -> sh instanceof IText &&
				canvas.getViewFromShape(sh).orElse(null) instanceof ViewText &&
				((ViewText) canvas.getViewFromShape(sh).get()).getCompilationData().isPresent()).findFirst().ifPresent(txt -> {
					final ViewText view = (ViewText) canvas.getViewFromShape(txt).get();
					final Future<?> currentCompil = view.getCurrentCompilation();

					if(currentCompil != null) {
						try {
							currentCompil.get();
						}catch(final InterruptedException | ExecutionException ex) {
							BadaboomCollector.INSTANCE.add(ex);
						}
					}

					logField.setText(view.getCompilationData().orElse(""));
				}));
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void configureBindings() {
		addTogglePropBinding(bButton, ShapeProperties.TEXT_POSITION, TextPosition.BOT);
		addTogglePropBinding(blButton, ShapeProperties.TEXT_POSITION, TextPosition.BOT_LEFT);
		addTogglePropBinding(brButton, ShapeProperties.TEXT_POSITION, TextPosition.BOT_RIGHT);
		addTogglePropBinding(tButton, ShapeProperties.TEXT_POSITION, TextPosition.TOP);
		addTogglePropBinding(tlButton, ShapeProperties.TEXT_POSITION, TextPosition.TOP_LEFT);
		addTogglePropBinding(centreButton, ShapeProperties.TEXT_POSITION, TextPosition.CENTER);
		addTogglePropBinding(lButton, ShapeProperties.TEXT_POSITION, TextPosition.LEFT);
		addTogglePropBinding(rButton, ShapeProperties.TEXT_POSITION, TextPosition.RIGHT);
		addTogglePropBinding(trButton, ShapeProperties.TEXT_POSITION, TextPosition.TOP_RIGHT);

		textInputBinder(ModifyLatexProperties.class).on(packagesField).
			first(c -> c.setProperty(LatexProperties.PACKAGES)).
			then((i, c) -> c.setValue(i.getWidget().getText())).bind();
	}
}
