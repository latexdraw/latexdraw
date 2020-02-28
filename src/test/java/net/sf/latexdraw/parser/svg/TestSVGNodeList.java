package net.sf.latexdraw.parser.svg;

import net.sf.latexdraw.LatexdrawExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(LatexdrawExtension.class)
public class TestSVGNodeList {
	SVGNodeList list;
	SVGDocument doc;

	@BeforeEach
	void setUp() {
		doc = new SVGDocument();
		list = new SVGNodeList();
	}

	@Test
	void testSVGNodeList() {
		assertNotNull(list.getNodes());
	}

	@Test
	void testGetLength0() {
		assertEquals(0, list.getLength());
	}

	@Test
	void testGetLength1() {
		list.getNodes().add((SVGElement) doc.createElement("elt"));
		assertEquals(1, list.getLength());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, 1})
	void testItem(final int index) {
		assertNull(list.item(index));
	}

	@Test
	void testItemKO() {
		final SVGElement elt = (SVGElement) doc.createElement("elt");
		list.getNodes().add(elt);
		assertNull(list.item(-1));
		assertNull(list.item(1));
	}

	@Test
	void testItemOK() {
		final SVGElement elt = (SVGElement) doc.createElement("elt");
		list.getNodes().add(elt);
		assertEquals(elt, list.item(0));
	}

	@Test
	void testGetNodes() {
		assertNotNull(list.getNodes());
	}
}
