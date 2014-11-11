package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.glib.views.GridStyle;
import net.sf.latexdraw.glib.views.MagneticGrid;

import org.malai.javafx.instrument.JfxInstrument;
import org.malai.undo.Undoable;

/**
 * This instrument customises the magnetic grid.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public class MagneticGridCustomiser extends JfxInstrument implements Initializable {
	/** The grid to customise. */
	protected MagneticGrid grid;

	/** Contains the different possible kind of grids. */
	@FXML protected ComboBox<GridStyle> styleList;

	/** Sets if the grid is magnetic. */
	@FXML protected CheckBox magneticCB;

	/** Defines the spacing of the personal grid. */
	@FXML protected Spinner<Integer> gridSpacing;


	/**
	 * Initialises the instrument.
	 */
	public MagneticGridCustomiser() {
		super();
	}

	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		styleList.getItems().addAll(GridStyle.values());
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

		gridSpacing.setDisable(style!=GridStyle.CUSTOMISED);
		magneticCB.setDisable(style==GridStyle.NONE);
		styleList.getSelectionModel().select(style);

		if(style!=GridStyle.NONE) {
			if(style==GridStyle.CUSTOMISED)
				gridSpacing.getValueFactory().setValue(grid.getGridSpacing());
			magneticCB.setSelected(grid.isMagnetic());
		}
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new List2ChangeStyle(this));
//			addInteractor(new Spinner2GridSpacing(this));
//			addInteractor(new CheckBox2MagneticGrid(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}
}


///**
// * Links a check-box widget to an action that sets if the grid is magnetic.
// */
//class CheckBox2MagneticGrid extends InteractorImpl<ModifyMagneticGrid, CheckBoxModified, MagneticGridCustomiser> {
//	/**
//	 * Initialises the link.
//	 * @since 3.0
//	 */
//	protected CheckBox2MagneticGrid(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, false, ModifyMagneticGrid.class, CheckBoxModified.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setProperty(GridProperties.MAGNETIC);
//		action.setGrid(instrument.grid);
//		action.setValue(interaction.getCheckBox().isSelected());
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getCheckBox()==instrument.magneticCB;
//	}
//}
//
//
//
///**
// * Links a spinner widget to an action that modifies the spacing of the customised magnetic grid.
// */
//class Spinner2GridSpacing extends InteractorImpl<ModifyMagneticGrid, SpinnerModified, MagneticGridCustomiser> {
//	/**
//	 * Initialises the link.
//	 * @since 3.0
//	 */
//	protected Spinner2GridSpacing(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, false, ModifyMagneticGrid.class, SpinnerModified.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setProperty(GridProperties.GRID_SPACING);
//		action.setGrid(instrument.grid);
//	}
//
//
//	@Override
//	public void updateAction() {
//		action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
//	}
//
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getSpinner()==instrument.gridSpacing;
//	}
//}
//
//
//
///**
// * Links a list widget to an action that modifies the style of the magnetic grid.
// */
//class List2ChangeStyle extends InteractorImpl<ModifyMagneticGrid, ListSelectionModified, MagneticGridCustomiser> {
//	/**
//	 * Initialises the link.
//	 * @since 3.0
//	 */
//	protected List2ChangeStyle(final MagneticGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, false, ModifyMagneticGrid.class, ListSelectionModified.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setValue(GridStyle.getStyleFromLabel(interaction.getList().getSelectedObjects()[0].toString()));
//		action.setGrid(instrument.grid);
//		action.setProperty(GridProperties.STYLE);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getList()==instrument.styleList;
//	}
//}

