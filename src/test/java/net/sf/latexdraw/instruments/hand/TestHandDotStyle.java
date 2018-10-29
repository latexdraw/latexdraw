package net.sf.latexdraw.instruments.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeDotCustomiser;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestDotStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
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
public class TestHandDotStyle extends TestDotStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeDotCustomiser.class);
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
	public void testControllerActivatedWhenSelectionDot() {
		new CompositeGUIVoidCommand(selectionAddDot, activateHand, updateIns).execute();
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
	public void testDotSizeSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddDot, updateIns), dotSizeField,
			incrementDotSize, Arrays.asList(
			() ->  ((IDot) drawing.getSelection().getShapeAt(0)).getDiametre(),
			() ->  ((IDot) drawing.getSelection().getShapeAt(2)).getDiametre()));
	}

	@Test
	public void testSelectDotStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddDot, updateIns).execute();
		final DotStyle style = dotCB.getSelectionModel().getSelectedItem();
		selectNextDotStyle.execute();
		waitFXEvents.execute();
		final DotStyle newStyle = dotCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IDot) drawing.getSelection().getShapeAt(0)).getDotStyle());
		assertEquals(newStyle, ((IDot) drawing.getSelection().getShapeAt(2)).getDotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectFillingNotEnabledWhenNonFillableStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, updateIns).execute();
		setDotStyle.execute(DotStyle.ASTERISK);
		waitFXEvents.execute();
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
		final Color col = fillingB.getValue();
		pickFillingColour.execute();
		waitFXEvents.execute();
		assertEquals(fillingB.getValue(), ((IDot) drawing.getSelection().getShapeAt(0)).getDotFillingCol().toJFX());
		assertEquals(fillingB.getValue(), ((IDot) drawing.getSelection().getShapeAt(2)).getDotFillingCol().toJFX());
		assertNotEquals(col, fillingB.getValue());
	}
}
