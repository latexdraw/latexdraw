package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGText {
	SVGDocument doc;
	SVGText txt;

	@BeforeEach
	void setUp() {
		doc = new SVGDocument();
		txt = createSVGText("test", doc);
	}

	@Test
	void testConstructorFail1() {
		assertThrows(NullPointerException.class, () -> txt = createSVGText(null, null));
	}

	@Test
	void testConstructorFail2() {
		assertThrows(NullPointerException.class, () -> txt = createSVGText(null, doc));
	}

	@Test
	void testConstructorOK1() {
		txt = createSVGText("a", null);
		assertEquals("a", txt.getData());
		assertNull(txt.getOwnerDocument());
	}

	@Test
	void testConstructorOK2() {
		assertEquals("test", txt.getData());
		assertEquals(txt.getOwnerDocument(), doc);
	}

	@Test
	void testGetNodeValue() {
		assertEquals("test", txt.getNodeValue());
		txt = createSVGText("", doc);
		assertEquals("", txt.getNodeValue());
	}

	@Test
	void testAppendDataDefault() {
		assertEquals("test", txt.getData());
	}

	@Test
	void testAppendDataNULL() {
		txt.appendData(null);
		assertEquals("test", txt.getData());
	}

	@Test
	void testAppendDataAppend() {
		txt.appendData("coucou");
		assertEquals("testcoucou", txt.getData());
	}

	@Test
	void testGetData() {
		assertEquals("test", txt.getData());
	}

	@Test
	void testGetLength() {
		assertEquals(txt.getLength(), "text".length());
	}

	@Test
	void testGetNodeType() {
		assertEquals(Node.TEXT_NODE, txt.getNodeType());
	}

	@Test
	void testSetData() {
		txt.setData("coucou");
		assertEquals("coucou", txt.getData());
	}

	SVGText createSVGText(final String str, final SVGDocument document) {
		return new SVGText(str, document);
	}
}
