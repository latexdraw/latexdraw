package net.sf.latexdraw.instruments;

import java.awt.Dimension;
import java.awt.ItemSelectable;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.Dottable;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;

import org.malai.ui.UIComposer;
import org.malai.widget.MComboBox;
import org.malai.widget.MSpinner;

/**
 * This instrument modifies dot parameters.<br>
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
 * 08/10/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDotCustomiser extends ShapePropertyCustomiser {
	/** Allows to define the size of a dot. */
	protected MSpinner dotSizeField;

	/** Allows the selection of a dot shape. */
	protected MComboBox dotCB;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public ShapeDotCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);

		initialiseWidgets();
     	initialiseLinks();
	}



	@Override
	protected void initialiseWidgets() {
     	dotSizeField = new MSpinner(new SpinnerNumberModel(6, 0.1, 1000, 1), new JLabel(LResources.DOT_STYLE_NONE_ICON));
     	dotSizeField.setEditor(new JSpinner.NumberEditor(dotSizeField, "0.0"));//$NON-NLS-1$
     	dotSizeField.setToolTipText("Define the size of a dot.");

     	dotCB = createDotStyleChoice();
     	dotSizeField.setToolTipText("Select the style of the dot.");
	}

	/**
	 * Creates a list of the different styles of the dot.
	 * @return The created list.
	 */
	public static MComboBox createDotStyleChoice() {
		final MComboBox dotChoice = new MComboBox();

		dotChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(DotStyle.DOT.toString());
		label.setIcon(LResources.DOT_STYLE_NONE_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.ASTERISK.toString());
     	label.setIcon(LResources.DOT_STYLE_ASTERISK_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.BAR.toString());
     	label.setIcon(LResources.DOT_STYLE_BAR_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.DIAMOND.toString());
     	label.setIcon(LResources.DOT_STYLE_DIAMOND_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.FDIAMOND.toString());
     	label.setIcon(LResources.DOT_STYLE_DIAMOND_F_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.O.toString());
     	label.setIcon(LResources.DOT_STYLE_O_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.OPLUS.toString());
     	label.setIcon(LResources.DOT_STYLE_O_PLUS_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.OTIMES.toString());
     	label.setIcon(LResources.DOT_STYLE_O_CROSS_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.PLUS.toString());
     	label.setIcon(LResources.DOT_STYLE_PLUS_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.X.toString());
     	label.setIcon(LResources.DOT_STYLE_CROSS_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.TRIANGLE.toString());
     	label.setIcon(LResources.DOT_STYLE_TRIANGLE_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.FTRIANGLE.toString());
     	label.setIcon(LResources.DOT_STYLE_TRIANGLE_F_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.PENTAGON.toString());
     	label.setIcon(LResources.DOT_STYLE_PENTAGON_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.FPENTAGON.toString());
     	label.setIcon(LResources.DOT_STYLE_PENTAGON_F_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.SQUARE.toString());
     	label.setIcon(LResources.DOT_STYLE_SQUARE_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.FSQUARE.toString());
     	label.setName(DotStyle.FSQUARE.toString());
     	label.setIcon(LResources.DOT_STYLE_SQUARE_F_ICON);
     	dotChoice.addItem(label);

     	dotChoice.setPreferredSize(new Dimension(55,30));
     	dotChoice.setMaximumSize(new Dimension(55,30));

     	return dotChoice;
	}

	@Override
	protected void update(final IShape shape) {
		if(shape instanceof Dottable && ((Dottable)shape).hasDot()) {
			final Dottable dot 	= (Dottable)shape;
			dotSizeField.setValueSafely(dot.getRadius());
			dotCB.setSelectedItemSafely(dot.getDotStyle().toString());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(dotCB, visible);
		composer.setWidgetVisible(dotSizeField, visible);
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new Spinner2PencilDotSize(this));
			links.add(new Spinner2SelectionDotSize(this));
			links.add(new List2PencilDotStyle(this));
			links.add(new List2SelectionDotStyle(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	/**
	 * @return The dot size field.
	 * @since 3.0
	 */
	public MSpinner getDotSizeField() {
		return dotSizeField;
	}

	/**
	 * @return The dot style combo box.
	 * @since 3.0
	 */
	public MComboBox getDotCB() {
		return dotCB;
	}
}



/**
 * This link maps a list to a ModifyPencil action.
 */
class List2PencilDotStyle extends ListForCustomiser<ModifyPencilParameter, ShapeDotCustomiser> {
	protected List2PencilDotStyle(final ShapeDotCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		action.setPencil(instrument.pencil);
		action.setProperty(ShapeProperties.DOT_STYLE);
		action.setValue(DotStyle.getStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is = interaction.getList();
		return is==instrument.dotCB && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a list to a ModifyShape action.
 */
class List2SelectionDotStyle extends ListForCustomiser<ModifyShapeProperty, ShapeDotCustomiser> {
	protected List2SelectionDotStyle(final ShapeDotCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setShape(instrument.pencil.drawing.getSelection().duplicate());
		action.setProperty(ShapeProperties.DOT_STYLE);
		action.setValue(DotStyle.getStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();
		return is==instrument.dotCB && instrument.hand.isActivated();
	}
}



/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2SelectionDotSize extends SpinnerForCustomiser<ModifyShapeProperty, ShapeDotCustomiser> {
	protected Spinner2SelectionDotSize(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.DOT_SIZE);
		action.setShape(instrument.pencil.drawing.getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.dotSizeField && instrument.hand.isActivated();
	}
}


/**
 * This link maps a spinner to a ModifyPencil action.
 */
class Spinner2PencilDotSize extends SpinnerForCustomiser<ModifyPencilParameter, ShapeDotCustomiser> {
	protected Spinner2PencilDotSize(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.DOT_SIZE);
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.dotSizeField && instrument.pencil.isActivated();
	}
}
