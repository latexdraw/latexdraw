package net.sf.latexdraw.parsers.ps;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestPSCommand<T extends PSArithemticCommand> {
	T cmd;
	Deque<Double> dequeue;

	@BeforeEach
	void setUp() {
		cmd = createCmd();
		dequeue = new ArrayDeque<>();
	}

	abstract T createCmd();

	abstract void testExecuteVal0() throws InvalidFormatPSFunctionException;

	abstract void testExecuteValNeg() throws InvalidFormatPSFunctionException;

	abstract void testExecuteValPos() throws InvalidFormatPSFunctionException;

	abstract void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException;
}
