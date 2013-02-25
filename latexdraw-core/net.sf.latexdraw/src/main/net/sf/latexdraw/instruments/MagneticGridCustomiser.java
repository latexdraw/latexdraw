package net.sf.latexdraw.instruments;

import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.ModifyMagneticGrid;
import net.sf.latexdraw.actions.ModifyMagneticGrid.GridProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.glib.ui.LMagneticGrid.GridStyle;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Link;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.CheckBoxModified;
import org.malai.swing.interaction.library.ListSelectionModified;
import org.malai.swing.interaction.library.SpinnerModified;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MCheckBox;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MSpinner;
import org.malai.undo.Undoable;

/**
 * This instrument customises the magnetic grid.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
public class MagneticGridCustomiser extends WidgetInstrument {
	/** The grid to customise. */
	protected LMagneticGrid grid;

	/** Contains the different possible kind of grids. */
	protected MComboBox<String> styleList;

	/** Sets if the grid is magnetic. */
	protected MCheckBox magneticCB;

	/** Defines the spacing of the personal grid. */
	protected MSpinner gridSpacing;


	/**
	 * Initialises the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param grid The grid to customise.
	 * @throws IllegalArgumentException If the given grid is null.
	 * @since 3.0
	 */
	public MagneticGridCustomiser(final UIComposer<?> composer, final LMagneticGrid grid) {
		super(composer);
		this.grid = Objects.requireNonNull(grid);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
		styleList				= createStyleList();
     	gridSpacing 			= new MSpinner(new MSpinner.MSpinnerNumberModel(10, 2, 100000, 1), new JLabel(LResources.GRID_GAP_ICON));
     	gridSpacing.setEditor(new JSpinner.NumberEditor(gridSpacing, "0"));//$NON-NLS-1$
     	gridSpacing.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.15")); //$NON-NLS-1$
 		magneticCB 				= new MCheckBox(LangTool.INSTANCE.getString18("LaTeXDrawFrame.13")); //$NON-NLS-1$
 		magneticCB.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.14")); //$NON-NLS-1$
	}


	/**
	 * @return The list widget that contains the different style of the magnetic grid.
	 * @since 3.0
	 */
	public static MComboBox<String> createStyleList() {
		final MComboBox<String> list = new MComboBox<>();

		list.addItem(GridStyle.STANDARD.getLabel());
		list.addItem(GridStyle.CUSTOMISED.getLabel());
		list.addItem(GridStyle.NONE.getLabel());

		return list;
	}


	@Override
	public void onUndoableUndo(final Undoable undoable) {
		super.onUndoableUndo(undoable);
		update();
	}


	@Override
	public void onUndoableRedo(final Undoable undoable) {
		super.onUndoableRedo(undoable);
		update();
	}


	@Override
	public void interimFeedback() {
		super.interimFeedback();
		update();
	}


	protected void update() {
		final GridStyle style = grid.getStyle();
		final boolean visible = style!=GridStyle.NONE;

		composer.setWidgetVisible(gridSpacing, visible && style==GridStyle.CUSTOMISED);
		composer.setWidgetVisible(magneticCB, visible);
		styleList.setSelectedItemSafely(style.getLabel());

		if(visible) {
			if(style==GridStyle.CUSTOMISED)
				gridSpacing.setValueSafely(grid.getGridSpacing());
			magneticCB.setSelected(grid.isMagnetic());
		}
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new List2ChangeStyle(this));
			addLink(new Spinner2GridSpacing(this));
			addLink(new CheckBox2MagneticGrid(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		composer.setWidgetVisible(gridSpacing, activated);
		composer.setWidgetVisible(magneticCB, activated);
		composer.setWidgetVisible(styleList, activated);
	}


	/**
	 * @return The list that contains the different possible kind of grids.
	 * @since 3.0
	 */
	public MComboBox<String> getStyleList() {
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
	protected CheckBox2MagneticGrid(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
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
	protected Spinner2GridSpacing(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ModifyMagneticGrid.class, SpinnerModified.class);
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
	protected List2ChangeStyle(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
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

