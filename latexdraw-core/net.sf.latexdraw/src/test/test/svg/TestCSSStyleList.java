package test.svg;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.CSSStyleList;

import org.junit.Test;

public class TestCSSStyleList extends TestCase {
	protected CSSStyleList list = new CSSStyleList();

	@Test
	public void testAddCSSStyle() {
		list.clear();
		list.addCSSStyle(null, null);
		assertEquals(0, list.size());
		list.addCSSStyle("", null);
		assertEquals(0, list.size());
		list.addCSSStyle(null, "");
		assertEquals(0, list.size());
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
		list.clear();
	}


	@Test
	public void testGetCSSValue() {
		list.clear();
		assertNull(list.getCSSValue(null));
		assertNull(list.getCSSValue(""));
		assertNull(list.getCSSValue("test"));
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
		assertNull(list.getCSSValue(null));
		assertNull(list.getCSSValue(""));
		assertNull(list.getCSSValue("test"));
		list.clear();
	}


	@Test
	public void testOnCSSStyle() {
		list.clear();
		list.addCSSStyle(null, null);
		assertEquals(0, list.size());
		list.addCSSStyle("", null);
		assertEquals(0, list.size());
		list.addCSSStyle(null, "");
		assertEquals(0, list.size());
		list.addCSSStyle("name", "value");
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name"));
		list.clear();
	}
}
