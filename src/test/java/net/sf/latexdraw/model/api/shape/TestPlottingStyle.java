package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
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
	public void testGetStyleKO() {
		assertEquals(PlottingStyle.ALL, PlottingStyle.getStyle(null));
		assertEquals(PlottingStyle.ALL, PlottingStyle.getStyle(""));
	}

	@Theory
	public void testGetStyle(final PlottingStyle style) {
		assertEquals(PlottingStyle.getStyle(style.toString()), style);
	}

	@Theory
	public void testGetStylePST(final PlottingStyle style) {
		assertEquals(PlottingStyle.getStyle(style.getPSTToken()), style);
	}
}
