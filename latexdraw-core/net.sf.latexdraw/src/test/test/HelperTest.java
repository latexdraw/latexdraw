package test;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

public interface HelperTest {
	default Field getField(final Class<?> clazz, final String name) throws SecurityException, NoSuchFieldException {
		final Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	default boolean isX11Set() {
		return !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();
	}

	default void assertEqualsDouble(double v1, double v2) {
		assertEquals(v1, v2, 0.0000001);
	}
}
