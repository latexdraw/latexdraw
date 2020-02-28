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

	@ParameterizedTest
	@EnumSource
	public void testGetStyle(final PlottingStyle style) {
		assertEquals(PlottingStyle.getStyle(style.toString()), style);
	}

	@ParameterizedTest
	@EnumSource
	public void testGetStylePST(final PlottingStyle style) {
		assertEquals(PlottingStyle.getStyle(style.getPSTToken()), style);
	}
}
