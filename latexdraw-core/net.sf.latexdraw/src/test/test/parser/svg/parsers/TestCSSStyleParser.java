package test.parser.svg.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.CSSStyleHandler;
import net.sf.latexdraw.parsers.svg.parsers.CSSStyleParser;

import org.junit.Before;
import org.junit.Test;

import test.parser.TestCodeParser;

public class TestCSSStyleParser extends TestCodeParser implements CSSStyleHandler {
	protected String name;

	protected String value;


	@Before
	public void setUp() {
		parser  = new CSSStyleParser("", this);
		parser2 = new CSSStyleParser("", this);
	}


	@Test
	@Override
	public void testParse() throws ParseException {
		name = null;
		value = null;
		parser.setCode("");
		parser.parse();
		assertNull(name);
		assertNull(value);
		name = null;
		value = null;
		parser.setCode("\t \n/**/ \n\r \t /* fldijfsd */ \n");
		parser.parse();
		assertNull(name);
		assertNull(value);
		parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ \n");
		parser.parse();
		assertEquals(name, "stroke");
		assertEquals(value, "red");
		parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ ;\nstroke-width : 2cm");
		parser.parse();
		assertEquals(name, "stroke-width");
		assertEquals(value, "2cm");
	}



	@Test
	@Override
	public void testSkipComment() throws ParseException {
		parser.setCode("fill:/*test*/green");
		parser.parse();
		assertEquals(name, "fill");
		assertEquals(value, "green");
		parser.setCode(" /* fksduh fdssd \n \t fdsf d */ stroke : blue");
		parser.parse();
		assertEquals(name, "stroke");
		assertEquals(value, "blue");
		parser.setCode("fill:green /* fkdhf fss */  ");
		parser.parse();
		assertEquals(name, "fill");
		assertEquals(value, "green");
		parser.setCode("stroke: /**/ blue   ");
		parser.parse();
		assertEquals(name, "stroke");
		assertEquals(value, "blue");
		parser.setCode("stroke:/**/blue;");
		parser.parse();
		assertEquals(name, "stroke");
		assertEquals(value, "blue");
		parser.setCode("fill:green /* fkdhf fss */;/*f*/stroke/*fds fsd*/:/**/blue/*fgdds */");
		parser.parse();
		assertEquals(name, "stroke");
		assertEquals(value, "blue");
	}


	@Test
	@Override
	public void testSkipWSP() throws ParseException {
		parser.setCode("fill:green ;stroke:blue");
		parser.parse();
		assertEquals(name, "stroke");
		assertEquals(value, "blue");
		parser.setCode(" \n \t \r stroke\t\n \r: \r \t\n blue \r\t \n \t ;\t\t\n\r fill\t\r :\n green \r \t ");
		parser.parse();
		assertEquals(name, "fill");
		assertEquals(value, "green");
	}


	@Override
	public void onCSSStyle(String n, String v) {
		name = n;
		value = v;
	}
}
