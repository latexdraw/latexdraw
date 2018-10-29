package net.sf.latexdraw.models.interfaces.shape;

import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
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

	@Theory
	public void testGetStyleOKBorderPos(final BorderPos style) {
		assertEquals(style, BorderPos.getStyle(style.toString()));
	}

	@Theory
	public void testGetStyleOKPSTConst(final BorderPos style) {
		assertEquals(style, BorderPos.getStyle(style.getLatexToken()));
	}
}
