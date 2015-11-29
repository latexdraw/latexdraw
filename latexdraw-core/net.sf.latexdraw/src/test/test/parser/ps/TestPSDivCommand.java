package test.parser.ps;

import static org.junit.Assert.*;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSDivCommand;

import org.junit.Test;

public class TestPSDivCommand extends TestPSCommand<PSDivCommand> {
	@Override
	protected PSDivCommand createCmd() {
		return new PSDivCommand();
	}

	@Override
	@Test(expected = ArithmeticException.class)
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = ArithmeticException.class)
	public void testExecuteVal01() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.5, dequeue.peek(), 0.000001);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteValNegPos() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-0.5, dequeue.peek(), 0.000001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(20.0);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(2.0, dequeue.peek(), 0.0);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
	}
}
