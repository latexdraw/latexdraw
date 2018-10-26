package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
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
import org.malai.javafx.ui.JfxUI;
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
				bindAsEagerSingleton(ShapeDotCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testIncrementDotSizePencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns), dotSizeField,
			incrementDotSize, Collections.singletonList(() ->  ((IDot)pencil.createShapeInstance()).getDiametre()));
	}

	@Test
	public void testSelectBARStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.BAR);
		waitFXEvents.execute();
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((IDot)pencil.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectASTERISKStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.ASTERISK);
		waitFXEvents.execute();
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((IDot)pencil.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectDOTStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.DOT);
		waitFXEvents.execute();
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((IDot)pencil.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectFillingNotEnabledWhenNonFillableStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.ASTERISK);
		waitFXEvents.execute();
		assertTrue(fillingB.isDisabled());
	}

	@Test
	public void testSelectFillingEnabledWhenFillableStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns, setDotStyleFillable).execute();
		assertFalse(fillingB.isDisabled());
	}

	@Test
	public void testPickLineColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, setDotStyleFillable, updateIns).execute();
		final Color col = fillingB.getValue();
		pickFillingColour.execute();
		waitFXEvents.execute();
		assertEquals(fillingB.getValue(), ((IDot)pencil.createShapeInstance()).getDotFillingCol().toJFX());
		assertNotEquals(col, fillingB.getValue());
	}
}
