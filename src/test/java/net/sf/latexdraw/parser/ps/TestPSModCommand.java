package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSModCommand extends TestPSCommand<PSModCommand> {
	@Override
	PSModCommand createCmd() {
		return new PSModCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		assertThrows(ArithmeticException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(2.0);
		dequeue.push(0.0);
		assertThrows(ArithmeticException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-9.0);
		dequeue.push(2.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-1.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(9.0);
		dequeue.push(2.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(1.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(9.0);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
