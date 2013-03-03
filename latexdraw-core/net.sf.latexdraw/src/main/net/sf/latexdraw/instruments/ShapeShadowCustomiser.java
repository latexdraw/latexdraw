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
 * This instrument modifies shadow properties of shapes or the pencil.<br>
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
 * 11/07/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeShadowCustomiser extends ShapePropertyCustomiser {
	/** Sets if the a shape has a shadow or not. */
	protected MCheckBox shadowCB;

	/** Sets the colour of the shadow of a figure. */
	protected MColorButton shadowColB;

	/** Changes the size of the shadow. */
	protected MSpinner shadowSizeField;

	/** Changes the angle of the shadow. */
	protected MSpinner shadowAngleField;



	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeShadowCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
		shadowCB = new MCheckBox(LangTool.INSTANCE.getString17("LaTeXDrawFrame.0")); //$NON-NLS-1$
		shadowCB.setMargin(LResources.INSET_BUTTON);
		shadowCB.setToolTipText(LangTool.INSTANCE.getString17("LaTeXDrawFrame.4")); //$NON-NLS-1$

		shadowColB = new MColorButton("Colour", new MButtonIcon(Color.BLACK));
		shadowColB.setMargin(LResources.INSET_BUTTON);
		shadowColB.setToolTipText(LangTool.INSTANCE.getString17("LaTeXDrawFrame.5")); //$NON-NLS-1$

		shadowSizeField = new MSpinner(new MSpinner.MSpinnerNumberModel(1., 0.01, 1000. ,1.), new JLabel("Size:"));
		shadowSizeField.setEditor(new JSpinner.NumberEditor(shadowSizeField, "0.00"));//$NON-NLS-1$

     	shadowAngleField = new MSpinner(new MSpinner.MSpinnerNumberModel(45., -360., 360., 0.5), new JLabel("Angle:"));
     	shadowAngleField.setEditor(new JSpinner.NumberEditor(shadowAngleField, "0.0"));//$NON-NLS-1$
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(shadowCB, visible);
		composer.setWidgetVisible(shadowColB, visible);
		composer.setWidgetVisible(shadowSizeField, visible);
		composer.setWidgetVisible(shadowAngleField, visible);
	}


	@Override
	protected void update(final IShape shape) {
		if(shape!=null && shape.isShadowable()) {
			final boolean hasShadow = shape.hasShadow();

			shadowCB.setSelected(hasShadow);
			shadowColB.setEnabled(hasShadow);
			shadowAngleField.setEnabled(hasShadow);
			shadowSizeField.setEnabled(hasShadow);

			if(hasShadow) {
				shadowColB.setColor(shape.getShadowCol());
				shadowAngleField.setValueSafely(Math.toDegrees(shape.getShadowAngle()));
				shadowSizeField.setValueSafely(shape.getShadowSize());
			}
		}
		else setActivated(false);
	}

	@Override
	protected void initialiseLinks() {
		try{
			addLink(new CheckBox2PencilShadow(this));
			addLink(new CheckBox2SelectionShadow(this));
			addLink(new Spinner2SelectionShadow(this));
			addLink(new Spinner2PencilShadow(this));
			addLink(new ColourButton2SelectionShadow(this));
			addLink(new ColourButton2PencilShadow(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	/**
	 * @return The widget used to define if the shape has a shadow.
	 * @since 3.0
	 */
	public MCheckBox getShadowCB() {
		return shadowCB;
	}

	/**
	 * @return The widget used to change the shadow colour.
	 * @since 3.0
	 */
	public MColorButton getShadowColB() {
		return shadowColB;
	}

	/**
	 * @return The widget that modifies the size of the shadow.
	 * @since 3.0
	 */
	public MSpinner getShadowSizeField() {
		return shadowSizeField;
	}

	/**
	 * @return The widget that modifies the angle of the shadow.
	 * @since 3.0
	 */
	public MSpinner getShadowAngleField() {
		return shadowAngleField;
	}
}


/**
 * This link uses a checkbox to modify the pencil.
 */
class CheckBox2PencilShadow extends CheckBoxForCustomiser<ModifyPencilParameter, ShapeShadowCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public CheckBox2PencilShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.SHADOW);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.shadowCB && instrument.pencil.isActivated();
	}
}


/**
 * This link uses a checkbox to modify shapes.
 */
class CheckBox2SelectionShadow extends CheckBoxForCustomiser<ModifyShapeProperty, ShapeShadowCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public CheckBox2SelectionShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		action.setProperty(ShapeProperties.SHADOW);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.shadowCB && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2SelectionShadow extends SpinnerForCustomiser<ModifyShapeProperty, ShapeShadowCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public Spinner2SelectionShadow(final ShapeShadowCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		final JSpinner spinner = interaction.getSpinner();
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());

		if(spinner==instrument.shadowSizeField)
			action.setProperty(ShapeProperties.SHADOW_SIZE);
		else
			action.setProperty(ShapeProperties.SHADOW_ANGLE);
	}

	@Override
	public void updateAction() {
		if(interaction.getSpinner()==instrument.shadowAngleField)
			action.setValue(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString())));
		else
			super.updateAction();
	}

	@Override
	public boolean isConditionRespected() {
		final JSpinner spinner = getInteraction().getSpinner();
		return (spinner==instrument.shadowAngleField || spinner==instrument.shadowSizeField) && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2PencilShadow extends SpinnerForCustomiser<ModifyPencilParameter, ShapeShadowCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public Spinner2PencilShadow(final ShapeShadowCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}


	@Override
	public void initAction() {
		final JSpinner spinner = getInteraction().getSpinner();
		action.setPencil(instrument.pencil);

		if(spinner==instrument.shadowAngleField)
			action.setProperty(ShapeProperties.SHADOW_ANGLE);
		else
			action.setProperty(ShapeProperties.SHADOW_SIZE);
	}

	@Override
	public void updateAction() {
		if(interaction.getSpinner()==instrument.shadowAngleField)
			action.setValue(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString())));
		else
			super.updateAction();
	}

	@Override
	public boolean isConditionRespected() {
		final JSpinner spinner = interaction.getSpinner();
		return (spinner==instrument.shadowSizeField || spinner==instrument.shadowAngleField) && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a colour button to the pencil.
 */
class ColourButton2PencilShadow extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeShadowCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public ColourButton2PencilShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_SHADOW);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.shadowColB && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a colour button to the selected shapes.
 */
class ColourButton2SelectionShadow extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeShadowCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public ColourButton2SelectionShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_SHADOW);
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.shadowColB && instrument.hand.isActivated();
	}
}
