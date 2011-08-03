package net.sf.latexdraw.instruments;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.widget.MComboBox;
import fr.eseo.malai.widget.MSpinner;

/**
 * This instrument allows to parameterise the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 05/16/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PencilCustomiser extends Instrument {
	/** The pencil to parameterise. */
	protected Pencil pencil;

	/** Allows to define the size of a dot. */
	protected MSpinner dotSizeField;

	/** Allows the selection of a dot shape. */
	protected MComboBox dotCB;

	/** Allows to change the style of the left-end of the shape. */
	protected MComboBox arrowLeftCB;

	/** Allows to change the style of the right-end of the shape. */
	protected MComboBox arrowRightCB;


	/**
	 * Creates a pencil customiser.
	 * @param pencil The pencil to customise.
	 * @since 3.0
	 */
	public PencilCustomiser(final Pencil pencil) {
		super();

		this.pencil = pencil;

     	SpinnerModel model 	= new SpinnerNumberModel(6, 0.1, 1000, 0.1);
     	dotSizeField = new MSpinner(model, new JLabel(LResources.DOT_STYLE_NONE_ICON));
     	dotSizeField.setEditor(new JSpinner.NumberEditor(dotSizeField, "0.0"));//$NON-NLS-1$
     	dotSizeField.setToolTipText("Define the size of a dot.");
     	dotSizeField.setMaximumSize(new Dimension(55, 30));
     	dotSizeField.setMinimumSize(new Dimension(55, 30));
     	dotSizeField.setPreferredSize(new Dimension(55, 30));

     	dotCB = createDotStyleChoice();
     	dotCB.setPreferredSize(new Dimension(55,30));
     	dotCB.setMaximumSize(new Dimension(55,30));

     	arrowLeftCB = createLeftArrowStyleList();
     	arrowLeftCB.setPreferredSize(new Dimension(80,30));
     	arrowLeftCB.setMaximumSize(new Dimension(80,30));

     	arrowRightCB = createRightArrowStyleList();
     	arrowRightCB.setPreferredSize(new Dimension(80,30));
     	arrowRightCB.setMaximumSize(new Dimension(80,30));

        updateValues();
        initialiseLinks();
	}



	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		updateVisibility();
	}




	/**
	 * Creates a list of the different styles of arrowhead (left).
	 * @return The created list.
	 */
	public static MComboBox createLeftArrowStyleList() {
		MComboBox lineArrowLChoice = new MComboBox();

		lineArrowLChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(PSTricksConstants.NONEARROW_STYLE);//TODO Maybe it should be the name of the Enum.
		label.setIcon(LResources.ARROW_STYLE_NONE_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.BARIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BAR_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.BAREND_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BAR_END_L_ICON);
		lineArrowLChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.CIRCLEEND_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_END_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.CIRCLEIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.DISKEND_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_DISK_END_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.DISKIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_DISK_IN_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.LARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ARROW_L_ICON);
     	lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.RARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_R_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.LRBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ARC_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.RRBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ARC_LR_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.LSBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BRACK_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.RSBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BRACK_LR_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.DLARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_DBLE_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(PSTricksConstants.DRARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_R_DBLE_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.ROUNDIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ROUND_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
     	lineArrowLChoice.setPreferredSize(new Dimension(75, 30));
     	lineArrowLChoice.setSize(new Dimension(75, 30));
     	lineArrowLChoice.setMaximumSize(new Dimension(75, 30));
     	lineArrowLChoice.setMinimumSize(new Dimension(75, 30));

		return lineArrowLChoice;
	}



	/**
	 * Creates a list of the different styles of arrowhead (right).
	 * @return The created list.
	 */
	public static MComboBox createRightArrowStyleList() {
		MComboBox lineArrowRChoice = new MComboBox();

		lineArrowRChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(PSTricksConstants.NONEARROW_STYLE);
		label.setIcon(LResources.ARROW_STYLE_NONE_R_ICON);
		lineArrowRChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.BARIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BAR_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.BAREND_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BAR_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.CIRCLEEND_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.CIRCLEIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.DISKEND_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_DISK_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.DISKIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_DISK_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.RARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.LARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_R_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.RRBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ARC_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.LRBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ARC_RR_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.RSBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BRACK_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.LSBRACKET_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_BRACK_RR_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.DRARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_DBLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(PSTricksConstants.DLARROW_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_R_DBLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(PSTricksConstants.ROUNDIN_STYLE);
     	label.setIcon(LResources.ARROW_STYLE_ROUND_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
     	lineArrowRChoice.setPreferredSize(new Dimension(75, 30));
     	lineArrowRChoice.setSize(new Dimension(75, 30));
     	lineArrowRChoice.setMaximumSize(new Dimension(75, 30));
     	lineArrowRChoice.setMinimumSize(new Dimension(75, 30));

     	return lineArrowRChoice;
	}


	/**
	 * Creates a list of the different styles of the dot.
	 * @return The created list.
	 */
	public static MComboBox createDotStyleChoice() {
		MComboBox dotChoice = new MComboBox();

		dotChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(DotStyle.DOT.toString());
		label.setIcon(LResources.DOT_STYLE_NONE_ICON);
     	dotChoice.addItem(label);
     	label = new JLabel(DotStyle.DOT.toString());
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
     	label = new JLabel(DotStyle.PLUS.toString());
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

     	return dotChoice;
	}



	@Override
	protected void initialiseLinks() {
		//
	}


	/**
	 * @return The pencil to modify.
	 * @since 3.0
	 */
	public Pencil getPencil() {
		return pencil;
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



	/**
	 * Updates the value of the widgets.
	 * @since 3.0
	 */
	protected void updateValues() {
		//
	}


	/**
	 * Updates the visibility of the widgets.
	 * @since 3.0
	 */
	@SuppressWarnings("null")
	protected void updateVisibility() {
		final IShape tmpShape 		= pencil.createShapeInstance();
		final boolean shapeNotNull 	= tmpShape!=null;
		final boolean isDotShape	= shapeNotNull && tmpShape instanceof IDot;

		dotSizeField.setVisible(activated && shapeNotNull && isDotShape);
		dotCB.setVisible(activated && shapeNotNull && isDotShape);
		arrowLeftCB.setVisible(activated && shapeNotNull && tmpShape.isArrowable());
		arrowRightCB.setVisible(activated && shapeNotNull && tmpShape.isArrowable());
	}



	/**
	 * Updates the customiser and its widgets.
	 * @since 3.0
	 */
	public void update() {
		updateValues();
		updateVisibility();
	}
}

