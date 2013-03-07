package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPSbezier extends TestParsingShape {
	@Test public void testSimpleBezierCurve() throws ParseException {
		IBezierCurve bc = (IBezierCurve)parser.parsePSTCode(
				"\\psbezier[linewidth=0.02](1.3918242,0.7584497)(2.0668242,0.95844966)(4.3168244,0.95844966)(4.991824,0.7584497)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(bc.getPtAt(0).getY(), bc.getPtAt(1).getY(), 0.001);
		assertEquals(bc.getFirstCtrlPtAt(0).getY(), bc.getFirstCtrlPtAt(1).getY(), 0.001);
		assertFalse(bc.isFilled());
	}

	@Test
	public void testParamArrowsArrowsNoneNone() throws ParseException {
		IBezierCurve line = (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"[arrows=<->]{-}(1,2)(3,4)(5,6)(7,8)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamBarInSqureBracket() throws ParseException {
		IBezierCurve line = (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"{|-]}(1,2)(3,4)(5,6)(7,8)").get().getShapeAt(0);
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsArrows() throws ParseException {
		IBezierCurve line = (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"[arrows=<->](1,2)(3,4)(5,6)(7,8)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test9Coordinates() throws ParseException {
		IBezierCurve bc =  (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"(1,2)(3,4)(5,6)(7,8)(9,10)(11,12)(13,14)(15,16)(17,18)").get().getShapeAt(0);
		assertEquals(4, bc.getNbPoints());
		assertEquals(0., bc.getPtAt(0).getX(), 0.0001);
		assertEquals(0., bc.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, bc.getPtAt(1).getX(), 0.0001);
		assertEquals(-6.*IShape.PPC, bc.getPtAt(1).getY(), 0.0001);
		assertEquals(11.*IShape.PPC, bc.getPtAt(2).getX(), 0.0001);
		assertEquals(-12.*IShape.PPC, bc.getPtAt(2).getY(), 0.0001);
		assertEquals(17.*IShape.PPC, bc.getPtAt(3).getX(), 0.0001);
		assertEquals(-18.*IShape.PPC, bc.getPtAt(3).getY(), 0.0001);
		assertEquals(-1.*IShape.PPC, bc.getSecondCtrlPtAt(0).getX(), 0.0001);
		assertEquals(2.*IShape.PPC, bc.getSecondCtrlPtAt(0).getY(), 0.0001);
		assertEquals(7.*IShape.PPC, bc.getSecondCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-8.*IShape.PPC, bc.getSecondCtrlPtAt(1).getY(), 0.0001);
		assertEquals(13.*IShape.PPC, bc.getSecondCtrlPtAt(2).getX(), 0.0001);
		assertEquals(-14.*IShape.PPC, bc.getSecondCtrlPtAt(2).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, bc.getFirstCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, bc.getFirstCtrlPtAt(1).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getFirstCtrlPtAt(2).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getFirstCtrlPtAt(2).getY(), 0.0001);
		assertEquals(15.*IShape.PPC, bc.getFirstCtrlPtAt(3).getX(), 0.0001);
		assertEquals(-16.*IShape.PPC, bc.getFirstCtrlPtAt(3).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test10Coordinates() throws ParseException {
		IBezierCurve bc =  (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"(5,10)(1,2)(3,4)(5,6)(7,8)(9,10)(11,12)(13,14)(15,16)(17,18)").get().getShapeAt(0);
		assertEquals(4, bc.getNbPoints());
		assertEquals(5.*IShape.PPC, bc.getPtAt(0).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, bc.getPtAt(1).getX(), 0.0001);
		assertEquals(-6.*IShape.PPC, bc.getPtAt(1).getY(), 0.0001);
		assertEquals(11.*IShape.PPC, bc.getPtAt(2).getX(), 0.0001);
		assertEquals(-12.*IShape.PPC, bc.getPtAt(2).getY(), 0.0001);
		assertEquals(17.*IShape.PPC, bc.getPtAt(3).getX(), 0.0001);
		assertEquals(-18.*IShape.PPC, bc.getPtAt(3).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getSecondCtrlPtAt(0).getX(), 0.0001);
		assertEquals(-18.*IShape.PPC, bc.getSecondCtrlPtAt(0).getY(), 0.0001);
		assertEquals(7.*IShape.PPC, bc.getSecondCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-8.*IShape.PPC, bc.getSecondCtrlPtAt(1).getY(), 0.0001);
		assertEquals(13.*IShape.PPC, bc.getSecondCtrlPtAt(2).getX(), 0.0001);
		assertEquals(-14.*IShape.PPC, bc.getSecondCtrlPtAt(2).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, bc.getFirstCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, bc.getFirstCtrlPtAt(1).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getFirstCtrlPtAt(2).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getFirstCtrlPtAt(2).getY(), 0.0001);
		assertEquals(15.*IShape.PPC, bc.getFirstCtrlPtAt(3).getX(), 0.0001);
		assertEquals(-16.*IShape.PPC, bc.getFirstCtrlPtAt(3).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test6Coordinates() throws ParseException {
		IBezierCurve bc =  (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"(1,2)(3,4)(5,6)(7,8)(9,10)(11,12)").get().getShapeAt(0);
		assertEquals(3, bc.getNbPoints());
		assertEquals(0., bc.getPtAt(0).getX(), 0.0001);
		assertEquals(0., bc.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, bc.getPtAt(1).getX(), 0.0001);
		assertEquals(-6.*IShape.PPC, bc.getPtAt(1).getY(), 0.0001);
		assertEquals(11.*IShape.PPC, bc.getPtAt(2).getX(), 0.0001);
		assertEquals(-12.*IShape.PPC, bc.getPtAt(2).getY(), 0.0001);
		assertEquals(-1.*IShape.PPC, bc.getSecondCtrlPtAt(0).getX(), 0.0001);
		assertEquals(2.*IShape.PPC, bc.getSecondCtrlPtAt(0).getY(), 0.0001);
		assertEquals(7.*IShape.PPC, bc.getSecondCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-8.*IShape.PPC, bc.getSecondCtrlPtAt(1).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, bc.getFirstCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, bc.getFirstCtrlPtAt(1).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getFirstCtrlPtAt(2).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getFirstCtrlPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test7Coordinates() throws ParseException {
		IBezierCurve bc =  (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"(5,10)(1,2)(3,4)(5,6)(7,8)(9,10)(11,12)").get().getShapeAt(0);
		assertEquals(3, bc.getNbPoints());
		assertEquals(5.*IShape.PPC, bc.getPtAt(0).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, bc.getPtAt(1).getX(), 0.0001);
		assertEquals(-6.*IShape.PPC, bc.getPtAt(1).getY(), 0.0001);
		assertEquals(11.*IShape.PPC, bc.getPtAt(2).getX(), 0.0001);
		assertEquals(-12.*IShape.PPC, bc.getPtAt(2).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getSecondCtrlPtAt(0).getX(), 0.0001);
		assertEquals(-18.*IShape.PPC, bc.getSecondCtrlPtAt(0).getY(), 0.0001);
		assertEquals(7.*IShape.PPC, bc.getSecondCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-8.*IShape.PPC, bc.getSecondCtrlPtAt(1).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, bc.getFirstCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, bc.getFirstCtrlPtAt(1).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getFirstCtrlPtAt(2).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getFirstCtrlPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test3Coordinates() throws ParseException {
		IBezierCurve bc =  (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"(1,2)(3,4)(5,6)").get().getShapeAt(0);
		assertEquals(2, bc.getNbPoints());
		assertEquals(0., bc.getPtAt(0).getX(), 0.0001);
		assertEquals(0., bc.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, bc.getPtAt(1).getX(), 0.0001);
		assertEquals(-6.*IShape.PPC, bc.getPtAt(1).getY(), 0.0001);
		assertEquals(-1.*IShape.PPC, bc.getSecondCtrlPtAt(0).getX(), 0.0001);
		assertEquals(2.*IShape.PPC, bc.getSecondCtrlPtAt(0).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, bc.getFirstCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, bc.getFirstCtrlPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void test4Coordinates() throws ParseException {
		IBezierCurve bc =  (IBezierCurve)parser.parsePSTCode("\\"+getCommandName()+"(5,10)(1,2)(3,4)(5,6)").get().getShapeAt(0);
		assertEquals(2, bc.getNbPoints());
		assertEquals(5.*IShape.PPC, bc.getPtAt(0).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, bc.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, bc.getPtAt(1).getX(), 0.0001);
		assertEquals(-6.*IShape.PPC, bc.getPtAt(1).getY(), 0.0001);
		assertEquals(9.*IShape.PPC, bc.getSecondCtrlPtAt(0).getX(), 0.0001);
		assertEquals(-18.*IShape.PPC, bc.getSecondCtrlPtAt(0).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, bc.getFirstCtrlPtAt(1).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, bc.getFirstCtrlPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "psbezier";
	}

	@Override
	public String getBasicCoordinates() {
		return "(0,0)(1,1)(2,2)(3,3)";
	}
}
