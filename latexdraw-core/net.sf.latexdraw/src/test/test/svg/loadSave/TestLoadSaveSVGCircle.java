package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;

import org.junit.Before;

public class TestLoadSaveSVGCircle extends TestLoadSaveSVGSquaredShape<ICircle> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createCircle();
	}
}
