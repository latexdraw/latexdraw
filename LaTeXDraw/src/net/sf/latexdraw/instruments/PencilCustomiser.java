package net.sf.latexdraw.instruments;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
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

        updateValues();
        initialiseLinks();
	}



	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		updateVisibility();
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
	protected void updateVisibility() {
		final IShape tmpShape 		= pencil.createShapeInstance();
		final boolean shapeNotNull 	= tmpShape!=null;
		final boolean isDotShape	= shapeNotNull && tmpShape instanceof IDot;

		dotSizeField.setVisible(activated && shapeNotNull && isDotShape);
		dotCB.setVisible(activated && shapeNotNull && isDotShape);
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

