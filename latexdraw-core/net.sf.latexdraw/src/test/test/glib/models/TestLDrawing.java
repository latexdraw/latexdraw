package test.glib.models;

import static org.junit.Assert.assertNotNull;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIDrawing;

public class TestLDrawing extends TestIDrawing {
	@Before
	public void setUp() {
		drawing = ShapeFactory.INST.createDrawing();
	}

	@Test
	public void testConstructor() {
		IDrawing d = ShapeFactory.INST.createDrawing();

		assertNotNull(d.getSelection());
		assertNotNull(d.getShapes());
	}
}
