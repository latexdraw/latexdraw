package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
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

	@Theory
	public void testToString(final TicksStyle style) {
		assertThat(style.toString(), not(isEmptyOrNullString()));
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(TicksStyle.FULL, TicksStyle.getStyle(null));
		assertEquals(TicksStyle.FULL, TicksStyle.getStyle("")); //$NON-NLS-1$
	}

	@Theory
	public void testGetStyle(final TicksStyle style) {
		assertEquals(TicksStyle.getStyle(style.toString()), style);
	}

	@Test
	public void testGetStylePST() {
		assertEquals(TicksStyle.getStyle(PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM), TicksStyle.BOTTOM);
		assertEquals(TicksStyle.getStyle(PSTricksConstants.TOKEN_TICKS_STYLE_FULL), TicksStyle.FULL);
		assertEquals(TicksStyle.getStyle(PSTricksConstants.TOKEN_TICKS_STYLE_TOP), TicksStyle.TOP);
	}
}
