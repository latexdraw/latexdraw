package test.parser.svg.parsers;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.parsers.URIReferenceParser;

import org.junit.Test;

@SuppressWarnings("nls")
public class TestURIReferenceParser extends TestCase
{
	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		try {
			new URIReferenceParser(null);
			fail();
		}
		catch(IllegalArgumentException e) { /* */ }

		new URIReferenceParser("test");
	}



	@Test
	public void testGetURI() {
		URIReferenceParser p = new URIReferenceParser("url(#id)");

		assertEquals(p.getURI(), "id");
		p.setCode("");
		assertEquals(p.getURI(), "");
		p.setCode("url(#id");
		assertEquals(p.getURI(), "");
		p.setCode("url#id)");
		assertEquals(p.getURI(), "");
		p.setCode("url(id)");
		assertEquals(p.getURI(), "");
		p.setCode(null);
		assertEquals(p.getURI(), "");
		p.setCode("u");
		assertEquals(p.getURI(), "");
		p.setCode("ur");
		assertEquals(p.getURI(), "");
		p.setCode("url");
		assertEquals(p.getURI(), "");
		p.setCode("url(");
		assertEquals(p.getURI(), "");
		p.setCode("url()");
		assertEquals(p.getURI(), "");
		p.setCode("url(#)");
		assertEquals(p.getURI(), "");
	}
}
