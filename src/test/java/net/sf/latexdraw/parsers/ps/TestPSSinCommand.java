package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSSinCommand extends TestPSCommand<PSSinCommand> {
	@Override
	PSSinCommand createCmd() {
		return new PSSinCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.sin(0.0), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.sin(Math.toRadians(-10.0)), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.sin(Math.toRadians(10.0)), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
