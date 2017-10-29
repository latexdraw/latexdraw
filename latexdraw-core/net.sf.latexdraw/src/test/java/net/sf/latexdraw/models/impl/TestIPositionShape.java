package net.sf.latexdraw.models.impl;

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
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static java.lang.annotation.ElementType.PARAMETER;

@RunWith(Theories.class)
public class TestIPositionShape implements HelperTest {
	@Theory
	public void testCopy(@PosShapeData final IPositionShape shape, @PosShapeData final IPositionShape shape2) {
		shape.setPosition(11d, 23d);
		shape2.copy(shape);
		assertEqualsDouble(11d, shape2.getPosition().getX());
		assertEqualsDouble(23d, shape2.getPosition().getY());
	}

	@Theory
	public void testTranslate(@PosShapeData final IPositionShape shape) {
		shape.setPosition(0, 0);
		shape.translate(100, 50);
		assertEqualsDouble(100., shape.getPosition().getX());
		assertEqualsDouble(50., shape.getPosition().getY());
	}

	@Theory
	public void testGetSetX(@PosShapeData final IPositionShape shape, @DoubleData final double value) {
		shape.setX(value);
		assertEqualsDouble(value, shape.getX());
	}

	@Theory
	public void testGetSetXKO(@PosShapeData final IPositionShape shape, @DoubleData(vals = {}, bads = true) final double value) {
		shape.setX(10d);
		shape.setX(value);
		assertEqualsDouble(10d, shape.getX());
	}

	@Theory
	public void testGetSetY(@PosShapeData final IPositionShape shape, @DoubleData final double value) {
		shape.setY(value);
		assertEqualsDouble(value, shape.getY());
	}

	@Theory
	public void testGetSetYKO(@PosShapeData final IPositionShape shape, @DoubleData(vals = {}, bads = true) final double value) {
		shape.setY(10d);
		shape.setY(value);
		assertEqualsDouble(10d, shape.getY());
	}

	@Theory
	public void testSetXThenSetY(@PosShapeData final IPositionShape shape, @DoubleData final double value) {
		shape.setX(value);
		assertEqualsDouble(value, shape.getX());
	}

	@Theory
	public void testSetYThenSetX(@PosShapeData final IPositionShape shape, @DoubleData final double value) {
		shape.setY(value);
		assertEqualsDouble(value, shape.getY());
	}

	@Theory
	public void testGetSetPosition(@PosShapeData final IPositionShape shape) {
		IPoint pt = ShapeFactory.INST.createPoint(15d, 25d);
		shape.setPosition(pt);
		assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		assertEqualsDouble(15d, shape.getX());
		assertEqualsDouble(25d, shape.getY());
	}

	@Theory
	public void testGetSetPositionKONULL(@PosShapeData final IPositionShape shape) {
		IPoint pt = ShapeFactory.INST.createPoint(15d, 25d);
		shape.setPosition(pt);
		shape.setPosition(null);
		assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		assertEqualsDouble(pt.getY(), shape.getPosition().getY());
	}

	@Theory
	public void testGetSetPositionKO(@PosShapeData final IPositionShape shape, @DoubleData(vals = {}, bads = true) final double value) {
		IPoint pt = ShapeFactory.INST.createPoint(15d, 25d);
		shape.setPosition(pt);
		shape.setPosition(ShapeFactory.INST.createPoint(value, value));
		assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		assertEqualsDouble(pt.getY(), shape.getPosition().getY());
	}

	@Retention(RetentionPolicy.RUNTIME)
	@ParametersSuppliedBy(PosShapeSupplier.class)
	@Target(PARAMETER)
	public @interface PosShapeData {
	}

	public static class PosShapeSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
			return Stream.of(ShapeFactory.INST.createEllipse(),
				ShapeFactory.INST.createCircleArc(),
				ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()),
				ShapeFactory.INST.createCircle(),
				ShapeFactory.INST.createRectangle(),
				ShapeFactory.INST.createText(),
				ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()),
				ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()),
				ShapeFactory.INST.createSquare(),
				ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0d, 10d, "x", false),
				ShapeFactory.INST.createRhombus(),
				ShapeFactory.INST.createTriangle(),
				ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint())).
				map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
		}
	}
}
