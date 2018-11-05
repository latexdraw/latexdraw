package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeAxesCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestAxesStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.property.AxesProp;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;
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
				bindToSupplier(Stage.class, () -> stage);
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
		final boolean sel = showOrigin.isSelected();
		selectShowOrigin.execute();
		assertEquals(!sel, ((AxesProp) drawing.getSelection().getShapeAt(0)).isShowOrigin());
		assertEquals(!sel, ((AxesProp) drawing.getSelection().getShapeAt(2)).isShowOrigin());
	}

	@Test
	public void testIncrementDistYSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), distLabelsY,
			incrementDistLabelY, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0)).getDistLabelsY(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2)).getDistLabelsY()));
	}

	@Test
	public void testIncrementDistXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), distLabelsX,
			incrementDistLabelX, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0)).getDistLabelsX(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2)).getDistLabelsX()));
	}

	@Test
	public void testIncrementLabelYSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), incrLabelY,
			incrementLabelY, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0)).getIncrementY(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2)).getIncrementY()));
	}

	@Test
	public void testIncrementLabelXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), incrLabelX,
			incrementLabelX, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0)).getIncrementX(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2)).getIncrementX()));
	}

	@Test
	public void testSelectShowLabelsSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		waitFXEvents.execute();
		final PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0)).getLabelsDisplayed());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2)).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		waitFXEvents.execute();
		final PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0)).getTicksDisplayed());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2)).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		waitFXEvents.execute();
		final TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0)).getTicksStyle());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2)).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		waitFXEvents.execute();
		final AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0)).getAxesStyle());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2)).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
