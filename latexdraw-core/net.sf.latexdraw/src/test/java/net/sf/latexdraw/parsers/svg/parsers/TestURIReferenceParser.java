package net.sf.latexdraw.parsers.svg.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.parsers.URIReferenceParser;

import org.junit.Test;

public class TestURIReferenceParser {
	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		try {
			new URIReferenceParser(null);
			fail();
		}catch(IllegalArgumentException e) {
			/* */ }

		new URIReferenceParser("test"); //$NON-NLS-1$
	}

	@Test
	public void testGetURI() {
		URIReferenceParser p = new URIReferenceParser("url(#id)"); //$NON-NLS-1$

		assertEquals(p.getURI(), "id"); //$NON-NLS-1$
		p.setCode(""); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url(#id"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url#id)"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url(id)"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode(null);
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("u"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("ur"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url("); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url()"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
		p.setCode("url(#)"); //$NON-NLS-1$
		assertEquals(p.getURI(), ""); //$NON-NLS-1$
	}
}
