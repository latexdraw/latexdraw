package net.sf.latexdraw.view.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

import org.junit.Before;

public class TestLoadSaveSVGTriangle extends TestLoadSaveSVGRectangularShape<ITriangle> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createTriangle();
	}
}
