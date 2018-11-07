package net.sf.latexdraw.parser.svg.parsers;

import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestSVGLengthParser {
	@ParameterizedTest
	@ValueSource(strings = {"", "1m", "m", "1p", "1e1i", "1ci", "1 c "})
	public void testParseLengthKO(final String data) {
		assertThrows(ParseException.class, () -> new SVGLengthParser(data).parseLength());
	}

	@Test
	public void testParseLengthNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGLengthParser(null));
	}

	@Test
	public void testParseLengthOKMM() throws ParseException {
		final SVGLength length = new SVGLengthParser("0.65mm").parseLength();
		assertEquals("0.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.MM), length.getValue(), 0.001);
	}

	@Test
	public void testParseLengthOKSpace() throws ParseException {
		final SVGLength length = new SVGLengthParser("\t -10.65  \t \n pc").parseLength();
		assertEquals("-10.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.PC), length.getValue(), 0.001);
	}

	@Test
	public void testParseLengthOKpower() throws ParseException {
		final SVGLength length = new SVGLengthParser("-10.65e2 px").parseLength();
		assertEquals("-10.65e2", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e2, SVGLength.LengthType.PX), length.getValue(), 0.001);
	}

	@Test
	public void testParseLengthOKpt() throws ParseException {
		final SVGLength length = new SVGLengthParser("-10.65e0pt").parseLength();
		assertEquals("-10.65e0", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e0, SVGLength.LengthType.PT), length.getValue(), 0.001);
	}

	@Test
	public void testParseLengthOKin() throws ParseException {
		final SVGLength length = new SVGLengthParser("-1.in").parseLength();
		assertEquals("-1.", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.IN), length.getValue(), 0.001);
	}

	@Test
	public void testParseLengthOKDouble() throws ParseException {
		final SVGLength length = new SVGLengthParser("-1.").parseLength();
		assertEquals("-1.", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), length.getValue(), 0.001);
	}

	@Test
	public void testParseNumberOrPercentmm() throws ParseException {
		final SVGLength l = new SVGLengthParser("1mm").parseNumberOrPercent();
		assertEquals("1", l.getValueAsString());
		assertEquals(SVGLength.LengthType.NUMBER, l.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}

	@Test
	public void testParseNumberOrPercentdouble() throws ParseException {
		final SVGLength l = new SVGLengthParser("0.876").parseNumberOrPercent();
		assertEquals("0.876", l.getValueAsString());
		assertEquals(SVGLength.LengthType.NUMBER, l.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.876, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "1m", "m", "1p", "1e1i", "1ci", "1 c "})
	public void testParseCoordinate(final String data) {
		assertThrows(ParseException.class, () -> new SVGLengthParser(data).parseCoordinate());
	}

	@Test
	public void testParseCoordinateOKMM() throws ParseException {
		final SVGLength length = new SVGLengthParser("0.65mm").parseCoordinate();
		assertEquals("0.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.MM), length.getValue(), 0.001);
	}

	@Test
	public void testParseCoordinateOKSpace() throws ParseException {
		final SVGLength length = new SVGLengthParser("\t -10.65  \t \n pc").parseCoordinate();
		assertEquals("-10.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.PC), length.getValue(), 0.001);
	}

	@Test
	public void testParseCoordinateOKpower() throws ParseException {
		final SVGLength length = new SVGLengthParser("-10.65e2 px").parseCoordinate();
		assertEquals("-10.65e2", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e2, SVGLength.LengthType.PX), length.getValue(), 0.001);
	}

	@Test
	public void testParseCoordinateOKpt() throws ParseException {
		final SVGLength length = new SVGLengthParser("-10.65e0pt").parseCoordinate();
		assertEquals("-10.65e0", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e0, SVGLength.LengthType.PT), length.getValue(), 0.001);
	}

	@Test
	public void testParseCoordinateOKin() throws ParseException {
		final SVGLength length = new SVGLengthParser("-1.in").parseCoordinate();
		assertEquals("-1.", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.IN), length.getValue(), 0.001);
	}

	@Test
	public void testParseCoordinateOKDouble() throws ParseException {
		final SVGLength length = new SVGLengthParser("-1.").parseCoordinate();
		assertEquals("-1.", length.getValueAsString());
		assertEquals(SVGLength.LengthType.PX, length.getLengthType());
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), length.getValue(), 0.001);
	}
}
