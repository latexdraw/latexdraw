package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IRhombus;

import org.junit.Before;

public class TestLoadSaveSVGRhombus extends TestLoadSaveSVGRectangularShape<IRhombus> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.createRhombus(false);
	}
}
