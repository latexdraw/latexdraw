package net.sf.latexdraw.parser;

import java.text.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TestCodeParser {
	protected CodeParser parser;
	protected CodeParser parser2;

	@Test
	public void testInitialise() {
		parser.setPosition(10);
		parser.initialise();
		assertEquals(0, parser.getPosition());
	}

	@Test
	public void testGetCharAtKO() {
		parser.setCode("test");
		assertEquals(CodeParser.EOC, parser.getCharAt(-1));
		assertEquals(CodeParser.EOC, parser.getCharAt(5));
	}

	@Test
	public void testGetCharAtOK() {
		parser.setCode("test");
		assertEquals('t', parser.getCharAt(0));
		assertEquals('e', parser.getCharAt(1));
		assertEquals('s', parser.getCharAt(2));
		assertEquals('t', parser.getCharAt(3));
	}

	@Test
	public void testGetCode() {
		final String code = "this is my code";
		parser.setCode(code);
		assertEquals(parser.getCode(), code);
	}

	@Test
	public void testSetCode() {
		final String code = "a piece of code";
		parser.setPosition(10);
		parser.setCode(code);
		assertEquals(0, parser.getPosition());
	}

	@Test
	public void testSetCodeNULL() {
		final String code = "a piece of code";
		parser.setCode(code);
		parser.setCode(null);
		assertEquals(parser.getCode(), code);
	}

	@Test
	public void testNextChar() {
		parser.setCode("code");
		assertEquals('o', parser.nextChar());
		assertEquals('d', parser.nextChar());
		assertEquals('e', parser.nextChar());
		assertEquals(CodeParser.EOC, parser.nextChar());
	}

	@Test
	public void testGetChar() {
		parser.setCode("my code");
		assertEquals('m', parser.getChar());
		parser.setPosition(2);
		assertEquals(' ', parser.getChar());
	}

	@Test
	public void testIsEOCEmpty() {
		parser.setCode("");
		assertTrue(parser.isEOC());
	}

	@Test
	public void testIsNotEOC() {
		parser.setCode("aa");
		assertFalse(parser.isEOC());
	}

	@Test
	public void testGetPositionInit() {
		parser.initialise();
		assertEquals(0, parser.getPosition());
	}

	@Test
	public void testGetPosition() {
		parser.setPosition(10);
		assertEquals(10, parser.getPosition());
	}

	@Test
	public abstract void testSkipComment() throws ParseException;

	@Test
	public abstract void testSkipWSP() throws ParseException;

	@Test
	public abstract void testParse() throws ParseException;

	@Test
	public void testIsNotEOL() {
		parser.setCode("");
		assertFalse(parser.isEOL());
	}

	@Test
	public void testIsEOL() {
		parser.setCode("a\n");
		parser.nextChar();
		assertTrue(parser.isEOL());
	}

	@Test
	public void testSetPosition() {
		parser.setCode("");
		parser.setPosition(10);
		assertEquals(10, parser.getPosition());
	}

	@Test
	public void testSetPositionKO() {
		parser.setCode("");
		parser.setPosition(10);
		parser.setPosition(-1);
		assertEquals(10, parser.getPosition());
	}

	@Test
	public void testSetLinePosition() {
		parser.setLinePosition(10);
		assertEquals(10, parser.getLinePosition());
	}

	@Test
	public void testSetLinePositionKO() {
		parser.setLinePosition(10);
		parser.setLinePosition(0);
		assertEquals(10, parser.getLinePosition());
	}
}
