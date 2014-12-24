package test.gui.hand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestAxesStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestHandAxeStyle extends TestAxesStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeAxesCustomiser.class).asEagerSingleton();
				bind(Hand.class).asEagerSingleton();
				bind(Pencil.class).toInstance(pencil);
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionAxes() {
		new CompositeGUIVoidCommand(selectionAddAxes, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotAxes() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testCheckShowOriginSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		boolean sel = showOrigin.isSelected();
		selectShowOrigin.execute();
		assertEquals(!sel, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).isShowOrigin());
		assertEquals(!sel, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).isShowOrigin());
	}

	@Test
	public void testIncrementDistYSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		double val = distLabelsY.getValue();
		incrementDistLabelY.execute();
		assertEquals(distLabelsY.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getDistLabelsY(), 0.0001);
		assertEquals(distLabelsY.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getDistLabelsY(), 0.0001);
		assertNotEquals(val, distLabelsY.getValue(), 0.0001);
	}

	@Test
	public void testIncrementDistXSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		double val = distLabelsX.getValue();
		incrementDistLabelX.execute();
		assertEquals(distLabelsX.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getDistLabelsX(), 0.0001);
		assertEquals(distLabelsX.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getDistLabelsX(), 0.0001);
		assertNotEquals(val, distLabelsX.getValue(), 0.0001);
	}

	@Test
	public void testIncrementLabelYSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		double val = incrLabelY.getValue();
		incrementLabelY.execute();
		assertEquals(incrLabelY.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getIncrementY(), 0.0001);
		assertEquals(incrLabelY.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getIncrementY(), 0.0001);
		assertNotEquals(val, incrLabelY.getValue(), 0.0001);
	}

	@Test
	public void testIncrementLabelXSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		double val = incrLabelX.getValue();
		incrementLabelX.execute();
		assertEquals(incrLabelX.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getIncrementX(), 0.0001);
		assertEquals(incrLabelX.getValue(), ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getIncrementX(), 0.0001);
		assertNotEquals(val, incrLabelX.getValue(), 0.0001);
	}

	@Test
	public void testSelectShowLabelsSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		IAxesProp.PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		IAxesProp.PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getLabelsDisplayed());
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		IAxesProp.PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		IAxesProp.PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getTicksDisplayed());
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		IAxesProp.TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		IAxesProp.TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getTicksStyle());
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		IAxesProp.AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		IAxesProp.AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getAxesStyle());
		assertEquals(newStyle, ((IAxesProp)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
