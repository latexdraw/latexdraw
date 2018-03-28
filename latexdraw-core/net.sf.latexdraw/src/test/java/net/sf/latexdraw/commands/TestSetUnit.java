package net.sf.latexdraw.commands;

import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Unit;

import static org.junit.Assert.assertEquals;

public class TestSetUnit extends TestUndoableCommand<SetUnit, Unit> {
	@Override
	protected SetUnit createCmd() {
		return new SetUnit();
	}

	@Override
	protected void configCorrectCmd() {
		memento = ScaleRuler.getUnit();
		cmd.setUnit(Unit.INCH);
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
