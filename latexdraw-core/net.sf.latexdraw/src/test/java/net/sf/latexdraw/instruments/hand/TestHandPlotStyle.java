package net.sf.latexdraw.instruments.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.application.Platform;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePlotCustomiser;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestPlotStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandPlotStyle extends TestPlotStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapePlotCustomiser.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Pencil.class, pencil);
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionGrid() {
		new CompositeGUIVoidCommand(selectionAddPlot, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectDOTSStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.DOTS);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCCURVEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CCURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectPOLYGONStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.POLYGON);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectECURVEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCURVEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		waitFXEvents.execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLINEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.LINE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementnbPtsSpinnerHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), nbPtsSpinner,
			incrementnbPtsSpinner, Arrays.asList(
				() -> ((IPlot)drawing.getSelection().getShapeAt(1)).getNbPlottedPoints(),
				() -> ((IPlot)drawing.getSelection().getShapeAt(2)).getNbPlottedPoints()
			));
	}

	@Test
	public void testIncrementminXSpinnerHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), minXSpinner,
			incrementminXSpinner, Arrays.asList(
			() ->  ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotMinX(),
			() ->  ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotMinX()));
	}

	@Test
	public void testIncrementmaxXSpinnerHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), maxXSpinner,
			incrementmaxXSpinner, Arrays.asList(
			() ->  ((IPlot) drawing.getSelection().getShapeAt(1)).getPlotMaxX(),
			() ->  ((IPlot) drawing.getSelection().getShapeAt(2)).getPlotMaxX()));
	}

	@Test
	public void testIncrementxScaleSpinnerHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), xScaleSpinner,
			incrementxScaleSpinner, Arrays.asList(
			() ->  ((IPlot) drawing.getSelection().getShapeAt(1)).getXScale(),
			() ->  ((IPlot) drawing.getSelection().getShapeAt(2)).getXScale()));
	}

	@Test
	public void testIncrementyScaleSpinnerHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), yScaleSpinner,
			incrementyScaleSpinner, Arrays.asList(
			() ->  ((IPlot) drawing.getSelection().getShapeAt(1)).getYScale(),
			() ->  ((IPlot) drawing.getSelection().getShapeAt(2)).getYScale()));
	}

	@Test
	public void testSelectpolarCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final boolean sel = polarCB.isSelected();
		clickpolarCB.execute();
		waitFXEvents.execute();
		assertEquals(polarCB.isSelected(), ((IPlot) drawing.getSelection().getShapeAt(1)).isPolar());
		assertEquals(polarCB.isSelected(), ((IPlot) drawing.getSelection().getShapeAt(2)).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}

	@Test
	public void testCrashInvalidFunctionValueMinX() {
		new CompositeGUIVoidCommand(activateHand, selectionAddPlot, updateIns).execute();
		Platform.runLater(() -> ((IPlot) drawing.getSelection().getShapeAt(0)).setPlotEquation("x log"));
		WaitForAsyncUtils.waitForFxEvents();
		minXSpinner.getEditor().setText("0");
		minXSpinner.getEditor().commitValue();
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(0d, minXSpinner.getValue(), 0.00001);
	}

	@Test
	public void testCrashInvalidFunctionValueMaxX() {
		new CompositeGUIVoidCommand(activateHand, selectionAddPlot, updateIns).execute();
		Platform.runLater(() -> ((IPlot) drawing.getSelection().getShapeAt(0)).setPlotEquation("2 x div"));
		WaitForAsyncUtils.waitForFxEvents();
		minXSpinner.getEditor().setText("-1");
		minXSpinner.getEditor().commitValue();
		WaitForAsyncUtils.waitForFxEvents();
		maxXSpinner.getEditor().setText("0");
		maxXSpinner.getEditor().commitValue();
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(-1d, minXSpinner.getValue(), 0.00001);
		assertNotEquals(0d, maxXSpinner.getValue(), 0.00001);
	}

	@Test
	public void testCrashInvalidFunctionPolarToCart() {
		new CompositeGUIVoidCommand(activateHand, selectionAddPlot, clickpolarCB, updateIns).execute();
		Platform.runLater(() -> ((IPlot) drawing.getSelection().getShapeAt(0)).setPlotEquation("2 x div"));
		WaitForAsyncUtils.waitForFxEvents();
		minXSpinner.getEditor().setText("-1");
		minXSpinner.getEditor().commitValue();
		WaitForAsyncUtils.waitForFxEvents();
		maxXSpinner.getEditor().setText("0");
		maxXSpinner.getEditor().commitValue();
		WaitForAsyncUtils.waitForFxEvents();
		clickpolarCB.execute();
		WaitForAsyncUtils.waitForFxEvents();
	}
}
