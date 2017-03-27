package test.views.jfx;

import java.util.concurrent.TimeoutException;
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.view.jfx.ViewEllipse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;

public class TestViewEllipse extends TestViewBorderedShape<ViewEllipse, IEllipse, Ellipse> {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
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
	public void testRadiusXPosition() {
		model.setWidth(20d);
		assertEquals(10d, border.getRadiusX(), 0.001);
	}

	@Test
	public void testRadiusYPosition() {
		model.setHeight(24d);
		assertEquals(12d, border.getRadiusY(), 0.001);
	}

	@Test
	public void testCenterXPosition() {
		model.setX(123d);
		assertEquals(123d + border.getRadiusX(), border.getCenterX(), 0.001);
	}

	@Test
	public void testXMaxPositionDbleBordInside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(123d + model.getThickness(), view.getDbleBorder().get().getCenterX() - view.getDbleBorder().get().getRadiusX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordMiddle() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(123d + model.getThickness(), view.getDbleBorder().get().getCenterX() - view.getDbleBorder().get().getRadiusX(), 0.001);
	}

	@Test
	public void testXPositionDbleBordOutside() {
		model.setX(123d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(123d + model.getThickness(), view.getDbleBorder().get().getCenterX() - view.getDbleBorder().get().getRadiusX(), 0.001);
	}


	@Test
	public void testYPosition() {
		model.setY(-123.4);
		assertEquals(-123.4 - model.getHeight() / 2d, border.getCenterY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordInside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getCenterY() - border.getRadiusY() + model.getThickness(), view.getDbleBorder().get().getCenterY() - view.getDbleBorder().get().getRadiusY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordMiddle() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getCenterY() - border.getRadiusY() + model.getThickness(), view.getDbleBorder().get().getCenterY() - view.getDbleBorder().get().getRadiusY(), 0.001);
	}

	@Test
	public void testYPositionDbleBordOutside() {
		model.setY(-123.4);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getCenterY() - border.getRadiusY() + model.getThickness(), view.getDbleBorder().get().getCenterY() - view.getDbleBorder().get().getRadiusY(), 0.001);
	}

	@Test
	public void testWidthDbleBordInside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getRadiusX() - model.getThickness(), view.getDbleBorder().get().getRadiusX(), 0.001);
	}

	@Test
	public void testWidthDbleBordMiddle() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getRadiusX() - model.getThickness(), view.getDbleBorder().get().getRadiusX(), 0.001);
	}

	@Test
	public void testWidthDbleBordOutside() {
		model.setWidth(74.3);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getRadiusX() - model.getThickness(), view.getDbleBorder().get().getRadiusX(), 0.001);
	}

	@Test
	public void testHeightDbleBordInside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.INTO);
		assertEquals(border.getRadiusY() - model.getThickness(), view.getDbleBorder().get().getRadiusY(), 0.001);
	}

	@Test
	public void testHeightDbleBordMiddle() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.MID);
		assertEquals(border.getRadiusY() - model.getThickness(), view.getDbleBorder().get().getRadiusY(), 0.001);
	}

	@Test
	public void testHeightDbleBordOutside() {
		model.setHeight(10d);
		model.setBordersPosition(BorderPos.OUT);
		assertEquals(border.getRadiusY() - model.getThickness(), view.getDbleBorder().get().getRadiusY(), 0.001);
	}


	@Override
	@Test
	public void testShadowPositionSameThanBorder() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			assertEquals(border.getCenterX(), view.getShadow().get().getCenterX(), 0.01);
			assertEquals(border.getCenterY(), view.getShadow().get().getCenterY(), 0.01);
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
