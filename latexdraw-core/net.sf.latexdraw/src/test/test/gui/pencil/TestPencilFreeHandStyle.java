package test.gui.pencil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestFreeHandStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilFreeHandStyle extends TestFreeHandStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeFreeHandCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testIncrementgapPointsPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		double val = gapPoints.getValue();
		incrementgapPoints.execute();
		assertEquals((int)gapPoints.getValue(), ((IFreehand)pencil.createShapeInstance()).getInterval());
		assertNotEquals(val, gapPoints.getValue(), 0.0001);
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		selectLineStyle.execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((IFreehand)pencil.createShapeInstance()).getType());
		assertEquals(FreeHandStyle.LINES, ((IFreehand)pencil.createShapeInstance()).getType());
	}

	@Test
	public void testSelectCurveStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		selectCurveStyle.execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((IFreehand)pencil.createShapeInstance()).getType());
		assertEquals(FreeHandStyle.CURVES, ((IFreehand)pencil.createShapeInstance()).getType());
	}

	@Test
	public void testSelectOpenPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		boolean isopen = open.isSelected();
		selectOpen.execute();
		assertEquals(open.isSelected(), ((IFreehand)pencil.createShapeInstance()).isOpen());
		assertNotEquals(isopen, open.isSelected());
	}
}
