package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPSValueCommand extends TestPSCommand<PSValue> {
	@Override
	PSValue createCmd() {
		return new PSValue(2.0);
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd = new PSValue(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		cmd = new PSValue(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-10.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		cmd = new PSValue(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(10.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
