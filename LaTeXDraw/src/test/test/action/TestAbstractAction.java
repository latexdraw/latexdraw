package test.action;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.instrument.Instrument;

public abstract class TestAbstractAction<T extends Action> extends TestCase {
	protected T action;

	@Override
	@Before
	public void setUp() {
		action = createAction();
	}

	protected abstract T createAction();

	@Test
	public abstract void testConstructor() throws Exception;

	@Test
	public abstract void testFlush() throws Exception;

	@Test
	public abstract void testDo() throws Exception;

	@Test
	public abstract void testCanDo() throws Exception;

	@Test
	public abstract void testIsRegisterable() throws Exception;

	@Test
	public abstract void testHadEffect() throws Exception;


	public class InstrumentMock extends Instrument {
		public InstrumentMock() {
			super();
		}
		@Override
		protected void initialiseLinks() {
			//
		}
	}
}
