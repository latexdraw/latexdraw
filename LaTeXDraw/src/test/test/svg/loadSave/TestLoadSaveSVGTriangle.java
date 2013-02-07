package test.svg.loadSave;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;

import org.junit.Before;

public class TestLoadSaveSVGTriangle extends TestLoadSaveSVGRectangularShape<ITriangle> {
	@Before
	@Override
	public void setUp() {
		shape = DrawingTK.getFactory().createTriangle(false);
	}
}
