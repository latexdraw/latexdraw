package test.parser.ps;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSCountCommand;

import org.junit.Test;

public class TestPSCountCommand extends TestPSCommand<PSCountCommand> {
	@Override protected PSCountCommand createCmd() { return new PSCountCommand(); }

	@Override @Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
		assertEquals(0.0,dequeue.peek(),0.0);
		assertEquals(1, dequeue.size());
	}

	@Override @Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(1.0,dequeue.peek(),0.0);
		assertEquals(2, dequeue.size());
	}

	@Override @Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(10.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(2.0,dequeue.peek(),0.0);
		assertEquals(3, dequeue.size());
	}

	@Override @Test
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		// Nothing to do.
	}
}
