package net.sf.latexdraw.instruments;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.AbstractButton;

import net.sf.latexdraw.actions.ModifyPencilStyle;
import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.util.LResources;

import org.malai.action.Action;
import org.malai.instrument.Link;
import org.malai.swing.action.library.ActivateInactivateInstruments;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButton;
import org.malai.swing.widget.MToggleButton;

/**
 * This instrument selects the pencil or the hand.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/09/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class EditingSelector extends WidgetInstrument {
	/** The instrument that converts PST code into shapes. */
	protected CodeInserter codeInserter;

	/** The button that allows to select the instrument Hand. */
	protected MToggleButton handB;

	/** The button that allows to select the instrument Pencil to draw dots. */
	protected MToggleButton dotB;

	/** The button that allows to select the instrument Pencil to draw free hand shapes. */
	protected MToggleButton freeHandB;

	/** The button that allows to select the instrument Pencil to add texts. */
	protected MToggleButton textB;

	/** The button that allows to select the instrument Pencil to add rectangles. */
	protected MToggleButton recB;

	/** The button that allows to select the instrument Pencil to add squares. */
	protected MToggleButton squareB;

	/** The button that allows to select the instrument Pencil to add ellipses. */
	protected MToggleButton ellipseB;

	/** The button that allows to select the instrument Pencil to add circles. */
	protected MToggleButton circleB;

	/** The button that allows to select the instrument Pencil to add lines. */
	protected MToggleButton linesB;

	/** The button that allows to select the instrument Pencil to add polygons. */
	protected MToggleButton polygonB;

	/** The button that allows to select the instrument Pencil to add bezier curves. */
	protected MToggleButton bezierB;

	/** The button that allows to select the instrument Pencil to add closed bezier curves. */
	protected MToggleButton bezierClosedB;

	/** The button that allows to select the instrument Pencil to add grids. */
	protected MToggleButton gridB;

	/** The button that allows to select the instrument Pencil to add axes. */
	protected MToggleButton axesB;

	/** The button that allows to select the instrument Pencil to add rhombuses. */
	protected MToggleButton rhombusB;

	/** The button that allows to select the instrument Pencil to add triangles. */
	protected MToggleButton triangleB;

	/** The button that allows to select the instrument Pencil to add arcs. */
	protected MToggleButton arcB;

	/** The button that allows to select the instrument Pencil to add pictures. */
	protected MToggleButton picB;

	/** The button that allows to insert some code (converted in shapes). */
	protected MButton codeB;

	/** The instrument Hand. */
	protected Hand hand;

	/** The instrument Pencil. */
	protected Pencil pencil;

	/** The instrument that manages instruments that customise shapes and the pencil. */
	protected MetaShapeCustomiser metaShapeCustomiser;

	/** The instrument that manages selected shapes. */
	protected Border border;

	/** The instrument used to delete shapes. */
	protected ShapeDeleter deleter;

	protected Map<MToggleButton, EditionChoice> button2EditingChoiceMap;


	/**
	 * Creates the instruments selector.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The pencil to select.
	 * @param hand The hand to select.
	 * @param border The instrument that manages selected shapes.
	 * @param metaShapeCustomiser The instrument that manages instruments that customise shapes and the pencil.
	 * @param deleter The instrument used to delete shapes.
	 * @param inserter The instrument that converts PST code into shapes.
	 * @throws IllegalArgumentException If one of the given parameter is null.
	 * @since 3.0
	 */
	public EditingSelector(final UIComposer<?> composer, final Pencil pencil, final Hand hand, final MetaShapeCustomiser metaShapeCustomiser,
							final Border border, final ShapeDeleter deleter, final CodeInserter inserter) {
		super(composer);

		this.codeInserter		= Objects.requireNonNull(inserter);
		this.deleter			= Objects.requireNonNull(deleter);
		this.border				= Objects.requireNonNull(border);
		this.pencil 			= Objects.requireNonNull(pencil);
		this.hand				= Objects.requireNonNull(hand);
		this.metaShapeCustomiser= Objects.requireNonNull(metaShapeCustomiser);

		initialiseWidgets();
		initialiseEditingChoiceMap();
		codeInserter.setActivated(false);
		hand.setActivated(true);
		pencil.setActivated(false);
	}


	private void initialiseEditingChoiceMap() {
		button2EditingChoiceMap	= new HashMap<>();

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
	}


	@Override
	protected void initialiseWidgets() {
		/* Creation of the widgets of the instrument. */
		handB = new MToggleButton(LResources.SELECT_ICON);
		handB.setMargin(LResources.INSET_BUTTON);

		dotB = new MToggleButton(LResources.DOT_ICON);
		dotB.setMargin(LResources.INSET_BUTTON);

		freeHandB = new MToggleButton(LResources.FREE_HAND_ICON);
		freeHandB.setMargin(LResources.INSET_BUTTON);

		textB = new MToggleButton(LResources.TEXT_ICON);
		textB.setMargin(LResources.INSET_BUTTON);

		recB = new MToggleButton(LResources.RECT_ICON);
		recB.setMargin(LResources.INSET_BUTTON);

		squareB = new MToggleButton(LResources.SQUARE_ICON);
		squareB.setMargin(LResources.INSET_BUTTON);

		ellipseB = new MToggleButton(LResources.ELLIPSE_ICON);
		ellipseB.setMargin(LResources.INSET_BUTTON);

		circleB = new MToggleButton(LResources.CIRCLE_ICON);
		circleB.setMargin(LResources.INSET_BUTTON);

		axesB = new MToggleButton(LResources.AXES_ICON);
		axesB.setMargin(LResources.INSET_BUTTON);

		gridB = new MToggleButton(LResources.GRID_ICON);
		gridB.setMargin(LResources.INSET_BUTTON);

		bezierB = new MToggleButton(LResources.BEZIER_CURVE_ICON);
		bezierB.setMargin(LResources.INSET_BUTTON);

		bezierClosedB = new MToggleButton(LResources.CLOSED_BEZIER_ICON);
		bezierClosedB.setMargin(LResources.INSET_BUTTON);

		arcB = new MToggleButton(LResources.ARC_ICON);
		arcB.setMargin(LResources.INSET_BUTTON);

		triangleB = new MToggleButton(LResources.TRIANGLE_ICON);
		triangleB.setMargin(LResources.INSET_BUTTON);

		rhombusB = new MToggleButton(LResources.RHOMBUS_ICON);
		rhombusB.setMargin(LResources.INSET_BUTTON);

		polygonB = new MToggleButton(LResources.POLYGON_ICON);
		polygonB.setMargin(LResources.INSET_BUTTON);

		linesB = new MToggleButton(LResources.LINES_ICON);
		linesB.setMargin(LResources.INSET_BUTTON);

		picB = new MToggleButton(LResources.INSERT_PIC_ICON);
		picB.setMargin(LResources.INSET_BUTTON);

		codeB = new MButton(LResources.TEX_EDITOR_ICON);
		codeB.setMargin(LResources.INSET_BUTTON);
	}


	@Override
	public void interimFeedback() {
		if(hand.isActivated()) {
			handB.setSelected(true);
			textB.setSelected(false);
			freeHandB.setSelected(false);
			recB.setSelected(false);
			squareB.setSelected(false);
			ellipseB.setSelected(false);
			arcB.setSelected(false);
			circleB.setSelected(false);
			axesB.setSelected(false);
			gridB.setSelected(false);
			dotB.setSelected(false);
			polygonB.setSelected(false);
			linesB.setSelected(false);
			rhombusB.setSelected(false);
			triangleB.setSelected(false);
			bezierB.setSelected(false);
			bezierClosedB.setSelected(false);
			picB.setSelected(false);
		} else if(pencil.isActivated()){
			EditionChoice ec = pencil.getCurrentChoice();

			recB.setSelected(ec==EditionChoice.RECT);
			squareB.setSelected(ec==EditionChoice.SQUARE);
			ellipseB.setSelected(ec==EditionChoice.ELLIPSE);
			arcB.setSelected(ec==EditionChoice.CIRCLE_ARC);
			circleB.setSelected(ec==EditionChoice.CIRCLE);
			axesB.setSelected(ec==EditionChoice.AXES);
			gridB.setSelected(ec==EditionChoice.GRID);
			dotB.setSelected(ec==EditionChoice.DOT);
			polygonB.setSelected(ec==EditionChoice.POLYGON);
			linesB.setSelected(ec==EditionChoice.LINES);
			rhombusB.setSelected(ec==EditionChoice.RHOMBUS);
			triangleB.setSelected(ec==EditionChoice.TRIANGLE);
			bezierB.setSelected(ec==EditionChoice.BEZIER_CURVE);
			bezierClosedB.setSelected(ec==EditionChoice.BEZIER_CURVE_CLOSED);
			textB.setSelected(ec==EditionChoice.TEXT);
			freeHandB.setSelected(ec==EditionChoice.FREE_HAND);
			picB.setSelected(ec==EditionChoice.PICTURE);
			handB.setSelected(false);
		}
	}



	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ButtonPressed2AddText(this, false));
			addLink(new ButtonPressed2DefineStylePencil(this));
			addLink(new ButtonPressed2ActivateIns(this));
			addLink(new ButtonPressed2LaunchCodeInserter(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		composer.setWidgetVisible(arcB, activated);
		composer.setWidgetVisible(axesB, activated);
		composer.setWidgetVisible(bezierB, activated);
		composer.setWidgetVisible(bezierClosedB, activated);
		composer.setWidgetVisible(circleB, activated);
		composer.setWidgetVisible(recB, activated);
		composer.setWidgetVisible(squareB, activated);
		composer.setWidgetVisible(ellipseB, activated);
		composer.setWidgetVisible(gridB, activated);
		composer.setWidgetVisible(polygonB, activated);
		composer.setWidgetVisible(dotB, activated);
		composer.setWidgetVisible(linesB, activated);
		composer.setWidgetVisible(rhombusB, activated);
		composer.setWidgetVisible(triangleB, activated);
		composer.setWidgetVisible(textB, activated);
		composer.setWidgetVisible(freeHandB, activated);
		composer.setWidgetVisible(handB, activated);
		composer.setWidgetVisible(picB, activated);
		composer.setWidgetVisible(codeB, activated);
	}


	/**
	 * @param ab The widget to test.
	 * @return True if the given widget is a widget of the instrument.
	 * @since 3.0
	 */
	public boolean isWidget(final Object ab) {
		return ab!=null && (button2EditingChoiceMap.get(ab)!=null || ab==handB || ab==codeB);
	}


	/**
	 * @return The button that allows to insert PST code.
	 * @since 3.0
	 */
	public MButton getCodeB() {
		return codeB;
	}

	/**
	 * @return The button that allows the select instrument Hand.
	 * @since 3.0
	 */
	public MToggleButton getHandB() {
		return handB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw free hand shapes.
	 * @since 3.0
	 */
	public MToggleButton getFreeHandB() {
		return freeHandB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to add texts.
	 * @since 3.0
	 */
	public MToggleButton getTextB() {
		return textB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw dots.
	 * @since 3.0
	 */
	public MToggleButton getDotB() {
		return dotB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw rectangles.
	 * @since 3.0
	 */
	public MToggleButton getRecB() {
		return recB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw squares.
	 * @since 3.0
	 */
	public MToggleButton getSquareB() {
		return squareB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw ellipses.
	 * @since 3.0
	 */
	public MToggleButton getEllipseB() {
		return ellipseB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw circles.
	 * @since 3.0
	 */
	public MToggleButton getCircleB() {
		return circleB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw lines.
	 * @since 3.0
	 */
	public MToggleButton getLinesB() {
		return linesB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw polygons.
	 * @since 3.0
	 */
	public MToggleButton getPolygonB() {
		return polygonB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw bezier curves.
	 * @since 3.0
	 */
	public MToggleButton getBezierB() {
		return bezierB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw closed bezier curves.
	 * @since 3.0
	 */
	public MToggleButton getBezierClosedB() {
		return bezierClosedB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw grids.
	 * @since 3.0
	 */
	public MToggleButton getGridB() {
		return gridB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw axes.
	 * @since 3.0
	 */
	public MToggleButton getAxesB() {
		return axesB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw rhombuses.
	 * @since 3.0
	 */
	public MToggleButton getRhombusB() {
		return rhombusB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw triangles.
	 * @since 3.0
	 */
	public MToggleButton getTriangleB() {
		return triangleB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to draw arcs.
	 * @since 3.0
	 */
	public MToggleButton getArcB() {
		return arcB;
	}

	/**
	 * @return The button that allows the select instrument Pencil to add pictures.
	 * @since 3.0
	 */
	public MToggleButton getPicB() {
		return picB;
	}

	/**
	 * @return The hand.
	 * @since 3.0
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * @return The pencil.
	 * @since 3.0
	 */
	public Pencil getPencil() {
		return pencil;
	}


	@Override
	public void onActionDone(final Action action) {
		super.onActionDone(action);
		hand.canvas().requestFocus();
	}
}



/**
 * This link allows to activate the code inserter instrument.
 */
class ButtonPressed2LaunchCodeInserter extends Link<ActivateInactivateInstruments, ButtonPressed, EditingSelector> {
	public ButtonPressed2LaunchCodeInserter(EditingSelector ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ActivateInactivateInstruments.class, ButtonPressed.class);
	}

	@Override
	public void initAction() {
		action.addInstrumentToActivate(instrument.codeInserter);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.codeB;
	}
}



/**
 * This link allows the modify the link of shape the pencil will create using a ButtonPressed interaction.
 */
class ButtonPressed2DefineStylePencil extends Link<ModifyPencilStyle, ButtonPressed, EditingSelector> {
	protected ButtonPressed2DefineStylePencil(final EditingSelector ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ModifyPencilStyle.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		action.setEditingChoice(instrument.button2EditingChoiceMap.get(interaction.getButton()));
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.button2EditingChoiceMap.get(interaction.getButton())!=null;
	}
}


/**
 * When the user types a text using the text field (instrument text setter) and then he
 * selects another kind of editing, the typed text must be added to the canvas.
 */
class ButtonPressed2AddText extends Link<AddShape, ButtonPressed, EditingSelector> {
	protected ButtonPressed2AddText(final EditingSelector ins, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(ins, exec, AddShape.class, ButtonPressed.class);
	}

	@Override
	public void initAction() {
		action.setDrawing(instrument.pencil.canvas.getDrawing());
		action.setShape(DrawingTK.getFactory().createText(true, DrawingTK.getFactory().createPoint(instrument.pencil.textSetter.relativePoint),
				instrument.pencil.textSetter.getTextField().getText()));
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.pencil.textSetter.isActivated() && instrument.pencil.textSetter.getTextField().getText().length()>0 &&
			interaction.getButton()==instrument.textB;
	}
}



/**
 * This link maps a ButtonPressed interaction to an action that activates/desactivates instruments.
 */
class ButtonPressed2ActivateIns extends Link<ActivateInactivateInstruments, ButtonPressed, EditingSelector> {
	protected ButtonPressed2ActivateIns(final EditingSelector ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ActivateInactivateInstruments.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		final AbstractButton ab = interaction.getButton();

		action.setActivateFirst(false);

		if(ab!=instrument.textB)
			action.addInstrumentToInactivate(instrument.pencil.textSetter);

		/* Selection of the instruments to activate/desactivate. */
		if(ab==instrument.handB) {
			final boolean noSelection = instrument.hand.canvas().getDrawing().getSelection().isEmpty();
			action.addInstrumentToActivate(instrument.hand);

			if(!noSelection)
				action.addInstrumentToActivate(instrument.deleter);

			action.addInstrumentToInactivate(instrument.pencil);

			if(noSelection) {
				action.addInstrumentToInactivate(instrument.metaShapeCustomiser);
				action.addInstrumentToInactivate(instrument.border);
			}
			else {
				action.addInstrumentToActivate(instrument.metaShapeCustomiser);
				action.addInstrumentToActivate(instrument.border);
			}
		} else {
			action.addInstrumentToInactivate(instrument.hand);
			action.addInstrumentToInactivate(instrument.border);
			action.addInstrumentToInactivate(instrument.deleter);
			action.addInstrumentToActivate(instrument.pencil);
			action.addInstrumentToActivate(instrument.metaShapeCustomiser);
		}
	}


	@Override
	public boolean isConditionRespected() {
		/* The pressed button must be a button of the instrument. */
		return instrument.codeB!=interaction.getButton() && instrument.isWidget(interaction.getButton());
	}
}
