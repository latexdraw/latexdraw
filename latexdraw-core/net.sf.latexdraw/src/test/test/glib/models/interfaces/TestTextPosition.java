package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.glib.models.interfaces.IText;

import org.junit.Test;

public class TestTextPosition {
	@Test
	public void testGetTextPositionFromText() {
		assertEquals(IText.TextPosition.BASE, IText.TextPosition.getTextPosition("B"));
		assertEquals(IText.TextPosition.BASE_LEFT, IText.TextPosition.getTextPosition("Bl"));
		assertEquals(IText.TextPosition.BASE_RIGHT, IText.TextPosition.getTextPosition("Br"));
		assertEquals(IText.TextPosition.BOT, IText.TextPosition.getTextPosition("b"));
		assertEquals(IText.TextPosition.BOT_LEFT, IText.TextPosition.getTextPosition("bl"));
		assertEquals(IText.TextPosition.BOT_RIGHT, IText.TextPosition.getTextPosition("br"));
		assertEquals(IText.TextPosition.TOP, IText.TextPosition.getTextPosition("t"));
		assertEquals(IText.TextPosition.TOP_RIGHT, IText.TextPosition.getTextPosition("tr"));
		assertEquals(IText.TextPosition.TOP_LEFT, IText.TextPosition.getTextPosition("tl"));
		assertEquals(IText.TextPosition.CENTER, IText.TextPosition.getTextPosition(""));
		assertEquals(IText.TextPosition.LEFT, IText.TextPosition.getTextPosition("l"));
		assertEquals(IText.TextPosition.RIGHT, IText.TextPosition.getTextPosition("r"));
	}


	@Test
	public void testGetLatexToken() {
		assertEquals("B", IText.TextPosition.BASE.getLatexToken());
		assertEquals("Bl", IText.TextPosition.BASE_LEFT.getLatexToken());
		assertEquals("Br", IText.TextPosition.BASE_RIGHT.getLatexToken());
		assertEquals("b", IText.TextPosition.BOT.getLatexToken());
		assertEquals("bl", IText.TextPosition.BOT_LEFT.getLatexToken());
		assertEquals("Br", IText.TextPosition.BASE_RIGHT.getLatexToken());
		assertEquals("", IText.TextPosition.CENTER.getLatexToken());
		assertEquals("l", IText.TextPosition.LEFT.getLatexToken());
		assertEquals("r", IText.TextPosition.RIGHT.getLatexToken());
		assertEquals("t", IText.TextPosition.TOP.getLatexToken());
		assertEquals("tl", IText.TextPosition.TOP_LEFT.getLatexToken());
		assertEquals("tr", IText.TextPosition.TOP_RIGHT.getLatexToken());
	}
}
