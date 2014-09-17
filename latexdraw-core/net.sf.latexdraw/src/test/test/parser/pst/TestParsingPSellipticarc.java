package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPSellipticarc extends TestPSTParser {
	@Test
	public void testCanParsePsellipticarcn() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psellipticarcn[]{<->}"+getBasicCoordinates()).get().isEmpty()); //$NON-NLS-1$
		assertFalse(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCanParsePsellipticarc() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psellipticarc[]{<->}"+getBasicCoordinates()).get().isEmpty()); //$NON-NLS-1$
		assertFalse(PSTParser.errorLogs().isEmpty());
	}

	@Override
	public String getCommandName() {
		return "psellipticarc"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(.5,0)(1.5,1){215}{0}"; //$NON-NLS-1$
	}
}
