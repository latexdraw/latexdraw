package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.malai.instrument.Link;
import org.malai.instrument.library.WidgetContainerInstrument;
import org.malai.interaction.library.CheckBoxModified;
import org.malai.interaction.library.ListSelectionModified;
import org.malai.interaction.library.SpinnerModified;
import org.malai.widget.MCheckBox;
import org.malai.widget.MComboBox;
import org.malai.widget.MSpinner;

import net.sf.latexdraw.actions.ModifyMagneticGrid;
import net.sf.latexdraw.actions.ModifyMagneticGrid.GridProperties;
import net.sf.latexdraw.badaboom.BordelCollector;
import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.glib.ui.LMagneticGrid.GridStyle;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument customises the magnetic grid.<br>
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
 * 11/14/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class MagneticGridCustomiser extends WidgetContainerInstrument {
	/** The grid to customise. */
	protected LMagneticGrid grid;

	/** Contains the different possible kind of grids. */
	protected MComboBox styleList;

	/** Sets if the grid is magnetic. */
	protected MCheckBox magneticCB;

	/** Defines the spacing of the personal grid. */
	protected MSpinner gridSpacing;


	/**
	 * Initialises the instrument.
	 * @param grid The grid to customise.
	 * @throws IllegalArgumentException If the given grid is null.
	 * @since 3.0
	 */
	public MagneticGridCustomiser(final LMagneticGrid grid) {
		super();

		if(grid==null)
			throw new IllegalArgumentException();

		this.grid 				= grid;
		styleList				= createStyleList();
		SpinnerNumberModel model= new SpinnerNumberModel(10, 2, 100000, 1);
     	gridSpacing 			= new MSpinner(model, new JLabel(LResources.GRID_GAP_ICON));
     	gridSpacing.setEditor(new JSpinner.NumberEditor(gridSpacing, "0"));//$NON-NLS-1$
     	gridSpacing.setToolTipText(LangTool.LANG.getString18("LaTeXDrawFrame.15")); //$NON-NLS-1$
 		magneticCB 				= new MCheckBox(LangTool.LANG.getString18("LaTeXDrawFrame.13")); //$NON-NLS-1$
 		magneticCB.setToolTipText(LangTool.LANG.getString18("LaTeXDrawFrame.14")); //$NON-NLS-1$
		initialiseLinks();
	}


	/**
	 * @return The list widget that contains the different style of the magnetic grid.
	 * @since 3.0
	 */
	public static MComboBox createStyleList() {
		final MComboBox list = new MComboBox();

		list.addItem(GridStyle.STANDARD.getLabel());
		list.addItem(GridStyle.CUSTOMISED.getLabel());
		list.addItem(GridStyle.NONE.getLabel());

		return list;
	}


	@Override
	public void interimFeedback() {
		super.interimFeedback();
		final GridStyle style 	= grid.getStyle();
		final boolean visible	= style!=GridStyle.NONE;

		gridSpacing.setVisible(visible && style==GridStyle.CUSTOMISED);
		magneticCB.setVisible(visible);
		styleList.setSelectedItemSafely(style.getLabel());

		if(visible) {
			if(style==GridStyle.CUSTOMISED)
				gridSpacing.setValueSafely(grid.getGridSpacing());
			magneticCB.setSelected(grid.isMagnetic());
		}

		if(widgetContainer!=null)
			widgetContainer.repaint();
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new List2ChangeStyle(this));
			links.add(new Spinner2GridSpacing(this));
			links.add(new CheckBox2MagneticGrid(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The list that contains the different possible kind of grids.
	 * @since 3.0
	 */
	public MComboBox getStyleList() {
		return styleList;
	}


	/**
	 * @return The check box that sets if the grid is magnetic.
	 * @since 3.0
	 */
	public MCheckBox getMagneticCB() {
		return magneticCB;
	}


	/**
	 * @return The spinner that defines the spacing of the personal grid.
	 * @since 3.0
	 */
	public MSpinner getGridSpacing() {
		return gridSpacing;
	}
}


/**
 * Links a check-box widget to an action that sets if the grid is magnetic.
 */
class CheckBox2MagneticGrid extends Link<ModifyMagneticGrid, CheckBoxModified, MagneticGridCustomiser> {
	/**
	 * Initialises the link.
	 * @since 3.0
	 */
	public CheckBox2MagneticGrid(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ModifyMagneticGrid.class, CheckBoxModified.class);
	}

	@Override
	public void initAction() {
		action.setProperty(GridProperties.MAGNETIC);
		action.setGrid(instrument.grid);
		action.setValue(interaction.getCheckBox().isSelected());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getCheckBox()==instrument.magneticCB;
	}
}



/**
 * Links a spinner widget to an action that modifies the spacing of the customised magnetic grid.
 */
class Spinner2GridSpacing extends Link<ModifyMagneticGrid, SpinnerModified, MagneticGridCustomiser> {
	/**
	 * Initialises the link.
	 * @since 3.0
	 */
	public Spinner2GridSpacing(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, true, ModifyMagneticGrid.class, SpinnerModified.class);
	}

	@Override
	public void initAction() {
		action.setProperty(GridProperties.GRID_SPACING);
		action.setGrid(instrument.grid);
	}


	@Override
	public void updateAction() {
		action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.gridSpacing;
	}
}



/**
 * Links a list widget to an action that modifies the style of the magnetic grid.
 */
class List2ChangeStyle extends Link<ModifyMagneticGrid, ListSelectionModified, MagneticGridCustomiser> {
	/**
	 * Initialises the link.
	 * @since 3.0
	 */
	public List2ChangeStyle(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ModifyMagneticGrid.class, ListSelectionModified.class);
	}

	@Override
	public void initAction() {
		action.setValue(GridStyle.getStyleFromLabel(interaction.getList().getSelectedObjects()[0].toString()));
		action.setGrid(instrument.grid);
		action.setProperty(GridProperties.STYLE);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getList()==instrument.styleList;
	}
}

