package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsline extends TestParsingShape {
	@Test public void testBugTwoSameArrows() throws ParseException {
		IPolyline sh = (IPolyline)parser.parsePSTCode("\\psline{<-<}(-0.1,-0.2)(2,5)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(sh.getArrowAt(0).getArrowStyle(), IArrow.ArrowStyle.LEFT_ARROW);
		assertEquals(sh.getArrowAt(1).getArrowStyle(), IArrow.ArrowStyle.LEFT_ARROW);
		assertTrue(sh.getArrowAt(0)!=sh.getArrowAt(1));
	}


	@Test public void testUnit() throws ParseException {
		IPolyline sh = (IPolyline)parser.parsePSTCode("\\psset{unit=2}\\psline[linewidth=0.3,linestyle=dashed](2,3)(1cm,2cm)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2.*2.*IShape.PPC, sh.getPtAt(0).getX(), 0.0001);
		assertEquals(-3.*2.*IShape.PPC, sh.getPtAt(0).getY(), 0.0001);
		assertEquals(IShape.PPC, sh.getPtAt(1).getX(), 0.0001);
		assertEquals(-2.*IShape.PPC, sh.getPtAt(1).getY(), 0.0001);
	}

	@Test
	public void testcornersizeParsed() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[cornersize=relative](5,10)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testLinearcParsed() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[linearc=0.3cm](5,10)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testMoreThanTwoPointsCoordinates() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(5,10)(6, 7) (1, 2) % foo \n(3, \t4)").get().getShapeAt(0);
		assertEquals(4, line.getNbPoints());
		assertEquals(5.*IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(6.*IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-7.*IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(1.*IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(-2.*IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertEquals(3.*IShape.PPC, line.getPtAt(3).getX(), 0.0001);
		assertEquals(-4.*IShape.PPC, line.getPtAt(3).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test1Coordinates() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(5,10)").get().getShapeAt(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(0., line.getPtAt(0).getX(), 0.0001);
		assertEquals(0., line.getPtAt(0).getY(), 0.0001);
		assertEquals(5.*IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testCoordinatesPt() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(35pt,20pt)(10pt,5pt)").get().getShapeAt(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(35.*IShape.PPC/PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC/PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getY(), 0.0001);
		assertEquals(10.*IShape.PPC/PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-5.*IShape.PPC/PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesMm() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(350mm,200mm)(10mm, 30.3mm)").get().getShapeAt(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(35.*IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.*IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-3.03*IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesInch() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(35in,20in)(1.2in,0.2in)").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC/2.54, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC/2.54, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2*IShape.PPC/2.54, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-0.2*IShape.PPC/2.54, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesCm() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm)(1.2cm,2cm)").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20.*IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2*IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-2.*IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testFloatSigns() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(+++35.5,--50.5)(--+12, -1)").get().getShapeAt(0);
		assertEquals(35.5*IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12.*IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(1.*IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testCoordinatesFloat2() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(35.5,50.5)(12, 1)").get().getShapeAt(0);
		assertEquals(35.5*IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12.*IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-1.*IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testErrorOnNoPoint() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"").get().isEmpty();
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Override
	public String getCommandName() {
		return "psline";
	}


	@Override
	public String getBasicCoordinates() {
		return "(10,10)(20,20)(30,30)";
	}
}
