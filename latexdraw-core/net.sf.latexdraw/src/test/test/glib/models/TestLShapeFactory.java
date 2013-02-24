package test.glib.models;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IPicture;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ISquare;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;

import org.junit.Before;
import org.junit.Test;

public class TestLShapeFactory extends TestCase{
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
	}


	@Test
	public void testNewShape() {
		IShapeFactory factory = DrawingTK.getFactory();
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
