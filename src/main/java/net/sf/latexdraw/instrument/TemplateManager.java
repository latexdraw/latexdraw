/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.command.Command;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.ui.JfxUI;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import net.sf.latexdraw.command.LoadTemplate;
import net.sf.latexdraw.command.UpdateTemplates;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument manages the templates.
 * @author Arnaud BLOUIN
 */
public class TemplateManager extends JfxInstrument implements Initializable {
	/** The menu item that permits to update the templates. */
	@FXML private Button updateTemplates;
	@FXML FlowPane templatePane;
	@FXML private TitledPane mainPane;
	@FXML private Label emptyLabel;
	private final @NotNull Drawing drawing;
	private final @NotNull StatusBarController statusController;
	private final @NotNull SVGDocumentGenerator svgGen;
	private final @NotNull JfxUI app;

	@Inject
	public TemplateManager(final Drawing drawing, final StatusBarController statusController, final SVGDocumentGenerator svgGen, final JfxUI app) {
		super();
		this.drawing = Objects.requireNonNull(drawing);
		this.statusController = Objects.requireNonNull(statusController);
		this.svgGen = Objects.requireNonNull(svgGen);
		this.app = Objects.requireNonNull(app);
	}


	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		emptyLabel.managedProperty().bind(emptyLabel.visibleProperty());
		emptyLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> templatePane.getChildren().isEmpty(), templatePane.getChildren()));
		emptyLabel.setFont(Font.font(emptyLabel.getFont().getFamily(), FontPosture.ITALIC, emptyLabel.getFont().getSize()));

		Command.executeAndFlush(new UpdateTemplates(templatePane, svgGen, false));

		setActivated(true);
		templatePane.setCursor(Cursor.MOVE);
	}

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		mainPane.setVisible(activated);
	}


	@Override
	protected void configureBindings() {
		buttonBinder()
			.toProduce(() -> new UpdateTemplates(templatePane, svgGen, true))
			.on(updateTemplates)
			.bind();

		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(() -> new LoadTemplate(svgGen, drawing))
			.on(templatePane)
			.first((i, c) -> {
				c.setFile(new File((String) i.getSrcObject().orElseThrow().getUserData()));
				c.setProgressBar(statusController.getProgressBar());
				c.setStatusWidget(statusController.getLabel());
				c.setUi(app);

				templatePane.setCursor(Cursor.CLOSED_HAND);
			})
			.then((i, c) -> {
				final Node srcObj = i.getSrcObject().orElseThrow();
				final Point3D pt3d = i.getTgtObject().orElseThrow().sceneToLocal(srcObj.localToScene(i.getTgtLocalPoint())).
					subtract(Canvas.ORIGIN.getX() + srcObj.getLayoutX(), Canvas.ORIGIN.getY() + srcObj.getLayoutY(), 0d);
				c.setPosition(ShapeFactory.INST.createPoint(pt3d));
			})
			.when(i -> i.getSrcObject().orElse(null) instanceof ImageView &&
				i.getSrcObject().get().getUserData() instanceof String &&
				i.getTgtObject().orElse(null) instanceof Canvas)
			.endOrCancel(i -> templatePane.setCursor(Cursor.MOVE))
			.bind();
	}
}
