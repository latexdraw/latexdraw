package test.views.jfx;

import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.jfx.ViewShape;

public interface ITestViewShape<T extends ViewShape<S>, S extends ISingleShape> {
	T getView();

	S getModel();
}
