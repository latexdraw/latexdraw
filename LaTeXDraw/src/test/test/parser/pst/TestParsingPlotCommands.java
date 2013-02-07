package test.parser.pst;

import java.text.ParseException;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPlotCommands extends TestPSTParser {
	@Test
	public void testParametricplot() throws ParseException {
		parser.parsePSTCode("\\parametricplot[plotstyle=dots,plotpoints=13]{-6}{6}{1.2 t exp 1.2 t neg exp}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testParametricplotStar() throws ParseException {
		parser.parsePSTCode("\\parametricplot*[plotstyle=dots,plotpoints=13]{-6}{6}{1.2 t exp 1.2 t neg exp}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testPsplot() throws ParseException {
		parser.parsePSTCode("\\psplot[plotpoints=200]{0}{720}{x sin}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testPsplotStar() throws ParseException {
		parser.parsePSTCode("\\psplot*[plotpoints=200]{0}{720}{x sin}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testListplot() throws ParseException {
		parser.parsePSTCode("\\listplot[plotstyle=line]{\\mydata}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testListplotStar() throws ParseException {
		parser.parsePSTCode("\\listplot*[plotstyle=line]{\\mydata}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testFileplot() throws ParseException {
		parser.parsePSTCode("\\fileplot[plotstyle=line]{file}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testFileplotStar() throws ParseException {
		parser.parsePSTCode("\\fileplot*[plotstyle=line]{file}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testDataplot() throws ParseException {
		parser.parsePSTCode("\\dataplot[plotstyle=line]{\\cmdName}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testDataplotStar() throws ParseException {
		parser.parsePSTCode("\\dataplot*[plotstyle=line]{\\foo\\bar}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testSavedata() throws ParseException {
		parser.parsePSTCode("\\savedata{\\foo}[{{0, 0}, {1., 0.946083}, {2., 1.60541}, {3., 1.84865}, {4., 1.7582}," +
				"{5., 1.54993}, {6., 1.42469}, {7., 1.4546}, {8., 1.57419}," +
				"{9., 1.66504}, {10., 1.65835}, {11., 1.57831}, {12., 1.50497}," +
				"{13., 1.49936}, {14., 1.55621}, {15., 1.61819}, {16., 1.6313}," +
				"{17., 1.59014}, {18., 1.53661}, {19., 1.51863}, {20., 1.54824}}]");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testReaddata() throws ParseException {
		parser.parsePSTCode("\\readdata{\\foo}{foo.data}");
		assertEquals(1, PSTParser.errorLogs().size());
	}	
	
	
	@Test
	public void testPlotstyle() throws ParseException {
		parser.parsePSTCode("\\psframe[plotstyle=line](1,1)");
		parser.parsePSTCode("\\psframe[plotstyle=dots](1,1)");
		parser.parsePSTCode("\\psframe[plotstyle=polygon](1,1)");
		parser.parsePSTCode("\\psframe[plotstyle=curve](1,1)");
		parser.parsePSTCode("\\psframe[plotstyle=ecurve](1,1)");
		parser.parsePSTCode("\\psframe[plotstyle=ccurve](1,1)");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	@Override
	public String getCommandName() {
		return "dataplot";
	}

	@Override
	public String getBasicCoordinates() {
		return null;
	}
}
