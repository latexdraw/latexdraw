package net.sf.latexdraw.view.jfx;

import javafx.scene.shape.Shape;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;

public interface ITestViewBorderedShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> extends ITestViewShape<T, S> {
	R getBorder();
}
