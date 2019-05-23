package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
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
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectDOTSStylePencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectplotStyleCB.execute(PlotStyle.DOTS)).execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCCURVEStylePencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectplotStyleCB.execute(PlotStyle.CCURVE)).execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectPOLYGONStylePencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectplotStyleCB.execute(PlotStyle.POLYGON)).execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectECURVEStylePencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectplotStyleCB.execute(PlotStyle.ECURVE)).execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCURVEStylePencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns, () -> selectplotStyleCB.execute(PlotStyle.ECURVE)).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectplotStyleCB.execute(PlotStyle.CURVE)).execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLINEStylePencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		final PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectplotStyleCB.execute(PlotStyle.LINE)).execute();
		final PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((Plot) editing.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementnbPtsSpinnerPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesPlot, updateIns), nbPtsSpinner,
			incrementnbPtsSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getNbPlottedPoints()));
	}

	@Test
	public void testIncrementminXSpinnerPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesPlot, updateIns), minXSpinner,
			incrementminXSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getPlotMinX()));
	}

	@Test
	public void testIncrementmaxXSpinnerPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesPlot, updateIns), maxXSpinner,
			incrementmaxXSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getPlotMaxX()));
	}

	@Test
	public void testIncrementxScaleSpinnerPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesPlot, updateIns), xScaleSpinner,
			incrementxScaleSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getXScale()));
	}

	@Test
	public void testIncrementyScaleSpinnerPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesPlot, updateIns), yScaleSpinner,
			incrementyScaleSpinner, Collections.singletonList(() ->  ((Plot) editing.createShapeInstance()).getYScale()));
	}

	@Test
	public void testSelectpolarCBPencil() {
		Cmds.of(activatePencil, pencilCreatesPlot, updateIns).execute();
		final boolean sel = polarCB.isSelected();
		Cmds.of(clickpolarCB).execute();
		assertEquals(!sel, ((Plot) editing.createShapeInstance()).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}
}
