package test.gui.hand;

import com.google.inject.AbstractModule;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeShadowCustomiser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestShadowStyleGUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandShadowStyle extends TestShadowStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeShadowCustomiser.class).asEagerSingleton();
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
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		new CompositeGUIVoidCommand(selectionAddGrid, activateHand, updateIns).execute();
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
	public void testSelectShadowCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddGrid, selectionAddRec, selectionAddRec, updateIns).execute();
		boolean sel = shadowCB.isSelected();
		checkShadow.execute();
		assertEquals(shadowCB.isSelected(), drawing.getSelection().getShapeAt(1).hasShadow());
		assertEquals(shadowCB.isSelected(), drawing.getSelection().getShapeAt(2).hasShadow());
		assertNotEquals(sel, shadowCB.isSelected());
	}

	@Test
	public void testPickShadowColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddGrid, selectionAddRec, selectionAddRec, checkShadow, updateIns).execute();
		Color col = shadowColB.getValue();
		pickShadCol.execute();
		assertEquals(shadowColB.getValue(), drawing.getSelection().getShapeAt(1).getShadowCol().toJFX());
		assertEquals(shadowColB.getValue(), drawing.getSelection().getShapeAt(2).getShadowCol().toJFX());
		assertNotEquals(col, shadowColB.getValue());
	}

	@Test
	public void testIncrementShadowSizeHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddGrid, selectionAddRec, selectionAddRec, checkShadow, updateIns).execute();
		double val = shadowSizeField.getValue();
		incrementshadowSizeField.execute();
		assertEquals(shadowSizeField.getValue(), drawing.getSelection().getShapeAt(1).getShadowSize(), 0.0001);
		assertEquals(shadowSizeField.getValue(), drawing.getSelection().getShapeAt(2).getShadowSize(), 0.0001);
		assertNotEquals(val, shadowSizeField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementShadowAngleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddGrid, selectionAddRec, selectionAddRec, checkShadow, updateIns).execute();
		double val = shadowAngleField.getValue();
		incrementshadowAngleField.execute();
		assertEquals(Math.toRadians(shadowAngleField.getValue()), drawing.getSelection().getShapeAt(1).getShadowAngle(), 0.0001);
		assertEquals(Math.toRadians(shadowAngleField.getValue()), drawing.getSelection().getShapeAt(2).getShadowAngle(), 0.0001);
		assertNotEquals(val, shadowAngleField.getValue(), 0.0001);
	}
}
