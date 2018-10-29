package net.sf.latexdraw.view.jfx;

import javafx.application.Platform;
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestViewEllipse extends TestViewBorderedShape<ViewEllipse, IEllipse, Ellipse> {
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
	protected IEllipse createModel() {
		final IEllipse rec = ShapeFactory.INST.createEllipse();
		rec.setWidth(10d);
		rec.setHeight(20d);
		rec.setX(100d);
		rec.setY(200d);
		return rec;
	}

	@Test
	void testRadiusXPosition() {
		model.setWidth(20d);
		assertEquals(10d, border.getRadiusX(), 0.001);
	}

	@Test
	void testRadiusYPosition() {
		model.setHeight(24d);
		assertEquals(12d, border.getRadiusY(), 0.001);
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

	@Test
	void testHeightDbleBordInside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getRadiusY() - model.getThickness(), view.getDbleBorder().orElseThrow().getRadiusY(), 0.001);
	}

	@Test
	void testHeightDbleBordMiddle() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getRadiusY(), view.getDbleBorder().orElseThrow().getRadiusY(), 0.001);
	}

	@Test
	void testHeightDbleBordOutside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getRadiusY() + model.getThickness(), view.getDbleBorder().orElseThrow().getRadiusY(), 0.001);
	}


	@Override
	@Test
	void testShadowPositionSameThanBorder() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			assertEquals(border.getCenterX(), view.getShadow().orElseThrow().getCenterX(), 0.01);
			assertEquals(border.getCenterY(), view.getShadow().orElseThrow().getCenterY(), 0.01);
		}
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
