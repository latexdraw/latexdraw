package net.sf.latexdraw.gui.hand;

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
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = gridWidth.getValue();
		incrementgridWidth.execute();
		assertEquals(gridWidth.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getGridWidth(), 0.0001);
		assertEquals(gridWidth.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getGridWidth(), 0.0001);
		assertNotEquals(val, gridWidth.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridWidthHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = subGridWidth.getValue();
		incrementsubGridWidth.execute();
		assertEquals(subGridWidth.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getSubGridWidth(), 0.0001);
		assertEquals(subGridWidth.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getSubGridWidth(), 0.0001);
		assertNotEquals(val, subGridWidth.getValue(), 0.0001);
	}

	@Test
	public void testIncrementgridDotsHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = gridDots.getValue();
		incrementgridDots.execute();
		assertEquals((int)gridDots.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getGridDots());
		assertEquals((int)gridDots.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getGridDots());
		assertNotEquals(val, gridDots.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridDotsHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = subGridDots.getValue();
		incrementsubGridDots.execute();
		assertEquals((int)subGridDots.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getSubGridDots());
		assertEquals((int)subGridDots.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getSubGridDots());
		assertNotEquals(val, subGridDots.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridDivHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = subGridDiv.getValue();
		incrementsubGridDiv.execute();
		assertEquals((int)subGridDiv.getValue(), ((IGrid)drawing.getSelection().getShapeAt(1)).getSubGridDiv());
		assertEquals((int)subGridDiv.getValue(), ((IGrid)drawing.getSelection().getShapeAt(2)).getSubGridDiv());
		assertNotEquals(val, subGridDiv.getValue(), 0.0001);
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
