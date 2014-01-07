package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;

import org.junit.Before;

public class TestLoadSaveSVGPolyline extends TestLoadSaveSVGPolygon {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.createPolyline(false);
	}
}
