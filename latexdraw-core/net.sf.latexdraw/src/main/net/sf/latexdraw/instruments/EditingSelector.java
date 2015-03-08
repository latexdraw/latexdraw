/*
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.latexdraw.actions.ModifyPencilStyle;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LangTool;

import org.malai.action.Action;
import org.malai.javafx.action.library.ActivateInactivateInstruments;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.library.ToggleButtonInteractor;

import com.google.inject.Inject;

/**
 * This instrument selects the pencil or the hand.<br>
 * <br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class EditingSelector extends JfxInstrument implements Initializable {
	/** The button that allows to select the instrument Hand. */
	@FXML ToggleButton handB;

	/** The button that allows to select the instrument Pencil to draw dots. */
	@FXML ToggleButton dotB;

	/**
	 * The button that allows to select the instrument Pencil to draw free hand
	 * shapes.
	 */
	@FXML ToggleButton freeHandB;

	/** The button that allows to select the instrument Pencil to add texts. */
	@FXML ToggleButton textB;

	/**
	 * The button that allows to select the instrument Pencil to add rectangles.
	 */
	@FXML ToggleButton recB;

	/** The button that allows to select the instrument Pencil to add squares. */
	@FXML ToggleButton squareB;

	/** The button that allows to select the instrument Pencil to add ellipses. */
	@FXML ToggleButton ellipseB;

	/** The button that allows to select the instrument Pencil to add circles. */
	@FXML ToggleButton circleB;

	/** The button that allows to select the instrument Pencil to add lines. */
	@FXML ToggleButton linesB;

	/** The button that allows to select the instrument Pencil to add polygons. */
	@FXML ToggleButton polygonB;

	/**
	 * The button that allows to select the instrument Pencil to add bezier
	 * curves.
	 */
	@FXML ToggleButton bezierB;

	/**
	 * The button that allows to select the instrument Pencil to add closed
	 * bezier curves.
	 */
	@FXML ToggleButton bezierClosedB;

	/** The button that allows to select the instrument Pencil to add grids. */
	@FXML ToggleButton gridB;

	/** The button that allows to select the instrument Pencil to add axes. */
	@FXML ToggleButton axesB;

	/** The button that allows to select the instrument Pencil to add rhombuses. */
	@FXML ToggleButton rhombusB;

	/** The button that allows to select the instrument Pencil to add triangles. */
	@FXML ToggleButton triangleB;

	/** The button that allows to select the instrument Pencil to add arcs. */
	@FXML ToggleButton arcB;

	/** The button that allows to select the instrument Pencil to add pictures. */
	@FXML ToggleButton picB;

	/**
	 * The button that allows to select the instrument Pencil to add plotted
	 * curves.
	 */
	@FXML ToggleButton plotB;

	/** The button that allows to insert some code (converted in shapes). */
	@FXML Button codeB;

	/** The instrument Hand. */
	@Inject Hand hand;

	/** The instrument Pencil. */
	@Inject Pencil pencil;

	/**
	 * The instrument that manages instruments that customise shapes and the
	 * pencil.
	 */
	@Inject MetaShapeCustomiser metaShapeCustomiser;

	/** The instrument that manages selected shapes. */
	@Inject Border border;

	/** The instrument used to delete shapes. */
	@Inject ShapeDeleter deleter;

	final Map<ToggleButton, EditionChoice> button2EditingChoiceMap;

	private Stage codeInserterDialogue;

	/**
	 * Creates the instrument.
	 * 
	 * @since 4.0
	 */
	public EditingSelector() {
		super();
		button2EditingChoiceMap = new HashMap<>();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		button2EditingChoiceMap.put(dotB, EditionChoice.DOT);
		button2EditingChoiceMap.put(textB, EditionChoice.TEXT);
		button2EditingChoiceMap.put(freeHandB, EditionChoice.FREE_HAND);
		button2EditingChoiceMap.put(arcB, EditionChoice.CIRCLE_ARC);
		button2EditingChoiceMap.put(axesB, EditionChoice.AXES);
		button2EditingChoiceMap.put(bezierB, EditionChoice.BEZIER_CURVE);
		button2EditingChoiceMap.put(bezierClosedB, EditionChoice.BEZIER_CURVE_CLOSED);
		button2EditingChoiceMap.put(circleB, EditionChoice.CIRCLE);
		button2EditingChoiceMap.put(ellipseB, EditionChoice.ELLIPSE);
		button2EditingChoiceMap.put(gridB, EditionChoice.GRID);
		button2EditingChoiceMap.put(linesB, EditionChoice.LINES);
		button2EditingChoiceMap.put(polygonB, EditionChoice.POLYGON);
		button2EditingChoiceMap.put(recB, EditionChoice.RECT);
		button2EditingChoiceMap.put(rhombusB, EditionChoice.RHOMBUS);
		button2EditingChoiceMap.put(squareB, EditionChoice.SQUARE);
		button2EditingChoiceMap.put(triangleB, EditionChoice.TRIANGLE);
		button2EditingChoiceMap.put(picB, EditionChoice.PICTURE);
		button2EditingChoiceMap.put(plotB, EditionChoice.PLOT);
		setActivated(true);
		handB.setSelected(true);
		// codeInserter.setActivated(false);
		hand.setActivated(true);
		pencil.setActivated(false);
		metaShapeCustomiser.update();
	}

	/** @return The created latexdraw dialogue box. */
	protected Stage getInsertCodeDialogue() {
		if(codeInserterDialogue==null) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../glib/views/jfx/ui/InsertCode.fxml"), LangTool.INSTANCE.getBundle());
				final Scene scene = new Scene(root);
				codeInserterDialogue = new Stage(StageStyle.UTILITY);
				codeInserterDialogue.setTitle(LangTool.INSTANCE.getBundle().getString("InsertPSTricksCodeFrame.0"));
				codeInserterDialogue.setScene(scene);
				codeInserterDialogue.centerOnScreen();
			}catch(final Exception e) {
				e.printStackTrace();
				BadaboomCollector.INSTANCE.add(e);
			}
		}
		return codeInserterDialogue;
	}

	@Override
	protected void initialiseInteractors() {
		try {
			final List<Node> nodes = new ArrayList<>(button2EditingChoiceMap.keySet());
			nodes.add(handB);
			// addInteractor(new ButtonPressed2AddText(this));
			addInteractor(new ButtonPressed2DefineStylePencil(this));
			addInteractor(new ButtonPressed2ActivateIns(this, nodes));
			// addInteractor(new ButtonPressed2LaunchCodeInserter(this));
		}catch(InstantiationException|IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	@Override
	public void setActivated(final boolean isActivated) {
		super.setActivated(isActivated);
		button2EditingChoiceMap.keySet().forEach(but -> but.setVisible(activated));
		handB.setVisible(activated);
		codeB.setVisible(activated);
	}

	// /**
	// * @param ab The widget to test.
	// * @return True if the given widget is a widget of the instrument.
	// * @since 3.0
	// */
	// public boolean isWidget(final Object ab) {
	// return ab!=null && (button2EditingChoiceMap.get(ab)!=null || ab==handB ||
	// ab==codeB);
	// }

	@Override
	public void onActionDone(final Action action) {
		super.onActionDone(action);
		hand.getCanvas().requestFocus();
	}

	private static class ButtonPressed2DefineStylePencil extends ToggleButtonInteractor<ModifyPencilStyle, EditingSelector> {
		ButtonPressed2DefineStylePencil(final EditingSelector ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilStyle.class, new ArrayList<>(ins.button2EditingChoiceMap.keySet()));
		}

		@Override
		public void initAction() {
			action.setEditingChoice(instrument.button2EditingChoiceMap.get(interaction.getWidget()));
			action.setPencil(instrument.pencil);
		}
	}

	private static class ButtonPressed2ActivateIns extends ToggleButtonInteractor<ActivateInactivateInstruments, EditingSelector> {
		ButtonPressed2ActivateIns(final EditingSelector ins, final List<Node> nodes) throws InstantiationException, IllegalAccessException {
			super(ins, ActivateInactivateInstruments.class, nodes);
		}

		@Override
		public void initAction() {
			final ToggleButton button = interaction.getWidget();

			action.setActivateFirst(false);

			if(button!=instrument.textB)
				action.addInstrumentToInactivate(instrument.pencil.textSetter);

			/* Selection of the instruments to activate/desactivate. */
			if(button==instrument.handB) {
				final boolean noSelection = instrument.hand.canvas.getDrawing().getSelection().isEmpty();
				action.addInstrumentToActivate(instrument.hand);

				if(!noSelection)
					action.addInstrumentToActivate(instrument.deleter);

				action.addInstrumentToInactivate(instrument.pencil);

				if(noSelection) {
					action.addInstrumentToInactivate(instrument.metaShapeCustomiser);
					action.addInstrumentToInactivate(instrument.border);
				}else {
					action.addInstrumentToActivate(instrument.metaShapeCustomiser);
					action.addInstrumentToActivate(instrument.border);
				}
			}else {
				action.addInstrumentToInactivate(instrument.hand);
				action.addInstrumentToInactivate(instrument.border);
				action.addInstrumentToInactivate(instrument.deleter);
				action.addInstrumentToActivate(instrument.pencil);
				action.addInstrumentToActivate(instrument.metaShapeCustomiser);
			}
		}
	}
}

// class ButtonPressed2LaunchCodeInserter extends
// InteractorImpl<ActivateInactivateInstruments, ButtonPressed, EditingSelector>
// {
// ButtonPressed2LaunchCodeInserter(final EditingSelector ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, ActivateInactivateInstruments.class, ButtonPressed.class);
// }
//
// @Override
// public void initAction() {
// action.addInstrumentToActivate(instrument.codeInserter);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getButton()==instrument.codeB;
// }
// }

// class ButtonPressed2AddText extends InteractorImpl<AddShape, ButtonPressed,
// EditingSelector> {
// protected ButtonPressed2AddText(final EditingSelector ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, AddShape.class, ButtonPressed.class);
// }
//
// @Override
// public void initAction() {
// action.setDrawing(instrument.pencil.canvas().getDrawing());
// action.setShape(ShapeFactory.createText(ShapeFactory.createPoint(instrument.pencil.textSetter().relativePoint),
// instrument.pencil.textSetter().getTextField().getText()));
// }
//
// @Override
// public boolean isConditionRespected() {
// return instrument.pencil.textSetter().isActivated() &&
// !instrument.pencil.textSetter().getTextField().getText().isEmpty() &&
// interaction.getButton()==instrument.textB;
// }
// }
