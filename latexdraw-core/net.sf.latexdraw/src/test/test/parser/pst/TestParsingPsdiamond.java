package test.parser.pst;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsdiamond extends TestParsingShape {
	@Override
	public String getCommandName() {
		return "psdiamond";
	}
	
	
	@Override
	public String getBasicCoordinates() {
		return "(1,2)";
	}
	
	
	@Test
	public void testCoordinatesPt() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35pt,20pt)").get().getShapeAt(0);
		assertEquals(-35.*IShape.PPC/PSTricksConstants.CM_VAL_PT, rh.getPosition().getX());
		assertEquals(-20.*IShape.PPC/PSTricksConstants.CM_VAL_PT*-1., rh.getPosition().getY());
		assertEquals(35.*IShape.PPC/PSTricksConstants.CM_VAL_PT*2., rh.getWidth());
		assertEquals(20.*IShape.PPC/PSTricksConstants.CM_VAL_PT*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesMm() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(350mm,200mm)").get().getShapeAt(0);
		assertEquals(-35.*IShape.PPC, rh.getPosition().getX());
		assertEquals(-20.*IShape.PPC*-1., rh.getPosition().getY());
		assertEquals(35.*IShape.PPC*2., rh.getWidth());
		assertEquals(20.*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesInch() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35in,20in)").get().getShapeAt(0);
		assertEquals(-35.*IShape.PPC/2.54, rh.getPosition().getX());
		assertEquals(-20.*IShape.PPC/2.54*-1., rh.getPosition().getY());
		assertEquals(35.*IShape.PPC/2.54*2., rh.getWidth());
		assertEquals(20.*IShape.PPC/2.54*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesCm() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35cm,20cm)").get().getShapeAt(0);
		assertEquals(-35.*IShape.PPC, rh.getPosition().getX());
		assertEquals(-20.*IShape.PPC*-1., rh.getPosition().getY());
		assertEquals(35.*IShape.PPC*2., rh.getWidth());
		assertEquals(20.*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test1Coordinates() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(35,20)").get().getShapeAt(0);
		assertEquals(-35.*IShape.PPC, rh.getPosition().getX());
		assertEquals(-20.*IShape.PPC*-1., rh.getPosition().getY());
		assertEquals(35.*IShape.PPC*2., rh.getWidth());
		assertEquals(20.*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesInt() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(10,20)(35,50)").get().getShapeAt(0);
		assertEquals(10.*IShape.PPC-35.*IShape.PPC, rh.getPosition().getX());
		assertEquals(20.*-IShape.PPC+50.*IShape.PPC, rh.getPosition().getY());
		assertEquals(35.*IShape.PPC*2., rh.getWidth());
		assertEquals(50.*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloatSigns2() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(-+.5,+-5)(+++35.5,--50.5)").get().getShapeAt(0);
		assertEquals(-.5*IShape.PPC-35.5*IShape.PPC, rh.getPosition().getX());
		assertEquals(-5.*-IShape.PPC+50.5*IShape.PPC, rh.getPosition().getY());
		assertEquals(35.5*IShape.PPC*2., rh.getWidth());
		assertEquals(50.5*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloatSigns() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(-+-.5,+--.5)(+++35.5,--50.5)").get().getShapeAt(0);
		assertEquals(.5*IShape.PPC-35.5*IShape.PPC, rh.getPosition().getX());
		assertEquals(.5*-IShape.PPC+50.5*IShape.PPC, rh.getPosition().getY());
		assertEquals(35.5*IShape.PPC*2., rh.getWidth());
		assertEquals(50.5*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(.5,.5)(35.5,50.5)").get().getShapeAt(0);
		assertEquals(.5*IShape.PPC-35.5*IShape.PPC, rh.getPosition().getX());
		assertEquals(.5*-IShape.PPC+50.5*IShape.PPC, rh.getPosition().getY());
		assertEquals(35.5*IShape.PPC*2., rh.getWidth());
		assertEquals(50.5*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloat() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(10.5,20.5)(35.5,50.5)").get().getShapeAt(0);
		assertEquals(10.5*IShape.PPC-35.5*IShape.PPC, rh.getPosition().getX());
		assertEquals(20.5*-IShape.PPC+50.5*IShape.PPC, rh.getPosition().getY());
		assertEquals(35.5*IShape.PPC*2., rh.getWidth());
		assertEquals(50.5*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesTwoFirstMissing() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(,)(35,50)").get().getShapeAt(0);
		assertEquals(PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE*IShape.PPC-35*IShape.PPC, rh.getPosition().getX());
		assertEquals(PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE*-IShape.PPC+50.*IShape.PPC, rh.getPosition().getY());
		assertEquals(35.*IShape.PPC*2., rh.getWidth());
		assertEquals(50.*IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesTwoLastMissing() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(,)").get().getShapeAt(0);
		assertEquals((double)-IShape.PPC, rh.getPosition().getX());
		assertEquals((double)IShape.PPC, rh.getPosition().getY());
		assertEquals(IShape.PPC*2., rh.getWidth());
		assertEquals(IShape.PPC*2., rh.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2WidthHeight0() throws ParseException {
		IRhombus rh = (IRhombus)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(0,0)").get().getShapeAt(0);
		assertTrue(rh.getWidth()>0);
		assertTrue(rh.getHeight()>0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
}
