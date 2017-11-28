package net.sf.latexdraw.actions;

import net.sf.latexdraw.util.Unit;

public class TestSetUnit extends TestUndoableAction<SetUnit, Unit> {
	@Override
	protected SetUnit createAction() {
		return new SetUnit();
	}

	@Override
	protected void configCorrectAction() {

	}

	@Override
	protected void checkDo() {

	}

	@Override
	public void testIsRegisterable() throws Exception {

	}

	@Override
	protected void checkUndo() {

	}
}
