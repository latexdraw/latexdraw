package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IAxes;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsaxes extends TestPSTParser {
	@Test public void testArrows() throws ParseException {
		final IAxes grid = (IAxes)parser.parsePSTCode("\\"+getCommandName()+"{|->}(0,0)(0,0)(3,4)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(grid.getArrowAt(0).getArrowStyle(), IArrow.ArrowStyle.BAR_IN);
		assertEquals(grid.getArrowAt(2).getArrowStyle(), IArrow.ArrowStyle.RIGHT_ARROW);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test public void test3Coord() throws ParseException {
		IAxes grid = (IAxes)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(0,0)(3,4)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(0., grid.getOriginX(), 0.0001);
		assertEquals(0., grid.getOriginY(), 0.0001);
		assertEquals(0., grid.getGridStartX(), 0.0001);
		assertEquals(0., grid.getGridStartY(), 0.0001);
		assertEquals(3., grid.getGridEndX(), 0.0001);
		assertEquals(4., grid.getGridEndY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void test3Coord2() throws ParseException {
		IAxes grid = (IAxes)parser.parsePSTCode("\\"+getCommandName()+"(0,0)(1,2)(3,4)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(0., grid.getOriginX(), 0.0001);
		assertEquals(0., grid.getOriginY(), 0.0001);
		assertEquals(1., grid.getGridStartX(), 0.0001);
		assertEquals(2., grid.getGridStartY(), 0.0001);
		assertEquals(3., grid.getGridEndX(), 0.0001);
		assertEquals(4., grid.getGridEndY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Override public String getCommandName() {return "psaxes";} //$NON-NLS-1$

	@Override public String getBasicCoordinates() { return "(0,0)(0,0)(1,1)"; } //$NON-NLS-1$

}
