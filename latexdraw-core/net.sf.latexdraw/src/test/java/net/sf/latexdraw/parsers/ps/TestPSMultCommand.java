package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSMultCommand extends TestPSCommand<PSMulCommand> {
	@Override
	protected PSMulCommand createCmd() {
		return new PSMulCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(200.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteValNeg2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-200.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(5.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(50.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
	}
}
