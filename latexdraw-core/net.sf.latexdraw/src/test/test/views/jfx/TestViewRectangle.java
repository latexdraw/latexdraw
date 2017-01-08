package test.views.jfx;

import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.view.jfx.ViewRectangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestViewRectangle extends TestViewSingleShape<ViewRectangle, IRectangle, Rectangle> {
	@Override
	protected IRectangle createModel() {
		return ShapeFactory.INST.createRectangle();
	}

	@Test
	public void testXPosition() {
		model.setX(123d);
		assertEquals(123d, border.getX(), 0.00d);
	}

	@Test
	public void testYPosition() {
		model.setY(-123.4);
		assertEquals(-123.4 - model.getHeight(), border.getY(), 0.00d);
	}

	@Test
	public void testWidth() {
		model.setWidth(74.3);
		assertEquals(74.3, border.getWidth(), 0.00d);
	}

	@Test
	public void testHeight() {
		model.setHeight(2d);
		assertEquals(2d, border.getHeight(), 0.00d);
	}
}
