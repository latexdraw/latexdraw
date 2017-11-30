package net.sf.latexdraw.gui.hand;

import java.util.Arrays;
import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestShadowStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeShadowCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
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
public class TestHandShadowStyle extends TestShadowStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeShadowCustomiser.class);
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
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		new CompositeGUIVoidCommand(selectionAddGrid, activateHand, updateIns).execute();
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
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddGrid, selectionAddRec, selectionAddRec, checkShadow, updateIns), shadowSizeField,
			incrementshadowSizeField, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).getShadowSize(),
			() ->  drawing.getSelection().getShapeAt(2).getShadowSize()));
	}

	@Test
	public void testIncrementShadowAngleHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddGrid, selectionAddRec, selectionAddRec, checkShadow, updateIns), shadowAngleField,
			incrementshadowAngleField, Arrays.asList(
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(1).getShadowAngle()),
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(2).getShadowAngle())));
	}
}
