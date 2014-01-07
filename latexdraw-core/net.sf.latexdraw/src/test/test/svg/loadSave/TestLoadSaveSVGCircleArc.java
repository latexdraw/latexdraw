package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ICircleArc;

import org.junit.Before;

public class TestLoadSaveSVGCircleArc extends TestLoadSaveSVGRectangularShape<ICircleArc> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.createCircleArc(false);
	}

	public void testStartAngle0() {
		setDefaultDimensions();
		shape.setAngleStart(0);
		compareShapes(generateShape());
	}

	public void testStartAngle90() {
		setDefaultDimensions();
		shape.setAngleStart(Math.PI/2.);
		compareShapes(generateShape());
	}

	public void testStartAngle180() {
		setDefaultDimensions();
		shape.setAngleStart(Math.PI);
		compareShapes(generateShape());
	}

	public void testStartAngle270EndAngle180() {
		setDefaultDimensions();
		shape.setAngleEnd(-Math.PI);
		shape.setAngleStart(-Math.PI/2.);
		compareShapes(generateShape());
	}

	public void testEndAngle0() {
		setDefaultDimensions();
		shape.setAngleStart(-Math.PI);
		shape.setAngleEnd(0);
		compareShapes(generateShape());
	}

	public void testEndAngle90() {
		setDefaultDimensions();
		shape.setAngleEnd(Math.PI/2.);
		compareShapes(generateShape());
	}

	public void testEndAngle180() {
		setDefaultDimensions();
		shape.setAngleEnd(Math.PI);
		compareShapes(generateShape());
	}

	public void testEndAngle270() {
		setDefaultDimensions();
		shape.setAngleEnd(3*Math.PI/2.);
		compareShapes(generateShape());
	}
}
