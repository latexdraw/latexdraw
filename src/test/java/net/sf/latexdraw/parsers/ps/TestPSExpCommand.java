package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSExpCommand extends TestPSCommand<PSExpCommand> {
	@Override
	PSExpCommand createCmd() {
		return new PSExpCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(1.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(3.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-1000.0, dequeue.peek(), 0.00001);
	}

	@Test
	void testExecuteValNeg2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(-3.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.001, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(2.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(100.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
