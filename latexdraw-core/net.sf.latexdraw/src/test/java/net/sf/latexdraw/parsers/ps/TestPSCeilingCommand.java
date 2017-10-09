package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSCeilingCommand extends TestPSCommand<PSCeilingCommand> {
	@Override
	protected PSCeilingCommand createCmd() {
		return new PSCeilingCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.0);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-11.2);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.ceil(-11.2), dequeue.peek(), 0.0);
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(11.2);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.ceil(11.2), dequeue.peek(), 0.0);
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}
}
