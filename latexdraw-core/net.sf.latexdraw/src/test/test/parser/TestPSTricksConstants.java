package test.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

import org.junit.Test;

public class TestPSTricksConstants{
	@Test
	public void testIsValidDotStyle() {
		assertFalse(PSTricksConstants.isValidDotStyle(null));
		assertFalse(PSTricksConstants.isValidDotStyle("coucou!!"));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.DOT_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.DIAMOND_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.FDIAMOND_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.FPENTAGON_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.PENTAGON_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.FSQUARE_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.SQUARE_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.FTRIANGLE_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.TRIANGLE_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.O_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.OPLUS_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.OTIMES_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.X_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.PLUS_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.BAR_STYLE));
		assertTrue(PSTricksConstants.isValidDotStyle(PSTricksConstants.ASTERISK_STYLE));
	}


	@Test
	public void testIsValidArrowStyle() {
		assertFalse(PSTricksConstants.isValidFillStyle(null));
		assertFalse(PSTricksConstants.isValidFillStyle("coucou!!"));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_CROSSHATCH));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_CROSSHATCH_F));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_GRADIENT));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_HLINES));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_HLINES_F));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_NONE));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_SOLID));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_VLINES));
		assertTrue(PSTricksConstants.isValidFillStyle(PSTricksConstants.TOKEN_FILL_VLINES_F));
	}

}
