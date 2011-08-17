package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
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
 * This instrument modifies shadow properties of shapes or the pencil.<br>
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
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeShadowCustomiser(final Hand hand, final Pencil pencil) {
		super(hand, pencil);

		initWidgets();
		initialiseLinks();
	}


	@Override
	protected void initWidgets() {
		shadowCB = new MCheckBox(LangTool.LANG.getString17("LaTeXDrawFrame.0")); //$NON-NLS-1$
		shadowCB.setMargin(LResources.INSET_BUTTON);
		shadowCB.setToolTipText(LangTool.LANG.getString17("LaTeXDrawFrame.4")); //$NON-NLS-1$

		shadowColB = new MColorButton("Colour", new MButtonIcon(pencil.shadowable.getShadowCol()));
		shadowColB.setMargin(LResources.INSET_BUTTON);
		shadowColB.setToolTipText(LangTool.LANG.getString17("LaTeXDrawFrame.5")); //$NON-NLS-1$

		SpinnerModel model = new SpinnerNumberModel(1,0.01,1000,1);
		shadowSizeField = new MSpinner(model, new JLabel("Size:"));
		shadowSizeField.setEditor(new JSpinner.NumberEditor(shadowSizeField, "0.00"));//$NON-NLS-1$

     	model = new SpinnerNumberModel(45, -360, 360,0.5);
     	shadowAngleField = new MSpinner(model, new JLabel("Angle:"));
     	shadowAngleField.setEditor(new JSpinner.NumberEditor(shadowAngleField, "0.0"));//$NON-NLS-1$
	}


	@Override
	protected void update(final IShape shape) {
		if(shape!=null) {
			boolean shadowable = shape.isShadowable();

			if(widgetContainer!=null)
				widgetContainer.setVisible(shadowable);

			if(shadowable) {
				boolean hasShadow = shape.hasShadow();
				shadowCB.setSelected(hasShadow);
				shadowColB.setVisible(hasShadow);
				shadowSizeField.setVisible(hasShadow);
				shadowAngleField.setVisible(hasShadow);

				if(hasShadow) {
					shadowColB.setColor(shape.getShadowCol());
					shadowAngleField.setValueSafely(Math.toDegrees(shape.getShadowAngle()));
					shadowSizeField.setValueSafely(shape.getShadowSize());
				}
			}
		}
	}

	@Override
	protected void initialiseLinks() {
		try{
			links.add(new CheckBox2PencilShadow(this));
			links.add(new CheckBox2SelectionShadow(this));
			links.add(new Spinner2SelectionShadow(this));
			links.add(new Spinner2PencilShadow(this));
			links.add(new ColourButton2SelectionShadow(this));
			links.add(new ColourButton2PencilShadow(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
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
	 */
	public CheckBox2SelectionShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setShape(instrument.drawing.getSelection().duplicate());
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
	 */
	public Spinner2SelectionShadow(final ShapeShadowCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		final JSpinner spinner = interaction.getSpinner();
		action.setShape(instrument.drawing.getSelection().duplicate());

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
	 */
	public Spinner2PencilShadow(final ShapeShadowCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}


	@Override
	public void initAction() {
		super.initAction();

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
	 */
	public ColourButton2SelectionShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setProperty(ShapeProperties.COLOUR_SHADOW);
		action.setShape(instrument.drawing.getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.shadowColB && instrument.hand.isActivated();
	}
}
