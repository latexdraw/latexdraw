package net.sf.latexdraw.instruments;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Theories.class)
public class TestCanvasTranslation extends BaseTestCanvas {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(ShapeCoordDimCustomiser.class, Mockito.mock(ShapeCoordDimCustomiser.class));
				bindToInstance(ShapeBorderCustomiser.class, Mockito.mock(ShapeBorderCustomiser.class));
				bindToInstance(ShapeDoubleBorderCustomiser.class, Mockito.mock(ShapeDoubleBorderCustomiser.class));
				bindToInstance(ShapeShadowCustomiser.class, Mockito.mock(ShapeShadowCustomiser.class));
				bindToInstance(ShapeFillingCustomiser.class, Mockito.mock(ShapeFillingCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapeArrowCustomiser.class, Mockito.mock(ShapeArrowCustomiser.class));
				bindToInstance(ShapeDotCustomiser.class, Mockito.mock(ShapeDotCustomiser.class));
				bindToInstance(ShapeArcCustomiser.class, Mockito.mock(ShapeArcCustomiser.class));
				bindToInstance(ShapeStdGridCustomiser.class, Mockito.mock(ShapeStdGridCustomiser.class));
				bindToInstance(ShapeAxesCustomiser.class, Mockito.mock(ShapeAxesCustomiser.class));
				bindToInstance(ShapeGridCustomiser.class, Mockito.mock(ShapeGridCustomiser.class));
				bindToInstance(ShapeFreeHandCustomiser.class, Mockito.mock(ShapeFreeHandCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(ShapeTransformer.class, Mockito.mock(ShapeTransformer.class));
				bindToInstance(ShapeGrouper.class, Mockito.mock(ShapeGrouper.class));
				bindToInstance(ShapePositioner.class, Mockito.mock(ShapePositioner.class));
				bindToInstance(ShapeRotationCustomiser.class, Mockito.mock(ShapeRotationCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		hand.setActivated(true);
		canvas.getMagneticGrid().setMagnetic(false);
		when(pencil.isActivated()).thenReturn(false);
		final MetaShapeCustomiser meta = (MetaShapeCustomiser) injectorFactory.call(MetaShapeCustomiser.class);
		meta.dimPosCustomiser = (ShapeCoordDimCustomiser) injectorFactory.call(ShapeCoordDimCustomiser.class);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Theory
	public void testShapeTranslationOK(@ShapeData final IShape sh) {
		sh.setFilled(true);
		sh.translate(-canvas.getOrigin().getX(), -canvas.getOrigin().getY());
		Platform.runLater(() -> {
			canvas.getDrawing().addShape(sh);
			canvas.requestFocus();
		});
		WaitForAsyncUtils.waitForFxEvents();
		final IPoint tl = sh.getTopLeftPoint();
		clickOn(canvas);
		canvas.getSelectionBorder().setFill(new Color(1d,1d, 1d, 0.1d));
		press(KeyCode.CONTROL, KeyCode.A).sleep(10).release(KeyCode.CONTROL, KeyCode.A).sleep(10).drag(canvas.getSelectionBorder()).dropBy(101, 163).sleep(SLEEP);

		assertEquals(tl.getX() + 101d, sh.getTopLeftPoint().getX(), 2d);
		assertEquals(tl.getY() + 163d, sh.getTopLeftPoint().getY(), 2d);
	}

	@Test
	public void testTranslateCorrectSelectedShape() {
		new CompositeGUIVoidCommand(addRec, addRec2, waitFXEvents).execute();
		canvas.getDrawing().getShapes().forEach(sh -> sh.setFilled(true));
		final IPoint tl1 = canvas.getDrawing().getShapeAt(0).getTopLeftPoint();
		final IPoint tl2 = canvas.getDrawing().getShapeAt(1).getTopLeftPoint();
		clickOn(canvas.getViews().getChildren().get(0)).sleep(10).drag(canvas.getViews().getChildren().get(1)).sleep(10).
			dropBy(100d, 200d).sleep(SLEEP);

		assertEquals(tl1, canvas.getDrawing().getShapeAt(0).getTopLeftPoint());
		assertEquals(tl2.getX() + 100d, canvas.getDrawing().getShapeAt(1).getTopLeftPoint().getX(), 1d);
		assertEquals(tl2.getY() + 200d, canvas.getDrawing().getShapeAt(1).getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testTranslateOnSelectionRectangle() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		final IPoint tl = canvas.getDrawing().getShapeAt(0).getTopLeftPoint();

		clickOn(canvas.getViews().getChildren().get(0)).sleep(SLEEP);
		final Point2D bounds = canvas.localToScreen(canvas.getSelectionBorder().getLayoutX() + Canvas.MARGINS,
			canvas.getSelectionBorder().getLayoutY() + Canvas.MARGINS);
		drag(bounds.getX() + 150, bounds.getY()).sleep(10).dropBy(100d, 200d).sleep(SLEEP);
		assertEquals(tl.getX() + 100d, canvas.getDrawing().getShapeAt(0).getTopLeftPoint().getX(), 1d);
		assertEquals(tl.getY() + 200d, canvas.getDrawing().getShapeAt(0).getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testTranslateAbortOK() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		final IPoint tl = canvas.getDrawing().getShapeAt(0).getTopLeftPoint();
		drag(canvas.getViews().getChildren().get(0)).sleep(10).moveBy(100d, 200d).type(KeyCode.ESCAPE).sleep(SLEEP);
		assertEquals(tl, canvas.getDrawing().getShapeAt(0).getTopLeftPoint());
	}
}
