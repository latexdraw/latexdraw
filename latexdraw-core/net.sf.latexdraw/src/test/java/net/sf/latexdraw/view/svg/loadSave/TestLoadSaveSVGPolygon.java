package net.sf.latexdraw.view.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import org.junit.Before;

public class TestLoadSaveSVGPolygon extends TestLoadSaveSVGModifiablePointsShape<IPolygon> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPolygon();
	}
}
