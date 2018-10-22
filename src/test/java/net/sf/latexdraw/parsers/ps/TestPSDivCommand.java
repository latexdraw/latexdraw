package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSDivCommand extends TestPSCommand<PSDivCommand> {
	@Override
	PSDivCommand createCmd() {
		return new PSDivCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		dequeue.push(0d);
		assertThrows(ArithmeticException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		dequeue.push(0d);
		assertThrows(ArithmeticException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		dequeue.push(-20d);
		cmd.execute(dequeue, 0d);
		assertEquals(0.5, dequeue.peek(), 0.000001);
	}

	@Test
	void testExecuteValNegPos() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		dequeue.push(20d);
		cmd.execute(dequeue, 0d);
		assertEquals(-0.5, dequeue.peek(), 0.000001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(20d);
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
		assertEquals(2d, dequeue.peek(), 0.000001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
