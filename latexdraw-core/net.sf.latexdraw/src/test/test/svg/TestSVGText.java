package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGText;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

public class TestSVGText {
	protected SVGDocument doc;
	SVGText txt;

	@Before
	public void setUp() {
		doc = new SVGDocument();
		txt = createSVGText("test", doc); //$NON-NLS-1$
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
		txt = createSVGText("a", null); //$NON-NLS-1$
		assertEquals("a", txt.getData()); //$NON-NLS-1$
		assertNull(txt.getOwnerDocument());
	}

	@Test
	public void testConstructorOK2() {
		assertEquals("test", txt.getData()); //$NON-NLS-1$
		assertEquals(txt.getOwnerDocument(), doc);
	}

	@Test
	public void testGetNodeValue() {
		assertEquals("test", txt.getNodeValue()); //$NON-NLS-1$
		txt = createSVGText("", doc); //$NON-NLS-1$
		assertEquals("", txt.getNodeValue()); //$NON-NLS-1$
	}

	@Test
	public void testAppendData() {
		txt.appendData(null);
		assertEquals("test", txt.getData()); //$NON-NLS-1$
		txt.appendData("coucou"); //$NON-NLS-1$
		assertEquals("testcoucou", txt.getData()); //$NON-NLS-1$
		txt.appendData(""); //$NON-NLS-1$
		assertEquals("testcoucou", txt.getData()); //$NON-NLS-1$
	}

	@Test
	public void testGetData() {
		assertEquals("test", txt.getData()); //$NON-NLS-1$
		txt = createSVGText("", doc); //$NON-NLS-1$
		assertEquals("", txt.getData()); //$NON-NLS-1$
	}

	@Test
	public void testGetLength() {
		assertEquals("text".length(), txt.getLength()); //$NON-NLS-1$
		txt = createSVGText("", doc); //$NON-NLS-1$
		assertEquals("".length(), txt.getLength()); //$NON-NLS-1$
	}

	@Test
	public void testGetNodeType() {
		assertEquals(Node.TEXT_NODE, txt.getNodeType());
	}

	@Test
	public void testSetData() {
		txt.setData(""); //$NON-NLS-1$
		assertEquals(txt.getData(), ""); //$NON-NLS-1$
		txt.setData("coucou"); //$NON-NLS-1$
		assertEquals(txt.getData(), "coucou"); //$NON-NLS-1$
	}

	protected SVGText createSVGText(String str, SVGDocument document) {
		return new SVGText(str, document);
	}
}
