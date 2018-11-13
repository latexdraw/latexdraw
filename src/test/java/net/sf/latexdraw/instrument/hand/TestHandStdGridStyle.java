package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.ShapeStdGridCustomiser;
import net.sf.latexdraw.instrument.TestStdGridStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.property.IStdGridProp;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandStdGridStyle extends TestStdGridStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeStdGridCustomiser.class);
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
	public void testControllerActivatedWhenSelectionGrid() {
		new CompositeGUIVoidCommand(selectionAddGrid, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testIncrLabelsSizeHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			labelsSizeS, incrementlabelsSizeS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getLabelsSize(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getLabelsSize()));
	}

	@Test
	public void testScrollLabelsSizeHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			labelsSizeS, scrolllabelsSizeS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getLabelsSize(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getLabelsSize()));
	}

	@Test
	public void testIncrxEndSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			xEndS, incrementxEndS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridEndX(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridEndX()));
	}

	@Test
	public void testScrollIncrxEndSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			xEndS, scrollxEndS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridEndX(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridEndX()));
	}

	@Test
	public void testIncryEndSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			yEndS, incrementyEndS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridEndY(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridEndY()));
	}

	@Test
	public void testScrollIncryEndSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			yEndS, scrollyEndS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridEndY(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridEndY()));
	}

	@Test
	public void testIncrxStartSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			xStartS, decrementxStartS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridStartX(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridStartX()));
	}

	@Test
	public void testScrollIncrxStartSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			xStartS, scrollxStartS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridStartX(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridStartX()));
	}

	@Test
	public void testIncryStartSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			yStartS, decrementyStartS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridStartY(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridStartY()));
	}

	@Test
	public void testScrollIncryStartSHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns),
			yStartS, scrollyStartS, Arrays.asList(
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridStartY(),
				() -> ((IStdGridProp) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridStartY()));
	}
}
