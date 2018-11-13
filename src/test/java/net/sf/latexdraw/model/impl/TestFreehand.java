package net.sf.latexdraw.model.impl;

import java.util.Collections;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestFreehand implements HelperTest {
	Freehand shape;
	Freehand shape2;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createFreeHand(Collections.emptyList());
		shape2 = ShapeFactory.INST.createFreeHand(Collections.emptyList());
	}

	@Theory
	public void testSetGetType(final FreeHandStyle style) {
		shape.setType(style);
		assertEquals(style, shape.getType());
	}

	@Theory
	public void testSetIsOpen(final boolean open) {
		shape.setOpened(open);
		assertEquals(open, shape.isOpened());
	}

	@Theory
	public void testGetSetInterval(@TestedOn(ints = {1, 10}) final int value) {
		shape.setInterval(value);
		assertEquals(value, shape.getInterval());
	}

	@Test
	public void testGetSetIntervalKO() {
		shape.setInterval(10);
		shape.setInterval(-1);
		assertEquals(10, shape.getInterval());
	}

	@Test
	public void testCopy() {
		shape2.setOpened(!shape2.isOpened());
		shape.setInterval(shape.getInterval() * 2);
		shape2.setType(FreeHandStyle.LINES);

		shape.copy(shape2);

		assertEquals(shape2.isOpened(), shape.isOpened());
		assertEquals(shape2.getInterval(), shape.getInterval());
		assertEquals(FreeHandStyle.LINES, shape.getType());
	}

	@Test
	public void testDuplicate() {
		shape.setOpened(!shape.isOpened());
		shape.setInterval(shape.getInterval() * 2);
		shape.setType(FreeHandStyle.LINES);

		final Freehand dup = shape.duplicate();

		assertEquals(shape.isOpened(), dup.isOpened());
		assertEquals(shape.getInterval(), dup.getInterval());
		assertEquals(FreeHandStyle.LINES, dup.getType());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(Freehand.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
