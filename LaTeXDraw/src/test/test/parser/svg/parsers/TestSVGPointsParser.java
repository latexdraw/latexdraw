package test.parser.svg.parsers;

import java.awt.geom.Point2D;
import java.text.ParseException;


import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;

import org.junit.Before;

import test.parser.TestCodeParser;

public class TestSVGPointsParser extends TestCodeParser
{
	@Override
	@Before
	public void setUp() {
		parser  = new SVGPointsParser("");
		parser2 = new SVGPointsParser("");
	}



	@Override
	public void testParse() {
		try {
			parser.setCode(" 1, 2, \t 3\n 4 \r ,5 6  \n \t ");
			parser.parse();
			assertNotNull(((SVGPointsParser)parser).getPoints());
			assertEquals(((SVGPointsParser)parser).getPoints().get(0), new Point2D.Double(1., 2.));
			assertEquals(((SVGPointsParser)parser).getPoints().get(1),   new Point2D.Double(3., 4.));
			assertEquals(((SVGPointsParser)parser).getPoints().get(((SVGPointsParser)parser).getPoints().size()-1),  new Point2D.Double(5., 6.));
		}
		catch(ParseException e) { fail(); }

		try {
			parser.setCode("dsf");
			parser.parse();
		}catch(ParseException e) { /* */ }

		try {
			parser.setCode("10 10 10");
			parser.parse();
		}catch(ParseException e) { /* */ }

		try {
			parser.setCode("10,, 10 10 10");
			parser.parse();
		}catch(ParseException e) { /* */ }
		try {
			parser.setCode(",10 10 10 10");
			parser.parse();
		}catch(ParseException e) { /* */ }
		try {
			parser.setCode("10 10 10,");
			parser.parse();
		}catch(ParseException e) { /* */ }
		try {
			parser.setCode("10 10 aa 10");
			parser.parse();
		}catch(ParseException e) { /* */ }
		try {
			parser.setCode("10 10 10e 10");
			parser.parse();
		}catch(ParseException e) { /* */ }
		try {
			parser.setCode("10 10E 10 10");
			parser.parse();
		}catch(ParseException e) { /* */ }
	}

	@Override
	public void testSkipComment() {
		// No comment allowed.
	}

	@Override
	public void testSkipWSP() {
		parser.setCode(" \r  \t \n 10 10");
		parser.skipWSP();
		assertEquals(parser.getChar(), '1');
	}


	public void testIsWSP() {
		parser.setCode(" \r\t\na");
		assertTrue(parser.isWSP());
		parser.nextChar();
		assertTrue(parser.isWSP());
		parser.nextChar();
		assertTrue(parser.isWSP());
		parser.nextChar();
		assertTrue(parser.isWSP());
		parser.nextChar();
		assertFalse(parser.isWSP());
		parser.nextChar();
		assertFalse(parser.isWSP());
	}


	public void testSkipWSPComma() {
		parser.setCode(" \r , \t \n 10 10");
		((SVGPointsParser)parser).skipWSPComma();
		assertEquals(parser.getChar(), '1');
		parser.setCode(" \r , \t \n , 10 10");
		((SVGPointsParser)parser).skipWSPComma();
		assertEquals(parser.getChar(), ',');
	}


	public void testReadNumber() {
		SVGPointsParser p = (SVGPointsParser)parser;

		try {
			p.setCode("10");
			assertEquals(p.readNumber(), 10.);
			p.setCode("+10");
			assertEquals(p.readNumber(), 10.);
			p.setCode("-10");
			assertEquals(p.readNumber(), -10.);
			p.setCode("-10.");
			assertEquals(p.readNumber(), -10.);
			p.setCode("-.1");
			assertEquals(p.readNumber(), -.1);
			p.setCode("10e2");
			assertEquals(p.readNumber(), 1000.);
			p.setCode("10e-2");
			assertEquals(p.readNumber(), 0.1);
			p.setCode("10e+2");
			assertEquals(p.readNumber(), 1000.);
			p.setCode("10E2");
			assertEquals(p.readNumber(), 1000.);
			p.setCode("10E-2");
			assertEquals(p.readNumber(), 0.1);
			p.setCode("10E+2");
			assertEquals(p.readNumber(), 1000.);
			p.setCode("0.E+2");
			assertEquals(p.readNumber(), 0.);
		}
		catch(ParseException e) { e.printStackTrace(); fail(); }

		try {
			p.setCode(".E+2");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode(".Efd+2");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode("");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode(" \t");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode("aa");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode(".");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode("--10");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
		try {
			p.setCode("+-10");
			assertEquals(p.readNumber(), 0.);
		} catch(ParseException e) { /* */}
	}


	public void testGetPoints() {
		try {
			parser.setCode(" 1, 2,3 4 5,6");
			parser.parse();
			assertNotNull(((SVGPointsParser)parser).getPoints());
			assertEquals(((SVGPointsParser)parser).getPoints().get(0), new Point2D.Double(1., 2.));
			assertEquals(((SVGPointsParser)parser).getPoints().get(1),   new Point2D.Double(3., 4.));
			assertEquals(((SVGPointsParser)parser).getPoints().get(((SVGPointsParser)parser).getPoints().size()-1),  new Point2D.Double(5., 6.));
		}
		catch(ParseException e) { fail(); }
	}
}
