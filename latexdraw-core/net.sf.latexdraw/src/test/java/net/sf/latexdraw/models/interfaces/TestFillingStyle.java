package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestFillingStyle {
	@Test
	public void testIsFillable() {
		assertFalse(FillingStyle.GRAD.isFilled());
		assertFalse(FillingStyle.CLINES.isFilled());
		assertFalse(FillingStyle.VLINES.isFilled());
		assertFalse(FillingStyle.HLINES.isFilled());
		assertFalse(FillingStyle.NONE.isFilled());
		assertTrue(FillingStyle.CLINES_PLAIN.isFilled());
		assertTrue(FillingStyle.VLINES_PLAIN.isFilled());
		assertTrue(FillingStyle.HLINES_PLAIN.isFilled());
		assertTrue(FillingStyle.PLAIN.isFilled());
	}

	@Test
	public void testGetLatexToken() {
		assertEquals(FillingStyle.CLINES.getLatexToken(), PSTricksConstants.TOKEN_FILL_CROSSHATCH);
		assertEquals(FillingStyle.CLINES_PLAIN.getLatexToken(), PSTricksConstants.TOKEN_FILL_CROSSHATCH_F);
		assertEquals(FillingStyle.GRAD.getLatexToken(), PSTricksConstants.TOKEN_FILL_GRADIENT);
		assertEquals(FillingStyle.HLINES.getLatexToken(), PSTricksConstants.TOKEN_FILL_HLINES);
		assertEquals(FillingStyle.HLINES_PLAIN.getLatexToken(), PSTricksConstants.TOKEN_FILL_HLINES_F);
		assertEquals(FillingStyle.NONE.getLatexToken(), PSTricksConstants.TOKEN_FILL_NONE);
		assertEquals(FillingStyle.PLAIN.getLatexToken(), PSTricksConstants.TOKEN_FILL_SOLID);
		assertEquals(FillingStyle.VLINES.getLatexToken(), PSTricksConstants.TOKEN_FILL_VLINES);
		assertEquals(FillingStyle.VLINES_PLAIN.getLatexToken(), PSTricksConstants.TOKEN_FILL_VLINES_F);
	}

	@Theory
	public void testGetStyleFromLatexOK(final FillingStyle style) {
		assertEquals(FillingStyle.getStyleFromLatex(style.getLatexToken()), style);
	}

	@Test
	public void testGetStyleFromLatexKO() {
		assertEquals(FillingStyle.NONE, FillingStyle.getStyleFromLatex(null));
		assertEquals(FillingStyle.NONE, FillingStyle.getStyleFromLatex(""));
	}

	@Theory
	public void testGetStyle(final FillingStyle style) {
		assertEquals(FillingStyle.getStyle(style.toString()), style);
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(FillingStyle.NONE, FillingStyle.getStyle(null));
		assertEquals(FillingStyle.NONE, FillingStyle.getStyle(""));
	}

	@Test
	public void testIsNotHatchings() {
		assertFalse(FillingStyle.GRAD.isHatchings());
		assertFalse(FillingStyle.NONE.isHatchings());
		assertFalse(FillingStyle.PLAIN.isHatchings());
	}

	@Test
	public void testIsHatchings() {
		assertTrue(FillingStyle.CLINES.isHatchings());
		assertTrue(FillingStyle.CLINES_PLAIN.isHatchings());
		assertTrue(FillingStyle.VLINES.isHatchings());
		assertTrue(FillingStyle.VLINES_PLAIN.isHatchings());
		assertTrue(FillingStyle.HLINES.isHatchings());
		assertTrue(FillingStyle.HLINES_PLAIN.isHatchings());
	}
}
