package net.sf.latexdraw.parsers.svg.parsers;

import net.sf.latexdraw.data.StringData;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestURIReferenceParser {
	URIReferenceParser p;

	@Before
	public void setUp() throws Exception {
		p = new URIReferenceParser("url(#id)");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorKO() {
		new URIReferenceParser(null);
	}

	@Test
	public void testGetURIOK() {
		assertEquals("id", p.getURI());
	}

	@Test
	public void testSetCodeOK() {
		p.setCode("url(#id2)");
		assertEquals("id2", p.getURI());
	}

	@Theory
	public void testSetCodeKO(@StringData(vals = {"", "url(#id", "url#id)", "url(id)", "u", "url(", "url(#)"}) final String data) {
		p.setCode(data);
		assertEquals("", p.getURI());
	}
}
