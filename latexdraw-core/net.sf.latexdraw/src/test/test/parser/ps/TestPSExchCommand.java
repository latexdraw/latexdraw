package test.parser.ps;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSExchCommand;

import org.junit.Test;

public class TestPSExchCommand extends TestPSCommand<PSExchCommand> {
	@Override protected PSExchCommand createCmd() { return new PSExchCommand(); }

	@Override @Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0,dequeue.peek(),0.0);
		assertEquals(10.0,dequeue.getLast(),0.0);
		assertEquals(2,dequeue.size());
	}

	@Override @Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-20.0);
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-20.0,dequeue.pop(),0.0);
		assertEquals(-10.0,dequeue.pop(),0.0);
	}

	@Override @Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(20.0);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(20.0,dequeue.peek(),0.0);
		assertEquals(10.0,dequeue.getLast(),0.0);
		assertEquals(2,dequeue.size());
	}

	@Override @Test(expected=InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected=InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(20.0);
		cmd.execute(dequeue, 0.0);
	}
}
