package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSDivCommand extends TestPSCommand<PSDivCommand> {
	@Override
	protected PSDivCommand createCmd() {
		return new PSDivCommand();
	}

	@Override
	@Test(expected = ArithmeticException.class)
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
	}

	@Test(expected = ArithmeticException.class)
	public void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		dequeue.push(-20d);
		cmd.execute(dequeue, 0d);
		assertEquals(0.5, dequeue.peek(), 0.000001);
	}

	@Test
	public void testExecuteValNegPos() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		dequeue.push(20d);
		cmd.execute(dequeue, 0d);
		assertEquals(-0.5, dequeue.peek(), 0.000001);
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(20d);
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
		assertEquals(2d, dequeue.peek(), 0.000001);
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0d);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
	}
}
