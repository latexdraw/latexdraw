/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument (de-)activates instruments while changing of tab (drawing tab, PST tab, etc.).
 * @author Arnaud BLOUIN
 */
public class TabSelector extends JfxInstrument {
	// /** The main frame of the system. */
	// protected LFrame frame;

	/**
	 * Creates the instrument.
	 */
	public TabSelector() {
		super();
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new TabSelected2ActivateInstruments(this));
	}

	// private static class TabSelected2ActivateInstruments extends
	// InteractorImpl<ActivateInactivateInstruments, TabSelected, TabSelector> {
	// protected TabSelected2ActivateInstruments(final TabSelector ins) throws
	// InstantiationException, IllegalAccessException {
	// super(ins, false, ActivateInactivateInstruments.class,
	// TabSelected.class);
	// }
	//
	// @Override
	// public void initAction() {
	// action.setHideWidgets(true);
	//
	// switch(interaction.getTabbedPane().getSelectedIndex()) {
	// case 0: // Drawing tab.
	// initActionOnDrawingPanel();
	// break;
	// case 1: // PST tab.
	// initActionOnPSTCodePanel();
	// break;
	// default:
	// }
	// }
	//
	//
	// protected void initActionOnDrawingPanel() {
	// action.addInstrumentToActivate(instrument.frame.getEditingSelector());
	// action.addInstrumentToActivate(instrument.frame.getPaster());
	// action.addInstrumentToActivate(instrument.frame.getUndoManager());
	// action.addInstrumentToActivate(instrument.frame.getZoomer());
	// }
	//
	//
	// protected void initActionOnPSTCodePanel() {
	// action.addInstrumentToInactivate(instrument.frame.getEditingSelector());
	// action.addInstrumentToInactivate(instrument.frame.getPaster());
	// action.addInstrumentToInactivate(instrument.frame.getUndoManager());
	// action.addInstrumentToInactivate(instrument.frame.getZoomer());
	// // The deleter must be added to use the hideWidgets parameter of the
	// action.
	// action.addInstrumentToInactivate(instrument.frame.getDeleter());
	// }
	//
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getTabbedPane()==instrument.frame.getTabbedPanel();
	// }
	// }
}
