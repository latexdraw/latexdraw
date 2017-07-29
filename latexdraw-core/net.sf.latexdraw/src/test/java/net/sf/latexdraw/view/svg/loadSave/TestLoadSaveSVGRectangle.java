package net.sf.latexdraw.view.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.junit.Before;
import org.junit.Test;

public class TestLoadSaveSVGRectangle extends TestLoadSaveSVGRectangularShape<IRectangle> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createRectangle();
	}

	@Test
	public void testFrameArc() {
		shape.setThickness(10);
		shape.setLineArc(0.3);
		setDefaultDimensions();
		compareShapes(generateShape());
	}
}
