package test.gui.hand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import javafx.scene.paint.Color;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeDotCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestDotStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestHandDotStyle extends TestDotStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeDotCustomiser.class).asEagerSingleton();
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
		new CompositeGUIVoidCommand(selectionAddDot, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotDot() {
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
	public void testDotSizeSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddDot, updateIns).execute();
		double angle = dotSizeField.getValue();
		incrementDotSize.execute();
		assertEquals(dotSizeField.getValue(), ((IDot)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getDiametre(), 0.0001);
		assertEquals(dotSizeField.getValue(), ((IDot)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getDiametre(), 0.0001);
		assertNotEquals(angle, dotSizeField.getValue(), 0.0001);
	}

	@Test
	public void testSelectDotStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddDot, updateIns).execute();
		DotStyle style = dotCB.getSelectionModel().getSelectedItem();
		selectNextDotStyle.execute();
		DotStyle newStyle = dotCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IDot)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getDotStyle());
		assertEquals(newStyle, ((IDot)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getDotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectFillingNotEnabledWhenNonFillableStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, updateIns).execute();
		setDotStyle.execute(DotStyle.ASTERISK);
		assertTrue(fillingB.isDisabled());
	}

	@Test
	public void testSelectFillingEnabledWhenFillableStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddDot, updateIns, setDotStyleFillable).execute();
		assertFalse(fillingB.isDisabled());
	}

	@Test
	public void testPickLineColourSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddBezier, selectionAddDot, setDotStyleFillable, updateIns).execute();
		Color col = fillingB.getValue();
		pickFillingColour.execute();
		assertEquals(fillingB.getValue(), ((IDot)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getDotFillingCol().toJFX());
		assertEquals(fillingB.getValue(), ((IDot)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getDotFillingCol().toJFX());
		assertNotEquals(col, fillingB.getValue());
	}
}
