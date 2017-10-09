package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSValueCommand extends TestPSCommand<PSValue> {
	@Override
	protected PSValue createCmd() {
		return new PSValue(2.0);
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd = new PSValue(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.0);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		cmd = new PSValue(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-10.0, dequeue.peek(), 0.0);
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		cmd = new PSValue(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(10.0, dequeue.peek(), 0.0);
	}

	@Override
	@Test
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
