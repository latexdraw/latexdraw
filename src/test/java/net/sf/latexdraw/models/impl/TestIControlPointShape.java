package net.sf.latexdraw.models.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(Theories.class)
public class TestIControlPointShape implements HelperTest {
	@Theory
	public void testGetFirstCtrlPtAt(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {0, 1, -1}) final int value) {
		assertNotNull(sh.getFirstCtrlPtAt(value));
	}

	@Theory
	public void testGetFirstCtrlPtAtKO(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {-2, 2}) final int value) {
		assertNull(sh.getFirstCtrlPtAt(value));
	}

	@Theory
	public void testGetSecondCtrlPtAt(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {0, 1, -1}) final int value) {
		assertNotNull(sh.getSecondCtrlPtAt(value));
	}

	@Theory
	public void testUpdateSecondControlPoints(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {-2, 2}) final int value) {
		assertNull(sh.getSecondCtrlPtAt(value));
	}

	@Theory
	public void testSetXFirstCtrlPt(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {0, 1, -1}) final int index) {
		sh.setXFirstCtrlPt(33d, index);
		assertEqualsDouble(33d, sh.getFirstCtrlPtAt(index).getX());
	}

	@Theory
	public void testSetYFirstCtrlPt(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {0, 1, -1}) final int index) {
		sh.setYFirstCtrlPt(33d, index);
		assertEqualsDouble(33d, sh.getFirstCtrlPtAt(index).getY());
	}

	@Theory
	public void testSetXSecondCtrlPt(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {0, 1, -1}) final int index) {
		sh.setXSecondCtrlPt(33d, index);
		assertEqualsDouble(33d, sh.getSecondCtrlPtAt(index).getX());
	}

	@Theory
	public void testSetYSecondCtrlPt(@CtrlShapeData final IControlPointShape sh, @TestedOn(ints = {0, 1, -1}) final int index) {
		sh.setYSecondCtrlPt(33d, index);
		assertEqualsDouble(33d, sh.getSecondCtrlPtAt(index).getY());
	}

	@Theory
	public void testGetFirstCtrlPts(@CtrlShapeData final IControlPointShape sh) {
		assertNotNull(sh.getFirstCtrlPts());
	}

	@Theory
	public void testGetSecondCtrlPts(@CtrlShapeData final IControlPointShape sh) {
		assertNotNull(sh.getSecondCtrlPts());
	}

	@Theory
	public void testTranslate(@CtrlShapeData final IControlPointShape sh) {
		final List<IPoint> ctrlPts1 = cloneList(sh.getFirstCtrlPts(), p -> ShapeFactory.INST.createPoint(p));
		final List<IPoint> ctrlPts2 = cloneList(sh.getSecondCtrlPts(), p -> ShapeFactory.INST.createPoint(p));

		sh.translate(10d, -12d);

		ctrlPts1.forEach(p -> p.translate(10d, -12d));
		ctrlPts2.forEach(p -> p.translate(10d, -12d));

		assertEquals(ctrlPts1, sh.getFirstCtrlPts());
		assertEquals(ctrlPts2, sh.getSecondCtrlPts());
	}


	@Theory
	public void testDuplicate(@CtrlShapeData final IControlPointShape sh) {
		sh.translate(10d, -12d);
		final IControlPointShape dup = sh.duplicate();

		assertEquals(sh.getFirstCtrlPts(), dup.getFirstCtrlPts());
		assertEquals(sh.getSecondCtrlPts(), dup.getSecondCtrlPts());
	}

	@Theory
	public void testDuplicateDoNotShareCtrlPoints(@CtrlShapeData final IControlPointShape shape) {
		final List<IPoint> ctrlPts = cloneList(shape.getFirstCtrlPts(), pt -> ShapeFactory.INST.createPoint(pt));
		final IShape sh = shape.duplicate();
		sh.translate(11d, 12d);
		assertEquals(ctrlPts, shape.getFirstCtrlPts());
	}

	@Theory
	public void testMirrorHorizontal(@CtrlShapeData final IControlPointShape sh) {
		sh.translate(-100d, 120d);
		final double x = sh.getGravityCentre().getX();
		List<IPoint> ctrlPts1 = cloneList(sh.getFirstCtrlPts(), p -> ShapeFactory.INST.createPoint(p));
		List<IPoint> ctrlPts2 = cloneList(sh.getSecondCtrlPts(), p -> ShapeFactory.INST.createPoint(p));
		sh.mirrorHorizontal(x);
		ctrlPts1 = ctrlPts1.stream().map(p -> p.horizontalSymmetry(x)).collect(Collectors.toList());
		ctrlPts2 = ctrlPts2.stream().map(p -> p.horizontalSymmetry(x)).collect(Collectors.toList());

		assertEquals(ctrlPts1, sh.getFirstCtrlPts());
		assertEquals(ctrlPts2, sh.getSecondCtrlPts());
	}

	@Theory
	public void testMirrorVertical(@CtrlShapeData final IControlPointShape sh) {
		sh.translate(-100d, 120d);
		final double y = sh.getGravityCentre().getY();
		List<IPoint> ctrlPts1 = cloneList(sh.getFirstCtrlPts(), p -> ShapeFactory.INST.createPoint(p));
		List<IPoint> ctrlPts2 = cloneList(sh.getSecondCtrlPts(), p -> ShapeFactory.INST.createPoint(p));
		sh.mirrorVertical(y);
		ctrlPts1 = ctrlPts1.stream().map(p -> p.verticalSymmetry(y)).collect(Collectors.toList());
		ctrlPts2 = ctrlPts2.stream().map(p -> p.verticalSymmetry(y)).collect(Collectors.toList());

		assertEquals(ctrlPts1, sh.getFirstCtrlPts());
		assertEquals(ctrlPts2, sh.getSecondCtrlPts());
	}

	@Theory
	public void setRotationAngle(@CtrlShapeData final IControlPointShape sh) {
		sh.translate(100d, 120d);
		final IPoint pt = sh.getGravityCentre();
		final double angle = Math.PI / 2d;
		List<IPoint> ctrlPts1 = cloneList(sh.getFirstCtrlPts(), p -> ShapeFactory.INST.createPoint(p));
		List<IPoint> ctrlPts2 = cloneList(sh.getSecondCtrlPts(), p -> ShapeFactory.INST.createPoint(p));

		sh.setRotationAngle(angle);
		ctrlPts1 = ctrlPts1.stream().map(p -> p.rotatePoint(pt, angle)).collect(Collectors.toList());
		ctrlPts2 = ctrlPts2.stream().map(p -> p.rotatePoint(pt, angle)).collect(Collectors.toList());

		assertEquals(ctrlPts1, sh.getFirstCtrlPts());
		assertEquals(ctrlPts2, sh.getSecondCtrlPts());
	}


	@Retention(RetentionPolicy.RUNTIME)
	@ParametersSuppliedBy(CtrlPtShapeSupplier.class)
	@Target(PARAMETER)
	public @interface CtrlShapeData {
	}

	public static class CtrlPtShapeSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
			return Stream.of(ShapeFactory.INST.createBezierCurve(Arrays.asList(
				ShapeFactory.INST.createPoint(10d, 20d),
				ShapeFactory.INST.createPoint(30d, 40d)))).
				map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
		}
	}
}
