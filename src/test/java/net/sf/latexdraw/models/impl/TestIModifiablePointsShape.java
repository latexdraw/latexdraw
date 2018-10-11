package net.sf.latexdraw.models.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static java.lang.annotation.ElementType.PARAMETER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIModifiablePointsShape implements HelperTest {
	@Theory
	public void testSetRotationAngleOnly(@ModifPtShapeData final IModifiablePointsShape shape) {
		final List<IPoint> pts = cloneList(shape.getPoints(), pt -> ShapeFactory.INST.createPoint(pt));
		shape.setRotationAngleOnly(0.33d);
		assertEquals(0.33d, shape.getRotationAngle(), 0.0001);
		assertEquals(pts, shape.getPoints());
	}

	@Theory
	public void testDuplicate(@ModifPtShapeData(x = {1.1d, 3d}, y = {-21d, 1d}) final IModifiablePointsShape shape) {
		final IModifiablePointsShape dup = shape.duplicate();
		assertEqualsDouble(1.1d, dup.getPtAt(0).getX());
		assertEqualsDouble(3d, dup.getPtAt(1).getX());
		assertEqualsDouble(-21d, dup.getPtAt(0).getY());
		assertEqualsDouble(1d, dup.getPtAt(1).getY());
	}

	@Theory
	public void testMirrorHorizontal(@ModifPtShapeData(x = {1d, 3d}, y = {1d, 1d}) final IModifiablePointsShape shape) {
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEqualsDouble(3d, shape.getPtAt(0).getX());
		assertEqualsDouble(1d, shape.getPtAt(1).getX());
		assertEqualsDouble(1d, shape.getPtAt(0).getY());
		assertEqualsDouble(1d, shape.getPtAt(1).getY());
	}

	@Theory
	public void testMirrorVertical(@ModifPtShapeData(x = {1d, 3d}, y = {1d, 3d}) final IModifiablePointsShape shape) {
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
	}

	@Theory
	public void testTranslate(@ModifPtShapeData(x = {1d, 3d}, y = {1d, 1d}) final IModifiablePointsShape shape) {
		shape.translate(10, 0);
		assertEqualsDouble(11., shape.getPtAt(0).getX());
		assertEqualsDouble(13., shape.getPtAt(1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
	}

	@Theory
	public void testTranslateKO(@ModifPtShapeData(x = {1d, 3d}, y = {1d, 1d}) final IModifiablePointsShape shape,
								@DoubleData(vals = {}, bads = true) final double value) {
		shape.translate(value, value);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
	}

	@Theory
	public void testGetGravityCentre(@ModifPtShapeData(x = {0d, 2d, 2d}, y = {0d, 0d, 2d}) final IModifiablePointsShape shape) {
		assertTrue(shape.getGravityCentre().equals(ShapeFactory.INST.createPoint(1, 1)));
	}


	@Theory
	public void testSetPoint2(@ModifPtShapeData(x = {1d}, y = {0d}) final IModifiablePointsShape shape) {
		assertTrue(shape.setPoint(10, 12, 0));
		assertEqualsDouble(10., shape.getPtAt(0).getX());
		assertEqualsDouble(12., shape.getPtAt(0).getY());
	}

	@Theory
	public void testSetPoint2KO1(@ModifPtShapeData final IModifiablePointsShape shape,
								@DoubleData(bads = true, vals = {}) final double value) {
		assertFalse(shape.setPoint(value, value, 0));
	}

	@Theory
	public void testSetPoint2KO2(@ModifPtShapeData final IModifiablePointsShape shape,
								@TestedOn(ints = {-2, 10}) final int value) {
		assertFalse(shape.setPoint(0, 0, value));
	}

	@Theory
	public void testGetTopLeftPoint(@ModifPtShapeData(x = {0d, 2d, 2d, 0d}, y = {0d, 0d, 2d, 2d}) final IModifiablePointsShape shape) {
		assertEqualsDouble(0d, shape.getTopLeftPoint().getX());
		assertEqualsDouble(0d, shape.getTopLeftPoint().getY());
	}

	@Theory
	public void testGetTopRightPoint(@ModifPtShapeData(x = {0d, 2d, 2d, 0d}, y = {0d, 0d, 2d, 2d}) final IModifiablePointsShape shape) {
		assertEqualsDouble(2d, shape.getTopRightPoint().getX());
		assertEqualsDouble(0d, shape.getTopRightPoint().getY());
	}

	@Theory
	public void testGetBottomRightPoint(@ModifPtShapeData(x = {0d, 2d, 2d, 0d}, y = {0d, 0d, 2d, 2d}) final IModifiablePointsShape shape) {
		assertEqualsDouble(2d, shape.getBottomRightPoint().getX());
		assertEqualsDouble(2d, shape.getBottomRightPoint().getY());
	}

	@Theory
	public void testGetBottomLeftPoint(@ModifPtShapeData(x = {0d, 2d, 2d, 0d}, y = {0d, 0d, 2d, 2d}) final IModifiablePointsShape shape) {
		assertEqualsDouble(0d, shape.getBottomLeftPoint().getX());
		assertEqualsDouble(2d, shape.getBottomLeftPoint().getY());
	}

	@Retention(RetentionPolicy.RUNTIME)
	@ParametersSuppliedBy(ModifPtShapeSupplier.class)
	@Target(PARAMETER)
	public @interface ModifPtShapeData {
		double[] x() default {0d, -1d, 11d};
		double[] y() default {0d, 1d, 13d};
	}

	public static class ModifPtShapeSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
			final ModifPtShapeData data = sig.getAnnotation(ModifPtShapeData.class);
			final List<IPoint> pts = Arrays.stream(data.x()).mapToObj(x -> ShapeFactory.INST.createPoint(x, 0d)).collect(Collectors.toList());
			for(int i = 0, size = pts.size(); i < size; i++) {
				pts.get(i).setY(data.y()[i]);
			}

			return Stream.of(ShapeFactory.INST.createBezierCurve(pts),
				ShapeFactory.INST.createPolyline(pts), ShapeFactory.INST.createPolygon(pts)).
				map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
		}
	}
}
