package test.svg.loadSave;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ISquare;

import org.junit.Before;

public class TestLoadSaveSVGSquare extends TestLoadSaveSVGRectangularShape<ISquare> {
	@Before
	@Override
	public void setUp() {
		shape = DrawingTK.getFactory().createSquare(false);
	}
}
