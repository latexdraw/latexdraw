package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
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
		assertEquals(DotStyle.getStyle(PSTricksConstants.ASTERISK_STYLE), DotStyle.ASTERISK);
		assertEquals(DotStyle.getStyle(PSTricksConstants.BAR_STYLE), DotStyle.BAR);
		assertEquals(DotStyle.getStyle(PSTricksConstants.DIAMOND_STYLE), DotStyle.DIAMOND);
		assertEquals(DotStyle.getStyle(PSTricksConstants.DOT_STYLE), DotStyle.DOT);
		assertEquals(DotStyle.getStyle(PSTricksConstants.FDIAMOND_STYLE), DotStyle.FDIAMOND);
		assertEquals(DotStyle.getStyle(PSTricksConstants.FPENTAGON_STYLE), DotStyle.FPENTAGON);
		assertEquals(DotStyle.getStyle(PSTricksConstants.FSQUARE_STYLE), DotStyle.FSQUARE);
		assertEquals(DotStyle.getStyle(PSTricksConstants.FTRIANGLE_STYLE), DotStyle.FTRIANGLE);
		assertEquals(DotStyle.getStyle(PSTricksConstants.O_STYLE), DotStyle.O);
		assertEquals(DotStyle.getStyle(PSTricksConstants.OPLUS_STYLE), DotStyle.OPLUS);
		assertEquals(DotStyle.getStyle(PSTricksConstants.OTIMES_STYLE), DotStyle.OTIMES);
		assertEquals(DotStyle.getStyle(PSTricksConstants.PENTAGON_STYLE), DotStyle.PENTAGON);
		assertEquals(DotStyle.getStyle(PSTricksConstants.PLUS_STYLE), DotStyle.PLUS);
		assertEquals(DotStyle.getStyle(PSTricksConstants.SQUARE_STYLE), DotStyle.SQUARE);
		assertEquals(DotStyle.getStyle(PSTricksConstants.TRIANGLE_STYLE), DotStyle.TRIANGLE);
		assertEquals(DotStyle.getStyle(PSTricksConstants.X_STYLE), DotStyle.X);
	}

	@Theory
	public void testGetStyleOKDotStyle(final DotStyle style) {
		assertEquals(DotStyle.getStyle(style.toString()), style);
	}

	@Theory
	public void testGetStyleOKDotStylePST(final DotStyle style) {
		assertEquals(DotStyle.getStyle(style.getPSTToken()), style);
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(DotStyle.DOT, DotStyle.getStyle(null));
		assertEquals(DotStyle.DOT, DotStyle.getStyle(""));
	}
}
