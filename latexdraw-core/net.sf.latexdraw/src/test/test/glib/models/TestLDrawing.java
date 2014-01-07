package test.glib.models;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIDrawing;

public class TestLDrawing extends TestIDrawing {
	@Override
	@Before
	public void setUp() {
		drawing = ShapeFactory.createDrawing();
	}


	@Test
	public void testConstructor() {
		IDrawing d = ShapeFactory.createDrawing();

		assertNotNull(d.getSelection());
		assertNotNull(d.getShapes());
	}
}
