package net.sf.latexdraw.instruments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ItemSelectable;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.LabelComboBox;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButtonIcon;
import org.malai.swing.widget.MColorButton;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies filling properties of shapes or the pencil.<br>
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
 * 11/11/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeFillingCustomiser extends ShapePropertyCustomiser {
	/** Sets the colour of the interior of a shape. */
	protected MColorButton fillColButton;

	/** Sets the colour of the hatchings. */
	protected MColorButton hatchColButton;

	/** Changes the first colour of a gradient. */
	protected MColorButton gradStartColButton;

	/** Changes the second colour of a gradient. */
	protected MColorButton gradEndColButton;

	/** Changes the style of filling. */
	protected LabelComboBox fillStyleCB;

	/** Changes the mid point of the gradient. */
	protected MSpinner gradMidPtField;

	/** Changes the angle of the gradient. */
	protected MSpinner gradAngleField;

	/** Changes the separation of the hatchings. */
	protected MSpinner hatchSepField;

	/** Changes the angle of the hatchings. */
	protected MSpinner hatchAngleField;

	/** Changes the width of the hatchings. */
	protected MSpinner hatchWidthField;



	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeFillingCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	/**
	 * Creates a list that contains all kinds of hatchings.
	 * @return A created list.
	 */
	public static LabelComboBox createFillingChoice() {
		final LabelComboBox list = new LabelComboBox();

		list.setRenderer(new LabelListCellRenderer());

		JLabel label = new JLabel(FillingStyle.NONE.toString());
		label.setIcon(LResources.HATCH_NONE_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.PLAIN.toString());
		label.setIcon(LResources.HATCH_SOLID_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.CLINES.toString());
		label.setIcon(LResources.HATCH_CROSS_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.CLINES_PLAIN.toString());
		label.setIcon(LResources.HATCH_F_CROSS_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.HLINES.toString());
		label.setIcon(LResources.HATCH_HORIZ_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.HLINES_PLAIN.toString());
		label.setIcon(LResources.HATCH_F_HORIZ_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.VLINES.toString());
		label.setIcon(LResources.HACTH_VERT_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.VLINES_PLAIN.toString());
		label.setIcon(LResources.HATCH_F_VERT_ICON);
		list.addItem(label);
		label = new JLabel(FillingStyle.GRAD.toString());
		label.setIcon(LResources.GRADIENT_ICON);
		list.addItem(label);

		return list;
	}


	@Override
	protected void initialiseWidgets() {
		// Creation of the filling widgets.
     	fillColButton = new MColorButton(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.48"), new MButtonIcon(Color.WHITE));//$NON-NLS-1$
     	fillColButton.setMargin(LResources.INSET_BUTTON);
     	fillColButton.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.68")); //$NON-NLS-1$

     	fillStyleCB = createFillingChoice();
     	fillStyleCB.setPreferredSize(new Dimension(65,30));
     	fillStyleCB.setMaximumSize(new Dimension(65,30));

		// Creation of the gradient widgets.
        gradStartColButton = new MColorButton(LangTool.INSTANCE.getString17("LaTeXDrawFrame.1"), new MButtonIcon(Color.BLACK));
        gradStartColButton.setMargin(LResources.INSET_BUTTON);
        gradStartColButton.setToolTipText(LangTool.INSTANCE.getString17("LaTeXDrawFrame.7")); //$NON-NLS-1$

        gradEndColButton = new MColorButton(LangTool.INSTANCE.getString17("LaTeXDrawFrame.2"), new MButtonIcon(Color.BLACK));
        gradEndColButton.setMargin(LResources.INSET_BUTTON);
        gradEndColButton.setToolTipText(LangTool.INSTANCE.getString17("LaTeXDrawFrame.8")); //$NON-NLS-1$

     	gradMidPtField = new MSpinner(new MSpinner.MSpinnerNumberModel(0.5, 0., 1., 0.01), new JLabel(LangTool.INSTANCE.getString17("AbstractParametersFrame.4"))); //$NON-NLS-1$
     	gradMidPtField.setEditor(new JSpinner.NumberEditor(gradMidPtField, "0.000"));//$NON-NLS-1$

     	gradAngleField = new MSpinner(new MSpinner.MSpinnerNumberModel(0., -360., 360., 0.5), new JLabel(LangTool.INSTANCE.getString17("AbstractParametersFrame.3"))); //$NON-NLS-1$
     	gradAngleField.setEditor(new JSpinner.NumberEditor(gradAngleField, "0.0"));//$NON-NLS-1$

		// Creation of the hatchings widgets.
     	hatchWidthField = new MSpinner(new MSpinner.MSpinnerNumberModel(5., 0.1, 100., 0.1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.11"))); //$NON-NLS-1$
     	hatchWidthField.setEditor(new JSpinner.NumberEditor(hatchWidthField, "0.0"));//$NON-NLS-1$

     	hatchSepField = new MSpinner(new MSpinner.MSpinnerNumberModel(2., 0.01, 1000., 1.), new JLabel(LangTool.INSTANCE.getString18("AbstractParametersFrame.0"))); //$NON-NLS-1$
     	hatchSepField.setEditor(new JSpinner.NumberEditor(hatchSepField, "0.00"));//$NON-NLS-1$

     	hatchAngleField = new MSpinner(new MSpinner.MSpinnerNumberModel(0., -1000., 1000., 1.), new JLabel("angle:"));
     	hatchAngleField.setEditor(new JSpinner.NumberEditor(hatchAngleField, "0.00"));//$NON-NLS-1$

     	hatchColButton = new MColorButton(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.49"), new MButtonIcon(Color.BLACK)); //$NON-NLS-1$
     	hatchColButton.setMargin(LResources.INSET_BUTTON);
     	hatchColButton.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.69")); //$NON-NLS-1$
	}


	@Override
	protected void update(final IShape shape) {
		if(shape.isInteriorStylable()) {
			final FillingStyle style	= shape.getFillingStyle();
			final boolean isFillable	= style.isFilled();
			final boolean hatchings		= style.isHatchings();
			final boolean gradient		= style.isGradient();

			// Updating the visibility of the widgets.
			composer.setWidgetVisible(fillColButton, isFillable);
			composer.setWidgetVisible(hatchColButton, hatchings);
			composer.setWidgetVisible(hatchAngleField, hatchings);
			composer.setWidgetVisible(hatchSepField, hatchings);
			composer.setWidgetVisible(hatchWidthField, hatchings);
			composer.setWidgetVisible(gradStartColButton, gradient);
			composer.setWidgetVisible(gradEndColButton, gradient);
			composer.setWidgetVisible(gradAngleField, gradient);
			composer.setWidgetVisible(gradMidPtField, gradient);

			fillStyleCB.setSelectedItemSafely(style.toString());
			if(isFillable)
				fillColButton.setColor(shape.getFillingCol());
			if(hatchings) {
				hatchColButton.setColor(shape.getHatchingsCol());
				hatchAngleField.setValueSafely(Math.toDegrees(shape.getHatchingsAngle()));
				hatchSepField.setValueSafely(shape.getHatchingsSep());
				hatchWidthField.setValueSafely(shape.getHatchingsWidth());
			}
			else if(gradient){
				gradStartColButton.setColor(shape.getGradColStart());
				gradEndColButton.setColor(shape.getGradColEnd());
				gradAngleField.setValueSafely(Math.toDegrees(shape.getGradAngle()));
				gradMidPtField.setValueSafely(shape.getGradMidPt());
			}
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(fillColButton, visible);
		composer.setWidgetVisible(hatchColButton, visible);
		composer.setWidgetVisible(gradStartColButton, visible);
		composer.setWidgetVisible(gradEndColButton, visible);
		composer.setWidgetVisible(fillStyleCB, visible);
		composer.setWidgetVisible(gradMidPtField, visible);
		composer.setWidgetVisible(gradAngleField, visible);
		composer.setWidgetVisible(hatchSepField, visible);
		composer.setWidgetVisible(hatchAngleField, visible);
		composer.setWidgetVisible(hatchWidthField, visible);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new List2PencilFilling(this));
			addLink(new List2SelectionFilling(this));
			addLink(new ColourButton2PencilFilling(this));
			addLink(new ColourButton2SelectionFilling(this));
			addLink(new Spinner2PencilFilling(this));
			addLink(new Spinner2SelectionFilling(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	/**
	 * @return The widget that modifies the colour of the interior of a shape.
	 * @since 3.0
	 */
	public MColorButton getFillColButton() {
		return fillColButton;
	}

	/**
	 * @return The widget that modifies the colour of the hatchings.
	 * @since 3.0
	 */
	public MColorButton getHatchColButton() {
		return hatchColButton;
	}

	/**
	 * @return The widget that modifies the colour of the starting gradient.
	 * @since 3.0
	 */
	public MColorButton getGradStartColButton() {
		return gradStartColButton;
	}

	/**
	 * @return The widget that modifies the colour of the ending gradient.
	 * @since 3.0
	 */
	public MColorButton getGradEndColButton() {
		return gradEndColButton;
	}

	/**
	 * @return The widget that sets if the shape is filled.
	 * @since 3.0
	 */
	public MComboBox<JLabel> getFillStyleCB() {
		return fillStyleCB;
	}

	/**
	 * @return The widget that modifies the middle point of the gradient.
	 * @since 3.0
	 */
	public MSpinner getGradMidPtField() {
		return gradMidPtField;
	}

	/**
	 * @return The widget that modifies the angle of the gradient.
	 * @since 3.0
	 */
	public MSpinner getGradAngleField() {
		return gradAngleField;
	}

	/**
	 * @return The widget that modifies the separation between hatchings.
	 * @since 3.0
	 */
	public MSpinner getHatchSepField() {
		return hatchSepField;
	}

	/**
	 * @return The widget that modifies the angle of the hatchings.
	 * @since 3.0
	 */
	public MSpinner getHatchAngleField() {
		return hatchAngleField;
	}

	/**
	 * @return The widget that modifies the width of the hatchings.
	 * @since 3.0
	 */
	public MSpinner getHatchWidthField() {
		return hatchWidthField;
	}
}


/**
 * This link maps a list to a ModifyPencil action.
 */
class List2PencilFilling extends ListForCustomiser<ModifyPencilParameter, ShapeFillingCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected List2PencilFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		action.setPencil(instrument.pencil);
		action.setProperty(ShapeProperties.FILLING_STYLE);
		action.setValue(FillingStyle.getStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is = interaction.getList();

		return is==instrument.fillStyleCB && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a list to a ModifyShape action.
 */
class List2SelectionFilling extends ListForCustomiser<ModifyShapeProperty, ShapeFillingCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected List2SelectionFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		action.setProperty(ShapeProperties.FILLING_STYLE);
		action.setValue(FillingStyle.getStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();

		return is==instrument.fillStyleCB && instrument.hand.isActivated();
	}
}


/**
 * This link maps a colour button to the pencil.
 */
class ColourButton2PencilFilling extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeFillingCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ColourButton2PencilFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		final AbstractButton but = interaction.getButton();
		action.setPencil(instrument.pencil);

		if(but==instrument.fillColButton)
			action.setProperty(ShapeProperties.COLOUR_FILLING);
		else if(but==instrument.gradEndColButton)
			action.setProperty(ShapeProperties.COLOUR_GRADIENT_END);
		else if(but==instrument.gradStartColButton)
			action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
		else if(but==instrument.gradStartColButton)
			action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
		else if(but==instrument.hatchColButton)
			action.setProperty(ShapeProperties.COLOUR_HATCHINGS);
		else action = null;
	}

	@Override
	public boolean isConditionRespected() {
		final AbstractButton but = interaction.getButton();
		return (but==instrument.fillColButton || but==instrument.gradEndColButton || but==instrument.gradStartColButton ||
				but==instrument.hatchColButton) && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a colour button to the pencil.
 */
class ColourButton2SelectionFilling extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeFillingCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ColourButton2SelectionFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		final AbstractButton but = interaction.getButton();
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());

		if(but==instrument.fillColButton)
			action.setProperty(ShapeProperties.COLOUR_FILLING);
		else if(but==instrument.gradEndColButton)
			action.setProperty(ShapeProperties.COLOUR_GRADIENT_END);
		else if(but==instrument.gradStartColButton)
			action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
		else if(but==instrument.gradStartColButton)
			action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
		else if(but==instrument.hatchColButton)
			action.setProperty(ShapeProperties.COLOUR_HATCHINGS);
		else action = null;
	}

	@Override
	public boolean isConditionRespected() {
		final AbstractButton but = interaction.getButton();
		return (but==instrument.fillColButton || but==instrument.gradEndColButton || but==instrument.gradStartColButton ||
				but==instrument.hatchColButton) && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2SelectionFilling extends SpinnerForCustomiser<ModifyShapeProperty, ShapeFillingCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected Spinner2SelectionFilling(final ShapeFillingCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		setProperty(interaction.getSpinner(), instrument, action);
	}


	@Override
	public void updateAction() {
		setValue(interaction.getSpinner(), instrument, action);
	}


	@Override
	public boolean isConditionRespected() {
		final JSpinner spinner = getInteraction().getSpinner();
		return instrument.hand.isActivated() && (spinner==instrument.gradAngleField || spinner==instrument.gradMidPtField ||
				spinner==instrument.hatchAngleField || spinner==instrument.hatchSepField || spinner==instrument.hatchWidthField);
	}

	protected static void setProperty(final JSpinner spinner, final ShapeFillingCustomiser sfc, final ShapePropertyAction act) {
		if(spinner==sfc.gradAngleField)
			act.setProperty(ShapeProperties.GRAD_ANGLE);
		else if(spinner==sfc.gradMidPtField)
			act.setProperty(ShapeProperties.GRAD_MID_POINT);
		else if(spinner==sfc.hatchAngleField)
			act.setProperty(ShapeProperties.HATCHINGS_ANGLE);
		else if(spinner==sfc.hatchSepField)
			act.setProperty(ShapeProperties.HATCHINGS_SEP);
		else if(spinner==sfc.hatchWidthField)
			act.setProperty(ShapeProperties.HATCHINGS_WIDTH);
		else
			act.setProperty(null);
	}


	protected static void setValue(final JSpinner spinner, final ShapeFillingCustomiser sfc, final ShapePropertyAction act) {
		if(spinner==sfc.hatchAngleField || spinner==sfc.gradAngleField)
			act.setValue(Math.toRadians(Double.valueOf(spinner.getValue().toString())));
		else
			act.setValue(Double.valueOf(spinner.getValue().toString()));
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2PencilFilling extends SpinnerForCustomiser<ModifyPencilParameter, ShapeFillingCustomiser> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected Spinner2PencilFilling(final ShapeFillingCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		action.setPencil(instrument.pencil);
		Spinner2SelectionFilling.setProperty(interaction.getSpinner(), instrument, action);
	}

	@Override
	public void updateAction() {
		Spinner2SelectionFilling.setValue(interaction.getSpinner(), instrument, action);
	}

	@Override
	public boolean isConditionRespected() {
		final JSpinner spinner = interaction.getSpinner();
		return instrument.pencil.isActivated() && (spinner==instrument.gradAngleField || spinner==instrument.gradMidPtField || spinner==instrument.hatchAngleField ||
				spinner==instrument.hatchSepField || spinner==instrument.hatchWidthField);
	}
}
