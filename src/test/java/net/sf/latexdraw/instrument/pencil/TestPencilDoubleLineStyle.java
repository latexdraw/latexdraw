package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
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
public class TestPencilDoubleLineStyle extends TestDoubleLineStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindAsEagerSingleton(ShapeDoubleBorderCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesPic, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testNotDbledWidgetsNotEnabledPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertTrue(dbleBoundColB.isDisabled());
		assertTrue(dbleSepField.isDisabled());
	}

	@Test
	public void testDbledWidgetsEnabledPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, selectdbleLine, updateIns).execute();
		assertFalse(dbleBoundCB.isDisabled());
		assertFalse(dbleBoundColB.isDisabled());
		assertFalse(dbleSepField.isDisabled());
	}

	@Test
	public void testSelectDbleLinePencil() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		final boolean sel = dbleBoundCB.isSelected();
		Cmds.of(selectdbleLine).execute();
		assertEquals(!sel, editing.createShapeInstance().hasDbleBord());
		assertNotEquals(sel, dbleBoundCB.isSelected());
	}

	@Test
	public void testIncrementDbleSpacingPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesRec, selectdbleLine, updateIns), dbleSepField,
			incrementDbleSep, Collections.singletonList(() ->  editing.createShapeInstance().getDbleBordSep()));
	}

	@Test
	public void testPickDbleColourPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, selectdbleLine, updateIns).execute();
		final Color col = dbleBoundColB.getValue();
		Cmds.of(pickDbleColour).execute();
		assertEquals(dbleBoundColB.getValue(), editing.createShapeInstance().getDbleBordCol().toJFX());
		assertNotEquals(col, dbleBoundColB.getValue());
	}
}
