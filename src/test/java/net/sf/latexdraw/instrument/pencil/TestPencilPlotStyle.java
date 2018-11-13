package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
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
public class TestPencilPlotStyle extends TestPlotStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindAsEagerSingleton(ShapePlotCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectDOTSStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.DOTS);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCCURVEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CCURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectPOLYGONStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.POLYGON);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectECURVEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCURVEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		waitFXEvents.execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CURVE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLINEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.LINE);
		waitFXEvents.execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementnbPtsSpinnerPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), nbPtsSpinner,
			incrementnbPtsSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getNbPlottedPoints()));
	}

	@Test
	public void testIncrementminXSpinnerPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), minXSpinner,
			incrementminXSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getPlotMinX()));
	}

	@Test
	public void testIncrementmaxXSpinnerPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), maxXSpinner,
			incrementmaxXSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getPlotMaxX()));
	}

	@Test
	public void testIncrementxScaleSpinnerPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), xScaleSpinner,
			incrementxScaleSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getXScale()));
	}

	@Test
	public void testIncrementyScaleSpinnerPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), yScaleSpinner,
			incrementyScaleSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getYScale()));
	}

	@Test
	public void testSelectpolarCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		final boolean sel = polarCB.isSelected();
		clickpolarCB.execute();
		waitFXEvents.execute();
		assertEquals(!sel, ((Plot) editing.createShapeInstance()).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}
}
