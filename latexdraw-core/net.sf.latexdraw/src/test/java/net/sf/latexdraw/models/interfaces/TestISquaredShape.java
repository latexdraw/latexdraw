package net.sf.latexdraw.models.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static java.lang.annotation.ElementType.PARAMETER;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestISquaredShape implements HelperTest {
	@Theory
	public void testGetSetWidth(@SquaredData final ISquaredShape shape, @DoubleData final double value) {
		assumeThat(value, greaterThan(0d));
		shape.setWidth(value);
		assertEqualsDouble(value, shape.getWidth());
		assertEqualsDouble(value, shape.getHeight());
	}


	@Theory
	public void testGetSetWidthKO(@SquaredData final ISquaredShape shape, @DoubleData(bads = true) final double value) {
		assumeThat(value, lessThanOrEqualTo(0d));
		shape.setWidth(30d);
		shape.setWidth(value);
		assertEqualsDouble(30d, shape.getWidth());
		assertEqualsDouble(30d, shape.getHeight());
	}

	@Theory
	public void testGetPtAt(@SquaredData final ISquaredShape shape) {
		assertNotNull(shape.getPtAt(0));
		assertNotNull(shape.getPtAt(1));
		assertNotNull(shape.getPtAt(2));
		assertNotNull(shape.getPtAt(3));
		assertNotNull(shape.getPtAt(-1));
		assertNull(shape.getPtAt(4));
		assertNull(shape.getPtAt(-2));
	}

	@Theory
	public void testGetBottomLeftPoint(@SquaredData final ISquaredShape shape) {
		shape.setPosition(-5, 0);
		shape.setWidth(10);

		assertEqualsDouble(-5., shape.getBottomLeftPoint().getX());
		assertEqualsDouble(0., shape.getBottomLeftPoint().getY());
	}

	@Theory
	public void testGetBottomRightPoint(@SquaredData final ISquaredShape shape) {
		shape.setPosition(-15, 100);
		shape.setWidth(10);

		assertEqualsDouble(-5., shape.getBottomRightPoint().getX());
		assertEqualsDouble(100., shape.getBottomRightPoint().getY());
	}

	@Theory
	public void testGetTopLeftPoint(@SquaredData final ISquaredShape shape) {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		assertEqualsDouble(20., shape.getTopLeftPoint().getX());
		assertEqualsDouble(0., shape.getTopLeftPoint().getY());
	}

	@Theory
	public void testGetTopRightPoint(@SquaredData final ISquaredShape shape) {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		assertEqualsDouble(30., shape.getTopRightPoint().getX());
		assertEqualsDouble(0., shape.getTopRightPoint().getY());
	}

	@Theory
	public void testMirrorHorizontal(@SquaredData final ISquaredShape shape) {
		final IPoint p1 = ShapeFactory.INST.createPoint(3, 1);
		final IPoint p2 = ShapeFactory.INST.createPoint(1, 3);

		shape.setPosition(p2);
		shape.setWidth(p1.getX() - p2.getX());

		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEqualsDouble(3., shape.getPtAt(0).getX());
		assertEqualsDouble(1., shape.getPtAt(1).getX());
		assertEqualsDouble(1., shape.getPtAt(2).getX());
		assertEqualsDouble(3., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());
	}

	@Theory
	public void testMirrorVertical(@SquaredData final ISquaredShape shape) {
		final IPoint p1 = ShapeFactory.INST.createPoint(3, 1);
		final IPoint p2 = ShapeFactory.INST.createPoint(1, 3);

		shape.setPosition(p2);
		shape.setWidth(p1.getX() - p2.getX());

		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(3., shape.getPtAt(0).getY());
		assertEqualsDouble(3., shape.getPtAt(1).getY());
		assertEqualsDouble(1., shape.getPtAt(2).getY());
		assertEqualsDouble(1., shape.getPtAt(-1).getY());
	}

	@Theory
	public void testTranslate(@SquaredData final ISquaredShape shape, @DoubleData final double tx, @DoubleData final double ty) {
		final IPoint p1 = ShapeFactory.INST.createPoint(3d, 1d);
		final IPoint p2 = ShapeFactory.INST.createPoint(1d, 3d);

		shape.setPosition(p2);
		shape.setWidth(p1.getX() - p2.getX());

		shape.translate(tx, ty);
		assertEqualsDouble(p2.getX() + tx, shape.getPtAt(0).getX());
		assertEqualsDouble(p1.getX() + tx, shape.getPtAt(1).getX());
		assertEqualsDouble(p1.getX() + tx, shape.getPtAt(2).getX());
		assertEqualsDouble(p2.getX() + tx, shape.getPtAt(-1).getX());
		assertEqualsDouble(p1.getY() + ty, shape.getPtAt(0).getY());
		assertEqualsDouble(p1.getY() + ty, shape.getPtAt(1).getY());
		assertEqualsDouble(p2.getY() + ty, shape.getPtAt(2).getY());
		assertEqualsDouble(p2.getY() + ty, shape.getPtAt(-1).getY());
	}

	@Theory
	public void testTranslateKO(@SquaredData final ISquaredShape shape, @DoubleData(bads = true, vals = {0d}) final double tx,
								@DoubleData(bads = true, vals = {0d}) final double ty) {
		final IPoint p1 = ShapeFactory.INST.createPoint(3d, 1d);
		final IPoint p2 = ShapeFactory.INST.createPoint(1d, 3d);

		shape.setPosition(p2);
		shape.setWidth(p1.getX() - p2.getX());
		shape.translate(tx, ty);
		assertEqualsDouble(1d, shape.getPtAt(0).getX());
		assertEqualsDouble(3d, shape.getPtAt(1).getX());
		assertEqualsDouble(3d, shape.getPtAt(2).getX());
		assertEqualsDouble(1d, shape.getPtAt(-1).getX());
		assertEqualsDouble(1d, shape.getPtAt(0).getY());
		assertEqualsDouble(1d, shape.getPtAt(1).getY());
		assertEqualsDouble(3d, shape.getPtAt(2).getY());
		assertEqualsDouble(3d, shape.getPtAt(-1).getY());
	}

	@Retention(RetentionPolicy.RUNTIME)
	@ParametersSuppliedBy(SquaredSupplier.class)
	@Target(PARAMETER)
	public @interface SquaredData {
	}

	public static class SquaredSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
			return Stream.of(ShapeFactory.INST.createSquare(), ShapeFactory.INST.createCircle(), ShapeFactory.INST.createCircleArc()).map
				(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
		}
	}
}
