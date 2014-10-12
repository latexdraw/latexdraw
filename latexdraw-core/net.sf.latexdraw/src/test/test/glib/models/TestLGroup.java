package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.glib.models.interfaces.shape.*;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIGroup;

public class TestLGroup extends TestIGroup<IGroup> {
	@Before
	public void setUp() {
		shape  = ShapeFactory.createGroup();
		shape2 = ShapeFactory.createGroup();
	}


	@Test public void testCannotAddShapeWhichIsEmptyGroup() {
		shape.addShape(ShapeFactory.createGroup());
		assertTrue(shape.isEmpty());
		shape.addShape(ShapeFactory.createGroup(), -1);
		assertTrue(shape.isEmpty());
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertTrue(shape.isTypeOf(shape.getClass()));
		assertTrue(shape.isTypeOf(IShape.class));

		assertFalse(shape.isTypeOf(IAxes.class));
		shape.addShape(ShapeFactory.createAxes(ShapeFactory.createPoint()));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IBezierCurve.class));
		shape.addShape(ShapeFactory.createBezierCurve());
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IEllipse.class));
		shape.addShape(ShapeFactory.createEllipse());
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));

		assertFalse(shape.isTypeOf(ICircle.class));
		shape.addShape(ShapeFactory.createCircle());
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IArc.class));
		shape.addShape(ShapeFactory.createCircleArc());
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(IArcProp.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IDot.class));
		shape.addShape(ShapeFactory.createDot(ShapeFactory.createPoint()));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IFreehand.class));
		shape.addShape(ShapeFactory.createFreeHand());
		assertTrue(shape.isTypeOf(IFreehand.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IGrid.class));
		shape.addShape(ShapeFactory.createGrid(ShapeFactory.createPoint()));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertTrue(shape.isTypeOf(IGroup.class));
		shape.addShape(ShapeFactory.createGroup());
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IPicture.class));
		shape.addShape(ShapeFactory.createPicture(ShapeFactory.createPoint()));
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
		shape.addShape(ShapeFactory.createPolygon());
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
		shape.addShape(ShapeFactory.createPolyline());
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
		shape.addShape(ShapeFactory.createRectangle());
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
		shape.addShape(ShapeFactory.createRhombus());
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
		shape.addShape(ShapeFactory.createSquare());
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
		shape.addShape(ShapeFactory.createText());
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
		shape.addShape(ShapeFactory.createTriangle());
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
