package net.sf.latexdraw.models.interfaces;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sf.latexdraw.models.interfaces.shape.TextPosition;

public class TestTextPosition {
	@Test
	public void testGetTextPositionFromText() {
		assertEquals(TextPosition.BASE, TextPosition.getTextPosition("B")); //$NON-NLS-1$
		assertEquals(TextPosition.BASE_LEFT, TextPosition.getTextPosition("Bl")); //$NON-NLS-1$
		assertEquals(TextPosition.BASE_RIGHT, TextPosition.getTextPosition("Br")); //$NON-NLS-1$
		assertEquals(TextPosition.BOT, TextPosition.getTextPosition("b")); //$NON-NLS-1$
		assertEquals(TextPosition.BOT_LEFT, TextPosition.getTextPosition("bl")); //$NON-NLS-1$
		assertEquals(TextPosition.BOT_RIGHT, TextPosition.getTextPosition("br")); //$NON-NLS-1$
		assertEquals(TextPosition.TOP, TextPosition.getTextPosition("t")); //$NON-NLS-1$
		assertEquals(TextPosition.TOP_RIGHT, TextPosition.getTextPosition("tr")); //$NON-NLS-1$
		assertEquals(TextPosition.TOP_LEFT, TextPosition.getTextPosition("tl")); //$NON-NLS-1$
		assertEquals(TextPosition.CENTER, TextPosition.getTextPosition("")); //$NON-NLS-1$
		assertEquals(TextPosition.LEFT, TextPosition.getTextPosition("l")); //$NON-NLS-1$
		assertEquals(TextPosition.RIGHT, TextPosition.getTextPosition("r")); //$NON-NLS-1$
	}

	@Test
	public void testGetLatexToken() {
		assertEquals("B", TextPosition.BASE.getLatexToken()); //$NON-NLS-1$
		assertEquals("Bl", TextPosition.BASE_LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("Br", TextPosition.BASE_RIGHT.getLatexToken()); //$NON-NLS-1$
		assertEquals("b", TextPosition.BOT.getLatexToken()); //$NON-NLS-1$
		assertEquals("bl", TextPosition.BOT_LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("Br", TextPosition.BASE_RIGHT.getLatexToken()); //$NON-NLS-1$
		assertEquals("", TextPosition.CENTER.getLatexToken()); //$NON-NLS-1$
		assertEquals("l", TextPosition.LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("r", TextPosition.RIGHT.getLatexToken()); //$NON-NLS-1$
		assertEquals("t", TextPosition.TOP.getLatexToken()); //$NON-NLS-1$
		assertEquals("tl", TextPosition.TOP_LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("tr", TextPosition.TOP_RIGHT.getLatexToken()); //$NON-NLS-1$
	}
}
