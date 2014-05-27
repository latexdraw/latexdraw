package test.parser.ps;

import java.util.ArrayDeque;
import java.util.Deque;

import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSArithemticCommand;

import org.junit.Before;
import org.junit.Test;

public abstract class TestPSCommand<T extends PSArithemticCommand> {
	T cmd;
	Deque<Double> dequeue;

	@Before public void setUp() throws Exception {
		cmd = createCmd();
		dequeue = new ArrayDeque<>();
	}



	protected abstract T createCmd();

	@Test(expected=InvalidFormatPSFunctionException.class)
	public void testExecuteNullDequeue() throws InvalidFormatPSFunctionException {
		cmd.execute(null, 0.0);
	}

	@Test public abstract void testExecuteVal0() throws InvalidFormatPSFunctionException;

	@Test public abstract void testExecuteValNeg() throws InvalidFormatPSFunctionException;

	@Test public abstract void testExecuteValPos() throws InvalidFormatPSFunctionException;

	@Test public abstract void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException;
}
