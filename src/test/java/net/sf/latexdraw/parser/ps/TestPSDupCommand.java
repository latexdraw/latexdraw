package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSDupCommand extends TestPSCommand<PSDupCommand> {
	@Override
	PSDupCommand createCmd() {
		return new PSDupCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
		assertEquals(0d, dequeue.peek(), 0.000001);
		assertEquals(2, dequeue.size());
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10d);
		cmd.execute(dequeue, 0d);
		assertEquals(-10d, dequeue.peek(), 0.000001);
		assertEquals(2, dequeue.size());
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10d);
		cmd.execute(dequeue, 0d);
		assertEquals(10d, dequeue.peek(), 0.000001);
		assertEquals(2, dequeue.size());
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
