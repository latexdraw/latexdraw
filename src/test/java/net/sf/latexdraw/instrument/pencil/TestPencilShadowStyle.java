package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.ShapeShadowCustomiser;
import net.sf.latexdraw.instrument.TestShadowStyleGUI;
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
public class TestPencilShadowStyle extends TestShadowStyleGUI {
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
				bindAsEagerSingleton(ShapeShadowCustomiser.class);
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
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectShadowCBPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		final boolean sel = shadowCB.isSelected();
		Cmds.of(checkShadow).execute();
		assertEquals(!sel, editing.createShapeInstance().hasShadow());
		assertNotEquals(sel, shadowCB.isSelected());
	}

	@Test
	public void testPickShadowColourPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, checkShadow, updateIns).execute();
		final Color col = shadowColB.getValue();
		Cmds.of(pickShadCol).execute();
		assertEquals(shadowColB.getValue(), editing.createShapeInstance().getShadowCol().toJFX());
		assertNotEquals(col, shadowColB.getValue());
	}

	@Test
	public void testIncrementShadowSizePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesRec, checkShadow, updateIns), shadowSizeField,
			incrementshadowSizeField, Collections.singletonList(() -> editing.createShapeInstance().getShadowSize()));
	}

	@Test
	public void testIncrementShadowAnglePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesRec, checkShadow, updateIns),
			shadowAngleField, incrementshadowAngleField,
			Collections.singletonList(() -> Math.toDegrees(editing.createShapeInstance().getShadowAngle())));
	}
}
