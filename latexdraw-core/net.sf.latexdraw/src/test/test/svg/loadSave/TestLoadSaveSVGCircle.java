package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ICircle;

import org.junit.Before;

public class TestLoadSaveSVGCircle extends TestLoadSaveSVGRectangularShape<ICircle> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.factory().createCircle(false);
	}
}
