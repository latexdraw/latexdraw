package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
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
public class TestPencilFreeHandStyle extends TestFreeHandStyleGUI {
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
				bindAsEagerSingleton(ShapeFreeHandCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesFreehand, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesFreehand, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testIncrementgapPointsPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesFreehand, updateIns), gapPoints,
			incrementgapPoints, Collections.singletonList(() ->  ((Freehand) editing.createShapeInstance()).getInterval()));
	}

	@Test
	public void testSelectLineStylePencil() {
		Cmds.of(activatePencil, pencilCreatesFreehand, updateIns, selectLineStyle).execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((Freehand) editing.createShapeInstance()).getType());
		assertEquals(FreeHandStyle.LINES, ((Freehand) editing.createShapeInstance()).getType());
	}

	@Test
	public void testSelectCurveStylePencil() {
		Cmds.of(activatePencil, pencilCreatesFreehand, updateIns, selectCurveStyle).execute();
		assertEquals(freeHandType.getSelectionModel().getSelectedItem(), ((Freehand) editing.createShapeInstance()).getType());
		assertEquals(FreeHandStyle.CURVES, ((Freehand) editing.createShapeInstance()).getType());
	}
}
