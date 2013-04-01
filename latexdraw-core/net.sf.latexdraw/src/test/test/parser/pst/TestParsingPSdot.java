package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Ignore;
import org.junit.Test;

public class TestParsingPSdot extends TestParsingShape {
	@Test public void test_psset_unit_yunit() throws ParseException {
		IDot dot = (IDot)parser.parsePSTCode("\\psset{unit=2,yunit=3}\\psdot(1,1)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2.*3.*IShape.PPC, dot.getY(), 0.000001);
	}

	@Test public void test_psset_unit_xunit() throws ParseException {
		IDot dot = (IDot)parser.parsePSTCode("\\psset{unit=2,xunit=3}\\psdot(1,1)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2.*3.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2.*IShape.PPC, dot.getY(), 0.000001);
	}

	@Test public void test_psset_dot_unit_dot() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\psdot(1,1)\\psset{unit=2}\\psdot(1,1)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		IDot dot = (IDot)gp.getShapeAt(1);
		assertEquals(2.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2.*IShape.PPC, dot.getY(), 0.000001);
		dot = (IDot)gp.getShapeAt(0);
		assertEquals(IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-IShape.PPC, dot.getY(), 0.000001);
	}

	@Test public void test_psset_unit() throws ParseException {
		IDot dot = (IDot)parser.parsePSTCode("\\psset{unit=2}\\psdot(1,1)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2.*IShape.PPC, dot.getY(), 0.000001);
	}


	@Test
	public void testDotAngle() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotangle=90]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(Math.PI/2., dot.getRotationAngle(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotScale2num() throws ParseException {
		IDot dot1 =  (IDot)parser.parsePSTCode("\\"+getCommandName()+getBasicCoordinates()).get().getShapeAt(0);
		IDot dot2 = (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotscale=2 3]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(dot1.getRadius()*2., dot2.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotScale1num() throws ParseException {
		IDot dot1 =  (IDot)parser.parsePSTCode("\\"+getCommandName()+getBasicCoordinates()).get().getShapeAt(0);
		IDot dot2 = (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotscale=2]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(dot1.getRadius()*2., dot2.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotsizeNoUnit() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotsize=1.5 2]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals((1.5*IShape.PPC+2.*PSTricksConstants.DEFAULT_LINE_WIDTH*IShape.PPC)/2., dot.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotsizeNoNum() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotsize=1.5]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1.5*IShape.PPC/2., dot.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotsizeNoNumWithUnit() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotsize=15 mm]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1.5*IShape.PPC/2., dot.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotsizeNoNumWithWhitespace() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotsize=15 mm]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1.5*IShape.PPC/2., dot.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotsize() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotsize=1.5 cm 4]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals((1.5*IShape.PPC+4.*PSTricksConstants.DEFAULT_LINE_WIDTH*IShape.PPC)/2., dot.getRadius(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStylePentagonStar() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=pentagon*]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.FPENTAGON, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStylePentagon() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=pentagon]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.PENTAGON, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleTriangleStar() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=triangle*]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.FTRIANGLE, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleTriangle() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=triangle]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.TRIANGLE, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleDiamondStar() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=diamond*]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.FDIAMOND, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleDiamond() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=diamond]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.DIAMOND, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleSquareStar() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=square*]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.FSQUARE, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleSquare() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=square]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.SQUARE, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Ignore
	@Test
	public void testDotStyleBpentagon() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=Bpentagon]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBtriangle() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=Btriangle]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBdiamond() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=Bdiamond]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBsquare() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=Bsquare]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBbar() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=B|]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleBar() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=|]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.BAR, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleOtimes() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=otimes]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.OTIMES, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleOplus() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=oplus]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.OPLUS, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBasterisk() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=Basterisk]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleAsterisk() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=asterisk]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.ASTERISK, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBPlus() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=B+]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStylePlus() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=+]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.PLUS, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleX() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=x]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.X, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Ignore
	@Test
	public void testDotStyleBo() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=Bo]"+getBasicCoordinates());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleo() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=o]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.O, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDotStyleDot() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=*]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(DotStyle.DOT, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoDotStyle() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()).get().getShapeAt(0);
		assertEquals(DotStyle.DOT, dot.getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoCoordinate() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()).get().getShapeAt(0);
		assertEquals(0., dot.getPtAt(0).getX(), 0.0001);
		assertEquals(0., dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test1Coordinates() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(5,10)").get().getShapeAt(0);
		assertEquals(5.*IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testCoordinatesPt() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(35pt,20pt)").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC/PSTricksConstants.CM_VAL_PT, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC/PSTricksConstants.CM_VAL_PT, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesMm() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(350mm,200mm)").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesInch() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(35in,20in)").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC/2.54, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC/2.54, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesCm() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm)").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testFloatSigns() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(+++35.5,--50.5)").get().getShapeAt(0);
		assertEquals(35.5*IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testStarFillingParametershaveNoEffect() throws ParseException {
		//
	}


	@Override
	@Test
	public void testStarLineColourIsFillingColour() throws ParseException {
		IDot sh = (IDot)parser.parsePSTCode("\\"+getCommandName()+"*["+"linecolor=green, dotstyle=o]"+getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(Color.GREEN, sh.getFillingCol());
		assertEquals(Color.GREEN, sh.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesFloat2() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(35.5,50.5)").get().getShapeAt(0);
		assertEquals(35.5*IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
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
