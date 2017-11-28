package net.sf.latexdraw.actions;

import org.junit.Test;
import org.malai.action.Action;
import org.malai.undo.Undoable;

import static org.junit.Assert.assertNotNull;

public abstract class TestUndoableAction<T extends Action & Undoable, S> extends TestAction<T> {
	protected S memento;

	@Test
	public void testUndo() {
		configCorrectAction();
		action.doIt();
		action.undo();
		checkUndo();
	}

	@Test
	public void testRedo() {
		configCorrectAction();
		action.doIt();
		action.undo();
		action.redo();
		checkDo();
	}

	@Test
	public void testDoubleUndoThenRedo() {
		configCorrectAction();
		action.doIt();
		action.undo();
		action.redo();
		action.undo();
		action.redo();
		checkDo();
	}

	protected abstract void checkUndo();

	@Test
	public void testUndoRedoNameDefault() {
		assertNotNull(action.getUndoName());
	}

	@Test
	public void testUndoRedoNameConfigured() {
		configCorrectAction();
		assertNotNull(action.getUndoName());
	}
}
