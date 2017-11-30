package net.sf.latexdraw.gui.pencil;

import java.util.Collections;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestFreeHandStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
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
public class TestPencilFreeHandStyle extends TestFreeHandStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeFreeHandCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
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
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testIncrementgapPointsPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns), gapPoints,
			incrementgapPoints, Collections.singletonList(() ->  ((IFreehand)pencil.createShapeInstance()).getInterval()));
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
