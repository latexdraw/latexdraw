package net.sf.latexdraw.parser.svg.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestURIReferenceParser {
	URIReferenceParser p;

	@BeforeEach
	void setUp() {
		p = new URIReferenceParser("url(#id)");
	}

	@Test
	void testConstructorKO() {
		assertThrows(IllegalArgumentException.class, () -> new URIReferenceParser(null));
	}

	@Test
	void testGetURIOK() {
		assertEquals("id", p.getURI());
	}

	@Test
	void testSetCodeOK() {
		p.setCode("url(#id2)");
		assertEquals("id2", p.getURI());
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "url(#id", "url#id)", "url(id)", "u", "url(", "url(#)"})
	void testSetCodeKO(final String data) {
		p.setCode(data);
		assertEquals("", p.getURI());
	}
}
