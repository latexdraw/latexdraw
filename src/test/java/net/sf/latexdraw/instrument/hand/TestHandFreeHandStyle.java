package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeFreeHandCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestFreeHandStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandFreeHandStyle extends TestFreeHandStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapeFreeHandCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
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
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns), gapPoints,
			incrementgapPoints, Arrays.asList(
			() ->  ((Freehand) drawing.getSelection().getShapeAt(1).orElseThrow()).getInterval(),
			() ->  ((Freehand) drawing.getSelection().getShapeAt(2).orElseThrow()).getInterval()));
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns).execute();
		selectLineStyle.execute();
		waitFXEvents.execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((Freehand) drawing.getSelection().getShapeAt(1).orElseThrow()).getType());
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((Freehand) drawing.getSelection().getShapeAt(2).orElseThrow()).getType());
		assertEquals(FreeHandStyle.LINES, freeHandType.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectCurveStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddFreehand, selectionAddFreehand, updateIns).execute();
		selectCurveStyle.execute();
		waitFXEvents.execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((Freehand) drawing.getSelection().getShapeAt(1).orElseThrow()).getType());
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((Freehand) drawing.getSelection().getShapeAt(2).orElseThrow()).getType());
		assertEquals(FreeHandStyle.CURVES, freeHandType.getSelectionModel().getSelectedItem());
	}
}
