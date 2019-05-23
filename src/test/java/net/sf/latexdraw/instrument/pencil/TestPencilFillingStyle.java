package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeFillingCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestFillingStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.FillingStyle;
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
public class TestPencilFillingStyle extends TestFillingStyleGUI {
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
				bindAsEagerSingleton(ShapeFillingCustomiser.class);
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
	public void testSelectFillingPlainPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		final FillingStyle style = fillStyleCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectStyle.execute(FillingStyle.PLAIN)).execute();
		final FillingStyle newStyle = fillStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, editing.createShapeInstance().getFillingStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testPickFillingColourPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, selectPlainStyle, updateIns).execute();
		final Color col = fillColButton.getValue();
		Cmds.of(pickfillCol).execute();
		assertEquals(fillColButton.getValue(), editing.createShapeInstance().getFillingCol().toJFX());
		assertNotEquals(col, fillColButton.getValue());
	}

	@Test
	public void testPickHatchingsColourPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, selectHatchingsStyle, updateIns).execute();
		final Color col = hatchColButton.getValue();
		Cmds.of(pickhatchCol).execute();
		assertEquals(hatchColButton.getValue(), editing.createShapeInstance().getHatchingsCol().toJFX());
		assertNotEquals(col, hatchColButton.getValue());
	}

	@Test
	public void testPickGradStartColourPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, selectGradStyle, updateIns).execute();
		final Color col = gradStartColButton.getValue();
		Cmds.of(pickgradStartCol).execute();
		assertEquals(gradStartColButton.getValue(), editing.createShapeInstance().getGradColStart().toJFX());
		assertNotEquals(col, gradStartColButton.getValue());
	}

	@Test
	public void testPickGradEndColourPencil() {
		Cmds.of(activatePencil, pencilCreatesRec, selectGradStyle, updateIns).execute();
		final Color col = gradEndColButton.getValue();
		Cmds.of(pickgradEndCol).execute();
		assertEquals(gradEndColButton.getValue(), editing.createShapeInstance().getGradColEnd().toJFX());
		assertNotEquals(col, gradEndColButton.getValue());
	}

	@Test
	public void testIncrementGradMidPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectGradStyle), gradMidPtField,
			incrementgradMidPt, Collections.singletonList(() ->  editing.createShapeInstance().getGradMidPt()));
	}

	@Test
	public void testIncrementGradAnglePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectGradStyle), gradAngleField,
			incrementgradAngle, Collections.singletonList(() ->  Math.toDegrees(editing.createShapeInstance().getGradAngle())));
	}

	@Test
	public void testIncrementHatchAnglePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle), hatchAngleField,
			incrementhatchAngle, Collections.singletonList(() ->  Math.toDegrees(editing.createShapeInstance().getHatchingsAngle())));
	}

	@Test
	public void testIncrementHatchWidthPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle), hatchWidthField,
			incrementhatchWidth, Collections.singletonList(() ->  editing.createShapeInstance().getHatchingsWidth()));
	}

	@Test
	public void testIncrementHatchSepPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle), hatchSepField,
			incrementhatchSep, Collections.singletonList(() ->  editing.createShapeInstance().getHatchingsSep()));
	}
}
