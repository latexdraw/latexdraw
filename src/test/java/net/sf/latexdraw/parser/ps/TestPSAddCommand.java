package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSAddCommand extends TestPSCommand<PSAddCommand> {
	@Override
	PSAddCommand createCmd() {
		return new PSAddCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
		assertEquals(12.1, dequeue.peek(), 0.000000000001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.1);
		dequeue.push(-10d);
		cmd.execute(dequeue, 0d);
		assertEquals(-22.1, dequeue.peek(), 0.000000000001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
		assertEquals(22.1, dequeue.peek(), 0.000000000001);
	}

	@Test
	void testExecuteValPosNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(-10d);
		cmd.execute(dequeue, 0d);
		assertEquals(2.1, dequeue.peek(), 0.000000000001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
