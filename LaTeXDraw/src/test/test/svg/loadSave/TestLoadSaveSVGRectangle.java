package test.svg.loadSave;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;

import org.junit.Before;

public class TestLoadSaveSVGRectangle extends TestLoadSaveSVGRectangularShape<IRectangle> {
	@Before
	@Override
	public void setUp() {
		shape = DrawingTK.getFactory().createRectangle(false);
	}


	public void testFrameArc() {
		shape.setThickness(10);
		shape.setLineArc(0.3);
		setDefaultDimensions();
		compareShapes(generateShape());
	}
}
