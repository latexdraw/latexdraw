package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestParsingPSdot extends TestPSTParser {
	@Test
	public void testpssetunityunit() {
		parser("\\psset{unit=2,yunit=3}\\psdot(1,1)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(2d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * 3d * IShape.PPC, dot.getY(), 0.000001);
	}

	@Test
	public void testpssetunitxunit() {
		parser("\\psset{unit=2,xunit=3}\\psdot(1,1)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(2d * 3d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, dot.getY(), 0.000001);
	}

	@Test
	public void testpssetdotunitdot() {
		parser("\\psdot(1,1)\\psset{unit=2}\\psdot(1,1)");
		IDot dot = (IDot) listener.getShapes().get(1);
		assertEquals(2d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, dot.getY(), 0.000001);
		dot = (IDot) listener.getShapes().get(0);
		assertEquals(IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-IShape.PPC, dot.getY(), 0.000001);
	}

	@Test
	public void testpssetunit() {
		parser("\\psset{unit=2}\\psdot(1,1)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(2d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, dot.getY(), 0.000001);
	}

	@Test
	public void testDotAngle() {
		parser("\\" + getCommandName() + "[dotangle=90]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(Math.PI / 2d, dot.getRotationAngle(), 0.001);
	}

	@Test
	public void testDotScale2num() {
		parser("\\" + getCommandName() + getBasicCoordinates() + "\\" + getCommandName() + "[dotscale=2 3]" + getBasicCoordinates());
		final IDot dot1 = (IDot) listener.getShapes().get(0);
		final IDot dot2 = (IDot) listener.getShapes().get(1);
		assertEquals(dot1.getDiametre() * 2d, dot2.getDiametre(), 0.001);
	}

	@Test
	public void testDotScale1num() {
		parser("\\" + getCommandName() + getBasicCoordinates() + "\\" + getCommandName() + "[dotscale=2]" + getBasicCoordinates());
		final IDot dot1 = (IDot) listener.getShapes().get(0);
		final IDot dot2 = (IDot) listener.getShapes().get(1);
		assertEquals(dot1.getDiametre() * 2d, dot2.getDiametre(), 0.001);
	}

	@Test
	public void testDotsizeNoUnit() {
		parser("\\" + getCommandName() + "[dotsize=1.5 2]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC + 2d * PSTricksConstants.DEFAULT_LINE_WIDTH * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Test
	public void testDotsizeNoNum() {
		parser("\\" + getCommandName() + "[dotsize=1.5]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Test
	public void testDotsizeNoNumWithUnit() {
		parser("\\" + getCommandName() + "[dotsize=15 mm]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Test
	public void testDotsizeNoNumWithWhitespace() {
		parser("\\" + getCommandName() + "[dotsize=15 mm]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Test
	public void testDotsize() {
		parser("\\" + getCommandName() + "[dotsize=1.5 cm 4]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC + 4d * PSTricksConstants.DEFAULT_LINE_WIDTH * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Theory
	public void testDotStyleOK(final DotStyle style) {
		parser("\\" + getCommandName() + "[dotstyle=" + style.getPSTToken() + "]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(style, dot.getDotStyle());
	}

	@Test
	public void testNoDotStyle() {
		parser("\\" + getCommandName());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(DotStyle.DOT, dot.getDotStyle());
	}

	@Test
	public void testNoCoordinate() {
		parser("\\" + getCommandName());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(0d, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(0d, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void test1Coordinates() {
		parser("\\" + getCommandName() + "(5,10)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(5d * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-10d * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\" + getCommandName() + "(35pt,20pt)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / PSTricksConstants.CM_VAL_PT, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\" + getCommandName() + "(350mm,200mm)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35d * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\" + getCommandName() + "(35in,20in)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35d * IShape.PPC / 2.54, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / 2.54, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\" + getCommandName() + "(35cm,20cm)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35d * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testFloatSigns() {
		parser("\\" + getCommandName() + "(+++35.5,--50.5)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35.5 * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testStarLineColourIsFillingColour() {
		parser("\\" + getCommandName() + "*[" + "linecolor=green, dotstyle=o]" + getBasicCoordinates());
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(DviPsColors.GREEN, dot.getFillingCol());
		assertEquals(DviPsColors.GREEN, dot.getLineColour());
	}

	@Test
	public void testCoordinatesFloat2() {
		parser("\\" + getCommandName() + "(35.5,50.5)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35.5 * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Override
	public String getCommandName() {
		return "psdot";
	}

	@Override
	public String getBasicCoordinates() {
		return "(1,1)";
	}
}
