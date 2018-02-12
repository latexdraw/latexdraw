package net.sf.latexdraw.instruments.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestAxesStyleGUI;
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
public class TestHandAxeStyle extends TestAxesStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeAxesCustomiser.class);
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
		assertEquals(!sel, ((IAxesProp)drawing.getSelection().getShapeAt(0)).isShowOrigin());
		assertEquals(!sel, ((IAxesProp)drawing.getSelection().getShapeAt(2)).isShowOrigin());
	}

	@Test
	public void testIncrementDistYSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), distLabelsY,
			incrementDistLabelY, Arrays.asList(
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(0)).getDistLabelsY(),
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(2)).getDistLabelsY()));
	}

	@Test
	public void testIncrementDistXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), distLabelsX,
			incrementDistLabelX, Arrays.asList(
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(0)).getDistLabelsX(),
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(2)).getDistLabelsX()));
	}

	@Test
	public void testIncrementLabelYSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), incrLabelY,
			incrementLabelY, Arrays.asList(
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(0)).getIncrementY(),
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(2)).getIncrementY()));
	}

	@Test
	public void testIncrementLabelXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), incrLabelX,
			incrementLabelX, Arrays.asList(
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(0)).getIncrementX(),
			() ->  ((IAxesProp) drawing.getSelection().getShapeAt(2)).getIncrementX()));
	}

	@Test
	public void testSelectShowLabelsSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(0)).getLabelsDisplayed());
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(2)).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(0)).getTicksDisplayed());
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(2)).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(0)).getTicksStyle());
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(2)).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(0)).getAxesStyle());
		assertEquals(newStyle, ((IAxesProp)drawing.getSelection().getShapeAt(2)).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
