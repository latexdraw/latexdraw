package net.sf.latexdraw.gui.hand;

import com.google.inject.AbstractModule;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropModule;
import net.sf.latexdraw.gui.TestFreeHandStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandFreeHandStyle extends TestFreeHandStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeFreeHandCustomiser.class).asEagerSingleton();
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
		new CompositeGUIVoidCommand(selectionAddFreehand, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotDot() {
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
	public void testIncrementgapPointsSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns).execute();
		double val = gapPoints.getValue();
		incrementgapPoints.execute();
		assertEquals((int)gapPoints.getValue(), ((IFreehand)drawing.getSelection().getShapeAt(1)).getInterval());
		assertEquals((int)gapPoints.getValue(), ((IFreehand)drawing.getSelection().getShapeAt(2)).getInterval());
		assertNotEquals(val, gapPoints.getValue(), 0.0001);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns).execute();
		selectLineStyle.execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((IFreehand)drawing.getSelection().getShapeAt(1)).getType());
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((IFreehand)drawing.getSelection().getShapeAt(2)).getType());
		assertEquals(FreeHandStyle.LINES, freeHandType.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectCurveStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns).execute();
		selectCurveStyle.execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((IFreehand)drawing.getSelection().getShapeAt(1)).getType());
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((IFreehand)drawing.getSelection().getShapeAt(2)).getType());
		assertEquals(FreeHandStyle.CURVES, freeHandType.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectOpenSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns).execute();
		boolean isopen = open.isSelected();
		selectOpen.execute();
		assertEquals(open.isSelected(), ((IFreehand)drawing.getSelection().getShapeAt(1)).isOpen());
		assertEquals(open.isSelected(), ((IFreehand)drawing.getSelection().getShapeAt(2)).isOpen());
		assertNotEquals(isopen, open.isSelected());
	}
}
