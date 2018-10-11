package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestParsingPsBegin extends TestPSTParser {
	@Test
	public void testPspictureWithGridAndShapesBasic() {
		parser("\\pspicture(0,0)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\endpspicture");
		IGrid grid = getShapeAt(0);
		IRectangle rec1 = getShapeAt(1);
		IRectangle rec2 = getShapeAt(2);
		assertEquals(0d, grid.getPosition().getX(), 0.001);
		assertEquals(0d, grid.getPosition().getY(), 0.001);
		assertEquals(0d, rec1.getPosition().getX(), 0.001);
		assertEquals(0d, rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double) IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testPspictureWithGridAndShapesComplex() {
		parser("\\pspicture(-3,-3)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\endpspicture");
		IGrid grid = getShapeAt(0);
		IRectangle rec1 = getShapeAt(1);
		IRectangle rec2 = getShapeAt(2);
		assertEquals(0d, grid.getPosition().getX(), 0.001);
		assertEquals(0d, grid.getPosition().getY(), 0.001);
		assertEquals(0d, rec1.getPosition().getX(), 0.001);
		assertEquals(0d, rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double) IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testPspictureWithGrid() {
		parser("\\pspicture\\psgrid\\endpspicture");
		IGrid grid = getShapeAt(0);
		assertEquals(10d, grid.getGridMaxX(), 0.001);
		assertEquals(10d, grid.getGridMaxY(), 0.001);
		assertEquals(0d, grid.getGridMinY(), 0.001);
		assertEquals(0d, grid.getGridMinX(), 0.001);
	}

	@Test
	public void testBeginPsPictureWithGrid() {
		parser("\\begin{pspicture}\\psgrid\\end{pspicture}");
		IGrid grid = getShapeAt(0);
		assertEquals(10d, grid.getGridMaxX(), 0.001);
		assertEquals(10d, grid.getGridMaxY(), 0.001);
		assertEquals(0d, grid.getGridMinY(), 0.001);
		assertEquals(0d, grid.getGridMinX(), 0.001);
	}

	@Test
	public void testBeginPsPictureWithGridAndShapesBasic() {
		parser("\\begin{pspicture}(0,0)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\end{pspicture}");
		IGrid grid = getShapeAt(0);
		IRectangle rec1 = getShapeAt(1);
		IRectangle rec2 = getShapeAt(2);
		assertEquals(0d, grid.getPosition().getX(), 0.001);
		assertEquals(0d, grid.getPosition().getY(), 0.001);
		assertEquals(0d, rec1.getPosition().getX(), 0.001);
		assertEquals(0d, rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double) IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testBeginPsPictureWithGridAndShapesComplex() {
		parser("\\begin{pspicture}(-3,-3)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\end{pspicture}");
		IGrid grid = getShapeAt(0);
		IRectangle rec1 = getShapeAt(1);
		IRectangle rec2 = getShapeAt(2);
		assertEquals(0d, grid.getPosition().getX(), 0.001);
		assertEquals(0d, grid.getPosition().getY(), 0.001);
		assertEquals(0d, rec1.getPosition().getX(), 0.001);
		assertEquals(0d, rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double) IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testBeginPsPictureStar2Coord() {
		parser("\\begin{pspicture*}(0,0)(1,1)\n\n\\end{pspicture*}");
	}

	@Test
	public void testBeginPsPictureStar1Coord() {
		parser("\\begin{pspicture*}(1,1)\n\n\\end{pspicture*}");
	}

	@Test
	public void testBeginPsPicture2Coord() {
		parser("\\begin{pspicture}(0,0)(1,1)\n\n\\end{pspicture}");
	}

	@Test
	public void testBeginPsPicture1Coord() {
		parser("\\begin{pspicture}(1,1)\n\n\\end{pspicture}");
	}
}
