package test.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;

import org.junit.Before;

public class TestLoadSaveSVGRhombus extends TestLoadSaveSVGRectangularShape<IRhombus> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createRhombus();
	}
}
