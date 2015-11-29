package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGNodeList;

import org.junit.Before;
import org.junit.Test;

public class TestSVGNodeList {
	protected SVGNodeList list;
	protected SVGDocument doc;

	@Before
	public void setUp() {
		doc = new SVGDocument();
		list = new SVGNodeList();
	}

	@Test
	public void testSVGNodeList() {
		assertNotNull(list.getNodes());
	}

	@Test
	public void testGetLength() {
		list.getNodes().clear();
		assertEquals(0, list.getLength());
		list.getNodes().add((SVGElement)doc.createElement("elt")); //$NON-NLS-1$
		assertEquals(1, list.getLength());
		list.getNodes().clear();
	}

	@Test
	public void testItem() {
		SVGElement elt = (SVGElement)doc.createElement("elt"); //$NON-NLS-1$

		list.getNodes().clear();
		assertNull(list.item(0));
		assertNull(list.item(-1));
		assertNull(list.item(1));
		list.getNodes().add(elt);
		assertNull(list.item(-1));
		assertNull(list.item(1));
		assertEquals(list.item(0), elt);
	}

	@Test
	public void testGetNodes() {
		assertNotNull(list.getNodes());
	}
}
