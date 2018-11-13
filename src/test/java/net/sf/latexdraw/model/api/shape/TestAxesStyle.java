package net.sf.latexdraw.model.api.shape;

import java.util.ResourceBundle;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestAxesStyle {
	@Test
	void testSupportsArrows() {
		assertTrue(AxesStyle.AXES.supportsArrows());
		assertFalse(AxesStyle.FRAME.supportsArrows());
		assertFalse(AxesStyle.NONE.supportsArrows());
	}

	@Test
	void testGetPSTToken() {
		assertEquals(PSTricksConstants.TOKEN_AXES_STYLE_AXES, AxesStyle.AXES.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_AXES_STYLE_FRAME, AxesStyle.FRAME.getPSTToken());
		assertEquals(PSTricksConstants.TOKEN_AXES_STYLE_NONE, AxesStyle.NONE.getPSTToken());
	}

	@ParameterizedTest
	@EnumSource(AxesStyle.class)
	void testGetStylePSTConst(final AxesStyle style) {
		assertEquals(AxesStyle.getStyle(style.getPSTToken()), style);
	}

	@Test
	void testGetStyleKO() {
		assertEquals(AxesStyle.AXES, AxesStyle.getStyle(null));
		assertEquals(AxesStyle.AXES, AxesStyle.getStyle(""));
	}

	@ParameterizedTest
	@EnumSource(AxesStyle.class)
	void testGetStyleAxesStyle(final AxesStyle style) {
		assertEquals(AxesStyle.getStyle(style.name()), style);
	}

	@ParameterizedTest
	@EnumSource(AxesStyle.class)
	void testGetLabel(final AxesStyle style) {
		final ResourceBundle lang = new PreferencesService().getBundle();
		assertNotNull(style.getLabel(lang));
		assertFalse(style.getLabel(lang).isEmpty());
	}
}
