package net.sf.latexdraw.parser;

import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestPSTricksConstants {
	@Test
	void testIsValidArrowStyleNULL() {
		assertFalse(PSTricksConstants.isValidFillStyle(null));
	}

	@Test
	void testIsValidArrowStyleKO() {
		assertFalse(PSTricksConstants.isValidFillStyle("notanarrow"));
	}

	@Test
	void testIsValidArrowStyle() {
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
