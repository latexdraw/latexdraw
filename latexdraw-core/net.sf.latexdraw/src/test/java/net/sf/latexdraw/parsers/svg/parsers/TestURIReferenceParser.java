package net.sf.latexdraw.parsers.svg.parsers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestURIReferenceParser {
	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		try {
			new URIReferenceParser(null);
			fail();
		}catch(IllegalArgumentException e) {
			/* */ }

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
