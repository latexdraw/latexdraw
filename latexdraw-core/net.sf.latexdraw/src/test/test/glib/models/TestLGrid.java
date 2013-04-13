package test.glib.models;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIGrid;

public class TestLGrid<T extends IGrid> extends TestIGrid<T> {
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createGrid(false, DrawingTK.getFactory().createPoint());
		shape2 = (T) DrawingTK.getFactory().createGrid(false, DrawingTK.getFactory().createPoint());
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IStandardGrid.class));
		assertTrue(shape.isTypeOf(IGrid.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		IGrid grid = DrawingTK.getFactory().createGrid(false, DrawingTK.getFactory().createPoint());
		assertTrue(grid.getGridEndX()>=grid.getGridStartX());
		assertTrue(grid.getGridEndY()>=grid.getGridStartY());
		HelperTest.assertEqualsDouble(0., grid.getPosition().getX());
		HelperTest.assertEqualsDouble(0., grid.getPosition().getY());
	}
}
