package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(LatexdrawExtension.class)
public class TestArrowStyle {
	@Test
	void testGetPSTToken() {
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

	@ParameterizedTest
	@EnumSource
	void testGetArrowStylePSTConst(final ArrowStyle style) {
		assertEquals(style, ArrowStyle.getArrowStyle(style.getPSTToken()));
	}

	@Test
	void testGetArrowStyleKONULL() {
		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle(null));
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "diqdo "})
	void testGetArrowStyleKOSTR(final String style) {
		assertEquals(ArrowStyle.NONE, ArrowStyle.getArrowStyle(style));
	}

	@ParameterizedTest
	@EnumSource
	void testGetArrowStyleArrowStyle(final ArrowStyle style) {
		assertEquals(style, ArrowStyle.getArrowStyle(style.toString()));
	}
}
