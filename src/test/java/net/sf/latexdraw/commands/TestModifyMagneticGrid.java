package net.sf.latexdraw.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGridImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.malai.command.CommandsRegistry;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestModifyMagneticGrid extends TestUndoableCommand<ModifyMagneticGrid, Object> {
	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object> data() {
		return Arrays.stream(GridProperties.values()).collect(Collectors.toList());
	}

	@Parameterized.Parameter
	public GridProperties property;

	MagneticGrid grid;

	// The function that provides the memento, ie the values before setting the new value
	private Supplier<Object> mementoCmd;
	// The value to set
	private Object value;


	@Override
	@Before
	public void setUp() {
		grid = new MagneticGridImpl(new Canvas(), new SystemService());
		// Cannot have two runners so cannot use mock to mock Canvas:
		CommandsRegistry.INSTANCE.removeAllHandlers();

		switch(property) {
			case STYLE:
				mementoCmd = () -> grid.getGridStyle();
				value = GridStyle.STANDARD;
				break;
			case MAGNETIC:
				mementoCmd = () -> grid.isMagnetic();
				value = true;
				break;
			case GRID_SPACING:
				mementoCmd = () -> grid.getGridSpacing();
				value = 11;
				break;
		}

		memento = mementoCmd.get();
		super.setUp();
	}

	@Override
	protected void configCorrectCmd() {
		cmd = new ModifyMagneticGrid(property, value, grid);
	}

	@Override
	protected void checkDo() {
		assertEquals(value, mementoCmd.get());
	}

	@Override
	protected void checkUndo() {
		assertEquals(memento, mementoCmd.get());
	}
}
