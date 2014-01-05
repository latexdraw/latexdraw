package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.TicksStyle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

import org.junit.Test;

public class TestTicksStyle {
	@Test
	public void testGetPSTToken() {
		assertEquals(PSTricksConstants.TOKEN_TICKS_STYLE_FULL, TicksStyle.FULL.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM, TicksStyle.BOTTOM.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_TICKS_STYLE_TOP, TicksStyle.TOP.getPSTToken());
	}

	@Test
	public void testIsBottom() {
		assertTrue(TicksStyle.FULL.isBottom());
		assertFalse(TicksStyle.TOP.isBottom());
		assertTrue(TicksStyle.BOTTOM.isBottom());
	}


	@Test
	public void testIsTop() {
		assertTrue(TicksStyle.FULL.isTop());
		assertTrue(TicksStyle.TOP.isTop());
		assertFalse(TicksStyle.BOTTOM.isTop());
	}

	@Test
	public void testToString() {
		assertNotNull(TicksStyle.BOTTOM.toString());
		assertTrue(TicksStyle.BOTTOM.toString().length()>0);
		assertNotNull(TicksStyle.FULL.toString());
		assertTrue(TicksStyle.FULL.toString().length()>0);
		assertNotNull(TicksStyle.TOP.toString());
		assertTrue(TicksStyle.TOP.toString().length()>0);
	}


	@Test
	public void testGetStyle() {
		assertNull(TicksStyle.getStyle(null));
		assertNull(TicksStyle.getStyle(""));
		assertNull(TicksStyle.getStyle("ezpoke zae"));
		assertNull(TicksStyle.getStyle("7dsqd 4ds'"));
		assertEquals(TicksStyle.getStyle(TicksStyle.BOTTOM.toString()), TicksStyle.BOTTOM);
		assertEquals(TicksStyle.getStyle(TicksStyle.FULL.toString()), TicksStyle.FULL);
		assertEquals(TicksStyle.getStyle(TicksStyle.TOP.toString()), TicksStyle.TOP);

		assertNull(TicksStyle.getStyle(null));
		assertNull(TicksStyle.getStyle(""));
		assertNull(TicksStyle.getStyle("dez"));
		assertNull(TicksStyle.getStyle("&ç eydfs h"));
		assertEquals(TicksStyle.getStyle(PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM), TicksStyle.BOTTOM);
		assertEquals(TicksStyle.getStyle(PSTricksConstants.TOKEN_TICKS_STYLE_FULL), TicksStyle.FULL);
		assertEquals(TicksStyle.getStyle(PSTricksConstants.TOKEN_TICKS_STYLE_TOP), TicksStyle.TOP);
	}
}
