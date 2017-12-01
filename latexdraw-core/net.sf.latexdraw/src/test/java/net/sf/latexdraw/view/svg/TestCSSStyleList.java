package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.CSSStyleList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestCSSStyleList {
	CSSStyleList list;

	@Before
	public void setUp() {
		list = new CSSStyleList();
	}

	@Test
	public void testAddCSSStyleNULLNULL() {
		list.addCSSStyle(null, null);
		assertEquals(0, list.size());
	}

	@Test
	public void testAddCSSStyleEmptyNULL() {
		list.addCSSStyle("", null);
		assertEquals(0, list.size());
	}

	@Test
	public void testAddCSSStyleNULLEmpty() {
		list.addCSSStyle(null, "");
		assertEquals(0, list.size());
	}

	@Test
	public void testAddCSSStyleOK() {
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
	}

	@Test
	public void testGetCSSValueNULL() {
		assertNull(list.getCSSValue(null));
	}

	@Test
	public void testGetCSSValueEmpty() {
		assertNull(list.getCSSValue(""));
	}

	@Test
	public void testGetCSSValueNotExisting() {
		assertNull(list.getCSSValue("test"));
	}

	@Test
	public void testGetCSSValueOK() {
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
	}

	@Test
	public void testOnCSSStyleNULLNULL() {
		list.onCSSStyle(null, null);
		assertEquals(0, list.size());
	}

	@Test
	public void testOnCSSStyleEmptyNULL() {
		list.onCSSStyle("", null);
		assertEquals(0, list.size());
	}

	@Test
	public void testOnCSSStyleNULLEmpty() {
		list.onCSSStyle(null, "");
		assertEquals(0, list.size());
	}

	@Test
	public void testOnCSSStyleOK() {
		list.onCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
	}
}
