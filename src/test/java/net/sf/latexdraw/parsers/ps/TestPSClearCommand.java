package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPSClearCommand extends TestPSCommand<PSClearCommand> {
	@Override
	protected PSClearCommand createCmd() {
		return new PSClearCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-11.2);
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(11.2);
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}
}
