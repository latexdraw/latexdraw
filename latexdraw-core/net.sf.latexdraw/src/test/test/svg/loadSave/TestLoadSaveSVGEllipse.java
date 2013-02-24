package test.svg.loadSave;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;

import org.junit.Before;

public class TestLoadSaveSVGEllipse extends TestLoadSaveSVGRectangularShape<IEllipse> {
	@Before
	@Override
	public void setUp() {
		shape = DrawingTK.getFactory().createEllipse(false);
	}
}
