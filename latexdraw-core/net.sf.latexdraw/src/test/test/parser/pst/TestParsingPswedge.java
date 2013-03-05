package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPswedge extends TestParsingShape {
	@Test
	public void testAngle1Angle2() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{200}{100}").get().getShapeAt(0);
		assertEquals(Math.toRadians(200.), arc.getAngleStart(), 0.0000001);
		assertEquals(Math.toRadians(100.), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{100}{200}").get().getShapeAt(0);
		assertEquals(Math.toRadians(100.), arc.getAngleStart(), 0.0000001);
		assertEquals(Math.toRadians(200.), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testAngle2() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{11}{20}").get().getShapeAt(0);
		assertEquals(Math.toRadians(20.), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{11}{-200.15}").get().getShapeAt(0);
		assertEquals(Math.toRadians(-200.15), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testAngle1() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{10}{20}").get().getShapeAt(0);
		assertEquals(Math.toRadians(10.), arc.getAngleStart(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{-10.12}{20}").get().getShapeAt(0);
		assertEquals(Math.toRadians(-10.12), arc.getAngleStart(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testBadAngle2() {
		try {
			assertTrue(parser.parsePSTCode("\\"+getCommandName()+"{2}{10}{foo}").get().isEmpty());
			assertFalse(PSTParser.errorLogs().isEmpty());
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testBadAngle1() {
		try {
			assertTrue(parser.parsePSTCode("\\"+getCommandName()+"{2}{foo}{20}").get().isEmpty());
			assertFalse(PSTParser.errorLogs().isEmpty());
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testBadRadius() {
		try {
			assertTrue(parser.parsePSTCode("\\"+getCommandName()+"{foo}{10}{20}").get().isEmpty());
			assertFalse(PSTParser.errorLogs().isEmpty());
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testMissingOrigin() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{10}{20}").get().getShapeAt(0);
		assertEquals(-10.*IShape.PPC, arc.getPosition().getX(), 0.0000001);
		assertEquals(-10.*IShape.PPC*-1., arc.getPosition().getY(), 0.0000001);
		assertEquals(10.*IShape.PPC*2., arc.getWidth(), 0.0000001);
		assertEquals(10.*IShape.PPC*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesPt() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(35pt,20pt){10pt}{10}{20}").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC/PSTricksConstants.CM_VAL_PT-10.*IShape.PPC/PSTricksConstants.CM_VAL_PT, arc.getPosition().getX(), 0.0000001);
		assertEquals((20.*IShape.PPC/PSTricksConstants.CM_VAL_PT-10.*IShape.PPC/PSTricksConstants.CM_VAL_PT)*-1., arc.getPosition().getY(), 0.0000001);
		assertEquals(10.*IShape.PPC/PSTricksConstants.CM_VAL_PT*2., arc.getWidth(), 0.0000001);
		assertEquals(10.*IShape.PPC/PSTricksConstants.CM_VAL_PT*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesMm() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(350mm,200mm){10mm}{10}{20}").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC-1.*IShape.PPC, arc.getPosition().getX(), 0.0000001);
		assertEquals((20.*IShape.PPC-1.*IShape.PPC)*-1., arc.getPosition().getY(), 0.0000001);
		assertEquals(1.*IShape.PPC*2., arc.getWidth(), 0.0000001);
		assertEquals(1.*IShape.PPC*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesInch() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(35in,20in){1.2in}{10}{20}").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC/2.54-1.2*IShape.PPC/2.54, arc.getPosition().getX(), 0.0000001);
		assertEquals((20.*IShape.PPC/2.54-1.2*IShape.PPC/2.54)*-1., arc.getPosition().getY(), 0.0000001);
		assertEquals(1.2*IShape.PPC/2.54*2., arc.getWidth(), 0.0000001);
		assertEquals(1.2*IShape.PPC/2.54*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesCm() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm){.5cm}{10}{20}").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC-.5*IShape.PPC, arc.getPosition().getX(), 0.001);
		assertEquals((20.*IShape.PPC-.5*IShape.PPC)*-1., arc.getPosition().getY(), 0.001);
		assertEquals(.5*IShape.PPC*2., arc.getWidth(), 0.0000001);
		assertEquals(.5*IShape.PPC*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesRadius() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(35,20){10}{10}{20}").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC-10.*IShape.PPC, arc.getPosition().getX(), 0.0000001);
		assertEquals((20.*IShape.PPC-10.*IShape.PPC)*-1., arc.getPosition().getY(), 0.0000001);
		assertEquals(10.*IShape.PPC*2., arc.getWidth(), 0.0000001);
		assertEquals(10.*IShape.PPC*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testFloatSigns() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(+++35.5,--50.5){--+12}{10}{20}").get().getShapeAt(0);
		assertEquals(35.5*IShape.PPC-12.*IShape.PPC, arc.getPosition().getX(), 0.001);
		assertEquals((50.5*IShape.PPC-12.*IShape.PPC)*-1., arc.getPosition().getY(), 0.001);
		assertEquals(12.*IShape.PPC*2., arc.getWidth(), 0.0000001);
		assertEquals(12.*IShape.PPC*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testNegativeRadius() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(0,0){-1}{10}{20}").get().getShapeAt(0);
		assertTrue(arc.getWidth()>0);
		assertTrue(arc.getHeight()>0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"(35.5,50.5){1.25}{10}{20}").get().getShapeAt(0);
		assertEquals(35.5*IShape.PPC-1.25*IShape.PPC, arc.getPosition().getX(), 0.001);
		assertEquals((50.5*IShape.PPC-1.25*IShape.PPC)*-1., arc.getPosition().getY(), 0.001);
		assertEquals(1.25*IShape.PPC*2., arc.getWidth(), 0.0000001);
		assertEquals(1.25*IShape.PPC*2., arc.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCoordinatesMissing() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"(,){1}").get().isEmpty();
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testErrorOnNoAngle2() throws Exception {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"(,){1}{30}{}").get().isEmpty();
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testErrorOnNoAngle1() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"(,){1}{}{30}").get().isEmpty();
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testErrorOnNoRadius() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"(,){}{10}{30}").get().isEmpty();
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Override
	public String getCommandName() {
		return "pswedge";
	}


	@Override
	public String getBasicCoordinates() {
		return "{1}{10}{90}";
	}
}
