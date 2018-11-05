package net.sf.latexdraw.view.jfx;

import javafx.application.Platform;
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.data.CircleSupplier;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Circle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestViewCircle extends TestViewBorderedShape<ViewCircle, Circle, Ellipse> {
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
	protected Circle createModel() {
		return CircleSupplier.createCircle();
	}

	@Test
	void testRadiusXPosition() {
		model.setWidth(20d);
		assertEquals(10d, border.getRadiusX(), 0.001);
	}


	@Test
	void testCenterXPosition() {
		model.setX(123d);
		assertEquals(123d + border.getRadiusX(), border.getCenterX(), 0.001);
	}

	@Test
	void testXMaxPositionDbleBordInside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(123d + model.getThickness(), view.getDbleBorder().orElseThrow().getCenterX() - view.getDbleBorder().orElseThrow().getRadiusX(), 0.001);
	}

	@Test
	void testXPositionDbleBordMiddle() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(123d, view.getDbleBorder().orElseThrow().getCenterX() - view.getDbleBorder().orElseThrow().getRadiusX(), 0.001);
	}

	@Test
	void testXPositionDbleBordOutside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(123d - model.getThickness(), view.getDbleBorder().orElseThrow().getCenterX() - view.getDbleBorder().orElseThrow().getRadiusX(), 0.001);
	}


	@Test
	void testYPosition() {
		model.setY(-123.4);
		assertEquals(-123.4 - model.getHeight() / 2d, border.getCenterY(), 0.001);
	}

	@Test
	void testYPositionDbleBordInside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getCenterY() - border.getRadiusY() + model.getThickness(), view.getDbleBorder().orElseThrow().getCenterY() - view.getDbleBorder().orElseThrow().getRadiusY(), 0.001);
	}

	@Test
	void testYPositionDbleBordMiddle() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getCenterY() - border.getRadiusY(), view.getDbleBorder().orElseThrow().getCenterY() - view.getDbleBorder().orElseThrow().getRadiusY(), 0.001);
	}

	@Test
	void testYPositionDbleBordOutside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getCenterY() - border.getRadiusY() - model.getThickness(), view.getDbleBorder().orElseThrow().getCenterY() - view.getDbleBorder().orElseThrow().getRadiusY(), 0.001);
	}

	@Test
	void testWidthDbleBordInside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getRadiusX() - model.getThickness(), view.getDbleBorder().orElseThrow().getRadiusX(), 0.001);
	}

	@Test
	void testWidthDbleBordMiddle() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getRadiusX(), view.getDbleBorder().orElseThrow().getRadiusX(), 0.001);
	}

	@Test
	void testWidthDbleBordOutside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getRadiusX() + model.getThickness(), view.getDbleBorder().orElseThrow().getRadiusX(), 0.001);
	}

	@Override
	@Test
	void testShadowPositionSameThanBorder() {
		model.setHasShadow(true);
		assertEquals(border.getCenterX(), view.getShadow().orElseThrow().getCenterX(), 0.01);
		assertEquals(border.getCenterY(), view.getShadow().orElseThrow().getCenterY(), 0.01);
	}

	@Override
	@Test
	public void testOnTranslateX() {
		final double x = getBorder().getCenterX();
		model.translate(11d, 0d);
		assertEquals(x + 11d, getBorder().getCenterX(), 0.0000001);
	}

	@Override
	@Test
	public void testOnTranslateY() {
		final double y = getBorder().getCenterY();
		model.translate(0d, 13d);
		assertEquals(y + 13d, getBorder().getCenterY(), 0.0000001);
	}
}
