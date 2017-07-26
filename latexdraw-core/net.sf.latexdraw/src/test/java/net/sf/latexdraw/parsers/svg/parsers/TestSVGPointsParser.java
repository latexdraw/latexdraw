package net.sf.latexdraw.parsers.svg.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;

import org.junit.Before;
import org.junit.Test;

import net.sf.latexdraw.parsers.TestCodeParser;

public class TestSVGPointsParser extends TestCodeParser {
	@Before
	public void setUp() {
		parser = new SVGPointsParser(""); //$NON-NLS-1$
		parser2 = new SVGPointsParser(""); //$NON-NLS-1$
	}

	@Test
	@Override
	public void testParse() throws ParseException {
		parser.setCode(" 1, 2, \t 3\n 4 \r ,5 6  \n \t "); //$NON-NLS-1$
		parser.parse();
		assertNotNull(((SVGPointsParser)parser).getPoints());
		assertEquals(((SVGPointsParser)parser).getPoints().get(0), new Point2D.Double(1., 2.));
		assertEquals(((SVGPointsParser)parser).getPoints().get(1), new Point2D.Double(3., 4.));
		assertEquals(((SVGPointsParser)parser).getPoints().get(((SVGPointsParser)parser).getPoints().size() - 1), new Point2D.Double(5., 6.));

		try {
			parser.setCode("dsf"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }

		try {
			parser.setCode("10 10 10"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }

		try {
			parser.setCode("10,, 10 10 10"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }
		try {
			parser.setCode(",10 10 10 10"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }
		try {
			parser.setCode("10 10 10,"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }
		try {
			parser.setCode("10 10 aa 10"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }
		try {
			parser.setCode("10 10 10e 10"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }
		try {
			parser.setCode("10 10E 10 10"); //$NON-NLS-1$
			parser.parse();
		}catch(ParseException e) {
			/* */ }
	}

	@Test
	@Override
	public void testSkipComment() {
		// No comment allowed.
	}

	@Test
	@Override
	public void testSkipWSP() {
		parser.setCode(" \r  \t \n 10 10"); //$NON-NLS-1$
		parser.skipWSP();
		assertEquals(parser.getChar(), '1');
	}

	@Test
	public void testIsWSP() {
		parser.setCode(" \r\t\na"); //$NON-NLS-1$
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

	@Test
	public void testSkipWSPComma() {
		parser.setCode(" \r , \t \n 10 10"); //$NON-NLS-1$
		((SVGPointsParser)parser).skipWSPComma();
		assertEquals(parser.getChar(), '1');
		parser.setCode(" \r , \t \n , 10 10"); //$NON-NLS-1$
		((SVGPointsParser)parser).skipWSPComma();
		assertEquals(parser.getChar(), ',');
	}

	@Test
	public void testReadNumber() throws ParseException {
		SVGPointsParser p = (SVGPointsParser)parser;

		p.setCode("10"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 10., 0.0001);
		p.setCode("+10"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 10., 0.0001);
		p.setCode("-10"); //$NON-NLS-1$
		assertEquals(p.readNumber(), -10., 0.0001);
		p.setCode("-10."); //$NON-NLS-1$
		assertEquals(p.readNumber(), -10., 0.0001);
		p.setCode("-.1"); //$NON-NLS-1$
		assertEquals(p.readNumber(), -.1, 0.0001);
		p.setCode("10e2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 1000., 0.0001);
		p.setCode("10e-2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 0.1, 0.0001);
		p.setCode("10e+2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 1000., 0.0001);
		p.setCode("10E2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 1000., 0.0001);
		p.setCode("10E-2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 0.1, 0.0001);
		p.setCode("10E+2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 1000., 0.0001);
		p.setCode("0.E+2"); //$NON-NLS-1$
		assertEquals(p.readNumber(), 0., 0.0001);

		try {
			p.setCode(".E+2"); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode(".Efd+2"); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode(""); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode(" \t"); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode("aa"); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode("."); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode("--10"); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
		try {
			p.setCode("+-10"); //$NON-NLS-1$
			assertEquals(p.readNumber(), 0., 0.0001);
		}catch(ParseException e) {
			/* */}
	}

	@Test
	public void testGetPoints() throws ParseException {
		parser.setCode(" 1, 2,3 4 5,6"); //$NON-NLS-1$
		parser.parse();
		assertNotNull(((SVGPointsParser)parser).getPoints());
		assertEquals(((SVGPointsParser)parser).getPoints().get(0), new Point2D.Double(1., 2.));
		assertEquals(((SVGPointsParser)parser).getPoints().get(1), new Point2D.Double(3., 4.));
		assertEquals(((SVGPointsParser)parser).getPoints().get(((SVGPointsParser)parser).getPoints().size() - 1), new Point2D.Double(5., 6.));
	}
}
