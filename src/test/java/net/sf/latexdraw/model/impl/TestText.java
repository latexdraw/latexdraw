package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.TextPosition;
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
public class TestText implements HelperTest {
	Text shape;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createText();
	}

	@Test
	public void testGetSetText() {
		shape.setText("bar");
		assertEquals("bar", shape.getText());
	}

	@Theory
	public void testGetSetTextPosition(final TextPosition value) {
		shape.setTextPosition(value);
		assertEquals(value, shape.getTextPosition());
	}

	@Test
	public void testGetBottomLeftPoint() {
		assertEquals(shape.getBottomLeftPoint(), shape.getPosition());
	}

	@Test
	public void testMirrorHorizontal() {
		final Point pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testMirrorVertical() {
		final Point pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testCopy() {
		final Text s2 = ShapeFactory.INST.createText();
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
		final Text dup = shape.duplicate();
		assertEquals(shape.getText(), dup.getText());
		assertEquals(shape.getTextPosition(), dup.getTextPosition());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(Text.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructorsOK1() {
		final Text txt = ShapeFactory.INST.createText();
		assertNotNull(txt.getText());
		assertFalse(txt.getText().isEmpty());
	}

	@Test
	public void testConstructorsOK2() {
		final Text txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), "coucou");
		assertNotNull(txt.getText());
		assertFalse(txt.getText().isEmpty());
	}

	@Test
	public void testConstructorsOK3() {
		final Text txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), "");
		assertNotNull(txt.getText());
		assertFalse(txt.getText().isEmpty());
	}

	@Test
	public void testConstructorsOK4() {
		final Text txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY), "aa");
		assertEquals(ShapeFactory.INST.createPoint(), txt.getPosition());
	}

	@Test
	public void testTextPropNotNull() {
		assertNotNull(shape.textProperty());
	}
}
