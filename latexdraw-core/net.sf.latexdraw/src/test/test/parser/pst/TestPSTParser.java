package test.parser.pst;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Before;

public abstract class TestPSTParser {
	protected PSTParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new PSTParser();
		PSTParser.cleanErrors();
	}

	public abstract String getCommandName();

	public abstract String getBasicCoordinates();
}
