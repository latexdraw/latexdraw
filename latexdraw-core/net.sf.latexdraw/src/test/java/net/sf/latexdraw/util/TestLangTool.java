package net.sf.latexdraw.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(Theories.class)
public class TestLangTool {
	@DataPoints
	public static Locale[] locales = new Locale[] {Locale.TAIWAN, new Locale("foo", "Bar")};

	Constructor<LangTool> constructor;
	LangTool lang;
	Locale locale;

	@Before
	public void setUp() throws Exception {
		locale = Locale.getDefault();
		constructor = LangTool.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		lang = constructor.newInstance();
	}

	@After
	public void tearDown() {
		constructor.setAccessible(false);
		Locale.setDefault(locale);
	}

	@Test
	public void testgetBundle() {
		assertNotNull(lang.getBundle());
	}

	@Test
	public void getSupportedLocales() {
		assertNotNull(lang.getSupportedLocales());
		assertFalse(lang.getSupportedLocales().isEmpty());
	}

	@Theory
	public void testIncorrectLocale(final Locale badLocale) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Locale.setDefault(badLocale);
		lang = constructor.newInstance();
		assertNotNull(lang.getBundle());
	}
}
