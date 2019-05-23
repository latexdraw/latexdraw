package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeDoubleBorderCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestDoubleLineStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
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
public class TestHandDoubleLineStyle extends TestDoubleLineStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, pencil);
				bindAsEagerSingleton(ShapeDoubleBorderCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionDot() {
		Cmds.of(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotDot() {
		Cmds.of(selectionAddAxes, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testNotDbledWidgetsNotEnabledHand() {
		Cmds.of(activateHand, selectionAddRec, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertTrue(dbleBoundColB.isDisabled());
		assertTrue(dbleSepField.isDisabled());
	}

	@Test
	public void testDbledWidgetsEnabledHand() {
		Cmds.of(activateHand, selectionAddRec, selectdbleLine, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertFalse(dbleBoundColB.isDisabled());
		assertFalse(dbleSepField.isDisabled());
	}

	@Test
	public void testSelectDoubleLineSelection() {
		Cmds.of(activateHand, selectionAddRec, selectionAddAxes, selectionAddRec, updateIns).execute();
		final boolean sel = dbleBoundCB.isSelected();
		Cmds.of(selectdbleLine).execute();
		assertEquals(!sel, drawing.getSelection().getShapeAt(0).orElseThrow().hasDbleBord());
		assertEquals(!sel, drawing.getSelection().getShapeAt(2).orElseThrow().hasDbleBord());
		assertNotEquals(sel, dbleBoundCB.isSelected());
	}

	@Test
	public void testIncrementDbleSpacingSelection() {
		doTestSpinner(Cmds.of(activateHand, selectionAddRec, selectionAddAxes, selectionAddRec, selectdbleLine, updateIns), dbleSepField,
			incrementDbleSep, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(0).orElseThrow().getDbleBordSep(),
			() ->  drawing.getSelection().getShapeAt(2).orElseThrow().getDbleBordSep()));
	}

	@Test
	public void testPickDbleColourSelection() {
		Cmds.of(activateHand, selectionAddRec, selectionAddBezier, selectionAddRec, selectdbleLine, updateIns).execute();
		final Color col = dbleBoundColB.getValue();
		Cmds.of(pickDbleColour).execute();
		assertEquals(dbleBoundColB.getValue(), drawing.getSelection().getShapeAt(0).orElseThrow().getDbleBordCol().toJFX());
		assertEquals(dbleBoundColB.getValue(), drawing.getSelection().getShapeAt(1).orElseThrow().getDbleBordCol().toJFX());
		assertNotEquals(col, dbleBoundColB.getValue());
	}
}
