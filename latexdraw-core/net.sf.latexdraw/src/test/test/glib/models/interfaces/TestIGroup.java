package test.glib.models.interfaces;


import static org.junit.Assert.*;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;

import org.junit.Test;

public abstract class TestIGroup<T extends IGroup> extends TestIShape<T> {
	@Test
	public void testAddShapeIShape() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);

		shape.addShape(sh1);
		shape.addShape(sh2);
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh2, shape.getShapes().get(1));
		shape.addShape((IShape)null);
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh2, shape.getShapes().get(1));
	}


	@Test
	public void testAddShapeIShapeInt() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.addShape(sh1, 1);
		assertEquals(0, shape.getShapes().size());
		shape.addShape(sh1, -2);
		assertEquals(0, shape.getShapes().size());
		shape.addShape(sh1, 0);
		assertEquals(1, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		shape.addShape(null, 0);
		assertEquals(1, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		shape.addShape(sh2, 2);
		assertEquals(1, shape.getShapes().size());
		shape.addShape(sh2, -2);
		assertEquals(1, shape.getShapes().size());
		shape.addShape(sh2, -1);
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh2, shape.getShapes().get(1));
		shape.addShape(sh3, 1);
		assertEquals(3, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh2, shape.getShapes().get(2));
		assertEquals(sh3, shape.getShapes().get(1));
	}


	@Test
	public void testRemoveShapeIShape() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);
		shape.removeShape(sh2);
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh3, shape.getShapes().get(1));
		shape.removeShape(null);
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh3, shape.getShapes().get(1));
		shape.removeShape(DrawingTK.getFactory().createRectangle(false));
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh3, shape.getShapes().get(1));
	}


	@Test
	public void testRemoveShapeInt() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);

		assertNull(shape.removeShape(4));
		assertNull(shape.removeShape(-2));
		assertEquals(sh2, shape.removeShape(1));
		assertEquals(sh1, shape.removeShape(0));
		assertEquals(sh3, shape.removeShape(-1));
	}


	@Test
	public void testGetShapeAt() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);

		assertEquals(sh1, shape.getShapeAt(0));
		assertEquals(sh2, shape.getShapeAt(1));
		assertEquals(sh3, shape.getShapeAt(2));
		assertEquals(sh3, shape.getShapeAt(-1));
		assertNull(shape.getShapeAt(-2));
		assertNull(shape.getShapeAt(3));
	}


	@Test
	public void testSize() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().add(sh1);
		assertEquals(1, shape.size());
		shape.getShapes().add(sh2);
		assertEquals(2, shape.size());
		shape.getShapes().add(sh3);
		assertEquals(3, shape.size());
		shape.getShapes().clear();
		assertEquals(0, shape.size());
	}


	@Test
	public void testContains() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);
		IShape sh4 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);

		assertFalse(shape.contains(null));
		assertFalse(shape.contains(sh4));
		assertTrue(shape.contains(sh1));
		assertTrue(shape.contains(sh2));
		assertTrue(shape.contains(sh3));
	}


	@Test
	public void testIsEmpty() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().clear();
		assertTrue(shape.isEmpty());
		shape.getShapes().add(sh1);
		assertFalse(shape.isEmpty());
		shape.getShapes().add(sh2);
		assertFalse(shape.isEmpty());
		shape.getShapes().add(sh3);
		assertFalse(shape.isEmpty());
		shape.getShapes().clear();
		assertTrue(shape.isEmpty());
	}


	@Test
	public void testClear() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);
		shape.clear();
		assertEquals(0, shape.getShapes().size());
	}


	@Test
	public void testGetShapes() {
		assertNotNull(shape.getShapes());
	}


	@Test
	public void testGetDotStyle() {
		//TODO
	}


	@Test
	public void testSetDotStyle() {
		//TODO
	}


	@Override
	@Test
	public void testCopy() {
		//TODO
	}

	@Override
	@Test
	public void testIsParametersEquals() {
		//TODO
	}

	@Override
	@Test
	public void testDuplicate() {
		//TODO
	}


	private IRectangle setRectangle(double x, double y, double w, double h) {
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		rec.setPosition(x, y);
		rec.setWidth(w);
		rec.setHeight(h);
		return rec;
	}


	@Override
	public void testGetBottomLeftPoint() {
		assertEquals(DrawingTK.getFactory().createPoint(Double.NaN, Double.NaN), shape.getBottomLeftPoint());
		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(DrawingTK.getFactory().createPoint(90, 40), shape.getBottomLeftPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(DrawingTK.getFactory().createPoint(5, 40), shape.getBottomLeftPoint());
	}


	@Override
	public void testGetBottomRightPoint() {
		assertEquals(DrawingTK.getFactory().createPoint(Double.NaN, Double.NaN), shape.getBottomRightPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(DrawingTK.getFactory().createPoint(11, 10), shape.getBottomRightPoint());

		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(DrawingTK.getFactory().createPoint(100, 40), shape.getBottomRightPoint());
	}


	@Override
	public void testGetTopLeftPoint() {
		assertEquals(DrawingTK.getFactory().createPoint(Double.NaN, Double.NaN), shape.getTopLeftPoint());

		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(DrawingTK.getFactory().createPoint(90, 19), shape.getTopLeftPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(DrawingTK.getFactory().createPoint(5, -10), shape.getTopLeftPoint());
	}


	@Override
	public void testGetTopRightPoint() {
		assertEquals(DrawingTK.getFactory().createPoint(Double.NaN, Double.NaN), shape.getTopRightPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(DrawingTK.getFactory().createPoint(11, -10), shape.getTopRightPoint());

		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(DrawingTK.getFactory().createPoint(100, -10), shape.getTopRightPoint());
	}


	@Override
	public void testMirrorHorizontal() {
		//TODO
	}


	@Override
	public void testMirrorVertical() {
		//TODO
	}


	@Override
	public void testTranslate() {
		//TODO
	}



	@Override
	@Test
	public void testGetGravityCentre() {
		assertEquals(DrawingTK.getFactory().createPoint(0, 0), shape.getGravityCentre());

		IRectangle rec1 = setRectangle(100, 2, 924, 12);
		IRectangle rec2 = setRectangle(200, 828, 17, 87);

		shape.addShape(rec1);
		shape.addShape(rec2);
		assertEquals(DrawingTK.getFactory().createPoint((100+100+924)/2., (828+2-12)/2.), shape.getGravityCentre());
	}


	@Override
	@Test
	public void testSetHasHatchings() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);

		shape.addShape(rec1);
		shape.addShape(rec2);
		assertFalse(shape.hasHatchings());

		rec2.setFillingStyle(FillingStyle.HLINES);
		assertTrue(shape.hasHatchings());

		rec1.setFillingStyle(FillingStyle.HLINES);
		rec2.setFillingStyle(FillingStyle.GRAD);
		assertTrue(shape.hasHatchings());

		rec1.setFillingStyle(FillingStyle.HLINES);
		rec2.setFillingStyle(FillingStyle.VLINES);
		assertTrue(shape.hasHatchings());

		rec1.setFillingStyle(FillingStyle.PLAIN);
		rec2.setFillingStyle(FillingStyle.GRAD);
		assertFalse(shape.hasHatchings());
	}


	@Test
	public void testHasHatchingsWithUnstylableShape() {
		IGrid grid = DrawingTK.getFactory().createGrid(false, DrawingTK.getFactory().createPoint());

		// The test is useful only if the shape is not stylable.
		assertFalse(grid.isInteriorStylable());

		shape.addShape(grid);

		assertFalse(shape.hasHatchings());
		grid.setFillingStyle(FillingStyle.CLINES_PLAIN);
		assertFalse(shape.hasHatchings());

		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		shape.addShape(rec1);
		assertFalse(shape.hasHatchings());

		rec1.setFillingStyle(FillingStyle.HLINES);
		assertTrue(shape.hasHatchings());
	}


	@Override
	@Test
	public void testSetHasGradient() {
		IGrid grid = DrawingTK.getFactory().createGrid(false, DrawingTK.getFactory().createPoint());

		// The test is useful only if the shape is not stylable.
		assertFalse(grid.isInteriorStylable());

		shape.addShape(grid);

		assertFalse(shape.hasGradient());
		grid.setFillingStyle(FillingStyle.GRAD);
		assertFalse(shape.hasGradient());

		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		shape.addShape(rec1);
		assertFalse(shape.hasGradient());

		rec1.setFillingStyle(FillingStyle.VLINES_PLAIN);
		assertFalse(shape.hasGradient());

		rec1.setFillingStyle(FillingStyle.GRAD);
		assertTrue(shape.hasGradient());
	}


	@Override
	@Test
	public void testGetSetGradColEnd() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);

		// The test is useful only if the shape is stylable.
		assertTrue(rec1.isInteriorStylable());

		shape.addShape(rec1);
		shape.addShape(rec2);

		shape.setGradColEnd(Color.CYAN);
		assertEquals(Color.CYAN, rec1.getGradColEnd());
		assertEquals(Color.CYAN, rec2.getGradColEnd());
	}


	@Test
	public void testGetGradColEnd() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);

		// The test is useful only if the shape is stylable.
		assertTrue(rec1.isInteriorStylable());

		shape.addShape(rec1);
		shape.addShape(rec2);

		rec1.setGradColEnd(Color.ORANGE);
		rec2.setGradColEnd(Color.PINK);

		assertNull(shape.getGradColEnd());

		rec1.setFillingStyle(FillingStyle.GRAD);
		rec2.setFillingStyle(FillingStyle.GRAD);

		assertEquals(Color.ORANGE, shape.getGradColEnd());
	}


	@Override
	@Test
	public void testGetSetGradColStart() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetGradAngle() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetGradMidPt() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetHatchingsSep() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetHatchingsCol() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetHatchingsAngle() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetHatchingsWidth() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetRotationAngle() {
		//TODO
	}


	@Override
	@Test
	public void testIsSetShowPts() {
		//TODO
	}


	@Override
	@Test
	public void testhasSetDbleBord() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetDbleBordCol() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetDbleBordSep() {
		//TODO
	}


	@Override
	@Test
	public void testHasSetShadow() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetShadowCol() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetShadowAngle() {
		//TODO
	}


	@Override
	@Test
	public void testIsSetFilled() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetShadowSize() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetBorderPosition() {
		//TODO
	}


	@Override
	@Test
	public void testGetSetOpacity() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetThickness() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetLineColour() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetLineStyle() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetDashSepWhite() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetDashSepBlack() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetDotSep() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetFillingCol() {
		//TODO
	}


	@Override
	@Test
	public void testSetGetFillingStyle() {
		//TODO
	}
}
