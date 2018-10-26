package net.sf.latexdraw.commands;

import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Unit;

import static org.junit.Assert.assertEquals;

public class TestSetUnit extends TestUndoableCommand<SetUnit, Unit> {
	@Override
	protected void configCorrectCmd() {
		cmd = new SetUnit(Unit.INCH);
		memento = ScaleRuler.getUnit();
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
