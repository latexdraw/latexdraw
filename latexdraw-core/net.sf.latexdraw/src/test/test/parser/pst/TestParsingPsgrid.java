package test.parser.pst;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsgrid extends TestPSTParser {
	@Test
	public void testGridXUnit() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[xunit=20in]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1., grid.getUnit(), 0.00001);
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[xunit=0.3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1., grid.getUnit(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridYUnit() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[yunit=20in]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1., grid.getUnit(), 0.00001);
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[yunit=0.3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1., grid.getUnit(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridUnit() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[unit=20in]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(20./PSTricksConstants.INCH_VAL_CM, grid.getUnit(), 0.00001);
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[unit=0.3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(0.3, grid.getUnit(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridSubGridWidth() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgridwidth=20in]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(20.*IShape.PPC/PSTricksConstants.INCH_VAL_CM, grid.getSubGridWidth(), 0.00001);
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgridwidth=0.3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(0.3*IShape.PPC, grid.getSubGridWidth(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridLabels() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[gridlabels=20in]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals((int)(20.*IShape.PPC/PSTricksConstants.INCH_VAL_CM), grid.getLabelsSize());
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[gridlabels=0.3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals((int)(0.3*IShape.PPC), grid.getLabelsSize());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridSubGridDiv() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgriddiv=3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(3, grid.getSubGridDiv());
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgriddiv=0]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(0, grid.getSubGridDiv());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testSubGridDots() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgriddots=3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(3, grid.getSubGridDots());
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgriddots=0]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(0, grid.getSubGridDots());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridDots() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[griddots=3]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(3, grid.getGridDots());
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[griddots=0]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(0, grid.getGridDots());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testSubGridLabelColor() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[subgridcolor=green]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(Color.GREEN, grid.getSubGridColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridLabelColor() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[gridlabelcolor=green]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(Color.GREEN, grid.getGridLabelsColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridColor() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[gridcolor=green]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(Color.GREEN, grid.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void testGridWidth() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[gridwidth=1.3cm]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(1.3*IShape.PPC, grid.getGridWidth());
		grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"[gridwidth=.3in]" +getBasicCoordinates()).get().getShapeAt(0);
		assertEquals(.3*IShape.PPC/PSTricksConstants.INCH_VAL_CM, grid.getGridWidth());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	@Test
	public void test0CoordDoubleValue() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\begin{pspicture}(2.1,2.6)(5.6,5.5)\\"+getCommandName()+"\\end{pspicture}").get().getShapeAt(0);
		
		assertEquals(2., grid.getOriginX(), 0.0001);
		assertEquals(3., grid.getOriginY(), 0.0001);
		assertEquals(2., grid.getGridMinX(), 0.0001);
		assertEquals(3., grid.getGridMinY(), 0.0001);
		assertEquals(6., grid.getGridMaxX(), 0.0001);
		assertEquals(6., grid.getGridMaxY(), 0.0001);
		assertTrue(grid.isXLabelSouth());
		assertTrue(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test0Coord() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\begin{pspicture}(2,2)(5,5)\\"+getCommandName()+"\\end{pspicture}").get().getShapeAt(0);
		
		assertEquals(2., grid.getOriginX(), 0.0001);
		assertEquals(2., grid.getOriginY(), 0.0001);
		assertEquals(2., grid.getGridMinX(), 0.0001);
		assertEquals(2., grid.getGridMinY(), 0.0001);
		assertEquals(5., grid.getGridMaxX(), 0.0001);
		assertEquals(5., grid.getGridMaxY(), 0.0001);
		assertTrue(grid.isXLabelSouth());
		assertTrue(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test1Coord() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"(1,2)").get().getShapeAt(0);
		
		assertEquals(0., grid.getOriginX(), 0.0001);
		assertEquals(0., grid.getOriginY(), 0.0001);
		assertEquals(0., grid.getGridMinX(), 0.0001);
		assertEquals(0., grid.getGridMinY(), 0.0001);
		assertEquals(1., grid.getGridMaxX(), 0.0001);
		assertEquals(2., grid.getGridMaxY(), 0.0001);
		assertTrue(grid.isXLabelSouth());
		assertTrue(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2CoordInverted() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"(3,4)(1,2)").get().getShapeAt(0);
		
		assertEquals(3., grid.getOriginX(), 0.0001);
		assertEquals(4., grid.getOriginY(), 0.0001);
		assertEquals(1., grid.getGridMinX(), 0.0001);
		assertEquals(2., grid.getGridMinY(), 0.0001);
		assertEquals(3., grid.getGridMaxX(), 0.0001);
		assertEquals(4., grid.getGridMaxY(), 0.0001);
		assertFalse(grid.isXLabelSouth());
		assertFalse(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test2Coord() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"(1,2)(3,4)").get().getShapeAt(0);
		
		assertEquals(1., grid.getOriginX(), 0.0001);
		assertEquals(2., grid.getOriginY(), 0.0001);
		assertEquals(1., grid.getGridMinX(), 0.0001);
		assertEquals(2., grid.getGridMinY(), 0.0001);
		assertEquals(3., grid.getGridMaxX(), 0.0001);
		assertEquals(4., grid.getGridMaxY(), 0.0001);
		assertTrue(grid.isXLabelSouth());
		assertTrue(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test3CoordInverted() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"(0,-1)(3,4)(1,2)").get().getShapeAt(0);
		
		assertEquals(0., grid.getOriginX(), 0.0001);
		assertEquals(-1., grid.getOriginY(), 0.0001);
		assertEquals(1., grid.getGridMinX(), 0.0001);
		assertEquals(2., grid.getGridMinY(), 0.0001);
		assertEquals(3., grid.getGridMaxX(), 0.0001);
		assertEquals(4., grid.getGridMaxY(), 0.0001);
		assertFalse(grid.isXLabelSouth());
		assertFalse(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Test
	public void test3Coord() throws ParseException {
		IGrid grid = (IGrid)parser.parsePSTCode("\\"+getCommandName()+"(0,-1)(1,2)(3,4)").get().getShapeAt(0);
		
		assertEquals(0., grid.getOriginX(), 0.0001);
		assertEquals(-1., grid.getOriginY(), 0.0001);
		assertEquals(1., grid.getGridMinX(), 0.0001);
		assertEquals(2., grid.getGridMinY(), 0.0001);
		assertEquals(3., grid.getGridMaxX(), 0.0001);
		assertEquals(4., grid.getGridMaxY(), 0.0001);
		assertTrue(grid.isXLabelSouth());
		assertTrue(grid.isYLabelWest());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Override
	public String getCommandName() {
		return "psgrid";
	}

	@Override
	public String getBasicCoordinates() {
		return "(0,0)(0,0)(1,1)";
	}

}
