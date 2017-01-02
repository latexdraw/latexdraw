package test.svg.loadSave;

import org.junit.Before;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;

public class TestLoadSaveSVGPolygon extends TestLoadSaveSVGModifiablePointsShape<IPolygon> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPolygon();
	}
}
