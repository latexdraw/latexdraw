package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc;

import org.junit.Before;
import org.junit.Test;

public class TestLoadSaveSVGCircleArc extends TestLoadSaveSVGSquaredShape<ICircleArc> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createCircleArc();
	}

	@Test public void testStartAngle0() {
		setDefaultDimensions();
		shape.setAngleStart(0);
		compareShapes(generateShape());
	}

	@Test public void testStartAngle90() {
		setDefaultDimensions();
		shape.setAngleStart(Math.PI/2.);
		compareShapes(generateShape());
	}

	@Test public void testStartAngle180() {
		setDefaultDimensions();
		shape.setAngleStart(Math.PI);
		compareShapes(generateShape());
	}

	@Test public void testStartAngle270EndAngle180() {
		setDefaultDimensions();
		shape.setAngleEnd(-Math.PI);
		shape.setAngleStart(-Math.PI/2.);
		compareShapes(generateShape());
	}

	@Test public void testEndAngle0() {
		setDefaultDimensions();
		shape.setAngleStart(-Math.PI);
		shape.setAngleEnd(0);
		compareShapes(generateShape());
	}

	@Test public void testEndAngle90() {
		setDefaultDimensions();
		shape.setAngleEnd(Math.PI/2.);
		compareShapes(generateShape());
	}

	@Test public void testEndAngle180() {
		setDefaultDimensions();
		shape.setAngleEnd(Math.PI);
		compareShapes(generateShape());
	}

	@Test public void testEndAngle270() {
		setDefaultDimensions();
		shape.setAngleEnd(3*Math.PI/2.);
		compareShapes(generateShape());
	}
}
