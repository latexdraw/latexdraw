package net.sf.latexdraw.instruments;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButtonIcon;
import org.malai.swing.widget.MCheckBox;
import org.malai.swing.widget.MColorButton;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies double border properties of shapes or the pencil.<br>
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
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeDoubleBorderCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
        dbleBoundCB = new MCheckBox(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.0")); //$NON-NLS-1$
        dbleBoundCB.setMargin(LResources.INSET_BUTTON);
        dbleBoundCB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.78")); //$NON-NLS-1$

        dbleBoundColB = new MColorButton("Colour", new MButtonIcon(Color.WHITE));
        dbleBoundColB.setMargin(LResources.INSET_BUTTON);
        dbleBoundColB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.79")); //$NON-NLS-1$

     	dbleSepField = new MSpinner(new MSpinner.MSpinnerNumberModel(5., 1., 1000., 1.), new JLabel(LResources.GRID_GAP_ICON));
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
		if(shape!=null && shape.isDbleBorderable()) {
			final boolean dble = shape.hasDbleBord();

			dbleBoundCB.setSelected(dble);
			dbleBoundColB.setEnabled(dble);
			dbleSepField.setEnabled(dble);

			if(dble) {
				dbleBoundColB.setColor(shape.getDbleBordCol());
				dbleSepField.setValueSafely(shape.getDbleBordSep());
			}
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(dbleBoundCB, visible);
		composer.setWidgetVisible(dbleBoundColB, visible);
		composer.setWidgetVisible(dbleSepField, visible);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new CheckBox2PencilDoubleBorder(this));
			addLink(new CheckBox2SelectionDoubleBorder(this));
			addLink(new ColourButton2PencilDoubleBorder(this));
			addLink(new ColourButton2SelectionDoubleBorder(this));
			addLink(new Spinner2PencilDoubleBorder(this));
			addLink(new Spinner2SelectionDoubleBorder(this));
		}catch(InstantiationException | IllegalAccessException e){
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
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected CheckBox2PencilDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
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
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected CheckBox2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
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
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ColourButton2PencilDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
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
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ColourButton2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
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
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected Spinner2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
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
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected Spinner2PencilDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
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
