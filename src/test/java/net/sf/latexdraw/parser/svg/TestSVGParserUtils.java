package net.sf.latexdraw.parser.svg;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSVGParserUtils {
	@Test
	void testGetURIOK() {
		Assertions.assertEquals("id2", SVGParserUtils.INSTANCE.parseURIRerefence("url(#id2)"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"url(", "ul(#id)", "url()", "url(#)", "url(id)"})
	void testGetURIKO(final String code) {
		assertTrue(SVGParserUtils.INSTANCE.parseURIRerefence(code).isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "1m", "m", "1p", "1e1i", "1ci", "1 c "})
	void testParseLengthKO(final String data) {
		assertTrue(SVGParserUtils.INSTANCE.parseLength(data).isEmpty());
	}

	@Test
	void testParseLengthOKMM() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("0.65mm").orElseThrow();
		assertEquals("0.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.mm), length.getValue(), 0.001);
	}

	@Test
	void testParseLengthOKSpace() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("\t -10.65  \t \n pc").orElseThrow();
		assertEquals("-10.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.pc), length.getValue(), 0.001);
	}

	@Test
	void testParseLengthOKDouble() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("-1.1").orElseThrow();
		assertEquals("-1.1", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(-1.1, SVGLength.LengthType.NUMBER), length.getValue(), 0.001);
	}

	@Test
	void testParseNumberOrPercentdouble() {
		final SVGLength l = SVGParserUtils.INSTANCE.parseLength("0.876").orElseThrow();
		assertEquals("0.876", l.getValueAsString());
		assertEquals(SVGLength.LengthType.px, l.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(0.876, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}

	@Test
	void testParseCoordinateOKMM() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("0.65mm").orElseThrow();
		assertEquals("0.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.mm), length.getValue(), 0.001);
	}

	@Test
	void testParseCoordinateOKSpace() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("-10.65  \t \n pc").orElseThrow();
		assertEquals("-10.65", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.pc), length.getValue(), 0.001);
	}

	@Test
	void testParseCoordinateOKin() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("-1.0in").orElseThrow();
		assertEquals("-1.0", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(-1., SVGLength.LengthType.in), length.getValue(), 0.001);
	}

	@Test
	void testParseCoordinateOKDouble() {
		final SVGLength length = SVGParserUtils.INSTANCE.parseLength("-1.0").orElseThrow();
		assertEquals("-1.0", length.getValueAsString());
		assertEquals(SVGLength.LengthType.px, length.getLengthType());
		assertEquals(SVGParserUtils.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), length.getValue(), 0.001);
	}

	@Test
	void testParse() {
		final List<Point2D> pts = SVGParserUtils.INSTANCE.parsePoints(" 1, 2, \t 3\n 4 \r ,5 6  \n \t ");
		assertEquals(pts.get(0), new Point2D.Double(1d, 2d));
		assertEquals(pts.get(1), new Point2D.Double(3d, 4d));
		assertEquals(pts.get(2), new Point2D.Double(5d, 6d));
	}

	@ParameterizedTest
	@ValueSource(strings = {"fdsfsd", "10 10 10", "10,, 10 10 10"})
	void testParseKO(final String data) {
		final List<Point2D> pts = SVGParserUtils.INSTANCE.parsePoints(data);
		assertTrue(pts.isEmpty());
	}

	@Test
	void testGetPoints() {
		final List<Point2D> pts = SVGParserUtils.INSTANCE.parsePoints(" 1, 0,3 4 5,6");
		assertNotNull(pts);
		assertEquals(new Point2D.Double(1d, 0d), pts.get(0));
		assertEquals(new Point2D.Double(3d, 4d), pts.get(1));
		assertEquals(new Point2D.Double(5d, 6d), pts.get(2));
	}

	@Test
	void testToUserUnitCM() {
		assertEquals(35.43307, SVGParserUtils.INSTANCE.toUserUnit(1d, SVGLength.LengthType.cm), 0.001);
	}

	@Test
	void testToUserUnitMM() {
		assertEquals(3.543307, SVGParserUtils.INSTANCE.toUserUnit(1d, SVGLength.LengthType.mm), 0.001);
	}

	@Test
	void testToUserUnitPT() {
		assertEquals(1.25, SVGParserUtils.INSTANCE.toUserUnit(1d, SVGLength.LengthType.pt), 0.001);
	}

	@Test
	void testToUserUnitPC() {
		assertEquals(15d, SVGParserUtils.INSTANCE.toUserUnit(1d, SVGLength.LengthType.pc), 0.001);
	}

	@Test
	void testToUserUnitIN() {
		assertEquals(90d, SVGParserUtils.INSTANCE.toUserUnit(1d, SVGLength.LengthType.in), 0.001);
	}

	@Test
	void testParseCSS() {
		final Map<String, String> data = SVGParserUtils.INSTANCE.parseCSS("stroke\t \n :\n\r \t red ;\nstroke-width : 2cm");
		assertEquals(2, data.size());
		assertEquals("red", data.get("stroke"));
		assertEquals("2cm", data.get("stroke-width"));
	}

	@Test
	void testParseEmpty() {
		final Map<String, String> data = SVGParserUtils.INSTANCE.parseCSS("");
		assertTrue(data.isEmpty());
	}

	@Test
	void testParseKO() {
		final Map<String, String> data = SVGParserUtils.INSTANCE.parseCSS("\t \n/**/ \n\r \t /* fldijfsd */ \n");
		assertTrue(data.isEmpty());
	}

	@Test
	void testSkipWSP() {
		final Map<String, String> data = SVGParserUtils.INSTANCE.parseCSS(" \n \t \r stroke\t\n \r: \r \t\n blue \r\t \n \t ;\t\t\n\r fill\t\r :\n green \r \t ");
		assertEquals(2, data.size());
		assertEquals("blue", data.get("stroke"));
		assertEquals("green", data.get("fill"));
	}
}
