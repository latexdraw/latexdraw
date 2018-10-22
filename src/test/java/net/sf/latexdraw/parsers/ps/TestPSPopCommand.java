package net.sf.latexdraw.parsers.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSPopCommand extends TestPSCommand<PSPopCommand> {
	@Override
	PSPopCommand createCmd() {
		return new PSPopCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0, dequeue.size());
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-10.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(20.0);
		dequeue.push(30.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(20.0, dequeue.peek(), 0.00001);
		assertEquals(2, dequeue.size());
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
