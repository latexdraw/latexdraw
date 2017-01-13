package test.models;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.Before;
import org.junit.Test;
import test.models.interfaces.TestIGrid;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLGrid extends TestIGrid<IGrid> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		shape2 = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
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
		IGrid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		assertTrue(grid.getGridEndX() >= grid.getGridStartX());
		assertTrue(grid.getGridEndY() >= grid.getGridStartY());
		assertEqualsDouble(0., grid.getPosition().getX());
		assertEqualsDouble(0., grid.getPosition().getY());
	}
}
