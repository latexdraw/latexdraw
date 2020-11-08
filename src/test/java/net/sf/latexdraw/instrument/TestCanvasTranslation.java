package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
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
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
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
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTransformer.class, Mockito.mock(ShapeTransformer.class));
				bindToInstance(ShapeGrouper.class, Mockito.mock(ShapeGrouper.class));
				bindToInstance(ShapePositioner.class, Mockito.mock(ShapePositioner.class));
				bindToInstance(ShapeRotationCustomiser.class, Mockito.mock(ShapeRotationCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Theory
	public void testShapeTranslationOK(@ShapeData final Shape sh) {
		if(sh instanceof Picture) {
			((Picture) sh).setPathSource(getClass().getResource("/Arc.png").getFile());
		}

		Cmds.of(CmdFXVoid.of(() -> {
			canvas.getSelectionBorder().setFill(new Color(1d, 1d, 1d, 0.1d));
			sh.setFilled(true);
			sh.translate(-canvas.getOrigin().getX(), -canvas.getOrigin().getY());
			canvas.getDrawing().addShape(sh);
			canvas.getDrawing().setSelection(List.of(sh));
		}), requestFocusCanvas).execute();

		final Point tl = sh.getTopLeftPoint();

		Cmds
			.of(() -> drag(canvas.getSelectionBorder()).dropBy(101, 163))
			.execute();

		assertEquals(tl.getX() + 101d, sh.getTopLeftPoint().getX(), 5d);
		assertEquals(tl.getY() + 163d, sh.getTopLeftPoint().getY(), 5d);
	}

	@Test
	public void testShapeTranslationWithZoom50() {
		final var sh = ShapeSupplier.createRectangle();

		Cmds.of(CmdFXVoid.of(() -> {
			canvas.getSelectionBorder().setFill(new Color(1d, 1d, 1d, 0.1d));
			sh.setFilled(true);
			sh.translate(-canvas.getOrigin().getX(), -canvas.getOrigin().getY());
			canvas.getDrawing().addShape(sh);
			canvas.getDrawing().setSelection(List.of(sh));
			canvas.setZoom(Double.NaN, Double.NaN, 0.5);
		}), requestFocusCanvas).execute();

		final Point tl = sh.getTopLeftPoint();

		Cmds
			.of(() -> drag(canvas.getSelectionBorder()).dropBy(100, 160))
			.execute();

		assertEquals(tl.getX() + 200d, sh.getTopLeftPoint().getX(), 5d);
		assertEquals(tl.getY() + 320d, sh.getTopLeftPoint().getY(), 5d);
	}

	@Test
	public void testShapeTranslationWithZoom120() {
		final var sh = ShapeSupplier.createRectangle();

		Cmds.of(CmdFXVoid.of(() -> {
			canvas.getSelectionBorder().setFill(new Color(1d, 1d, 1d, 0.1d));
			sh.setFilled(true);
			sh.translate(-canvas.getOrigin().getX(), -canvas.getOrigin().getY());
			canvas.getDrawing().addShape(sh);
			canvas.getDrawing().setSelection(List.of(sh));
			canvas.setZoom(Double.NaN, Double.NaN, 1.2);
		}), requestFocusCanvas).execute();

		final Point tl = sh.getTopLeftPoint();

		Cmds
			.of(() -> drag(canvas.getSelectionBorder()).dropBy(100, 160))
			.execute();

		assertEquals(tl.getX() + (100d / 1.2), sh.getTopLeftPoint().getX(), 5d);
		assertEquals(tl.getY() + (160d / 1.2), sh.getTopLeftPoint().getY(), 5d);
	}

	@Test
	public void testTranslateSeveralShapesUsingOne() {
		Cmds.of(addRec, addRec2, selectAllShapes,
			CmdFXVoid.of(() -> canvas.getDrawing().getShapes().forEach(sh -> sh.setFilled(true)))).execute();
		final Point tl1 = canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint();
		final Point tl2 = canvas.getDrawing().getShapeAt(1).orElseThrow().getTopLeftPoint();

		Cmds.of(() -> drag(canvas.getViews().getChildren().get(1)),
			() -> dropBy(100d, 200d)).execute();

		assertEquals(2, canvas.getDrawing().getSelection().size());
		assertEquals(tl1.getX() + 100d, canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 1d);
		assertEquals(tl1.getY() + 200d, canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 1d);
		assertEquals(tl2.getX() + 100d, canvas.getDrawing().getShapeAt(1).orElseThrow().getTopLeftPoint().getX(), 1d);
		assertEquals(tl2.getY() + 200d, canvas.getDrawing().getShapeAt(1).orElseThrow().getTopLeftPoint().getY(), 1d);
	}


	@Test
	public void testTranslateCorrectSelectedShape() {
		Cmds.of(addRec, addRec2,
			CmdFXVoid.of(() -> canvas.getDrawing().getShapes().forEach(sh -> sh.setFilled(true)))).execute();
		final Point tl1 = canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint();
		final Point tl2 = canvas.getDrawing().getShapeAt(1).orElseThrow().getTopLeftPoint();

		Cmds.of(() -> clickOn(canvas.getViews().getChildren().get(0)),
			() -> drag(canvas.getViews().getChildren().get(1)),
			() -> dropBy(100d, 200d)).execute();

		assertEquals(tl1, canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint());
		assertEquals(tl2.getX() + 100d, canvas.getDrawing().getShapeAt(1).orElseThrow().getTopLeftPoint().getX(), 1d);
		assertEquals(tl2.getY() + 200d, canvas.getDrawing().getShapeAt(1).orElseThrow().getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testTranslateOnSelectionRectangle() {
		Cmds.of(addRec).execute();
		final Point tl = canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint();

		Cmds.of(() -> clickOn(canvas.getViews().getChildren().get(0))).execute();

		final Point2D bounds = canvas.localToScreen(canvas.getSelectionBorder().getLayoutX() + Canvas.getMargins(),
			canvas.getSelectionBorder().getLayoutY() + Canvas.getMargins());

		Cmds.of(() -> drag(bounds.getX() + 150, bounds.getY()),
			() -> dropBy(100d, 200d)).execute();

		assertEquals(tl.getX() + 100d, canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 1d);
		assertEquals(tl.getY() + 200d, canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testTranslateAbortOK() {
		Cmds.of(addRec).execute();
		final Point tl = canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint();

		Cmds.of(() -> drag(canvas.getViews().getChildren().get(0)),
			() -> moveBy(100d, 200d).type(KeyCode.ESCAPE)).execute();

		assertEquals(tl, canvas.getDrawing().getShapeAt(0).orElseThrow().getTopLeftPoint());
	}
}
