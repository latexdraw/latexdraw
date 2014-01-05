package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.AxesStyle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

import org.junit.Test;

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

	@Test
	public void testToString() {
		assertNotNull(AxesStyle.AXES.toString());
		assertNotNull(AxesStyle.FRAME.toString());
		assertNotNull(AxesStyle.NONE.toString());
		assertTrue(AxesStyle.AXES.toString().length()>0);
		assertTrue(AxesStyle.FRAME.toString().length()>0);
		assertTrue(AxesStyle.NONE.toString().length()>0);
	}


	@Test
	public void testGetStyle() {
		assertEquals(AxesStyle.getStyle(PSTricksConstants.TOKEN_AXES_STYLE_AXES), AxesStyle.AXES);
		assertEquals(AxesStyle.getStyle(PSTricksConstants.TOKEN_AXES_STYLE_FRAME), AxesStyle.FRAME);
		assertEquals(AxesStyle.getStyle(PSTricksConstants.TOKEN_AXES_STYLE_NONE), AxesStyle.NONE);

		assertNull(AxesStyle.getStyle(null));
		assertNull(AxesStyle.getStyle(""));
		assertNull(AxesStyle.getStyle("-çà( gfd"));

		assertEquals(AxesStyle.getStyle(AxesStyle.NONE.toString()), AxesStyle.NONE);
		assertEquals(AxesStyle.getStyle(AxesStyle.AXES.toString()), AxesStyle.AXES);
		assertEquals(AxesStyle.getStyle(AxesStyle.FRAME.toString()), AxesStyle.FRAME);
	}
}
