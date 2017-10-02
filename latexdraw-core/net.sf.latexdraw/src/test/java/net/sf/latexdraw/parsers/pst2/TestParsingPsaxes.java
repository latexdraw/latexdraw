package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.models.interfaces.shape.IAxes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestParsingPsaxes extends TestPSTParser {
	@Test
	public void test3Coord() {
		parser("\\psaxes(0,0)(0,0)(3,4)");
		final IAxes axes = getShapeAt(0);
		assertEquals(0d, axes.getOriginX(), 0.0001);
		assertEquals(0d, axes.getOriginY(), 0.0001);
		assertEquals(0d, axes.getGridStartX(), 0.0001);
		assertEquals(0d, axes.getGridStartY(), 0.0001);
		assertEquals(3d, axes.getGridEndX(), 0.0001);
		assertEquals(4d, axes.getGridEndY(), 0.0001);
	}

	@Test
	public void test3Coord2() {
		parser("\\psaxes(0,0)(1,2)(3,4)");
		final IAxes axes = getShapeAt(0);
		assertEquals(0d, axes.getOriginX(), 0.0001);
		assertEquals(0d, axes.getOriginY(), 0.0001);
		assertEquals(1d, axes.getGridStartX(), 0.0001);
		assertEquals(2d, axes.getGridStartY(), 0.0001);
		assertEquals(3d, axes.getGridEndX(), 0.0001);
		assertEquals(4d, axes.getGridEndY(), 0.0001);
	}
}
