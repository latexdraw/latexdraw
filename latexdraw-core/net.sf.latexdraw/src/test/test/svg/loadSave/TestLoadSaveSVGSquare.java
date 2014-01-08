package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;

import org.junit.Before;

public class TestLoadSaveSVGSquare extends TestLoadSaveSVG<ISquare> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.createSquare(false);
	}

	protected void setSquare(final double x, final double y, final double w) {
		shape.setPosition(x, y+w);
		shape.setWidth(w);
	}

	@Override
	protected void setDefaultDimensions() {
		setSquare(2, 30, 20);
	}


	@Override
	protected void compareShapes(final ISquare r2) {
		super.compareShapes(r2);
		assertEquals(shape.getPosition().getX(), r2.getPosition().getX());
		assertEquals(shape.getPosition().getY(), r2.getPosition().getY());
		assertEquals(shape.getWidth(), r2.getWidth());
		assertEquals(shape.getHeight(), r2.getHeight());
	}


	public void testDimensionOkWith00Position() {
		setSquare(0, 0, 10);
		compareShapes(generateShape());
	}


	public void testDimensionOkWithPositivePosition() {
		setSquare(5, 5, 10);
		compareShapes(generateShape());
	}


	public void testDimensionOkWithNegPosition() {
		setSquare(-21, -829, 923);
		compareShapes(generateShape());
	}

	public void testDimensionOkWith00PositionAndMiddleBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.MID);
			setSquare(0, 0, 10);
			compareShapes(generateShape());
		}
	}


	public void testDimensionOkWithPositivePositionAndMiddleBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.MID);
			setSquare(5, 5, 10);
			compareShapes(generateShape());
		}
	}


	public void testDimensionOkWithNegPositionAndMiddleBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.MID);
			setSquare(-21, -829, 923);
			compareShapes(generateShape());
		}
	}

	public void testDimensionOkWith00PositionAndOutBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.OUT);
			setSquare(0, 0, 10);
			compareShapes(generateShape());
		}
	}


	public void testDimensionOkWithPositivePositionAndOutBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.OUT);
			setSquare(5, 5, 10);
			compareShapes(generateShape());
		}
	}


	public void testDimensionOkWithNegPositionAndOutBord() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.OUT);
			setSquare(-21, -829, 923);
			compareShapes(generateShape());
		}
	}
}
