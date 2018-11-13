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
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
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
		assertEquals(!sel, ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).isShowOrigin());
		assertEquals(!sel, ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).isShowOrigin());
	}

	@Test
	public void testIncrementDistYSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), distLabelsY,
			incrementDistLabelY, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getDistLabelsY(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getDistLabelsY()));
	}

	@Test
	public void testIncrementDistXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), distLabelsX,
			incrementDistLabelX, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getDistLabelsX(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getDistLabelsX()));
	}

	@Test
	public void testIncrementLabelYSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), incrLabelY,
			incrementLabelY, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getIncrementY(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getIncrementY()));
	}

	@Test
	public void testIncrementLabelXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns), incrLabelX,
			incrementLabelX, Arrays.asList(
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getIncrementX(),
			() ->  ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getIncrementX()));
	}

	@Test
	public void testSelectShowLabelsSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		waitFXEvents.execute();
		final PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getLabelsDisplayed());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		waitFXEvents.execute();
		final PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getTicksDisplayed());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		waitFXEvents.execute();
		final TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getTicksStyle());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddAxes, selectionAddRec, selectionAddAxes, updateIns).execute();
		final AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		waitFXEvents.execute();
		final AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(0).orElseThrow()).getAxesStyle());
		assertEquals(newStyle, ((AxesProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
