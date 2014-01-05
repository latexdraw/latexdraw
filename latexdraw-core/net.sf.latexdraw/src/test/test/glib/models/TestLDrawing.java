package test.glib.models;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIDrawing;

public class TestLDrawing extends TestIDrawing {
	@Override
	@Before
	public void setUp() {
		drawing = ShapeFactory.factory().createDrawing();
	}


	@Test
	public void testConstructor() {
		IDrawing d = ShapeFactory.factory().createDrawing();

		assertNotNull(d.getSelection());
		assertNotNull(d.getShapes());
	}
}
