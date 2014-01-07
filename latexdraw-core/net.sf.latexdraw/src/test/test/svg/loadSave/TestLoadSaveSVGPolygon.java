package test.svg.loadSave;

import org.junit.Before;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;

public class TestLoadSaveSVGPolygon extends TestLoadSaveSVGModifiablePointsShape<IPolygon> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.createPolygon(false);
	}
}
