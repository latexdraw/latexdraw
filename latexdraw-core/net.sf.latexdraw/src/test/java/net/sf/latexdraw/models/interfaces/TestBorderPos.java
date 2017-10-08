package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.BorderPos;
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
		assertEquals(BorderPos.INTO.getLatexToken(), PSTricksConstants.BORDERS_INSIDE);
		assertEquals(BorderPos.MID.getLatexToken(), PSTricksConstants.BORDERS_MIDDLE);
		assertEquals(BorderPos.OUT.getLatexToken(), PSTricksConstants.BORDERS_OUTSIDE);
	}

	@Test
	public void testGetStyleKO() {
		assertEquals(BorderPos.INTO, BorderPos.getStyle(null));
		assertEquals(BorderPos.INTO, BorderPos.getStyle(""));
	}

	@Theory
	public void testGetStyleOKBorderPos(final BorderPos style) {
		assertEquals(BorderPos.getStyle(style.toString()), style);
	}

	@Theory
	public void testGetStyleOKPSTConst(final BorderPos style) {
		assertEquals(BorderPos.getStyle(style.getLatexToken()), style);
	}
}
