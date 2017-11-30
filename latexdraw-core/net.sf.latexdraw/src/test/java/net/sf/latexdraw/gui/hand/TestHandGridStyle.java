package net.sf.latexdraw.gui.hand;

import java.util.Arrays;
import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestGridStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
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
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeGridCustomiser.class);
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
	public void testControllerActivatedWhenSelectionGrid() {
		new CompositeGUIVoidCommand(selectionAddGrid, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
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
	public void testPickcolourLabelsColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		Color col = colourLabels.getValue();
		pickcolourLabels.execute();
		assertEquals(colourLabels.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getGridLabelsColour().toJFX());
		assertEquals(colourLabels.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getGridLabelsColour().toJFX());
		assertNotEquals(col, colourLabels.getValue());
	}

	@Test
	public void testPickLineColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		Color col = colourSubGrid.getValue();
		pickcolourSubGrid.execute();
		assertEquals(colourSubGrid.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getSubGridColour().toJFX());
		assertEquals(colourSubGrid.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getSubGridColour().toJFX());
		assertNotEquals(col, colourSubGrid.getValue());
	}

	@Test
	public void testIncrementgridWidthHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), gridWidth,
			incrementgridWidth, Arrays.asList(
			() ->  ((IGrid) drawing.getSelection().getShapeAt(1)).getGridWidth(),
			() ->  ((IGrid) drawing.getSelection().getShapeAt(2)).getGridWidth()));
	}

	@Test
	public void testIncrementsubGridWidthHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), subGridWidth,
			incrementsubGridWidth, Arrays.asList(
			() ->  ((IGrid) drawing.getSelection().getShapeAt(1)).getSubGridWidth(),
			() ->  ((IGrid) drawing.getSelection().getShapeAt(2)).getSubGridWidth()));
	}

	@Test
	public void testIncrementgridDotsHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), gridDots,
			incrementgridDots, Arrays.asList(
			() ->  ((IGrid) drawing.getSelection().getShapeAt(1)).getGridDots(),
			() ->  ((IGrid) drawing.getSelection().getShapeAt(2)).getGridDots()));
	}

	@Test
	public void testIncrementsubGridDotsHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), subGridDots,
			incrementsubGridDots, Arrays.asList(
			() ->  ((IGrid) drawing.getSelection().getShapeAt(1)).getSubGridDots(),
			() ->  ((IGrid) drawing.getSelection().getShapeAt(2)).getSubGridDots()));
	}

	@Test
	public void testIncrementsubGridDivHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns), subGridDiv,
			incrementsubGridDiv, Arrays.asList(
			() ->  ((IGrid) drawing.getSelection().getShapeAt(1)).getSubGridDiv(),
			() ->  ((IGrid) drawing.getSelection().getShapeAt(2)).getSubGridDiv()));
	}

	@Test
	public void testSelectlabelsYInvertedCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		final boolean sel = labelsYInvertedCB.isSelected();
		clicklabelsYInvertedCB.execute();
		assertEquals(sel, ((IGrid) drawing.getSelection().getShapeAt(1)).isXLabelSouth());
		assertEquals(sel, ((IGrid) drawing.getSelection().getShapeAt(2)).isXLabelSouth());
		assertNotEquals(sel, labelsYInvertedCB.isSelected());
	}

	@Test
	public void testSelectlabelsXInvertedCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		final boolean sel = labelsXInvertedCB.isSelected();
		clicklabelsXInvertedCB.execute();
		assertEquals(sel, ((IGrid) drawing.getSelection().getShapeAt(1)).isYLabelWest());
		assertEquals(sel, ((IGrid) drawing.getSelection().getShapeAt(2)).isYLabelWest());
		assertNotEquals(sel, labelsXInvertedCB.isSelected());
	}
}
