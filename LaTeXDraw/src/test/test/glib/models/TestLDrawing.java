package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIDrawing;

public class TestLDrawing extends TestIDrawing {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		drawing = DrawingTK.getFactory().createDrawing();
	}


	@Test
	public void testConstructor() {
		IDrawing d = DrawingTK.getFactory().createDrawing();

		assertNotNull(d.getSelection());
		assertNotNull(d.getShapes());
	}
}
