package net.sf.latexdraw.model.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.SetShapesProp;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Before;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static java.lang.annotation.ElementType.PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestSetShapesBase {
	Rectangle sh1;
	Shape sh2;
	Shape sh3;

	@Before
	public void setUp() {
		sh1 = ShapeFactory.INST.createRectangle();
		sh2 = ShapeFactory.INST.createEllipse();
		sh3 = ShapeFactory.INST.createRhombus();
	}

	@Theory
	public void testGetShapes(@SetShapeData final SetShapesProp shape) {
		assertNotNull(shape.getShapes());
	}

	@Theory
	public void testAddShapeIShape(@SetShapeData final SetShapesProp shape) {
		shape.addShape(sh1);
		shape.addShape(sh2);
		assertThat(shape.getShapes(), contains(sh1, sh2));
	}

	@Theory
	public void testAddShapeIShapeIntKO1(@SetShapeData final SetShapesProp shape) {
		shape.addShape(ShapeFactory.INST.createRectangle(), 1);
		assertThat(shape.getShapes(), empty());
	}

	@Theory
	public void testAddShapeIShapeIntKO2(@SetShapeData final SetShapesProp shape) {
		shape.addShape(ShapeFactory.INST.createRectangle(), -2);
		assertThat(shape.getShapes(), empty());
	}

	@Theory
	public void testAddShapeIShapeInt(@SetShapeData final SetShapesProp shape) {
		shape.addShape(sh1);
		shape.addShape(sh2, 0);
		assertThat(shape.getShapes(), contains(sh2, sh1));
	}

	@Theory
	public void testRemoveShapeKO(@SetShapeData final SetShapesProp shape) {
		shape.removeShape(ShapeFactory.INST.createRectangle());
		assertThat(shape.getShapes(), empty());
	}

	@Theory
	public void testRemoveShape1(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		shape.removeShape(sh2);
		assertThat(shape.getShapes(), contains(sh1, sh3));
	}

	@Theory
	public void testRemoveShape2(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		shape.removeShape(sh1);
		shape.removeShape(sh2);
		assertThat(shape.getShapes(), contains(sh3));
	}

	@Theory
	public void testRemoveShape3(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		shape.removeShape(sh3);
		shape.removeShape(sh1);
		shape.removeShape(sh2);
		assertThat(shape.getShapes(), empty());
	}

	@Theory
	public void testRemoveShapeIntKO(@SetShapeData final SetShapesProp shape, @TestedOn(ints = {-2, -1, 1, 2}) final int value) {
		shape.getShapes().add(sh1);
		shape.removeShape(value);
	}

	@Theory
	public void testRemoveShapeIntOK1(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().add(sh1);
		shape.removeShape(0);
		assertThat(shape.getShapes(), empty());
	}

	@Theory
	public void testRemoveShapeIntOK2(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		shape.removeShape(0);
		assertThat(shape.getShapes(), contains(sh2, sh3));
	}

	@Theory
	public void testRemoveShapeIntOK3(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		shape.removeShape(2);
		assertThat(shape.getShapes(), contains(sh1, sh2));
	}

	@Theory
	public void testGetShapeAt(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		assertEquals(sh1, shape.getShapeAt(0).orElseThrow());
		assertEquals(sh2, shape.getShapeAt(1).orElseThrow());
		assertEquals(sh3, shape.getShapeAt(2).orElseThrow());
		assertEquals(sh3, shape.getShapeAt(-1).orElseThrow());
	}

	@Theory
	public void testSize(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2, sh3);
		assertEquals(3, shape.size());
	}

	@Theory
	public void testContains(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2);
		assertTrue(shape.contains(sh1));
		assertTrue(shape.contains(sh2));
	}

	@Theory
	public void testContainsKO(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2);
		assertFalse(shape.contains(sh3));
	}

	@Theory
	public void testIsEmpty(@SetShapeData final SetShapesProp shape) {
		assertTrue(shape.isEmpty());
	}

	@Theory
	public void testIsNotEmpty(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().add(sh1);
		assertFalse(shape.isEmpty());
	}

	@Theory
	public void testClear(@SetShapeData final SetShapesProp shape) {
		shape.getShapes().addAll(sh1, sh2);
		shape.clear();
		assertThat(shape.getShapes(), empty());
	}


	@Retention(RetentionPolicy.RUNTIME)
	@ParametersSuppliedBy(SetShapesSupplier.class)
	@Target(PARAMETER)
	public @interface SetShapeData {
	}

	public static class SetShapesSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
			return Stream.of(ShapeFactory.INST.createDrawing(),
				ShapeFactory.INST.createGroup()).map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
		}
	}
}
