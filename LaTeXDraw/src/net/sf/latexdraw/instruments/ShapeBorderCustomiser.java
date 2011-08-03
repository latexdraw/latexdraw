package net.sf.latexdraw.instruments;

import java.awt.Dimension;
import java.awt.ItemSelectable;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.widget.MButtonIcon;
import fr.eseo.malai.widget.MCheckBox;
import fr.eseo.malai.widget.MColorButton;
import fr.eseo.malai.widget.MComboBox;
import fr.eseo.malai.widget.MSpinner;

/**
 * This instrument modifies border properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 10/31/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeBorderCustomiser extends ShapePropertyCustomiser {

	/** The field which allows to change shapes thickness. */
	protected MSpinner thicknessField;

	/** Allows to set the colour of the borders of shapes. */
	protected MColorButton lineColButton;

	/** Allows to change the style of the borders */
	protected MComboBox lineCB;

	/** Allows to select the position of the borders of the shape. */
	protected MComboBox bordersPosCB;

	/** Defines if the border corners are round. */
	protected MCheckBox isRound;

	/** Allows to change the angle of the round corner. */
	protected MSpinner frameArcField;



	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeBorderCustomiser(final Hand hand, final Pencil pencil) {
		super(hand, pencil);

		initWidgets();
		initialiseLinks();
	}



	/**
	 * Creates a list of the different positions of the borders.
	 * @return The created list.
	 */
	public static MComboBox createBordersPositionChoice() {
		MComboBox dbPositionChoice = new MComboBox();
		dbPositionChoice.setRenderer(new LabelListCellRenderer());

		JLabel label = new JLabel(BorderPos.INTO.toString());
		label.setIcon(LResources.INNER_ICON);
		dbPositionChoice.addItem(label);

		label = new JLabel(BorderPos.OUT.toString());
		label.setIcon(LResources.OUTER_ICON);
		dbPositionChoice.addItem(label);

		label = new JLabel(BorderPos.MID.toString());
		label.setIcon(LResources.MIDDLE_ICON);
		dbPositionChoice.addItem(label);

		return dbPositionChoice;
	}



	/**
	 * Creates a list of the different styles of line.
	 * @return The created list.
	 */
	public static MComboBox createStyleLineChoice() {
		MComboBox lineChoice = new MComboBox();

		lineChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(LineStyle.SOLID.toString());
		label.setIcon(LResources.LINE_STYLE_NONE_ICON);
     	lineChoice.addItem(label);
		label = new JLabel(LineStyle.DASHED.toString());
		label.setIcon(LResources.LINE_STYLE_DASHED_ICON);
     	lineChoice.addItem(label);
     	label = new JLabel(LineStyle.DOTTED.toString());
		label.setIcon(LResources.LINE_STYLE_DOTTED_ICON);
     	lineChoice.addItem(label);

     	return lineChoice;
	}



	@Override
	protected void initWidgets() {
     	thicknessField 		= new MSpinner(new SpinnerNumberModel(2, 0.1, 1000, 0.1), new JLabel(LResources.THICKNESS_ICON));
     	thicknessField.setEditor(new JSpinner.NumberEditor(thicknessField, "0.0"));//$NON-NLS-1$
     	thicknessField.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.65")); //$NON-NLS-1$

     	lineColButton = new MColorButton("Colour", new MButtonIcon(pencil.lineStylable.getLineColour()));
     	lineColButton.setMargin(LResources.INSET_BUTTON);
     	lineColButton.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.66")); //$NON-NLS-1$

     	lineCB = createStyleLineChoice();
     	lineCB.setPreferredSize(new Dimension(70,30));
     	lineCB.setMaximumSize(new Dimension(70,30));

        bordersPosCB = createBordersPositionChoice();
        bordersPosCB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.77")); //$NON-NLS-1$
        bordersPosCB.setPreferredSize(new Dimension(45, 30));
        bordersPosCB.setMaximumSize(new Dimension(45, 30));

     	isRound = new MCheckBox(LangTool.LANG.getStringDialogFrame("AbstractParametersFrame.16"));//$NON-NLS-1$

		frameArcField = new MSpinner(new SpinnerNumberModel(0.5, 0.01, 1,0.05), new JLabel(LResources.ROUNDNESS_ICON));
		frameArcField.setEditor(new JSpinner.NumberEditor(frameArcField, "0.00"));//$NON-NLS-1$
	}


	@Override
	protected void update(final IShape shape) {
		if(shape!=null) {
			boolean isTh	 	 = shape.isThicknessable();
			boolean isStylable 	 = shape.isLineStylable();
			boolean isMvble		 = shape.isBordersMovable();
			boolean supportRound = shape instanceof IGroup ? ((IGroup)shape).containsRoundables() : shape instanceof ILineArcShape;
			boolean rounded		 = supportRound && ((ILineArcShape)shape).isRoundCorner();

			thicknessField.setVisible(isTh);
			lineCB.setVisible(isStylable);
			bordersPosCB.setVisible(isMvble);
			isRound.setVisible(supportRound);
			frameArcField.setVisible(rounded);
			lineColButton.setColor(shape.getLineColour());

			if(isTh)
				thicknessField.setValueSafely(shape.getThickness());
			if(isStylable)
				lineCB.setSelectedItemSafely(shape.getLineStyle().toString());
			if(isMvble)
				bordersPosCB.setSelectedItemSafely(shape.getBordersPosition().toString());
			if(supportRound) {
				isRound.setSelected(rounded);

				if(rounded)
					frameArcField.setValueSafely(((ILineArcShape)shape).getLineArc());
			}
		}
	}


	/**
	 * @return The line style combo box.
	 * @since 3.0
	 */
	public MComboBox getLineCB() {
		return lineCB;
	}

	/**
	 * @return The border position combo box.
	 * @since 3.0
	 */
	public MComboBox getBordersPosCB() {
		return bordersPosCB;
	}

	/**
	 * @return The line colour button.
	 * @since 3.0
	 */
	public MColorButton getLineColButton() {
		return lineColButton;
	}

	/**
	 * @return The field that allows to modify the thickness of the pencil.
	 * @since 3.0
	 */
	public MSpinner getThicknessField() {
		return thicknessField;
	}

	/**
	 * @return The checkbox that defines if the corner of the border are round.
	 * @since 3.0
	 */
	public MCheckBox getIsRound() {
		return isRound;
	}

	/**
	 * @return The field that defines the roundness of the corners.
	 * @since 3.0
	 */
	public MSpinner getFrameArcField() {
		return frameArcField;
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new Spinner2PencilBorder(this));
			links.add(new CheckBox2PencilBorder(this));
			links.add(new List2PencilBorder(this));
			links.add(new List2SelectionBorder(this));
			links.add(new CheckBox2SelectionBorder(this));
			links.add(new Spinner2SelectionBorder(this));
			links.add(new ColourButton2PencilBorder(this));
			links.add(new ColourButton2SelectionBorder(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}
}



