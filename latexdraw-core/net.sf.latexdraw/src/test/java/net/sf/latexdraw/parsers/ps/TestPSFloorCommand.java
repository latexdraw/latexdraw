package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSFloorCommand extends TestPSCommand<PSFloorCommand> {
	@Override
	protected PSFloorCommand createCmd() {
		return new PSFloorCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.3);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(-12.3), dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteValNeg2() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.8);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(-12.8), dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.1);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(10.1), dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteValPos2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.9);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(10.9), dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}
}
