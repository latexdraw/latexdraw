package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewArrow;
import net.sf.latexdraw.view.jfx.ViewPlot;
import net.sf.latexdraw.view.jfx.ViewPolyline;
import net.sf.latexdraw.view.jfx.ViewText;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestCanvasSelection extends BaseTestCanvas {
	TextSetter setter;

	final GUIVoidCommand clickOnAddedFirstShape = () -> rightClickOn(getPane().getChildren().get(0));

	final GUIVoidCommand ctrlClickOnAddedRec2 = () -> press(KeyCode.CONTROL).rightClickOn(getPane().getChildren().get(1)).release(KeyCode.CONTROL);

	final GUIVoidCommand shiftClickOnAddedRec = () -> press(KeyCode.SHIFT).rightClickOn(getPane().getChildren().get(0)).release(KeyCode.SHIFT);

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
				bindAsEagerSingleton(TextSetter.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(EditingService.class, Mockito.mock(EditingService.class));
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		setter = injector.getInstance(TextSetter.class);
		Platform.runLater(() -> setter.initialize(null, null));
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testOneClickOnShapeSelectsIt() {
		new CompositeGUIVoidCommand(addRec, clickOnAddedFirstShape).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testClickOnCanvasUnselects() {
		new CompositeGUIVoidCommand(addRec, clickOnAddedFirstShape).execute();
		clickOn(canvas);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testTwoClicksOnShapeSelectsItOneTime() {
		new CompositeGUIVoidCommand(addRec, clickOnAddedFirstShape, clickOnAddedFirstShape).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testShiftClickOnShapeDeselectsIt() {
		new CompositeGUIVoidCommand(addRec, clickOnAddedFirstShape, shiftClickOnAddedRec).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testCtrlClickOnShapeAddsSelection() {
		new CompositeGUIVoidCommand(addRec, addRec2, clickOnAddedFirstShape, ctrlClickOnAddedRec2).execute();
		assertEquals(2, canvas.getDrawing().getSelection().size());
		assertNotSame(canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow(), canvas.getDrawing().getSelection().getShapeAt(1).orElseThrow());
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testTwoAddsAndShiftClickSelectsOneShape() {
		new CompositeGUIVoidCommand(addRec, addRec2, clickOnAddedFirstShape, ctrlClickOnAddedRec2,
			shiftClickOnAddedRec).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertNotSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testCtrlASelectAll() {
		new CompositeGUIVoidCommand(addRec, addRec2).execute();
		clickOn(canvas).press(KeyCode.CONTROL).type(KeyCode.A).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		assertEquals(2, canvas.getDrawing().getSelection().size());
	}

	@Test
	public void testClickArrowSelectsShape() {
		new CompositeGUIVoidCommand(addLines).execute();
		final ViewPolyline vlines = (ViewPolyline) getPane().getChildren().get(0);
		new CompositeGUIVoidCommand(() -> clickOn(vlines.lookup("#" + ViewArrow.ID))).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPolyline, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testClickPlotSelectsShape() {
		new CompositeGUIVoidCommand(addPlot).execute();
		final ViewPlot vplot = (ViewPlot) getPane().getChildren().get(0);
		new CompositeGUIVoidCommand(() -> clickOn(vplot)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPlot, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testClickInsideEmptyShapeDoesNotSelectIt() {
		new CompositeGUIVoidCommand(addLines).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		new CompositeGUIVoidCommand(() -> clickOn(canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d,
			canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d),
			waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testDnDInsideEmptyShapeDoesNotSelectIt() {
		new CompositeGUIVoidCommand(addLines).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 5, y + 5)).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testClickInsideBOundsButOutsideShapeDoesNotSelectIt() {
		new CompositeGUIVoidCommand(addLines).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMaxX() - 5;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + 5;
		new CompositeGUIVoidCommand(() -> clickOn(x, y)).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testDnDToSelectShapeSelectsIt() {
		new CompositeGUIVoidCommand(addLines).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 10),
			() -> drag(x + 50, y), () -> drag(x + 100, y)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPolyline, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testDnDToSelectShapeSelectsIt2() {
		new CompositeGUIVoidCommand(addLines).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 10d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 10),
			() -> drag(x + 60, y + 10), () -> drag(x + 70, y + 10)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPolyline, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testDnDToSelectRecOutToIn() {
		new CompositeGUIVoidCommand(addRec).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 100, y + 20)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testDnDToSelectRecInsideFill() {
		new CompositeGUIVoidCommand(addRec).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 100, y + 20)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testDnDToNotSelectRecInsideEmpty() {
		new CompositeGUIVoidCommand(addRec).execute();
		addedRec.setFillingStyle(FillingStyle.NONE);
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 60, y + 20)).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testDnDToSelectGroup() {
		new CompositeGUIVoidCommand(addGroup).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInLocal();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 60, y + 20)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedGroup, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testDnDToSelectGrid() {
		new CompositeGUIVoidCommand(addGrid).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 60, y + 20)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedGrid, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testDnDToSelectRecOutToInRotated() {
		new CompositeGUIVoidCommand(addRec).execute();
		addedRec.setRotationAngle(Math.PI / 8d);
		waitFXEvents.execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 60, y + 20)).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow());
	}

	@Test
	public void testEditTextDoesNotCreateANewOne() {
		when(editing.getCurrentChoice()).thenReturn(EditionChoice.TEXT);
		when(editing.createShapeInstance()).thenReturn(ShapeFactory.INST.createText());
		new CompositeGUIVoidCommand(addText).execute();
		final ViewText v = (ViewText) canvas.getViews().getChildren().get(0);
		doubleClickOn(v);
		waitFXEvents.execute();
		write("@bar bar");
		waitFXEvents.execute();
		type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertEquals(1, canvas.getDrawing().size());
		assertEquals(1, canvas.getViews().getChildren().size());
	}

	@Test
	public void testEditPlotDoesNotCreateANewOne() {
		when(editing.getCurrentChoice()).thenReturn(EditionChoice.PLOT);
		when(editing.createShapeInstance()).thenReturn(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0, 5, "x", false));
		final ShapePlotCustomiser plot = injector.getInstance(ShapePlotCustomiser.class);
		plot.maxXSpinner = Mockito.mock(Spinner.class);
		plot.minXSpinner = Mockito.mock(Spinner.class);
		plot.nbPtsSpinner = Mockito.mock(Spinner.class);
		when(plot.maxXSpinner.getValue()).thenReturn(10d);
		when(plot.minXSpinner.getValue()).thenReturn(0d);
		when(plot.nbPtsSpinner.getValue()).thenReturn(10);

		new CompositeGUIVoidCommand(addPlot).execute();
		final ViewPlot v = (ViewPlot) canvas.getViews().getChildren().get(0);
		doubleClickOn(v);
		waitFXEvents.execute();
		type(KeyCode.DELETE).write("x 2 mul");
		waitFXEvents.execute();
		type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertEquals(1, canvas.getDrawing().size());
		assertEquals(1, canvas.getViews().getChildren().size());
	}
}
