package net.sf.latexdraw.instruments;

import java.util.Objects;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.ui.LFrame;

import org.malai.swing.instrument.SwingInstrument;
import org.malai.instrument.InteractorImpl;
import org.malai.swing.action.library.ActivateInactivateInstruments;
import org.malai.swing.interaction.library.TabSelected;

/**
 * This instrument (de-)activates instruments while changing of tab (drawing tab, PST tab, etc.).<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 12/09/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TabSelector extends SwingInstrument {
	/** The main frame of the system. */
	protected LFrame frame;


	/**
	 * Creates the instrument.
	 * @param frame The main frame of the system.
	 * @throws IllegalArgumentException If the given argument is null.
	 * @since 3.0
	 */
	public TabSelector(final LFrame frame) {
		super();
		this.frame = Objects.requireNonNull(frame);
	}



	@Override
	protected void initialiseInteractors() {
		try{
			addInteractor(new TabSelected2ActivateInstruments(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	private static class TabSelected2ActivateInstruments extends InteractorImpl<ActivateInactivateInstruments, TabSelected, TabSelector> {
		protected TabSelected2ActivateInstruments(final TabSelector ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ActivateInactivateInstruments.class, TabSelected.class);
		}

		@Override
		public void initAction() {
			switch(interaction.getTabbedPane().getSelectedIndex()) {
				case 0: // Drawing tab.
					action.addInstrumentToActivate(instrument.frame.getEditingSelector());
					action.addInstrumentToActivate(instrument.frame.getGridCustomiser());
					action.addInstrumentToActivate(instrument.frame.getPaster());
					action.addInstrumentToActivate(instrument.frame.getScaleRulersCustomiser());
					action.addInstrumentToActivate(instrument.frame.getUndoManager());
					action.addInstrumentToActivate(instrument.frame.getZoomer());
					if(getInstrument().frame.getDrawing().getSelection().isEmpty()) {
						action.addInstrumentToInactivate(instrument.frame.getDeleter());
					}else {
						action.addInstrumentToActivate(instrument.frame.getDeleter());
					}
					break;
				case 1: // PST tab.
					action.setHideWidgets(true);
					action.addInstrumentToInactivate(instrument.frame.getEditingSelector());
					action.addInstrumentToInactivate(instrument.frame.getGridCustomiser());
					action.addInstrumentToInactivate(instrument.frame.getPaster());
					action.addInstrumentToInactivate(instrument.frame.getScaleRulersCustomiser());
					action.addInstrumentToInactivate(instrument.frame.getUndoManager());
					action.addInstrumentToInactivate(instrument.frame.getZoomer());
					action.addInstrumentToInactivate(instrument.frame.getDeleter());
					break;
					default:
			}
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getTabbedPane()==instrument.frame.getTabbedPanel();
		}
	}
}
