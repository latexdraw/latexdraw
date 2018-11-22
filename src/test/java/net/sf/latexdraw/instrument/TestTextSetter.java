package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTextSetter extends BaseTestCanvas {
	TextSetter setter;

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
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		final EditingService editing = injector.getInstance(EditingService.class);
		editing.getGroupParams().setPlotMinX(0d);
		editing.getGroupParams().setPlotMaxX(10d);
		editing.getGroupParams().setNbPlottedPoints(10);
		setter = injector.getInstance(TextSetter.class);
		pencil.setActivated(true);
		injector.getInstance(PreferencesService.class).magneticGridProperty().set(false);
		Platform.runLater(() -> setter.initialize(null, null));
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testNotActivated() {
		editing.setCurrentChoice(EditionChoice.DOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		assertFalse(setter.isActivated());
	}

	@Test
	public void testShowTextTextField() {
		editing.setCurrentChoice(EditionChoice.TEXT);
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
		editing.setCurrentChoice(EditionChoice.PLOT);
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
		editing.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("gridGapProp bar").type(KeyCode.ENTER);
		waitFXEvents.execute();

		assertEquals(1, canvas.getDrawing().size());
		assertTrue(canvas.getDrawing().getShapeAt(0).orElseThrow() instanceof Text);
		final Text sh = (Text) canvas.getDrawing().getShapeAt(0).orElseThrow();
		assertEquals("gridGapProp bar", sh.getText());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testTypeEqFieldOKAddShape() {
		editing.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("x 2 mul").type(KeyCode.ENTER);
		waitFXEvents.execute();

		assertEquals(1, canvas.getDrawing().size());
		assertTrue(canvas.getDrawing().getShapeAt(0).orElseThrow() instanceof Plot);
		final Plot sh = (Plot) canvas.getDrawing().getShapeAt(0).orElseThrow();
		assertEquals("x 2 mul", sh.getPlotEquation());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() + setter.getTextField().getPrefHeight(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testHideTextFieldOnOK() {
		editing.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("gridGapProp bar").type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertFalse(setter.isActivated());
	}

	@Test
	public void testHidePlotFieldOnOK() {
		editing.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("x 2 mul").type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertFalse(setter.isActivated());
	}

	@Test
	public void testNotHidePlotFieldOnKO() {
		editing.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("not a valid formula").type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
		assertFalse(setter.getTextField().isValidText());
	}

	@Test
	public void testNotHideTextFieldOnEmptyField() {
		editing.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("foo").eraseText(3).type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
	}

	@Test
	public void testNotHidePlotFieldOnEmptyField() {
		editing.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("x").eraseText(1).type(KeyCode.ENTER);
		waitFXEvents.execute();
		assertTrue(setter.isActivated());
	}

	@Test
	public void testEscapeText() {
		editing.setCurrentChoice(EditionChoice.TEXT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("gridGapProp").type(KeyCode.ESCAPE);
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
		assertFalse(setter.isActivated());
	}

	@Test
	public void testEscapePlot() {
		editing.setCurrentChoice(EditionChoice.PLOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();
		write("x").type(KeyCode.ESCAPE);
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
