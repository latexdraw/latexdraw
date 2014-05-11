package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGText;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

public class TestSVGText{
	protected SVGDocument doc;


	@Before
	public void setUp() {
		doc = new SVGDocument();
	}


	@Test
	public void testConstructors() {
		SVGText txt;

		try {
			txt = createSVGText(null, null);
			fail();
		}
		catch(Exception e) { /* ok */ }

		try {
			txt = createSVGText(null, doc);
			fail();
		}
		catch(Exception e) { /* ok */ }

		txt = createSVGText("a", null);
		assertEquals("a", txt.getData());
		assertNull(txt.getOwnerDocument());

		txt = createSVGText("test", doc);
		assertEquals("test", txt.getData());
		assertEquals(txt.getOwnerDocument(), doc);
	}


	@Test
	public void testGetNodeValue() {
		SVGText txt = createSVGText("test", doc);
		assertEquals("test", txt.getNodeValue());
		txt = createSVGText("", doc);
		assertEquals("", txt.getNodeValue());
	}


	@Test
	public void testAppendData() {
		SVGText txt = createSVGText("test", doc);
		txt.appendData(null);
		assertEquals("test", txt.getData());
		txt.appendData("coucou");
		assertEquals("testcoucou", txt.getData());
		txt.appendData("");
		assertEquals("testcoucou", txt.getData());
	}


	@Test
	public void testGetData() {
		SVGText txt = createSVGText("test", doc);
		assertEquals("test", txt.getData());
		txt = createSVGText("", doc);
		assertEquals("", txt.getData());
	}


	@Test
	public void testGetLength() {
		SVGText txt = createSVGText("test", doc);
		assertEquals("text".length(), txt.getLength());
		txt = createSVGText("", doc);
		assertEquals("".length(), txt.getLength());
	}


	@Test
	public void testGetNodeType() {
		SVGText txt = createSVGText("test", doc);
		assertEquals(Node.TEXT_NODE, txt.getNodeType());
	}


	@Test
	public void testSetData() {
		SVGText txt = createSVGText("test", doc);
		txt.setData("");
		assertEquals(txt.getData(), "");
		txt.setData("coucou");
		assertEquals(txt.getData(), "coucou");
	}


	protected SVGText createSVGText(String txt, SVGDocument document) throws IllegalArgumentException {
		return new SVGText(txt, document);
	}
}

