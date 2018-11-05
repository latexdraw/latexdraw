package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.model.api.shape.SingleShape;

public interface ITestViewShape<T extends ViewShape<S>, S extends SingleShape> {
	T getView();

	S getModel();

	void testOnTranslateX();

	void testOnTranslateY();
}
