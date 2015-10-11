package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

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
		assertEquals(ArrowStyle.NONE.getPSTToken(), ""); //$NON-NLS-1$
		assertEquals(ArrowStyle.RIGHT_ARROW.getPSTToken(), PSTricksConstants.RARROW_STYLE);
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW.getPSTToken(), PSTricksConstants.DRARROW_STYLE);
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET.getPSTToken(), PSTricksConstants.RRBRACKET_STYLE);
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET.getPSTToken(), PSTricksConstants.RSBRACKET_STYLE);
//		assertEquals(ArrowStyle.ROUND_END.getPSTToken(), PSTricksConstants.ROUNDEND_STYLE);
		assertEquals(ArrowStyle.ROUND_IN.getPSTToken(), PSTricksConstants.ROUNDIN_STYLE);
	}


	@Test
	public void testGetArrowStyle() {
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.BAREND_STYLE), ArrowStyle.BAR_END);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.BARIN_STYLE), ArrowStyle.BAR_IN);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.CIRCLEEND_STYLE), ArrowStyle.CIRCLE_END);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.CIRCLEIN_STYLE), ArrowStyle.CIRCLE_IN);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.DISKEND_STYLE), ArrowStyle.DISK_END);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.DISKIN_STYLE), ArrowStyle.DISK_IN);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.LARROW_STYLE), ArrowStyle.LEFT_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.DLARROW_STYLE), ArrowStyle.LEFT_DBLE_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.LRBRACKET_STYLE), ArrowStyle.LEFT_ROUND_BRACKET);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.LSBRACKET_STYLE), ArrowStyle.LEFT_SQUARE_BRACKET);
		assertEquals(ArrowStyle.getArrowStyle(""), ArrowStyle.NONE); //$NON-NLS-1$
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.RARROW_STYLE), ArrowStyle.RIGHT_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.DRARROW_STYLE), ArrowStyle.RIGHT_DBLE_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.RRBRACKET_STYLE), ArrowStyle.RIGHT_ROUND_BRACKET);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.RSBRACKET_STYLE), ArrowStyle.RIGHT_SQUARE_BRACKET);
//		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.ROUNDEND_STYLE), ArrowStyle.ROUND_END);
		assertEquals(ArrowStyle.getArrowStyle(PSTricksConstants.ROUNDIN_STYLE), ArrowStyle.ROUND_IN);

		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle(null));
		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle("")); //$NON-NLS-1$
		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle("diqdo ")); //$NON-NLS-1$

		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.BAR_END.toString()), ArrowStyle.BAR_END);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.BAR_IN.toString()), ArrowStyle.BAR_IN);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.CIRCLE_END.toString()), ArrowStyle.CIRCLE_END);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.CIRCLE_IN.toString()), ArrowStyle.CIRCLE_IN);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.DISK_END.toString()), ArrowStyle.DISK_END);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.DISK_IN.toString()), ArrowStyle.DISK_IN);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.LEFT_ARROW.toString()), ArrowStyle.LEFT_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.LEFT_DBLE_ARROW.toString()), ArrowStyle.LEFT_DBLE_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.LEFT_ROUND_BRACKET.toString()), ArrowStyle.LEFT_ROUND_BRACKET);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.LEFT_SQUARE_BRACKET.toString()), ArrowStyle.LEFT_SQUARE_BRACKET);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.NONE.toString()), ArrowStyle.NONE);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.RIGHT_ARROW.toString()), ArrowStyle.RIGHT_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW.toString()), ArrowStyle.RIGHT_DBLE_ARROW);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.RIGHT_ROUND_BRACKET.toString()), ArrowStyle.RIGHT_ROUND_BRACKET);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.RIGHT_SQUARE_BRACKET.toString()), ArrowStyle.RIGHT_SQUARE_BRACKET);
//		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.ROUND_END.toString()), ArrowStyle.ROUND_END);
		assertEquals(ArrowStyle.getArrowStyle(ArrowStyle.ROUND_IN.toString()), ArrowStyle.ROUND_IN);
	}
}
