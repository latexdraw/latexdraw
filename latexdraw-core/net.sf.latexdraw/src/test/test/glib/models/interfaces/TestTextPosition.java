package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;

import org.junit.Test;

public class TestTextPosition {
	@Test
	public void testGetTextPositionFromText() {
		assertEquals(IText.TextPosition.BASE, IText.TextPosition.getTextPosition("B")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.BASE_LEFT, IText.TextPosition.getTextPosition("Bl")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.BASE_RIGHT, IText.TextPosition.getTextPosition("Br")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.BOT, IText.TextPosition.getTextPosition("b")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.BOT_LEFT, IText.TextPosition.getTextPosition("bl")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.BOT_RIGHT, IText.TextPosition.getTextPosition("br")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.TOP, IText.TextPosition.getTextPosition("t")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.TOP_RIGHT, IText.TextPosition.getTextPosition("tr")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.TOP_LEFT, IText.TextPosition.getTextPosition("tl")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.CENTER, IText.TextPosition.getTextPosition("")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.LEFT, IText.TextPosition.getTextPosition("l")); //$NON-NLS-1$
		assertEquals(IText.TextPosition.RIGHT, IText.TextPosition.getTextPosition("r")); //$NON-NLS-1$
	}


	@Test
	public void testGetLatexToken() {
		assertEquals("B", IText.TextPosition.BASE.getLatexToken()); //$NON-NLS-1$
		assertEquals("Bl", IText.TextPosition.BASE_LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("Br", IText.TextPosition.BASE_RIGHT.getLatexToken()); //$NON-NLS-1$
		assertEquals("b", IText.TextPosition.BOT.getLatexToken()); //$NON-NLS-1$
		assertEquals("bl", IText.TextPosition.BOT_LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("Br", IText.TextPosition.BASE_RIGHT.getLatexToken()); //$NON-NLS-1$
		assertEquals("", IText.TextPosition.CENTER.getLatexToken()); //$NON-NLS-1$
		assertEquals("l", IText.TextPosition.LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("r", IText.TextPosition.RIGHT.getLatexToken()); //$NON-NLS-1$
		assertEquals("t", IText.TextPosition.TOP.getLatexToken()); //$NON-NLS-1$
		assertEquals("tl", IText.TextPosition.TOP_LEFT.getLatexToken()); //$NON-NLS-1$
		assertEquals("tr", IText.TextPosition.TOP_RIGHT.getLatexToken()); //$NON-NLS-1$
	}
}
