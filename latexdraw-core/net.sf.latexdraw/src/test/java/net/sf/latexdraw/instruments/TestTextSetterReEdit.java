package net.sf.latexdraw.instruments;

import javafx.application.Platform;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(Theories.class)
public class TestTextSetterReEdit extends BaseTestCanvas {
	TextSetter setter;
	ShapePlotCustomiser plot;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
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
		setter = (TextSetter) injectorFactory.call(TextSetter.class);
		plot = (ShapePlotCustomiser) injectorFactory.call(ShapePlotCustomiser.class);
		plot.maxXSpinner = Mockito.mock(Spinner.class);
		plot.minXSpinner = Mockito.mock(Spinner.class);
		plot.nbPtsSpinner = Mockito.mock(Spinner.class);
		when(plot.maxXSpinner.getValue()).thenReturn(10d);
		when(plot.minXSpinner.getValue()).thenReturn(0d);
		when(plot.nbPtsSpinner.getValue()).thenReturn(10);
		hand.setActivated(true);
		Platform.runLater(() -> setter.initialize(null, null));
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testEditText() {
		final IText txt = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+100, -Canvas.ORIGIN.getY()+200), "$foo");
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(txt));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(txt).get();
		doubleClickOn(view, MouseButton.PRIMARY).sleep(200L).type(KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals("abc$foo", txt.getText());
	}

	@Test
	public void testEditPlot() {
		final IPlot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 300),
			0, 5, "x 2.5 div", false);
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(plot));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(plot).get();
		doubleClickOn(view, MouseButton.PRIMARY).sleep(200L).type(KeyCode.END).eraseText(10).write("x x mul").type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals("x x mul", plot.getPlotEquation());
	}

	@Theory
	public void testEditBadPlot(@StringData(vals={"y", "x log", "x 0 div"}) final String badEq) {
		final IPlot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 100, -Canvas.ORIGIN.getY() + 300),
			0, 5, "x 2.5 div", false);
		Platform.runLater(() -> hand.canvas.getDrawing().addShape(plot));
		WaitForAsyncUtils.waitForFxEvents();
		final ViewShape<?> view = hand.canvas.getViewFromShape(plot).get();
		doubleClickOn(view, MouseButton.PRIMARY).sleep(200L).type(KeyCode.END).eraseText(10).write(badEq).type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(setter.getTextField().isVisible());
		assertTrue(setter.getTextField().getMessageField().isVisible());
		assertEquals("x 2.5 div", plot.getPlotEquation());
	}
}
