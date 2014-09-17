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

		txt = createSVGText("a", null); //$NON-NLS-1$
		assertEquals("a", txt.getData()); //$NON-NLS-1$
		assertNull(txt.getOwnerDocument());

		txt = createSVGText("test", doc); //$NON-NLS-1$
		assertEquals("test", txt.getData()); //$NON-NLS-1$
		assertEquals(txt.getOwnerDocument(), doc);
	}


	@Test
	public void testGetNodeValue() {
		SVGText txt = createSVGText("test", doc); //$NON-NLS-1$
		assertEquals("test", txt.getNodeValue()); //$NON-NLS-1$
		txt = createSVGText("", doc); //$NON-NLS-1$
		assertEquals("", txt.getNodeValue()); //$NON-NLS-1$
	}


	@Test
	public void testAppendData() {
		SVGText txt = createSVGText("test", doc); //$NON-NLS-1$
		txt.appendData(null);
		assertEquals("test", txt.getData()); //$NON-NLS-1$
		txt.appendData("coucou"); //$NON-NLS-1$
		assertEquals("testcoucou", txt.getData()); //$NON-NLS-1$
		txt.appendData(""); //$NON-NLS-1$
		assertEquals("testcoucou", txt.getData()); //$NON-NLS-1$
	}


	@Test
	public void testGetData() {
		SVGText txt = createSVGText("test", doc); //$NON-NLS-1$
		assertEquals("test", txt.getData()); //$NON-NLS-1$
		txt = createSVGText("", doc); //$NON-NLS-1$
		assertEquals("", txt.getData()); //$NON-NLS-1$
	}


	@Test
	public void testGetLength() {
		SVGText txt = createSVGText("test", doc); //$NON-NLS-1$
		assertEquals("text".length(), txt.getLength()); //$NON-NLS-1$
		txt = createSVGText("", doc); //$NON-NLS-1$
		assertEquals("".length(), txt.getLength()); //$NON-NLS-1$
	}


	@Test
	public void testGetNodeType() {
		SVGText txt = createSVGText("test", doc); //$NON-NLS-1$
		assertEquals(Node.TEXT_NODE, txt.getNodeType());
	}


	@Test
	public void testSetData() {
		SVGText txt = createSVGText("test", doc); //$NON-NLS-1$
		txt.setData(""); //$NON-NLS-1$
		assertEquals(txt.getData(), ""); //$NON-NLS-1$
		txt.setData("coucou"); //$NON-NLS-1$
		assertEquals(txt.getData(), "coucou"); //$NON-NLS-1$
	}


	protected SVGText createSVGText(String txt, SVGDocument document) throws IllegalArgumentException {
		return new SVGText(txt, document);
	}
}

