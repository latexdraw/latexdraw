package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
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

@Ignore
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
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindAsEagerSingleton(TextSetter.class);
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		setter = (TextSetter) injectorFactory.call(TextSetter.class);
		Platform.runLater(() -> setter.initialize(null, null));
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testOneClickOnShapeSelectsIt() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedFirstShape, waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testClickOnCanvasUnselects() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedFirstShape, waitFXEvents).execute();
		clickOn(canvas);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testTwoClicksOnShapeSelectsItOneTime() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedFirstShape, waitFXEvents, clickOnAddedFirstShape, waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testShiftClickOnShapeDeselectsIt() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedFirstShape, waitFXEvents, shiftClickOnAddedRec, waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testCtrlClickOnShapeAddsSelection() {
		new CompositeGUIVoidCommand(addRec, addRec2, waitFXEvents, clickOnAddedFirstShape, waitFXEvents, ctrlClickOnAddedRec2, waitFXEvents).execute();
		assertEquals(2, canvas.getDrawing().getSelection().size());
		assertNotSame(canvas.getDrawing().getSelection().getShapeAt(0), canvas.getDrawing().getSelection().getShapeAt(1));
	}

	@Ignore("Monocle does not capture key modifiers https://github.com/TestFX/Monocle/pull/48")
	@Test
	public void testTwoAddsAndShiftClickSelectsOneShape() {
		new CompositeGUIVoidCommand(addRec, addRec2, waitFXEvents, clickOnAddedFirstShape, waitFXEvents, ctrlClickOnAddedRec2, waitFXEvents,
			shiftClickOnAddedRec, waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertNotSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testClickArrowSelectsShape() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents).execute();
		final ViewPolyline vlines = (ViewPolyline) getPane().getChildren().get(0);
		new CompositeGUIVoidCommand(() -> clickOn(vlines.lookup("#"+ ViewArrow.ID)), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPolyline, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testClickPlotSelectsShape() {
		new CompositeGUIVoidCommand(addPlot, waitFXEvents).execute();
		final ViewPlot vplot = (ViewPlot) getPane().getChildren().get(0);
		new CompositeGUIVoidCommand(() -> clickOn(vplot), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPlot, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testClickInsideEmptyShapeDoesNotSelectIt() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		new CompositeGUIVoidCommand(() -> clickOn(canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d,
			canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d),
			waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testDnDInsideEmptyShapeDoesNotSelectIt() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 5, y + 5), waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testClickInsideBOundsButOutsideShapeDoesNotSelectIt() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMaxX() - 5;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + 5;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testDnDToSelectShapeSelectsIt() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 10),
			() -> drag(x + 50, y), () -> drag(x + 100, y), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPolyline, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testDnDToSelectShapeSelectsIt2() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 10d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 10),
			() -> drag(x + 60, y+10), () -> drag(x + 70, y + 10), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedPolyline, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testDnDToSelectRecOutToIn() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 100, y + 20), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testDnDToSelectRecInsideFill() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 100, y + 20), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testDnDToNotSelectRecInsideEmpty() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		addedRec.setFillingStyle(FillingStyle.NONE);
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() + bounds.getWidth() / 2d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY() + bounds.getHeight() / 2d;
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 60, y + 20), waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}

	@Test
	public void testDnDToSelectGroup() {
		new CompositeGUIVoidCommand(addGroup, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInLocal();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 60, y + 20), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedGroup, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testDnDToSelectGrid() {
		new CompositeGUIVoidCommand(addGrid, waitFXEvents).execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 50, y + 20), () -> drag(x + 60, y + 20), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedGrid, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testDnDToSelectRecOutToInRotated() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		addedRec.setRotationAngle(Math.PI / 8d);
		waitFXEvents.execute();
		final Bounds bounds = getPane().getChildren().get(0).getBoundsInParent();
		final double x = canvas.getScene().getWindow().getX() + Canvas.ORIGIN.getX() + bounds.getMinX() - 20d;
		final double y = canvas.getScene().getWindow().getY() + Canvas.ORIGIN.getY() + bounds.getMinY();
		new CompositeGUIVoidCommand(() -> clickOn(x, y), () -> drag(x + 10, y + 20),
			() -> drag(x + 60, y + 20), waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testEditTextDoesNotCreateANewOne() {
		when(pencil.getCurrentChoice()).thenReturn(EditionChoice.TEXT);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.INST.createText());
		new CompositeGUIVoidCommand(addText, waitFXEvents).execute();
		final ViewText v = (ViewText) canvas.getViews().getChildren().get(0);
		doubleClickOn(v).sleep(10).write("@bar bar").sleep(10).type(KeyCode.ENTER).sleep(SLEEP);
		assertEquals(1, canvas.getDrawing().size());
		assertEquals(1, canvas.getViews().getChildren().size());
	}

	@Test
	public void testEditPlotDoesNotCreateANewOne() {
		when(pencil.getCurrentChoice()).thenReturn(EditionChoice.PLOT);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0, 5, "x", false));
		ShapePlotCustomiser plot = (ShapePlotCustomiser) injectorFactory.call(ShapePlotCustomiser.class);
		plot.maxXSpinner = Mockito.mock(Spinner.class);
		plot.minXSpinner = Mockito.mock(Spinner.class);
		plot.nbPtsSpinner = Mockito.mock(Spinner.class);
		when(plot.maxXSpinner.getValue()).thenReturn(10d);
		when(plot.minXSpinner.getValue()).thenReturn(0d);
		when(plot.nbPtsSpinner.getValue()).thenReturn(10);

		new CompositeGUIVoidCommand(addPlot, waitFXEvents).execute();
		final ViewPlot v = (ViewPlot) canvas.getViews().getChildren().get(0);
		doubleClickOn(v).sleep(10).type(KeyCode.DELETE).write("x 2 mul").sleep(10).type(KeyCode.ENTER).sleep(SLEEP);
		assertEquals(1, canvas.getDrawing().size());
		assertEquals(1, canvas.getViews().getChildren().size());
	}
}
