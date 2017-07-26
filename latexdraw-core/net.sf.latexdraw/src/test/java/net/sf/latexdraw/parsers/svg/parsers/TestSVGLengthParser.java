package net.sf.latexdraw.parsers.svg.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;

import org.junit.Test;

public class TestSVGLengthParser {
	@SuppressWarnings("unused")
	@Test
	public void testParseLength() throws ParseException {
		SVGLengthParser p;
		SVGLength l;

		try {
			new SVGLengthParser(null);
			fail();
		}catch(IllegalArgumentException e) {
			/* */ }

		try {
			p = new SVGLengthParser(""); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1m"); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("m"); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1p"); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1e1i"); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1ci"); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1 c "); //$NON-NLS-1$
			l = p.parseLength();
			fail();
		}catch(ParseException e) {
			/* */ }

		p = new SVGLengthParser("1mm"); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "1"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.MM), l.getValue(), 0.001);

		p = new SVGLengthParser("0.65 cm"); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "0.65"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.CM), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65  \t \n pc"); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-10.65"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.PC), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e2  \t \n px \t   "); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-10.65e2"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e2, SVGLength.LengthType.PX), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e0pt \t   "); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-10.65e0"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e0, SVGLength.LengthType.PT), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -1.in \t   "); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-1."); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.IN), l.getValue(), 0.001);

		p = new SVGLengthParser("-1."); //$NON-NLS-1$
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-1."); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}

	@Test
	public void testParseNumberOrPercent() throws ParseException {
		SVGLengthParser p;
		SVGLength l;

		p = new SVGLengthParser("1mm"); //$NON-NLS-1$
		l = p.parseNumberOrPercent();

		assertEquals(l.getValueAsString(), "1"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.NUMBER);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);

		p = new SVGLengthParser("0.876"); //$NON-NLS-1$
		l = p.parseNumberOrPercent();

		assertEquals(l.getValueAsString(), "0.876"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.NUMBER);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.876, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}

	@Test
	public void testParseCoordinate() throws ParseException {
		SVGLengthParser p;
		SVGLength l;

		try {
			p = new SVGLengthParser(""); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1m"); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("m"); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1p"); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1e1i"); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1ci"); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		try {
			p = new SVGLengthParser("1 c "); //$NON-NLS-1$
			l = p.parseCoordinate();
			fail();
		}catch(ParseException e) {
			/* */ }

		p = new SVGLengthParser("1mm"); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "1"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.MM), l.getValue(), 0.001);

		p = new SVGLengthParser("0.65 cm"); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "0.65"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.CM), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65  \t \n pc"); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-10.65"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.PC), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e2  \t \n px \t   "); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-10.65e2"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e2, SVGLength.LengthType.PX), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e0pt \t   "); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-10.65e0"); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e0, SVGLength.LengthType.PT), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -1.in \t   "); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-1."); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.IN), l.getValue(), 0.001);

		p = new SVGLengthParser("-1."); //$NON-NLS-1$
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-1."); //$NON-NLS-1$
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}
}
