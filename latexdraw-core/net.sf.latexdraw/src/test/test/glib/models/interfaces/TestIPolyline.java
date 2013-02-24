package test.glib.models.interfaces;

import net.sf.latexdraw.glib.models.interfaces.IPolyline;

import org.junit.Test;


public abstract class TestIPolyline<T extends IPolyline> extends TestIPolygon<T> {
	@Override
	@Test
	public void testIsArrowable() {
		assertTrue(shape.isArrowable());
	}
}

