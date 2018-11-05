package net.sf.latexdraw.model.api.shape;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(InjectionExtension.class)
public class TestArcStyle {
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
	void testSupportArrow() {
		assertFalse(ArcStyle.CHORD.supportArrow());
		assertFalse(ArcStyle.WEDGE.supportArrow());
		assertTrue(ArcStyle.ARC.supportArrow());
	}

	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testGetLabel(final ArcStyle style, final LangService lang) {
		assertNotNull(style.getLabel(lang.getBundle()));
		assertFalse(style.getLabel(lang.getBundle()).isEmpty());
	}
}
