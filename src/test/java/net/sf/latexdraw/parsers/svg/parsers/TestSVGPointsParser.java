package net.sf.latexdraw.parsers.svg.parsers;

import java.awt.geom.Point2D;
import java.text.ParseException;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.parsers.TestCodeParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestSVGPointsParser extends TestCodeParser {
	@Rule public ExpectedException exceptionGrabber = ExpectedException.none();

	@Before
	public void setUp() {
		parser = new SVGPointsParser("");
		parser2 = new SVGPointsParser("");
	}

	@Test
	@Override
	public void testParse() throws ParseException {
		parser.setCode(" 1, 2, \t 3\n 4 \r ,5 6  \n \t ");
		parser.parse();
		assertEquals(((SVGPointsParser) parser).getPoints().get(0), new Point2D.Double(1d, 2d));
		assertEquals(((SVGPointsParser) parser).getPoints().get(1), new Point2D.Double(3d, 4d));
		assertEquals(((SVGPointsParser) parser).getPoints().get(((SVGPointsParser) parser).getPoints().size() - 1), new Point2D.Double(5d, 6d));
	}

	@Theory
	public void testParseKO(@StringData(vals = {"fdsfsd", "10 10 10", "10,, 10 10 10"}) final String data) throws ParseException {
		exceptionGrabber.expect(ParseException.class);
		parser.setCode(data);
		parser.parse();
	}

	@Test
	@Override
	public void testSkipComment() {
		// No comment allowed.
	}

	@Test
	@Override
	public void testSkipWSP() {
		parser.setCode(" \r  \t \n 10 10");
		parser.skipWSP();
		assertEquals('1', parser.getChar());
	}

	@Test
	public void testIsWSP() {
		parser.setCode(" \r\t\na");
		assertTrue(parser.isWSP());
	}

	@Test
	public void testIsNotWSP() {
		parser.setCode(" \r\t\na");
		parser.nextChar();
		parser.nextChar();
		parser.nextChar();
		parser.nextChar();
		assertFalse(parser.isWSP());
	}

	@Test
	public void testSkipWSPComma() {
		parser.setCode(" \r , \t \n 10 10");
		((SVGPointsParser) parser).skipWSPComma();
		assertEquals('1', parser.getChar());
	}

	@Theory
	public void testReadNumber(@StringData(vals = {"10", "+10", "-10", "-10.", "-.1", "10e2", "10e-2", "10e+2", "10E2", "10E-2", "10E+2", "0.E+2"})
								final String data) throws ParseException {
		final SVGPointsParser p = (SVGPointsParser) parser;
		p.setCode(data);
		assertEquals(Double.valueOf(data), p.readNumber(), 0.0001);
	}

	@Theory
	public void testReadNumberKO(@StringData(vals = {".E+2", ".Efd+2", "", " \t", "aa", ".", "--10"}) final String data) throws ParseException {
		exceptionGrabber.expect(ParseException.class);
		final SVGPointsParser p = (SVGPointsParser) parser;
		p.setCode(data);
		p.readNumber();
	}

	@Test
	public void testGetPoints() throws ParseException {
		parser.setCode(" 1, 2,3 4 5,6");
		parser.parse();
		assertNotNull(((SVGPointsParser) parser).getPoints());
		assertEquals(new Point2D.Double(1d, 2d), ((SVGPointsParser) parser).getPoints().get(0));
		assertEquals(new Point2D.Double(3d, 4d), ((SVGPointsParser) parser).getPoints().get(1));
		assertEquals(new Point2D.Double(5d, 6d), ((SVGPointsParser) parser).getPoints().get(((SVGPointsParser) parser).getPoints().size() - 1));
	}
}
