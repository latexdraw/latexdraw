package net.sf.latexdraw.gui.pencil;

import com.google.inject.AbstractModule;
import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropModule;
import net.sf.latexdraw.gui.TestDotStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeDotCustomiser;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
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
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeDotCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		double val = dotSizeField.getValue();
		incrementDotSize.execute();
		assertEquals(dotSizeField.getValue(), ((IDot)pencil.createShapeInstance()).getDiametre(), 0.0001);
		assertNotEquals(val, dotSizeField.getValue(), 0.0001);
	}

	@Test
	public void testSelectBARStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.BAR);
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((IDot)pencil.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectASTERISKStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.ASTERISK);
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((IDot)pencil.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectDOTStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.DOT);
		assertEquals(dotCB.getSelectionModel().getSelectedItem(), ((IDot)pencil.createShapeInstance()).getDotStyle());
	}

	@Test
	public void testSelectFillingNotEnabledWhenNonFillableStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns).execute();
		setDotStyle.execute(DotStyle.ASTERISK);
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
		Color col = fillingB.getValue();
		pickFillingColour.execute();
		assertEquals(fillingB.getValue(), ((IDot)pencil.createShapeInstance()).getDotFillingCol().toJFX());
		assertNotEquals(col, fillingB.getValue());
	}
}
