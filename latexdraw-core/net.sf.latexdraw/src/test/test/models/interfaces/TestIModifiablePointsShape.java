package test.models.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import test.HelperTest;
import test.data.DoubleData;

import static java.lang.annotation.ElementType.PARAMETER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIModifiablePointsShape implements HelperTest {
	@Theory
	public void testMirrorHorizontal(@ModifPtShapeData final IModifiablePointsShape shape) {
		final IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		final IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEqualsDouble(3., pt1.getX());
		assertEqualsDouble(1., pt2.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
	}

	@Theory
	public void testMirrorVertical(@ModifPtShapeData final IModifiablePointsShape shape) {
		final IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		final IPoint pt2 = ShapeFactory.INST.createPoint(3, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
	}

	@Theory
	public void testTranslate(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.translate(10, 0);
		assertEqualsDouble(11., pt1.getX());
		assertEqualsDouble(13., pt2.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
	}

	@Theory
	public void testTranslateKO(@ModifPtShapeData final IModifiablePointsShape shape, @DoubleData(vals = {}, bads = true) final double value) {
		IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.translate(value, value);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
	}

	@Theory
	public void testGetGravityCentre(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		assertNotNull(shape.getGravityCentre());
		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		assertTrue(shape.getGravityCentre().equals(ShapeFactory.INST.createPoint(1, 1)));
	}

	@Theory
	public void testAddPoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		final IPoint pt = ShapeFactory.INST.createPoint();
		final int size = shape.getNbPoints();
		shape.addPoint(pt);
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 1, shape.getPoints().size());
	}

	@Theory
	public void testAddPointKO(@ModifPtShapeData final IModifiablePointsShape shape) {
		final int size = shape.getNbPoints();
		shape.addPoint(null);
		assertEquals(size, shape.getPoints().size());
	}

	@Theory
	public void testAddPointAtNULL(@ModifPtShapeData final IModifiablePointsShape shape) {
		int size = shape.getNbPoints();
		shape.addPoint(null, 0);
		assertEquals(size, shape.getPoints().size());
	}

	@Theory
	public void testAddPointAtKO(@ModifPtShapeData final IModifiablePointsShape shape,
								 @DoubleData(bads = true, vals = {}) final double value) {
		int size = shape.getNbPoints();
		shape.addPoint(ShapeFactory.INST.createPoint(value, value), 0);
		assertEquals(size, shape.getPoints().size());
	}

	@Theory
	public void testAddPointAtKO2(@ModifPtShapeData final IModifiablePointsShape shape,
								 @TestedOn(ints = {-2, 10}) final int value) {
		int size = shape.getNbPoints();
		shape.addPoint(ShapeFactory.INST.createPoint(), value);
		assertEquals(size, shape.getPoints().size());
	}

	@Theory
	public void testAddPointAt(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt = ShapeFactory.INST.createPoint();
		int size = shape.getNbPoints();
		shape.addPoint(pt, 0);
		assertEquals(pt, shape.getPoints().get(0));
		assertEquals(size + 1, shape.getPoints().size());
	}

	@Theory
	public void testRemovePoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		int size = shape.getPoints().size();
		IPoint pt = ShapeFactory.INST.createPoint();
		shape.addPoint(pt);
		assertEquals(size + 1, shape.getPoints().size());
		assertTrue(shape.removePoint(pt));
		assertEquals(size, shape.getPoints().size());
	}

	@Theory
	public void testRemovePointNULL(@ModifPtShapeData final IModifiablePointsShape shape) {
		assertFalse(shape.removePoint(null));
	}

	@Theory
	public void testRemovePointKO(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt = ShapeFactory.INST.createPoint();
		shape.addPoint(pt);
		int size = shape.getPoints().size();
		assertNull(shape.removePoint(size));
	}

	@Theory
	public void testSetPoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt = ShapeFactory.INST.createPoint(1, 0);
		shape.getPoints().clear();
		shape.addPoint(pt);
		assertTrue(shape.setPoint(ShapeFactory.INST.createPoint(10, 12), 0));
		assertEqualsDouble(10., pt.getX());
		assertEqualsDouble(12., pt.getY());
	}

	@Theory
	public void testSetPointKO1(@ModifPtShapeData final IModifiablePointsShape shape,
							   @DoubleData(bads = true, vals = {}) final double value) {
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(value, value), 0));
	}

	@Theory
	public void testSetPointKO2(@ModifPtShapeData final IModifiablePointsShape shape,
							   @TestedOn(ints = {-2, 10}) final int value) {
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(0, 0), value));
	}

	@Theory
	public void testSetPoint2(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt = ShapeFactory.INST.createPoint(1, 0);
		shape.getPoints().clear();
		shape.addPoint(pt);
		assertTrue(shape.setPoint(10, 12, 0));
		assertEqualsDouble(10., pt.getX());
		assertEqualsDouble(12., pt.getY());
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
	public void testReplacePoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 1);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		assertEquals(pt1, shape.replacePoint(pt3, 0));
		assertEquals(0, shape.getPoints().indexOf(pt3));
	}

	@Theory
	public void testReplacePointKO(@ModifPtShapeData final IModifiablePointsShape shape) {
		assertNull(shape.replacePoint(null, 0));
		assertNull(shape.replacePoint(ShapeFactory.INST.createPoint(2, 2), 10));
	}

	@Theory
	public void testGetTopLeftPoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);
		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getTopLeftPoint().equals(pt1));
	}

	@Theory
	public void testGetTopRightPoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);
		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getTopRightPoint().equals(pt2));
	}

	@Theory
	public void testGetBottomRightPoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);
		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getBottomRightPoint().equals(pt3));
	}

	@Theory
	public void testGetBottomLeftPoint(@ModifPtShapeData final IModifiablePointsShape shape) {
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);
		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getBottomLeftPoint().equals(pt4));
	}

	@Retention(RetentionPolicy.RUNTIME)
	@ParametersSuppliedBy(ModifPtShapeSupplier.class)
	@Target(PARAMETER)
	public @interface ModifPtShapeData {
	}

	public static class ModifPtShapeSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
			return Stream.of(ShapeFactory.INST.createBezierCurve(),
				ShapeFactory.INST.createFreeHand(),
				ShapeFactory.INST.createPolyline(),
				ShapeFactory.INST.createPolygon()).
				map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
		}
	}
}
