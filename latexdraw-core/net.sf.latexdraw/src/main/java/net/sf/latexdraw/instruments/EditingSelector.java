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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import net.sf.latexdraw.actions.ModifyPencilStyle;
import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.action.Action;
import org.malai.javafx.action.ActivateInactivateInstruments;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument selects the pencil or the hand.
 * @author Arnaud Blouin
 */
public class EditingSelector extends JfxInstrument implements Initializable {
	/** The button that allows to select the instrument Hand. */
	@FXML ToggleButton handB;

	/** The button that allows to select the instrument Pencil to draw dots. */
	@FXML ToggleButton dotB;

	/** The button that allows to select the instrument Pencil to draw free hand shapes. */
	@FXML ToggleButton freeHandB;

	/** The button that allows to select the instrument Pencil to add texts. */
	@FXML ToggleButton textB;

	/** The button that allows to select the instrument Pencil to add rectangles. */
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

	/** The button that allows to select the instrument Pencil to add bezier curves. */
	@FXML ToggleButton bezierB;

	/** The button that allows to select the instrument Pencil to add closed bezier curves. */
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

	/** The button that allows to select the instrument Pencil to add plotted curves. */
	@FXML ToggleButton plotB;

	/** The button that allows to insert some code (converted in shapes). */
	@FXML Button codeB;

	@FXML ToggleGroup groupEditing;

	/** The instrument Hand. */
	@Inject Hand hand;

	/** The instrument Pencil. */
	@Inject Pencil pencil;

	/** The instrument that manages instruments that customise shapes and the pencil. */
	@Inject MetaShapeCustomiser metaShapeCustomiser;

	/** The instrument that manages selected shapes. */
	@Inject Border border;

	/** The instrument used to delete shapes. */
	@Inject ShapeDeleter deleter;

	@Inject Canvas canvas;

	@Inject CodeInserter codeInserter;

	final Map<ToggleButton, EditionChoice> button2EditingChoiceMap;


	/**
	 * Creates the instrument.
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
		codeInserter.setActivated(false);
		hand.setActivated(true);
		pencil.setActivated(false);
		metaShapeCustomiser.update();

		// At least one button must be selected.
		groupEditing.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null && oldValue != null) {
				oldValue.setSelected(true);
			}
		});
	}


	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		final ToggleButton[] nodes = button2EditingChoiceMap.keySet().toArray(new ToggleButton[button2EditingChoiceMap.size()+1]);
		nodes[nodes.length-1] = handB;

		toggleButtonBinder(AddShape.class).on(textB).init(action -> {
			action.setDrawing(canvas.getDrawing());
			action.setShape(ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(pencil.textSetter.getPosition()),
				pencil.textSetter.getTextField().getText()));
		}).when(i -> pencil.textSetter.isActivated() && !pencil.textSetter.getTextField().getText().isEmpty()).bind();

		toggleButtonBinder(ModifyPencilStyle.class).on(nodes).init((action, interaction) -> {
			action.setEditingChoice(button2EditingChoiceMap.get(interaction.getWidget()));
			action.setPencil(pencil);
		}).bind();

		toggleButtonBinder(ActivateInactivateInstruments.class).on(nodes).init((action, interaction) -> {
			final ToggleButton button = interaction.getWidget();

			action.setActivateFirst(false);

			if(button != textB) {
				action.addInstrumentToInactivate(pencil.textSetter);
			}

			/* Selection of the instruments to activate/desactivate. */
			if(button == handB) {
				final boolean noSelection = hand.canvas.getDrawing().getSelection().isEmpty();
				action.addInstrumentToActivate(hand);

				if(!noSelection) action.addInstrumentToActivate(deleter);

				action.addInstrumentToInactivate(pencil);

				if(noSelection) {
					action.addInstrumentToInactivate(metaShapeCustomiser);
					action.addInstrumentToInactivate(border);
				}else {
					action.addInstrumentToActivate(metaShapeCustomiser);
					action.addInstrumentToActivate(border);
				}
			}else {
				action.addInstrumentToInactivate(hand);
				action.addInstrumentToInactivate(border);
				action.addInstrumentToInactivate(deleter);
				action.addInstrumentToActivate(pencil);
				action.addInstrumentToActivate(metaShapeCustomiser);
			}
		}).bind();

		buttonBinder(ActivateInactivateInstruments.class).on(codeB).init(action -> action.addInstrumentToActivate(codeInserter)).bind();
	}

	@Override
	public void setActivated(final boolean isActivated) {
		super.setActivated(isActivated);
		button2EditingChoiceMap.keySet().forEach(but -> but.setVisible(activated));
		handB.setVisible(activated);
		codeB.setVisible(activated);
	}


	@Override
	public void onActionDone(final Action action) {
		super.onActionDone(action);
		canvas.requestFocus();
	}
}
