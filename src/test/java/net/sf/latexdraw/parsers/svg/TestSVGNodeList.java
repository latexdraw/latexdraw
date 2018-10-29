package net.sf.latexdraw.parsers.svg;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(Theories.class)
public class TestSVGNodeList {
	SVGNodeList list;
	SVGDocument doc;

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
	public void testGetLength0() {
		assertEquals(0, list.getLength());
	}

	@Test
	public void testGetLength1() {
		list.getNodes().add((SVGElement) doc.createElement("elt"));
		assertEquals(1, list.getLength());
	}

	@Theory
	public void testItem(@TestedOn(ints = {0, -1, 1}) final int index) {
		assertNull(list.item(index));
	}

	@Test
	public void testItemKO() {
		final SVGElement elt = (SVGElement) doc.createElement("elt");
		list.getNodes().add(elt);
		assertNull(list.item(-1));
		assertNull(list.item(1));
	}

	@Test
	public void testItemOK() {
		final SVGElement elt = (SVGElement) doc.createElement("elt");
		list.getNodes().add(elt);
		assertEquals(elt, list.item(0));
	}

	@Test
	public void testGetNodes() {
		assertNotNull(list.getNodes());
	}
}
