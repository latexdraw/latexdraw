package test.parser.pst;

import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Before;

public abstract class TestPSTParser {
	protected PSTParser parser;

	@Before
	public void setUp() throws Exception {
		DrawingTK.setFactory(new LShapeFactory());
		parser = new PSTParser();
		PSTParser.errorLogs().clear();
	}


	public abstract String getCommandName();


	public abstract String getBasicCoordinates();


//	public void failShouldNotParse() {
//		fail("Should not parse.");
//	}
}
