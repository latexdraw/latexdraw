package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSPlotXCommand extends TestPSCommand<PSPlotXVariable> {
	@Override
	protected PSPlotXVariable createCmd() {
		return new PSPlotXVariable();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, -10.0);
		assertEquals(-10.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 10.0);
		assertEquals(10.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
