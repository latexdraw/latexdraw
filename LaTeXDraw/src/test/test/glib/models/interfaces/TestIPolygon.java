package test.glib.models.interfaces;

import net.sf.latexdraw.glib.models.interfaces.IPolygon;

import org.junit.Test;


public abstract class TestIPolygon<T extends IPolygon> extends TestIModifiablePointsShape<T> {
	@Test
	public void testIsArrowable() {
		assertFalse(shape.isArrowable());
	}
}
