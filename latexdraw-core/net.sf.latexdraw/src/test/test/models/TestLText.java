package test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;

import org.junit.Before;
import org.junit.Test;

import test.models.interfaces.TestIText;

public class TestLText extends TestIText<IText> {
	@Before
	public void setUp() {
		// FlyweightThumbnail.images().clear();
		// FlyweightThumbnail.setThread(false);
		shape = ShapeFactory.INST.createText();
		shape2 = ShapeFactory.INST.createText();
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
	public void testConstructorsOK1() {
		IText txt = ShapeFactory.INST.createText();
		assertNotNull(txt.getText());
		assertTrue(txt.getText().length() > 0);
	}
	
	@Test
	public void testConstructorsOK2() {
		IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), "coucou"); //$NON-NLS-1$
		assertNotNull(txt.getText());
		assertTrue(txt.getText().length() > 0);
	}
	
	@Test
	public void testConstructorsOK3() {
		IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), ""); //$NON-NLS-1$
		assertNotNull(txt.getText());
		assertTrue(txt.getText().length() > 0);
	}
	
	@Test
	public void testConstructorsOK4() {
		IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY), "aa"); //$NON-NLS-1$
		assertEquals(ShapeFactory.INST.createPoint(), txt.getPosition());
	}
}
