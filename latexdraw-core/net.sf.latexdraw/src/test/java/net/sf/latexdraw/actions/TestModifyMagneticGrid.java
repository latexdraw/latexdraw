package net.sf.latexdraw.actions;

public class TestModifyMagneticGrid extends TestUndoableAction<ModifyMagneticGrid, Object> {
	@Override
	protected void checkUndo() {

	}

	@Override
	protected ModifyMagneticGrid createAction() {
		return new ModifyMagneticGrid();
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
}
