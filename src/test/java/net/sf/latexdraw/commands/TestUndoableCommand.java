package net.sf.latexdraw.commands;

import org.junit.Test;
import org.malai.command.Command;
import org.malai.undo.Undoable;

import static org.junit.Assert.assertNotNull;

public abstract class TestUndoableCommand<T extends Command & Undoable, S> extends TestCommand<T> {
	protected S memento;

	@Test
	public void testUndo() {
		configCorrectCmd();
		cmd.doIt();
		cmd.undo();
		checkUndo();
	}

	@Test
	public void testRedo() {
		configCorrectCmd();
		cmd.doIt();
		cmd.undo();
		cmd.redo();
		checkDo();
	}

	@Test
	public void testDoubleUndoThenRedo() {
		configCorrectCmd();
		cmd.doIt();
		cmd.undo();
		cmd.redo();
		cmd.undo();
		cmd.redo();
		checkDo();
	}

	protected abstract void checkUndo();

	@Test
	public void testUndoRedoNameDefault() {
		assertNotNull(cmd.getUndoName());
	}

	@Test
	public void testUndoRedoNameConfigured() {
		configCorrectCmd();
		assertNotNull(cmd.getUndoName());
	}
}
