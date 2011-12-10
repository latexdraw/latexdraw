package net.sf.latexdraw.instruments;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.ui.LFrame;

import org.malai.action.library.ActivateInactivateInstruments;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.TabSelected;

public class TabSelector extends Instrument {

	protected LFrame frame;


	public TabSelector(final LFrame frame) {
		super();

		if(frame==null)
			throw new IllegalArgumentException();

		this.frame = frame;
		initialiseLinks();
	}



	@Override
	protected void initialiseLinks() {
		try{
			links.add(new TabSelected2ActivateInstruments(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	private static class TabSelected2ActivateInstruments extends Link<ActivateInactivateInstruments, TabSelected, TabSelector> {

		public TabSelected2ActivateInstruments(TabSelector ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ActivateInactivateInstruments.class, TabSelected.class);
		}

		@Override
		public void initAction() {
			action.setHideWidgets(true);

			switch(interaction.getTabbedPane().getSelectedIndex()) {
				case 0: // Drawing tab.
					initActionOnDrawingPanel();
					break;
				case 1: // PST tab.
					initActionOnPSTCodePanel();
					break;
					default:
			}
		}


		public void initActionOnDrawingPanel() {
			action.addInstrumentToActivate(instrument.frame.getEditingSelector());
			action.addInstrumentToActivate(instrument.frame.getGridCustomiser());
			action.addInstrumentToActivate(instrument.frame.getPaster());
			action.addInstrumentToActivate(instrument.frame.getScaleRulersCustomiser());
			action.addInstrumentToActivate(instrument.frame.getUndoManager());
			action.addInstrumentToActivate(instrument.frame.getZoomer());
		}


		public void initActionOnPSTCodePanel() {
			action.addInstrumentToInactivate(instrument.frame.getEditingSelector());
			action.addInstrumentToInactivate(instrument.frame.getGridCustomiser());
			action.addInstrumentToInactivate(instrument.frame.getPaster());
			action.addInstrumentToInactivate(instrument.frame.getScaleRulersCustomiser());
			action.addInstrumentToInactivate(instrument.frame.getUndoManager());
			action.addInstrumentToInactivate(instrument.frame.getZoomer());
			// The deleter must be added to use the hideWidgets parameter of the action.
			action.addInstrumentToInactivate(instrument.frame.getDeleter());
		}


		@Override
		public boolean isConditionRespected() {
			return interaction.getTabbedPane()==instrument.frame.getTabbedPanel();
		}
	}
}
