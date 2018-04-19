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

import java.io.File;
import java.net.URL;
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
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.LoadTemplate;
import net.sf.latexdraw.commands.UpdateTemplates;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.DnD;

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
	@Inject private IDrawing drawing;
	@Inject private StatusBarController statusController;


	public TemplateManager() {
		super();
	}


	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		emptyLabel.managedProperty().bind(emptyLabel.visibleProperty());
		emptyLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> templatePane.getChildren().isEmpty(), templatePane.getChildren()));
		emptyLabel.setFont(Font.font(emptyLabel.getFont().getFamily(), FontPosture.ITALIC, emptyLabel.getFont().getSize()));

		final UpdateTemplates cmd = new UpdateTemplates();
		cmd.setTemplatesPane(templatePane);
		cmd.updateThumbnails(false);

		if(cmd.canDo()) {
			cmd.doIt();
		}
		cmd.flush();
		setActivated(true);
	}

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		mainPane.setVisible(activated);
	}

	@Override
	public void interimFeedback() {
		templatePane.setCursor(Cursor.MOVE);
	}


	@Override
	protected void configureBindings() {
		buttonBinder(UpdateTemplates.class).on(updateTemplates).first(c -> {
			c.setTemplatesPane(templatePane);
			c.updateThumbnails(true);
		}).bind();

		nodeBinder(new DnD(), LoadTemplate.class).
			on(templatePane).
			first((i, c) -> {
				c.setDrawing(drawing);
				c.setFile(new File((String) i.getSrcObject().get().getUserData()));
				c.setOpenSaveManager(SVGDocumentGenerator.INSTANCE);
				c.setProgressBar(statusController.getProgressBar());
				c.setStatusWidget(statusController.getLabel());
				c.setUi(LaTeXDraw.getInstance());
			}).
			then((i, c) -> {
				final Node srcObj = i.getSrcObject().get();
				final Point3D pt3d = i.getTgtObject().get().sceneToLocal(srcObj.localToScene(i.getTgtLocalPoint())).
					subtract(Canvas.ORIGIN.getX() + srcObj.getLayoutX(), Canvas.ORIGIN.getY() + srcObj.getLayoutY(), 0d);
				c.setPosition(ShapeFactory.INST.createPoint(pt3d));
			}).
			feedback(() -> templatePane.setCursor(Cursor.CLOSED_HAND)).
			when(i -> i.getSrcObject().orElse(null) instanceof ImageView &&
				i.getSrcObject().get().getUserData() instanceof String &&
				i.getTgtObject().orElse(null) instanceof Canvas).
			bind();
	}
}
