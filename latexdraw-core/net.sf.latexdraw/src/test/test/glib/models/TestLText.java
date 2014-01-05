package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
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
	@Before
	public void setUp() {
		FlyweightThumbnail.images().clear();
		FlyweightThumbnail.setThread(false);
		shape  = (T) ShapeFactory.factory().createText(false);
		shape2 = (T) ShapeFactory.factory().createText(false);
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
		IText txt = ShapeFactory.factory().createText(false);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.factory().createText(true);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.factory().createText(true, ShapeFactory.factory().createPoint(), "coucou");

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.factory().createText(true, ShapeFactory.factory().createPoint(), "");

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.factory().createText(true, ShapeFactory.factory().createPoint(), null);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);

		txt = ShapeFactory.factory().createText(false, null, "aa");
		assertEquals(ShapeFactory.factory().createPoint(), txt.getPosition());
		txt = ShapeFactory.factory().createText(false, ShapeFactory.factory().createPoint(0, Double.NEGATIVE_INFINITY), "aa");
		assertEquals(ShapeFactory.factory().createPoint(), txt.getPosition());
	}
}
