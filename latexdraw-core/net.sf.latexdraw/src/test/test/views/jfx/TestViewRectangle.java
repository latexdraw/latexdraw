package test.views.jfx;

import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.view.jfx.ViewRectangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestViewRectangle extends TestViewSingleShape<ViewRectangle, IRectangle, Rectangle> {
	@Override
	protected IRectangle createModel() {
		final IRectangle rec = ShapeFactory.INST.createRectangle();
		rec.setWidth(10d);
		rec.setHeight(20d);
		rec.setX(100d);
		rec.setY(200d);
		return rec;
	}

	@Test
	public void testXPosition() {
		model.setX(123d);
		assertEquals(123d, border.getX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordInside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(123d+model.getThickness(), view.getDbleBorder().get().getX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordMiddle() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(123d+model.getThickness(), view.getDbleBorder().get().getX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordOutside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(123d+model.getThickness(), view.getDbleBorder().get().getX(), 0.001);
	}


	@Test
	public void testYPosition() {
		model.setY(-123.4);
		assertEquals(-123.4 - model.getHeight(), border.getY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordInside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getY()+model.getThickness(), view.getDbleBorder().get().getY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordMiddle() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getY()+model.getThickness(), view.getDbleBorder().get().getY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordOutside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getY()+model.getThickness(), view.getDbleBorder().get().getY(), 0.001);
	}

	@Test
	public void testWidth() {
		model.setWidth(74.3);
		assertEquals(74.3, border.getWidth(), 0.001d);
	}

	@Test
	public void testWidthDbleBordInside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getWidth()-2d*model.getThickness(), view.getDbleBorder().get().getWidth(), 0.001);
	}

	@Test
	public void testWidthDbleBordMiddle() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getWidth()-2d*model.getThickness(), view.getDbleBorder().get().getWidth(), 0.001);
	}

	@Test
	public void testWidthDbleBordOutside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getWidth()-2d*model.getThickness(), view.getDbleBorder().get().getWidth(), 0.001);
	}

	@Test
	public void testHeight() {
		model.setHeight(2d);
		assertEquals(2d, border.getHeight(), 0.001);
	}

	@Test
	public void testHeightDbleBordInside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getHeight()-2d*model.getThickness(), view.getDbleBorder().get().getHeight(), 0.001);
	}

	@Test
	public void testHeightDbleBordMiddle() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getHeight()-2d*model.getThickness(), view.getDbleBorder().get().getHeight(), 0.001);
	}

	@Test
	public void testHeightDbleBordOutside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getHeight()-2d*model.getThickness(), view.getDbleBorder().get().getHeight(), 0.001);
	}

	@Override
	@Test
	public void testShadowPositionSameThanBorder() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			assertEquals(border.getX(), view.getShadow().get().getX(), 0.01);
			assertEquals(border.getY(), view.getShadow().get().getY(), 0.01);
		}
	}
}
