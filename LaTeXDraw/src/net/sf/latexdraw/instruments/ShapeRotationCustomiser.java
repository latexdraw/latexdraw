package net.sf.latexdraw.instruments;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.malai.instrument.Link;
import org.malai.interaction.library.ButtonPressed;
import org.malai.ui.UIComposer;
import org.malai.widget.MButton;
import org.malai.widget.MSpinner;

import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.RotateShapes;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument modifies the rotation angle of selected shapes.<br>
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
 * 12/31/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeRotationCustomiser extends ShapePropertyCustomiser {
	/** The rotation button to perform 90 degree rotation. */
	protected MButton rotate90Button;

	/** The rotation button to perform 180 degree rotation. */
	protected MButton rotate180Button;

	/** The rotation button to perform 270 degree rotation. */
	protected MButton rotate270Button;

	/** The field that modifies the rotation angle. */
	protected MSpinner rotationField;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeRotationCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);

		initialiseWidgets();
		initialiseLinks();
	}


	@Override
	protected void initialiseWidgets() {
     	rotate90Button = new MButton(LResources.ROTATE_90_ICON);
     	rotate90Button.setMargin(LResources.INSET_BUTTON);
     	rotate90Button.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.3")); //$NON-NLS-1$

     	rotate180Button = new MButton(LResources.ROTATE_180_ICON);
     	rotate180Button.setMargin(LResources.INSET_BUTTON);
     	rotate180Button.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.4")); //$NON-NLS-1$

     	rotate270Button = new MButton(LResources.ROTATE_270_ICON);
     	rotate270Button.setMargin(LResources.INSET_BUTTON);
     	rotate270Button.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.5")); //$NON-NLS-1$

     	rotationField = new MSpinner(new SpinnerNumberModel(0, -360, 360,0.1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.7"))); //$NON-NLS-1$
     	rotationField.setEditor(new JSpinner.NumberEditor(rotationField, "0.0"));	//$NON-NLS-1$
	}


	@Override
	public void setActivated(final boolean activated) {
		if(activated && hand.isActivated())
			super.setActivated(true);
		else
			super.setActivated(false);

		composer.setWidgetVisible(rotate180Button, this.activated);
		composer.setWidgetVisible(rotate270Button, this.activated);
		composer.setWidgetVisible(rotate90Button, this.activated);
		composer.setWidgetVisible(rotationField, this.activated);
	}


	@Override
	protected void update(final IShape shape) {
		if(shape!=null)
			rotationField.setValueSafely(Math.toDegrees(shape.getRotationAngle()));
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new ButtonPress2RotateShape(this));
			links.add(new Spinner2RotateShape(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The rotation button to perform 90 degree rotation.
	 * @since 3.0
	 */
	public MButton getRotate90Button() {
		return rotate90Button;
	}


	/**
	 * @return The rotation button to perform 180 degree rotation.
	 * @since 3.0
	 */
	public MButton getRotate180Button() {
		return rotate180Button;
	}


	/**
	 * @return The rotation button to perform 270 degree rotation.
	 * @since 3.0
	 */
	public MButton getRotate270Button() {
		return rotate270Button;
	}


	/**
	 * @return The field that modifies the rotation angle.
	 * @since 3.0
	 */
	public MSpinner getRotationField() {
		return rotationField;
	}
}



/**
 * This link maps a spinner to an action that rotates the selected shapes.
 */
class Spinner2RotateShape extends SpinnerForCustomiser<ModifyShapeProperty, ShapeRotationCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public Spinner2RotateShape(final ShapeRotationCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.getRotationField()==interaction.getSpinner();
	}

	@Override
	public void initAction() {
		action.setValue(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString())));
		action.setShape(instrument.hand.canvas.getDrawing().getSelection().duplicate());
		action.setProperty(ShapeProperties.ROTATION_ANGLE);
	}
}



/**
 * This link maps a button to an action that rotates the selected shapes.
 */
class ButtonPress2RotateShape extends Link<RotateShapes, ButtonPressed, ShapeRotationCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public ButtonPress2RotateShape(final ShapeRotationCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, RotateShapes.class, ButtonPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		final AbstractButton button = interaction.getButton();
		return button==instrument.rotate90Button || button==instrument.rotate180Button || button==instrument.rotate270Button;
	}

	@Override
	public void initAction() {
		final double angle;
		final AbstractButton button = interaction.getButton();

		if(button==instrument.rotate90Button)
			angle = Math.PI/2.;
		else if(button==instrument.rotate180Button)
			angle = Math.PI;
		else
			angle = -Math.PI/2.;

		action.setRotationAngleIncrement(angle);
		action.setShape(instrument.hand.canvas.getDrawing().getSelection().duplicate());
	}
}
