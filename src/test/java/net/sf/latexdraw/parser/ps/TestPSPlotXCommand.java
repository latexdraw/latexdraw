package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPSPlotXCommand extends TestPSCommand<PSPlotXVariable> {
	@Override
	PSPlotXVariable createCmd() {
		return new PSPlotXVariable();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, -10.0);
		assertEquals(-10.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 10.0);
		assertEquals(10.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
