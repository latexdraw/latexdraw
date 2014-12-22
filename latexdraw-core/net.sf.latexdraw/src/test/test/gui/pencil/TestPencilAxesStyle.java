package test.gui.pencil;

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

import test.gui.CompositeGUICommand;
import test.gui.ShapePropModule;
import test.gui.TestAxesStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilAxesStyle extends TestAxesStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeAxesCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testCheckShowOriginPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		boolean sel = showOrigin.isSelected();
		selectShowOrigin.execute();
		assertEquals(!sel, ((IAxesProp)pencil.createShapeInstance()).isShowOrigin());
	}

	@Test
	public void testIncrementDistYPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = distLabelsY.getValue();
		incrementDistLabelY.execute();
		assertEquals(distLabelsY.getValue(), ((IAxesProp)pencil.createShapeInstance()).getDistLabelsY(), 0.0001);
		assertNotEquals(val, distLabelsY.getValue(), 0.0001);
	}

	@Test
	public void testIncrementDistXPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = distLabelsX.getValue();
		incrementDistLabelX.execute();
		assertEquals(distLabelsX.getValue(), ((IAxesProp)pencil.createShapeInstance()).getDistLabelsX(), 0.0001);
		assertNotEquals(val, distLabelsX.getValue(), 0.0001);
	}

	@Test
	public void testIncrementLabelYPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = incrLabelY.getValue();
		incrementLabelY.execute();
		assertEquals(incrLabelY.getValue(), ((IAxesProp)pencil.createShapeInstance()).getIncrementY(), 0.0001);
		assertNotEquals(val, incrLabelY.getValue(), 0.0001);
	}

	@Test
	public void testIncrementLabelXPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = incrLabelX.getValue();
		incrementLabelX.execute();
		assertEquals(incrLabelX.getValue(), ((IAxesProp)pencil.createShapeInstance()).getIncrementX(), 0.0001);
		assertNotEquals(val, incrLabelX.getValue(), 0.0001);
	}

	@Test
	public void testSelectShowLabelsPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		IAxesProp.PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		IAxesProp.PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		IAxesProp.PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		IAxesProp.PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStylePencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		IAxesProp.TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		IAxesProp.TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		IAxesProp.AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		IAxesProp.AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
