package test.views.jfx;

import javafx.scene.shape.Shape;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.jfx.ViewSingleShape;

public interface ITestViewShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> {
	T getView();
	S getModel();
	R getBorder();
}
