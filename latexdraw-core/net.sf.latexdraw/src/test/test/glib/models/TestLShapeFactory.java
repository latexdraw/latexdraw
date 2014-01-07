package test.glib.models;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.*;

import org.junit.Test;

public class TestLShapeFactory extends TestCase{
	@Test
	public void testNewShape() {
		assertFalse(ShapeFactory.newShape(IRectangle.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IPolyline.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IBezierCurve.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IDot.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IFreehand.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IGrid.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IGroup.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IPicture.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IAxes.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IPolygon.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IRhombus.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ITriangle.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IFreehand.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IEllipse.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ICircle.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ICircleArc.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IGroup.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IText.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ISquare.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IRectangle.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IPolyline.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IBezierCurve.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IDot.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IFreehand.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IGrid.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IGroup.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IPicture.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IAxes.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IPolygon.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IRhombus.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ITriangle.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IFreehand.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IEllipse.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ICircle.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ICircleArc.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IGroup.class).isEmpty());
		assertFalse(ShapeFactory.newShape(IText.class).isEmpty());
		assertFalse(ShapeFactory.newShape(ISquare.class).isEmpty());
		assertTrue(ShapeFactory.newShape(null).isEmpty());
	}
}
