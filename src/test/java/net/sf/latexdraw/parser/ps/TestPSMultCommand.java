package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSMultCommand extends TestPSCommand<PSMulCommand> {
	@Override
	PSMulCommand createCmd() {
		return new PSMulCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.00001);
	}

	@Test
	void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(200.0, dequeue.peek(), 0.00001);
	}

	@Test
	void testExecuteValNeg2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-200.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(5.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(50.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
