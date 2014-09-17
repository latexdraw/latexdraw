package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPstriangle extends TestParsingShape {
	@Override
	public String getCommandName() {
		return "pstriangle"; //$NON-NLS-1$
	}


	@Override
	public String getBasicCoordinates() {
		return "(1,2)"; //$NON-NLS-1$
	}


	@Test
	public void testGangle() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"[gangle=180](2,2)(4,1)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., tri.getPosition().getX(), 0.001);
		assertEquals(-1.*IShape.PPC, tri.getPosition().getY(), 0.001);
		assertEquals(4.*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(1.*IShape.PPC, tri.getHeight(), 0.001);
		assertEquals(Math.toRadians(180.), tri.getRotationAngle(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testHeightNegative() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(35,-10)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10.*IShape.PPC, tri.getHeight(), 0.001);
		assertEquals(Math.PI, tri.getRotationAngle(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesPt() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35pt,20pt)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35./2.*IShape.PPC/PSTricksConstants.CM_VAL_PT, tri.getPosition().getX(), 0.001);
		assertEquals(0., tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC/PSTricksConstants.CM_VAL_PT, tri.getWidth(), 0.001);
		assertEquals(20.*IShape.PPC/PSTricksConstants.CM_VAL_PT, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesMm() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(350mm,200mm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35./2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(0., tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(20.*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesInch() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35in,20in)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35./2.*IShape.PPC/2.54, tri.getPosition().getX(), 0.001);
		assertEquals(0., tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC/2.54, tri.getWidth(), 0.001);
		assertEquals(20.*IShape.PPC/2.54, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesCm() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(35cm,20cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35./2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(0., tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(20.*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test1Coordinates() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(35,20)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35./2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(0., tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(20.*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesInt() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(10,20)(35,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10.*IShape.PPC-35./2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(20.*-IShape.PPC, tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(50.*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesFloatSigns2() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(-+.5,+-5)(+++35.5,--50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(-5.*-IShape.PPC, tri.getPosition().getY(), 0.001);
		assertEquals(35.5*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(50.5*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesFloatSigns() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(-+-.5,+--.5)(+++35.5,--50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(.5*-IShape.PPC, tri.getPosition().getY(), 0.001);
		assertEquals(35.5*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(50.5*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(.5,.5)(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(.5*-IShape.PPC, tri.getPosition().getY(), 0.001);
		assertEquals(35.5*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(50.5*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesFloat() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(10.5,20.5)(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10.5*IShape.PPC-35.5/2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(20.5*-IShape.PPC, tri.getPosition().getY(), 0.001);
		assertEquals(35.5*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(50.5*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesTwoFirstMissing() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(,)(35,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(IShape.PPC-35./2.*IShape.PPC, tri.getPosition().getX(), 0.001);
		assertEquals(IShape.PPC*-1., tri.getPosition().getY(), 0.001);
		assertEquals(35.*IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(50.*IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesTwoLastMissing() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(,)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-IShape.PPC/2., tri.getPosition().getX(), 0.001);
		assertEquals(0., tri.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, tri.getWidth(), 0.001);
		assertEquals(IShape.PPC, tri.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2WidthHeight0() throws ParseException {
		ITriangle tri = (ITriangle)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(0,0)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(tri.getWidth()>0);
		assertTrue(tri.getHeight()>0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
}
