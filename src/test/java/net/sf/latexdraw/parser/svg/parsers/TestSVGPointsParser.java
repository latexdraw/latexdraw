package net.sf.latexdraw.parser.svg.parsers;

import java.awt.geom.Point2D;
import java.text.ParseException;
import net.sf.latexdraw.parser.TestCodeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestSVGPointsParser extends TestCodeParser {
	@BeforeEach
	void setUp() {
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

	@ParameterizedTest
	@ValueSource(strings = {"fdsfsd", "10 10 10", "10,, 10 10 10"})
	void testParseKO(final String data) {
		parser.setCode(data);
		assertThrows(ParseException.class, () -> parser.parse());
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
	void testIsWSP() {
		parser.setCode(" \r\t\na");
		assertTrue(parser.isWSP());
	}

	@Test
	void testIsNotWSP() {
		parser.setCode(" \r\t\na");
		parser.nextChar();
		parser.nextChar();
		parser.nextChar();
		parser.nextChar();
		assertFalse(parser.isWSP());
	}

	@Test
	void testSkipWSPComma() {
		parser.setCode(" \r , \t \n 10 10");
		((SVGPointsParser) parser).skipWSPComma();
		assertEquals('1', parser.getChar());
	}

	@ParameterizedTest
	@ValueSource(strings = {"10", "+10", "-10", "-10.", "-.1", "10e2", "10e-2", "10e+2", "10E2", "10E-2", "10E+2", "0.E+2"})
	void testReadNumber(final String data) throws ParseException {
		final SVGPointsParser p = (SVGPointsParser) parser;
		p.setCode(data);
		assertEquals(Double.parseDouble(data), p.readNumber(), 0.0001);
	}

	@ParameterizedTest
	@ValueSource(strings = {".E+2", ".Efd+2", "", " \t", "aa", ".", "--10"})
	void testReadNumberKO(final String data) {
		final SVGPointsParser p = (SVGPointsParser) parser;
		p.setCode(data);
		assertThrows(ParseException.class, () -> p.readNumber());
	}

	@Test
	void testGetwPoints() throws ParseException {
		parser.setCode(" 1, 2,3 4 5,6");
		parser.parse();
		assertNotNull(((SVGPointsParser) parser).getPoints());
		assertEquals(new Point2D.Double(1d, 2d), ((SVGPointsParser) parser).getPoints().get(0));
		assertEquals(new Point2D.Double(3d, 4d), ((SVGPointsParser) parser).getPoints().get(1));
		assertEquals(new Point2D.Double(5d, 6d), ((SVGPointsParser) parser).getPoints().get(((SVGPointsParser) parser).getPoints().size() - 1));
	}
}
