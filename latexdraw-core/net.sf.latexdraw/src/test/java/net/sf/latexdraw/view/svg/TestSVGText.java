package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGText;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestSVGText {
	SVGDocument doc;
	SVGText txt;

	@Before
	public void setUp() {
		doc = new SVGDocument();
		txt = createSVGText("test", doc);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorFail1() {
		txt = createSVGText(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorFail2() {
		txt = createSVGText(null, doc);
	}

	@Test
	public void testConstructorOK1() {
		txt = createSVGText("a", null);
		assertEquals("a", txt.getData());
		assertNull(txt.getOwnerDocument());
	}

	@Test
	public void testConstructorOK2() {
		assertEquals("test", txt.getData());
		assertEquals(txt.getOwnerDocument(), doc);
	}

	@Test
	public void testGetNodeValue() {
		assertEquals("test", txt.getNodeValue());
		txt = createSVGText("", doc);
		assertEquals("", txt.getNodeValue());
	}

	@Test
	public void testAppendDataDefault() {
		assertEquals("test", txt.getData());
	}

	@Test
	public void testAppendDataNULL() {
		txt.appendData(null);
		assertEquals("test", txt.getData());
	}

	@Test
	public void testAppendDataAppend() {
		txt.appendData("coucou");
		assertEquals("testcoucou", txt.getData());
	}

	@Test
	public void testGetData() {
		assertEquals("test", txt.getData());
	}

	@Test
	public void testGetLength() {
		assertEquals("text".length(), txt.getLength());
	}

	@Test
	public void testGetNodeType() {
		assertEquals(Node.TEXT_NODE, txt.getNodeType());
	}

	@Test
	public void testSetData() {
		txt.setData("coucou");
		assertEquals(txt.getData(), "coucou");
	}

	protected SVGText createSVGText(String str, SVGDocument document) {
		return new SVGText(str, document);
	}
}
