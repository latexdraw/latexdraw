package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class TestIGroup<T extends IGroup> extends TestIShape<T> {
	@Test
	public void testAddShapeIShape() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();

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

	@Override
	@Test
	public void testAddToRotationAngle() {
		shape.addShape(ShapeFactory.INST.createRectangle());
		shape.addShape(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		super.testAddToRotationAngle();
	}

	@Test
	public void testAddShapeIShapeInt() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
		shape.removeShape(ShapeFactory.INST.createRectangle());
		assertEquals(2, shape.getShapes().size());
		assertEquals(sh1, shape.getShapes().get(0));
		assertEquals(sh3, shape.getShapes().get(1));
	}

	@Test
	public void testRemoveShapeInt() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();
		IShape sh4 = ShapeFactory.INST.createRectangle();

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
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

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
	public void testGetDotStyleOK() {
		IShape sh = ShapeFactory.INST.createRectangle();
		IDot d1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		IDot d2 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());

		d1.setDotStyle(DotStyle.BAR);

		shape.getShapes().add(sh);
		shape.getShapes().add(d1);
		shape.getShapes().add(d2);

		assertEquals(DotStyle.BAR, shape.getDotStyle());
	}

	@Test
	public void testGetDotStyleNoDot() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(DotStyle.DOT, shape.getDotStyle());
	}

	@Test
	public void testSetDotStyle() {
		IShape sh = ShapeFactory.INST.createRectangle();
		IDot d1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		IDot d2 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());

		shape.getShapes().add(sh);
		shape.getShapes().add(d1);
		shape.getShapes().add(d2);

		shape.setDotStyle(DotStyle.DIAMOND);

		assertEquals(DotStyle.DIAMOND, d1.getDotStyle());
		assertEquals(DotStyle.DIAMOND, d2.getDotStyle());
	}

	@Override
	@Test
	public void testCopy() {
		// TODO
	}

	@Override
	@Test
	public void testDuplicate() {
		shape2 = shape.duplicate();
		assertNotNull(shape2);
	}

	@Test
	public void testDuplicateArrow() {
		shape.addShape(ShapeFactory.INST.createPolyline());
		shape.setArrowStyle(ArrowStyle.BAR_END, 0);
		shape.setArrowStyle(ArrowStyle.CIRCLE_END, 1);
		shape2 = shape.duplicate();
		assertEquals(ArrowStyle.BAR_END, shape2.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_END, shape2.getArrowStyle(1));
	}

	private IRectangle setRectangle(double x, double y, double w, double h) {
		IRectangle rec = ShapeFactory.INST.createRectangle();
		rec.setPosition(x, y);
		rec.setWidth(w);
		rec.setHeight(h);
		return rec;
	}

	@Override
	@Test
	public void testGetFullBottomRightPoint() {
		assertTrue(Double.isNaN(shape.getFullBottomRightPoint().getX()));
		assertTrue(Double.isNaN(shape.getFullBottomRightPoint().getY()));
		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(rec1.getFullBottomRightPoint(), shape.getFullBottomRightPoint());

		IRectangle rec2 = setRectangle(90, 40, 100, 200);

		shape.addShape(rec2);
		assertEquals(rec2.getFullBottomRightPoint(), shape.getFullBottomRightPoint());
	}

	@Override
	@Test
	public void testGetFullTopLeftPoint() {
		assertTrue(Double.isNaN(shape.getFullTopLeftPoint().getX()));
		assertTrue(Double.isNaN(shape.getFullTopLeftPoint().getY()));
		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(rec2.getFullTopLeftPoint(), shape.getFullTopLeftPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(rec1.getFullTopLeftPoint(), shape.getFullTopLeftPoint());
	}

	@Override
	@Test
	public void testGetBottomLeftPoint() {
		assertTrue(Double.isNaN(shape.getBottomLeftPoint().getX()));
		assertTrue(Double.isNaN(shape.getBottomLeftPoint().getY()));
		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(ShapeFactory.INST.createPoint(90, 40), shape.getBottomLeftPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(ShapeFactory.INST.createPoint(5, 40), shape.getBottomLeftPoint());
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		assertTrue(Double.isNaN(shape.getBottomRightPoint().getX()));
		assertTrue(Double.isNaN(shape.getBottomRightPoint().getY()));
		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(ShapeFactory.INST.createPoint(11, 10), shape.getBottomRightPoint());

		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(ShapeFactory.INST.createPoint(100, 40), shape.getBottomRightPoint());
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		assertTrue(Double.isNaN(shape.getTopLeftPoint().getX()));
		assertTrue(Double.isNaN(shape.getTopLeftPoint().getY()));
		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(ShapeFactory.INST.createPoint(90, 19), shape.getTopLeftPoint());

		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(ShapeFactory.INST.createPoint(5, -10), shape.getTopLeftPoint());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		assertTrue(Double.isNaN(shape.getTopRightPoint().getX()));
		assertTrue(Double.isNaN(shape.getTopRightPoint().getY()));
		IRectangle rec1 = setRectangle(5, 10, 6, 20);
		shape.addShape(rec1);
		assertEquals(ShapeFactory.INST.createPoint(11, -10), shape.getTopRightPoint());

		IRectangle rec2 = setRectangle(90, 40, 10, 21);

		shape.addShape(rec2);
		assertEquals(ShapeFactory.INST.createPoint(100, -10), shape.getTopRightPoint());
	}

	@Override
	@Test
	public void testMirrorHorizontal() {
		// TODO
	}

	@Override
	@Test
	public void testMirrorVertical() {
		// TODO
	}

	@Override
	@Test
	public void testTranslate() {
		// TODO
	}

	@Override
	@Test
	public void testGetGravityCentre() {
		assertEquals(ShapeFactory.INST.createPoint(0, 0), shape.getGravityCentre());

		IRectangle rec1 = setRectangle(100, 2, 924, 12);
		IRectangle rec2 = setRectangle(200, 828, 17, 87);

		shape.addShape(rec1);
		shape.addShape(rec2);
		assertEquals(ShapeFactory.INST.createPoint((100 + 100 + 924) / 2., (828 + 2 - 12) / 2.), shape.getGravityCentre());
	}

	@Override
	@Test
	public void testSetHasHatchings() {
		IRectangle rec1 = ShapeFactory.INST.createRectangle();
		IRectangle rec2 = ShapeFactory.INST.createRectangle();

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
		IGrid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());

		// The test is useful only if the shape is not stylable.
		assertFalse(grid.isInteriorStylable());

		shape.addShape(grid);

		assertFalse(shape.hasHatchings());
		grid.setFillingStyle(FillingStyle.CLINES_PLAIN);
		assertFalse(shape.hasHatchings());

		IRectangle rec1 = ShapeFactory.INST.createRectangle();
		shape.addShape(rec1);
		assertFalse(shape.hasHatchings());

		rec1.setFillingStyle(FillingStyle.HLINES);
		assertTrue(shape.hasHatchings());
	}

	@Override
	@Test
	public void testSetHasGradient() {
		IGrid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());

		// The test is useful only if the shape is not stylable.
		assertFalse(grid.isInteriorStylable());

		shape.addShape(grid);

		assertFalse(shape.hasGradient());
		grid.setFillingStyle(FillingStyle.GRAD);
		assertFalse(shape.hasGradient());

		IRectangle rec1 = ShapeFactory.INST.createRectangle();
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
		IRectangle rec1 = ShapeFactory.INST.createRectangle();
		IRectangle rec2 = ShapeFactory.INST.createRectangle();

		// The test is useful only if the shape is stylable.
		assertTrue(rec1.isInteriorStylable());

		shape.addShape(rec1);
		shape.addShape(rec2);

		shape.setGradColEnd(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, rec1.getGradColEnd());
		assertEquals(DviPsColors.CYAN, rec2.getGradColEnd());
	}

	@Test
	public void testGetGradColEnd() {
		IRectangle rec1 = ShapeFactory.INST.createRectangle();
		IRectangle rec2 = ShapeFactory.INST.createRectangle();

		// The test is useful only if the shape is stylable.
		assertTrue(rec1.isInteriorStylable());

		shape.addShape(rec1);
		shape.addShape(rec2);

		rec1.setGradColEnd(DviPsColors.ORANGE);
		rec2.setGradColEnd(DviPsColors.PINK);

		assertNotNull(shape.getGradColEnd());
		assertNotEquals(DviPsColors.PINK, shape.getGradColEnd());
		assertNotEquals(DviPsColors.ORANGE, shape.getGradColEnd());

		rec1.setFillingStyle(FillingStyle.GRAD);
		rec2.setFillingStyle(FillingStyle.GRAD);

		assertEquals(DviPsColors.ORANGE, shape.getGradColEnd());
	}

	private IAxes init4getAxes() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IAxes sh2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		IAxes sh3 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);
		return sh2;
	}

	@Test
	public void testGetAxesIncrementXOk() {
		init4getAxes().setIncrementX(11.0);
		assertEquals(11.0, shape.getIncrementX(), 0.0);
	}

	@Test
	public void testGetAxesIncrementXNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getIncrementX()));
	}

	@Test
	public void testGetAxesIncrementYOk() {
		init4getAxes().setIncrementY(11.0);
		assertEquals(11.0, shape.getIncrementY(), 0.0);
	}

	@Test
	public void testGetAxesDistLabelsYNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getDistLabelsY()));
	}

	@Test
	public void testGetAxesDistLabelsYOk() {
		init4getAxes().setDistLabelsY(11.0);
		assertEquals(11.0, shape.getDistLabelsY(), 0.0);
	}

	@Test
	public void testGetAxesDistLabelsXNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getDistLabelsX()));
	}

	@Test
	public void testGetAxesDistLabelsXOk() {
		init4getAxes().setDistLabelsX(11.0);
		assertEquals(11.0, shape.getDistLabelsX(), 0.0);
	}

	@Test
	public void testGetAxesIncrementYNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getIncrementY()));
	}

	@Test
	public void testGetAxesTicksSizeOk() {
		init4getAxes().setTicksSize(11.0);
		assertEquals(11.0, shape.getTicksSize(), 0.0);
	}

	@Test
	public void testGetAxesTicksSizeNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getTicksSize()));
	}

	@Test
	public void testGetAxesIncrementOk() {
		IPoint pt = ShapeFactory.INST.createPoint(10d, 11d);
		init4getAxes().setIncrement(pt);
		assertEquals(pt, shape.getIncrement());
	}

	@Test
	public void testGetAxesIncrementNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertNull(shape.getIncrement());
	}

	@Test
	public void testGetAxesDistLabelsOk() {
		IPoint pt = ShapeFactory.INST.createPoint(10d, 11d);
		init4getAxes().setDistLabels(pt);
		assertEquals(pt, shape.getDistLabels());
	}

	@Test
	public void testGetAxesDistLabelsNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertNull(shape.getDistLabels());
	}

	@Test
	public void testGetAxesAxesStyleOk() {
		init4getAxes().setAxesStyle(AxesStyle.FRAME);
		assertEquals(AxesStyle.FRAME, shape.getAxesStyle());
	}

	@Test
	public void testGetAxesAxesStyleNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(AxesStyle.AXES, shape.getAxesStyle());
	}

	@Test
	public void testGetAxesTicksStyleOk() {
		init4getAxes().setTicksStyle(TicksStyle.BOTTOM);
		assertEquals(TicksStyle.BOTTOM, shape.getTicksStyle());
	}

	@Test
	public void testGetAxesTicksStyleNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(TicksStyle.FULL, shape.getTicksStyle());
	}

	@Test
	public void testGetAxesTicksDisplayOk() {
		init4getAxes().setTicksDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, shape.getTicksDisplayed());
	}

	@Test
	public void testGetAxesTicksDisplayNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(PlottingStyle.ALL, shape.getTicksDisplayed());
	}

	@Test
	public void testGetAxesLabelsDisplayOk() {
		init4getAxes().setLabelsDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, shape.getLabelsDisplayed());
	}

	@Test
	public void testGetAxesLabelsDisplayNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(PlottingStyle.ALL, shape.getLabelsDisplayed());
	}

	@Test
	public void testGetAxesShowOriginOk() {
		init4getAxes().setShowOrigin(false);
		assertFalse(shape.isShowOrigin());
	}

	@Test
	public void testGetAxesShowOriginNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertFalse(shape.isShowOrigin());
	}

	private void init4setAxes() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IAxes sh2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		IShape sh1b = ShapeFactory.INST.createRectangle();
		IAxes sh3 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh1b);
		shape.getShapes().add(sh3);
	}

	@Test
	public void testSetAxesLabelsDisplay() {
		init4setAxes();
		shape.setLabelsDisplayed(PlottingStyle.NONE);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(PlottingStyle.NONE, sh.getLabelsDisplayed()));
	}

	@Test
	public void testSetAxesIncrementX() {
		init4setAxes();
		shape.setIncrementX(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(12d, sh.getIncrementX(), 0.0));
	}

	@Test
	public void testSetAxesIncrementY() {
		init4setAxes();
		shape.setIncrementY(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(12d, sh.getIncrementY(), 0.0));
	}

	@Test
	public void testSetAxesDistLabelsX() {
		init4setAxes();
		shape.setDistLabelsX(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(12d, sh.getDistLabelsX(), 0.0));
	}

	@Test
	public void testSetAxesDistLabelsY() {
		init4setAxes();
		shape.setDistLabelsY(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(12d, sh.getDistLabelsY(), 0.0));
	}

	@Test
	public void testSetAxesticksSize() {
		init4setAxes();
		shape.setTicksSize(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(12d, sh.getTicksSize(), 0.0));
	}

	@Test
	public void testSetShowOrigin() {
		init4setAxes();
		shape.setShowOrigin(false);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertFalse(sh.isShowOrigin()));
	}

	@Test
	public void testSetAxesticksDisplayed() {
		init4setAxes();
		shape.setTicksDisplayed(PlottingStyle.X);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(PlottingStyle.X, sh.getTicksDisplayed()));
	}

	@Test
	public void testSetAxesticksStyle() {
		init4setAxes();
		shape.setTicksStyle(TicksStyle.BOTTOM);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(TicksStyle.BOTTOM, sh.getTicksStyle()));
	}

	@Test
	public void testSetAxesStyle() {
		init4setAxes();
		shape.setAxesStyle(AxesStyle.FRAME);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(AxesStyle.FRAME, sh.getAxesStyle()));
	}

	@Test
	public void testSetAxesIncrement() {
		init4setAxes();
		IPoint pt = ShapeFactory.INST.createPoint(13d, 14d);
		shape.setIncrement(pt);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(pt, sh.getIncrement()));
	}

	@Test
	public void testSetAxesdistLabels() {
		init4setAxes();
		IPoint pt = ShapeFactory.INST.createPoint(13d, 14d);
		shape.setDistLabels(pt);
		shape.getShapes().stream().filter(sh -> sh instanceof IAxes).map(sh -> (IAxes)sh).forEach(sh -> assertEquals(pt, sh.getDistLabels()));
	}

	private void init4setFill() {
		IAxes sh2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh1b = ShapeFactory.INST.createRectangle();
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh1);
		shape.getShapes().add(sh1b);
	}

	@Override
	@Test
	public void testGetSetGradColStart() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		shape.setGradColStart(DviPsColors.RED);		assertEquals(java.awt.Color.RED, shape.getGradColStart().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEquals(java.awt.Color.RED, sh.getGradColStart().toAWT()));
	}

	@Override
	@Test
	public void testGetSetGradAngle() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		shape.setGradAngle(1d);
		assertEquals(1d, shape.getGradAngle(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEquals(1d, sh.getGradAngle(), 0d));
	}

	@Override
	@Test
	public void testGetSetGradMidPt() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		shape.setGradMidPt(1d);
		assertEquals(1d, shape.getGradMidPt(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEquals(1d, sh.getGradMidPt(), 0d));
	}

	@Override
	@Test
	public void testGetSetHatchingsSep() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
		shape.setHatchingsSep(1d);
		assertEquals(1d, shape.getHatchingsSep(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEquals(1d, sh.getHatchingsSep(), 0d));
	}

	@Override
	@Test
	public void testGetSetHatchingsCol() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.HLINES);
		shape.setHatchingsCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getHatchingsCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEquals(java.awt.Color.RED, sh.getHatchingsCol().toAWT()));
	}

	@Override
	@Test
	public void testGetSetHatchingsAngle() {
		init4setFill();
		shape.setHatchingsAngle(15d);
		assertEquals(15d, shape.getHatchingsAngle(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEquals(15d, sh.getHatchingsAngle(), 0d));
	}

	@Override
	@Test
	public void testGetSetHatchingsWidth() {
		init4setFill();
		shape.setHatchingsWidth(15d);
		assertEquals(15d, shape.getHatchingsWidth(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEquals(15d, sh.getHatchingsWidth(), 0d));
	}

	@Override
	@Test
	public void testGetSetRotationAngle() {
		init4setFill();
		shape.setRotationAngle(1);
		assertEquals(1d, shape.getRotationAngle(), 0d);
		shape.getShapes().forEach(sh -> assertEquals(1d, sh.getRotationAngle(), 0d));
	}

	@Override
	@Test
	public void testIsSetShowPts() {
		init4setFill();
		shape.addShape(ShapeFactory.INST.createBezierCurve());
		shape.setShowPts(true);
		assertTrue(shape.isShowPts());
		shape.getShapes().stream().filter(sh -> sh.isShowPtsable()).forEach(sh -> assertTrue(sh.isShowPts()));
	}

	@Override
	@Test
	public void testhasSetDbleBord() {
		init4setFill();
		shape.setHasDbleBord(true);
		assertTrue(shape.hasDbleBord());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).forEach(sh -> assertTrue(sh.hasDbleBord()));
	}

	@Override
	@Test
	public void testGetSetDbleBordCol() {
		init4setFill();
		shape.setHasDbleBord(true);
		shape.setDbleBordCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getDbleBordCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).forEach(sh -> assertEquals(java.awt.Color.RED, sh.getDbleBordCol().toAWT()));
	}

	@Override
	@Test
	public void testGetPtAt() {
		assertNull(shape.getPtAt(0));
	}

	@Override
	@Test
	public void testGetNbPoints() {
		assertEquals(0, shape.getNbPoints());
	}

	@Override
	@Test
	public void testGetSetDbleBordSep() {
		init4setFill();
		shape.setHasDbleBord(true);
		shape.setDbleBordSep(15d);
		assertEquals(15d, shape.getDbleBordSep(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).forEach(sh -> assertEquals(15d, sh.getDbleBordSep(), 0d));
	}

	@Override
	@Test
	public void testHasSetShadow() {
		init4setFill();
		shape.setHasShadow(true);
		assertTrue(shape.hasShadow());
		shape.getShapes().stream().filter(sh -> sh.isShadowable()).forEach(sh -> assertTrue(sh.hasShadow()));
	}

	@Override
	@Test
	public void testGetSetShadowCol() {
		init4setFill();
		shape.setHasShadow(true);
		shape.setShadowCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getShadowCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).forEach(sh -> assertEquals(java.awt.Color.RED, sh.getShadowCol().toAWT()));
	}

	@Override
	@Test
	public void testGetSetShadowAngle() {
		init4setFill();
		shape.setHasShadow(true);
		shape.setShadowAngle(1d);
		assertEquals(1d, shape.getShadowAngle(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEquals(1d, sh.getShadowAngle(), 0d));
	}

	@Override
	@Test
	public void testIsSetFilled() {
		init4setFill();
		shape.setFilled(true);
		assertTrue(shape.isFilled());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertTrue(shape.isFilled()));
	}

	@Override
	@Test
	public void testGetSetShadowSize() {
		init4setFill();
		shape.setHasShadow(true);
		shape.setShadowSize(1d);
		assertEquals(1d, shape.getShadowSize(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEquals(1d, sh.getShadowSize(), 0d));
	}

	@Override
	@Test
	public void testGetSetBorderPosition() {
		init4setFill();
		shape.setBordersPosition(BorderPos.MID);
		assertEquals(BorderPos.MID, shape.getBordersPosition());
		shape.getShapes().stream().filter(sh -> sh.isBordersMovable()).forEach(sh -> assertEquals(BorderPos.MID, sh.getBordersPosition()));
	}

	@Override
	@Test
	public void testSetGetThickness() {
		init4setFill();
		shape.setThickness(10d);
		assertEquals(10d, shape.getThickness(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isThicknessable()).forEach(sh -> assertEquals(10d, sh.getThickness(), 0d));
	}

	@Override
	@Test
	public void testSetGetLineColour() {
		init4setFill();
		shape.setLineColour(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getLineColour().toAWT());
		shape.getShapes().forEach(sh -> assertEquals(java.awt.Color.RED, sh.getLineColour().toAWT()));
	}

	@Test
	public void testSetGetLineColourWithDot() {
		init4setFill();
		shape.addShape(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		shape.setLineColour(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getLineColour().toAWT());
		shape.getShapes().forEach(sh -> assertEquals(java.awt.Color.RED, sh.getLineColour().toAWT()));
	}

	@Override
	@Test
	public void testSetGetLineStyle() {
		init4setFill();
		shape.setLineStyle(LineStyle.DASHED);
		assertEquals(LineStyle.DASHED, shape.getLineStyle());
		shape.getShapes().stream().filter(sh -> sh.isBordersMovable()).forEach(sh -> assertEquals(LineStyle.DASHED, sh.getLineStyle()));
	}

	@Override
	@Test
	public void testSetGetDashSepWhite() {
		init4setFill();
		shape.setDashSepWhite(15d);
		assertEquals(15d, shape.getDashSepWhite(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isLineStylable()).forEach(sh -> assertEquals(15d, sh.getDashSepWhite(), 0d));
	}

	@Override
	@Test
	public void testSetGetDashSepBlack() {
		init4setFill();
		shape.setDashSepBlack(15d);
		assertEquals(15d, shape.getDashSepBlack(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isLineStylable()).forEach(sh -> assertEquals(15d, sh.getDashSepBlack(), 0d));
	}

	@Override
	@Test
	public void testSetGetDotSep() {
		init4setFill();
		shape.setDotSep(15d);
		assertEquals(15d, shape.getDotSep(), 0d);
		shape.getShapes().stream().filter(sh -> sh.isLineStylable()).forEach(sh -> assertEquals(15d, sh.getDotSep(), 0d));
	}

	@Override
	@Test
	public void testSetGetFillingCol() {
		init4setFill();
		shape.setFilled(true);
		shape.setFillingCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getFillingCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEquals(java.awt.Color.RED, sh.getFillingCol().toAWT()));
	}

	@Override
	@Test
	public void testSetGetFillingStyle() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		assertEquals(FillingStyle.GRAD, shape.getFillingStyle());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEquals(FillingStyle.GRAD, sh.getFillingStyle()));
	}
}
