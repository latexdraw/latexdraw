package net.sf.latexdraw.models.interfaces.shape;

import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	public void testGetStylePSTConst(final AxesStyle style) {
		assertEquals(AxesStyle.getStyle(style.getPSTToken()), style);
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(AxesStyle.AXES, AxesStyle.getStyle(null));
		assertEquals(AxesStyle.AXES, AxesStyle.getStyle(""));
	}

	@Theory
	public void testGetStyleAxesStyle(final AxesStyle style) {
		assertEquals(AxesStyle.getStyle(style.toString()), style);
	}
}
