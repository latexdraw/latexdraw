package net.sf.latexdraw.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.latex.VerticalPosition;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestModifyLatexProperties extends TestUndoableCommand<ModifyLatexProperties, Object> {
	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object> data() {
		return Arrays.stream(LatexProperties.values()).collect(Collectors.toList());
	}

	LaTeXGenerator gen;

	@Parameterized.Parameter
	public LatexProperties property;

	// The function that provides the memento, ie the values before setting the new value
	private Supplier<Object> mementoCmd;
	// The value to set
	private Object value;

	@Override
	@Before
	public void setUp() {
		gen = new PSTCodeGenerator();

		switch(property) {
			case PACKAGES:
				mementoCmd = () -> gen.getPackages();
				value = "pkg";
				break;
			case CAPTION:
				mementoCmd = () -> gen.getCaption();
				value = "mycaption";
				break;
			case LABEL:
				mementoCmd = () -> gen.getLabel();
				value = "mylabel";
				break;
			case POSITION_HORIZONTAL:
				mementoCmd = () -> gen.isPositionHoriCentre();
				value = true;
				break;
			case POSITION_VERTICAL:
				mementoCmd = () -> gen.getPositionVertToken();
				value = VerticalPosition.FLOATS_PAGE;
				break;
			case SCALE:
				mementoCmd = () -> gen.getScale();
				value = 1.1d;
				break;
			case COMMENT:
				mementoCmd = () -> gen.getComment();
				value = "comment";
				break;
		}

		memento = mementoCmd.get();
		super.setUp();
	}

	@Override
	protected void checkUndo() {
		assertEquals(memento, mementoCmd.get());
	}

	@Override
	protected void configCorrectCmd() {
		cmd = new ModifyLatexProperties(gen, property, value);
	}

	@Override
	protected void checkDo() {
		assertEquals(value, mementoCmd.get());
	}
}
