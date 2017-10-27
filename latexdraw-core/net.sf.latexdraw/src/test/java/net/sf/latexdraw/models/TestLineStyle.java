package net.sf.latexdraw.models;

import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestLineStyle {
	@Test
	public void testGetLatexToken() {
		assertEquals(PSTricksConstants.LINE_DASHED_STYLE, LineStyle.DASHED.getLatexToken());
		assertEquals(PSTricksConstants.LINE_DOTTED_STYLE, LineStyle.DOTTED.getLatexToken());
		assertEquals(PSTricksConstants.LINE_SOLID_STYLE, LineStyle.SOLID.getLatexToken());
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(LineStyle.SOLID, LineStyle.getStyle(null));
		assertEquals(LineStyle.SOLID, LineStyle.getStyle(""));
	}

	@Theory
	public void testGetStyle(final LineStyle style) {
		assertEquals(style, LineStyle.getStyle(style.toString()));
	}

	@Theory
	public void testGetStyleFromTeX(final LineStyle style) {
		assertEquals(style, LineStyle.getStyle(style.getLatexToken()));
	}
}
