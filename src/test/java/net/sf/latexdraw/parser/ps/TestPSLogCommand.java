package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSLogCommand extends TestPSCommand<PSLogCommand> {
	@Override
	PSLogCommand createCmd() {
		return new PSLogCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
		assertEquals(Math.log10(0.0), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		cmd.execute(dequeue, 0d);
		assertEquals(Math.log10(-10.0), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(11.1d);
		cmd.execute(dequeue, 0d);
		assertEquals(Math.log10(11.1), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
