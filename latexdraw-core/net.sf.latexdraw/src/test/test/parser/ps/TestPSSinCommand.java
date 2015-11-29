package test.parser.ps;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSSinCommand;

import org.junit.Test;

public class TestPSSinCommand extends TestPSCommand<PSSinCommand> {
	@Override
	protected PSSinCommand createCmd() {
		return new PSSinCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.sin(0.0), dequeue.peek(), 0.00001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.sin(Math.toRadians(-10.0)), dequeue.peek(), 0.00001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(Math.sin(Math.toRadians(10.0)), dequeue.peek(), 0.00001);
		assertEquals(1, dequeue.size());
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}
}
