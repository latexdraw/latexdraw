package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTextSetterReEdit extends BaseTestCanvas {
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
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		setter = injector.getInstance(TextSetter.class);
		final EditingService editing = injector.getInstance(EditingService.class);
		editing.getGroupParams().setPlotMinX(0d);
		editing.getGroupParams().setPlotMaxX(10d);
		editing.getGroupParams().setNbPlottedPoints(10);
		editing.setCurrentChoice(EditionChoice.HAND);
		hand.setActivated(true);
		Platform.runLater(() -> setter.initialize(null, null));
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testEditText() {
		final Text txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 200), "$gridGapProp");
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(txt));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(txt).orElseThrow();
		doubleClickOn(view, MouseButton.PRIMARY);
		waitFXEvents.execute();
		type(KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals("abc$gridGapProp", txt.getText());
	}

	@Test
	public void testEditPlot() {
		final Plot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 300),
			0, 2, "x 2.5 div", false);
		plot.setNbPlottedPoints(5);
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(plot));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(plot).get();
		doubleClickOn(view, MouseButton.PRIMARY);
		waitFXEvents.execute();
		type(KeyCode.END).eraseText(10).write("x x mul").type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals("x x mul", plot.getPlotEquation());
	}

	@Test
	public void testEditBadPlot1() {
		final Plot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 300),
			0, 3, "x 2.5 div", false);
		plot.setNbPlottedPoints(5);
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(plot));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(plot).get();
		doubleClickOn(view, MouseButton.PRIMARY);
		waitFXEvents.execute();
		type(KeyCode.END).eraseText(10).write("y").type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(setter.getTextField().isVisible());
		assertTrue(setter.getTextField().getMessageField().isVisible());
		assertEquals("x 2.5 div", plot.getPlotEquation());
	}

	@Test
	public void testEditBadPlotLog() {
		final Plot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 300),
			0, 3, "x 2.5 div", false);
		plot.setNbPlottedPoints(5);
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(plot));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(plot).get();
		doubleClickOn(view, MouseButton.PRIMARY);
		waitFXEvents.execute();
		type(KeyCode.END).eraseText(10).write("x log").type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(setter.getTextField().isVisible());
		assertTrue(setter.getTextField().getMessageField().isVisible());
		assertEquals("x 2.5 div", plot.getPlotEquation());
	}

	@Test
	public void testEditBadPlotDiv0() {
		final Plot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 300),
			0, 3, "x 2.5 div", false);
		plot.setNbPlottedPoints(5);
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(plot));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(plot).get();
		doubleClickOn(view, MouseButton.PRIMARY);
		waitFXEvents.execute();
		type(KeyCode.END).eraseText(10).write("x 0 div").type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(setter.getTextField().isVisible());
		assertTrue(setter.getTextField().getMessageField().isVisible());
		assertEquals("x 2.5 div", plot.getPlotEquation());
	}
}
