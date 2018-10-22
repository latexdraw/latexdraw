package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSExchCommand extends TestPSCommand<PSExchCommand> {
	@Override
	PSExchCommand createCmd() {
		return new PSExchCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.000001);
		assertEquals(10.0, dequeue.getLast(), 0.000001);
		assertEquals(2, dequeue.size());
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-20.0);
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-20.0, dequeue.pop(), 0.000001);
		assertEquals(-10.0, dequeue.pop(), 0.000001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(20.0);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(20.0, dequeue.peek(), 0.000001);
		assertEquals(10.0, dequeue.getLast(), 0.000001);
		assertEquals(2, dequeue.size());
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}

	@Test
	void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(20.0);
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
