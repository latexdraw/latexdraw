package test.views.jfx;

import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.view.jfx.ViewRectangle;

public class TestViewRectangle extends TestViewSingleShape<ViewRectangle, IRectangle, Rectangle> {
	@Override
	protected IRectangle createModel() {
		return ShapeFactory.INST.createRectangle();
	}
}
