package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.models.interfaces.shape.ISingleShape;

public interface ITestViewShape<T extends ViewShape<S>, S extends ISingleShape> {
	T getView();

	S getModel();

	void testOnTranslateX();

	void testOnTranslateY();
}
