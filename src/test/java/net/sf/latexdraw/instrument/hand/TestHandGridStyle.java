package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeGridCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestGridStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.Grid;
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
public class TestHandGridStyle extends TestGridStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapeGridCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionGrid() {
		Cmds.of(selectionAddGrid, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		Cmds.of(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testPickcolourLabelsColourHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		final Color col = colourLabels.getValue();
		Cmds.of(pickcolourLabels).execute();
		assertEquals(colourLabels.getValue(), ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridLabelsColour().toJFX());
		assertEquals(colourLabels.getValue(), ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridLabelsColour().toJFX());
		assertNotEquals(col, colourLabels.getValue());
	}

	@Test
	public void testPickLineColourHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		final Color col = colourSubGrid.getValue();
		Cmds.of(pickcolourSubGrid).execute();
		assertEquals(colourSubGrid.getValue(), ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getSubGridColour().toJFX());
		assertEquals(colourSubGrid.getValue(), ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getSubGridColour().toJFX());
		assertNotEquals(col, colourSubGrid.getValue());
	}

	@Test
	public void testIncrementgridWidthHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), gridWidth,
			incrementgridWidth, Arrays.asList(
			() ->  ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridWidth(),
			() ->  ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridWidth()));
	}

	@Test
	public void testIncrementsubGridWidthHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), subGridWidth,
			incrementsubGridWidth, Arrays.asList(
			() ->  ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getSubGridWidth(),
			() ->  ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getSubGridWidth()));
	}

	@Test
	public void testIncrementgridDotsHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), gridDots,
			incrementgridDots, Arrays.asList(
			() ->  ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getGridDots(),
			() ->  ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getGridDots()));
	}

	@Test
	public void testIncrementsubGridDotsHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), subGridDots,
			incrementsubGridDots, Arrays.asList(
			() ->  ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getSubGridDots(),
			() ->  ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getSubGridDots()));
	}

	@Test
	public void testIncrementsubGridDivHand() {
		doTestSpinner(Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), subGridDiv,
			incrementsubGridDiv, Arrays.asList(
			() ->  ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).getSubGridDiv(),
			() ->  ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).getSubGridDiv()));
	}

	@Test
	public void testSelectlabelsYInvertedCBHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		final boolean sel = labelsYInvertedCB.isSelected();
		Cmds.of(clicklabelsYInvertedCB).execute();
		assertEquals(sel, ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).isXLabelSouth());
		assertEquals(sel, ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).isXLabelSouth());
		assertNotEquals(sel, labelsYInvertedCB.isSelected());
	}

	@Test
	public void testSelectlabelsXInvertedCBHand() {
		Cmds.of(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		final boolean sel = labelsXInvertedCB.isSelected();
		Cmds.of(clicklabelsXInvertedCB).execute();
		assertEquals(sel, ((Grid) drawing.getSelection().getShapeAt(1).orElseThrow()).isYLabelWest());
		assertEquals(sel, ((Grid) drawing.getSelection().getShapeAt(2).orElseThrow()).isYLabelWest());
		assertNotEquals(sel, labelsXInvertedCB.isSelected());
	}
}
