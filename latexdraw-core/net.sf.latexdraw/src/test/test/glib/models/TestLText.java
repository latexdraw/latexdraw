package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIText;

public class TestLText<T extends IText> extends TestIText<T> {
	@Override
	@Before
	public void setUp() {
		FlyweightThumbnail.images().clear();
		FlyweightThumbnail.setThread(false);
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createText(false);
		shape2 = (T) DrawingTK.getFactory().createText(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IText.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		IText txt = DrawingTK.getFactory().createText(false);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = DrawingTK.getFactory().createText(true);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = DrawingTK.getFactory().createText(true, DrawingTK.getFactory().createPoint(), "coucou");

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = DrawingTK.getFactory().createText(true, DrawingTK.getFactory().createPoint(), "");

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = DrawingTK.getFactory().createText(true, DrawingTK.getFactory().createPoint(), null);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);

		txt = DrawingTK.getFactory().createText(false, null, "aa");
		assertEquals(DrawingTK.getFactory().createPoint(), txt.getPosition());
		txt = DrawingTK.getFactory().createText(false, DrawingTK.getFactory().createPoint(0, Double.NEGATIVE_INFINITY), "aa");
		assertEquals(DrawingTK.getFactory().createPoint(), txt.getPosition());
	}
}
