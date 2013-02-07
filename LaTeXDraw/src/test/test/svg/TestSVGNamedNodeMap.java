package test.svg;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.SVGAttr;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGNamedNodeMap;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;

public class TestSVGNamedNodeMap extends TestCase {
	protected SVGNamedNodeMap map;
	protected SVGDocument doc;

	@Override
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
		map.getAttributes().add(new SVGAttr("", "", doc.createElement("elt")));
		assertEquals(1, map.getLength());
		map.getAttributes().clear();
	}


	@Test
	public void testGetNamedItem() {
		map.getAttributes().clear();
		assertNull(map.getNamedItem(null));
		assertNull(map.getNamedItem(""));
		assertNull(map.getNamedItem("test"));
		map.getAttributes().add(new SVGAttr("test", "", doc.createElement("elt")));
		assertNull(map.getNamedItem(null));
		assertNull(map.getNamedItem(""));
		assertNotNull(map.getNamedItem("test"));
		map.getAttributes().clear();
	}


	@Test
	public void testItem() {
		SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt"));

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
		SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt"));

		map.getAttributes().clear();

		try {
			map.removeNamedItem(null);
			fail();
		}
		catch(DOMException e) { /* ok */ }

		try {
			map.removeNamedItem("");
			fail();
		}
		catch(DOMException e) { /* ok */ }

		try {
			map.removeNamedItem("test");
			fail();
		}
		catch(DOMException e) { /* ok */ }

		map.getAttributes().add(attr);
		assertEquals(attr, map.removeNamedItem("test"));

		try {
			map.removeNamedItem("test");
			fail();
		}
		catch(DOMException e) { /* ok */ }

		map.getAttributes().clear();
	}


	@Test
	public void testSetNamedItem() {
		SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1"));
		SVGAttr attr2 = new SVGAttr("test2", "v2", doc.createElement("elt2"));
		SVGAttr attr3 = new SVGAttr("test1", "v1b", doc.createElement("elt1b"));

		map.getAttributes().clear();
		assertNull(map.setNamedItem(null));
		assertNull(map.setNamedItem(attr1));
		assertEquals(attr1, map.getNamedItem("test1"));
		assertNull(map.setNamedItem(attr2));
		assertEquals(attr1, map.getNamedItem("test1"));
		assertEquals(attr2, map.getNamedItem("test2"));
		assertEquals(attr1, map.setNamedItem(attr3));
		assertEquals(attr3, map.getNamedItem("test1"));
		assertEquals(attr2, map.getNamedItem("test2"));
		map.getAttributes().clear();
	}


	@Test
	public void testClone() {
		SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1"));
		SVGAttr attr2 = new SVGAttr("test2", "v2", doc.createElement("elt2"));
		SVGNamedNodeMap map2;

		map.getAttributes().clear();
		assertNull(map.setNamedItem(attr1));
		assertNull(map.setNamedItem(attr2));
		map2 = (SVGNamedNodeMap)map.clone();
		assertEquals(map.getLength(), map2.getLength());
		assertTrue(map.getNamedItem("test1").isEqualNode(map2.getNamedItem("test1")));
		assertTrue(map.getNamedItem("test2").isEqualNode(map2.getNamedItem("test2")));
		map.getAttributes().clear();
	}


	@Test
	public void testGetAttributes() {
		assertNotNull(map.getAttributes());
	}
}
