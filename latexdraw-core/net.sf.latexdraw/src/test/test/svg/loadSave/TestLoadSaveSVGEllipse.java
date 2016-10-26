package test.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;

import org.junit.Before;

public class TestLoadSaveSVGEllipse extends TestLoadSaveSVGRectangularShape<IEllipse> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createEllipse();
	}
}
