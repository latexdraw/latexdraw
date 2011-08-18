package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.malai.widget.MButtonIcon;
import org.malai.widget.MCheckBox;
import org.malai.widget.MColorButton;
import org.malai.widget.MSpinner;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument modifies double border properties of shapes or the pencil.<br>
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
 * 11/02/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDoubleBorderCustomiser extends ShapePropertyCustomiser {
	/** Sets if the shape has double borders or not. */
	protected MCheckBox dbleBoundCB;

	/** Allows to change the colour of the space between the double boundaries. */
	protected MColorButton dbleBoundColB;

	/** This field modifies the double separation of the double line. */
	protected MSpinner dbleSepField;



	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeDoubleBorderCustomiser(final Hand hand, final Pencil pencil) {
		super(hand, pencil);

		initWidgets();
		initialiseLinks();
	}


	@Override
	protected void initWidgets() {
        dbleBoundCB = new MCheckBox(LangTool.LANG.getStringDialogFrame("AbstractParametersFrame.0")); //$NON-NLS-1$
        dbleBoundCB.setMargin(LResources.INSET_BUTTON);
        dbleBoundCB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.78")); //$NON-NLS-1$

        dbleBoundColB = new MColorButton("Colour", new MButtonIcon(pencil.dbleBorderable.getDbleBordCol()));
        dbleBoundColB.setMargin(LResources.INSET_BUTTON);
        dbleBoundColB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.79")); //$NON-NLS-1$

     	dbleSepField = new MSpinner(new SpinnerNumberModel(5, 1, 1000,1), new JLabel(LResources.GRID_GAP_ICON));
     	dbleSepField.setEditor(new JSpinner.NumberEditor(dbleSepField, "0")); //$NON-NLS-1$
	}


	/**
	 * @return The field that modifies the separation between the double line.
	 * @since 3.0
	 */
	public MSpinner getDbleSepField() {
		return dbleSepField;
	}


	/**
	 * @return The double border check box.
	 * @since 3.0
	 */
	public MColorButton getDbleBoundColB() {
		return dbleBoundColB;
	}

	/**
	 * @return The double border check box.
	 * @since 3.0
	 */
	public MCheckBox getDbleBoundCB() {
		return dbleBoundCB;
	}


	@Override
	protected void update(final IShape shape) {
		if(shape!=null) {
			boolean dblable = shape.isDbleBorderable();

			if(widgetContainer!=null)
				widgetContainer.setVisible(dblable);

			if(dblable) {
				boolean dble = shape.hasDbleBord();
				dbleBoundCB.setSelected(dble);
				dbleBoundColB.setVisible(dble);
				dbleSepField.setVisible(dble);

				if(dble) {
					dbleBoundColB.setColor(shape.getDbleBordCol());
					dbleSepField.setValueSafely((int)shape.getDbleBordSep());
				}
			}
		}
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new CheckBox2PencilDoubleBorder(this));
			links.add(new CheckBox2SelectionDoubleBorder(this));
			links.add(new ColourButton2PencilDoubleBorder(this));
			links.add(new ColourButton2SelectionDoubleBorder(this));
			links.add(new Spinner2PencilDoubleBorder(this));
			links.add(new Spinner2SelectionDoubleBorder(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}
}



/**
 * This link uses a checkbox to modify the pencil.
 */
class CheckBox2PencilDoubleBorder extends CheckBoxForCustomiser<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public CheckBox2PencilDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.DBLE_BORDERS);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.dbleBoundCB && instrument.pencil.isActivated();
	}
}



/**
 * This link uses a checkbox to modify shapes.
 */
class CheckBox2SelectionDoubleBorder extends CheckBoxForCustomiser<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public CheckBox2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setShape(instrument.drawing.getSelection().duplicate());
		action.setProperty(ShapeProperties.DBLE_BORDERS);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.dbleBoundCB && instrument.hand.isActivated();
	}
}


/**
 * This link maps a colour button to the pencil.
 */
class ColourButton2PencilDoubleBorder extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public ColourButton2PencilDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.dbleBoundColB && instrument.pencil.isActivated();
	}
}



/**
 * This link maps a colour button to the selected shapes.
 */
class ColourButton2SelectionDoubleBorder extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public ColourButton2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
		action.setShape(instrument.drawing.getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.dbleBoundColB && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2SelectionDoubleBorder extends SpinnerForCustomiser<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public Spinner2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
		action.setShape(instrument.drawing.getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.dbleSepField && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2PencilDoubleBorder extends SpinnerForCustomiser<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
	/**
	 * Creates the link.
	 */
	public Spinner2PencilDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}


	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.dbleSepField && instrument.pencil.isActivated();
	}
}
