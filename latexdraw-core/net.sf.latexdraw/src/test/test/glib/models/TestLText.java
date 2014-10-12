package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIText;

public class TestLText extends TestIText<IText> {
	@Before
	public void setUp() {
		FlyweightThumbnail.images().clear();
		FlyweightThumbnail.setThread(false);
		shape  = ShapeFactory.createText();
		shape2 = ShapeFactory.createText();
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
		IText txt = ShapeFactory.createText();

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.createText();

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.createText(ShapeFactory.createPoint(), "coucou"); //$NON-NLS-1$

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.createText(ShapeFactory.createPoint(), ""); //$NON-NLS-1$

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);
		txt = ShapeFactory.createText(ShapeFactory.createPoint(), null);

		assertNotNull(txt.getText());
		assertTrue(txt.getText().length()>0);

		txt = ShapeFactory.createText(null, "aa"); //$NON-NLS-1$
		assertEquals(ShapeFactory.createPoint(), txt.getPosition());
		txt = ShapeFactory.createText(ShapeFactory.createPoint(0, Double.NEGATIVE_INFINITY), "aa"); //$NON-NLS-1$
		assertEquals(ShapeFactory.createPoint(), txt.getPosition());
	}
}
