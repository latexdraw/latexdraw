package net.sf.latexdraw.command;

import net.sf.latexdraw.service.PreferencesService;
import org.junit.Before;
import org.junit.Test;
import org.malai.command.Command;
import org.malai.undo.Undoable;

import static org.junit.Assert.assertNotNull;

public abstract class TestUndoableCommand<T extends Command & Undoable, S> extends TestCommand<T> {
	protected S memento;
	PreferencesService prefs;

	@Override
	@Before
	public void setUp() {
		prefs = new PreferencesService();
		super.setUp();
	}

	@Test
	public void testUndo() {
		cmd.doIt();
		cmd.undo();
		checkUndo();
	}

	@Test
	public void testRedo() {
		cmd.doIt();
		cmd.undo();
		cmd.redo();
		checkDo();
	}

	@Test
	public void testDoubleUndoThenRedo() {
		cmd.doIt();
		cmd.undo();
		cmd.redo();
		cmd.undo();
		cmd.redo();
		checkDo();
	}

	protected abstract void checkUndo();

	@Test
	public void testUndoRedoNameConfigured() {
		assertNotNull(cmd.getUndoName(prefs.getBundle()));
	}
}
