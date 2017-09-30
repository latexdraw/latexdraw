package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestParsingPsframe extends TestPSTParser {
	@Override
	public String getCommandName() {
		return "psframe";
	}

	@Override
	public String getBasicCoordinates() {
		return "(35,20)";
	}

	@Test
	public void testpssetunityunit() {
		parser("\\psset{unit=2,yunit=3}\\" + getCommandName() + "(1,1)(5,5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(2d * IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-2d * 3d * IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2d * 4d * IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2d * 4d * 3d * IShape.PPC, rec.getHeight(), 0.000001);
	}

	@Test
	public void testpssetunitxunit() {
		parser("\\psset{unit=2,xunit=3}\\" + getCommandName() + "(1,1)(5,5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(2d * 3d * IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2d * 4d * 3d * IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2d * 4d * IShape.PPC, rec.getHeight(), 0.000001);
	}

	@Test
	public void testpssetunit() {
		parser("\\psset{unit=2}\\" + getCommandName() + "(1,1)(5,5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(2d * IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2d * 4d * IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2d * 4d * IShape.PPC, rec.getHeight(), 0.000001);
	}

	@Test
	public void testParamAddfillstyle() {
		parser("\\" + getCommandName() + "[addfillstyle=hlines]" + getBasicCoordinates());
	}

	@Test
	public void testParamBorder() {
		parser("\\" + getCommandName() + "[border=2.1in]" + getBasicCoordinates());
	}

	@Test
	public void testParamDotsep() {
		parser("\\" + getCommandName() + "[dotsep=2.1in]" + getBasicCoordinates());
	}

	@Test
	public void testParamDashNumNum() {
		parser("\\" + getCommandName() + "[dash=2.1 +0.3]" + getBasicCoordinates());
	}

	@Test
	public void testParamDashDimDim() {
		parser("\\" + getCommandName() + "[dash=2cm 0.3 pt]" + getBasicCoordinates());
	}

	@Test
	public void testParamDashDimNum() {
		parser("\\" + getCommandName() + "[dash=2cm 0.3]" + getBasicCoordinates());
	}

	@Theory
	public void testParamFramearcOK(@DoubleData(vals = {0d, 1d, 0.5}) final double arc) {
		parser("\\" + getCommandName() + "[framearc=" + arc + "]" + getBasicCoordinates());
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(arc, rec.getLineArc(), 0.00001);
	}

	@Test
	public void testParamFramearcReset() {
		parser("\\" + getCommandName() + "[framearc=0.2, framearc=0.3]" + getBasicCoordinates());
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0.3, rec.getLineArc(), 0.00001);
	}

	@Theory
	public void testParamFramearcKO(@DoubleData(vals = {-1d, 2}) final double arc) {
		parser("\\" + getCommandName() + "[framearc=" + arc + "]" + getBasicCoordinates());
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getLineArc(), 0.00001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\" + getCommandName() + "(0,0)(35pt,20pt)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getPosition().getX(), 0.001);
		assertEquals(0d, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, rec.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC / PSTricksConstants.CM_VAL_PT, rec.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\" + getCommandName() + "(0,0)(350mm,200mm)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getPosition().getX(), 0.001);
		assertEquals(0d, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\" + getCommandName() + "(0,0)(35in,20in)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getPosition().getX(), 0.001);
		assertEquals(0d, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC / 2.54, rec.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC / 2.54, rec.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\" + getCommandName() + "(0,0)(35cm,20cm)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getPosition().getX(), 0.001);
		assertEquals(0d, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test1Coordinates() {
		parser("\\" + getCommandName() + "(35,20)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getPosition().getX(), 0.001);
		assertEquals(0d, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesIntOppositeAll() {
		parser("\\" + getCommandName() + "(35,50)(10,20)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(10d * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20d * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesIntOppositeX() {
		parser("\\" + getCommandName() + "(35,20)(10,50)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(10d * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20d * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesIntOppositeY() {
		parser("\\" + getCommandName() + "(10,50)(35,20)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(10d * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20d * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesInt() {
		parser("\\" + getCommandName() + "(10,20)(35,50)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(10d * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20d * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloatSigns2() {
		parser("\\" + getCommandName() + "(-+.5,+-5)(+++35.5,--50.5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(-0.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(-5d * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(36d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(55.5 * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloatSigns() {
		parser("\\" + getCommandName() + "(-+-.5,+--.5)(+++35.5,--50.5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(0.5 * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(50d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloat2() {
		parser("\\" + getCommandName() + "(.5,.5)(35.5,50.5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(0.5 * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(50d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloat() {
		parser("\\" + getCommandName() + "(10.5,20.5)(35.5,50.5)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(10.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20.5 * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesTwoFirstMissing() {
		parser("\\" + getCommandName() + "(,)(35,50)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(-IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(34d * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(49d * IShape.PPC, rec.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesTwoLastMissing() {
		parser("\\" + getCommandName() + "(0,0)(,)");
		final IRectangle rec = (IRectangle) listener.getShapes().get(0);
		assertEquals(0d, rec.getPosition().getX(), 0.001);
		assertEquals(0d, rec.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(IShape.PPC, rec.getHeight(), 0.001);
	}
}
