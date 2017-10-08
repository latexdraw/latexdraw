package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
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
	public void testGetStyleKO() {
		assertEquals(TicksStyle.FULL, TicksStyle.getStyle(null));
		assertEquals(TicksStyle.FULL, TicksStyle.getStyle(""));
	}

	@Theory
	public void testGetStyle(final TicksStyle style) {
		assertEquals(TicksStyle.getStyle(style.toString()), style);
	}

	@Theory
	public void testGetStylePST(final TicksStyle style) {
		assertEquals(TicksStyle.getStyle(style.getPSTToken()), style);
	}
}
