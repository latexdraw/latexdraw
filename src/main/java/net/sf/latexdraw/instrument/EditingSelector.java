/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
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
import java.util.Arrays;
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
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		dotB.setUserData(EditionChoice.DOT);
		textB.setUserData(EditionChoice.TEXT);
		freeHandB.setUserData(EditionChoice.FREE_HAND);
		arcB.setUserData(EditionChoice.CIRCLE_ARC);
		axesB.setUserData(EditionChoice.AXES);
		bezierB.setUserData(EditionChoice.BEZIER_CURVE);
		circleB.setUserData(EditionChoice.CIRCLE);
		ellipseB.setUserData(EditionChoice.ELLIPSE);
		gridB.setUserData(EditionChoice.GRID);
		linesB.setUserData(EditionChoice.LINES);
		polygonB.setUserData(EditionChoice.POLYGON);
		recB.setUserData(EditionChoice.RECT);
		rhombusB.setUserData(EditionChoice.RHOMBUS);
		squareB.setUserData(EditionChoice.SQUARE);
		triangleB.setUserData(EditionChoice.TRIANGLE);
		picB.setUserData(EditionChoice.PICTURE);
		plotB.setUserData(EditionChoice.PLOT);
		handB.setUserData(EditionChoice.HAND);
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

	private ToggleButton[] getShapeButtons() {
		return new ToggleButton[] {textB, recB, linesB, dotB, plotB, gridB, picB, axesB, bezierB, ellipseB,
			polygonB, arcB, circleB, squareB, triangleB, rhombusB, freeHandB};
	}


	@Override
	protected void configureBindings() {
		final var shapeBaseBinder = toggleButtonBinder()
			.on(getShapeButtons())
			.end(() -> canvas.requestFocus());

		// Checking that converting pictures can be done.
		toggleButtonBinder()
			.toProduce(() -> new CheckConvertExists(status.getLabel(), status.getLink()))
			.on(picB)
			.end(() -> canvas.requestFocus())
			.bind();

		toggleButtonBinder()
			.toProduce(() -> new AddShape(ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(textSetter.getPosition()),
				textSetter.getTextField().getText()), canvas.getDrawing()))
			.on(handB)
			.when(() -> textSetter.isActivated() && !textSetter.getTextField().getText().isEmpty())
			.end(() -> canvas.requestFocus())
			.bind();

		shapeBaseBinder
			.on(handB)
			.toProduce(i -> new ModifyEditingMode(editing, (EditionChoice) i.getWidget().getUserData()))
			.bind();

		toggleButtonBinder()
			.toProduce(ActivateInactivateInstruments::new)
			.on(handB)
			.first(c -> {
				final boolean noSelection = canvas.getDrawing().getSelection().isEmpty();
				c.setActivateFirst(false);
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
			})
			.end(i -> canvas.requestFocus())
			.bind();

		shapeBaseBinder
			.toProduce(ActivateInactivateInstruments::new)
			.first((i, c) -> {
				c.setActivateFirst(false);

				if(i.getWidget() != textB) {
					c.addInstrumentToInactivate(textSetter);
				}

				c.addInstrumentToInactivate(hand);
				c.addInstrumentToInactivate(border);
				c.addInstrumentToInactivate(deleter);
				c.addInstrumentToActivate(pencil);
				c.addInstrumentToActivate(metaShapeCustomiser);
			})
			.bind();

		buttonBinder()
			.toProduce(ActivateInactivateInstruments::new)
			.on(codeB)
			.first(c -> c.addInstrumentToActivate(codeInserter))
			.end(() -> canvas.requestFocus())
			.bind();
	}

	@Override
	public void setActivated(final boolean isActivated) {
		super.setActivated(isActivated);
		Arrays.asList(getShapeButtons()).forEach(but -> but.setVisible(activated));
		handB.setVisible(activated);
		codeB.setVisible(activated);
	}
}
