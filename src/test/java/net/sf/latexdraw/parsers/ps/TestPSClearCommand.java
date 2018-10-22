package net.sf.latexdraw.parsers.ps;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPSClearCommand extends TestPSCommand<PSClearCommand> {
	@Override
	PSClearCommand createCmd() {
		return new PSClearCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-11.2);
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(11.2);
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0d);
		assertTrue(dequeue.isEmpty());
	}
}
