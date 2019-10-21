package net.sf.latexdraw.command;

import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.github.interacto.command.Command;
import io.github.interacto.command.CommandsRegistry;
import io.github.interacto.undo.UndoCollector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TestCommand<T extends Command> {
	protected T cmd;
	protected Drawing drawing;

	@Before
	public void setUp() {
		drawing = ShapeFactory.INST.createDrawing();
		configCorrectCmd();
	}

	@After
	public void tearDown() {
		CommandsRegistry.INSTANCE.clear();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
	}

	@Test
	public void testFlush() {
		cmd.flush();
	}

	protected abstract void configCorrectCmd();

	protected abstract void checkDo();

	@Test
	public void testDo() {
		cmd.doIt();
		checkDo();
	}

	@Test
	public void testFlushAfterExecution() {
		cmd.doIt();
		cmd.flush();
	}

	@Test
	public void testCanDo() {
		assertTrue(cmd.canDo());
	}

	@Test
	public void testGetRegisterablePolicy() {
		assertEquals(Command.RegistrationPolicy.NONE, cmd.getRegistrationPolicy());
	}

	@Test
	public void testGetRegisterablePolicyOK() {
		cmd.doIt();
		cmd.done();
		assertEquals(Command.RegistrationPolicy.LIMITED, cmd.getRegistrationPolicy());
	}

	@Test
	public void testHadEffect() {
		cmd.doIt();
		cmd.done();
		assertTrue(cmd.hadEffect());
	}
}
