package net.sf.latexdraw.commands;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.malai.command.Command;
import org.malai.command.CommandsRegistry;
import org.malai.undo.UndoCollector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TestCommand<T extends Command> {
	protected T cmd;
	protected IDrawing drawing;

	@Before
	public void setUp() {
		drawing = ShapeFactory.INST.createDrawing();
		cmd = createCmd();
	}

	@After
	public void tearDown() {
		CommandsRegistry.INSTANCE.clear();
		CommandsRegistry.INSTANCE.removeAllHandlers();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
	}

	protected abstract T createCmd();

	@Test
	public void testFlush() {
		cmd.flush();
	}

	protected abstract void configCorrectCmd();

	protected abstract void checkDo();

	@Test
	public void testDo() {
		configCorrectCmd();
		cmd.doIt();
		checkDo();
	}

	@Test
	public void testFlushAfterExecution() {
		configCorrectCmd();
		cmd.doIt();
		cmd.flush();
	}

	@Test
	public void testCanDo() {
		configCorrectCmd();
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
		configCorrectCmd();
		cmd.doIt();
		cmd.done();
		assertTrue(cmd.hadEffect());
	}
}
