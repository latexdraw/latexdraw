package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.*;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIGroup;

public class TestLGroup<T extends IGroup> extends TestIGroup<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.createGroup(false);
		shape2 = (T) ShapeFactory.createGroup(false);
	}


	@Test public void testCannotAddShapeWhichIsEmptyGroup() {
		shape.addShape(ShapeFactory.createGroup(false));
		assertTrue(shape.isEmpty());
		shape.addShape(ShapeFactory.createGroup(false), -1);
		assertTrue(shape.isEmpty());
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertTrue(shape.isTypeOf(shape.getClass()));
		assertTrue(shape.isTypeOf(IShape.class));

		assertFalse(shape.isTypeOf(IAxes.class));
		shape.addShape(ShapeFactory.createAxes(false, ShapeFactory.createPoint()));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IBezierCurve.class));
		shape.addShape(ShapeFactory.createBezierCurve(false));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IEllipse.class));
		shape.addShape(ShapeFactory.createEllipse(false));
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));

		assertFalse(shape.isTypeOf(ICircle.class));
		shape.addShape(ShapeFactory.createCircle(false));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IArc.class));
		shape.addShape(ShapeFactory.createCircleArc(false));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(ICircleArc.class));
		shape.addShape(ShapeFactory.createCircleArc(false));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IDot.class));
		shape.addShape(ShapeFactory.createDot(ShapeFactory.createPoint(), false));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IFreehand.class));
		shape.addShape(ShapeFactory.createFreeHand(false));
		assertTrue(shape.isTypeOf(IFreehand.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IGrid.class));
		shape.addShape(ShapeFactory.createGrid(false, ShapeFactory.createPoint()));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertTrue(shape.isTypeOf(IGroup.class));
		shape.addShape(ShapeFactory.createGroup(false));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IPicture.class));
		shape.addShape(ShapeFactory.createPicture(false, ShapeFactory.createPoint()));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IPolygon.class));
		shape.addShape(ShapeFactory.createPolygon(false));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IPolyline.class));
		shape.addShape(ShapeFactory.createPolyline(false));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IRectangle.class));
		shape.addShape(ShapeFactory.createRectangle(false));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IRhombus.class));
		shape.addShape(ShapeFactory.createRhombus(false));
		assertTrue(shape.isTypeOf(IRhombus.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(ISquare.class));
		shape.addShape(ShapeFactory.createSquare(false));
		assertTrue(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(IRhombus.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IText.class));
		shape.addShape(ShapeFactory.createText(false));
		assertTrue(shape.isTypeOf(IText.class));
		assertTrue(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(IRhombus.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(ITriangle.class));
		shape.addShape(ShapeFactory.createTriangle(false));
		assertTrue(shape.isTypeOf(ITriangle.class));
		assertTrue(shape.isTypeOf(IText.class));
		assertTrue(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(IRhombus.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));
	}
}
