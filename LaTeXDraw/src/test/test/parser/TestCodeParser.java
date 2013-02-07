package test.parser;

import net.sf.latexdraw.parsers.CodeParser;
import junit.framework.TestCase;

public abstract class TestCodeParser extends TestCase
{
	protected CodeParser parser;
	protected CodeParser parser2;


	public void testInitialise() {
		parser.setPosition(10);
		parser.initialise();
		assertTrue(parser.getPosition()==0);
	}


	public void testGetCharAt() {
		parser.setCode("test");
		assertEquals(CodeParser.EOC, parser.getCharAt(-2));
		assertEquals(CodeParser.EOC, parser.getCharAt(10));
		assertEquals(CodeParser.EOC, parser.getCharAt(4));
		assertEquals('t', parser.getCharAt(0));
		assertEquals('e', parser.getCharAt(1));
		assertEquals('s', parser.getCharAt(2));
		assertEquals('t', parser.getCharAt(3));
	}


	public void testGetCode() {
		String code = "this is my code";

		parser.setCode(code);
		assertEquals(parser.getCode(), code);

		code = "";

		parser.setCode(code);
		assertEquals(parser.getCode(), code);
	}


	public void testSetCode() {
		String code = "a piece of code";

		parser.setPosition(10);
		parser.setCode(code);
		assertTrue(parser.getPosition()==0);
		parser.setCode(null);
		assertEquals(parser.getCode(), code);
	}



	public void testNextChar() {
		parser.setCode("code");
		assertTrue(parser.nextChar()=='o');
		assertTrue(parser.nextChar()=='d');
		assertTrue(parser.nextChar()=='e');
		assertTrue(parser.nextChar()==CodeParser.EOC);
		assertTrue(parser.nextChar()==CodeParser.EOC);
		assertTrue(parser.nextChar()==CodeParser.EOC);
		parser.setPosition(20);
		assertTrue(parser.nextChar()==CodeParser.EOC);
	}


	public void testGetChar() {
		parser.setCode("my code");
		assertTrue(parser.getChar()=='m');
		parser.setPosition(1);
		assertTrue(parser.getChar()=='y');
		parser.setPosition(2);
		assertTrue(parser.getChar()==' ');
		parser.setPosition(10);
		assertTrue(parser.getChar()==CodeParser.EOC);
	}


	public void testIsEOC() {
		parser.setCode("");
		assertTrue(parser.isEOC());
		parser.setCode("aa");
		assertFalse(parser.isEOC());
		parser.nextChar();
		assertFalse(parser.isEOC());
		parser.nextChar();
		assertTrue(parser.isEOC());
		parser.setPosition(100);
		assertTrue(parser.isEOC());
	}


	public void testGetPosition() {
		parser.initialise();
		assertTrue(parser.getPosition()==0);
		parser.setPosition(10);
		assertTrue(parser.getPosition()==10);
		parser.setPosition(100);
		assertTrue(parser.getPosition()==100);
	}


	public abstract void testSkipComment();

	public abstract void testSkipWSP();

	public abstract void testParse();


	public void testIsEOL() {
		parser.setCode("");
		assertFalse(parser.isEOL());
		parser.setCode("a");
		assertFalse(parser.isEOL());
		parser.nextChar();
		assertFalse(parser.isEOL());
		parser.setCode("a\n");
		assertFalse(parser.isEOL());
		parser.nextChar();
		assertTrue(parser.isEOL());
		parser.setCode("a\r");
		assertFalse(parser.isEOL());
		parser.nextChar();
		assertTrue(parser.isEOL());
		parser.setCode("a\r\n");
		assertFalse(parser.isEOL());
		parser.nextChar();
		assertTrue(parser.isEOL());
		assertTrue(parser.getPosition()==2);
	}


	public void testSetPosition() {
		parser.setCode("");
		parser.setPosition(10);
		assertEquals(10, parser.getPosition());
		parser.setPosition(-1);
		assertEquals(10, parser.getPosition());
		parser.setPosition(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, parser.getPosition());
		parser.setPosition(Integer.MIN_VALUE);
		assertEquals(Integer.MAX_VALUE, parser.getPosition());
	}


	public void testIncLinePosition() {
		parser.setLinePosition(1);
		parser.incLinePosition();
		assertEquals(2, parser.getLinePosition());
		parser.incLinePosition();
		assertEquals(3, parser.getLinePosition());
		parser.incLinePosition();
		assertEquals(4, parser.getLinePosition());
	}



	public void testSetLinePosition() {
		parser.setLinePosition(10);
		assertEquals(10, parser.getLinePosition());
		parser.setLinePosition(0);
		assertEquals(10, parser.getLinePosition());
		parser.setLinePosition(-10);
		assertEquals(10, parser.getLinePosition());
		parser.setLinePosition(20);
		assertEquals(20, parser.getLinePosition());
		parser.setLinePosition(Integer.MIN_VALUE);
		assertEquals(20, parser.getLinePosition());
		parser.setLinePosition(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, parser.getLinePosition());
	}


	public void testGetCoreCode() {
		assertNotNull(parser.getCodeCore());
	}


	public void testSetCodeCore() {
		parser2.setCodeCore(parser.getCodeCore());
		assertEquals(parser2.getCodeCore(), parser.getCodeCore());
	}
}
