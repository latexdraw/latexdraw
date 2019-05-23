package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeDotCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestDotStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.Dot;
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
public class TestPencilDotStyle extends TestDotStyleGUI {
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
				bindAsEagerSingleton(ShapeDotCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testIncrementDotSizePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesDot, updateIns), dotSizeField,
			incrementDotSize, Collections.singletonList(() ->  ((Dot) editing.createShapeInstance()).getDiametre()));
	}

	@Test
	public void testSelectBARStylePencil() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, () -> setDotStyle.execute(DotStyle.BAR)).execute();
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((Dot) editing.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectASTERISKStylePencil() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, () -> setDotStyle.execute(DotStyle.ASTERISK)).execute();
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((Dot) editing.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectDOTStylePencil() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, () -> setDotStyle.execute(DotStyle.DOT)).execute();
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((Dot) editing.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectFillingNotEnabledWhenNonFillableStylePencil() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, () -> setDotStyle.execute(DotStyle.ASTERISK)).execute();
		assertTrue(fillingB.isDisabled());
	}

	@Test
	public void testSelectFillingEnabledWhenFillableStylePencil() {
		Cmds.of(activatePencil, pencilCreatesDot, updateIns, setDotStyleFillable).execute();
		assertFalse(fillingB.isDisabled());
	}

	@Test
	public void testPickLineColourPencil() {
		Cmds.of(activatePencil, pencilCreatesDot, setDotStyleFillable, updateIns).execute();
		final Color col = fillingB.getValue();
		Cmds.of(pickFillingColour).execute();
		assertEquals(fillingB.getValue(), ((Dot) editing.createShapeInstance()).getDotFillingCol().toJFX());
		assertNotEquals(col, fillingB.getValue());
	}
}
