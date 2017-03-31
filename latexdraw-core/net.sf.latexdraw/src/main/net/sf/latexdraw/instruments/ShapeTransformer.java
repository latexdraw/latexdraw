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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sf.latexdraw.actions.shape.AlignShapes;
import net.sf.latexdraw.actions.shape.DistributeShapes;
import net.sf.latexdraw.actions.shape.MirrorShapes;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.malai.javafx.instrument.library.ButtonInteractor;

/**
 * This instrument transforms (mirror, etc.) the selected shapes.
 */
public class ShapeTransformer extends ShapePropertyCustomiser implements Initializable {
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

	/**
	 * Creates the instrument.
	 */
	ShapeTransformer() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
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
	protected void update(final IGroup shape) {
		setActivated(hand.isActivated() && !shape.isEmpty());
	}

	@Override
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		addInteractor(new Button2Align(this));
		addInteractor(new Button2Mirror(this));
		addInteractor(new Button2Distribute(this));
	}

	private static class Button2Align extends ButtonInteractor<AlignShapes, ShapeTransformer> {
		Button2Align(final ShapeTransformer ins) throws InstantiationException, IllegalAccessException {
			super(ins, AlignShapes.class, ins.alignBot, ins.alignLeft, ins.alignMidHoriz, ins.alignMidVert, ins.alignRight, ins.alignTop);
		}

		@Override
		public void initAction() {
			action.setAlignment((AlignShapes.Alignment) getInteraction().getWidget().getUserData());
			action.setCanvas(getInstrument().canvas);
			action.setShape(instrument.pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}
	}

	private static class Button2Mirror extends ButtonInteractor<MirrorShapes, ShapeTransformer> {
		Button2Mirror(final ShapeTransformer ins) throws InstantiationException, IllegalAccessException {
			super(ins, MirrorShapes.class, ins.mirrorH, ins.mirrorV);
		}

		@Override
		public void initAction() {
			action.setHorizontally(interaction.getWidget() == instrument.mirrorH);
			action.setShape(instrument.pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}
	}

	private static class Button2Distribute extends ButtonInteractor<DistributeShapes, ShapeTransformer> {
		Button2Distribute(final ShapeTransformer ins) throws InstantiationException, IllegalAccessException {
			super(ins, DistributeShapes.class, ins.distribHorizEq, ins.distribHorizLeft, ins.distribHorizMid, ins.distribHorizRight,
				ins.distribVertBot, ins.distribVertEq, ins.distribVertMid, ins.distribVertTop);
		}

		@Override
		public void initAction() {
			action.setDistribution((DistributeShapes.Distribution) getInteraction().getWidget().getUserData());
			action.setCanvas(getInstrument().canvas);
			action.setShape(instrument.pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}
	}
}
