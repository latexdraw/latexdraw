package test.gui.pencil;

import static org.junit.Assert.*;
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
public class TestPencilGridStyle extends TestGridHandStyleGUI {
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
		assertEquals(!sel, ((IGrid)pencil.createShapeInstance()).isYLabelWest());
		assertNotEquals(sel, labelsYInvertedCB.isSelected());
	}

	@Test
	public void testSelectlabelsXInvertedCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		boolean sel = labelsXInvertedCB.isSelected();
		clicklabelsXInvertedCB.execute();
		assertEquals(!sel, ((IGrid)pencil.createShapeInstance()).isXLabelSouth());
		assertNotEquals(sel, labelsXInvertedCB.isSelected());
	}
}
