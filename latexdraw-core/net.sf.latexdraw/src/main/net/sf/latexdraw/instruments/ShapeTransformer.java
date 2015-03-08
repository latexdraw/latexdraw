package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

/**
 * This instrument transforms (mirror, etc.) the selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-03-07<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeTransformer extends ShapePropertyCustomiser implements Initializable {
	/** The widget to mirror horizontally. */
	@FXML protected Button mirrorH;

	/** The widget to mirror vertically. */
	@FXML protected Button mirrorV;

	/** The widget to bottom align the shapes. */
	@FXML protected Button alignBot;

	/** The widget to left align the shapes. */
	@FXML protected Button alignLeft;

	/** The widget to right align the shapes. */
	@FXML protected Button alignRight;

	/** The widget to top align the shapes. */
	@FXML protected Button alignTop;

	/** The widget to middle horizontal align the shapes. */
	@FXML protected Button alignMidHoriz;

	/** The widget to middle vertical align the shapes. */
	@FXML protected Button alignMidVert;

	/** The widget to bottom-vertically distribute the shapes. */
	@FXML protected Button distribVertBot;

	/** The widget to equal-vertically distribute the shapes. */
	@FXML protected Button distribVertEq;

	/** The widget to middle-vertically distribute the shapes. */
	@FXML protected Button distribVertMid;

	/** The widget to top-vertically distribute the shapes. */
	@FXML protected Button distribVertTop;

	/** The widget to equal-horizontally distribute the shapes. */
	@FXML protected Button distribHorizEq;

	/** The widget to left-horizontally distribute the shapes. */
	@FXML protected Button distribHorizLeft;

	/** The widget to middle-horizontally distribute the shapes. */
	@FXML protected Button distribHorizMid;

	/** The widget to right-horizontally distribute the shapes. */
	@FXML protected Button distribHorizRight;

	@FXML VBox mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeTransformer() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		setActivated(!shape.isEmpty());
	}

	@Override
	protected void initialiseInteractors() {
		// try{
		// addInteractor(new Button2Mirror(this))
		// addInteractor(new Button2Align(this))
		// addInteractor(new Button2Distribute(this))
		// }catch{case ex: Throwable => BadaboomCollector.INSTANCE.add(ex)}
	}
}

// /**
// * Maps a button interaction with an action that aligns the selected shapes.
// */
// private sealed class Button2Align(ins:ShapeTransformer) extends
// InteractorImpl[AlignShapes, ButtonPressed, ShapeTransformer](ins, false,
// classOf[AlignShapes], classOf[ButtonPressed]) {
// override def initAction() {
// val but = interaction.getButton
//
// action.setShape(instrument.pencil.canvas.getDrawing.getSelection.duplicateDeep(false))
// if(but==instrument._alignBot) action.setAlignment(AlignmentType.bottom)
// else if(but==instrument._alignLeft) action.setAlignment(AlignmentType.left)
// else if(but==instrument._alignMidHoriz)
// action.setAlignment(AlignmentType.midHoriz)
// else if(but==instrument._alignMidVert)
// action.setAlignment(AlignmentType.midVert)
// else if(but==instrument._alignRight) action.setAlignment(AlignmentType.right)
// else if(but==instrument._alignTop) action.setAlignment(AlignmentType.top)
// action.setBorder(instrument.border.border)
// }
//
// override def isConditionRespected = {
// val but = interaction.getButton
// but==instrument._alignBot || but==instrument._alignLeft ||
// but==instrument._alignMidHoriz || but==instrument._alignMidVert ||
// but==instrument._alignRight || but==instrument._alignTop
// }
// }
//
// /**
// * Maps a button interaction with an action that distributes the selected
// shapes.
// */
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

