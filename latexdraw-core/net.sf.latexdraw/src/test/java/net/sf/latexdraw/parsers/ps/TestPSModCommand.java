package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSModCommand extends TestPSCommand<PSModCommand> {
	@Override
	protected PSModCommand createCmd() {
		return new PSModCommand();
	}

	@Override
	@Test(expected = ArithmeticException.class)
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = ArithmeticException.class)
	public void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(2.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-9.0);
		dequeue.push(2.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-1.0, dequeue.peek(), 0.0);
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(9.0);
		dequeue.push(2.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(1.0, dequeue.peek(), 0.0);
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(9.0);
		cmd.execute(dequeue, 0.0);
	}
}
