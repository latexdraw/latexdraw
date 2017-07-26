package net.sf.latexdraw.view.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.SVGAttr;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGNamedNodeMap;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;

public class TestSVGNamedNodeMap {
	protected SVGNamedNodeMap map;
	protected SVGDocument doc;

	@Before
	public void setUp() {
		doc = new SVGDocument();
		map = new SVGNamedNodeMap();
	}

	@Test
	public void testSVGNamedNodeMap() {
		assertNotNull(map.getAttributes());
	}

	@Test
	public void testGetLength() {
		map.getAttributes().clear();
		assertEquals(0, map.getLength());
		map.getAttributes().add(new SVGAttr("", "", doc.createElement("elt"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(1, map.getLength());
		map.getAttributes().clear();
	}

	@Test
	public void testGetNamedItem() {
		map.getAttributes().clear();
		assertNull(map.getNamedItem(null));
		assertNull(map.getNamedItem("")); //$NON-NLS-1$
		assertNull(map.getNamedItem("test")); //$NON-NLS-1$
		map.getAttributes().add(new SVGAttr("test", "", doc.createElement("elt"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNull(map.getNamedItem(null));
		assertNull(map.getNamedItem("")); //$NON-NLS-1$
		assertNotNull(map.getNamedItem("test")); //$NON-NLS-1$
		map.getAttributes().clear();
	}

	@Test
	public void testItem() {
		SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		map.getAttributes().clear();
		assertNull(map.item(0));
		assertNull(map.item(-1));
		assertNull(map.item(1));
		map.getAttributes().add(attr);
		assertNull(map.item(-1));
		assertNull(map.item(1));
		assertEquals(map.item(0), attr);
		map.getAttributes().clear();
	}

	@Test
	public void testRemoveNamedItem() {
		SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		map.getAttributes().clear();

		try {
			map.removeNamedItem(null);
			fail();
		}catch(DOMException e) {
			/* ok */ }

		try {
			map.removeNamedItem(""); //$NON-NLS-1$
			fail();
		}catch(DOMException e) {
			/* ok */ }

		try {
			map.removeNamedItem("test"); //$NON-NLS-1$
			fail();
		}catch(DOMException e) {
			/* ok */ }

		map.getAttributes().add(attr);
		assertEquals(attr, map.removeNamedItem("test")); //$NON-NLS-1$

		try {
			map.removeNamedItem("test"); //$NON-NLS-1$
			fail();
		}catch(DOMException e) {
			/* ok */ }

		map.getAttributes().clear();
	}

	@Test
	public void testSetNamedItem() {
		SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		SVGAttr attr2 = new SVGAttr("test2", "v2", doc.createElement("elt2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		SVGAttr attr3 = new SVGAttr("test1", "v1b", doc.createElement("elt1b")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		map.getAttributes().clear();
		assertNull(map.setNamedItem(null));
		assertNull(map.setNamedItem(attr1));
		assertEquals(attr1, map.getNamedItem("test1")); //$NON-NLS-1$
		assertNull(map.setNamedItem(attr2));
		assertEquals(attr1, map.getNamedItem("test1")); //$NON-NLS-1$
		assertEquals(attr2, map.getNamedItem("test2")); //$NON-NLS-1$
		assertEquals(attr1, map.setNamedItem(attr3));
		assertEquals(attr3, map.getNamedItem("test1")); //$NON-NLS-1$
		assertEquals(attr2, map.getNamedItem("test2")); //$NON-NLS-1$
		map.getAttributes().clear();
	}

	@Test
	public void testClone() {
		SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		SVGAttr attr2 = new SVGAttr("test2", "v2", doc.createElement("elt2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		SVGNamedNodeMap map2;

		map.getAttributes().clear();
		assertNull(map.setNamedItem(attr1));
		assertNull(map.setNamedItem(attr2));
		map2 = (SVGNamedNodeMap)map.clone();
		assertEquals(map.getLength(), map2.getLength());
		assertTrue(map.getNamedItem("test1").isEqualNode(map2.getNamedItem("test1"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(map.getNamedItem("test2").isEqualNode(map2.getNamedItem("test2"))); //$NON-NLS-1$ //$NON-NLS-2$
		map.getAttributes().clear();
	}

	@Test
	public void testGetAttributes() {
		assertNotNull(map.getAttributes());
	}
}
