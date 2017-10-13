package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSCountCommand extends TestPSCommand<PSCountCommand> {
	@Override
	protected PSCountCommand createCmd() {
		return new PSCountCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0d);
		assertEquals(0d, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		cmd.execute(dequeue, 0d);
		assertEquals(1d, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
		assertEquals(2d, dequeue.peek(), 0.00001);
		assertEquals(3, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
