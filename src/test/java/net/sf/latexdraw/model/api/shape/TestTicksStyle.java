package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestTicksStyle {
	@Test
	void testGetPSTToken() {
		assertEquals(PSTricksConstants.TOKEN_TICKS_STYLE_FULL, TicksStyle.FULL.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM, TicksStyle.BOTTOM.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_TICKS_STYLE_TOP, TicksStyle.TOP.getPSTToken());
	}

	@Test
	void testIsBottom() {
		assertTrue(TicksStyle.FULL.isBottom());
		assertFalse(TicksStyle.TOP.isBottom());
		assertTrue(TicksStyle.BOTTOM.isBottom());
	}

	@Test
	void testIsTop() {
		assertTrue(TicksStyle.FULL.isTop());
		assertTrue(TicksStyle.TOP.isTop());
		assertFalse(TicksStyle.BOTTOM.isTop());
	}

	@Test
	void testGetStyleKO() {
		assertEquals(TicksStyle.FULL, TicksStyle.getStyle(null));
		assertEquals(TicksStyle.FULL, TicksStyle.getStyle(""));
	}

	@ParameterizedTest
	@EnumSource(TicksStyle.class)
	void testGetStyle(final TicksStyle style) {
		assertEquals(TicksStyle.getStyle(style.toString()), style);
	}

	@ParameterizedTest
	@EnumSource(TicksStyle.class)
	void testGetStylePST(final TicksStyle style) {
		assertEquals(TicksStyle.getStyle(style.getPSTToken()), style);
	}
}
