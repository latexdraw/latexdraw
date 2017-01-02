package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.shape.*;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIGroup;

public class TestLGroup extends TestIGroup<IGroup> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createGroup();
		shape2 = ShapeFactory.INST.createGroup();
	}

	@Test
	public void testCannotAddShapeWhichIsEmptyGroup() {
		shape.addShape(ShapeFactory.INST.createGroup());
		assertTrue(shape.isEmpty());
		shape.addShape(ShapeFactory.INST.createGroup(), -1);
		assertTrue(shape.isEmpty());
	}

	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertTrue(shape.isTypeOf(shape.getClass()));
		assertTrue(shape.isTypeOf(IShape.class));

		assertFalse(shape.isTypeOf(IAxes.class));
		shape.addShape(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IBezierCurve.class));
		shape.addShape(ShapeFactory.INST.createBezierCurve());
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IEllipse.class));
		shape.addShape(ShapeFactory.INST.createEllipse());
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));

		assertFalse(shape.isTypeOf(ICircle.class));
		shape.addShape(ShapeFactory.INST.createCircle());
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IArc.class));
		shape.addShape(ShapeFactory.INST.createCircleArc());
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(IArcProp.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IDot.class));
		shape.addShape(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IFreehand.class));
		shape.addShape(ShapeFactory.INST.createFreeHand());
		assertTrue(shape.isTypeOf(IFreehand.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IGrid.class));
		shape.addShape(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertTrue(shape.isTypeOf(IGroup.class));
		shape.addShape(ShapeFactory.INST.createGroup());
		assertTrue(shape.isTypeOf(IGroup.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(IAxes.class));

		assertFalse(shape.isTypeOf(IPicture.class));
		shape.addShape(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint()));
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
		shape.addShape(ShapeFactory.INST.createPolygon());
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
		shape.addShape(ShapeFactory.INST.createPolyline());
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
		shape.addShape(ShapeFactory.INST.createRectangle());
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
		shape.addShape(ShapeFactory.INST.createRhombus());
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
		shape.addShape(ShapeFactory.INST.createSquare());
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
		shape.addShape(ShapeFactory.INST.createText());
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
		shape.addShape(ShapeFactory.INST.createTriangle());
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
