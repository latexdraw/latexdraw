package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
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
public class TestPencilDoubleLineStyle extends TestDoubleLineStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeDoubleBorderCustomiser.class);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPic, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testNotDbledWidgetsNotEnabledPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertTrue(dbleBoundColB.isDisabled());
		assertTrue(dbleSepField.isDisabled());
	}

	@Test
	public void testDbledWidgetsEnabledPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectdbleLine, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertFalse(dbleBoundColB.isDisabled());
		assertFalse(dbleSepField.isDisabled());
	}

	@Test
	public void testSelectDbleLinePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final boolean sel = dbleBoundCB.isSelected();
		selectdbleLine.execute();
		waitFXEvents.execute();
		assertEquals(!sel, editing.createShapeInstance().hasDbleBord());
		assertNotEquals(sel, dbleBoundCB.isSelected());
	}

	@Test
	public void testIncrementDbleSpacingPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectdbleLine, updateIns), dbleSepField,
			incrementDbleSep, Collections.singletonList(() ->  editing.createShapeInstance().getDbleBordSep()));
	}

	@Test
	public void testPickDbleColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectdbleLine, updateIns).execute();
		final Color col = dbleBoundColB.getValue();
		pickDbleColour.execute();
		waitFXEvents.execute();
		assertEquals(dbleBoundColB.getValue(), editing.createShapeInstance().getDbleBordCol().toJFX());
		assertNotEquals(col, dbleBoundColB.getValue());
	}
}
