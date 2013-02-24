package test.parser.pst;

import java.text.ParseException;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingNewpsstyle extends TestPSTParser {
	@Test
	public void testParseCommandExample() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+getBasicCoordinates());
		assertEquals(1, PSTParser.errorLogs().size());
	}
	
	
	@Override
	public String getCommandName() {
		return "newpsstyle";
	}

	@Override
	public String getBasicCoordinates() {
		return "{mystyle}{linecolor=green,linestyle=dotted}";
	}
}
