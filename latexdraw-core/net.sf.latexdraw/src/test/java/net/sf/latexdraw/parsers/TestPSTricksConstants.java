package net.sf.latexdraw.parsers;

import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPSTricksConstants {
	@Test
	public void testIsValidArrowStyle() {
		assertFalse(PSTricksConstants.isValidFillStyle(null));
		assertFalse(PSTricksConstants.isValidFillStyle("coucou!!")); //$NON-NLS-1$
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
