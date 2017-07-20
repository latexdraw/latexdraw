package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	@Test
	public void testGetStyleOKDotStyle() {
		assertEquals(DotStyle.getStyle(DotStyle.ASTERISK.toString()), DotStyle.ASTERISK);
		assertEquals(DotStyle.getStyle(DotStyle.BAR.toString()), DotStyle.BAR);
		assertEquals(DotStyle.getStyle(DotStyle.DIAMOND.toString()), DotStyle.DIAMOND);
		assertEquals(DotStyle.getStyle(DotStyle.DOT.toString()), DotStyle.DOT);
		assertEquals(DotStyle.getStyle(DotStyle.FDIAMOND.toString()), DotStyle.FDIAMOND);
		assertEquals(DotStyle.getStyle(DotStyle.FPENTAGON.toString()), DotStyle.FPENTAGON);
		assertEquals(DotStyle.getStyle(DotStyle.FSQUARE.toString()), DotStyle.FSQUARE);
		assertEquals(DotStyle.getStyle(DotStyle.FTRIANGLE.toString()), DotStyle.FTRIANGLE);
		assertEquals(DotStyle.getStyle(DotStyle.O.toString()), DotStyle.O);
		assertEquals(DotStyle.getStyle(DotStyle.OPLUS.toString()), DotStyle.OPLUS);
		assertEquals(DotStyle.getStyle(DotStyle.OTIMES.toString()), DotStyle.OTIMES);
		assertEquals(DotStyle.getStyle(DotStyle.PENTAGON.toString()), DotStyle.PENTAGON);
		assertEquals(DotStyle.getStyle(DotStyle.PLUS.toString()), DotStyle.PLUS);
		assertEquals(DotStyle.getStyle(DotStyle.SQUARE.toString()), DotStyle.SQUARE);
		assertEquals(DotStyle.getStyle(DotStyle.TRIANGLE.toString()), DotStyle.TRIANGLE);
		assertEquals(DotStyle.getStyle(DotStyle.X.toString()), DotStyle.X);
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(DotStyle.DOT, DotStyle.getStyle(null));
		assertEquals(DotStyle.DOT, DotStyle.getStyle("")); //$NON-NLS-1$
	}
}
