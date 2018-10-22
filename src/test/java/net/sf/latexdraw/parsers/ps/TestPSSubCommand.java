package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSSubCommand extends TestPSCommand<PSSubCommand> {
	@Override
	PSSubCommand createCmd() {
		return new PSSubCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(12.1, dequeue.peek(), 0.000000000001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.1);
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-2.1, dequeue.peek(), 0.000000000001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(2.1, dequeue.peek(), 0.000000000001);
	}

	@Test
	void testExecuteValPosNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(22.1, dequeue.peek(), 0.000000000001);
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
