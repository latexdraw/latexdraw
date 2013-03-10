package test.parser.svg.parsers;

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
		}
		catch(IllegalArgumentException e){ /* */ }

		try {
			p = new SVGLengthParser("");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1m");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("m");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1p");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1e1i");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1ci");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1 c ");
			l = p.parseLength();
			fail();
		}
		catch(ParseException e){ /* */ }

		p = new SVGLengthParser("1mm");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "1");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.MM), l.getValue(), 0.001);

		p = new SVGLengthParser("0.65 cm");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "0.65");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.CM), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65  \t \n pc");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-10.65");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.PC), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e2  \t \n px \t   ");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-10.65e2");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e2, SVGLength.LengthType.PX), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e0pt \t   ");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-10.65e0");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e0, SVGLength.LengthType.PT), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -1.in \t   ");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-1.");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.IN), l.getValue(), 0.001);

		p = new SVGLengthParser("-1.");
		l = p.parseLength();

		assertEquals(l.getValueAsString(), "-1.");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}



	@Test
	public void testParseNumberOrPercent() throws ParseException {
		SVGLengthParser p;
		SVGLength l;

		p = new SVGLengthParser("1mm");
		l = p.parseNumberOrPercent();

		assertEquals(l.getValueAsString(), "1");
		assertEquals(l.getLengthType(), SVGLength.LengthType.NUMBER);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);

		p = new SVGLengthParser("0.876");
		l = p.parseNumberOrPercent();

		assertEquals(l.getValueAsString(), "0.876");
		assertEquals(l.getLengthType(), SVGLength.LengthType.NUMBER);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.876, SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}



	@Test
	public void testParseCoordinate() throws ParseException {
		SVGLengthParser p;
		SVGLength l;

		try {
			p = new SVGLengthParser("");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1m");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("m");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1p");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1e1i");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1ci");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		try {
			p = new SVGLengthParser("1 c ");
			l = p.parseCoordinate();
			fail();
		}
		catch(ParseException e){ /* */ }

		p = new SVGLengthParser("1mm");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "1");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1, SVGLength.LengthType.MM), l.getValue(), 0.001);

		p = new SVGLengthParser("0.65 cm");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "0.65");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(0.65, SVGLength.LengthType.CM), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65  \t \n pc");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-10.65");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65, SVGLength.LengthType.PC), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e2  \t \n px \t   ");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-10.65e2");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e2, SVGLength.LengthType.PX), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -10.65e0pt \t   ");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-10.65e0");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-10.65e0, SVGLength.LengthType.PT), l.getValue(), 0.001);

		p = new SVGLengthParser("\t -1.in \t   ");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-1.");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.IN), l.getValue(), 0.001);

		p = new SVGLengthParser("-1.");
		l = p.parseCoordinate();

		assertEquals(l.getValueAsString(), "-1.");
		assertEquals(l.getLengthType(), SVGLength.LengthType.PX);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(-1., SVGLength.LengthType.NUMBER), l.getValue(), 0.001);
	}
}
