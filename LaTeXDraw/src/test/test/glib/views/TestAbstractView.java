package test.glib.views;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.views.IAbstractView;

import org.junit.Test;

public abstract class TestAbstractView<T extends IAbstractView> extends TestCase {
	public T view;

	@Test
	public void testGetView() {
		assertNotNull(view.getShape());
	}


	@Test
	public void testUpdate() {
		view.update();
	}
}
