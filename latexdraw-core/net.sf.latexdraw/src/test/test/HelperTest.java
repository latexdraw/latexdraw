package test;


import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;

public abstract class HelperTest {
	public static Field getField(final Class<?> clazz, final String name) throws SecurityException, NoSuchFieldException {
		final Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}


	public static boolean isX11Set() {
		return !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();
	}


	public static void assertEqualsDouble(double v1, double v2) {
		junit.framework.Assert.assertEquals(v1, v2, 0.0000001);
	}
}
