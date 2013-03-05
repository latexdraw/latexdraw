package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingNewpsobject extends TestPSTParser {
	@Test
	public void testParseCommandExample2() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{mygrid}{psgrid}{subgriddiv=1,griddots=10,gridlabels=7pt}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testParseCommandExample1() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{myline}{psline}{linecolor=green,linestyle=dotted}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Override
	public String getCommandName() {
		return "newpsobject";
	}

	@Override
	public String getBasicCoordinates() {
		return "{myline}{psline}{linecolor=green,linestyle=dotted}";
	}
}
