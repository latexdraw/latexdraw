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
	public ShapeTransformer() {
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
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		setActivated(hand.isActivated() && shape.size() > 1);
	}

	@Override
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		addInteractor(new Button2Align(this));
//		 addInteractor(new Button2Align(this))
//		 addInteractor(new Button2Distribute(this))
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
}

// private sealed class Button2Distribute(ins:ShapeTransformer) extends
// InteractorImpl[DistributeShapes, ButtonPressed, ShapeTransformer](ins, false,
// classOf[DistributeShapes], classOf[ButtonPressed]) {
// override def initAction() {
// val but = interaction.getButton
//
// action.setShape(instrument.pencil.canvas.getDrawing.getSelection.duplicateDeep(false))
// if(but==instrument._distribHorizEq)
// action.setDistribution(DistributionType.horizEq)
// else if(but==instrument._distribHorizLeft)
// action.setDistribution(DistributionType.horizLeft)
// else if(but==instrument._distribHorizMid)
// action.setDistribution(DistributionType.horizMid)
// else if(but==instrument._distribHorizRight)
// action.setDistribution(DistributionType.horizRight)
// else if(but==instrument._distribVertBot)
// action.setDistribution(DistributionType.vertBot)
// else if(but==instrument._distribVertEq)
// action.setDistribution(DistributionType.vertEq)
// else if(but==instrument._distribVertMid)
// action.setDistribution(DistributionType.vertMid)
// else if(but==instrument._distribVertTop)
// action.setDistribution(DistributionType.vertTop)
// action.setBorder(instrument.border.border)
// }
//
// override def isConditionRespected = {
// val but = interaction.getButton
// but==instrument._distribHorizEq || but==instrument._distribHorizLeft ||
// but==instrument._distribHorizMid || but==instrument._distribHorizRight ||
// but==instrument._distribVertBot || but==instrument._distribVertEq ||
// but==instrument._distribVertMid || but==instrument._distribVertTop
// }
// }
//
// /**
// * Maps a button interaction with an action that mirrors the selected shapes.
// */
// private sealed class Button2Mirror(ins:ShapeTransformer) extends
// InteractorImpl[MirrorShapes, ButtonPressed, ShapeTransformer](ins, false,
// classOf[MirrorShapes], classOf[ButtonPressed]) {
// override def initAction() {
// action.setShape(instrument.pencil.canvas.getDrawing.getSelection.duplicateDeep(false))
// action.setHorizontally(interaction.getButton==instrument._mirrorH)
// }
//
// override def isConditionRespected =
// interaction.getButton==instrument._mirrorH ||
// interaction.getButton==instrument._mirrorV
// }

