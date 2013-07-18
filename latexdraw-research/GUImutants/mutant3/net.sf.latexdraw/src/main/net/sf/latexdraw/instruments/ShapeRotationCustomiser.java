package net.sf.latexdraw.instruments;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.shape.RotateShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Link;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.interaction.library.SpinnerModified;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButton;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies the rotation angle of selected shapes.<br>
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

     	rotationField = new MSpinner(new MSpinner.MSpinnerNumberModel(0., -360., 360., 0.1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.7"))); //$NON-NLS-1$
     	rotationField.setEditor(new JSpinner.NumberEditor(rotationField, "0.0"));	//$NON-NLS-1$
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(rotate180Button, visible);
		composer.setWidgetVisible(rotate270Button, visible);
		composer.setWidgetVisible(rotate90Button, visible);
		composer.setWidgetVisible(rotationField, visible);
	}


	@Override
	protected void update(final IShape shape) {
		if(shape!=null)
			// MUTANT
			rotationField.setValueSafely(shape.getRotationAngle());
//			rotationField.setValueSafely(Math.toDegrees(shape.getRotationAngle()));
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ButtonPress2RotateShape(this));
			addLink(new Spinner2RotateShape(this));
		}catch(InstantiationException | IllegalAccessException e){
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
class Spinner2RotateShape extends Link<RotateShapes, SpinnerModified, ShapeRotationCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	public Spinner2RotateShape(final ShapeRotationCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, RotateShapes.class, SpinnerModified.class);
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.getRotationField()==interaction.getSpinner();
	}

	@Override
	public void updateAction() {
		Object obj  = action.shape().get();
		if(obj instanceof IShape)
			action.setRotationAngle(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString()))-((IShape)obj).getRotationAngle());
	}

	@Override
	public void initAction() {
		IShape sh = instrument.pencil.canvas.getDrawing().getSelection().duplicate();
		action.setShape(sh);
		action.setGravityCentre(sh.getGravityCentre());
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

		action.setGravityCentre(instrument.pencil.canvas.getDrawing().getSelection().getGravityCentre());
		action.setRotationAngle(angle);
		action.setShape(instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}
}