/**
 * This link maps a list to an action that modifies shapes properties.
 */
class List2SelectionBorder extends ListForCustomiser<ModifyShapeProperty, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public List2SelectionBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is = interaction.getList();
		return (is==instrument.bordersPosCB || is==instrument.lineCB) && instrument.hand.isActivated();
	}

	@Override
	public void initAction() {
		super.initAction();

		final ItemSelectable is	= interaction.getList();
		action.setShape(instrument.drawing.getSelection().duplicate());

		if(is==instrument.bordersPosCB) {
			action.setProperty(ShapeProperties.BORDER_POS);
			action.setValue(BorderPos.getStyle(getLabelText()));
		}
		else if(is==instrument.lineCB) {
			action.setProperty(ShapeProperties.LINE_STYLE);
			action.setValue(LineStyle.getStyle(getLabelText()));
		}
		else action = null;
	}
}



/**
 * This link maps a list to a ModifyPencil action.
 */
class List2PencilBorder extends ListForCustomiser<ModifyPencilParameter, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public List2PencilBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		final ItemSelectable is	= interaction.getList();
		action.setPencil(instrument.pencil);

		if(is==instrument.bordersPosCB) {
			action.setProperty(ShapeProperties.BORDER_POS);
			action.setValue(BorderPos.getStyle(getLabelText()));
		}
		else if(is==instrument.lineCB) {
			action.setProperty(ShapeProperties.LINE_STYLE);
			action.setValue(LineStyle.getStyle(getLabelText()));
		}
		else action = null;
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();
		return (is==instrument.bordersPosCB || is==instrument.lineCB) && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2SelectionBorder extends SpinnerForCustomiser<ModifyShapeProperty, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public Spinner2SelectionBorder(final ShapeBorderCustomiser borderCustom) throws InstantiationException, IllegalAccessException {
		super(borderCustom, ModifyShapeProperty.class);
	}


	@Override
	public void initAction() {
		super.initAction();

		final JSpinner spinner = interaction.getSpinner();

		if(spinner==instrument.thicknessField)
			action.setProperty(ShapeProperties.LINE_THICKNESS);
		else
			if(spinner==instrument.frameArcField)
				action.setProperty(ShapeProperties.ROUND_CORNER_VALUE);
		else
			action = null;

		if(action!=null)
			action.setShape(instrument.drawing.getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		final JSpinner spinner = interaction.getSpinner();
		return (spinner==instrument.thicknessField || spinner==instrument.frameArcField) && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2PencilBorder extends SpinnerForCustomiser<ModifyPencilParameter, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public Spinner2PencilBorder(final ShapeBorderCustomiser borderCustom) throws InstantiationException, IllegalAccessException {
		super(borderCustom, ModifyPencilParameter.class);
	}


	@Override
	public void initAction() {
		super.initAction();

		final JSpinner spinner = interaction.getSpinner();

		if(spinner==instrument.thicknessField)
			action.setProperty(ShapeProperties.LINE_THICKNESS);
		else
			if(spinner==instrument.frameArcField)
				action.setProperty(ShapeProperties.ROUND_CORNER_VALUE);
		else
			action = null;

		if(action!=null)
			action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		final JSpinner spinner = interaction.getSpinner();
		return (spinner==instrument.thicknessField || spinner==instrument.frameArcField) && instrument.pencil.isActivated();
	}
}


/**
 * This link uses a checkbox to modify the pencil.
 */
class CheckBox2PencilBorder extends CheckBoxForCustomiser<ModifyPencilParameter, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public CheckBox2PencilBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		if(interaction.getCheckBox()==instrument.isRound) {
			action.setProperty(ShapeProperties.ROUND_CORNER);
			action.setPencil(instrument.pencil);
		}
		else
			action = null;
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.isRound && instrument.pencil.isActivated();
	}
}



/**
 * This link uses a checkbox to modify shapes.
 */
class CheckBox2SelectionBorder extends CheckBoxForCustomiser<ModifyShapeProperty, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public CheckBox2SelectionBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		if(interaction.getCheckBox()==instrument.isRound) {
			action.setProperty(ShapeProperties.ROUND_CORNER);
			action.setShape(instrument.drawing.getSelection().duplicate());
		}
		else
			action = null;
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.isRound && instrument.hand.isActivated();
	}
}


/**
 * This link maps a colour button to the pencil.
 */
class ColourButton2PencilBorder extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public ColourButton2PencilBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_LINE);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.lineColButton && instrument.pencil.isActivated();
	}
}



/**
 * This link maps a colour button to the selected shapes.
 */
class ColourButton2SelectionBorder extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public ColourButton2SelectionBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_LINE);
		action.setShape(instrument.drawing.getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.lineColButton && instrument.hand.isActivated();
	}
}
