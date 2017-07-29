package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSExpCommand extends TestPSCommand<PSExpCommand> {
	@Override
	protected PSExpCommand createCmd() {
		return new PSExpCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(1.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(3.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-1000.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteValNeg2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(-3.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.001, dequeue.peek(), 0.00001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(2.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(100.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
	}
}
