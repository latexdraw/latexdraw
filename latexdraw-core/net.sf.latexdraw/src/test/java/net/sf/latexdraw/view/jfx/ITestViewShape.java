package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.Test;

public interface ITestViewShape<T extends ViewShape<S>, S extends ISingleShape> {
	T getView();

	S getModel();

	@Test
	void testOnTranslateX();

	@Test
	void testOnTranslateY();
}
