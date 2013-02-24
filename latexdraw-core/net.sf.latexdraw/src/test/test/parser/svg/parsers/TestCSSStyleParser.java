package test.parser.svg.parsers;

import java.text.ParseException;


import net.sf.latexdraw.parsers.svg.parsers.CSSStyleHandler;
import net.sf.latexdraw.parsers.svg.parsers.CSSStyleParser;

import org.junit.Before;

import test.parser.TestCodeParser;

public class TestCSSStyleParser extends TestCodeParser implements CSSStyleHandler
{
	protected String name;

	protected String value;


	@Override
	@Before
	public void setUp() {
		parser  = new CSSStyleParser("", this);
		parser2 = new CSSStyleParser("", this);
	}


	@Override
	public void testParse() {
		try {
			name = null;
			value = null;
			parser.setCode("");
			parser.parse();
			assertNull(name);
			assertNull(value);
		}catch(ParseException e) {fail();}
		try {
			name = null;
			value = null;
			parser.setCode("\t \n/**/ \n\r \t /* fldijfsd */ \n");
			parser.parse();
			assertNull(name);
			assertNull(value);
		}catch(ParseException e) {fail();}
		try {
			parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ \n");
			parser.parse();
			assertEquals(name, "stroke");
			assertEquals(value, "red");
		}catch(ParseException e) {fail();}
		try {
			parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ ;\nstroke-width : 2cm");
			parser.parse();
			assertEquals(name, "stroke-width");
			assertEquals(value, "2cm");
		}catch(ParseException e) {fail();}
	}




	@Override
	public void testSkipComment() {
		try {
			parser.setCode("fill:/*test*/green");
			parser.parse();
			assertEquals(name, "fill");
			assertEquals(value, "green");
		}catch(ParseException e) {fail();}

		try {
			parser.setCode(" /* fksduh fdssd \n \t fdsf d */ stroke : blue");
			parser.parse();
			assertEquals(name, "stroke");
			assertEquals(value, "blue");
		}catch(ParseException e) {fail();}

		try {
			parser.setCode("fill:green /* fkdhf fss */  ");
			parser.parse();
			assertEquals(name, "fill");
			assertEquals(value, "green");
		}catch(ParseException e) {fail();}

		try {
			parser.setCode("stroke: /**/ blue   ");
			parser.parse();
			assertEquals(name, "stroke");
			assertEquals(value, "blue");
		}catch(ParseException e) {fail();}

		try {
			parser.setCode("stroke:/**/blue;");
			parser.parse();
			assertEquals(name, "stroke");
			assertEquals(value, "blue");
		}catch(ParseException e) {fail();}

		try {
			parser.setCode("fill:green /* fkdhf fss */;/*f*/stroke/*fds fsd*/:/**/blue/*fgdds */");
			parser.parse();
			assertEquals(name, "stroke");
			assertEquals(value, "blue");
		}catch(ParseException e) {fail();}
	}



	@Override
	public void testSkipWSP() {
		try {
			parser.setCode("fill:green ;stroke:blue");
			parser.parse();
			assertEquals(name, "stroke");
			assertEquals(value, "blue");
		}catch(ParseException e) {fail();}
		try {
			parser.setCode(" \n \t \r stroke\t\n \r: \r \t\n blue \r\t \n \t ;\t\t\n\r fill\t\r :\n green \r \t ");
			parser.parse();
			assertEquals(name, "fill");
			assertEquals(value, "green");
		}catch(ParseException e) {fail();}
	}


	@Override
	public void onCSSStyle(String n, String v)
	{
		name = n;
		value = v;
	}
}
