package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCSSStyleList {
	CSSStyleList list;

	@BeforeEach
	void setUp() {
		list = new CSSStyleList();
	}

	@Test
	void testAddCSSStyleNULLNULL() {
		list.addCSSStyle(null, null);
		assertEquals(0, list.size());
	}

	@Test
	void testAddCSSStyleEmptyNULL() {
		list.addCSSStyle("", null);
		assertEquals(0, list.size());
	}

	@Test
	void testAddCSSStyleNULLEmpty() {
		list.addCSSStyle(null, "");
		assertEquals(0, list.size());
	}

	@Test
	void testAddCSSStyleOK() {
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
	}

	@Test
	void testGetCSSValueNULL() {
		assertNull(list.getCSSValue(null));
	}

	@Test
	void testGetCSSValueEmpty() {
		assertNull(list.getCSSValue(""));
	}

	@Test
	void testGetCSSValueNotExisting() {
		assertNull(list.getCSSValue("test"));
	}

	@Test
	void testGetCSSValueOK() {
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
	}

	@Test
	void testOnCSSStyleNULLNULL() {
		list.onCSSStyle(null, null);
		assertEquals(0, list.size());
	}

	@Test
	void testOnCSSStyleEmptyNULL() {
		list.onCSSStyle("", null);
		assertEquals(0, list.size());
	}

	@Test
	void testOnCSSStyleNULLEmpty() {
		list.onCSSStyle(null, "");
		assertEquals(0, list.size());
	}

	@Test
	void testOnCSSStyleOK() {
		list.onCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
	}
}
