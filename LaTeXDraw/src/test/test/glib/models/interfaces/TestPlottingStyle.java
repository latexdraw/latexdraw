package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

import org.junit.Test;

public class TestPlottingStyle {
	@Test
	public void testIsX() {
		assertTrue(PlottingStyle.ALL.isX());
		assertTrue(PlottingStyle.X.isX());
		assertFalse(PlottingStyle.Y.isX());
		assertFalse(PlottingStyle.NONE.isX());
	}

	@Test
	public void testIsY() {
		assertTrue(PlottingStyle.ALL.isY());
		assertTrue(PlottingStyle.Y.isY());
		assertFalse(PlottingStyle.X.isY());
		assertFalse(PlottingStyle.NONE.isY());
	}

	@Test
	public void testGetPSTToken() {
		assertEquals(PSTricksConstants.TOKEN_LABELS_DISPLAYED_ALL, PlottingStyle.ALL.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_LABELS_DISPLAYED_NONE, PlottingStyle.NONE.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_LABELS_DISPLAYED_X, PlottingStyle.X.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_LABELS_DISPLAYED_Y, PlottingStyle.Y.getPSTToken());
	}

	@Test
	public void testGetStyle() {
		assertNull(PlottingStyle.getStyle(null));
		assertNull(PlottingStyle.getStyle(""));
		assertNull(PlottingStyle.getStyle("ezpoke zae"));
		assertNull(PlottingStyle.getStyle("7dsqd 4ds'"));
		assertEquals(PlottingStyle.getStyle(PlottingStyle.ALL.toString()), PlottingStyle.ALL);
		assertEquals(PlottingStyle.getStyle(PlottingStyle.NONE.toString()), PlottingStyle.NONE);
		assertEquals(PlottingStyle.getStyle(PlottingStyle.X.toString()), PlottingStyle.X);
		assertEquals(PlottingStyle.getStyle(PlottingStyle.Y.toString()), PlottingStyle.Y);
	}
}
