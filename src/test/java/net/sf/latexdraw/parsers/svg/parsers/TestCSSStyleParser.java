package net.sf.latexdraw.parsers.svg.parsers;

import java.text.ParseException;
import net.sf.latexdraw.parsers.TestCodeParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestCSSStyleParser extends TestCodeParser implements CSSStyleHandler {
	String name;
	String value;

	@Before
	public void setUp() {
		parser = new CSSStyleParser("", this);
		parser2 = new CSSStyleParser("", this);
	}

	@Test
	@Override
	public void testParse() throws ParseException {
		parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ ;\nstroke-width : 2cm");
		parser.parse();
		assertEquals("stroke-width", name);
		assertEquals("2cm", value);
	}

	@Test
	public void testParseEmpty() throws ParseException {
		parser.setCode("");
		parser.parse();
		assertNull(name);
		assertNull(value);
	}

	@Test
	public void testParseKO() throws ParseException {
		parser.setCode("\t \n/**/ \n\r \t /* fldijfsd */ \n");
		parser.parse();
		assertNull(name);
		assertNull(value);
	}

	@Test
	@Override
	public void testSkipComment() throws ParseException {
		parser.setCode("fill:/*test*/green");
		parser.parse();
		assertEquals("fill", name);
		assertEquals("green", value);
	}

	@Test
	@Override
	public void testSkipWSP() throws ParseException {
		parser.setCode(" \n \t \r stroke\t\n \r: \r \t\n blue \r\t \n \t ;\t\t\n\r fill\t\r :\n green \r \t ");
		parser.parse();
		assertEquals("fill", name);
		assertEquals("green", value);
	}

	@Override
	public void onCSSStyle(final String n, final String v) {
		name = n;
		value = v;
	}
}
