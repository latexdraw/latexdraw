package net.sf.latexdraw.gui.hand;

import com.google.inject.AbstractModule;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeDoubleBorderCustomiser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropModule;
import net.sf.latexdraw.gui.TestDoubleLineStyleGUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandDoubleLineStyle extends TestDoubleLineStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeDoubleBorderCustomiser.class).asEagerSingleton();
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
	public void testControllerActivatedWhenSelectionDot() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotDot() {
		new CompositeGUIVoidCommand(selectionAddAxes, activateHand, updateIns).execute();
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
	public void testNotDbledWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertTrue(dbleBoundColB.isDisabled());
		assertTrue(dbleSepField.isDisabled());
	}

	@Test
	public void testDbledWidgetsEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectdbleLine, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertFalse(dbleBoundColB.isDisabled());
		assertFalse(dbleSepField.isDisabled());
	}

	@Test
	public void testSelectDoubleLineSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddAxes, selectionAddRec, updateIns).execute();
		boolean sel = dbleBoundCB.isSelected();
		selectdbleLine.execute();
		assertEquals(!sel, drawing.getSelection().getShapeAt(0).hasDbleBord());
		assertEquals(!sel, drawing.getSelection().getShapeAt(2).hasDbleBord());
		assertNotEquals(sel, dbleBoundCB.isSelected());
	}

	@Test
	public void testIncrementDbleSpacingSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddAxes, selectionAddRec, selectdbleLine, updateIns).execute();
		final double value = dbleSepField.getValue();
		incrementDbleSep.execute();
		assertEquals(dbleSepField.getValue(), drawing.getSelection().getShapeAt(0).getDbleBordSep(), 0.0001);
		assertEquals(dbleSepField.getValue(), drawing.getSelection().getShapeAt(2).getDbleBordSep(), 0.0001);
		assertNotEquals(value, dbleSepField.getValue(), 0.0001);
	}

	@Test
	public void testPickDbleColourSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddBezier, selectionAddRec, selectdbleLine, updateIns).execute();
		Color col = dbleBoundColB.getValue();
		pickDbleColour.execute();
		assertEquals(dbleBoundColB.getValue(), drawing.getSelection().getShapeAt(0).getDbleBordCol().toJFX());
		assertEquals(dbleBoundColB.getValue(), drawing.getSelection().getShapeAt(1).getDbleBordCol().toJFX());
		assertNotEquals(col, dbleBoundColB.getValue());
	}
}
