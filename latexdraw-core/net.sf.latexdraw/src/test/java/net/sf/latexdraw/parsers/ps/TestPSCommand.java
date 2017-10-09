package net.sf.latexdraw.parsers.ps;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.Before;
import org.junit.Test;

public abstract class TestPSCommand<T extends PSArithemticCommand> {
	T cmd;
	Deque<Double> dequeue = new ArrayDeque<>();

	@Before
	public void setUp() throws Exception {
		cmd = createCmd();
		dequeue = new ArrayDeque<>();
	}

	protected abstract T createCmd();

	@Test
	public abstract void testExecuteVal0() throws InvalidFormatPSFunctionException;

	@Test
	public abstract void testExecuteValNeg() throws InvalidFormatPSFunctionException;

	@Test
	public abstract void testExecuteValPos() throws InvalidFormatPSFunctionException;

	@Test
	public abstract void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException;
}
