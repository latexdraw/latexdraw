package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestPlotStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePlotCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
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
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapePlotCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
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
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.DOTS);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IPlot)pencil.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCCURVEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CCURVE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IPlot)pencil.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectPOLYGONStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.POLYGON);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IPlot)pencil.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectECURVEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IPlot)pencil.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCURVEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CURVE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IPlot)pencil.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLINEStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.LINE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IPlot)pencil.createShapeInstance()).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementnbPtsSpinnerPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), nbPtsSpinner,
			incrementnbPtsSpinner, Collections.singletonList(() ->  ((IPlot) pencil.createShapeInstance()).getNbPlottedPoints()));
	}

	@Test
	public void testIncrementminXSpinnerPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), minXSpinner,
			incrementminXSpinner, Collections.singletonList(() ->  ((IPlot) pencil.createShapeInstance()).getPlotMinX()));
	}

	@Test
	public void testIncrementmaxXSpinnerPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), maxXSpinner,
			incrementmaxXSpinner, Collections.singletonList(() ->  ((IPlot) pencil.createShapeInstance()).getPlotMaxX()));
	}

	@Test
	public void testIncrementxScaleSpinnerPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), xScaleSpinner,
			incrementxScaleSpinner, Collections.singletonList(() ->  ((IPlot) pencil.createShapeInstance()).getXScale()));
	}

	@Test
	public void testIncrementyScaleSpinnerPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns), yScaleSpinner,
			incrementyScaleSpinner, Collections.singletonList(() ->  ((IPlot) pencil.createShapeInstance()).getYScale()));
	}

	@Test
	public void testSelectpolarCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		boolean sel = polarCB.isSelected();
		clickpolarCB.execute();
		assertEquals(!sel, ((IPlot) pencil.createShapeInstance()).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}
}
