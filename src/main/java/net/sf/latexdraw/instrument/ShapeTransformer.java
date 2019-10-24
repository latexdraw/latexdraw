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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sf.latexdraw.command.shape.AlignShapes;
import net.sf.latexdraw.command.shape.DistributeShapes;
import net.sf.latexdraw.command.shape.MirrorShapes;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * This instrument transforms (mirror, etc.) the selected shapes.
 * @author Arnaud Blouin
 */
public class ShapeTransformer extends ShapePropertyCustomiser {
	/** The widget to mirror horizontally. */
	@FXML private Button mirrorH;
	/** The widget to mirror vertically. */
	@FXML private Button mirrorV;
	/** The widget to BOTTOM align the shapes. */
	@FXML private Button alignBot;
	/** The widget to LEFT align the shapes. */
	@FXML private Button alignLeft;
	/** The widget to RIGHT align the shapes. */
	@FXML private Button alignRight;
	/** The widget to TOP align the shapes. */
	@FXML private Button alignTop;
	/** The widget to middle horizontal align the shapes. */
	@FXML private Button alignMidHoriz;
	/** The widget to middle vertical align the shapes. */
	@FXML private Button alignMidVert;
	/** The widget to BOTTOM-vertically distribute the shapes. */
	@FXML private Button distribVertBot;
	/** The widget to equal-vertically distribute the shapes. */
	@FXML private Button distribVertEq;
	/** The widget to middle-vertically distribute the shapes. */
	@FXML private Button distribVertMid;
	/** The widget to TOP-vertically distribute the shapes. */
	@FXML private Button distribVertTop;
	/** The widget to equal-horizontally distribute the shapes. */
	@FXML private Button distribHorizEq;
	/** The widget to LEFT-horizontally distribute the shapes. */
	@FXML private Button distribHorizLeft;
	/** The widget to middle-horizontally distribute the shapes. */
	@FXML private Button distribHorizMid;
	/** The widget to RIGHT-horizontally distribute the shapes. */
	@FXML private Button distribHorizRight;
	@FXML private VBox mainPane;

	@Inject
	public ShapeTransformer(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		alignBot.setUserData(AlignShapes.Alignment.BOTTOM);
		alignLeft.setUserData(AlignShapes.Alignment.LEFT);
		alignMidHoriz.setUserData(AlignShapes.Alignment.MID_HORIZ);
		alignMidVert.setUserData(AlignShapes.Alignment.MID_VERT);
		alignRight.setUserData(AlignShapes.Alignment.RIGHT);
		alignTop.setUserData(AlignShapes.Alignment.TOP);
		distribHorizEq.setUserData(DistributeShapes.Distribution.HORIZ_EQ);
		distribHorizLeft.setUserData(DistributeShapes.Distribution.HORIZ_LEFT);
		distribHorizMid.setUserData(DistributeShapes.Distribution.HORIZ_MID);
		distribHorizRight.setUserData(DistributeShapes.Distribution.HORIZ_RIGHT);
		distribVertBot.setUserData(DistributeShapes.Distribution.VERT_BOT);
		distribVertEq.setUserData(DistributeShapes.Distribution.VERT_EQ);
		distribVertMid.setUserData(DistributeShapes.Distribution.VERT_MID);
		distribVertTop.setUserData(DistributeShapes.Distribution.VERT_BOT);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final Group shape) {
		setActivated(hand.isActivated() && shape.size() > 1);
	}

	@Override
	protected void configureBindings() {
		buttonBinder()
			.toProduce(i -> new AlignShapes(canvas, (AlignShapes.Alignment) i.getWidget().getUserData(), canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(alignBot, alignLeft, alignMidHoriz, alignMidVert, alignRight, alignTop)
			.bind();

		buttonBinder()
			.toProduce(i -> new MirrorShapes(i.getWidget() == mirrorH, canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(mirrorH, mirrorV)
			.bind();

		buttonBinder()
			.toProduce(i -> new DistributeShapes(canvas, (DistributeShapes.Distribution) i.getWidget().getUserData(),
				canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(distribHorizEq, distribHorizLeft, distribHorizMid, distribHorizRight, distribVertBot, distribVertEq, distribVertMid, distribVertTop)
			.bind();
	}
}
