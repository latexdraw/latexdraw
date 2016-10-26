package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;

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

	@Test
	public void testGetStyleFromLatex() {
		assertEquals(FillingStyle.NONE, FillingStyle.getStyleFromLatex(null));
		assertEquals(FillingStyle.NONE, FillingStyle.getStyleFromLatex("")); //$NON-NLS-1$
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_CROSSHATCH), FillingStyle.CLINES);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_CROSSHATCH_F), FillingStyle.CLINES_PLAIN);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_GRADIENT), FillingStyle.GRAD);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_HLINES), FillingStyle.HLINES);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_HLINES_F), FillingStyle.HLINES_PLAIN);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_NONE), FillingStyle.NONE);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_SOLID), FillingStyle.PLAIN);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_VLINES), FillingStyle.VLINES);
		assertEquals(FillingStyle.getStyleFromLatex(PSTricksConstants.TOKEN_FILL_VLINES_F), FillingStyle.VLINES_PLAIN);
	}

	@Test
	public void testGetStyle() {
		assertEquals(FillingStyle.NONE, FillingStyle.getStyle(null));
		assertEquals(FillingStyle.NONE, FillingStyle.getStyle("")); //$NON-NLS-1$
		assertEquals(FillingStyle.getStyle(FillingStyle.CLINES.toString()), FillingStyle.CLINES);
		assertEquals(FillingStyle.getStyle(FillingStyle.VLINES.toString()), FillingStyle.VLINES);
		assertEquals(FillingStyle.getStyle(FillingStyle.HLINES.toString()), FillingStyle.HLINES);
		assertEquals(FillingStyle.getStyle(FillingStyle.CLINES_PLAIN.toString()), FillingStyle.CLINES_PLAIN);
		assertEquals(FillingStyle.getStyle(FillingStyle.VLINES_PLAIN.toString()), FillingStyle.VLINES_PLAIN);
		assertEquals(FillingStyle.getStyle(FillingStyle.HLINES_PLAIN.toString()), FillingStyle.HLINES_PLAIN);
		assertEquals(FillingStyle.getStyle(FillingStyle.NONE.toString()), FillingStyle.NONE);
		assertEquals(FillingStyle.getStyle(FillingStyle.GRAD.toString()), FillingStyle.GRAD);
		assertEquals(FillingStyle.getStyle(FillingStyle.PLAIN.toString()), FillingStyle.PLAIN);
	}

	@Test
	public void testIsHatchings() {
		assertFalse(FillingStyle.GRAD.isHatchings());
		assertFalse(FillingStyle.NONE.isHatchings());
		assertFalse(FillingStyle.PLAIN.isHatchings());
		assertTrue(FillingStyle.CLINES.isHatchings());
		assertTrue(FillingStyle.CLINES_PLAIN.isHatchings());
		assertTrue(FillingStyle.VLINES.isHatchings());
		assertTrue(FillingStyle.VLINES_PLAIN.isHatchings());
		assertTrue(FillingStyle.HLINES.isHatchings());
		assertTrue(FillingStyle.HLINES_PLAIN.isHatchings());
	}
}
