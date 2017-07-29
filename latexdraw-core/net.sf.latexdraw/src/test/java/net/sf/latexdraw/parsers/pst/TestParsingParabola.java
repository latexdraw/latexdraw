package net.sf.latexdraw.parsers.pst;

import java.text.ParseException;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestParsingParabola extends TestPSTParser {
	@Test
	public void testParseCurvature() throws ParseException {
		parser.parsePSTCode("\\psline[curvature=1 -1 0.33](35,20)(35,20)"); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCanParse() throws ParseException {
		assertTrue(parser.parsePSTCode("\\parabola(35,20)(35,20)").get().isEmpty()); //$NON-NLS-1$
		assertFalse(PSTParser.errorLogs().isEmpty());
	}

	@Override
	public String getCommandName() {
		return "parabola"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(35,20)(35,20)"; //$NON-NLS-1$
	}
}
