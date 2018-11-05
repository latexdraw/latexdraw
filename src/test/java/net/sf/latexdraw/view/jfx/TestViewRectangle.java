package net.sf.latexdraw.view.jfx;

import javafx.application.Platform;
import net.sf.latexdraw.data.RectSupplier;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestViewRectangle extends TestViewBorderedShape<ViewRectangle, Rectangle, javafx.scene.shape.Rectangle> {
	@BeforeAll
	public static void beforeClass() {
		try {
			Platform.startup(() -> {
			});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	@Override
	protected Rectangle createModel() {
		return RectSupplier.createRectangle();
	}

	@Test
	void testXPosition() {
		model.setX(123d);
		assertEquals(123d, border.getX(), 0.001);
	}

	@Test
	void testXPositionDbleBordInside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(123d + model.getThickness(), view.getDbleBorder().orElseThrow().getX(), 0.001);
	}

	@Test
	void testXPositionDbleBordMiddle() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(123d, view.getDbleBorder().orElseThrow().getX(), 0.001);
	}

	@Test
	void testXPositionDbleBordOutside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(123d - model.getThickness(), view.getDbleBorder().orElseThrow().getX(), 0.001);
	}


	@Test
	void testYPosition() {
		model.setY(-123.4);
		assertEquals(-123.4 - model.getHeight(), border.getY(), 0.001);
	}

	@Test
	void testYPositionDbleBordInside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getY() + model.getThickness(), view.getDbleBorder().orElseThrow().getY(), 0.001);
	}

	@Test
	void testYPositionDbleBordMiddle() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getY(), view.getDbleBorder().orElseThrow().getY(), 0.001);
	}

	@Test
	void testYPositionDbleBordOutside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getY() - model.getThickness(), view.getDbleBorder().orElseThrow().getY(), 0.001);
	}

	@Test
	void testWidth() {
		model.setWidth(74.3);
		assertEquals(74.3, border.getWidth(), 0.001d);
	}

	@Test
	void testWidthDbleBordInside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getWidth() - 2d * model.getThickness(), view.getDbleBorder().orElseThrow().getWidth(), 0.001);
	}

	@Test
	void testWidthDbleBordMiddle() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getWidth(), view.getDbleBorder().orElseThrow().getWidth(), 0.001);
	}

	@Test
	void testWidthDbleBordOutside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getWidth() + 2d * model.getThickness(), view.getDbleBorder().orElseThrow().getWidth(), 0.001);
	}

	@Test
	void testHeight() {
		model.setHeight(2d);
		assertEquals(2d, border.getHeight(), 0.001);
	}

	@Test
	void testHeightDbleBordInside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getHeight() - 2d * model.getThickness(), view.getDbleBorder().orElseThrow().getHeight(), 0.001);
	}

	@Test
	void testHeightDbleBordMiddle() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getHeight(), view.getDbleBorder().orElseThrow().getHeight(), 0.001);
	}

	@Test
	void testHeightDbleBordOutside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getHeight() + 2d * model.getThickness(), view.getDbleBorder().orElseThrow().getHeight(), 0.001);
	}

	@Override
	@Test
	void testShadowPositionSameThanBorder() {
		model.setHasShadow(true);
		assertEquals(border.getX(), view.getShadow().orElseThrow().getX(), 0.01);
		assertEquals(border.getY(), view.getShadow().orElseThrow().getY(), 0.01);
	}

	@Test
	void testBorderLineArcWidth() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getWidth(), getBorder().getArcWidth(), 0.000001);
	}

	@Test
	void testBorderLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getBorder().getArcHeight(), 0.000001);
	}

	@Test
	void testShadowLineArcWidth() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getWidth(), getView().getShadow().orElseThrow().getArcWidth(), 0.000001);
	}

	@Test
	void testShadowLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getView().getShadow().orElseThrow().getArcHeight(), 0.000001);
	}

	@Test
	void testDbleLineArcWidth() {
		if(getModel().isDbleBorderable()) {
			getModel().setLineArc(0.33);
			assertEquals(0.33 * getModel().getWidth(), getView().getDbleBorder().orElseThrow().getArcWidth(), 0.000001);
		}
	}

	@Test
	void testDbleLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getView().getDbleBorder().orElseThrow().getArcHeight(), 0.000001);
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
