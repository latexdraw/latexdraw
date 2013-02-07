package test.parser.pst;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPstriangle extends TestParsingShape {
	@Override
	public String getCommandName() {
		return "pstriangle";
	}
	
	
	@Override
	public String getBasicCoordinates() {
		return "(1,2)";
	}
	
	
	@Test
	public void testGangle() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"[gangle=180](2,2)(4,1)").get().getShapeAt(0);
		assertEquals(0., tri.getPosition().getX());
		assertEquals(-1.*IShape.PPC, tri.getPosition().getY());
		assertEquals(4.*IShape.PPC, tri.getWidth());
		assertEquals(1.*IShape.PPC, tri.getHeight());
		assertEquals(Math.toRadians(180.), tri.getRotationAngle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testHeightNegative() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(35,-10)").get().getShapeAt(0);
		assertEquals(10.*IShape.PPC, tri.getHeight());
		assertEquals(Math.PI, tri.getRotationAngle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesPt() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35pt,20pt)").get().getShapeAt(0);
		assertEquals(-35./2.*IShape.PPC/PSTricksConstants.CM_VAL_PT, tri.getPosition().getX());
		assertEquals(0., tri.getPosition().getY());
		assertEquals(35.*IShape.PPC/PSTricksConstants.CM_VAL_PT, tri.getWidth());
		assertEquals(20.*IShape.PPC/PSTricksConstants.CM_VAL_PT, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesMm() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(350mm,200mm)").get().getShapeAt(0);
		assertEquals(-35./2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(0., tri.getPosition().getY());
		assertEquals(35.*IShape.PPC, tri.getWidth());
		assertEquals(20.*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesInch() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35in,20in)").get().getShapeAt(0);
		assertEquals(-35./2.*IShape.PPC/2.54, tri.getPosition().getX());
		assertEquals(0., tri.getPosition().getY());
		assertEquals(35.*IShape.PPC/2.54, tri.getWidth());
		assertEquals(20.*IShape.PPC/2.54, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testCoordinatesCm() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35cm,20cm)").get().getShapeAt(0);
		assertEquals(-35./2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(0., tri.getPosition().getY());
		assertEquals(35.*IShape.PPC, tri.getWidth());
		assertEquals(20.*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test1Coordinates() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(35,20)").get().getShapeAt(0);
		assertEquals(-35./2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(0., tri.getPosition().getY());
		assertEquals(35.*IShape.PPC, tri.getWidth());
		assertEquals(20.*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesInt() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(10,20)(35,50)").get().getShapeAt(0);
		assertEquals(10.*IShape.PPC-35./2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(20.*-IShape.PPC, tri.getPosition().getY());
		assertEquals(35.*IShape.PPC, tri.getWidth());
		assertEquals(50.*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloatSigns2() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(-+.5,+-5)(+++35.5,--50.5)").get().getShapeAt(0);
		assertEquals(-.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(-5.*-IShape.PPC, tri.getPosition().getY());
		assertEquals(35.5*IShape.PPC, tri.getWidth());
		assertEquals(50.5*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloatSigns() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(-+-.5,+--.5)(+++35.5,--50.5)").get().getShapeAt(0);
		assertEquals(.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(.5*-IShape.PPC, tri.getPosition().getY());
		assertEquals(35.5*IShape.PPC, tri.getWidth());
		assertEquals(50.5*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(.5,.5)(35.5,50.5)").get().getShapeAt(0);
		assertEquals(.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(.5*-IShape.PPC, tri.getPosition().getY());
		assertEquals(35.5*IShape.PPC, tri.getWidth());
		assertEquals(50.5*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesFloat() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(10.5,20.5)(35.5,50.5)").get().getShapeAt(0);
		assertEquals(10.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(20.5*-IShape.PPC, tri.getPosition().getY());
		assertEquals(35.5*IShape.PPC, tri.getWidth());
		assertEquals(50.5*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesTwoFirstMissing() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(,)(35,50)").get().getShapeAt(0);
		assertEquals(PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE*IShape.PPC-35./2.*IShape.PPC, tri.getPosition().getX());
		assertEquals(PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE*IShape.PPC*-1., tri.getPosition().getY());
		assertEquals(35.*IShape.PPC, tri.getWidth());
		assertEquals(50.*IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordinatesTwoLastMissing() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(,)").get().getShapeAt(0);
		assertEquals(-IShape.PPC/2., tri.getPosition().getX());
		assertEquals(0., tri.getPosition().getY());
		assertEquals((double)IShape.PPC, tri.getWidth());
		assertEquals((double)IShape.PPC, tri.getHeight());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2WidthHeight0() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(0,0)").get().getShapeAt(0);
		assertTrue(tri.getWidth()>0);
		assertTrue(tri.getHeight()>0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
}
