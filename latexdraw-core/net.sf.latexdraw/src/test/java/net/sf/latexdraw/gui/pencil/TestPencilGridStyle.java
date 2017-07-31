package net.sf.latexdraw.gui.pencil;

import com.google.inject.AbstractModule;
import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropModule;
import net.sf.latexdraw.gui.TestGridStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilGridStyle extends TestGridStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeGridCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testPickcolourLabelsColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		Color col = colourLabels.getValue();
		pickcolourLabels.execute();
		assertEquals(colourLabels.getValue(), ((IGrid)pencil.createShapeInstance()).getGridLabelsColour().toJFX());
		assertNotEquals(col, colourLabels.getValue());
	}

	@Test
	public void testPickLineColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		Color col = colourSubGrid.getValue();
		pickcolourSubGrid.execute();
		assertEquals(colourSubGrid.getValue(), ((IGrid)pencil.createShapeInstance()).getSubGridColour().toJFX());
		assertNotEquals(col, colourSubGrid.getValue());
	}

	@Test
	public void testIncrementgridWidthPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = gridWidth.getValue();
		incrementgridWidth.execute();
		assertEquals(gridWidth.getValue(), ((IGrid)pencil.createShapeInstance()).getGridWidth(), 0.0001);
		assertNotEquals(val, gridWidth.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridWidthPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = subGridWidth.getValue();
		incrementsubGridWidth.execute();
		assertEquals(subGridWidth.getValue(), ((IGrid)pencil.createShapeInstance()).getSubGridWidth(), 0.0001);
		assertNotEquals(val, subGridWidth.getValue(), 0.0001);
	}

	@Test
	public void testIncrementgridDotsPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = gridDots.getValue();
		incrementgridDots.execute();
		assertEquals((int)gridDots.getValue(), ((IGrid)pencil.createShapeInstance()).getGridDots());
		assertNotEquals(val, gridDots.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridDotsPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = subGridDots.getValue();
		incrementsubGridDots.execute();
		assertEquals((int)subGridDots.getValue(), ((IGrid)pencil.createShapeInstance()).getSubGridDots());
		assertNotEquals(val, subGridDots.getValue(), 0.0001);
	}

	@Test
	public void testIncrementsubGridDivPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = subGridDiv.getValue();
		incrementsubGridDiv.execute();
		assertEquals((int)subGridDiv.getValue(), ((IGrid)pencil.createShapeInstance()).getSubGridDiv());
		assertNotEquals(val, subGridDiv.getValue(), 0.0001);
	}

	@Test
	public void testSelectlabelsYInvertedCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		boolean sel = labelsYInvertedCB.isSelected();
		clicklabelsYInvertedCB.execute();
		assertEquals(sel, ((IGrid) pencil.createShapeInstance()).isXLabelSouth());
		assertNotEquals(sel, labelsYInvertedCB.isSelected());
	}

	@Test
	public void testSelectlabelsXInvertedCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		boolean sel = labelsXInvertedCB.isSelected();
		clicklabelsXInvertedCB.execute();
		assertEquals(sel, ((IGrid) pencil.createShapeInstance()).isYLabelWest());
		assertNotEquals(sel, labelsXInvertedCB.isSelected());
	}
}
