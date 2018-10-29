package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestTextSetter extends BaseTestCanvas {
	TextSetter setter;
	ShapePlotCustomiser plot;

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
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindAsEagerSingleton(TextSetter.class);
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		setter = injector.getInstance(TextSetter.class);
		plot = injector.getInstance(ShapePlotCustomiser.class);
		plot.maxXSpinner = Mockito.mock(Spinner.class);
		plot.minXSpinner = Mockito.mock(Spinner.class);
		plot.nbPtsSpinner = Mockito.mock(Spinner.class);
		when(plot.maxXSpinner.getValue()).thenReturn(10d);
		when(plot.minXSpinner.getValue()).thenReturn(0d);
		when(plot.nbPtsSpinner.getValue()).thenReturn(10);
		pencil.setActivated(true);
		canvas.getMagneticGrid().setMagnetic(false);
		Platform.runLater(() -> setter.initialize(null, null));
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testNotActivated() {
		pencil.setCurrentChoice(EditionChoice.DOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		assertFalse(setter.isActivated());
	}

	@Test
	public void testShowTextTextField() {
		pencil.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
		assertTrue(setter.getTextField().isVisible());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), setter.getTextField().getLayoutX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - setter.getTextField().getPrefHeight(), setter.getTextField().getLayoutY(), 1d);
		assertTrue(setter.getTextField().isFocused());
	}

	@Test
	public void testShowPlotTextField() {
		pencil.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
		assertTrue(setter.getTextField().isVisible());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), setter.getTextField().getLayoutX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - setter.getTextField().getPrefHeight(), setter.getTextField().getLayoutY(), 1d);
		assertTrue(setter.getTextField().isFocused());
	}

	@Test
	public void testTypeTextFieldOKAddShape() {
		pencil.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("foo bar").type(KeyCode.ENTER);
		waitFXEvents.execute();

		assertEquals(1, canvas.getDrawing().size());
		assertTrue(canvas.getDrawing().getShapeAt(0) instanceof IText);
		final IText sh = (IText) canvas.getDrawing().getShapeAt(0);
		assertEquals("foo bar", sh.getText());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testTypeEqFieldOKAddShape() {
		pencil.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("x 2 mul").type(KeyCode.ENTER);
		waitFXEvents.execute();

		assertEquals(1, canvas.getDrawing().size());
		assertTrue(canvas.getDrawing().getShapeAt(0) instanceof IPlot);
		final IPlot sh = (IPlot) canvas.getDrawing().getShapeAt(0);
		assertEquals("x 2 mul", sh.getPlotEquation());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() + setter.getTextField().getPrefHeight(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testHideTextFieldOnOK() {
		pencil.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("foo bar").type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertFalse(setter.isActivated());
	}

	@Test
	public void testHidePlotFieldOnOK() {
		pencil.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("x 2 mul").type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertFalse(setter.isActivated());
	}

	@Test
	public void testNotHidePlotFieldOnKO() {
		pencil.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("not a valid formula").type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
		assertFalse(setter.getTextField().isValidText());
	}

	@Test
	public void testNotHideTextFieldOnEmptyField() {
		pencil.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("foo").eraseText(3).type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
	}

	@Test
	public void testNotHidePlotFieldOnEmptyField() {
		pencil.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("x").eraseText(1).type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
	}

	@Test
	public void testEscapeText() {
		pencil.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("foo").type(KeyCode.ESCAPE);
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
		assertFalse(setter.isActivated());
	}

	@Test
	public void testEscapePlot() {
		pencil.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(10).write("x").type(KeyCode.ESCAPE);
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
		assertFalse(setter.isActivated());
	}

	@Test
	public void testNotCrashDeactivate() {
		setter.setActivated(true);
		setter.setActivated(false);
	}
}
