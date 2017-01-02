package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIDot;

public class TestLDot extends TestIDot<IDot> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		shape2 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
	}

	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructor1() {
		IDot dot1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());

		assertTrue(dot1.getDiametre() > 0);
		assertNotNull(dot1.getDotStyle());
		assertNotNull(dot1.getPosition());
		HelperTest.assertEqualsDouble(0., dot1.getPosition().getX());
		HelperTest.assertEqualsDouble(0., dot1.getPosition().getY());
	}

	@Test
	public void testConstructor3() {
		IDot dot1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint(-1, 2));
		HelperTest.assertEqualsDouble(-1., dot1.getPosition().getX());
		HelperTest.assertEqualsDouble(2., dot1.getPosition().getY());
	}
}
