package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestAxesStyle {
	@Test
	public void testSupportsArrows() {
		assertTrue(AxesStyle.AXES.supportsArrows());
		assertFalse(AxesStyle.FRAME.supportsArrows());
		assertFalse(AxesStyle.NONE.supportsArrows());
	}

	@Test
	public void testGetPSTToken() {
		assertEquals(PSTricksConstants.TOKEN_AXES_STYLE_AXES, AxesStyle.AXES.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_AXES_STYLE_FRAME, AxesStyle.FRAME.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_AXES_STYLE_NONE, AxesStyle.NONE.getPSTToken());
	}

	@Theory
	public void testToString(final AxesStyle style) {
		assertNotNull(style.toString());
		assertFalse(style.toString().isEmpty());
	}

	@Test
	public void testGetStylePSTConst() {
		assertEquals(AxesStyle.getStyle(PSTricksConstants.TOKEN_AXES_STYLE_AXES), AxesStyle.AXES);
		assertEquals(AxesStyle.getStyle(PSTricksConstants.TOKEN_AXES_STYLE_FRAME), AxesStyle.FRAME);
		assertEquals(AxesStyle.getStyle(PSTricksConstants.TOKEN_AXES_STYLE_NONE), AxesStyle.NONE);
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(AxesStyle.AXES, AxesStyle.getStyle(null));
		assertEquals(AxesStyle.AXES, AxesStyle.getStyle("")); //$NON-NLS-1$
	}

	@Test
	public void testGetStyleAxesStyle() {
		assertEquals(AxesStyle.getStyle(AxesStyle.NONE.toString()), AxesStyle.NONE);
		assertEquals(AxesStyle.getStyle(AxesStyle.AXES.toString()), AxesStyle.AXES);
		assertEquals(AxesStyle.getStyle(AxesStyle.FRAME.toString()), AxesStyle.FRAME);
	}
}
