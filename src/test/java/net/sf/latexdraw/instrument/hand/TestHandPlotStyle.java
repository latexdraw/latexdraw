package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapePlotCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestPlotStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapePlotCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionGrid() {
		Cmds.of(selectionAddPlot, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		Cmds.of(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectDOTSStyleHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.DOTS);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCCURVEStyleHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CCURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectPOLYGONStyleHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.POLYGON);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectECURVEStyleHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCURVEStyleHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		waitFXEvents.execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLINEStyleHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.LINE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementnbPtsSpinnerHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), nbPtsSpinner,
			incrementnbPtsSpinner, Arrays.asList(
				() -> ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getNbPlottedPoints(),
				() -> ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getNbPlottedPoints()
			));
	}

	@Test
	public void testIncrementminXSpinnerHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), minXSpinner,
			incrementminXSpinner, Arrays.asList(
			() ->  ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotMinX(),
			() ->  ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotMinX()));
	}

	@Test
	public void testIncrementmaxXSpinnerHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), maxXSpinner,
			incrementmaxXSpinner, Arrays.asList(
			() ->  ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getPlotMaxX(),
			() ->  ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getPlotMaxX()));
	}

	@Test
	public void testIncrementxScaleSpinnerHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), xScaleSpinner,
			incrementxScaleSpinner, Arrays.asList(
			() ->  ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getXScale(),
			() ->  ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getXScale()));
	}

	@Test
	public void testIncrementyScaleSpinnerHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns), yScaleSpinner,
			incrementyScaleSpinner, Arrays.asList(
			() ->  ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).getYScale(),
			() ->  ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).getYScale()));
	}

	@Test
	public void testSelectpolarCBHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		final boolean sel = polarCB.isSelected();
		Cmds.of(clickpolarCB).execute();
		assertEquals(polarCB.isSelected(), ((Plot) drawing.getSelection().getShapeAt(1).orElseThrow()).isPolar());
		assertEquals(polarCB.isSelected(), ((Plot) drawing.getSelection().getShapeAt(2).orElseThrow()).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}

	@Test
	public void testCrashInvalidFunctionValueMinX() {
		Cmds.of(activateHand, selectionAddPlot, updateIns,
			() -> ((Plot) drawing.getSelection().getShapeAt(0).orElseThrow()).setPlotEquation("x log"),
			() -> minXSpinner.getEditor().setText("0"),
			() -> minXSpinner.getEditor().commitValue()).execute();
		assertNotEquals(0d, minXSpinner.getValue(), 0.00001);
	}

	@Test
	public void testCrashInvalidFunctionValueMaxX() {
		Cmds.of(activateHand, selectionAddPlot, updateIns,
			() -> ((Plot) drawing.getSelection().getShapeAt(0).orElseThrow()).setPlotEquation("2 x div"),
			() -> minXSpinner.getEditor().setText("-1"),
			() -> minXSpinner.getEditor().commitValue(),
			() -> maxXSpinner.getEditor().setText("0"),
			() -> maxXSpinner.getEditor().commitValue()).execute();
		assertNotEquals(-1d, minXSpinner.getValue(), 0.00001);
		assertNotEquals(0d, maxXSpinner.getValue(), 0.00001);
	}

	@Test
	public void testCrashInvalidFunctionPolarToCart() {
		Cmds.of(activateHand, selectionAddPlot, clickpolarCB, updateIns,
			() -> ((Plot) drawing.getSelection().getShapeAt(0).orElseThrow()).setPlotEquation("2 x div"),
			() -> minXSpinner.getEditor().setText("-1"),
			() -> minXSpinner.getEditor().commitValue(),
			() -> maxXSpinner.getEditor().setText("0"),
			() -> maxXSpinner.getEditor().commitValue(),
			clickpolarCB).execute();
	}
}
