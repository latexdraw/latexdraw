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

import io.github.interacto.jfx.command.ActivateInactivateInstruments;
import io.github.interacto.jfx.instrument.JfxInstrument;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import net.sf.latexdraw.command.CheckConvertExists;
import net.sf.latexdraw.command.ModifyEditingMode;
import net.sf.latexdraw.command.shape.AddShape;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

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
	private final @NotNull Hand hand;
	/** The instrument Pencil. */
	private final @NotNull Pencil pencil;
	private final @NotNull EditingService editing;
	/** The instrument that manages instruments that customise shapes and the pencil. */
	private final @NotNull MetaShapeCustomiser metaShapeCustomiser;
	/** The instrument that manages instruments that customise shapes and the pencil. */
	private final @NotNull TextSetter textSetter;
	/** The instrument that manages selected shapes. */
	private final @NotNull Border border;
	/** The instrument used to delete shapes. */
	private final @NotNull ShapeDeleter deleter;
	private final @NotNull Canvas canvas;
	private final @NotNull CodeInserter codeInserter;
	private final @NotNull StatusBarController status;
	private final @NotNull Map<ToggleButton, EditionChoice> button2EditingChoiceMap;


	@Inject
	public EditingSelector(final Hand hand, final Pencil pencil, final MetaShapeCustomiser meta, final EditingService editing, final TextSetter textSetter,
						final Border border, final ShapeDeleter deleter, final Canvas canvas, final CodeInserter codeInserter, final StatusBarController status) {
		super();
		this.hand = Objects.requireNonNull(hand);
		this.pencil = Objects.requireNonNull(pencil);
		this.editing = Objects.requireNonNull(editing);
		this.textSetter = Objects.requireNonNull(textSetter);
		this.metaShapeCustomiser = Objects.requireNonNull(meta);
		this.border = Objects.requireNonNull(border);
		this.deleter = Objects.requireNonNull(deleter);
		this.canvas = Objects.requireNonNull(canvas);
		this.codeInserter = Objects.requireNonNull(codeInserter);
		this.status = Objects.requireNonNull(status);
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
		button2EditingChoiceMap.put(handB, EditionChoice.HAND);
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
	protected void configureBindings() {
		final ToggleButton[] nodes = button2EditingChoiceMap.keySet().toArray(new ToggleButton[0]);

		// Checking that converting pictures can be done.
		toggleButtonBinder()
			.toProduce(i -> new CheckConvertExists(status.getLabel(), status.getLink()))
			.on(picB)
			.end(i -> canvas.requestFocus())
			.bind();

		toggleButtonBinder()
			.toProduce(i -> new AddShape(ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(textSetter.getPosition()),
				textSetter.getTextField().getText()), canvas.getDrawing()))
			.on(handB)
			.when(i -> textSetter.isActivated() && !textSetter.getTextField().getText().isEmpty())
			.end(i -> canvas.requestFocus())
			.bind();

		toggleButtonBinder()
			.toProduce(i -> new ModifyEditingMode(editing, button2EditingChoiceMap.get(i.getWidget())))
			.on(nodes)
			.end(i -> canvas.requestFocus())
			.bind();

		toggleButtonBinder()
			.toProduce(ActivateInactivateInstruments::new)
			.on(nodes)
			.first((i, c) -> {
				final ToggleButton button = i.getWidget();

				c.setActivateFirst(false);

				if(button != textB) {
					c.addInstrumentToInactivate(textSetter);
				}

				/* Selection of the instruments to activate/deactivate. */
				if(button == handB) {
					final boolean noSelection = canvas.getDrawing().getSelection().isEmpty();
					c.addInstrumentToActivate(hand);

					if(!noSelection) {
						c.addInstrumentToActivate(deleter);
					}

					c.addInstrumentToInactivate(pencil);

					if(noSelection) {
						c.addInstrumentToInactivate(metaShapeCustomiser);
						c.addInstrumentToInactivate(border);
					}else {
						c.addInstrumentToActivate(metaShapeCustomiser);
						c.addInstrumentToActivate(border);
					}
				}else {
					c.addInstrumentToInactivate(hand);
					c.addInstrumentToInactivate(border);
					c.addInstrumentToInactivate(deleter);
					c.addInstrumentToActivate(pencil);
					c.addInstrumentToActivate(metaShapeCustomiser);
				}
			})
			.end(i -> canvas.requestFocus())
			.bind();

		buttonBinder()
			.toProduce(ActivateInactivateInstruments::new)
			.on(codeB)
			.first(cmd -> cmd.addInstrumentToActivate(codeInserter))
			.end(i -> canvas.requestFocus())
			.bind();
	}

	@Override
	public void setActivated(final boolean isActivated) {
		super.setActivated(isActivated);
		button2EditingChoiceMap.keySet().forEach(but -> but.setVisible(activated));
		handB.setVisible(activated);
		codeB.setVisible(activated);
	}
}
