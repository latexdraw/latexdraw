package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSIDivCommand extends TestPSCommand<PSIDivCommand> {
	@Override
	PSIDivCommand createCmd() {
		return new PSIDivCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(0.0);
		assertThrows(ArithmeticException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteVal02() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		assertThrows(ArithmeticException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.1);
		dequeue.push(-5.2);
		cmd.execute(dequeue, 0.0);
		assertEquals((int) (-10.1 / -5.2), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.1);
		dequeue.push(5.3);
		cmd.execute(dequeue, 0.0);
		assertEquals((int) (10.1 / 5.3), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10.1);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
