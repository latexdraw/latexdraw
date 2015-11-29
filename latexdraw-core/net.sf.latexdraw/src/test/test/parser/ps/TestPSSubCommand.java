package test.parser.ps;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSSubCommand;

import org.junit.Test;

public class TestPSSubCommand extends TestPSCommand<PSSubCommand> {
	@Override
	protected PSSubCommand createCmd() {
		return new PSSubCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(12.1, dequeue.peek(), 0.000000000001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-12.1);
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-2.1, dequeue.peek(), 0.000000000001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(2.1, dequeue.peek(), 0.000000000001);
		assertEquals(1, dequeue.size());
	}

	@Test
	public void testExecuteValPosNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(22.1, dequeue.peek(), 0.000000000001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(12.1);
		cmd.execute(dequeue, 0.0);
	}
}
