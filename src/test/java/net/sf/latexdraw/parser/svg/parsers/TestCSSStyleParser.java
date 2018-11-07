package net.sf.latexdraw.parser.svg.parsers;

import java.text.ParseException;
import net.sf.latexdraw.parser.TestCodeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCSSStyleParser extends TestCodeParser implements CSSStyleHandler {
	String name;
	String value;

	@BeforeEach
	void setUp() {
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
	void testParseEmpty() throws ParseException {
		parser.setCode("");
		parser.parse();
		assertNull(name);
		assertNull(value);
	}

	@Test
	void testParseKO() throws ParseException {
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
