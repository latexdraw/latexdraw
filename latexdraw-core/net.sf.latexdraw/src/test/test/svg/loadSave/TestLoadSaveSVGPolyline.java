package test.svg.loadSave;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;

import org.junit.Before;

public class TestLoadSaveSVGPolyline extends TestLoadSaveSVGPolygon {
	@Before
	@Override
	public void setUp() {
		shape = DrawingTK.getFactory().createPolyline(false);
	}
}
