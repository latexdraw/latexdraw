package net.sf.latexdraw.models.interfaces.shape;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ExtendWith(InjectionExtension.class)
public class TestAxesStyle {
	@ConfigureInjection
	Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(SystemService.class);
				bindAsEagerSingleton(LangService.class);
			}
		};
	}

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
	void testGetLabel(final AxesStyle style, final LangService lang) {
		assertNotNull(style.getLabel(lang.getBundle()));
		assertFalse(style.getLabel(lang.getBundle()).isEmpty());
	}
}
