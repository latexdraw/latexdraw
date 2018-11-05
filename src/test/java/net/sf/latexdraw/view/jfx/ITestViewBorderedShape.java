package net.sf.latexdraw.view.jfx;

import javafx.scene.shape.Shape;
import net.sf.latexdraw.model.api.shape.SingleShape;

public interface ITestViewBorderedShape<T extends ViewSingleShape<S, R>, S extends SingleShape, R extends Shape> extends ITestViewShape<T, S> {
	R getBorder();
}
