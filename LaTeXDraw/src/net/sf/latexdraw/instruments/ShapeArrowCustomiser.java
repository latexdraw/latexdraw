package net.sf.latexdraw.instruments;

import java.awt.Dimension;
import java.awt.ItemSelectable;

import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.widget.MComboBox;

/**
 * This instrument customises the arrows of shapes or of the pencil.<br>
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
 * 08/05/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeArrowCustomiser extends ShapePropertyCustomiser {
	/** Allows to change the style of the left-end of the shape. */
	protected MComboBox arrowLeftCB;

	/** Allows to change the style of the right-end of the shape. */
	protected MComboBox arrowRightCB;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeArrowCustomiser(final Hand hand, final Pencil pencil) {
		super(hand, pencil);

		initialiseLinks();
		initWidgets();
	}


	@Override
	protected void initWidgets() {
     	arrowLeftCB = createLeftArrowStyleList();
     	arrowLeftCB.setPreferredSize(new Dimension(80,30));
     	arrowLeftCB.setMaximumSize(new Dimension(80,30));

     	arrowRightCB = createRightArrowStyleList();
     	arrowRightCB.setPreferredSize(new Dimension(80,30));
     	arrowRightCB.setMaximumSize(new Dimension(80,30));
	}


	/**
	 * Creates a list of the different styles of arrowhead (right).
	 * @return The created list.
	 */
	public static MComboBox createRightArrowStyleList() {
		final MComboBox lineArrowRChoice = new MComboBox();

		lineArrowRChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(ArrowStyle.NONE.name());
		label.setIcon(LResources.ARROW_STYLE_NONE_R_ICON);
		lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.BAR_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.BAR_END.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_END.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_END.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_RR_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_RR_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_DBLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_DBLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.ROUND_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_ROUND_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
     	lineArrowRChoice.setPreferredSize(new Dimension(75, 30));
     	lineArrowRChoice.setSize(new Dimension(75, 30));
     	lineArrowRChoice.setMaximumSize(new Dimension(75, 30));
     	lineArrowRChoice.setMinimumSize(new Dimension(75, 30));

     	return lineArrowRChoice;
	}



	/**
	 * Creates a list of the different styles of arrowhead (left).
	 * @return The created list.
	 */
	public static MComboBox createLeftArrowStyleList() {
		final MComboBox lineArrowLChoice = new MComboBox();

		lineArrowLChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(ArrowStyle.NONE.name());
		label.setIcon(LResources.ARROW_STYLE_NONE_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.BAR_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.BAR_END.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_END_L_ICON);
		lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_END.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_END_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_END.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_END_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_IN_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_ARROW_L_ICON);
     	lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_LR_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_LR_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_DBLE_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_DBLE_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.ROUND_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_ROUND_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
     	lineArrowLChoice.setPreferredSize(new Dimension(75, 30));
     	lineArrowLChoice.setSize(new Dimension(75, 30));
     	lineArrowLChoice.setMaximumSize(new Dimension(75, 30));
     	lineArrowLChoice.setMinimumSize(new Dimension(75, 30));

		return lineArrowLChoice;
	}



	@Override
	protected void initialiseLinks() {
		try{
			links.add(new List2PencilArrowStyle(this));
			links.add(new List2ShapeArrowStyle(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}



	@Override
	protected void update(final IShape shape) {
		if(shape!=null) {
			final boolean arrowable = shape.isArrowable();

			if(widgetContainer!=null)
				widgetContainer.setVisible(activated && arrowable);

			if(arrowable) {//TODO this code support that is arrowable, there is 2 arrows.
				arrowLeftCB.setSelectedItemSafely(shape.getArrowStyle(0).name());
				arrowRightCB.setSelectedItemSafely(shape.getArrowStyle(1).name());
			}
		}
	}


	/**
	 * @return The left arrow style combo box.
	 * @since 3.0
	 */
	public MComboBox getArrowLeftCB() {
		return arrowLeftCB;
	}

	/**
	 * @return The right arrow style combo box.
	 * @since 3.0
	 */
	public MComboBox getArrowRightCB() {
		return arrowRightCB;
	}
}


/**
 * This link maps a list to a ModifyPencil action.
 */
class List2PencilArrowStyle extends ListForCustomiser<ModifyPencilParameter, ShapeArrowCustomiser> {
	/**
	 * Creates the link.
	 */
	public List2PencilArrowStyle(final ShapeArrowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();

		if(getInteraction().getList()==instrument.arrowLeftCB)
			action.setProperty(ShapeProperties.ARROW1_STYLE);
		else
			action.setProperty(ShapeProperties.ARROW2_STYLE);

		action.setPencil(instrument.pencil);
		action.setValue(ArrowStyle.getArrowStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();
		return (is==instrument.arrowLeftCB || is==instrument.arrowRightCB) && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a list to a ModifyShape action.
 */
class List2ShapeArrowStyle extends ListForCustomiser<ModifyShapeProperty, ShapeArrowCustomiser> {
	/**
	 * Creates the link.
	 */
	public List2ShapeArrowStyle(final ShapeArrowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}


	@Override
	public void initAction() {
		super.initAction();

		if(getInteraction().getList()==instrument.arrowLeftCB)
			action.setProperty(ShapeProperties.ARROW1_STYLE);
		else
			action.setProperty(ShapeProperties.ARROW2_STYLE);

		action.setShape(instrument.drawing.getSelection().duplicate());
		action.setValue(ArrowStyle.getArrowStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();
		return (is==instrument.arrowLeftCB || is==instrument.arrowRightCB) && instrument.hand.isActivated();
	}
}

