package test.gui.pencil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePlotCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestPlotStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilPlotStyle extends TestPlotStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapePlotCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
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
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
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
	public void testincrementnbPtsSpinnerPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		int val = nbPtsSpinner.getValue();
		incrementnbPtsSpinner.execute();
		assertEquals((int)nbPtsSpinner.getValue(), ((IPlot)pencil.createShapeInstance()).getNbPlottedPoints());
		assertNotEquals(val, nbPtsSpinner.getValue(), 0.0001);
	}

	@Test
	public void testincrementminXSpinnerPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		double val = minXSpinner.getValue();
		incrementminXSpinner.execute();
		assertEquals(minXSpinner.getValue(), ((IPlot)pencil.createShapeInstance()).getPlotMinX(), 0.0001);
		assertNotEquals(val, minXSpinner.getValue(), 0.0001);
	}

	@Test
	public void testincrementmaxXSpinnerPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		double val = maxXSpinner.getValue();
		incrementmaxXSpinner.execute();
		assertEquals(maxXSpinner.getValue(), ((IPlot)pencil.createShapeInstance()).getPlotMaxX(), 0.0001);
		assertNotEquals(val, maxXSpinner.getValue(), 0.0001);
	}

	@Test
	public void testincrementxScaleSpinnerPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		double val = xScaleSpinner.getValue();
		incrementxScaleSpinner.execute();
		assertEquals(xScaleSpinner.getValue(), ((IPlot)pencil.createShapeInstance()).getXScale(), 0.0001);
		assertNotEquals(val, xScaleSpinner.getValue(), 0.0001);
	}

	@Test
	public void testincrementyScaleSpinnerPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		double val = yScaleSpinner.getValue();
		incrementyScaleSpinner.execute();
		assertEquals(yScaleSpinner.getValue(), ((IPlot)pencil.createShapeInstance()).getYScale(), 0.0001);
		assertNotEquals(val, yScaleSpinner.getValue(), 0.0001);
	}

	@Test
	public void testSelectpolarCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPlot, updateIns).execute();
		boolean sel = polarCB.isSelected();
		clickpolarCB.execute();
		assertEquals(!sel, ((IPlot)pencil.createShapeInstance()).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}
}
