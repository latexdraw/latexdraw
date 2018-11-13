package net.sf.latexdraw.model.api.shape;

import java.util.ResourceBundle;
import net.sf.latexdraw.service.PreferencesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestArcStyle {
	@Test
	void testSupportArrow() {
		assertFalse(ArcStyle.CHORD.supportArrow());
		assertFalse(ArcStyle.WEDGE.supportArrow());
		assertTrue(ArcStyle.ARC.supportArrow());
	}

	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testGetLabel(final ArcStyle style) {
		final ResourceBundle bundle = new PreferencesService().getBundle();
		assertNotNull(style.getLabel(bundle));
		assertFalse(style.getLabel(bundle).isEmpty());
	}
}
