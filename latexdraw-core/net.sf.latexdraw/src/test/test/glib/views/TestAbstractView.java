package test.glib.views;

import net.sf.latexdraw.glib.views.IAbstractView;

import org.junit.Test;
import static org.junit.Assert.*;

public abstract class TestAbstractView<T extends IAbstractView> {
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
