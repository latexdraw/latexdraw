package net.sf.latexdraw.actions;

import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Unit;

import static org.junit.Assert.assertEquals;

public class TestSetUnit extends TestUndoableAction<SetUnit, Unit> {
	@Override
	protected SetUnit createAction() {
		return new SetUnit();
	}

	@Override
	protected void configCorrectAction() {
		memento = ScaleRuler.getUnit();
		action.setUnit(Unit.INCH);
	}

	@Override
	protected void checkDo() {
		assertEquals(Unit.INCH, ScaleRuler.getUnit());
	}

	@Override
	protected void checkUndo() {
		assertEquals(memento, ScaleRuler.getUnit());
	}
}
