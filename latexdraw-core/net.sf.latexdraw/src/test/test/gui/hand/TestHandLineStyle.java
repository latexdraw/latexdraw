package test.gui.hand;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUICommand;
import test.gui.ShapePropModule;
import test.gui.TestLineStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestHandLineStyle extends TestLineStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeBorderCustomiser.class).asEagerSingleton();
				bind(Hand.class).asEagerSingleton();
				bind(Pencil.class).toInstance(pencil);
			}
		};
	}
	

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUICommand(activateHand, updateIns, checkInsDeactivated).execute();
	}
	
	
	@Test
	public void testControllerActivatedWhenSelectionNotEmpty() {
		new CompositeGUICommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertTrue(bordersPosCB.isVisible());
		assertFalse(bordersPosCB.isDisabled());
		assertTrue(frameArcField.isVisible());
		assertFalse(frameArcField.isDisabled());
		assertFalse(showPoints.isVisible());
	}
	
	
	@Test
	public void testCheckShowPointSelection() {
		new CompositeGUICommand(activateHand, selectionAddBezier, selectionAddBezier, updateIns).execute();
		boolean sel = showPoints.isSelected();
		checkShowPts.execute();
		assertEquals(!sel, hand.getCanvas().getDrawing().getSelection().getShapeAt(0).isShowPts());
		assertEquals(!sel, hand.getCanvas().getDrawing().getSelection().getShapeAt(1).isShowPts());
	}
	
	
	@Test
	public void testPickLineColourSelection() {
		new CompositeGUICommand(activateHand, selectionAddRec, selectionAddBezier, updateIns).execute();
		Color col = lineColButton.getValue();
		pickLineCol.execute();
		assertEquals(lineColButton.getValue(), hand.getCanvas().getDrawing().getSelection().getShapeAt(0).getLineColour().toJFX());
		assertEquals(lineColButton.getValue(), hand.getCanvas().getDrawing().getSelection().getShapeAt(1).getLineColour().toJFX());
		assertNotEquals(col, lineColButton.getValue());
	}
}
