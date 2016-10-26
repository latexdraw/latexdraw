package test.svg.loadSave;

import static org.junit.Assert.assertEquals;

import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;

import org.junit.Test;

public abstract class TestLoadSaveSVGRectangularShape<T extends IRectangularShape> extends TestLoadSaveSVGPositionShape<T> {
	protected void setRectangle(final double x, final double y, final double w, final double h) {
		shape.setPosition(x, y + h);
		shape.setWidth(w);
		shape.setHeight(h);
	}

	@Override
	protected void setDefaultDimensions() {
		setRectangle(2, 30, 20, 12);
	}

	@Override
	protected void compareShapes(final T r2) {
		super.compareShapes(r2);
		assertEquals(shape.getPosition().getX(), r2.getPosition().getX(), 0.0001);
		assertEquals(shape.getPosition().getY(), r2.getPosition().getY(), 0.0001);
		assertEquals(shape.getWidth(), r2.getWidth(), 0.0001);
		assertEquals(shape.getHeight(), r2.getHeight(), 0.0001);
	}

	@Test
	public void testDimensionOkWith00Position() {
		setRectangle(0, 0, 10, 10);
		compareShapes(generateShape());
	}

	@Test
	public void testDimensionOkWithPositivePosition() {
		setRectangle(5, 5, 10, 10);
		compareShapes(generateShape());
	}

	@Test
	public void testDimensionOkWithNegPosition() {
		setRectangle(-21, -829, 923, 5);
		compareShapes(generateShape());
	}

	@Test
	public void testDimensionOkWith00PositionAndMiddleBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.MID);
			setRectangle(0, 0, 10, 10);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDimensionOkWithPositivePositionAndMiddleBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.MID);
			setRectangle(5, 5, 10, 10);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDimensionOkWithNegPositionAndMiddleBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.MID);
			setRectangle(-21, -829, 923, 5);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDimensionOkWith00PositionAndOutBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.OUT);
			setRectangle(0, 0, 10, 10);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDimensionOkWithPositivePositionAndOutBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.OUT);
			setRectangle(5, 5, 10, 10);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDimensionOkWithNegPositionAndOutBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.OUT);
			setRectangle(-21, -829, 923, 5);
			compareShapes(generateShape());
		}
	}
}
