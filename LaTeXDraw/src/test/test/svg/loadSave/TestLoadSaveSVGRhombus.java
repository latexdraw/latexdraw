package test.svg.loadSave;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;

import org.junit.Before;

public class TestLoadSaveSVGRhombus extends TestLoadSaveSVGRectangularShape<IRhombus> {
	@Before
	@Override
	public void setUp() {
		shape = DrawingTK.getFactory().createRhombus(false);
	}
}
