package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare;

import org.junit.Before;

public class TestLoadSaveSVGSquare extends TestLoadSaveSVGRectangularShape<ISquare> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.createSquare(false);
	}
}
