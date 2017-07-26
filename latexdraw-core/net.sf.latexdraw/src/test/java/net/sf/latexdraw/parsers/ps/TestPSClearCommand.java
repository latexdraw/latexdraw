package net.sf.latexdraw.parsers.ps;

import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSClearCommand;

import org.junit.Test;

public class TestPSClearCommand extends TestPSCommand<PSClearCommand> {
	@Override
	protected PSClearCommand createCmd() {
		return new PSClearCommand();
	}

	@Override
	@Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-11.2);
		cmd.execute(dequeue, 0.0);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(11.2);
		cmd.execute(dequeue, 0.0);
		assertTrue(dequeue.isEmpty());
	}

	@Override
	@Test
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
		assertTrue(dequeue.isEmpty());
	}
}
