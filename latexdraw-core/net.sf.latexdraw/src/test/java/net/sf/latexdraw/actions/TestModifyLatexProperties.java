package net.sf.latexdraw.actions;

import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.Before;

public class TestModifyLatexProperties extends TestUndoableAction<ModifyLatexProperties, Object> {
	LaTeXGenerator gen;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		gen = new PSTCodeGenerator();
	}

	@Override
	protected void checkUndo() {

	}

	@Override
	protected ModifyLatexProperties createAction() {
		return new ModifyLatexProperties();
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
