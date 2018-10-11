package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestFillingStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
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
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeFillingCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
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
	public void testSelectFillingPlainPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final FillingStyle style = fillStyleCB.getSelectionModel().getSelectedItem();
		selectStyle.execute(FillingStyle.PLAIN);
		waitFXEvents.execute();
		final FillingStyle newStyle = fillStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, pencil.createShapeInstance().getFillingStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testPickFillingColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectPlainStyle, updateIns).execute();
		final Color col = fillColButton.getValue();
		pickfillCol.execute();
		waitFXEvents.execute();
		assertEquals(fillColButton.getValue(), pencil.createShapeInstance().getFillingCol().toJFX());
		assertNotEquals(col, fillColButton.getValue());
	}

	@Test
	public void testPickHatchingsColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectHatchingsStyle, updateIns).execute();
		final Color col = hatchColButton.getValue();
		pickhatchCol.execute();
		waitFXEvents.execute();
		assertEquals(hatchColButton.getValue(), pencil.createShapeInstance().getHatchingsCol().toJFX());
		assertNotEquals(col, hatchColButton.getValue());
	}

	@Test
	public void testPickGradStartColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectGradStyle, updateIns).execute();
		final Color col = gradStartColButton.getValue();
		pickgradStartCol.execute();
		waitFXEvents.execute();
		assertEquals(gradStartColButton.getValue(), pencil.createShapeInstance().getGradColStart().toJFX());
		assertNotEquals(col, gradStartColButton.getValue());
	}

	@Test
	public void testPickGradEndColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectGradStyle, updateIns).execute();
		final Color col = gradEndColButton.getValue();
		pickgradEndCol.execute();
		waitFXEvents.execute();
		assertEquals(gradEndColButton.getValue(), pencil.createShapeInstance().getGradColEnd().toJFX());
		assertNotEquals(col, gradEndColButton.getValue());
	}

	@Test
	public void testIncrementGradMidPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectGradStyle), gradMidPtField,
			incrementgradMidPt, Collections.singletonList(() ->  pencil.createShapeInstance().getGradMidPt()));
	}

	@Test
	public void testIncrementGradAnglePencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectGradStyle), gradAngleField,
			incrementgradAngle, Collections.singletonList(() ->  Math.toDegrees(pencil.createShapeInstance().getGradAngle())));
	}

	@Test
	public void testIncrementHatchAnglePencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle), hatchAngleField,
			incrementhatchAngle, Collections.singletonList(() ->  Math.toDegrees(pencil.createShapeInstance().getHatchingsAngle())));
	}

	@Test
	public void testIncrementHatchWidthPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle), hatchWidthField,
			incrementhatchWidth, Collections.singletonList(() ->  pencil.createShapeInstance().getHatchingsWidth()));
	}

	@Test
	public void testIncrementHatchSepPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle), hatchSepField,
			incrementhatchSep, Collections.singletonList(() ->  pencil.createShapeInstance().getHatchingsSep()));
	}
}
