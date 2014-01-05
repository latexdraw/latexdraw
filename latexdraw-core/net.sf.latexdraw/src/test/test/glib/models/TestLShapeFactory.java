package test.glib.models;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.*;

import org.junit.Test;

public class TestLShapeFactory extends TestCase{
	@Test
	public void testNewShape() {
		IShapeFactory factory = ShapeFactory.factory();
		assertFalse(factory.newShape(IRectangle.class).isEmpty());
		assertFalse(factory.newShape(IPolyline.class).isEmpty());
		assertFalse(factory.newShape(IBezierCurve.class).isEmpty());
		assertFalse(factory.newShape(IDot.class).isEmpty());
		assertFalse(factory.newShape(IFreehand.class).isEmpty());
		assertFalse(factory.newShape(IGrid.class).isEmpty());
		assertFalse(factory.newShape(IGroup.class).isEmpty());
		assertFalse(factory.newShape(IPicture.class).isEmpty());
		assertFalse(factory.newShape(IAxes.class).isEmpty());
		assertFalse(factory.newShape(IPolygon.class).isEmpty());
		assertFalse(factory.newShape(IRhombus.class).isEmpty());
		assertFalse(factory.newShape(ITriangle.class).isEmpty());
		assertFalse(factory.newShape(IFreehand.class).isEmpty());
		assertFalse(factory.newShape(IEllipse.class).isEmpty());
		assertFalse(factory.newShape(ICircle.class).isEmpty());
		assertFalse(factory.newShape(ICircleArc.class).isEmpty());
		assertFalse(factory.newShape(IGroup.class).isEmpty());
		assertFalse(factory.newShape(IText.class).isEmpty());
		assertFalse(factory.newShape(ISquare.class).isEmpty());
		assertFalse(factory.newShape(IRectangle.class).isEmpty());
		assertFalse(factory.newShape(IPolyline.class).isEmpty());
		assertFalse(factory.newShape(IBezierCurve.class).isEmpty());
		assertFalse(factory.newShape(IDot.class).isEmpty());
		assertFalse(factory.newShape(IFreehand.class).isEmpty());
		assertFalse(factory.newShape(IGrid.class).isEmpty());
		assertFalse(factory.newShape(IGroup.class).isEmpty());
		assertFalse(factory.newShape(IPicture.class).isEmpty());
		assertFalse(factory.newShape(IAxes.class).isEmpty());
		assertFalse(factory.newShape(IPolygon.class).isEmpty());
		assertFalse(factory.newShape(IRhombus.class).isEmpty());
		assertFalse(factory.newShape(ITriangle.class).isEmpty());
		assertFalse(factory.newShape(IFreehand.class).isEmpty());
		assertFalse(factory.newShape(IEllipse.class).isEmpty());
		assertFalse(factory.newShape(ICircle.class).isEmpty());
		assertFalse(factory.newShape(ICircleArc.class).isEmpty());
		assertFalse(factory.newShape(IGroup.class).isEmpty());
		assertFalse(factory.newShape(IText.class).isEmpty());
		assertFalse(factory.newShape(ISquare.class).isEmpty());
		assertTrue(factory.newShape(null).isEmpty());
	}
}
