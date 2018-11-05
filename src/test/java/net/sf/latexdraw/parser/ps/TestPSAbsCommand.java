package net.sf.latexdraw.parser.ps;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSAbsCommand extends TestPSCommand<PSAbsCommand> {
	@Override
	PSAbsCommand createCmd() {
		return new PSAbsCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		cmd.execute(dequeue, 0.0001);
		assertEquals(0d, dequeue.peek(), 0.0001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		cmd.execute(dequeue, 0.0001);
		assertEquals(10d, dequeue.peek(), 0.0001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		cmd.execute(dequeue, 0.0001);
		assertEquals(10d, dequeue.peek(), 0.0001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
