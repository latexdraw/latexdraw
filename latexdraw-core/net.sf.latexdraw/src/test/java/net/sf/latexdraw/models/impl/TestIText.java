package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIText implements HelperTest {
	IText shape;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createText();
	}

	@Test
	public void testGetSetText() {
		shape.setText("bar");
		assertEquals("bar", shape.getText());
	}

	@Test
	public void testGetSetTextKO() {
		shape.setText("bar");
		shape.setText(null);
		assertEquals("bar", shape.getText());
	}

	@Theory
	public void testGetSetTextPosition(final TextPosition value) {
		shape.setTextPosition(value);
		assertEquals(value, shape.getTextPosition());
	}

	@Test
	public void testGetSetTextPositionKO() {
		shape.setTextPosition(TextPosition.BOT_RIGHT);
		shape.setTextPosition(null);
		assertEquals(TextPosition.BOT_RIGHT, shape.getTextPosition());
	}

	@Test
	public void testGetBottomLeftPoint() {
		assertEquals(shape.getBottomLeftPoint(), shape.getPosition());
	}

	@Test
	public void testMirrorHorizontal() {
		final IPoint pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testMirrorVertical() {
		final IPoint pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testCopy() {
		final IText s2 = ShapeFactory.INST.createText();
		shape.setText("foo");
		shape.setTextPosition(TextPosition.BOT_RIGHT);
		s2.copy(shape);
		assertEquals(shape.getText(), s2.getText());
		assertEquals(shape.getTextPosition(), s2.getTextPosition());
	}

	@Test
	public void testDuplicate() {
		shape.setText("foo");
		shape.setTextPosition(TextPosition.BOT_RIGHT);
		final IText dup = shape.duplicate();
		assertEquals(shape.getText(), dup.getText());
		assertEquals(shape.getTextPosition(), dup.getTextPosition());
	}

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
		assertFalse(txt.getText().isEmpty());
	}

	@Test
	public void testConstructorsOK2() {
		IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), "coucou");
		assertNotNull(txt.getText());
		assertFalse(txt.getText().isEmpty());
	}

	@Test
	public void testConstructorsOK3() {
		IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), "");
		assertNotNull(txt.getText());
		assertFalse(txt.getText().isEmpty());
	}

	@Test
	public void testConstructorsOK4() {
		IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY), "aa");
		assertEquals(ShapeFactory.INST.createPoint(), txt.getPosition());
	}
}
