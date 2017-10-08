package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestParsingPsline extends TestPSTParser {
	@Test
	public void testBugTwoSameArrows() {
		parser("\\psline{<-<}(-0.1,-0.2)(2,5)");
		final IPolyline sh = (IPolyline) listener.getShapes().get(0);
		assertEquals(sh.getArrowAt(0).getArrowStyle(), ArrowStyle.LEFT_ARROW);
		assertEquals(sh.getArrowAt(1).getArrowStyle(), ArrowStyle.LEFT_ARROW);
		assertNotEquals(sh.getArrowAt(0), sh.getArrowAt(1));
	}

	@Test
	public void testUnit() {
		parser("\\psset{unit=2}\\psline[linewidth=0.3,linestyle=dashed](2,3)(1cm,2cm)");
		final IPolyline sh = (IPolyline) listener.getShapes().get(0);
		assertEquals(2d * 2d * IShape.PPC, sh.getPtAt(0).getX(), 0.0001);
		assertEquals(-3d * 2d * IShape.PPC, sh.getPtAt(0).getY(), 0.0001);
		assertEquals(IShape.PPC, sh.getPtAt(1).getX(), 0.0001);
		assertEquals(-2d * IShape.PPC, sh.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testcornersizeParsed() {
		parser("\\psline[cornersize=relative](5,10)");
	}

	@Test
	public void testLinearcParsed() {
		parser("\\psline[linearc=0.3cm](5,10)");
	}

	@Test
	public void testMoreThanTwoPointsCoordinates() {
		parser("\\psline(5,10)(6, 7) (1, 2) % foo \n(3, \t4)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(4, line.getNbPoints());
		assertEquals(5d * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-10d * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(6d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-7d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(1d * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(-2d * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertEquals(3d * IShape.PPC, line.getPtAt(3).getX(), 0.0001);
		assertEquals(-4d * IShape.PPC, line.getPtAt(3).getY(), 0.0001);
	}

	@Test
	public void test1Coordinates() {
		parser("\\psline(5,10)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(0d, line.getPtAt(0).getX(), 0.0001);
		assertEquals(0d, line.getPtAt(0).getY(), 0.0001);
		assertEquals(5d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-10d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\psline(35pt,20pt)(10pt,5pt)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getY(), 0.0001);
		assertEquals(10d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-5d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\psline(350mm,200mm)(10mm, 30.3mm)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(35d * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-3.03 * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\psline(35in,20in)(1.2in,0.2in)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(35d * IShape.PPC / 2.54, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / 2.54, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2 * IShape.PPC / 2.54, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-0.2 * IShape.PPC / 2.54, line.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\psline(35cm,20cm)(1.2cm,2cm)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(35d * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2 * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-2d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testFloatSigns() {
		parser("\\psline(+++35.5,--50.5)(--+12, -1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(35.5 * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(1d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesFloat2() {
		parser("\\psline(35.5,50.5)(12, 1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(35.5 * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-1d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
	}
}
