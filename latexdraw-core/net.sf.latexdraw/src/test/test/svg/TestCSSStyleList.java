package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import net.sf.latexdraw.parsers.svg.CSSStyleList;

import org.junit.Test;

public class TestCSSStyleList {
	protected CSSStyleList list = new CSSStyleList();

	@Test
	public void testAddCSSStyle() {
		list.clear();
		list.addCSSStyle(null, null);
		assertEquals(0, list.size());
		list.addCSSStyle("", null); //$NON-NLS-1$
		assertEquals(0, list.size());
		list.addCSSStyle(null, ""); //$NON-NLS-1$
		assertEquals(0, list.size());
		list.addCSSStyle("name", "value"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name")); //$NON-NLS-1$ //$NON-NLS-2$
		list.clear();
	}

	@Test
	public void testGetCSSValue() {
		list.clear();
		assertNull(list.getCSSValue(null));
		assertNull(list.getCSSValue("")); //$NON-NLS-1$
		assertNull(list.getCSSValue("test")); //$NON-NLS-1$
		list.addCSSStyle("name", "value"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name")); //$NON-NLS-1$ //$NON-NLS-2$
		assertNull(list.getCSSValue(null));
		assertNull(list.getCSSValue("")); //$NON-NLS-1$
		assertNull(list.getCSSValue("test")); //$NON-NLS-1$
		list.clear();
	}

	@Test
	public void testOnCSSStyle() {
		list.clear();
		list.addCSSStyle(null, null);
		assertEquals(0, list.size());
		list.addCSSStyle("", null); //$NON-NLS-1$
		assertEquals(0, list.size());
		list.addCSSStyle(null, ""); //$NON-NLS-1$
		assertEquals(0, list.size());
		list.addCSSStyle("name", "value"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(1, list.size());
		assertEquals("value", list.getCSSValue("name")); //$NON-NLS-1$ //$NON-NLS-2$
		list.clear();
	}
}
