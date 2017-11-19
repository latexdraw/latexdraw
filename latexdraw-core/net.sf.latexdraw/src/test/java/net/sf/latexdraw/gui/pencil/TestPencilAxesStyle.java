package net.sf.latexdraw.gui.pencil;

import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestAxesStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
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
public class TestPencilAxesStyle extends TestAxesStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeAxesCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testCheckShowOriginPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		boolean sel = showOrigin.isSelected();
		selectShowOrigin.execute();
		assertEquals(!sel, ((IAxesProp)pencil.createShapeInstance()).isShowOrigin());
	}

	@Test
	public void testIncrementDistYPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = distLabelsY.getValue();
		incrementDistLabelY.execute();
		assertEquals(distLabelsY.getValue(), ((IAxesProp)pencil.createShapeInstance()).getDistLabelsY(), 0.0001);
		assertNotEquals(val, distLabelsY.getValue(), 0.0001);
	}

	@Test
	public void testIncrementDistXPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = distLabelsX.getValue();
		incrementDistLabelX.execute();
		assertEquals(distLabelsX.getValue(), ((IAxesProp)pencil.createShapeInstance()).getDistLabelsX(), 0.0001);
		assertNotEquals(val, distLabelsX.getValue(), 0.0001);
	}

	@Test
	public void testIncrementLabelYPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = incrLabelY.getValue();
		incrementLabelY.execute();
		assertEquals(incrLabelY.getValue(), ((IAxesProp)pencil.createShapeInstance()).getIncrementY(), 0.0001);
		assertNotEquals(val, incrLabelY.getValue(), 0.0001);
	}

	@Test
	public void testIncrementLabelXPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		double val = incrLabelX.getValue();
		incrementLabelX.execute();
		assertEquals(incrLabelX.getValue(), ((IAxesProp)pencil.createShapeInstance()).getIncrementX(), 0.0001);
		assertNotEquals(val, incrLabelX.getValue(), 0.0001);
	}

	@Test
	public void testSelectShowLabelsPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
