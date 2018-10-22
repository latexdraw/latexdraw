package net.sf.latexdraw.parsers.ps;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSCeilingCommand extends TestPSCommand<PSCeilingCommand> {
	@Override
	PSCeilingCommand createCmd() {
		return new PSCeilingCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0d);
		cmd.execute(dequeue, 0d);
		assertEquals(0.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-11.2);
		cmd.execute(dequeue, 0d);
		assertEquals(Math.ceil(-11.2), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(11.2);
		cmd.execute(dequeue, 0d);
		assertEquals(Math.ceil(11.2), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
