package net.sf.latexdraw.view.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import org.junit.Before;

public class TestLoadSaveSVGPolyline extends TestLoadSaveSVGPolygon {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.INST.createPolyline();
	}
}
