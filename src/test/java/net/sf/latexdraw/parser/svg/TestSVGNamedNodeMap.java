package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.DOMException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGNamedNodeMap {
	SVGNamedNodeMap map;
	SVGDocument doc;

	@BeforeEach
	void setUp() {
		doc = new SVGDocument();
		map = new SVGNamedNodeMap();
	}

	@Test
	void testSVGNamedNodeMap() {
		assertNotNull(map.getAttributes());
	}

	@Test
	void testGetLengthEmpty() {
		map.getAttributes().clear();
		assertEquals(0, map.getLength());
	}

	@Test
	void testGetLength() {
		map.getAttributes().add(new SVGAttr("", "", doc.createElement("elt")));
		assertEquals(1, map.getLength());
	}

	@Test
	void testGetNamedItemNULL() {
		assertNull(map.getNamedItem(null));
	}

	@Test
	void testGetNamedItemEmpty() {
		assertNull(map.getNamedItem(""));
	}

	@Test
	void testGetNamedItemInvalid() {
		assertNull(map.getNamedItem("test"));
	}

	@Test
	void testGetNamedItemOK() {
		map.getAttributes().add(new SVGAttr("test", "", doc.createElement("elt")));
		assertNotNull(map.getNamedItem("test"));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, 1})
	void testItem(final int index) {
		assertNull(map.item(index));
	}

	@Test
	void testItemKO() {
		final SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt"));
		map.getAttributes().add(attr);
		assertNull(map.item(-1));
	}

	@Test
	void testItemOK() {
		final SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt"));
		map.getAttributes().add(attr);
		assertEquals(map.item(0), attr);
	}

	@Test
	void testRemoveNamedItemKONULL() {
		assertThrows(DOMException.class, () -> map.removeNamedItem(null));
	}


	@Test
	void testRemoveNamedItemKOEmpty() {
		assertThrows(DOMException.class, () -> map.removeNamedItem(""));
	}

	@Test
	void testRemoveNamedItemKOInvalid() {
		assertThrows(DOMException.class, () -> map.removeNamedItem("test"));
	}

	@Test
	void testRemoveNamedItemOK() {
		final SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt"));
		map.getAttributes().add(attr);
		assertEquals(attr, map.removeNamedItem("test"));
	}

	@Test
	void testRemoveNamedItemKOAlreadyRemoved() {
		final SVGAttr attr = new SVGAttr("test", "", doc.createElement("elt"));
		map.getAttributes().add(attr);
		map.removeNamedItem("test");
		assertThrows(DOMException.class, () -> map.removeNamedItem("test"));
	}

	@Test
	void testSetNamedItemKONULL() {
		assertNull(map.setNamedItem(null));
	}

	@Test
	void testSetNamedItemKOInvalid() {
		assertNull(map.setNamedItem(new SVGAttr("test1", "v1", doc.createElement("elt1"))));
	}

	@Test
	void testSetNamedItem() {
		final SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1"));
		map.setNamedItem(attr1);
		assertEquals(attr1, map.getNamedItem("test1"));
	}

	@Test
	void testSetNamedItem2() {
		final SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1"));
		final SVGAttr attr3 = new SVGAttr("test1", "v1b", doc.createElement("elt1b"));
		map.setNamedItem(attr1);
		assertEquals(attr1, map.setNamedItem(attr3));
	}

	@Test
	void testClone2() {
		final SVGAttr attr1 = new SVGAttr("test1", "v1", doc.createElement("elt1"));
		final SVGAttr attr2 = new SVGAttr("test2", "v2", doc.createElement("elt2"));
		map.setNamedItem(attr1);
		map.setNamedItem(attr2);
		final SVGNamedNodeMap map2 = map.duplicate();
		assertEquals(map.getLength(), map2.getLength());
		assertTrue(map.getNamedItem("test1").isEqualNode(map2.getNamedItem("test1")));
		assertTrue(map.getNamedItem("test2").isEqualNode(map2.getNamedItem("test2")));
	}

	@Test
	void testGetAttributes() {
		assertNotNull(map.getAttributes());
	}
}
