package net.sf.latexdraw.view.jfx;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.data.RectSupplier;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestViewRectangle extends TestViewBorderedShape<ViewRectangle, IRectangle, Rectangle> {
	@BeforeClass
	public static void beforeClass() {
		Platform.startup(() -> {});
	}

	@Override
	protected IRectangle createModel() {
		return RectSupplier.createRectangle();
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
		assertEquals(123d + model.getThickness(), view.getDbleBorder().get().getX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordMiddle() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(123d, view.getDbleBorder().get().getX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordOutside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(123d - model.getThickness(), view.getDbleBorder().get().getX(), 0.001);
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
		assertEquals(border.getY() + model.getThickness(), view.getDbleBorder().get().getY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordMiddle() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getY(), view.getDbleBorder().get().getY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordOutside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getY() - model.getThickness(), view.getDbleBorder().get().getY(), 0.001);
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
		assertEquals(border.getWidth() - 2d * model.getThickness(), view.getDbleBorder().get().getWidth(), 0.001);
	}

	@Test
	public void testWidthDbleBordMiddle() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getWidth(), view.getDbleBorder().get().getWidth(), 0.001);
	}

	@Test
	public void testWidthDbleBordOutside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getWidth() + 2d * model.getThickness(), view.getDbleBorder().get().getWidth(), 0.001);
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
		assertEquals(border.getHeight() - 2d * model.getThickness(), view.getDbleBorder().get().getHeight(), 0.001);
	}

	@Test
	public void testHeightDbleBordMiddle() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getHeight(), view.getDbleBorder().get().getHeight(), 0.001);
	}

	@Test
	public void testHeightDbleBordOutside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getHeight() + 2d * model.getThickness(), view.getDbleBorder().get().getHeight(), 0.001);
	}

	@Override
	@Test
	public void testShadowPositionSameThanBorder() {
		model.setHasShadow(true);
		assertEquals(border.getX(), view.getShadow().get().getX(), 0.01);
		assertEquals(border.getY(), view.getShadow().get().getY(), 0.01);
	}

	@Test
	public void testBorderLineArcWidth() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getWidth(), getBorder().getArcWidth(), 0.000001);
	}

	@Test
	public void testBorderLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getBorder().getArcHeight(), 0.000001);
	}

	@Test
	public void testShadowLineArcWidth() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getWidth(), getView().getShadow().get().getArcWidth(), 0.000001);
	}

	@Test
	public void testShadowLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getView().getShadow().get().getArcHeight(), 0.000001);
	}

	@Test
	public void testDbleLineArcWidth() {
		if(getModel().isDbleBorderable()) {
			getModel().setLineArc(0.33);
			assertEquals(0.33 * getModel().getWidth(), getView().getDbleBorder().get().getArcWidth(), 0.000001);
		}
	}

	@Test
	public void testDbleLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getView().getDbleBorder().get().getArcHeight(), 0.000001);
	}

	@Override
	@Test
	public void testOnTranslateX() {
		final double x = getBorder().getX();
		model.translate(11d, 0d);
		assertEquals(x + 11d, getBorder().getX(), 0.0000001);
	}

	@Override
	@Test
	public void testOnTranslateY() {
		final double y = getBorder().getY();
		model.translate(0d, 13d);
		assertEquals(y + 13d, getBorder().getY(), 0.0000001);
	}
}
