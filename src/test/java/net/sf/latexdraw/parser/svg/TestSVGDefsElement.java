package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGDefsElement extends TestBaseSVGElement {
	SVGDefsElement defs;

	@BeforeEach
	void setUpDefs() {
		defs = new SVGDefsElement(node, null);
	}

	@Test
	void testEnableRendering() {
		assertFalse(defs.enableRendering());
	}

	@Test
	void testGetDefNULL() {
		assertNull(defs.getDef(null));
	}

	@Test
	void testGetDefEmpty() {
		assertNull(defs.getDef(""));
	}

	@Test
	void testGetDefInvalid() {
		assertNull(defs.getDef("dsqd"));
	}

	@Test
	void testGetDefOK() {
		final SVGMarkerElement mark = new SVGMarkerElement(node.getOwnerDocument());
		mark.setAttribute(SVGAttributes.SVG_ID, SVGAttributes.SVG_ID);
		defs.appendChild(mark);
		assertEquals(mark, defs.getDef("id"));
	}

	@Test
	void testContructorKO() {
		assertThrows(IllegalArgumentException.class, () -> new SVGDefsElement(null, null));
	}

	@Test
	void testContructorOK() {
		new SVGDefsElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_DEFS;
	}
}
