package net.sf.latexdraw.models.interfaces.shape;

import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestArrowStyle {
	@Test
	public void testGetPSTToken() {
		assertEquals(PSTricksConstants.BAREND_STYLE, ArrowStyle.BAR_END.getPSTToken());
		assertEquals(PSTricksConstants.BARIN_STYLE, ArrowStyle.BAR_IN.getPSTToken());
		assertEquals(PSTricksConstants.CIRCLEEND_STYLE, ArrowStyle.CIRCLE_END.getPSTToken());
		assertEquals(PSTricksConstants.CIRCLEIN_STYLE, ArrowStyle.CIRCLE_IN.getPSTToken());
		assertEquals(PSTricksConstants.DISKEND_STYLE, ArrowStyle.DISK_END.getPSTToken());
		assertEquals(PSTricksConstants.DISKIN_STYLE, ArrowStyle.DISK_IN.getPSTToken());
		assertEquals(PSTricksConstants.LARROW_STYLE, ArrowStyle.LEFT_ARROW.getPSTToken());
		assertEquals(PSTricksConstants.DLARROW_STYLE, ArrowStyle.LEFT_DBLE_ARROW.getPSTToken());
		assertEquals(PSTricksConstants.LRBRACKET_STYLE, ArrowStyle.LEFT_ROUND_BRACKET.getPSTToken());
		assertEquals(PSTricksConstants.LSBRACKET_STYLE, ArrowStyle.LEFT_SQUARE_BRACKET.getPSTToken());
		assertEquals("", ArrowStyle.NONE.getPSTToken());
		assertEquals(PSTricksConstants.RARROW_STYLE, ArrowStyle.RIGHT_ARROW.getPSTToken());
		assertEquals(PSTricksConstants.DRARROW_STYLE, ArrowStyle.RIGHT_DBLE_ARROW.getPSTToken());
		assertEquals(PSTricksConstants.RRBRACKET_STYLE, ArrowStyle.RIGHT_ROUND_BRACKET.getPSTToken());
		assertEquals(PSTricksConstants.RSBRACKET_STYLE, ArrowStyle.RIGHT_SQUARE_BRACKET.getPSTToken());
		assertEquals(PSTricksConstants.ROUNDEND_STYLE, ArrowStyle.ROUND_END.getPSTToken());
		assertEquals(PSTricksConstants.ROUNDIN_STYLE, ArrowStyle.ROUND_IN.getPSTToken());
	}

	@Theory
	public void testGetArrowStylePSTConst(final ArrowStyle style) {
		assertEquals(style, ArrowStyle.getArrowStyle(style.getPSTToken()));
	}

	@Test
	public void testGetArrowStyleKONULL() {
		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle(null));
	}

	@Theory
	public void testGetArrowStyleKOSTR(@StringData(vals = {"", "diqdo "}) final String style) {
		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle(style));
	}

	@Theory
	public void testGetArrowStyleArrowStyle(final ArrowStyle style) {
		assertEquals(style, ArrowStyle.getArrowStyle(style.toString()));
	}
}
