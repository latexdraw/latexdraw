package net.sf.latexdraw.parser.ps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSFloorCommand extends TestPSCommand<PSFloorCommand> {
	@Override
	PSFloorCommand createCmd() {
		return new PSFloorCommand();
	}

	@Override
	@Test
	void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0, dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.3);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(-12.3), dequeue.peek(), 0.00001);
	}

	@Test
	void testExecuteValNeg2() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.8);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(-12.8), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.1);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(10.1), dequeue.peek(), 0.00001);
	}

	@Test
	void testExecuteValPos2() throws InvalidFormatPSFunctionException {
		dequeue.push(10.9);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.floor(10.9), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		assertThrows(InvalidFormatPSFunctionException.class, () -> cmd.execute(dequeue, 0d));
	}
}
