package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.junit.MatcherAssume.assumeThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestFillingStyle {
	@Theory
	public void testGetFilledStyle(final FillingStyle style) {
		assumeTrue(style.isFilled());
		assertEquals(style, style.getFilledStyle());
	}

	@Theory
	public void testGetUnfilledStyle(final FillingStyle style) {
		assumeFalse(style.isFilled());
		assertEquals(style, style.getUnfilledStyle());
	}

	@Test
	public void testGetFilledStyleSpecificCases() {
		assertEquals(FillingStyle.HLINES_PLAIN, FillingStyle.HLINES.getFilledStyle());
		assertEquals(FillingStyle.CLINES_PLAIN, FillingStyle.CLINES.getFilledStyle());
		assertEquals(FillingStyle.VLINES_PLAIN, FillingStyle.VLINES.getFilledStyle());
		assertEquals(FillingStyle.PLAIN, FillingStyle.NONE.getFilledStyle());
	}

	@Test
	public void testGetUnfilledStyleSpecificCases() {
		assertEquals(FillingStyle.HLINES, FillingStyle.HLINES_PLAIN.getUnfilledStyle());
		assertEquals(FillingStyle.CLINES, FillingStyle.CLINES_PLAIN.getUnfilledStyle());
		assertEquals(FillingStyle.VLINES, FillingStyle.VLINES_PLAIN.getUnfilledStyle());
		assertEquals(FillingStyle.NONE, FillingStyle.PLAIN.getUnfilledStyle());
	}

	@Test
	public void testIsGradientOK() {
		assertTrue(FillingStyle.GRAD.isGradient());
	}

	@Theory
	public void testIsGradientKO(final FillingStyle style) {
		assumeThat(style, Matchers.not(Matchers.equalTo(FillingStyle.GRAD)));
		assertFalse(style.isGradient());
	}

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
		assertEquals(PSTricksConstants.TOKEN_FILL_CROSSHATCH, FillingStyle.CLINES.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_CROSSHATCH_F, FillingStyle.CLINES_PLAIN.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_GRADIENT, FillingStyle.GRAD.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_HLINES, FillingStyle.HLINES.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_HLINES_F, FillingStyle.HLINES_PLAIN.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_NONE, FillingStyle.NONE.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_SOLID, FillingStyle.PLAIN.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_VLINES, FillingStyle.VLINES.getLatexToken());
		assertEquals(PSTricksConstants.TOKEN_FILL_VLINES_F, FillingStyle.VLINES_PLAIN.getLatexToken());
	}

	@Theory
	public void testGetStyleFromLatexOK(final FillingStyle style) {
		assertEquals(style, FillingStyle.getStyleFromLatex(style.getLatexToken()));
	}

	@Test
	public void testGetStyleFromLatexKO() {
		assertEquals(FillingStyle.NONE, FillingStyle.getStyleFromLatex(null));
		assertEquals(FillingStyle.NONE, FillingStyle.getStyleFromLatex(""));
	}

	@Theory
	public void testGetStyle(final FillingStyle style) {
		assertEquals(style, FillingStyle.getStyle(style.toString()));
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
