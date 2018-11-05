package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPSCountCommand extends TestPSCommand<PSCountCommand> {
	@Override
	PSCountCommand createCmd() {
		return new PSCountCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0d);
		assertEquals(0d, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		cmd.execute(dequeue, 0d);
		assertEquals(1d, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
		assertEquals(2d, dequeue.peek(), 0.00001);
		assertEquals(3, dequeue.size());
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
