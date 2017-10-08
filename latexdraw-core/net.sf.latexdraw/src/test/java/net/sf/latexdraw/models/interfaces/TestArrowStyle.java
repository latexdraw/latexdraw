package net.sf.latexdraw.models.interfaces;

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
		assertEquals(ArrowStyle.BAR_END.getPSTToken(), PSTricksConstants.BAREND_STYLE);
		assertEquals(ArrowStyle.BAR_IN.getPSTToken(), PSTricksConstants.BARIN_STYLE);
		assertEquals(ArrowStyle.CIRCLE_END.getPSTToken(), PSTricksConstants.CIRCLEEND_STYLE);
		assertEquals(ArrowStyle.CIRCLE_IN.getPSTToken(), PSTricksConstants.CIRCLEIN_STYLE);
		assertEquals(ArrowStyle.DISK_END.getPSTToken(), PSTricksConstants.DISKEND_STYLE);
		assertEquals(ArrowStyle.DISK_IN.getPSTToken(), PSTricksConstants.DISKIN_STYLE);
		assertEquals(ArrowStyle.LEFT_ARROW.getPSTToken(), PSTricksConstants.LARROW_STYLE);
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW.getPSTToken(), PSTricksConstants.DLARROW_STYLE);
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET.getPSTToken(), PSTricksConstants.LRBRACKET_STYLE);
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET.getPSTToken(), PSTricksConstants.LSBRACKET_STYLE);
		assertEquals(ArrowStyle.NONE.getPSTToken(), "");
		assertEquals(ArrowStyle.RIGHT_ARROW.getPSTToken(), PSTricksConstants.RARROW_STYLE);
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW.getPSTToken(), PSTricksConstants.DRARROW_STYLE);
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET.getPSTToken(), PSTricksConstants.RRBRACKET_STYLE);
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET.getPSTToken(), PSTricksConstants.RSBRACKET_STYLE);
		assertEquals(ArrowStyle.ROUND_END.getPSTToken(), PSTricksConstants.ROUNDEND_STYLE);
		assertEquals(ArrowStyle.ROUND_IN.getPSTToken(), PSTricksConstants.ROUNDIN_STYLE);
	}

	@Theory
	public void testGetArrowStylePSTConst(final ArrowStyle style) {
		assertEquals(ArrowStyle.getArrowStyle(style.getPSTToken()), style);
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
		assertEquals(ArrowStyle.getArrowStyle(style.toString()), style);
	}
}
