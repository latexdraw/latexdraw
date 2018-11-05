package net.sf.latexdraw.model.impl;

import java.util.Arrays;
import java.util.Collections;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.ModifiablePointsShape;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPolygon {
	Polygon shape;
	Polygon shape2;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPolygon(Collections.emptyList());
		shape2 = ShapeFactory.INST.createPolygon(Collections.emptyList());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(ModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(Polygon.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructor() {
		final Point pt1 = ShapeFactory.INST.createPoint(1, 1);
		final Point pt2 = ShapeFactory.INST.createPoint(2, 2);
		final Polygon pol = ShapeFactory.INST.createPolygon(Arrays.asList(pt1, pt2));
		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));
	}
}
