package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeBorderCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestLineStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.LineStyle;
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
public class TestPencilLineStyle extends TestLineStyleGUI {
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
				bindAsEagerSingleton(ShapeBorderCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testIncrementFrameArcPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns), frameArcField,
			incrementFrameArc, Collections.singletonList(() ->  ((Rectangle) editing.createShapeInstance()).getLineArc()));
	}

	@Test
	public void testIncrementThicknessPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns), thicknessField,
			incrementThickness, Collections.singletonList(() ->  editing.createShapeInstance().getThickness()));
	}

	@Test
	public void testSelectBorderPosPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final BorderPos style = bordersPosCB.getSelectionModel().getSelectedItem();
		selectBorderPos.execute();
		waitFXEvents.execute();
		assertEquals(bordersPosCB.getSelectionModel().getSelectedItem(), editing.createShapeInstance().getBordersPosition());
		assertNotEquals(style, bordersPosCB.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final LineStyle style = lineCB.getSelectionModel().getSelectedItem();
		selectLineStyle.execute();
		waitFXEvents.execute();
		assertEquals(lineCB.getSelectionModel().getSelectedItem(), editing.createShapeInstance().getLineStyle());
		assertNotEquals(style, lineCB.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testPickLineColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final Color col = lineColButton.getValue();
		pickLineCol.execute();
		waitFXEvents.execute();
		assertEquals(lineColButton.getValue(), editing.createShapeInstance().getLineColour().toJFX());
		assertNotEquals(col, lineColButton.getValue());
	}

	@Test
	public void testCheckShowPointPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final boolean sel = showPoints.isSelected();
		checkShowPts.execute();
		waitFXEvents.execute();
		assertEquals(!sel, editing.createShapeInstance().isShowPts());
	}

	@Test
	public void testWidgetsGoodStateWhenNotShowPointPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertTrue(bordersPosCB.isVisible());
		assertFalse(bordersPosCB.isDisabled());
		assertTrue(frameArcField.isVisible());
		assertFalse(frameArcField.isDisabled());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenNotBorderMovableShowPointablePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isDisabled());
		assertTrue(showPoints.isVisible());
		assertFalse(showPoints.isDisabled());
	}

	@Test
	public void testWidgetsGoodStateWhenNotFrameArcPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesCircle, updateIns).execute();
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertTrue(bordersPosCB.isVisible());
		assertFalse(bordersPosCB.isDisabled());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenNotThicknessablePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		assertFalse(thicknessField.isVisible());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertFalse(lineCB.isVisible());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenNotColourablePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPic, updateIns).execute();
		assertFalse(thicknessField.isVisible());
		assertFalse(lineColButton.isVisible());
		assertFalse(lineCB.isVisible());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testSelectOpenFreehandPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		final boolean isopen = opened.isSelected();
		checkOpened.execute();
		waitFXEvents.execute();
		assertEquals(opened.isSelected(), ((Freehand) editing.createShapeInstance()).isOpened());
		assertNotEquals(isopen, opened.isSelected());
	}

	@Test
	public void testSelectOpenBezierPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final boolean isopen = opened.isSelected();
		checkOpened.execute();
		waitFXEvents.execute();
		assertEquals(opened.isSelected(), ((BezierCurve) editing.createShapeInstance()).isOpened());
		assertNotEquals(isopen, opened.isSelected());
	}
}
