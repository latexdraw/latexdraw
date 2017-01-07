package test.models;

import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

import org.junit.Test;

public class TestLShapeFactory {
	@SuppressWarnings("null")
	@Test
	public void testNewShape() {
		assertTrue(ShapeFactory.INST.newShape(IRectangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolyline.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IBezierCurve.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IDot.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGrid.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPicture.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IAxes.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolygon.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IRhombus.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ITriangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IEllipse.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircleArc.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IText.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ISquare.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IRectangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolyline.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IBezierCurve.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IDot.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGrid.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPicture.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IAxes.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolygon.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IRhombus.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ITriangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IEllipse.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircleArc.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IText.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ISquare.class).isPresent());
	}
}
