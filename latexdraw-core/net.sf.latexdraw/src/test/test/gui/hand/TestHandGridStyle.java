package test.gui.hand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import javafx.scene.paint.Color;
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestGridHandStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestHandGridStyle extends TestGridHandStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeGridCustomiser.class).asEagerSingleton();
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
		assertEquals(colourLabels.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getGridLabelsColour().toJFX());
		assertEquals(colourLabels.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getGridLabelsColour().toJFX());
		assertNotEquals(col, colourLabels.getValue());
	}

	@Test
	public void testPickLineColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		Color col = colourSubGrid.getValue();
		pickcolourSubGrid.execute();
		assertEquals(colourSubGrid.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getSubGridColour().toJFX());
		assertEquals(colourSubGrid.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getSubGridColour().toJFX());
		assertNotEquals(col, colourSubGrid.getValue());
	}

	@Test
	public void testIncrementgridWidthHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = gridWidth.getValue();
		incrementgridWidth.execute();
		assertEquals(gridWidth.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getGridWidth(), 0.0001);
		assertEquals(gridWidth.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getGridWidth(), 0.0001);
		assertNotEquals(val, gridWidth.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridWidthHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = subGridWidth.getValue();
		incrementsubGridWidth.execute();
		assertEquals(subGridWidth.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getSubGridWidth(), 0.0001);
		assertEquals(subGridWidth.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getSubGridWidth(), 0.0001);
		assertNotEquals(val, subGridWidth.getValue(), 0.0001);
	}

	@Test
	public void testIncrementgridDotsHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = gridDots.getValue();
		incrementgridDots.execute();
		assertEquals((int)gridDots.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getGridDots());
		assertEquals((int)gridDots.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getGridDots());
		assertNotEquals(val, gridDots.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridDotsHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = subGridDots.getValue();
		incrementsubGridDots.execute();
		assertEquals((int)subGridDots.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getSubGridDots());
		assertEquals((int)subGridDots.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getSubGridDots());
		assertNotEquals(val, subGridDots.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridDivHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		double val = subGridDiv.getValue();
		incrementsubGridDiv.execute();
		assertEquals((int)subGridDiv.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).getSubGridDiv());
		assertEquals((int)subGridDiv.getValue(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getSubGridDiv());
		assertNotEquals(val, subGridDiv.getValue(), 0.0001);
	}

	@Test
	public void testSelectlabelsYInvertedCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		boolean sel = labelsYInvertedCB.isSelected();
		clicklabelsYInvertedCB.execute();
		assertEquals(labelsYInvertedCB.isSelected(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).isYLabelWest());
		assertEquals(labelsYInvertedCB.isSelected(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).isYLabelWest());
		assertNotEquals(sel, labelsYInvertedCB.isSelected());
	}

	@Test
	public void testSelectlabelsXInvertedCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddGrid, updateIns).execute();
		boolean sel = labelsXInvertedCB.isSelected();
		clicklabelsXInvertedCB.execute();
		assertEquals(labelsXInvertedCB.isSelected(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(1)).isXLabelSouth());
		assertEquals(labelsXInvertedCB.isSelected(), ((IGrid)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).isXLabelSouth());
		assertNotEquals(sel, labelsXInvertedCB.isSelected());
	}
}
