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

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.command.LatexProperties;
import net.sf.latexdraw.command.ModifyLatexProperties;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.TextProp;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.TextPosition;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;
import net.sf.latexdraw.view.jfx.ViewText;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument modifies texts.
 * @author Arnaud BLOUIN
 */
public class ShapeTextCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** The list to select the text position. */
	@FXML private ComboBox<TextPosition> textPos;
	/** This text field permits to add latex packages that will be used during compilation. */
	@FXML private TextArea packagesField;
	/** The error log field. */
	@FXML private TextArea logField;
	@FXML private TitledPane mainPane;
	private final @NotNull LaTeXDataService latexData;

	@Inject
	public ShapeTextCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing,
		final LaTeXDataService latexData) {
		super(hand, pencil, canvas, drawing, editing);
		//FIXME update the instrument when text property commands are done.
		this.latexData = Objects.requireNonNull(latexData);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	public void initialize(final URL url, final ResourceBundle bundle) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		final Map<TextPosition, Image> cachePos = new EnumMap<>(TextPosition.class);
		cachePos.put(TextPosition.TOP_LEFT, new Image("/res/textPosTL.png")); //NON-NLS
		cachePos.put(TextPosition.TOP, new Image("/res/textPosT.png")); //NON-NLS
		cachePos.put(TextPosition.TOP_RIGHT, new Image("/res/textPosTR.png")); //NON-NLS
		cachePos.put(TextPosition.RIGHT, new Image("/res/textPosRight.png")); //NON-NLS
		cachePos.put(TextPosition.BOT_RIGHT, new Image("/res/textPosBR.png")); //NON-NLS
		cachePos.put(TextPosition.BOT, new Image("/res/textPosB.png")); //NON-NLS
		cachePos.put(TextPosition.BOT_LEFT, new Image("/res/textPosBL.png")); //NON-NLS
		cachePos.put(TextPosition.LEFT, new Image("/res/textPosLeft.png")); //NON-NLS
		cachePos.put(TextPosition.CENTER, new Image("/res/textPosCentre.png")); //NON-NLS
		initComboBox(textPos, cachePos, TextPosition.values());
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isTypeOf(TextProp.class)) {
			setActivated(true);
			final TextPosition tp = shape.getTextPosition();

			textPos.getSelectionModel().select(tp);

			// Otherwise it means that this field is currently being edited and must not be updated.
			if(!packagesField.isFocused()) {
				packagesField.setText(latexData.getPackages());
			}

			// Updating the log field.
			Platform.runLater(() -> shape.getShapes().stream().filter(sh -> sh instanceof Text &&
				canvas.getViewFromShape(sh).orElse(null) instanceof ViewText &&
				((ViewText) canvas.getViewFromShape(sh).get()).getCompilationData().isPresent()).findFirst().ifPresent(txt -> {
					final ViewText view = (ViewText) canvas.getViewFromShape(txt).get();
					final Future<?> currentCompil = view.getCurrentCompilation();

					if(currentCompil != null) {
						try {
							currentCompil.get();
						}catch(final InterruptedException ex) {
							Thread.currentThread().interrupt();
							BadaboomCollector.INSTANCE.add(ex);
						}catch(final ExecutionException ex) {
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
		addComboPropBinding(textPos, ShapeProperties.TEXT_POSITION);

		textInputBinder(() -> new ModifyLatexProperties(latexData, LatexProperties.PACKAGES, null)).on(packagesField).
			then((i, c) -> c.setValue(i.getWidget().getText())).bind();
	}
}
