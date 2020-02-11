package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@ParameterizedTest
	@EnumSource
	public void testGetStyle(final LineStyle style) {
		assertEquals(style, LineStyle.getStyle(style.toString()));
	}

	@ParameterizedTest
	@EnumSource
	public void testGetStyleFromTeX(final LineStyle style) {
		assertEquals(style, LineStyle.getStyle(style.getLatexToken()));
	}
}
