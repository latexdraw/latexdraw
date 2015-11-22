package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsdots extends TestParsingPSdot {
	@Override
	@Test
	public void testDotStyleo() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=o]"+getBasicCoordinates()).get(); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, group.size());
		assertEquals(DotStyle.O, ((IDot)group.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.O, ((IDot)group.getShapeAt(1)).getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testDotStyleDot() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"[dotstyle=*]"+getBasicCoordinates()).get(); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, group.size());
		assertEquals(DotStyle.DOT, ((IDot)group.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.DOT, ((IDot)group.getShapeAt(1)).getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testNoDotStyle() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+getBasicCoordinates()).get(); //$NON-NLS-1$
		assertEquals(2, group.size());
		assertEquals(DotStyle.DOT, ((IDot)group.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.DOT, ((IDot)group.getShapeAt(1)).getDotStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test(expected=ParseException.class)
	public void testNoCoordinate() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()).get().isEmpty(); //$NON-NLS-1$
	}


	@Override
	@Test
	public void test1Coordinates() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"(5,10)").get(); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(1, group.size());
		assertEquals(5.*IShape.PPC, group.getShapeAt(0).getPtAt(0).getX(), 0.0001);
		assertEquals(-10.*IShape.PPC, group.getShapeAt(0).getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testFloatSigns() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"(+++35.5,--50.5)(+-++35.5,---50.5)").get(); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35.5*IShape.PPC, group.getShapeAt(0).getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, group.getShapeAt(0).getPtAt(0).getY(), 0.0001);
		assertEquals(-35.5*IShape.PPC, group.getShapeAt(1).getPtAt(0).getX(), 0.0001);
		assertEquals(50.5*IShape.PPC, group.getShapeAt(1).getPtAt(0).getY(), 0.0001);
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
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"*["+"linecolor=green, dotstyle=o]"+getBasicCoordinates()).get(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(DviPsColors.GREEN, group.getShapeAt(0).getFillingCol());
		assertEquals(DviPsColors.GREEN, group.getShapeAt(0).getLineColour());
		assertEquals(DviPsColors.GREEN, group.getShapeAt(1).getFillingCol());
		assertEquals(DviPsColors.GREEN, group.getShapeAt(1).getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testCoordinatesFloat2() throws ParseException {
		IDot dot =  (IDot)parser.parsePSTCode("\\"+getCommandName()+"(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35.5*IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void test2Points() throws ParseException {
		IGroup group =  parser.parsePSTCode("\\"+getCommandName()+"(35.5,50.5)(2,2)").get(); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IDot);
		assertTrue(group.getShapeAt(1) instanceof IDot);
		assertEquals(35.5*IShape.PPC, group.getShapeAt(0).getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5*IShape.PPC, group.getShapeAt(0).getPtAt(0).getY(), 0.0001);
		assertEquals(2.*IShape.PPC, group.getShapeAt(1).getPtAt(0).getX(), 0.0001);
		assertEquals(-2.*IShape.PPC, group.getShapeAt(1).getPtAt(0).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Override
	public String getCommandName() {
		return "psdots"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(1,1)(2,2)"; //$NON-NLS-1$
	}
}
