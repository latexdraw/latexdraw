package net.sf.latexdraw.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.view.latex.VerticalPosition;
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

	LaTeXDataService data;

	@Parameterized.Parameter
	public LatexProperties property;

	// The function that provides the memento, ie the values before setting the new value
	private Supplier<Object> mementoCmd;
	// The value to set
	private Object value;

	@Override
	@Before
	public void setUp() {
		data = new LaTeXDataService();

		switch(property) {
			case PACKAGES:
				mementoCmd = () -> data.getPackages();
				value = "pkg";
				break;
			case CAPTION:
				mementoCmd = () -> data.getCaption();
				value = "mycaption";
				break;
			case LABEL:
				mementoCmd = () -> data.getLabel();
				value = "mylabel";
				break;
			case POSITION_HORIZONTAL:
				mementoCmd = () -> data.isPositionHoriCentre();
				value = true;
				break;
			case POSITION_VERTICAL:
				mementoCmd = () -> data.getPositionVertToken();
				value = VerticalPosition.FLOATS_PAGE;
				break;
			case SCALE:
				mementoCmd = () -> data.getScale();
				value = 1.1d;
				break;
			case COMMENT:
				mementoCmd = () -> data.getComment();
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
		cmd = new ModifyLatexProperties(data, property, value);
	}

	@Override
	protected void checkDo() {
		assertEquals(value, mementoCmd.get());
	}
}
