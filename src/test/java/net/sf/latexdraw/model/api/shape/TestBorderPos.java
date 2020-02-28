package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(LatexdrawExtension.class)
public class TestBorderPos {
	@Test
	public void testGetLatexToken() {
		assertEquals(PSTricksConstants.BORDERS_INSIDE, BorderPos.INTO.getLatexToken());
		assertEquals(PSTricksConstants.BORDERS_MIDDLE, BorderPos.MID.getLatexToken());
		assertEquals(PSTricksConstants.BORDERS_OUTSIDE, BorderPos.OUT.getLatexToken());
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(BorderPos.INTO, BorderPos.getStyle(null));
		assertEquals(BorderPos.INTO, BorderPos.getStyle(""));
	}

	@ParameterizedTest
	@EnumSource
	public void testGetStyleOKBorderPos(final BorderPos style) {
		assertEquals(style, BorderPos.getStyle(style.toString()));
	}

	@ParameterizedTest
	@EnumSource
	public void testGetStyleOKPSTConst(final BorderPos style) {
		assertEquals(style, BorderPos.getStyle(style.getLatexToken()));
	}
}
