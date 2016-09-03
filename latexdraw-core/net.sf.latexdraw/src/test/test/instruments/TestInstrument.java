package test.instruments;

import org.junit.Test;
import org.malai.instrument.Instrument;
import org.malai.instrument.Interactor;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public abstract class TestInstrument<T extends Instrument> {
	protected T instrument;

	@Test
	public void testNotNoLink() {
		assertTrue(instrument.getNbInteractors()==0);
		instrument.setActivated(true);
		assertTrue(instrument.getNbInteractors()>0);
	}


	@Test
	public void testActivation() {
		instrument.setActivated(true);
		assertTrue(instrument.isActivated());
		instrument.setActivated(false);
		assertFalse(instrument.isActivated());
	}


	public Interactor getLink(final String nameClassInteractor) {
		Interactor link = null;
		List<Interactor> interactors = instrument.getInteractors();

		for(int i=0; i<interactors.size() && link==null; i++)
			if(interactors.get(i).getClass().getName().endsWith(nameClassInteractor))
				link = interactors.get(i);

		return link;
	}
}
