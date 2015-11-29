package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingRPut extends TestPSTParser {
	@Test
	public void testRefPointCombo_t_br() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[t](0,0){\\rput[br](2,2){coucou}}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BOT_RIGHT, txt.getTextPosition());
	}

	@Test
	public void testRefPoint_None() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput(10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.CENTER, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_B() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[B](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BASE, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_Br() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[Br](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BASE_RIGHT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_Bl() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[Bl](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BASE_LEFT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_r() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[r](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.RIGHT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_l() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[l](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.LEFT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_tr() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[tr](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.TOP_RIGHT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_b() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[b](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BOT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_t() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[t](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.TOP, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_tl() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[tl](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.TOP_LEFT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_bl() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[bl](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BOT_LEFT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRefPoint_br() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput[br](10,20){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(TextPosition.BOT_RIGHT, txt.getTextPosition());
		assertEquals(10. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testDoubleRputRotationMustNotRotateOtherShapes() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\rput{10}(0,0){\\rput{80}(0,0){coucou}}\\psframe(10,10)").get(); //$NON-NLS-1$
		IText txt = (IText)gp.getShapeAt(0);
		IRectangle rec = (IRectangle)gp.getShapeAt(1);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(Math.toRadians(-90), txt.getRotationAngle(), 0.001);
		assertEquals(0., rec.getRotationAngle(), 0.001);
	}

	@Test
	public void testTripleRputRotationWithStar() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{10}(0,0){\\rput{*30}(0,0){\\rput{50}(0,0){coucou}}}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(-80., Math.toDegrees(txt.getRotationAngle()), 0.001);
	}

	@Test
	public void testDoubleRputRotation() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{10}(0,0){\\rput{80}(0,0){coucou}}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(Math.toRadians(-90), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testDoubleRputPosition() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput(1,2){\\rput(2,3){coucou}}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(3. * IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-5. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Test
	public void testRPutCoordStarFloatText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{*-90.8929}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(90.8929), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordStarSignedIntText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{*-++-90}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-90.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordStarIntText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{*90}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-90.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationRText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{R}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-270.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationDText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{D}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-180.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationLText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{L}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-90.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationUText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{U}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(0., txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationEText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{E}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-270.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationSText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{S}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-180.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationWText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{W}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(-90.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationNText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{N}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(0., txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordRotationDoubleText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput{-10.0}(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
		assertEquals(Math.toRadians(10.), txt.getRotationAngle(), 0.001);
	}

	@Test
	public void testRPutCoordText() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\rput(1,2){coucou}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("coucou", txt.getText()); //$NON-NLS-1$
		assertEquals(IShape.PPC, txt.getPosition().getX(), 0.001);
		assertEquals(-2. * IShape.PPC, txt.getPosition().getY(), 0.001);
	}

	@Override
	public String getCommandName() {
		return "rput"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return ""; //$NON-NLS-1$
	}
}
