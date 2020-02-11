package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDotStyle {
	@Test
	public void testIsFillable() {
		assertFalse(DotStyle.ASTERISK.isFillable());
		assertFalse(DotStyle.BAR.isFillable());
		assertFalse(DotStyle.PLUS.isFillable());
		assertFalse(DotStyle.X.isFillable());
		assertTrue(DotStyle.DIAMOND.isFillable());
		assertFalse(DotStyle.DOT.isFillable());
		assertFalse(DotStyle.FDIAMOND.isFillable());
		assertFalse(DotStyle.FPENTAGON.isFillable());
		assertFalse(DotStyle.FSQUARE.isFillable());
		assertFalse(DotStyle.FTRIANGLE.isFillable());
		assertTrue(DotStyle.O.isFillable());
		assertFalse(DotStyle.OPLUS.isFillable());
		assertFalse(DotStyle.OTIMES.isFillable());
		assertTrue(DotStyle.PENTAGON.isFillable());
		assertTrue(DotStyle.SQUARE.isFillable());
		assertTrue(DotStyle.TRIANGLE.isFillable());
	}

	@Test
	public void testGetStyleOKPSTConst() {
		assertEquals(DotStyle.ASTERISK, DotStyle.getStyle(PSTricksConstants.ASTERISK_STYLE));
		assertEquals(DotStyle.BAR, DotStyle.getStyle(PSTricksConstants.BAR_STYLE));
		assertEquals(DotStyle.DIAMOND, DotStyle.getStyle(PSTricksConstants.DIAMOND_STYLE));
		assertEquals(DotStyle.DOT, DotStyle.getStyle(PSTricksConstants.DOT_STYLE));
		assertEquals(DotStyle.FDIAMOND, DotStyle.getStyle(PSTricksConstants.FDIAMOND_STYLE));
		assertEquals(DotStyle.FPENTAGON, DotStyle.getStyle(PSTricksConstants.FPENTAGON_STYLE));
		assertEquals(DotStyle.FSQUARE, DotStyle.getStyle(PSTricksConstants.FSQUARE_STYLE));
		assertEquals(DotStyle.FTRIANGLE, DotStyle.getStyle(PSTricksConstants.FTRIANGLE_STYLE));
		assertEquals(DotStyle.O, DotStyle.getStyle(PSTricksConstants.O_STYLE));
		assertEquals(DotStyle.OPLUS, DotStyle.getStyle(PSTricksConstants.OPLUS_STYLE));
		assertEquals(DotStyle.OTIMES, DotStyle.getStyle(PSTricksConstants.OTIMES_STYLE));
		assertEquals(DotStyle.PENTAGON, DotStyle.getStyle(PSTricksConstants.PENTAGON_STYLE));
		assertEquals(DotStyle.PLUS, DotStyle.getStyle(PSTricksConstants.PLUS_STYLE));
		assertEquals(DotStyle.SQUARE, DotStyle.getStyle(PSTricksConstants.SQUARE_STYLE));
		assertEquals(DotStyle.TRIANGLE, DotStyle.getStyle(PSTricksConstants.TRIANGLE_STYLE));
		assertEquals(DotStyle.X, DotStyle.getStyle(PSTricksConstants.X_STYLE));
	}

	@ParameterizedTest
	@EnumSource
	public void testGetStyleOKDotStyle(final DotStyle style) {
		assertEquals(style, DotStyle.getStyle(style.toString()));
	}

	@ParameterizedTest
	@EnumSource
	public void testGetStyleOKDotStylePST(final DotStyle style) {
		assertEquals(style, DotStyle.getStyle(style.getPSTToken()));
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(DotStyle.DOT, DotStyle.getStyle(null));
		assertEquals(DotStyle.DOT, DotStyle.getStyle(""));
	}
}
